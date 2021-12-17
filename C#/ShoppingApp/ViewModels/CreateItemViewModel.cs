//Author: Michael Roshin
using System;
using System.Collections.Generic;
using System.Text;

namespace ShoppingApp.ViewModels
{
    public class CreateItemViewModel : ViewModel
    {
        private string itemName;
        public string ItemName
        {
            get => itemName;
            set
            {
                itemName = value;
                NotifyPropertyChanged(nameof(ItemName));
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

        public void ClearData()
        {
            ItemName = null;
            Description = null;
            Price = 0;
            QuantityAvailable = 0;
        }
    }
}
