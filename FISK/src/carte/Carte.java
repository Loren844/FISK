package carte;

public class Carte {
    private String nom;
    private static Ville[] villes;

    //Si plusieurs cartes sont jouables, alors rajouter une condition sur le nom de la carte
    public Carte(String nom)
    {
        this.nom = nom;

        //Création de la ville Andorre et de ses agences pour permettre a une ville d'avoir des formes différentes selon les cartes
        Agence a0 = new Agence();
        Agence a1 = new Agence();
        Agence a2 = new Agence();
        Agence a3 = new Agence();
        Agence a4 = new Agence();
        Agence a5 = new Agence();
        Agence[] agAndorre = {a0, a1, a2, a3, a4, a5};
        Ville Andorre = new Ville("Andorre", agAndorre, 3000);

        //La valette
        Agence a6 = new Agence();
        Agence a7 = new Agence();
        Agence a8 = new Agence();
        Agence a9 = new Agence();
        Agence a10 = new Agence();
        Agence[] agLaValette = {a6, a7, a8, a9, a10};
        Ville LaValette = new Ville("La Valette", agLaValette, 2000);

        //Paris
        Agence a11 = new Agence();
        Agence a12 = new Agence();
        Agence a13 = new Agence();
        Agence a14 = new Agence();
        Agence a15 = new Agence();
        Agence a16 = new Agence();
        Agence a17 = new Agence();
        Agence a18 = new Agence();
        Agence a19 = new Agence();
        Agence[] agParis = {a11, a12, a13, a14, a15, a16, a17, a18, a19};
        Ville Paris = new Ville("Paris", agParis, 6000);

        //Geneve
        Agence a20 = new Agence();
        Agence a21 = new Agence();
        Agence a22 = new Agence();
        Agence a23 = new Agence();
        Agence a24 = new Agence();
        Agence a25 = new Agence();
        Agence[] agGeneve = {a20, a21, a22, a23, a24, a25};
        Ville Geneve = new Ville("Geneve", agGeneve, 4000);

        //Monaco
        Agence a26 = new Agence();
        Agence a27 = new Agence();
        Agence a28 = new Agence();
        Agence a29 = new Agence();
        Agence a30 = new Agence();
        Agence a31 = new Agence();
        Agence[] agMonaco = {a26, a27, a28, a29, a30, a31};
        Ville Monaco = new Ville("Monaco", agMonaco, 4000);

        //Londres
        Agence a32 = new Agence();
        Agence a33 = new Agence();
        Agence a34 = new Agence();
        Agence a35 = new Agence();
        Agence a36 = new Agence();
        Agence a37 = new Agence();
        Agence[] agLondres = {a32, a33, a34, a35, a36, a37};
        Ville Londres = new Ville("Londres", agLondres, 2000);

        Ville[] vieuxContVilles = {Andorre, LaValette, Paris, Geneve, Monaco, Londres};
        this.villes = vieuxContVilles;
    }

    public static Ville[] getVilles() {
        return villes;
    }

    public static Ville getVille(int numVille)
    {
        return villes[numVille];
    }

}
