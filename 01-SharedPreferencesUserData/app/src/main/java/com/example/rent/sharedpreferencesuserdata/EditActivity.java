package com.example.rent.sharedpreferencesuserdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static com.example.rent.sharedpreferencesuserdata.MainActivity.PREFERENCES_PATH;
import static com.example.rent.sharedpreferencesuserdata.MainActivity.PREFERENCE_COUNTRY;
import static com.example.rent.sharedpreferencesuserdata.MainActivity.PREFERENCE_LANGUAGE;
import static com.example.rent.sharedpreferencesuserdata.MainActivity.PREFERENCE_NAME;
import static com.example.rent.sharedpreferencesuserdata.MainActivity.PREFERENCE_SURNAME;
import static com.example.rent.sharedpreferencesuserdata.R.id.save;

public class EditActivity extends AppCompatActivity {

    private EditText name_input;
    private EditText surname_input;

    private Button saveBtn, cancelBtn;

    private Spinner spin_country, spin_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name_input = (EditText) findViewById(R.id.editor_name);
        surname_input = (EditText) findViewById(R.id.editor_surname);

        spin_language = (Spinner) findViewById(R.id.spinner_language);
        spin_country = (Spinner) findViewById(R.id.spinner_country);

        saveBtn = (Button) findViewById(save);
        cancelBtn = (Button) findViewById(R.id.cancel);

        ArrayAdapter<String> spinnerCountriesAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.countries));
        spin_country.setAdapter(spinnerCountriesAdapter);

        ArrayAdapter<String> spinnerLanguageAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.languages));
        spin_language.setAdapter(spinnerLanguageAdapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity(RESULT_CANCELED);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                returnToMainActivity(RESULT_OK);
            }
        });

        checkIfEmptyFields();

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {     }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfEmptyFields();
            }
            @Override
            public void afterTextChanged(Editable s) {      }
        };

        name_input.addTextChangedListener(watcher);
        surname_input.addTextChangedListener(watcher);

        name_input.setText(getIntent().getStringExtra(PREFERENCE_NAME));
        surname_input.setText(getIntent().getStringExtra(PREFERENCE_SURNAME));

        String lang = getIntent().getStringExtra(PREFERENCE_LANGUAGE);
        String country = getIntent().getStringExtra(PREFERENCE_COUNTRY);

        if (lang != null && !lang.isEmpty()) {
            int positionLang = spinnerLanguageAdapter.getPosition(lang);
            spin_language.setSelection(positionLang);
        }
        if (country != null && !country.isEmpty()) {
            int positionCountry = spinnerLanguageAdapter.getPosition(country);
            spin_country.setSelection(positionCountry);
        }
    }

    private void checkIfEmptyFields() {
        if (name_input.getText().toString().isEmpty() && surname_input.getText().toString().isEmpty()) {
            cancelBtn.setEnabled(false);
            saveBtn.setEnabled(false);
        } else {
            cancelBtn.setEnabled(true);
            saveBtn.setEnabled(true);
        }
    }

    private void savePreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_PATH, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(PREFERENCE_NAME, name_input.getText().toString());
        editor.putString(PREFERENCE_SURNAME, surname_input.getText().toString());
        editor.putString(PREFERENCE_LANGUAGE, spin_language.getSelectedItem().toString());
        editor.putString(PREFERENCE_COUNTRY, spin_country.getSelectedItem().toString());

        editor.commit();
    }

    private void returnToMainActivity(int result) {
        Intent i = new Intent();

        setResult(result, i);
        finish();
    }
}
