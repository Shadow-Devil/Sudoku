
class Model
{
    public Feld [][] spielfeld; //Sudokufeld
    //int tmptry = 0; //Debugzahl für Prüfen des Spielfelds
    View view;
    Model (View view)
    {        
        this.view = view;
        spielfeld = new Feld [9][9]; //Erstelle ein leeres Sudokufeld
        start();
    }


    private void start() //Debugging
    {
        //int tmptry = 0;
        erstelleSpielfeld();
        boolean erstSZ = erstelleSpielzahlen();
        while(!erstSZ)
        {
            erstSZ = erstelleSpielzahlen();
            //System.out.println(tmptry);                                
            //tmptry++;
        }            
        //tmptry = 0;
        
        boolean entf = entfernen();

        while(!entf)
        {               
            erstSZ = erstelleSpielzahlen();
            while(!erstSZ)
            {
                erstSZ = erstelleSpielzahlen();
            }

            //System.out.println(tmptry);
            //ausgeben(8,8);
            entf = entfernen();
            //System.out.println(entf);
            //tmptry++;
            
        }
        //System.out.println(tmptry);
        //tmptry = 0;

        
        for(int y=0;y<9; y++)
        {
            for(int x=0;x<9; x++)
            {                   
                aendereInhaltGraphisch(x,y, spielfeld[x][y].getInhalt());
            }            
        }
        
        for(int y=0;y<9; y++)
        {
            for(int x=0;x<9; x++)
            {                   
                if(spielfeld[x][y].getInhalt() == 0)
                {
                    spielfeld[x][y].setzeKonstant(false);
                }
            }            
        }
        //ausgebenkonstant();
        //ausgeben(8,8);
    }


    
    private void erstelleSpielfeld() //Füllen aller Felder mit 0
    {
        for(int y=0; y<9; y++)
        {
            for(int x=0; x<9; x++)
            {
                spielfeld[x][y] = new Feld(x,y,0);
            }
        }
        view.erstelleSpielfeld();
    }
    
