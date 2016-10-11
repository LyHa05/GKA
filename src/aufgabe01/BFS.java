package aufgabe01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * @author Lydia Pflug, Lucas Anders
 * @date 11.10.2016
 * Diese Klasse implementiert den BFS-Algorithmus und gibt den kuerzsten
 * Weg aus.
 */

public class BFS {
	
	private void BFS() {}
	
	private static MultiGraph graph;
	private static Node source;
	private static Node target;
	private static Integer markierer;
	private static HashMap<Integer,ArrayList<Node>> gekennzeichnKnoten;
	private static HashMap<Integer,ArrayList<Node>> kuerzesterWeg;
	private static boolean targetGefunden = false;
	private static boolean nachbarnLeer = false;
	
	/**
	 * Die Methode liest den Graph, startet den BFS-Algorithmus
	 * und gibt am Ende den kuerzesten Weg aus.
	 * @param source
	 * @param target
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public static void startenAlgorithmus(String source, String target) throws IllegalArgumentException, Exception {
			graph = Parser.einlesenDatei();
			
			if (eingabeKnotenPruefen(source) && eingabeKnotenPruefen(target)) {
			
				hinwegVerfolgen(source, target);
				
				for(Entry<Integer, ArrayList<Node>> entry : gekennzeichnKnoten.entrySet()){
				    System.out.printf("Nummer : %s and Knoten: %s %n", entry.getKey(), entry.getValue());
				}
				
				System.out.println("-------------gekennzeichnKnoten zu Ende---------------");
				
				rueckwegVerfolgen();
				
				for(Entry<Integer, ArrayList<Node>> entry : kuerzesterWeg.entrySet()){
				    System.out.printf("Nummer : %s and Knoten: %s %n", entry.getKey(), entry.getValue());
				}
				
				ausgabeKuerzesterWeg();
			} else {
				throw new IllegalArgumentException("Die Eingaben fuer Quelle und Ziel sind ungueltig.");
			}
			
	}
	
	/**
	 * Die Methode ueberprueft, ob die eingegebenen Knoten(Source, Target)
	 * fuer den Algorithmus im eingelesenen Graphen enthalten sind. 
	 * @param eingabe
	 * @return true, wenn Knoten im Graphen enthalten sind, ansonsten false
	 */
	private static boolean eingabeKnotenPruefen(String eingabe) {
		Iterator<? extends Node> iterNode = graph.getEachNode().iterator();
		boolean enthalten = false;
		while (iterNode.hasNext()) {
			Node node = (Node) iterNode.next();
			if (node.toString().equals(eingabe)) {
				enthalten = true;
			}
			
		}
		return enthalten;
	}
	
	/**
	 * Methode gibt den kuerzesten Weg als Ergebnis des Algorithmus BFS aus.
	 */
	private static void ausgabeKuerzesterWeg() {
		
		StringBuilder sb = new StringBuilder();
		
		
		for(Entry<Integer, ArrayList<Node>> entry : kuerzesterWeg.entrySet()){
			
			for (Node node : entry.getValue()) {
				if (entry.getValue().size() > 1 && !node.equals(entry.getValue().get(entry.getValue().size()-1))) {
					sb.append(node);
					sb.append("/");
				} else if (!entry.getValue().contains(target)) { 
					sb.append(node);
					sb.append(", ");
				} else {
					sb.append(node);
				}
			}
		}
		
		System.out.println("kuerzester Weg: " + sb.toString());
		
	}
	
	/**
	 * Methode nimmt Initialisierungen fuer den Hinweg des Alsgorithmus vor
	 * und ruft die Methode zum Markieren der benachbarten unmarkierten Knoten auf.
	 * @param s
	 * @param t
	 */
	private static void hinwegVerfolgen(String s, String t) {
		
		source = graph.getNode(s);
		target = graph.getNode(t);

		gekennzeichnKnoten = new HashMap<>();
		
		// i = 0 gesetzt
		markierer = 0;
		
		// source auf 0 setzen
		ArrayList<Node> knotenListe = new ArrayList<>();
		knotenListe.add(source);
		gekennzeichnKnoten.put(markierer,new ArrayList<Node>(knotenListe));
		
		benachbarteKnotenMarkieren(source);
		
	}
	
