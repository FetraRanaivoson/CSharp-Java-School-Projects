using System;
using System.Collections.Generic;

namespace DatabaseDemo
{
    class Program
    {
        static void Main()
        {
            BookRepository bookRepository = new BookRepository();

            //Reading specific book from db
            Book firstBook = bookRepository.Get(1000);
            Book secondBook = bookRepository.Get(1001);
            Book thirdBook = bookRepository.Get(1002);

            //Getting all books from db
            List <Book> books = bookRepository.GetAll();

            //Adding a new book record to the db
            Book newBook = new Book(0, "The Hobbit", "JRRT", 146, true);
            bookRepository.Add(newBook);

            //Update a book
            newBook.Author = "DDDD";
            newBook.PageCount = 999;
            newBook.Borrowed = false;
            bool success = bookRepository.Update(newBook);

            //Remove a book record
            //bool success = bookRepository.Delete(newBook);
            //success = bookRepository.Delete(newBook);

            //Get a  book by titleFilter
            List<Book> filteredBooks = bookRepository.GetAllByTitle("est");

            //Get borrowed books
            List<Book> borrowedBooks = bookRepository.GetAllBorrowed();

            //Get available books
            List<Book> availableBooks = bookRepository.GetAllAvailable();

        }
    }
}
