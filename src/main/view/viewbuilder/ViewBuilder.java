package main.view.viewbuilder;

import main.controller.Controller;
import main.view.*;
import main.view.components.ChooseNumberLabel;
import main.view.components.MyButton;
import main.view.components.MyLabel;
import main.view.components.SudokuLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.Color.*;
import static java.awt.Color.WHITE;
import static main.model.Difficulty.*;

public record ViewBuilder(View view, Controller controller, JFrame frame) {
	public static final  Color FCOLOR       = WHITE;
	public static final  Color BCOLOR       = BLUE;
	private static final Color BORDER_COLOR = new Color(57, 77, 0);
	
	/**
	 * Inizialises the 5 Buttons
	 */
	public void createStartMenu(Container menuPanel, MyButton[] buttons) {
		menuPanel.setLayout(new GridLayout(5, 1));
		menuPanel.setBounds(0, 0, 500, 500);
		
		buttons[0] = new MyButton();
		buttons[0].addActionListener(e -> {
			switch (view.getState()) {
				case START -> view.showSelectDifficultyScreen();
				case SELECTDIFF -> controller.start(EASY);
				case PAUSE -> {
					controller.startTimer();
					view.showGameScreen();
				}
			}
		});
		
		buttons[1] = new MyButton();
		buttons[1].addActionListener(e -> {
			switch (view.getState()) {
				case START -> controller.load();
				case SELECTDIFF -> controller.start(MEDIUM);
				case PAUSE -> controller.save();
			}
		});
		
		buttons[2] = new MyButton();
		buttons[2].addActionListener(e -> {
			switch (view.getState()) {
				case START, PAUSE -> view.showRuleScreen();
				case SELECTDIFF -> controller.start(HARD);
			}
		});
		
		buttons[3] = new MyButton();
		buttons[3].addActionListener(e -> {
			switch (view.getState()) {
				case START -> controller.highscore();
				case SELECTDIFF -> controller.start(EXTREME);
				case PAUSE -> controller.reset();
			}
		});
		
		buttons[4] = new MyButton();
		buttons[4].addActionListener(e -> {
			switch (view.getState()) {
				case START -> System.exit(0);
				case SELECTDIFF, PAUSE -> view.showStartScreen();
			}
		});
		
		for (JButton button : buttons)
			menuPanel.add(button);
		
		frame.add(menuPanel);
	}
	
	public void createEndMenu(JPanel endPanel, MyLabel winnerText, MyLabel timeLabel, TextField textField) {
		endPanel.setLayout(new GridLayout(5, 1));
		endPanel.setBounds(0, 0, 500, 500);
		
		endPanel.add(winnerText);
		
		endPanel.add(timeLabel);
		
		JButton addHighscore = new MyButton("Highscore eintragen", 25);
		addHighscore.addActionListener(e -> controller.highscoreinsert());
		endPanel.add(addHighscore);
		
		textField.setFont(new Font("Arial", Font.ITALIC, 40));
		endPanel.add(textField);
		
		JButton zurueck = new MyButton("Zurück zum Hauptmenü", 25);
		zurueck.addActionListener(e -> view.showStartScreen());
		endPanel.add(zurueck);
		
		frame.add(endPanel);
	}
	
	/**
	 *
	 */
	public void createHighScoreMenu(JPanel highscorePanel, MyLabel[] highscores) {
		highscorePanel.setBounds(0, 0, 500, 500);
		highscorePanel.setLayout(new GridLayout(11, 1));
		highscorePanel.setBackground(BLUE);
		
		for (int i = 0; i < highscores.length; i++) {
			highscores[i] = new MyLabel(20);
			highscorePanel.add(highscores[i]);
		}
		
		MyButton zurueck = new MyButton("Zurück");
		zurueck.addActionListener(e -> view.showStartScreen());
		highscorePanel.add(zurueck);
		
		frame.add(highscorePanel);
	}
	
	
	public void createRuleScreen(JPanel rulePanel) {
		rulePanel.setLayout(null);
		rulePanel.setBounds(0, 0, 500, 500);
		
		MyLabel regeln = new MyLabel("<html><p>Sudoku besteht aus 9 großen Feldern, die wiederaum aus 3x3 Feldern besteht.<br>" +
				"Die meisten Felder sind mit Zahlen ausgefüllt, jedoch nicht alle:<br>" +
				"Ziel ist es, die nicht gefüllten Feldern mit Zahlen zu füllen mit Beachtung folgender Regeln:<br>" +
				"<ol> <li> 1.Es darf keine Zahl 2 mal in der Reihe und Spalte vorkommen.</li>" +
				"<li>In den jeweils 9 großen Feldern darf auch keine Zahl 2x vorkommen.</li>" +
				"<li>Die Zahlen, mit denen man die Felder füllt, müssen zwischen 1-9 betragen.</li></ol></html>", 22);
		regeln.setBounds(0, 0, 500, 400);
		rulePanel.add(regeln);
		
		MyButton zurueck = new MyButton("Zurück");
		zurueck.setBounds(0, 400, 500, 100);
		zurueck.addActionListener(e -> {
			switch (view.getPrevState()) {
				case PAUSE -> view.showPauseScreen();
				case START -> view.showStartScreen();
			}
		});
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
		gamePanel.getActionMap().put("END", new FunctionalAction(e -> {
			controller.stopTimer();
			view.showEndScreen(controller.getTimer());
		}));
		
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "HELP");
		gamePanel.getActionMap().put("HELP", new FunctionalAction(e -> view.toggleHelp()));
		
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				sudokuFields[x][y] = new SudokuLabel(view);
				gamePanel.add(sudokuFields[x][y]);
			}
		}
		
		ZwischenRandErstellen(sudokuFields);
		frame.add(gamePanel);
	}
	
	private static void ZwischenRandErstellen(JLabel[][] sudokuFields) {
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				sudokuFields[x][y].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, BORDER_COLOR));
		
		for (int i = 0; i < 9; i++) {
			sudokuFields[i][2].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, BORDER_COLOR));
			sudokuFields[2][i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, BORDER_COLOR));
			sudokuFields[i][5].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, BORDER_COLOR));
			sudokuFields[5][i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, BORDER_COLOR));
		}
		sudokuFields[2][2].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, BORDER_COLOR));
		sudokuFields[5][2].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, BORDER_COLOR));
		sudokuFields[2][5].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, BORDER_COLOR));
		sudokuFields[5][5].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, BORDER_COLOR));
	}
	
	public void createExtraPanel(JPanel extraPanel, JPanel chooseNumberPanel, JLabel[][] chooseNumberFields, JLabel helpLabel) {
		extraPanel.setBounds(500, 0, 300, 500);
		extraPanel.setLayout(new GridLayout(2, 1));
		
		chooseNumberPanel.setLayout(new GridLayout(3, 3));
		for (int y = 0; y < chooseNumberFields.length; y++) {
			for (int x = 0; x < chooseNumberFields.length; x++) {
				chooseNumberFields[x][y] = new ChooseNumberLabel(1 + x + (3 * y), controller);
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
}
