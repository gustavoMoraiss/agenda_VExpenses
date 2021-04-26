package com.example.agendacrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.agendacrud.domain.Telefone;
import com.example.agendacrud.util.Conexao;

import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO {

    private final Conexao conexao;
    private final SQLiteDatabase banco;

    public TelefoneDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Telefone tel, Integer idUser) {
        ContentValues values = new ContentValues();

        if (tel.getTelefone_comercial() != null) {
            values.put("telefone_comercial", tel.getTelefone_comercial());
        }

        if (tel.getTelefone_residencial() != null) {
            values.put("telefone_residencial", tel.getTelefone_residencial());
        }

        values.put("telefone_celular", tel.getTelefone_celular());
        values.put("id_usuario", idUser.toString());
        return banco.insert("telefone", null, values);
    }

    public List<Telefone> obterTodos(Integer id) {
        List<Telefone> telefones = new ArrayList<>();

        Cursor cursor = banco.query("telefone", new String[]{"id", "telefone_celular", "telefone_comercial", "telefone_residencial", "id_usuario"},
              "id_usuario='" + id + "'", null, null, null, null);
        while (cursor.moveToNext()) {
            Telefone tel = new Telefone();
            tel.setId(cursor.getInt(0));
            tel.setTelefone_celular(cursor.getString(1));

            if (cursor.getString(2) != null) {
                tel.setTelefone_comercial(cursor.getString(2));
            }

            if (cursor.getString(3) != null) {
                tel.setTelefone_residencial(cursor.getString(3));
            }

            tel.setId_usuario(cursor.getInt(4));
            telefones.add(tel);
        }
        return telefones;
    }

    public void excluir(Telefone tel) {
        banco.delete("telefone", "id=?", new String[]{tel.getId().toString()});
    }

    public void atualizar(Telefone tel) {
        ContentValues values = new ContentValues();
        if (tel.getTelefone_comercial() != null) {
            values.put("telefone_comercial", tel.getTelefone_comercial());
        }

        if (tel.getTelefone_residencial() != null) {
            values.put("telefone_residencial", tel.getTelefone_residencial());
        }
        values.put("telefone_celular", tel.getTelefone_celular());
        banco.update("telefone", values, "id=?", new String[]{tel.getId().toString()});
    }

    public String obterTelefone(Integer id) {
        Cursor cursor = banco.query("telefone", new String[]{"id", "telefone_celular", "telefone_comercial", "telefone_residencial", "id_usuario"},
                "id_usuario =" + id, null, null, null, null);

        if (cursor.getString(1) != null) {
            String result = cursor.getString(1);
            Log.i("Teste", result);
            return result;
        }
        return "";
    }

}
