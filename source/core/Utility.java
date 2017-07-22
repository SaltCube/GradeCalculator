package core;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	
	static Map<String, LinkedHashMap<String, List<Integer>>> getData(TextArea userText)
	{
		Map<String, LinkedHashMap<String, List<Integer>>> students = new LinkedHashMap<>();
		String[] labels = new String[0];
		List<String> studentLines = new ArrayList<>();
		boolean inStudents = false;
		for (CharSequence readChars : userText.getParagraphs())
		{
			String readLine = readChars.toString();
			if (!readLine.equals(""))
			{
				readLine = readLine.split("//")[0].replaceAll("/\\\\*.*/\\\\*", "");
				if (readLine.contains("labels"))
					labels = readLine.toLowerCase().replaceAll("labels:|;", "").split(",");
				if (!inStudents & readLine.toUpperCase().contains("STUDENTS")) inStudents = true;
				else if (inStudents & !readLine.equals("")) studentLines.add(readLine.replaceAll(";", ""));
			}
		}
		for (String student : studentLines)
		{
			String[] studentLine = student.split(":");
			String name = studentLine[0];
			LinkedHashMap<String, List<Integer>> studentData = new LinkedHashMap<>();
			assert labels.length > 0;
			int labelIndex = 0;
			for (String label : labels)
			{
				String[] currentGrades = studentLine[1].split("\\*");
				List<Integer> gradeData = new ArrayList<>();
				for (String c : currentGrades[labelIndex].split(","))
				{
					if (c.equals("*")) break;
					if (!c.equals("")) gradeData.add(Integer.parseInt(c));
				}
				studentData.put(label, gradeData);
				labelIndex++;
			}
			students.put(name, studentData);
		}
		return students;
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
}
