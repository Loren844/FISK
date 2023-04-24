package jeu;

import carte.Agence;
import carte.Ville;

public class AgentFisk {
    private int nbToursAvantApp;
    private int evenement;
    private int tourEvenement;
    private double prixInfo;
    private Agence agenceCible;
    private Ville villeCible;

    public AgentFisk()
    {
        prixInfo = 2000;
    }

    void setProchainEvenement()
    {
        agenceCible = null;
        villeCible = null;
        prixInfo += 1000;

        Partie instPartie = new Partie();
        nbToursAvantApp = (int) Math.random() * (instPartie.nbJoueursRestants() * 3) + 1; //l'agent apparait au maximum tous les 3 tours de table

        evenement = (int) (Math.random() * (9));

        int hasard;
        if(evenement != 3)
        {
            hasard = (int) (Math.random() * (38));
        }
        else
        {
            hasard = (int) (Math.random() * (6));
        }
    }
}
