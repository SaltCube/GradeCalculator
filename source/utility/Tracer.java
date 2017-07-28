package utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
	
	public Tracer(Exception exception)
	{
		if (exception == null) throw new NullPointerException();
		this.exception = exception;
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
	
	public void showAlert()
	{
		try
		{
			Stage alert = new Stage();
			buffer.objects.put("Tracer", this);
			alert.setTitle("Error - " + getType()); //sets title
			alert.getIcons().add(new Image("utility/alert.png")); //sets icon
			alert.setScene(new Scene(FXMLLoader.load(getClass().getResource("ExceptionAlert_GUI.fxml")))); //sets GUI file
			alert.setResizable(false);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.show(); //shows window
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Could not open alert window to show exception");
			System.out.println(color.red + "IO EXCEPTION THROWN WHILE TRYING TO HANDLE AN EXCEPTION" + color.reset);
			System.out.println(color.red + e.toString() + color.reset);
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fatal Error");
			alert.setHeaderText("Could not open exception handler");
			alert.setContentText("IO EXCEPTION THROWN WHILE TRYING TO HANDLE AN EXCEPTION");
			alert.showAndWait();
		}
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
			String[] temp = exception.toString().split("\\.");
			type = temp[temp.length - 1];
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