package br.com.whatsappandroid.cursoandroid.whatsapp.activity.atual;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.PreferencesUsuario;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private EditText edit_cadastroNome;
    private EditText edit_cadastroEmail;
    private EditText edit_cadastroSenha;
    private Button button_cadastrarUsuario;
    private DatabaseReference referenciaFireBase;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        edit_cadastroNome = (EditText) findViewById(R.id.edit_cadastroNome);
        edit_cadastroEmail = (EditText) findViewById(R.id.edit_cadastroEmail);
        edit_cadastroSenha = (EditText) findViewById(R.id.edit_cadastroSenha);
        button_cadastrarUsuario = (Button) findViewById(R.id.button_cadastrarUsuario);

        button_cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(edit_cadastroEmail.getText().toString());
                usuario.setNome(edit_cadastroNome.getText().toString());
                usuario.setSenha(edit_cadastroSenha.getText().toString());
                cadastrarUsuarioFirebase();

            }
        });
    }

    private void cadastrarUsuarioFirebase() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { /*verifica se o cadastro foi feito com sucesso*/

                    usuario.setBase64ID(Base64Custom.codificandoBase64(usuario.getEmail()));
                    usuario.setId(usuario.getBase64ID());

                    salvarUsuariosFireBase();

                    PreferencesUsuario preferencesUsuario = new PreferencesUsuario(CadastroUsuarioActivity.this);
                    preferencesUsuario.salvarDados(usuario.getBase64ID());

                    abrirLoginUsuario();

                } else {
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        exception = "Digite uma senha contendo letras e numeros, no minimo 8";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "E-mail digitado é invalido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "E-mail já está em uso";
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "Autenticação não é mais valida, realize o login novamente";
                    } catch (Exception e) {
                        exception = "Erro ao cadastrar o usuário: ";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this, exception, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void salvarUsuariosFireBase() {
        referenciaFireBase = ConfiguracaoFirebase.getFirebase();
        referenciaFireBase.child("usuario").child(usuario.getId()).setValue(usuario);
    }

    private void abrirLoginUsuario() {
        Intent intent = new Intent(CadastroUsuarioActivity.this, Login2Activity.class);
        startActivity(intent);
        finish();
    }


}
