package com.alberto.ticketqr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.alberto.ticketqr.serverConnections.ConnectionToSQL;

public class Settings extends AppCompatActivity {

    Button button;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        inputText = findViewById(R.id.inputText);

        button = findViewById(R.id.submit);
        button.setOnClickListener(v -> {
            //leggi e chiama serverConnections
            ConnectionToSQL connectionToSQL = new ConnectionToSQL();
            boolean state = connectionToSQL.connect(inputText.getText().toString());
            if (state){
                //assumiamo il server sia corretto, quindi procediamo,
                Intent intent = new Intent(Settings.this, MainActivity.class);
                intent.putExtra("FROM", "SETTINGS");
                intent.putExtra("STATE", true); //MainActivity può controllare che la connessione sia funzionante
                startActivity(intent);
                finish(); //non si può tornare a questa activity, si chiude l'app
            } else{
                Toast.makeText(this, "L'indirizzo IP inserito è errato, riprovare", Toast.LENGTH_SHORT).show();
            }
        });
    }
}