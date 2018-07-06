import java.util.concurrent.*;

class Test //haha noob

{
    public Feld [][] spielfeld;
    int tmptry = 0;
    View view;

    Test (View view)
    {
        this.view = view;
        spielfeld = new Feld [9][9];
        erstelleSpielfeld();
        erstelleSpielzahlen();
    }

    public boolean prüfeSpielfeld()
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

        if(tmp==81)
        {
            return true;
        }
        return false;
    }

    public void erstelleSpielzahlen()
    {        
        for(int y=0; y<9; y++)
        {
            for(int x=0; x<9; x++)
            {     
                int zahl = ThreadLocalRandom.current().nextInt(1, 10);
                int tmp = zahl;
                while(!pruefeSpielzahl(x,y))
                {
                    
                    aendereInhaltObjekt(x,y,zahl);
                    if(zahl == 9)
                    {
                        zahl = 1;
                    }
                    else
                    {
                        zahl++;
                    }
                    if(zahl == tmp)
                    {
                        //System.out.println("Fehler bei: "+x+"  "+y);
                        //ausgeben(x,y);
                        break;
                    }
                }
            }
        }
        if(!prüfeSpielfeld())
        {
            tmptry++;
            System.out.println(tmptry);
            for(int x=0; x<9; x++)
            {
                for(int y=0; y<9; y++)
                {
                    aendereInhaltObjekt(x,y,0);
                }
            }
            erstelleSpielzahlen();
        }
        else
        {

            System.out.println("VICTORY");
            for(int x=0; x<9; x++)
            {
                for(int y=0; y<9; y++)
                {
                    aendereInhaltObjekt(x,y, spielfeld[x][y].getInhalt());
                }
            }
            tmptry = 0;
        }
    }

    public boolean pruefeSpielzahl(int x, int y)
    {
        if(spielfeld[x][y].getInhalt() == 0)
        {
            return false;
        }

        for (int i=0; i<9; i++)
        {
            if(spielfeld[x][y].getInhalt() == spielfeld[i][y].getInhalt() && x != i)
            {
                return false;
            }

            if(spielfeld[x][y].getInhalt() == spielfeld[x][i].getInhalt() && y != i)
            {
                return false;
            }                        
        }
        int xtmp = ((int)(x/3)) * 3;
        int ytmp = ((int)(y/3)) * 3;
        int j = xtmp+3;
        int i = ytmp+3;
        while(ytmp<i)
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

    public void erstelleSpielfeld()
    {
        for(int x=0; x<9; x++)
        {
            for(int y=0; y<9; y++)
            {
                spielfeld[x][y] = new Feld(x,y,0);
            }
        }
        view.erstelleSpielfeld();
    }

    public void aendereInhaltObjekt(int x, int y, int zahl) {
        for(int xt=0; xt<9; xt++)
        {
            for(int yt=0; yt<9; yt++)
            {
                if(spielfeld[xt][yt].getAusgewaehlt() == true) {
                    spielfeld[xt][yt].setzeInhalt(zahl);
                    spielfeld[xt][yt].setzeAusgewaehlt(false);
                }
            }
        }

    }

    public void aendereInhalt(int x, int y, int zahl) {
        for(int xt=0; xt<9; xt++)
        {
            for(int yt=0; yt<9; yt++)
            {
                if(spielfeld[xt][yt].getAusgewaehlt() == true) {
                    spielfeld[xt][yt].setzeInhalt(zahl);
                    spielfeld[xt][yt].setzeAusgewaehlt(false);
                    view.ändereInhalt(x,y,zahl);
                }
            }
        }
        view.zahlenfeldSichtbar(false);
    }

    public void angeklickt(int x, int y) {
        spielfeld[x][y].setzeAusgewaehlt(true);
        view.zahlenfeldSichtbar(true);
    }

}
