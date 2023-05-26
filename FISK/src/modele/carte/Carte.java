package modele.carte;

public class Carte {
    private String nom;
    private static Ville[] villes;

    //Si plusieurs cartes sont jouables, alors rajouter une condition sur le nom de la modele.carte
    public Carte()
    {
        this.nom = "Vieux Continent";

        //Création de la ville Andorre et de ses agences pour permettre a une ville d'avoir des formes différentes selon les cartes
        Agence a0 = new Agence(0);
        Agence a1 = new Agence(1);
        Agence a2 = new Agence(2);
        Agence a3 = new Agence(3);
        Agence a4 = new Agence(4);
        Agence a5 = new Agence(5);
        Agence[] agAndorre = {a0, a1, a2, a3, a4, a5};
        Ville Andorre = new Ville("Andorre", agAndorre, 3000);

        //La valette
        Agence a6 = new Agence(6);
        Agence a7 = new Agence(7);
        Agence a8 = new Agence(8);
        Agence a9 = new Agence(9);
        Agence a10 = new Agence(10);
        Agence[] agLaValette = {a6, a7, a8, a9, a10};
        Ville LaValette = new Ville("La Valette", agLaValette, 2000);

        //Paris
        Agence a11 = new Agence(11);
        Agence a12 = new Agence(12);
        Agence a13 = new Agence(13);
        Agence a14 = new Agence(14);
        Agence a15 = new Agence(15);
        Agence a16 = new Agence(16);
        Agence a17 = new Agence(17);
        Agence a18 = new Agence(18);
        Agence a19 = new Agence(19);
        Agence[] agParis = {a11, a12, a13, a14, a15, a16, a17, a18, a19};
        Ville Paris = new Ville("Paris", agParis, 6000);

        //Geneve
        Agence a20 = new Agence(20);
        Agence a21 = new Agence(21);
        Agence a22 = new Agence(22);
        Agence a23 = new Agence(23);
        Agence a24 = new Agence(24);
        Agence a25 = new Agence(25);
        Agence[] agGeneve = {a20, a21, a22, a23, a24, a25};
        Ville Geneve = new Ville("Geneve", agGeneve, 4000);

        //Monaco
        Agence a26 = new Agence(26);
        Agence a27 = new Agence(27);
        Agence a28 = new Agence(28);
        Agence a29 = new Agence(29);
        Agence a30 = new Agence(30);
        Agence a31 = new Agence(31);
        Agence[] agMonaco = {a26, a27, a28, a29, a30, a31};
        Ville Monaco = new Ville("Monaco", agMonaco, 4000);

        //Londres
        Agence a32 = new Agence(32);
        Agence a33 = new Agence(33);
        Agence a34 = new Agence(34);
        Agence a35 = new Agence(35);
        Agence a36 = new Agence(36);
        Agence a37 = new Agence(37);
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
    
    public static Agence getAgenceById(int id)
    {
        if (id > 31)
        {
            id -= 32;
            return Carte.getVille(5).getAgence(id);
        }

        else if (id > 25)
        {
            id -= 26;
            return Carte.getVille(4).getAgence(id);
        }

        else if (id > 19)
        {
            id -= 20;
            return Carte.getVille(3).getAgence(id);
        }

        else if (id > 10)
        {
            id -= 11;
            return Carte.getVille(2).getAgence(id);
        }

        else if (id > 5)
        {
            id -= 6;
            return Carte.getVille(1).getAgence(id);
        }
        else {
            return Carte.getVille(0).getAgence(id);
        }
    }
}
