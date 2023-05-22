package modele.jeu;

import modele.carte.*;

public class Joueur {
    private int idJoueur;
    private String pseudo;
    private double argentDispo;
    private double argentPlace;
    private double argentParTour;
    private int nbBanquiersDispo;
    private int nbBanquiersTotaux;
    private Agence[] agences = new Agence[38];
    private boolean infoAchetee;
    private Ville[] villesMonop;

    public Joueur()
    {

    }

    public Joueur(int[] idAgences)
    {
        for(int i = 0; i < idAgences.length; i++)
        {
            this.agences[i] = Carte.getAgenceById(idAgences[i]);
        }
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
