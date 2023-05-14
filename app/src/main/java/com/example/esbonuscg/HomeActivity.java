package com.example.esbonuscg;

import static com.example.esbonuscg.SigninActivity.USER_EXTRA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    ImageView resultImage;
    User user;
    TextView resultUsername, resultPassw, resultCity, resultDate, welcomeUser, changePwd;
    ArrayList<Uri> arrayList = new ArrayList<>();
    Button logoutButton;

    private int defaultImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        if(user.isAdmin){

            String utentiList[] = new String[Utenti.utenti.size()];
            String utentiFoto[] = new String[Utenti.utenti.size()];
            int i = 0;
            for(User u :Utenti.utenti){
                utentiFoto[i] = u.getFoto().toString();
                utentiList[i] = u.getUsername();
                i= i+1;
                Log.d("usr", u.getUsername());
            }
            setContentView(R.layout.activity_admin);
            ListView simpleList = (ListView)findViewById(R.id.simpleListView);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), utentiList, utentiFoto);
            simpleList.setAdapter(customAdapter);


            Utility.setListViewHeightBasedOnChildren(simpleList);
            SearchView simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView);


            simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<String> utentiAList = new ArrayList<>();
                    ArrayList<String> utentiAFoto = new ArrayList<>();

                    int i = 0;
                    for(User u :Utenti.utenti){
                        if(u.getUsername().toLowerCase().contains(newText.toLowerCase())){
                            utentiAFoto.add(u.getFoto().toString());
                            utentiAList.add(u.getUsername());
                            i= i+1;
                        }
                    }
                    ListView simpleList = (ListView)findViewById(R.id.simpleListView);
                    String utentiList[] = new String[utentiAList.size()];
                    String utentiFoto[] = new String[utentiAFoto.size()];
                    for (int y = 0; y < utentiAList.size(); y++) {
                        utentiFoto[y] = utentiAFoto.get(y);
                        utentiList[y] = utentiAList.get(y);


                    }
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), utentiList, utentiFoto);
                    simpleList.setAdapter(customAdapter);

                    Utility.setListViewHeightBasedOnChildren(simpleList);


                    return false;
                }
            });
        }else {
            setContentView(R.layout.activity_home);
        }



        getWindow().setBackgroundDrawableResource(R.drawable.homeastro);

        welcomeUser =  findViewById(R.id.welcome);
        resultUsername = findViewById(R.id.resultUsername);
        resultPassw = findViewById(R.id.resultPassw);
        resultCity = findViewById(R.id.resultCity);
        resultDate = findViewById(R.id.resultDate);
        logoutButton =findViewById(R.id.logoutButton);

        resultImage = findViewById(R.id.resultImage);
        defaultImage = R.drawable.default_img;
        changePwd = findViewById(R.id.changePwd);
        if( user.getUsername().equals( "admin")){
            //changePwd.setVisibility(View.INVISIBLE);
            changePwd.setText("");
        }


        updateTextViews();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prova = new Intent(HomeActivity.this, LoginActivity.class);

                startActivity(prova);
            }
        });


        if( !user.getUsername().equals("admin")) {
            changePwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent changeActivity = new Intent(HomeActivity.this, ChangePasswordActivity.class);
                    changeActivity.putExtra(USER_EXTRA, user);
                    startActivity(changeActivity);

                }
            });
        }
    }

    void updateTextViews(){
        welcomeUser.setText("Benvenuto, " + user.getUsername() + "!!");
        resultUsername.setText(user.getUsername());
        resultPassw.setText(user.getPassword());
        resultCity.setText(user.getCity());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        resultDate.setText(format.format(user.getBirthDate().getTime()));
        resultImage.setImageURI(user.getFoto());

        if(user.getFoto() != null){
            resultImage.setImageURI(user.getFoto());
        } else{
            Toast.makeText(this, "Non hai inserito una immagine", Toast.LENGTH_LONG).show();
            resultImage.setImageResource(defaultImage);
        }
    }
}