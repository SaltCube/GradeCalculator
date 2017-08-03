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
		Text author = new Text("Author: SaltCube\n");
		Text info = new Text("* labels: drops: weights: letters: cutoffs: must be specified before the scores\n" +
							 "each on its own line with \",\" separating values including the last value.\n" +
							 "These lines can be any order\n" +
							 "* StudentReport portions must end with a *, including at the end.\n" +
							 "* First line with points is assumed to supply the correct number of values for each portion. You can use a dummy record for this, if you prefer. \n" +
							 "* Each grade value and the * are separated by a comma.\n" +
							 "* Weights are typed as decimal. Ex: .3 for 30%.\n" +
							 "* No extraneous characters at the end is allowed other than a \",\" or \",*\".\n" +
							 "* Comment lines begin with // and can be any where.\n" +
							 "* Blank lines can appear anywhere. No blank characters mixed with data values.\n" +
							 "* At the end of regular data you can add as many extra credits as you want\n" +
							 "* Sample data file is shown below\n" +
							 "* Warnings are not necessarily errors. You have to decide on those.\n" +
							 "* Totals and individual scores can be > 100, if you choose to.\n" +
							 "* Weights do not have to add up to 100, if you choose to.\n" +
							 "* Nothing is saved automatically.\n" +
							 "* Blanks are allowed except at the beginning and end of any line.\n");
		Text example = new Text("//___________________________________________________________\n" +
								"// class: CSCI 1234 FALL 2002 \n" +
								"labels:Tests,Labs,Final,\n" +
								"drops:1,0,0,\n" +
								"weights:.3,.4,.3,\n" +
								"letters:A+,A,B,C,D,F,\n" +
								"cutoffs:90,84.0,78.5,66.5,59.5,0,\n" +
								"//data or dummy record can go here \n" +
								"tom:90,80,40,100,78,66,77,88,*,90,80,90,*,88,*\n" +
								"pam:90,80,60,80,78,66,77,88,*,150,80,90,*,82,*\n" +
								"pat:90,80,50,80,78,66,77,*,90,80,40,*,40,*");
		aboutFlow.getChildren().add(author);
		aboutFlow.getChildren().add(info);
		aboutFlow.getChildren().add(example);
	}
}