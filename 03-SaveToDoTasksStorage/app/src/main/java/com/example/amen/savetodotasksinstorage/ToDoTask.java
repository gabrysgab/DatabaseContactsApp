package com.example.amen.savetodotasksinstorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amen on 1/16/17.
 */

public class ToDoTask {

    private static final String SEPARATOR = ";";

    private Date data;
    private String tytul;
    private String tresc;

    public ToDoTask(String fromLine) {
        String[] splits = fromLine.split(SEPARATOR);

        tytul = splits[0];
        try {
            data = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(splits[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tresc = splits[2];
    }

    public ToDoTask(String pTytul, String pTresc, Date pDate) {
        data = pDate;
        tytul = pTytul;
        tresc = pTresc;
    }

    public String toSerializedString() {
        return tytul + SEPARATOR + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(data) + SEPARATOR + tresc;
    }

    public String getTitle() {
        return tytul;
    }

    public String getContent() {
        return tresc;
    }

    public String getDateString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(data);
    }
}
