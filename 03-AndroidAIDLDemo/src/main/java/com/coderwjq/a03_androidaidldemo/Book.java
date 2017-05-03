package com.coderwjq.a03_androidaidldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by coderwjq on 2017/4/21.
 */

public class Book implements Parcelable {
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    private int price;
    private String name;

    public Book() {
    }

    protected Book(Parcel in) {
        price = in.readInt();
        name = in.readString();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(price);
        dest.writeString(name);
    }

    public void readFromParcel(Parcel dest) {
        // 此处读值顺序应当和writeToParcel方法一致
        price = dest.readInt();
        name = dest.readString();
    }

    @Override
    public String toString() {
        return "Book{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
