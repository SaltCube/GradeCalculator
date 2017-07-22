package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable //Settings is disabled for now
{
	private final String path = "GCAL_settings.cfg"; //path to store the settings configurations
	@FXML CheckBox autoSaveBox;
	@FXML TextField autoSaveTime;
	
	/*@FXML private void autoSave()
	{
		if (autoSaveBox.isSelected())
		{
			//int time = Integer.parseInt((new BufferedReader(new FileReader(path))).readLine());
			Thread saveThread = new Thread(() -> MainController.get().save(null));
			saveThread.start();
		}
	}*/
	
	@FXML public void settingsChange() throws IOException //NEEDS FIX; if something changes in the settings window, update the file
	{
		try (BufferedWriter configOut = new BufferedWriter(new FileWriter(path)))
		{
			configOut.write("autosave: "); //just filler for now, updates the autosave file
			configOut.write(autoSaveBox.isSelected() + ", " + autoSaveTime.getText());
			configOut.write(System.getProperty("line.separator"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("settings file not found");
		}
	}
	
	public void openSettings()
	{
		try (BufferedReader in = new BufferedReader(new FileReader(path))) //reads the file before opening the settings window
		{
			autoSaveTime.setText(in.readLine()); //reads the file and sets the settings autoSave time (filler for now)
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		//openSettings(); //on window open, call the openSettings() method
	}
}