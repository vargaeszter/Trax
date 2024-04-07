package player;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tile.TileColor;

/**
 * A játékosakat reprezentáló osztály.
 */
public class Player {
	
	/**
	 * A játékos neve
	 */
	private String name;
	
	/**
	 * A játékos színe (melyik színből kell teljes pályát átszelő utat, vagy hurkot létrehoznia a győzelemhez.
	 */
	private TileColor color;
	
	/**
	 * Az osztály konstruktora, beállítja a tagváltozók értékeit.
	 * @param name A játékos neve
	 * @param color A játékos színe
	 */
	public Player(String name, TileColor color) {
		this.name = name;
		this.color = color;
	}
	
	/**
	 * A játékos nevének lekérdezésére szolgáló függvény.
	 * @return A játékos neve, string-ként
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * A játékos színének lekérdezésére szolgáló függvény.
	 * @return A játékos színe, string-ként
	 */
	public String colorToString() {
		return color.toString();
	}
	
	/**
	 * A játékosnak egy a színének megfelelő hátterű grafikus panelt generáló függvény, amely tartalmazza a játékos nevét és színét.
	 * @return A játékos adatait megjeelenítő grafikus panel
	 */
	public JPanel getPlayerCard() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JLabel name = new JLabel(getName());
		name.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		name.setForeground(Color.BLACK);
		panel.add(name);
		JLabel colorName = new JLabel(colorToString());
		colorName.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		panel.add(colorName);
		if(color.equals(TileColor.WHITE))
			panel.setBackground(Color.white);
		else
			panel.setBackground(new Color(240,80,80));

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return panel;
	}

}
