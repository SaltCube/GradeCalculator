package core;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.IO;
import utility.Tracer;
import utility.buffer;
import utility.form;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application
{
	@Override public void start(Stage mainStage) throws Exception
	{
		mainStage.setTitle("TextFX"); //set title of the main window
		mainStage.getIcons().add(new Image("core/icon.png")); //sets the icon for this stage
		mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Main_GUI.fxml")))); //sets the GUI file for this stage
		mainStage.show(); //shows the window
	}
	
	@FXML Button buttonAbout;
	@FXML MenuItem menuStudentReport;
	@FXML TextArea userText;
	@FXML MenuItem fileNew;
	@FXML MenuItem fileSave;
	@FXML MenuItem fileSaveAs;
	@FXML MenuItem fileOpen;
	@FXML MenuItem fileSettings;
	@FXML MenuItem helpAbout;
	@FXML MenuItem menuClassReport;
	@FXML MenuItem filePrint;
	@FXML MenuItem editUndo;
	@FXML MenuItem editRedo;
	@FXML MenuItem editPaste;
	@FXML MenuItem editCopy;
	@FXML MenuItem editCut;
	private String path = null;
	
	@FXML public void onFileNew()
	{
		IO.newFile();
	} //FXML call to new file method
	
	@FXML public void onFileSave()
	{
		if (path == null)
		{
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Save failed");
			alert.setHeaderText("There is nothing to save");
			alert.setContentText("No file specified to save new data to.\nFile path is null.");
			alert.showAndWait();
		}
		else IO.save(new File(path), form.textList(userText));
	} //FXML call to save method
	
	@FXML public void onFileSaveAs()
	{
		
		File file = new FileChooser().showSaveDialog(userText.getScene().getWindow());
		path = file.getPath();
		IO.save(file, form.textList(userText));
	} //FXML call to save as method
	
	@FXML public void onFileOpen()
	{
		try
		{
			open(new FileChooser().showOpenDialog(userText.getScene().getWindow()));
		}
		catch (Exception e)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("null pointer error");
			Tracer tracer = new Tracer(e);
			alert.setContentText(tracer.toString());
			alert.showAndWait();
		}
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
			System.err.println("Error finding the file");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Couldn't open file specified");
			alert.setContentText(new Tracer(e).toString());
			alert.showAndWait();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Error reading the file");
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error reading the file.");
			alert.setTitle("Error");
			alert.setHeaderText("Couldn't read the file specified");
			alert.setContentText(new Tracer(e).toString());
			alert.showAndWait();
		}
		catch (NullPointerException e) //when cancel is pressed
		{
			e.printStackTrace();
			System.err.println("Null pointer error");
		}
	}
	
	@FXML public void onFilePrint() //NEEDS FIX; (disabled) FXML call print method
	{
		IO.print(new File("C:/File.txt") /* <-this is a placeholder, get the current file instead*/);
	}
	
	@FXML public void onFileSettings()
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
			System.err.println("Something went wrong opening settings");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Couldn't open settings");
			alert.setContentText(new Tracer(e).toString());
			alert.showAndWait();
		}
	}
	
	@FXML public void onEditUndo()
	{
		userText.undo();
	}
	
	@FXML public void onEditRedo()
	{
		userText.redo();
	}
	
	@FXML public void onEditCut()
	{
		userText.cut();
	}
	
	@FXML public void onEditCopy()
	{
		userText.copy();
	}
	
	@FXML public void onEditPaste()
	{
		userText.paste();
	}
	
	@FXML public void onMenuClassReport()
	{
		buffer.objects.put("TextArea", userText);
		classReport();
	} //FXML call open report window
	
	private void classReport() //open report window logic
	{
		try
		{
			if (userText.getParagraphs().size() < 8)
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Report failed");
				alert.setHeaderText("No data to parse");
				alert.setContentText("Could not parse the class report because there was insufficient data to generate a report from");
				alert.showAndWait();
			}
			else
			{
				Stage report = new Stage(); //opens a new stage for the report window
				report.setTitle("ClassReport"); //sets title
				report.getIcons().add(new Image("core/icon.png")); //sets icon
				report.setScene(new Scene(FXMLLoader.load(getClass().getResource("ClassReport_GUI.fxml")))); //sets GUI file
				report.show(); //shows window
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Something went wrong opening class report");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Couldn't open class report");
			alert.setContentText((new Tracer(e)).toString());
			alert.showAndWait();
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
			if (userText.getParagraphs().size() < 8)
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Report failed");
				alert.setHeaderText("No data to parse");
				alert.setContentText("Could not parse the student report because there was insufficient data to generate a report from");
				alert.showAndWait();
			}
			else
			{
				Stage report = new Stage(); //new stage for report window
				report.setTitle("StudentReport"); //sets title
				report.getIcons().add(new Image("core/icon.png")); //sets icon
				report.setScene(new Scene(FXMLLoader.load(getClass().getResource("StudentReport_GUI.fxml")))); //sets GUI file
				report.setAlwaysOnTop(true); //sets to always show on top of desktop (until closed)
				report.show(); //shows window
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Something went wrong opening student report");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Couldn't open student report");
			alert.setContentText(new Tracer(e).toString());
			alert.showAndWait();
		}
	}
	
	@FXML public void onHelpAbout()
	{
		about();
	} //FXML call open about window
	
	@FXML public void onButtonAbout()
	{
		about();
	}
	private void about() //open about window logic
	{
		try
		{
			Stage about = new Stage(); //new stage for about window
			about.setTitle("About"); //sets title
			about.getIcons().add(new Image("core/icon.png")); //sets icon
			about.setScene(new Scene(FXMLLoader.load(getClass().getResource("About_GUI.fxml")))); //sets GUI file
			about.setResizable(false); //sets the window to not be resizable
			//about.initModality(Modality.APPLICATION_MODAL);
			about.setAlwaysOnTop(true); //sets to always show on top of desktop (until closed)
			about.show(); //shows window
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Something went wrong opening about");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Couldn't open about");
			alert.setContentText(new Tracer(e).toString());
			alert.showAndWait();
		}
	}
	
	private List<String> getTextList(TextArea textArea)
	{
		List<String> list = new ArrayList<>();
		for (CharSequence line : textArea.getParagraphs()) list.add(line.toString());
		return list;
	}
}