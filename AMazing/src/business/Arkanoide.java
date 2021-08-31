package business;

import domain.Minigame;
import domain.MinigamesInterface;

public class Arkanoide extends Minigame implements MinigamesInterface{
	
	private int w,h;
	private int[][] game;
	private char[][] frame;
	private int bx = 20;
	private int by = 36;
	private int px = 19;
	private int py = 35;
	private int dirbx = 1;
	private int dirby = -1;
	private int cont = 0;
	
	public Arkanoide() {
		
		super.setTitle("Arkanoide");
		super.setFps(30);
		super.setW(40);
		super.setH(40);
		
		w = super.getW();
		h = super.getH();
		
		game = new int[w][h];
		frame = new char[w][h];
			
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				
				if(j > 2 && j <= 20) {
					if(j%3 == 0) {
						if(j%2 ==0) {
							if(i > 0 && i < this.w-3) {
								game[i][j] = 2;
							}
						}else {
							if(i > 2 && i < this.w-1) {
								game[i][j] = 2;
							}
						}
					}
				}
				
				if (i == 0) {
					game[i][j] = 4;
				}
				if (i == this.w - 1) {
					game[i][j] = 4;
				}
				if (j == 0) {
					game[i][j] = 4;
				}
				if (j == this.h - 1) {
					game[i][j] = 4;
				}
			}
		}
	}
	
	
	@Override
	public char[][] draw() {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				switch (game[i][j]) {
				case 0:
					frame[i][j] = ' ';
					break;
				case 1:
					frame[i][j] = '@';
					break;
				case 2:
					frame[i][j] = '▣';
					break;
				case 3:
					frame[i][j] = '=';
					break;
				case 4:
					frame[i][j] = '█';
					break;
				}
			}
		}
		return frame;
	}


	@Override
	public void update(boolean w, boolean s, boolean d, boolean a, boolean space) {
		game[bx][by] = 0;
		
		for (int i = 0; i < 6; i++) {
			game[px+i][py] =0;
		}
		
		if (a) {
			if(px > 1) {
				px-=2;
			}
		}
		if (d) {
			if(px+5 < this.w-2) {
				px+=2;
			}
		}
		
		bx += dirbx;
		by += dirby;
		
		if (bx ==1) {
			dirbx = 1;
		}
		if (bx == this.w-2) {
			dirbx = -1;
		}
		if (by==1) {
			dirby = 1;
		}
		
		if(game[bx+1][by] == 2) {
			game[bx+1][by] = 0;
			dirbx= -1;
			cont++;
		}
		else if(game[bx][by+1] == 2) {
			game[bx][by+1] = 0;
			dirby= -1;
			cont++;
		}
		else if(game[bx-1][by] == 2) {
			game[bx-1][by] = 0;
			dirbx= 1;
			cont++;
		}
		else if(game[bx][by-1] == 2) {
			game[bx][by-1] = 0;
			dirby= 1;
			cont++;
		}
		
		for (int i = 0; i < 6; i++) {
			if (by == py-1) {
				if (bx == px+i) {
					dirby = -1;
					if (dirbx==1) {
						dirbx= 1;
					}else {
						dirbx = -1;
					}
				}
			}
		}
			
		game[bx][by] = 1;
		
		for (int i = 0; i < 6; i++) {
			game[px+i][py] = 3;
		}
		
		
	}

	@Override
	public boolean over() {
		if(cont >= 45) {
			super.setWin(true);
			return true;
		}
		if (by == this.h-2) {
			return true;
		}
		return false;
	}

}
