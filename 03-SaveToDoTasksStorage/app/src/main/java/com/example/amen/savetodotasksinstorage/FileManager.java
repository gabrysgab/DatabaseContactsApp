package com.example.amen.savetodotasksinstorage;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by amen on 1/16/17.
 */

public class FileManager {
    private static final String FILE_NAME = "todotasks.txt";

    public static final FileManager instance = new FileManager();
    private final List<ToDoTask> list;

    private FileManager() {
        list = new LinkedList<>();
    }

    public List<ToDoTask> getList() {
        return list;
    }

    // ładowanie do listy (która jest w pamięci aplikacji) wszystkich elementów z pliku
    public void load(Context context){
        list.addAll(getFromFile(context));
    }

    // zapisywanie listy do pliku.
    public void save(Context context){
        saveToFile(context, list);
    }

    private void saveToFile(Context context, List<ToDoTask> listToSave) {
        try {
            FileOutputStream ofs = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(ofs);
            PrintWriter printWriter = new PrintWriter(writer);

            for (ToDoTask task : listToSave) {
                printWriter.println(task.toSerializedString());
            }

            printWriter.close();
        } catch (FileNotFoundException fnfe) {
            Toast.makeText(context, "File not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            Toast.makeText(context, "File read error.", Toast.LENGTH_SHORT).show();
        }
    }

    private List<ToDoTask> getFromFile(Context context) {
        List<ToDoTask> returnList = new LinkedList<>();

        try {
            FileInputStream inputStreamReader = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(inputStreamReader);
            BufferedReader reader = new BufferedReader(streamReader);

//            InputStreamReader inputStreamReader = new InputStreamReader(context.openFileInput(FILE_NAME));
//            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();

            while (line != null && !line.isEmpty()) {
                returnList.add(new ToDoTask(line));

                line = reader.readLine();
            }

            reader.close();
        } catch (FileNotFoundException fnfe) {
            Toast.makeText(context, "File not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            Toast.makeText(context, "Read exception.", Toast.LENGTH_SHORT).show();
        }

        return returnList;
    }
}
