package com.example.amen.externalstoragereaderapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout;
    private boolean permissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionsGranted = isStoragePermissionGranted();

        layout = (LinearLayout) findViewById(R.id.layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        repaintButtons();
    }

    public void repaintButtons(){
        if(permissionsGranted ){
            for (final File file : FileManager.instance.getFilesFromFolder()) {
                Button newButton = new Button(this);
                newButton.setText(file.getName());

                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, ViewActivity.class);
                        startActivity(i);
                    }
                });

                layout.addView(newButton);
            }
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                repaintButtons();
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}
