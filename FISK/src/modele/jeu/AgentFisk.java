package modele.jeu;
import modele.carte.*;

public class AgentFisk {
    private static boolean effetActif = false;
    private static int idDernierJoueur;
    private int evenement;
    private static int prixInfo;
    private static Agence agenceCible;
    private static Ville villeCible;

    private static String message;

    public AgentFisk()
    {
        prixInfo = 10000;
    }

    public static String getMessage()
    {
        return message;
    }

    public void setProchainEvenement()
    {
        agenceCible = null;
        villeCible = null;
        prixInfo += 2000;

        idDernierJoueur = (int) (Math.random() * Partie.nbJoueursRestants()); //l'agent apparait au maximum tous les 3 tours

        evenement = (int) (Math.random() * (9));

        int tirage;
        //definition de ville cible et agence cible
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

        //definition de ville cible
        else
        {
            tirage = (int) (Math.random() * (6));
            villeCible = Carte.getVille(tirage);
        }

        //definition du message
        if(evenement == 0)
        {
            message = "Campagne de recrutement : Double immédiatement le nombre de banquiers de l'agence dont le nombre de banquiers est en blanc";
        }

        else if(evenement == 1)
        {
            message = "Satisfaction clients : L'agence dont le nombre de banquiers est en blanc ne peut être attaquée pendant 1 tour";
        }

        else if(evenement == 2)
        {
            message = "Campagne de pub : L'agence dont le nombre de banquiers est en blanc connait un essor et rapporte 4 fois plus d'argent pendant 1 tour";
        }

        else if(evenement == 3)
        {
            message = "Nouveaux riches : " + villeCible.getNom() + " rapporte 2 fois plus pendant 1 tour";
        }

        else if(evenement == 4)
        {
            message = "Épidémie : Perte immédiate de la moitié des banquiers de l'agence";
        }

        else if(evenement == 5)
        {
            message = "Accident de la route : Perte immédiate de 5 banquiers de l'agence dont le nombre de banquiers est en blanc";
        }

        else if(evenement == 6)
        {
            message = "Pannes automobiles : Les banquiers de l'agence dont le nombre de banquiers est en blanc sont immobilisés pendant 1 tour";
        }

        else if(evenement == 7)
        {
            message = "Banqueroute : L'agence dont le nombre de banquiers est en blanc ne rapporte rien pendant 1 tour";
        }

        else if(evenement == 8)
        {
            message = "Contrôle fiskal : Le joueur perd le quart de l’argent placé MAIS seul le joueur qui détient cette agence peut savoir de laquelle il s’agit";
        }
    }
}
