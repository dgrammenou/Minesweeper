import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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

public class CreateWindow {
	public static void popup() {
		Stage popupwindow =new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Create a scenario!");


		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		pane.setHgap(5);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(0,0,0,0));

		TextField textField1 = new TextField();
		textField1.setPrefWidth(150);
		textField1.setPromptText("Create a filename..");
		textField1.setStyle("-fx-prompt-text-fill:gray");
		textField1.setFocusTraversable(false);
		textField1.setStyle("-fx-text-box-border: LIGHTYELLOW ;-fx-focus-color:LIGHTYELLOW");

	

		TextField textField3 = new TextField();
		textField3.setPrefWidth(150);
		textField3.setStyle("-fx-text-box-border:LIGHTYELLOW;-fx-focus-color:LIGHTYELLOW");
		textField3.setPromptText("Enter mines...");
		textField3.setStyle("-fx-prompt-text-fill:gray");
		textField3.setFocusTraversable(false);
		textField3.setStyle("-fx-text-box-border:LIGHTYELLOW ;-fx-focus-color:LIGHTYELLOW");

		TextField textField4 = new TextField();
		textField4.setPrefWidth(150);
		textField4.setStyle("-fx-text-box-border:LIGHTYELLOW;-fx-focus-color:LIGHTYELLOW");
		textField4.setPromptText("Enter seconds...");
		textField4.setStyle("-fx-prompt-text-fill:gray");
		textField4.setFocusTraversable(false);
		textField4.setStyle("-fx-text-box-border:LIGHTYELLOW;-fx-focus-color:LIGHTYELLOW");


		textField1.setPrefColumnCount(14);

		textField3.setPrefColumnCount(14);
		
		textField4.setPrefColumnCount(14);
		

		//create a scenario-id
		Label label1 = new Label("Create game scenario:");
		label1.setStyle("-fx-text-fill:SADDLEBROWN");
		label1.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 15));


		//choose difficulty level
		Label label2 = new Label("Choose a game level:");
		label2.setStyle("-fx-text-fill:SADDLEBROWN");
		label2.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 15));
		ComboBox<String> level = new ComboBox<String>();
		level.setEditable(true);
		

		LevelOptions(level);

		level.setStyle("-fx-text-box-border: LIGHTYELLOW ;-fx-focus-color:LIGHTYELLOW;-fx-selection-bar:WHEAT");

		//choose number of mines
		Label label3 = new Label("Choose game mines:");
		label3.setStyle("-fx-text-fill:SADDLEBROWN");
		label3.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 15));


		//choose time
		Label label4 = new Label("Choose game time:");
		label4.setStyle("-fx-text-fill:SADDLEBROWN");
		label4.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 15));


		//choose if there is hypermine
		Label label5 = new Label("Choose hyper-mine:");
		label5.setStyle("-fx-text-fill:SADDLEBROWN");
		label5.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 15));

		ComboBox<String> hypermine = new ComboBox<String>();
		hypermine.setEditable(true);

		hypermineOptions(hypermine);

		hypermine.setStyle("-fx-text-box-border: LIGHTYELLOW;-fx-focus-color:LIGHTYELLOW;-fx-selection-bar:WHEAT");

		pane.getChildren().addAll(label1,textField1,label2,level,label3,textField3,label4,textField4,label5,hypermine);


		VBox vbox = new VBox();
		Button submit = new Button("Submit!");
		submit.setStyle("-fx-base:BURLYWOOD;-fx-text-box-border: LIGHTYELLOW ;-fx-focus-color:LIGHTYELLOW");
		submit.setOnAction(event -> {
			try {
				if(textField1.getText()==null || level.getValue()==null || textField3.getText()==null || textField4.getText()==null ||hypermine.getValue()==null)
				{
					create_alert();
				}
				else {
				CreateNewFile(textField1, level, textField3, textField4, hypermine);
			}}catch (IOException e) {
				e.printStackTrace();
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

		Scene scene1 = new Scene(borderPane, 300, 300);
		popupwindow.setScene(scene1);
		popupwindow.setResizable(false);
		popupwindow.showAndWait();

	}
	public static void hypermineOptions(ComboBox<String> combo) {
		combo.getItems().add("No");
		combo.getItems().add("Yes");
	}

	public static void LevelOptions(ComboBox<String> combo) {
		combo.getItems().add("1");
		combo.getItems().add("2");
	}

	private static void CreateNewFile(TextField filename,ComboBox<String> level1, TextField mines1, TextField time1, ComboBox<String> hyper1) throws IOException{
		String scenario = filename.getText();
		scenario = "medialab/" + scenario +".txt";
		String game_level = level1.getValue();
		String game_mines = mines1.getText();
		String game_time = time1.getText();
		String game_hyper = hyper1.getValue();
		int hyper = 0;
		if(game_hyper!=null) {
		if(game_hyper.equals("Yes")) {
			hyper = 1;
		}
		}

		String hyper_m = Integer.toString(hyper);

		String game_info = game_level + "\n" + game_mines + "\n" + game_time + "\n" + hyper_m;
		File file = new File(scenario);
		

		FileWriter fWriter = null;
		BufferedWriter writer = null;
		try {
			
			fWriter = new FileWriter(scenario);
			writer = new BufferedWriter(fWriter);
			writer.write(game_info);
			writer.newLine();
			writer.close();
			
		} catch(Exception e) {
			System.out.println("Can't create new scenario!");
		}

	}
	public static void create_alert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("No values inserted..");
		alert.setHeaderText("Can't create new scenario");
		alert.setContentText("You have to complete all fields first!");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			alert.close();
		}
	}
	
}
