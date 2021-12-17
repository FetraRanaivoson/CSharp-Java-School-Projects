using System;
using System.Collections.Generic;
using System.Text;

namespace WpfTest
{
    public class MyClass
    {
        public string Name { get; set; }
        public int Age { get; set; }

        public MyClass (string name, int age)
        {
            Name = name;
            Age = age;
        }
    }
}
