package modele.carte;

public class Ville {
    private String nom;
    private Agence[] agences;
    private int argentMonop;

    public Ville()
    {

    }

    public Ville(String nom, Agence[] agences, int argentMonop)
    {
        this.nom = nom;
        this.agences = agences;
        this.argentMonop = argentMonop;
    }

    public Agence[] getAgences()
    {
        return agences;
    }

    public Agence getAgence(int numAgence)
    {
        if(numAgence!= -1)
        {
            return agences[numAgence];
        }
        return null;
    }

    public String getNom()
    {
        return nom;
    }

    public int getArgentMonop() {
        return argentMonop;
    }
}