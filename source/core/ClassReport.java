package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ClassReport implements Initializable
{
	@FXML TextArea reportText;
	@FXML Button reportSaveAs;
	@FXML Button reportPrint;
	
	@FXML void onReportSaveAs()
	{
		MainController.get().save(new FileChooser().showSaveDialog(null)); //opens save as for the report window
	} //NEEDS FIX; FXML call for report save
	
	@FXML void onReportPrint()
	{
		MainController.get().print(new File("C:/File.txt")/* <-this is a placeholder, print report file instead*/);
	} //NEEDS FIX; (disabled) FXML call for report print
	
	void getData()
	{
		for (String line : Utility.getDataList(Utility.getData(MainController.get().userText)))
		{
			System.out.println(line);
		}
	}
	
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		reportText.setEditable(false); //make text not editable
		getData();
	}
}