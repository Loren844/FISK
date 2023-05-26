package modele.jeu;

public class Partie {
    private static int phase = 1;
    private static int tour = 1;
    private static int nbToursMax = -1; //-1 si richesse prospere ou 30 si richesse immediate
    private static Joueur[] joueursRestants;
    private static Joueur joueurActuel;


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

    public void setJoueursRestants(Joueur[] joueursRestants)
    {
        this.joueursRestants = joueursRestants;
    }

    public static int nbJoueursRestants()
    {
        return joueursRestants.length;
    }

    public static Joueur getJoueurSuivant()
    {
        if(joueursRestants[joueursRestants.length-1] == joueurActuel)
        {
            return joueursRestants[0];
        }
        else
        {
            int i =0;
            while(i < joueursRestants.length && !(joueursRestants[i].equals(joueurActuel)))
            {
                i++;
            }
            return joueursRestants[i+1];
        }
    }
}
