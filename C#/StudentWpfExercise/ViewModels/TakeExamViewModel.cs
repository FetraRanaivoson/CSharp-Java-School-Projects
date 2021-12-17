using StudentWpfExercise.Models;
using System.Collections.Generic;
using System.ComponentModel;

namespace StudentWpfExercise.ViewModels
{
    public class TakeExamViewModel : ViewModel, INotifyPropertyChanged
    {
        

        private double gradeValue { get; set; }
        public double GradeValue
        {
            get => gradeValue;
            set
            {
                gradeValue = value;
                NotifyPropertyChanged(nameof(GradeValue));
            }
        }

        public TakeExamViewModel (double gradeValue)
        {
            GradeValue = gradeValue;
        }
        
    }
}
