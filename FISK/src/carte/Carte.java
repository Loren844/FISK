package carte;

public class Carte {
    private String nom;
    private static Ville[] villes;

    public Carte(String nom, Ville[] villes)
    {
        this.nom = nom;
        this.villes = villes;
    }

    public static Ville getVille(int numVille)
    {
        return villes[numVille];
    }
}
