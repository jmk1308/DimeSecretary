package com.example.dimeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button btnIngreso,btnGasto,btnRegGas,btnAnalisis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngreso = (Button) findViewById(R.id.btnIngreso);
        btnGasto = (Button) findViewById(R.id.btnGasto);
        btnAnalisis = (Button) findViewById(R.id.btnAnalisis);
        btnRegGas = (Button) findViewById(R.id.btnRegGas);




        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NuevoIngreso.class);
                startActivity(intent);
            }
        });
        btnGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NuevoGasto.class);
                startActivity(intent);
            }
        });
        btnAnalisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AnalisisMensual.class);
                startActivity(intent);
            }
        });
        btnRegGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrosGastosBBDD.class);
                startActivity(intent);
            }
        });
    }

}