//Author: Michael Roshin
using ShoppingApp.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace ShoppingApp.Model
{
    public class Item : Entity, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        //public long ItemId { get; }
        public string ItemName { get; set; }

        public DateTime? lastPurchase;
        public DateTime? LastPurchase {
            get => lastPurchase;
            set
            {
                lastPurchase = value;
                NotifyPropertyChanged(nameof(LastPurchase));
            }
        }

        private decimal price;
        public decimal Price 
        {
            get => price;
            set
            {
                price = value;
                NotifyPropertyChanged(nameof(Price));
            }
        }

        private decimal totalPrice;
        public decimal TotalPrice
        {
            get => totalPrice;
            set
            {
                totalPrice = value ;
                NotifyPropertyChanged(nameof(TotalPrice));
            }
        }

        private string description;
        public string Description 
        {
            get => description;
            set
            {
                description = value;
                NotifyPropertyChanged(nameof(Description));
            }
        }

        private long quantityAvailable;
        public long QuantityAvailable
        {
            get => quantityAvailable;
            set
            {
                quantityAvailable = value;
                NotifyPropertyChanged(nameof(QuantityAvailable));
            }
        }

        private long quantitySold;
        public long QuantitySold
        {
            get => quantitySold;
            set
            {
                quantitySold = value;
                NotifyPropertyChanged(nameof(QuantitySold));
                NotifyPropertyChanged(nameof(LastPurchase));
            }
        }

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

        private bool isPaid;
        public bool IsPaid
        {
            get => isPaid;
            set
            {
                isPaid = value;
                NotifyPropertyChanged(nameof(IsPaid));
            }
        }


        public Item (string itemName, decimal price, string description, DateTime? lastPurchase,
                    long quantityAvailable, long quantitySold, decimal turnover)
            : this (default, default, itemName, price, description, lastPurchase,
                    quantityAvailable, quantitySold, turnover)
        { }


        public Item (long itemId, DateTime dateCreated, 
                     string itemName, decimal price, string description, DateTime? lastPurchase,
                     long quantityAvailable, long quantitySold, decimal turnover)
            : base (itemId, dateCreated)
        {
            ItemName = itemName;
            Price = price;
            Description = description;
            LastPurchase = lastPurchase;
            QuantityAvailable = quantityAvailable;
            QuantitySold = quantitySold;
            Turnover = turnover;

        }

     


    }
}
