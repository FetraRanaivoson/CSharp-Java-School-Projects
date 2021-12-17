//Author: Fetra Ranaivoson
using ShoppingApp.Model;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace ShoppingApp.Repositories
{
    public interface IShopItemRepository
    {
        Item Get(long itemId);
        IList<Item> GetAll();
        void Add(Item item);
        bool Remove(Item item);
        bool Update(Item item);
        bool UpdateByAdmin(Item item);
    }


    public class ShopItemRepository : IShopItemRepository
    {
        private static readonly string SelectCommandCore =
            "SELECT TOP(1000) ItemId, DateCreated, LastPurchase, "
            + "ItemName, Price, Description, QuantityAvailable, QuantitySold, Turnover "
            + "FROM dbo.Items ";
        private static readonly string Where = "WHERE ";
        private static readonly string And = "AND ";
        private static readonly string OrderBy = "ORDER BY ";

        private readonly string connectionString;

        public ShopItemRepository()
        {
            connectionString = ConfigurationManager.ConnectionStrings["ShopDatabase"].ConnectionString;
        }

        public Item Get (long itemId)
        {  

            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = SelectCommandCore + "WHERE ItemId = @ItemId ";
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = itemId;

            SqlDataReader reader = command.ExecuteReader();

            if (reader.Read())
                return ExtractNextItem(reader);
            return null;
        }
        
        private Item ExtractNextItem (SqlDataReader reader)
        {
            long itemId = reader.GetInt64(0);
            DateTime dateCreated = reader.GetDateTime(1);
            DateTime? lastPurchase = reader.IsDBNull(2) ? null : reader.GetDateTime(2);
            string itemName = reader.GetString(3);
            decimal price = reader.GetDecimal(4);
            string description = reader.GetString(5);
            long quantityAvailable = reader.GetInt64(6);
            long quantitySold = reader.GetInt64(7);
            decimal turnover = reader.GetDecimal(8);

            return new Item(itemId, dateCreated,
                itemName, price, description, lastPurchase,
                quantityAvailable, quantitySold, turnover);
        }

        public IList<Item> GetAll()
        {
            return GetAllCore (whereClause: null, orderByClause: null);
        }

        private IList<Item> GetAllCore (string whereClause, string orderByClause, params SqlParameter [] parameters)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = SelectCommandCore + whereClause + orderByClause;
            command.Parameters.AddRange(parameters);

            SqlDataReader reader = command.ExecuteReader();
            List<Item> items = new List<Item>();

            while (reader.Read())
            {
                items.Add(ExtractNextItem(reader));
            }
            return items;
        }

        public void Add(Item item)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "INSERT INTO dbo.Items "
                + "(DateCreated, ItemName, Price, Description, "
                + "QuantityAvailable, QuantitySold, Turnover) "
                + "OUTPUT inserted.ItemId "
                + "VALUES(@DateCreated, @ItemName, @Price, @Description, "
                + "@QuantityAvailable, @QuantitySold, @Turnover) ";

            item.DateCreated = DateTime.UtcNow;
            command.Parameters.Add("@DateCreated", SqlDbType.DateTime2).Value = item.DateCreated;

            command.Parameters.Add("@ItemName", SqlDbType.NVarChar).Value = item.ItemName;
            command.Parameters.Add("@Price", SqlDbType.Decimal).Value = item.Price;
            command.Parameters.Add("@Description", SqlDbType.NVarChar).Value = item.Description;
            command.Parameters.Add("@QuantityAvailable", SqlDbType.BigInt).Value = item.QuantityAvailable;
            command.Parameters.Add("@QuantitySold", SqlDbType.BigInt).Value = item.QuantitySold;
            command.Parameters.Add("@Turnover", SqlDbType.Decimal).Value = item.Turnover;

            item.ItemId = (long)command.ExecuteScalar();
        }

        public bool Remove(Item item)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText = "DELETE from dbo.Items WHERE ItemId = @ItemId ";
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = item.ItemId;

            int rowsAffected = command.ExecuteNonQuery();
            return rowsAffected > 0;
        }

        public bool Update(Item item)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "UPDATE dbo.Items "
                + "SET LastPurchase = @LastPurchase, "
                + "ItemName = @ItemName, Price = @Price, "
                + "Description = @Description, QuantityAvailable = @QuantityAvailable, "
                + "QuantitySold = @QuantitySold, Turnover = @Turnover "
                + "WHERE ItemId = @ItemId ";

            item.LastPurchase = DateTime.UtcNow;

            command.Parameters.Add("@LastPurchase", SqlDbType.DateTime2).Value = item.LastPurchase;
            command.Parameters.Add("@ItemName", SqlDbType.NVarChar).Value = item.ItemName;
            command.Parameters.Add("@Price", SqlDbType.Decimal).Value = item.Price;
            command.Parameters.Add("@Description", SqlDbType.NVarChar).Value = item.Description;
            command.Parameters.Add("@QuantityAvailable", SqlDbType.BigInt).Value = item.QuantityAvailable;
            command.Parameters.Add("@QuantitySold", SqlDbType.BigInt).Value = item.QuantitySold;
            command.Parameters.Add("@Turnover", SqlDbType.Decimal).Value = item.Turnover;
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = item.ItemId;

            int rowsAffected = command.ExecuteNonQuery();

            return rowsAffected > 0;
        }

        public bool UpdateByAdmin(Item item)
        {
            using SqlConnection connection = new SqlConnection(connectionString);
            connection.Open();
            using SqlCommand command = connection.CreateCommand();

            command.CommandText =
                "UPDATE dbo.Items "
                + "SET ItemName = @ItemName, Price = @Price, "
                + "Description = @Description, QuantityAvailable = @QuantityAvailable "
                + "WHERE ItemId = @ItemId ";

            command.Parameters.Add("@ItemName", SqlDbType.NVarChar).Value = item.ItemName;
            command.Parameters.Add("@Price", SqlDbType.Decimal).Value = item.Price;
            command.Parameters.Add("@Description", SqlDbType.NVarChar).Value = item.Description;
            command.Parameters.Add("@QuantityAvailable", SqlDbType.BigInt).Value = item.QuantityAvailable;
            command.Parameters.Add("@ItemId", SqlDbType.BigInt).Value = item.ItemId;

            int rowsAffected = command.ExecuteNonQuery();

            return rowsAffected > 0;
        }
    }
}
