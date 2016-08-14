package com.joshkyunghyunlee.morrangu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmResults;

public class CompletedTaskActivity extends AppCompatActivity {

    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task);

        mRealm = Realm.getDefaultInstance();
        RealmResults<Task> completedTasks = mRealm.where(Task.class).findAllAsync();
    }
}
