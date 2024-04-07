package game;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.Main;
import tile.CurvedTile;
import tile.PlacedTile;
import tile.StraightTile;
import tile.TileColor;

/**
 * A GamaData osztály működését tesztelő osztály.
 */
public class GameBoardTest {
	/**
	 * GameData típusú tagváltozó, amelyeken a teszteket végezzük.
	 */
	GameBoard board;

	/**
	 * A tesztosztály futtatásának legelején, egyszer lefuttatott inicializáló függvény.
	 * @throws IOException ha probléma van a használt fájlokkal
	 */
	@BeforeClass
	static public void setupClass() throws IOException {
		Main.main(null);
	}
	/**
	 * A tesztosztály futtatása során minden tesztfüggvény előtt lefuttatott inicializáló függvény.
	 * @throws IOException ha probléma van a használt fájlokkal
	 */
	@Before
	public void setup() throws IOException {
		Main.getMenu().setVisible(false);
		board = new GameBoard(true);
	}

	/**
	 * Annak tesztelése, hogy az isTilePalce tagfüggvény helyesen ítéli e meg, hogy egy már lerakott csempével szomszédes mezőre egy másik csempe lehelyezhető e.
	 * Annak tesztelése, hogy az addTileIfPossible tagffügvény valóban csak akkor rak le egy csempét, amennyiben az helyesen lerakható.
	 * @throws IOException ha probléma van a használt fájlokkal
	 */
	@Test
	public void addTileTest() throws IOException {
		boolean result;
		/**
		 * Egy csempe lehelyezése egy üres pályára.
		 */
		result = board.isTilePlacable(3, 3, new StraightTile(3, 3, 50, 50, 0));
		Assert.assertEquals(true, result);
		board.getChoiceTileToClick(0).doClick();
		board.getFieldTileToCLick(3, 3).doClick();

		/**
		 * Egy csempe lehelyezésének megkísérlése egy nem üres mezőre.
		 */
		result = board.isTileEmpty(3, 3);
		Assert.assertEquals(false, result);
		
		board.getChoiceTileToClick(2).doClick();
		board.getFieldTileToCLick(3, 3).doClick();
		int fieldState = board.getFieldTileState(3,3);
		Assert.assertEquals(0, fieldState);

		/**
		 * Egy csempe lerakhatóságának vizsgálata, helytelen lerakás megkísérlése.
		 */
		result = board.isTilePlacable(3, 4, new StraightTile(3, 4, 50, 50, 1));
		Assert.assertEquals(false, result);
		board.getChoiceTileToClick(1).doClick();
		board.getFieldTileToCLick(3, 4).doClick();
		result = board.isTileEmpty(3, 4);
		Assert.assertEquals(true, result);

		/**
		 * Egy új csempe lerakása, majd nem lerakható csempékkel isTilePlacable() tesztelése
		 */
		board.getChoiceTileToClick(0).doClick();;
		board.getFieldTileToCLick(3, 5).doClick();

		for (int i = 0; i < 4; i++) {
			result = board.isTilePlacable(3, 4, new CurvedTile(3, 4, 50, 50, i));
			Assert.assertEquals(false, result);
			board.getChoiceTileToClick(i+2).doClick();
			board.getFieldTileToCLick(3, 4).doClick();
			result = board.isTileEmpty(3, 4);
			Assert.assertEquals(true, result);
		}
		
		result = board.isTilePlacable(4, 4, new StraightTile(3, 4, 50, 50, 1));
		Assert.assertEquals(false, result);
		
	}

