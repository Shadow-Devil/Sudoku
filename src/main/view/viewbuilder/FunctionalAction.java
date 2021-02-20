package main.view.viewbuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunctionalAction extends AbstractAction {

    private final ActionListener myaction;

    public FunctionalAction(ActionListener customaction) {
        this.myaction = customaction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        myaction.actionPerformed(e);
    }
}
