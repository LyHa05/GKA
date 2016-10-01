package aufgabe01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class BFS {
	
	private void BFS() {}
	
	private static MultiGraph graph;
	
	public static void startenAlgorithmus(String source, String target) throws IllegalArgumentException, Exception {
			graph = Parser.einlesenDatei();
			
			durchfuehrenBFS(source, target);
			
	}
	
	private static void durchfuehrenBFS(String s, String t) {
		
		ArrayList<Node> knotenListe = new ArrayList<>();
		
		HashMap<Integer,ArrayList<Node>> gekennzeichnKnoten = new HashMap<>();
		
		Node source = graph.addNode(s);
		Node target = graph.addNode(t);
		
		// i = 0 gesetzt
		Integer markierer = 0;
		
		// source auf 0 setzen
		knotenListe.add(source);
		gekennzeichnKnoten.put(markierer,new ArrayList<Node>(knotenListe));
		
		boolean targetGefunden = false;
		boolean nachbarnLeer = false;
		
		while(!targetGefunden && !nachbarnLeer) {
			++markierer;
			
			// benachbarte Knoten ermitteln und kennzeichnen
			gekennzeichnKnoten.put(markierer,ermittelnBenachbarteKnoten(source));
			
			// pruefen, ob Target bereits gefunden oder kein Weg zu Target vorhanden
			if (ermittelnBenachbarteKnoten(source).isEmpty()) {nachbarnLeer = true;}
			if (ermittelnBenachbarteKnoten(source).contains(target)) {targetGefunden = true;}
		
			
		}
		
	}
	
	private static ArrayList<Node> ermittelnBenachbarteKnoten(Node knoten) {
		
		ArrayList<Node> benachbarKnotenList = new ArrayList<>();
		
		Iterator<Node> benachbarKnotenIter = knoten.getNeighborNodeIterator();
		
		while (benachbarKnotenIter.hasNext()) {
			Node nachbar = benachbarKnotenIter.next();
			benachbarKnotenList.add(nachbar);
		}
		
		return benachbarKnotenList;
	}

}
