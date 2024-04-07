package tile;

import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * Az egymást keresztező vonalakkal borított csempéket reprezentáló osztály, amely a PlacedTile osztályból származik le.
 */
@SuppressWarnings("serial")
public class StraightTile extends PlacedTile {

	/**
	 * Az osztály konstruktora
	 * @param x A csempe x, azaz sor koordinátája a játék tábláján.
	 * @param y A csempe y, azaz oszlop koordinátája a játék tábláján.
	 * @param w A mező szélessége
	 * @param h A mező magassága
	 * @param dir 0 estén a piros a függőleges vonal, 1 esetén fehér
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	public StraightTile(int x, int y, int w, int h, int dir) throws IOException {
		super(x, y, w, h);
		this.dir = dir;
		switch (dir) {
		case 0:
			iconFile = "line0s.png";
			break;
		default:
			iconFile = "line1s.png";
			break;
		}
		setIcon(new ImageIcon(iconFile));
		setVisible(true);
	}

	@Override
	public TileColor getUpperColor() {
		if (dir == 0)
			return TileColor.RED;
		else
			return TileColor.WHITE;
	}

	@Override
	public TileColor getRightColor() {
		if (dir == 0)
			return TileColor.WHITE;
		else
			return TileColor.RED;
	}

	@Override
	public TileColor getDownColor() {
		if (dir == 0)
			return TileColor.RED;
		else
			return TileColor.WHITE;
	}

	@Override
	public TileColor getLeftColor() {
		if (dir == 0)
			return TileColor.WHITE;
		else
			return TileColor.RED;
	}

	@Override
	public PlacedTile getRedEdge() {
		return this.redEdge;
	}

	@Override
	public PlacedTile getWhiteEdge() {
		return this.whiteEdge;
	}

}
