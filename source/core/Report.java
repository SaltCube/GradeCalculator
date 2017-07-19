package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Report implements Initializable
{
	@FXML Button reportSaveAs;
	@FXML Button reportPrint;
	
	@FXML void onReportSaveAs()
	{
		TextEditorController.get().save(new FileChooser().showSaveDialog(null));
	}
	
	@FXML void onReportPrint()
	{
		TextEditorController.get().print(new File("C:/File.txt")/* <-this is a placeholder, print report file instead*/);
	}
	@Override public void initialize(URL location, ResourceBundle resources)
	{
	}
}