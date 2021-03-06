package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import utility.IO;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentReport implements Initializable
{
	@FXML Button reportSaveAs;
	@FXML Button reportPrint;
	
	@FXML void onReportSaveAs()
	{
		IO.save(new FileChooser().showSaveDialog(null), new ArrayList<>() /*placeholder for data*/); //opens save as for the report window
	} //NEEDS FIX; FXML call for report save
	
	@FXML void onReportPrint()
	{
		IO.print(new File("C:/File.txt")/* <-this is a placeholder, print report file instead*/);
	} //NEEDS FIX; (disabled) FXML call for report print
	
	List getReport() //NEEDS FIX
	{
		//List<String> list = Main.get().getTextList(); //not inline because not done
		//for (String line : list) {}
		/* placeholder for processing logic */
		return new ArrayList();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {}
}