package com.example.amen.fragmentapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button first, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first = (Button) findViewById(R.id.button);
        second = (Button) findViewById(R.id.button2);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(1);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(2);
            }
        });
    }

    private void setFragment(int i) {
        Fragment fragmentToSet;
        if (i == 1) {
            fragmentToSet = new FirstFragment();
        } else {
            fragmentToSet = new BlankFragment();
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment2, fragmentToSet);

        transaction.commit();
    }
}
