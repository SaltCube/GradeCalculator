package core;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainController
{
	private static MainController instance = null;
	@FXML MenuItem menuStudentReport;
	@FXML TextArea userText;
	@FXML MenuItem menuNew;
	@FXML MenuItem menuSave;
	@FXML MenuItem menuSaveAs;
	@FXML MenuItem menuOpen;
	@FXML MenuItem menuSettings;
	@FXML MenuItem menuAbout;
	@FXML MenuItem menuClassReport;
	@FXML MenuItem menuPrint;
	
	private String path = null;
	
	static MainController get() //singleton-ish mostly so the save function can be called from other classes
	{
		if (instance == null) instance = new MainController();
		return instance;
	}
	
	@FXML public void onMenuNew()
	{
		Utility.newFile();
	} //FXML call to new file method
	
	@FXML public void onMenuSave()
	{
		save(new File(path));
	} //FXML call to save method
	
	@FXML public void onMenuSaveAs()
	{
		save(new FileChooser().showSaveDialog(null));
	} //FXML call to save as method
	
	void save(File file) //save file method logic
	{
		if (file == null) file = new File(path);
		else
		{
			String path = file.getPath(); //sets the file path to the opened file
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
		{
			for (CharSequence line : userText.getParagraphs())
				writer.write(line.toString() + System.getProperty("line.separator"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Error writing to the file");
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error writing to the file.", ButtonType.OK);
			alert.showAndWait();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			System.out.println("Null pointer error");
		}
	}
	
	@FXML public void onMenuOpen()
	{
		open(new FileChooser().showOpenDialog(null));
	} //FXML call to open file
	
	private void open(File file) //open file method logic
	{
		path = file.getPath(); //sets the file path to the opened file
		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			String line; //string for storing current line read
			userText.setText(null); //wipes the current text in the textarea
			while ((line = reader.readLine()) != null)
			{
				//System.out.println(line);
				userText.appendText(line); //adds line read
				userText.appendText(System.getProperty("line.separator")); //adds a new line
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Error finding the file");
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error finding the file.", ButtonType.OK);
			alert.showAndWait();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Error reading the file");
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error reading the file.", ButtonType.OK);
			alert.showAndWait();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			System.out.println("Null pointer error");
		}
	}
	
	@FXML public void onMenuPrint() //NEEDS FIX; (disabled) FXML call print method
	{
		print(new File("C:/File.txt") /* <-this is a placeholder, get the current file instead*/);
	}
	
	void print(File file) //print method logic
	{
		/* print the file passed */
	}
	
	@FXML public void onMenuSettings()
	{
		settings();
	} //NEEDS FIX; (currently disabled) FXML call open settings window
	
	private void settings() //open settings window logic
	{
		try
		{
			Stage settings = new Stage(); //opens a new stage for the settings window
			settings.setTitle("Settings"); //sets title
			settings.getIcons().add(new Image("core/icon.png")); //sets icon
			settings.setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings_GUI.fxml")))); //sets GUI file
			settings.setAlwaysOnTop(true); //sets settings window to always show on top of desktop (until closed)
			settings.show(); //shows the settings window
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("something went wrong opening settings");
		}
	}
	
	@FXML public void onMenuClassReport()
	{
		classReport();
	} //FXML call open report window
	
	private void classReport() //open report window logic
	{
		try
		{
			Stage report = new Stage(); //opens a new stage for the report window
			report.setTitle("ClassReport"); //sets title
			report.getIcons().add(new Image("core/icon.png")); //sets icon
			report.setScene(new Scene(FXMLLoader.load(getClass().getResource("ClassReport_GUI.fxml")))); //sets GUI file
			report.show(); //shows window
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong opening report");
		}
	}
	
	@FXML public void onMenuStudentReport()
	{
		studentReport();
	} //FXML call open studentReport window
	
	private void studentReport() //open studentReport window logic
	{
		try
		{
			Stage report = new Stage(); //new stage for report window
			report.setTitle("StudentReport"); //sets title
			report.getIcons().add(new Image("core/icon.png")); //sets icon
			report.setScene(new Scene(FXMLLoader.load(getClass().getResource("StudentReport_GUI.fxml")))); //sets GUI file
			report.setAlwaysOnTop(true); //sets to always show on top of desktop (until closed)
			report.show(); //shows window
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong opening studentReport");
		}
	}
	
	@FXML public void onMenuAbout()
	{
		about();
	} //FXML call open about window
	
	private void about() //open about window logic
	{
		try
		{
			Stage about = new Stage(); //new stage for about window
			about.setTitle("About"); //sets title
			about.getIcons().add(new Image("core/icon.png")); //sets icon
			about.setScene(new Scene(FXMLLoader.load(getClass().getResource("About_GUI.fxml")))); //sets GUI file
			about.setResizable(false); //sets the window to not be resizable
			about.setAlwaysOnTop(true); //sets to always show on top of desktop (until closed)
			about.show(); //shows window
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong opening about");
		}
	}
	
	List<String> getTextList()
	{
		List<String> list = new ArrayList<>();
		for (CharSequence line : userText.getParagraphs()) list.add(line.toString());
		return list;
	}
}