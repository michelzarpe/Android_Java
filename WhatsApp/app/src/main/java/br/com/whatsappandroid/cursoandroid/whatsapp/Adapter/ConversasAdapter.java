package br.com.whatsappandroid.cursoandroid.whatsapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Conversa;

public class ConversasAdapter extends ArrayAdapter<Conversa> {
    private Context context;
    private ArrayList<Conversa> listaConversa;
    private TextView tv_mensagem_conversa;
    private TextView tv_nome_conversa;

    public ConversasAdapter(@NonNull Context context, @NonNull ArrayList<Conversa> objects) {
        super(context, 0, objects);
        this.listaConversa = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if(listaConversa!=null){
            /*inicializa o objeot para montar o layout*/
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_conversa, parent, false);
            /*recuperando o campo da tela*/
            tv_mensagem_conversa = (TextView) view.findViewById(R.id.tv_mensagem_conversa);
            tv_nome_conversa = (TextView) view.findViewById(R.id.tv_nome_conversa);

            tv_mensagem_conversa.setText(listaConversa.get(position).getMensagem());
            tv_nome_conversa.setText(listaConversa.get(position).getNome());

        }



        return view;
    }

}
