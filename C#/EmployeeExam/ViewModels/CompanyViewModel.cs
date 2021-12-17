using EmployeeExam.Entities;
using EmployeeExam.Services;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;

namespace EmployeeExam.ViewModels
{
    public class CompanyViewModel : ViewModel
    {
        // TODO:
        // Add properties for view data
        // Call NotifyPropertyChanged for any view model properties that need to update the view
        // Add commands for view controls
        // Use the employee service to get and update employees

        public ObservableCollection <Employee> Employees { get; }
        public IList<Employee> list;

        public AddEmployeeViewModel AddEmployeeViewModel { get; }
        public DelegateCommand AddEmployeeCommand { get; }
        public event Action <string> OnAddedEmployee;

        private Employee selectedEmployee;
        public Employee SelectedEmployee
        {
            get => selectedEmployee;
            set
            {
                selectedEmployee = value;
                NotifyPropertyChanged(nameof(SelectedEmployee));
            }
        }

        private readonly IEmployeeService service;

        public CompanyViewModel(IEmployeeService service)
        {
            this.service = service;
            list = service.GetAllEmployees();
            Employees = new ObservableCollection<Employee>();
            Employees = ReturnExistingEmployees();

            AddEmployeeViewModel = new AddEmployeeViewModel();

            AddEmployeeCommand = new DelegateCommand(AddEmployee);
        }

        private ObservableCollection<Employee> ReturnExistingEmployees()
        {
            for ( int i = 0; i < list.Count; i++)
            {
                Employees.Add(list[i]);
            }
            return Employees;
        }

        public void AddEmployee (object _)
        {
            Employee employee = new Employee(
                AddEmployeeViewModel.FirstName,
                AddEmployeeViewModel.LastName,
                AddEmployeeViewModel.DateOfBirth,
                AddEmployeeViewModel.JobTitle,
                AddEmployeeViewModel.HourlyWage,
                0, 0, 0
            );

            Employees.Add(employee);
            service.AddEmployee(employee);
            OnAddedEmployee.Invoke("Employee successfully added!");
        }
    }
}
