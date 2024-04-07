package tile;

import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * Az egymást nem metsző, görbe vonalakkal borított csempéket reprezentáló osztály, amely a PlacedTile osztályból származik le.
 */
@SuppressWarnings("serial")
public class CurvedTile extends PlacedTile {
	
	/**
	 * Az osztály konstruktora
	 * @param x A csempe x, azaz sor koordinátája a játék tábláján.
	 * @param y A csempe y, azaz oszlop koordinátája a játék tábláján.
	 * @param w A mező szélessége
	 * @param h A mező magassága
	 * @param dir 0 esetén a piros görbe a jobbalsó sarok, 1 esetén a piros görbe a balasló sarok, 2 esetén a piros görbe a balfelső sarok, 3 esetén a piros görbe a jobbfelső sarok
	 * @throws IOException ha probléma van a használt képfájlokkal
	 */
	public CurvedTile(int x, int y, int w, int h, int dir) throws IOException {
		super(x, y, w, h);
		this.dir = dir;
		switch (dir) {
		case 0:
			iconFile = "curved0s.png";
			break;
		case 1:
			iconFile = "curved1s.png";
			break;
		case 2:
			iconFile = "curved2s.png";
			break;
		default:
			iconFile = "curved3s.png";
			break;
		}
		setIcon(new ImageIcon(iconFile));
		setVisible(true);
	}

	@Override
	public TileColor getUpperColor() {
		if (dir == 0 || dir == 1)
			return TileColor.WHITE;
		else
			return TileColor.RED;
	}

	@Override
	public TileColor getRightColor() {
		if (dir == 1 || dir == 2)
			return TileColor.WHITE;
		else
			return TileColor.RED;
	}

	@Override
	public TileColor getDownColor() {
		if (dir == 2 || dir == 3)
			return TileColor.WHITE;
		else
			return TileColor.RED;
	}

	@Override
	public TileColor getLeftColor() {
		if (dir == 0 || dir == 3)
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
