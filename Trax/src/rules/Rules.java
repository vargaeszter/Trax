package rules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import main.Main;

/**
 * A játék szabályait megjelenítő grafikus osztály, amely a JFrame-ből származik le. 
 */ 
@SuppressWarnings("serial")
public class Rules extends JFrame {

	/**
	 * Az osztály konstruktora. Meghívja a grafikus ablak kinézetét beállító rules függvényt. 
	 */
	public Rules() {
		rules();
	}
	
	/**
	 * A függvény a grafikus ablak kinézetének beállításáért felelős. 
	 * Az ablak nem szerkeszthető, a játék szabályait jelentíti meg.
	 * Visszalépni a főmenübe a bal felső sarok legördülő menüjében a "Vissza" elemre kattintva lehetséges.
	 */
	private void rules() {
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menü");
		JMenuItem menuitem = new JMenuItem("Vissza");
		JTextArea rule = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(rule);
		JLabel title = new JLabel("Szabályok ");
		JPanel panel = new JPanel();
		Font f1 = new Font(Font.SERIF, Font.BOLD, 80);
		Font f2 = new Font(Font.SERIF, Font.BOLD, 20);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(new Color(220, 220, 220));

		title.setFont(f1);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setForeground(Color.BLACK);

		rule.setFont(f2);
		rule.setLineWrap(true);
		rule.setWrapStyleWord(true);
		rule.setEditable(false);
		rule.setText(
				"A Trax egy két játékos által játszott stratégiai játék. "
				+ "A játéktér egy 8×8-as tábla, amelyet kétféle csempével kell kirakni. "
				+ "Az egyik csempének az átellenes oldalait köti össze egy piros, illetve egy fehér vonal, a másik csempén páronként szomszédos oldalakat köt össze egy piros, illetve egy fehér vonal. "
				+ "\r\n\r\n"
				+ "A játékosok felváltva tesznek le egy-egy csempét. "
				+ "Ha egy lépés következtében olyan üres pozíció jönne létre, amelybe 3 vagy 4 oldalról is ugyanolyan szín fut be, akkor a lépést nem szabad meglépni.\r\n"

				+ "\r\n"
				+ "A kezdő játékos színe a fehér, a másiké a piros. "
				+ "A játékot az nyeri, aki:"
				+ "\r\n"
				+ " - előbb hoz létre a saját színéből egy hurkot, vagy\r\n"
				+ " - előbb hoz létre a saját színéből a táblát átmetsző a szemközti oldalaira illeszkedő utat. \r\n"
			);

		scrollPane.getViewport().setViewPosition(new Point(0, 0));
		scrollPane.setMaximumSize(new Dimension(600, 400));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		menuitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getMenu().setVisible(true);
				dispose();
			}
		});
		menu.add(menuitem);
		bar.add(menu);

		panel.add(title, CENTER_ALIGNMENT);
		panel.add(scrollPane, CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 50)));

		add(panel, BorderLayout.CENTER);
		setBackground(Color.BLACK);
		setJMenuBar(bar);
		setTitle("Trax");
		setSize(800,600);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
