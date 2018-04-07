package com.example.michel.adivinha.Bean;

import java.util.ArrayList;

/**
 * Created by michel on 24/03/18.
 */

public class Frase {
    ArrayList<String> Banco_de_Frases = new ArrayList<>();


    public Frase(){
        getBanco_de_Frases().clear();
        getBanco_de_Frases().add("Hoje vai ser o melhor dia da sua vida");
        getBanco_de_Frases().add("Melhor que amanha 'e fazer hoje");
        getBanco_de_Frases().add("Pense no hoje e nao no ontem");
        getBanco_de_Frases().add("Veja la na frente mas pense no hoje");
        getBanco_de_Frases().add("Hoje, eu amo");
        getBanco_de_Frases().add("Ontem eu amei");
        getBanco_de_Frases().add("Salve o Goku");
        getBanco_de_Frases().add("Goku Rei.");

    }

    public String geradorFraseAleatoria(int numeroGerado){
        return getBanco_de_Frases().get(numeroGerado);
    }

    public ArrayList<String> getBanco_de_Frases() {
        return Banco_de_Frases;
    }

    public void setBanco_de_Frases(ArrayList<String> banco_de_Frases) {
        Banco_de_Frases = banco_de_Frases;
    }
}
