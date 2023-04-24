package carte;

public class Carte {
    private String nom;
    private Ville[] villes;

    public Carte(String nom, Ville[] villes)
    {
        this.nom = nom;
        this.villes = villes;
    }

    public Agence getAgence(int numVille, int numAgence)
    {
        return villes[numVille].getAgences()[numAgence];
    }

}
