package br.com.whatsappandroid.cursoandroid.whatsapp.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import java.util.ArrayList;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;


/*Referencia sobre a classe com
* http://blog.alura.com.br/personalizando-uma-listview-no-android/
* */
public class AdapterPersonalidado extends BaseAdapter {

    private ArrayList<Contato> contatos;
    private Activity activity;

    public AdapterPersonalidado(ArrayList<Contato> contatos, Activity act) {
        this.contatos = contatos;
        this.activity = act;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lista_contato, parent, false);

        if (contatos != null) {
            TextView nome = (TextView) view.findViewById(R.id.tv_nome);
            TextView email = (TextView) view.findViewById(R.id.tv_email);
            nome.setText(contatos.get(position).getNome());
            email.setText(contatos.get(position).getEmail());
        }
        return view;
    }
}
