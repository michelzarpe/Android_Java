 package com.example.michel.adivinha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.michel.adivinha.Bean.Frase;

import java.util.Random;

 public class MainActivity extends AppCompatActivity {

    public Button btJogar;
    public TextView txtV;
    public Button btGerarFrase;
    public TextView fraseGerada;
    private Frase frase = new Frase();
    public Random numeroAleatorio = new Random();


    //sempre que inicializar chama esse metodo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       btGerarFrase = (Button) findViewById(R.id.geradorFrase);
       fraseGerada = (TextView) findViewById(R.id.fraseGerada);
       txtV = (TextView) findViewById(R.id.resultado);
       btGerarFrase.setOnClickListener(new View.OnClickListener() {
           @Override
             public void onClick(View v) {
               fraseGerada.setText(frase.getBanco_de_Frases().get(numeroAleatorio.nextInt(frase.getBanco_de_Frases().size())));
               txtV.setText(String.valueOf(numeroAleatorio.nextInt(10)));
            }
        });


    }
}
