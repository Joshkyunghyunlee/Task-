package com.joshkyunghyunlee.morrangu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AddCategoryFixedTitleActivity extends AppCompatActivity {

    private String categoryTitle;
    private int imageIndex;
    private static final int GET_TASKS_REQUEST = 3;
    private static final int GET_CUSTOM_TITLE_REQUEST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_fixed_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#415EAA")));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            if(b != null) {

            }
            final ArrayList<Task> taskList = b.getParcelableArrayList("taskList");
            Intent createIntent = new Intent();

            if(requestCode == GET_CUSTOM_TITLE_REQUEST) {
                categoryTitle = data.getStringExtra("title");
                createIntent.putExtra("title", categoryTitle);
                createIntent.putExtra("taskList", taskList);
                setResult(RESULT_OK, createIntent);
                finish();
            }

            else if (requestCode == GET_TASKS_REQUEST) {
                createIntent.putExtra("title", categoryTitle);
                createIntent.putExtra("taskList", taskList);
                setResult(RESULT_OK, createIntent);
                finish();
                //TODO: get the task list and set the title back
            }
         }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void fixedOption1 (View view) {
        Intent tasksIntent = new Intent(this, AddCategoryTasksActivity.class);
        Button button = (Button) findViewById(R.id.fixedOption1);
        categoryTitle = button.getText().toString();
        tasksIntent.putExtra("title", categoryTitle);
        startActivityForResult(tasksIntent, GET_TASKS_REQUEST);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void fixedOption2 (View view) {
        Intent tasksIntent = new Intent(this, AddCategoryTasksActivity.class);
        Button button = (Button) findViewById(R.id.fixedOption2);
        categoryTitle = button.getText().toString();
        tasksIntent.putExtra("title", categoryTitle);
        startActivityForResult(tasksIntent, GET_TASKS_REQUEST);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void fixedOption3 (View view) {
        Intent tasksIntent = new Intent(this, AddCategoryTasksActivity.class);
        Button button = (Button) findViewById(R.id.fixedOption3);
        categoryTitle = button.getText().toString();
        tasksIntent.putExtra("title", categoryTitle);
        startActivityForResult(tasksIntent, GET_TASKS_REQUEST);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void fixedOption4 (View view) {
        Intent tasksIntent = new Intent(this, AddCategoryTasksActivity.class);
        Button button = (Button) findViewById(R.id.fixedOption4);
        categoryTitle = button.getText().toString();
        tasksIntent.putExtra("title", categoryTitle);
        startActivityForResult(tasksIntent, GET_TASKS_REQUEST);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void customOption (View view) {
        Intent customTitleIntent = new Intent(this, AddCategoryCustomTitleActivity.class);
        startActivityForResult(customTitleIntent, GET_CUSTOM_TITLE_REQUEST);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
