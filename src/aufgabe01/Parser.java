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
		
//		graph = new MultiGraph(String.valueOf(graphID));
//		System.out.println(graph);
		
		HashSet<String> knotenSet = new HashSet<String>();
		
		// durchlaufen der einzelnen Zeilen
		for (String zeile : geleseneZeilen) {
			
			Matcher matcher = pattern.matcher(zeile);
			matcher.find();
			
			knotenSet.add(matcher.group("quelle"));
			
			String ziel = matcher.group("ziel");
			
			if(!ziel.equals("null")) {
				knotenSet.add(ziel);
			}
			
			String kante = matcher.group("kante");
			
			
			graph.edgeFactory();
		    
//					graph.addNode(knoten1);
//
//					graph.addNode(knoten2);


//				graph.addEdge((knoten1 + knoten2), knoten1, knoten2, gerichteteKante);

		}
		
	}
	
}
