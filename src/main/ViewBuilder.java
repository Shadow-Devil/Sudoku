package main;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;
import static java.awt.Color.WHITE;

public class ViewBuilder {
	
	/**
	 * Inizialises the 5 Buttons
	 */
	public static void createStartMenu(JFrame frame, Controller controller, JPanel menuPanel, JButton[] buttons) {
		menuPanel.setLayout(new GridLayout(5, 1));
		menuPanel.setBounds(0, 0, 500, 500);
		buttons[0] = new JButton("Neues Spiel");
		
		buttons[1] = new JButton("Spiel laden");
		//b[1].addActionListener((e) -> controller.laden());
		
		buttons[2] = new JButton("Regeln");
		//b[2].addActionListener((e) -> regelnfeldAnzeigen(true));
		
		buttons[3] = new JButton("Highscores");
		//b[3].addActionListener();
		
		buttons[4] = new JButton("Beenden");
		for (JButton button : buttons) {
			button.setFont(new Font("Arial", Font.PLAIN, 40));
			button.setForeground(WHITE);
			button.setBackground(BLUE);
			menuPanel.add(button);
			button.addActionListener(controller);
		}
		menuPanel.setVisible(true);
		frame.add(menuPanel);
	}
	
	public static void createEndMenu(JFrame frame, Controller controller, JPanel endPanel, JLabel winnerText, JLabel timeLabel, TextField textField) {
		endPanel.setLayout(new GridLayout(5, 1));
		endPanel.setBounds(0, 0, 500, 500);
		
		winnerText.setHorizontalAlignment(SwingConstants.CENTER);
		winnerText.setFont(new Font("Arial", Font.PLAIN, 25));
		winnerText.setForeground(WHITE);
		winnerText.setBackground(BLUE);
		winnerText.setOpaque(true);
		endPanel.add(winnerText);
		
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		timeLabel.setForeground(WHITE);
		timeLabel.setBackground(BLUE);
		timeLabel.setOpaque(true);
		endPanel.add(timeLabel);
		
		JButton addHighscore = new JButton("Highscore eintragen");
		addHighscore.setFont(new Font("Arial", Font.PLAIN, 25));
		addHighscore.setForeground(WHITE);
		addHighscore.setBackground(BLUE);
		addHighscore.addActionListener(controller);
		endPanel.add(addHighscore);
		
		textField.setFont(new Font("Arial", Font.ITALIC, 40));
		endPanel.add(textField);
		
		JButton zurueck = new JButton("Zurück zum Hauptmenü");
		zurueck.setFont(new Font("Arial", Font.PLAIN, 25));
		zurueck.setForeground(WHITE);
		zurueck.setBackground(BLUE);
		zurueck.addActionListener(controller);
		endPanel.add(zurueck);
		
		endPanel.setVisible(false);
		frame.add(endPanel);
	}
	
