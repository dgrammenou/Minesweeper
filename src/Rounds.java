import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class Rounds extends LoadWindow{
	public static ArrayList<String> rounds_arr = new ArrayList<String>();
	public static void readrounds(String filename) {
		
		String rounds_file = filename + "txt";
		try
	       {
	    	 //System.out.println("here");
	    	 FileInputStream fis = new FileInputStream(rounds_file);
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
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result= alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Platform.exit();
			}
}

public static void popup() {
	readrounds("rounds");
	 String text ="";
	for (int i=0;i<5;i++) {
		text = text + rounds_arr.get(i) +"\n";
	}
	games(text);
			
	
}
}