    private boolean /*void*/ erstelleSpielzahlen() //Zufälliges Füllen mit Abfrage nach Regelverstoß
    {        

        for(int y=0; y<9; y++)
        {
            for(int x=0; x<9; x++)
            {     
                int zahl =  1 + (int)(Math.random() * 9);
                int tmp = zahl;
                aendereInhaltObjekt(x,y,zahl);
                while(!pruefeSpielzahl(x,y))
                {                   
                    if(zahl == 9)
                    {
                        zahl = 1;
                    }
                    else
                    {
                        zahl++;
                    } 
                    aendereInhaltObjekt(x,y,zahl);
                    if(zahl == tmp) //Spielfeld kann nicht gefüllt werden
                    {
                        //System.out.println("Fehler bei: "+x+"  "+y);
                        //ausgeben(x,y); debugging       
                        x = 9;
                        y = 9;
                        break;
                    }                    
                }
            }
        }

        if(!prüfeSpielfeld(false)) // Feld funktioniert?
        {
            //tmptry++;            
            for(int y=0; y<9; y++) //Reset des Felds
            {
                for(int x=0; x<9; x++)
                {
                    aendereInhaltObjekt(x,y,0);
                }
            }
            //erstelleSpielzahlen();
            //return;
            return false;
        }

        for(int y=0;y<9; y++)
        {
            for(int x=0;x<9; x++)
            {                   
                spielfeld[x][y].setzeKonstant(true);
            }            
        }

        return true;

    }

    
    private boolean prüfeSpielfeld(boolean a) //Prüft alle 81 Felder nach Regelverstoß
    {
        int tmp = 0;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(pruefeSpielzahl(i,j))
                {
                    tmp++;
                }
            }
        }

        if(tmp==81 && a)
        {
            view.endTimer();
            return true;
        }
        else if(tmp==81)
        {
            return true;
        }
        return false;
    }

    private boolean pruefeSpielzahl(int x, int y) //Prüft eine Zahl, nach Regelverstoß
    {
        if(spielfeld[x][y].getInhalt() == 0) //testen, ob Feld beschrieben
        {
            return false;
        }

        for (int i=0; i<9; i++) //Zeilen und Spalten prüfen
        {
            if(spielfeld[x][y].getInhalt() == spielfeld[i][y].getInhalt() && x != i) //Zeilenabfrage
            {
                return false;
            }

            if(spielfeld[x][y].getInhalt() == spielfeld[x][i].getInhalt() && y != i)//Spaltenabfrage
            {
                return false;
            }                        
        }
        int xtmp = ((int)(x/3)) * 3; // Welches 3x3
        int ytmp = ((int)(y/3)) * 3;
        int j = xtmp+3;
        int i = ytmp+3;
        while(ytmp<i)// im 3x3 Quadrat Regelverstoß
        {           
            while(xtmp<j)
            {
                if(spielfeld[x][y].getInhalt() == spielfeld[xtmp][ytmp].getInhalt() && x != xtmp && y != ytmp)
                {
                    return false;
                }
                xtmp++;
            }
            xtmp = ((int)(x/3)) * 3;
            ytmp++;
        }
        return true;
    }

    
    
    private void aendereInhaltObjekt(int x, int y, int zahl)//Inhalt eines Felds ändern
    {
        //System.out.println("nur Init");
        spielfeld[x][y].setzeInhaltInit(zahl);
    }

    public void aendereInhaltGraphisch(int x, int y, int zahl)//Inhalt eines Felds ändern
    {
        spielfeld[x][y].setzeAusgewaehlt(false);
        //System.out.println("KAPPA");
        spielfeld[x][y].setzeInhalt(zahl);
        view.ändereInhalt(x,y,zahl);                    
        view.zahlenfeldSichtbar(false);
    }

    
    
    private boolean entfernen()
    {
        int i = 0;
        int x = 0;
        int y = 0;
        int tmp = 0;
        while(eindeutigloesbar() == 0)
        {            
            x = (int)(Math.random() * 9);
            y = (int)(Math.random() * 9);
            tmp = spielfeld[x][y].getInhalt();
            if(spielfeld[x][y].getInhalt() != 0)
            {
                aendereInhaltObjekt(x,y,0);
                i++;
            }
            //System.out.println("nicht eindeutig " +i);
        }
        System.out.println("x und y " +x +"  "+y);
        aendereInhaltObjekt(x,y,tmp);

        if(i<15 || i > 35)
        {
            System.out.println(i);
            return false;
        }
        else
        {
            return true;
        }
    }

    private int eindeutigloesbar()
    {
        int tmpfeld[][] = new int [9][9];

        for(int y=0;y<9; y++)
        {
            for(int x=0;x<9; x++)
            {
                tmpfeld[x][y] = spielfeld[x][y].getInhalt();

            }            
        }

        int tmp = loesen(0,0, tmpfeld);

        if(tmp == 1)
        {
            return 0;
        }
        else if(tmp>1)
        {
            return 1;
        }
        else
        {

            System.out.println("Fehler, unlösbar :" + tmp);
            return 2;
        }

    }

    private int loesen(int x, int y, int tmpfeld[][])
    {
        int tmp = 0;
        int zahl = 0;
        if(tmpfeld[x][y] == 0)
        {
            zahl = tmpfeld[x][y];
            for(int i=1;i<10;i++)
            {                
                tmpfeld[x][y] = i;
                if(pruefetmpzahl(x,y,tmpfeld))
                {
                    if(x<8)
                    {
                        tmp = tmp + loesen(x+1,y,tmpfeld);
                    }
                    else if(x==8 && y ==8)
                    {
                        return 1;
                    }
                    else
                    {
                        tmp = tmp + loesen(0, y+1,tmpfeld);
                    }
                }
            }
            tmpfeld[x][y] = zahl;
        }
        else
        {
            if(x<8)
            {
                tmp = tmp + loesen(x+1,y,tmpfeld);
            }
            else if(x==8 && y ==8)
            {
                return 1;                
            }
            else
            {
                tmp = tmp + loesen(0, y+1,tmpfeld);
            }
        }

        return tmp;
    }

    private boolean pruefetmpzahl(int x, int y, int tmpfeld[][])
    {
        if(tmpfeld[x][y] == 0) //testen, ob Feld beschrieben
        {
            return false;
        }

        for (int i=0; i<9; i++) //Zeilen und Spalten prüfen
        {
            if(tmpfeld[x][y] == tmpfeld[i][y] && x != i) //Zeilenabfrage
            {
                return false;
            }

            if(tmpfeld[x][y] == tmpfeld[x][i] && y != i)//Spaltenabfrage
            {
                return false;
            }                        
        }
        int xtmp = ((int)(x/3)) * 3; // Welches 3x3
        int ytmp = ((int)(y/3)) * 3;
        int j = xtmp+3;
        int i = ytmp+3;
        while(ytmp<i)// im 3x3 Quadrat Regelverstoß
        {           
            while(xtmp<j)
            {
                if(tmpfeld[x][y] == tmpfeld[xtmp][ytmp] && x != xtmp && y != ytmp)
                {
                    return false;
                }
                xtmp++;
            }
            xtmp = ((int)(x/3)) * 3;
            ytmp++;
        }
        return true;
    }

    
    

    public void angeklickt(int x,int y){
        if(!spielfeld[x][y].getKonstant())
        {
            spielfeld[x][y].setzeAusgewaehlt(true);
            view.zahlenfeldSichtbar(true);
        }        
    }
        
    public void ausgeben(int x, int y) //Ausgeben des Gesamten Feldes
    {
        for(int i=0;i<y+1;i++)
        {
            for(int j=0;j<x+1;j++){
                System.out.print(spielfeld[j][i].getInhalt() + " ");
            }
            System.out.println();
        }
    }

    public void ausgebenkonstant() //Ausgeben des Gesamten Feldes
    {
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++){
                System.out.print(spielfeld[j][i].getKonstant() + "\t");
            }
            System.out.println();
        }
    }
    
    public void test()
    {

    }
}
