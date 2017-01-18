package com.example.amen.externalstoragereaderapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        if (getIntent().hasExtra("filename")) {
            String fileName = getIntent().getStringExtra("filename");

            FileManager.instance.openInputStreams(fileName);

            TextView view = (TextView) findViewById(R.id.textView);
            view.setText(FileManager.instance.readFile(fileName));
        }
    }
}
