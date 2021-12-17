using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using System.Text;

namespace DatabaseDemo
{
    public class BookRepository
    {
        private readonly string connectionString;

        public BookRepository ()
        {
            connectionString = ConfigurationManager.ConnectionStrings["LibraryDatabase"].ConnectionString;
        }

        public Book Get (long id)
        {
            //Creating a connection objet, loading the necessary data
            using SqlConnection connection = new SqlConnection (connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();

            //Fill the command with text
            command.CommandText = "select Id, Title, Author, PageCount, Borrowed "  
                                    + "from dbo.Book "
                                    + "where Id = @Id";

            //Setting up the command object
            command.Parameters.Add("@Id", System.Data.SqlDbType.BigInt).Value = id;

            //Sending the SQL query above (commandText) to the database then return the data (execute the Sql statement), find all the matching records
            SqlDataReader reader = command.ExecuteReader();  

            if (reader.Read()) //If there is a record(s) or data //Extract the data from the reader
            {
                return ExtractNextBook(reader);
            }
            

            return null;
        } 

        public List<Book> GetAll ()
        {
            //Creating a connection objet, loading the necessary data
            //Using guarantee that there's a connection
            using SqlConnection connection = new SqlConnection(connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "select Id, Title, Author, PageCount, Borrowed "
                                    + "from dbo.Book ";

            //Sending the SQL query above (commandText) to the database then return the data (execute the Sql statement), find all the matching records
            SqlDataReader reader = command.ExecuteReader();

            //Loop through all the db records to read them all
            List<Book> books = new List<Book>();
            while (reader.Read())
            {
                Book book = ExtractNextBook(reader);
                //Add the book to our list of books
                books.Add(book);
            }

            return books;
        }

        public List<Book> GetAllByTitle (string titleFilter)
        {
            //Creating a connection objet, loading the necessary data
            using SqlConnection connection = new SqlConnection(connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();


            command.CommandText = "select Id, Title, Author, PageCount, Borrowed "
                                    + "from dbo.Book "
                                    + "where Title like '%" + titleFilter + "%'";

            //Sending the SQL query above (commandText) to the database then return the data (execute the Sql statement), find all the matching records
            SqlDataReader reader = command.ExecuteReader();

            List<Book> books = new List<Book>();
            while (reader.Read())
            {
                Book book = ExtractNextBook(reader);
                //Add the book to our list of books
                books.Add(book);
            }

            return books;
        }

        public List<Book> GetAllBorrowed ()
        {
            //Creating a connection objet, loading the necessary data
            using SqlConnection connection = new SqlConnection(connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "select Id, Title, Author, PageCount, Borrowed "
                                   + "from dbo.Book "
                                   + "where Borrowed = 1 ";

            //Sending the SQL query above (commandText) to the database then return the data (execute the Sql statement), find all the matching records
            SqlDataReader reader = command.ExecuteReader();

            List<Book> books = new List<Book>();
            while (reader.Read())
            {
                Book book = ExtractNextBook(reader);
                //Add the book to our list of books
                books.Add(book);
            }

            return books;
        }

        public List<Book> GetAllAvailable()
        {
            //Creating a connection objet, loading the necessary data
            using SqlConnection connection = new SqlConnection(connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "select Id, Title, Author, PageCount, Borrowed "
                                   + "from dbo.Book "
                                   + "where Borrowed = 0 ";

            //Sending the SQL query above (commandText) to the database then return the data (execute the Sql statement), find all the matching records
            SqlDataReader reader = command.ExecuteReader();

            List<Book> books = new List<Book>();
            while (reader.Read())
            {
                Book book = ExtractNextBook(reader);
                //Add the book to our list of books
                books.Add(book);
            }

            return books;
        }


        public void Add (Book book)
        {
            //Creating a connection objet, loading the necessary data
            using SqlConnection connection = new SqlConnection(connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "insert into dbo.Book "
                + "(Title, Author, PageCount, Borrowed) "
                + "output inserted.Id "
                + "values(@Title, @Author, @PageCount, @Borrowed) ";

            //Setting up the command object
            command.Parameters.Add("@Title", System.Data.SqlDbType.NVarChar).Value = book.Title;
            command.Parameters.Add("@Author", System.Data.SqlDbType.NVarChar).Value = book.Author;
            command.Parameters.Add("@PageCount", System.Data.SqlDbType.Int).Value = book.PageCount;
            command.Parameters.Add("@Borrowed", System.Data.SqlDbType.Bit).Value = book.Borrowed;

            //
            book.Id = (long)command.ExecuteScalar();
        }


        public bool Delete (Book book)
        {
            //Creating a connection objet, loading the necessary data
            using SqlConnection connection = new SqlConnection(connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "delete from dbo.Book "
                + "where Id = @Id ";

            command.Parameters.Add("@Id", System.Data.SqlDbType.BigInt).Value = book.Id;
            int rowsAffected = command.ExecuteNonQuery();

            return (rowsAffected > 0);
        }

        public bool Update (Book book)
        {
            //Creating a connection objet, loading the necessary data
            using SqlConnection connection = new SqlConnection(connectionString);

            //Make connection to the database
            connection.Open();

            //Creating a command object in Cs
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "update dbo.Book "
                + "set Author = @Author, PageCount = @PageCount "
                + "where Id = @Id ";

            command.Parameters.Add("@Author", System.Data.SqlDbType.NVarChar).Value = book.Author;
            command.Parameters.Add("@PageCount", System.Data.SqlDbType.Int).Value = book.PageCount;
            command.Parameters.Add("@Id", System.Data.SqlDbType.BigInt).Value = book.Id;


            int rowsAffected = command.ExecuteNonQuery();

            return (rowsAffected > 0);
        }

        private Book ExtractNextBook (SqlDataReader reader) //Create new Book object
        {
            long id = reader.GetInt64(0); //Column 0
            string title = reader.GetString(1); //1
            string author = reader.GetString(2); //2
            int pageCount = reader.GetInt32(3); //3
            bool borrowed = reader.GetBoolean(4); //4

            return new Book(id, title, author, pageCount, borrowed);
        }
    }
}
