package com.example.root.tg_01.models;

import java.util.List;

public class Empresa {

    private String nome;

    private List<Pedido> listaDePedidos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Pedido> getListaDePedidos() {
        return listaDePedidos;
    }

    public void setListaDePedidos(List<Pedido> listaDePedidos) {
        this.listaDePedidos = listaDePedidos;
    }


}
