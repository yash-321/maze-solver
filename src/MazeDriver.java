import maze.*;
import maze.routing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MazeDriver {
    public static void main(String args[]) {
    	Scanner myObj = new Scanner(System.in);
    	while (true){
    		System.out.println("Enter name of maze file in resource folder(Ctrl+C to exit): ");
	    	String input = myObj.nextLine();
	    	try{
	    		Maze maze = Maze.fromTxt("../resources/mazes/" + input);
	    		System.out.println(maze.toString());

	    		try{
	    			RouteFinder solver = new RouteFinder(maze);
	    			solver.toString();
	    			while (!solver.isFinished()){
	    				System.out.println("Press <enter>: ");
	    				String enter = myObj.nextLine();
	    				solver.step();
	    				System.out.println(solver.toString());
	    			}

	    		} catch(NoRouteFoundException e){
	    			System.out.println("\nThere was a problem solving the maze");
	    		}

	    	}catch(InvalidMazeException e){
	    		System.out.println("\nThere is something wrong with the maze");
	    	}
		}
    }
}
