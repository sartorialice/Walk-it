package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Challenge extends AppCompatActivity implements SensorEventListener {

    private int numpassi;
    private int obiettivoPassi;
    private int timeSec;
    private final String TAG = "Challenge";
    private TextView tvNomeSfida=null;
    private TextView tvNumPassi= null;
    private TextView tvPassiSfida=null;
    private TextView tvTimeSfida=null;

    private TextView countDownText= null;
    private Button startButton = null, terminateButton=null;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 600000; //10 min
    private boolean timeIsRunning;

    private SensorManager sensorManager = null;
    private Sensor detectorStep = null;
    private SensorEventListener sensorEventListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numpassi=0;
        setContentView(R.layout.activity_challenge);
        sensorEventListener = this;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //se il sensore esiste sul telefonno dove gira l'app allora viene recuperato
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            detectorStep = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            Log.i(TAG, "Sensore StepDetector inizializzato correttamente");
        } else {
            Log.i(TAG, "Sensore StepDetector non presente");
        }

        //timer
        countDownText = findViewById(R.id.countDown);
        startButton = findViewById(R.id.start);
        terminateButton=findViewById(R.id.bttTermina);

        tvPassiSfida=findViewById(R.id.tvPassiSfida);
        tvTimeSfida=findViewById(R.id.tvTimeSfida);
        tvNomeSfida=findViewById(R.id.tvNomeSfida);
        tvNumPassi=findViewById(R.id.tvNumPassi);


        Intent _intent=getIntent();
        String _nomeSfida=_intent.getStringExtra("NOME_SFIDA");
        String _passi=_intent.getStringExtra("PASSI");
        String _time=_intent.getStringExtra("TIME");
        tvNomeSfida.setText(_nomeSfida);
        tvTimeSfida.setText(_time + " Secondi");
        tvPassiSfida.setText(_passi + " Passi");

        obiettivoPassi= Integer.parseInt(_passi);
        timeSec=Integer.parseInt(_time);
        startStop();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();

            }
        });

        terminateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("result");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire

                intent.putExtra("RESULT","YOU NOT DID IT :(");
                startActivity(intent);
            }
        });
    }

    private void startStop() {


        if(timeIsRunning) stopTimer();
        else startTimer();
    }

    private void startTimer() {
        onResume();
        countDownTimer = new CountDownTimer(timeSec*1000, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Intent intent=new Intent("result");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("RESULT","YOU NOT DID IT :(");
                startActivity(intent);
            }
        }.start();
        startButton.setText("PAUSE");
        timeIsRunning = true;
    }

    private void stopTimer() {
        onPause();
        countDownTimer.cancel();
        startButton.setText("START");
        timeIsRunning = false;

    }

    private void updateTimer() {
        int minute = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeft;
        timeLeft = "" + minute;
        timeLeft += ":";
        if(seconds < 10) timeLeft += "0";
        timeLeft += seconds;

        countDownText.setText(timeLeft);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.i(TAG, "OnSensorChanged");
        if (sensorEvent.sensor == detectorStep) {
            numpassi++;
            tvNumPassi.setText(String.valueOf(numpassi)+"/"+ String.valueOf(obiettivoPassi));
            if(numpassi >= obiettivoPassi){

                Intent intent=new Intent("result");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("RESULT","YOU DID IT");

                startActivity(intent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "Accuracy changed, New accuracy: " + accuracy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null)
            sensorManager.registerListener(this,detectorStep,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorManager.unregisterListener(this,detectorStep);
        }
    }

}