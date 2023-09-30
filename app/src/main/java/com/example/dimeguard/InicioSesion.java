package com.example.dimeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicioSesion extends AppCompatActivity {
    private Button btnSesion,btnCrearUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnSesion = (Button) findViewById(R.id.btnSesion);
        btnCrearUser = (Button) findViewById(R.id.btnCrearUser);

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioSesion.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCrearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioSesion.this, CrearUsuario.class);
                startActivity(intent);
            }
        });
    }
}