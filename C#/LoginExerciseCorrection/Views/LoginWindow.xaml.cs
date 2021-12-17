using System;
using System.Windows;
using Utility.Authentication;
using Utility.Monads;
using LoginExercise.ViewModels;

namespace LoginExercise.Views
{
    public partial class LoginWindow : Window, IPasswordAccessor
    {
        /// <remarks>
        /// The window implements IPasswordAccessor as a workaround
        /// because of the fact that binding is specifically not allowed
        /// for the Password property in PasswordBoxes.
        /// 
        /// The view model will be given an IPasswordAccessor reference
        /// to the window, so that the view model:
        ///   1. Can access the password entered by the user,
        ///   2. Can be notified when the password changes, and
        ///   3. Can clear the password.
        /// </remarks>
        
        #region IPasswordAccessor
        string IPasswordAccessor.Password => PasswordTextBox.Password;

        private Action passwordChanged;
        event Action IPasswordAccessor.PasswordChanged
        {
            add => passwordChanged += value;
            remove => passwordChanged -= value;
        }

        void IPasswordAccessor.ClearPassword()
        {
            PasswordTextBox.Password = "";
        }
        #endregion

        public LoginWindow(LoginViewModel viewModel)
        {
            InitializeComponent();
            DataContext = viewModel;

            // Give view model an IPasswordAccessor reference to the window
            // so that the view model can access the password without binding
            viewModel.SetPasswordAccessor(this);

            // Add handler method to view model's LoginAttempted event
            viewModel.LoginAttempted += LoginAttempted;
        }

        private void WindowLoaded(object sender, RoutedEventArgs e)
        {
            MinWidth = MaxWidth = Width;
            MinHeight = MaxHeight = Height;
        }

        private void PasswordBoxPasswordChanged(object sender, RoutedEventArgs e)
        {
            passwordChanged?.Invoke(); // Notify view model that the password changed
        }

        /// <summary>
        /// Handler method for responding to
        /// the view model's LoginAttempted event.
        /// </summary>
        private void LoginAttempted(Result result)
        {
            string message = result.Successful ? "Login successful." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Exclamation);

            UsernameTextBox.Focus();
        }
    }
}
