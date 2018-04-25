package br.com.whatsappandroid.cursoandroid.whatsapp.controller;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;/*editor é uma interface para editar*/
    private static final String ARQUIVO_NOME = "WHATSAPPMZ.PREFERENCIA";
    public static final String NOME_USUARO= "nome";
    public static final String TELEFONE_USUARIO = "telefone";
    public static final String TOKE_USUARIO = "token";

    public Preferencias(Context contexto) {
        this.contexto = contexto;
        this.preferences = contexto.getSharedPreferences(ARQUIVO_NOME,Context.MODE_PRIVATE);/*arquivo de preferencias apenas do meu app*/
        this.editor = preferences.edit();
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public void salvarUsuarioPreferencia(String nome, String telefone, String token){
        editor.putString(NOME_USUARO,nome);
        editor.putString(TELEFONE_USUARIO,telefone);
        editor.putString(TOKE_USUARIO,token);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario(){
        HashMap<String, String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(NOME_USUARO,preferences.getString(NOME_USUARO,null));
        dadosUsuario.put(TELEFONE_USUARIO,preferences.getString(TELEFONE_USUARIO,null));
        dadosUsuario.put(TOKE_USUARIO,preferences.getString(TOKE_USUARIO,null));
        return dadosUsuario;
    }



}
