package com.example.amen.savetodotasksinstorage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private EditText content, title, date;

    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        content = (EditText) findViewById(R.id.inputContent);
        date = (EditText) findViewById(R.id.inputDate);
        title = (EditText) findViewById(R.id.inputTitle);

        saveBtn = (Button) findViewById(R.id.buttonSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getIntent().hasExtra("Task")) {
                    ToDoTask task = createToDoTask();


                    FileManager.instance.getList().add(task);
                    FileManager.instance.save(getApplicationContext());
                }
//                setResult(RESULT_OK);
                finish();
            }
        });

        date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        if (getIntent().hasExtra("Task")) {
            ToDoTask toSet = new ToDoTask(getIntent().getStringExtra("Task"));
            content.setEnabled(false);
            title.setEnabled(false);

            title.setText(toSet.getTitle());
            content.setText(toSet.getContent());
            date.setText(toSet.getDateString());
        }
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    private ToDoTask createToDoTask() {
        String titleString = title.getText().toString();
        String contentString = content.getText().toString();
        Date currentDate = new Date();
        try {
            currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Wrong date...", Toast.LENGTH_SHORT).show();
        }

        return new ToDoTask(titleString, contentString, currentDate);
    }


}
