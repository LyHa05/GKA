package aufgabe01;

/**
 * @author Lydia Pflug, Lucas Anders
 * @date 27.09.2016
 *
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.graphstream.graph.implementations.MultiGraph;

public class Parser {
	
	private void Parser() {}
	
	private static Path dateiPfad;
	private static List<String> geleseneZeilen;
	private static MultiGraph graph;
	private static int graphID = 0;
	
	/**
	 * Methode liest aus Datei Graph ein, prueft die Syntax, inkrementiert die Graphen-ID
	 * und erstellt einen Multigraphen. Ist die Syntax der Datei fehlerhaft wird eine
	 * Fehlermeldung angezeigt.
	 * 
	 * @throws IOException
	 */
	static void einlesenGraph() throws IOException {
		dateiPfad = MeinFileChooser.chooseFile().toPath();
		System.out.println(dateiPfad);
		geleseneZeilen = Files.lines(dateiPfad).collect(Collectors.toList());
		boolean syntaxOK = pruefenSyntax();
		if (syntaxOK) {
			incrementGraphID();
			erstellenGraphen();
		} else {
			System.err.println("Der Graph konnte nicht eingelesen werden.");
		}
	}
	
	/**
	 * Methode inkrementiert Graphen-ID.
	 */
	private static void incrementGraphID() {
		++graphID;
	}
	
	/**
	 * Methode erstellt Multigraphen.
	 */
	private static void erstellenGraphen() {
		
		graph = new MultiGraph(String.valueOf(graphID));
		System.out.println(graph);
		
		for (String zeile : geleseneZeilen) {
			String[] zeilenArray = zeile.split(" ");
			
			
			System.out.println(zeile);
		}
		
	}

	/**
	 * Methode prueft Syntax der eingelesenen Datei und gibt True zurueck, wenn 
	 * alles ok ist - ansonsten false.
	 * 
	 * @return boolean
	 */
	private static boolean pruefenSyntax() {
		for (String zeile : geleseneZeilen) {
			if (!zeile.contains(";")) {
				System.err.println("Die Zeile ist nicht korrekt abgeschlossen.");
				return false;
			}
		}
		return true;
		
	}

}
