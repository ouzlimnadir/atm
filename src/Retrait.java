import java.io.IOException;

public class Retrait extends Operation{
    private static int compteur = 0;
    private double somme;

    protected Retrait(Compte Cm, double somme) throws IOException, SecurityException, ClassNotFoundException{
        super(Cm);
        this.setNumOp(this.getClass().getName().substring(0,3)+(++compteur));
        this.somme = somme;
        Cm.retirer(somme);
        save();
    }

    public static int getCompteur() {
        return compteur;
    }
    public static void setCompteur(int compteur) {
        Retrait.compteur = compteur;
    }
}
