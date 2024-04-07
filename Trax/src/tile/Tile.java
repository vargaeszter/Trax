package tile;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 * A játék mezőit megjelenítő grafikus elemek absztrakt ősosztálya.
 */
@SuppressWarnings("serial")
abstract public class Tile extends JButton {
	
	/**
	 * A JButton felületén megjelenítendő kép neve.
	 */
	protected String iconFile;

	/**
	 * Az ősosztály konstruktora
	 * @param w A mező szélessége
	 * @param h A mező magassága
	 */
	public Tile(int w, int h) {
		setPreferredSize(new Dimension(w, h));

	}

	/**
	 * Abstrakt getter függvény, ami megadja, hogy helyeztek e már le csempét az adott mezőre.
	 * @return Igaz, ha már helyeztek le csempét, hamis ha még nem.
	 */
	public abstract boolean isEmpty();

}
