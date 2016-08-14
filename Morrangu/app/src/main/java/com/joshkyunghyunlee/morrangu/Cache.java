package com.joshkyunghyunlee.morrangu;

/**
 * Created by joshlee on 2016-06-16.
 */

import android.content.pm.PackageInstaller;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Cache {

    private ArrayList<TaskCategory> taskCategoryDataSet;
    private HashMap<TaskCategory, ArrayList<Task>> taskDataSet;
    private ArrayList<ArrayList<Task>> taskArrayList;

    int savedIncrement;

    private static Cache instance;

    //data to save
    protected Cache() {
        taskCategoryDataSet = new ArrayList<TaskCategory>();
        savedIncrement = 0;
        taskDataSet = new HashMap<TaskCategory, ArrayList<Task>>();
        taskArrayList = new ArrayList<>();
    }

    public static Cache getInstance() {
        if (instance == null) {
            synchronized (PackageInstaller.Session.class) {
                if (instance == null) {
                    instance = new Cache();
                }
            }
        }
        return instance;
    }

    public ArrayList<TaskCategory> getTaskCategoryDataSet() {
        return taskCategoryDataSet;
    }

    public void setTaskCategoryDataSet(ArrayList<TaskCategory> taskCategoryDataSet) {
        this.taskCategoryDataSet = taskCategoryDataSet;
    }

    public HashMap<TaskCategory, ArrayList<Task>> getTaskDataSet() {
        return taskDataSet;
    }

    public void setTaskDataSet(HashMap<TaskCategory, ArrayList<Task>> taskDataSet) {
        this.taskDataSet = taskDataSet;
    }

    public ArrayList<Task> getTaskArrayList (TaskCategory taskCategory) {
        HashMap<TaskCategory, ArrayList<Task>> hashMap = getTaskDataSet();
        for (Map.Entry<TaskCategory, ArrayList<Task>> entry : hashMap.entrySet()) {
            if(taskCategory.getCategoryName().equals(entry.getKey().getCategoryName())) {
                return entry.getValue();
            }
        }
        return null;
    }


    //below methods are used to store the ArrayList of Tasks in SharedPreferences
    public void setTaskArrayList(HashMap<TaskCategory, ArrayList<Task>> taskDataSet) {
        int i = 0;
        for (Map.Entry<TaskCategory, ArrayList<Task>> entry : taskDataSet.entrySet()) {
            this.taskArrayList.add(i, entry.getValue());
            i++;
        }
    }

    public ArrayList<ArrayList<Task>> getTask2DArrayList() {
        return taskArrayList;
    }

    public void convertToHashMap(ArrayList<ArrayList<Task>> taskArrayList, ArrayList<TaskCategory> taskCategoryDataSet) {
        for (int i = 0; i < taskCategoryDataSet.size(); i++) {
            this.taskDataSet.put(taskCategoryDataSet.get(i), taskArrayList.get(i));
        }
    }
    //Delete category from both ArrayList and HashMap
    public void deleteCategory (TaskCategory taskCategory) {
        for(Iterator<Map.Entry<TaskCategory, ArrayList<Task>>> hashmapIterator = getTaskDataSet().entrySet().iterator(); hashmapIterator.hasNext(); ) {
            Map.Entry<TaskCategory, ArrayList<Task>> entry = hashmapIterator.next();
            if(entry.getKey().getCategoryName().equals(taskCategory.getCategoryName())) {
                hashmapIterator.remove();
            }
        }

        Iterator<TaskCategory> iterator = getTaskCategoryDataSet().iterator();
        while(iterator.hasNext()) {
            TaskCategory mCategory = iterator.next();
            if(mCategory.getCategoryName().equals(taskCategory.getCategoryName())) {
                iterator.remove();
            }
        }
    }
}
