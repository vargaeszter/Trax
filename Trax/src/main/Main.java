package main;

import java.io.IOException;

import menu.Menu;

/**
 * A Trax játék belépési pontja megvalósító Main osztály
 */
public class Main {
	/**
	 * Statikus tagváltozó a főmenü objektum tárolására.
	 */
	static Menu menu;

	/**
	 * A főmenü onjektum referenciájával visszatérő statikus függvény.
	 * @return A főmenü objektum referenciája
	 */
	public static Menu getMenu() {
		return menu;
	}

	/**
	 * A program belépési pontja. Inicializálja a főmenü grafikus ablakot.
	 * @param args Az esetleges parancssori argumentumok, paracssorból való futtatás esetén.
	 * @throws IOException ha probléma van a használt olyan fájlok valamelyikével, amely lehetetlenné tenné a játék működését.
	 */
	public static void main(String args[]) throws IOException {
		menu = new Menu();
	}
}