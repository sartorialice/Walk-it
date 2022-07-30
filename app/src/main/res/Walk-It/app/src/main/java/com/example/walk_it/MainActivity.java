package com.example.walk_it;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int ACTIVITY_REQUEST_CODE = 1;
    private int STEP_COUNTER_PERMISSION = 1;

    private Button bttSfida1=null,bttSfida2=null,bttSfida3=null,bttSfidaPersonalizzata=null;
    private TextView tvSfida1=null, tvSfida2=null, tvSfida3=null, tvnumPassiSfida1=null, tvSfida1Time=null , tvnumPassiSfida2=null, tvSfida2Time=null, tvnumPassiSfida3=null, tvSfida3Time=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bttSfida1=findViewById(R.id.bttSfida1);
        bttSfida2=findViewById(R.id.bttSfida2);
        bttSfida3=findViewById(R.id.bttSfida3);
        bttSfidaPersonalizzata=findViewById(R.id.bttSfidaPersonalizzata);
        tvSfida1=findViewById(R.id.tvSfida1);
        tvnumPassiSfida1=findViewById((R.id.tvSfida1NumPassi));
        tvSfida1Time=findViewById(R.id.tvSfida1Time);
        tvSfida2=findViewById(R.id.tvSfida2);
        tvnumPassiSfida2=findViewById((R.id.tvSfida2NumPassi));
        tvSfida2Time=findViewById(R.id.tvSfida2Time);
        tvSfida3=findViewById(R.id.tvSfida3);
        tvnumPassiSfida3=findViewById((R.id.tvSfida3NumPassi));
        tvSfida3Time=findViewById(R.id.tvSfida3Time);
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

        Button buttonRequest = findViewById(R.id.button);
        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted this permission!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestStoragePermission();
                }
            }
        });
    }
    private void requestStoragePermission() {
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
    }
}