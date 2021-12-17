using System.Collections.ObjectModel;
using System.Windows;
using Utility;
using WpfReview.Model;
using WpfReview.Views;

namespace WpfReview.ViewModels
{

    public class MainViewModel : ViewModel
    {
        private int nextId = 10001;
        public ObservableCollection<Student> Students { get; }

        public DelegateCommand ClearCommand { get; }
        public DelegateCommand RemoveCommand { get; }
        public DelegateCommand AddGrade { get; }
        public DelegateCommand AddStudent { get; }

        public AddStudentViewModel AddStudentViewModel { get; }
        
        /*
        private ObservableCollection<double> listOfGrades;
        public ObservableCollection<double> ListOfGrades
        {
            get => listOfGrades;
            set
            {
                listOfGrades = value;
            }
        }
        */

        /*
        private double grade;
        public double Grade
        {
            get => grade;
            set
            {
                grade = value; //
                SelectedStudent.Grade = value;

                AddGradeToList(grade); //
                NotifyPropertyChanged(nameof(ListOfGrades));
                AverageGrade = CalculateAverageGrades();
                NotifyPropertyChanged(nameof(Grade));
                NotifyPropertyChanged(nameof(AverageGrade));

            }
        }
        */

        /*
        private double averageGrade;
        public double AverageGrade
        {
            get => averageGrade;
            set
            {
                averageGrade = value;
            }
        }
        */


        /*
        private double CalculateAverageGrades ()
        {
            double sumOfGrade = 0;
            foreach (double grade in ListOfGrades)
                sumOfGrade += grade;
            return sumOfGrade / ListOfGrades.Count;
        }
        */

        /*
        private void AddGradeToList (double grade)
        {
            ListOfGrades.Add(grade);
        }
        */


        public Student selectedStudent;
        public Student SelectedStudent 
        {
            get => selectedStudent;
            set
            {
                selectedStudent = value;
                NotifyPropertyChanged(nameof(SelectedStudent));
            }
        }

        public MainViewModel ()
        {

            Students = CreateStudents();
            AddStudentViewModel = new AddStudentViewModel();
            //ListOfGrades = new ObservableCollection<double>();
            ClearCommand = new DelegateCommand(Clear);
            RemoveCommand = new DelegateCommand(Remove);
            AddGrade = new DelegateCommand(Grade);
            AddStudent = new DelegateCommand(AddStudents);
        }

        private void Clear (object _)
        {
            Students.Clear();
        }

        private void Remove(object _)
        {
            if (SelectedStudent != null)
                Students.Remove(SelectedStudent);
        }

        private void Grade(object parameter)
        {

            if (SelectedStudent != null)
                SelectedStudent.Grade = SelectedStudent.GetGradeFromView ;
            //Or we can use a separate AddGradeViewModel where we can
            //bind the gradeTextBox to double.Parse(AddGradeViewModel.Text)
        }

        private void AddStudents (object _)
        {
            Student student = new Student
                (GetUniqueId(),
                AddStudentViewModel.FirstName,
                AddStudentViewModel.LastName);
            Students.Add(student);
            AddStudentViewModel.ClearData();
        }

        private ObservableCollection<Student> CreateStudents ()
        {
            return new ObservableCollection<Student>
            {
                new Student (GetUniqueId(),"Fetra","Ranaivoson"),
                new Student (GetUniqueId(),"Jemima", "Rakotonarivo"),
                new Student (GetUniqueId(),"Amandeep", "Kaur"),
                new Student (GetUniqueId(), "Rasoa", "Kininike")
            };
        }
        private int GetUniqueId()
        {
            return nextId++;
        }
    }
}
