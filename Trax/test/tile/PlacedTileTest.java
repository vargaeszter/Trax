package tile;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * A Tile ősosztályból származó PlacedTile osztályok getTileX és getTileY tagfüggvényeit tesztelő osztály.
 */
public class PlacedTileTest {

	/**
	 * A teszteléshez használt PlacedTile referencia.
	 */
	PlacedTile t;
	/**
	 * A tesztelés során elvárt értékeket tároló int tagváltozók.
	 */
	int x, y;

	/**
	 * A StraightTile objektum gettereit tesztelő függvény.
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	@Test
	public void straightTileGetterTest() throws IOException {
		x = 0;
		y = 5;
		t = new StraightTile(x, y, 50, 50, 0);
		int resultX = t.getTileX();
		int resultY = t.getTileY();
		Assert.assertEquals(resultX, x);
		Assert.assertEquals(resultY, y);

		x = 4;
		y = 6;
		t = new StraightTile(x, y, 50, 50, 1);
		resultX = t.getTileX();
		resultY = t.getTileY();
		Assert.assertEquals(resultX, x);
		Assert.assertEquals(resultY, y);
	}
	
	/**
	 * A CurvedTile objektum gettereit tesztelő függvény.
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	@Test
	public void curvedTileGetterTest() throws IOException {
		x = 0;
		y = 5;
		t = new CurvedTile(x, y, 50, 50, 2);
		int resultX = t.getTileX();
		int resultY = t.getTileY();
		Assert.assertEquals(resultX, x);
		Assert.assertEquals(resultY, y);

		x = 4;
		y = 6;
		t = new CurvedTile(x, y, 50, 50, 3);
		resultX = t.getTileX();
		resultY = t.getTileY();
		Assert.assertEquals(resultX, x);
		Assert.assertEquals(resultY, y);
	}

}
