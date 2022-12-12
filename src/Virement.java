import java.io.IOException;

public class Virement extends Operation{
    private static int compteur = 0;
    private double somme;
    private String destinataire;
    private String numCompteDestinataire;
    private String agenceDestinataire;
    private String agenceAdresseDestinataire;

    protected Virement(Compte Cm, Compte Cd, double somme) throws IOException, SecurityException, ClassNotFoundException{
        super(Cm);
        this.setNumOp(this.getClass().getName().substring(0,3)+(++compteur));
        this.somme = somme;
        this.destinataire = Cd.getProprietaire().getNom().toUpperCase() + " " + Cd.getProprietaire().getPrenom();
        this.numCompteDestinataire = Cd.getCode();
        this.agenceDestinataire = Cd.getProprietaire().getMonAgence().getNumero();
        this.agenceAdresseDestinataire = Cd.getProprietaire().getMonAgence().getAdresse();
        Cm.retirer(somme);
        Cd.deposer(somme);
        save();
    }

    public static int getCompteur() {
        return compteur;
    }
    public static void setCompteur(int compteur) {
        Virement.compteur = compteur;
    }
}