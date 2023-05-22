package modele.jeu;

public class Partie {
    private int phase = 0;
    private int nbToursMax; //-1 si richesse prospere ou 30 si richesse immediate
    private static Joueur[] joueursRestants;

    public Partie()
    {
        this.nbToursMax = -1;
    }

    public Partie(Joueur[] joueursRestants)
    {
        Partie.joueursRestants = joueursRestants;

    }

    public Partie(int nbToursMax, Joueur[] joueursRestants)
    {
        this.nbToursMax = nbToursMax;
        Partie.joueursRestants = joueursRestants;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getNbToursMax() {
        return nbToursMax;
    }

    public void setNbToursMax(int nbToursMax) {
        this.nbToursMax = nbToursMax;
    }

    public static Joueur[] getJoueursRestants() {
        return joueursRestants;
    }

    public void setJoueursRestants(Joueur[] joueursRestants) {
        this.joueursRestants = joueursRestants;
    }

    public static int nbJoueursRestants()
    {
        return joueursRestants.length;
    }
}
