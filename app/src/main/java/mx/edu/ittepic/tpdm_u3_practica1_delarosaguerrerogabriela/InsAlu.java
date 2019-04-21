package mx.edu.ittepic.tpdm_u3_practica1_delarosaguerrerogabriela;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InsAlu extends AppCompatActivity {
    EditText nc_alu, nom_alu,ape_alu,carrera;
    Button insertar,regresar;
    DatabaseReference servicioRealtime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nc_alu = findViewById(R.id.nc_alu);
        nom_alu = findViewById(R.id.nom_alu);
        ape_alu = findViewById(R.id.ape_alu);
        carrera = findViewById(R.id.carrera);
        regresar=findViewById(R.id.btnregresaralu);

        insertar = findViewById(R.id.btninserta);

        servicioRealtime = FirebaseDatabase.getInstance().getReference();

        /*consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarAlu();
            }
        });*/

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarAlu();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsAlu.this,LisAlu.class));
                finish();
            }
        });


    }


    private void insertarAlu(){
        Alumnos alumnos = new Alumnos(nc_alu.getText().toString(),nom_alu.getText().toString(),ape_alu.getText().toString(),carrera.getText().toString());

        servicioRealtime.child("alumnos").push().setValue(alumnos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InsAlu.this,"EXITO",Toast.LENGTH_SHORT).show();
                        nc_alu.setText("");
                        nom_alu.setText("");
                        ape_alu.setText("");
                        carrera.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InsAlu.this,"ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
    }









}
