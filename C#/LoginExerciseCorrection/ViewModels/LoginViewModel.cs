using System;
using System.ComponentModel;
using Utility.Authentication;
using Utility.Monads;
using Utility.ViewModels;
using LoginExercise.Services;

namespace LoginExercise.ViewModels
{
    public class LoginViewModel : ViewModel, INotifyPropertyChanged
    {
        public event Action<Result> LoginAttempted;
        public event Action SignUpRequested;

        private string username;
        public string Username
        {
            get => username;
            set
            {
                username = value;
                NotifyPropertyChanged(nameof(Username));
                NotifyCanLoginChanged();
            }
        }

        public DelegateCommand LoginCommand { get; }
        public DelegateCommand GoToSignUpCommand { get; }

        private readonly IUserService service;
        private IPasswordAccessor passwordAccessor;

        public LoginViewModel(IUserService service)
        {
            this.service = service;
            passwordAccessor = null;

            LoginCommand = new DelegateCommand(Login, CanLogin);
            GoToSignUpCommand = new DelegateCommand(GoToSignUp);
        }
        
        /// <summary>
        /// Sets the view model's password accessor in order for it to
        /// be able to access to a plaintext password string.
        /// </summary>
        /// <param name="passwordAccessor">A non-null reference to an object that enables access to a plaintext password string.</param>
        public void SetPasswordAccessor(IPasswordAccessor passwordAccessor)
        {
            this.passwordAccessor = passwordAccessor ?? throw new ArgumentNullException(nameof(passwordAccessor));
            passwordAccessor.PasswordChanged += NotifyCanLoginChanged;
        }

        /// <summary>
        /// Notify view that there may have been a change to
        /// whether LoginCommand can or cannot execute,
        /// meaning that the Login button may need to
        /// become enabled or disabled.
        /// </summary>
        private void NotifyCanLoginChanged()
        {
            LoginCommand.NotifyCanExecuteChanged();
        }

        /// <summary>
        /// When the view calls this method (via LoginCommand),
        /// the Login button will become enabled if it returns true,
        /// and the button will become disabled if it returns false.
        /// </summary>
        private bool CanLogin(object _)
        {
            return !string.IsNullOrWhiteSpace(Username)
                && !string.IsNullOrWhiteSpace(passwordAccessor?.Password);
        }

        /// <summary>
        /// Attempt to login using the entered username and password.
        /// </summary>
        private void Login(object _)
        {
            Result result = service.Login(Username, passwordAccessor?.Password);
            LoginAttempted?.Invoke(result);

            ClearUsernameAndPassword();
        }

        private void ClearUsernameAndPassword()
        {
            Username = "";
            passwordAccessor?.ClearPassword();
        }

        /// <summary> Navigate to the sign up window. </summary>
        private void GoToSignUp(object _)
        {
            SignUpRequested?.Invoke();
        }
    }
}
