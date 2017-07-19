package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable
{
	private final String path = "GCAL_settings.cfg";
	@FXML CheckBox autoSaveBox;
	@FXML TextField autoSaveTime;
	
	// --Commented out by Inspection START (7/18/2017 12:49):
	//	@FXML private void autoSave()
	//	{
	//		if (autoSaveBox.isSelected())
	//		{
	//			//int time = Integer.parseInt((new BufferedReader(new FileReader(path))).readLine());
	//			Thread saveThread = new Thread(() -> TextEditorController.get().save(null));
	//			saveThread.start();
	//		}
	//	}
	// --Commented out by Inspection STOP (7/18/2017 12:49)
	
	@FXML public void settingsChange() throws IOException
	{
		try (BufferedWriter configOut = new BufferedWriter(new FileWriter(path)))
		{
			configOut.write("autosave: ");
			configOut.write(autoSaveBox.isSelected() + ", " + autoSaveTime.getText());
			configOut.write(System.getProperty("line.separator"));
			configOut.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("settings file not found");
		}
	}
	
	public void openSettings()
	{
		try (BufferedReader in = new BufferedReader(new FileReader(path)))
		{
			autoSaveTime.setText(in.readLine());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		//autoSaveBox.setSelected(true);
	}
}