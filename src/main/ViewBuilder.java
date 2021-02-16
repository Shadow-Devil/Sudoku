package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.Color.*;
import static java.awt.Color.WHITE;
import static main.Model.Difficulty.*;

public class ViewBuilder {
	
//	private static final Color gameBorder =
	
	private final View view;
	private final Controller controller;
	private final JFrame frame;
	
	ViewBuilder(View view, Controller controller, JFrame frame){
		this.view = view;
		this.controller = controller;
		this.frame = frame;
	}
	
	/**
	 * Inizialises the 5 Buttons
	 */
	public void createStartMenu(Container menuPanel, JButton[] buttons) {
		menuPanel.setLayout(new GridLayout(5, 1));
		menuPanel.setBounds(0, 0, 500, 500);
		buttons[0] = new JButton();
		buttons[0].addActionListener(e -> {
			switch (view.getState()){
				case START -> view.showSelectDifficultyScreen();
				case SELECTDIFF -> controller.start(EASY);
				case PAUSE -> {
					controller.startTimer();
					view.showGameScreen();
				}
			}
		});
		
		buttons[1] = new JButton();
		buttons[1].addActionListener(e -> {
			switch (view.getState()){
				case START -> controller.load();
				case SELECTDIFF -> controller.start(MEDIUM);
				case PAUSE -> controller.save();
			}
		});
		
		buttons[2] = new JButton();
		buttons[2].addActionListener(e -> {
			switch (view.getState()){
				case START, PAUSE -> view.showRuleScreen();
				case SELECTDIFF -> controller.start(HARD);
			}
		});
		
		buttons[3] = new JButton();
		buttons[3].addActionListener(e -> {
			switch (view.getState()){
				case START -> controller.highscore();
				case SELECTDIFF -> controller.start(EXTREME);
				case PAUSE -> controller.reset();
			}
		});
		
		buttons[4] = new JButton();
		buttons[4].addActionListener(e -> {
			switch (view.getState()){
				case START -> System.exit(0);
				case SELECTDIFF, PAUSE -> view.showStartScreen();
			}
		});
		
		for (JButton button : buttons) {
			button.setFont(new Font("Arial", Font.PLAIN, 40));
			button.setForeground(WHITE);
			button.setBackground(BLUE);
			menuPanel.add(button);
		}
		frame.add(menuPanel);
	}
	
	public void createEndMenu(JPanel endPanel, JLabel winnerText, JLabel timeLabel, TextField textField) {
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
		addHighscore.addActionListener(e -> controller.highscoreinsert());
		endPanel.add(addHighscore);
		
		textField.setFont(new Font("Arial", Font.ITALIC, 40));
		endPanel.add(textField);
		
		JButton zurueck = new JButton("Zurück zum Hauptmenü");
		zurueck.setFont(new Font("Arial", Font.PLAIN, 25));
		zurueck.setForeground(WHITE);
		zurueck.setBackground(BLUE);
		zurueck.addActionListener(e -> view.showStartScreen());
		endPanel.add(zurueck);
		
		frame.add(endPanel);
	}
	
	/**
	 *
	 */
	public void createHighScoreMenu(JPanel highscorePanel, JLabel[] highscores) {
		highscorePanel.setBounds(0, 0, 500, 500);
		highscorePanel.setLayout(new GridLayout(11, 1));
		highscorePanel.setBackground(Color.BLUE);
		
		for (int i = 0; i < 10; i++) {
			highscores[i] = new JLabel((i+1) + ". ---", SwingConstants.CENTER);
			highscores[i].setFont(new Font("Arial", Font.ITALIC, 20));
			highscores[i].setForeground(WHITE);
			highscorePanel.add(highscores[i]);
		}
		
		JButton zurueck = new JButton("Zurück");
		zurueck.setBackground(BLUE);
		zurueck.setForeground(WHITE);
		zurueck.setFont(new Font("Arial", Font.PLAIN, 40));
		zurueck.addActionListener(e -> view.highscoreReset());
		highscorePanel.add(zurueck);
		
		frame.add(highscorePanel);
	}
	
	
	public void createRuleScreen(JPanel rulePanel) {
		rulePanel.setLayout(null);
		rulePanel.setBounds(0, 0, 500, 500);
		
        JLabel regeln = new JLabel("<html><p>Sudoku besteht aus 9 großen Feldern, die wiederaum aus 3x3 Feldern besteht.<br>" +
				"Die meisten Felder sind mit Zahlen ausgefüllt, jedoch nicht alle:<br>" +
				"Ziel ist es, die nicht gefüllten Feldern mit Zahlen zu füllen mit Beachtung folgender Regeln:<br>" +
				"<ol> <li> 1.Es darf keine Zahl 2 mal in der Reihe und Spalte vorkommen.</li>" +
				"<li>In den jeweils 9 großen Feldern darf auch keine Zahl 2x vorkommen.</li>" +
				"<li>Die Zahlen, mit denen man die Felder füllt, müssen zwischen 1-9 betragen.</li></ol></html>", SwingConstants.CENTER);
		regeln.setBackground(BLUE);
		regeln.setForeground(WHITE);
		regeln.setOpaque(true);
		regeln.setFont(new Font("Arial", Font.PLAIN, 22));
		regeln.setBounds(0, 0, 500, 400);
		rulePanel.add(regeln);
		
		JButton zurueck = new JButton("Zurück");
		zurueck.setBackground(BLUE);
		zurueck.setForeground(WHITE);
		zurueck.setFont(new Font("Arial", Font.PLAIN, 40));
		zurueck.setBounds(0, 400, 500, 100);
		zurueck.addActionListener(e -> view.showStartScreen());
		rulePanel.add(zurueck);
	
		frame.add(rulePanel);
	}
	
	public void createGameScreen(JPanel gamePanel, JLabel[][] sudokuFields) {
		gamePanel.setBounds(0, 0, 500, 500);
		gamePanel.setLayout(new GridLayout(9, 9));
		
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "PAUSE");
		gamePanel.getActionMap().put("PAUSE", new FunctionalAction(e -> {
			controller.stopTimer();
			view.showPauseScreen();
		}));
		
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "END");
		gamePanel.getActionMap().put("END", new FunctionalAction(e -> view.showEndScreen(1000)));
		
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "HELP");
		gamePanel.getActionMap().put("HELP", new FunctionalAction(e -> view.toggleHelp()));

		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				sudokuFields[x][y] = new JLabel();
				sudokuFields[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				defaultJLabelStyling(sudokuFields[x][y]);
				sudokuFields[x][y].addMouseListener(controller);
//				sudokuFields[x][y].addMouseListener(e -> {
//					controller.aendereInhaltGraphisch(x, y, 0);
//				});
				gamePanel.add(sudokuFields[x][y]);
			}
		}
		
		ZwischenRandErstellen(sudokuFields);
		frame.add(gamePanel);
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
	
	public void createExtraPanel(JPanel extraPanel, JPanel chooseNumberPanel, JLabel[][] chooseNumberFields, JLabel helpLabel) {
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
