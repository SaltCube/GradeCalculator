package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import utility.IO;
import utility.buffer;
import utility.form;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClassReport implements Initializable
{
	@FXML TextArea reportText;
	@FXML Button reportSaveAs;
	@FXML Button reportPrint;
	
	@FXML void onReportSaveAs()
	{
		IO.save(new FileChooser().showSaveDialog(null), form.textList(reportText)); //opens save as for the report window
	} //NEEDS FIX; FXML call for report save
	
	@FXML void onReportPrint()
	{
		IO.print(new File("C:/File.txt")/* <-this is a placeholder, print report file instead*/);
	} //NEEDS FIX; (disabled) FXML call for report print
	
	private void showStudentData()
	{
		for (Object line : (List<String>)form.listData(form.parseData((TextArea)buffer.objects.get("TextArea")))[1])
			reportText.appendText(line + System.getProperty("line.separator"));
	}
	
	private void showReport()
	{
	
	}
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		reportText.setEditable(false); //make text not editable
		showReport();
		reportText.appendText("\n\n");
		reportText.appendText("————————————————");
		reportText.appendText("\n\n");
		showStudentData();
	}
}