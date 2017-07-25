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
	private List<String> labels = new ArrayList<>(formats.get("labels"));
	private int labelCount = labels.size();
	private List<Integer> drops = new ArrayList<>(formats.get("drops"));
	private List<Float> weights = new ArrayList<>(formats.get("weights"));
	private List<Character> letters = (ArrayList<Character>)formats.get("letters");
	private int letterCount = letters.size();
	private List<Float> cutoffs = (ArrayList<Float>)formats.get("cutoffs");
	private Map<String, Integer> gradeLabelCounts = new LinkedHashMap<>();
	private List<String> warningData = new ArrayList<>();
	private List<Character> classLetters = new ArrayList<>(letterCount);

	private void showReport()
	{
		String[] data = reportData();
		reportText.appendText("COMMENTS———————————————————————\n");
		reportText.appendText(data[0]);
		reportText.appendText("\nNOTES——————————————————————————\n");
		reportText.appendText(data[1]);
		reportText.appendText("\nGRADES—————————————————————————\n");
		reportText.appendText(data[2]);
		reportText.appendText("\nLETTER-COUNTS——————————————————\n");
		reportText.appendText(data[3]);
		reportText.appendText("\nWARNINGS———————————————————————\n");
		//reportText.appendText(data[4]);
	}
	
	private String[] reportData()
	{
		String[] dataStrings = new String[5];
		dataStrings[0] = form.listString((List)buffer.objects.get("comments")); //comment data
		dataStrings[2] = form.listString(Arrays.asList(studentData())); //student data
		dataStrings[1] = form.listString(Arrays.asList(noteData())); //note data
		dataStrings[3] = form.listString(letterData()); //letter count data
		dataStrings[4] = form.listString(warningData); //students' warning data
		return dataStrings;
	}
	
	private List<String> letterData()
	{
		List<String> letterData = new ArrayList<>(letterCount);
		for (char letter : letters) letterData.add(letter + " count: " + Collections.frequency(classLetters, letter));
		return letterData;
	}
	
	private String[] noteData()
	{
		String[] notes = new String[labelCount];
		for (int i = 0; i < labels.size(); i++)
		{
			String label = labels.get(i);
			int drop = drops.get(i);
			notes[i] = (new StringBuilder(3))
					   .append(label.endsWith("s") ? label + " are" : label + " is")
					   .append(" weighed at ")
					   .append(weights.get(i))
					   .append(" and ")
					   .append((drop == 1) ? drop + " drops" : drop + " drop")
					   .toString();
		}
		return notes;
	}
	
	private String[] studentData()
	{
		String[] studentData = new String[studentCount]; //array for storing each student's data in a String line
		for (int i = 0; i < studentCount; i++)
			studentData[i] = studentString(i); //iterate through the students and assign them their place in the array
		return studentData;
	}
	
	private String[] studentArray = students.keySet().toArray(new String[studentCount]);
	
	private String studentString(int index) //for example- Student A: [95 98 100 80 100 70 100], [66 77], [92] -> 84.8 = B
	{
		String student = studentArray[index];
		Map<String, List<Float>> studentData = students.get(student);
		StringBuilder gradeData = new StringBuilder();
		for (Object entryObject : studentData.entrySet())
		{
			Map.Entry<String, List<Float>> entry = (Map.Entry<String, List<Float>>)entryObject;
			gradeData.append(entry.getValue()).append(" ");
		}
		return student + ": " + gradeData.toString() + gradeString(student, studentData);
	}
	
	private void setGradeLabelCounts()
	{
		//int studentGradeCount = studentGrades.size();
		//if(studentGradeCount >= gradeLabelCounts.get(label)) gradeLabelCounts.put(label, studentGradeCount);
		//else warningData.add(student + " is missing" + (gradeLabelCounts.get(label) - studentGradeCount) + label);
	}
	
	private String gradeString(String student, Map<String, List<Float>> studentData)
	{
		assert labelCount == drops.size() & labelCount == weights.size(); //change to popup later
		float total = 0;
		//gradesCount;
		for (int i = 0; i < labelCount; i++) gradeLabelCounts.put(labels.get(i), 0);
		for (int i = 0; i < labelCount; i++)
		{
			String label = labels.get(i);
			List<Float> studentGrades = studentData.get(label);
			
			//int studentGradeCount = studentGrades.size();
			//if(studentGradeCount >= gradeLabelCounts.get(label)) gradeLabelCounts.put(label, studentGradeCount);
			//else warningData.add(student + " is missing" + (gradeLabelCounts.get(label) - studentGradeCount) + label);
			
			total += getGrade(studentGrades, label, drops.get(i)) * weights.get(i);
		}
		return (new StringBuilder()).append("\tx̅:").append(form.digits(total)) //x̅ means sample average; μ means population average
									.append(" ∴").append(getLetter(total))
									.toString();
	}
	
	private float getGrade(List<Float> grades, String label, int dropCount)
	{
		grades.sort(Float::compare);
		for (int i = 0; i < dropCount; i++) grades.remove(0);
		float average = 0;
		for (float grade : grades) average += grade;
		average /= grades.size();
		return average;
	}
	
	private char getLetter(float total)
	{
		assert letterCount == cutoffs.size(); //change to popup later
		Map<Character, Float> limit = new TreeMap<>();
		int mapIndex = 0;
		for (char letter : letters)
		{
			limit.put(letter, cutoffs.get(mapIndex));
			mapIndex++;
		}
		char letter = '0';
		for (Map.Entry<Character, Float> entry : limit.entrySet())
		{
			if (total >= entry.getValue())
			{
				letter = entry.getKey();
				break;
			}
		}
		assert letter != '0'; //replace with popup
		classLetters.add(letter);
		return letter;
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