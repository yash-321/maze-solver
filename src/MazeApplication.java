import maze.*;
import maze.routing.*;
import maze.visualisation.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

public class MazeApplication extends Application {

	Maze maze = null;
	RouteFinder solver = null;
	GridPane mazeGrid = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		try{
	    	maze = Maze.fromTxt("../resources/mazes/maze1.txt");
	    	solver = new RouteFinder(maze);
			// Hbox for load map
			Label label1 = new Label("File:");
			TextField mapTxt = new TextField();
			Label txtLbl = new Label(".txt");
			Button mapBtn = new Button("Load Map");
			HBox hb1 = new HBox();
			hb1.getChildren().addAll(label1, mapTxt, txtLbl, mapBtn);
			hb1.setSpacing(20);

			// Hbox for load route
			Label label2 = new Label("File:");
			TextField loadTxt = new TextField();
			Label serLbl1 = new Label(".ser");
			Button loadBtn = new Button("Load Route");
			HBox hb2 = new HBox();
			hb2.getChildren().addAll(label2, loadTxt, serLbl1, loadBtn);
			hb2.setSpacing(20);

			// Hbox for save route
			Label label3 = new Label("File:");
			TextField saveTxt = new TextField();
			Label serLbl2 = new Label(".ser");
			Button saveBtn = new Button("Save Route");
			HBox hb3 = new HBox();
			hb3.getChildren().addAll(label3, saveTxt, serLbl2, saveBtn);
			hb3.setSpacing(20);

			// Vbox for maze
			double x = 500;
			double sqr = x/solver.getMaze().getTiles().get(0).size();
			double y = sqr*solver.getMaze().getTiles().size() + 200;
			mazeGrid = DisplayMaze.go(solver);

			Button step = new Button("Step");
			VBox mazeBox = new VBox();
			mazeBox.getChildren().addAll(mazeGrid, step);
			mapBtn.setOnAction(e -> {
				try{
					maze = Maze.fromTxt("../resources/mazes/" + mapTxt.getText() + ".txt");
					solver = new RouteFinder(maze);
					mazeGrid = DisplayMaze.go(solver);
					mazeBox.getChildren().clear();
					mazeBox.getChildren().addAll(mazeGrid, step);
					step.setVisible(true);
				}catch(FileNotFoundException f){
			    	AlertBox.display("No file was read");
				}catch(IOException f){
			    	AlertBox.display("There was a problem reading the file");
				}catch(MultipleEntranceException f) {
					AlertBox.display("There are multiple entrances in the maze");
				}catch(MultipleExitException f) {
					AlertBox.display("There are multiple exits in the maze");
				}catch(NoEntranceException f) {
					AlertBox.display("There are no entrances in the maze");
				}catch(NoExitException f) {
					AlertBox.display("There are no exits in the maze");
				}catch(RaggedMazeException f) {
					AlertBox.display("The maze is ragged");
				}catch(InvalidMazeException f){
			    	AlertBox.display("Something is wrong with the maze");
				}
			});
			saveBtn.setOnAction(e -> {
				try{
					solver.save("../resources/routes/" + saveTxt.getText() + ".ser");
					AlertBox.display("Route saved in ../resources/routes/" + saveTxt.getText() + ".ser");
				} catch(IOException f) {
					AlertBox.display("There was a problem saving the route");
				}
			});
			loadBtn.setOnAction(e -> {
				try{
					solver = RouteFinder.load("../resources/routes/" + loadTxt.getText() + ".ser");
					maze = solver.getMaze();
					mazeGrid = DisplayMaze.go(solver);
					mazeBox.getChildren().clear();
					mazeBox.getChildren().addAll(mazeGrid, step);
				}catch(ClassNotFoundException f){
			    	AlertBox.display("RouteFinder class not found");
				}catch(IOException f){
			    	AlertBox.display("There was a problem reading the file");
				}
			});
			step.setOnAction(e -> { 
				GridPane mazeUpdate = Step.update(solver);
				if (mazeUpdate != null) {
					mazeBox.getChildren().clear();
					mazeBox.getChildren().addAll(mazeUpdate, step);
				}
				if (solver.isFinished()) {
					step.setVisible(false);
					mazeBox.getChildren().add(new Label("The maze has been solved!"));
				}
			});

			VBox root = new VBox();
			root.setSpacing(10);
			root.setAlignment(Pos.CENTER);
			root.getChildren().addAll(hb1, hb2, hb3, mazeBox);
			Scene scene = new Scene(root, x, y);

			stage.setScene(scene);
			stage.setTitle("Maze Solver");

			stage.show();

	    }catch(InvalidMazeException e){
	    	System.out.println("\nThere is something wrong with the maze");
	    }
		
	}
}
