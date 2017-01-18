package com.example.amen.savetodotasksinstorage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.scrollable_layout);

        btn = (Button) findViewById(R.id.addTaskButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(i);
            }
        });

        FileManager.instance.load(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        layout.removeAllViews();
        List<ToDoTask> tasks = FileManager.instance.getList();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(layout.getLayoutParams());

        params.topMargin = 15;

        for (final ToDoTask task : tasks) {
            Button btn = new Button(this);
            btn.setText(task.getTitle());
            btn.setBackgroundColor(Color.RED);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, TaskActivity.class);

                    i.putExtra("Task", task.toSerializedString());
                    startActivity(i);
                }
            });
            layout.addView(btn, params);
        }
    }
}
