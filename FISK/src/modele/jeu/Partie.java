package modele.jeu;

import modele.carte.Ville;

public class Partie {
    private static int phase = 1;
    private static int tour = 1;
    private static int nbToursMax = -1; //-1 si richesse prospere ou 30 si richesse immediate
    private static Joueur[] joueursRestants;
    private static Joueur[] joueursElimines = {null, null, null};
    private static Joueur joueurActuel;
    private static int[] idJoueursBDD = {-1,-1,-1,-1};

    //Constructeurs
    public Partie() {}

    public Partie(int nbToursMax, Joueur[] joueursRestants)
    {
        this.nbToursMax = nbToursMax;
        if(nbToursMax == 30)
        {
            tour = 30;
        }
        Partie.joueursRestants = joueursRestants;
        Partie.joueurActuel = joueursRestants[0];
    }

    //Getters and setters
    public static int getPhase()
    {
        return phase;
    }

    public static void setPhase(int newPhase)
    {
        phase = newPhase;
    }

    public static int getTour()
    {
        return tour;
    }

    public static void setTour(int tour)
    {
        Partie.tour = tour;
    }

    public static Joueur getJoueurActuel()
    {
        return joueurActuel;
    }

    public static void setJoueurActuel(Joueur joueurActuel)
    {
        Partie.joueurActuel = joueurActuel;
    }

    public static int getNbToursMax()
    {
        return nbToursMax;
    }

    public void setNbToursMax(int nbToursMax)
    {
        this.nbToursMax = nbToursMax;
    }

    public static Joueur[] getJoueursRestants()
    {
        return joueursRestants;
    }

    public static int nbJoueursRestants()
    {
        int nbJoueursRestants = 0;
        for (Joueur joueursRestant : joueursRestants) {
            if (joueursRestant != null) {
                nbJoueursRestants++;
            }
        }
        return nbJoueursRestants;
    }

    public static int getNbJoueursBDD()
    {
        int cpt = 0;
        for(int id:idJoueursBDD)
        {
            if(id != -1)
            {
                cpt++;
            }
        }
        return cpt;
    }

    public static int[] getIdJoueursBDD()
    {
        return idJoueursBDD;
    }

    public static void setIdJoueurBDD(int id)
    {
        idJoueursBDD[getNbJoueursBDD()] = id;
    }

    public static Joueur[] getJoueursElimines()
    {
        return joueursElimines;
    }

    public static int nbJoueursElimines()
    {
        int nbJoueursElimines = 0;
        for (Joueur joueursElimine : joueursElimines) {
            if (joueursElimine != null) {
                nbJoueursElimines++;
            }
        }
        return nbJoueursElimines;
    }

    public static Joueur getJoueurSuivant()
    {
        //si le joueur actuel est le dernier joueur retourne le premier joueur
        if(joueursRestants[Partie.nbJoueursRestants()-1] == joueurActuel)
        {
            return joueursRestants[0];
        }
        else
        {
            int i =0;
            //on parcourt le tableau des joueurs restants jusau'à trouver la position du joueur actuel
            while(i < Partie.nbJoueursRestants()-1 && !(joueursRestants[i].equals(joueurActuel)))
            {
                i++;
            }
            //on retourne le joueur suivant
            return joueursRestants[i+1];
        }
    }

    public static Joueur getJoueurPlusRiche()
    {
        Joueur joueurPlusRiche = joueursRestants[0];
        for(int i = 1; i < joueursRestants.length; i++)
        {
            if(joueursRestants[i].getArgentPlace() > joueurPlusRiche.getArgentPlace())
            {
                joueurPlusRiche = joueursRestants[i];
            }
        }
        return joueurPlusRiche;
    }

    public static void setArgentParTour(Joueur j)
    {
        int bonus = 0;
        for(Ville ville:j.getVillesMonop())
        {
            if(ville != null)
            {
                bonus += ville.getArgentMonop();
            }
        }
        j.setArgentParTour( 3000 + j.getArgentPlace()/10 + bonus + (j.getNbAgences()/3 + 1)*1000 );
    }

    public static Joueur aUnGagnant()
    {
        if(nbToursMax == 30)
        {
            if(tour == 0)
            {
                return Partie.getJoueurPlusRiche();
            }
        }
        if(joueurActuel.getNbAgences() == 38)
        {
            return Partie.getJoueurActuel();
        }
        return null;
    }

    public static void eliminerJoueur(int idJoueur)
    {
        int i = 0;
        while(i < Partie.nbJoueursRestants() && !(joueursRestants[i].getIdJoueur() == idJoueur))
        {
            i++;
        }
        joueursElimines[nbJoueursElimines()] = joueursRestants[i];
        System.out.println(joueursElimines[0].getIdJoueur());

        for(int j = i ; j < nbJoueursRestants()-1; j++)
        {
            joueursRestants[j] = joueursRestants[j+1];
        }
        joueursRestants[nbJoueursRestants()-1] = null;
    }

    public static void resetPartie()
    {
        for(int i = 0; i < 4; i++)
        {
            idJoueursBDD[i] = -1;
        }

        for(int i = 0; i < 3; i++)
        {
            joueursElimines[i] = null;
        }
    }
}
