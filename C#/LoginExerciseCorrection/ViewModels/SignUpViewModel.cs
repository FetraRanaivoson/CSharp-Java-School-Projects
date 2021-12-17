using System;
using System.ComponentModel;
using Utility.Authentication;
using Utility.Monads;
using Utility.ViewModels;
using LoginExercise.Services;

namespace LoginExercise.ViewModels
{
    public class SignUpViewModel : ViewModel, INotifyPropertyChanged
    {
        public event Action<Result> SignUpAttempted;
        public event Action LoginRequested;

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

        public DelegateCommand SignUpCommand { get; }
        public DelegateCommand GoToLoginCommand { get; }

        private readonly IUserService service;
        private IPasswordAccessor passwordAccessor;

        public SignUpViewModel(IUserService service)
        {
            this.service = service;
            passwordAccessor = null;

            SignUpCommand = new DelegateCommand(SignUp, CanSignUp);
            GoToLoginCommand = new DelegateCommand(GoToLogin);
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
            SignUpCommand.NotifyCanExecuteChanged();
        }

        /// <summary>
        /// When the view calls this method (via LoginCommand),
        /// the Login button will become enabled if it returns true,
        /// and the button will become disabled if it returns false.
        /// </summary>
        private bool CanSignUp(object _)
        {
            return !string.IsNullOrWhiteSpace(Username)
                && !string.IsNullOrWhiteSpace(passwordAccessor?.Password);
        }

        /// <summary>
        /// Attempt to sign up using the entered username and password.
        /// </summary>
        private void SignUp(object _)
        {
            Result result = service.SignUp(Username, passwordAccessor?.Password);
            SignUpAttempted?.Invoke(result);

            ClearUsernameAndPassword();
        }

        private void ClearUsernameAndPassword()
        {
            Username = "";
            passwordAccessor?.ClearPassword();
        }

        /// <summary> Navigate to the login window. </summary>
        private void GoToLogin(object _)
        {
            LoginRequested?.Invoke();
        }
    }
}
