package com.example.dimeguard;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBase
{
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;

    public static void inicalizarFireBase(Context context){

        try {
            FirebaseApp.initializeApp(context);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
        } catch (Exception e){
            Toast.makeText(context, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
