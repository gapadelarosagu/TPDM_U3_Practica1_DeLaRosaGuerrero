package mx.edu.ittepic.tpdm_u3_practica1_delarosaguerrerogabriela;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsDoc extends AppCompatActivity {
    EditText rfc, nom_doc,ape_doc,tel;
    Button insertar,regresar;
    DatabaseReference servicioRealtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        rfc = findViewById(R.id.rfc);
        nom_doc = findViewById(R.id.nom_doc);
        ape_doc = findViewById(R.id.ape_doc);
        tel = findViewById(R.id.tel);
        regresar=findViewById(R.id.btnregresardoc);
        insertar = findViewById(R.id.btninsertaD);

        servicioRealtime = FirebaseDatabase.getInstance().getReference();

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDoc();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsDoc.this,LisDoc.class));
                finish();
            }
        });
    }
    private void insertarDoc(){
        Docentes docentes = new Docentes(rfc.getText().toString(),nom_doc.getText().toString(),ape_doc.getText().toString(),tel.getText().toString());

        servicioRealtime.child("docentes").push().setValue(docentes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InsDoc.this,"EXITO",Toast.LENGTH_SHORT).show();
                        rfc.setText("");
                        nom_doc.setText("");
                        ape_doc.setText("");
                        tel.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InsDoc.this,"ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
