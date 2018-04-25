package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Usuario;

public class Login2Activity extends AppCompatActivity {
    private EditText edit_loginEmail;
    private EditText edit_loginSenha;
    private Button button_loginLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        /* caso esteja logado ja entra direto para pagina principal*/
        verificarUsuarioLogado();

        edit_loginEmail = (EditText) findViewById(R.id.edit_loginEmail);
        edit_loginSenha = (EditText) findViewById(R.id.edit_loginSenha);
        button_loginLogar = (Button) findViewById(R.id.button_loginLogar);

        button_loginLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(edit_loginEmail.getText().toString());
                usuario.setSenha(edit_loginSenha.getText().toString());
                validarLogin();
            }
        });


    }


    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser()!=null){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(Login2Activity.this,"Login realizado com sucesso",Toast.LENGTH_SHORT).show();
                }else {
                    String exception = "";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception ="E-mail não existente";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        exception ="Senha Invalida";
                    }catch (Exception e) {
                        exception = "Erro ao logar";
                        e.printStackTrace();
                    }
                    Toast.makeText(Login2Activity.this, exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void abrirTelaPrincipal(){
        Intent intent = new Intent(Login2Activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(Login2Activity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }
}
