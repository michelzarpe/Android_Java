package com.example.usuario.sqllitemichel;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //tabela - updates - insert - delete - drop
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas(nome VARCHAR, idade INT(3) ) ");
           // bancoDados.execSQL("INSERT INTO pessoas (nome, idade) VALUES ('Michel', 29) ");
            // bancoDados.execSQL("INSERT INTO pessoas (nome, idade) VALUES ('Alessandra', 37) ");
            //select
            Cursor cursor = bancoDados.rawQuery("SELECT nome, idade FROM pessoas WHERE idade > 30", null);

            int indiceColunaNome = cursor.getColumnIndex("nome");
            int indiceColunaIdade = cursor.getColumnIndex("idade");

            cursor.moveToFirst();

            do  {
                Log.i("RESULTADO - nome: ", cursor.getString(indiceColunaNome));
                Log.i("RESULTADO - idade: ", cursor.getString(indiceColunaIdade));

            }while ( cursor.moveToNext());

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
