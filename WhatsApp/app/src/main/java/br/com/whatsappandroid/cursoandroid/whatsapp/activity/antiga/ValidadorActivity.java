package br.com.whatsappandroid.cursoandroid.whatsapp.activity.antiga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.controller.Preferencias;

public class ValidadorActivity extends AppCompatActivity {

    private EditText editTextValidador;
    private Button buttonValidar;
    private Preferencias preferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        editTextValidador = (EditText) findViewById(R.id.editTextValidador);
        buttonValidar = (Button) findViewById(R.id.buttonValidar);
        editTextValidador.addTextChangedListener(new MaskTextWatcher(editTextValidador, new SimpleMaskFormatter("NNNN")));
        buttonValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Recuperar os dados*/
                preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                String tokenSalvo = usuario.get(Preferencias.TOKE_USUARIO);
                String tokenDigitado = editTextValidador.getText().toString();

                if (tokenDigitado.equals(tokenSalvo)) {
                    Toast.makeText(ValidadorActivity.this,"Token validado",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ValidadorActivity.this,"Token não validado",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
