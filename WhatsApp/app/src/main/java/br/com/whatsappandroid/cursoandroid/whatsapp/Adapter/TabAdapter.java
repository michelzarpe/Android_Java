package br.com.whatsappandroid.cursoandroid.whatsapp.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.whatsappandroid.cursoandroid.whatsapp.activity.Fragment.ContatosFragment;
import br.com.whatsappandroid.cursoandroid.whatsapp.activity.Fragment.ConversasFragment;

/*FragmentStatePagerAdapter, trata como fragmento as paginas, e tem um melhor aproveitamento de memoria*/
public class TabAdapter extends FragmentStatePagerAdapter {

    private String [] titulosAbas = {"CONVERSAS","CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    /*retorna para o page os fragmentos*/
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:{
                fragment = new ConversasFragment();
                break;
            }
            case 1:{
                fragment = new ContatosFragment();
                break;
            }
        }

        return fragment;
    }
    /*quantidade de abas que nós queremos*/
    @Override
    public int getCount() {
        return titulosAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulosAbas[position];
    }
}
