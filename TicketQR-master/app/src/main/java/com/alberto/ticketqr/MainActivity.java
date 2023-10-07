package com.alberto.ticketqr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {
    private final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner codeScanner;
    private boolean isConnect = false;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---SETTINGS---
        button = findViewById(R.id.openSettings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        setupPermission();
        codeScanner();
    }

    //---SCANNING---
    private void codeScanner(){
        //TextView textView = findViewById(R.id.tv_textView);

        codeScanner = new CodeScanner(this, findViewById(R.id.scanner_view));

        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);

        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                Intent intent = new Intent(MainActivity.this, Response.class);
                intent.putExtra("CODE", result.getText());
                startActivity(intent);

                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result.getText());
                    }
                });

                 */
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, thrown.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //---OVERRIDE---
    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getStringExtra("FROM") != null) {

            switch (getIntent().getStringExtra("FROM")){
                case "SETTINGS": isConnect = getIntent().getBooleanExtra("STATE", false);
                    break;
                case "RESPONSE": isConnect = true;
                    break;
                default:
            }
        }
        
        if (isConnect){
            codeScanner.startPreview();
        }else {
            Toast.makeText(this, "Non connesso al server, imposta l'indirizzo IP corretto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    //---PERMISSION---
    private void setupPermission(){
        int  permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
    }

    private void makeRequest() {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {

            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Sono necessari i permissi per usare la fotocamera", Toast.LENGTH_SHORT).show();

            } else {
                //successful
                Toast.makeText(this, "Grazie", Toast.LENGTH_SHORT).show();
            }
        }
    }
}