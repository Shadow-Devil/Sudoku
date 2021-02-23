package main.view.components;

import main.controller.Controller;
import main.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public class ChooseNumberLabel extends JLabel {
	
	public ChooseNumberLabel(int number, Controller controller) {
		super(String.valueOf(number));
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(BLACK));
		setBackground(WHITE);
		setFont(new Font("Arial", Font.ITALIC, 20));
		setOpaque(true);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.choseNumber(number);
			}
		});
	}
}
