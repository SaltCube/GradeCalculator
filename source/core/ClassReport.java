package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import utility.*;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ClassReport implements Initializable
{
	@FXML TextArea reportText;
	@FXML Button reportSaveAs;
	@FXML Button reportPrint;
	private final TextArea parentText = (TextArea)buffer.objects.get("parentText");
	//private Map[] data = form.parseTextArea(parentText);
	//format relevant objects
	private final Object[] data = form.parseTextAreaNew(parentText);
	private final Map<String, List> formats = (HashMap<String, List>)data[0];
	private List<String> labels = new ArrayList<>(formats.get("labels"));
	private final int labelCount = labels.size();
	private List<Integer> drops = new ArrayList<>(formats.get("drops"));
	private List<Float> weights = new ArrayList<>(formats.get("weights"));
	private final List<Character> letters = (ArrayList<Character>)formats.get("letters");
	private final int letterCount = letters.size();
	private final List<Float> cutoffs = (ArrayList<Float>)formats.get("cutoffs");
	//student relevant objects
	//private Map<String, LinkedHashMap<String, List<Float>>> students = (LinkedHashMap<String, LinkedHashMap<String, List<Float>>>)data[1];
	private final List<Student> students = (ArrayList<Student>)data[1];
	private final int studentCount = students.size();
	private List<String> warningData = new ArrayList<>();
	private List<Character> classLetters = new ArrayList<>(letterCount);
	private Map<String, Integer> gradeTypeCounts = new LinkedHashMap<>(labelCount);
	private final List<String> studentList = students.stream().map(Student::getName).collect(Collectors.toList());
	private final List comments = (List<String>)buffer.objects.get("comments");
	
	public TextArea getParentText()
	{
		return parentText;
	}
	
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
		for (String line : (List<String>)form.listData(form.parseTextArea(parentText))[1])
			reportText.appendText(line + System.getProperty("line.separator"));
	}
	
	private void showReport()
	{
		String[] data = reportData();
		reportText.appendText("COMMENTS———————————————————————\n");
		reportText.appendText(data[0]);
		reportText.appendText("\nNOTES——————————————————————————\n");
		reportText.appendText(data[1]);
		reportText.appendText("\nGRADES—————————————————————————\n");
		reportText.appendText(data[2]);
		reportText.appendText("\nCLASS-STATS————————————————————\n");
		reportText.appendText(data[3]);
		reportText.appendText("\nWARNINGS———————————————————————\n");
		reportText.appendText(data[4]);
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
	
	private String[] reportData()
	{
		String[] dataStrings = new String[5];
		dataStrings[0] = form.listString(comments); //comment data
		dataStrings[2] = form.listString(Arrays.asList(studentData())); //student data
		dataStrings[1] = form.listString(Arrays.asList(noteData())); //note data
		dataStrings[3] = form.listString(letterData()); //letter count data
		dataStrings[4] = form.listString(warningData); //students' warning data
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
		String student = studentList.get(index);
		Student studentData = students.get(index);
		StringBuilder gradeData = new StringBuilder();
		for (Grades scores : studentData.getGrades())
		{
			//Map.Entry<String, List<Float>> entry = (Map.Entry<String, List<Float>>)entryObject;
			String gradeType = scores.getType();
			int studentScoreCount = scores.getScores().size();
			Integer currentCount = gradeTypeCounts.get(gradeType);
			if (currentCount == null) gradeTypeCounts.put(gradeType, studentScoreCount);
			else if (currentCount < studentScoreCount) gradeTypeCounts.put(gradeType, studentScoreCount);
		}
		/*int dropIndex = 0;
		for (Map.Entry<String, Integer> entry : gradeTypeCounts.entrySet())
		{
			String gradeType = entry.getKey();
			int gradeCount = entry.getValue();
			gradeTypeCounts.replace(gradeType, gradeCount, gradeCount - drops.get(dropIndex));
			dropIndex++;
		}*/
		for (Grades scores : studentData.getGrades())
		{
			//Map.Entry<String, List<Float>> entry = (Map.Entry<String, List<Float>>)entryObject;
			gradeData.append(scores.getScores().toString()).append(" "); //fix later combine with gradeString()
		}
		return student + ": " + gradeData.toString() + gradeString(studentData);
	}
	
	private String gradeString(Student studentData)
	{
		assert labelCount == drops.size() && labelCount == weights.size(); //change to popup later
		float total = 0;
		for (int i = 0; i < labelCount; i++)
		{
			Grades studentGrades = studentData.getGrades().get(i);
			total += getGrade(studentData.getName(), studentGrades, drops.get(i)) * weights.get(i);
		}
		return (new StringBuilder()).append("\tx̅:").append(form.digits(total)) //x̅ means sample average; μ means population average
									.append(" ∴").append(getLetter(total))
									.toString();
	}
	
	private float getGrade(String student, Grades grades, int dropCount)
	{
		String gradeType = grades.getType();
		int requiredCount = gradeTypeCounts.get(gradeType);
		int difference = requiredCount - grades.getScores().size();
		if (difference > 0)
		{
			warningData.add(student + " is missing " + difference + " "
							+ ((difference > 1) ? ((gradeType.endsWith("s")) ? gradeType : gradeType + "s") : ((gradeType.endsWith("s")) ? gradeType.substring(0, gradeType.length() - 1) : gradeType)));
		}
		else if (difference < 0)
		{
			System.err.println("miscalculated required grade counts");
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Incorrect calculation");
			alert.setHeaderText("Calculated " + student + "'s grades incorrectly");
			alert.setContentText(student + "'s grades show " + (-1 * difference) + " more than calculated maximum number of grades for " + gradeType);
			alert.showAndWait();
		}
		List<Float> scores = grades.getScores();
		scores.sort(Float::compare);
		for (int i = 0; i < dropCount; i++) scores.remove(0);
		float average = 0;
		for (float grade : scores) average += grade;
		average /= (requiredCount - dropCount);
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
		buffer.objects.remove("comments");
		buffer.objects.remove("parentText");
	}
}