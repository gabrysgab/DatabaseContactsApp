package com.example.amen.externalstoragereaderapp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amen on 1/18/17.
 */

public class FileManager {
    public static final FileManager instance = new FileManager();

    private static final String folder_name = "accelerometer";

    private FileInputStream reader_bin;
    private BufferedReader reader_ascii;

    private FileManager() {

    }

    private void openInputStreamReaderASCII(String filename) {
        try {
            FileInputStream fileInputStreamAscii = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStreamAscii);
            reader_ascii = new BufferedReader(inputStreamReader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openOutputStreamWriter(String filename) {
        try {
            reader_bin = new FileInputStream(filename);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<File> getFilesFromFolder() {
        List<File> list = new ArrayList<>();

        File containingFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name);
        if (!containingFolder.exists()) {
            containingFolder.mkdir();
        }
        for (File file : containingFolder.listFiles()) {
            if (file.isFile() && (file.getName().endsWith(".bin") || file.getName().endsWith(".ascii"))) {
                list.add(file);
            }
        }

        return list;
    }

    public void openInputStreams(String filename) {
        File containingFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name);
        if (!containingFolder.exists()) {
            containingFolder.mkdir();
        }

        if (filename.endsWith(".bin")) {
            openOutputStreamWriter(filename);
        } else {
            openInputStreamReaderASCII(filename);
        }
    }

    public void closeOutputStreams() {
        try {
            reader_bin.close();
            reader_ascii.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String fileName) {
        try {
            if (fileName.endsWith(".ascii")) {
                return readAscii();
            } else {
                return readBinary();
            }
        } catch (IOException ioe) {
            Log.wtf(FileManager.class.getName(), "IO Error: " + ioe.getMessage());
        }
        return "Error reading file.";
    }

//    public static byte[] FloatArray2ByteArray(float[] values) {
//        ByteBuffer buffer = ByteBuffer.allocate(4 * values.length);
//
//        for (float value : values) {
//            buffer.putFloat(value);
//        }
//
//        return buffer.array();
//    }
//
//    private void saveToFile(float[] values) {
//        byte[] byteArray = FloatArray2ByteArray(values);
//        try {
//            writer_bin.write(new String(byteArray).toCharArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private String readBinary() throws IOException {
        StringBuilder builder = new StringBuilder();

        DataInputStream dis = new DataInputStream(reader_bin);

        while (dis.available() >= 12) {

            builder.append(String.valueOf(dis.readFloat()));
            builder.append(String.valueOf(dis.readFloat()));
            builder.append(String.valueOf(dis.readFloat()));
            builder.append("\n");
        }

        dis.close();
        return builder.toString();
    }

    private String readAscii() throws IOException {
        StringBuilder builder = new StringBuilder();

        String line = reader_ascii.readLine();

        while (line != null && !line.isEmpty()) {
            builder.append(line).append("\n");

            line = reader_ascii.readLine();
        }

        reader_ascii.close();
        return builder.toString();
    }
}
