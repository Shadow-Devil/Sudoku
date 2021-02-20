package main.view.viewbuilder;

import main.view.View;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
	private final static Color BUTTON_FCOLOR = ViewBuilder.FCOLOR;
	private final static Color BUTTON_BCOLOR = ViewBuilder.BCOLOR;
	
	
	public MyButton() {
		setup(40);
	}
	
	/**
	 * Default size: 40
	 * @param text the text of the button
	 */
	public MyButton(String text) {
		super(text);
		setup(40);
	}
	
	public MyButton(String text, int size) {
		super(text);
		setup(size);
	}
	
	private void setup(int size){
		setFont(new Font("Arial", Font.PLAIN, size));
		setForeground(BUTTON_FCOLOR);
		setBackground(BUTTON_BCOLOR);
	}
}
