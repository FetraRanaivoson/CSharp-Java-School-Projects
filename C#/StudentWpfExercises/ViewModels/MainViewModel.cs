using StudentWpfExercise.Models;
using System.Collections.ObjectModel;
using System.ComponentModel;

namespace StudentWpfExercise.ViewModels
{
    public class MainViewModel : ViewModel, INotifyPropertyChanged
    {
        private int nextId = 1000001;

        public MainViewModel()
        {

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
