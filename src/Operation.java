import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Operation implements Serializable {
    private static String monFic;
    private static final String tmpFic = "tmpOperation.ser";

    protected Date dateOp;
    protected double somme;
    private String gab;

    private String numOp;

    protected Operation(String g, Compte Cm, double somme){
        // Informations Concernant l'operation
        this.dateOp = new Date();
        this.somme = somme;
        this.gab = g;
    }

    public static void save(Operation A) throws IOException, SecurityException, ClassNotFoundException{
        File f1 = new File (monFic);
        if(f1.exists()){
            File f2 = new File (tmpFic);

            FileInputStream fis = new FileInputStream(f1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            FileOutputStream fos = new FileOutputStream(f2);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Object O;
            Operation P;
            while(!((O = ois.readObject()) instanceof EofIndicatorClass)){
                P = (Operation) O;
                oos.writeObject(P);
            }
            oos.writeObject(A);
            oos.writeObject(new EofIndicatorClass());
            oos.close(); fos.close();
            ois.close(); fis.close();
            if(!f1.delete())
                System.out.println("Suppression erroné");
            if(!f2.renameTo(new File(monFic)))
                System.out.println("Renomage erroné");
        } else {
            FileOutputStream fos = new FileOutputStream(f1);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(A);
            oos.writeObject(new EofIndicatorClass());
            oos.close(); fos.close();
        }

    }
    public static void demarrage() throws IOException, ClassNotFoundException{
        File f1 = new File(monFic);
        if(f1.exists()){
            FileInputStream fis = new FileInputStream(f1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object O; Operation A;
            int a;
            while(!((O = ois.readObject()) instanceof EofIndicatorClass)){
                A = (Operation) O;
                a = Integer.parseInt(A.numOp.substring(3));

                if((A instanceof Retrait) && (a > Retrait.getCompteur()))
                    Retrait.setCompteur(a);
                else if((A instanceof Depot) && (a > Depot.getCompteur()))
                    Depot.setCompteur(a);
                else if((A instanceof Virement) && (a > Virement.getCompteur()))
                    Virement.setCompteur(a);

            }
            ois.close(); fis.close();
        }
    }

    public String toString(){
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return s.format(dateOp) + " "
                + getClass().getName() + " "
                + numOp + " "
                + gab + " "
                + somme;
    }

    public String getNumOp() {
        return numOp;
    }
    public void setNumOp(String numOp) {
        this.numOp = numOp;
    }
    public String getGab() {
        return gab;
    }
    public void setGab(String gab) {
        this.gab = gab;
    }
    public static String getMonFic() {
        return monFic;
    }
    public static void setMonFic(String monFic) {
        Operation.monFic = monFic;
    }
    public double getSomme() {
        return somme;
    }
}
