package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TextEditor extends Application
{
	@Override
	public void start(Stage mainStage) throws Exception
	{
		mainStage.setTitle("TextFX");
		mainStage.getIcons().add(new Image("core/icon.png"));
		mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("TextEditor_GUI.fxml"))));
		mainStage.show();
	}
}