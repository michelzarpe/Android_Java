package br.com.whatsappandroid.cursoandroid.whatsapp.helper;

import android.util.Base64;

public class Base64Custom {


    public static String codificandoBase64(String emailCodificar) {
        String emailCodificado = Base64.encodeToString(emailCodificar.getBytes(), Base64.DEFAULT).replace("\r", "");
        emailCodificado.replace("\n","");
        emailCodificado.replace("\\n","");
        emailCodificado.replace("\\r","");
        emailCodificado = emailCodificado.substring(0,emailCodificado.length()-1);
        return emailCodificado;
    }

    public static String decodificandoBase64(String emailCodificado) {
        return new String(Base64.decode(emailCodificado,Base64.DEFAULT));
    }

}
