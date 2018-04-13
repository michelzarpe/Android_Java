package com.example.usuario.listatarefas;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button bottaoID;
    private EditText textoID;
    private ListView listaTarefas;
    private SQLiteDatabase dataBase;
    private Cursor cursor;
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottaoID = findViewById(R.id.botaoID);
        textoID = findViewById(R.id.textoID);
        listaTarefas = findViewById(R.id.listViewID);


        try{
            criarBanco();
            listaTarefas();
        }catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        /*achao do botao*/
        bottaoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    inserir(textoID.getText().toString());
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        /*achao do listener item*/
        listaTarefas.setLongClickable(true);
        listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excluir(ids.get(position));
                return true;
            }
        });


    }

    private void listaTarefas(){
        try{
            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_expandable_list_item_2,
                    android.R.id.text2,
                    itens);
            listaTarefas.setAdapter(itensAdaptador);
            cursor = cursorTarefas();
            cursor.moveToFirst();
            do  {
                itens.add(cursor.getString(cursor.getColumnIndex("nome_tarefa")));
                ids.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            }while ( cursor.moveToNext());
        }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void criarBanco(){
        try{
        dataBase = openOrCreateDatabase("tarefasBD",MODE_PRIVATE,null);
        //dataBase.execSQL("Drop Table tarefa");
        dataBase.execSQL("CREATE TABLE IF  NOT EXISTS tarefa (" +
                "id INTEGER primary key AUTOINCREMENT," +
                "nome_tarefa varchar)");
        }catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void inserir(String tarefa){
        try{
            if(!tarefa.isEmpty()) {
                dataBase.execSQL("INSERT INTO tarefa (nome_tarefa) VALUES ('" + tarefa + "')");
                listaTarefas();
                textoID.setText("");
                Toast.makeText(MainActivity.this,"Inserindo...",Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(MainActivity.this,"Preencher o Campo",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
             Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private Cursor cursorTarefas() {
        return dataBase.rawQuery("select * from tarefa order by id desc",null);
    }

    private void excluir(Integer id){
        try{
            dataBase.execSQL("delete from tarefa where id ="+id);
            listaTarefas();
            Toast.makeText(MainActivity.this,"Removida...",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
