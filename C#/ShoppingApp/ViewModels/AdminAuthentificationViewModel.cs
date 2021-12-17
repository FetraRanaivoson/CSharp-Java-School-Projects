//Author: Michael Roshin
using System;
using System.Collections.Generic;
using System.Text;

namespace ShoppingApp.ViewModels
{
    public class AdminAuthentificationViewModel: ViewModel
    {
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

        private string password;
        public string Password
        {
            get => password;
            set
            {
                password = value;
                NotifyPropertyChanged(nameof(Password));
            }
        }

        public void ClearData()
        {
            UserName = null;
            Password = null;
        }
    }
}
