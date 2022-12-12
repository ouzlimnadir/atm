import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
/**
 * todo list
 * 1- compteur des operations
 * 2- reste d'autentifications
 */
public class ATM {
    public static void main (String[] args) throws IOException, ClassNotFoundException {
        // Au demarrage du programme, celui ci remet les compteurs en fonction des fichiers disponibles
        Agence.demarrage();

        // Creation d'une base de donnee
        File f1 = new File("agence.ser");
        if(!f1.exists())
            progTest();

        // Supposons qu'un client a inseré sa carte et le programme détecte son numéro de compte
        Agence A0;
        Client CL0;
        Compte CM0;
        FileInputStream fis = new FileInputStream(f1);
        ObjectInputStream ois = new ObjectInputStream(fis);
        A0 = (Agence) ois.readObject();
        ois.close(); fis.close();
        CL0 = A0.getClient(0);

        Authentification Auth = new Authentification(CL0);
        if(Auth.connect()){
            Auth.compte();
            int menu;
            do {
                switch ( menu = Auth.menu() ){
                    case 1 -> Auth.retrait();
                    case 2 -> Auth.depot();
                    case 3 -> Auth.virement();
                    case 4 -> Auth.solde();
                    case 5 -> Auth.releve();
                }
                if(menu==0){
                    Auth.sure();
                }
            } while (menu > 0);

        }

        Auth.fin(30);

    }
    public static void progTest() throws IOException, ClassNotFoundException{
        // Simunation d'un compte deja existant
        Agence A01 = new Agence("Agadir salam");
        Client C01 = new Client(A01,"OUZLIM","Nadir","Immeuble 5 N55 Salam");
        C01.setPassword(1234);
        Compte CM1 = new ComptePayant(C01,8000);
        Compte CM2 = new CompteEpargne(C01,50000);

        Agence A02 = new Agence("Agadir Dakhla");
        Client C02 = new Client(A02,"Alaoui","Ahmed","Secteur B Villa 8 Sonaba");
        C01.setPassword(2222);
        Compte CM3 = new ComptePayant(C02,10000);
        Compte CM4 = new CompteEpargne(C02,1000);

        Client C03 = new Client(A02,"BAKA","Mohamed","Residence lka=hair Talborjt");
        C01.setPassword(3333);
        Compte CM5 = new ComptePayant(C03,5000);
    }
}
