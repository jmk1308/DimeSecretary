package com.example.dimeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnalisisMensual extends AppCompatActivity {
    private Button btnVolverAnal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis_mensual);
        btnVolverAnal = (Button) findViewById(R.id.btnVolverAnal);

        btnVolverAnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalisisMensual.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}