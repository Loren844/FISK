package carte;

import connexion.Joueur;

public class Agence {
    private int nbBanquiers;
    private static Agence[] frontalieres;

    public Agence(Agence[] frontalieres)
    {
        this.frontalieres = frontalieres;
    }

    public void setNbBanquiers(int nbBanquiers)
    {
        this.nbBanquiers = nbBanquiers;
    }

    public static Agence[] getFrontalieres()
    {
        return frontalieres;
    }

    public Agence[] transfertPossible(Agence[] agencesDispo, int tailleDispo, Agence[] agencesTestees, int tailleTestees, Agence origine, Joueur j) //les listes agencesDispo et agencesTestees sont crées dans le programme appelant
    {
        int test = 0;

        //pour chaque agence frontaliere on vérifie qu'elle n'a pas été testée
        for(int i = 0; i < frontalieres.length; i++)
        {
            for (int j = 0; j < agencesTestees.length; j++)
            {
                if (frontalieres[i].equals(agencesTestees[j]))
                {
                    test = 1;
                }
            }
            //si elle n'a pas été testée
            if(test==0)
            {
                //on vérifie qu'elle est dispo : appartient au joueur et ce n'est pas l'agence d'origine
                if(j.possede(frontalieres[i]) && !(frontalieres[i].equals(origine)))
                {
                    //on l'ajoute comme agence dispo et agence testée
                    agencesDispo[tailleDispo] = frontalieres[i];
                    agencesTestees[tailleTestees] = frontalieres[i];

                    //on incrémente la taille des tableaux de 1
                    tailleDispo++;
                    tailleTestees++;

                    //récursivité
                    frontalieres[i].transfertPossible(agencesDispo, tailleDispo, agencesTestees, tailleTestees, origine, j);
                }
                //si elle n'est pas dispo ou que c'est l'agence d'origine
                else
                {
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

    public void transfertVers(Agence destination, int nbBanquiers)
    {
        
    }
}
