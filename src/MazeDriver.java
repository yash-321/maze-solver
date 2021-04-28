import maze.*;
import java.util.ArrayList;
import java.util.List;

public class MazeDriver {
    public static void main(String args[]) {
    	try{
    		Maze maze = Maze.fromTxt("../resources/mazes/test.txt");
    		System.out.println(maze.toString());
    	}catch(InvalidMazeException e){
    		System.out.println("\nThere is something wrong with the maze");
    	}
    	
    }
}
