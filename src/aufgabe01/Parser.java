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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import aufgabe01.PredicateUtility;

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
	 * @throws Exception 
	 */
	static void einlesenGraph() throws Exception {
		dateiPfad = MeinFileChooser.chooseFile().toPath();
		System.out.println(dateiPfad);
//		geleseneZeilen = Files.lines(dateiPfad,Charset.forName("ISO_8859_1")).collect(Collectors.toList());
		geleseneZeilen = Files.lines(dateiPfad,Charset.forName("ISO_8859_1"))
				.map(string -> string.replaceAll(" ",""))
				.flatMap(line -> Stream.of(line.split(";")))
				.collect(Collectors.toList());
		
		System.out.println(geleseneZeilen);
		
		boolean syntaxOK = geleseneZeilen.stream().allMatch(syntaxAnforderungen());

		System.out.println(syntaxOK);

		if (syntaxOK) {
			incrementGraphID();
			erstellenGraphen();
		} else {
			System.err.println("Der Graph konnte nicht eingelesen werden.");
		}
		graph.display();
	}
	
	/**
	 * Methode prueft Syntax der eingelesenen Datei und gibt als Predicate<String>
	 * für das Matchen zurueck, sodass true oder false fuer die Syntax zurueck gegeben
	 * werden kann.
	 * 
	 * @return Predicate<String>
	 */
	private static Predicate<String> syntaxAnforderungen() {
		
		return aktuelleZeile -> aktuelleZeile.matches(""
				+ "(?<quelle> ^[0-9a-zA-Z\\w]+)" 				// enthaelt nur Knoten
				+ "(((?<kante> --|->)(?<ziel> [0-9a-zA-Z\\w]+))"		// enthaelt Kante und zweiten Knoten
				+ "(?<kantenname> [\\(][0-9a-zA-Z\\w]+[\\)])?"	// enthaelt Kantenname
				+ "(?<kantengewicht> :[0-9]+.?[0-9]*)?)?"			// enthaelt Kantengewicht
				);
				
//		return PredicateUtility.orAny(
				// enthaelt nur Knoten
//				string -> string.matches("^[0-9a-zA-Z\\w]*$")
				// enthaelt 2 Knoten und 1 Kante
//                ,string -> string.matches("(^[0-9a-zA-Z\\w]*)(--|->)([0-9a-zA-Z]*$)")
                // enthaelt 2 Knoten und 1 Kante und Kantenname
//                ,string -> string.matches("(^[0-9a-zA-Z\\w]*)(--|->)([0-9a-zA-Z\\w]*)([\\(][0-9a-zA-Z\\w]*[\\)])")
                // enthaelt 2 Knoten und 1 Kante und Kantengewicht
//                ,string -> string.matches("(^[0-9a-zA-Z\\w]*)(--|->)([0-9a-zA-Z\\w]*)(:[0-9]*.?[0-9]*)")               
                // enthaelt 2 Knoten und 1 Kante und Kantenname und Kantengewicht
//                ,string -> string.matches("(^[0-9a-zA-Z\\w]*)(--|->)?([0-9a-zA-Z\\w]*)?([\\(][0-9a-zA-Z\\w]*[\\)])?(:[0-9]*.?[0-9]*)?")
//				);
		
//		+ mindestens einer 
//		* keiner, einer, viel
//		? kann muss nicht
		
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
			System.out.println(graph);
		}
		
	}

}
