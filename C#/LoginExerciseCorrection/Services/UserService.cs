using Utility.Authentication;
using Utility.Monads;
using LoginExercise.Repositories;
using LoginExercise.Entities;

namespace LoginExercise.Services
{
    public interface IUserService
    {
        Result Login(string username, string password);
        Result SignUp(string username, string password);
        Result DeleteAccount(string username, string password);
    }

    public class UserService : IUserService
    {
        private readonly IUserRepository repository;

        public UserService(IUserRepository repository)
        {
            this.repository = repository;
        }

        public Result Login(string username, string password)
        {
            if (InvalidUsernameFormat(username))
                return Result.Error("Invalid username.");
            if (InvalidPasswordFormat(password))
                return Result.Error("Invalid password.");

            if (!repository.Exists(username))
                return Result.Error("Invalid username.");

            User user = repository.Get(username);
            bool correct = PasswordUtility.CheckPassword(password, user.Password);
            if (!correct)
                return Result.Error("Invalid password.");

            return Result.Success();
        }

        public Result SignUp(string username, string password)
        {
            if (InvalidUsernameFormat(username))
                return Result.Error("Invalid username.");
            if (InvalidPasswordFormat(password))
                return Result.Error("Invalid password.");

            if (repository.Exists(username))
                return Result.Error("Username already exists.");

            PasswordHash passwordHash = PasswordUtility.GeneratePasswordHash(password);
            User user = new User(username, passwordHash);
            repository.Add(user);
            return Result.Success();
        }

        public Result DeleteAccount(string username, string password)
        {
            if (InvalidUsernameFormat(username))
                return Result.Error("Invalid username.");
            if (InvalidPasswordFormat(password))
                return Result.Error("Invalid password.");

            User user = repository.Get(username);
            bool correct = PasswordUtility.CheckPassword(password, user.Password);
            if (!correct)
                return Result.Error("Invalid password.");

            repository.Delete(user);
            return Result.Success();
        }

        private bool InvalidUsernameFormat(string username)
        {
            return string.IsNullOrWhiteSpace(username);
        }

        private bool InvalidPasswordFormat(string password)
        {
            return string.IsNullOrWhiteSpace(password);
        }
    }
}
