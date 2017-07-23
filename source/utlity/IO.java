package utlity;

import core.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class IO
{
	public static void newFile()
	{
		try
		{
			new Main().start(new Stage()); //simply opens new duplicate window, blank
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Error opening new window");
		}
	}
	
	public static void save(File file, List<String> data) //save file method logic
	{
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
		{
			for (String line : data)
				writer.write(line + System.getProperty("line.separator"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Error writing to the file");
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error writing to the file.", ButtonType.OK);
			alert.showAndWait();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			System.err.println("Null pointer error");
		}
	}
	
	public static void print(File file) //NEEDS FIX
	{
	
	}
}

