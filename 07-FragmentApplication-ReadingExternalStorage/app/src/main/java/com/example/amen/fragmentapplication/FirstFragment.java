package com.example.amen.fragmentapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by amen on 1/19/17.
 */

public class FirstFragment extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_fragment, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null) {// zawsze nie będzie nullem, ponieważ view został stworzony w onCreateView
            textView = (TextView) getView().findViewById(R.id.textView1);
            if (getArguments() != null) { // bo mogliśmy nic nie przekazać ( przy 1 uruchomieniu)
                String fileName = getArguments().getString("filename");
                if (fileName != null && !fileName.isEmpty()) {
                    FileManager.instance.openInputStreams(fileName);
                    textView.setText(FileManager.instance.readFile(fileName));
                }
            }
        }
    }
}
