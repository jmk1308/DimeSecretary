package com.example.dimeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Map;

public class AnalisisMensual extends AppCompatActivity {
    private Button btnVolverAnal;
    private TextView totalGasto,totalIngreso,totalComida,totalTransporte,TotalEntretenimiento,totalCuentas,totalOtros;
    private static final String TAG = "FirestoreManager";
    private FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();

            /*public void consultarTotales() {
                consultarTotalGastos();
                consultarTotalIngresos();
                consultarTotalGastosCategoria("Comida");
                consultarTotalGastosCategoria("Transporte");
                consultarTotalGastosCategoria("Entretenimiento");
                consultarTotalGastosCategoria("Cuentas");
                consultarTotalGastosCategoria("Otros");
            }*/


        }

    private void consultarTotalGastos() {
        db.collection("gastos").document("totalGastos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            double totalGastos = documentSnapshot.getDouble("monto");
                            Log.d(TAG, "Total de Gastos: " + totalGastos);
                        } else {
                            Log.d(TAG, "El documento 'totalGastos' no existe");
                        }
                    }
                });
    }

    private void consultarTotalIngresos() {
        // Implementar lógica similar a consultarTotalGastos para ingresos
    }

    /*private void consultarTotalGastosCategoria(String categoria) {
        db.collection("gastos")
                .whereEqualTo("categoria", categoria)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            double totalGastosCategoria = 0;
                            for (Map.Entry<String, Object> entry : documentSnapshot.getData().entrySet()) {
                                // Suponiendo que la estructura del documento es { "monto": valor }
                                if (entry.getKey().equals("monto")) {
                                    totalGastosCategoria += Double.parseDouble(entry.getValue().toString());
                                }
                            }
                            Log.d(TAG, "Total de Gastos en " + categoria + ": " + totalGastosCategoria);
                        } else {
                            Log.d(TAG, "No hay gastos registrados en la categoría: " + categoria);
                        }
                    }
                });
        }
    }*/
}