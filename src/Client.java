import java.io.*;

public class Client implements Serializable{
    private String code;
    private String nom;
    private String prenom;
    private String adresse;
    private Agence monAgence;
    private Compte[] mesComptes;
    private final int MAX_COMPTES = 10;
    private int iCompte = 0;
    private static int compteur = 0;
    private int password = 0;

    public Client(Agence agenceClient, String nomClient, String prenomClient, String adresseClient) throws IOException, SecurityException, ClassNotFoundException{
        code = this.getClass().getName() + ":" + (++compteur);
        monAgence = agenceClient;
        nom = nomClient;
        prenom = prenomClient;
        adresse = adresseClient;
        mesComptes = new Compte[MAX_COMPTES];
        agenceClient.addClient(this);
    }

    public Agence getMonAgence() {
        return monAgence;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getPassword() {
        return password;
    }

    public Compte getCompte(int i){ return mesComptes[i]; }

    public static int getCompteur() {
        return compteur;
    }

    public static void setCompteur(int compteur) {
        Client.compteur = compteur;
    }

    public void addCompte(Compte nvCompte) throws IOException, SecurityException, ClassNotFoundException{
        if(iCompte < MAX_COMPTES) {
            mesComptes[iCompte++] = nvCompte;
            monAgence.addCompte(nvCompte);
        }
    }
    public boolean deposer(int i, double money){
        if((i >= 0) && (i < iCompte)) {
            mesComptes[i].deposer(money);
            return true;
        }
        else
            return false;
    }
    public void retirer(int i, double money){ mesComptes[i].retirer(money); }
    public int getNbComptes(){ return iCompte; }
    public String getCode(){ return code; }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String toString(){
        return code + " | " + monAgence.getNumero() + " | Nom : " + nom + " | Prenom : " + prenom + " | Adresse : " + adresse + " | nbComptes : " + iCompte;
    }

    public boolean equals(Client A){
        return (A.code.equals(this.code));
    }

}
