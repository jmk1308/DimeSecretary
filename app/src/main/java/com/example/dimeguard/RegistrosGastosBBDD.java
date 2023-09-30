package com.example.dimeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrosGastosBBDD extends AppCompatActivity {
    private Button btnVolverRegGas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros_gastos_bbdd);
        btnVolverRegGas = (Button) findViewById(R.id.btnVolverRegGas);

        btnVolverRegGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrosGastosBBDD.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}