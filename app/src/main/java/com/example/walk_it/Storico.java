package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Storico extends AppCompatActivity {

    String _nomeSfida;
    TextView tvStorico=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);
        tvStorico=findViewById(R.id.tvStorico);
        Intent _intent=getIntent();
        _nomeSfida=_intent.getStringExtra("NOME_SFIDA");

        readFile(_nomeSfida,tvStorico);
    }

    public void readFile(String _nomeSfida, TextView WriteResult){
        try {
            FileInputStream fileInputStream=openFileInput(_nomeSfida+"storico.txt");
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