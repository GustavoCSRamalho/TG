package com.example.root.tg_01.utils;

import com.example.root.tg_01.models.Empresa;
import com.example.root.tg_01.models.Usuario;

public class Usuario_logado {

    protected static Usuario_logado usuario_logado;

    protected Usuario usuario;

    protected Empresa empresa;

    protected Usuario_logado(){}

    public static Usuario_logado getInstance(){
        if(usuario_logado == null){
            usuario_logado = new Usuario_logado();
        }
        return usuario_logado;
    }

    public Empresa getEmpresa() {
        if(usuario_logado.empresa == null){
            usuario_logado.empresa = new Empresa();
        }
        return usuario_logado.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        usuario_logado.empresa = empresa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
