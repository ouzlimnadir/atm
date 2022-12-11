import java.io.Serializable;
import java.util.Date;

public class Operation implements Serializable {
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
        this.proprietaire = Cm.getProprietaire().getNom() + " " + Cm.getProprietaire().getPrenom();
        this.numCompte = Cm.getCode();

        // Informations Concernant l'operation
        this.dateOp = new Date();


        // DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // format.format(date);
    }

    public String getNumOp() {
        return numOp;
    }
    public void setNumOp(String numOp) {
        this.numOp = numOp;
    }

}
