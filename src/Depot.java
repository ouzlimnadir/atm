import java.io.IOException;
import java.text.SimpleDateFormat;

public class Depot extends Operation{
    private static int compteur = 0;

    public Depot(String g, Compte Cm, double somme) throws IOException, SecurityException, ClassNotFoundException{
        super(g,Cm,somme);
        this.setNumOp(this.getClass().getName().substring(0,3)+(++compteur));
        Cm.deposer(somme);
    }

    public String toString(){
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return s.format(dateOp) + " "
                + getClass().getName() + " "
                + getNumOp() + " "
                + getGab() + " "
                + "- "
                + "- "
                + somme;
    }

    public static int getCompteur() {
        return compteur;
    }
    public static void setCompteur(int compteur) {
        Depot.compteur = compteur;
    }
}