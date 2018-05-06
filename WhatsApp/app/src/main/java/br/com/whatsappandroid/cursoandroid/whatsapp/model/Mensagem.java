package br.com.whatsappandroid.cursoandroid.whatsapp.model;

public class Mensagem {
    private String idUsuario;
    private String mensagem;

    public Mensagem() {
    }

    public Mensagem(String idUsuario, String mensagem) {
        this.idUsuario = idUsuario;
        this.mensagem = mensagem;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
