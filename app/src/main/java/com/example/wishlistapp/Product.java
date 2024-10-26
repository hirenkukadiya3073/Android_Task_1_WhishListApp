package com.example.wishlistapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private String description;
    private double price;
    private int imageResource;
    private boolean isFavorite;

    // Constructor
    public Product(String name, String description, double price, int imageResource, boolean isFavorite) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResource = imageResource;
        this.isFavorite = isFavorite;
    }

    // Parcelable implementation
    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        imageResource = in.readInt();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(imageResource);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
