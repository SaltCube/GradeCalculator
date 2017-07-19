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

public class TextEditorController
{
	private static TextEditorController instance = null;
	@FXML private TextArea userText;
	@FXML private MenuItem menuNew;
	@FXML private MenuItem menuSave;
	@FXML private MenuItem menuSaveAs;
	@FXML private MenuItem menuOpen;
	@FXML private MenuItem menuSettings;
	@FXML private MenuItem menuAbout;
	@FXML private MenuItem menuReport;
	private String path = null;

	static public void instantiate()
	{
		instance = TextEditorController.get();
	}

	static TextEditorController get()
	{
		if (instance == null) instance = new TextEditorController();
		return instance;
	}

	@FXML public void onMenuNew()
	{
		newFile();
	}

	private void newFile()
	{
		try
		{
			new TextEditor().start(new Stage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error opening new window");
		}
	}

	@FXML public void onMenuSave()
	{
		save(new File(path));
	}

	@FXML public void onMenuSaveAs()
	{
		save(new FileChooser().showSaveDialog(null));
	}

	void save(File file)
	{
		if (file == null) file = new File(path);
		else
		{
			String path = file.getPath();
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
	}

	private void open(File file)
	{
		path = file.getPath();
		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			String line;
			userText.setText(null);
			while ((line = reader.readLine()) != null)
			{
				//System.out.println(line);
				userText.appendText(line);
				userText.appendText(System.getProperty("line.separator"));
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

	@FXML public void onMenuSettings()
	{
		settings();
	}

	private void settings()
	{
		try
		{
			Stage settings = new Stage();
			settings.setTitle("Settings");
			settings.getIcons().add(new Image("core/icon.png"));
			settings.setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings_GUI.fxml"))));
			settings.setAlwaysOnTop(true);
			settings.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("something went wrong opening settings");
		}
	}

	@FXML public void onMenuReport()
	{
		report();
	}

	private void report()
	{
		try
		{
			Stage report = new Stage();
			report.setTitle("About");
			report.getIcons().add(new Image("core/icon.png"));
			report.setScene(new Scene(FXMLLoader.load(getClass().getResource("Report_GUI.fxml"))));
			report.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong opening report");
		}
	}

	@FXML public void onMenuGrade()
	{
		grade();
	}

	private void grade()
	{
		try
		{
			Stage grade = new Stage();
			grade.setTitle("About");
			grade.getIcons().add(new Image("core/icon.png"));
			grade.setScene(new Scene(FXMLLoader.load(getClass().getResource("Grade_GUI.fxml"))));
			grade.setAlwaysOnTop(true);
			grade.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong opening grade");
		}
	}

	@FXML public void onMenuAbout()
	{
		about();
	}

	private void about()
	{
		try
		{
			Stage about = new Stage();
			about.setTitle("About");
			about.getIcons().add(new Image("core/icon.png"));
			about.setScene(new Scene(FXMLLoader.load(getClass().getResource("About_GUI.fxml"))));
			about.setAlwaysOnTop(true);
			about.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong opening about");
		}
	}
}
