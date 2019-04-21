package mx.edu.ittepic.tpdm_u3_practica1_delarosaguerrerogabriela;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {
    Button alu, doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        alu = findViewById(R.id.button);
        doc = findViewById(R.id.button2);

        alu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Principal.this,LisAlu.class);
                startActivity(i);
                finish();
            }
        });
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Principal.this,LisDoc.class);
                startActivity(i);
                finish();
            }
        });
    }
}
