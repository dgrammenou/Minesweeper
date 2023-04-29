import java.io.BufferedWriter;

//import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StartWindow2 extends LoadWindow{

	static int grid_dim;
	static int counter;
	static double width;
	static double height;

	static int mines = Integer.parseInt(strArray.get(1));

	

	static int right_clicks = 0;

	static int tries =0;

	static int x_hyper=-1;
	static int y_hyper=-1;

	static int pos_hyper;

	static boolean check_hyper=false;

	static String text ="";

	static Random rand = new Random();

	static GridPane gridpane = new GridPane();

	static Label timelabel = new Label();
    
   static Label num_of_flags = new Label();
	
	static Label num_of_mines = new Label();
	

	public static int time = Integer.parseInt(strArray.get(2));

	public static Timer timer;
	public static void setTimer() {
		App.set_timer=true;
		if(time == Integer.parseInt(strArray.get(2))) {
			timelabel.setText(Integer.toString(time));
		}
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(time > 0 )
				{
					Platform.runLater(() -> {
						timelabel.setText(Integer.toString(time+1));
					});
					System.out.println(time);
					time--;
				}
				else {
					Platform.runLater(() -> {
						timelabel.setText(Integer.toString(time));
						try {
							winner("PC");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lost();

					});
					timer.cancel();
				}
			}
		}, 1000,1000);
	}

	public static int grid_init(){ 
		if (strArray.get(0).equals("1")) {
			grid_dim = 9;
		}
		else {
			grid_dim = 16;
			check_hyper = true;
			pos_hyper =rand.nextInt(grid_dim);
		}
		return grid_dim;
	}

	static int dimensions = grid_init();



	static int [][] mines_grid = new int [dimensions][dimensions];
	static int [][] right_clicked = new int [dimensions][dimensions];
	static boolean [][] left_clicked = new boolean [dimensions][dimensions];
	static Label[][] label = new Label[dimensions][dimensions];
	static boolean [][] flags = new boolean[dimensions][dimensions];



	File txt = new File("medialab/mines.txt");
	static FileWriter filewriter = null;
	static BufferedWriter bufferedwriter = null;

	
	static  double  win_tiles = Math.pow(dimensions,2) -mines;


	public static void create_mines() throws IOException{



		for(int i=0; i<dimensions;i++) {
			for(int j=0;j<dimensions;j++) {
				mines_grid[i][j] = -1;//blank window
				right_clicked[i][j] = 0;
				left_clicked[i][j]=false;

			}
		}
		try {
			filewriter = new FileWriter("medialab/mines.txt");
			bufferedwriter = new BufferedWriter(filewriter);
		}
		catch(Exception e){
			System.out.println("exception is" +e);
		}
		for(int i=0; i<mines;i++) {

			int rand1 = rand.nextInt(dimensions);
			int rand2= rand.nextInt(dimensions);
			while(mines_grid[rand1][rand2]==0) {
				rand1 = rand.nextInt(dimensions);
				rand2= rand.nextInt(dimensions);
			}
			if(check_hyper == true) {
				if(i == pos_hyper) {
					text =String.valueOf(rand1) + "," + String.valueOf(rand2) + "," + "1";
					x_hyper=rand1;
					y_hyper=rand2;
					mines_grid[x_hyper][y_hyper] =-2;//hypermine

				}
				else {
					text = String.valueOf(rand1) + "," + String.valueOf(rand2) + "," + "0";
					mines_grid[rand1][rand2] = 0;//bomb
				}
			}
			else {
				counter++;
				text = String.valueOf(rand1) + "," + String.valueOf(rand2) + "," + "0";
				mines_grid[rand1][rand2] = 0;//bomb

			}

			bufferedwriter.write(text);
			bufferedwriter.newLine();

			if(rand1 >0) {
				update_cells(rand1-1,rand2);
				if(rand2>0) {
					update_cells(rand1-1,rand2-1);
				}
				if(rand2<dimensions-1) {
					update_cells(rand1-1,rand2+1);
				}
			}
			if(rand1<dimensions-1) {
				update_cells(rand1+1,rand2);
				if(rand2<dimensions-1) {
					update_cells(rand1+1,rand2+1);
				}
			}

			if(rand2>0) {
				update_cells(rand1,rand2-1);
				if(rand1<dimensions-1) {
					update_cells(rand1+1,rand2-1);
				}
			}
			if(rand2<dimensions-1) {
				update_cells(rand1,rand2+1);
			}


		}

		bufferedwriter.close();
		ArrayList<String> strArray1 = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("medialab/mines.txt");
		Scanner sc = new Scanner(fis);
		while(sc.hasNextLine())
		{
			strArray1.add(sc.nextLine());
		}
		sc.close();
		//gridpane.setGridLinesVisible(true);
		for(int i=0; i<dimensions;i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0 / dimensions);
			gridpane.getColumnConstraints().add(colConst);
			width= colConst.getPercentWidth() ;
		}
		for(int i=0;i<dimensions;i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0 /dimensions);
			gridpane.getRowConstraints().add(rowConst); 
			height= rowConst.getPercentHeight() ;

		}

		for(int i=0;i<dimensions;i++) {
			for(int j=0;j<dimensions;j++) {
				display_tiles(i,j);
				label[i][j] = new Label(String.valueOf(mines_grid[i][j]));
			}
		}

		num_of_mines.setText(Integer.toString(mines));
		if(right_clicks==0) {
			num_of_flags.setText(Integer.toString(0));
		}
		
		gridpane.setStyle("-fx-background-color:BURLYWOOD");
		SubScene subSceneOne = new SubScene(gridpane,800,540);
		num_of_mines.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 40));
		num_of_flags.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 40));
		timelabel.setFont(Font.font("Cambria Math",FontWeight.EXTRA_BOLD, 40));
	    
		HBox info = new HBox();
	    
	    
	    info.getChildren().addAll(num_of_mines, num_of_flags, timelabel);	
	    info.setSpacing(267);
		info.setAlignment(Pos.BOTTOM_CENTER);
		info.setStyle("-fx-background-color:BURLYWOOD");
		
		SubScene labels = new SubScene(info,800,60);
		VBox boxroot = new VBox();
		boxroot.setAlignment(Pos.CENTER);
		boxroot.getChildren().addAll(labels,subSceneOne,App.layout1);
       
		App.scene= new Scene(boxroot, 800, 620);
		setTimer();

		
	}

	public static void update_cells(int x, int y) {
		if(mines_grid[x][y]!=0 && mines_grid[x][y]!=-2) {
			if(mines_grid[x][y]!=-1) {
				mines_grid[x][y]++;
			}
			else {
				mines_grid[x][y]=1;
			}
		}
	}

	public static void display_tiles(int row, int col) {
		Pane tile = new Pane();
		HBox hbox =new HBox();
		Image image =new Image("file:images/tile.png");
		ImageView tile1 = new ImageView();
		tile1.setFitWidth(width*8);
		tile1.setFitHeight(height*5.4);
		tile1.setImage(image);
		hbox.getChildren().add(tile1);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		tile.getChildren().add(hbox);
		tile.setOnMouseClicked(e-> {
			if (e.getButton().equals(MouseButton.SECONDARY)) {
				right_click(row,col,tile);
			}
			else {
				left_click(row,col,tile);
			}
		});
		//gridpane.setGridLinesVisible(false);
		gridpane.add(tile, col, row);
	}

	public static void mark_flags(int row, int col, Pane tile) {

		if(left_clicked[row][col]==false) {
			System.out.println("im here");
			
			if(right_clicks<mines) {
				
				right_clicked[row][col]++;
				System.out.println(right_clicked[row][col]);
				if(right_clicked[row][col]%2!=0) {
					right_clicks++;
					System.out.println(right_clicks);
					flag(tile);
					num_of_flags.setText(Integer.toString(right_clicks));


				}
				else {
					right_clicks--;
					if(right_clicks<0) {
						right_clicks=0;
					}
					System.out.println(right_clicks);
					tile.getChildren().clear();
					display_tiles(row,col);
					num_of_flags.setText(Integer.toString(right_clicks));

				}
			}
			else {

				if(right_clicked[row][col]%2!=0){
					right_clicked[row][col]++;
					right_clicks--;
					if(right_clicks<0) {
						right_clicks=0;
					}
					num_of_flags.setText(Integer.toString(right_clicks));
					System.out.println(right_clicks);
					tile.getChildren().clear();
					display_tiles(row,col);
				}
				else {
					invalid_flags();
				}
			}

		}



	}




	public static void  right_click(int row, int col, Pane tile) {
		if(check_hyper==true) {
			if(row==x_hyper && col==y_hyper) {

				if(tries<=4 && left_clicked[row][col]==false) {
					mark_flags(row,col,tile);
					left_clicked[row][col]=true;
					
					for(int i=0;i<dimensions;i++) {
						if(i!=row && left_clicked[i][col]==false) {
							if(right_clicked[i][col]%2!=0) {
								right_clicks--;
								num_of_flags.setText(Integer.toString(right_clicks));
							}
							left_clicked[i][col] = true;
							//System.out.println(mines_grid[i][col]);
							if(mines_grid[i][col]==0) {
								Image image = new Image("file:images/bomb.png");
								ImageView flag1 = new ImageView();
								flag1.setFitWidth(width*8);
								flag1.setFitHeight(height*5.4);
								flag1.setImage(image);
								gridpane.add(flag1, col, i);
							}
							
							else {
								win_tiles--;
								//System.out.println(win_tiles);
								gridpane.add(img(label[i][col].getText()), col, i);
							}

						}
					}
					for(int i=0;i<dimensions;i++) {
						if(i!=col && left_clicked[row][i]==false) {
							if(right_clicked[row][i]%2!=0) {
								right_clicks--;
								num_of_flags.setText(Integer.toString(right_clicks));
							}
							left_clicked[row][i] =true;
							if(mines_grid[row][i]==0) {
								Image image = new Image("file:images/bomb.png");
								ImageView flag1 = new ImageView();
								flag1.setFitWidth(width*8);
								flag1.setFitHeight(height*5.4);
								flag1.setImage(image);
								gridpane.add(flag1, i, row);
							}
							else {
								win_tiles--;
								System.out.println(win_tiles);
								gridpane.add(img(label[row][i].getText()), i, row);
							}

						}
					}
				}



			else {
				mark_flags(row, col, tile);
			}
		}
			else {
				mark_flags(row, col, tile);
			}

		}
		else {
			//System.out.println("im here");
			mark_flags(row, col, tile);


		}
	}




	public static void left_click(int row, int col, Pane tile) {
		if(left_clicked[row][col]==false) {
			if(right_clicked[row][col]%2!=0 && mines_grid[row][col]!=-1) {
				right_clicks--;
				tile.getChildren().clear();
				display_tiles(row,col);
				num_of_flags.setText(Integer.toString(right_clicks));
				if(mines_grid[row][col]==0 || mines_grid[row][col]==-2) {
					try {
						winner("PC");
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					Image image = new Image("file:images/bomb.png");
					ImageView flag1 = new ImageView();
					flag1.setFitWidth(width*8);
					flag1.setFitHeight(height*5.4);
					flag1.setImage(image);
					gridpane.add(flag1, col, row);
					lost();
				}
				else if(mines_grid[row][col]>0) {
                    win_tiles--;
                    gridpane.add(img(label[row][col].getText()), col, row);
					if(win_tiles == 0.0) {

						try {
							winner("Player");
						} catch (IOException e) {
							e.printStackTrace();
						}
						win();
					}
					
					System.out.println(win_tiles);
					
				}
				else {
					if(left_clicked[row][col]==false) {
						blank_tiles(row,col,tile);
					}
				}
			}
			else {
				
				if(mines_grid[row][col]==0 || mines_grid[row][col]==-2) {
					try {
						winner("PC");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mine(tile);
					
				}
				else if(mines_grid[row][col]>0) {
					win_tiles--;
					gridpane.add(img(label[row][col].getText()), col, row);
                    if(win_tiles == 0.0) {
	                    
						try {
							winner("Player");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						win();
					}
					System.out.println(win_tiles);
					//gridpane.add(img(label[row][col].getText()), col, row);
				}
				else {
					if(left_clicked[row][col]==false) {
						blank_tiles(row,col,tile);
					}
				}

			}


		}
		left_clicked[row][col]=true;
		tries++;

	}



	public static void blank_tiles(int row,int col, Pane tile) {
		if(row >= 0 && row<=dimensions-1 && col>=0 && col<=dimensions-1) {
			if (left_clicked[row][col] == false) {

				left_clicked[row][col] = true;
				if(mines_grid[row][col]!=0) {
					if(right_clicked[row][col]%2!=0) {
						right_clicks--;
						num_of_flags.setText(Integer.toString(right_clicks));
						tile.getChildren().clear();
						//display_tiles(row,col);

					}
					win_tiles--;
					left_clicked[row][col] = true;
					gridpane.add(img(label[row][col].getText()), col, row);
					if(win_tiles ==0.0) {

						try {
							winner("Player");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						win();
						
					}
					
				}
				if (mines_grid[row][col] == -1) {
						System.out.println(row);
						System.out.println(col);
						
					blank_tiles(row-1,col-1,tile);
					blank_tiles(row-1,col,tile);
					blank_tiles(row,col+1,tile);
					blank_tiles(row,col-1,tile);
					blank_tiles(row+1,col,tile);
					blank_tiles(row+1,col-1,tile);
					blank_tiles(row-1,col+1,tile);
					blank_tiles(row+1,col+1,tile);
				}else {return;}
			}else {return;}
		}else {return;}
	}

	public static void flag(Pane tile) {
		Image image = new Image("file:images/flag.png");
		ImageView flag1 = new ImageView();
		flag1.setFitWidth(width*8);
		flag1.setFitHeight(height*5.4);
		flag1.setImage(image);
		tile.getChildren().add(flag1);

	}

	public static void mine(Pane tile) {
		Image image = new Image("file:images/bomb.png");
		ImageView flag1 = new ImageView();
		flag1.setFitWidth(width*8);
		flag1.setFitHeight(height*5.4);
		flag1.setImage(image);
		tile.getChildren().add(flag1);
		lost();

	}

	public static ImageView img(String label) {
		Image image = new Image("file:images/"+ label +"_number4.png");
		ImageView flag1 = new ImageView();
		flag1.setFitWidth(width*8);
		flag1.setFitHeight(height*5.4);
		flag1.setImage(image);
		return flag1;
	}



	public static void invalid_flags() {
		//check_flags=true;
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Too many flags");
		alert.setHeaderText("You've reached maximum number of marked tiles");
		alert.setContentText("Please unmark a tile or you can only use left-click");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			alert.close();
		}
	}

	public static void solution() throws IOException {
		for (int i=0; i<dimensions; i++) {
			for(int j=0;j<dimensions;j++) {
				if(left_clicked[i][j]==false) {
					if(mines_grid[i][j]==0 || mines_grid[i][j]==-2) {
						Image image = new Image("file:images/bomb.png");
						ImageView flag1 = new ImageView();
						flag1.setFitWidth(width*8);
						flag1.setFitHeight(height*5.5);
						flag1.setImage(image);
						gridpane.add(flag1, j, i);
					}
					left_clicked[i][j]=true;
				}
			}
		}
		timer.cancel();
		winner("PC");
		solution_alert();
	}



	public static void winner(String winner1) throws IOException {
		//ArrayList<String> rounds1 = new ArrayList<String>();

		FileWriter filewriter1 = null;
		BufferedWriter bufferedwriter1 = null;

		int given_time = Integer.parseInt(strArray.get(2));
		int remain_time = Integer.parseInt(timelabel.getText());
		int play_time = given_time - remain_time;

		String text = "Total number of mines:" + Integer.toString(mines) + ", total number of tries:" + Integer.toString(tries) + ", playing time:" + Integer.toString(play_time) + ", winner:" + winner1 + "\n";
		filewriter1 = new FileWriter("medialab/rounds.txt", true);
		bufferedwriter1 = new BufferedWriter(filewriter1);
		bufferedwriter1.write(text);
		bufferedwriter1.close();

	}






	public static void lost() {
		timer.cancel();
		//winner("PC");
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("You chose a mine...");
		alert.setHeaderText("You lost...");
		alert.setContentText("Make another try!");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result= alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}

	public static void win() {
		timer.cancel();

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("You didn't choose a mine...");
		alert.setHeaderText("Congratulations!");
		alert.setContentText("You won!");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result= alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}
	
	public static void solution_alert() {
		timer.cancel();

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Showing the solution....");
		alert.setHeaderText("You revealed all mines...");
		alert.setContentText("Try again!");
		DialogPane dialogpane = alert.getDialogPane();
		dialogpane.setStyle("-fx-base:LIGHTYELLOW;-fx-font-style:italic;-fx-font-size:15;");
		Optional<ButtonType> result= alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}

}

















