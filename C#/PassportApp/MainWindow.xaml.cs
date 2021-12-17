using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace PassportApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly ObservableCollection<Passport> passports;

        public MainWindow()
        {
            InitializeComponent();

            passports = new ObservableCollection<Passport>();
            PassportDataGrid.ItemsSource = passports;
            
        }

        private void AddButtonClicked (object sender, RoutedEventArgs e)
        {

            DateTime? dateOfBirth = DateOfBirthDatePicker.SelectedDate;

           //if (FirstNameTextBox.Text=="" || LastNameTextBox.Text =="" || dateOfBirth == null || HeightTextBox.Text == "" || CountryTextBox.Text == "")
                //MessageBox.Show("All fields are required", "Information", MessageBoxButton.OK, MessageBoxImage.Error);

            if (dateOfBirth >= DateTime.UtcNow)
                MessageBox.Show("Invalid Date of Birth", "Information", MessageBoxButton.OK, MessageBoxImage.Error);

           
            if (double.Parse(HeightTextBox.Text) <= 0)
                MessageBox.Show("Invalid height", "Information", MessageBoxButton.OK, MessageBoxImage.Error);


            else 
            {
                DateTime dob = dateOfBirth.Value;

                Passport passport = new Passport(
                FirstNameTextBox.Text,
                LastNameTextBox.Text,
                dateOfBirth.Value,
                double.Parse(HeightTextBox.Text),
                CountryTextBox.Text);

                passports.Add(passport);

                FirstNameTextBox.Text = "";
                LastNameTextBox.Text = "";
                //DateOfBirthDatePicker.SetValue();
                HeightTextBox.Text = "";
                CountryTextBox.Text = "";
            }
           
        
        }

        private void RemoveButton_Click(object sender, RoutedEventArgs e)
        {
           
            IList selectedItems = new ArrayList(PassportDataGrid.SelectedItems);

            if (passports.Count == 0)
                MessageBox.Show("Nothing to remove", "Warning", MessageBoxButton.OK, MessageBoxImage.Information);


            else if (selectedItems.Count == 0)
            {
                MessageBox.Show("Must selected an item", "Warning", MessageBoxButton.OK, MessageBoxImage.Error);
            }

            else
            {
                foreach (object element in selectedItems)
                {
                    Passport passport = (Passport)element;
                    passports.Remove(passport);
                }
            }
           
        }

        private void ClearButton_Click(object sender, RoutedEventArgs e)
        {
            if (passports.Count == 0)
            {
                MessageBox.Show("Nothing to clear", "Warning", MessageBoxButton.OK, MessageBoxImage.Information);
            }

            else
            {
                PassportDataGrid.SelectAll();
               
                IList selectedItems = new ArrayList(PassportDataGrid.SelectedItems);

                foreach (object element in selectedItems)
                {
                    Passport passport = (Passport)element;
                    passports.Remove(passport);
                }
            }
         
        }
    }
}
