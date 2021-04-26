package com.example.agendacrud.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendacrud.R;
import com.example.agendacrud.dao.ContatoDAO;
import com.example.agendacrud.dao.EnderecoDAO;
import com.example.agendacrud.dao.TelefoneDAO;
import com.example.agendacrud.domain.Contato;
import com.example.agendacrud.domain.Endereco;
import com.example.agendacrud.domain.Telefone;
import com.example.agendacrud.util.SharedPreferencesHelper;
import com.example.agendacrud.util.SucessoDialog;
import com.example.agendacrud.util.Util;
import com.example.agendacrud.util.ZipCodeListener;

import java.util.ArrayList;
import java.util.List;

public class CadastroContatoActivity extends AppCompatActivity {

    public static final String EXTRA_INTENT_USUARIO = "usuario";

    private EditText editNome, editTelefone, editTelefoneComercial, editTelefoneResidencial, editCep, editLogradouro, editNumero, editCidade, editBairro, editComplemento;
    private EditText editCep2, editLogradouro2, editNumero2, editCidade2, editBairro2, editComplemento2;
    private Button btnAddTelefone, btnRemoveTelefone, btnSalvar, btnRemoveEndereco, btnAddEndereco;
    private TextView tvTelefoneComercial, tvTelefoneResidencial, tvEndereco;
    private Spinner spEstados, spEstados2;

    private LinearLayout llCep, llRuaComplemento, llBairroNumero, llCidade;

    private ContatoDAO contatoDAO;
    private Contato contato = null;

    private EnderecoDAO enderecoDAO;
    private Endereco endereco = null;

    private TelefoneDAO telefoneDAO;
    private Telefone tel;

    private Util util;
    private boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        contatoDAO = new ContatoDAO(this);
        enderecoDAO = new EnderecoDAO(this);
        telefoneDAO = new TelefoneDAO(this);

        llCep = findViewById(R.id.ll_cep_2);
        llCidade = findViewById(R.id.ll_cidade_2);
        llRuaComplemento = findViewById(R.id.ll_rua_complemento_2);
        llBairroNumero = findViewById(R.id.ll_bairro_numero_2);

        editNome = findViewById(R.id.edit_nome);
        editTelefone = findViewById(R.id.edit_telefone);
        editTelefoneComercial = findViewById(R.id.edit_telefone_residencial);
        editTelefoneResidencial = findViewById(R.id.edit_telefone_comercial);
        editLogradouro = findViewById(R.id.edit_rua);
        editCidade = findViewById(R.id.edit_cidade);
        editBairro = findViewById(R.id.edit_bairro);
        editNumero = findViewById(R.id.edit_numero);
        editComplemento = findViewById(R.id.edit_complemento);
        editCep = findViewById(R.id.edit_cep);
        editLogradouro2 = findViewById(R.id.edit_rua_2);
        editCidade2 = findViewById(R.id.edit_cidade_2);
        editBairro2 = findViewById(R.id.edit_bairro_2);
        editNumero2 = findViewById(R.id.edit_numero_2);
        editComplemento2 = findViewById(R.id.edit_complemento_2);
        editCep2 = findViewById(R.id.edit_cep_2);

        btnAddTelefone = findViewById(R.id.add_telefone);
        btnRemoveTelefone = findViewById(R.id.remove_telefone);
        btnSalvar = findViewById(R.id.btn_salvar);
        btnRemoveEndereco = findViewById(R.id.remove_endereco);
        btnAddEndereco = findViewById(R.id.add_endereco);

        spEstados = findViewById(R.id.sp_estado);
        spEstados2 = findViewById(R.id.sp_estado_2);

        tvTelefoneComercial = findViewById(R.id.text_celular_residencial);
        tvTelefoneResidencial = findViewById(R.id.text_celular_comercial);
        tvEndereco = findViewById(R.id.sub_titulo_endereco_2);

