import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class App extends Application{


	public  static BorderPane layout1 = new BorderPane();
	
	static boolean set_timer=false;
	
	static boolean load_window = false;

	public static Scene scene;

	public void start(Stage stage) throws IOException{

		MenuBar menubar = new MenuBar();
		menubar.setStyle("-fx-base:CORNSILK;-fx-selection-bar:WHEAT");
		
		Menu Application = new Menu("");
		Label app = new Label("Application");
		app.setStyle("-fx-text-fill: black;");
		app.setFont(Font.font("Cambria Math",FontWeight.SEMI_BOLD, 15));
		Application.setGraphic(app);

		Menu details = new Menu("");
		Label det =new Label("Details");
		det.setStyle("-fx-text-fill: black;");
		det.setFont(Font.font("Cambria Math",FontWeight.SEMI_BOLD, 15));
		details.setGraphic(det);

		layout1.setBottom(menubar);

		menubar.getMenus().addAll(Application,details);

		MenuItem Create = new MenuItem("");
		Label create = new Label("Create");
		create.setStyle("-fx-text-fill: black;");
		create.setFont(Font.font("Arial",FontWeight.SEMI_BOLD, 13));
		Create.setGraphic(create);

		Create.setOnAction(event ->{
			CreateWindow.popup();
		});


		MenuItem Load = new MenuItem("");
		Label load = new Label("Load");
		load.setStyle("-fx-text-fill: black;");
		load.setFont(Font.font("Arial",FontWeight.SEMI_BOLD, 13));
		Load.setGraphic(load);

		Load.setOnAction(event ->{
			LoadWindow.load_popup();

		});

		MenuItem Start = new MenuItem("");
		Label start = new Label("Start");
		start.setStyle("-fx-text-fill: black;");
		start.setFont(Font.font("Arial",FontWeight.SEMI_BOLD, 13));
		Start.setGraphic(start);

		Start.setOnAction(event ->{
			try {
				if(load_window ==true) {
				StartWindow2.create_mines();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
				}
				else {
					load_alert();
				}


			} catch (IOException e) {
				
				e.printStackTrace();
			}

		});

		MenuItem Exit = new MenuItem("");
		Label exit = new Label("Exit");
		exit.setStyle("-fx-text-fill: black;");
		exit.setFont(Font.font("Arial",FontWeight.SEMI_BOLD, 13));
		Exit.setGraphic(exit);

		Exit.setOnAction(event ->{
			if(set_timer==true) {
				StartWindow2.timer.cancel();
				}
			Platform.exit();
			
		});

		Application.getItems().addAll(Create, Load, Start, Exit);


		MenuItem Rounds = new MenuItem("");
		Label rounds = new Label("Rounds");
		rounds.setStyle("-fx-text-fill: black;");
		rounds.setFont(Font.font("Arial",FontWeight.SEMI_BOLD, 13));
		Rounds.setGraphic(rounds);

		Rounds.setOnAction(event ->{
			RoundsWindow.popup();
			
		});




		MenuItem Solution = new MenuItem("");
		Label solution = new Label("Solution");
		solution.setStyle("-fx-text-fill: black;");
		solution.setFont(Font.font("Arial",FontWeight.SEMI_BOLD, 13));
		Solution.setGraphic(solution);

		Solution.setOnAction(event ->{
			try {
				if(set_timer==true) {
				StartWindow2.solution();
				}
				else {
					start_alert();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});


		details.getItems().addAll(Rounds, Solution);
        
		 GridPane mine_image = new GridPane();
	     Image image = new Image("file:images/minesweeper2.png");
	     ImageView imageView = new ImageView();
	      
	     imageView.setImage(image);
	     
	     imageView.setFitHeight(585);
	     imageView.setFitWidth(800);
	     mine_image.getChildren().add(imageView);
	     SubScene minesweeper = new SubScene(mine_image,800,585);
	     
	    VBox initial = new VBox();
	    initial.getChildren().addAll(minesweeper,layout1);

		scene = new Scene(initial,800,610);
		layout1.setStyle("-fx-background-color:BURLYWOOD");
		stage.setTitle("MediaLab Minesweeper");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();


	}
	
	public static void load_alert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Failed to start game...");
		alert.setHeaderText("There is no scenario loaded!");
		alert.setContentText("You have to load a scenario first!");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			alert.close();
		}
	}
	
	public static void start_alert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Failed to show solution..");
		alert.setHeaderText("There is no game played..");
		alert.setContentText("You have to start a game first!");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			alert.close();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}

}
