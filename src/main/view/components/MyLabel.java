package main.view.components;

import main.view.viewbuilder.ViewBuilder;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

public class MyLabel extends JLabel{
	protected static final Color LABEL_FOREGROUND = ViewBuilder.FCOLOR;
	protected static final Color LABEL_BACKGROUND = ViewBuilder.BCOLOR;
	
	
	public MyLabel() {
		setup(25);
	}
	
	public MyLabel(int size) {
		setup(size);
	}
	
	public MyLabel(String text, int size) {
		super(text);
		setup(size);
	}
	
	private void setup(int size){
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("Arial", Font.PLAIN, size));
		setForeground(LABEL_FOREGROUND);
		setBackground(LABEL_BACKGROUND);
		setOpaque(true);
	}
}
