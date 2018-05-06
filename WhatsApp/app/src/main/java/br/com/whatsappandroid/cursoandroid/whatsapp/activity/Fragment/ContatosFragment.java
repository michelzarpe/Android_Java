package br.com.whatsappandroid.cursoandroid.whatsapp.activity.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.whatsappandroid.cursoandroid.whatsapp.Adapter.AdapterPersonalidado;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.activity.atual.ConversaActivity;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.PreferencesUsuario;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;

public class ContatosFragment extends Fragment {
    private ListView listView_Contatos;
    private AdapterPersonalidado arrayAdapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contatos = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_contatos, container, false);
        listView_Contatos = (ListView) view.findViewById(R.id.listView_Contatos);
        arrayAdapter = new AdapterPersonalidado(contatos,getActivity());
        listView_Contatos.setAdapter(arrayAdapter);

        /*recuperar os contatos direto do fireBase*/
        recuperarContatosDoUsuarioCorrente();

        /*adicionando um evento de click nos itens*/
        listView_Contatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*abrir o activity de conversa*/

                Intent intent = new Intent(getActivity(), ConversaActivity.class);
                intent.putExtra("nameUser",contatos.get(position).getNome());
                intent.putExtra("emailUser",contatos.get(position).getEmail());
                startActivity(intent);
            }
        });

        return view;
    }

    private void recuperarContatosDoUsuarioCorrente() {
        PreferencesUsuario preferencesUsuario = new PreferencesUsuario(getActivity());
        databaseReference = ConfiguracaoFirebase.getFirebase().child("contatos").child(preferencesUsuario.getIdentificador());
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //limpar lista
                contatos.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Contato c = dados.getValue(Contato.class);
                    contatos.add(c);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


    }
}
