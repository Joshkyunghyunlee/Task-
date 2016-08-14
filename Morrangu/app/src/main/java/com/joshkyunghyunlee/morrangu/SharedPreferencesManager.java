package com.joshkyunghyunlee.morrangu;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by joshlee on 2016-06-16.
 */
public class SharedPreferencesManager {
    Context context;
    String jsonTasks;
    String jsonCategories;
    String getJsonTasks;
    String getJsonCategories;
    static int savedIncrement = 0;
    Cache cache;
    private static final String PREFERENCES_TAG = "TASKS";

    public SharedPreferencesManager(Context context) {
        this.context = context;
        cache = Cache.getInstance(); //get access to cache from this class to save data
    }


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE);
    }


    public void loadArray() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE);
        Type typeCategory = new TypeToken<ArrayList<TaskCategory>>() {
        }.getType();
        Type typeTasks = new TypeToken<ArrayList<ArrayList<Task>>>() {}.getType();
        getJsonTasks = sp.getString("Tasks", "");
        getJsonCategories = sp.getString("Categories", "");
        Log.d("json", getJsonTasks);
        savedIncrement = sp.getInt("IncrementValue", 0);
        ArrayList<TaskCategory> savedCategories = gson.fromJson(getJsonCategories, typeCategory);
        ArrayList<ArrayList<Task>> savedTasks = gson.fromJson(getJsonTasks, typeTasks);
        if (savedCategories != null) {
            cache.setTaskCategoryDataSet(savedCategories);
        }
        if(savedTasks != null && savedCategories != null) {
            cache.convertToHashMap(savedTasks, savedCategories);
        }
    }

    public void saveArray() {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();

        jsonCategories = gson.toJson(cache.getTaskCategoryDataSet());
        cache.setTaskArrayList(cache.getTaskDataSet());
        jsonTasks = gson.toJson(cache.getTask2DArrayList());

        editor.putString("Categories", jsonCategories);
        editor.putString("Tasks", jsonTasks).apply();
        //save our increment value from Envelope class
        editor.putInt("IncrementValue", TaskCategory.increment);

        editor.commit();
    }
}

