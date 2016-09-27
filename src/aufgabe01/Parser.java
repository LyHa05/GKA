package aufgabe01;

/**
 * @author Lydia Pflug, Lucas Anders
 * @date 27.09.2016
 *
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.graphstream.graph.Node;
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
		geleseneZeilen = Files.lines(dateiPfad,Charset.forName("ISO_8859_1")).collect(Collectors.toList());
		List<String> lesen = Files.lines(dateiPfad,Charset.forName("ISO_8859_1"))
				.map(string -> string.replaceAll(" ",""))
				.flatMap(line -> Stream.of(line.split(";")))
				.collect(Collectors.toList());
		

		System.out.println(lesen);
				
//		boolean syntaxOK = pruefenSyntax();
//		if (syntaxOK) {
//			incrementGraphID();
//			erstellenGraphen();
//		} else {
//			System.err.println("Der Graph konnte nicht eingelesen werden.");
//		}
//		graph.display();
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
		
		// durchlaufen der einzelnen Zeilen
		for (String zeile : geleseneZeilen) {
			String[] zeilenArray = zeile.split(" ");
			
			String knoten1 = zeilenArray[0].trim();
			
			// pruefen, ob mehr als Knoten enthalten ist
			if (zeilenArray.length > 1) {
			
				String knoten2 = zeilenArray[2].trim();
				boolean gerichteteKante = true;
				if (zeilenArray[1].trim().equals("--")) {
					gerichteteKante = false;
				} else if (zeilenArray[1].trim().equals("->")) {
					gerichteteKante = true;
				}
						


					graph.addNode(knoten1);

					graph.addNode(knoten2);


				graph.addEdge((knoten1 + knoten2), knoten1, knoten2, gerichteteKante);

			}
			
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
