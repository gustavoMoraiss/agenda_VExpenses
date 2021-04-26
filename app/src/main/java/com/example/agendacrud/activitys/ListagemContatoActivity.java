package com.example.agendacrud.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendacrud.R;
import com.example.agendacrud.adapter.UsuarioAdapter;
import com.example.agendacrud.dao.ContatoDAO;
import com.example.agendacrud.domain.Contato;
import com.example.agendacrud.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class ListagemContatoActivity extends AppCompatActivity {
    private ListView listView;
    private final List<Contato> usuariosFiltrados = new ArrayList<>();
    private ContatoDAO userDAO;
    private List<Contato> contatoes;
    private int id_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        listView = findViewById(R.id.lista_usuarios);
        userDAO = new ContatoDAO(this);

        id_login = SharedPreferencesHelper.getInt(this, SharedPreferencesHelper.SHARED_PREFERENCES_ID_LOGIN);

        contatoes = userDAO.obterTodos(id_login);
        usuariosFiltrados.addAll(contatoes);

        UsuarioAdapter usuarioAdapter = new UsuarioAdapter(contatoes, this);
        listView.setAdapter(usuarioAdapter);

        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procuraUsuario(newText);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraUsuario(String nome){
        usuariosFiltrados.clear();
        for (Contato user : contatoes) {
            if (user.getNome().toLowerCase().contains(nome.toLowerCase())) {
                usuariosFiltrados.add(user);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato user = usuariosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção!")
                .setMessage("Deseja Realmente excluir o Usuário?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usuariosFiltrados.remove(user);
                        userDAO.excluir(user);
                        listView.invalidateViews();
                        Toast.makeText(ListagemContatoActivity.this, "Usuário excluído!", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contato = usuariosFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, CadastroContatoActivity.class);
        it.putExtra(CadastroContatoActivity.EXTRA_INTENT_USUARIO, contato);
        startActivity(it);
    }

    public void cadastrar(MenuItem item) {
        startActivity(new Intent(this, CadastroContatoActivity.class));
    }

    public void sair(MenuItem item) {
        SharedPreferencesHelper.putBoolean(this, SharedPreferencesHelper.SHARED_PREFERENCES_USUARIO_LOGADO, false);
        SharedPreferencesHelper.removeValue(this, SharedPreferencesHelper.SHARED_PREFERENCES_ID_LOGIN);

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        contatoes = userDAO.obterTodos(id_login);
        usuariosFiltrados.clear();
        usuariosFiltrados.addAll(contatoes);
        listView.invalidateViews();

        UsuarioAdapter usuarioAdapter = new UsuarioAdapter(usuariosFiltrados, this);
        listView.setAdapter(usuarioAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        contatoes = userDAO.obterTodos(id_login);
        usuariosFiltrados.clear();
        usuariosFiltrados.addAll(contatoes);
        listView.invalidateViews();

        UsuarioAdapter usuarioAdapter = new UsuarioAdapter(usuariosFiltrados, this);
        listView.setAdapter(usuarioAdapter);
    }

}