package modele.jeu;
import modele.carte.*;

public class AgentFisk {
    private static boolean effetActif = false;
    private static int idPremierJoueur;
    private static int evenement;
    private static int prixInfo = 8000;
    private static Agence agenceCible;
    private static Ville villeCible;
    private static String nom;
    private static String message;

    public AgentFisk()
    {
    }

    public static String getMessage()
    {
        return message;
    }

    public static int getPrixInfo() {
        return prixInfo;
    }

    public static Agence getAgenceCible() {
        return agenceCible;
    }

    public static int getEvenement() {
        return evenement;
    }

    public static int getIdPremierJoueur() {
        return idPremierJoueur;
    }

    public static String getNom()
    {
        return nom;
    }

    public static boolean estActif()
    {
        return effetActif;
    }

    public static void setProchainEvenement()
    {
        effetActif = false;
        //nouvel evenement => les joueurs peuvent acheter l'info
        for(Joueur j:Partie.getJoueursRestants())
        {
            j.setInfoAchetee(false);
        }

        agenceCible = null;
        villeCible = null;
        prixInfo += 2000;

        idPremierJoueur = (int) (Math.random() * Partie.nbJoueursRestants());

        evenement = (int) (Math.random() * (6));

        int tirage;
        //definition de ville cible et agence cible
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


        //definition du message
        if(evenement == 0)
        {
            nom = "Campagne de recrutement";
            message = "Campagne de recrutement : Double le nombre de banquiers de l'agence dont le nombre de banquiers est en blanc";
        }

        else if(evenement == 1)
        {
            nom = "Faux et usage de faux";
            message = "Faux et usage de faux : Vous licenciez un des banquiers de l'agence dont le nombre de banquiers est en blanc grâce à une fausse rupture conventionnelle, vous perdez 20000$ d'argent placé";
        }

        else if(evenement == 2)
        {
            nom = "Nouveaux arrivants";
            message = "Nouveaux arrivants : L'agence dont le nombre de banquiers est en blanc accueillent 5 nouveaux banquiers";
        }

        else if(evenement == 3)
        {
            nom = "Épidémie";
            message = "Épidémie : Perte immédiate de la moitié des banquiers de l'agence dont le nombre de banquiers est en blanc";
        }

        else if(evenement == 4)
        {
            nom = "Accident de la route";
            message = "Accident de la route : Perte immédiate de 5 banquiers de l'agence dont le nombre de banquiers est en blanc";
        }

        else if(evenement == 5)
        {
            nom = "Contrôle fiskal";
            message = "Contrôle fiskal : Le joueur perd le quart de l’argent placé MAIS seul le joueur qui détient cette agence peut savoir de laquelle il s’agit";
        }
    }

    public static void activer()
    {
        effetActif = true;
        if(evenement == 0)
        {
            agenceCible.setNbBanquiers(agenceCible.getNbBanquiers()*2);
        }

        else if(evenement == 1)
        {
            if(agenceCible.getJoueur().getArgentPlace() < 20000)
            {
                agenceCible.getJoueur().setArgentPlace(0);
            }
            else
            {
                agenceCible.getJoueur().setArgentPlace(agenceCible.getJoueur().getArgentPlace() - 20000);
            }
        }

        else if(evenement == 2)
        {
            agenceCible.setNbBanquiers(agenceCible.getNbBanquiers()+5);
        }

        else if(evenement == 3)
        {
            agenceCible.setNbBanquiers(agenceCible.getNbBanquiers()/2);
        }

        else if(evenement == 4)
        {
            if(agenceCible.getNbBanquiers() > 5)
            {
                agenceCible.setNbBanquiers(agenceCible.getNbBanquiers()-5);
            }
            else
            {
                agenceCible.setNbBanquiers(1);
            }
        }

        else if(evenement == 5)
        {
            agenceCible.getJoueur().setArgentPlace(agenceCible.getJoueur().getArgentPlace()/4*3);
        }
    }
}
