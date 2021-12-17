//Author: Fetra Ranaivoson
using ShoppingApp.Model;
using ShoppingApp.Repositories;
using System;
using System.Collections.Generic;
using System.Text;
using Utility.Monads;

namespace ShoppingApp.Services
{
    public interface IItemService
    {
        Item GetItem(long itemId);
        IList<Item> GetAllItemsInStore();
        Result UpdateItem(Item item);
        Result AddItem(Item item);
        Result UpdateByAdmin(Item item);
    }


    public class ShopItemService : IItemService
    {
        private readonly IShopItemRepository repository;

        public ShopItemService (IShopItemRepository repository)
        {
            this.repository = repository;
        }

        public Result AddItem(Item item)
        {
            if (item.Price > 0 )
            {
                repository.Add(item);
                return Result.Success();
            }
            return Result.Error("Invalid price");
        }

        public IList<Item> GetAllItemsInStore()
        {
            return repository.GetAll();
        }

        public Item GetItem(long itemId)
        {
            return repository.Get(itemId);
        }

        public Result UpdateByAdmin(Item item)
        {
            repository.UpdateByAdmin(item);
            return Result.Success();
        }

        public Result UpdateItem(Item item)
        {
            repository.Update(item);
            return Result.Success();
        }


    }
}
