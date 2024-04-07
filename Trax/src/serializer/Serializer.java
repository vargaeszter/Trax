package serializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Sablon paraméterrel létrehozott osztály szerializálás végrehajtásához.
 * @param <T> - értéke az az osztály, amilyen típusú objektumot szerializálni szeretnénk.
 */
@SuppressWarnings("serial")
public class Serializer<T> implements Serializable{

	/**
	 * Az osztály konstruktora.
	 */
	public Serializer() {}

	/**
	 * A szerializált mentést végző függvény.
	 * @param fn Annak a fájlnak a neve, amibe menteni szeretnénk
	 * @param object T típusú objektum, amit szerializálva menteni szeretnénk.
	 * @throws IOException Ha probléma lép fel a fájlba írás közben.
	 */
	public void save(String fn, T object) throws IOException {
		FileOutputStream fos = new FileOutputStream(fn);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(object);
		oos.close();
	}

	/**
	 * A szerializált fájlból történő beolvasást végző függvény.
	 * @param fn Annak a fájlnak a neve, amiből beolvasunk
	 * @return A beolvasott T típusú objectum
	 * @throws IOException Ha probléma van a használt fájlokkal
	 * @throws ClassNotFoundException Ha nem található a Files paramétereként megadott osztály
	 */
	@SuppressWarnings("unchecked")
	public T load(String fn) throws ClassNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(fn);
		ObjectInputStream ois = new ObjectInputStream(fis);
		T obj = (T) ois.readObject();
		ois.close();
		return obj;
	}
}
