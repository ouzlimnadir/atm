import java.io.IOException;

public class ComptePayant extends Compte {
    private static final double TAUX_OPERATION = 5;
    private static int compteur = 0;

    public ComptePayant(Client sonClient, double depotInit) throws IOException, SecurityException, ClassNotFoundException{
        super(sonClient, depotInit);
        this.setCode(this.getClass().getName() + ":" + (++compteur));
        sonClient.addCompte(this);
    }
    public ComptePayant(Client sonClient) throws IOException, SecurityException, ClassNotFoundException{ this(sonClient, 0); }

    public void deposer ( double depot) { solde += depot-TAUX_OPERATION; }
    public void retirer ( double retrait) { solde -= (retrait + TAUX_OPERATION); }
    public String toString(){
        return super.toString() + " | Taux doperation : " + TAUX_OPERATION;
    }

    public static int getCompteur() {
        return compteur;
    }

    public static void setCompteur(int compteur) {
        ComptePayant.compteur = compteur;
    }
}