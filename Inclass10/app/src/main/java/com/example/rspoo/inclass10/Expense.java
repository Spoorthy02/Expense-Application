package com.example.rspoo.inclass10;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by rspoo on 11/8/2016.
 */


enum Category
{
    SelectCategory ("Select Category"),
    Groceries ("Groceries"),
    Invoice ("Invoice"),
    Transportation("Transportation"),
    Shopping("Shopping"),
    Rent("Rent"),
    Trips("Trips"),
    Utilities("Utilities"),
    Others("Others");
    private final String name;

    private Category(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}

public class Expense implements Parcelable {
    int expenseID;
    String expenseName;
    Category category;
    double amount;
    Date date;
    Uri imgUri;
    String key;
    String k[];

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(expenseID);
        dest.writeString(expenseName);
        dest.writeDouble(amount);
        dest.writeValue(category);
        dest.writeValue(date);
        dest.writeValue(imgUri);
        dest.writeString(key);

    }

    public static final Creator<Expense> CREATOR
            = new Creator<Expense>() {
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    private Expense(Parcel in )
    {
        this.expenseID = in.readInt();
        this.expenseName = in.readString();
        this.amount = in.readDouble();
        this.category =(Category) in.readValue(Category.class.getClassLoader());
        this.date = (Date)in.readValue(Date.class.getClassLoader());
        this.imgUri = (Uri) in.readValue(Uri.class.getClassLoader());
        this.key = in.readString();
    }

    @Override
    public String toString() {
        return expenseName;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public Expense(int expenseID, String expenseName, Category category, double amount, Date date, Uri imgUri) {
        this.expenseID = expenseID;
        this.expenseName = expenseName;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.imgUri = imgUri;
    }

    public Expense()
    {}





}
