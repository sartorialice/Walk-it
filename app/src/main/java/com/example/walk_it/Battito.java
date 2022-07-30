package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EventListener;

public class Battito extends AppCompatActivity implements SensorEventListener{


    private TextView tvBattiti=null;
    private Button bttReturnHome = null;
    private Sensor battito;
    SensorManager sMgr;
    private long battiti = 0;
    private String _nomeSfida = "Battito";
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battito);

        bttReturnHome = findViewById(R.id.bttReturnHome);
        tvBattiti=findViewById(R.id.tvBattiti);



        sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        battito = sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        if (battito == null) {
            String err = "Non possiedi questo sensore ";
            tvBattiti.setText(err);
            myunregister();
        }
        else{
        sMgr.registerListener(this, battito,SensorManager.SENSOR_DELAY_FASTEST);}

        bttReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myunregister();
                Intent intent=new Intent("return");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                startActivity(intent);
            }
        });



    }


    @Override
    protected void onPause() {
        super.onPause();
        myunregister();
    }

    private void myunregister() {
        if (sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
            sMgr.unregisterListener(this,battito);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            battiti = (int)sensorEvent.values[0];
            if(battiti > 0) writeFile("Ultima misurazione: " + msg, _nomeSfida,MODE_PRIVATE);
            msg = "" + battiti;
            tvBattiti.setText(msg);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void writeFile(String textToSave, String _nomeSfida, int MODE){

        try {
            FileOutputStream fileOutputStream=openFileOutput(_nomeSfida+".txt", MODE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();

            Toast.makeText(getApplicationContext(),"Result save", Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}