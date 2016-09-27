package aufgabe01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
	
	private void Parser() {}
	
	private static Path dateiPfad;
	private static List<String> geleseneZeilen;
	
	static void einlesenGraph() throws IOException {
		dateiPfad = MeinFileChooser.chooseFile().toPath();
		System.out.println(dateiPfad);
		geleseneZeilen = Files.lines(dateiPfad).collect(Collectors.toList());
		pruefenSyntax();
		erstellenGrapen();
		
	}

	private static void erstellenGrapen() {
		// TODO Auto-generated method stub
		
	}

	private static void pruefenSyntax() {
		// TODO Auto-generated method stub
		
	}
	

}
