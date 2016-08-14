package com.joshkyunghyunlee.morrangu;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joshlee on 2016-06-28.
 */
public class Task extends RealmObject implements Serializable, Parcelable {
    @PrimaryKey
    private String taskName;
    private String dateCreated;

    public Task(String taskName) {
        this.taskName = taskName;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        dateCreated = sdf.format(c.getTime());
    }

    protected Task(Parcel in) {
        taskName = in.readString();
        dateCreated = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public void setTaskName (String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName () {
        return taskName;
    }

    public String getDateCreated () {
        return dateCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskName);
        dest.writeString(dateCreated);
    }
}
