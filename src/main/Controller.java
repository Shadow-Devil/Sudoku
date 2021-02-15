package main;

import main.Model.Difficulty;
import main.Model.Field;
import main.Model.Model;

import java.awt.*;
import java.awt.event.*;

import static java.awt.event.KeyEvent.*;
import static main.Model.Difficulty.*;

public class Controller extends KeyAdapter implements MouseListener, ActionListener {
	public boolean gameRunning;
	public int timer;
	
	private final SQL sql;
	private final Model model;
	private final View view;
	
	private Field[][] gameBoard;
	
	Controller() {
		model = new Model();
		gameBoard = model.getGameBoard();
		view = new View(this);
//		view.addKeyListener(new KeyHandler());
//		view.addMouseListener(new MouseHandler());
//		view.addActionListener(new ActionHandler());
		view.writeHelpLabel(
				"<p>Herzlich willkommen zu Sudoku!</p>" +
						"<p>Starte ein neues Spiel, lade einen Spielstand,</p>" +
						"<p>schau dir die Highscores an</p>" +
						"<p>oder ließ erstmal die Regeln durch</p>");
		
		sql = new SQL();
	}
	
	
	public void start(Difficulty diff) {
		model.start(diff);
		gameBoard = model.getGameBoard();
//		if(view.sudokuFields[0][0] == null)
//			view.createGameScreen();
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				aendereInhaltGraphisch(x, y, gameBoard[x][y].getInhalt());
			}
		}
		view.setKonstant(gameBoard);
		
		timer = 0;
		startTimer();
		gameRunning = true;
		view.showGameScreen();
	}
	
	public void laden() {
		if (sql.sid == 0) {
			System.err.println("Keine Spielstände gespeichert");
			return;
		}
		String s = sql.sget();
		
		view.showGameScreen();
//		if (view.sudokuFields[0][0] == null) {
//			view.createGameScreen();
//		}
		int i = model.load(s);
		if (i != 162) {
			System.err.println("model load weird???");
			System.exit(1);
		}
		int j = s.length() - i;
		System.out.println("i:" + i + " j:" + j);
		while (i < s.length()) {
			timer = timer + (Integer.parseInt(String.valueOf(s.charAt(i))) * 10 ^ j);
			i++;
			j--;
		}
		
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				aendereInhaltGraphisch(x, y, gameBoard[x][y].getInhalt());
			}
		}
		
		view.setKonstant(gameBoard);
		
		gameRunning = true;
		startTimer();
		
	}
	
	public void speichern() {
		sql.insert(model.getGameBoard(), timer);
		gameRunning = false;
		view.showStartScreen();
	}
	
	public void reset() {
		model.reset();
		view.reset(gameBoard);
		timer = 0;
		view.showGameScreen();
	}
	
	
	private void startTimer() {
		new Thread(() -> {
			while (true) {
				if (gameRunning) {
					timer++;
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						System.out.println("Error");
					}
				}
			}
		}).start();
	}
	
	
	//Inhalt eines Felds ändern
	public void aendereInhaltGraphisch(int x, int y, int zahl) {
		gameBoard[x][y].setSelected(false);
//		gameBoard[x][y].setInhalt(zahl);
		view.aendereInhalt(x, y, zahl);
		view.showChooseNumberPanel(false);
		
		if (gameRunning && model.isFinished()) {
			view.showEndScreen(true, timer);
			gameRunning = false;
		} else if (model.isEmpty() && gameRunning) {
			Thread t = new Thread(() -> {
				
				for (int y1 = 0; y1 < 9; y1++) {
					for (int x1 = 0; x1 < 9; x1++) {
						if (!model.pruefeSpielzahl(x1, y1) && !gameBoard[x1][y1].isConstant()) {
							for (int i = 0; i < 5; i++) {
								view.sudokuFields[x1][y1].setForeground(Color.RED);
								try {
									Thread.sleep(100);
								} catch (Exception e) {
									System.out.println("Error");
								}
								view.sudokuFields[x1][y1].setForeground(Color.BLACK);
							}
						}
					}
				}
			});
			t.start();
		}
	}
	
	public void highscore() {
		int i = 0;
		String[] s = sql.hget();
		while (i < 10) {
			view.highscoreEinfuegen(s[i]);
			i++;
		}
	}
	
	public void highscoreinsert() {
		String name = view.getSpielername();
		view.setUsername(name);
		sql.insert(name, timer);
		view.textField.setText("");
	}
	
	public void angeklickt(int x, int y) {
		Field[][] gameBoard = model.getGameBoard();
		if (!gameBoard[x][y].isConstant() && gameRunning) {
			gameBoard[x][y].setSelected(true);
			view.showChooseNumberPanel(true);
			view.writeHelpLabel("<p>Bitte klicken sie auf die gewünschte Zahl für das markierte Feld</p>");
		} else {
			for (int yt = 0; yt < 9; yt++) {
				for (int xt = 0; xt < 9; xt++) {
					if (gameBoard[xt][yt].getInhalt() == gameBoard[x][y].getInhalt()) {
						view.highlighteZahlen(xt, yt, true);
					}
				}
			}
			view.writeHelpLabel("<p>Nicht änderbar! Wählen sie ein anderes Feld aus</p>");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("added KeyListener");
		switch (e.getKeyCode()) {
			//esc-Taste Pausenmenü aufrufen
			case VK_ESCAPE -> {
				gameRunning = false;
				view.showPauseScreen();
			}
			//u-Taste: nur zum Testen des Endscreenes
			case VK_U -> view.showEndScreen(true, 1000);
			//h-Taste (Hilfe an/aus);
			case VK_H -> view.toggleHelp();
			
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Neues Spiel":
				view.showSelectDifficultyScreen();
				break;
			case "Easy":
				start(EASY);
				break;
			case "Medium":
				start(MEDIUM);
				break;
			case "Hard":
				start(HARD);
				break;
			case "Extreme":
				start(EXTREME);
				break;
			case "Weiterspielen":
				view.showGameScreen();
				gameRunning = true;
				break;
			case "Beenden":
				System.exit(0);
			case "Speichern":
				speichern();
				break;
			case "Spiel laden":
				laden();
				break;
			case "Highscores":
				view.showHighscoreScreen();
				highscore();
				break;
			case "Schließen":
				view.showStartScreen();
				break;
			case "Zurück":
				if (view.buttons[0].getText().equals("Easy")) {
					view.showStartScreen();
				} else {
					view.showStartScreen();
					view.highscoreReset();
				}
				break;
			case "Zurück zum Hauptmenü":
				view.showEndScreen(false, 0);
				view.showStartScreen();
				break;
			case "Highscore eintragen":
				highscoreinsert();
				view.showEndScreen(false, 0);
				view.showStartScreen();
				break;
			case "Regeln":
				view.showRuleScreen();
				break;
			case "Neustart":
				reset();
				break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		int inhaltTMP;
		for (int y = 0; y < 9; y++) { //neues Ausgewählt => altes aktuallisiert
			for (int x = 0; x < 9; x++) {
				if (model.getFeldSelected(x, y)) {
					for (int yt = 0; yt < 9; yt++) {
						for (int xt = 0; xt < 9; xt++) {
							if (e.getSource() == view.sudokuFields[xt][yt]) {
								model.setFeldSelected(x, y, false);
								view.markiereLabel(x, y, false, false);
							}
						}
					}
				}
			}
		}
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (e.getSource() == view.sudokuFields[x][y] && model.isFieldNotConstant(x, y)) {
					angeklickt(x, y);
					view.markiereLabel(x, y, true, true);
				}
			}
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (e.getSource() == view.chooseNumberFields[x][y]) {
					if (y == 0) {
						inhaltTMP = 1 + x;
					} else {
						if (y == 1) {
							inhaltTMP = 4 + x;
						} else {
							inhaltTMP = 7 + x;
						}
					}
					
					for (int yt = 0; yt < 9; yt++) {
						for (int xt = 0; xt < 9; xt++) {
							if (model.getFeldSelected(xt, yt)) {
								model.setField(xt, yt, inhaltTMP);
								aendereInhaltGraphisch(xt, yt, inhaltTMP);
								view.markiereLabel(xt, yt, false, false);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mouseEntered(MouseEvent e) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (e.getSource() == view.sudokuFields[x][y] && model.getFeldSelected(x, y) && model.isFieldNotConstant(x, y)) {
//					System.out.println("1");
					view.markiereLabel(x, y, true, true);
				} else if (e.getSource() == view.sudokuFields[x][y] && model.isFieldNotConstant(x, y) && !model.getFeldSelected(x, y)) {
//						System.out.println("2");
						view.markiereLabel(x, y, true, false);
				} else if (model.getFeldSelected(x, y)) {
//					System.out.println("3");
					view.markiereLabel(x, y, false, true);
				} else if (model.isFieldNotConstant(x, y)) {
					view.markiereLabel(x, y, false, false);
				}
				
			}
		}

	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
}