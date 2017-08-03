package utility;

import java.util.List;
public class Grades
{
	private String type;
	private List<Float> scores;
	
	Grades() {}
	
	Grades(String type)
	{
		this.type = type;
	}
	
	Grades(String type, List<Float> scores)
	{
		this.type = type;
		this.scores = scores;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public List<Float> getScores()
	{
		return scores;
	}
	
	public void setScores(List<Float> scores)
	{
		this.scores = scores;
	}
}
