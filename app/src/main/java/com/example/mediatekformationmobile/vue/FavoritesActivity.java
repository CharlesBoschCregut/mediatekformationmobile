package com.example.mediatekformationmobile.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mediatekformationmobile.R;
import com.example.mediatekformationmobile.controleur.Controle;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FavoritesActivity extends AppCompatActivity {

    private Controle controle;
    private Button btnFiltrer;
    private EditText txtFiltre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);
        init();
    }

    /**
     * initialisations
     */
    private void init(){
        controle = Controle.getInstance(this);
        btnFiltrer = (Button) findViewById(R.id.btnFiltrer);
        txtFiltre = (EditText) findViewById(R.id.txtFiltre);
        creerListe(null);
        creerFiltre();
    }

    private void creerFiltre(){
        ecouteFiltre(btnFiltrer);
    }

    /**
     * création de la liste adapter
     */
    private void creerListe(ArrayList<Formation> formationfiltrees) {
        controle.getlesFavorites();
        ArrayList<Formation> lesFormations = controle.getLesFormations();
        if (formationfiltrees != null && formationfiltrees.size() > 0) {
            lesFormations = formationfiltrees;
        }

        if(lesFormations != null){
            Collections.sort(lesFormations, Collections.<Formation>reverseOrder());
            ListView listView = (ListView)findViewById(R.id.lstFormations);
            HashMap<Integer, Integer> favorites = controle.getlesFavorites();
            ArrayList<Formation> favFormations = new ArrayList<Formation>(); // Initialize the list
            for (int i = 0; i < lesFormations.size(); i++){
                if (favorites.containsKey(lesFormations.get(i).getId())){
                    favFormations.add(lesFormations.get(i));
                }
            }
            FormationListAdapter adapter = new FormationListAdapter(favFormations,FavoritesActivity.this, true);
            listView.setAdapter(adapter);
        }
    }

    private void ecouteFiltre(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = String.valueOf(txtFiltre.getText());
                ArrayList<Formation> formationfiltrees = controle.getLesFormationFiltre(value);
                creerListe(formationfiltrees);
            }
        });
    }

}