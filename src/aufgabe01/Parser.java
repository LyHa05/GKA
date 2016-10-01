package aufgabe01;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

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
	public static MultiGraph einlesenDatei() throws Exception, IllegalArgumentException {
		dateiPfad = MeinFileChooser.chooseFile().toPath();
		System.out.println(dateiPfad);
		geleseneZeilen = Files.lines(dateiPfad,Charset.forName("ISO_8859_1"))
				.map(string -> string.replaceAll(" ",""))
				.flatMap(line -> Stream.of(line.split(";")))
				.collect(Collectors.toList());
		
		System.out.println(geleseneZeilen);
		
		setRegex();
		
		boolean syntaxOK = geleseneZeilen.stream().allMatch(syntaxAnforderungen());

		if (syntaxOK) {
			
			incrementGraphID();
			
			graph = new MultiGraph(String.valueOf(graphID));
	        graph.setStrict(false);
	        graph.setAutoCreate(true);
	        graph.display();
			
			erstellenGraphen();
			
		} else {
			throw new IllegalArgumentException();
		}

		return graph;
		
	}
	
	/**
	 * Methode prueft Syntax der eingelesenen Datei und gibt Predicate<String>
	 * für das Matchen zurueck, sodass true oder false fuer die Syntax zurueck gegeben
	 * werden kann.
	 * 
	 * @return Predicate<String> mit regulaerem Ausdruck fuer Matching
	 */
	private static Predicate<String> syntaxAnforderungen() {
		
		return aktuelleZeile -> aktuelleZeile.matches(regexGraph);
		
	}
	
	/**
	 * Methode inkrementiert Graphen-ID.
	 */
	private static void incrementGraphID() {
		++graphID;
	}

	private static void setRegex() {
		regexGraph = ""
				+ "(?<quelle>^[0-9a-zA-Z\\w]+)" 					// enthaelt nur Knoten
				+ "(((?<kante>--|->)(?<ziel>[0-9a-zA-Z\\w]+))"		// enthaelt Kante und zweiten Knoten
				+ "(?<kantenname>[\\(][0-9a-zA-Z\\w]+[\\)])?"		// enthaelt Kantenname
				+ "(?<kantengewicht>:[0-9]+.?[0-9]*)?)?"			// enthaelt Kantengewicht
				;
	}
		
	/**
	 * Methode zum Erstellen des Graphen
	 */
	private static void erstellenGraphen() {
		
		Pattern pattern = Pattern.compile(regexGraph);
		
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
					nQuelle.addAttribute("ui.label", sQuelle);
				}
				
				// falls Quellknoten schon vorhanden, bestehenden Knoten aufrufen
				if (nZiel == null) {
					nZiel = graph.getNode(sZiel);
					nZiel.addAttribute("ui.label", sZiel);
				}
				
				// mit gerichtetem oder ungerichtetem Pfeil
				eKante = graph.addEdge((sQuelle+sZiel), nQuelle, nZiel, richtung);
				
				// mit Kantenname
				if(sKantenname != null) {
					eKante.addAttribute("Kantenname", sKantenname);
					eKante.addAttribute("ui.label", sKantenname);
				}
				
				// mit Kantengewicht
				if(sKantengewicht != null) {
					eKante.addAttribute("Kantengewicht", sKantengewicht);
					eKante.addAttribute("ui.label", sKantengewicht);
				}
				
			}
			
		}
		
	}
	
	/**
	 * Methode speichert Graphen in *.gka-Datei
	 */
	public static void speichernGraphen() {
		
	}
	
	/**
	 * Methode ueberprueft, ob Kante in Graph enthalten ist und
	 * gibt einen Boolean zurueck.
	 * 
	 * @param String mit ID der Kante (Quelle und Ziel)
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
	 * @param String mit ID der Kante
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
