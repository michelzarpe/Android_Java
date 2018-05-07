package br.com.whatsappandroid.cursoandroid.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUsuario {


    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;/*editor é uma interface para editar*/
    private static final String ARQUIVO_NOME = "WHATSAPPMZ.PREFERENCIA";
    public static final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    public static final String CHAVE_NOME = "nomeUsuarioLogado";


    public PreferencesUsuario(Context contexto) {
        this.contexto = contexto;
        this.preferences = contexto.getSharedPreferences(ARQUIVO_NOME, Context.MODE_PRIVATE);/*arquivo de preferencias apenas do meu app*/
        this.editor = preferences.edit();
    }

    public void salvarDados(String identificadorUsuarioLogado, String nomeUsuarioLogado) {
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuarioLogado);
        editor.putString(CHAVE_NOME, nomeUsuarioLogado);
        editor.commit();
    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null); /*recuperando identificador*/
    }

    public String getNome() {
        return preferences.getString(CHAVE_NOME, null); /*recuperando nome do usuario*/
    }


    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

}


