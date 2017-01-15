package com.example.amen.basicviewoptions;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import static com.example.amen.basicviewoptions.MainActivity.KEY_BTN_COLOR;
import static com.example.amen.basicviewoptions.MainActivity.KEY_FONT_SIZE;
import static com.example.amen.basicviewoptions.MainActivity.KEY_HEIGHT;
import static com.example.amen.basicviewoptions.MainActivity.KEY_WIDTH;
import static com.example.amen.basicviewoptions.MainActivity.PREFS_NAME;

public class PrefsEditorActivity extends AppCompatActivity {

    private EditText inputWidth, inputHeight;

    private Spinner colorsSpinner;
    private SeekBar seekBarFont;

    private Button saveButton;

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs_editor);


        inputHeight = (EditText) findViewById(R.id.input_height);
        inputWidth = (EditText) findViewById(R.id.input_width);

        colorsSpinner = (Spinner) findViewById(R.id.spinner_color);

        saveButton = (Button) findViewById(R.id.button);

        seekBarFont = (SeekBar) findViewById(R.id.seek_font);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.colors));
        colorsSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();

                setResult(RESULT_OK);
                finish();
            }
        });

        loadPreferences();
    }

    private void loadPreferences(){
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int width = preferences.getInt(KEY_WIDTH, -1);
        int height = preferences.getInt(KEY_HEIGHT, -1);
        String color = preferences.getString(KEY_BTN_COLOR, "");
        int fontSize= preferences.getInt(KEY_FONT_SIZE, -1);

        inputHeight.setText(String.valueOf(width));
        inputWidth.setText(String.valueOf(height));
        seekBarFont.setProgress(fontSize);

        int zaznaczonyKolor = adapter.getPosition(color);

        colorsSpinner.setSelection(zaznaczonyKolor);
    }

    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String widthString = inputWidth.getText().toString();
        String heightString = inputHeight.getText().toString();
        int widthInteger = -1;
        int heightInteger = -1;

        try {
            if (!widthString.isEmpty())
                widthInteger = Integer.parseInt(widthString);
            if (!heightString.isEmpty())
                heightInteger = Integer.parseInt(heightString);
        } catch (NumberFormatException nfe) {
            Toast.makeText(
                    getApplicationContext(),
                    "Wrong value!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        editor.putInt(KEY_WIDTH, widthInteger);
        editor.putInt(KEY_HEIGHT, heightInteger);
        editor.putInt(KEY_FONT_SIZE, seekBarFont.getProgress());
        editor.putString(KEY_BTN_COLOR, colorsSpinner.getSelectedItem().toString());

        editor.apply();
    }
}
