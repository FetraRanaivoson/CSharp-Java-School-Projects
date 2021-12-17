using LoginExercise.Entities;
using LoginExercise.Repositories;
using LoginExercise.Services;
using LoginExercise.ViewModels;
using LoginExercise.Views;
using System.Windows;
using Utility.Authentication;
using Utility.Monads;

namespace LoginExercise
{
    public partial class App : Application
    {
        private LoginWindow loginWindow;
        private SignUpWindow signUpWindow;

        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            loginWindow = CreateLoginWindow(); //Default window shown (logIn Window)
            loginWindow.Show();
        }

        private LoginWindow CreateLoginWindow ()
        {
            UserRepository repository = new UserRepository();
            UserService service = new UserService(repository);
            LoginViewModel viewModel = new LoginViewModel(service);
            LoginWindow window = new LoginWindow(viewModel); //The Log in window is using the
                                                             //Login VM for data context

            viewModel.LogInAttempted += LoginAttempted;
            viewModel.SignUpRequested += GoToSignUp; //ViewModel has an event for the SignUp link
                                                        //Which is to display a signUp window
                                                        //Where its data context is Sign Up VM

            return window;
        }

        private void GoToSignUp ()
        {
            signUpWindow = CreateSignUpWindow();
            loginWindow.Close();
            loginWindow = null;
            signUpWindow.Show();
        }

        private SignUpWindow CreateSignUpWindow()
        {
            UserRepository repository = new UserRepository();
            UserService service = new UserService(repository);
            SignUpViewModel viewModel = new SignUpViewModel(service);
            SignUpWindow window = new SignUpWindow(viewModel);

            viewModel.SignUpAttempted += SignUpAttempted;
            viewModel.LogInRequested += GoToLogIn;

            return window;
        }

        private void LoginAttempted (Result result)
        {
            string message = result.Successful ? "Login successful." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Exclamation);
        }
       
        private void SignUpAttempted (Result result)
        {
            string message = result.Successful ? "Sign up successful." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Exclamation);
        }

        private void GoToLogIn ()
        {
            loginWindow = CreateLoginWindow();
            signUpWindow.Close();
            signUpWindow = null;
            loginWindow.Show();
        }

        private void SignUp ()
        {
            /*

            //Check: does username already exist? If yes, error!

            //Else, if not
            string username = "admin";
            string password = "abc";

            PasswordHash passwordHash = PasswordUtility.GeneratePasswordHash(password);
            User user = new User(username, passwordHash);

            //Add a new User record to the database
            //Save Salt and Hash in that record
            UserRepository userRepository = new UserRepository();
            userRepository.Add(user);


            */
        }

        /*
        private bool Login (string username, string password)
        {
            /*


            //Check: Does username already exist?
            //If no, error!

            //Else, if yes:
            //Query database to get that user's Salt and Hash
            PasswordHash passwordHash = null;
            bool success = PasswordUtility.CheckPassword(password, passwordHash);

            return success;

        }
        */
    }
}
