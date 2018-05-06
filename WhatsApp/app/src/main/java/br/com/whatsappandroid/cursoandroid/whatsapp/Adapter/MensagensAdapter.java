package br.com.whatsappandroid.cursoandroid.whatsapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;

import java.util.ArrayList;

import br.com.whatsappandroid.cursoandroid.whatsapp.helper.PreferencesUsuario;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Mensagem;

public class MensagensAdapter extends ArrayAdapter<Mensagem> {
    private Context context;
    private ArrayList<Mensagem> listaMensagens;
    private TextView edit_mensagem;

    public MensagensAdapter(@NonNull Context context, @NonNull ArrayList<Mensagem> objects) {
        super(context, 0, objects);
        this.listaMensagens = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (listaMensagens != null) {
            /*Recuperar dados do usuario remetente*/
            PreferencesUsuario preferencesUsuario = new PreferencesUsuario(context);

            /*inicializa o objeot para montar o layout*/
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            /*passar o layout das mensagens*/
            if (preferencesUsuario.getIdentificador().equals(listaMensagens.get(position).getIdUsuario())) {
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            } else {
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            /*recuperando o campo da tela*/
            edit_mensagem = (TextView) view.findViewById(R.id.edit_mensagem);
            edit_mensagem.setText(listaMensagens.get(position).getMensagem());

        }

        return view;
    }
}
