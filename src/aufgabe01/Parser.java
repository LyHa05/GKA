package aufgabe01;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class Parser {
	
	private void Parser() {}
	
	private static Path dateiPfad;
	private static List<String> geleseneZeilen;
	private static MultiGraph graph;
	private static int graphID = 0;
	private static String regexGraph;
	
	/**
	 * Methode liest aus Datei Graph ein, prueft die Syntax, inkrementiert die Graphen-ID
	 * und erstellt einen Multigraphen. Ist die Syntax der Datei fehlerhaft wird eine
	 * Fehlermeldung angezeigt.
	 * @throws Exception 
	 */
	static void einlesenDatei() throws Exception {
		dateiPfad = MeinFileChooser.chooseFile().toPath();
		System.out.println(dateiPfad);
		geleseneZeilen = Files.lines(dateiPfad,Charset.forName("ISO_8859_1"))
				.map(string -> string.replaceAll(" ",""))
				.flatMap(line -> Stream.of(line.split(";")))
				.collect(Collectors.toList());
		
		System.out.println(geleseneZeilen);
		
		setRegex();
		
		boolean syntaxOK = geleseneZeilen.stream().allMatch(syntaxAnforderungen());

		System.out.println(syntaxOK);

		if (syntaxOK) {
			incrementGraphID();
//			erstellenGraphen();
			patternDaten();
			graph.display();
		} else {
			System.err.println("Der Graph konnte nicht eingelesen werden.");
		}
		
	}
	
	/**
	 * Methode prueft Syntax der eingelesenen Datei und gibt als Predicate<String>
	 * für das Matchen zurueck, sodass true oder false fuer die Syntax zurueck gegeben
	 * werden kann.
	 * 
	 * @return Predicate<String>
	 */
	private static Predicate<String> syntaxAnforderungen() {
		
		return aktuelleZeile -> aktuelleZeile.matches(regexGraph);
//				+ "(?<quelle>^[0-9a-zA-Z\\w]+)" 					// enthaelt nur Knoten
//				+ "(((?<kante>--|->)(?<ziel>[0-9a-zA-Z\\w]+))"	// enthaelt Kante und zweiten Knoten
//				+ "(?<kantenname>[\\(][0-9a-zA-Z\\w]+[\\)])?"		// enthaelt Kantenname
//				+ "(?<kantengewicht>:[0-9]+.?[0-9]*)?)?"			// enthaelt Kantengewicht
//				);
				
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

//				Pattern 
//				Matcher
//				.find aufrufen, danach können Ausdrücke gefunden werden
				
				
				
			}
			
			System.out.println(zeile);
			System.out.println(graph);
		}
		
	}

	private static void setRegex() {
		regexGraph = ""
				+ "(?<quelle>^[0-9a-zA-Z\\w]+)" 					// enthaelt nur Knoten
				+ "(((?<kante>--|->)(?<ziel>[0-9a-zA-Z\\w]+))"		// enthaelt Kante und zweiten Knoten
				+ "(?<kantenname>[\\(][0-9a-zA-Z\\w]+[\\)])?"		// enthaelt Kantenname
				+ "(?<kantengewicht>:[0-9]+.?[0-9]*)?)?"			// enthaelt Kantengewicht
				;
	}
		
	private static void patternDaten() {
		
		Pattern pattern = Pattern.compile(regexGraph);
//		String sQuelle = "null";
//		String sZiel = "null";
//		String sKante = "null";
//		String sKantenname = "null";
//		String sKantengewicht = "null";

		
		graph = new MultiGraph("bla " + String.valueOf(graphID));
		System.out.println(graph);
		
		// durchlaufen der einzelnen Zeilen
		for (String zeile : geleseneZeilen) {
			
			Matcher matcher = pattern.matcher(zeile);
			matcher.find();
			
			String sQuelle = matcher.group("quelle");
			String sZiel = matcher.group("ziel");
			String sKante = matcher.group("kante");
			String sKantenname = matcher.group("kantenname");
			String sKantengewicht = matcher.group("kantengewicht");
			Node nQuelle = null;
			Node nZiel = null;
			Edge eKante = null;
			boolean richtung;
			
			// Quelle als Knoten hinzufuegen
			if (!knotenEnthalten(sQuelle)) {
				System.out.println("!knotenEnthalten(sQuelle) " + (!knotenEnthalten(sQuelle)));
				nQuelle = graph.addNode(sQuelle);
			}
			
			// Ziel als Knoten hinzufuegen
			if(!sZiel.equals("null") && !knotenEnthalten(sZiel)) {
				nZiel = graph.addNode(sZiel);
			}
			
			// Kanten hinzufuegen
			if(!sKante.equals("null") && !kanteEnthalten(sQuelle + sZiel)) {
				if (sKante.equals("--")) {
					richtung = false;
				} else {
					richtung = true;
				}
				
				// falls Quellknoten schon vorhanden, bestehenden Knoten aufrufen
				if (nQuelle == null) {
					nQuelle = graph.getNode(sQuelle);
				}
				
				// falls Quellknoten schon vorhanden, bestehenden Knoten aufrufen
				if (nZiel == null) {
					nZiel = graph.getNode(sZiel);
				}
				
				System.out.println(eKante);
				// mit gerichtetem oder ungerichtetem Pfeil
				eKante = graph.addEdge((sQuelle+sZiel), nQuelle, nZiel, richtung);			
				
				// mit Kantenname
				if(sKantenname != null) {
					eKante.addAttribute("Kantenname", sKantenname);
				}
				
				// mit Kantengewicht
				if(sKantengewicht != null) {
					eKante.addAttribute("Kantengewicht", sKantengewicht);
				}
				
			}
			
		}
		
	}
	
	
	
	/**
	 * Methode ueberprueft, ob Kante in Graph enthalten ist und
	 * gibt einen Boolean zurueck.
	 * 
	 * @param e
	 * @return true, wenn Kante enthalten, ansonsten false
	 */
	private static boolean kanteEnthalten(String k) {
		
		Iterable<? extends Edge> iterableKante =  graph.getEachEdge();
		
		boolean enthalten = false;
		
		for (Edge kante : iterableKante) {
			if (kante.getId().equals(k)) {
				enthalten = true;
			} 
		}
		
		return enthalten;
		
	}
	
	
	/**
	 * Methode ueberprueft, ob Knoten in Graph enthalten ist und
	 * gibt einen Boolean zurueck.
	 * 
	 * @param e
	 * @return true, wenn Knoten enthalten, ansonsten false
	 */
	private static boolean knotenEnthalten(String k) {
		
		Iterable<? extends Node> iterableKnoten =  graph.getEachNode();
		
		boolean enthalten = false;
		
		for (Node knoten : iterableKnoten) {
			if (knoten.getId().equals(k)) {
				enthalten = true;
			}
		}
		
		return enthalten;
		
	}
	
}
