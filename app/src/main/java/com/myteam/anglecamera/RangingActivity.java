package com.myteam.anglecamera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Cao on 2016/12/19.
 */

public class RangingActivity
        extends Activity
        implements SensorEventListener
{

    static final String TAG = "CameraActivity";

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
//    private Sensor gyroscopeSensor;
//    private Sensor magneticSensor;

    private TextView showTextViewX;
    private TextView showTextViewY;
    private TextView showTextViewZ;


    private EditText realDisText;
    private EditText cameraDisText;
    private TextView showResult;

    private ImageButton calculateBtn;

    private float scale = 1.0f;
    private float realDistance = 0f;
    private float cameraDistance = 0f;
    private float result = 0.0f;

    // 将纳秒转化为秒
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float angle[] = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);

        getFragmentManager().beginTransaction()
                .replace(R.id.camera_container, Camera2BasicFragment.newInstance("0"))
                .commit();

        realDisText = (EditText) findViewById(R.id.realDisText);
        cameraDisText = (EditText) findViewById(R.id.cameraDisText);
        showResult = (TextView)findViewById(R.id.result);
        calculateBtn = (ImageButton) findViewById(R.id.calculate_btn);

        if(calculateBtn != null){
            calculateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realDistance = Float.parseFloat(realDisText.getText().toString());
                    cameraDistance = Float.parseFloat(cameraDisText.getText().toString());
                    result = (realDistance / cameraDistance) * scale * 1.0f;
                    showResult.setText(getString(R.string.print_result,result));
                }
            });
        }
        if(realDisText == null){
            Log.i(TAG, "realDisText == null");
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
//        magneticSensor = sensorManager
//                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        gyroscopeSensor = sensorManager
//                .getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        sensorManager.registerListener(this, gyroscopeSensor,
//                SensorManager.SENSOR_DELAY_GAME);
//        sensorManager.registerListener(this, magneticSensor,
//                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //@Override
    public void onSensorChanged(SensorEvent event) {
        if(showTextViewX == null){
            Log.i(TAG, "showTextViewX == null");
        }
        if(calculateBtn == null){
            calculateBtn = (ImageButton) findViewById(R.id.calculate_btn);
            calculateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realDistance = Float.parseFloat(realDisText.getText().toString());
                    cameraDistance = Float.parseFloat(cameraDisText.getText().toString());
                    result = (realDistance / cameraDistance) * scale * 1.0f;
                    showResult.setText(getString(R.string.print_result,result));
                }
            });
        }

        showTextViewX = (TextView) findViewById(R.id.horizontal_content);
        showTextViewY = (TextView) findViewById(R.id.vertical_content);
        showTextViewZ = (TextView) findViewById(R.id.z_content);
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // x,y,z分别存储坐标轴x,y,z上的加速度
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            showTextViewX.setText(getString(R.string.print_angle, x));
            showTextViewY.setText(getString(R.string.print_angle, y));
            showTextViewZ.setText(getString(R.string.print_angle, z));
        }
    }

    //@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
