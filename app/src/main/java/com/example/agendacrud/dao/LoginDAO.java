package com.example.agendacrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendacrud.domain.Login;
import com.example.agendacrud.util.Conexao;
import com.example.agendacrud.util.SharedPreferencesHelper;

public class LoginDAO {

    private final Conexao conexao;
    private final SQLiteDatabase banco;
    private final ContatoDAO contatoDAO;
    private final Context contexto;

    public LoginDAO(Context context) {
        this.conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
        this.contatoDAO = new ContatoDAO(context);
        this.contexto = context;
    }

    public long inserir(Login login) {
        ContentValues values = new ContentValues();
        values.put("email", login.getEmail());
        values.put("senha", login.getSenha());
        return banco.insert("login", null, values);
    }

    public void excluir(Login login) {
        banco.delete("login", "id=?", new String[]{login.getId().toString()});
    }

    public void atualizar(Login login) {
        ContentValues values = new ContentValues();
        values.put("email", login.getEmail());
        values.put("senha", login.getSenha());
        banco.update("login", values, "id=?", new String[]{login.getId().toString()});
    }

    public boolean validaEmail(String email) {
        Cursor cursor = banco.query("login", new String[]{"id", "email", "senha"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean validaLogin(String email, String senha) {
        Cursor cursor = banco.query("login", new String[]{"id", "email", "senha"},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(email) && cursor.getString(2).equals(senha)) {
                int id = Integer.parseInt(cursor.getString(0));
                salvarIdLogin(id);
                return true;
            }
        }
        return false;
    }

    private void salvarIdLogin(int id) {
        SharedPreferencesHelper.putBoolean(contexto, SharedPreferencesHelper.SHARED_PREFERENCES_USUARIO_LOGADO, true);
        SharedPreferencesHelper.putInt(contexto, SharedPreferencesHelper.SHARED_PREFERENCES_ID_LOGIN, id);
    }

}
