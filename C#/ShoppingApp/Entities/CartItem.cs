//Author: Michael Roshin
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace ShoppingApp.Entities
{
    //Class for recording all cart-items in every user's cart

    public class CartItem : INotifyPropertyChanged
    {

        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion


        private long purchaseId;
        public long PurchaseId
        {
            get => purchaseId;
            set
            {
                purchaseId = value;
                NotifyPropertyChanged(nameof(PurchaseId));
            }
        }

        private long userId;
        public long UserId
        {
            get => userId;
            set
            {
                userId = value;
                NotifyPropertyChanged(nameof(UserId));
            }
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

        private long itemId;
        public long ItemId
        {
            get => itemId;
            set
            {
                itemId = value;
                NotifyPropertyChanged(nameof(ItemId));
            }
        }

        private bool purchased;
        public bool Purchased
        {
            get => purchased;
            set

            {
                purchased = value;
                NotifyPropertyChanged(nameof(Purchased));
            }
        }

        private long quantityPurchased;
        public long QuantityPurchased
        {
            get => quantityPurchased;
            set
            {
                quantityPurchased = value;
                NotifyPropertyChanged(nameof(QuantityPurchased));
            }
        }

        private DateTime? datePurchased;

        public DateTime? DatePurchased
        {
            get => datePurchased;
            set
            {
                datePurchased = value;
                NotifyPropertyChanged(nameof(DatePurchased));
            }
        }

     
        public CartItem (long purchaseId ,long userId, string userName, 
                        long itemId, bool purchased, long quantityPurchased,
                        DateTime? datePurchased)
        {
            PurchaseId = purchaseId; 
            UserId = userId;
            UserName = userName;
            ItemId = itemId;
            Purchased = purchased;
            QuantityPurchased = quantityPurchased;
            DatePurchased = datePurchased;
        }
    }
}
