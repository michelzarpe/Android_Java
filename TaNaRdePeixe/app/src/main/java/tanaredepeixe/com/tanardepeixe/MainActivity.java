package tanaredepeixe.com.tanardepeixe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //desconectarRedePeixe();
        //logarRedePeixe("michelzarpe@gmail.com", "123456789");
        //usuarioLogadoRedePeixe("michelzarpe@gmail.com", "123456789");
        
    }

    private void desconectarRedePeixe(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

    private void usuarioLogadoRedePeixe(String email, String senha) {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            Log.i("Logado", "Usuario está logado");
        } else {
            Log.i("Logado", "Usuario não está logado");
        }
    }


    /*modificar para retornar boolean e mostrar no tost o erro*/
    private void cadastrarRedePeixe(String email, String senha) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("createrUser", "Cadastrado com Sucesso");
                        } else {
                            Log.i("createrUser", "Cadastro com erro");
                        }
                    }
                });
    }

    /*modificar para retornar boolean e mostrar no tost o erro*/
    private void logarRedePeixe(String email, String senha) {
        /*criptografar*/
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        /*login de usuario*/
        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("login", "login com sucesso!! " + firebaseAuth.getCurrentUser().toString());
                        } else {
                            Log.i("login", "Login com erro!! " + task.getException());
                        }
                    }
                });
    }

}
