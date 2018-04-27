package br.com.whatsappandroid.cursoandroid.whatsapp.model;

public class Contato {
    private String identificadorContato;
    private String nome;
    private String email;

    public Contato() {
    }

    public Contato(String identificadorContato, String nome, String email) {
        this.identificadorContato = identificadorContato;
        this.nome = nome;
        this.email = email;
    }

    public String getIdentificadorContato() {
        return identificadorContato;
    }

    public void setIdentificadorContato(String identificadorContato) {
        this.identificadorContato = identificadorContato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