        editCep.addTextChangedListener(new ZipCodeListener(this));
        editCep2.addTextChangedListener(new ZipCodeListener(this));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        android.R.layout.simple_spinner_item);
        spEstados.setAdapter(adapter);

        ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        android.R.layout.simple_spinner_item);
        spEstados2.setAdapter(adapter);

        util = new Util(this,
                R.id.edit_rua,
                R.id.edit_complemento,
                R.id.edit_bairro,
                R.id.edit_cidade,
                R.id.sp_estado);

        btnSalvar.setOnClickListener(v -> {
            if (validaCadastroMinimo()) {
                salvar(v);
            }
        });

        btnAddEndereco.setOnClickListener(v -> {
            if (tvEndereco.getVisibility() == View.GONE) {
                tvEndereco.setVisibility(View.VISIBLE);
            }
            if (llBairroNumero.getVisibility() == View.GONE) {
                llBairroNumero.setVisibility(View.VISIBLE);
            }
            if (llRuaComplemento.getVisibility() == View.GONE) {
                llRuaComplemento.setVisibility(View.VISIBLE);
            }
            if (llCidade.getVisibility() == View.GONE) {
                llCidade.setVisibility(View.VISIBLE);
            }
            if (llCep.getVisibility() == View.GONE) {
                llCep.setVisibility(View.VISIBLE);
            }
        });

        btnRemoveEndereco.setOnClickListener(v -> {
            if (tvEndereco.getVisibility() != View.GONE) {
                tvEndereco.setVisibility(View.GONE);
            }
            if (llBairroNumero.getVisibility() != View.GONE) {
                llBairroNumero.setVisibility(View.GONE);
                editBairro2.setText("");
                editNumero2.setText("");
            }
            if (llRuaComplemento.getVisibility() != View.GONE) {
                llRuaComplemento.setVisibility(View.GONE);
                editLogradouro2.setText("");
                editComplemento2.setText("");
            }
            if (llCidade.getVisibility() != View.GONE) {
                llCidade.setVisibility(View.GONE);
                editCidade2.setText("");
            }
            if (llCep.getVisibility() != View.GONE) {
                llCep.setVisibility(View.GONE);
                editCep2.setText("");
            }
        });

        btnAddTelefone.setOnClickListener(v -> {
            if (editTelefoneComercial.getVisibility() == View.GONE) {
                editTelefoneComercial.setVisibility(View.VISIBLE);
                tvTelefoneComercial.setVisibility(View.VISIBLE);
            } else if (editTelefoneResidencial.getVisibility() == View.GONE) {
                editTelefoneResidencial.setVisibility(View.VISIBLE);
                tvTelefoneResidencial.setVisibility(View.VISIBLE);
            }
        });

        btnRemoveTelefone.setOnClickListener(v -> {
            if (editTelefoneResidencial.getVisibility() == View.VISIBLE) {
                editTelefoneResidencial.setText("");
                editTelefoneResidencial.setVisibility(View.GONE);
                tvTelefoneResidencial.setVisibility(View.GONE);
            } else if (editTelefoneComercial.getVisibility() == View.VISIBLE) {
                editTelefoneComercial.setText("");
                editTelefoneComercial.setVisibility(View.GONE);
                tvTelefoneComercial.setVisibility(View.GONE);
            }
        });

        Intent it = getIntent();
        if (it.hasExtra(EXTRA_INTENT_USUARIO)) {
            btnSalvar.setText(getString(R.string.bt_atualizar));

            contato = (Contato) it.getSerializableExtra(EXTRA_INTENT_USUARIO);
            editNome.setText(contato.getNome());
            List<Telefone> telefones = telefoneDAO.obterTodos(contato.getId());
            editTelefone.setText(contato.getTelefones().get(0).getTelefone_celular());

            if (telefones.get(0).getTelefone_comercial() != null) {
                editTelefoneComercial.setText(telefones.get(0).getTelefone_comercial());
                editTelefoneComercial.setVisibility(View.VISIBLE);
                tvTelefoneComercial.setVisibility(View.VISIBLE);
            }

            if (telefones.get(0).getTelefone_residencial() != null) {
                editTelefoneResidencial.setText(telefones.get(0).getTelefone_residencial());
                editTelefoneResidencial.setVisibility(View.VISIBLE);
                tvTelefoneResidencial.setVisibility(View.VISIBLE);
            }

            editCep.removeTextChangedListener(new ZipCodeListener(this));
            final Endereco endereco = contato.getEnderecos().get(0);
            editCep.setText(endereco.getCep());
            editLogradouro.setText(endereco.getLogradouro());
            editCidade.setText(endereco.getLocalidade());
            editNumero.setText(endereco.getNumber());
            editBairro.setText(endereco.getBairro());
            editComplemento.setText(endereco.getComplemento() != null ? endereco.getComplemento() : "");

            if (contato.getEnderecos().size() > 1) {
                final Endereco endereco2 = contato.getEnderecos().get(1);
                if (tvEndereco.getVisibility() == View.GONE) {
                    tvEndereco.setVisibility(View.VISIBLE);
                }
                if (llBairroNumero.getVisibility() == View.GONE) {
                    llBairroNumero.setVisibility(View.VISIBLE);
                    editNumero2.setText(endereco2.getNumber());
                    editBairro2.setText(endereco2.getBairro());
                }
                if (llRuaComplemento.getVisibility() == View.GONE) {
                    llRuaComplemento.setVisibility(View.VISIBLE);
                    editLogradouro2.setText(endereco2.getLogradouro());
                    editComplemento2.setText(endereco2.getComplemento() != null ? endereco2.getComplemento() : "");
                }
                if (llCidade.getVisibility() == View.GONE) {
                    llCidade.setVisibility(View.VISIBLE);
                    editCidade2.setText(endereco2.getLocalidade());
                }
                if (llCep.getVisibility() == View.GONE) {
                    llCep.setVisibility(View.VISIBLE);
                    editCep2.setText(endereco2.getCep());
                }
            }
        }
    }

    public void salvar(View view) {
        if (contato == null) {
            List<Telefone> telefones = new ArrayList<>();
            List<Endereco> enderecos = new ArrayList<>();

            // if(validaCadastroMinimo()) {
            contato = new Contato();
            contato.setNome(editNome.getText().toString());
            contato.setId_login(SharedPreferencesHelper.getInt(this, SharedPreferencesHelper.SHARED_PREFERENCES_ID_LOGIN));
            long idUsuario = contatoDAO.inserir(contato);
            //   }
            tel = new Telefone();
            tel.setTelefone_celular(editTelefone.getText().toString());

            if (!editTelefoneComercial.getText().toString().equals("")) {
                tel.setTelefone_comercial(editTelefoneComercial.getText().toString());
            }

            if (!editTelefoneResidencial.getText().toString().equals("")) {
                tel.setTelefone_residencial(editTelefoneResidencial.getText().toString());
            }

            telefoneDAO.inserir(tel, (int) idUsuario);

            endereco = new Endereco();
            endereco.setBairro(getField(R.id.edit_bairro));
            endereco.setCep(getField(R.id.edit_cep));
            endereco.setLocalidade(getField(R.id.edit_cidade));
            endereco.setLogradouro(getField(R.id.edit_rua));
            endereco.setUf(getSpinnerText(R.id.sp_estado));
            endereco.setNumber(getField(R.id.edit_numero));
            enderecoDAO.inserir(endereco, (int) idUsuario);
            enderecos.add(endereco);

            if (getCepSecundario() && validaSegundoEndereco()) {
                endereco = new Endereco();
                endereco.setBairro(getField(R.id.edit_bairro_2));
                endereco.setCep(getField(R.id.edit_cep_2));
                endereco.setLocalidade(getField(R.id.edit_cidade_2));
                endereco.setLogradouro(getField(R.id.edit_rua_2));
                endereco.setUf(getSpinnerText(R.id.sp_estado_2));
                endereco.setNumber(getField(R.id.edit_numero_2));
                enderecoDAO.inserir(endereco, (int) idUsuario);
            }

            contato.setTelefones(telefones);
            contato.setEnderecos(enderecos);

            openDialogSucesso(getString(R.string.contato_criado_sucesso));
        } else {
            contato.setNome(editNome.getText().toString());
            Telefone tel = contato.getTelefones().get(0);
            contatoDAO.atualizar(contato);

            if (!editTelefone.getText().toString().equals("")) {
                tel.setTelefone_celular(editTelefone.getText().toString());
            }
            if (!editTelefoneComercial.getText().toString().equals("")) {
                tel.setTelefone_comercial(editTelefoneComercial.getText().toString());
            }

            if (!editTelefoneResidencial.getText().toString().equals("")) {
                tel.setTelefone_residencial(editTelefoneResidencial.getText().toString());
            }
            if (getCepSecundario() && validaSegundoEndereco()) {
                endereco = new Endereco();
                endereco.setBairro(getField(R.id.edit_bairro_2));
                endereco.setCep(getField(R.id.edit_cep_2));
                endereco.setLocalidade(getField(R.id.edit_cidade_2));
                endereco.setLogradouro(getField(R.id.edit_rua_2));
                endereco.setUf(getSpinnerText(R.id.sp_estado_2));
                endereco.setNumber(getField(R.id.edit_numero_2));
                enderecoDAO.inserir(endereco, contato.getId());
            }
            telefoneDAO.atualizar(tel);
            openDialogSucesso(getString(R.string.contato_atualizado_sucesso));
        }
    }

    private String getZipCode() {
        if (getCepSecundario()) {
            return editCep2.getText().toString();
        } else {
            return editCep.getText().toString();
        }
    }

    public String getUriRequest() {
        return "https://viacep.com.br/ws/" + getZipCode() + "/json/";
    }

    public void lockFields(boolean isToLock) {
        util.lockFields(isToLock);
    }

    public void setAddressFields(Endereco endereco) {
        editLogradouro.setText(endereco.getLogradouro());
        editBairro.setText(endereco.getBairro());
        editCidade.setText(endereco.getLocalidade());
        setSpinner(R.id.sp_estado, R.array.states, endereco.getUf());
    }

    public void setAddressFields2(Endereco endereco) {
        editLogradouro2.setText(endereco.getLogradouro());
        editBairro2.setText(endereco.getBairro());
        editCidade2.setText(endereco.getLocalidade());
        setSpinner(R.id.sp_estado_2, R.array.states, endereco.getUf());
    }

    private void setSpinner(int fieldId, int arrayId, String uf) {
        Spinner spinner = findViewById(fieldId);
        String[] states = getResources().getStringArray(arrayId);

        for (int i = 0; i < states.length; i++) {
            if (states[i].endsWith("(" + uf + ")")) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private String getField(int fieldId) {
        return ((EditText) findViewById(fieldId)).getText().toString();
    }

    private String getSpinnerText(int fieldId) {
        Spinner spinner = findViewById(fieldId);
        return spinner.getSelectedItem().toString();
    }

    private void openDialogSucesso(String mensagem) {
        SucessoDialog sucessoDialog = new SucessoDialog(mensagem);
        sucessoDialog.show(getSupportFragmentManager(), "");
    }

    public boolean getCepSecundario() {
        return llCep.getVisibility() == View.VISIBLE;
    }

    private boolean validaSegundoEndereco() {
        ok = true;
        if (TextUtils.isEmpty(editBairro2.getText().toString())) {
            ok = false;
        }
        if (TextUtils.isEmpty(editNumero2.getText().toString())) {
            ok = false;
        }
        if (TextUtils.isEmpty(editLogradouro2.getText().toString())) {
            ok = false;
        }
        if (TextUtils.isEmpty(editCidade2.getText().toString())) {
            ok = false;
        }
        if (TextUtils.isEmpty(editCep2.getText().toString())) {
            ok = false;
        }
        return ok;
    }

    private boolean validaCadastroMinimo() {
        ok = true;
        if (TextUtils.isEmpty(editNome.getText().toString().trim())) {
            editNome.setError(getString(R.string.message_campo_obrigatorio));
            ok = false;
        }
        if (TextUtils.isEmpty(editTelefone.getText().toString().trim())) {
            editTelefone.setError(getString(R.string.message_campo_obrigatorio));
            ok = false;
        }
        return ok;
    }

}