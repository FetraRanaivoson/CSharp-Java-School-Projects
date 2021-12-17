using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using EmployeeExam.Entities;

namespace EmployeeExam.Data
{
    public interface IEmployeeRepository
    {
        Employee Get(long id);
        IList<Employee> GetAll();
        IList<Employee> GetAll(string nameFilter, string jobTitleFilter = null);
        IList<Employee> GetAll(DateTime minDateOfBirth, DateTime maxDateOfBirth = default);
        IList<Employee> GetAllWithPaymentOwed();
        void Add(Employee employee);
        bool Remove(Employee employee);
        bool Update(Employee employee);
    }

    public class EmployeeRepository : IEmployeeRepository
    {
        private static readonly string SelectCommandCore =
            "SELECT TOP(1000) Id, DateCreated, DateModified, "
            + "FirstName, LastName, DateOfBirth, JobTitle, "
            + "HourlyWage, HoursWorked, HoursPaid, PaymentReceived "
            + "FROM dbo.Employees ";
        private static readonly string Where = "WHERE ";
        private static readonly string And = "AND ";
        private static readonly string OrderBy = "ORDER BY ";
        
        private readonly string connectionString;

        public EmployeeRepository()
        {
            connectionString = ConfigurationManager.ConnectionStrings["D05ExamDatabase"].ConnectionString;
        }

        public Employee Get(long id)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = SelectCommandCore + "WHERE Id = @Id ";
            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = id;

            SqlDataReader reader = command.ExecuteReader();
            if (reader.Read())
                return ExtractNextEmployee(reader);

            return null;
        }

        public IList<Employee> GetAll()
        {
            return GetAllCore(whereClause: null, orderByClause: null);
        }

        public IList<Employee> GetAll(string nameFilter, string jobTitleFilter = null)
        {
            bool filterByName = !string.IsNullOrWhiteSpace(nameFilter);
            bool filterByJobTitle = !string.IsNullOrWhiteSpace(jobTitleFilter);

            string nameCondition = "(FirstName LIKE @NameFilter OR LastName LIKE @NameFilter) ";
            string jobTitleCondition = "JobTitle LIKE @JobTitleFilter ";

            int whereConditions = 0;
            string whereClause = null;
            List<SqlParameter> parameters = new List<SqlParameter>();

            if (filterByName)
                AddWhereCondition(nameCondition, new SqlParameter("@NameFilter", SqlDbType.NVarChar) { Value = "%" + nameFilter + "%" });
            if (filterByJobTitle)
                AddWhereCondition(jobTitleCondition, new SqlParameter("@JobTitleFilter", SqlDbType.NVarChar) { Value = "%" + jobTitleFilter + "%" });

            void AddWhereCondition(string condition, SqlParameter parameter)
            {
                whereClause += (whereConditions <= 0) ? Where : And;
                whereClause += condition;
                parameters.Add(parameter);
                whereConditions++;
            }

            string orderByClause = null;
            if (filterByName)
                orderByClause = OrderBy + "LastName, FirstName ";
            else if (filterByJobTitle)
                orderByClause = OrderBy + "JobTitle ";

            return GetAllCore(whereClause, orderByClause, parameters.ToArray());
        }

        public IList<Employee> GetAll(DateTime minDateOfBirth, DateTime maxDateOfBirth = default)
        {
            bool filterByMin = !minDateOfBirth.Equals(default);
            bool filterByMax = !maxDateOfBirth.Equals(default);

            string minClause = Where + "DateOfBirth >= @MinDateOfBirth ";
            string maxClause = Where + "DateOfBirth <= @MaxDateOfBirth ";
            string betweenClause = Where + "DateOfBirth BETWEEN @MinDateOfBirth AND @MaxDateOfBirth ";
            string orderByClause = OrderBy + "DateOfBirth ";

            SqlParameter minParameter = new SqlParameter("@MinDateOfBirth", SqlDbType.Date) { Value = minDateOfBirth };
            SqlParameter maxParameter = new SqlParameter("@MaxDateOfBirth", SqlDbType.Date) { Value = maxDateOfBirth };

            if (filterByMin && filterByMax)
                return GetAllCore(betweenClause, orderByClause, minParameter, maxParameter);
            else if (filterByMin)
                return GetAllCore(minClause, orderByClause, minParameter);
            else if (filterByMax)
                return GetAllCore(maxClause, orderByClause, maxParameter);
            else
                return GetAllCore(null, orderByClause);
        }

        public IList<Employee> GetAllWithPaymentOwed()
        {
            string whereClause = Where + "HoursWorked > HoursPaid ";
            string orderByClause = OrderBy + "(HoursWorked - HoursPaid) DESC";
            return GetAllCore(whereClause, orderByClause);
        }

        private IList<Employee> GetAllCore(string whereClause, string orderByClause, params SqlParameter[] parameters)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = SelectCommandCore + whereClause + orderByClause;
            command.Parameters.AddRange(parameters);

