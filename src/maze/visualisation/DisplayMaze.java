package maze.visualisation;
import maze.*;
import maze.routing.*;
import maze.visualisation.*;
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

public class DisplayMaze {
	
	public static GridPane go(RouteFinder solver) {
		double x = 500;
		double sqr = x/solver.getMaze().getTiles().get(0).size();
		GridPane mazeGrid = new GridPane();
		for(int i = 0; i < solver.getMaze().getTiles().size(); i++) {
			for(int j = 0; j < solver.getMaze().getTiles().get(0).size(); j++) {
				Tile block = solver.getMaze().getTiles().get(i).get(j);
				if (block.getType().equals(Tile.Type.WALL)) {
					mazeGrid.add(new Rectangle(sqr, sqr, Color.BLACK), j, i, 1, 1);
				}
				if (block.getType().equals(Tile.Type.ENTRANCE)) {
					mazeGrid.add(new Rectangle(sqr, sqr, Color.GREEN), j, i, 1, 1);
				}
				if (block.getType().equals(Tile.Type.EXIT)) {
					mazeGrid.add(new Rectangle(sqr, sqr, Color.RED), j, i, 1, 1);
				}
				if (solver.getRoute().contains(block)) {
					mazeGrid.add(new Rectangle(sqr, sqr, Color.GREEN), j, i, 1, 1);
				}
				if (solver.getVisited().contains(block)) {
					mazeGrid.add(new Rectangle(sqr, sqr, Color.GREY), j, i, 1, 1);
				}
				if (block.getType().equals(Tile.Type.EXIT)) {
					mazeGrid.add(new Rectangle(sqr, sqr, Color.RED), j, i, 1, 1);
				}
			}
		}
		return mazeGrid;
	}
}