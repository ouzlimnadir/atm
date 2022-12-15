import java.io.IOException;
import java.text.SimpleDateFormat;

public class Virement extends Operation{
    private static int compteur = 0;
    private Compte dest;

    public Virement(String g, Compte Cm, Compte Cd, double somme) throws IOException, SecurityException, ClassNotFoundException{
        super(g,Cm,somme);
        this.setNumOp(this.getClass().getName().substring(0,3)+(++compteur));
        this.dest = Cd;
        Cm.retirer(somme);
        Cd.deposer(somme);
    }

    public String toString(){
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return s.format(dateOp) + " "
                + getClass().getName() + " "
                + getNumOp() + " "
                + getGab() + " "
                + dest.getCode() + " "
                + somme + " "
                + "-";
    }

    public static int getCompteur() {
        return compteur;
    }
    public static void setCompteur(int compteur) {
        Virement.compteur = compteur;
    }
}