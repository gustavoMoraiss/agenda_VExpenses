package com.example.agendacrud.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.agendacrud.activitys.CadastroContatoActivity;

public class ZipCodeListener implements TextWatcher {
    private final Context context;

    public ZipCodeListener( Context context ){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();

        if( zipCode.length() == 8 ){
            new AddressRequest((CadastroContatoActivity) context).execute();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