            SqlDataReader reader = command.ExecuteReader();
            List<Employee> employees = new List<Employee>();

            while (reader.Read())
            {
                employees.Add(ExtractNextEmployee(reader));
            }

            return employees;
        }

        private Employee ExtractNextEmployee(SqlDataReader reader)
        {
            long id = reader.GetInt64(0);
            DateTime dateCreated = reader.GetDateTime(1);
            DateTime dateModified = reader.GetDateTime(2);
            string firstName = reader.GetString(3);
            string lastName = reader.GetString(4);
            DateTime dateOfBirth = reader.GetDateTime(5);
            string jobTitle = reader.GetString(6);
            decimal hourlyWage = reader.GetDecimal(7);
            decimal hoursWorked = reader.GetDecimal(8);
            decimal hoursPaid = reader.GetDecimal(9);
            decimal paymentReceived = reader.GetDecimal(10);

            return new Employee(id, dateCreated, dateModified,
                firstName, lastName, dateOfBirth, jobTitle,
                hourlyWage, hoursWorked, hoursPaid, paymentReceived);
        }

        public void Add(Employee employee)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "INSERT INTO dbo.Employees "
                + "(DateCreated, DateModified, "
                + "FirstName, LastName, DateOfBirth, JobTitle, "
                + "HourlyWage, HoursWorked, HoursPaid, PaymentReceived) "
                + "OUTPUT INSERTED.Id "
                + "VALUES(@DateCreated, @DateModified, "
                + "@FirstName, @LastName, @DateOfBirth, @JobTitle, "
                + "@HourlyWage, @HoursWorked, @HoursPaid, @PaymentReceived) ";

            employee.DateCreated = employee.DateModified = DateTime.UtcNow;
            command.Parameters.Add("@DateCreated", SqlDbType.DateTime2).Value = employee.DateCreated;
            command.Parameters.Add("@DateModified", SqlDbType.DateTime2).Value = employee.DateModified;

            command.Parameters.Add("@FirstName", SqlDbType.NVarChar).Value = employee.FirstName;
            command.Parameters.Add("@LastName", SqlDbType.NVarChar).Value = employee.LastName;
            command.Parameters.Add("@DateOfBirth", SqlDbType.Date).Value = employee.DateOfBirth;
            command.Parameters.Add("@JobTitle", SqlDbType.NVarChar).Value = employee.JobTitle;
            command.Parameters.Add("@HourlyWage", SqlDbType.Decimal).Value = employee.HourlyWage;
            command.Parameters.Add("@HoursWorked", SqlDbType.Decimal).Value = employee.HoursWorked;
            command.Parameters.Add("@HoursPaid", SqlDbType.Decimal).Value = employee.HoursPaid;
            command.Parameters.Add("@PaymentReceived", SqlDbType.Decimal).Value = employee.PaymentReceived;

            employee.Id = (long)command.ExecuteScalar();
        }

        public bool Remove(Employee employee)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "DELETE from dbo.Employees WHERE Id = @Id ";
            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = employee.Id;

            int rowsAffected = command.ExecuteNonQuery();

            return rowsAffected > 0;
        }

        public bool Update(Employee employee)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "UPDATE dbo.Employees "
                + "SET DateModified = @DateModified, "
                + "FirstName = @FirstName, LastName = @LastName, "
                + "DateOfBirth = @DateOfBirth, JobTitle = @JobTitle, "
                + "HourlyWage = @HourlyWage, HoursWorked = @HoursWorked, "
                + "HoursPaid = @HoursPaid, PaymentReceived = @PaymentReceived "
                + "WHERE Id = @Id ";

            employee.DateModified = DateTime.UtcNow;
            command.Parameters.Add("@DateModified", SqlDbType.DateTime2).Value = employee.DateModified;

            command.Parameters.Add("@FirstName", SqlDbType.NVarChar).Value = employee.FirstName;
            command.Parameters.Add("@LastName", SqlDbType.NVarChar).Value = employee.LastName;
            command.Parameters.Add("@DateOfBirth", SqlDbType.Date).Value = employee.DateOfBirth;
            command.Parameters.Add("@JobTitle", SqlDbType.NVarChar).Value = employee.JobTitle;
            command.Parameters.Add("@HourlyWage", SqlDbType.Decimal).Value = employee.HourlyWage;
            command.Parameters.Add("@HoursWorked", SqlDbType.Decimal).Value = employee.HoursWorked;
            command.Parameters.Add("@HoursPaid", SqlDbType.Decimal).Value = employee.HoursPaid;
            command.Parameters.Add("@PaymentReceived", SqlDbType.Decimal).Value = employee.PaymentReceived;
            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = employee.Id;

            int rowsAffected = command.ExecuteNonQuery();

            return rowsAffected > 0;
        }
    }
}
