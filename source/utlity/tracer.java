package utlity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
public class tracer
{
	public Exception exception;
	public String type;
	public StackTraceElement[] traceElements;
	public StackTraceElement[] mainElements;
	public Throwable cause;
	public Throwable[] suppressed;
	
	@NotNull tracer(Exception exception)
	{
		if (exception == null) throw new NullPointerException();
		else
		{
			this.exception = exception;
			type = exception.toString();
			traceElements = exception.getStackTrace();
			mainElements = mainTrace(traceElements);
			cause = exception.getCause();
			suppressed = exception.getSuppressed();
		}
	}
	
	@Deprecated public static String stringedTrace(StackTraceElement[] array)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (StackTraceElement line : array)
		{
			String stringLine = line.toString();
			if (stringLine.contains("core"))
				stringBuilder.append(stringLine).append("\n");//just append the immediately relevant
			else System.out.println(color.yellow + "\t\t(util-tracer)\t" + stringLine + color.reset);
		}
		return stringBuilder.toString();
	}
	
	private StackTraceElement[] mainTrace(StackTraceElement[] array)
	{
		List<StackTraceElement> elements = new ArrayList<>();
		for (StackTraceElement line : array)
		{
			if (line.toString().contains("core")) elements.add(line);//just append the immediately relevant
		}
		return (StackTraceElement[])elements.toArray();
	}
	
	public String mainElements()
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (StackTraceElement line : mainElements)
		{
			String stringLine = line.toString();
			if (stringLine.contains("core"))
				stringBuilder.append(stringLine).append("\n");//just append the immediately relevant
			else System.out.println(color.yellow + "\t\t(util-tracer)\t" + stringLine + color.reset);
		}
		return stringBuilder.toString();
	}
	
	public String toString()
	{
		return type;
	}
}