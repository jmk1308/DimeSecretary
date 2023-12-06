package com.example.dimeguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InicioSesion extends AppCompatActivity {
    private Button btnSesion,btnCrearUser;
    private TextView emailUsertxt2, passwordTxt, textView20;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        emailUsertxt2 = (TextView) findViewById(R.id.emailUserTxt2);
        passwordTxt = (TextView) findViewById(R.id.passwordTxt);
        btnSesion = (Button) findViewById(R.id.btnSesion);
        btnCrearUser = (Button) findViewById(R.id.btnCrearUser);
        textView20 = (TextView) findViewById(R.id.textView20);

        mAuth = FirebaseAuth.getInstance();

        btnSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // Obtener el usuario actual
                        String emailIngresado = emailUsertxt2.getText().toString();
                        String contrasenaIngresada = passwordTxt.getText().toString();
                        loginUser(emailIngresado,contrasenaIngresada);

                    } catch (Exception e){
                        textView20.setText(e.getMessage());
                    }

                    // Resto del código...

                }
            });



        //manda a la pestaña de creacion de usuarios
        btnCrearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioSesion.this, CrearUsuario.class);
                startActivity(intent);
            }
        });
    }
    private void loginUser(String emailUsuario, String passwordUser){
        try {
            mAuth.signInWithEmailAndPassword(emailUsuario,passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        finish();
                        Intent intent = new Intent(InicioSesion.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(InicioSesion.this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InicioSesion.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            textView20.setText(e.getMessage());
        }
        mAuth.signInWithEmailAndPassword(emailUsuario,passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(InicioSesion.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(InicioSesion.this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InicioSesion.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}