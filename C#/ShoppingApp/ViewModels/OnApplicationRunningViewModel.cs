//Author: Fetra Ranaivoson
using ShoppingApp.Entities;
using ShoppingApp.Model;
using ShoppingApp.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;
using System.Windows;
using Utility;
using Utility.Monads;

namespace ShoppingApp.ViewModels
{
    public class OnApplicationRunningViewModel : ViewModel
    {
        //For retrieving existing items in shop database
        private ObservableCollection<Item> storeItems;
        public ObservableCollection<Item> StoreItems 
        {
            get => storeItems;
            set
            {
                storeItems = value;
                NotifyPropertyChanged(nameof(StoreItems));
            }
        
        }
        public IList<Item> DatabaseStoreItems;

        //Filter Store items
        private string itemNameFilter;
        public string ItemNameFilter 
        {
            get => itemNameFilter;
            set
            {
                itemNameFilter = value.ToLower();
                FilterChanged();
            }
        }

        private readonly ObservableCollection<Item> allItems;


        //For retrieving existing user cart in database 
        //after the user logged out and logged in again
        public ObservableCollection<Item> UserItems { get; set; }
    
        //Services
        private readonly CartService CartService;
        private readonly ShopItemService ShopItemService;
        private readonly CartItemService CartItemService;
        private readonly AuthentificationViewModel ViewModel;

        //For handling errors
        public event Action<string> AddItemError;
        public event Action<string> PayItemError;
        public event Action<string> SuccessPayment;
        public event Action<string> SuccessRemoveItem;
        public event Action<string> SuccessAddItem;
        public event Action<Result> AddToCartAttempted;
        public event Action<Result> RemoveFromCartAttempted;
        public event Action<Result> PayItemAttempted;
        public event Action<string> RechargeError;

        //Log out
        public event Action Logout;
        public DelegateCommand LogoutCommand { get; }

        //Add item to cart
        public DelegateCommand AddItemCommand { get; }

        //Remove item from cart
        public DelegateCommand RemoveItemCommand { get; }
        public DelegateCommand EmptyItemCommand { get; }

        //Recharge Cart
        public DelegateCommand RechargeCommand { get; }


        //Pay item in cart
        public DelegateCommand PayItemCommand { get; }

        //Select USER item
        private Item userSelectedItem;
        public Item UserSelectedItem
        {
            get => userSelectedItem;
            set
            {
                userSelectedItem = value;
                NotifyPropertyChanged(nameof(UserSelectedItem));
            }
        }

        private decimal? rechargeAmount;
        public decimal? RechargeAmount
        {
            get => rechargeAmount;
            set
            {
                rechargeAmount = value;
                NotifyPropertyChanged(nameof(RechargeAmount));
            }
        }

        //Select STORE item
        private Item selectedItem;
        public Item SelectedItem
        {
            get => selectedItem;
            set
            {
                selectedItem = value;
                NotifyPropertyChanged(nameof(SelectedItem));
            }
        }

        public event Action DeselectStoreItem;
        
        private long quantityToPurchase;
        public long QuantityToPurchase
        {
            get => quantityToPurchase;
            set
            {
                quantityToPurchase = value;
                TotalPrice = (decimal)(QuantityToPurchase * UserSelectedItem.Price);
                NotifyPropertyChanged(nameof(quantityToPurchase));
                NotifyPropertyChanged(nameof(TotalPrice));        
            }
        }

        private decimal totalPrice;
        public decimal TotalPrice
        {
            get => totalPrice;
            set
            {
                totalPrice = value;
                NotifyPropertyChanged(nameof(TotalPrice));
            }
        }

        //Constructor
        public OnApplicationRunningViewModel(CartService CartService,
                                              ShopItemService ShopItemService,
                                              CartItemService cartItemService,
                                              AuthentificationViewModel authentificationViewModel)
        {
            //Services used
            this.CartService = CartService;
            this.ShopItemService = ShopItemService;
            this.CartItemService = cartItemService;

            //Used for getting and displaying the name of the current user from the authentification window
            //That is the data context
            this.ViewModel = authentificationViewModel;

            //Getting all existing items from store database
            DatabaseStoreItems = ShopItemService.GetAllItemsInStore();
            StoreItems = new ObservableCollection<Item>();
            StoreItems = RetrieveStoresItems();

            //For filtering StoreItems
            allItems = StoreItems;

            //Initializing user's list of item in cart
            UserItems = new ObservableCollection<Item>();

            //Buttons commands
            AddItemCommand = new DelegateCommand(AddCommand);
            RemoveItemCommand = new DelegateCommand(RemoveCommand);
            PayItemCommand = new DelegateCommand(PayCommand);
            EmptyItemCommand = new DelegateCommand(EmptyCommand);
            RechargeCommand = new DelegateCommand(Recharge);
            LogoutCommand = new DelegateCommand(LogoutMethod);

        }


        //Getting all existing items from store database
        private ObservableCollection <Item> RetrieveStoresItems ()
        {
            for (int itemIndex = 0; itemIndex < DatabaseStoreItems.Count; itemIndex++)
            {
                StoreItems.Add(DatabaseStoreItems[itemIndex]);
            }
            return StoreItems;
        }

      

