using System;
using System.Collections.Generic;
using EmployeeExam.Data;
using EmployeeExam.Entities;
using EmployeeExam.Monads;

namespace EmployeeExam.Services
{
    public interface IEmployeeService
    {
        Result<Employee> GetEmployee(long id);
        IList<Employee> GetAllEmployees();

        Result AddEmployee(Employee employee);
        Result RemoveEmployee(Employee employee);
        Result LogHours(Employee employee, decimal additionalHoursWorked);
        Result GiveRaise(Employee employee, decimal raisePercentage);
        Result PayEmployee(Employee employee);
        Result UpdateEmployee(Employee employee);
    }

    public class EmployeeService : IEmployeeService
    {
        private readonly IEmployeeRepository repository;

        public EmployeeService(IEmployeeRepository repository)
        {
            this.repository = repository;
        }

        public Result<Employee> GetEmployee(long id)
        {
            repository.Get(id);
            return (Result<Employee>)Result.Success();
            
        }

        public IList<Employee> GetAllEmployees()
        {
            return repository.GetAll();
            
        }

        public Result AddEmployee(Employee employee)
        {
            repository.Add(employee);
            return Result.Success();
        }

        public Result RemoveEmployee(Employee employee)
        {
            // TODO
            return null;
        }

        public Result LogHours(Employee employee, decimal additionalHoursWorked)
        {
            // TODO
            // Validate parameters
            // If valid:
            //      Call employee.LogHours
            //      Using repository, update employee
            // Return result: either Result.Success() or Result.Error("<Insert error message here>")
            return null;
        }

        public Result GiveRaise(Employee employee, decimal raisePercentage)
        {
            // TODO
            // Validate parameters
            // If valid:
            //      Call employee.GiveRaise
            //      Using repository, update employee
            // Return result: either Result.Success() or Result.Error("<Insert error message here>")
            return null;
        }

        public Result PayEmployee(Employee employee)
        {
            // TODO
            // Validate parameters
            // If valid:
            //      Call employee.PayAmountDue
            //      Using repository, update employee
            // Return result: either Result.Success() or Result.Error("<Insert error message here>")
            return null;
        }

        public Result UpdateEmployee(Employee employee)
        {
            //Admin view Model
            //
            //SelectedEmployee.JobTitle = UpdateEmployeeViewModel.JobTitle;
            //service.UpdateEmployee(SelectedEmployee);

            repository.Update(employee);
            // TODO
            // Using repository, update employee
            // Return result: either Result.Success() or Result.Error("<Insert error message here>")
            return null;
        }
    }
}
