package utility;

import core.Main;
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
			new Tracer(e).showAlert();
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
			new Tracer(e).showAlert();
		}
		catch (NullPointerException e)
		{
			System.err.println("Null pointer error in save method");
		}
	}
	
	public static void print(File file) //NEEDS FIX
	{
	
	}
}

