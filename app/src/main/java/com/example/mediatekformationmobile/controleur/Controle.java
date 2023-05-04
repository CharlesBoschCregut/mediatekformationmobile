package com.example.mediatekformationmobile.controleur;

import android.content.Context;

import com.example.mediatekformationmobile.modele.AccesLocal;
import com.example.mediatekformationmobile.modele.AccesDistant;
import com.example.mediatekformationmobile.modele.Formation;

import java.util.ArrayList;
import java.util.HashMap;

public class Controle {

    private static Controle instance = null ;
    private ArrayList<Formation> lesFormations = new ArrayList<>();
    private Formation formation = null;
    private static AccesLocal accesLocal;

    /**
     * constructeur privé
     */
    private Controle(){
        super();
    }

    /**
     * récupération de l'instance unique de Controle
     * @return instance
     */
    public static final Controle getInstance(Context context){
        if(Controle.instance == null){
            Controle.instance = new Controle();
            AccesDistant accesDistant = new AccesDistant();
            accesDistant.envoi("tous", null);
            if (context != null){
                accesLocal = new AccesLocal(context);
            }

        }
        return Controle.instance;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    /**
     * retourne les formations dont le titre contient le filtre
     * @param filtre
     * @return
     */
    public ArrayList<Formation> getLesFormationFiltre(String filtre){
        ArrayList<Formation> lesFormationsFiltre = new ArrayList<>();
        for(Formation uneFormation : lesFormations){
            if(uneFormation.getTitle().toUpperCase().contains(filtre.toUpperCase())){
                lesFormationsFiltre.add(uneFormation);
            }
        }
        return lesFormationsFiltre;
    }

    public void setLesFormations(ArrayList<Formation> lesFormations) {
        HashMap<Integer, Integer> favorites = accesLocal.recupFavorites();
        for (int i = 0; i < lesFormations.size(); i++){
            if (favorites.containsKey(lesFormations.get(i).getId())) {
                favorites.remove(lesFormations.get(i).getId());
            }
        }

        int restant = favorites.size();
        if (restant > 0){
            for (Integer key : favorites.keySet()) {
                accesLocal.supprFav(key);
            }
        }
        this.lesFormations = lesFormations;
    }

    public HashMap<Integer, Integer> getlesFavorites(){
        return accesLocal.recupFavorites();
    }

    public void GererFavrotie(int fav) {
            this.accesLocal.toggleFav(fav);
    }

}

