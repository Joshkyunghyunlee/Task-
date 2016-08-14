package com.joshkyunghyunlee.morrangu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCategoryCustomTitleActivity extends AppCompatActivity {

    private MenuItem mNextButton;
    EditText editText;
    String title;
    private static final int GET_TASKS_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_custom_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#415EAA")));

        EditText titleEditText = (EditText) findViewById(R.id.title_edit_text);
        if(titleEditText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            //TODO: get task list from data and pass it back to prev
            Bundle b = data.getExtras();
            final ArrayList<Task> taskList = b.getParcelableArrayList("taskList");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("taskList", taskList);
            returnIntent.putExtra("title", title);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wizard_next_menu, menu);
        mNextButton = menu.findItem(R.id.next_button);
        //mNextButton.setTitle(isFinalStep ? R.string.done : R.string.next);
        mNextButton.setTitle("Next");
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                onBackPressed();
                break;
            case R.id.next_button:
                editText = (EditText) findViewById(R.id.title_edit_text);
                title = editText.getText().toString();
                if(title.trim().equals("")) {
                    Toast.makeText(AddCategoryCustomTitleActivity.this, "Enter a category name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent createTaskIntent = new Intent(this, AddCategoryTasksActivity.class);
                    createTaskIntent.putExtra("title", title);
                    startActivityForResult(createTaskIntent, GET_TASKS_REQUEST);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
