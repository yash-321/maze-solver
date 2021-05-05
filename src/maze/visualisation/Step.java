package maze.visualisation;
import maze.*;
import maze.routing.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

public class Step {

	public static GridPane update(RouteFinder solver) {
		GridPane updatedGrid = null;
		boolean done;

		try{
			done = solver.step();
		} catch(NoRouteFoundException e) {
			return updatedGrid;
		}

		if (!done) {
			updatedGrid = DisplayMaze.go(solver);
			return updatedGrid;
		} else{
			return updatedGrid;
		}

	}
}