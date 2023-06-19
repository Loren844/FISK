package modele.carte;

import modele.jeu.Joueur;
import modele.jeu.*;

import java.util.Arrays;

public class Agence {
    private int id;
    private int nbBanquiers = 1;
    private int[] idFrontalieres = {};

    private int[] transfertsPossibles = new int[38];

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
            //si le joueur n'a plus d'agences, il est éliminé
            if(frontiereAttaquee.getJoueur().getNbAgences() == 1)
            {
                Joueur elimine = frontiereAttaquee.getJoueur();

                //enlever l'agence au défenseur
                frontiereAttaquee.getJoueur().perdreAgence(frontiereAttaquee);

                Partie.eliminerJoueur(elimine.getIdJoueur());
            }

            else
            {
                //enlever l'agence au défenseur
                frontiereAttaquee.getJoueur().perdreAgence(frontiereAttaquee);
            }

            //rajouter l'agence à l'attaquant
            this.getJoueur().gagnerAgence(frontiereAttaquee);
            frontiereAttaquee.setNbBanquiers(stockAttaquants);
            this.getJoueur().setVillesMonop();
        }
    }


    public boolean transfertPossible(int id)
    {
        for (int i = 0; i < 38; i++)
        {
            transfertsPossibles[i] = -1;
        }
        transfertsPossibles[0] = this.id;

        this.transfertsPossibles(this.getId());

        int i = 0;
        while(transfertsPossibles[i] != id && i < nbTransfertsPossibles())
        {
            i++;
        }

        return transfertsPossibles[i] == id;
    }

    public int nbTransfertsPossibles()
    {
        int cpt = 0;
        while(transfertsPossibles[cpt] != -1 && cpt < 37)
        {
            cpt++;
        }
        return cpt;
    }

    public int[] getTransfertsPossibles() {
        return transfertsPossibles;
    }

    public void transfertsPossibles(int idDepart)
    {
        Agence depart = Carte.getAgenceById(idDepart);
        for(int id:this.idFrontalieres)
        {
            //si l'agence appartient au meme joueur
            if(depart.getJoueur() == Carte.getAgenceById(id).getJoueur())
            {
                int i = 0;
                while(depart.transfertsPossibles[i] != -1 && depart.transfertsPossibles[i] != id && i < 37)
                {
                    i++;
                }

                //si l'agence n'a pas été testée, on la teste
                if(depart.transfertsPossibles[i] == -1 || i == 37)
                {
                    depart.transfertsPossibles[depart.nbTransfertsPossibles()] = id;
                    Carte.getAgenceById(id).transfertsPossibles(idDepart);
                }
            }
        }
    }


    //transfertsPossibles agit avant l'appel de transfertVers, on ne peut appeler transfertVers que vers une agence valide ET le nb de banquiers est vérifié avant aussi
    public void transfertVers(Agence destination, int nbBanquiers) {
        this.nbBanquiers -= nbBanquiers;
        destination.setNbBanquiers(destination.getNbBanquiers()+nbBanquiers);
    }
}