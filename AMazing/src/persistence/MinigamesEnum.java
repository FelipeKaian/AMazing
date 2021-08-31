package persistence;

public enum MinigamesEnum {
	
	Snake(2),SpaceShoter(3),Arkanoide(4);
	
	private int id;

	MinigamesEnum(int id) {
       this.id = id;
	}

	public int getID() {
	   return id;
	}
	
}
