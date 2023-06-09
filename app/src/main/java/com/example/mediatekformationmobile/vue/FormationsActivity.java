package com.example.mediatekformationmobile.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mediatekformationmobile.R;
import com.example.mediatekformationmobile.controleur.Controle;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;
import java.util.Collections;

public class FormationsActivity extends AppCompatActivity {

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
            FormationListAdapter adapter = new FormationListAdapter(lesFormations,FormationsActivity.this, false);
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