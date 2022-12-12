import java.io.*;
import java.util.Date;

public class Operation implements Serializable {
    private static final String monFic = "operation.ser";
    private static final String tmpFic = "tmpOperation.ser";

    private Date dateOp;
    private String proprietaire;
    private String numCompte;
    private String agence;
    private String agenceAdresse;

    private String numOp;

    protected Operation(Compte Cm){
        // Information concernant l'agence
        this.agence = Cm.getProprietaire().getMonAgence().getNumero();
        this.agenceAdresse = Cm.getProprietaire().getMonAgence().getAdresse();

        // Informations concernant le client
        this.proprietaire = Cm.getProprietaire().getNom().toUpperCase() + " " + Cm.getProprietaire().getPrenom();
        this.numCompte = Cm.getCode();

        // Informations Concernant l'operation
        this.dateOp = new Date();


        // DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // format.format(date);
    }

    public void save() throws IOException, SecurityException, ClassNotFoundException{
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
            oos.writeObject(this);
            oos.writeObject(new EofIndicatorClass());
            oos.close(); fos.close();
            ois.close(); fis.close();
            f1.delete();
            f2.renameTo(new File(monFic));
        } else {
            FileOutputStream fos = new FileOutputStream(f1);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.writeObject(new EofIndicatorClass());
            oos.close(); fos.close();
        }

    }

    public String getNumOp() {
        return numOp;
    }
    public void setNumOp(String numOp) {
        this.numOp = numOp;
    }

}
