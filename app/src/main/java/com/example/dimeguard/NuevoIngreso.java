package com.example.dimeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NuevoIngreso extends AppCompatActivity {
    private Button btnVolverIng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ingreso);

        btnVolverIng = (Button) findViewById(R.id.btnVolverIng);

        btnVolverIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoIngreso.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}