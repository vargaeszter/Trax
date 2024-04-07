package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import menu.Menu;
import serializer.Serializer;

/**
 * Az uktuális játék mentéséhez, korábbi játék visszaállításához a szerializációt megvalósító és kezelő osztály.
 */

public class GameData {

	/**
	 * A pálya aktuális állapotának szerializálásához használt tagváltozó.
	 */
	Serializer<int[][]> saver;
	
	/**
	 * A pálya aktuális állapotát reprezentáló int tömb (szerializáláshoz) .
	 */
	int[][] fieldState;
	
	/**
	 * A mentett játékok fájlneveinek listája.
	 */
	private ArrayList<String> games;
	
	/**
	 * A mentett játékok szerializálásához használt tagváltozó.
	 */
	private Serializer<ArrayList<String>> gamelist;
	
	/**
	 * Statikus String változó az aktuális könyvtár elérési útjának tárolására (fájl törléséhez).
	 */
	static String path;
	
	/**
	 * Statikus File típusú változó az aktuális könyvtár tárolására (fájl törléséhez).
	 */
	static File wd;

	/**
	 * Az osztály egy példányának konstruktora. Beállítja a tagváltozók értékeit.
	 * @param isNewGame megadja, hogy új játékot kívánunk e kezdeni, vagy egy korábbit folytatni.
	 */
	public GameData(boolean isNewGame) {
		games = new ArrayList<String>();
		gamelist = new Serializer<ArrayList<String>>();
		path = System.getProperty("user.dir");
		wd = new File(path);
		saver = new Serializer<int[][]>();
		fieldState = new int[8][8];

	}

	/**
	 * Új játék kezdetén beállítja a pálya állapotát tároló int tömböt "üres" mező értékűre.
	 */
	public void setupNewGame() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				fieldState[i][j] = -1;
			}
		}
	}

	/**
	 * Visszatölti egy, a játékosnevekkel azonosított, korábbi játék táblájának állapotát.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	public void setupPreviousGame() throws ClassNotFoundException, IOException {
		fieldState = saver.load(getFileName());
	}

	/**
	 * Elmenti az aktuális játék állapotát. Ha még nincs benne, hozzáadja a fájl nevét a játékneveket tartalmazó szerializált fájlhoz.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	public void saveGame() throws IOException, ClassNotFoundException {
		saver.save(getFileName(), fieldState);
		saveGames();
	}

	/**
	 * Törli az aktuális játékot mind a tárolt fájlok neveit tartalmazó szerializált fálból, mind a fájlrendszerből.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	public void removeGame() throws IOException, ClassNotFoundException {
		loadGames();
		if (games.contains(getFileName())) {
			games.remove(getFileName());
		}
		saveGames();
		File f = new File(wd, getFileName());
		f.delete();
	}

	/**
	 * Elmenti a tárolt játékok listáját a szerializált Games fájlba.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	private void saveGames() throws IOException, ClassNotFoundException {
		loadGames();
		if (!games.contains(getFileName())) {
			games.add(getFileName());
		}
		gamelist.save("Games", games);
		
	}

	/**
	 * A Games szerializált fájlból betölti a tárolt játékok fájlneveit.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	private void loadGames() throws IOException, ClassNotFoundException {
		games = gamelist.load("Games");
	}

	/**
	 * Visszaadja az aktuális játéknak megfelelő fájlnevet "(fehér játékos)  (piros játékos) trax" formátumban.
	 * @return a játékot tároló szerializált fájl neve.
	 */
	public String getFileName() {
		return Menu.getWhitePlayer().getName() + " " + Menu.getRedPlayer().getName() + " trax";
	}

	/**
	 * Beállítja a tábla x. sorában és y. oszlopában lévő mező értékét a fieldstate-re.
	 * @param x a tábla kiválasztandó sorát adja meg
	 * @param y a tábla kiválasztandó oszlopát adja meg
	 * @param fieldstate a tábla kiválasztandó mezőjének új értékét adja meg
	 */
	public void setFieldState(int x, int y, int fieldstate) {
		fieldState[x][y] = fieldstate;
	}
	
	/**
	 * Visszaadja a tábla x. sorában és y. oszlopában lévő mező értékét.
	 * @param x a tábla kiválasztandó sorát adja meg
	 * @param y a tábla kiválasztandó oszlopát adja meg
	 * @return a kiválasztott mező állapotát reprezentáló int
	 */
	public int getFieldState(int x, int y) {
		return fieldState[x][y];
	}

}
