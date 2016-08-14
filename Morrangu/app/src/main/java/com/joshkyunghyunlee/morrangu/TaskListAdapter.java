package com.joshkyunghyunlee.morrangu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joshlee on 2016-07-07.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {

    private ArrayList<Task> mTaskList;
    private int layoutResourceID;
    OnCardClickListener onCardClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView dateCreated;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.task_name);
            cardView = (CardView) view.findViewById(R.id.card_view);
            if(layoutResourceID != R.layout.task_creation_list_item) {
                dateCreated = (TextView) view.findViewById(R.id.date_created_text);
            }
        }
    }

    public interface OnCardClickListener {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public TaskListAdapter(ArrayList<Task> tasks, int layoutResourceID) {
        mTaskList = tasks;
        this.layoutResourceID = layoutResourceID;
    }

    public void updateTaskList(ArrayList<Task> newList) {
        mTaskList.clear();
        mTaskList.addAll(newList);
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(layoutResourceID, parent, false);
            return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Task task = mTaskList.get(position);
        holder.title.setText(task.getTaskName());
        if(layoutResourceID != R.layout.task_creation_list_item) {
            holder.dateCreated.setText(task.getDateCreated());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardClickListener.OnCardClicked(v, position);
                    CheckBox checkBox = (CheckBox) v.findViewById(R.id.task_completed);
                    if (checkBox.isChecked() == true) {
                        checkBox.setChecked(false);
                    }
                    else {
                        checkBox.setChecked(true);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public Task getTask(int position) {
        return mTaskList.get(position);
    }
}
