package com.example.esbonuscg;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adp extends RecyclerView.Adapter<Adp.ViewHolder> {
    //Inizializzo le variabili
    ArrayList<Uri> arrayList;

    //Creazione del costruttore
    public Adp(ArrayList<Uri> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inizializzo le variabili per la view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Stampa dell'immagine
        holder.ivImage.setImageURI(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        //Grandeza della lista
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Inizializzo le variabili
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Assegno la variabile
            ivImage = itemView.findViewById(R.id.iv_img);
        }
    }
}
