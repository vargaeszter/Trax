package tile;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;


/**
 * A Tile ősosztályból származó osztályok isEmpty tagfüggvény felüldefiniálásait tesztelő osztály.
 */
public class IsEmptyTest {
	
	/**
	 * Az EmptyTile class isEmpty tagfüggvényének tesztesete.
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	@Test
	public void testEmptyTile() throws IOException {
		Tile t = new EmptyTile(50,50);
		boolean result = t.isEmpty();
		Assert.assertEquals(result, true);
	}
	
	/**
	 * A CurvedTile class isEmpty tagfüggvényének tesztesete.
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	@Test
	public void testCurvedTile() throws IOException {
		Tile t = new CurvedTile(1, 2, 50, 50, 2);
		boolean result = t.isEmpty();
		Assert.assertEquals(result, false);
	}
	
	/**
	 * A StraightTile class isEmpty tagfüggvényének tesztesete.
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	@Test
	public void testStraightTile() throws IOException {
		Tile t = new StraightTile(4, 6, 50, 50, 0);
		boolean result = t.isEmpty();
		Assert.assertEquals(result, false);
	}

}
