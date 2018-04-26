package br.com.whatsappandroid.cursoandroid.whatsapp.activity.atual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private Toolbar toolbar_activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        toolbar_activity_main = (Toolbar) findViewById(R.id.toolbar_activity_main);
        toolbar_activity_main.setTitle("WhatsAppMZ");
        setSupportActionBar(toolbar_activity_main); /*toolbar com retorno, sempre colocar para funcionar corretamente*/
    }

    /*passo qual os menus eu quero*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater(); /*cria um objeto do tipo inflater com o contexto, para colocar os menus na tela*/
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /*acao dos botoes da toolbar*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_configuracao:
                return true;
            case R.id.action_adicionar:
                return true;
            case R.id.action_pesquisar:
                return true;
            case R.id.action_sair:
                deslogar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogar() {
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this,Login2Activity.class);
        startActivity(intent);
        finish();
    }

}
