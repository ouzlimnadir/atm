import java.io.*;

public class Agence implements Serializable {
    private static final String monFic = "agence.ser";
    private static final String tmpFic = "tmpAgence.ser";
    
    private final int MAX_CLIENTS = 100;
    private final int MAX_COMPTES = 400;
    private String numero;
    private String adresse;
    private Client[] lesClients;
    private Compte[] lesComptes;
    private int iCompte = 0;
    private int iClient = 0;
    private static int compteur = 0;

    public Agence(String adresseAgence) throws IOException, SecurityException, ClassNotFoundException{
        numero = this.getClass().getName() + ":" + (++compteur);
        adresse = adresseAgence;
        lesClients = new Client[MAX_CLIENTS];
        lesComptes = new Compte[MAX_COMPTES];
        save();
    }

    public Compte getCompte(int i) { return lesComptes[i]; }
    public Client getClient(int i) { return lesClients[i]; }
    public void addCompte (Compte nvCompte) throws IOException, SecurityException, ClassNotFoundException{
        if(iCompte < MAX_COMPTES) lesComptes[iCompte++] = nvCompte;
        update();
    }
    public void addClient(Client nvClient) throws IOException, SecurityException, ClassNotFoundException{
        if(iClient < MAX_CLIENTS) lesClients[iClient++] = nvClient;
        update();
    }
    public int getNbClients() { return iClient; }
    public int getNbComptes() { return iCompte; }
    public String toString() {
        return numero + " | Adresse : " + adresse + " | nb Clients : " + iClient + " | nb Comptes : " + iCompte;
    }
    public String getNumero() { return numero; }

    public boolean equals(Agence A){
        return (A.numero.equals(this.numero));
    }

    public void save() throws IOException, SecurityException, ClassNotFoundException{
        File f1 = new File (monFic);
        if(f1.exists()){
            File f2 = new File (tmpFic);

            FileInputStream fis = new FileInputStream(f1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            FileOutputStream fos = new FileOutputStream(f2);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            boolean found = false;
            Object O;
            Agence P;
            while(!((O = ois.readObject()) instanceof EofIndicatorClass)){
                P=(Agence) O;
                if(equals(P)) {
                    found = true;
                    break;
                }
                oos.writeObject(P);
            }
            if(!found) {
                oos.writeObject(this);
                oos.writeObject(new EofIndicatorClass());
            }
            oos.close(); fos.close();
            ois.close(); fis.close();
            if(found) {
                System.out.println(this + " existe deja");
                f2.delete();
            }
            else {
                f1.delete();
                f2.renameTo(new File(monFic));
            }
        } else {
            FileOutputStream fos = new FileOutputStream(f1);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.writeObject(new EofIndicatorClass());
            oos.close(); fos.close();
        }

    }

    public void update() throws IOException, SecurityException, ClassNotFoundException{
        File f1 = new File (monFic);
        if(f1.exists()){
            File f2 = new File (tmpFic);

            FileInputStream fis = new FileInputStream(f1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            FileOutputStream fos = new FileOutputStream(f2);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Object O;
            Agence P;
            while(!((O = ois.readObject()) instanceof EofIndicatorClass)){
                P=(Agence) O;
                if(equals(P)) {
                    P = this;
                    System.out.println("Mise a jour reussi");
                }
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

    public static void read() throws IOException, ClassNotFoundException{
        File f1 = new File (monFic);
        FileInputStream fis = new FileInputStream(f1);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object O;
        Agence P;
        int i=0;
        while(!((O = ois.readObject()) instanceof EofIndicatorClass)){
            P = (Agence) O;
            ++i;
            System.out.println(P);
        }
        ois.close(); fis.close();
    }

    public static void demarrage() throws IOException, ClassNotFoundException{
        File f1 = new File(monFic);
        if(f1.exists()){
            FileInputStream fis = new FileInputStream(f1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object O; Agence A;
            int a,c,m;
            while(!((O = ois.readObject()) instanceof EofIndicatorClass)){
                A = (Agence) O;
                a = Integer.parseInt(String.valueOf(A.numero.charAt(A.numero.length()-1)));

                // Set Compteur d'agences au demarrage du programme
                if(a>compteur)
                    compteur=a;
                // Set Compteur de client au demarrage du programme
                for(int i=0; i<A.iClient; i++) {
                    c = Integer.parseInt(String.valueOf(A.lesClients[i].getCode().charAt(A.lesClients[i].getCode().length()-1)));
                    if(c>Client.getCompteur())
                        Client.setCompteur(c);
                }
                // Set Compteur de comptes au demarrage du programme

            }
            ois.close(); fis.close();
        }
    }
}


