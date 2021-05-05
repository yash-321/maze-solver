package maze.routing;

import maze.*;
import java.util.*;
import java.io.*;

public class RouteFinder implements Serializable {
	private Maze maze;
	private Stack<Tile> route = new Stack<Tile>();
	private Boolean finished = false;
	private List<Tile> visited = new ArrayList<Tile>();
	

	public RouteFinder(Maze maze) {
		this.maze = maze;
		try{
			this.route.push(maze.getEntrance());	
		} catch(NoEntranceException e) {
			System.out.println("\nThere are no entrances in the maze");
		}
	}

	public Maze getMaze() {
		return maze;
	}

	public List<Tile> getRoute() {
		return route;
	}

	public List<Tile> getVisited() {
		return visited;
	}

	public boolean isFinished() {
		return finished;
	}

	public static RouteFinder load(String file) throws FileNotFoundException, ClassNotFoundException {
		RouteFinder load = null;
		try {
			FileInputStream myFileInputStream = new FileInputStream(file);
		    ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
		    load = (RouteFinder) myObjectInputStream.readObject(); 
		    myObjectInputStream.close();
		    return load;
		}
		catch (IOException e) {
		    System.out.println("Error when loading file.");
		    throw new FileNotFoundException("File not found");
		} 
		catch (ClassNotFoundException c) {
        	System.out.println("RouteFinder class not found");
        	throw new FileNotFoundException("RouteFinder class not found");
     	}
	}

	public void save(String file) throws IOException {
		try {
		   FileOutputStream myFileOutputStream = new FileOutputStream(file);
		   ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
		   myObjectOutputStream.writeObject(this);
		   myObjectOutputStream.close();
		}
		catch (IOException e) {
		    System.out.println("Error when saving to file. " + e);
		    throw new IOException("Error when saving to file.");
		}
	}

	public boolean step() throws NoRouteFoundException {
		if (!finished) {
			if (maze.getTileLocation(route.peek()).getX() != maze.getTiles().get(0).size() - 1
				&& maze.getAdjacentTile(route.peek(), Maze.Direction.EAST).isNavigable()
				&& !visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST))) {
				if (route.search(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST)) == -1){
					route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST));
					if (route.peek().getType().equals(Tile.Type.EXIT)){
						finished = true;
						return true;
					} else{
						return false;
					}
				}
			}
			if (maze.getTileLocation(route.peek()).getY() != 0
				&& maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH).isNavigable()
				&& !visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH))) {
				if (route.search(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH)) == -1){
					route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH));
					if (route.peek().getType().equals(Tile.Type.EXIT)){
						finished = true;
						return true;
					} else{
						return false;
					}
				}
			}
			if (maze.getTileLocation(route.peek()).getX() != 0
				&& maze.getAdjacentTile(route.peek(), Maze.Direction.WEST).isNavigable()
				&& !visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST))) {
				if (route.search(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST)) == -1){
					route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST));
					if (route.peek().getType().equals(Tile.Type.EXIT)){
						finished = true;
						return true;
					} else{
						return false;
					}
				}
			}
			if (maze.getTileLocation(route.peek()).getY() != maze.getTiles().size() - 1
				&& maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH).isNavigable()
				&& !visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH))) {
				if (route.search(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH)) == -1){
					route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH));
					if (route.peek().getType().equals(Tile.Type.EXIT)){
						finished = true;
						return true;
					} else{
						return false;
					}
				}
			}
			if (route.peek().getType().equals(Tile.Type.ENTRANCE)){
				throw new NoRouteFoundException("No route found");
			}
			visited.add(route.pop());
			return false;
		} else{
			return true;
		}
	}

	public String toString() {
		String result = "";
        for(int i = 0; i < maze.getTiles().size(); i++){
        	result += Integer.toString(maze.getTiles().size()-1-i) + "   ";
            for(int j = 0; j < maze.getTiles().get(i).size(); j++){
            	if (visited.contains(maze.getTiles().get(i).get(j))){
            		result += "- ";
            	} else if (route.contains(maze.getTiles().get(i).get(j))){
            		result += "* ";
            	}else {
                	result += maze.getTiles().get(i).get(j) + " ";
            	}
            }
            result += "\n";
        }
        result += "\n    ";
        for(int j = 0; j < maze.getTiles().get(0).size(); j++){
            result += Integer.toString(j) + " ";
        }
        return result;
	}
}