import java.io.IOException;
import java.text.SimpleDateFormat;

public class Retrait extends Operation{
    private static int compteur = 0;

    public Retrait(String g, Compte Cm, double somme) throws IOException, SecurityException, ClassNotFoundException{
        super(g,Cm,somme);
        this.setNumOp(this.getClass().getName().substring(0,3)+(++compteur));
        Cm.retirer(somme);
    }

    public String toString(){
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return s.format(dateOp) + " "
                + getClass().getName() + " "
                + getNumOp() + " "
                + getGab() + " "
                + "- "
                + somme + " "
                + "-";
    }

    public static int getCompteur() {
        return compteur;
    }
    public static void setCompteur(int compteur) {
        Retrait.compteur = compteur;
    }
}
