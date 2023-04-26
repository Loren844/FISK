package carte;

public class Ville {
    private String nom;
    private Agence[] agences;
    private double argentGenere;

    public Ville(String nom, Agence[] agences, double argentGenere)
    {
        this.nom = nom;
        this.agences = agences;
        this.argentGenere = argentGenere;
    }

    public Agence[] getAgences()
    {
        return agences;
    }

    public Agence getAgence(int numAgence)
    {
        return agences[numAgence];
    }
}