using System;
using System.Collections.Generic;
using System.Text;
using Utility.Entities;


namespace DatabaseDemo
{
    public class Book 
    {
        public long Id { get; set; }
        public string Title { get; set; }
        public string Author { get; set; }
        public int PageCount { get; set; }
        public bool Borrowed { get; set; }

        public Book()
        { }

        public Book (long id, string title, string author, int pageCount, bool borrowed)

            //: base (id, dateCreated, dateModified)
        {
            Id = id;
            Title = title;
            Author = author;
            PageCount = pageCount;
            Borrowed = borrowed;
        }
    }
}
