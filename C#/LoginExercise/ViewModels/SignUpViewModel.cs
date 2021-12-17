using LoginExercise.Services;
using System;
using System.Collections.Generic;
using System.Text;
using Utility.Monads;
using Utility.ViewModels;

namespace LoginExercise.ViewModels
{
    public class SignUpViewModel : ViewModel
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

        public readonly UserService UserService;
        public DelegateCommand SignUpCommand { get; } //Sign up button
        public DelegateCommand LogInCommand { get; } //Log in link
        public event Action <Result> SignUpAttempted; //Sign up success or not
        public event Action LogInRequested; //Switch to log in window

        public SignUpViewModel (UserService UserService)
        {
            this.UserService = UserService;
            SignUpCommand = new DelegateCommand(SignUp);
            LogInCommand = new DelegateCommand(LogIn);
        }

        private void SignUp (object _) //Command for Sign up button
        {
            Result signUpResult = UserService.SignUp(Username, Password);
            if (signUpResult.Successful)
                ClearData();
            SignUpAttempted?.Invoke(signUpResult);
        }

        private void LogIn (object _)
        {
            LogInRequested?.Invoke();
        }

        public void ClearData()
        {
            Username = null;
            Password = null;
        }
    }
}
