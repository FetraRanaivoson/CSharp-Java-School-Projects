using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Text;

namespace WpfReview.Model
{
    public class Student: INotifyPropertyChanged
    {
        public int Id { get; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public ObservableCollection<double> ExamResults { get; }

        private ObservableCollection<double> listOfGrades;
        public ObservableCollection<double> ListOfGrades
        {
            get => listOfGrades;
            set
            {
                listOfGrades = value;
            }
        }

        private double grade;
        public double Grade
        {
            get => grade;
            
            set
            {
                if (value < 0)
                    throw new ArgumentException("Invalid grade", nameof(Grade));
                grade = value;
                AddGradeToList(grade);
                AverageGrade = CalculateAverageGrades();
                NotifyPropertyChanged(nameof(ListOfGrades));
                NotifyPropertyChanged(nameof(Grade));
                NotifyPropertyChanged(nameof(AverageGrade));
            }
        }

        private double CalculateAverageGrades()
        {
            double sumOfGrade = 0;
            foreach (double grade in ListOfGrades)
                sumOfGrade += grade;
            return sumOfGrade / ListOfGrades.Count;
        }

        private double averageGrade;
        public double AverageGrade
        {
            get => averageGrade;
            set
            {
                averageGrade = value;
            }
        }


        private void AddGradeToList(double grade)
        {
            ListOfGrades.Add(grade);
        }

      
        public Student (int id, string firstName, string lastName)
        {
            ValidateData(firstName, lastName);
            Id = id;
            FirstName = firstName;
            LastName = lastName;
            ListOfGrades = new ObservableCollection<double>();
        }

        private void ValidateData (string firstName, string lastName)
        {
            if (string.IsNullOrWhiteSpace(firstName))
                throw new ArgumentException("Must not be null", nameof(firstName));
            if (string.IsNullOrWhiteSpace(lastName))
                throw new ArgumentException("Must not be null", nameof(lastName));
        }

        public event PropertyChangedEventHandler PropertyChanged;
        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }


        private double getGradeFromView;
        public double GetGradeFromView
        {
            get => getGradeFromView;
            set
            {
                getGradeFromView = value;
            }
        }

    }
}
