package core;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
class Utility //Utility class
{
	static void newFile()
	{
		try
		{
			new Main().start(new Stage()); //simply opens new duplicate window, blank
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error opening new window");
		}
	}
	
	static Map<String, Object> buffer = new HashMap<>();
	
	static List<String> textList(TextArea textArea)
	{
		List<String> data = new ArrayList<>();
		for (CharSequence line : textArea.getParagraphs()) data.add(line.toString());
		return data;
	}
	
	static void save(File file, List<String> data) //save file method logic
	{
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
		{
			for (String line : data)
				writer.write(line + System.getProperty("line.separator"));
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
	
	static void print(File file) //NEEDS FIX
	{
	
	}
	
	static List<String> getDataList(Map<String, LinkedHashMap<String, List<Integer>>> students)
	{
		List<String> data = new ArrayList<>();
		for (Map.Entry<String, LinkedHashMap<String, List<Integer>>> bigGrades : students.entrySet())
		{
			String bigKey = bigGrades.getKey();
			LinkedHashMap<String, List<Integer>> bigValue = bigGrades.getValue();
			data.add(bigKey);
			for (Map.Entry<String, List<Integer>> midGrades : bigValue.entrySet())
			{
				String midKey = midGrades.getKey();
				List<Integer> midValue = midGrades.getValue();
				data.add(midKey + ":" + midValue);
			}
			data.add("————————————————"); //"————————————————" to stand in as separator between individual student data
		}
		return data;
	}
	
	static Map<String, LinkedHashMap<String, List<Integer>>> getData(TextArea textArea)
	{
		Map<String, LinkedHashMap<String, List<Integer>>> students = new LinkedHashMap<>();
		String[] labels = new String[0];
		List<String> studentLines = new ArrayList<>();
		boolean inStudents = false;
		for (CharSequence readChars : textArea.getParagraphs())
		{
			String readLine = readChars.toString();
			readLine = readLine.split("//")[0].replaceAll("[^A-Za-z0-9:,.* \\-]|/\\\\*.*/\\\\*", "");
			System.out.println(readLine);
			if (!readLine.equals(""))
			{
				if (readLine.contains("labels"))
					labels = readLine.toLowerCase().replaceAll("labels:|[; \\-]", "").split(",");
				if (!inStudents & readLine.toUpperCase().contains("STUDENTS")) inStudents = true;
				else if (inStudents & !readLine.equals("")) studentLines.add(readLine);
			}
		}
		for (String student : studentLines)
		{
			String[] studentLine = student.split(":");
			LinkedHashMap<String, List<Integer>> studentData = new LinkedHashMap<>();
			assert labels.length > 0;
			for (int labelIndex = 0; labelIndex < labels.length; labelIndex++)
			{
				String[] currentGrades = studentLine[1].replaceAll("[ -]", "").split("\\*");
				List<Integer> gradeData = new ArrayList<>();
				for (String score : currentGrades[labelIndex].split(","))
				{
					if (score.equals("*")) break;
					if (!score.equals("")) gradeData.add(Integer.parseInt(score));
				}
				studentData.put(labels[labelIndex], gradeData);
			}
			students.put(studentLine[0], studentData);
		}
		return students;
	}
}