package com.example.walk_it;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Challenge extends AppCompatActivity implements SensorEventListener {

    DateFormat datum;
    String date;

    private int stepcount;
    private int numpassi;
    private int obiettivoPassi;
    private int timeSec;
    private String _nomeSfida;
    private final String TAG = "Challenge";
    private TextView tvNomeSfida=null;
    private TextView tvNumPassi= null;
    private TextView tvPassiSfida=null;
    private TextView tvTimeSfida=null;

    private TextView countDownText= null;
    private Button startButton = null, terminateButton=null;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds;
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
        /*
        //se il sensore esiste sul telefonno dove gira l'app allora viene recuperato
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            detectorStep = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            Log.i(TAG, "Sensore StepDetector inizializzato correttamente");
        } else {
            Log.i(TAG, "Sensore StepDetector non presente");
        }
*/
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            detectorStep = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Log.i(TAG, "Sensore StepCounter inizializzato correttamente");
        } else {
            Log.i(TAG, "Sensore StepCounter non presente");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensorManager.registerListener(this, detectorStep, SensorManager.SENSOR_DELAY_FASTEST);
            Log.i(TAG, "sensore registrato");
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
        _nomeSfida=_intent.getStringExtra("NOME_SFIDA");
        String _passi=_intent.getStringExtra("PASSI");
        String _time=_intent.getStringExtra("TIME");
        tvNomeSfida.setText(_nomeSfida);
        tvTimeSfida.setText(_time + " Secondi");
        tvPassiSfida.setText(_passi + " Passi");

        obiettivoPassi= Integer.parseInt(_passi);
        timeLeftInMilliseconds=Integer.parseInt(_time) * 1000;
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

                startStop();
                Intent intent=new Intent("result");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire

                intent.putExtra("RESULT","SARA' PER LA PROSSIMA VOLTA!");
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
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                datum = new SimpleDateFormat("MMM dd yyyy, h:mm");
                date = datum.format(Calendar.getInstance().getTime());
                writeFile("Ultimo tentativo (non superata):" + date, _nomeSfida, MODE_PRIVATE);
                writeFile("(non superata): " + date + "\n", _nomeSfida + "storico", MODE_APPEND);
                Intent intent = new Intent("result");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("RESULT", "PURTROPPO QUESTA VOLTA NON CE L'HAI FATTA");
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.i(TAG, "OnSensorChanged");
        if(stepcount == 0) stepcount = (int) sensorEvent.values[0];
        if (sensorEvent.sensor == detectorStep) {
            numpassi = (int) sensorEvent.values[0] - stepcount;
            tvNumPassi.setText(String.valueOf(numpassi)+"/"+ String.valueOf(obiettivoPassi));
            if(numpassi >= obiettivoPassi){
                stopTimer();
                datum = new SimpleDateFormat("MMM dd yyyy, h:mm");
                date = datum.format(Calendar.getInstance().getTime());
                writeFile("(Superata)",_nomeSfida+"superata",MODE_PRIVATE);
                writeFile("Ultimo tentativo(superata):\n"+ date, _nomeSfida,MODE_PRIVATE);
                writeFile("(superata): "+ date+"\n", _nomeSfida+"storico",MODE_APPEND);

                final String CHANNELID = "WALK_IT SERVICE";
                NotificationChannel channel = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    channel = new NotificationChannel(
                            CHANNELID,
                            CHANNELID,
                            NotificationManager.IMPORTANCE_HIGH
                    );

                    getSystemService(NotificationManager.class).createNotificationChannel(channel);
                    Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                            .setContentText("Riposati o prova un'altra sfida")
                            .setContentTitle("Successo!")
                            .setSmallIcon(R.drawable.ic_launcher_foreground);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                    notificationManager.notify(1 , notification.build());

                    Intent intent=new Intent("result");//mettere stringa nel file strings
                    //al posto di sfida 1 mettere path del file da aprire
                    intent.putExtra("RESULT","CE L'HAI FATTA!");

                    startActivity(intent);
                }
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

    public void writeFile(String textToSave, String _nomeSfida, int MODE){

        try {
            FileOutputStream fileOutputStream=openFileOutput(_nomeSfida+".txt", MODE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();

            //Toast.makeText(getApplicationContext(),"Result save", Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}