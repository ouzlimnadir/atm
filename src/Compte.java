import java.io.*;

public class Compte implements Serializable {
    private String code;
    protected double solde;
    protected Client Proprietaire;

    public Compte (Client sonClient, double depotInit) {
        Proprietaire = sonClient;
        solde = depotInit;
    }

    public double getSolde(){ return solde; }
    public void deposer ( double depot) { solde += depot; }
    public void retirer ( double retrait) { solde -= retrait; }
    public String toString(){
        return code + " | Solde : " + solde + " | " + Proprietaire.getMonAgence().getNumero() + " | " + Proprietaire.getCode();
    }

    public boolean equals(Compte A){
        return (A.code.equals(this.code));
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
