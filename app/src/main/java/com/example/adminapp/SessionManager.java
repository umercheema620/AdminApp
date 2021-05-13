package com.example.adminapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGGED = "IsLoggedIn";
    public static final String KEY_FULLNAME = "name";
    public static final String KEY_PHONE = "phone";


    public SessionManager(Context _context){
        context = _context;
        sharedPreferences = context.getSharedPreferences("loginSession",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String name, String phone){
        editor.putBoolean(IS_LOGGED,true);
        editor.putString(KEY_FULLNAME,name);
        editor.putString(KEY_PHONE,phone);

        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String, String> user = new HashMap<String,String>();
        user.put(KEY_FULLNAME,sharedPreferences.getString(KEY_FULLNAME,null));
        user.put(KEY_PHONE,sharedPreferences.getString(KEY_PHONE,null));

        return user;
    }

    public boolean checkLogin(){
        if(sharedPreferences.getBoolean(IS_LOGGED,false)){
            return true;
        }else{
            return false;
        }
    }

    public void LogOut(){
        editor.clear();
        editor.commit();
    }
}
