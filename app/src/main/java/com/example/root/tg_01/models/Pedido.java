package com.example.root.tg_01.models;

public class Pedido {

    public Pedido(){}

    public Pedido(String empresa, String destino,String descricao){
        this.descricao = descricao;
        this.empresa = empresa;
        this.destino = destino;
    }

    private String empresa;

    private String descricao;

    private String destino;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
