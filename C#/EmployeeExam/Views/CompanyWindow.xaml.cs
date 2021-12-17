using EmployeeExam.ViewModels;
using System.Windows;

namespace EmployeeExam.Views
{
    public partial class CompanyWindow : Window
    {
        public CompanyWindow(CompanyViewModel viewModel)
        {
            InitializeComponent();
            DataContext = viewModel;
        }
    }
}
