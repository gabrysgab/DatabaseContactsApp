package com.mgabrynowicz.sensorreaderapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textViewAxisX;
    private TextView textViewAxisY;
    private TextView textViewAxisZ;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Button buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        textViewAxisX = (TextView) findViewById(R.id.axis_x);
        textViewAxisY = (TextView) findViewById(R.id.axis_y);
        textViewAxisZ = (TextView) findViewById(R.id.axis_z);
        buttonStop = (Button) findViewById(R.id.button_stop);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        FileManager.instance.openOutputStreams();

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileManager.instance.closeOutputStreams();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueX = sensorEvent.values[0];
            String stringValueX = String.valueOf(valueX);
            textViewAxisX.setText(stringValueX);

            float valueY = sensorEvent.values[1];
            String stringValueY = String.valueOf(valueY);
            textViewAxisY.setText(stringValueY);

            float valueZ = sensorEvent.values[2];
            String stringValueZ = String.valueOf(valueZ);
            textViewAxisZ.setText(stringValueZ);

            FileManager.instance.save(getApplicationContext(), sensorEvent.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
