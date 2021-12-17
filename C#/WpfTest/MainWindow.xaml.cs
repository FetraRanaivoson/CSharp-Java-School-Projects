using System;
using System.Collections.Generic;
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

namespace WpfTest
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public List <MyClass> myClasses { get; set; }
       
        public MainWindow()
        {
            myClasses = new List<MyClass>();
            myClasses.Add(new MyClass("Fetra", 28));
            myClasses.Add(new MyClass("Jem", 22));
            myClasses.Add(new MyClass("Kiki", 25));
            InitializeComponent();
           
        }

     
    }
}
