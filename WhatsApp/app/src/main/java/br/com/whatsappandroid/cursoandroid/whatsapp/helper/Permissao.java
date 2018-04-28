package br.com.whatsappandroid.cursoandroid.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {


    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissao) {
        /*verificar se a versao é maior que 23*/

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<String>();

            for (String permissoes : permissao) {
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissoes) == PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao) listaPermissoes.add(permissoes);
            }
            /*caso a lista esteja vazia não é necessario solicitar permissao*/
            if (listaPermissoes.isEmpty())
                return true;

            /*conversao de list para array String*/
            String [] permissoesConvertida = new String[listaPermissoes.size()];
            listaPermissoes.toArray(permissoesConvertida);

            /*solicita permissao*/
            ActivityCompat.requestPermissions(activity,permissoesConvertida,requestCode);

        }
        return true;
    }

}
