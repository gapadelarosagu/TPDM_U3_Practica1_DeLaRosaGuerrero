package mx.edu.ittepic.tpdm_u3_practica1_delarosaguerrerogabriela;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LisAlu extends AppCompatActivity {
    Button regresa;
    ListView listaAlu;
    DatabaseReference servicioRealtime;
    List<Alumnos> datosAlu;
    List<Map> tecnologico;
    ArrayAdapter<String> adaptador;
    List lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        listaAlu = findViewById(R.id.listaAlu);
        regresa = findViewById(R.id.regresa);

        tecnologico = new ArrayList<>();
        lista = new ArrayList();

        servicioRealtime = FirebaseDatabase.getInstance().getReference();
        adaptador = new ArrayAdapter<>(LisAlu.this, android.R.layout.simple_list_item_1, lista);

        regresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LisAlu.this, Principal.class);
                startActivity(i);
                finish();
            }
        });


        servicioRealtime.child("alumnos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datosAlu = new ArrayList<>();

                if (dataSnapshot.getChildrenCount() <= 0) {
                    Toast.makeText(LisAlu.this, "No hay alumnos registrados", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (final DataSnapshot snap : dataSnapshot.getChildren()) {
                    servicioRealtime.child("alumnos").child(snap.getKey()).addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Alumnos d = dataSnapshot.getValue(Alumnos.class);

                                    if (d != null) {
                                        d.setId(snap.getKey());
                                        datosAlu.add(d);
                                    }
                                    cargaDatosA();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            }
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        listaAlu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(LisAlu.this);
                alerta.setTitle("Alerta")
                        .setMessage("¿Desea modificar/eliminar el alumno seleccionado?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(LisAlu.this, ModAlu.class);
                                i.putExtra("id",datosAlu.get(position).getId());
                                i.putExtra("nombre",datosAlu.get(position).getNombre());
                                i.putExtra("apellidos",datosAlu.get(position).getApellidos());
                                i.putExtra("nc",datosAlu.get(position).getNc());
                                i.putExtra("carrera",datosAlu.get(position).getCarrera());
                                startActivity(i);
                                finish();

                            }
                        })
                        .setNegativeButton("NO", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insertar:
                Intent insertar = new Intent(this,InsAlu.class);
                startActivity(insertar);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cargaDatosA(){
        if (datosAlu.size()==0) return;
        String nombres[] = new String[datosAlu.size()];
        for(int i = 0; i<nombres.length; i++){
            Alumnos u = datosAlu.get(i);
            nombres[i] = u.nombre;

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listaAlu.setAdapter(adapter);
    }





    }