        //Adding item to cart
        public void AddCommand(object _)
        {
            if (SelectedItem != null && ValidateItemToAdd())
            {
                //Adding selected item to cart
                UserItems.Add(SelectedItem);
                
                //Recording the "add to cart" action to the CartItem database 
                //And retrieve the existing item added to cart, later for LOGIN
                CartItem cartItem = new CartItem(0, ViewModel.CurrentUser.Id, 
                                                 ViewModel.CurrentUser.UserName,
                                                 SelectedItem.ItemId, 
                                                 false, 0, null);
                Result result = CartItemService.AddCartItem(cartItem, ViewModel.CurrentUser.UserName, SelectedItem.ItemId);
                AddToCartAttempted.Invoke(result);
                DeselectStoreItem?.Invoke();
            }
        }


        //Remove item from cart
        public void RemoveCommand(object _)
        {
            if (UserSelectedItem != null)
            {
                //Set transaction status
                UserSelectedItem.IsPaid = false;

                //Delete current user's corresponding cart item from database
                Result result = CartItemService.DeleteCartItem(ViewModel.CurrentUser.UserName, UserSelectedItem.ItemId);

                //Remove the item from the UI
                UserItems.Remove(UserSelectedItem);

                RemoveFromCartAttempted.Invoke(result);
            }
        }

        //Empty cart
        public void EmptyCommand(object _)
        {
            //Set transaction status
            foreach (Item userItem in UserItems)
            {
                userItem.IsPaid = false;
            }

            //Delete all current user's corresponding cart item from database
            CartItemService.DeleteAllCartItem(ViewModel.CurrentUser.UserName);

            //Clear the UI
            UserItems.Clear();
        }

        //Pay an item in cart
        public void PayCommand(object _)
        {
            if (UserSelectedItem != null && UserIsAbleToPay())
            {
                //For the user
                ViewModel.CurrentUser.PayItem(UserSelectedItem);
                UserSelectedItem.IsPaid = true;
                ViewModel.CurrentUser.Balance -= TotalPrice;
                CartService.UpdateBalance(ViewModel.CurrentUser);
               
                //For the administrator
                UserSelectedItem.QuantitySold += QuantityToPurchase;
                UserSelectedItem.QuantityAvailable -= QuantityToPurchase;
                UserSelectedItem.Turnover += TotalPrice;

                //Updating the cart item database
                CartItem cartItem = new CartItem
                (
                    0, 
                    ViewModel.CurrentUser.Id,
                    ViewModel.CurrentUser.UserName,
                    UserSelectedItem.ItemId, 
                    true,
                    QuantityToPurchase,
                    DateTime.UtcNow
                 );
                CartItemService.UpdateCartItem(cartItem);

                //Update items on database
                Result result = ShopItemService.UpdateItem(UserSelectedItem);
                PayItemAttempted.Invoke(result);
                //ReturnSuccessMessagePayment();

                ClearData();
            }
        }

        //Recharge cart
        private void Recharge(object _)
        {
            if (RechargeAmount <= 0)
                RechargeError.Invoke("Invalid action!");
            else
            {
                ViewModel.CurrentUser.Balance += (decimal)RechargeAmount;
                CartService.UpdateBalance(ViewModel.CurrentUser);
                RechargeAmount = null;
            }
        }

        //Logout
        private void LogoutMethod (object _)
        {
            Logout.Invoke();
        }

        private void ClearData ()
        {
            QuantityToPurchase = 0;
            TotalPrice = 0;
        }

        private bool ValidateBalance()
        {
            if (ViewModel.CurrentUser.Balance < TotalPrice)
            {
                PayItemError?.Invoke(ViewModel.CurrentUser.UserName + ", you don't have enough money!");
                return false;
            }
            return true;
        }

        private bool ItemAlreadyPaid()
        {
            if (UserSelectedItem.IsPaid)
            {
                PayItemError?.Invoke("Item already paid!");
                return true;
            }
            return false;
        }

        private bool ValidateQuantityToPurchase ()
        {
            if (QuantityToPurchase <= 0)
            {
                MessageBox.Show("Invalid quantity");
                return false;
            }
            return true;
        }

        private bool UserIsAbleToPay()
        {
            return ValidateBalance() && !ItemAlreadyPaid() && ValidateQuantityToPurchase();
        }

        private bool ValidateItemToAdd()
        {
            if (UserItems.Contains(SelectedItem))
            {
                AddItemError?.Invoke(SelectedItem.ItemName + " already added");
                return false;
            }
            return true;
        }

        private void FilterChanged ()
        {
            if (string.IsNullOrWhiteSpace(ItemNameFilter))
                StoreItems = allItems;
            else
                StoreItems = CreateFilteredCollection();
        }

        private ObservableCollection<Item> CreateFilteredCollection()
        {
            ObservableCollection<Item> filteredCollection = new ObservableCollection<Item>();
            foreach (Item item in allItems)
            {
                if(item.ItemName.ToLower().Contains(ItemNameFilter))
                    filteredCollection.Add(item);
            }
            return filteredCollection;
        }
    }
}
