//Author: Michael Roshin
using ShoppingApp.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Text;
using Utility.Authentication;
using Utility.Entities;

namespace ShoppingApp
{
    public class Cart : Entity, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }


        private string userName;
        public string UserName
        {
            get => userName;
            set
            {
                userName = value;
                NotifyPropertyChanged(nameof(UserName));
            }
        }

        public PasswordHash Password { get; set; }
        public byte[] Salt => Password.Salt;
        public byte[] Hash => Password.Hash;

        private decimal balance;
        public decimal Balance
        {
            get => balance;
            set
            {
                balance = value;
                NotifyPropertyChanged(nameof(Balance));
            }
        }

        private ObservableCollection<Item> userItems;
        public ObservableCollection<Item> UserItems 
        {
            get => userItems; 
            set
            {
                userItems = value;
                NotifyPropertyChanged(nameof(UserItems));
            }
        }
        

        //Cart methods
        public void AddItem (Item itemToAdd)
        {
            UserItems.Add(itemToAdd);
        }
        public void RemoveItem (Item itemToRemove)
        {
            UserItems.Remove(itemToRemove);
        }

        public void PayItem (Item itemToPay)
        {
            Balance -= itemToPay.TotalPrice;
        }

        //Constructors

        public Cart (string username, PasswordHash password, decimal balance)
            : this (default, default, default, username, password, balance )
        { }

        public Cart(long id, DateTime dateCreated, DateTime dateModified, string username, PasswordHash password, decimal balance)
            : base (id, dateCreated, dateModified )
        {
            UserName = username;
            Password = password;
            Balance = balance;
            UserItems = new ObservableCollection<Item>();
        }

    }
}
