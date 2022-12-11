import java.util.Scanner;

public class Authentification {
    private Client Cl;
    private int password;

    public Authentification(Client C){
        this.Cl = C;
    }
    public boolean connect(){
        int width = 30;
        char h='h',m='m',b='b';
        Scanner clavier = new Scanner(System.in);
        int i=3;
        do {
            cadreBarre(width, h);
            cadreString("Authentification", width);
            cadreBarre(width, m);
            nonCadreString("Compte : " + Cl.getCode(), width);
            nonCadreString("Mot de passe : ", width);
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
        int width = 30;
        char h='h',m='m',b='b';
        Scanner clavier = new Scanner(System.in);
        int choix;
        do {
            cadreBarre(width,h);
            cadreString("Bonjour M." + Cl.getNom().toUpperCase(), width);
            cadreBarre(width,m);
            nonCadreString("1. Retrait", width);
            nonCadreString("2. Depot", width);
            nonCadreString("3. Virement", width);
            nonCadreString("4. Solde", width);
            nonCadreString("5. Releve banquaire", width);
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
    public int compte(){
        int width = 30;
        char h='h',m='m',b='b';
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
        return choix;
    }

    public void fin(int L){
        char h='h',m='m',b='b';
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
    public void cadreVide(int L){
        char barrette = '|';
        System.out.print(barrette);
        for(int i=0; i<L-2; i++)
            System.out.print(" ");
        System.out.println(barrette);
    }
}
