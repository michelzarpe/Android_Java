package com.example.michel.idadededog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private EditText editTextGasolina;
    private EditText editTextAlcool;
    private Button buttonVerificador;
    private TextView textViewResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipowcombustivel);
        editTextGasolina = (EditText) findViewById(R.id.editTextGasolina);
        editTextAlcool = (EditText) findViewById(R.id.editTextalcool);
        buttonVerificador = (Button) findViewById(R.id.buttonVerificador);
        textViewResultado = (TextView) findViewById(R.id.textViewResultado);

        buttonVerificador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Double.parseDouble(editTextAlcool.getText().toString()))/(Double.parseDouble(editTextGasolina.getText().toString())))>0.7){
                    textViewResultado.setText("Melhor usar a gasolina"+(Double.parseDouble(editTextAlcool.getText().toString()))/(Double.parseDouble(editTextGasolina.getText().toString())));
                }else{
                    textViewResultado.setText("Melhor usar o alcoolr"+(Double.parseDouble(editTextAlcool.getText().toString()))/(Double.parseDouble(editTextGasolina.getText().toString())));
                }
            }
        });

    }

}


        /*
    private EditText editTextIdade;
    private Button buttonConverter;
    private TextView resultadoConversao;
    Aplicativo do Catioro
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIdade = (EditText) findViewById(R.id.editTextIdade);
        buttonConverter = (Button) findViewById(R.id.buttonConverter);
        resultadoConversao = (TextView) findViewById(R.id.resultadoConversao);

        buttonConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!editTextIdade.getText().toString().isEmpty()){
                   resultadoConversao.setText("Idade de Catioro: "+String.valueOf((Integer.parseInt(editTextIdade.getText().toString()))*7));
                }else{
                   resultadoConversao.setText("Digite o valor de vida para seu dog");
                }

            }
        });

    }
*/

