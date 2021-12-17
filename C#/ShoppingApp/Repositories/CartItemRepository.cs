//Author: Fetra Ranaivoson
using ShoppingApp.Entities;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace ShoppingApp.Repositories
{
    public interface ICartItemRepository
    {
        IList<CartItem> FilterByUserId(long userId);
        IList<CartItem> GetAll();
        void Add(CartItem cartItem);
        bool Remove(CartItem cartItem);
        bool Update(CartItem cartItem);
        bool Exists(long itemId);
        bool Exists(string userName, long itemId);
        bool Delete(string userName, long selectedItemId);
        bool DeleteAll(string userName);
    }

    public class CartItemRepository : ICartItemRepository
    {
        private readonly string connectionString;

        public CartItemRepository ()
        {
            connectionString = ConfigurationManager.ConnectionStrings["ShopDatabase"].ConnectionString;
        }

        public void Add(CartItem cartItem)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "INSERT INTO dbo.CartItem "
                + "(UserId, UserName, ItemId, Purchased, QuantityPurchased, DatePurchased) "
                + "OUTPUT inserted.PurchaseId "
                + "VALUES(@UserId, @UserName, @ItemId, @Purchased, @QuantityPurchased, @DatePurchased) ";

            

            
            command.Parameters.Add("@DatePurchased", SqlDbType.DateTime2).Value = DBNull.Value;
            command.Parameters.Add("@UserId", SqlDbType.BigInt).Value = cartItem.UserId;
            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = cartItem.UserName;
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = cartItem.ItemId;
            command.Parameters.Add("@Purchased", SqlDbType.Bit).Value = cartItem.Purchased;
            command.Parameters.Add("@QuantityPurchased", SqlDbType.BigInt).Value = cartItem.QuantityPurchased;
            

            cartItem.PurchaseId = (long)command.ExecuteScalar();
        }

        public IList<CartItem> FilterByUserId(long userId)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "SELECT* FROM dbo.CartItem "
                + "WHERE UserId = @UserId ";

            command.Parameters.Add("@UserId", SqlDbType.NVarChar).Value = userId;

            SqlDataReader reader = command.ExecuteReader();
            List<CartItem> cartItem = new List<CartItem>();

            while (reader.Read())
            {
                cartItem.Add(ExtractNextCartItem(reader));
            }

            return cartItem;

        }

        private  CartItem ExtractNextCartItem(SqlDataReader reader)
        {
            long purchasedId = reader.GetInt64(0);
            long userId = reader.GetInt64(1);
            string userName = reader.GetString(2);
            long itemId = reader.GetInt64(3);
            bool purchased = reader.GetBoolean(4);
            long quantityPurchased = reader.GetInt64(5);
            DateTime? datePurchased = reader.IsDBNull(6) ? null : reader.GetDateTime(6);

            return new CartItem(purchasedId, userId, userName, itemId, purchased, quantityPurchased, datePurchased);
        }

        public bool Remove(CartItem cartItem)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "DELETE from dbo.CartItem WHERE PurchaseId = @PurchaseId ";

            int rowsAffected = command.ExecuteNonQuery();

            return rowsAffected > 0;
        }

        public bool Update(CartItem cartItem)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "UPDATE dbo.CartItem "
                + "SET Purchased = @Purchased, "
                + "QuantityPurchased = @QuantityPurchased, "
                + "DatePurchased = @DatePurchased "
                + "WHERE UserName = @UserName AND ItemId = @ItemId ";

            cartItem.DatePurchased = DateTime.UtcNow;
            command.Parameters.Add("@DatePurchased", SqlDbType.DateTime2).Value = cartItem.DatePurchased;
            command.Parameters.Add("@PurchaseId", SqlDbType.BigInt).Value = cartItem.PurchaseId;
            command.Parameters.Add("@UserId", SqlDbType.BigInt).Value = cartItem.UserId;
            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = cartItem.UserName;
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = cartItem.ItemId;
            command.Parameters.Add("@Purchased", SqlDbType.Bit).Value = cartItem.Purchased;
            command.Parameters.Add("@QuantityPurchased", SqlDbType.BigInt).Value = cartItem.QuantityPurchased;
            
            int rowsAffected = command.ExecuteNonQuery();
            return rowsAffected > 0;

        }

        public IList<CartItem> GetAll()
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "SELECT * FROM dbo.CartItem ";

            SqlDataReader reader = command.ExecuteReader();
            List<CartItem> cartItems = new List<CartItem>();

            while (reader.Read())
            {
                cartItems.Add(ExtractNextCartItem(reader));
            }
            return cartItems;
        }

        public bool Exists(long itemId)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "SELECT COUNT (ItemId) from dbo.CartItem "
                + "WHERE ItemId = @ItemId ";

            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = itemId;

            int count = (int)command.ExecuteScalar();
            return (count > 0);
        }


        public bool Exists(string userName, long itemId )
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "SELECT COUNT (UserName) from dbo.CartItem "
                + "WHERE UserName = @UserName "
                + "AND ItemId = @ItemId " ;

            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = userName;
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = itemId;

            int count = (int)command.ExecuteScalar();
            return (count > 0);
        }

        public bool Delete(string userName, long selectedItemId)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "DELETE from dbo.CartItem "
                + "WHERE UserName = @UserName "
                + "AND ItemId = @ItemId ";

            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = userName;
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = selectedItemId;

            int rowsAffected = command.ExecuteNonQuery();
            return (rowsAffected > 0);
        }

        public bool DeleteAll(string userName)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "DELETE from dbo.CartItem "
                + "WHERE UserName = @UserName ";

            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = userName;

            int rowsAffected = command.ExecuteNonQuery();
            return (rowsAffected > 0);
        }
    }
}
