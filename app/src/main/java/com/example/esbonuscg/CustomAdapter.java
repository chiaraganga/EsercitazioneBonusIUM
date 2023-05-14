package com.example.esbonuscg;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String utentiList[];
    String utentiFoto[];
    LayoutInflater inflter;
    //Context context;


    public CustomAdapter(Context applicationContext, String[] utentiList, String[] utentiFoto) {
        this.context = context;
        this.utentiList = utentiList;
        this.utentiFoto = utentiFoto;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return utentiList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView country = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        country.setText(utentiList[i]);
        icon.setImageURI(Uri.parse(utentiFoto[i]));


        Switch toggle = view.findViewById(R.id.switchButton);
        Boolean isThisAdmin = Utenti.srcUtenteByUsername(country.getText().toString()).isAdmin;
        toggle.setChecked(isThisAdmin);
        if( Utenti.srcUtenteByUsername(country.getText().toString()).getUsername().equals("admin") ){
            toggle.setVisibility(View.INVISIBLE);
        }
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (User u : Utenti.utenti) {
                    if (u.getUsername() == utentiList[i] && u.getUsername() != "admin") {
                        u.setAdmin(!u.isAdmin);

                    }
                }
            }

        });

        //icon.setImageResource(utentiFoto[i]);
        return view;
    }
}