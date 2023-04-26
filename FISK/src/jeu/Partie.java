package jeu;

import carte.Agence;
import connexion.Joueur;

public class Partie {
    private int phase = 0;
    private int nbToursMax;
    private static Joueur[] joueursRestants;

    void Partie(Joueur[] joueursRestants)
    {
        this.joueursRestants = joueursRestants;

    }

    void Partie(int nbToursMax, Joueur[] joueursRestants)
    {
        this.nbToursMax = nbToursMax;
        this.joueursRestants = joueursRestants;
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

    public Joueur[] getJoueursRestants() {
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
