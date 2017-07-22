package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override public void start(Stage mainStage) throws Exception
	{
		mainStage.setTitle("TextFX"); //set title of the main window
		mainStage.getIcons().add(new Image("core/icon.png")); //sets the icon for this stage
		mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Main_GUI.fxml")))); //sets the GUI file for this stage
		mainStage.show(); //shows the window
	}
}