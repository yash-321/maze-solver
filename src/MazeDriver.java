import maze.*;
import java.util.ArrayList;
import java.util.List;

public class MazeDriver {
    public static void main(String args[]) {
    	try{
    		Maze maze = Maze.fromTxt("../resources/mazes/maze1.txt");
    		System.out.println(maze.toString());
    		Tile tile = maze.getTiles().get(3).get(2);
    		System.out.println(tile.getType());
    		System.out.println(maze.getTileLocation(tile).toString());
	    	Tile tile2 = maze.getAdjacentTile(tile, Maze.Direction.WEST);
	    	System.out.println(tile2.getType());
	    	System.out.println(maze.getTileLocation(tile2).toString());


    	}catch(InvalidMazeException e){
    		System.out.println("\nThere is something wrong with the maze");
    	}
    }
}
