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
import br.com.whatsappandroid.cursoandroid.whatsapp.Adapter.ConversasAdapter;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.activity.atual.ConversaActivity;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.PreferencesUsuario;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Conversa;


public class ConversasFragment extends Fragment {

    private ListView listView_Conversas;
    private ArrayAdapter<Conversa> arrayAdapter;
    private ArrayList<Conversa> conversas;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public ConversasFragment() {
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
        // Inflate the layout for this fragment
        conversas = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);
        listView_Conversas = (ListView) view.findViewById(R.id.listView_Conversas);
        arrayAdapter = new ConversasAdapter(getActivity(), conversas);
        listView_Conversas.setAdapter(arrayAdapter);
        recuperandoConversasdoUsuario();

        listView_Conversas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);
                intent.putExtra("nameUser",conversas.get(position).getNome());
                intent.putExtra("emailUser", Base64Custom.decodificandoBase64(conversas.get(position).getIdUsuario()));
                startActivity(intent);
            }
        });

        return view;
    }

    public void recuperandoConversasdoUsuario() {
        //recuperar dados do usuario
        PreferencesUsuario preferencesUsuarioLogado = new PreferencesUsuario(getActivity());
        //recuperar conversas do firebase
        databaseReference = ConfiguracaoFirebase.getFirebase().child("conversas").child(preferencesUsuarioLogado.getIdentificador());
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversas.clear();
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    conversas.add(s.getValue(Conversa.class));
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

}
