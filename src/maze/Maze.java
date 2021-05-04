package maze;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Class used to store the maze as a 2d arraylist of tiles
 *  @author Yashpal Sangha
 *  @version 29th April 2020
 */
public class Maze{
	// attributes
	private Tile entrance = null;
	private Tile exit = null;
	private List<List<Tile>> tiles;

	/** Constructor initialises the empty tiles 2d arraylist
	 */
	private Maze(){
		tiles = new ArrayList<List<Tile>>();
	}

	/** Creates maze object from a text file
	 *  @param 	file: The path of the file containing the maze
	 *  @return Returns the maze object that was made from the text file
	 */
	public static Maze fromTxt(String file) throws InvalidMazeException, NoEntranceException, NoExitException, RaggedMazeException{
		String line;
		Maze maze = new Maze();
		try(
			FileReader mazeFile = new FileReader(file);
			BufferedReader stream = new BufferedReader(mazeFile);
			) {
				while((line = stream.readLine()) != null){
					ArrayList<Tile> row = new ArrayList<Tile>();
					char[] arr = line.toCharArray();
					for(char character:arr){
						if (character == 'e'){
							Tile entrance = Tile.fromChar(character);
							maze.setEntrance(entrance);
							row.add(entrance);
						} else if(character == 'x'){
							Tile exit = Tile.fromChar(character);
							maze.setExit(exit);
							row.add(exit);
						} else{
							row.add(Tile.fromChar(character));
						}
					}
				maze.tiles.add(row);
				}
				maze.getEntrance();
				maze.getExit();
				maze.raggedMazeCheck();
				return maze;
		
		}catch(FileNotFoundException e) {
			System.out.println("\nNo file was read");
			throw new InvalidMazeException("\nNo file was read");
		}catch(IOException e) {
			System.out.println("\nThere was a problem reading the file");
			throw new InvalidMazeException("\nThere was a problem reading the file");
		}catch(MultipleEntranceException e) {
			System.out.println("\nThere are multiple entrances in the maze");
			throw new InvalidMazeException("\nThere are multiple entrances in the maze");
		}catch(MultipleExitException e) {
			System.out.println("\nThere are multiple exits in the maze");
			throw new InvalidMazeException("\nThere are multiple exits in the maze");
		}catch(NoEntranceException e) {
			System.out.println("\nThere are no entrances in the maze");
			throw new NoEntranceException("\nThere are no entrances in the maze");
		}catch(NoExitException e) {
			System.out.println("\nThere are no exits in the maze");
			throw new NoExitException("\nThere are no exits in the maze");
		}catch(RaggedMazeException e) {
			System.out.println("\nThe maze is ragged");
			throw new RaggedMazeException("\nThe maze is ragged");
		}
	}

	/** 
	 *  
	 */
	private void raggedMazeCheck() throws RaggedMazeException {
		int size = tiles.get(0).size();
		for (List row:tiles){
			if (row.size() != size){
				throw new RaggedMazeException("\nMaze is ragged");
			}
		}
	}

	/** 
	 *  
	 */
	public Tile getAdjacentTile(Tile tile, Direction direction){
		Maze.Coordinate currentCoord = this.getTileLocation(tile);
		if (direction.equals(Direction.NORTH)) {
			Maze.Coordinate newCoord = this.new Coordinate(currentCoord.getX(), currentCoord.getY()+1);
			return this.getTileAtLocation(newCoord);
		}
		if (direction.equals(Direction.EAST)) {
			Maze.Coordinate newCoord = this.new Coordinate(currentCoord.getX()+1, currentCoord.getY());
			return this.getTileAtLocation(newCoord);
		}
		if (direction.equals(Direction.SOUTH)) {
			Maze.Coordinate newCoord = this.new Coordinate(currentCoord.getX(), currentCoord.getY()-1);
			return this.getTileAtLocation(newCoord);
		}
		Maze.Coordinate newCoord = this.new Coordinate(currentCoord.getX()-1, currentCoord.getY());
		return this.getTileAtLocation(newCoord);
	}

	/** 
	 *  
	 */
	public Tile getEntrance()throws NoEntranceException {
		if (entrance == null) throw new NoEntranceException("\nThere are no entrances in the maze");
		return entrance;
	}

	/** 
	 *  
	 */
	public Tile getExit()throws NoExitException {
		if (exit == null) throw new NoExitException("\nThere are no exits in the maze");
		return exit;
	}

	/** 
	 *  
	 */
	public Tile getTileAtLocation(Coordinate coord){
		return tiles.get(tiles.size()-1-coord.getY()).get(coord.getX());
	}

	/** 
	 *  
	 */
	public Coordinate getTileLocation(Tile tile){
		int x, y;
		x = y = 0;
		for(int i = 0; i < tiles.size(); i++){
            for(int j = 0; j < tiles.get(i).size(); j++){
            	if (tiles.get(i).get(j) == tile){
            		x = j;
            		y = tiles.size() - 1 - i;
            	}
            }
        }
		return this.new Coordinate(x,y);
	}

	/** 
	 *  
	 */
	public List<List<Tile>> getTiles(){
		return tiles;
	}

	/** 
	 *  
	 */
	private void setEntrance(Tile tile) throws MultipleEntranceException {
		if (entrance == null){
			entrance = tile;
		} else {
			throw new MultipleEntranceException("Maze contains multiple entrances");
		}
	}

	/** 
	 *  
	 */
	private void setExit(Tile tile) throws MultipleExitException {
		if (exit == null){
			exit = tile;
		} else {
			throw new MultipleExitException("Maze contains multiple entrances");
		}
	}

	/** 
	 *  
	 */
	public String toString(){
		String result = "";
        for(int i = 0; i < tiles.size(); i++){
        	result += Integer.toString(tiles.size()-1-i) + "   ";
            for(int j = 0; j < tiles.get(i).size(); j++){
                result += tiles.get(i).get(j) + " ";
            }
            result += "\n";
        }
        result += "\n    ";
        for(int j = 0; j < tiles.get(0).size(); j++){
            result += Integer.toString(j) + " ";
        }
        return result;
	}

	/** 
	 *  
	 */
	public class Coordinate{
		private int x;
		private int y;

		/** 
		 *  
		 */
		public Coordinate(int x, int y){
			this.x = x;
			this.y = y;
		}

		/** 
		 *  
		 */
		public int getX(){
			return x;
		}

		/** 
		 *  
		 */
		public int getY(){
			return y;
		}

		/** 
		 *  
		 */
		public String toString(){
			return "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
		}
	}

	/** 
	 *  
	 */
	public enum Direction{
		NORTH, SOUTH, EAST, WEST;
	}
}