package domain;

public interface MinigamesInterface {

	char[][] draw();

	void update(boolean w, boolean s, boolean d, boolean a, boolean space);

	boolean over();

}
