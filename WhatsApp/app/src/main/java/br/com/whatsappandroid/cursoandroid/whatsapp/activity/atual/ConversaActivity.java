package br.com.whatsappandroid.cursoandroid.whatsapp.activity.atual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import br.com.whatsappandroid.cursoandroid.whatsapp.Adapter.MensagensAdapter;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.PreferencesUsuario;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Mensagem;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar_activity_main;
    private EditText edit_text_enviar;
    private ListView list_view_conversas;
    private ImageButton bt_enviar;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private String nomeDestinatario;
    private String idUsuarioDestinatario;
    private String emailDestinatario;
    private String idUsuarioRemetente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        recuperandoElementosInterface();
        recuperandoDadosDoUsuarioLogado();
        recuperandoDadosDoUsuarioDestinatario();
        configurandoToolbar();
        exibindoMensagensNaInterface();
        enviarMensagem();

    }

    private void exibindoMensagensNaInterface() {

        mensagens = new ArrayList<Mensagem>();
        adapter = new MensagensAdapter(ConversaActivity.this, mensagens);
        list_view_conversas.setAdapter(adapter);

        firebase = ConfiguracaoFirebase.getFirebase().child("mensagens").child(idUsuarioRemetente).child(idUsuarioDestinatario);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Mensagem mensagem = snapshot.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        firebase.addValueEventListener(valueEventListener);
    }

    private void configurandoToolbar() {
        toolbar_activity_main.setTitle(nomeDestinatario);
        toolbar_activity_main.setNavigationIcon(R.drawable.ic_chevron_left);
        setSupportActionBar(toolbar_activity_main);
    }

    private void recuperandoDadosDoUsuarioLogado() {
        /*dados usuario logado(Remetente)*/
        PreferencesUsuario preferencesUsuario = new PreferencesUsuario(ConversaActivity.this);
        idUsuarioRemetente = preferencesUsuario.getIdentificador();
    }

    private void recuperandoDadosDoUsuarioDestinatario() {
        Intent intent = getIntent();
        nomeDestinatario = intent.getStringExtra("nameUser");
        emailDestinatario = intent.getStringExtra("emailUser");
        idUsuarioDestinatario = Base64Custom.codificandoBase64(emailDestinatario);
    }

    private void enviarMensagem() {
        /*enviado mensagem*/
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoMensagem = edit_text_enviar.getText().toString();
                if (!textoMensagem.isEmpty()) {
                    Mensagem mensagem = new Mensagem(idUsuarioRemetente, textoMensagem);
                    idUsuarioDestinatario = Base64Custom.codificandoBase64(emailDestinatario);
                    salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                    salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);
                    edit_text_enviar.setText("");
                }

            }
        });
    }

    private boolean salvarMensagem(String idUsuarioRemetente, String idUsuarioDestinatario, Mensagem mensagem) {
        try {
            /*push cria um novo identificador para ficar no mesmo nó*/
            firebase = ConfiguracaoFirebase.getFirebase();
            firebase.child("mensagens")
                    .child(idUsuarioRemetente)
                    .child(idUsuarioDestinatario)
                    .push()
                    .setValue(mensagem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }


    private void recuperandoElementosInterface() {
        toolbar_activity_main = (Toolbar) findViewById(R.id.toolbar_activity_conversa);
        edit_text_enviar = (EditText) findViewById(R.id.edit_text_enviar);
        bt_enviar = (ImageButton) findViewById(R.id.bt_enviar);
        list_view_conversas = (ListView) findViewById(R.id.list_view_conversas);
    }


}
