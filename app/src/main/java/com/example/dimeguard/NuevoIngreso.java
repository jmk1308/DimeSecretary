package com.example.dimeguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class NuevoIngreso extends AppCompatActivity {
    private TextView txtFecha, txMontoIng;
    private Button btnVolverIng, btnNewIng, btnPickDate;
    private FirebaseFirestore mfirestore;
    private static final String BROKER_URL = "tcp://androidteststiqq.cloud.shiftr.io:1883";
    private static final String CLIENT_ID = "josemartinez";
    private String fechaSeleccionada;
    private MqttHandler mqttHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ingreso);

        mfirestore = FirebaseFirestore.getInstance();

        btnVolverIng = (Button) findViewById(R.id.btnVolverIng);
        txtFecha = (TextView) findViewById(R.id.txtFecha);
        btnPickDate = (Button) findViewById(R.id.BtnPickDate);
        txMontoIng = (TextView) findViewById(R.id.txtMontoIngreso);
        btnNewIng = (Button) findViewById(R.id.btnNewIng);

        btnVolverIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoIngreso.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnNewIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtén el texto del campo txMontoIngreso
                String montoIngresoStr = txMontoIng.getText().toString();
                String uid = UUID.randomUUID().toString();
                String tipo = "Ingreso";

                String montoGastoStr = txMontoIng.getText().toString();
                // Verifica si el campo no está vacío
                if (!montoGastoStr.isEmpty()) {
                    // Intenta convertir el texto a un número decimal
                    try {
                        double montoGasto = Double.parseDouble(montoGastoStr);

                        // Verifica si el monto ingresado es positivo
                        if (montoGasto > 0) {
                            // Aquí procesas el monto ingresado (puedes almacenarlo, enviarlo a otro lugar, etc.)
                            try {
                                if (montoGastoStr.isEmpty()){
                                    Toast.makeText(NuevoIngreso.this, "Ingrese un monto", Toast.LENGTH_SHORT).show();
                                } else if(fechaSeleccionada == null){
                                    Toast.makeText(NuevoIngreso.this, "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                                } else{
                                    postMonto(montoGasto, tipo, fechaSeleccionada);
                                }
                            } catch (Exception e){
                                Toast.makeText(NuevoIngreso.this, "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
                            }
                            // Ejemplo: muestra un mensaje de éxito
                            Toast.makeText(NuevoIngreso.this, "Monto gastado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // Muestra un mensaje de error si el monto es negativo
                            Toast.makeText(NuevoIngreso.this, "El monto no puede ser negativo", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Muestra un mensaje de error si no se puede convertir a número
                        Toast.makeText(NuevoIngreso.this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Muestra un mensaje de error si el campo está vacío
                    Toast.makeText(NuevoIngreso.this, "Ingrese un monto", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                String fechaText = day+"/"+month+"/"+year;
                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        NuevoIngreso.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                fechaSeleccionada = year + " - " + (monthOfYear + 1) + " - "+ dayOfMonth;
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });




    }

    private void postMonto(double montoIngresoStr, String tipo, String fecha) {
        Map<String, Object> map = new HashMap<>();
        map.put("monto", montoIngresoStr);
        map.put("tipo", tipo);
        map.put("fecha", fecha);
        mfirestore.collection("Ingresos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(NuevoIngreso.this, "Dato ingresado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NuevoIngreso.this, "Dato ingresado incorrectamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void publishMessage(String topic, String message){
        Toast.makeText(this, "Publishing message: " + message, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic,message);
    }
    private void subscribeToTopic(String topic){
        Toast.makeText(this, "Subscribing to topic "+ topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic);
    }
}