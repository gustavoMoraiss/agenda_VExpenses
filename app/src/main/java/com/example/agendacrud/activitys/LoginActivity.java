package com.example.agendacrud.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendacrud.R;
import com.example.agendacrud.dao.LoginDAO;
import com.example.agendacrud.domain.Login;
import com.example.agendacrud.util.SharedPreferencesHelper;
import com.example.agendacrud.util.SucessoDialog;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCadastro;
    private EditText editEmail, editSenha;
    private Button btnLogin;

    private LoginDAO loginDAO;
    private Login login;

    private boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (usuarioEstaLogado()) {
            startActivity(new Intent(this, ListagemContatoActivity.class));
            finish();

        } else {
            editEmail = findViewById(R.id.edit_email);
            editSenha = findViewById(R.id.edit_senha);
            btnLogin = findViewById(R.id.btn_login);
            txtCadastro = findViewById(R.id.txt_login);

            loginDAO = new LoginDAO(this);

            btnLogin.setOnClickListener(v -> {

                if (validaCampos()) {
                    if (loginDAO.validaLogin(editEmail.getText().toString(), editSenha.getText().toString())) {
                        openDialogSucesso(getString(R.string.login_realizado_sucesso));
                    } else {
                        Toast.makeText(LoginActivity.this, "Dados inválidos.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            txtCadastro.setOnClickListener(v -> signUpActivity(v));
        }
    }

    private boolean usuarioEstaLogado() {
        return SharedPreferencesHelper.getBoolean(this, SharedPreferencesHelper.SHARED_PREFERENCES_USUARIO_LOGADO);
    }

    public void signUpActivity(View view) {
        Intent intent = new Intent(this, NovoUsuarioActivity.class);
        startActivity(intent);
    }

    private boolean validaCampos() {
        login = new Login();
        login.setEmail(editEmail.getText().toString());
        login.setSenha(editSenha.getText().toString());

        ok = true;

        if (TextUtils.isEmpty(editEmail.getText().toString())) {
            editEmail.setError(getString(R.string.message_campo_obrigatorio));
            editEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            ok = false;
        }

        if (TextUtils.isEmpty(editSenha.getText().toString())) {
            editSenha.setError(getString(R.string.message_campo_obrigatorio));
            editSenha.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_failure));
            ok = false;
        }

        return ok;
    }

    private void openDialogSucesso(String mensagem) {
        SucessoDialog sucessoDialog = new SucessoDialog(mensagem);
        sucessoDialog.show(getSupportFragmentManager(), "");
    }

}