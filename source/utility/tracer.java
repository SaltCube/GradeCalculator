package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Tracer
{
	private Exception exception = null;
	private String type = null;
	private StackTraceElement[] elements = null;
	private String stringElements = null;
	private StackTraceElement[] mainElements = null;
	private String stringMainElements = null;
	private Throwable cause = null;
	private Throwable[] suppressed = null;
	
	public Tracer(Exception e)
	{
		if (exception == null) throw new NullPointerException();
		else exception = e;
	}
	
	private static StackTraceElement[] mainTrace(StackTraceElement[] array)
	{
		List<StackTraceElement> elements = new ArrayList<>();
		for (StackTraceElement line : array)
		{
			if (line.toString().contains("core")) elements.add(line);//just append the immediately relevant
			if (line.toString().contains("utility")) elements.add(line);
		}
		return elements.toArray(new StackTraceElement[elements.size()]);
	}
	
	private static String stringedElements(StackTraceElement[] array)
	{
		return form.listString(Arrays.asList(array));
	}
	
	public Exception getException()
	{
		return exception;
	}
	
	public void setException(Exception e)
	{
		exception = e;
	}
	
	public String getType()
	{
		if (type == null)
		{
			type = exception.toString();
			return type;
		}
		else return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public StackTraceElement[] getElements()
	{
		if (elements == null)
		{
			elements = exception.getStackTrace();
			return elements;
		}
		else return elements;
	}
	
	public String getStringElements()
	{
		if (stringElements == null)
		{
			stringElements = stringedElements(getElements());
			return stringElements;
		}
		else return stringElements;
	}
	
	public StackTraceElement[] getMainElements()
	{
		if (mainElements == null)
		{
			mainElements = mainTrace(getElements());
			return mainElements;
		}
		else return mainElements;
	}
	
	public String getStringMainElements()
	{
		if (stringMainElements == null)
		{
			stringMainElements = stringedElements(getMainElements());
			return stringMainElements;
		}
		else return stringMainElements;
	}
	
	public Throwable getCause()
	{
		if (cause == null)
		{
			cause = exception.getCause();
			return cause;
		}
		else return cause;
	}
	
	public Throwable[] getSuppressed()
	{
		if (suppressed == null)
		{
			suppressed = exception.getSuppressed();
			return suppressed;
		}
		else return suppressed;
	}
	
	public String toString()
	{
		return getType() + "\n" + getStringMainElements();
	}
}