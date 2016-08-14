package com.joshkyunghyunlee.morrangu;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by joshlee on 2016-08-11.
 */
public class ApplicationLoader extends Application {

    //Configure necessary steps to use Realm
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
    }
}
