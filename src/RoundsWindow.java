
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;

public class RoundsWindow{
	public static ArrayList<String> rounds_arr = new ArrayList<String>();
	public static void readrounds() {

		try
		{
			FileInputStream fis = new FileInputStream("medialab/rounds.txt");
			Scanner sc = new Scanner(fis);
			while(sc.hasNextLine())
			{
				rounds_arr.add(sc.nextLine());
			}

			sc.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();	
		}


	}

	public static void games(String results) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Last games...");
		alert.setHeaderText("Here are the five last results!");
		alert.setContentText(results);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		alert.showAndWait();
		
	}

	public static void playfirst() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Empty history...");
		alert.setHeaderText("There are no games played!");
		alert.setContentText("You have to play first!");
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		alert.showAndWait();
		
	}



	public static void popup() {

		readrounds();
		String text ="";
		if(rounds_arr.size()<5) {

			for (int i=0;i<rounds_arr.size();i++) {
				
				text = text + rounds_arr.get(i) +"\n";
			}
		}
		else {
			for(int i=rounds_arr.size()-5;i<rounds_arr.size();i++) {
				text = text + rounds_arr.get(i) +"\n";
				
			}
		}
		
		if(text.equals("")) {
			playfirst();
		}
		else {
			games(text);
		}		

	}
}
