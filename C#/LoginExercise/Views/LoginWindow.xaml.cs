using LoginExercise.ViewModels;
using System.Windows;

namespace LoginExercise.Views
{
    public partial class LoginWindow : Window
    {

        public LoginWindow(LoginViewModel loginViewModel)
        {
            InitializeComponent();
            DataContext = loginViewModel;
        }
    }
}
