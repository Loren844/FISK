package modele.jeu;

import modele.carte.*;

public class Joueur {
    private static int nbInstances = 0;
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
    private static final String[] couleurs = {"#ff0000","#ffa100","#9500ff","#ff5d8f"};

    public Joueur()
    {
        idJoueur = nbInstances;
        nbInstances++;
    }

    public Joueur(int[] idAgences)
    {

        for(int i = 0; i < idAgences.length; i++)
        {
            this.agences[i] = Carte.getAgenceById(idAgences[i]);
        }
        idJoueur = nbInstances;
        nbInstances++;
    }

    public int getId()
    {
        return idJoueur;
    }

    public Agence[] getAgences()
    {
        return agences;
    }

    public String getCouleur()
    {
        return couleurs[idJoueur];
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
