package mx.edu.ittepic.tpdm_u3_practica1_delarosaguerrerogabriela;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ModAlu extends AppCompatActivity {
    Button eliminar, modificar, regresa;
    EditText nc_alu, nom_alu, ape_alu, carrera;
    DatabaseReference servicioRealtime;
    ListView listaAlu;
    Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        servicioRealtime = FirebaseDatabase.getInstance().getReference();
        nc_alu = findViewById(R.id.nc_alu);
        nom_alu = findViewById(R.id.nom_alu);
        ape_alu = findViewById(R.id.ape_alu);
        carrera = findViewById(R.id.carrera);
        listaAlu = findViewById(R.id.listaAlu);
        datos = getIntent().getExtras();

        modificar = findViewById(R.id.modifica);
        eliminar = findViewById(R.id.elimina);

        regresa = findViewById(R.id.regresa);
        nc_alu.setText(datos.get("nc").toString());
        nom_alu.setText(datos.get("nombre").toString());
        ape_alu.setText(datos.get("apellidos").toString());
        carrera.setText(datos.get("carrera").toString());
        regresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModAlu.this, LisAlu.class);
                startActivity(i);
                finish();
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("nombre",nom_alu.getText().toString());
                data.put("apellidos",ape_alu.getText().toString());
                data.put("nc",nc_alu.getText().toString());
                data.put("carrera",carrera.getText().toString());
                servicioRealtime.child("alumnos").child(datos.get("id").toString()).updateChildren(data);
                Toast.makeText(ModAlu.this,"Se actualizo correctamente",Toast.LENGTH_LONG).show();
                Intent i =new Intent(ModAlu.this,LisAlu.class);
                startActivity(i);
                finish();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicioRealtime.child("alumnos").child(datos.get("id").toString()).removeValue();
                Toast.makeText(ModAlu.this, "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ModAlu.this,LisAlu.class);
                startActivity(i);
            }
        });



    }
}