	/**
	 * Annak tesztelése, hogy egy üres hely mellé lerakve egy új csempét sérülne-e a nem lehet három szomszédos és azonos színű csempeszél feltétel.
	 * @throws IOException ha probléma van a használt fájlokkal
	 */
	@Test
	public void isThirdFromSameColorTest() throws IOException {

		boolean result;
		board.getChoiceTileToClick(0).doClick();
		board.getFieldTileToCLick(3, 3).doClick();
		result = board.isThirdFromSameColor(3, 4, TileColor.WHITE);
		Assert.assertEquals(false, result);

		board.getChoiceTileToClick(0).doClick();
		board.getFieldTileToCLick(3, 5).doClick();
		result = board.isThirdFromSameColor(3, 4, TileColor.WHITE);
		Assert.assertEquals(true, result);

		result = board.isThirdFromSameColor(4, 3, TileColor.RED);
		Assert.assertEquals(false, result);

		board.getChoiceTileToClick(0).doClick();
		board.getFieldTileToCLick(5, 3).doClick();
		result = board.isThirdFromSameColor(4, 3, TileColor.RED);
		Assert.assertEquals(true, result);
	}

	/**
	 * Annak tesztelése, hogy csempék egymásmellé helyezése esetén a szélső csempéken fehér útjainak szélét helyesen állítja be az addNewFinishedTile tagfüggvény.
	 */
	@Test
	public void getWhiteEdgeTest() {
		board.getChoiceTileToClick(0).doClick();;
		board.getFieldTileToCLick(3, 3).doClick();
		board.getChoiceTileToClick(0).doClick();;
		board.getFieldTileToCLick(3, 4).doClick();
		
		PlacedTile result = ((PlacedTile) board.getFieldTileToCLick(3, 3)).getWhiteEdge();
		Assert.assertEquals((PlacedTile) board.getFieldTileToCLick(3, 4), result);

		board.getChoiceTileToClick(0).doClick();
		board.getFieldTileToCLick(3, 6).doClick();
		
		result = ((PlacedTile) board.getFieldTileToCLick(3, 6)).getWhiteEdge();
		Assert.assertEquals((PlacedTile) board.getFieldTileToCLick(3, 6), result);

		board.getChoiceTileToClick(0).doClick();
		board.getFieldTileToCLick(3, 5).doClick();
		
		result = ((PlacedTile) board.getFieldTileToCLick(3, 3)).getWhiteEdge();
		Assert.assertEquals((PlacedTile) board.getFieldTileToCLick(3, 6), result);
		
	}	
	
	/**
	 * Annak tesztelése, hogy csempék egymásmellé helyezése esetén a szélső csempéken  piros útjainak szélét helyesen állítja be az addNewFinishedTile tagfüggvény.
	 */
	@Test
	public void getRedEdgeTest() {
		board.getChoiceTileToClick(1).doClick();
		board.getFieldTileToCLick(3, 3).doClick();
		board.getChoiceTileToClick(1).doClick();
		board.getFieldTileToCLick(3, 4).doClick();
		
		PlacedTile result = ((PlacedTile) board.getFieldTileToCLick(3, 3)).getRedEdge();
		Assert.assertEquals((PlacedTile) board.getFieldTileToCLick(3, 4), result);

		board.getChoiceTileToClick(1).doClick();
		board.getFieldTileToCLick(3, 6).doClick();
		
		result = ((PlacedTile) board.getFieldTileToCLick(3, 6)).getRedEdge();
		Assert.assertEquals((PlacedTile) board.getFieldTileToCLick(3, 6), result);

		board.getChoiceTileToClick(1).doClick();
		board.getFieldTileToCLick(3, 5).doClick();
		
		result = ((PlacedTile) board.getFieldTileToCLick(3, 3)).getRedEdge();
		Assert.assertEquals((PlacedTile) board.getFieldTileToCLick(3, 6), result);
		
	}
	
	/**
	 * Annak tesztelése, hogy piros kör létrejötte után valóban végetér a játék.
	 */
	@Test
	public void redCircleWinTest() {
		board.getChoiceTileToClick(2).doClick();
		board.getFieldTileToCLick(3, 3).doClick();
		board.getChoiceTileToClick(3).doClick();
		board.getFieldTileToCLick(3, 4).doClick();
		board.getChoiceTileToClick(4).doClick();
		board.getFieldTileToCLick(4, 4).doClick();
		
		boolean result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(true, result);

		board.getChoiceTileToClick(5).doClick();
		board.getFieldTileToCLick(4, 3).doClick();
		
		result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(false, result);
	}
	
