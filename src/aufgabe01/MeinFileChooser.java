package aufgabe01;

import java.io.File;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MeinFileChooser extends Application{
	private static File meinFile;

	@Override
	public void start(Stage meineStage) throws Exception {
		FileChooser meinFC = new FileChooser();
		meinFC.getExtensionFilters().add(new ExtensionFilter("Graph", "*.gka","*.txt"));
		meinFile = meinFC.showOpenDialog(meineStage);
		
		meineStage.show();
		meineStage.close();
	}
	
	public static File chooseFile() {
		meinFile = null;
		MeinFileChooser.launch();
		return meinFile;
	}
	
}
