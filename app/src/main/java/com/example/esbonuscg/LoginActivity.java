package com.example.esbonuscg;

import static com.example.esbonuscg.SigninActivity.USER_EXTRA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;

    //Riferimento al pulsante
    Button loginButton;

    TextView signIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawableResource(R.drawable.loginastro);

        Log.d("myTag", "This is my message");

        editUsername = findViewById(R.id.usernm);
        editPassword = findViewById(R.id.passw);
        loginButton = findViewById(R.id.loginButton);
        signIn = findViewById(R.id.signIn);
        signIn.setPaintFlags(signIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signIn.setText("Non hai un account? Registrati!");

        Utenti.createDb();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //costruttore prende l'Activity di partenza e quella di destinazione dal Login alla Registrazione
                Intent prova = new Intent(LoginActivity.this, SigninActivity.class);

                startActivity(prova);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User iscritto = Utenti.srcUtente(editUsername.getText().toString().trim(), editPassword.getText().toString().trim());

                if(iscritto == null){
                    Toast.makeText(LoginActivity.this, "Username o password errati, riprova o registrati se non sei iscritto", Toast.LENGTH_SHORT).show();

                } else {
                    Intent prova = new Intent(LoginActivity.this, HomeActivity.class);
                    prova.putExtra(USER_EXTRA, iscritto);
                    startActivity(prova);

                }
            }
        });
    }
}