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
    public static final String ID = "ID";
    public static final String ALARM = "ALARM";
    public static final String CYCLE = "CYCLE";
    public static final String TABLE = "TABLE";

    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String alarm, String cycle, String table) {
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(ALARM, alarm);
        editor.putString(CYCLE, cycle);
        editor.putString(TABLE, table);

        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {

        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(ALARM, sharedPreferences.getString(ALARM, null));
        user.put(CYCLE, sharedPreferences.getString(CYCLE, null));
        user.put(TABLE, sharedPreferences.getString(TABLE, null));

        return user;
    }

    public String getCurrentCycle() {
        return sharedPreferences.getString(CYCLE, null);
    }

    public String getCurrentTable() {
        return sharedPreferences.getString(TABLE, null);
    }

    public String getCurrentID() {
        return sharedPreferences.getString(ID, null);
    }

    public void changeValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void logout() {

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }

}
