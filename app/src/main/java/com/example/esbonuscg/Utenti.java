package com.example.esbonuscg;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Utenti {

    public static ArrayList<User> utenti;

    public static void createDb(){
        if(utenti == null) {
            utenti = new ArrayList<User>();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Calendar date = Calendar.getInstance();
            date.set(1994, 1, 14, 0,0,0);
            format.format(date.getTime());
            //0 -> Gennaio, 1 -> Febbraio ecc

            File file = new File( "/Users/chiaraganga/AndroidStudioProjects/ProvaEB2/app/src/main/res/drawable/admin.jpeg");
            Uri uri = Uri.parse("android.resource://" + "com.example.esbonuscg" + "/" + R.drawable.admin);


            User admin = new User("admin", "admin", "admin", "Seattle", date, uri.toString());
            admin.setAdmin(true);
            utenti.add(admin);
        }
    }

    public static boolean addUtente(User utente){
        for (User u : utenti) {
            if(u.username.equals(utente.username))
                return false;
        }
        utenti.add(utente);
        return true;
    }
    public static User srcUtenteByUsername(String username){
        Log.d("username", username);
        for (User u : utenti) {
            if(u.username.equals(username)){
                Log.d("trovato", "trovato");
                return u;
            }
        }
        Log.d("nontrovato", "nontrovato");
        return null;
    }


    public static User srcUtente(String username, String password) {
        Log.d("username", username);
        Log.d("username", password);
        for (User u : utenti) {
            if(u.username.equals(username)  && u.password.equals(password)){
                Log.d("trovato", "trovato");
                return u;
            }
        }
        Log.d("nontrovato", "nontrovato");
        return null;
    }


}