	/**
	 * Annak tesztelése, hogy fehér kör létrejötte után valóban végetér a játék.
	 */
	@Test
	public void whiteCircleWinTest() {
		board.getChoiceTileToClick(4).doClick();
		board.getFieldTileToCLick(3, 3).doClick();
		board.getChoiceTileToClick(5).doClick();
		board.getFieldTileToCLick(3, 4).doClick();
		board.getChoiceTileToClick(2).doClick();
		board.getFieldTileToCLick(4, 4).doClick();
		
		boolean result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(true, result);

		board.getChoiceTileToClick(3).doClick();
		board.getFieldTileToCLick(4, 3).doClick();
		
		result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(false, result);
	}
	
	/**
	 * Annak tesztelése, hogy piros függőleges táblát átszelő létrejötte után valóban végetér a játék.
	 */
	@Test
	public void redCrossFieldVerticallyWin() {
		boolean result;
		for(int i = 0; i < 8; i++) {
			result = board.getChoiceTileToClick(0).isEnabled();
			Assert.assertEquals(true, result);
			board.getChoiceTileToClick(0).doClick();
			board.getFieldTileToCLick(4, i).doClick();
		}
		
		result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(false, result);
	}
	
	/**
	 * Annak tesztelése, hogy fehér függőleges táblát átszelő létrejötte után valóban végetér a játék.
	 */
	@Test
	public void whiteCrossFieldVerticallyWin() {
		boolean result;
		for(int i = 0; i < 8; i++) {
			result = board.getChoiceTileToClick(0).isEnabled();
			Assert.assertEquals(true, result);
			board.getChoiceTileToClick(1).doClick();
			board.getFieldTileToCLick(6, i).doClick();
		}
		
		result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(false, result);
	}
	
	/**
	 * Annak tesztelése, hogy piros vízszintes táblát átszelő létrejötte után valóban végetér a játék.
	 */
	@Test
	public void redCrossFieldHorizontallyWin() {
		boolean result;
		for(int i = 0; i < 8; i++) {
			result = board.getChoiceTileToClick(0).isEnabled();
			Assert.assertEquals(true, result);
			board.getChoiceTileToClick(1).doClick();
			board.getFieldTileToCLick(i,4).doClick();
		}
		
		result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(false, result);
	}
	
	/**
	 * Annak tesztelése, hogy fehér vízszintes táblát átszelő létrejötte után valóban végetér a játék.
	 */
	@Test
	public void whiteCrossFieldHorizontallyWin() {
		boolean result;
		for(int i = 0; i < 8; i++) {
			result = board.getChoiceTileToClick(0).isEnabled();
			Assert.assertEquals(true, result);
			board.getChoiceTileToClick(0).doClick();
			board.getFieldTileToCLick(i,6).doClick();
		}
		
		result = board.getChoiceTileToClick(0).isEnabled();
		Assert.assertEquals(false, result);
	}
	
	/**
	 * Mezők lerakása a táblára, tábla mentése, majd visszatöltése új objektumba.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 */
	@Test
	public void testSaveAndLoad() throws IOException {
		for(int i = 0; i < 7; i++) {

			board.getChoiceTileToClick(0).doClick();
			board.getFieldTileToCLick(2, i).doClick();
		}
		
		board.getSaveMenuItem().doClick();
		
		board = new GameBoard(false);
		
		int result;
		for(int i = 0; i < 7; i++) {
			result = board.getFieldTileState(2, i);
			Assert.assertEquals(0, result);
		}
		
		board.getEndMenuItem().doClick();
		
	}
	
	/**
	 * Minden tesztfüggvény után lefutó lezáró függvény.
	 */
	@After
	public void close() {
		board.dispose();
	}


}
