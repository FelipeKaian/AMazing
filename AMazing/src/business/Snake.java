package business;

import domain.Minigame;
import domain.MinigamesInterface;

public class Snake extends Minigame implements MinigamesInterface {
	
	int[][] game;
	char[][] frame;
	int w,h;
	int px = 12, py = 13, dir = 0, len = 3;
	boolean over = false, start = false;

	public Snake() {
		
		super.setTitle("Snake");
		super.setFps(60);
		super.setW(25);
		super.setH(25);
		
		w = super.getW();
		h = super.getH();
		
		game = new int[w][h];
		frame = new char[w][h];
		
		game[12][13] = 2;
		game[11][13] = 2;
		
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				
				if (i%4 == 0 && j%4==0) {
					game[i][j] = -2;
				}
				if (i == 0) {
					game[i][j] = -1;
				}
				if (i == this.w - 1) {
					game[i][j] = -1;
				}
				if (j == 0) {
					game[i][j] = -1;
				}
				if (j == this.h - 1) {
					game[i][j] = -1;
				}
			}
		}

	}

	public char[][] draw() {

		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				switch (game[i][j]) {
				case -2:
					frame[i][j] = 'O';
					break;
				case -1:
					frame[i][j] = 'X';
					break;
				case 0:
					frame[i][j] = ' ';
					break;
				default:
					frame[i][j] = '#';
					break;
				}
			}
		}

		return frame;
	}

	public void update(boolean w, boolean s, boolean d, boolean a, boolean space) {

			if (d) {
				dir = 0;
			}
			if (s) {
				dir = 1;
			}
			if (a) {
				dir = 2;
			}
			if (w) {
				dir = 3;
			}

			for (int i = 0; i < this.w; i++) {
				for (int j = 0; j < this.h; j++) {
					if (game[i][j] > 0) {
						game[i][j]--;
					}
				}
			}

			if (dir == 0 && px + 1 < this.w) {
				if (game[px + 1][py] == -2) {
					len++;
				}
				if (game[px + 1][py] == 0 || game[px + 1][py] == -2) {
					px++;
				} else {
					over = true;
				}
			}
			if (dir == 1 && py + 1 < this.h) {
				if (game[px][py + 1] == -2) {
					len++;
				}
				if (game[px][py + 1] == 0 || game[px][py + 1] == -2) {
					py++;
				} else {
					over = true;
				}
			}
			if (dir == 2 && px - 1 >= 0) {
				if (game[px - 1][py] == -2) {
					len++;
				}
				if (game[px - 1][py] == 0 || game[px - 1][py] == -2) {
					px--;
				} else {
					over = true;
				}

			}
			if (dir == 3 && py - 1 >= 0) {
				if (game[px][py - 1] == -2) {
					len++;
				}
				if (game[px][py - 1] == 0 || game[px][py - 1] == -2) {
					py--;
				} else {
					over = true;
				}
			}

			game[px][py] = len;

	}

	public boolean over() {
		
		super.setWin(true);
		for (int[] objrow : game) {
			for (int obj : objrow) {
				if(obj == -2) {
					super.setWin(false);
				}
			}
		}
		if (super.isWin()) {
			return true;
		}
		return over;

	}

}
