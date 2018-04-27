package br.com.whatsappandroid.cursoandroid.projetofragmento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.whatsappandroid.cursoandroid.projetofragmento.R;

public class LoginFragment extends Fragment {
    private TextView text_fragmentLogin;

    public LoginFragment() {
        // Required empty public constructor
    }



    /*para que funcione um fragmento ele converte para uma view, entao tudo que precisar mudar tem que usar essa view*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        text_fragmentLogin = (TextView) view.findViewById(R.id.text_fragmentLogin);
        text_fragmentLogin.setText("Estamos na tela de login");
        return view;
    }

}
