package com.example.michel.consultoriaatm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private ImageView imagemViewEmpresa;
    private ImageView imagemViewCliente;
    private ImageView imagemViewServico;
    private ImageView imagemViewContato;
    private EditText idInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagemViewEmpresa = (ImageView) findViewById(R.id.imagemViewEmpresa);
        imagemViewCliente = (ImageView) findViewById(R.id.imagemViewCliente);
        imagemViewServico = (ImageView) findViewById(R.id.imagemViewServico);
        imagemViewContato = (ImageView) findViewById(R.id.imagemViewContato);
        idInputText = (EditText) findViewById(R.id.idInputText);


        imagemViewEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EmpresaActivity.class);
                startActivity(intent);
            }
        });
        imagemViewCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ClienteActivity.class);
                intent.putExtra("idInputText",idInputText.getText().toString());
                startActivity(intent);
            }
        });
        imagemViewServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ServicoActivity.class));
            }
        });
        imagemViewContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ContatoActivity.class));
            }
        });




    }
}
