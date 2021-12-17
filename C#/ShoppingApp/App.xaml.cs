//Author: Fetra Ranaivoson, Michael Roshin
using ShoppingApp.Entities;
using ShoppingApp.Model;
using ShoppingApp.Repositories;
using ShoppingApp.Services;
using ShoppingApp.ViewModels;
using ShoppingApp.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using Utility.Monads;

namespace ShoppingApp
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {
        private AuthentificationWindow authentificationWindow;
        private MainApplicationWindow mainApplicationWindow;
        private AdminLoginWindow adminLoginWindow;
        private AdminPageWindow adminPageWindow;
        private AuthentificationViewModel ViewModel { get; set; }

        public CartItemService CartItemService { get; set; }
        public ShopItemService ShopItemService { get; set; }
        public CartService CartService { get; set; }

        public CartItemRepository CartItemRepository { get; set; }
        public CartRepository CartRepository { get; set; }
        public ShopItemRepository ShopItemRepository { get; set; }

        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);
            //This is the default window shown on startup
            authentificationWindow = CreateUserAuthentificationWindow();
            authentificationWindow.Show();
        }

        public AuthentificationWindow CreateUserAuthentificationWindow()
        {
            CartRepository = new CartRepository();
            CartService = new CartService(CartRepository);

            ShopItemRepository = new ShopItemRepository();
            ShopItemService = new ShopItemService(ShopItemRepository);

            CartItemRepository = new CartItemRepository();
            CartItemService = new CartItemService(CartItemRepository);

            ViewModel = new AuthentificationViewModel(CartService, ShopItemService, CartItemService);
            AuthentificationWindow window = new AuthentificationWindow(ViewModel);

            ViewModel.SignUpAttempted += OnUserSignUpAttempted;
            ViewModel.LogInAttempted += OnUserLogInAttempted;
            ViewModel.AuthentificationSuccess += OnUserAuthentificationSuccess;

            ViewModel.LogInAsAdminRequested += OnJumpingToAdminAuthentification;
            ViewModel.AdminAuthentificationSuccess += OnAdminLoggedInSuccess;

            return window;
        }

        private void OnUserSignUpAttempted(Result result)
        {
            string message = result.Successful ? "Sign up successful." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Exclamation);
        }

        private void OnUserLogInAttempted(Result result)
        {
            string message = result.Successful ? "Login successful." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Exclamation);
        }

        private void OnJumpingToAdminAuthentification()
        {
            adminLoginWindow = CreateAdminLoginWindow();
            authentificationWindow.Hide();
            authentificationWindow = null;
            adminLoginWindow.Show();
        }

        private void OnAdminLoggedInSuccess()
        {
            adminPageWindow = CreateAdminPageWindow();
            adminLoginWindow.Close();
            adminLoginWindow = null;
            adminPageWindow.Show();
        }

        public AdminLoginWindow CreateAdminLoginWindow()
        {
            AdminLoginWindow window = new AdminLoginWindow(ViewModel);
            ViewModel.AdminAuthentificationError += OnAdminAuthentificationError;
            return window;
        }

        private void OnAdminAuthentificationError()
        {
            MessageBox.Show("Error, wrong username and password!");
        }

        private AdminPageWindow CreateAdminPageWindow()
        {
            OnAdminPageViewModel viewModel = new OnAdminPageViewModel(CartService, ShopItemService, CartItemService, ViewModel);
            AdminPageWindow window = new AdminPageWindow(ViewModel);
            ViewModel.OnAdminPageViewModel.Logout += OnAdminLogout;
            ViewModel.OnAdminPageViewModel.AddItemAttempted += OnAdminAddItem;
            ViewModel.OnAdminPageViewModel.UpdateItemAttempted += OnAdminItemUpdate;

            return window;
        }

        private void OnAdminItemUpdate(Result result)
        {
            string message = result.Successful ? "Item updated successfully." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Information);
        }


        private void OnAdminAddItem (Result result)
        {
            string message = result.Successful ? "Item added successfully." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Information);
        }

        private void OnAdminLogout ()
        {
            adminPageWindow.Close();
            authentificationWindow = CreateUserAuthentificationWindow();
            MessageBox.Show("Log out successful!");
            authentificationWindow.Show();
        }

        private void OnUserAuthentificationSuccess ()
        {
            mainApplicationWindow = CreateMainApplicationWindow();
            authentificationWindow.Hide();
            authentificationWindow = null;
            mainApplicationWindow.Show();
        }
        private MainApplicationWindow CreateMainApplicationWindow()
        {
            OnApplicationRunningViewModel viewModel = new OnApplicationRunningViewModel(CartService, ShopItemService, CartItemService, ViewModel);
            MainApplicationWindow window = new MainApplicationWindow(ViewModel);
            
            ViewModel.OnApplicationRunningViewModel.AddItemError += OnAddItemError;
            ViewModel.OnApplicationRunningViewModel.PayItemError += OnPayItemError;
            //ViewModel.OnApplicationRunningViewModel.SuccessPayment += OnSuccessPayment;
            //ViewModel.OnApplicationRunningViewModel.SuccessRemoveItem += OnSuccessRemoveItem;
            //ViewModel.OnApplicationRunningViewModel.SuccessAddItem += OnSuccessAddItem;

            ViewModel.OnApplicationRunningViewModel.DeselectStoreItem += OnDeselectStoreItem;
            ViewModel.OnApplicationRunningViewModel.Logout += OnLogout;

            ViewModel.OnApplicationRunningViewModel.AddToCartAttempted += OnAddToCart;
            ViewModel.OnApplicationRunningViewModel.RemoveFromCartAttempted += OnRemoveFromCart;
            ViewModel.OnApplicationRunningViewModel.PayItemAttempted += OnPayItem;
            ViewModel.OnApplicationRunningViewModel.RechargeError += OnRechargeError;
            return window;
        }
        private void OnRechargeError (string errorMessage)
        {
            MessageBox.Show(errorMessage);
        }

        private void OnPayItem(Result result)
        {
            string message = result.Successful ? "Item paid successfully." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Information);
        }

        private void OnRemoveFromCart(Result result)
        {
            string message = result.Successful ? "Item removed successfully." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Information);

        }

        private void OnAddToCart(Result result)
        {
            string message = result.Successful ? "Item added successfully." : result.ErrorMessage;
            string title = result.Successful ? "Success" : "Error";
            MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Information);

        }

        private void OnLogout()
        {
            mainApplicationWindow.Close();
            authentificationWindow = CreateUserAuthentificationWindow();
            MessageBox.Show("Log out successful!");
            authentificationWindow.Show();
        }


        private void OnDeselectStoreItem ()
        {
            mainApplicationWindow.StoreItemsDataGrid.SelectedItem = null;  
        }

        private void OnAddItemError (string errorMessage)
        {
            MessageBox.Show(errorMessage);
        }


        private void OnPayItemError (string errorMessage)
        {
            MessageBox.Show(errorMessage);
        }

        //private void OnSuccessPayment (string successMessage)
        //{
        //    MessageBox.Show(successMessage);
        //}

        //private void OnSuccessRemoveItem (string successMessage)
        //{
        //    MessageBox.Show(successMessage);
        //}

        //private void OnSuccessAddItem (string successMessage)
        //{
        //    MessageBox.Show(successMessage);
        //}
    }
}
