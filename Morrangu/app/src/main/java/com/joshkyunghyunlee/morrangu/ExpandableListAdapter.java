package com.joshkyunghyunlee.morrangu;

/**
 * Created by joshlee on 2016-06-30.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<TaskCategory> taskCategoriesList; // header titles
    // child data in format of header title, child title
    private HashMap<TaskCategory, ArrayList<Task>> taskList;

    public ExpandableListAdapter(Context context, List<TaskCategory> taskCategoriesList,
                                 HashMap<TaskCategory, ArrayList<Task>> taskList) {
        this.context = context;
        this.taskCategoriesList = taskCategoriesList;
        this.taskList = taskList;
    }

    @Override
    public Task getChild(int groupPosition, int childPosition) {
        return this.taskList.get(this.taskCategoriesList.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        String taskTitle = getChild(groupPosition, childPosition).getTaskName();
        String dateCreated = getChild(groupPosition, childPosition).getDateCreated();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.task_item_no_divider, null);
        }

        TextView taskName = (TextView) convertView.findViewById(R.id.task_name);
        TextView dateCreatedText = (TextView) convertView.findViewById(R.id.date_created);

        taskName.setText(taskTitle);
        dateCreatedText.setText(dateCreated);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return taskList.get(taskCategoriesList.get(groupPosition)).size();
    }

    @Override
    public TaskCategory getGroup(int groupPosition) {
        return this.taskCategoriesList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.taskCategoriesList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = getGroup(groupPosition).getCategoryName();
        int imageID = getGroup(groupPosition).getPhotoID();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.task_category_item, null);
        }

        TextView categoryTitle = (TextView) convertView
                .findViewById(R.id.task_category_title);
        categoryTitle.setText(headerTitle);
        ImageView categoryImage = (ImageView) convertView
                .findViewById(R.id.task_category_image_view);
        categoryImage.setImageResource(imageID);


        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
