using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using System.Text;

namespace WpfReview.Model
{
    public class StudentRepository
    {
        private readonly string connectionString;
        public StudentRepository ()
        {
            connectionString = ConfigurationManager.ConnectionStrings["StudentDatabase"].ConnectionString;
        }

        public Student Get (int id)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();

            using SqlCommand command = connection.CreateCommand();

            return null;
        }
    }
}
