package com.example.dimeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NuevoGasto extends AppCompatActivity {
    private Button btnVolverGas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gasto);

        btnVolverGas = (Button) findViewById(R.id.btnVolverGas);
        btnVolverGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoGasto.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}