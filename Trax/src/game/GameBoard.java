package game;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import main.Main;
import menu.Menu;
import player.Player;
import tile.CurvedTile;
import tile.EmptyTile;
import tile.PlacedTile;
import tile.StraightTile;
import tile.Tile;
import tile.TileColor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A játék logikáját megvalósító és a pályát megjelenítő grafikus osztály.
 */
@SuppressWarnings("serial")
public class GameBoard extends JFrame {
	/**
	 * A pálya elemeinek 2D tömbje (8x8).
	 */
	Tile[][] palya;
	/**
	 * A pálya elemeinek 1D tömbje (1x6).
	 */
	Tile[] tiles;
	/**
	 * A pályát megjelenítő grafikus panel.
	 */
	JPanel mainPanel;
	/**
	 * A csempeválasztót megjelenítő grafikus panel.
	 */
	JPanel bottomPanel;
	/**
	 * A fehér illetve piros játékos győzelmét tároló logikai változók.
	 */
	private boolean whiteWins, redWins;
	/**
	 * A fehér illetve piros játékost reprezentáló objektumok.
	 */
	private Player whitePlayer, redPlayer;
	/**
	 * A fehér játékos adatait megjelenítő panel.
	 */
	JPanel whitePlayerPanel;
	/**
	 * A piros játékos adatait megjelenítő panel.
	 */
	JPanel redPlayerPanel;
	/**
	 * A játék mentéséhez használt menuitem.
	 */
	JMenuItem menuitem1;
	/**
	 * A játék befejezéséhez használt menuitem.
	 */
	JMenuItem menuitem2;
	/**
	 * Szövegdoboz, amelyben a játék aktuális állapotáról tájékoztatja a program a játékosokat (ki következik, hibás lépés, győzelem).
	 */
	JLabel text;
	/**
	 * A  csempeválasztón utoljára kiválasztott csempét reprezentáló int.
	 */
	int lastSelected;
	/**
	 * Az eddig lerakott csempék száma.
	 */
	int placedTiles;

	/**
	 * A játék adatainek mentésére és visszaállítására használó szerializáló objektum.
	 */
	GameData game;

	/**
	 * A csempeválasztó eseménykezelője.
	 */
	class SelectTileActionListener implements ActionListener {

		int index;

		public SelectTileActionListener(int index) {
			super();
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			lastSelected = index;
		}

	}

	/**
	 * A pályamező eseménykezelője.
	 */
	class SelectPlaceActionListener implements ActionListener {

		int x, y;

		public SelectPlaceActionListener(int x, int y) {
			super();
			this.x = x;
			this.y = y;

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				addTileIfPossible(x, y);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * Az osztály konstruktora. Beállítja a tagváltozók értékét.  
	 * Beállítja a csempeválasztó mezőket, a játékosokat és a menubart.
	 * A paraméternek megfelelően be/visszaállítja a pályát, majd elindítja a játékot (eseménykezelők beállítása).
	 * @param isNewGame Megadja, hogy új játékot kezdünk, vagy egy korábbit folytatunk.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 */
	public GameBoard(Boolean isNewGame) throws IOException {
		whiteWins = false;
		redWins = false;
		lastSelected = -1;

		placedTiles = 0;
		palya = new Tile[8][8];

		game = new GameData(isNewGame);

		setChoiceButtons();
		loadPlayers();
		setJMenuBar(menubar());

		if (isNewGame) {
			setupNewGame();
		} else {
			try {
				setupPreviousGame();
			} catch (ClassNotFoundException | IOException e) {
				setupNewGame();
			}
		}
		start();
	}

	/**
	 * Új üres pálya beállítását végző függvény.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * */
	private void setupNewGame() throws IOException {
		game.setupNewGame();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				palya[i][j] = new EmptyTile(50, 50);
			}
		}
		drawGameField();
	}

