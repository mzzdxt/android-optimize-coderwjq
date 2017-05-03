// BookManager.aidl
package com.coderwjq.a03_androidaidldemo;

// Declare any non-default types here with import statements
import com.coderwjq.a03_androidaidldemo.Book;

interface BookManager {
    List<Book> getBooks();
    void addBook(in Book book);
}
