package aufgabe01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
	private static ArrayList<Node> markierteKnoten;
	private static HashMap<Integer,ArrayList<Node>> kuerzesterWeg;
	
	public static void startenAlgorithmus(String source, String target) throws IllegalArgumentException, Exception {
			graph = Parser.einlesenDatei();
			
			durchfuehrenBFS(source, target);
			
			for(Entry<Integer, ArrayList<Node>> entry : gekennzeichnKnoten.entrySet()){
			    System.out.printf("Nummer : %s and Knoten: %s %n", entry.getKey(), entry.getValue());
			}
			
			System.out.println("-------------gekennzeichnKnoten zu Ende---------------");
			
			rueckverfolgenWeg();
			
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
		
		System.out.println("targetGefunden:" + targetGefunden);
		System.out.println("nachbarnLeer: " + nachbarnLeer);
		
		// TODO Schleifenabbruch pruefen, Algorithmus wird zu schnell unterbrochen, da Liste bereits vorher leer
		while(!targetGefunden && !nachbarnLeer) {
			++markierer;
			
			// benachbarte Knoten ermitteln
			ArrayList<Node> nachbarn = new ArrayList<>(ermittelnBenachbarteUnmarkierteKnoten(betrachteterKnoten));
						
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
		
		System.out.println("benachbarteKnotenMarkieren() fertig");
	}
	
	private static ArrayList<Node> ermittelnBenachbarteUnmarkierteKnoten(Node knoten) {
		
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

//		ArrayList<Node> nachbarn = new ArrayList<>(gekennzeichnKnoten.get(markierer));
		
		kuerzesterWeg = new HashMap<>();
		
		// target in kuerzestesterWeg mit aufnehmen
		ArrayList<Node> targetArrayList = new ArrayList<>();
		targetArrayList.add(target);
		kuerzesterWeg.put(markierer, targetArrayList);
		

		
		ermittelnBenachbarteMarkierteKnoten(target);
		
//		while (!kuerzesterWeg.contains(source)) {
//		
//			Iterator<Node> nachbarKnotenIter = betrachteterKnoten.getNeighborNodeIterator();
//			
//			--markierer;
//			
//			ArrayList<Node> tempArray = new ArrayList<>();
//			Node nachbar = null;
//						
//			while (nachbarKnotenIter.hasNext()) {
//				nachbar = nachbarKnotenIter.next();
//				if (gekennzeichnKnoten.get(markierer).contains(nachbar)) {
//					tempArray.add(nachbar);
//				}
//			}
//			
//			betrachteterKnoten = nachbar;
//		
//			
////			--markierer;
////			nachbarn = gekennzeichnKnoten.get(markierer);
//			
//		
//			
//		}
		
		for(Entry<Integer, ArrayList<Node>> entry : kuerzesterWeg.entrySet()){
		    System.out.printf("Nummer : %s and Knoten: %s %n", entry.getKey(), entry.getValue());
		}

		
//		for (HashMap<Integer, ArrayList<Node>> knoten : kuerzesterWeg) {
//			System.out.println("Nummer: " + knoten.kuerzesterWeg..indexOf(knoten));
//			System.out.println(knoten);
//		}
		
	}
	
	
	private static void ermittelnBenachbarteMarkierteKnoten(Node knoten) {
		
		Node betrachteterKnoten = knoten;
		
		while (!kuerzesterWeg.get(markierer).contains(source)) {
			
			Iterator<Node> nachbarKnotenIter = betrachteterKnoten.getNeighborNodeIterator();
			
			--markierer;
			
			ArrayList<Node> tempArray = new ArrayList<>();
			Node nachbar = null;
						
			while (nachbarKnotenIter.hasNext()) {
				nachbar = nachbarKnotenIter.next();
				System.out.println("gekennzeichnKnoten: " + gekennzeichnKnoten);
				System.out.println("markierer: " + markierer);
				System.out.println("nachbar: " + nachbar);
				if (gekennzeichnKnoten.get(markierer).contains(nachbar)) {
					tempArray.add(nachbar);
				}
			}
			
			kuerzesterWeg.put(markierer, tempArray);
			
			ermittelnBenachbarteMarkierteKnoten(nachbar);
			
		}
		
	}
	
}


