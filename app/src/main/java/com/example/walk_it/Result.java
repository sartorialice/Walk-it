package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity implements SensorEventListener {

    private TextView tvResultSfida= null, tvBattiti=null;
    private Button bttReturnHome = null;
    private Sensor battito;
    SensorManager sMgr;
    private long battiti = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResultSfida = findViewById(R.id.tvResultSfida);
        bttReturnHome = findViewById(R.id.bttReturnHome);
        tvBattiti=findViewById(R.id.tvBattiti);

        Intent _intent=getIntent();
        String _resultSfida=_intent.getStringExtra("RESULT");

        tvResultSfida.setText(_resultSfida);

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
            String msg = "" + battiti;
            tvBattiti.setText(msg);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}