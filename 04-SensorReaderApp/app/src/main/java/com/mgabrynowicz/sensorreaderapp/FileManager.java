package com.mgabrynowicz.sensorreaderapp;

import android.content.Context;
import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

/**
 * Created by RENT on 2017-01-17.
 */

public class FileManager {
    public static final FileManager instance = new FileManager();

    private static final String folder_name = "accelerometer";

    private DataOutputStream writer_bin;
    private PrintWriter writer_ascii;
    private String filename;

    private FileManager() {

    }

    public void save(Context context, float[] values) {
        saveToFile(values);
        saveToFileASCII(values);
    }

    private void openOutputStreamWriterASCII() {
        try {

            FileOutputStream fileOutputStreamAscii = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name + "/" + filename + ".ascii");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStreamAscii);
            writer_ascii = new PrintWriter(outputStreamWriter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openOutputStreamWriter() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name + "/" + filename + ".bin");
            writer_bin = new DataOutputStream(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(float[] values) {
        try {
            writer_bin.writeFloat(values[0]);
            writer_bin.writeFloat(values[1]);
            writer_bin.writeFloat(values[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFileASCII(float[] values) {
        StringBuilder builder = new StringBuilder();
        builder.append(values[0]);
        builder.append(";");
        builder.append(values[1]);
        builder.append(";");
        builder.append(values[2]);

        writer_ascii.println(builder.toString());
    }

    public void openOutputStreams() {
        filename = "accelerometer_readings" + String.valueOf(System.currentTimeMillis());

        File containingFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name);
        if (!containingFolder.exists()) {
            containingFolder.mkdir();
        }

        openOutputStreamWriter();
        openOutputStreamWriterASCII();
    }

    public void closeOutputStreams() {

        try {
            writer_bin.close();
            writer_ascii.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
