package modele.jeu;
import modele.carte.*;

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

        nbToursAvantApp = (int) Math.random() * (Partie.nbJoueursRestants() * 3) + 1; //l'agent apparait au maximum tous les 3 tours de table

        evenement = (int) (Math.random() * (9));

        int tirage;
        if(evenement != 3)
        {
            tirage = (int) (Math.random() * (38));
            if (tirage > 31)
            {
                tirage -= 32;
                villeCible = Carte.getVille(5);
            }

            else if (tirage > 25)
            {
                tirage -= 26;
                villeCible = Carte.getVille(4);
            }

            else if (tirage > 19)
            {
                tirage -= 20;
                villeCible = Carte.getVille(3);
            }

            else if (tirage > 10)
            {
                tirage -= 11;
                villeCible = Carte.getVille(2);
            }

            else if (tirage > 5)
            {
                tirage -= 6;
                villeCible = Carte.getVille(1);
            }
            else {
                villeCible = Carte.getVille(0);
            }

            agenceCible = villeCible.getAgence(tirage);
        }
        else
        {
            tirage = (int) (Math.random() * (6));
            villeCible = Carte.getVille(tirage);
        }
    }
}
