package tile;

/**
 * A Tile ősosztályból leszármazó, az lerakott csempéket reprezentáló grafikus osztály.
 */
@SuppressWarnings({ "serial" })
public abstract class PlacedTile extends Tile {

	/**
	 * A mezőre illeszkedő piros út széle. Lerakáskor inicializáljuk, ha nincs szomszédja önmagára állítjuk.
	 * Mivel lerakott csempét sosem veszünk vissza, ezért csak a szélső mezők esetén karbantartott az értéka.
	 */
	protected PlacedTile redEdge;
	
	/**
	 * A mezőre illeszkedő fehér út széle. Lerakáskor inicializáljuk, ha nincs szomszédja önmagára állítjuk.
	 * Mivel lerakott csempét sosem veszünk vissza, ezért csak a szélső mezők esetén karbantartott az értéka.
	 */
	protected PlacedTile whiteEdge;

	/**
	 * A csempe x, azaz sor koordinátája a játék tábláján.
	 */
	protected int x;
	
	/**
	 * A csempe y, azaz oszlop koordinátája a játék tábláján.
	 */
	protected int y;
	

	/**
	 * A csempe mintájának elforgatását adja meg, értelmezés: elforgatás szöge jobbra dir*90°.
	 */
	protected int dir;

	/**
	 * Az osztály konstruktora
	 * @param x A csempe x, azaz sor koordinátája a játék tábláján.
	 * @param y A csempe y, azaz oszlop koordinátája a játék tábláján.
	 * @param w A mező szélessége
	 * @param h A mező magassága
	 */
	public PlacedTile(int x, int y, int w, int h) {
		super(w, h);
		this.x = x;
		this.y = y;
		redEdge = null;
		whiteEdge = null;
	}

	/**
	 * Visszaadja a csempe felső szélén lévő színt
	 * @return A csempe felső színe
	 */
	abstract public TileColor getUpperColor();
	
	/**
	 * Visszaadja a csempe jobb szélén lévő színt
	 * @return A csempe jobb színe
	 */
	abstract public TileColor getRightColor();
	
	/**
	 * Visszaadja a csempe alsó szélén lévő színt
	 * @return A csempe alsó színe
	 */
	abstract public TileColor getDownColor();

	/**
	 * Visszaadja a csempe bal szélén lévő színt
	 * @return A csempe bal színe
	 */
	abstract public TileColor getLeftColor();

	/**
	 * Visszaadja a csempe piros útjának szélén lévő csempét
	 * @return A piros út széle
	 */
	abstract public PlacedTile getRedEdge();

	/**
	 * Visszaadja a csempe fehér útjának szélén lévő csempét
	 * @return A fehér út széle
	 */
	abstract public PlacedTile getWhiteEdge();

	/**
	 * Beállítja a csempe piros útjának végét a paraméterként kapott másik csempére.
	 * @param other A piros út széle
	 */
	public void setRedEdge(PlacedTile other) {
		redEdge = other;
	}

	/**
	 * Beállítja a csempe fehér útjának végét a paraméterként kapott másik csempére.
	 * @param other A fehér út széle
	 */
	public void setWhiteEdge(PlacedTile other) {
		whiteEdge = other;
	}

	/**
	 * A csempe x, azaz sor koordinátájával visszatérő függvény.
	 * @return A csempe x, azaz sor koordinátája
	 */
	public int getTileX() {
		return x;
	}

	/**
	 * A csempe y, azaz oszlop koordinátájával visszatérő függvény.
	 * @return A csempe y, azaz oszlop koordinátája
	 */
	public int getTileY() {
		return y;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
