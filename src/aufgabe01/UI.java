package aufgabe01;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UI {
	
	private void UI() {}
	
	private static void start() throws IllegalArgumentException, Exception {
		
	    InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
		
		System.out.println("Moechten Sie");
		System.out.println("\t (a) einen Graphen einlesen und darstellen");
		System.out.println("\t (b) einen Graphen einlesen und einen Algorithmus starten");
		System.out.println("\t (c) einen Graphen nach der festgelegten Syntax eingeben");
		System.out.println("\t (x) das Programm beenden");
		String eingabe = br.readLine();
		
		pruefenEingabe(eingabe);
		
	}

	private static void pruefenEingabe(String s) throws Exception, IllegalArgumentException {
			switch(s) {
				case "a": {
					Parser.einlesenDatei().display();
					break;
				}
				case "b": {
					BFS.startenAlgorithmus();
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
			System.err.println("Der Graph konnte nicht eingelesen werden.");
		} catch (Exception e) {
			System.err.println("Die Datei konnte nicht gelesen werden.");
		}
	}
}
