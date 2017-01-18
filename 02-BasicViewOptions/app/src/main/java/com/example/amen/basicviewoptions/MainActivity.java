package com.example.amen.basicviewoptions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_WIDTH = "view.width";
    public static final String KEY_HEIGHT = "view.height";
    public static final String KEY_FONT_SIZE = "view.font_size";
    public static final String KEY_BTN_COLOR = "view.btn_color";

    public static final String PREFS_NAME = "view.prefs";

    private Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn1 = (Button) findViewById(R.id.button6);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);

    }

    private void setButtonStyle(Button btnToSet, int width, int height, int fontSize, int color) {
        btnToSet.setWidth(width);
        btnToSet.setHeight(height);
        btnToSet.setTextSize((float) fontSize);
        btnToSet.setBackgroundColor(color);
    }

    private int parseColor(String color) {
        switch (color) {
            case "Red":
                return Color.RED;
            case "Blue":
                return Color.BLUE;
            case "Black":
                return Color.BLACK;
            case "Green":
                return Color.GREEN;
            case "White":
                return Color.WHITE;
            default:
                return Color.BLACK;
        }
    }

    @Override
    protected void onResume() {
        getStylePreferences();
        super.onResume();
    }

    private void getStylePreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int width, height, fontSize, colorInt;
        String color;

        width = preferences.getInt(KEY_WIDTH, -1);
        height = preferences.getInt(KEY_HEIGHT, -1);
        fontSize = preferences.getInt(KEY_FONT_SIZE, -1);
        color = preferences.getString(KEY_BTN_COLOR, "");

        colorInt = parseColor(color);
        if (width == -1 || height == -1) {
            return;
        }

        setButtonStyle(btn1, width, height, fontSize, colorInt);
        setButtonStyle(btn2, width, height, fontSize, colorInt);
        setButtonStyle(btn3, width, height, fontSize, colorInt);
        setButtonStyle(btn4, width, height, fontSize, colorInt);
        setButtonStyle(btn5, width, height, fontSize, colorInt);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear) {

            // obsługa czyszczenia
            clearPreferences();
            getStylePreferences();
            return true;
        } else if (id == R.id.action_edit) {

            // obsługa edycji
            openPreferencesEditor();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferencesEditor() {
        Intent intent = new Intent(this, PrefsEditorActivity.class);
        startActivity(intent);
    }

    private void clearPreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();

        editor.commit();
    }
}
