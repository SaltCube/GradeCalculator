package utility;

import java.util.List;
public class Student
{
	private String name;
	private List<Grades> grades;
	
	Student() {}
	
	Student(String name)
	{
		this.name = name;
	}
	
	Student(List<Grades> grades)
	{
		this.grades = grades;
	}
	
	Student(String name, List<Grades> grades)
	{
		this.name = name;
		this.grades = grades;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public List<Grades> getGrades()
	{
		return grades;
	}
	
	public void setGrades(List<Grades> grades)
	{
		this.grades = grades;
	}
}