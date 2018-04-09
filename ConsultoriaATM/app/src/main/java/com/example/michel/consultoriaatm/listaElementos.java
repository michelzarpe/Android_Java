package com.example.michel.consultoriaatm;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class listaElementos extends Activity {

    private String [] elementos = {"item numero 1","item numero 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_elementos);
        final ListView listaItens = (ListView) findViewById(R.id.lista);
        ArrayAdapter<String> listaAdaptada  =  new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                elementos
        );
        listaItens.setAdapter(listaAdaptada);
        listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
             String valorClicado = listaItens.getItemAtPosition(position).toString();
             Toast.makeText(getApplicationContext(),"Clicou em "+valorClicado,Toast.LENGTH_LONG).show();
         }
        });
    }
}
