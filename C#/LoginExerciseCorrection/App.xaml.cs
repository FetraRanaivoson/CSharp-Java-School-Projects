using System.Windows;
using LoginExercise.Repositories;
using LoginExercise.Services;
using LoginExercise.ViewModels;
using LoginExercise.Views;

namespace LoginExercise
{
    public partial class App : Application
    {
        private LoginWindow loginWindow;
        private SignUpWindow signUpWindow;

        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            loginWindow = CreateLoginWindow();
            loginWindow.Show();
        }

        private LoginWindow CreateLoginWindow()
        {
            UserRepository repository = new UserRepository();
            UserService service = new UserService(repository);
            LoginViewModel viewModel = new LoginViewModel(service);
            LoginWindow window = new LoginWindow(viewModel);
            
            viewModel.SignUpRequested += GoToSignUp;

            return window;
        }

        private SignUpWindow CreateSignUpWindow()
        {
            UserRepository repository = new UserRepository();
            UserService service = new UserService(repository);
            SignUpViewModel viewModel = new SignUpViewModel(service);
            SignUpWindow window = new SignUpWindow(viewModel);
            
            viewModel.LoginRequested += GoToLogin;

            return window;
        }

        /// <summary> Navigate from LoginWindow to SignUpWindow. </summary>
        private void GoToSignUp()
        {
            signUpWindow = CreateSignUpWindow();

            loginWindow.Close();
            loginWindow = null;

            signUpWindow.Show();
        }

        /// <summary> Navigate from SignUpWindow to LoginWindow. </summary>
        private void GoToLogin()
        {
            loginWindow = CreateLoginWindow();

            signUpWindow.Close();
            signUpWindow = null;

            loginWindow.Show();
        }
    }
}
