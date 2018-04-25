package br.com.whatsappandroid.cursoandroid.whatsapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/* quando colocado final na classe, ela nao pode ser extendida*/
public final class ConfiguracaoFirebase {
    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;

    /*retorna a instancia do firebird sem instancia*/
    public static DatabaseReference getFirebase(){
        /*caso seja nula instancia novamente*/
        if(referenciaFirebase==null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    /*instancia para o processo de autenticacao*/
    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao==null){
            autenticacao = FirebaseAuth.getInstance();
        }
    return autenticacao;
    }
}
