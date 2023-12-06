package com.example.dimeguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrearUsuario extends AppCompatActivity {
    private Button btnVolvInicio, btnCraUser;
    private TextView nombUserTxt, emailUserTxt, passwordUserTxt, passwordUserRepeatTxt,textView21;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        btnVolvInicio = (Button) findViewById(R.id.btnVolvInicio);
        btnCraUser = (Button) findViewById(R.id.btnCreaUser);
        nombUserTxt = (TextView) findViewById(R.id.nombUserTxt);
        emailUserTxt = (TextView) findViewById(R.id.emailUserTxt);
        passwordUserTxt = (TextView) findViewById(R.id.passwordUserTxt);
        passwordUserRepeatTxt = (TextView) findViewById(R.id.passwordUserRepeatTxt);
        textView21 = (TextView) findViewById(R.id.textView21);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnVolvInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearUsuario.this, InicioSesion.class);
                startActivity(intent);
            }
        });

        btnCraUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = nombUserTxt.getText().toString();
                String emailUsuario = emailUserTxt.getText().toString();
                String contrasenaUsuario = passwordUserTxt.getText().toString();
                String contrasenaRepetirUsuario = passwordUserRepeatTxt.getText().toString();

                if (nombreUsuario.isEmpty() || emailUsuario.isEmpty() || contrasenaUsuario.isEmpty() || contrasenaRepetirUsuario.isEmpty()){
                    Toast.makeText(CrearUsuario.this, "Por favor rellena los recuadros faltantes", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        registerUser(nombreUsuario,emailUsuario,contrasenaUsuario);
                    }catch (Exception e){
                        textView21.setText(e.getMessage());
                    }

                }


            }
        });

    }
    private boolean contieneSoloLetras(String cadena) {
        return cadena.matches("[a-zA-Z]+");
    }

    private void registerUser(String nameUser, String emailUser, String passUser){
        mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id",id);
                map.put("name",nombUserTxt);
                map.put("email",emailUserTxt);
                map.put("password", passwordUserTxt);

                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(CrearUsuario.this, InicioSesion.class));
                        Toast.makeText(CrearUsuario.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearUsuario.this, "Error al guardar Usuario", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CrearUsuario.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

}