//Author: Fetra Ranaivoson
using ShoppingApp.Entities;
using ShoppingApp.Model;
using ShoppingApp.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;
using Utility;
using Utility.Monads;

namespace ShoppingApp.ViewModels
{
    public class OnAdminPageViewModel : ViewModel
    {
        //Services
        private readonly CartService CartService;
        private readonly ShopItemService ShopItemService;
        private readonly CartItemService CartItemService;
        private readonly AuthentificationViewModel ViewModel;

        //Logout
        public event Action Logout;
        public DelegateCommand LogoutCommand { get; }

        //Create/Update items
        public CreateItemViewModel CreateItemViewModel { get; }
        public UpdateItemViewModel UpdateItemViewModel { get; }
        public DelegateCommand UpdateItemCommand { get; }
        public Action<Result> UpdateItemAttempted;
        
        //Add item
        public DelegateCommand AddItemCommand { get; }
        public Action<Result> AddItemAttempted;


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

        //For retrieving cart item database
        private ObservableCollection<CartItem> cartItems;
        public ObservableCollection<CartItem> CartItems
        {
            get => cartItems;
            set
            {
                cartItems = value;
                NotifyPropertyChanged(nameof(cartItems));
            }
        
        }

        //Filter items
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

        //Filter IDs
        private string idFilter;
        public string IdFilter
        {
            get => idFilter;
            set
            {
                idFilter = value;
                IdFilterChanged();
            }
        }

        private readonly ObservableCollection<Item> allItems;


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

        //Select cart item
        private CartItem selectedCartItem;
        public CartItem SelectedCartItem
        {
            get => selectedCartItem;
            set
            {
                selectedCartItem = value;
                NotifyPropertyChanged(nameof(SelectedItem));
            }
        }

        //Turnover
        private decimal turnover;
        public decimal Turnover
        {
            get => turnover;
            set
            {
                turnover = value;
                NotifyPropertyChanged(nameof(Turnover));
            }
        }

        private Decimal CalculateTurnover ()
        {
            foreach (Item item in StoreItems)
            {
                Turnover += item.Turnover;
            }

            return Turnover;
        }

        private string currentAdmin;
        public string CurrentAdmin
        {
            get => currentAdmin;
            set
            {
                currentAdmin = value;
                NotifyPropertyChanged(nameof(CurrentAdmin));
            }
        }

        public OnAdminPageViewModel(CartService CartService,
                                    ShopItemService ShopItemService,
                                    CartItemService cartItemService,
                                    AuthentificationViewModel authentificationViewModel)
        {
            //Services used
            this.CartService = CartService;
            this.ShopItemService = ShopItemService;
            this.CartItemService = cartItemService;

            //Used for getting and displaying the name of the current admin from the authentification window
            //That is the data context
            this.ViewModel = authentificationViewModel;

            DatabaseStoreItems = ShopItemService.GetAllItemsInStore();
            StoreItems = new ObservableCollection<Item>();
            StoreItems = RetrieveStoresItems();
            CalculateTurnover();


            CartItems = new ObservableCollection<CartItem>();

            LogoutCommand = new DelegateCommand(LogoutMethod);
            AddItemCommand = new DelegateCommand(AddItemMethod);
            UpdateItemCommand = new DelegateCommand(UpdateItemMethod);

            CreateItemViewModel = new CreateItemViewModel();
            UpdateItemViewModel = new UpdateItemViewModel();

            //For filtering StoreItems
            allItems = StoreItems;


        }

        //Getting all existing items from store database
        private ObservableCollection<Item> RetrieveStoresItems()
        {
            for (int itemIndex = 0; itemIndex < DatabaseStoreItems.Count; itemIndex++)
            {
                StoreItems.Add(DatabaseStoreItems[itemIndex]);
            }
            return StoreItems;
        }

        private void LogoutMethod (object _)
        {
            Logout.Invoke();
        }

        private void AddItemMethod(object _)
        {
            Item item = new Item(CreateItemViewModel.ItemName,
                                 CreateItemViewModel.Price, 
                                 CreateItemViewModel.Description, 
                                 null,
                                 CreateItemViewModel.QuantityAvailable, 
                                 0, 
                                 0);
            Result AddItemResult = ShopItemService.AddItem(item);
            if (AddItemResult.Successful)
            {
                CreateItemViewModel.ClearData();
                StoreItems.Add(item);
            }
                
            AddItemAttempted.Invoke(AddItemResult);
        }

        private void UpdateItemMethod(object _)
        {
            if (SelectedItem != null)
            {
                if (!string.IsNullOrWhiteSpace(UpdateItemViewModel.ItemName))
                    SelectedItem.ItemName = UpdateItemViewModel.ItemName;
                
                if (!string.IsNullOrWhiteSpace(UpdateItemViewModel.Description))
                    SelectedItem.Description = UpdateItemViewModel.Description;

                if (UpdateItemViewModel.Price != 0)
                    SelectedItem.Price = UpdateItemViewModel.Price;

                if (UpdateItemViewModel.QuantityAvailable != 0)
                    SelectedItem.QuantityAvailable = UpdateItemViewModel.QuantityAvailable;

                Result UpdateItemResult = ShopItemService.UpdateByAdmin(SelectedItem);
                if (UpdateItemResult.Successful)
                    UpdateItemViewModel.ClearData();
                UpdateItemAttempted.Invoke(UpdateItemResult);
            }
        }

        private void FilterChanged()
        {
            if (string.IsNullOrWhiteSpace(ItemNameFilter))
            {
                StoreItems = allItems;
            }
            else
            {
                StoreItems = CreateFilteredCollection();
            }
        }

        private void IdFilterChanged ()
        {
            if(string.IsNullOrWhiteSpace(IdFilter))
            {
                StoreItems = allItems;
            }
            else
            {
                StoreItems = CreateIdFilteredCollection();
            }
        }

        private ObservableCollection<Item> CreateFilteredCollection()
        {
            ObservableCollection<Item> filteredCollection = new ObservableCollection<Item>();
            foreach (Item item in allItems)
            {
                if (item.ItemName.ToLower().Contains(ItemNameFilter))
                {
                    filteredCollection.Add(item);
                }
            }
            return filteredCollection;
        }


        private ObservableCollection<Item> CreateIdFilteredCollection()
        {
            ObservableCollection<Item> filteredCollection = new ObservableCollection<Item>();
            foreach (Item item in allItems)
            {
                if (item.ItemId.ToString().Contains(IdFilter))
                {
                    filteredCollection.Add(item);
                }
            }
            return filteredCollection;
        }
    }
}
