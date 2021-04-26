package com.example.agendacrud.domain;

import java.io.Serializable;
import java.util.List;

public class Login implements Serializable {

    private Integer id;
    private String email;
    private String senha;
    private List<Contato> contatoes;

    public List<Contato> getContatoes() {
        return contatoes;
    }

    public void setContatoes(List<Contato> contatoes) {
        this.contatoes = contatoes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
