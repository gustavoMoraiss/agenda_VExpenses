package com.example.agendacrud.util;

import android.os.AsyncTask;

import com.example.agendacrud.activitys.CadastroContatoActivity;
import com.example.agendacrud.domain.Endereco;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

public class AddressRequest extends AsyncTask<Void, Void, Endereco> {
    private final WeakReference<CadastroContatoActivity> activity;
    private boolean ok = false;

    public AddressRequest(CadastroContatoActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.get().lockFields( true );
    }

    @Override
    protected Endereco doInBackground(Void... voids) {

        try{
            String jsonString = JsonRequest.request( activity.get().getUriRequest() );
            Gson gson = new Gson();
            return gson.fromJson(jsonString, Endereco.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Endereco address) {
        super.onPostExecute(address);

        if( activity.get() != null ){
            activity.get().lockFields( false );


            if( address != null ){
                if(activity.get().getCepSecundario()){
                    activity.get().setAddressFields2(address);
                }else{
                    activity.get().setAddressFields(address);
                }

            }
        }
    }
}