	/**
	 * Methode markiert benachbarte unmarkierte Knoten bis der Zielknoten gefunden
	 * worden ist oder es keine zu markierenden Nachbarn mehr gibt.
	 * @param knoten
	 */
	private static void benachbarteKnotenMarkieren(Node knoten) {
					
		Node betrachteterKnoten = knoten;
		
		// Schleifendurchlauf bis target gefunden worden ist
		// oder die es keine zu markierenden Nachbarn mehr gibt
		while(!targetGefunden && !nachbarnLeer) {
			
			// benachbarte Knoten ermitteln
			ArrayList<Node> nachbarn = new ArrayList<>(ermittelnBenachbarteUnmarkierteKnoten(betrachteterKnoten));
						
			// pruefen, kein Weg zu Target vorhanden, ansonsten Nachbarn kennzeichnen
			if (nachbarn.isEmpty()) {
				nachbarnLeer = true;
			} else {
				++markierer;
				gekennzeichnKnoten.put(markierer,nachbarn);
			}
			
			// pruefen, ob Target bereits gefunden
			if (nachbarn.contains(target)) {
				targetGefunden = true;
			}
			
			for (Node node : nachbarn) {
				betrachteterKnoten = node;
				
				benachbarteKnotenMarkieren(betrachteterKnoten);
				
			}	
		}
	}
	
	/**
	 * Die Methode ermittelt alle benachbarten Knoten, die noch unmarkiert sind.
	 * @param knoten
	 * @return ArrayList<Node> mit benachbarten und unmarkierten Knoten
	 */
	private static ArrayList<Node> ermittelnBenachbarteUnmarkierteKnoten(Node knoten) {
		
		ArrayList<Node> benachbarKnotenList = new ArrayList<>();
		
		Iterator<Node> benachbarKnotenIter = knoten.getNeighborNodeIterator();
		
		while (benachbarKnotenIter.hasNext()) {
			Node nachbar = benachbarKnotenIter.next();
			Edge kante = nachbar.getEdgeBetween(knoten);
			// gerichtete Kanten
			if (kante.isDirected()){
				if(kante.getSourceNode().equals(nachbar) && !bereitsMarkiert(nachbar)) {
					benachbarKnotenList.add(nachbar);
				}
			// ungerichtete Kanten
			} else {
				if (!bereitsMarkiert(nachbar)) {
					benachbarKnotenList.add(nachbar);
				}
			}

		}
		
		return benachbarKnotenList;
	}
	
	/**
	 * Methode prueft, ob Knoten bereits markiert ist und gibt entsprechend einen
	 * Boolean zurueck.
	 * @param knoten
	 * @return true, wenn Knoten bereits markiert ist, ansonsten false
	 */
	private static boolean bereitsMarkiert(Node knoten) {
		
		Set<Integer> markierungen =  gekennzeichnKnoten.keySet();
		
		boolean markiert = false;
		
		for(Integer m :markierungen) {
			ArrayList<Node> markierteKnoten = gekennzeichnKnoten.get(m);
			if (markierteKnoten.contains(knoten)) {markiert = true;}
		}
		
		return markiert;
	}

	/**
	 * Methode nimmt Initialisierungen fuer den Rueckweg des Alsgorithmus vor
	 * und ruft die Methode zum Ermitteln der benachbarten markierten Knoten auf.
	 */
	private static void rueckwegVerfolgen() {
		
		kuerzesterWeg = new HashMap<>();

		// Target in kuerzestesterWeg mit aufnehmen
		ArrayList<Node> targetArrayList = new ArrayList<>();
		targetArrayList.add(target);
		kuerzesterWeg.put(markierer, targetArrayList);
				
		ermittelnBenachbarteMarkierteKnoten(target);
			
	}
	
	/**
	 * Die Methode ermittelt alle benachbarten Knoten, die der naechst niedrigeren
	 * Markierung entsprechen.
	 * @param knoten
	 */
	private static void ermittelnBenachbarteMarkierteKnoten(Node knoten) {
		
		Node betrachteterKnoten = knoten;
		
		while (!kuerzesterWeg.get(markierer).contains(source) && markierer > 0) {
			
			Iterator<Node> nachbarKnotenIter = betrachteterKnoten.getNeighborNodeIterator();
			
			--markierer;
			
			ArrayList<Node> tempArray = new ArrayList<>();
			Node nachbar = null;
						
			while (nachbarKnotenIter.hasNext()) {
				nachbar = nachbarKnotenIter.next();
				if (gekennzeichnKnoten.get(markierer).contains(nachbar) && kuerzesterWeg.containsKey(markierer)) {
					kuerzesterWeg.get(markierer).add(nachbar);
				} else if (gekennzeichnKnoten.get(markierer).contains(nachbar) && !kuerzesterWeg.containsKey(markierer)) {	
					tempArray.add(nachbar);
					kuerzesterWeg.put(markierer, tempArray);
				}
			
			}
			
			for (Node enthalteneKnoten : kuerzesterWeg.get(markierer)) {
				ermittelnBenachbarteMarkierteKnoten(enthalteneKnoten);
			}
						
		}
		
	}
	
}


