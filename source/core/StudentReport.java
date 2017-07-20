package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentReport implements Initializable
{
	@FXML Button reportSaveAs;
	@FXML Button reportPrint;
	
	@FXML void onReportSaveAs()
	{
		TextEditorController.get().save(new FileChooser().showSaveDialog(null)); //opens save as for the report window
	} //NEEDS FIX; FXML call for report save
	
	@FXML void onReportPrint()
	{
		TextEditorController.get().print(new File("C:/File.txt")/* <-this is a placeholder, print report file instead*/);
	} //NEEDS FIX; (disabled) FXML call for report print
	
	ArrayList<String> getReport()
	{
		ArrayList<String> list = TextEditorController.get().EditorArrayList();
		//for (String line : list) {}
		return list;
	}
	@Override public void initialize(URL location, ResourceBundle resources) {}
}