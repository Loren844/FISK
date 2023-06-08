package modele.carte;

import modele.jeu.Joueur;
import modele.jeu.*;

public class Agence {
    private int id;
    private int nbBanquiers = 1;
    private int[] idFrontalieres = {};

    //constructeurs
    public Agence() {
        this.id = -1;
    }

    public Agence(int id, int[] idFrontalieres)
    {
        this.id=id;
        this.idFrontalieres = idFrontalieres;
    }

    //getters and setters
    public int getId()
    {
        return id;
    }

    public int getNbBanquiers()
    {
        return nbBanquiers;
    }

    public Joueur getJoueur() {
        for (Joueur joueur:Partie.getJoueursRestants())
        {
            for (int i = 0; i < joueur.getNbAgences(); i++)
            {
                if (joueur.getAgences()[i].equals(this))
                {
                    return joueur;
                }
            }
        }
        return null;
    }

    public Ville getVille()
    {
        Ville[] villes = Carte.getVilles();
        Ville proprietaire = new Ville();
        for (int i = 0; i < villes.length; i++) {
            for (int j = 0; j < villes[i].getAgences().length; j++) {
                if (villes[i].getAgences()[i].equals(this)) {
                    proprietaire = villes[i];
                }
            }
        }
        return proprietaire;
    }

    public void setNbBanquiers(int nbBanquiers)
    {
        this.nbBanquiers = nbBanquiers;
    }

    //fonctions

    public boolean estFrontaliere(String id)
    {
        int idAgence = Integer.parseInt(id.substring(2));
        Agence agence = Carte.getAgenceById(idAgence);

        for(int i = 0; i < idFrontalieres.length; i++)
        {
            Agence a = Carte.getAgenceById(idFrontalieres[i]);
            if(a.equals(agence))
            {
                return true;
            }
        }
        return false;
    }

    public void attaque(Agence frontiereAttaquee, int stockAttaquants) {
        this.setNbBanquiers(this.getNbBanquiers() - stockAttaquants);

        int stockDefenseurs = frontiereAttaquee.getNbBanquiers();
        int nbAttaquants, nbDefenseurs;

        while (stockAttaquants > 0 && stockDefenseurs > 0)
        {
            if (stockAttaquants >= 3)
            {
                nbAttaquants = 3;
            }
            else
            {
                nbAttaquants = stockAttaquants;
            }

            if (stockDefenseurs >= 2)
            {
                if(nbAttaquants == 1)
                {
                    nbDefenseurs = 1;
                }
                else
                {
                    nbDefenseurs = 2;
                }
            }

            else
            {
                nbDefenseurs = stockDefenseurs;
            }

            //Tirage des valeurs des dés
            int[] tirageAttaque = new int[3];
            int[] tirageDefense = new int[2];

            for (int i = 0; i < nbAttaquants; i++)
            {
                tirageAttaque[i] = (int) (Math.random() * (6)) + 1;
            }

            for (int i = 0; i < nbDefenseurs; i++)
            {
                tirageDefense[i] = (int) (Math.random() * (6)) + 1;
            }

            //Tri des tableaux
            if(nbAttaquants > 1)
            {
                for(int i = 0; i < nbAttaquants ; i++)
                {
                    int max = i;
                    for(int j = i+1; j < nbAttaquants; j++)
                    {
                        if(tirageAttaque[j] > tirageAttaque[max])
                        {
                            max = j;
                        }
                    }

                    int temp = tirageAttaque[max];
                    tirageAttaque[max] = tirageAttaque[i];
                    tirageAttaque[i] = temp;
                }
            }


            if(nbDefenseurs == 2)
            {
                if(tirageDefense[0]<tirageDefense[1])
                {
                    int temp = tirageDefense[0];
                    tirageDefense[0] = tirageDefense[1];
                    tirageDefense[1] = temp;
                }
            }

            //Comparaison des valeurs
            int nbCombats = Math.min(nbAttaquants, nbDefenseurs);

            for(int i = 0; i < nbCombats; i++)
            {
                if(tirageAttaque[i] > tirageDefense[i])
                {
                    stockDefenseurs--;
                }
                else
                {
                    stockAttaquants--;
                }
            }
        }

        //si l'attaque perd
        if(stockAttaquants == 0)
        {
            frontiereAttaquee.setNbBanquiers(stockDefenseurs);
        }

        //si la défense perd
        else if(stockDefenseurs == 0)
        {
            //enlever l'agence au défenseur
            frontiereAttaquee.getJoueur().perdreAgence(frontiereAttaquee);

            //rajouter l'agence à l'attaquant
            this.getJoueur().gagnerAgence(frontiereAttaquee);
            frontiereAttaquee.setNbBanquiers(stockAttaquants);
        }
    }

    /*
    public Agence[] transfertsPossibles(Agence[] agencesDispo, int tailleDispo, Agence[] agencesTestees, int tailleTestees, Agence origine, Joueur joueur) //les listes agencesDispo et agencesTestees sont crées dans le programme appelant
    {
        int test = 0;

        //pour chaque agence frontaliere on vérifie qu'elle n'a pas été testée
        for (int i = 0; i < frontalieres.length; i++) {
            for (int j = 0; j < agencesTestees.length; j++) {
                if (frontalieres[i].equals(agencesTestees[j])) {
                    test = 1;
                }
            }
            //si elle n'a pas été testée
            if (test == 0) {
                //on vérifie qu'elle est dispo : appartient au joueur et ce n'est pas l'agence d'origine
                if (joueur.possede(frontalieres[i]) && !(frontalieres[i].equals(origine))) {
                    //on l'ajoute comme agence dispo et agence testée
                    agencesDispo[tailleDispo] = frontalieres[i];
                    agencesTestees[tailleTestees] = frontalieres[i];

                    //on incrémente la taille des tableaux de 1
                    tailleDispo++;
                    tailleTestees++;

                    //récursivité
                    frontalieres[i].transfertsPossibles(agencesDispo, tailleDispo, agencesTestees, tailleTestees, origine, joueur);
                }
                //si elle n'est pas dispo ou que c'est l'agence d'origine
                else {
                    //on l'ajoute seulement a agencesTestees
                    agencesTestees[tailleTestees] = frontalieres[i];

                    //on incrémente la taille des tableaux de 1
                    tailleTestees++;
                }
            }
            //si elle a été testée on passe a la suivante
        }
        return agencesDispo;
    }

     */

    //transfertsPossibles agit avant l'appel de transfertVers, on ne peut appeler transfertVers que vers une agence valide ET le nb de banquiers est vérifié avant aussi
    public void transfertVers(Agence destination, int nbBanquiers) {
        this.nbBanquiers -= nbBanquiers;
        destination.nbBanquiers += nbBanquiers;
    }
}