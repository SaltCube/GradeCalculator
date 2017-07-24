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
import java.util.*;

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
	
	private Map[] data = form.parseData((TextArea)buffer.objects.get("TextArea"));
	private Map<String, List> formats = (HashMap<String, List>)data[0];
	private Map<String, LinkedHashMap<String, List<Float>>> students = (LinkedHashMap<String, LinkedHashMap<String, List<Float>>>)data[1];
	private int studentCount = students.keySet().size();

	private void showReport()
	{
		String[] data = reportData();
		reportText.appendText("COMMENTS:\n");
		reportText.appendText(data[0]);
		reportText.appendText("————————————————\n");
		//reportText.appendText(data[1]);
		//System.out.println(data.length);
		//for(String chunk : data) reportText.appendText(chunk + "\n");
	}
	
	private String[] reportData()
	{
		String[] dataStrings = new String[5];
		dataStrings[0] = form.listString((List)buffer.objects.get("comments")); //comment data
		dataStrings[1] = form.listString(Arrays.asList(studentData())); //[WIP] student data
		//dataStrings[2]
		//dataStrings[3]
		//dataStrings[4]
		return dataStrings;
	}
	
	private String[] studentData()
	{
		String[] studentData = new String[studentCount]; //array for storing each student's data in a String line
		for (int i = 0; i < studentCount; i++)
			studentData[i] = studentString(i); //iterate through the students and assign them their place in the array
		return studentData;
	}
	
	private String studentString(int index) //for example- Student A: [95 98 100 80 100 70 100], [66 77], [92] -> 84.8 = B
	{
		StringBuilder studentBuilder = new StringBuilder();
		String[] studentArray = students.keySet().toArray(new String[studentCount]);
		Map studentData = students.get(studentArray[index]);
		
		return studentBuilder.toString();
	}
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		reportText.setEditable(false); //make text not editable
		showReport();
		//reportText.appendText("\n\n");
		//reportText.appendText("————————————————");
		//reportText.appendText("\n\n");
		//showStudentData();
	}
}
