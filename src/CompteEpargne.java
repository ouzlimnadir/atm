import java.io.IOException;

public class CompteEpargne extends Compte {
    public static double taux = 6;
    private static int compteur = 0;

    public CompteEpargne(Client sonClient, double depotInit) throws IOException, SecurityException, ClassNotFoundException {
        super(sonClient, depotInit);
        this.setCode(this.getClass().getName() + ":" + (++compteur));
        sonClient.addCompte(this);
    }
    public CompteEpargne(Client sonClient) throws IOException, SecurityException, ClassNotFoundException{
        this(sonClient, 0);
    }

    public void calculInteret(){ solde *= 1 + (taux/100); }
    public String toString(){
        return super.toString() + " | Taux dinteret : " + taux;
    }

    public double getTaux(){ return taux;}
    public static int getCompteur() {
        return compteur;
    }
    public static void setTaux(double taux){ CompteEpargne.taux = taux; }
    public static void setCompteur(int compteur) {
        CompteEpargne.compteur = compteur;
    }
}
