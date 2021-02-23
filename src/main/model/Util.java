package main.model;

import java.util.Random;
import java.util.function.Predicate;

public class Util {
	private static final Random random = new Random();
	public static final  int    EMPTY  = 0;
	
	public static Field[][] produceGameBoard(Difficulty diff) {
		Field[][] fullBoard = Util.produceFullBoard();
		int       deleted;
		Field[][] board;
		do {
			board = copy(fullBoard);
			deleted = 0;
			int x   = 0;
			int y   = 0;
			int tmp = 0;
			while (uniqueSolution(board) == 1) {
				x = random.nextInt(9);
				y = random.nextInt(9);
				tmp = board[x][y].getContent();
				if (tmp != EMPTY) {
					board[x][y].setInhalt(EMPTY);
					deleted++;
				}
			}
			board[x][y].setInhalt(tmp);
		} while (!diff.isInRange(deleted));
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				if (board[x][y].getContent() != 0)
					board[x][y] = new FinalField(board[x][y].getContent());
			}
		}
		
		return board;
	}
	
	/**
	 * Zufälliges Füllen mit Abfrage nach Regelverstoß
	 */
	private static Field[][] produceFullBoard() {
		Field[][] result = setEmpty(new Field[9][9]);
		
		while (!isFull(result)) {
			f:
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					int r      = 1 + random.nextInt(9);
					int runner = r;
					result[x][y].setInhalt(r);
					while (!pruefeSpielzahl(result, x, y)) {
						runner = (runner % 9) + 1;
						result[x][y].setInhalt(runner);
						
						if (r == runner) //Spielfeld kann nicht gefüllt werden
						{
							result = setEmpty(result);
							break f;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Prüft eine Zahl nach Regelverstoß
	 */
	public static boolean pruefeSpielzahl(Field[][] board, int x, int y) {
		int current = board[x][y].getContent();
		
		//testen, ob Feld beschrieben
		if (current == EMPTY)
			return false;
		
		//Zeilen und Spalten prüfen
		for (int i = 0; i < 9; i++) {
			
			//Zeilenabfrage
			if (current == board[i][y].getContent() && x != i)
				return false;
			
			//Spaltenabfrage
			if (current == board[x][i].getContent() && y != i)
				return false;
			
		}
		int xtmp = x / 3 * 3; // Welches 3x3
		int ytmp = y / 3 * 3;
		int j    = xtmp + 3;
		int i    = ytmp + 3;
		while (ytmp < i)// im 3x3 Quadrat Regelverstoß
		{
			while (xtmp < j) {
				if (current == board[xtmp][ytmp].getContent() && x != xtmp && y != ytmp)
					return false;
				
				xtmp++;
			}
			xtmp = x / 3 * 3;
			ytmp++;
		}
		return true;
	}
	
	/**
	 * returns the number of Solutions
	 */
	private static int uniqueSolution(Field[][] board) {
		return solve(0, 0, board);
	}
	
	private static int solve(int x, int y, Field[][] tmpfeld) {
		int counter = 0;
		
		if (tmpfeld[x][y].getContent() == EMPTY) {
			for (int i = 1; i <= 9; i++) {
				tmpfeld[x][y].setInhalt(i);
				if (pruefeSpielzahl(tmpfeld, x, y)) {
					if (x < 8) {
						counter = counter + solve(x + 1, y, tmpfeld);
					} else if (y < 8) {
						counter = counter + solve(0, y + 1, tmpfeld);
					} else {
						return 1;
					}
				}
			}
			tmpfeld[x][y].setInhalt(EMPTY);
		} else {
			if (x < 8) {
				counter = counter + solve(x + 1, y, tmpfeld);
			} else if (y < 8) {
				counter = counter + solve(0, y + 1, tmpfeld);
			} else {
				return 1;
			}
		}
		return counter;
	}
	
	/**
	 * Prüft alle 81 Felder nach Regelverstoß
	 */
	public static boolean isFinished(Field[][] board) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (!pruefeSpielzahl(board, x, y)) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static boolean isFull(Field[][] board) {
		return andBoard(board, field -> field.getContent() != EMPTY);
	}
	
	public static Field[][] setEmpty(Field[][] board) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = new Field();
			}
		}
		return board;
	}
	
	/**
	 * Creates A new Sudoku with every Field copied.
	 *
	 * @param from the old Sudoku where to copy from
	 * @return the new Sudoku
	 */
	public static Field[][] copy(Field[][] from) {
		Field[][] to = new Field[from.length][from.length];
		for (int x = 0; x < from.length; x++)
			for (int y = 0; y < from[0].length; y++)
				to[x][y] = new Field(from[x][y].getContent());
		
		return to;
	}
	
	public static void ausgeben(Field[][] f) //Ausgeben des Gesamten Feldes
	{
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(f[j][i].getContent() + " ");
			}
			System.out.println();
		}
	}
	
	public static void ausgebenkonstant(Field[][] f) //Ausgeben des Gesamten Feldes
	{
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(f[j][i].isConstant() + "\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * Goes through the whole gameboard and checks if the Pradicate holds for every Field.
	 *
	 * @param p The Pradicate to check for
	 * @return True if all Fields return true on the Pradicate else false.
	 */
	public static boolean andBoard(Field[][] board, Predicate<Field> p) {
		for (Field[] column : board)
			for (Field field : column)
				if (!p.test(field))
					return false;
		return true;
	}
}
