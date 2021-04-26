package com.example.agendacrud.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendacrud.R;
import com.example.agendacrud.dao.LoginDAO;
import com.example.agendacrud.domain.Login;
import com.example.agendacrud.util.CadastroDialog;

public class NovoUsuarioActivity extends AppCompatActivity {

    private EditText editEmail, confirmEmail, editSenha, confirmSenha;
    private Button btnRegistrar;

    private Login login;
    private LoginDAO loginDAO;

    private boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        loginDAO = new LoginDAO(this);

        editEmail = findViewById(R.id.edit_email);
        confirmEmail = findViewById(R.id.confirm_email);

        editSenha = findViewById(R.id.edit_senha);
        confirmSenha = findViewById(R.id.confirm_senha);

        btnRegistrar = findViewById(R.id.btn_salvar);

        btnRegistrar.setOnClickListener(v -> {
            if(validaCampos() && validaEmail()){
                concluirCadastro(v);
                openDialogSucesso("Cadastro realizado!");
            }
        });
    }

    private boolean validaEmail() {
        login = new Login();
        login.setEmail(editEmail.getText().toString());
        login.setSenha(editSenha.getText().toString());
        if(loginDAO.validaEmail(login.getEmail())) {
            Toast.makeText(this, R.string.email_ja_cadastrado, Toast.LENGTH_SHORT).show();
            return false;
        } 
        return true;
    }

    private boolean validaSenha() {
        if (editSenha.getText().toString() != confirmSenha.getText().toString()) {
            editSenha.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            confirmSenha.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            return false;
        }
        return true;
    }

    private void concluirCadastro(View view) {
        login = new Login();
        login.setEmail(editEmail.getText().toString());
        login.setSenha(editSenha.getText().toString());
        if(loginDAO.validaEmail(login.getEmail())) {
            Toast.makeText(this, R.string.email_ja_cadastrado, Toast.LENGTH_SHORT).show();
        } else{
            loginDAO.inserir(login);
        }
    }

    private void openDialogSucesso(String mensagem) {
        CadastroDialog cadastroDialog = new CadastroDialog(mensagem);
        cadastroDialog.show(getSupportFragmentManager(), "");
    }

    private boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    private boolean isEmailValido(String email) {
        return (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean validaCampos(){
        login = new Login();
        login.setEmail(editEmail.getText().toString());
        login.setSenha(editSenha.getText().toString());

        ok = true;

        if(TextUtils.isEmpty(editEmail.getText().toString())){
            editEmail.setError(getString(R.string.message_campo_obrigatorio));
            editEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            ok = false;
        }

        if(TextUtils.isEmpty(editSenha.getText().toString())){
            editSenha.setError(getString(R.string.message_campo_obrigatorio));
            editSenha.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            ok = false;
        }

        if(TextUtils.isEmpty(confirmEmail.getText().toString())){
            confirmEmail.setError(getString(R.string.message_campo_obrigatorio));
            confirmEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            ok = false;
        }

        if(TextUtils.isEmpty(confirmSenha.getText().toString())){
            confirmSenha.setError(getString(R.string.message_campo_obrigatorio));
            confirmSenha.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            ok = false;
        }

        if(!login.getEmail().equals(confirmEmail.getText().toString())){
            confirmEmail.setError(getString(R.string.message_email_difference));
            ok = false;
        }

        if(!login.getSenha().equals(confirmSenha.getText().toString())){
            confirmEmail.setError(getString(R.string.message_password_difference));
            ok = false;
        }

        return ok;
    }
}