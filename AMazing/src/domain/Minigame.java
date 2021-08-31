package domain;

public class Minigame {
	private int fps,w,h;
	private String title;
	private boolean win = false;
	
	public Minigame() {
		this.fps = 30;
		this.w = 100;
		this.h = 100;
		this.title = "None";
	}
	
	public Minigame(int fps, int w, int h, String title, boolean win) {
		super();
		this.fps = fps;
		this.w = w;
		this.h = h;
		this.title = title;
		this.win = win;
	}

	public int getFps() {
		return fps;
	}
	public void setFps(int fps) {
		this.fps = fps;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
}
