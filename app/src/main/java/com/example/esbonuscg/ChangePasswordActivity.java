package com.example.esbonuscg;

import static com.example.esbonuscg.SigninActivity.USER_EXTRA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {
    User user;
    TextView nome, city, dataNascita, error, passCorretta;
    Button indietroButton, modificaButton;
    EditText editPassword, editNPassword;
    ImageView resultImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().setBackgroundDrawableResource(R.drawable.cambiopassastro);
        Intent intent = getIntent();

        //Passaggio dell'intent dalla signIn activity alla home activity
        Serializable obj = intent.getSerializableExtra(SigninActivity.USER_EXTRA);

        //controllo che sia una istanza di person
        if(obj instanceof User){
            user =  (User) obj;
        }
        else {
            user = new User();
        }

        nome =  findViewById(R.id.username);
        city =  findViewById(R.id.city);
        dataNascita =  findViewById(R.id.date);
        resultImage = findViewById(R.id.resultImage);

        nome.setText("Username: " + user.getUsername());
        city.setText("Città: " + user.getCity());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        dataNascita.setText("Data di nascita: " + format.format(user.getBirthDate().getTime()));
        //dataNascita.setText("Username: " + user.getBirthDate());
        indietroButton = findViewById(R.id.indietroButton);
        modificaButton = findViewById(R.id.modificaButton);
        resultImage.setImageURI(user.getFoto());


        indietroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeActivity = new Intent(ChangePasswordActivity.this, HomeActivity.class);
                homeActivity.putExtra(USER_EXTRA, user);

                startActivity(homeActivity);
            }
        });


        modificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkInput();

            }
        });
    }

    void checkInput(){
        error = findViewById(R.id.error);
        editPassword = findViewById(R.id.NuovaPass);
        editNPassword = findViewById(R.id.NuovaPass2);
        passCorretta = findViewById(R.id.passCorretta);
        //Controllo i campi e conto gli errori
        int errors = 0;

        if(editPassword.getText().toString().length() == 0){
            //Graficamente deve apparire l'errore
            errors++;
            editPassword.setError("Inserire Password");
        }

        if(editNPassword.getText().toString().length() == 0){
            //Graficamente deve apparire l'errore
            errors++;
            editNPassword.setError("Inserire Password");
        }

        if(!editPassword.getText().toString().equals(editNPassword.getText().toString())){
            //Graficamente deve apparire l'errore
            errors++;
            editPassword.setError("Le due password devono essere uguali");
            editNPassword.setError("Le due password devono essere uguali");
        }else if (editPassword.getText().toString().equals(user.getPassword())){
            errors++;
            editPassword.setError("La password non può essere uguale a quella precedente");
            editNPassword.setError("La password non può essere uguale a quella precedente");
        }
        String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,1000}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(editPassword.getText().toString());


        if(!matcher.matches() && editPassword.getText().toString().length()!=0){
            errors++;
            editPassword.setError("La password deve essere lunga 8 caratteri e deve contenere almeno una lettera maiuscola, almeno una minuscola, un numero e un carattere speciale");
        }


        switch (errors){
            case 0:
                //Non ci sono errori, ma ce ne possono essere stati prima quindi devo far scomparire la TextView
                error.setVisibility(View.GONE);
                break;
            case 1:
                error.setVisibility(View.VISIBLE);
                error.setText("Si è verificato un errore");
                break;
            default:
                error.setVisibility(View.VISIBLE);
                error.setText("Si sono verificati " + errors + " errori");
                break;
        }
        if(errors==0){
            user.setPassword(editPassword.getText().toString());
            User u = Utenti.srcUtenteByUsername(user.getUsername());
            u.setPassword(editPassword.getText().toString());
            passCorretta.setText("Password cambiata correttamente. Ora premi \"Torna indietro\" per tornare alla tua Home!");
        }
    }
}