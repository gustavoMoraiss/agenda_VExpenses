package com.example.agendacrud.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agendacrud.R;
import com.example.agendacrud.dao.TelefoneDAO;
import com.example.agendacrud.domain.Contato;
import com.example.agendacrud.domain.Telefone;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {

    private final List<Contato> contatoes;
    private final Activity activity;
    private final TelefoneDAO telefoneDAO;
    private LinearLayout llTelResidencial, llTelComercial;
    private TextView telefoneCelular, telefoneResidencial, telefoneComercial, nome, txt_telefone_residencial, txt_telefone_comercial;

    public UsuarioAdapter(List<Contato> contatoes, Activity activity) {
        this.contatoes = contatoes;
        this.activity = activity;
        telefoneDAO = new TelefoneDAO(activity);
    }

    @Override
    public int getCount() {
        return contatoes.size();
    }

    @Override
    public Object getItem(int position) {
        return contatoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatoes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.activity_item_usuario, parent, false);

        nome = view.findViewById(R.id.txt_nome);
        telefoneCelular = view.findViewById(R.id.txt_telefone_celular);
        telefoneResidencial = view.findViewById(R.id.txt_telefone_residencial);
        telefoneComercial = view.findViewById(R.id.txt_telefone_comercial);
        llTelResidencial = view.findViewById(R.id.llTelResidencial);
        llTelComercial = view.findViewById(R.id.llTelComercial);

        Contato contato = contatoes.get(position);
        List<Telefone> telefones = telefoneDAO.obterTodos(contato.getId());

        nome.setText(contato.getNome());
        telefoneCelular.setText(telefones.get(Telefone.INDEX_TELEFONE_CELULAR).getTelefone_celular());

        final Telefone telefone_residencial = telefones.get(Telefone.INDEX_TELEFONE_RESIDENCIAL);
        if (telefone_residencial.getTelefone_residencial() != null) {
            if (!telefone_residencial.getTelefone_residencial().equals("")) {
                llTelResidencial.setVisibility(View.VISIBLE);
                telefoneResidencial.setText(telefone_residencial.getTelefone_residencial());
            }
        }

        final Telefone telefone_comercial = telefones.get(Telefone.INDEX_TELEFONE_COMERCIAL);
        if (telefone_comercial.getTelefone_comercial() != null) {
            if (!telefone_comercial.getTelefone_comercial().equals("")) {
                llTelComercial.setVisibility(View.VISIBLE);
                telefoneComercial.setText(telefone_comercial.getTelefone_comercial());
            }
        }

        return view;
    }
}
