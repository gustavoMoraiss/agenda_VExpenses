package com.example.agendacrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendacrud.domain.Endereco;
import com.example.agendacrud.util.Conexao;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public EnderecoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Endereco endereco, Integer idUsuario){
        ContentValues values = new ContentValues();
        values.put("bairro", endereco.getBairro());
        values.put("cep", endereco.getCep());
        values.put("logradouro", endereco.getLogradouro());
        values.put("localidade", endereco.getLocalidade());
        values.put("complemento", endereco.getComplemento());
        values.put("uf", endereco.getUf());
        values.put("id_usuario", idUsuario.toString());
        values.put("numero", endereco.getNumber());
        return banco.insert("address", null, values);
    }

    public List<Endereco> obterTodos(Integer id){
        List<Endereco> adresss = new ArrayList<>();
        Cursor cursor = banco.query("address", new String[]{"id", "cep", "bairro", "logradouro", "localidade", "uf", "id_usuario", "numero"},
                  "id_usuario='" + id +"'", null, null, null, null);
        while(cursor.moveToNext()){
            Endereco endereco = new Endereco();
            endereco.setId(cursor.getInt(0));
            endereco.setCep(cursor.getString(1));
            endereco.setBairro(cursor.getString(2));
            endereco.setLogradouro(cursor.getString(3));
            endereco.setLocalidade(cursor.getString(4));
            endereco.setUf(cursor.getString(5));
            endereco.setId_usuario(cursor.getInt(6));
            endereco.setNumber(cursor.getString(7));
            adresss.add(endereco);
        }
        return adresss;
    }

    public void excluir(Endereco endereco){
        banco.delete("endereco", "id=?", new String[]{endereco.getId().toString()});
    }

    public void atualizar(Endereco endereco){
        ContentValues values = new ContentValues();
        values.put("cep", endereco.getCep());
        values.put("bairro", endereco.getBairro());
        values.put("logradouro", endereco.getLogradouro());
        values.put("localidade", endereco.getLocalidade());
        values.put("numero", endereco.getNumber());
        banco.update("endereco", values, "id=?", new String[]{endereco.getId().toString()});
    }

}