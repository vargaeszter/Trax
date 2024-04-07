package tile;

import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * A Tile ősosztályból leszármazó, az üres mezőket reprezentáló grafikus osztály.
 */
@SuppressWarnings("serial")
public class EmptyTile extends Tile {

	/**
	 * Az osztály konstruktora
	 * @param w A mező szélessége
	 * @param h A mező magassága
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	public EmptyTile( int w, int h) throws IOException {
		super(w, h);
		iconFile = "emptys.png";
		setIcon(new ImageIcon(iconFile));
		setVisible(true);
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

}
