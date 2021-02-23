package main.view.components;

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
	private boolean highlighted;
	
	
	public SudokuLabel(View view) {
		super();
		
		setBorder(BorderFactory.createLineBorder(BLACK));
		setBackground(WHITE);
		setFont(new Font("Arial", Font.ITALIC, 20));
		setOpaque(true);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.highlighteZahlen(field.getContent());
				
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
				if(field.isConstant() || SudokuLabel.this == selected || highlighted)
					return;
				setBackground(LIGHT_GRAY);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(field.isConstant() || SudokuLabel.this == selected || highlighted)
					return;
				setBackground(WHITE);
			}
		});
	}
	
	private void unselect() {
		if(!highlighted)
			setBackground(WHITE);
	}
	
	public void setField(Field field) {
		this.field = field;
		updateField();
	}
	
	public void updateField() {
		setForeground(field.isConstant() ? WHITE : BLACK);
		setBackground(field.isConstant() ? DARK_GREY:WHITE);
		highlighted = false;
		
		int content = field.getContent();
		setText(content == EMPTY ? "" : "" + content);
	}
	
	public void updateField(int number) {
		field.setInhalt(number);
		updateField();
		selected = null;
	}
	
	public void highlight(int number){
		if(field.getContent() == number && field.isConstant()){
			highlighted = true;
			setForeground(BLACK);
			setBackground(GREEN);
		}else if(field.getContent() == number){
			highlighted = true;
			setBackground(YELLOW);
		}
	}
	
	public void blink() {
		if(field.isConstant())
			return;
		
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					setBackground(RED);
					Thread.sleep(500);
					setBackground(WHITE);
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
