import java.awt.event.*;

class Controller implements MouseListener
{
    View view;
    Model model;

    Controller() {
        view = new View(this);
        model = new Model (view);
        
        
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e) {
        int inhaltTMP = 0;
        for(int y=0;y<9;y++) {
            for(int x=0;x<9;x++) {
                
                if(e.getSource() == view.feld[x][y]) {                    
                    model.angeklickt(x,y);
                }
            }
        }

        for(int y=0;y<3;y++) {
            for(int x=0;x<3;x++) {
                if(e.getSource() == view.zahlfeld[x][y]) {
                    if(x==0) {
                        inhaltTMP = 1+y;
                    }else{
                        if(x==1) {
                            inhaltTMP = 4+y;
                        }else{
                            inhaltTMP = 7+y;
                        }
                    }

                    for(int yt=0; yt<9; yt++)
                    {
                        for(int xt=0; xt<9; xt++)
                        {
                            if(model.spielfeld[xt][yt].getAusgewaehlt() == true) {
                                System.out.println(inhaltTMP + "\n"+ model.spielfeld[xt][yt]);
                                model.aendereInhaltGraphisch(xt,yt,inhaltTMP);
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseClicked(MouseEvent e) {

    }}
