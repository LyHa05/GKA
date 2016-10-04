package aufgabe01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	private static ArrayList<Node> markierteKnoten;
	
	public static void startenAlgorithmus(String source, String target) throws IllegalArgumentException, Exception {
			graph = Parser.einlesenDatei();
			
			durchfuehrenBFS(source, target);
			
	}
	
	private static void durchfuehrenBFS(String s, String t) {
		
//		ArrayList<Node> knotenListe = new ArrayList<>();
//		
//		gekennzeichnKnoten = new HashMap<>();
		
		source = graph.getNode(s);
		target = graph.getNode(t);
		
		benachbarteKnotenMarkieren(source);
		
		
		
//		// i = 0 gesetzt
//		markierer = 0;
//		
//		// source auf 0 setzen
//		knotenListe.add(source);
//		gekennzeichnKnoten.put(markierer,new ArrayList<Node>(knotenListe));
//		
//		boolean targetGefunden = false;
//		boolean nachbarnLeer = false;
//		
//		Node betrachteterKnoten = source;
//		
//		while(!targetGefunden && !nachbarnLeer) {
//			++markierer;
//			
//			// benachbarte Knoten ermitteln
//			ArrayList<Node> nachbarn = new ArrayList<>(ermittelnBenachbarteKnoten(betrachteterKnoten));
//						
//			// pruefen, kein Weg zu Target vorhanden, ansonsten Nachbarn kennzeichnen
//			if (nachbarn.isEmpty()) {
//				nachbarnLeer = true;
//			} else {
//				gekennzeichnKnoten.put(markierer,nachbarn);
//			}
//			
//			// pruefen, ob Target bereits gefunden
//			if (nachbarn.contains(target)) {targetGefunden = true;}
//			
//
//			
//			for (Node node : nachbarn) {
//				betrachteterKnoten = node;
//			}
//			
//			
//		}
		
	}
	
	private static void benachbarteKnotenMarkieren(Node knoten) {
		
		ArrayList<Node> knotenListe = new ArrayList<>();
		
		gekennzeichnKnoten = new HashMap<>();
		
//		source = graph.getNode(s);
//		target = graph.getNode(t);
		
		// i = 0 gesetzt
		markierer = 0;
		
		// source auf 0 setzen
		knotenListe.add(source);
		gekennzeichnKnoten.put(markierer,new ArrayList<Node>(knotenListe));
		
		boolean targetGefunden = false;
		boolean nachbarnLeer = false;
		
		Node betrachteterKnoten = source;
		
		while(!targetGefunden && !nachbarnLeer) {
			++markierer;
			
			// benachbarte Knoten ermitteln
			ArrayList<Node> nachbarn = new ArrayList<>(ermittelnBenachbarteKnoten(betrachteterKnoten));
						
			// pruefen, kein Weg zu Target vorhanden, ansonsten Nachbarn kennzeichnen
			if (nachbarn.isEmpty()) {
				nachbarnLeer = true;
			} else {
				gekennzeichnKnoten.put(markierer,nachbarn);
			}
			
			// pruefen, ob Target bereits gefunden
			if (nachbarn.contains(target)) {targetGefunden = true;}
			
			
			for (Node node : nachbarn) {
				betrachteterKnoten = node;
				
				benachbarteKnotenMarkieren(betrachteterKnoten);
				
			}
			
			
		}
		
		
	}
	
	private static ArrayList<Node> ermittelnBenachbarteKnoten(Node knoten) {
		
		ArrayList<Node> benachbarKnotenList = new ArrayList<>();
		
		Iterator<Node> benachbarKnotenIter = knoten.getNeighborNodeIterator();
		
		while (benachbarKnotenIter.hasNext()) {
			Node nachbar = benachbarKnotenIter.next();
			if (bereitsMarkiert(nachbar)) {
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
		
	}
	
}
