package br.com.whatsappandroid.cursoandroid.projetofragmento;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button botao_logar;
    private boolean status = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botao_logar = (Button) findViewById(R.id.botao_logar);
        botao_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(status){
                    inicarLogin();
                    botao_logar.setText("Cadastre-se");
                    status=false;
                }else{
                    inicarCadastro();
                    botao_logar.setText("Logar");
                    status=true;
                }


            }
        });

    }

    /*classes para usar os fragmentos
     * https://developer.android.com/guide/components/fragments?hl=pt-br
     * */
    private void inicarLogin(){
        FragmentManager fragmentManager = getSupportFragmentManager();/*retorna objeto para conseguir manipular os fragmentos*/
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();/*prepara objeto para fazer edições no fragmento*/
        LoginFragment fragment = new LoginFragment();
        fragmentTransaction.add(R.id.id_container_fragmentos, fragment);
        fragmentTransaction.commit();
    }

    private void inicarCadastro(){
        FragmentManager fragmentManager = getSupportFragmentManager();/*retorna objeto para conseguir manipular os fragmentos*/
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();/*prepara objeto para fazer edições no fragmento*/
        CadastroFragment fragment = new CadastroFragment();
        fragmentTransaction.add(R.id.id_container_fragmentos, fragment);
        fragmentTransaction.commit();
    }

}
