package com.example.agendacrud.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.agendacrud.activitys.ListagemContatoActivity;

public class SucessoDialog extends AppCompatDialogFragment {

    private final String mensagem;

    public SucessoDialog(String mensagem) {
        this.mensagem = mensagem;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sucesso!")
              .setMessage(mensagem)
              .setPositiveButton("OK", (dialog, which) -> {
                  Intent it = new Intent(getActivity(), ListagemContatoActivity.class);
                  startActivity(it);
                  getActivity().finish();
                });
        return builder.create();
    }
}
