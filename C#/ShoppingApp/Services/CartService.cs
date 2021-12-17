//Author: Fetra Ranaivoson
using ShoppingApp.Model;
using ShoppingApp.ViewModels;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;
using Utility.Authentication;
using Utility.Monads;

namespace ShoppingApp.Services
{
    public interface ICartService
    {
        Result Login(string username, string password);
        Result SignUp(string username, string password);
        Result DeleteAccount(string username, string password);
    }

    public class CartService : ICartService, INotifyPropertyChanged
    {
        private readonly ICartRepository repository;

        private Cart signedUpUser;
        public Cart SignedUpUser
        {
            get => signedUpUser;
            set
            {
                signedUpUser = value;
                NotifyPropertyChanged(nameof(SignedUpUser));
            }
        }

        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion

        private Cart loggedInUser;
        public Cart LoggedInUser
        {
            get => loggedInUser;
            set
            {
                loggedInUser = value;
                NotifyPropertyChanged(nameof(LoggedInUser));
            }
        }

        public CartService (ICartRepository repository)
        {
            this.repository = repository;
        }

        public Result DeleteAccount(string username, string password)
        {
            throw new NotImplementedException();
        }

        public Result Login(string username, string password)
        {
            //Validate username
            if (!repository.Exists(username))
                return Result.Error("Invalid username.");

            //If valid username, get that User
            LoggedInUser= repository.Get(username);

            //Check that User password and password hash
            bool correct = PasswordUtility.CheckPassword(password, LoggedInUser.Password);
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
            SignedUpUser = new Cart(username, passwordHash, 100);

            //Then add that user to the repository
            repository.Add(SignedUpUser);

            return Result.Success();
        }

        public Result UpdateBalance(Cart user)
        {
            if (user != null) { 
                repository.UpdateBalance(user);
                return Result.Success();
            }
            return Result.Error("No user logged in");       
        }
    }
}
