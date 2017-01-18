package com.example.amen.externalstoragereaderapp;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
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

    private InputStreamReader reader_bin;
    private BufferedReader reader_ascii;
    private String filename;

    private FileManager() {

    }

    private void openInputStreamReaderASCII() {
        try {
            FileInputStream fileInputStreamAscii = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name + "/" + filename + ".ascii");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStreamAscii);
            reader_ascii = new BufferedReader(inputStreamReader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openOutputStreamWriter() {
        try {
            FileInputStream fileInputStreamAscii = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name + "/" + filename + ".bin");
            reader_bin = new InputStreamReader(fileInputStreamAscii);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<File> getFilesFromFolder() {
        List<File> list = new ArrayList<>();

        File containingFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name);
        for (File file : containingFolder.listFiles()) {
            if (file.isFile() && (file.getName().endsWith(".bin") || file.getName().endsWith(".ascii"))) {
                list.add(file);
            }
        }

        return list;
    }

    public void openInputStreams() {
        filename = "accelerometer_readings" + String.valueOf(System.currentTimeMillis());

        File containingFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder_name);
        if (!containingFolder.exists()) {
            containingFolder.mkdir();
        }

        openOutputStreamWriter();
        openInputStreamReaderASCII();
    }

    public void closeOutputStreams() {
        try {
            reader_bin.close();
            reader_ascii.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
