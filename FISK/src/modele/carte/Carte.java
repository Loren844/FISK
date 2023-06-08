package modele.carte;

public class Carte {
    private String nom;
    private static Ville[] villes;

    //Si plusieurs cartes sont jouables, alors rajouter une condition sur le nom de la modele.carte
    public Carte()
    {
        this.nom = "Vieux Continent";

        //Création des tableaux des agences frontalieres
        int[] f0 = {1,6};
        int[] f1 = {0,2,3,4};
        int[] f2 = {1,4,8,11,12};
        int[] f3 = {1,4,5};
        int[] f4 = {1,2,3,5,11,12,14};
        int[] f5 = {3,4,14};
        int[] f6 = {0,7};
        int[] f7 = {6,8,9};
        int[] f8 = {2,7,9,11};
        int[] f9 = {7,8,10};
        int[] f10 = {7,9,20};
        int[] f11 = {2,8,12,13,26};
        int[] f12 = {2,4,11,13,14,15};
        int[] f13 = {11,12,15,16,26,28};
        int[] f14 = {4,5,12,15,17};
        int[] f15 = {12,13,14,16,17,18};
        int[] f16 = {13,15,18,19,28};
        int[] f17 = {14,15,18};
        int[] f18 = {15,16,17,19};
        int[] f19 = {16,18,31};
        int[] f20 = {10,21};
        int[] f21 = {20,22,23,24};
        int[] f22 = {21,24,25,32};
        int[] f23 = {21,24,26};
        int[] f24 = {21,22,23,25,27};
        int[] f25 = {22,24,27,32,33,35};
        int[] f26 = {11,13,23,28};
        int[] f27 = {24,25,29,35};
        int[] f28 = {13,16,26,29};
        int[] f29 = {27,28,30};
        int[] f30 = {29,31};
        int[] f31 = {19,30};
        int[] f32 = {22,25,33};
        int[] f33 = {25,32,34,35,36};
        int[] f34 = {33,36};
        int[] f35 = {25,27,33,36,37};
        int[] f36 = {33,34,35,37};
        int[] f37 = {35,36};

        //Création de la ville Andorre et de ses agences
        Agence a0 = new Agence(0,f0);
        Agence a1 = new Agence(1,f1);
        Agence a2 = new Agence(2,f2);
        Agence a3 = new Agence(3,f3);
        Agence a4 = new Agence(4,f4);
        Agence a5 = new Agence(5,f5);
        Agence[] agAndorre = {a0, a1, a2, a3, a4, a5};
        Ville Andorre = new Ville("Andorre", agAndorre, 3000);

        //La valette
        Agence a6 = new Agence(6,f6);
        Agence a7 = new Agence(7,f7);
        Agence a8 = new Agence(8,f8);
        Agence a9 = new Agence(9,f9);
        Agence a10 = new Agence(10,f10);
        Agence[] agLaValette = {a6, a7, a8, a9, a10};
        Ville LaValette = new Ville("La Valette", agLaValette, 2000);

        //Paris
        Agence a11 = new Agence(11,f11);
        Agence a12 = new Agence(12,f12);
        Agence a13 = new Agence(13,f13);
        Agence a14 = new Agence(14,f14);
        Agence a15 = new Agence(15,f15);
        Agence a16 = new Agence(16,f16);
        Agence a17 = new Agence(17,f17);
        Agence a18 = new Agence(18,f18);
        Agence a19 = new Agence(19,f19);
        Agence[] agParis = {a11, a12, a13, a14, a15, a16, a17, a18, a19};
        Ville Paris = new Ville("Paris", agParis, 6000);

        //Geneve
        Agence a20 = new Agence(20,f20);
        Agence a21 = new Agence(21,f21);
        Agence a22 = new Agence(22,f22);
        Agence a23 = new Agence(23,f23);
        Agence a24 = new Agence(24,f24);
        Agence a25 = new Agence(25,f25);
        Agence[] agGeneve = {a20, a21, a22, a23, a24, a25};
        Ville Geneve = new Ville("Geneve", agGeneve, 4000);

        //Monaco
        Agence a26 = new Agence(26,f26);
        Agence a27 = new Agence(27,f27);
        Agence a28 = new Agence(28,f28);
        Agence a29 = new Agence(29,f29);
        Agence a30 = new Agence(30,f30);
        Agence a31 = new Agence(31,f31);
        Agence[] agMonaco = {a26, a27, a28, a29, a30, a31};
        Ville Monaco = new Ville("Monaco", agMonaco, 4000);

        //Londres
        Agence a32 = new Agence(32,f32);
        Agence a33 = new Agence(33,f33);
        Agence a34 = new Agence(34,f34);
        Agence a35 = new Agence(35,f35);
        Agence a36 = new Agence(36,f36);
        Agence a37 = new Agence(37,f37);
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
