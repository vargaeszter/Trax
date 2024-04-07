package game;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import menu.Menu;

/**
 * A GamaData osztály működését tesztelő osztály.
 */
public class GameDataTest {
	
	/**
	 * GameData típusú tagváltozó, amelyeken a teszteket végezzük.
	 */
	GameData game;
	
	/**
	 * Minden teszt előtt elvégzett inicializáló műveletek.
	 */
	@Before
	public void setup() {
		game = new GameData(true);
		game.setupNewGame();
	}
	
	/**
	 * Annak ellenőrzése, hogy új játék esetén valóban mindih az üres mezőt reprezentáló -1 érték kerül-e a táblába.
	 */
	@Test
	public void testNewGameSetup() {
		int result;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				result = game.getFieldState(i, j);
				Assert.assertEquals(result, -1);
			}
		}
	}
	
	/**
	 * A DataGame osztály mezőértékeket beállító és visszaadó osztályainak tesztje.
	 */
	@Test
	public void testSetter() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				game.setFieldState(i, j, j % 6);
			}
		}
		
		int result;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				result = game.getFieldState(i, j);
				Assert.assertEquals(result, j % 6);
			}
		}
	}
	
	/**
	 * Szerializáció műveleteinek tesztelése.
	 * @throws IOException ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	@Test
	public void testSerialization() throws IOException, ClassNotFoundException {
		Menu menu = new Menu();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				game.setFieldState(i, j, i % 6);
			}
		}
		
		/**
		 * Tábla mentése
		 */
		game.saveGame();
		
		/**
		 * Tábla állapotának visszaállítása
		 */
		game = new GameData(false);
		game.setupPreviousGame();
		
		int result;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				result = game.getFieldState(i, j);
				Assert.assertEquals(result, i % 6);
			}
		}
		
		/**
		 * Tábla törlése
		 */
		game.removeGame();
		
	}

}
