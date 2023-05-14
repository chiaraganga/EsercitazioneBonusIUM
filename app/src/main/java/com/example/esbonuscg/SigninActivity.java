package com.example.esbonuscg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.intellij.lang.annotations.RegExp;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SigninActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    //Inizializzazione delle variabili

    EditText editUsername, editPassword, editNPassword, editDate, editCity;

    //Riferimento al pulsante
    Button signInButton, fpick, annulla;

    TextView error;

    RecyclerView recyclerView;

    ArrayList<Uri> arrayList = new ArrayList<>();
    User user;


    public static final String USER_EXTRA = "com.example.esbonuscg.User";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getWindow().setBackgroundDrawableResource(R.drawable.signinastro);

        //Assegno le variabili

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassw);
        editNPassword = findViewById(R.id.editNPassw);
        editCity = findViewById(R.id.editCity);
        editDate = findViewById(R.id.editDate);
        signInButton = findViewById(R.id.signInButton);
        fpick = findViewById(R.id.fpick);
        annulla = findViewById(R.id.annulla);
        error = findViewById(R.id.error);
        recyclerView = findViewById(R.id.recycler_view);

        user = new User();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //per passare da una Activity a un'altra

                if(checkInput()){

                    updateUser();

                    //costruttore prende l'Activity di partenza e quella di destinazione
                    Intent prova = new Intent(SigninActivity.this, LoginActivity.class);
                    prova.putExtra(USER_EXTRA, user);
                    startActivity(prova);
                }
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prova = new Intent(SigninActivity.this, LoginActivity.class);
                startActivity(prova);
            }
        });

        fpick.setOnClickListener(view -> {
            //Definisco i permessi della camera
            String[] strings  = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
            //Controllo le condizioni
            if(EasyPermissions.hasPermissions(this,strings)){
                //Quando sono già stati dati
                //Creo il costruttore
                imagePicker();
            }else{
                //Quando i permessi sono negati
                //Richiesta permessi
                EasyPermissions.requestPermissions(
                        this,
                        "L'applicazione desidera accedere alla tua camera e foto",
                        100,
                        strings
                );
            }
        });
         /*  editDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(b){
                editDate.setRawInputType(InputType.TYPE_NULL);
                    showDialog();

                }

            }
        }); */

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerFragment().show(getSupportFragmentManager(), "dialog");

            }
        });

    }
    void doPositiveClick(Calendar date){
        this.user.setBirthDate(date);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        editDate.setText(format.format(date.getTime()));;

    }

    /*void showDialog(){
       DialogFragment newFragment = new DatePickerFragment();
       newFragment.show(getSupportFragmentManager(), "dialog");

    }*/

    boolean checkInput(){
        //Controllo i campi e conto gli errori
        int errors = 0;
        if(editUsername.getText().toString().length() == 0){
            //Graficamente deve apparire l'errore
            errors++;
            editUsername.setError("Inserire l'username");
        }

        for (User u : Utenti.utenti) {
            if (u.username.equals(editUsername.getText().toString())){
                //Graficamente deve apparire l'errore
                errors++;
                editUsername.setError("Username già presente");
            }

        }

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
        }
        String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,1000}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(editPassword.getText().toString());


        if(!matcher.matches() && editPassword.getText().toString().length()!=0){
            errors++;
            editPassword.setError("La password deve essere lunga 8 caratteri e deve contenere almeno una lettera maiuscola, almeno una minuscola, un numero e un carattere speciale");
        }

        if(editCity.getText().toString().length() == 0){
            //Graficamente deve apparire l'errore
            errors++;
            editCity.setError("Inserire la città");
        }
        if(editDate.getText().toString().length() == 0){
            //Graficamente deve apparire l'errore
            errors++;
            editDate.setError("Inserire la data di nascita");
        }
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String date = editDate.getText().toString();
            LocalDate newDate = LocalDate.parse(date, formatter);

            Log.d("Anno", "value = " + newDate);
            Period period = Period.between(newDate, LocalDate.now());
            // con anno 1995 e anno corrente 2023 avrò 28, invece se metto una data nel 2022 avrò 1
            Log.d("Anno", "value = " + period.getYears());

            if (period.getYears() < 18) {
                errors++;
                editDate.setError("Devi essere maggiorenne per iscriverti");
            }

            else{
                editDate.setError(null);
            }
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
        return errors == 0; //Non ci sono errori
    }

    void updateUser(){

        //prende ciò che è contenuto nell'editText e lo imposta nei campi dell'user creato
        String username = this.editUsername.getText().toString().trim();
        this.user.setUsername(username);

        String password = this.editPassword.getText().toString().trim();
        this.user.setPassword(password);

        String npassw = this.editNPassword.getText().toString();
        this.user.setNpassw(npassw);

        String city = this.editCity.getText().toString();
        this.user.setCity(city);

        if(arrayList.size()>0){
            Uri uri = arrayList.get(0);
            this.user.setFoto(uri);
            Toast.makeText(this, "Registrazione completata con successo!", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "Registrazione completata, ma non hai inserito l'immagine", Toast.LENGTH_LONG).show();
            this.user.setFoto(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.default_img));
        }
        boolean aggiunta = Utenti.addUtente(user);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Per i risultati
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions,grantResults,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Controllo delle condizioni
        if(resultCode == RESULT_OK && data != null){
            //Quando l'activity contiene dati, controllo
            if(requestCode == FilePickerConst.REQUEST_CODE_PHOTO){
                //Richiesta di una foto, inizializzo array list
                arrayList = data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);

                if(arrayList.isEmpty()){
                    Toast.makeText(this, "Non hai inserito una immagine", Toast.LENGTH_LONG).show();
                    Uri defaultUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.default_img);
                    arrayList.add(defaultUri);
                }
                //Layout manager
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                //Adapter
                recyclerView.setAdapter(new Adp(arrayList));

            }
        }
    }

    private void imagePicker() {
        //Apre la scelta delle immagini da selezionare
        FilePickerBuilder.getInstance()
                .setActivityTitle("Seleziona l'immagine")
                                .setSpan(FilePickerConst.SPAN_TYPE.FOLDER_SPAN, 1)
                                .setSpan(FilePickerConst.SPAN_TYPE.DETAIL_SPAN, 1)
                                .setMaxCount(1)
                                .setSelectedFiles(arrayList)
                                .setActivityTheme(R.style.PhotoTheme)
                                .pickPhoto(this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //Controllo le condizioni
        if(requestCode == 100  && perms.size() == 2)
            //Quando sono concessi chiamo il metodo
            imagePicker();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //Controllo le condizioni
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }else {
            Toast.makeText(getApplicationContext(), "Permesso negato", Toast.LENGTH_SHORT).show();
        }
    }
}










