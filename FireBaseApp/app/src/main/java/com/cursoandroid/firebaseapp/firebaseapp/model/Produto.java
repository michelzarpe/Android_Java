package com.cursoandroid.firebaseapp.firebaseapp.model;

public class Produto {
    private String descricao;
    private String corProduto;
    private String fabricanteProduto;

    public Produto(String descricao, String corProduto, String fabricanteProduto) {
        this.descricao = descricao;
        this.corProduto = corProduto;
        this.fabricanteProduto = fabricanteProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCorProduto() {
        return corProduto;
    }

    public void setCorProduto(String corProduto) {
        this.corProduto = corProduto;
    }

    public String getFabricanteProduto() {
        return fabricanteProduto;
    }

    public void setFabricanteProduto(String fabricanteProduto) {
        this.fabricanteProduto = fabricanteProduto;
    }
}
