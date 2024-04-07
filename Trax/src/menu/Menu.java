package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import choosenames.ChooseNames;
import game.GameBoard;
import player.Player;
import rules.Rules;
import tile.TileColor;

/**
 * A játék főmenüjét megvalósító grafikus osztály, amely a JFrame osztályból származik le.
 */
@SuppressWarnings("serial")
public class Menu extends JFrame {
	
	/**
	 * Az aktuális piros, fehér játékost reprezentáló objektumok.
	 */
	static Player redPlayer, whitePlayer;
	
	/**
	 * Statikus függvény, amely beállítja az aktuális piros játékost a paraméterként kapott játékosra.
	 * @param player Az új piros játékos objektuma
	 */
	public static void setRedPlayer(Player player) {
		redPlayer = player;
	}
	
	/**
	 * Statikus függvény, amely beállítja az aktuális fehér játékost a paraméterként kapott játékosra.
	 * @param player Az új fehér játékos objektuma
	 */
	public static void setWhitePlayer(Player player) {
		whitePlayer = player;
	}
	
	/**
	 * Statikus függvény, amely visszatér az aktuális piros játékos objektumával
	 * @return A piros játékost reprezentáló onjektum
	 */
	public static Player getRedPlayer() {
		return redPlayer;
	}
	
	/**
	 * Statikus függvény, amely visszatér az aktuális fehér játékos objektumával
	 * @return A fehér játékost reprezentáló onjektum
	 */
	public static Player getWhitePlayer() {
		return whitePlayer;
	}
	
	/**
	 * Az osztály konstruktora.
	 * @throws IOException ha probléma van a használt olyan fájlok valamelyikével, amely lehetetlenné tenné a játék működését.
	 */
	public Menu() throws IOException {
		create();
	}

	/**
	 * Beállítja a menü ablak kinézetét, és a gombok eseménykezelőit beállító függvény.
	 */
	private void create() {
		whitePlayer = new Player("Player1", TileColor.WHITE);
		redPlayer = new Player("Player2", TileColor.RED);
		JButton rulesButton = new JButton("Játékszabály");
		JButton namesButton = new JButton("Játékosnevek megadása");
		JButton resumeButton = new JButton("Korábbi játék folytatása");
		JButton startButton = new JButton("Új játék kezdése");
		Font f1 = new Font(Font.SERIF, Font.BOLD, 20);
		Font f2 = new Font(Font.SERIF, Font.BOLD, 80);
		JLabel title = new JLabel("Trax");
		JLabel background = new JLabel();
		ImageIcon image = new ImageIcon("menuBackground3.png");
		background.setBounds(0, 0, 800, 600);
		background.setIcon(image);

		title.setBounds(320, 30, 300, 100);
		title.setFont(f2);
		title.setForeground(Color.BLACK);

		rulesButton.setBounds(250, 150, 300, 50);
		namesButton.setBounds(250, 250, 300, 50);
		resumeButton.setBounds(250, 350, 300, 50);
		startButton.setBounds(250, 450, 300, 50);

		rulesButton.setBackground(new Color(240,80,80));
		rulesButton.setForeground(Color.BLACK);
		rulesButton.setFont(f1);
		rulesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Rules();
				setVisible(false);
			}
		});

		namesButton.setBackground(Color.BLACK);
		namesButton.setForeground(Color.WHITE);
		namesButton.setFont(f1);
		namesButton.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e){ 
				try {
					new ChooseNames().setVisible(true);
					setVisible(false);
				} catch (IOException | ClassNotFoundException e1) {}
				setVisible(false);}}); 
		
		resumeButton.setBackground(new Color(240,80,80));
		resumeButton.setForeground(Color.BLACK);
		resumeButton.setFont(f1);
		resumeButton.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e){ 
				try {
					new GameBoard(false).setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				setVisible(false);
		}}); 

		startButton.setBackground(Color.BLACK);
		startButton.setForeground(Color.WHITE);
		startButton.setFont(f1);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new GameBoard(true).setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});

		add(title);
		add(rulesButton);
		add(namesButton);
		add(resumeButton);
		add(startButton);
		add(background);

		setTitle("Trax");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
