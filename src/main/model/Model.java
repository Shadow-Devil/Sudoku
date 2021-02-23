package main.model;

import static main.model.Util.*;

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
	
	//Spiel laden
	public void load(String s){
		int i = 0;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				int inhalt = Integer.parseInt(String.valueOf(s.charAt(i++)));
				gameBoard[x][y] = s.charAt(i++) == 't' ? new FinalField(inhalt) : new Field(inhalt);
				
			}
		}
	}
	
	
	public boolean isFinished(){
		return Util.isFinished(gameBoard);
	}
	
	public boolean isFull(){
		return Util.isFull(gameBoard);
	}
	
	
	public boolean isEmpty() {
		return andBoard(gameBoard, field -> field.getContent() == 0 || field.isConstant());
	}
	
	
	
	
	public boolean pruefeSpielzahl(int x, int y) {
		return Util.pruefeSpielzahl(gameBoard, x, y);
	}
	
	public boolean isFieldConstant(int x, int y) {
		return gameBoard[x][y].isConstant();
	}
	

	private void consumeBoard(TriConsumer<Integer, Integer, Field> c) {
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				c.consume(x, y, gameBoard[x][y]);
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
		consumeBoard((x, y, field) -> field.reset());
	}
	
	public void setField(int x, int y, int zahl) {
		gameBoard[x][y].setInhalt(zahl);
	}
	
	public int getFieldContent(int x, int y){
		return gameBoard[x][y].getContent();
	}
}
