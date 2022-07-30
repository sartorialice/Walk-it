package com.example.walk_it;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private int HEART_SENSOR_PERMISSION = 1;
    private final int ACTIVITY_REQUEST_CODE = 1;
    private int STEP_COUNTER_PERMISSION = 1;

    private Button bttBattito=null, bttSfida1=null,bttSfida2=null,bttSfida3=null,bttSfida4=null, bttSfida5=null,bttSfidaPersonalizzata=null, bttSfida1Storico=null, bttSfida2Storico=null, bttSfida3Storico=null,bttSfida4Storico=null, bttSfida5Storico=null;
    private TextView tvBattitoStorico=null, tvBattito=null, tvSfida1Superata=null,tvSfida2Superata=null,tvSfida3Superata=null,tvSfida4Superata=null,tvSfida5Superata=null, tvSfida1=null, tvSfida2=null, tvSfida3=null,tvSfida4=null,tvSfida5=null, tvnumPassiSfida1=null, tvSfida1Time=null , tvnumPassiSfida2=null, tvSfida2Time=null, tvnumPassiSfida3=null, tvSfida3Time=null, tvnumPassiSfida4=null, tvSfida4Time=null, tvnumPassiSfida5=null, tvSfida5Time=null, tvSfida1Storico=null,tvSfida2Storico=null,tvSfida3Storico=null,tvSfida4Storico=null,tvSfida5Storico=null;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bttBattito = findViewById(R.id.bttBattito);
        bttSfida1=findViewById(R.id.bttSfida1);
        bttSfida2=findViewById(R.id.bttSfida2);
        bttSfida3=findViewById(R.id.bttSfida3);
        bttSfida4=findViewById(R.id.bttSfida4);
        bttSfida5=findViewById(R.id.bttSfida5);
        bttSfida1Storico=findViewById(R.id.bttSfida1Storico);
        bttSfida2Storico=findViewById(R.id.bttSfida2Storico);
        bttSfida3Storico=findViewById(R.id.bttSfida3Storico);
        bttSfida4Storico=findViewById(R.id.bttSfida4Storico);
        bttSfida5Storico=findViewById(R.id.bttSfida5Storico);
        bttSfidaPersonalizzata=findViewById(R.id.bttSfidaPersonalizzata);

        tvBattito = findViewById(R.id.tvBattito);
        tvBattitoStorico = findViewById(R.id.tvBattitoStorico);
        tvSfida1=findViewById(R.id.tvSfida1);
        tvnumPassiSfida1=findViewById((R.id.tvSfida1NumPassi));
        tvSfida1Time=findViewById(R.id.tvSfida1Time);
        tvSfida1Storico=findViewById(R.id.tvSfida1Storico);
        tvSfida1Superata=findViewById(R.id.tvSfida1Superata);


        tvSfida2=findViewById(R.id.tvSfida2);
        tvnumPassiSfida2=findViewById((R.id.tvSfida2NumPassi));
        tvSfida2Time=findViewById(R.id.tvSfida2Time);
        tvSfida2Storico=findViewById(R.id.tvSfida2Storico);
        tvSfida2Superata=findViewById(R.id.tvSfida2Superata);


        tvSfida3=findViewById(R.id.tvSfida3);
        tvnumPassiSfida3=findViewById((R.id.tvSfida3NumPassi));
        tvSfida3Time=findViewById(R.id.tvSfida3Time);
        tvSfida3Storico=findViewById(R.id.tvSfida3Storico);
        tvSfida3Superata=findViewById(R.id.tvSfida3Superata);


        tvSfida4=findViewById(R.id.tvSfida4);
        tvnumPassiSfida4=findViewById((R.id.tvSfida4NumPassi));
        tvSfida4Time=findViewById(R.id.tvSfida4Time);
        tvSfida4Storico=findViewById(R.id.tvSfida4Storico);
        tvSfida4Superata=findViewById(R.id.tvSfida4Superata);


        tvSfida5=findViewById(R.id.tvSfida5);
        tvnumPassiSfida5=findViewById((R.id.tvSfida5NumPassi));
        tvSfida5Time=findViewById(R.id.tvSfida5Time);
        tvSfida5Storico=findViewById(R.id.tvSfida5Storico);
        tvSfida5Superata=findViewById(R.id.tvSfida5Superata);





        readFile(tvSfida1.getText().toString(),tvSfida1Storico);
        readFile(tvSfida2.getText().toString(),tvSfida2Storico);
        readFile(tvSfida3.getText().toString(),tvSfida3Storico);
        readFile(tvSfida4.getText().toString(),tvSfida4Storico);
        readFile(tvSfida5.getText().toString(),tvSfida5Storico);
        readFile("Battito",tvBattitoStorico);
        readFile(tvSfida1.getText().toString()+"superata",tvSfida1Superata);
        readFile(tvSfida2.getText().toString()+"superata",tvSfida2Superata);
        readFile(tvSfida3.getText().toString()+"superata",tvSfida3Superata);
        readFile(tvSfida4.getText().toString()+"superata",tvSfida4Superata);
        readFile(tvSfida5.getText().toString()+"superata",tvSfida5Superata);


        //FOREGROUND SERVICE
        if(!foregroundServiceRunning()) {
            Intent serviceIntent = new Intent(this, MyForegroundService.class);
            startForegroundService(serviceIntent);
        }
        bttSfida1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida1.getText().toString();
                String _numPassi=tvnumPassiSfida1.getText().toString();
                String _time=tvSfida1Time.getText().toString();
                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                intent.putExtra("PASSI",_numPassi);
                intent.putExtra("TIME",_time);
                startActivity(intent);
            }
        });

        bttSfida2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida2.getText().toString();
                String _numPassi=tvnumPassiSfida2.getText().toString();
                String _time=tvSfida2Time.getText().toString();

                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                intent.putExtra("PASSI",_numPassi);
                intent.putExtra("TIME",_time);

                startActivity(intent);
            }
        });

        bttSfida3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida3.getText().toString();
                String _numPassi=tvnumPassiSfida3.getText().toString();
                String _time=tvSfida3Time.getText().toString();

                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                intent.putExtra("PASSI",_numPassi);
                intent.putExtra("TIME",_time);

                startActivity(intent);
            }
        });

        bttSfida4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida4.getText().toString();
                String _numPassi=tvnumPassiSfida4.getText().toString();
                String _time=tvSfida4Time.getText().toString();

                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                intent.putExtra("PASSI",_numPassi);
                intent.putExtra("TIME",_time);

                startActivity(intent);
            }
        });

        bttSfida5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida5.getText().toString();
                String _numPassi=tvnumPassiSfida5.getText().toString();
                String _time=tvSfida5Time.getText().toString();

                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                intent.putExtra("PASSI",_numPassi);
                intent.putExtra("TIME",_time);

                startActivity(intent);
            }
        });

        bttSfidaPersonalizzata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("customChallenge");//mettere stringa nel file string
                startActivity(intent);
            }
        });

        bttBattito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("battito");//mettere stringa nel file string
                startActivity(intent);
            }
        });

        bttSfida1Storico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida1.getText().toString();
                Intent intent=new Intent("storico");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });

        bttSfida2Storico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida2.getText().toString();
                Intent intent=new Intent("storico");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });

        bttSfida3Storico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida3.getText().toString();
                Intent intent=new Intent("storico");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });

        bttSfida4Storico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida4.getText().toString();
                Intent intent=new Intent("storico");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });

        bttSfida5Storico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida5.getText().toString();
                Intent intent=new Intent("storico");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });

        Button buttonRequest1 = findViewById(R.id.requestButton1);
        buttonRequest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted this permission!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestActivityRecognitionPermission();
                }
            }
        });

        Button buttonRequest2 = findViewById(R.id.requestButton2);
        buttonRequest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted this permission!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestBodySensorPermission();
                }
            }
        });
    }

    private void requestBodySensorPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.BODY_SENSORS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.BODY_SENSORS}, HEART_SENSOR_PERMISSION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.BODY_SENSORS}, HEART_SENSOR_PERMISSION);
        }
    }

    private void requestActivityRecognitionPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACTIVITY_RECOGNITION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.ACTIVITY_RECOGNITION}, STEP_COUNTER_PERMISSION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACTIVITY_RECOGNITION}, STEP_COUNTER_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STEP_COUNTER_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == HEART_SENSOR_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(MyForegroundService.class.getName().equals(service.service.getClassName())) return true;
        }
        return false;
    }

    public void readFile(String _nomeSfida, TextView WriteResult){
        try {
            FileInputStream fileInputStream=openFileInput(_nomeSfida+".txt");
            InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();

            String lines;
            while ((lines=bufferedReader.readLine())!=null){
                stringBuffer.append(lines+"\n");
            }
            WriteResult.setText(stringBuffer.toString());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}