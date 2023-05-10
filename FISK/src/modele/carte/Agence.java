package modele.carte;

import modele.jeu.Joueur;
import modele.jeu.*;

public class Agence {
    private String id;
    private int nbBanquiers = 0;
    private static Agence[] frontalieres;

    //constructeurs
    public Agence(String id){
        this.id=id;
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public Joueur getJoueur() {
        Joueur[] joueurs = Partie.getJoueursRestants();
        Joueur proprietaire = new Joueur();
        for (int i = 0; i < joueurs.length; i++) {
            for (int j = 0; j < joueurs[i].getAgences().length; j++) {
                if (joueurs[i].getAgences()[i].equals(this)) {
                    proprietaire = joueurs[i];
                }
            }
        }
        return proprietaire;
    }

    public Ville getVille() {
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

    public void setNbBanquiers(int nbBanquiers) {
        this.nbBanquiers = nbBanquiers;
    }

    public static Agence[] getFrontalieres() {
        return frontalieres;
    }


    //fonctions
    public Agence[] attaquesPossibles() {
        Agence[] attaquables = new Agence[6];
        int cpt = 0;
        Joueur proprietaire = this.getJoueur();
        for (int i = 0; i < frontalieres.length; i++) {
            if (!(frontalieres[i].getJoueur().equals(proprietaire))) {
                attaquables[cpt] = frontalieres[i];
                cpt++;
            }
        }
        return attaquables;
    }

    //seules les agences voisines ennemies seront sélectionnables pour l'attaque
    public void attaque(Agence frontiereAttaquee, int stockAttaquants) {
        int stockDefenseurs = frontiereAttaquee.nbBanquiers;
        int nbAttaquants, nbDefenseurs;

        while (stockAttaquants > 0) {
            if (stockAttaquants >= 3) {
                nbAttaquants = 3;
                stockAttaquants -= 3;
            } else {
                nbAttaquants = stockAttaquants;
                stockAttaquants = 0;
            }

            if (stockDefenseurs >= 2) {
                nbDefenseurs = 2;
                stockDefenseurs -= 2;
            } else {
                nbDefenseurs = stockDefenseurs;
                stockDefenseurs = 0;
            }

            int[] tirageAttaque = new int[0];
            int[] tirageDefense = new int[0];
            for (int i = 0; i < nbAttaquants; i++) {
                tirageAttaque[i] = (int) (Math.random() * (6)) + 1;
            }
            for (int i = 0; i < nbDefenseurs; i++) {
                tirageDefense[i] = (int) (Math.random() * (6)) + 1;
            }
        }
    }

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

    //transfertsPossibles agit avant l'appel de transfertVers, on ne peut appeler transfertVers que vers une agence valide ET le nb de banquiers est vérifié avant aussi
    public void transfertVers(Agence destination, int nbBanquiers) {
        this.nbBanquiers -= nbBanquiers;
        destination.nbBanquiers += nbBanquiers;
    }
}