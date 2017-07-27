package utility;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ExceptionAlert implements Initializable
{
	@FXML public Button exceptionSaveAs;
	@FXML public Button exceptionClose;
	@FXML public TextArea exceptionText;
	Tracer tracer;
	private Exception exception;
	
	@FXML public void onExceptionSaveAs()
	{
	
	}
	
	@FXML public void onExceptionClose()
	{
		((Stage)exceptionClose.getScene().getWindow()).close();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		//exception = (Exception) buffer.objects.get("Exception");
		//tracer = (Tracer) buffer.objects.get("tracer");
		//System.out.println(tracer.toString());
	}
}
