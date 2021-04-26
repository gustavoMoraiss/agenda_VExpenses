package com.example.agendacrud.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public Conexao(Context context) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table login(id integer primary key autoincrement, email varchar(80), senha varchar(30))");
        db.execSQL("create table address(id integer primary key autoincrement, cep varchar (50), bairro varchar(50), logradouro varchar(50), localidade varchar(50), uf varchar(50), complemento varchar(50), id_usuario varchar(10), numero varchar(50))");

        db.execSQL("create table usuario(id integer primary key autoincrement, " +
              "nome varchar(50), id_login varchar(50))");

        db.execSQL("create table telefone(id integer primary key autoincrement, telefone_celular varchar(50), telefone_comercial varchar(50), telefone_residencial varchar(50), id_usuario varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuario");
        db.execSQL("DROP TABLE telefone");
        db.execSQL("DROP TABLE address");
    }


}
