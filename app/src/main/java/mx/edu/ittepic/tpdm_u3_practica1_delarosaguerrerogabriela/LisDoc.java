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
import java.util.List;

public class LisDoc extends AppCompatActivity {
    Button regresa;
    ListView listaDoc;
    DatabaseReference servicioRealtime;
    List<Docentes> datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        listaDoc = findViewById(R.id.listaDoc);
        regresa = findViewById(R.id.regresa);
        servicioRealtime = FirebaseDatabase.getInstance().getReference();

        regresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LisDoc.this,Principal.class);
                startActivity(i);
                finish();
            }
        });

        servicioRealtime.child("docentes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datos = new ArrayList<>();

                if(dataSnapshot.getChildrenCount()<=0){
                    Toast.makeText(LisDoc.this, "No hay docentes registrados", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(final DataSnapshot snap : dataSnapshot.getChildren()){
                    servicioRealtime.child("docentes").child(snap.getKey()).addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Docentes u = dataSnapshot.getValue(Docentes.class);

                                    if(u!=null){
                                        u.setId(snap.getKey());
                                        datos.add(u);
                                    }
                                    cargaDatos();
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

        listaDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alerta=new AlertDialog.Builder(LisDoc.this);
                alerta.setTitle("Alerta")
                        .setMessage("¿Desea modificar/eliminar el docente seleccionado?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(LisDoc.this, ModDoc.class);
                                i.putExtra("id",datos.get(position).getId());
                                i.putExtra("nombre",datos.get(position).getNombre());
                                i.putExtra("apellido",datos.get(position).getApellido());
                                i.putExtra("rfc",datos.get(position).getRfc());
                                i.putExtra("tel",datos.get(position).getTelefono());
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("NO",null).show();
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
                Intent i = new Intent(LisDoc.this,InsDoc.class);
                startActivity(i);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void cargaDatos(){
        if (datos.size()==0) return;
        String nombres[] = new String[datos.size()];

        for(int i = 0; i<nombres.length; i++){
            Docentes u = datos.get(i);
            nombres[i] = u.nombre;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listaDoc.setAdapter(adapter);
    }
}
