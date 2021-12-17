using System.Windows;
using EmployeeExam.Data;
using EmployeeExam.Services;
using EmployeeExam.ViewModels;
using EmployeeExam.Views;

namespace EmployeeExam
{
    public partial class App : Application
    {
        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            EmployeeRepository repository = new EmployeeRepository();
            EmployeeService service = new EmployeeService(repository);
            CompanyViewModel viewModel = new CompanyViewModel(service);
            CompanyWindow window = new CompanyWindow(viewModel);
            window.Show();

            viewModel.OnAddedEmployee += OnAddedEmployee;
        }

        private void OnAddedEmployee (string message)
        {
            MessageBox.Show(message);
        }


    }
}
