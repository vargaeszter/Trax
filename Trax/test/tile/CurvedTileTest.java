package tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *  A PlacedTile ősosztályból származó CurvedTile osztály oldalszín getter tagfüggvényeinek felüldefiniálásait tesztelő osztály.
 */
@RunWith(Parameterized.class)
public class CurvedTileTest {

	/**
	 * Egy CurvedTile objektum, amelyen inicializálás után a teszteket végezzük.
	 */
	CurvedTile t;
	
	/**
	 * Egy integer típusú tagváltozó, amelybe a várt értéket, mint paramétert tárolunk.
	 */
	int testcase;

	/**
	 * A testosztály konstruktora, itt veszi át a tesztesetek paramétereit.
	 * @param x A csempe x, azaz sor koordinátája a játék tábláján.
	 * @param y A csempe y, azaz oszlop koordinátája a játék tábláján.
	 * @param w A mező szélessége
	 * @param h A mező magassága
	 * @param dir 0 esetén a piros görbe a jobbalsó sarok, 1 esetén a piros görbe a balasló sarok, 2 esetén a piros görbe a balfelső sarok, 3 esetén a piros görbe a jobbfelső sarok
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	public CurvedTileTest(int x, int y, int w, int h, int dir) throws IOException {
		t = new CurvedTile(x, y, w, h, dir);
		testcase = dir;
	}

	/**
	 * A csempe alsó színét visszaadó getDownColor tagfüggvény tesztje.
	 */
	@Test
	public void getDownColorTest() {
		TileColor result = t.getDownColor();
		if (testcase == 2 || testcase == 3)
			Assert.assertEquals(result, TileColor.WHITE);
		else
			Assert.assertEquals(result, TileColor.RED);
	}

	/**
	 * A csempe felső színét visszaadó getUppernColor tagfüggvény tesztje.
	 */
	@Test
	public void getUpperColorTest() {
		TileColor result = t.getUpperColor();
		if (testcase == 0 || testcase == 1)
			Assert.assertEquals(result, TileColor.WHITE);
		else
			Assert.assertEquals(result, TileColor.RED);
	}

	/**
	 * A csempe baloldali színét visszaadó getLeftColor tagfüggvény tesztje.
	 */
	@Test
	public void getLeftColorTest() {
		TileColor result = t.getLeftColor();
		if (testcase == 0 || testcase == 3)
			Assert.assertEquals(result, TileColor.WHITE);
		else
			Assert.assertEquals(result, TileColor.RED);
	}

	/**
	 * A csempe jobboldali színét visszaadó getRightColor tagfüggvény tesztje.
	 */
	@Test
	public void getRightColorTest() {
		TileColor result = t.getRightColor();
		if (testcase == 1 || testcase == 2)
			Assert.assertEquals(result, TileColor.WHITE);
		else
			Assert.assertEquals(result, TileColor.RED);
	}

	/**
	 * A teszteléshez használt paramétereket megadása
	 * @return A paramétereket tároló statikus listával tér vissza
	 */
	@Parameters
	public static List<Object[]> parameters() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] { 0, 0, 50, 50, 0 });
		params.add(new Object[] { 0, 1, 50, 50, 1 });
		params.add(new Object[] { 1, 0, 50, 50, 1 });
		params.add(new Object[] { 1, 1, 50, 50, 2 });
		params.add(new Object[] { 6, 6, 50, 50, 3 });
		return params;
	}

}
