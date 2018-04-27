package br.com.whatsappandroid.cursoandroid.whatsapp.activity.atual;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.whatsappandroid.cursoandroid.whatsapp.Adapter.TabAdapter;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.controller.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.controller.PreferencesUsuario;
import br.com.whatsappandroid.cursoandroid.whatsapp.controller.SlidingTabLayout;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Usuario;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth usuarioAutenticacaoFirebase;
    private Toolbar toolbar_activity_main;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference referenceFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPage_1);
        usuarioAutenticacaoFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        /* toolbar primeiro */
        toolbar_activity_main = (Toolbar) findViewById(R.id.toolbar_activity_main);
        toolbar_activity_main.setTitle("WhatsAppMZ");
        setSupportActionBar(toolbar_activity_main); /*toolbar com retorno, sempre colocar para funcionar corretamente*/

        /*configurar o layout pra pagina inteira*/
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));/*recupera a cor do contexto */

        /*configurar o adapter para mostrar as paginas*/
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);

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
                abrirCadastroContato();
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
        usuarioAutenticacaoFirebase.signOut();
        Intent intent = new Intent(MainActivity.this, Login2Activity.class);
        startActivity(intent);
        finish();
    }

    private void abrirCadastroContato() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final EditText editTextEmailContato = new EditText(MainActivity.this);
        /*configurações*/
        alertBuilder.setView(editTextEmailContato);
        alertBuilder.setTitle("Novo Contato");
        alertBuilder.setMessage("E-mail do Usuario");
        alertBuilder.setCancelable(false); /*não sera possivel clicar fora da janela*/
        alertBuilder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailContato = editTextEmailContato.getText().toString();
                if (emailContato.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha um e-mail", Toast.LENGTH_SHORT).show();
                } else {
                    /*usuario ja esta cadastrado?*/
                    identificadorContato = Base64Custom.codificandoBase64(emailContato);
                    /*recupearar a instancia do banco, nó e ao identificador*/
                    referenceFirebase = ConfiguracaoFirebase.getFirebase();
                    referenceFirebase = referenceFirebase.child("usuario").child(identificadorContato);
                    referenceFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);
                                Contato contato = new Contato(identificadorContato,usuarioContato.getNome(),usuarioContato.getEmail());
                                referenceFirebase = ConfiguracaoFirebase.getFirebase();
                                PreferencesUsuario preferencesUsuario = new PreferencesUsuario(MainActivity.this);
                                referenceFirebase.child("contatos")
                                                 .child(preferencesUsuario.getIdentificador())
                                                 .child(identificadorContato).setValue(contato);

                            } else {
                                Toast.makeText(MainActivity.this, "Usuario sem Cadastro", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }
            }
        });
        alertBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.create();
        alertBuilder.show();

    }

}
