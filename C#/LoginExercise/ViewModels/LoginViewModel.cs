using LoginExercise.Repositories;
using LoginExercise.Services;
using System;
using System.Collections.Generic;
using System.Text;
using Utility.Monads;
using Utility.ViewModels;

namespace LoginExercise.ViewModels
{
    public class LoginViewModel : ViewModel
    {
        private string username;
        public string Username
        {
            get => username;
            set
            {
                username = value;
                NotifyPropertyChanged(nameof(Username));

            }
        }
        private string password;
        public string Password
        {
            get => password;
            set
            {
                password = value;
                NotifyPropertyChanged(nameof(Password));
            }
        }

        public DelegateCommand LogInCommand { get; } //Log in button
        public DelegateCommand SignUpCommand { get; } //Sign up link
        public event Action <Result> LogInAttempted; //Log in success or not
        public event Action SignUpRequested; // Switch to Sign up window

        //public UserRepository UserRepository {get;}
        private readonly UserService UserService;
 
        public LoginViewModel (UserService UserService)
        {
            //UserRepository = new UserRepository();
            //UserService = new UserService(UserRepository);
            this.UserService = UserService;
            LogInCommand = new DelegateCommand(LogIn);
            SignUpCommand = new DelegateCommand(SignUp);
        }

        private void LogIn (object _) //Command for Log in button
        {
            //UserService.Login(CreateUserViewModel.Username, CreateUserViewModel.Password);
            //CreateUserViewModel.ClearData();
            Result logInResult = UserService.Login(Username, Password);
            if (logInResult.Successful)
                ClearData();
            LogInAttempted?.Invoke(logInResult);
        }

        private void SignUp (object _) //Command For switching to Sign up window
        {
            SignUpRequested?.Invoke();
        }

        public void ClearData()
        {
            Username = null;
            Password = null;
        }
    }
}
