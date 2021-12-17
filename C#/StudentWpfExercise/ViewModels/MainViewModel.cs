using StudentWpfExercise.Models;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows;
using Utility;

namespace StudentWpfExercise.ViewModels
{
    public class MainViewModel : ViewModel
    {
        private int nextId = 1000001;

        public ObservableCollection<Student> Students { get; }


        private Student showAverage;
        public Student ShowAverage
        {
            get => showAverage;
            set
            {
                showAverage = value;
                NotifyPropertyChanged(nameof(ShowAverage));
              
            }
        }

        private Student selectedStudent;
        public Student SelectedStudent {
            get => selectedStudent;
            set
            {
                selectedStudent = value;
                NotifyPropertyChanged(nameof(SelectedStudent));
            }
        }

        public AddStudentViewModel AddStudentViewModel { get; }
        //public TakeExamViewModel TakeExamViewModel { get; }

        public DelegateCommand AddCommand { get; } //1
        public DelegateCommand RemoveCommand { get; } //1
        //public DelegateCommand TakeExamCommand { get; }//1

        public MainViewModel()
        {
            Students = CreateStudents();
            AddStudentViewModel = new AddStudentViewModel();
            //TakeExamViewModel = new TakeExamViewModel();
            AddCommand = new DelegateCommand(Add); //2
            RemoveCommand = new DelegateCommand(Remove); //2
            //TakeExamCommand = new DelegateCommand(TakeExam); //2
        }

        //private void TakeExam (object _) //3
        //{

        //}

        private void Remove (object _) //3
        {
            if (selectedStudent != null)
                Students.Remove(SelectedStudent);

        }
        private void Add (object _) //3
        {
            Student student = new Student(GetUniqueId(),
                                           AddStudentViewModel.FirstName,
                                           AddStudentViewModel.LastName
                                           );
            Students.Add(student);
            AddStudentViewModel.ClearData();
        }


        private ObservableCollection<Student> CreateStudents()
        {
            return new ObservableCollection<Student>
            {
                new Student(GetUniqueId(), "Raj", "Patel"),
                new Student(GetUniqueId(), "Amandine", "Duval"),
                new Student(GetUniqueId(), "Michael", "Bentley"),
                new Student(GetUniqueId(), "Veronika", "Weiss")
            };
        }

        private int GetUniqueId()
        {
            return nextId++;
        }
    }
}
