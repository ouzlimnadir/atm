import java.io.IOException;

public class Depot extends Operation{
    private static int compteur = 0;
    private double somme;

    protected Depot(Compte Cm, double somme) throws IOException, SecurityException, ClassNotFoundException{
        super(Cm);
        this.setNumOp(this.getClass().getName().substring(0,3)+(++compteur));
        this.somme = somme;
        Cm.deposer(somme);
        save();
    }

    public static int getCompteur() {
        return compteur;
    }
    public static void setCompteur(int compteur) {
        Depot.compteur = compteur;
    }
}