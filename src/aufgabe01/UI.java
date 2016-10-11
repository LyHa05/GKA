package aufgabe01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Lydia Pflug, Lucas Anders
 * @date 11.10.2016
 * Diese Klasse stellt ein Menue fuer die Graphenerstellung
 * und verschiedene Algorithmen bereit.
 */

public class UI {
	
    static InputStreamReader isr = new InputStreamReader(System.in);
    static BufferedReader br = new BufferedReader(isr);
	
	private void UI() {}
	
	/**
	 * Die Methode startet die Anwendung und stellt das Menue auf der Konsole dar.
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	private static void start() throws IllegalArgumentException, Exception {
		
		System.out.println("Moechten Sie");
		System.out.println("\t (a) einen Graphen einlesen und darstellen");
		System.out.println("\t (b) einen Graphen einlesen und einen Algorithmus starten");
		System.out.println("\t (c) einen Graphen nach der festgelegten Syntax eingeben");
		System.out.println("\t (x) das Programm beenden");
		String eingabe = br.readLine();
		
		eingabePruefenMenue(eingabe);
		
	}

	/**
	 * Die Methode prueft die Eingabe aus dem Menue und ruft die entsprechende Methode
	 * bzw. Klasse fuer die gewaehlte Funktionalitaet auf.
	 * @param s
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	private static void eingabePruefenMenue(String s) throws Exception, IllegalArgumentException {
			switch(s) {
				case "a": {
					Parser.einlesenDatei().display();
					break;
				}
				case "b": {
					System.out.println("Bitte geben Sie den Startknoten an.");
					String source = br.readLine();
					System.out.println("Bitte geben Sie den Zielknoten an.");
					String target = br.readLine();
					BFS.startenAlgorithmus(source, target);
					break;
				}
				case "c": {
					Parser.speichernGraphen();
					break;
				}
				case "x": {
					break;
				}
				default: {
					System.out.println("Bitte wiederholen Sie die Eingabe");
					start();
				}
			}
		
	}

	public static void main(String[] args) {
		try {
			start();
		} catch (IllegalArgumentException e) {
			System.err.println(e);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e);
			System.err.println("Die Datei konnte nicht gelesen werden.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
