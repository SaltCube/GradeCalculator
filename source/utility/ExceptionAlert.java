package utility;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ExceptionAlert implements Initializable
{
	@FXML public Button buttonSaveAs;
	@FXML public Button buttonClose;
	@FXML public Button buttonVerbose;
	@FXML public TextArea exceptionText;
	private Tracer tracer = (Tracer)buffer.objects.get("Tracer");
	private boolean isVerbose = false;

	@FXML public void onExceptionSaveAs()
	{
		File file = new FileChooser().showSaveDialog(exceptionText.getScene().getWindow());
		IO.save(file, form.textList(exceptionText));
	}
	
	@FXML public void onExceptionVerbose()
	{
		Stage alert = (Stage)exceptionText.getScene().getWindow();
		if (!isVerbose)
		{
			alert.setTitle("Error - " + tracer.getType() + " (verbose)");
			alert.setResizable(true);
			alert.setWidth(1000);
			alert.setHeight(800);
			alert.centerOnScreen();
			alert.setMinWidth(600);
			alert.setMinHeight(400);
			exceptionText.setFont(Font.font("System", FontWeight.NORMAL, 14));
			exceptionText.setText(tracer.getException() + "\n" + tracer.getStringElements());
			buttonVerbose.setText("Brief");
			isVerbose = true;
		}
		else
		{
			alert.setTitle("Error - " + tracer.getType());
			alert.setResizable(false);
			alert.setWidth(500);
			alert.setHeight(300);
			alert.centerOnScreen();
			alert.setMinWidth(300);
			alert.setMinHeight(200);
			exceptionText.setFont(Font.font("System", FontWeight.NORMAL, 24));
			exceptionText.setText(tracer.toString());
			buttonVerbose.setText("Verbose");
			isVerbose = false;
		}
	}
	
	@FXML public void onExceptionClose()
	{
		((Stage)buttonClose.getScene().getWindow()).close();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		exceptionText.setText(tracer.toString());
	}
}
