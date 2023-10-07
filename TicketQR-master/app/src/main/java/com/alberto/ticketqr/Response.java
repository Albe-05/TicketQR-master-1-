package com.alberto.ticketqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alberto.ticketqr.serverConnections.Check;

public class Response extends AppCompatActivity {

    //---UI---
    TextView status;
    TextView code;
    RelativeLayout relativeLayout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        status = findViewById(R.id.status);
        code = findViewById(R.id.code);
        relativeLayout = findViewById(R.id.rlResponse);
        //relativeLayout.setBackgroundColor(Color.BLUE);

        button = findViewById(R.id.back);
        button.setOnClickListener(v -> back());
    }

    //---CHECK---
    @Override
    protected void onResume() {
        super.onResume();

        String codeStr = getIntent().getStringExtra("CODE");

        if (codeStr != null) {
            code.setText(codeStr);

            int checkStatus = new Check().checkTicket(codeStr);

            String s = "?";
            //cambia colore e testo
            switch (checkStatus){
                case 0: relativeLayout.setBackgroundColor(Color.parseColor("#D1FF9519")); //arancione - ancora da vendere
                    s = "Ancora da vendere";
                    break;
                case 1: relativeLayout.setBackgroundColor(Color.parseColor("#CE1EE826")); //verde - venduto
                    s = "Venduto";
                    break;
                case 2: relativeLayout.setBackgroundColor(Color.parseColor("#C6FF2121")); //rosso - già entrato
                    s = "Già entrato";
                    break;
            }

            status.setText(s);

        } else {
            //se è null il contenuto del codice torniamo indietro con un Toast
            readingError();
        }
    }

    private void readingError(){
        Toast.makeText(this, "C'è stato qualche problema nella lettura del Qr Code", Toast.LENGTH_SHORT).show();
        back();
    }

    //torna alla MainActivity
    private void back(){
        Intent intent = new Intent(Response.this, MainActivity.class);
        intent.putExtra("FROM", "RESPONSE");
        startActivity(intent);
        finish();
    }
}