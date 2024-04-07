package choosenames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import main.Main;
import menu.Menu;
import player.Player;
import serializer.Serializer;
import tile.TileColor;

/**
 * A játékosnevek beállításáért felelős grafikus osztály, amely a JFrame-ből származik le.
 */
@SuppressWarnings("serial")
public class ChooseNames extends JFrame {

	/**
	 * A korábbi játékok neveit megjelenítő legördülő lista.
	 */
	private JComboBox<Object> gamenames;

	/**
	 * A kiírt szövegek megjelenését megadó, egész osztályra érvényed font.
	 */
	private Font f1;

	/**
	 * A piros és a fehér, játékosok neveit megjelenítő, szerkeszthető szövegdobozok.
	 */
	private JTextField red, white;

	/**
	 * A mentett játékok legördülő listájához tartozó 
	 */
	private JButton jatekButton;
	/**
	 * A mentett játékok fájlneveinek listája.
	 */
	private ArrayList<String> games;

	/**
	 * A mentett játékok szerializálásához használt tagváltozó.
	 */
	private Serializer<ArrayList<String>> gamelist;

	/**
	 * Az aktuális játék kimentésére szolg
	 */
	private Serializer<String> fstring;

	/**
	 * Az osztály konstruktora, inicializálja a játék tagváltozóit, majd meghívja az
	 * oldal beállítását végző createBox() és names() függvényeket.
	 * 
	 * @throws IOException            Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként
	 *                                megadott osztály
	 */

	public ChooseNames() throws IOException, ClassNotFoundException {

		f1 = new Font(Font.SERIF, Font.BOLD, 20);
		red = new JTextField(20);
		white = new JTextField(20);
		jatekButton = new JButton("OK");
		fstring = new Serializer<String>();

		createBox();
		names();
	}

	/**
	 * A játékok legördülő listáját megjelenítő JComboBox beállítása.
	 * 
	 * @throws IOException            Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként
	 *                                megadott osztály
	 */

	private void createBox() throws ClassNotFoundException, IOException {
		Object[] array;
		gamelist = new Serializer<ArrayList<String>>();
		games = gamelist.load("Games");

		array = new Object[games.size()];
		for (int i = 0; i < games.size(); i++)
			array[i] = (String) games.get(i);

		gamenames = new JComboBox<Object>(array);

		if (games.size() > 0) {
			jatekButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					String fn = (String) gamenames.getSelectedItem();
					String[] words = fn.split(" ");
					white.setText(words[0]);
					red.setText(words[1]);
					try {
						saveGameName(fn);
					} catch (IOException e) {
					}
				}
			});
		}

	}

	/**
	 * A kiválasztott (aktuális) játék nevének szerializálását végző függvény.
	 * @param name A mentendő játék fájlneve
	 * @throws IOException Ha probléma van a használt fájlokkal
	 */
	private void saveGameName(String name) throws IOException {
		fstring.save("Chosen", name);
	}
	
	
	/**
	 * A teljes névválasztó ablak grafikus kinézetéért felelős tagfüggvény.\r\n
	 * Az alsó girdlayout elrendezésű panelben egy legördülő listából
	 * kiválaszthatjuk, hogy mely korábbi játékot szeretnénk visszatölteni. Az OK
	 * gomb lenyomása után a játékosok nevei beíródnak a felső panel névmezőibe. \r\n
	 * Ha nem korábbi játékot szeretnénk visszatölteni, akkor a felső, szintén
	 * girdlayout elrendezésű panelben adhatjuk meg a játékosok neveit. Az OK gombok
	 * lenyomásával adjuk át a játékosok neveit a programot kezelő Menu osztálynak.
	 * @throws IOException Ha probléma van a használt fájlokkal
	 */

	private void names() throws IOException {
		GridLayout gridlayoutUpper = new GridLayout(4, 2);
		GridLayout gridlayoutBottom = new GridLayout(4, 2);
		JPanel panel = new JPanel();
		JPanel upperPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menü");
		JMenuItem menuitem = new JMenuItem("Vissza");
		
		JLabel labelNewGame = new JLabel("Ha új játékot kezdtek:");
		JLabel labelSetNames = new JLabel("Add meg a játékosok neveit!");
		JLabel labelContinue = new JLabel("Ha korábbit játékot folytattok:");
		JLabel labelChooseGame = new JLabel("Válasszátok ki a játékot!");
		JLabel hiba = new JLabel("Nincs megkezdett játék!");
		JLabel ok1 = new JLabel("Név mentése az OK gombbal!");
		JLabel ok2 = new JLabel("Név mentése az OK gombbal!");
		JLabel labelAlways = new JLabel("Folytatás és új játék esetén is:");
		
		JButton redButton = new JButton("OK");
		redButton.setBackground(new Color(240,80,80));
		redButton.setForeground(Color.BLACK);
		redButton.setPreferredSize(new Dimension(330,40));
		redButton.setFont(f1);
		
		JButton whiteButton = new JButton("OK");
		whiteButton.setBackground(Color.WHITE);
		whiteButton.setForeground(Color.BLACK);
		whiteButton.setPreferredSize(new Dimension(330,40));
		whiteButton.setFont(f1);

		jatekButton.setFont(f1);
		jatekButton.setBackground(Color.BLACK);
		jatekButton.setForeground(Color.WHITE);
		jatekButton.setPreferredSize(new Dimension(330,40));

		red.setFont(f1);
		white.setFont(f1);
		gamenames.setFont(f1);
		labelNewGame.setFont(f1);
		labelSetNames.setFont(f1);
		labelContinue.setFont(f1);
		labelChooseGame.setFont(f1);
		hiba.setFont(f1);
		labelAlways.setFont(f1);
		ok1.setFont(f1);
		ok2.setFont(f1);

		upperPanel.setLayout(gridlayoutUpper);
		gridlayoutUpper.setHgap(30);
		gridlayoutUpper.setVgap(10);
		upperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
		upperPanel.setBackground(new Color(220, 220, 220));

		upperPanel.add(labelNewGame);
		upperPanel.add(labelAlways);
		upperPanel.add(labelSetNames);
		upperPanel.add(ok1);
		upperPanel.add(white);
		upperPanel.add(whiteButton);
		upperPanel.add(red);
		upperPanel.add(redButton);

		bottomPanel.setLayout(gridlayoutBottom);
		gridlayoutBottom.setHgap(30);
		gridlayoutBottom.setVgap(10);
		bottomPanel.setBackground(new Color(220, 220, 220));

		bottomPanel.add(labelContinue);
		bottomPanel.add(new JLabel());
		bottomPanel.add(labelChooseGame);
		bottomPanel.add(ok2);
		bottomPanel.add(gamenames);
		bottomPanel.add(jatekButton);
		if (games.size() == 0)
			bottomPanel.add(hiba);
		else
			bottomPanel.add(new JLabel());

		redButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu.setRedPlayer(new Player(red.getText(), TileColor.RED));
			}
		});

		whiteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu.setWhitePlayer(new Player(white.getText(), TileColor.WHITE));
			}
		});

		menuitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getMenu().setVisible(true);
				dispose();
			}
		});

		menu.add(menuitem);
		bar.add(menu);

		panel.add(upperPanel);
		panel.add(bottomPanel);

		panel.setBackground(new Color(220, 220, 220));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setTitle("Trax");
		setJMenuBar(bar);
		setSize(800, 600);
		add(panel);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
}