	/**
	 * Korábbi pálya visszaállítását végző függvény.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	private void setupPreviousGame() throws ClassNotFoundException, IOException {
		game.setupPreviousGame();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				palya[i][j] = new EmptyTile(50, 50);
			}
		}
		drawGameField();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				lastSelected = game.getFieldState(i, j);
				addTileIfPossible(i, j);
			}
			redrawGameField();
			checkIfEndOfGame();
		}
	}

	/**
	 * Lerak egy, a az utoljára csempeválasztón kiválasztott új csempét a pályára, amennyiben ez lehetséges, majd visszaállítja a az utoljára kiválasztott csempe értéét default-ra.
	 * Amennyiben nem lehetséges, ezt szövegesen jelzi a felhasználónak.
	 * @param x A mező sor koordinátája a pályán.
	 * @param y A mező oszlop koordinátája a pályán.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 */
	void addTileIfPossible(int x, int y) throws IOException {
		if (palya[x][y].isEmpty()) {
			PlacedTile newtile = null;
			switch (lastSelected) {

			case 0:
				newtile = new StraightTile(x, y, 50, 50, 0);
				break;
			case 1:
				newtile = new StraightTile(x, y, 50, 50, 1);
				break;
			case 2:
				newtile = new CurvedTile(x, y, 50, 50, 0);
				break;
			case 3:
				newtile = new CurvedTile(x, y, 50, 50, 1);
				break;
			case 4:
				newtile = new CurvedTile(x, y, 50, 50, 2);
				break;
			case 5:
				newtile = new CurvedTile(x, y, 50, 50, 3);
				break;
			}
			if (newtile != null) {
				if (isTilePlacable(x, y, newtile)) {
					palya[x][y] = newtile;
					game.setFieldState(x, y, lastSelected);
					addNewFinishedTile(x, y, newtile);
					placedTiles++;

					if (placedTiles % 2 == 0) {
						text.setText(whitePlayer.getName() + " következik");
					} else {
						text.setText(redPlayer.getName() + " következik");
					}

				} else {
					if (placedTiles % 2 == 0) {
						text.setText("Nem megengedett lépés. " + whitePlayer.getName() + " következik");
					} else {
						text.setText("Nem megengedett lépés. " + redPlayer.getName() + " következik");
					}
				}
			}

			lastSelected = -1;
			redrawGameField();
			checkIfEndOfGame();
		}

	}

