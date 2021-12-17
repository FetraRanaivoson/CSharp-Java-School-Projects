using System.Windows;
using StudentWpfExercise.ViewModels;

namespace StudentWpfExercise.Views
{
    public partial class MainWindow : Window
    {
        private readonly MainViewModel mainViewModel;

        public MainWindow()
        {
            InitializeComponent();
            DataContext = mainViewModel = new MainViewModel();
        }

        private void WindowLoaded(object sender, RoutedEventArgs e)
        {
            MinWidth = Width;
            MinHeight = Height;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            double grade =double.Parse( GradeTextBox.Text);
            //mainViewModel.ShowAverage.TakeExam(grade);
            //AverageLabel.Content = mainViewModel.ShowAverage.AverageGrade;
            double msg = mainViewModel.ShowAverage.AverageGrade;
            MessageBox.Show(""+msg);
        }

      
    }
}
