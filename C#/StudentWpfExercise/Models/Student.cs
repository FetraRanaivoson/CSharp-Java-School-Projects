using StudentWpfExercise.ViewModels;
using System.Collections.ObjectModel;
using System.ComponentModel;

namespace StudentWpfExercise.Models
{
    public class Student : INotifyPropertyChanged
    {
        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion

        public int Id { get; }

        private string firstName;
        public string FirstName
        {
            get => firstName;
            set
            {
                firstName = value;
                NotifyPropertyChanged(nameof(FirstName));
            }
        }

        private string lastName;
        public string LastName
        {
            get => lastName;
            set
            {
                lastName = value;
                NotifyPropertyChanged(nameof(LastName));
            }
        }

        public ObservableCollection<TakeExamViewModel> ExamResults { get; }

        public double AverageGrade => GetAverageGrade();

        public Student(int id, string firstName, string lastName)
        {
            Id = id;
            FirstName = firstName;
            LastName = lastName;
            ExamResults = new ObservableCollection<TakeExamViewModel>();
        }

        public void TakeExam(double grade)
        {
            TakeExamViewModel gradeObject = new TakeExamViewModel(grade);
            gradeObject.PropertyChanged += OnGradeChanged;
            ExamResults.Add(gradeObject);
            NotifyPropertyChanged(nameof(AverageGrade));
            NotifyPropertyChanged(nameof(ExamResults));
        }

        private void OnGradeChanged (object sender, PropertyChangedEventArgs eventArgs)
        {
            NotifyPropertyChanged(nameof(AverageGrade));
        }
        private double GetAverageGrade()
        {
            double total = 0;
            foreach (TakeExamViewModel result in ExamResults)
                total += result.GradeValue;
            if (ExamResults.Count > 0) // Prevents division by 0 if empty
                return total / ExamResults.Count;
            return 0;
        }
    }
}
