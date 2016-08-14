package com.joshkyunghyunlee.morrangu;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joshlee on 2016-06-11.
 */
public class TaskCategory implements Parcelable {
    private int photoID;
    private String categoryName;
    private String categoryDescription;
    protected static int increment = 0;

    public TaskCategory() {
        increment ++;
    }

    public TaskCategory(String categoryName, String categoryDescription, int photoID) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.photoID = photoID;
        increment++;
    }

    public TaskCategory(String categoryName, int photoID) {
        this.categoryName = categoryName;
        this.photoID = photoID;
        increment++;
    }

    protected TaskCategory(Parcel in) {
        photoID = in.readInt();
        categoryName = in.readString();
        categoryDescription = in.readString();
    }

    public static final Creator<TaskCategory> CREATOR = new Creator<TaskCategory>() {
        @Override
        public TaskCategory createFromParcel(Parcel in) {
            return new TaskCategory(in);
        }

        @Override
        public TaskCategory[] newArray(int size) {
            return new TaskCategory[size];
        }
    };

    /**
     * Getters and Setters are good because you can add extra implementation to getting and setting values
     * They are also good b/c you can pass in an object reference b/w activities and do anything with it
     */
    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryDescription (String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(photoID);
        dest.writeString(categoryName);
        dest.writeString(categoryDescription);
    }
}
