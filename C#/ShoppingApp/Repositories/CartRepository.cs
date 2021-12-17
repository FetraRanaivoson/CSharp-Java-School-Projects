//Author: Fetra Ranaivoson
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Text;
using Utility.Authentication;

namespace ShoppingApp.Model
{
    public interface ICartRepository
    {
        Cart Get(long id);
        Cart Get(string username);
        bool Exists(long id);
        bool Exists(string username);
        void Add(Cart cartUser);
        bool Delete(Cart cartUser);
        bool Update(Cart cartUser);
        bool UpdateBalance(Cart cartUser);
    }


    public class CartRepository : ICartRepository
    {
        private readonly string connectionString;

        public CartRepository ()
        {
            connectionString = ConfigurationManager.ConnectionStrings["ShopDatabase"].ConnectionString;
        }

        public void Add(Cart cartUser)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "insert into dbo.Carts "
                + "(DateCreated, DateModified, UserName, Salt, Hash, Balance) "
                + "output inserted.Id "
                + "values(@DateCreated, @DateModified, @UserName, @Salt, @Hash, @Balance)";

            cartUser.DateCreated = cartUser.DateModified = DateTime.UtcNow;
            command.Parameters.Add("@DateCreated", SqlDbType.DateTime2).Value = cartUser.DateCreated;
            command.Parameters.Add("@DateModified", SqlDbType.DateTime2).Value = cartUser.DateModified;
            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = cartUser.UserName;
            command.Parameters.Add("@Salt", SqlDbType.VarBinary).Value = cartUser.Salt;
            command.Parameters.Add("@Hash", SqlDbType.VarBinary).Value = cartUser.Hash;
            command.Parameters.Add("@Balance", SqlDbType.Money).Value = cartUser.Balance; //Money?
                                                                     //How about adding the obs collection of item to the DB?
            cartUser.Id = (long)command.ExecuteScalar();
        }

        public bool Delete(Cart cartUser)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "delete from dbo.Carts "
                + "where Id = @Id ";

            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = cartUser.Id;

            int rowsAffected = command.ExecuteNonQuery();
            return (rowsAffected > 0);
        }

        public bool Exists(long id)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select count(Id) from dbo.Carts "
                + "where Id = @Id ";

            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = id;

            int count = (int)command.ExecuteScalar();
            return (count > 0);
        }

        public bool Exists(string username)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select count(UserName) from dbo.Carts "
                + "where UserName = @UserName ";

            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = username;

            int count = (int)command.ExecuteScalar();
            return (count > 0);
        }

        public Cart Get(long id)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select Id, DateCreated, DateModified, UserName, Salt, Hash, Balance "
                + "from dbo.Carts "
                + "where Id = @Id ";

            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = id;

            SqlDataReader reader = command.ExecuteReader();
            if (reader.Read())
                return ExtractNextCart(reader);
            return null;
        }

        public Cart Get(string username)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "select Id, DateCreated, DateModified, UserName, Salt, Hash, Balance "
                + "from dbo.Carts "
                + "where UserName = @UserName ";

            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = username;

            SqlDataReader reader = command.ExecuteReader();
            if (reader.Read())
                return ExtractNextCart(reader);
            return null;
        }

        private Cart ExtractNextCart(SqlDataReader reader)
        {
            long id = reader.GetInt64(0);
            DateTime dateCreated = reader.GetDateTime(1);
            DateTime dateModified = reader.GetDateTime(2);
            string username = reader.GetString(3);

            byte[] salt = (byte[])reader.GetValue(4);
            byte[] hash = (byte[])reader.GetValue(5);

            decimal balance = reader.GetDecimal(6);  
            PasswordHash password = new PasswordHash(salt, hash);

            return new Cart(id, dateCreated, dateModified, username, password, balance);
        }




        public bool Update(Cart cartUser)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "update dbo.Carts "
                + "set DateModified = @DateModified, UserName = @UserName, Salt = @Salt, Hash = @Hash, Balance = @Balance "
                + "where Id = @Id ";

            cartUser.DateModified = DateTime.UtcNow;
            command.Parameters.Add("@DateModified", SqlDbType.DateTime2).Value = cartUser.DateModified;

            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = cartUser.UserName;
            command.Parameters.Add("@Salt", SqlDbType.VarBinary).Value = cartUser.Salt;
            command.Parameters.Add("@Hash", SqlDbType.VarBinary).Value = cartUser.Hash;
            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = cartUser.Id;
            command.Parameters.Add("@Balance", SqlDbType.Float).Value = cartUser.Balance;

            int rowsAffected = command.ExecuteNonQuery();
            return (rowsAffected > 0);
        }

        public bool UpdateBalance (Cart cartUser)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
            "update dbo.Carts "
            + "set DateModified = @DateModified, Balance = @Balance "
            + "where Id = @Id ";

            cartUser.DateModified = DateTime.UtcNow;
            command.Parameters.Add("@DateModified", SqlDbType.DateTime2).Value = cartUser.DateModified;

            command.Parameters.Add("@Balance", SqlDbType.Float).Value = cartUser.Balance;
            command.Parameters.Add("@Id", SqlDbType.BigInt).Value = cartUser.Id;

            int rowsAffected = command.ExecuteNonQuery();
            return (rowsAffected > 0);
        }

        
    }
}
