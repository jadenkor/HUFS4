package com.example.hufs4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public  static final String ID = "ID";
    public  static final String HUFS_NOTICE = "HUFS_NOTICE";
    public  static final String BACHELOR_NOTICE = "BACHELOR_NOTICE";
    public  static final String SCHOLARSHIP_NOTICE = "SCHOLARSHIP_NOTICE";
    public  static final String E_NOTICE = "E_NOTICE";
    public  static final String E_ASSIGNMENT = "E_ASSIGNMENT";
    public  static final String E_LECTURENOTE = "E_LECTURENOTE";
    public  static final String E_ASSIGNMENT2 = "E_ASSIGNMENT2";
    public  static final String E_CYBERCLASS = "E_CYBERCLASS";
    public  static final String CYCLE = "CYCLE";


    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public  void createSession(String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.apply();
    }

    public boolean isLogin(){
        return  sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if (!this.isLogin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }
}
