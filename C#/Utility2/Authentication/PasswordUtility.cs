using System;
using System.Security.Cryptography;
using System.Text;

namespace Utility.Authentication
{
    public static class PasswordUtility
    {
        /// <summary>
        /// Generates an array of random bytes using a cryptographic random number generator.
        /// </summary>
        /// <param name="bytes">The size in bytes of the array to be generated. Must not be less than 1.</param>
        /// <returns>The randomly generated salt as an array of bytes.</returns>
        /// <exception cref="ArgumentOutOfRangeException">Bytes must not be less than 1.</exception>
        public static byte[] GenerateSalt(int bytes)
        {
            if (bytes < 1)
                throw new ArgumentOutOfRangeException("bytes", "Bytes must not be less than 1.");

            byte[] salt = new byte[bytes];
            using var random = new RNGCryptoServiceProvider();
            random.GetBytes(salt);
            return salt;
        }

        /// <summary>
        /// Hashes the password with a generated random salt, and returns the salt and hash.
        /// </summary>
        /// <param name="password">The password to be hashed.</param>
        /// <returns>A PasswordHash object containing the salt and hash.</returns>
        public static PasswordHash GeneratePasswordHash(string password)
        {
            return GeneratePasswordHash(password, GenerateSalt(32));
        }

        /// <summary>
        /// Hashes the password with the specified salt, and returns the salt and hash.
        /// </summary>
        /// <param name="password">The password to be hashed.</param>
        /// <param name="salt">The random salt to be combined with the password before hashing.</param>
        /// <returns>A PasswordHash object containing the salt and hash.</returns>
        public static PasswordHash GeneratePasswordHash(string password, byte[] salt)
        {
            return GeneratePasswordHash(StringToBytes(password), salt);
        }

        /// <summary>
        /// Hashes the password with the specified salt, and returns the salt and hash.
        /// </summary>
        /// <param name="password">The password to be hashed.</param>
        /// <param name="salt">The random salt to be combined with the password before hashing.</param>
        /// <returns>A PasswordHash object containing the salt and hash.</returns>
        public static PasswordHash GeneratePasswordHash(byte[] password, byte[] salt)
        {
            byte[] saltedPassword = CombinePasswordAndSalt(password, salt);
            byte[] hash = new SHA256Managed().ComputeHash(saltedPassword);
            return new PasswordHash(salt, hash);
        }

        private static byte[] CombinePasswordAndSalt(byte[] password, byte[] salt)
        {
            byte[] saltedPassword = new byte[password.Length + salt.Length];
            for (int i = 0; i < password.Length; i++)
                saltedPassword[i] = password[i];
            for (int i = 0; i < salt.Length; i++)
                saltedPassword[password.Length + i] = salt[i];
            return saltedPassword;
        }

        /// <summary>
        /// Checks whether the given password correctly matches the stored password hash.
        /// </summary>
        /// <param name="password">The password to check.</param>
        /// <param name="passwordHash">The stored password hash and salt.</param>
        /// <returns>True if the password is correct, false if incorrect.</returns>
        public static bool CheckPassword(string password, PasswordHash passwordHash)
        {
            return CheckPassword(StringToBytes(password), passwordHash);
        }

        /// <summary>
        /// Checks whether the given password correctly matches the stored password hash.
        /// </summary>
        /// <param name="password">The password to check.</param>
        /// <param name="passwordHash">The stored password hash and salt.</param>
        /// <returns>True if the password is correct, false if incorrect.</returns>
        public static bool CheckPassword(byte[] password, PasswordHash passwordHash)
        {
            PasswordHash generatedSaltedHash = GeneratePasswordHash(password, passwordHash.Salt);
            return CompareByteArrays(generatedSaltedHash.Hash, passwordHash.Hash);
        }

        private static bool CompareByteArrays(byte[] a, byte[] b)
        {
            if (a.Length != b.Length)
                return false;

            for (int i = 0; i < a.Length; i++)
            {
                if (a[i] != b[i])
                    return false;
            }

            return true;
        }

        private static byte[] StringToBytes(string s)
        {
            return Encoding.UTF8.GetBytes(s);
        }
    }
}
