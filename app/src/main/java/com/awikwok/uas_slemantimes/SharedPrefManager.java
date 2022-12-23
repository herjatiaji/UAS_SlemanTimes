package com.awikwok.uas_slemantimes;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPrefManager {
    public static final String SP_SLEMAN_TIMES="spSlemanTimes";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    public static final String SP_USERNAME ="spUsername";
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    private static SharedPrefManager instance = null;


    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_SLEMAN_TIMES, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }
    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();

    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }
    public void clearSharedPref(@NonNull Context context) {
        SharedPreferences pref = context.getSharedPreferences(SP_USERNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
    public String getSpUsername(){
        return sp.getString(SP_USERNAME,"");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

}

