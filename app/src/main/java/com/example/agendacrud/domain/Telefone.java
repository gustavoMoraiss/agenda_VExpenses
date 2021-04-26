package com.example.agendacrud.domain;

import java.io.Serializable;

public class Telefone implements Serializable {

    public static int INDEX_TELEFONE_CELULAR = 0;
    public static int INDEX_TELEFONE_COMERCIAL = 0;
    public static int INDEX_TELEFONE_RESIDENCIAL = 0;

    private Integer id;
    private String telefone_celular;
    private String telefone_comercial;
    private String telefone_residencial;

    private Integer id_usuario;

    public String getTelefone_comercial() {
        return telefone_comercial;
    }

    public void setTelefone_comercial(String telefone_comercial) {
        this.telefone_comercial = telefone_comercial;
    }

    public String getTelefone_residencial() {
        return telefone_residencial;
    }

    public void setTelefone_residencial(String telefone_residencial) {
        this.telefone_residencial = telefone_residencial;
    }


    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelefone_celular() {
        return telefone_celular;
    }

    public void setTelefone_celular(String telefone_celular) {
        this.telefone_celular = telefone_celular;
    }
}
