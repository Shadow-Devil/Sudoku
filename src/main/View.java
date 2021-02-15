package main;

import main.Model.Field;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static main.Model.Util.EMPTY;

public class View{
	public static final String TITLE = "Sudoku";
	private final JFrame frame;

    public final JPanel gamePanel = new JPanel();
	public final JLabel[][] sudokuFields = new JLabel[9][9];
	
	private String username = "Lieber User";
	
    private final JPanel menuPanel = new JPanel();
	public final JButton[] buttons = new JButton[5];
 
	private final JPanel endPanel = new JPanel();
	private final JLabel winnerText = new JLabel();
	private final JLabel timeLabel = new JLabel();
	public final TextField textField = new TextField(30);
	
	private final JPanel extraPanel = new JPanel();
	private final JPanel chooseNumberPanel = new JPanel();
	public final JLabel[][] chooseNumberFields = new JLabel[3][3];
	private final JLabel helpLabel = new JLabel();
	
	private final JPanel highscorePanel = new JPanel();
	private final JLabel[] highscores = new JLabel[10];
	
	private final JPanel rulePanel = new JPanel();
    
    private boolean help;
    private static final Color DARK_GREY = new Color(60, 60, 60);
 
	public View(Controller controller) {
		frame = new JFrame(TITLE);
		frame.setSize(820, 535);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		ViewBuilder.createStartMenu(frame, controller, menuPanel, buttons);
		
		ViewBuilder.createEndMenu(frame, controller, endPanel, winnerText, timeLabel, textField);
		
		ViewBuilder.createHighScoreMenu(frame, controller, highscorePanel, highscores);

		ViewBuilder.createGameScreen(frame, controller, gamePanel, sudokuFields);
		
		ViewBuilder.createExtraPanel(frame, controller, extraPanel, chooseNumberPanel, chooseNumberFields, helpLabel);
		
		ViewBuilder.createRuleScreen(frame, controller, rulePanel);
		
		frame.setVisible(true);
	}
	
	public void setKonstant(Field[][] board) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (board[x][y].getInhalt() == 0) {
					sudokuFields[x][y].setBackground(WHITE);
					sudokuFields[x][y].setForeground(BLACK);
				} else {
					sudokuFields[x][y].setBackground(DARK_GREY);
					sudokuFields[x][y].setForeground(WHITE);
				}
			}
		}
	}
	
	public void showChooseNumberPanel(boolean show) {
        chooseNumberPanel.setVisible(show);
		frame.repaint();
	}
	
	public void aendereInhalt(int x, int y, int i) {
		sudokuFields[x][y].setText(i == EMPTY ? "" : "" + i);
		showChooseNumberPanel(false);
    }
	
	public void toggleHelp() {
        help = !help;
	}
	
	public void highlighteZahlen(int x, int y, boolean b) {
		if (b && help) {
			sudokuFields[x][y].setBackground(GREEN);
		} else {
			for (int yt = 0; yt < 9; yt++) {
				for (int xt = 0; xt < 9; xt++) {
					sudokuFields[xt][yt].setFont(new Font("Arial", Font.ITALIC, 20));
				}
			}
		}
	}
	
	public void markiereLabel(int x, int y, boolean currentMouse, boolean selected) {
		if (selected) {
			sudokuFields[x][y].setBackground(GRAY);
		} else {
			if (currentMouse) {
				sudokuFields[x][y].setBackground(LIGHT_GRAY);
			} else {
				sudokuFields[x][y].setBackground(WHITE);
			}
		}
		frame.repaint();
	}
	

	
	public void writeHelpLabel(String text) { //Anweisungstext ändern
		helpLabel.setText("<html>" + username + " " + text + "</html>");
		frame.repaint();
	}
	

	
	public void showStartScreen() {
		buttons[0].setText("Neues Spiel");
		buttons[1].setText("Spiel laden");
		buttons[2].setText("Regeln");
		buttons[3].setText("Highscores");
		buttons[4].setText("Beenden");
		
		showPanel(menuPanel);
	}
	
	public void showPauseScreen() {
		buttons[0].setText("Weiterspielen");
		buttons[1].setText("Speichern");
		buttons[2].setText("Regeln");
		buttons[3].setText("Neustart");
		buttons[4].setText("Schließen");
		
		showPanel(menuPanel);
	}
	

	
	public void showEndScreen(boolean show, int z) {
		//"<html>" + username + ": Herzlichen Glückwunsch! <p> Sie haben gewonnen!</p></html>",
		//"Benötigte Zeit: " + timeLabel
		System.out.println("test");
		if (show) {
			timeLabel.setText(String.valueOf(z));
			writeHelpLabel("<p>das Spiel ist zuende</p>");
		}
		showPanel(endPanel);
	}
	
	public String getSpielername() {
		return textField.getText();
	}
	
	public void showHighscoreScreen() {
		showPanel(highscorePanel);
	}
	
	public void highscoreEinfuegen(String nameMitZeit) {
		for (int i = 0; i < 10; i++) {
			if (highscores[i].getText().equals((i + 1) + ". ---") && nameMitZeit != null) {
				highscores[i].setText((i + 1) + ". " + nameMitZeit);
				break;
			}
		}
	}
	
	public void highscoreReset() {
		for (int i = 1; i < 11; i++) {
			highscores[i - 1].setText(i + ". ---");
		}
	}
	

	
	public void showRuleScreen() {
		showPanel(rulePanel);
	}
	
	public void showSelectDifficultyScreen() {
		buttons[0].setText("Easy");
		buttons[1].setText("Medium");
		buttons[2].setText("Hard");
		buttons[3].setText("Extreme");
		buttons[4].setText("Zurück");
		
		showPanel(menuPanel);
	}
	
	public void showGameScreen(){
		showPanel(gamePanel);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void reset(Field[][] board) {
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				if (!board[x][y].isConstant())
					aendereInhalt(x, y, 0);
		frame.repaint();
	}
	
	private void showPanel(JPanel panel){
		gamePanel.setVisible(false);
		menuPanel.setVisible(false);
		endPanel.setVisible(false);
		rulePanel.setVisible(false);
		highscorePanel.setVisible(false);
		
		panel.setVisible(true);
		frame.repaint();
	}
}

