package com.example.esbonuscg;

import android.net.Uri;

import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable {
    public String username, password, npassw, city;
    public Calendar birthDate;
    public String foto;
    boolean isAdmin;

    public User () {
        this.setUsername("");
        this.setPassword("");
        this.setNpassw("");
        this.setCity("");
        // this.setBirthDate("");
        // this.setFoto();
    }

    public User(String username, String password, String npassw, String city, Calendar birthDate, String foto) {
        this.username = username;
        this.password = password;
        this.npassw = npassw;
        this.city = city;
        this.birthDate = birthDate;
        this.foto = foto;
        this.isAdmin = false;
    }

    public void setAdmin(boolean adminState){
        this.isAdmin = adminState;
    }

    public Uri getFoto() {
        return Uri.parse(foto);
    }

    public void setFoto(Uri foto) {
        this.foto = foto.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNpassw() {
        return npassw;
    }

    public void setNpassw(String npassw) {
        this.npassw = npassw;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }
}