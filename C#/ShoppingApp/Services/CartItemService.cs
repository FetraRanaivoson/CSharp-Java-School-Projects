//Author: Fetra Ranaivoson
using ShoppingApp.Entities;
using ShoppingApp.Repositories;
using System;
using System.Collections.Generic;
using System.Text;
using Utility.Monads;

namespace ShoppingApp.Services
{
    public interface ICartItemService
    {
        IList<CartItem> FilterUserId(long userId);
        IList<CartItem> GetAllCartItem();
        Result AddCartItem(CartItem cartItem, string userName, long selectedItemId);
        Result UpdateCartItem(CartItem cartItem);
        Result DeleteCartItem(string userName, long selectedItemId);
        Result DeleteAllCartItem(string userName);
    }

    public class CartItemService : ICartItemService
    {
        private readonly ICartItemRepository repository;

        public CartItemService (ICartItemRepository repository)
        {
            this.repository = repository;
        }

        public Result AddCartItem(CartItem cartItem, string userName, long selectedItemId)
        {
            
            if (!repository.Exists(userName, selectedItemId)) 
            {
                repository.Add(cartItem);
                return Result.Success();
            }
            return Result.Error("Item already added by the same user!");
        }

        public Result DeleteCartItem(string userName, long selectedItemId)
        {
            repository.Delete(userName, selectedItemId);
            return Result.Success();
        }

        public Result DeleteAllCartItem(string userName)
        {
            repository.DeleteAll(userName);
            return Result.Success();
        }

        public IList<CartItem> FilterUserId(long userId)
        {
            return repository.FilterByUserId(userId);
        }

        public IList<CartItem> GetAllCartItem()
        {
            return repository.GetAll();
        }

        public Result RemoveCartItem (CartItem cartItem)
        {
            if (cartItem != null)
            {
                repository.Remove(cartItem);
                return Result.Success();
            }
            return Result.Error("No cart item to remove");
        }

        public Result UpdateCartItem(CartItem cartItem)
        {
            if (cartItem != null)
            {
                repository.Update(cartItem);
                return Result.Success();
            }
            return Result.Error("No cart item to update");
        }
    }
}