	/**
	 *
	 */
	public static void createHighScoreMenu(JFrame frame, Controller controller, JPanel highscorePanel, JLabel[] highscores) {
		highscorePanel.setBounds(0, 0, 500, 500);
		highscorePanel.setLayout(new GridLayout(11, 1));
		highscorePanel.setBackground(Color.BLUE);
		
		JButton zurueck = new JButton("Zurück");
		zurueck.setBackground(BLUE);
		zurueck.setForeground(WHITE);
		zurueck.setFont(new Font("Arial", Font.PLAIN, 40));
		zurueck.addActionListener(controller);
		for (int i = 0; i < 10; i++) {
			highscores[i] = new JLabel((i + 1) + ". ---", SwingConstants.CENTER);
			highscores[i].setFont(new Font("Arial", Font.ITALIC, 20));
			highscores[i].setForeground(WHITE);
			highscorePanel.add(highscores[i]);
		}
		highscorePanel.add(zurueck);
		frame.add(highscorePanel);
		highscorePanel.setVisible(false);
	}
	
	
	public static void createRuleScreen(JFrame frame, Controller controller, JPanel rulePanel) {
		rulePanel.setLayout(null);
		rulePanel.setBounds(0, 0, 500, 500);
		
		JButton zurueck = new JButton("Zurück");
		zurueck.setBackground(BLUE);
		zurueck.setForeground(WHITE);
		zurueck.setFont(new Font("Arial", Font.PLAIN, 40));
		zurueck.setBounds(0, 400, 500, 100);
		zurueck.addActionListener(controller);
        JLabel regeln = new JLabel("<html><p>Sudoku besteht aus 9 großen Feldern, die wiederaum aus 3x3 Feldern besteht.</p>" +
				"<p>Die meisten Felder sind mit Zahlen ausgefüllt, jedoch nicht alle: </p>" +
				"<p> Ziel ist es, die nicht gefüllten Feldern mit Zahlen zu füllen mit Beachtung folgender Regeln: </p>" +
				"<p> 1.Es darf keine Zahl 2x in der Reihe und Spalte vorkommen. </p>" +
				"<p> 2. In den jeweils 9 großen Feldern darf auch keine Zahl 2x vorkommen. </p>" +
				"<p> 3. Die Zahlen, mit denen man die Felder füllt, müssen zwischen 1-9 betragen.</p>", SwingConstants.CENTER);
		regeln.setBackground(BLUE);
		regeln.setForeground(WHITE);
		regeln.setOpaque(true);
		regeln.setFont(new Font("Arial", Font.PLAIN, 22));
		regeln.setBounds(0, 0, 500, 400);
		
		rulePanel.add(regeln);
		rulePanel.add(zurueck);
	
		rulePanel.setVisible(false);
		frame.add(rulePanel);
	}
	
	public static void createGameScreen(JFrame frame, Controller controller, JPanel gamePanel, JLabel[][] sudokuFields) {
		gamePanel.setBounds(0, 0, 500, 500);
		gamePanel.setLayout(new GridLayout(9, 9));
		gamePanel.addKeyListener(controller);
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				sudokuFields[x][y] = new JLabel();
				sudokuFields[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				defaultJLabelStyling(sudokuFields[x][y]);
				sudokuFields[x][y].addMouseListener(controller);
				sudokuFields[x][y].addKeyListener(controller);
				gamePanel.add(sudokuFields[x][y]);
			}
		}
		
		ZwischenRandErstellen(sudokuFields);
		frame.add(gamePanel);
		gamePanel.setVisible(false);
	}
	
	private static void ZwischenRandErstellen(JLabel[][] sudokuFields) {
		Color farbe = new Color(57, 77, 0);
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				sudokuFields[x][y].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, farbe));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			sudokuFields[i][2].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, farbe));
			sudokuFields[2][i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, farbe));
			sudokuFields[i][5].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, farbe));
			sudokuFields[5][i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, farbe));
		}
		sudokuFields[2][2].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, farbe));
		sudokuFields[5][2].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, farbe));
		sudokuFields[2][5].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, farbe));
		sudokuFields[5][5].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, farbe));
	}
	
	public static void createExtraPanel(JFrame frame, Controller controller, JPanel extraPanel, JPanel chooseNumberPanel, JLabel[][] chooseNumberFields, JLabel helpLabel) {
		extraPanel.setBounds(500, 0, 300, 500);
		extraPanel.setLayout(new GridLayout(2, 1));
		
		chooseNumberPanel.setLayout(new GridLayout(3, 3));
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				chooseNumberFields[x][y] = new JLabel(String.valueOf(1 + x + (3*y)), SwingConstants.CENTER);
				defaultJLabelStyling(chooseNumberFields[x][y]);
				chooseNumberFields[x][y].addMouseListener(controller);
				chooseNumberPanel.add(chooseNumberFields[x][y]);
			}
		}
		chooseNumberPanel.setVisible(false);
		extraPanel.add(chooseNumberPanel);
		
		
		JPanel anweisungsfeld = new JPanel();
		anweisungsfeld.setBounds(500, 410, 320, 410);
		anweisungsfeld.setLayout(new GridLayout(1, 1));
		anweisungsfeld.add(helpLabel);
		extraPanel.add(anweisungsfeld);
		
		frame.add(extraPanel);
	}
	
	private static void defaultJLabelStyling(JLabel label){
		label.setBorder(BorderFactory.createLineBorder(BLACK));
		label.setBackground(WHITE);
		label.setFont(new Font("Arial", Font.ITALIC, 20));
		label.setOpaque(true);
	}
}
