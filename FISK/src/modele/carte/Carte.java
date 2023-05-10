package modele.carte;

public class Carte {
    private String nom;
    private static Ville[] villes;

    //Si plusieurs cartes sont jouables, alors rajouter une condition sur le nom de la modele.carte
    public Carte(String nom)
    {
        this.nom = nom;

        //Création de la ville Andorre et de ses agences pour permettre a une ville d'avoir des formes différentes selon les cartes
        Agence a0 = new Agence("a0");
        Agence a1 = new Agence("a1");
        Agence a2 = new Agence("a2");
        Agence a3 = new Agence("a3");
        Agence a4 = new Agence("a4");
        Agence a5 = new Agence("a5");
        Agence[] agAndorre = {a0, a1, a2, a3, a4, a5};
        Ville Andorre = new Ville("Andorre", agAndorre, 3000);

        //La valette
        Agence a6 = new Agence("a6");
        Agence a7 = new Agence("a7");
        Agence a8 = new Agence("a8");
        Agence a9 = new Agence("a9");
        Agence a10 = new Agence("a10");
        Agence[] agLaValette = {a6, a7, a8, a9, a10};
        Ville LaValette = new Ville("La Valette", agLaValette, 2000);

        //Paris
        Agence a11 = new Agence("a11");
        Agence a12 = new Agence("a12");
        Agence a13 = new Agence("a13");
        Agence a14 = new Agence("a14");
        Agence a15 = new Agence("a15");
        Agence a16 = new Agence("a16");
        Agence a17 = new Agence("a17");
        Agence a18 = new Agence("a18");
        Agence a19 = new Agence("a19");
        Agence[] agParis = {a11, a12, a13, a14, a15, a16, a17, a18, a19};
        Ville Paris = new Ville("Paris", agParis, 6000);

        //Geneve
        Agence a20 = new Agence("a20");
        Agence a21 = new Agence("a21");
        Agence a22 = new Agence("a22");
        Agence a23 = new Agence("a23");
        Agence a24 = new Agence("a24");
        Agence a25 = new Agence("a25");
        Agence[] agGeneve = {a20, a21, a22, a23, a24, a25};
        Ville Geneve = new Ville("Geneve", agGeneve, 4000);

        //Monaco
        Agence a26 = new Agence("a26");
        Agence a27 = new Agence("a27");
        Agence a28 = new Agence("a28");
        Agence a29 = new Agence("a29");
        Agence a30 = new Agence("a30");
        Agence a31 = new Agence("a31");
        Agence[] agMonaco = {a26, a27, a28, a29, a30, a31};
        Ville Monaco = new Ville("Monaco", agMonaco, 4000);

        //Londres
        Agence a32 = new Agence("a32");
        Agence a33 = new Agence("a33");
        Agence a34 = new Agence("a34");
        Agence a35 = new Agence("a35");
        Agence a36 = new Agence("a36");
        Agence a37 = new Agence("a37");
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

    public Agence getAgenceById(String id)
    {
        for (Ville ville : villes)
        {
            for (Agence agence : ville.getAgences())
            {
                if (agence.getId().equals(id))
                {
                    return agence;
                }
            }
        }
        return null;
    }
}
