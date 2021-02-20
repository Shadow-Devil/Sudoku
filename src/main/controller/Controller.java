package main.controller;

import main.model.Difficulty;
import main.model.Model;
import main.view.View;

import java.awt.event.*;

public class Controller extends MouseAdapter {
	private final SQL   sql;
	private final Model model;
	private final View  view;
	
	private boolean gameRunning;
	private long    timer = 0;
	private long    startTime;
	
	public Controller() {
		model = new Model();
		view = new View(this);
		sql = new SQL();
	}
	
	
	public void start(Difficulty diff) {
		model.start(diff);
		
		timer = 0;
		startTimer();
		
		view.start(model.getGameBoard());
	}
	
	public void load() {
		if (sql.isSudokuEmpty()) {
			view.writeHelpLabel("Es wurde kein Spielstand gefunden");
			return;
		}
		
		model.load(sql.getSudoku());
		timer = sql.getSudokuTime();
		startTimer();
		
		sql.clearSudoku();
		
		view.start(model.getGameBoard());
	}
	
	public void save() {
		stopTimer();
		view.showStartScreen();
		
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				sb.append(model.getFieldContent(x, y));
				sb.append(model.isFieldConstant(x, y) ? 't' : 'f');
			}
		}
		sql.save(sb.toString(), timer);
	}
	
	public void reset() {
		model.reset();
		view.reset();
		timer = 0;
		startTimer();
	}
	
	public void stopTimer() {
		if (startTime == 0)
			System.err.println("Timer isnt started!");
		
		gameRunning = false;
		timer += System.currentTimeMillis() - startTime;
		startTime = 0;
	}
	
	public void startTimer() {
		gameRunning = true;
		startTime = System.currentTimeMillis();
	}
	
	public void choseNumber(int number) {
		view.choseNumber(number);
		checkEnd();
	}
	
	public void checkEnd() {
		if (!gameRunning || !model.isFull())
			return;
		
		if (model.isFinished()) {
			stopTimer();
			view.showEndScreen(timer / 1000);
			return;
		}
		
		//the sudoku is full but isnt correct => highlight wrong fields
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				if (!model.pruefeSpielzahl(x, y) && !model.isFieldConstant(x, y))
					view.blink(x, y);
	}
	
	public void highscore() {
		view.showHighscoreScreen(sql.getHighscores());
	}
	
	public void highscoreinsert() {
		if(view.getUsername().equals("")){
			view.writeHelpLabel("Bitte gebe einen Namen ein");
			return;
		}
		sql.insertHighscore(view.getUsername(), timer);
		view.showStartScreen();
	}
}