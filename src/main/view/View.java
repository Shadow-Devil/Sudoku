package main.view;

import main.view.components.ChooseNumberLabel;
import main.view.components.MyButton;
import main.view.components.MyLabel;
import main.view.components.SudokuLabel;
import main.view.viewbuilder.*;
import main.controller.Controller;
import main.model.Field;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static main.view.State.*;

public class View {
	public static final String TITLE = "Sudoku";
	
	private final JFrame frame = new JFrame(TITLE);
	
	private final JPanel          gamePanel    = new JPanel();
	public final  SudokuLabel[][] sudokuFields = new SudokuLabel[9][9];
	
	private final JPanel     menuPanel = new JPanel();
	private final MyButton[] buttons   = new MyButton[5];
	
	private final JPanel    endPanel   = new JPanel();
	private final MyLabel   winnerText = new MyLabel();
	private final MyLabel   timeLabel  = new MyLabel();
	private final TextField textField  = new TextField(30);
	
	private final JPanel                chooseNumberPanel  = new JPanel();
	public final  ChooseNumberLabel[][] chooseNumberFields = new ChooseNumberLabel[3][3];
	private final JLabel                helpLabel          = new JLabel();
	
	private final JPanel    highscorePanel = new JPanel();
	private final MyLabel[] highscores     = new MyLabel[10];
	
	private final JPanel rulePanel = new JPanel();
	
	private String username = "Player";
	
	private State   state;
	private State   prevState = START;
	private boolean help;
	
	
	public View(Controller controller) {
		frame.setSize(820, 535);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		ViewBuilder vb = new ViewBuilder(this, controller, frame);
		vb.createStartMenu(menuPanel, buttons);
		vb.createEndMenu(endPanel, winnerText, timeLabel, textField);
		vb.createHighScoreMenu(highscorePanel, highscores);
		vb.createGameScreen(gamePanel, sudokuFields);
		vb.createExtraPanel(new JPanel(), chooseNumberPanel, chooseNumberFields, helpLabel);
		vb.createRuleScreen(rulePanel);
		
		frame.setVisible(true);
		showStartScreen();
	}
	
	public void start(Field[][] board) {
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				sudokuFields[x][y].setField(board[x][y]);
		
		changeState(gamePanel, GAME);
	}
	
	public void showChooseNumberPanel() {
		chooseNumberPanel.setVisible(true);
		frame.repaint();
	}
	
	public void toggleHelp() {
		help = !help;
	}
	
	public void highlighteZahlen(int number) {
		update();
		if(!help || number == 0)
			return;
		
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				sudokuFields[x][y].highlight(number);
	}
	
	//Anweisungstext ändern
	public void writeHelpLabel(String text) {
		text = String.format("<html>%s %s<br><p>%s</p></html>", "Liebe/er", username, text);
		helpLabel.setText(text);
		frame.repaint();
	}
	
	
	public void showStartScreen() {
		writeHelpLabel("Herzlich willkommen zu Sudoku!<br>" +
				"Starte ein neues Spiel, lade einen Spielstand,<br>" +
				"schau dir die Highscores an<br>" +
				"oder ließ erstmal die Regeln durch");
		
		buttons[0].setText("Neues Spiel");
		buttons[1].setText("Spiel laden");
		buttons[2].setText("Regeln");
		buttons[3].setText("Highscores");
		buttons[4].setText("Beenden");
		
		changeState(menuPanel, START);
	}
	
	public void showPauseScreen() {
		buttons[0].setText("Weiterspielen");
		buttons[1].setText("Speichern");
		buttons[2].setText("Regeln");
		buttons[3].setText("Neustart");
		buttons[4].setText("Zurück zum Hauptmenü");
		
		changeState(menuPanel, PAUSE);
	}
	
	public void showEndScreen(long time) {
		winnerText.setText("<html><p>" + username + ": Herzlichen Glückwunsch! <br>" +
				"Sie haben gewonnen! <br> " +
				"</html>");//Gebe deinen Namen unten ein um dich in die Highscore Liste einzutragen</p>
		timeLabel.setText("Benötigte Zeit: " + (time/1000));
		
		textField.setText("");
		writeHelpLabel("Das Spiel ist zuende");
		
		changeState(endPanel, END);
	}
	
	public String getNameInput() {
		return textField.getText();
	}
	
	public void showHighscoreScreen(String[] strings) {
		for (int i = 0; i < highscores.length && i < strings.length; i++)
			highscores[i].setText(String.format("%d. %s", i + 1, strings[i] == null ? "---" : strings[i]));
		changeState(highscorePanel, HIGHSCORE);
	}
	
	public void showRuleScreen() {
		changeState(rulePanel, RULE);
	}
	
	public void showSelectDifficultyScreen() {
		buttons[0].setText("Easy");
		buttons[1].setText("Medium");
		buttons[2].setText("Hard");
		buttons[3].setText("Extreme");
		buttons[4].setText("Zurück");
		
		changeState(menuPanel, SELECTDIFF);
	}
	
	public void showGameScreen() {
		changeState(gamePanel, GAME);
	}
	
	private void changeState(JPanel panel, State state) {
		gamePanel.setVisible(false);
		menuPanel.setVisible(false);
		endPanel.setVisible(false);
		rulePanel.setVisible(false);
		highscorePanel.setVisible(false);
		chooseNumberPanel.setVisible(false);
		
		prevState = this.state;
		this.state = state;
		panel.setVisible(true);
		frame.repaint();
	}
	
	public State getState() {
		return state;
	}
	
	public State getPrevState() {
		return prevState;
	}
	
	public void highlightBad(int x, int y) {
		sudokuFields[x][y].blink();
	}
	
	public void reset() {
		update();
		showGameScreen();
	}
	
	public void choseNumber(int number) {
		SudokuLabel.selected.updateField(number);

		chooseNumberPanel.setVisible(false);
		update();
		frame.repaint();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void update(){
		for (SudokuLabel[] column : sudokuFields)
			for (SudokuLabel label: column)
				label.updateField();
	}
}

