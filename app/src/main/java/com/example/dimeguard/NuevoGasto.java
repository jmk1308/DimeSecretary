package com.example.dimeguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

public class NuevoGasto extends AppCompatActivity {
    private String categoria;
    private Button btnVolverGas, btnNewGas, BtnPickDate;
    private EditText txMontoGasto;
    private Spinner spCategory;
    private TextView txtFecha2;
    private String fechaSeleccionada;
    private FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gasto);
        txMontoGasto = (EditText) findViewById(R.id.txMontoGasto);
        btnVolverGas = (Button) findViewById(R.id.btnVolverGas);
        btnNewGas = (Button) findViewById(R.id.btnNewGas);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        BtnPickDate = (Button) findViewById(R.id.btnPickDate);
        txtFecha2 = (TextView) findViewById(R.id.txtFecha2);

        mfirestore = FirebaseFirestore.getInstance();
        
        //boton para volver al menu de 4 opciones
        btnVolverGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoGasto.this, MainActivity.class);
                startActivity(intent);
            }
        });

        BtnPickDate.setOnClickListener(new View.OnClickListener() {
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
                        NuevoGasto.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                fechaSeleccionada = year + " - " + (monthOfYear + 1) + " - " + dayOfMonth;
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


        // Crear un ArrayAdapter utilizando un array de strings y un diseño simple para el spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoria, android.R.layout.simple_spinner_item);
        // Especificar el diseño que se utilizará cuando se muestren las opciones (dropdown layout)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplicar el adaptador al spinner
        spCategory.setAdapter(adapter);
        // Establecer un listener para el spinner
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // El elemento seleccionado se encuentra en la posición "position"
                categoria = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se ha seleccionado nada
                Toast.makeText(NuevoGasto.this, "Seleccione una categoria", Toast.LENGTH_SHORT).show();
            }
        });

        btnNewGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String montoGastoStr = txMontoGasto.getText().toString();
                // Verifica si el campo no está vacío
                if (!montoGastoStr.isEmpty()) {
                    // Intenta convertir el texto a un número decimal
                    try {
                        double montoGasto = Double.parseDouble(montoGastoStr);
                        String uid = UUID.randomUUID().toString();
                        String tipo = "Gasto";

                        // Verifica si el monto ingresado es positivo
                        if (montoGasto > 0) {
                            // Aquí procesas el monto ingresado (puedes almacenarlo, enviarlo a otro lugar, etc.)
                            try {
                                if (montoGastoStr.isEmpty()){
                                    Toast.makeText(NuevoGasto.this, "Ingrese un monto", Toast.LENGTH_SHORT).show();
                                } else if(fechaSeleccionada == null){
                                    Toast.makeText(NuevoGasto.this, "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                                } else{
                                    postMontoG(montoGasto, tipo, fechaSeleccionada,categoria);
                                }
                            } catch (Exception e){
                                Toast.makeText(NuevoGasto.this, "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
                            }
                            // Ejemplo: muestra un mensaje de éxito
                            Toast.makeText(NuevoGasto.this, "Monto gastado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // Muestra un mensaje de error si el monto es negativo
                            Toast.makeText(NuevoGasto.this, "El monto no puede ser negativo", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Muestra un mensaje de error si no se puede convertir a número
                        Toast.makeText(NuevoGasto.this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Muestra un mensaje de error si el campo está vacío
                    Toast.makeText(NuevoGasto.this, "Ingrese un monto", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void postMontoG(double montoGasto, String tipo, String fecha, String categoria) {
        Map<String, Object> map = new HashMap<>();
        map.put("monto", montoGasto);
        map.put("tipo", tipo);
        map.put("fecha", fecha);
        map.put("categoría", categoria);
        mfirestore.collection("Gastos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(NuevoGasto.this, "Dato ingresado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NuevoGasto.this, "Dato ingresado incorrectamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}