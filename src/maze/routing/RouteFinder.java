package maze.routing;

import maze.*;
import java.util.*;


public class RouteFinder{
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

	public boolean isFinished() {
		try{
			if (route.peek() == maze.getExit()){
				return true;
			}
		}catch(NoExitException e) {
			System.out.println("\nThere are no exits in the maze");
		}
		return false;
	}

	//public static RouteFinder load(String file) {}

	//public void save(String file) {}

	public boolean step() throws NoRouteFoundException {
		if (!finished) {
			if (maze.getTileLocation(route.peek()).getX() != maze.getTiles().get(0).size() - 1
				&& maze.getAdjacentTile(route.peek(), Maze.Direction.EAST).isNavigable()
				&& !visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST))) {
				if (route.search(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST)) == -1){
					route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST));
					if (this.isFinished()){
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
					if (this.isFinished()){
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
					if (this.isFinished()){
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
					if (this.isFinished()){
						return true;
					} else{
						return false;
					}
				}
			}
			try{
				if (route.peek() == maze.getEntrance()){
					throw new NoRouteFoundException("No route found");
				}
			}catch(NoEntranceException e) {
				System.out.println("\nThere is no entrance to the maze");
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