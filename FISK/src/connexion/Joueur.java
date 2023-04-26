package connexion;

import carte.*;
import jeu.Partie;

public class Joueur {
    private int idJoueur;
    private String pseudo;
    private double argentDispo;
    private double argentPlace;
    private double argentParTour;
    private int nbBanquiersDispo;
    private int nbBanquiersTotaux;
    private Agence[] agences;
    private boolean infoAchetee;
    private Ville[] villesMonop;

    public Joueur()
    {

    }

    public Agence[] getAgences()
    {
        return agences;
    }

    public boolean possede(Agence a)
    {
        for(int i = 0; i < agences.length; i++)
        {
            if(a.equals(agences[i]))
            {
                return true;
            }
        }
        return false;
    }
}
