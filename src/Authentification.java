import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Authentification {
    private Client Cl;
    private int password;
    private int compte;
    private final char h='h',m='m',b='b';
    private static Agence agenceDest;
    private String gab;

    public Authentification(Client C){
        this.Cl = C;
        gab = "GAB"+(int)Math.floor(Math.random()*901+100);
    }
    public boolean connect(){
        cls0();
        int width = 30;
        Scanner clavier = new Scanner(System.in);
        int i=3;
        do {
            cadreBarre(width, h);
            cadreString("Authentification", width);
            cadreBarre(width, m);
            nonCadreString("Compte : " + Cl.getCode(), width);
            nonCadreString("Mot de passe : ", width);
            //nonCadreString("cheat code",width); cadreString(String.valueOf(Cl.getPassword()),width);
            System.out.print("|          ");
            this.password = clavier.nextInt();
            cadreBarre(width, b);
            if (Cl.getPassword() != (this.password)){
                cadreBarre(width,h);
                cadreString("Mot de passe incorrect", width);
                nonCadreString(--i + "tentatives restantes", width);
                cadreBarre(width,b);
            }
            if(i==0)
                break;
        } while (Cl.getPassword() != (this.password));
        return Cl.getPassword() == (this.password);
    }
    public int menu(){
        cls0();
        int width = 30;
        Scanner clavier = new Scanner(System.in);
        int choix;
        do {
            cadreBarre(width,h);
            cadreString("Bonjour M." + Cl.getNom().toUpperCase(), width);
            cadreString(Cl.getCompte(compte).getCode(),width);
            cadreBarre(width,m);
            nonCadreString("1. Retrait", width);
            nonCadreString("2. Depot", width);
            nonCadreString("3. Virement", width);
            nonCadreString("4. Solde", width);
            nonCadreString("5. Releve bancaire", width);
            nonCadreString("6. Quitter", width);
            cadreBarre(width,m);
            nonCadreString("Votre choix :", width);
            System.out.print("|          ");
            choix = clavier.nextInt();
            cadreBarre(width,b);
            if((choix>6)||(choix<0)) {
                cadreBarre(width,h);
                cadreString("Choix invalid", width);
                cadreBarre(width,b);
            }
        } while ((choix>6)||(choix<0)) ;
        return choix;
    }
    public void compte(){
        cls0();
        int width = 30;
        Scanner clavier = new Scanner(System.in);
        int choix;
        do{
            cadreBarre(width,h);
            nonCadreString("1. Compte Payant", width);
            nonCadreString("2. Compte Epargne", width);
            cadreBarre(width,m);
            nonCadreString("Votre choix :", width);
            System.out.print("|          ");
            choix = clavier.nextInt();
            cadreBarre(width,b);
            if((choix>2)||(choix<1)) {
                cadreBarre(width,h);
                cadreString("Choix invalid", width);
                cadreBarre(width,b);
            }
        } while ((choix>2)||(choix<1));
        compte = choix-1;
        String f = Cl.getCompte(compte).getClass().getName() + Cl.getCompte(compte).getCode().substring(Cl.getCompte(compte).getClass().getName().length()+1);
        f = "Op" + f + ".ser";
        Operation.setMonFic(f);
    }
    public int retrait() throws IOException, SecurityException, ClassNotFoundException{
        cls0();
        int width = 50;
        double somme = -1.11 ;
        Scanner clavier = new Scanner(System.in);

        do {
            if(somme!=-1.11)
                erreur(width);
            cadreBarre(width, h);
            cadreString("Retrait",width);
            cadreBarre(width, m);
            nonCadreString("Somme a retirer :",width);
            System.out.print("|          ");
            somme = clavier.nextDouble();
            cadreBarre(width, b);
        } while((somme<Cl.getCompte(compte).getSolde())&&(somme<=0));
        Operation A = new Retrait(gab,Cl.getCompte(compte),somme);
        Operation.save(A);
        Cl.getMonAgence().update();

        // Accuse de transaction
        cadreBarre(width,h);
        cadreString(somme + " dh est bien retiree.",width);
        cadreBarre(width,b);
        return cls();
    }
    public int depot() throws IOException, ClassNotFoundException {
        cls0();
        int width = 50;
        double somme = -1.11 ;
        Scanner clavier = new Scanner(System.in);

        do {
            if(somme!=-1.11)
                erreur(width);
            cadreBarre(width, h);
            cadreString("Depot",width);
            cadreBarre(width, m);
            nonCadreString("Somme a deposer :",width);
            System.out.print("|          ");
            somme = clavier.nextDouble();
            cadreBarre(width, b);
        } while(somme<=0);
        Operation A = new Depot(gab,Cl.getCompte(compte),somme);
        Operation.save(A);
        Cl.getMonAgence().update();
        // Accuse de transaction
        cadreBarre(width,h);
        cadreString(somme + " dh est bien deposee.",width);
        cadreBarre(width,b);
        return cls();
    }
    public int solde(){
        cls0();
        int width = 50;
        cadreBarre(width,h);
        cadreString("Solde",width);
        cadreBarre(width,m);
        nonCadreString("Votre solde est : "+Cl.getCompte(compte).getSolde(),width);
        cadreBarre(width,b);
        return cls();
    }
    public int virement() throws IOException, ClassNotFoundException {
        cls0();
        int width = 50;
        double somme = -1.11 ;
        String strCompte;
        Compte Cm2= null;
        Scanner clavier = new Scanner(System.in);
        // Recherche du compte dans la base de donnee
        do {
            cadreBarre(width,h);
            nonCadreString("Saisir compte destinataire :",width);
            System.out.print("|          ");
            strCompte = clavier.nextLine();
            cadreBarre(width,b);
            Cm2 = Agence.checkCompte(strCompte);
            if(Cm2 == null){
                erreur(width);
            }
        } while(Cm2 == null);

        do {
            if(somme!=-1.11)
                erreur(width);
            cadreBarre(width, h);
            nonCadreString("Somme a envoyer :",width);
            System.out.print("|          ");
            somme = clavier.nextDouble();
            cadreBarre(width, b);
        } while((somme<Cl.getCompte(compte).getSolde())&&(somme<=0));
        Operation A = new Virement(gab,Cl.getCompte(compte),Cm2,somme);
        Operation.save(A);
        Cl.getMonAgence().update();
        agenceDest.update();
        // Accuse de transaction
        cadreBarre(width,h);
        cadreString(somme + " dh est bien envoyee.",width);
        cadreBarre(width,b);
        return cls();
    }
    public int releve(){
        cls0();
        int width = 100;

        // Entete du doc
        cadreBarre(width,h);
        cadreString("Releve Bancaire",width);
        cadreBarre(width,m);
        nonCadreString("BMTOZ Banque Of Morocco ",width);
        nonCadreString(Cl.getMonAgence().getNumero(),width);
        nonCadreString(Cl.getMonAgence().getAdresse(),width);
        cadreVide(width);
        nonCadreString(Cl.getCompte(compte).getCode()+"/"+Cl.getCode(),width);
        nonCadreString("M."+Cl.getNom().toUpperCase()+" "+Cl.getPrenom(),width);
        nonCadreString(Cl.getAdresse(),width);
        cadreBarre(width,b);
        petitCadreBarre(width);
        String tete = "Date Heure Operation Ref GAB Dest Debit Credit";
        ligneOp(tete);
        ligneOp("------------ -------- ----------- ---------- ---------- ---------------- ------------ ------------");

        // Corps du doc
        File f1 = new File(Operation.getMonFic());
        if(f1.exists()){
            try{
                FileInputStream fis = new FileInputStream(f1);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object O;
                Operation Op;
                double debit = 0, credit = 0;
                while(!((O = ois.readObject())instanceof EofIndicatorClass)){
                    Op = (Operation) O;
                    if(Op instanceof Depot)
                        credit += Op.getSomme();
                    else
                        debit += Op.getSomme();
                    ligneOp(Op.toString());
                }
                afficheTotal(debit,credit,width);
                ois.close(); fis.close();
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        
        // Pied du doc
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        cadreVide(width);
        nonCadreStringInverse("Solde le "+ s.format(date)+" : " + Cl.getCompte(compte).getSolde(),width);
        cadreBarre(width,b);
        return cls();
    }
    public void ligneOp(String S){
        char barrette = '|';
        int[] espace = {12,8,11,10,10,16,12,12};
        int space;
        String[] colonnes = S.split(" ");
        System.out.print(barrette);
        for(int i=0; i<colonnes.length; i++){
            space = espace[i]-colonnes[i].length();
            if(space%2!=0)
                System.out.print(" ");
            for(int j=0; j<space/2; j++)
                System.out.print(" ");
            System.out.print(colonnes[i]);
            for(int j=0; j<space/2; j++)
                System.out.print(" ");
            System.out.print(barrette);
        }
        System.out.println();
    }
    public void afficheTotal(double d, double c, int width){
        String D = String.valueOf(d);
        String C = String.valueOf(c);
        int size = width-22-2-25;
        System.out.print("|");
        for(int i = 0; i<size; i++)
            System.out.print(" ");
        System.out.print("Total des Operations  ");
        
        for(int i=0; i<(12-D.length())/2; i++)
            System.out.print(" ");
        if((12-D.length())%2!=0)
            System.out.print(" ");
        System.out.print(D);
        for(int i=0; i<(12-D.length())/2; i++)
            System.out.print(" ");

        System.out.print(" ");

        for(int i=0; i<(12-C.length())/2; i++)
            System.out.print(" ");
        if((12-C.length())%2!=0)
            System.out.print(" ");
        System.out.print(C);
        for(int i=0; i<(12-C.length())/2; i++)
            System.out.print(" ");
        System.out.println("|");
    }
    public int sure(){
        int width = 30, choix;
        Scanner clavier = new Scanner(System.in);
        do {
            cadreBarre(width, h);
            nonCadreString("Sure de vouloir quitter ?", width);
            cadreBarre(width, b);
            cadreString("0.OUI    1.NON",width);
            System.out.print("|          ");
            choix = clavier.nextInt();
        } while (( choix != 0)&&(choix != 1));
        return choix;
    }
    public int cls(){
        int width = 30;
        int choix;
        Scanner clavier = new Scanner(System.in);
        cadreBarre(width,h);
        cadreString("1. Retour      0. Quitter",width);
        System.out.print("|          ");
        choix = clavier.nextInt();
        cadreBarre(width,b);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return choix;
    }
    public void cls0(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public void fin(int L){
        cadreBarre(L,h);
        cadreString("FIN DU PROGRAMME", L);
        cadreBarre(L,b);
    }
    public void cadreString(String s,int L){
        char barrette = '|';
        L -= (s.length()+2);
        System.out.print(barrette);
        for(int i=0; i<L/2; i++)
            System.out.print(" ");
        System.out.print(s);
        for(int i=0; i<L/2; i++)
            System.out.print(" ");
        if(L%2!=0)
            System.out.print(" ");
        System.out.println(barrette);
    }
    public void nonCadreString(String s, int L){
        char barrette = '|';
        L -= (s.length()+4);
        System.out.print(barrette + "  ");
        System.out.print(s);
        for(int i=0; i<L; i++)
            System.out.print(" ");
        System.out.println(barrette);
    }
    public void nonCadreStringInverse(String s, int L){
        char barrette = '|';
        L -= (s.length()+4);
        System.out.print(barrette);
        for(int i=0; i<L; i++)
            System.out.print(" ");
        System.out.print(s + "  ");
        System.out.println(barrette);
    }
    public void cadreBarre(int L,char pos){
        char ligne = '-';
        char coin = '*';
        if((pos=='m')||(pos=='b'))
            cadreVide(L);
        System.out.print(coin);
        for(int i=0; i<L-2; i++)
            System.out.print(ligne);
        System.out.println(coin);
        if((pos=='m')||(pos=='h'))
            cadreVide(L);
    }
    public void petitCadreBarre(int L){
        char ligne = '-';
        char coin = '*';
        System.out.print(coin);
        for(int i=0; i<L-2; i++)
            System.out.print(ligne);
        System.out.println(coin);
    }
    public void cadreVide(int L){
        char barrette = '|';
        System.out.print(barrette);
        for(int i=0; i<L-2; i++)
            System.out.print(" ");
        System.out.println(barrette);
    }
    public void erreur(int wid){
        cadreBarre(wid, h);
        cadreString("Saisie erronee", wid);
        cadreBarre(wid, b);
    }

    public static Agence getAgenceDest() {
        return agenceDest;
    }
    public static void setAgenceDest(Agence ag) {
        agenceDest = ag;
    }
}
