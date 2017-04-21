package com.coderwjq.a03_androidaidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    private static final String TAG = "AIDLService";
    private ArrayList<Book> mBooks = new ArrayList<>();
    private final BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {

                if (mBooks != null) {
                    Log.d(TAG, "getBooks() called" + mBooks.toString());
                    return mBooks;
                }

                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "addBook() called with: book = [" + book + "]");

            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }

                if (book == null) {
                    Log.d(TAG, "addBook: book is null in In");
                    book = new Book();
                }

                book.setPrice(666);

                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }

                Log.d(TAG, "addBook() called with: mBooks = [" + mBooks.toString() + "]");
            }
        }
    };

    public AIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBookManager;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();

        Book book = new Book();
        book.setName("coderwjq");
        book.setPrice(28);
        mBooks.add(book);
    }
}
