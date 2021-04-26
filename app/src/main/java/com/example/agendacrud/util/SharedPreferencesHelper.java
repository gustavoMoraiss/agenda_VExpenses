package com.example.agendacrud.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper {

    public static final String SHARED_PREFERENCES_USUARIO_LOGADO = "usuario_logado";
    public static final String SHARED_PREFERENCES_ID_LOGIN = "id_login";


    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static void putInt(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).apply();
    }

    public static void removeValue(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).apply();
    }

    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, -1);
    }

    public static boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }
}
