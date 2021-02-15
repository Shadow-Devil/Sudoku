package main.Model;

import java.util.function.Predicate;

import static main.Model.Util.*;

public class Model {
	private Field[][] gameBoard = new Field[9][9]; //Sudokufeld
	
	
	/**
	 * Erstelle ein leeres Sudokufeld
	 * Füllen aller Felder mit 0
	 */
	public Model() {
		gameBoard = setEmpty(gameBoard);
	}
	
	/**
	 * Anfang von von dem erstellen eines Sudokus
	 *
	 * @param diff Die Schwierigkeit
	 */
	public void start(Difficulty diff) {
		gameBoard = produceGameBoard(diff);
		
	}
	
	public int load(String s)//Spiel laden
	{
		int i = 0;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				int inhalt = Integer.parseInt(String.valueOf(s.charAt(i++)));
				gameBoard[x][y] = s.charAt(i++) == 't' ? new FinalField(inhalt) : new Field(inhalt);
				
			}
		}
		return i;
	}
	
	
	public boolean isFinished(){
		return Util.isFinished(gameBoard);
	}
	
	
	public boolean isEmpty() {
		return andBoard(gameBoard, field -> field.getInhalt() == 0);
	}
	
	
	public boolean pruefeSpielzahl(int x, int y) {
		return Util.pruefeSpielzahl(gameBoard, x, y);
	}
	
	public boolean getFeldSelected(int x, int y) {
		return gameBoard[x][y].getSelected();
	}
	
	public boolean isFieldNotConstant(int x, int y) {
		return !gameBoard[x][y].isConstant();
	}
	
	public void setFeldSelected(int x, int y, boolean a) {
		gameBoard[x][y].setSelected(a);
	}

	
	private void consumeBoard(TriConsumer<Integer, Integer, Field> c) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				c.consume(x, y, gameBoard[x][y]);
			}
		}
	}
	
	private <T> T loopBoard(TriFunction<Integer, Integer, Field, T> f) {
		T tmp = null;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				tmp = f.apply(x, y, gameBoard[x][y]);
			}
		}
		return tmp;
	}
	
	public Field[][] getGameBoard() {
		return gameBoard;
	}
	
	public void reset() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (!gameBoard[x][y].isConstant()) {
					gameBoard[x][y].setInhalt(0);
				}
			}
		}
	}
	
	public void setField(int x, int y, int zahl) {
		gameBoard[x][y].setInhalt(zahl);
	}
}
