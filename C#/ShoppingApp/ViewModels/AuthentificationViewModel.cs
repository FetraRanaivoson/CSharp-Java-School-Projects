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
using Utility.Authentication;
using Utility.Monads;

namespace ShoppingApp.ViewModels
{
    public class AuthentificationViewModel : ViewModel
    {

        //For handling errors
        public event Action<string> CreateAccountError;

        //Authentifications and view models
        public CreateAccountViewModel CreateAccountViewModel { get; }
        public LogInToAccountViewModel LogInToAccountViewModel { get; }
        public OnApplicationRunningViewModel OnApplicationRunningViewModel { get; }
        public AdminAuthentificationViewModel AdminAuthentificationViewModel { get; }
        public OnAdminPageViewModel OnAdminPageViewModel { get; }

        public DelegateCommand CreateAccountCommand { get; }
        public DelegateCommand LogInCommand { get; }
        public event Action<Result> SignUpAttempted;
        public event Action<Result> LogInAttempted;
        public event Action AuthentificationSuccess;


        //Admin Authentification
        public DelegateCommand LogInAsAdminCommand { get; }
        public event Action LogInAsAdminRequested;
        public DelegateCommand LogInToAdminPageCommand { get; }
        public event Action AdminAuthentificationSuccess;
        public event Action AdminAuthentificationError;


        private CartService CartService { get; }
        private ShopItemService ShopItemService { get; }
        private CartItemService CartItemService { get; }

        
        //Getting all store items
        public ObservableCollection<Item> RetrievedUserItems;
        public IList<CartItem> DatabaseCartItems;

        public ObservableCollection<CartItem> CartItems;
        

        public IList<Item> DatabaseStoreItems;
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

        //Curent user logged in
        private Cart currentUser;
        public Cart CurrentUser
        {
            get => currentUser;
            set
            {
                
                currentUser = value;
                NotifyPropertyChanged(nameof(CurrentUser));
                
            }
        }

        //Select RETRIEVED item
        private Item selectedRetrievedItem;
        public Item SelectedRetrievedItem
        {
            get => selectedRetrievedItem;
            set
            {
                selectedRetrievedItem = value;
                NotifyPropertyChanged(nameof(SelectedRetrievedItem));
            }
        }

        public AuthentificationViewModel (CartService CartService, ShopItemService ShopItemService, CartItemService cartitemService)
        {        
            this.CartService = CartService;
            this.ShopItemService = ShopItemService;
            this.CartItemService = cartitemService;

            OnApplicationRunningViewModel = new OnApplicationRunningViewModel(this.CartService, this.ShopItemService, this.CartItemService, this);

            CreateAccountCommand = new DelegateCommand(CreateAccount);

            CreateAccountViewModel = new CreateAccountViewModel();
            LogInToAccountViewModel = new LogInToAccountViewModel();

            AdminAuthentificationViewModel = new AdminAuthentificationViewModel();

            LogInCommand = new DelegateCommand(LogIn);

            LogInAsAdminCommand = new DelegateCommand(LogInAsAdmin);
            LogInToAdminPageCommand = new DelegateCommand(LogInToAdminPage);

            OnAdminPageViewModel = new OnAdminPageViewModel(this.CartService, this.ShopItemService, this.CartItemService, this);
        }

        //All commands

        public void LogInAsAdmin (object _)
        {
            LogInAsAdminRequested.Invoke();
        }

        public void LogInToAdminPage (object _)
        {
            if (AdminAuthentificationViewModel.UserName == "Admin" 
                && AdminAuthentificationViewModel.Password == "admin")
            {
                //Getting all existing cart item from database
                DatabaseCartItems = CartItemService.GetAllCartItem();
                CartItems = new ObservableCollection<CartItem>();
                DatabaseCartItems = RetrieveAllCartItems();

                //Displaying the admin username
                OnAdminPageViewModel.CurrentAdmin = "Admin";

                //Displaying all cart items to the admin page view model
                OnAdminPageViewModel.CartItems = CartItems;

                //Success message
                AdminAuthentificationSuccess?.Invoke();
            }
            else
            {
                AdminAuthentificationError?.Invoke();
            }
        }

        //Retrieve all cartItems
        private ObservableCollection<CartItem> RetrieveAllCartItems()
        {
            for (int itemIndex = 0; itemIndex < DatabaseCartItems.Count; itemIndex++)
            {
                CartItems.Add(DatabaseCartItems[itemIndex]);
            }
            return CartItems;
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

        public void CreateAccount (object _)
        {
            //New user (name, password and new item collection)
            if (UserIsAbleToCreateAccount())
            {
                Result result = CartService.SignUp(CreateAccountViewModel.UserName, CreateAccountViewModel.Password); 
                if (result.Successful)
                {
                    //Set up the current user
                    CurrentUser = CartService.SignedUpUser;

                    //Getting all existing currrent user's stored items in cart
                    DatabaseCartItems = CartItemService.GetAllCartItem();
                    RetrievedUserItems = new ObservableCollection<Item>();
                    RetrievedUserItems = RetrieveUserItems();
                    //Displaying all current user's item in cart to the main application window (Cart tab)
                    OnApplicationRunningViewModel.UserItems = RetrievedUserItems;

                    CreateAccountViewModel.ClearData();
                    AuthentificationSuccess?.Invoke();
                }
                SignUpAttempted?.Invoke(result);
            }
        }

        public void LogIn(object _)
        {
            Result logInResult = CartService.Login(LogInToAccountViewModel.UserName, LogInToAccountViewModel.Password);
            if (logInResult.Successful)
            {
                //Set up the current user
                CurrentUser = CartService.LoggedInUser;

                //Getting all existing currrent user's stored items in cart
                DatabaseCartItems = CartItemService.GetAllCartItem();
                RetrievedUserItems = new ObservableCollection<Item>();
                RetrievedUserItems = RetrieveUserItems();
                //Displaying all current user's item in cart to the main application window (Cart tab)
                OnApplicationRunningViewModel.UserItems = RetrievedUserItems;

                LogInToAccountViewModel.ClearData();      
                AuthentificationSuccess?.Invoke();

            }
            LogInAttempted?.Invoke(logInResult);
        }

        private ObservableCollection<Item> RetrieveUserItems()
        {
            IList<CartItem> currentUserCartItems = new List<CartItem>();
            

            foreach (CartItem cartItem in DatabaseCartItems)
            {
                if (cartItem.UserId == CurrentUser.Id)
                {
                    currentUserCartItems.Add(cartItem);
                }
            }
                
            for (int itemIndex = 0; itemIndex < currentUserCartItems.Count; itemIndex++)
            {
                //Get matching item id from shop database and the current user cart item database 
                //and display the corresponding items to the user cart tab via Observable collection
                Item item = ShopItemService.GetItem(currentUserCartItems[itemIndex].ItemId);

                //Retrieving transaction status
                item.IsPaid = currentUserCartItems[itemIndex].Purchased;

                //Collecting all current user items in cart
                RetrievedUserItems.Add(item);
            }
            return RetrievedUserItems;
        }


        private bool ValidateFirstName ()
        {
            if (string.IsNullOrWhiteSpace(CreateAccountViewModel.UserName))
            {
                CreateAccountError?.Invoke("Username is required!");
                return false;
            }
            return true;
        }

        private bool ValidatePassword ()
        {
            if (string.IsNullOrWhiteSpace(CreateAccountViewModel.Password))
            {
                CreateAccountError?.Invoke("Password must be set!");
                return false;
            }
            return true;
        }

        private bool UserIsAbleToCreateAccount ()
        {
            return ValidateFirstName() && ValidatePassword();

        }

    }

}
