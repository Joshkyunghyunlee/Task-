package com.joshkyunghyunlee.morrangu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final int VIEW_CATEGORY_REQUEST_CODE = 1;
    private final int CREATE_TASK_REQUEST_CODE = 0;
    SharedPreferencesManager sharedPreferencesManager;
    Cache cache;
    ExpandableListView expListView;
    ExpandableListAdapter expListAdapter;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        cache = Cache.getInstance();
        sharedPreferencesManager = new SharedPreferencesManager(this);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Intent viewCategoryIntent = new Intent(MainActivity.this, ViewCategoryActivity.class);
                TaskCategory taskCategory = expListAdapter.getGroup(groupPosition);
                viewCategoryIntent.putExtra("taskCategory", taskCategory);
                startActivityForResult(viewCategoryIntent, VIEW_CATEGORY_REQUEST_CODE);
                return true; //click event has finally been handled
            }
        });

        loadArray();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createTaskIntent = new Intent(MainActivity.this, AddCategoryFixedTitleActivity.class);
                startActivityForResult(createTaskIntent, CREATE_TASK_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            ArrayList<Task> taskList = b.getParcelableArrayList("taskList");
            String categoryTitle = data.getStringExtra("title");

            int imageIndex = Theme.getContextualImage(categoryTitle);
            int imageID = Theme.getImageID(imageIndex);

            TaskCategory createdCategory = new TaskCategory(categoryTitle, imageID);
            cache.getTaskCategoryDataSet().add(createdCategory);

            for(int i = 0; i < cache.getTaskCategoryDataSet().size(); i++) {
                if(cache.getTaskCategoryDataSet().get(i).getCategoryName().equals(categoryTitle)) {
                    cache.getTaskDataSet().put(cache.getTaskCategoryDataSet().get(i), taskList);
                }
            }
        }
        else if (resultCode == ViewCategoryActivity.RESULT_DELETE) {
            Bundle b = data.getExtras();
            TaskCategory categoryToDelete = b.getParcelable("taskCategory");
            if(categoryToDelete != null) {
                cache.deleteCategory(categoryToDelete);
                Toast.makeText(this, "Task category deleted", Toast.LENGTH_SHORT).show();
            }
        }
        expListAdapter.notifyDataSetChanged();
        sharedPreferencesManager.saveArray(); //save data on any action
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void loadArray() {
        sharedPreferencesManager.loadArray();
        expListAdapter = new ExpandableListAdapter(this, cache.getTaskCategoryDataSet(), cache.getTaskDataSet());
        expListView.setAdapter(expListAdapter);
        expListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_calender) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            cache.getTaskCategoryDataSet().clear();
            cache.getTaskDataSet().clear();
            expListAdapter.notifyDataSetChanged();
            sharedPreferencesManager.saveArray();
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }
}