	/**
	 * A menubart és az eseménykezelőit beállító függvéní, visszatér a menubár objektumával.
	 * @return A menubar objektuma
	 */
	private JMenuBar menubar() {
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menü");
		menuitem1 = new JMenuItem("Mentés");
		menuitem2 = new JMenuItem("Befejezés");

		menuitem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.getMenu().setVisible(true);
					game.saveGame();

				} catch (IOException | ClassNotFoundException e1) {
				}
				setVisible(false);
				dispose();
				;
			}
		});

		menuitem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.getMenu().setVisible(true);
					game.removeGame();

				} catch (Exception e1) {
				}
				setVisible(false);
				dispose();
			}
		});

		menu.add(menuitem1);
		menu.add(menuitem2);
		bar.add(menu);

		return bar;
	}
	
	/**
	 * A csempeválasztó objektumokat beállító függvény.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 */
	private void setChoiceButtons() throws IOException {
		tiles = new Tile[6];
		tiles[0] = new StraightTile(0, 0, 50, 50, 0);
		tiles[1] = new StraightTile(0, 0, 50, 50, 1);
		tiles[2] = new CurvedTile(0, 0, 50, 50, 0);
		tiles[3] = new CurvedTile(0, 0, 50, 50, 1);
		tiles[4] = new CurvedTile(0, 0, 50, 50, 2);
		tiles[5] = new CurvedTile(0, 0, 50, 50, 3);
	}

	/**
	 * A főmenüből a játékosokat reprezentló objektumokat átvevő függvény.
	 */
	public void loadPlayers() {
		whitePlayer = Menu.getWhitePlayer();
		whitePlayerPanel = new JPanel();
		whitePlayerPanel.add(whitePlayer.getPlayerCard());
		whitePlayerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

		redPlayer = Menu.getRedPlayer();
		redPlayerPanel = new JPanel();
		redPlayerPanel.add(redPlayer.getPlayerCard());
		redPlayerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
	}

	/**
	 * A játék kezdetén a teljes pályát kirajzoló függvény.
	 */
	private void drawGameField() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JPanel linePanel;
		for (int i = 0; i < 8; i++) {
			linePanel = new JPanel();
			linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.LINE_AXIS));
			for (int j = 0; j < 8; j++) {
				linePanel.add(palya[i][j]);
			}
			mainPanel.add(linePanel);
		}

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		Tile t = tiles[0];
		t.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bottomPanel.add(t);
		t = tiles[1];
		t.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bottomPanel.add(t);
		t = tiles[2];
		t.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bottomPanel.add(t);
		t = tiles[3];
		t.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bottomPanel.add(t);
		t = tiles[4];
		t.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bottomPanel.add(t);
		t = tiles[5];
		t.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bottomPanel.add(t);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 4, 0, 4));

		mainPanel.add(bottomPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		text = new JLabel();
		text.setFont(new Font("Arial", Font.PLAIN, 24));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setText("Start. " + whitePlayer.getName() + " következik.");

		add(mainPanel, BorderLayout.CENTER);

		add(whitePlayerPanel, BorderLayout.WEST);
		add(redPlayerPanel, BorderLayout.EAST);

		add(text, BorderLayout.SOUTH);
		setTitle("Trax");

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	/**
	 * Új csempe lerakása után a pályát újrarajzoló függvény.
	 */
	private void redrawGameField() {
		remove(mainPanel);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JPanel linePanel;
		for (int i = 0; i < 8; i++) {
			linePanel = new JPanel();
			linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.LINE_AXIS));
			for (int j = 0; j < 8; j++) {
				linePanel.add(palya[i][j]);
			}
			mainPanel.add(linePanel);
		}
		mainPanel.add(bottomPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		add(mainPanel, BorderLayout.CENTER);
		pack();
	}

	/**
	 * A játék kezdetén beállítja a mezőket és a csempeválasztókat reprezentáló gombok eseménykezelőit.
	 */
	private void start() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].addActionListener(new SelectTileActionListener(i));
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				palya[i][j].addActionListener(new SelectPlaceActionListener(i, j));
			}
		}
	}
	/**
	 * Ellenőrzi, hogy egy csembe lerakható-e a pálya x. sorának és y. oszlopának mezőjére.
	 * @param x A mező sor koordinátája a pályán.
	 * @param y A mező oszlop koordinátája a pályán.
	 * @param newTile A hozzáadandó új csempe objektum
	 * @return Igaz, ha szabályos a csempe lerakás, különben hamis.
	 */
	public boolean isTilePlacable(int x, int y, PlacedTile newTile) {
		if(!palya[x][y].isEmpty()) {
			return false;
		}
		if (x > 0) {
			if (!palya[x - 1][y].isEmpty()) {
				if (!newTile.getUpperColor().equals(((PlacedTile) palya[x - 1][y]).getDownColor()))
					return false;
			}
			else if(isThirdFromSameColor(x-1,y,newTile.getUpperColor())) {
				return false;
			}
		}
		if (x < 7) {
			if (!palya[x + 1][y].isEmpty()) {
				if (!newTile.getDownColor().equals(((PlacedTile) palya[x + 1][y]).getUpperColor()))
					return false;
			}
			else if(isThirdFromSameColor(x+1,y,newTile.getDownColor())) {
				return false;
			}
		}
		if (y > 0) {
			if (!palya[x][y - 1].isEmpty()) {
				if (!newTile.getLeftColor().equals(((PlacedTile) palya[x][y - 1]).getRightColor()))
					return false;
			}
			else if(isThirdFromSameColor(x,y-1,newTile.getLeftColor())) {
				return false;
			}
		}
		if (y < 7) {
			if (!palya[x][y + 1].isEmpty()) {
				if (!newTile.getRightColor().equals(((PlacedTile) palya[x][y + 1]).getLeftColor()))
					return false;
			}
			else if(isThirdFromSameColor(x,y+1,newTile.getRightColor())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Ellenőrzi, hogy egy newColor színű csembe lerakásával a pálya x. sorában az y. oszlopban lévő elem mellé eredményezne-e egy mező mellett három azonos színű csempe szélt.
	 * @param x A vizsgált üres csempe sor koordinátája
	 * @param y A vizsgált üres csempe oszlop koordinátája
	 * @param newColor A lerakni kíván csempe oldalának színe az üres mezővel szomszédos oldalon.
	 * @return Igaz, ha legalább a harmadik azonos színű szomszéd lenne, különben hamis.
	 */
	public boolean isThirdFromSameColor(int x, int y, TileColor newColor) {

		int count = 0;
		if (x > 0 && !palya[x - 1][y].isEmpty() && ((PlacedTile) palya[x - 1][y]).getDownColor().equals(newColor))
			count++;
		if (x < 7 && !palya[x + 1][y].isEmpty() && ((PlacedTile) palya[x + 1][y]).getUpperColor().equals(newColor))
			count++;
		if (y > 0 && !palya[x][y - 1].isEmpty() && ((PlacedTile) palya[x][y - 1]).getRightColor().equals(newColor))
			count++;
		if (y < 7 && !palya[x][y + 1].isEmpty() && ((PlacedTile) palya[x][y + 1]).getLeftColor().equals(newColor))
			count++;

		if (count >= 2)
			return true;
		return false;

	}

	/**
	 * Lerak egy új csempét a pályára, majd ellenőrzi, hogy ezzel befejeződöt-e a játék. Ha igen, beállítja az adott játékos győzelmét.
	 * @param x A mező sor koordinátája a pályán.
	 * @param y A mező oszlop koordinátája a pályán.
	 * @param newTile A hozzáadandó új csempe objektum
	 */
	public void addNewFinishedTile(int x, int y, PlacedTile newTile) {

		newTile.setRedEdge(newTile);
		newTile.setWhiteEdge(newTile);

		if (x > 0) {
			if (!palya[x - 1][y].isEmpty()) {
				switch (((PlacedTile) palya[x - 1][y]).getDownColor()) {
				case RED:
					newTile.setRedEdge(((PlacedTile) palya[x - 1][y]).getRedEdge());
					newTile.getRedEdge().setRedEdge(newTile);
					break;
				case WHITE:
					newTile.setWhiteEdge(((PlacedTile) palya[x - 1][y]).getWhiteEdge());
					newTile.getWhiteEdge().setWhiteEdge(newTile);
					break;
				}

			}
		}
		if (x < 7) {
			if (!palya[x + 1][y].isEmpty()) {

				switch (((PlacedTile) palya[x + 1][y]).getUpperColor()) {
				case RED:
					if (newTile.getRedEdge().equals(newTile)) {
						newTile.setRedEdge(((PlacedTile) palya[x + 1][y]).getRedEdge());
						newTile.getRedEdge().setRedEdge(newTile);
					} else {
						if (newTile.getRedEdge().equals(palya[x + 1][y])) {
							redWon();
						} else {
							PlacedTile tmp1 = ((PlacedTile) palya[x + 1][y]).getRedEdge();
							PlacedTile tmp2 = newTile.getRedEdge();
							tmp1.setRedEdge(tmp2);
							tmp2.setRedEdge(tmp1);
						}
					}
					break;
				case WHITE:
					if (newTile.getWhiteEdge().equals(newTile)) {
						newTile.setWhiteEdge(((PlacedTile) palya[x + 1][y]).getWhiteEdge());
						newTile.getWhiteEdge().setWhiteEdge(newTile);
					} else {
						if (newTile.getWhiteEdge().equals(palya[x + 1][y])) {
							whiteWon();
						} else {
							PlacedTile tmp1 = ((PlacedTile) palya[x + 1][y]).getWhiteEdge();
							PlacedTile tmp2 = newTile.getWhiteEdge();
							tmp1.setWhiteEdge(tmp2);
							tmp2.setWhiteEdge(tmp1);
						}
					}
					break;
				}
			}
		}
		if (y > 0) {
			if (!palya[x][y - 1].isEmpty()) {
				switch (((PlacedTile) palya[x][y - 1]).getRightColor()) {
				case RED:
					if (newTile.getRedEdge().equals(newTile)) {
						newTile.setRedEdge(((PlacedTile) palya[x][y - 1]).getRedEdge());
						newTile.getRedEdge().setRedEdge(newTile);
					} else {
						if (newTile.getRedEdge().equals(palya[x][y - 1])) {
							redWon();
						} else {
							PlacedTile tmp1 = ((PlacedTile) palya[x][y - 1]).getRedEdge();
							PlacedTile tmp2 = newTile.getRedEdge();
							tmp1.setRedEdge(tmp2);
							tmp2.setRedEdge(tmp1);
						}
					}
					break;
				case WHITE:
					if (newTile.getWhiteEdge().equals(newTile)) {
						newTile.setWhiteEdge(((PlacedTile) palya[x][y - 1]).getWhiteEdge());
						newTile.getWhiteEdge().setWhiteEdge(newTile);
					} else {
						if (newTile.getWhiteEdge().equals(palya[x][y - 1])) {
							whiteWon();
						} else {
							PlacedTile tmp1 = ((PlacedTile) palya[x][y - 1]).getWhiteEdge();
							PlacedTile tmp2 = newTile.getWhiteEdge();
							tmp1.setWhiteEdge(tmp2);
							tmp2.setWhiteEdge(tmp1);
						}
					}
					break;
				}
			}
		}
		if (y < 7) {
			if (!palya[x][y + 1].isEmpty()) {
				switch (((PlacedTile) palya[x][y + 1]).getLeftColor()) {
				case RED:
					if (newTile.getRedEdge().equals(newTile)) {
						newTile.setRedEdge(((PlacedTile) palya[x][y + 1]).getRedEdge());
						newTile.getRedEdge().setRedEdge(newTile);
					} else {
						if (newTile.getRedEdge().equals(palya[x][y + 1])) {
							redWon();
						} else {
							PlacedTile tmp1 = ((PlacedTile) palya[x][y + 1]).getRedEdge();
							PlacedTile tmp2 = newTile.getRedEdge();
							tmp1.setRedEdge(tmp2);
							tmp2.setRedEdge(tmp1);
						}
					}
					break;
				case WHITE:
					if (newTile.getWhiteEdge().equals(newTile)) {
						newTile.setWhiteEdge(((PlacedTile) palya[x][y + 1]).getWhiteEdge());
						newTile.getWhiteEdge().setWhiteEdge(newTile);
					} else {
						if (newTile.getWhiteEdge().equals(palya[x][y + 1])) {
							whiteWon();
						} else {
							PlacedTile tmp1 = ((PlacedTile) palya[x][y + 1]).getWhiteEdge();
							PlacedTile tmp2 = newTile.getWhiteEdge();
							tmp1.setWhiteEdge(tmp2);
							tmp2.setWhiteEdge(tmp1);
						}
					}
					break;
				}
			}
		}

		if (newTile.getRedEdge().getTileX() == 0 && newTile.getRedEdge().getRedEdge().getTileX() == 7)
			redWon();
		if (newTile.getRedEdge().getTileY() == 0 && newTile.getRedEdge().getRedEdge().getTileY() == 7)
			redWon();

		if (newTile.getWhiteEdge().getTileX() == 0 && newTile.getWhiteEdge().getWhiteEdge().getTileX() == 7)
			whiteWon();
		if (newTile.getWhiteEdge().getTileY() == 0 && newTile.getWhiteEdge().getWhiteEdge().getTileY() == 7)
			whiteWon();

	}

	/**
	 * Beállítja a fehér játékos győzelmét.
	 */
	private void whiteWon() {
		whiteWins = true;
	}

	/**
	 * Beállítja a piros játékos győzelmét.
	 */
	private void redWon() {
		redWins = true;
	}
	
	/**
	 * Ellenőrzi, hogy befejeződött e a játék.
	 * Ha igen, akkor kiírja a győztes nevét, és letiltja az új csempe választás lehetőségét.
	 */
	private void checkIfEndOfGame() {
		if (redWins && whiteWins) {
			text.setText("Döntetlen!");
		} else if (redWins) {
			text.setText(redPlayer.getName() + " nyerte a játékot!");
		} else if (whiteWins) {
			text.setText(whitePlayer.getName() + " nyerte a játékot!");
		}

		if (redWins || whiteWins) {
			for (int i = 0; i < 6; i++) {
				tiles[i].setEnabled(false);

			}
		}
	}

	/**
	 * A teszteléshez használt függvény, amely megadja a pálya x. sorában és y. oszlopában lévő mezőről, hogy raktak-e le már oda csempét.
	 * @param x A mező sor koordinátája a pályán.
	 * @param y A mező oszlop koordinátája a pályán.
	 * @return Ha van PlacedTile az adott mezőn igaz, különben hamis.
	 */
	public boolean isTileEmpty(int x, int y) {
		return palya[x][y].isEmpty();
	}
	
	/**
	 * A teszteléshez használt függvény, amely visszatér a pálya x. sorában és y. oszlopában lévő mező referenciájával.
	 * @param x A mező sor koordinátája a pályán.
	 * @param y A mező oszlop koordinátája a pályán.
	 * @return A pálya x. sorában és y. oszlopában lévő mező referenciája
	 */
	public Tile getFieldTileToCLick(int x, int y) {
		return palya[x][y];
	}
	
	/**
	 * A teszteléshez használt függvény, amely visszatér az x. csempeválasztó objektum referenciájával.
	 * @param x Az x. csempeválasztó objektum
	 * @return Az x. csempeválasztó objektum referenciája
	 */
	public Tile getChoiceTileToClick(int x) {
		return tiles[x];
	}
	
	/**
	 * A teszteléshez használt függvény, amely visszatér a pálya x. sorában és y. oszlopában lévő mező referenciájával.
	 * @param x A mező sor koordinátája a pályán.
	 * @param y A mező oszlop koordinátája a pályán.
	 * @return A pálya x. sorában és y. oszlopában lévő mező állapota
	 */
	public int getFieldTileState(int x, int y) {
		return game.getFieldState(x, y);
	}
	
	/**
	 * A pálya állapotának mentésére használt menuitem gettere. * @return Visszatér a tábla mentését végző JMenuItem referenciájával.
	  * @return Visszatér a játék mentését végző JMenuItem referenciájával.
	 */
	public JMenuItem getSaveMenuItem() {
		return menuitem1;
	}
	
	/**
	 * Ajáték befejezésére használt menuitem gettere.
	 * @return Visszatér a játék törlését végző JMenuItem referenciájával.
	 */
	public JMenuItem getEndMenuItem() {
		return menuitem2;
	}
}
