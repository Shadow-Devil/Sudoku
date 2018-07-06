class Feld
{
    private int positionX;
    private int positionY;
    private int inhalt;
    private boolean konstant;
    private boolean ausgewaehlt;
    
    Feld (int x, int y)
    {
        positionX = x;
        positionY = y;
        inhalt = 0;
        konstant = false;
        ausgewaehlt = false;
    }
    
    Feld (int x, int y, int zahl)
    {
        positionX = x;
        positionY = y;
        inhalt = zahl;
        konstant = false;
    }

    
    public void setzeInhaltInit(int zahl)
    {
        inhalt = zahl;
    }
    
    public void setzeInhalt(int zahl)
    {
        if(konstant == false)
        {
            inhalt = zahl;
        }
    }
    
    public int getInhalt()
    {
        return inhalt;
    }
    
    
    public void setzepositionX(int x)
    {
        positionX = x;
    }
    
    public int getpositionX()
    {
        return positionX;
    }
    
    
    public void setzepositionY(int y)
    {
        positionX = y;
    }
    
    public int getpositionY()
    {
        return positionY;
    }
    
    
    public void setzeKonstant(boolean k)
    {
        konstant = k;
    }
    
    public boolean getKonstant()
    {
        return konstant;
    }
    
    public boolean getAusgewaehlt() {
        return ausgewaehlt;
    }
    
    public void setzeAusgewaehlt(boolean a) {
        //System.out.println(a);
        ausgewaehlt = a;
    }
}
