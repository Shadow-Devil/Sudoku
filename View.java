import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class View extends JFrame
{
    JLabel [][] feld;
    JLabel [][] zahlfeld;
    JPanel spielfeld;
    JPanel zusatzfeld;
    JPanel zahlenfeld;
    boolean zahlenfeldSichtbar;
    MouseListener mListener;
    long startTime;
    long endTime;
    long totalTime;
    
    View(MouseListener mListener) {

        this.mListener = mListener;

        feld = new JLabel[9][9];
        zahlfeld = new JLabel [3][3];

        spielfeld = new JPanel();
        spielfeld.setBounds(0,0,500,500);
        spielfeld.setLayout(new GridLayout(9,9));

        zusatzfeld = new JPanel();
        zusatzfeld.setBounds(500,0,300,500);
        zusatzfeld.setLayout(new GridLayout(2,1));

        zahlenfeld = new JPanel();
        zahlenfeld.setLayout(new GridLayout(3,3));

        setSize(800,600);
        setTitle("Sudoku");
        setLayout(null);

        zusatzfeld.add(zahlenfeld);
        add(spielfeld);
        add(zusatzfeld);

        setVisible(true);
        
    }
    
    public void erstelleSpielfeld() {
        for(int y=0;y<9;y++) {
            for(int x=0;x<9;x++) {
                feld[x][y] = new JLabel("0",SwingConstants.CENTER);
                feld[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                feld[x][y].setBackground(Color.WHITE);
                feld[x][y].setFont(new Font("Arial", Font.ITALIC, 20));
                feld[x][y].setOpaque(true);
                feld[x][y].addMouseListener(mListener);
                spielfeld.add(feld[x][y]);
                repaint();
            }
        }
        repaint();

        setVisible(true);
        zahlenfeld();
        zahlenfeldSichtbar(false);
        startTime = System.nanoTime();
    }

    public void zahlenfeld() {
        int inhaltTMP;
        for(int y=0;y<3;y++) {
            for(int x=0;x<3;x++) {
                if(x==0) {
                    inhaltTMP = 1+y;
                }else{
                    if(x==1) {
                        inhaltTMP = 4+y;
                    }else{
                        inhaltTMP = 7+y;
                    }
                }
                zahlfeld[x][y] = new JLabel(""+inhaltTMP,SwingConstants.CENTER);
                zahlfeld[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                zahlfeld[x][y].setBackground(Color.WHITE);
                zahlfeld[x][y].setFont(new Font("Arial", Font.ITALIC, 20));
                zahlfeld[x][y].setOpaque(true);
                zahlfeld[x][y].addMouseListener(mListener);
                zahlenfeld.add(zahlfeld[x][y]);
                repaint();
            }
        }
        repaint();
        setVisible(true);
    }

    public void zahlenfeldSichtbar(boolean b) {
        if(b==true) {
            zahlenfeld.setVisible(true);
            zahlenfeldSichtbar = true;
        }else{
            zahlenfeld.setVisible(false);
            zahlenfeldSichtbar = false;
        }
        repaint();
    }

    public void paintComponents(Graphics g){
            g.setColor(Color.RED);
            g.drawLine(0,165,505,165);
            g.drawLine(0,6*55,9*55,6*55);
            g.drawLine(3*55,0,3*55,9*55);
            g.drawLine(6*55,0,6*55,9*55);
    }

    public void ändereInhalt(int x, int y, int i) {
        feld[x][y].setText(""+i);
        repaint();
        zahlenfeldSichtbar(false);
    }
    
    public void endTimer()
    {
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
}

