package com.joshkyunghyunlee.morrangu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;


public class ViewCategoryActivity extends AppCompatActivity implements TaskListAdapter.OnCardClickListener {

    TaskCategory taskCategory;
    int photoID;
    String categoryName;
    ArrayList<Task> taskArrayList;
    Cache cache;
    SharedPreferencesManager sharedPreferencesManager;
    public final static int RESULT_DELETE = 2;
    RecyclerView taskRecyclerView;
    TaskListAdapter taskListAdapter;
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRealm = Realm.getDefaultInstance();

        /*
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        */

        cache = Cache.getInstance();
        sharedPreferencesManager = new SharedPreferencesManager(this);

        Intent taskCategoryIntent = getIntent();
        taskCategory = taskCategoryIntent.getParcelableExtra("taskCategory");
        photoID = taskCategory.getPhotoID();
        categoryName = taskCategory.getCategoryName();
        taskArrayList = cache.getTaskArrayList(taskCategory);

        bindDataToLayout();
    }

    @Override
    public void OnCardClicked(View view, int position) {
        Task completedTask = taskListAdapter.getTask(position);

    }

    public void bindDataToLayout() {
        TextView categoryNameView = (TextView) findViewById(R.id.view_category_name);
        ImageView categoryImageView = (ImageView) findViewById(R.id.view_category_image);
        taskRecyclerView = (RecyclerView) findViewById(R.id.task_list_recycler_view);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskListAdapter = new TaskListAdapter(taskArrayList, R.layout.task_item_with_divider);
        taskListAdapter.setOnCardClickListener(this);
        taskRecyclerView.setAdapter(taskListAdapter);

        categoryNameView.setText(categoryName);
        categoryImageView.setImageResource(photoID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                onBackPressed();
                break;
            case R.id.action_delete:
                AlertDialog customBuilder = new AlertDialog.Builder(this)
                        .setTitle("Are you sure you want to delete the '" + categoryName + "' task category?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent deleteIntent = new Intent();
                                deleteIntent.putExtra("taskCategory", taskCategory);
                                setResult(RESULT_DELETE, deleteIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                Button deleteButton = customBuilder.getButton(DialogInterface.BUTTON_POSITIVE);
                Button cancelButton = customBuilder.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (deleteButton != null && cancelButton!= null) {
                    deleteButton.setTextColor(Color.parseColor("#0CABCE"));
                    cancelButton.setTextColor(Color.parseColor("#0CABCE"));
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}