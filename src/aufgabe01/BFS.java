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
			
		Integer markierer = 0;
		
		knotenListe.add(source);
		
		gekennzeichnKnoten.put(markierer,new ArrayList<Node>(knotenListe));
		
		boolean tGefunden = false;
		
		while(!tGefunden) {
			++markierer;
			Iterator<Node> benachbarteKnoten = source.getNeighborNodeIterator();
			while (benachbarteKnoten.hasNext()) {
				Node nachbar = benachbarteKnoten.next();
// TODO weiter schreiben
			}
		}
		
	}

}
