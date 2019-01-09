package com.example.root.tg_01.models;

import java.util.List;

public class Pedido {

    private String empresa;
    private String nome;
    private String descricao;
    private String destino;
    private List<Usuario> caminhoneiros;

    public Pedido() {
    }

    public Pedido(String empresa, String destino, String descricao) {
        this.descricao = descricao;
        this.empresa = empresa;
        this.destino = destino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Usuario> getCaminhoneiros() {
        return caminhoneiros;
    }

    public void setCaminhoneiros(List<Usuario> caminhoneiros) {
        this.caminhoneiros = caminhoneiros;
    }

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
