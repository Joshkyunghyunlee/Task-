package com.joshkyunghyunlee.morrangu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryTasksActivity extends AppCompatActivity {

    MenuItem mDoneButton;
    ArrayList<Task> createdTaskList;
    String categoryTitle;
    String taskTitle;
    EditText taskEditText;
    FloatingActionButton addFab;
    private int animationComplete;
    TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_tasks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#415EAA")));

        addFab = (FloatingActionButton) findViewById(R.id.add_task_fab);
        addFab.setVisibility(View.GONE);

        Intent titleIntent = getIntent();
        categoryTitle = titleIntent.getStringExtra("title");
        taskEditText = (EditText) findViewById(R.id.task_edit_text);
        taskEditText.setHint("Create a task for '" + categoryTitle + "'");

        createdTaskList = new ArrayList<>();
        RecyclerView taskListView = (RecyclerView) findViewById(R.id.task_recycler_view);
        taskListView.setLayoutManager(new LinearLayoutManager(this));
        taskListAdapter = new TaskListAdapter(createdTaskList, R.layout.task_creation_list_item);
        taskListView.setAdapter(taskListAdapter);


        if(taskEditText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        taskEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    if (animationComplete == 0) {
                        animationComplete = 1;
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                        fadeIn.setDuration(1000);
                        AnimationSet animation = new AnimationSet(false); //change to false
                        animation.addAnimation(fadeIn);
                        addFab.setAnimation(animation);
                        addFab.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    if(animationComplete == 1) {
                        animationComplete = 0;
                        Animation fadeOut = new AlphaAnimation(1, 0);
                        fadeOut.setInterpolator(new DecelerateInterpolator());
                        fadeOut.setDuration(750);
                        AnimationSet animation = new AnimationSet(false);
                        animation.addAnimation(fadeOut);
                        addFab.setAnimation(animation);
                        addFab.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wizard_done_menu, menu);
        mDoneButton = menu.findItem(R.id.done_button);
        //mNextButton.setTitle(isFinalStep ? R.string.done : R.string.next);
        mDoneButton.setTitle("Done");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                onBackPressed();
                break;
            case R.id.done_button:
                if(createdTaskList.size() != 0) {
                    Intent finishIntent = new Intent();
                    finishIntent.putExtra("taskList", createdTaskList);
                    setResult(RESULT_OK, finishIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                else {
                    Toast.makeText(this, "Enter a task!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void addTask (View view) {
        taskTitle = taskEditText.getText().toString();
        if(!taskTitle.equals("")) {
            Task addedTask = new Task(taskTitle);
            createdTaskList.add(addedTask);
            taskEditText.setText("");
            taskEditText.setHint("Create another task");
            taskListAdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, "Enter a task!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}
