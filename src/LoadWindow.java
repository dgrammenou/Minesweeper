import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoadWindow {
	public static ArrayList<String> strArray = new ArrayList<String>();

	public static void load_popup() {
		
		App.load_window = true;

		Stage popupwindow =new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Load a scenario!");


		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		pane.setHgap(5);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(0,0,0,0));

		TextField textField1 = new TextField();
		textField1.setPrefWidth(150);
		textField1.setPromptText("Give a filename..");
		textField1.setStyle("-fx-prompt-text-fill:gray");
		textField1.setFocusTraversable(false);
		textField1.setStyle("-fx-text-box-border:LIGHTYELLOW ;-fx-focus-color:LIGHTYELLOW");

		Label label1 = new Label("Load a game scenario:");
		label1.setStyle("-fx-text-fill:SADDLEBROWN");
		label1.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 15));

		pane.getChildren().addAll(label1, textField1);

		VBox vbox = new VBox();
		Button submit = new Button("Submit!");
		submit.setStyle("-fx-base:BURLYWOOD;-fx-text-box-border: KHAKI ;-fx-focus-color:KHAKI");
		submit.setOnAction(event ->{
			try {
				LoadFile(textField1);
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			popupwindow.close();
		});

		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(submit);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(vbox);
		BorderPane.setAlignment(vbox,Pos.CENTER);
		borderPane.setStyle("-fx-background-color:LIGHTYELLOW");

		Scene scene1 = new Scene(borderPane, 300, 200);
		popupwindow.setScene(scene1);
		popupwindow.setResizable(false);
		popupwindow.showAndWait();

	}
	public static void LoadFile(TextField filename) throws IOException {
		String directory ="medialab/";
		
		String file = filename.getText();
		String file1 = file +".txt";
		boolean check = new File(directory,file1).exists();
		if (check==true) {

			try
			{
				
				FileInputStream fis = new FileInputStream(directory + file1);
				Scanner sc = new Scanner(fis);
				while(sc.hasNextLine())
				{
					strArray.add(sc.nextLine());
				}
				sc.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();	
			}
			
			int len = strArray.size();

			try {
				TestLines lines1 = new TestLines();
				lines1.validate(len); 
			}
			catch(InvalidDescriptionException ex)
			{
				showAlert("input lines");
				System.out.println("Caught the exception");
				System.out.println("Exception occured: " + ex);
			}

			if(len ==4) {
				try {
					System.out.println("arr is" + Integer.parseInt(strArray.get(0)) +"\n" + Integer.parseInt(strArray.get(1)) +"\n" +Integer.parseInt(strArray.get(2)) + "\n" + Integer.parseInt(strArray.get(3)));
					TestInputValues values = new TestInputValues();
					values.validateinput(Integer.parseInt(strArray.get(0)), Integer.parseInt(strArray.get(1)), Integer.parseInt(strArray.get(2)), Integer.parseInt(strArray.get(3)));
				}
				catch(InvalidValueException ex)
				{
					showAlert("input data");
					System.out.println("Caught the exception");
					System.out.println("Exception occured: " + ex);
				}
			}
		}
		else {
			showAlert1();

		}

		
	}
	public static void showAlert(String error) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Invalid input...");
		alert.setHeaderText("Try a valid scenario!");
		alert.setContentText("Your scenario has invalid:"+ error);
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			alert.close();
		}

	}

	public static void showAlert1() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("No such file...");
		alert.setHeaderText("Try another scenario");
		alert.setContentText("There is no such scenario!");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			alert.close();
		}
	}


}

