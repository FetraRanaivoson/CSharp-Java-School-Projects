using Utility.Monads;
using LoginExercise.Repositories;
using Utility.Authentication;
using LoginExercise.Entities;
using System.Windows;

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
            //Validate username
            if (!repository.Exists(username))
                return Result.Error("Invalid username.");

            //If valid username, get that User
            User user = repository.Get(username);

            //Check that User password and password hash
            bool correct = PasswordUtility.CheckPassword(password,user.Password);
            if (!correct)
                return Result.Error("Invalid password");

            return Result.Success();
        }

        public Result SignUp(string username, string password)
        {
            //Check if username already exist
            if (repository.Exists(username))
                return Result.Error("The username " + username + " is already taken!");

            //If username free, hash the password and create a new user
            PasswordHash passwordHash = PasswordUtility.GeneratePasswordHash(password);
            User user = new User(username, passwordHash);

            //Then add that user to the repository
            repository.Add(user);

            return Result.Success();
           
           
        }

        public Result DeleteAccount(string username, string password)
        {
            //From an ADMIN point of view
            //Validate if existing user
            if (!repository.Exists(username))
                return Result.Error("There's no such user in database.");

            //If user exists
            User user = repository.Get(username);
            repository.Delete(user);

            return Result.Success();
        }


    }
}
