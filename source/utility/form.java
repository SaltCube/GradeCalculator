package utility;

import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;

import java.util.*;
public class form
{
	@NotNull public static List<String> textList(TextArea textArea)
	{
		if (textArea == null) throw new NullPointerException();
		List<String> data = new ArrayList<>();
		for (CharSequence line : textArea.getParagraphs()) data.add(line.toString());
		return data;
	}
	
	@Deprecated public static List<String> getStudentsList(Map<String, LinkedHashMap<String, List<Float>>> students)
	{
		List<String> data = new ArrayList<>();
		for (Map.Entry<String, LinkedHashMap<String, List<Float>>> bigGrades : students.entrySet())
		{
			String bigKey = bigGrades.getKey();
			LinkedHashMap<String, List<Float>> bigValue = bigGrades.getValue();
			data.add(bigKey);
			for (Map.Entry<String, List<Float>> midGrades : bigValue.entrySet())
			{
				String midKey = midGrades.getKey();
				List<Float> midValue = midGrades.getValue();
				data.add(midKey + ":" + midValue);
			}
			data.add("————————————————"); //"————————————————" to stand in as separator between individual student data
		}
		return data;
	}
	
	@Deprecated public static Map<String, LinkedHashMap<String, List<Float>>> getStudents(TextArea textArea)
	{
		Map<String, LinkedHashMap<String, List<Float>>> students = new LinkedHashMap<>();
		String[] labels = new String[0];
		List<String> studentLines = new ArrayList<>();
		boolean inStudents = false;
		for (CharSequence readChars : textArea.getParagraphs())
		{
			String readLine = readChars.toString();
			readLine = unComment(readLine);
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
			LinkedHashMap<String, List<Float>> studentData = new LinkedHashMap<>();
			assert labels.length > 0;
			for (int labelIndex = 0; labelIndex < labels.length; labelIndex++)
			{
				String[] currentGrades = studentLine[1].replaceAll("[ -]", "").split("\\*");
				List<Float> gradeData = new ArrayList<>();
				for (String score : currentGrades[labelIndex].split(","))
				{
					if (score.equals("*")) break;
					if (!score.equals("")) gradeData.add(Float.parseFloat(score));
				}
				studentData.put(labels[labelIndex], gradeData);
			}
			students.put(studentLine[0], studentData);
		}
		return students;
	}
	
	@Deprecated public static Map<String, List> formatData(TextArea textArea)
	{
		Map<String, List> formats = new HashMap<>();
		//new FileChooser().showOpenDialog(null)
		for (CharSequence readChars : textArea.getParagraphs())
		{
			//boolean inFormat = false;
			String readLine = readChars.toString();
			readLine = unComment(readLine);
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
	
	@NotNull public static Map[] parseData(TextArea textArea)
	{
		if (textArea == null) throw new NullPointerException();
		Map<String, List> formats = new HashMap<>();
		Map<String, LinkedHashMap<String, List<Float>>> students = new LinkedHashMap<>();
		buffer.objects.put("comments", new ArrayList<String>());
		boolean inFormats = false, inStudents = false;
		for (CharSequence readChars : textArea.getParagraphs())
		{
			String readLine = readChars.toString();
			if (!readLine.isEmpty())
			{
				if (readLine.startsWith("//")) ((ArrayList<String>)buffer.objects.get("comments")).add(readLine);
				else if (readLine.contains("//"))
					((ArrayList<String>)buffer.objects.get("comments")).add(readLine.split("//")[1]);
				readLine = unComment(readLine);
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
							String[] formatting = line[1].split(",");
							int count = formatting.length;
							switch (line[0])
							{
								case ("labels"):
								{
									formats.put(line[0], Arrays.asList(formatting));
									break;
								}
								case ("drops"):
								{
									List<Integer> drops = new ArrayList<>(count);
									for (String drop : formatting) drops.add(Integer.parseInt(drop));
									formats.put(line[0], drops);
									break;
								}
								case ("weights"):
								{
									List<Float> weights = new ArrayList<>(count);
									for (String weight : formatting) weights.add(Float.parseFloat(weight));
									formats.put(line[0], weights);
									break;
								}
								case ("letters"):
								{
									List<Character> letters = new ArrayList<>(count);
									for (String letter : formatting)
									{
										assert letter.length() == 1;
										letters.add(letter.charAt(0));
									}
									formats.put(line[0], letters);
									break;
								}
								case ("cutoffs"):
								{
									List<Float> cutoffs = new ArrayList<>(count);
									for (String cutoff : formatting) cutoffs.add(Float.parseFloat(cutoff));
									formats.put(line[0], cutoffs);
									break;
								}
								default:
								{
									List<String> formatList = Arrays.asList(formatting);
									buffer.objects.put("unknownFormats", readLine);
									formats.put("?" + line[0], formatList);
								}
							}
						}
						if (inStudents)
						{
							String[] line = readLine.split(":");
							List labels = formats.get("labels");
							assert labels.size() > 0;
							LinkedHashMap<String, List<Float>> studentGrades = new LinkedHashMap<>(labels.size());
							for (int labelIndex = 0; labelIndex < labels.size(); labelIndex++)
							{
								String[] currentGrades = line[1].replaceAll("[ -]", "").split("\\*");
								List<Float> gradeData = new ArrayList<>();
								for (String score : currentGrades[labelIndex].split(","))
								{
									if (score.equals("*")) break;
									if (!score.isEmpty()) gradeData.add(Float.parseFloat(score));
								}
								studentGrades.put((String)labels.get(labelIndex), gradeData);
							}
							students.put(line[0], studentGrades);
						}
					}
				}
			}
		}
		return new Map[]{formats, students};
	}
	
	@NotNull public static List[] listData(Map[] data)
	{
		if (data == null) throw new NullPointerException();
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
	
	@NotNull public static String unComment(String line) //de-comments lines and removes any non-parsable special characters
	{
		if (line == null) throw new NullPointerException();
		return line.split("//")[0].replaceAll("[^A-Za-z0-9:,.* \\-]|/\\\\*.*/\\\\*", "");
	}
	
	@NotNull public static List<String> getComments(String line) //returns only the comment(s) in the line as a list
	{
		if (line == null) throw new NullPointerException();
		List<String> comments = new ArrayList<>();
		if (line.contains("//")) comments.add(line.split("//")[1]);
		if (line.matches("/\\\\*.*/\\\\*")) comments.add(line.replaceAll("^/\\\\*.*/\\\\*", ""));
		return comments;
	}
	
	public static String digits(float d)
	{
		if (d == (int)d) return String.format("%d", (int)d);
		else return String.format("%s", d);
	}
}