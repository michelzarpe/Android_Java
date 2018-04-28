package br.com.whatsappandroid.cursoandroid.whatsapp.activity.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.whatsappandroid.cursoandroid.whatsapp.Adapter.AdapterPersonalidado;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
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

        //getActivity, ele pega a activity que vai ser utilizada para passar ao adapter
        //arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1,contatos);
         //arrayAdapter = new ArrayAdapter(getActivity(), R.layout.lista_contato, contatos);

        arrayAdapter = new AdapterPersonalidado(contatos,getActivity());
        listView_Contatos.setAdapter(arrayAdapter);

        /*recuperar os contatos direto do fireBase*/
        recuperarContatosDoUsuarioCorrente();

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
