package mx.edu.ittepic.tpdm_u3_practica1_delarosaguerrerogabriela;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ModDoc extends AppCompatActivity {
    Button eliminar, modificar, regresa;
    EditText rfc, nom_doc,ape_doc,tel;
    DatabaseReference servicioRealtime;
    Bundle datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        servicioRealtime = FirebaseDatabase.getInstance().getReference();
        rfc = findViewById(R.id.rfc);
        nom_doc = findViewById(R.id.nom_doc);
        ape_doc = findViewById(R.id.ape_doc);
        tel = findViewById(R.id.telefono);
        datos=getIntent().getExtras();
        modificar = findViewById(R.id.modifica);
        eliminar = findViewById(R.id.elimina);
        regresa = findViewById(R.id.regresa);
        rfc.setText(datos.get("rfc").toString());
        nom_doc.setText(datos.get("nombre").toString());
        ape_doc.setText(datos.get("apellido").toString());
        tel.setText(datos.get("tel").toString());
        regresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModDoc.this, LisDoc.class);
                startActivity(i);
                finish();
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("nombre",nom_doc.getText().toString());
                data.put("apellido",ape_doc.getText().toString());
                data.put("rfc",rfc.getText().toString());
                data.put("telefono",tel.getText().toString());
                servicioRealtime.child("docentes").child(datos.get("id").toString()).updateChildren(data);
                Toast.makeText(ModDoc.this,"Se actualizo correctamente",Toast.LENGTH_LONG).show();
                Intent i =new Intent(ModDoc.this,LisDoc.class);
                startActivity(i);
                finish();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicioRealtime.child("docentes").child(datos.get("id").toString()).removeValue();
                Toast.makeText(ModDoc.this, "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ModDoc.this,LisDoc.class);
                startActivity(i);
            }
        });


    }
}
