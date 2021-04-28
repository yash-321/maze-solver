package maze;

public class Tile{
	private Type type;

	private Tile(Type type){
		this.type = type;
	}

	protected static Tile fromChar(char character) {
		if (character == '.'){
			return new Tile(Type.CORRIDOR);
		}
		if (character == 'e'){
			return new Tile(Type.ENTRANCE);
		}
		if (character == 'x'){
			return new Tile(Type.EXIT);
		}
		return new Tile(Type.WALL);
	}

	public Type getType() {
		return type;
	}

	public boolean isNavigable() {
		if (type == Type.WALL) {
			return false;
		}
		return true;
	}

	public String toString() {
		if (type == Type.CORRIDOR){
			return ".";
		}
		if (type == Type.ENTRANCE){
			return "e";
		}
		if (type == Type.EXIT){
			return "x";
		}
		return "#";
	}

	public enum Type{
		CORRIDOR, ENTRANCE, EXIT, WALL;
	}
}