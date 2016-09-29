package aufgabe01;

/**
 * @author Lydia Pflug, Lucas Anders
 * @date 27.09.2016
 *
 */

public class TestKlasse {

	public static void main(String[] args) throws Exception {
		
		try {
			Parser.einlesenDatei();
		} catch (IllegalArgumentException e) {
			System.err.println("Der Graph konnte nicht eingelesen werden.");
		}
		
	}
}
