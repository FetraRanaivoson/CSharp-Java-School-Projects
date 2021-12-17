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

        public ObservableCollection<double> ExamResults { get; }

        public double AverageGrade => GetAverageGrade();

        public Student(int id, string firstName, string lastName)
        {
            Id = id;
            FirstName = firstName;
            LastName = lastName;
            ExamResults = new ObservableCollection<double>();
        }

        public void TakeExam(double grade)
        {
            ExamResults.Add(grade);
            NotifyPropertyChanged(nameof(AverageGrade));
        }

        private double GetAverageGrade()
        {
            double total = 0;
            foreach (double result in ExamResults)
                total += result;
            if (ExamResults.Count > 0) // Prevents division by 0 if empty
                return total / ExamResults.Count;
            return 0;
        }
    }
}
