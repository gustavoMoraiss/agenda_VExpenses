package com.example.agendacrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendacrud.domain.Contato;
import com.example.agendacrud.domain.Endereco;
import com.example.agendacrud.domain.Telefone;
import com.example.agendacrud.util.Conexao;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private final Conexao conexao;
    private final SQLiteDatabase banco;
    private final TelefoneDAO telefoneDAO;
    private final EnderecoDAO enderecoDAO;
    private final Context contexto;


    public ContatoDAO(Context context) {
        this.conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
        this.contexto = context;

        telefoneDAO = new TelefoneDAO(context);
        enderecoDAO = new EnderecoDAO(context);
    }

    public long inserir(Contato contato) {
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("id_login", contato.getId_login());
        return banco.insert("usuario", null, values);
    }

    public List<Contato> obterTodos(int id_login) {
        List<Contato> contatoes = new ArrayList<>();

        Cursor cursor = banco.query("usuario", new String[]{"id", "nome", "id_login"},
              "id_login='" + id_login + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));

            List<Telefone> telefones = telefoneDAO.obterTodos(contato.getId());
            contato.setTelefones(telefones);

            List<Endereco> enderecos = enderecoDAO.obterTodos(contato.getId());
            contato.setEnderecos(enderecos);

            contatoes.add(contato);
        }

        return contatoes;
    }

    public void excluir(Contato user) {
        banco.delete("usuario", "id=?", new String[]{user.getId().toString()});
    }

    public void atualizar(Contato user) {
        ContentValues values = new ContentValues();
        values.put("nome", user.getNome());
        banco.update("usuario", values, "id=?", new String[]{user.getId().toString()});
    }

}
