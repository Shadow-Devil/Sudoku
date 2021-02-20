package main.view.viewbuilder;

import main.model.Field;
import main.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.*;
import static main.model.Util.EMPTY;

public class SudokuLabel extends MyLabel {
	private static final Color DARK_GREY = new Color(60, 60, 60);
	public static SudokuLabel selected = null;
	
	private Field field;
	
	public SudokuLabel(View view) {
		super();
		
		setBorder(BorderFactory.createLineBorder(BLACK));
		setBackground(WHITE);
		setFont(new Font("Arial", Font.ITALIC, 20));
		setOpaque(true);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(field.isConstant()){
					view.writeHelpLabel("Nicht änderbar! Wählen sie ein anderes Feld aus");
					return;
				}
				
				if(selected != null)
					selected.unselect();
				selected = SudokuLabel.this;
				view.writeHelpLabel("Bitte klicken sie auf die gewünschte Zahl für das markierte Feld");
				view.showChooseNumberPanel();
				setBackground(GRAY);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(field.isConstant() || SudokuLabel.this == selected)
					return;
				setBackground(LIGHT_GRAY);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(field.isConstant() || SudokuLabel.this == selected)
					return;
				setBackground(WHITE);
			}
		});
	}
	
	private void unselect() {
		setBackground(WHITE);
	}
	
	public void setField(Field field) {
		this.field = field;
		updateField();
		
		if (field.isConstant()) {
			setForeground(WHITE);
			setBackground(DARK_GREY);
		}
	}
	
	public void updateField() {
		int content = field.getContent();
		setText(content == EMPTY ? "" : "" + content);
		setBackground(WHITE);
		setForeground(BLACK);
	}
	
	public void updateField(int number) {
		field.setInhalt(number);
		updateField();
	}
}
