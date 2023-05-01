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
        controle = Controle.getInstance();
        btnFiltrer = (Button) findViewById(R.id.btnFiltrer);
        txtFiltre = (EditText) findViewById(R.id.txtFiltre);
        creerListe(null);
        creerFiltre();
    }

    private void creerFiltre(){
        ecouteFiltre((Button)findViewById(R.id.btnFiltrer), FormationsActivity.class);
    }

    /**
     * création de la liste adapter
     */
    private void creerListe(ArrayList<Formation> formationfiltrees) {
        ArrayList<Formation> lesFormations = controle.getLesFormations();
        if (formationfiltrees != null && formationfiltrees.size() > 0) {
            lesFormations = formationfiltrees;
        }

        if(lesFormations != null){
            Collections.sort(lesFormations, Collections.<Formation>reverseOrder());
            ListView listView = (ListView)findViewById(R.id.lstFormations);
            FormationListAdapter adapter = new FormationListAdapter(lesFormations,FormationsActivity.this);
            listView.setAdapter(adapter);
        }

    }

    private void ecouteFiltre(Button btn, final Class classe){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtFiltre = (EditText)findViewById(R.id.txtFiltre);
                String value = String.valueOf(txtFiltre.getText());
                System.out.println(value);
                ArrayList<Formation> formationfiltrees = controle.getLesFormationFiltre(value);
                System.out.println(formationfiltrees);
                creerListe(formationfiltrees);
            }
        });
    }

}