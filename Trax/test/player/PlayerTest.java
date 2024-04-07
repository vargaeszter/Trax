package player;

import org.junit.Assert;
import org.junit.Test;

import tile.TileColor;

/**
 * A Player osztály getName és getColor tagfüggvényeit tesztelő osztály.
 */
public class PlayerTest {
	
	/**
	 * A fehér játékos esetén elvárt működést tesztelő tagfüggvény.
	 */
	@Test
	public void whitePlayerTest() {
		Player white = new Player("Petra", TileColor.WHITE);
		String result = white.colorToString();
		Assert.assertEquals(result, "WHITE");
		result = white.getName();
		Assert.assertEquals(result, "Petra");
	}
	
	/**
	 * A piros játékos esetén elvárt működést tesztelő tagfüggvény.
	 */
	@Test
	public void redPlayerTest() {
		Player red = new Player("Peter", TileColor.RED);
		String result = red.colorToString();
		Assert.assertEquals(result, "RED");
		result = red.getName();
		Assert.assertEquals(result, "Peter");
	}

}
