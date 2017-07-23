package core;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
class utility //utility class
{
	static Map<String, Object> buffer = new HashMap<>();

	static void newFile()
	{
		try
		{
			new Main().start(new Stage()); //simply opens new duplicate window, blank
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Error opening new window");
		}
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
			System.err.println("Error writing to the file");
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error writing to the file.", ButtonType.OK);
			alert.showAndWait();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			System.err.println("Null pointer error");
		}
	}
	
	static List<String> textList(TextArea textArea)
	{
		List<String> data = new ArrayList<>();
		for (CharSequence line : textArea.getParagraphs()) data.add(line.toString());
		return data;
	}
	
	static List<String> getStudentsList(Map<String, LinkedHashMap<String, List<Integer>>> students)
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
	
	static void print(File file) //NEEDS FIX
	{
	
	}
	
	static Map<String, LinkedHashMap<String, List<Integer>>> getStudents(TextArea textArea)
	{
		Map<String, LinkedHashMap<String, List<Integer>>> students = new LinkedHashMap<>();
		String[] labels = new String[0];
		List<String> studentLines = new ArrayList<>();
		boolean inStudents = false;
		for (CharSequence readChars : textArea.getParagraphs())
		{
			String readLine = readChars.toString();
			readLine = digest(readLine);
			//System.out.println(readLine);
			if (!readLine.isEmpty())
			{
				if (readLine.toLowerCase().contains("labels"))
					labels = readLine.toLowerCase().replaceAll("labels:|[; \\-]", "").split(",");
				if (!inStudents & readLine.toUpperCase().contains("STUDENT")) inStudents = true;
				else if (inStudents) studentLines.add(readLine);
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
	
	static Map<String, List> formatData(TextArea textArea)
	{
		Map<String, List> formats = new HashMap<>();
		//new FileChooser().showOpenDialog(null)
		for (CharSequence readChars : textArea.getParagraphs())
		{
			//boolean inFormat = false;
			String readLine = readChars.toString();
			readLine = digest(readLine);
			if (readLine.toUpperCase().contains("STUDENT")) break;
			else if (!readLine.equals(""))
			{
				//System.out.println(readLine);
				String[] formatLine = readLine.split(":");
				if (!readLine.toUpperCase().contains("FORMAT"))
					formats.put(formatLine[0], Arrays.asList(formatLine[1].split(",")));
			}
		}
		for (Map.Entry<String, List> entry : formats.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue().toString());
		return formats;
	}
	
	@NotNull static Map[] parseData(TextArea textArea)
	{
		Map<String, List> formats = new HashMap<>();
		Map<String, LinkedHashMap<String, List<Integer>>> students = new LinkedHashMap<>();
		boolean inFormats = false, inStudents = false;
		for (CharSequence readChars : textArea.getParagraphs())
		{
			String readLine = digest(readChars.toString());
			if (!readLine.isEmpty())
			{
				if (!inStudents && !inFormats && readLine.toUpperCase().contains("FORMAT")) inFormats = true;
				else if (!inStudents && inFormats && readLine.toUpperCase().contains("STUDENT"))
				{
					inStudents = true;
					inFormats = false;
				}
				else
				{
					if (inFormats)
					{
						String[] line = readLine.toLowerCase().split(":");
						formats.put(line[0], Arrays.asList(line[1].split(",")));
					}
					if (inStudents)
					{
						String[] line = readLine.split(":");
						List labels = formats.get("labels");
						assert labels.size() > 0;
						LinkedHashMap<String, List<Integer>> studentGrades = new LinkedHashMap<>(labels.size());
						for (int labelIndex = 0; labelIndex < labels.size(); labelIndex++)
						{
							String[] currentGrades = line[1].replaceAll("[ -]", "").split("\\*");
							List<Integer> gradeData = new ArrayList<>();
							for (String score : currentGrades[labelIndex].split(","))
							{
								if (score.equals("*")) break;
								if (!score.isEmpty()) gradeData.add(Integer.parseInt(score));
							}
							studentGrades.put((String)labels.get(labelIndex), gradeData);
						}
						students.put(line[0], studentGrades);
					}
				}
			}
		}
		return new Map[]{formats, students};
	}
	
	@NotNull static List[] listData(Map[] data)
	{
		List<String> formats = new ArrayList<>();
		List<String> students = new ArrayList<>();
		for (Object entryObject : data[0].entrySet())
		{
			Map.Entry<String, List> entry = (Map.Entry<String, List>)entryObject;
			formats.add(entry.getKey() + ": " + entry.getValue());
		}
		for (Object entryObject : data[1].entrySet())
		{
			Map.Entry<String, Map<String, List>> entry = (Map.Entry<String, Map<String, List>>)entryObject;
			students.add(entry.getKey());
			for (Map.Entry<String, List> midGrades : entry.getValue().entrySet())
				students.add("\t" + midGrades.getKey() + ": " + midGrades.getValue());//empty line between students' data
		}
		return new List[]{formats, students};
	}
	
	@NotNull static String digest(String line) //de-comments lines and removes any non-parsable special characters
	{
		return line.split("//")[0].replaceAll("[^A-Za-z0-9:,.* \\-]|/\\\\*.*/\\\\*", "");
	}
	
	static String stringedTrace(StackTraceElement[] array)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (StackTraceElement line : array)
		{
			String stringLine = line.toString();
			if (stringLine.contains("core"))
				stringBuilder.append(stringLine).append("\n");//just append the immediately relevant
			else System.out.println(colors.YELLOW + "\t\t(util-tracer)\t" + stringLine + colors.RESET);
		}
		return stringBuilder.toString();
		//returns only minimum
	}
	
	final static class colors
	{
		static final String RESET = "\u001B[0m";
		static final String BLACK = "\u001B[30m";
		static final String RED = "\u001B[31m";
		static final String GREEN = "\u001B[32m";
		static final String YELLOW = "\u001B[33m";
		static final String BLUE = "\u001B[34m";
		static final String PURPLE = "\u001B[35m";
		static final String CYAN = "\u001B[36m";
		static final String WHITE = "\u001B[37m";
	}
	/*
	Alert alert = new Alert(Alert.AlertType.ERROR);
	alert.setTitle("Error");
	alert.setHeaderText("Test error");
	alert.setContentText(utility.stringedTrace(exception.getStackTrace()));
	alert.showAndWait();
	*/
}