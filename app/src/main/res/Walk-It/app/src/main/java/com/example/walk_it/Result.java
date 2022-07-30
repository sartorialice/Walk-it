package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private TextView tvResultSfida= null;
    private Button bttReturnHome = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResultSfida = findViewById(R.id.tvResultSfida);
        bttReturnHome = findViewById(R.id.bttReturnHome);

        Intent _intent=getIntent();
        String _resultSfida=_intent.getStringExtra("RESULT");

        tvResultSfida.setText(_resultSfida);

        bttReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent("return");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire


                startActivity(intent);
            }
        });
    }
}