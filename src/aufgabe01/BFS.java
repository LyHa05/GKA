package aufgabe01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class BFS {
	
	private void BFS() {}
	
	private static MultiGraph graph;
	private static Node source;
	private static Node target;
	private static Integer markierer;
	private static HashMap<Integer,ArrayList<Node>> gekennzeichnKnoten;
	private static HashMap<Integer,ArrayList<Node>> kuerzesterWeg;
	
	public static void startenAlgorithmus(String source, String target) throws IllegalArgumentException, Exception {
			graph = Parser.einlesenDatei();
			
			if (eingabeKnotenPruefen(source) && eingabeKnotenPruefen(target)) {
			
				durchfuehrenBFS(source, target);
				
				for(Entry<Integer, ArrayList<Node>> entry : gekennzeichnKnoten.entrySet()){
				    System.out.printf("Nummer : %s and Knoten: %s %n", entry.getKey(), entry.getValue());
				}
				
				System.out.println("-------------gekennzeichnKnoten zu Ende---------------");
				
				rueckverfolgenWeg();
				
				for(Entry<Integer, ArrayList<Node>> entry : kuerzesterWeg.entrySet()){
				    System.out.printf("Nummer : %s and Knoten: %s %n", entry.getKey(), entry.getValue());
				}
				
				ausgabeKuerzesterWeg();
			} else {
				throw new IllegalArgumentException("Die Eingaben fuer Quelle und Ziel sind ungueltig.");
			}
			
	}
	
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
	
	private static void durchfuehrenBFS(String s, String t) {
		
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
	
	private static void benachbarteKnotenMarkieren(Node knoten) {
			
		boolean targetGefunden = false;
		boolean nachbarnLeer = false;
		
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
				System.out.println("markierer: " + markierer);
				System.out.println("gekennzeichnKnoten: " + gekennzeichnKnoten);
				
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
	
	private static ArrayList<Node> ermittelnBenachbarteUnmarkierteKnoten(Node knoten) {
		
		ArrayList<Node> benachbarKnotenList = new ArrayList<>();
		
		Iterator<Node> benachbarKnotenIter = knoten.getNeighborNodeIterator();
		
		while (benachbarKnotenIter.hasNext()) {
			Node nachbar = benachbarKnotenIter.next();
			if (!bereitsMarkiert(nachbar)) {
				benachbarKnotenList.add(nachbar);
			}
		}
		
		return benachbarKnotenList;
	}
	
	private static boolean bereitsMarkiert(Node knoten) {
		
		Set<Integer> markierungen =  gekennzeichnKnoten.keySet();
		
		boolean markiert = false;
		
		for(Integer m :markierungen) {
			ArrayList<Node> markierteKnoten = gekennzeichnKnoten.get(m);
			if (markierteKnoten.contains(knoten)) {markiert = true;}
		}
		
		return markiert;
	}

	private static void rueckverfolgenWeg() {
		
		kuerzesterWeg = new HashMap<>();

		// Target in kuerzestesterWeg mit aufnehmen
		ArrayList<Node> targetArrayList = new ArrayList<>();
		targetArrayList.add(target);
		kuerzesterWeg.put(markierer, targetArrayList);
				
		ermittelnBenachbarteMarkierteKnoten(target);
			
	}
	
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


