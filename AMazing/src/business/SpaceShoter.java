package business;

import java.util.Random;

import domain.Minigame;
import domain.MinigamesInterface;

public class SpaceShoter extends Minigame implements MinigamesInterface {
	private static Random ran = new Random();
	//ELEMENTOS DO JOGO
	private int w,h;
	private boolean buff = false, spawnBuff = false;
	//MAPA BIDIMENSIONAL
	private int[][] game;
	private char[][] frame;
	//ELEMENTOS DE JOGABILIDADE
	private int defeat = 0;
	private double time = 120;
	//POSIÇÂO DO JOGADOR
	private int x = 15, y = 25;

	public char[][] draw() {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				switch (game[i][j]) {
				case 0:
					frame[i][j] = ' ';
					break;
				case 1:
					frame[i][j] = '♝';
					break;
				case -1:
					frame[i][j] = '█';
					break;
				case 2:
					frame[i][j] = '≜'; 
					break;
				case 3:
					frame[i][j] = '↟';
					break;
				case 4:
					frame[i][j] = '☤';
					break;
				case 5:
					frame[i][j] = '⍫';
					break;
				case 6:
					frame[i][j] = '⍢';
					break;
				case 7:
					frame[i][j] = '⍢';
					break;
				case 8:
					frame[i][j] = '◯';
					break;
				case 9:
					frame[i][j] = '✺';
					break;
				case 10:
					frame[i][j] = '⌘';
					break;
				case 11:
					frame[i][j] = '♝';
					break;
				case 12:
					frame[i][j] = '✰';
					break;
				case 13:
					frame[i][j] = '✫';
					break;
				case 14:
					frame[i][j] = '✩';
					break;
				case 15:
					frame[i][j] = '✯';
					break;
				case 16:
					frame[i][j] = '✮';
					break;
				case 17:
					frame[i][j] = '✬';
					break;
				case 18:
					frame[i][j] = '♨';
					break;

				}
			}
		}
		frame[x][y] = '♝';
		if (buff) {
			frame[x+1][y] = '≜';
		}
		return frame;

	}

	public void update(boolean w, boolean s, boolean d, boolean a, boolean space) {
		if (w) {
			if (game[x][y - 1] == 0 ||game[x][y - 1] >= 12 && game[x][y - 1] <= 17) {
				y--;

			}

		}
		if (s) {
			if (game[x][y + 1] == 0 ||game[x][y + 1] >= 12 && game[x][y + 1] <= 17) {
				y++;
			}

		}
		if (d) {
			if (game[x + 1][y] == 0 ||game[x + 1][y] >= 12 && game[x + 1][y] <= 17) {
				x++;
			}

		}
		if (a) {
			if (game[x - 1][y] == 0 ||game[x - 1][y] >= 12 && game[x - 1][y] <= 17) {
				x--;
			}

		}

		if (space) {
			if (game[x][y - 1] == 0) {
				game[x][y - 1] = 3;
			}
			if (buff) {
				game[x+1][y-1] = 3;
			}
		}
		int[][] backGame = new int[this.w][this.h];
		for (int i = 0; i < backGame.length; i++) {
			for (int j = 0; j < backGame[0].length; j++) {
				backGame[i][j] = game[i][j];
			}
		}
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				if (backGame[i][j] == 3 && j > 0) {
					game[i][j] = 0;
					if (backGame[i][j - 1] == 0) {
						game[i][j - 1] = 3;
					}else if(backGame[i][j - 1] == 10 || backGame[i][j - 1] == 6 || backGame[i][j - 1] == 7) {
						defeat++;
					}
				} else if (backGame[i][j] == 12 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i][j + 1] == 0 && j < 40) {
						game[i][j + 1] = 13;
					}else {
						game[i][j] = 13;
					}
					
				} else if (backGame[i][j] == 13 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i][j + 1] == 0 && j < 40) {
						game[i][j + 1] = 14;
					}else {
						game[i][j] = 14;
					}
					
				} else if (backGame[i][j] == 14 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i][j + 1] == 0 && j < 40) {
						game[i][j + 1] = 15;
					}else {
						game[i][j] = 15;
					}
					
				} else if (backGame[i][j] == 15 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i][j + 1] == 0 && j < 40) {
						game[i][j + 1] = 16;
					}else {
						game[i][j] = 16;
					}
					
				} else if (backGame[i][j] == 16 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i][j + 1] == 0 && j < 40) {
						game[i][j + 1] = 17;
					}else {
						game[i][j] = 17;
					}
					
				} else if (backGame[i][j] == 17 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i][j + 1] == 0 && j < 40) {
						game[i][j + 1] = 12;
					}else {
						game[i][j] = 12;
					}
				}
				else if (backGame[i][j] == 4 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i][j + 1] == 0) {
						game[i][j + 1] = 4;
					}
					
				} else if (backGame[i][j] == 8 && j < this.h - 1) {
					game[i][j] = 18;
					if (backGame[i][j + 1] == 0) {
						game[i][j + 1] = 8;
					}
					
				} else if (backGame[i][j] == 18) {
					game[i][j] = 0;

				} else if (backGame[i][j] == 6 && i < this.w - 1 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i + 1][j + 1] == 0) {
						game[i + 1][j + 1] = 6;
					} else if (backGame[i + 1][j + 1] == 3) {
						game[i + 1][j + 1] = 9;
						defeat++;
					}
					
				} else if (backGame[i][j] == 7 && i > 0 && j < this.h - 1) {
					game[i][j] = 0;
					if (backGame[i - 1][j + 1] == 0) {
						game[i - 1][j + 1] = 7;
					} else if (backGame[i - 1][j + 1] == 3) {
						game[i - 1][j + 1] = 9;
						defeat++;
					}
					
				} else if (backGame[i][j] == 10 && i > 0 && j < this.h - 1 && (int) time % 2 == 0) {
					game[i][j] = 0;
					if (y < j && game[i][j] == 0) {
						game[i][j - 1] = 10;
						if (backGame[i][j - 1] == 3) {
							game[i][j - 1] = 9;
							defeat++;
						}
						
					} else if (y > j && game[i][j] == 0) {
						game[i][j + 1] = 10;
						if (backGame[i][j + 1] == 3) {
							game[i][j + 1] = 9;
							defeat++;
						}
						
					} else {
						if (x > i && game[i][j] == 0) {
							game[i + 1][j] = 10;
							if (backGame[i + 1][j] == 3) {
								game[i + 1][j] = 9;
								defeat++;
							}
							
						} else if (x < i && game[i][j] == 0) {
							game[i - 1][j] = 10;
							if (backGame[i - 1][j] == 3) {
								game[i - 1][j] = 9;
								defeat++;
							}
						}
					}
				} else if (backGame[i][j] == 9) {
					game[i][j] = 0;
				}
			}
		}
		time -= 0.1;
		if ((int) time == 110 && spawnBuff == false) {
			game[20][1] = 12;
			spawnBuff = true;
		}
		if (game[x][y] >= 12 && game[x][y] <= 17 ) {
			buff = true; 
			game[x][y] = 0;
			
		}
		if ((int) time % 2 == 1) {
			int randx = ran.nextInt(this.w - 2) + 1;
			while (game[randx][1] != 0) {
				randx = ran.nextInt(this.w - 2) + 1;
			}
			game[randx][1] = 8;
		}

		if ((int) time % 5 == 0) {
			int sort = ran.nextInt(4);
			switch (sort) {
			case 0:
				int randx = ran.nextInt(28) + 1;
				while (game[randx][1] != 0) {
					randx = ran.nextInt(28) + 1;
				}

				game[randx][1] = 8;
				break;
			case 1:
				int ran2 = ran.nextInt(this.h / 3) + 1;
				while (ran2 < 10) {
					ran2 = ran.nextInt(10) + 1;
				}
				game[1][ran2] = 6;
				break;
			case 2:
				int randxa2 = ran.nextInt(this.h / 3) + 1;
				while (randxa2 < 10) {
					randxa2 = ran.nextInt(10) + 1;
				}
				game[28][randxa2] = 7;
				break;
			case 3:
				int randxa3 = ran.nextInt(this.w - 2) + 1;
				while (game[randxa3][h - 2] != 0) {
					randxa3 = ran.nextInt(this.w - 2) + 1;
				}
				game[randxa3][h - 2] = 10;
				break;
			}

		}

	}

	public SpaceShoter() {
		
		super.setTitle("Space Shoter");
		super.setFps(60);
		super.setW(30);
		super.setH(50);
		
		w = super.getW();
		h = super.getH();
		
		game = new int[w][h];
		frame = new char[w][h];
		
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				if (i == 0) {
					game[i][j] = -1;
				}
				if (i == w - 1) {
					game[i][j] = -1;
				}
				if (j == h - 1) {
					game[i][j] = -1;
				}
				if (j == 0) {
					game[i][j] = -1;
				}

			}

		}
	}

	public boolean over() {
		if (time <= 0 || defeat >= 10) {
			super.setWin(true);
			return true;
		} else if (!(game[x][y] == 0 || game[x][y] >= 12 && game[x][y] <= 17)) {
			return true;
		} else {
			return false;
		}

	}

}