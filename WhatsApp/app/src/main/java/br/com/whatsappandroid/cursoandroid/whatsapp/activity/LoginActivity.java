package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.controller.Permissao;
import br.com.whatsappandroid.cursoandroid.whatsapp.controller.Preferencias;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextDDI;
    private EditText editTextDDD;
    private EditText editTextTelefone;
    private Button buttonCatastrar;
    private Preferencias preferencias;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*validacao em android*/
        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextDDI = (EditText) findViewById(R.id.editTextDDI);
        editTextDDD = (EditText) findViewById(R.id.editTextDDD);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        buttonCatastrar = (Button) findViewById(R.id.buttonCatastrar);
        editTextDDI.addTextChangedListener(new MaskTextWatcher(editTextDDI, new SimpleMaskFormatter("NN")));
        editTextDDD.addTextChangedListener(new MaskTextWatcher(editTextDDD, new SimpleMaskFormatter("NN")));
        editTextTelefone.addTextChangedListener(new MaskTextWatcher(editTextTelefone, new SimpleMaskFormatter("N NNNN-NNNN")));

        buttonCatastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = editTextNome.getText().toString();
                String telefone = telefoneSemFormatacao(editTextDDI.getText().toString(), editTextDDD.getText().toString(), editTextTelefone.getText().toString());
                String tokeString = String.valueOf(token());
                salvarSharedPreferences(LoginActivity.this, nomeUsuario, telefone, tokeString);
                HashMap<String, String> usuario = recuperandoTokenSalvo();

                if (envioSMS("+" + telefone, "Código de confirmação: " + tokeString)) {
                    Intent intent = new Intent(LoginActivity.this,ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Error ao enviar Código",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private String telefoneSemFormatacao(String ddi, String ddd, String telefone) {
        String telefoneCompleto = ddi + ddd + telefone;
        String telefoneSemformatacao = telefoneCompleto.replace(" ", "");
        telefoneSemformatacao = telefoneSemformatacao.replace("+", "");
        telefoneSemformatacao = telefoneSemformatacao.replace("-", "");
        return telefoneSemformatacao;
    }

    private int token() {
        /*trocar futuramente, celular solicita o token para o servidor, servidor envia por uma mensagem de sms e tu inseri no aplicativo e servidor confirma*/
        Random random = new Random();
        return random.nextInt(9999) + 1000;
    }


    /*salvando token com SharedPreferences*/
    private void salvarSharedPreferences(Context context, String nome, String telefone, String token) {
        preferencias = new Preferencias(context);
        preferencias.salvarUsuarioPreferencia(nome, telefone, token);
    }

    private HashMap<String, String> recuperandoTokenSalvo() {
        return preferencias.getDadosUsuario();
    }

    private boolean envioSMS(String telefone, String mensagem) {
        try {
            /*recupera a instancia */
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*sobre escrevendo metodo original e colocando os parametros pra eles*/
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*percorrer se tem alguma permissao negada*/
        /*se um deles tiver valor negativo PackageManager.PERMISSION_DENIED*/
        for (int resultado : grantResults) {
            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Deve aceitar as permissões");
        builder.setPositiveButton("COMFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
