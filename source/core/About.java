package core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import utility.Tracer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class About implements Initializable
{
	@FXML TextFlow aboutFlow;
	@FXML Button GitHubButton;
	@FXML Button closeButton;
	
	@FXML public void GitHubPressed() //on "GitHub" button pressed
	{
		try
		{
			java.awt.Desktop.getDesktop().browse(new java.net.URI("https://saltcube.github.io/GradeCalculator/")); //opens application GitHub page with default browser
			closePressed();
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
			new Tracer(e).showAlert();
		}
	}
	
	@FXML public void closePressed() //on "close" button pressed
	{
		((Stage)closeButton.getScene().getWindow()).close(); //close the window
	}
	
	@Override public void initialize(URL location, ResourceBundle resources)
	{
		//aboutFlow.setWrapText(true); //wrap if the text is too long
		//aboutText.setEditable(false); //make text not editable
		//aboutText.setDisable(true); //make text not selectable
		Text info = new Text("* labels, drops, weights, letters, and cutoffs all must be specified before the scores each on its own line with \",\" separating values\n" +
							 "\tThe lines can be any order, however the values for each line must match order to each other\n" +
							 "* StudentReport portions must end with a *, including at the end.\n" +
							 "* The maximum number of values found is assumed to be the correct number of values \n" +
							 "* Each grade value and the * must be separated by comma.\n" +
							 "* Weights are typed as a decimal. Ex: .3 for 30%.\n" +
							 "* Comments are C-style. Block comments do not need asterisks, however.\n" +
							 "* Blank lines can appear anywhere, except within data values.\n" +
							 "* ?At the end of regular data you can add as many extra credits as you want?\n" +
							 "* Warnings are not necessarily errors. You have to decide on those.\n" +
							 "* Totals and individual scores can be > 100, if you choose to.\n" +
							 "* Weights do not have to add up to 100, if you choose to.\n" +
							 "* Nothing is saved automatically (yet).\n" +
							 "* Blanks are allowed except at the beginning and end of any line.\n");
		Text example = new Text("\nExample————————————————————————————————————————\n" +
								"//class: CSC 1302 FALL 2017 \n" +
								"FORMATTING\n" +
								"labels:Tests,Labs,Final\n" +
								"drops:1,0,0 \t//1 test drops, no labs or final drop\n" +
								"weights:.3,.4,.3 \t//test at 0.3, labs at 0.4, final at 0.3\n" +
								"letters:A+,A,B,C,D,F \t\t//6 grade letter categories\n" +
								"cutoffs:90,84.0,78.5,66.5,59.5,0 \t//6 cutoffs for the grade letter categories \n" +
								"STUDENTS\n" +
								"tom: /this is tom's data ->/ 90,80,40,100,78,66,77,88,*,90,80,90,*,88,*\n" +
								"pam:90,80,60,80,78,66,77,88,*,150,80,90,*,82,*\n" +
								"pat:90,80,50,80,78,66,77,*,90,80,40,*,40,*");
		aboutFlow.getChildren().add(info);
		aboutFlow.getChildren().add(example);
	}
}