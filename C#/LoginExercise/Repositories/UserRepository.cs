using System;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using LoginExercise.Entities;
using Utility.Authentication;

namespace LoginExercise.Repositories
{
    public interface IUserRepository
    {
        User Get(long id);
        User Get(string username);
        bool Exists(long id);
        bool Exists(string username);
        void Add(User user);
        bool Delete(User user);
        bool Update(User user);
    }

    public class UserRepository : IUserRepository
    {
        private readonly string connectionString;

        public UserRepository()
        {
            connectionString = ConfigurationManager.ConnectionStrings["LibraryDatabase"].ConnectionString;
        }

        public User Get(long id)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select Id, DateCreated, DateModified, Username, Salt, Hash "
                + "from dbo.Users "
                + "where Id = @Id ";

            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = id;

            SqlDataReader reader = command.ExecuteReader();
            if (reader.Read())
                return ExtractNextUser(reader);
            return null;
        }

        public User Get(string username)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select Id, DateCreated, DateModified, Username, Salt, Hash "
                + "from dbo.Users "
                + "where Username = @Username ";

            command.Parameters.Add("@Username", SqlDbType.NVarChar).Value = username;

            SqlDataReader reader = command.ExecuteReader();
            if (reader.Read())
                return ExtractNextUser(reader);
            return null;
        }

        private User ExtractNextUser(SqlDataReader reader)
        {
            long id = reader.GetInt64(0);
            DateTime dateCreated = reader.GetDateTime(1);
            DateTime dateModified = reader.GetDateTime(2);
            string username = reader.GetString(3);
            
            byte[] salt = (byte[])reader.GetValue(4);
            byte[] hash = (byte[])reader.GetValue(5);
            PasswordHash password = new PasswordHash(salt, hash);

            return new User(id, dateCreated, dateModified, username, password);
        }

        public bool Exists(long id)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select count(Id) from dbo.Users "
                + "where Id = @Id ";

            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = id;

            int count = (int)command.ExecuteScalar();
            return (count > 0);
        }

        public bool Exists(string username)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select count(Username) from dbo.Users "
                + "where Username = @Username ";

            command.Parameters.Add("@Username", SqlDbType.NVarChar).Value = username;

            int count = (int)command.ExecuteScalar();
            return (count > 0);
        }

        public void Add(User user)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "insert into dbo.Users "
                + "(DateCreated, DateModified, Username, Salt, Hash) "
                + "output inserted.Id "
                + "values(@DateCreated, @DateModified, @Username, @Salt, @Hash) ";

            user.DateCreated = user.DateModified = DateTime.UtcNow;
            command.Parameters.Add("@DateCreated", SqlDbType.DateTime2).Value = user.DateCreated;
            command.Parameters.Add("@DateModified", SqlDbType.DateTime2).Value = user.DateModified;
            command.Parameters.Add("@Username", SqlDbType.NVarChar).Value = user.Username;
            command.Parameters.Add("@Salt", SqlDbType.VarBinary).Value = user.Salt;
            command.Parameters.Add("@Hash", SqlDbType.VarBinary).Value = user.Hash;

            user.Id = (long)command.ExecuteScalar();
        }

        public bool Delete(User user)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "delete from dbo.Users "
                + "where Id = @Id ";

            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = user.Id;

            int rowsAffected = command.ExecuteNonQuery();
            return (rowsAffected > 0);
        }

        public bool Update(User user)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "update dbo.Users "
                + "set DateModified = @DateModified, Username = @Username, Salt = @Salt, Hash = @Hash "
                + "where Id = @Id ";

            user.DateModified = DateTime.UtcNow;
            command.Parameters.Add("@DateModified", SqlDbType.DateTime2).Value = user.DateModified;

            command.Parameters.Add("@Username", SqlDbType.NVarChar).Value = user.Username;
            command.Parameters.Add("@Salt", SqlDbType.VarBinary).Value = user.Salt;
            command.Parameters.Add("@Hash", SqlDbType.VarBinary).Value = user.Hash;
            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = user.Id;

            int rowsAffected = command.ExecuteNonQuery();
            return (rowsAffected > 0);
        }
    }
}
