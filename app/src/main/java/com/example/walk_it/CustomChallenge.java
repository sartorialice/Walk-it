package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomChallenge extends AppCompatActivity {
    private final String TAG = "CustomChallenge";
    private EditText etNumPassi = null;
    private EditText etTempoSfida = null;
    private Button bttConferma = null;
    private String regex = "\\d+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_challenge);

        etNumPassi = findViewById(R.id.etCustomPassi);
        etTempoSfida = findViewById(R.id.etCustomTempo);


        bttConferma = findViewById(R.id.bttConferma);

        bttConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String _passi = etNumPassi.getText().toString();
                    String _tempo = etTempoSfida.getText().toString();
                if(_passi.matches(regex) && _tempo.matches(regex)) {
                    Intent intent = new Intent("sfida");
                    intent.putExtra("NOME_SFIDA", "Sfida Personalizzata");
                    intent.putExtra("PASSI", _passi);
                    intent.putExtra("TIME", _tempo);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CustomChallenge.this, "Inserisci dati validi!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}