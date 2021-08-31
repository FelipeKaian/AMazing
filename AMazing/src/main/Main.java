package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import business.Arkanoide;
import business.Snake;
import business.SpaceShoter;
import domain.Minigame;
import domain.MinigamesInterface;
import persistence.MinigamesEnum;

public class Main {
	// ELEMENTOS DO JOGO
	private static boolean running = true;
	private static int sense = 2, camp = 80;
	private static double pSpeed = 0.04, scale = 0.01;
	// TAMANHO DE MAP
	private static int mapw = 17, maph = 17;
	// JANELA DE MINIGAMES
	private static String minititle = "";
	private static int miniw = 10, minih = 10, minifs = 30;
	// MAPA BIDIMENSIONAL
	private static int[][] map = new int[mapw][maph];
	// JOGADOR ELEMENTOS
	private static double px = 1.5, py = 1.5;
	private static int xangle = 90;
	// TAMANHO DA TELA
	private static int ww = 1980, wh = 1080, sw = ww / 10, sh = wh / 10;
	private static int miniID;
	private static Random rand = new Random();
	private static boolean inminigame = false, ministart = false;
	private static boolean w, s, d, a, space;
	private static Minigame minigame = null;
	private static MinigamesInterface miniInterface = (MinigamesInterface) minigame;
	private static char[][] tela = new char[sw][sh];
	private static int[] colors = new int[sw];
	private static int[][] bg = new int[sw / 2][sh / 2];
	private static int shortcuts = 10, QTDminigame = 3;
	private static MinigamesEnum snake = MinigamesEnum.Snake, spaceShoter = MinigamesEnum.SpaceShoter,
			arknoide = MinigamesEnum.Arkanoide;
	private static JLayeredPane Image;
	
	
	public static void main(String[] args) throws Exception {

		JFrame frame = new JFrame("");
		frame.setSize(ww, wh);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener((KeyListener) new KeyListener() {

			@Override
			public void keyTyped(KeyEvent tecla) {
			}

			@Override
			public void keyPressed(KeyEvent tecla) {

				if (tecla.getKeyChar() == ' ') {
					space = true;
				}
				if (tecla.getKeyChar() == 'w') {
					w = true;
				}
				if (tecla.getKeyChar() == 's') {
					s = true;
				}
				if (tecla.getKeyChar() == 'a') {
					a = true;
				}
				if (tecla.getKeyChar() == 'd') {
					d = true;
				}
				if (tecla.getKeyCode() == 38) {
					w = true;
				}
				if (tecla.getKeyCode() == 40) {
					s = true;
				}
				if (tecla.getKeyCode() == 37) {
					a = true;
				}
				if (tecla.getKeyCode() == 39) {
					d = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {

				if (tecla.getKeyChar() == ' ') {
					space = false;
				}
				if (tecla.getKeyChar() == 'w') {
					w = false;
				}
				if (tecla.getKeyChar() == 's') {
					s = false;
				}
				if (tecla.getKeyChar() == 'a') {
					a = false;
				}
				if (tecla.getKeyChar() == 'd') {
					d = false;
				}
				if (tecla.getKeyCode() == 38) {
					w = false;
				}
				if (tecla.getKeyCode() == 40) {
					s = false;
				}
				if (tecla.getKeyCode() == 37) {
					a = false;
				}
				if (tecla.getKeyCode() == 39) {
					d = false;
				}

			}
		});

		Canvas canvas = new Canvas();

		canvas.setSize(ww, wh);
		canvas.setBackground(Color.BLACK);
		canvas.setVisible(true);

		frame.add(canvas);

		canvas.createBufferStrategy(3);

		BufferStrategy bufferStrategy = null;
		Graphics ctx = null;

		mapSet();

		boolean gameStart = false;

		while (!gameStart) {

			intro(canvas, ctx, bufferStrategy);

			Thread.sleep(100);

			if (space) {
				gameStart = true;
				space = false;
			}

		}

		while (running) {

			render(canvas, ctx, bufferStrategy);

		}

	}

	private static void intro(Canvas canvas, Graphics ctx, BufferStrategy bufferStrategy) {
		bufferStrategy = canvas.getBufferStrategy();
		ctx = bufferStrategy.getDrawGraphics();

		ctx.setFont(new Font("Serif", Font.PLAIN, 20));

		ctx.setColor(Color.black);
		ctx.fillRect(0, 0, ww, wh);
		for (int i = 0; i < 50; i++) {
			bg[rand.nextInt(bg.length)][0] = rand.nextInt(100);
		}

		for (int i = 0; i < bg.length; i++) {
			for (int j = 0; j < bg[0].length - 1; j++) {
				bg[i][j + 1] = bg[i][j];
				bg[i][j] /= 2;
			}
		}

		for (int i = 0; i < bg.length; i++) {
			for (int j = 0; j < bg[0].length; j++) {
				ctx.setColor(new Color(255, 255, 255, bg[i][j]));
				ctx.drawString((char) rand.nextInt(255) + "", i * ctx.getFont().getSize(),
						j * ctx.getFont().getSize() + ctx.getFont().getSize() / 2);
			}
		}

		ctx.setFont(new Font("Serif", Font.PLAIN, 250));
		ctx.setColor(Color.white);
		ctx.drawString("AMazing", (ww / 2) - 530, 300);

		ctx.setFont(new Font("Serif", Font.PLAIN, 50));
		ctx.setColor(Color.white);
		ctx.drawString("PRESS SPACE", (ww / 2) - 210, 800);

		bufferStrategy.show();
		ctx.dispose();

	}

	private static void render(Canvas canvas, Graphics ctx, BufferStrategy bufferStrategy) throws Exception {

		bufferStrategy = canvas.getBufferStrategy();
		ctx = bufferStrategy.getDrawGraphics();

		ctx.setFont(new Font("Serif", Font.PLAIN, 10));

		ctx.setColor(Color.black);
		ctx.fillRect(0, 0, ww, wh);
		ctx.setColor(Color.white);

		boolean play = false;

		if (inminigame) {
			if (ministart) {

				try {
					Thread.sleep(minifs);
				} catch (InterruptedException e) {
					throw new Exception(
							"Não foi possivel executar um Theard do tipo Sleep em seu computador, impossibilitando o funcionamento do programa AMazing.java");
				}

				miniInterface.update(w, s, d, a, space);

				if (miniInterface.over()) {
					inminigame = false;
					ministart = false;
					space = false;

					if (minigame.isWin()) {
						for (int i = -1; i < 2; i++) {
							for (int j = -1; j < 2; j++) {
								if (map[(int)px+i][(int)py + j] == miniID) {
									map[(int)px+ i][(int)py + j] = 0; 
								}
							}
						}
					}

				}

			} else {
				if (space) {
					ministart = true;
				}
			}

			char[][] miniframe = miniInterface.draw();

			for (int i = sh / 2 - minih, y = 0; y < minih * 2; i++, y++) {
				for (int j = sw / 2 - miniw, x = 0; x < miniw * 2; j++, x++) {
					tela[j][i] = ' ';
				}
			}

			if (!ministart) {

				String text = "Press space to start";

				for (int i = (miniw + 1) / 2 - text.length() / 2, idx = 0; idx < text.length(); idx++, i++) {
					miniframe[i][minih * 2 / 3] = text.charAt(idx);
				}

				for (int i = (miniw + 1) / 2 - minititle.length() / 2, idx = 0; idx < minititle.length(); idx++, i++) {
					miniframe[i][minih / 3] = minititle.charAt(idx);
				}
			}

			ctx.setFont(new Font("Serif", Font.PLAIN, 20));

			for (int i = 0; i < minih; i++) {
				for (int j = 0; j < miniw; j++) {
					ctx.drawString(miniframe[j][i] + "", (int) ((ww / 2) - miniw * 10) + j * ctx.getFont().getSize(),
							(int) ((wh / 2) - minih * 10) + i * ctx.getFont().getSize() + ctx.getFont().getSize() / 2);
				}
			}

			ctx.setFont(new Font("Serif", Font.PLAIN, 10));

		} else {

			if (!inminigame && w
					&& map[(int) Math.floor(px + pSpeed * Math.cos((((xangle - 45) % 360) * Math.PI) / 180))][(int) Math
							.floor(py + pSpeed * Math.cos((((xangle - 45) % 360) * Math.PI) / 180))] < 1) {
				px += pSpeed * Math.sin((((xangle - 45) % 360) * Math.PI) / 180);
				py += pSpeed * Math.cos((((xangle - 45) % 360) * Math.PI) / 180);

			}
			if (!inminigame && s
					&& map[(int) Math
							.floor(px + pSpeed * Math.sin((((xangle + 135) % 360) * Math.PI) / 180))][(int) Math
									.floor(py + pSpeed * Math.cos((((xangle + 135) % 360) * Math.PI) / 180))] < 1) {
				px += pSpeed * Math.sin((((xangle + 135) % 360) * Math.PI) / 180);
				py += pSpeed * Math.cos((((xangle + 135) % 360) * Math.PI) / 180);

			}

			if (!inminigame && a) {
				xangle -= sense;
			}
			if (!inminigame && d) {
				xangle += sense;
			}

			for (int i = 0; i < sh; i++) {
				for (int j = 0; j < sw; j++) {
					tela[j][i] = ' ';
				}
			}

			for (int i = 0; i < sh; i++) {
				for (int j = 0; j < sw; j++) {
					ctx.drawString(tela[j][i] + "", j * ctx.getFont().getSize(),
							i * ctx.getFont().getSize() + ctx.getFont().getSize() / 2);
				}
			}

			double[] dist = new double[sw];

			for (double i = xangle - camp, cont = 0; i < xangle + camp; i += 0.4, cont++) {
				double distance = 0;
				double x = 0;
				double y = 0;
				if (cont < dist.length) {
					dist[(int) cont] = travel(distance, x, y, i, (int) cont);
				}

			}

			for (int i = 0; i < dist.length; i++) {
				for (double j = (sh / 2) - (sh / (dist[i] + 0.1)) / 2; j < (sh / 2) + (sh / (dist[i] + 0.1)); j++) {
					if (j > 0 && j < sh) {
						if (colors[i] == -1) {
							tela[i][(int) j] = '█';
						} else {
							if (dist[i] > 10) {
								tela[i][(int) j] = '.';
							} else if (dist[i] > 8) {
								tela[i][(int) j] = '-';
							} else if (dist[i] > 6) {
								tela[i][(int) j] = ',';
							} else if (dist[i] > 4.5) {
								tela[i][(int) j] = '~';
							} else if (dist[i] > 4) {
								tela[i][(int) j] = '^';
							} else if (dist[i] > 3.5) {
								tela[i][(int) j] = '+';
							} else if (dist[i] > 3) {
								tela[i][(int) j] = '*';
							} else if (dist[i] > 2.5) {
								tela[i][(int) j] = '=';
							} else if (dist[i] > 2) {
								tela[i][(int) j] = '#';
							} else if (dist[i] > 1.5) {
								tela[i][(int) j] = '$';
							} else if (dist[i] > 1) {
								tela[i][(int) j] = 'E';
							} else if (dist[i] > 0.5) {
								tela[i][(int) j] = 'H';
							} else {
								tela[i][(int) j] = '@';
							}
						}

					}
				}
			}


			if (map[(int) px][(int) py] > 1 || map[(int) px + 1][(int) py] > 1 || map[(int) px][(int) py + 1] > 1 || map[(int) px - 1][(int) py] > 1 || map[(int) px][(int) py - 1] > 1) {
				if (space) {

					space = false;

					int ID = 0;
					
					if (map[(int) px][(int) py] > 1) {
						ID = map[(int) px][(int) py];
					} else if (map[(int) px + 1][(int) py] > 1) {
						ID = map[(int) px + 1][(int) py];
					} else if (map[(int) px][(int) py + 1] > 1) {
						ID = map[(int) px][(int) py + 1];
					}else if (map[(int) px - 1][(int) py] > 1) {
						ID = map[(int) px - 1][(int) py];
					} else if (map[(int) px][(int) py - 1] > 1) {
						ID = map[(int) px][(int) py - 1];
					}

					if (snake.getID() == ID) {
						minigame = new Snake();
						miniID = 2;
					} else if (spaceShoter.getID() == ID) {
						minigame = new SpaceShoter();
						miniID = 3;
					} else if (arknoide.getID() == ID) {
						minigame = new Arkanoide();
						miniID = 4;
					}

					minititle = minigame.getTitle();
					miniw = minigame.getW();
					minih = minigame.getH();
					minifs = minigame.getFps();

					inminigame = true;

					miniInterface = (MinigamesInterface) minigame;
					miniInterface.update(w, s, d, a, space);
				} else {
					play = true;
				}
			}
			
			
			
		}
		
		for (int i = 0; i < sw; i++) {
			ctx.setColor(cor(colors[i]));
			for (int j = 0; j < sh; j++) {
				ctx.drawString(tela[i][j] + "", i * ctx.getFont().getSize(),
						j * ctx.getFont().getSize() + ctx.getFont().getSize() / 2);
			}
		}

		if (play) {
			ctx.setColor(Color.black);
			ctx.setFont(new Font("Serif", Font.PLAIN, 200));

			ctx.drawString("Space to play", (ww / 2) - 600, wh / 2);

			ctx.setFont(new Font("Serif", Font.PLAIN, 10));
		}
		
		if(map[(int)px][(int)py] == -1) {
			running = false;
			ctx.setColor(Color.black);
			ctx.fillRect(0, 0, ww, wh);
			ctx.setColor(new Color(50,50,50));
			for (int i = 0; i < sw; i++) {
				for (int j = 0; j < sh; j++) {
					ctx.drawString((char)(rand.nextInt(93)+33) + "", i * ctx.getFont().getSize(),
							j * ctx.getFont().getSize() + ctx.getFont().getSize() / 2);
				}
			}
			Image = new JLayeredPane();
            BufferedImage neiva = null;
            try {
                neiva = ImageIO.read(new File("image/neiva.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ctx.drawImage(neiva,0, 0,(int)(wh*2/3),(int)(wh*2/3), Image);
            
			ctx.setColor(Color.white);
			ctx.setFont(new Font("Serif", Font.PLAIN, 150));
			ctx.drawString("Obrigado por jogar AMazing", 50, 950);
			ctx.setFont(new Font("Serif", Font.PLAIN, 100));
			ctx.drawString("Você venceu!!!", 950, 100);
			ctx.drawString("Creditos:", 1250, 250);
			ctx.drawString("Felipe Kaian", 1250, 400);
			ctx.drawString("Felipe Diniz", 1250, 500);
			ctx.drawString("Alisson Ornelas", 1250, 600);
			ctx.setFont(new Font("Serif", Font.PLAIN, 40));
			ctx.drawString("Agradecimentos ao ", 800, 300);
			ctx.drawString("← professor Neiva", 755, 400);
			ctx.drawString("por proporcionar a", 800, 500);
			ctx.drawString("realização deste trabalho.", 800, 600);
			ctx.setFont(new Font("Serif", Font.PLAIN, 100));
			ctx.drawString("Manda estágio ^-^", 900, 750);
			
			
		}

		bufferStrategy.show();
		ctx.dispose();

	}

	private static Color cor(int i) {
		switch (i) {
		case 1:
		case -1:
			return Color.white;
		case 2:
			return Color.green;
		case 3:
			return Color.red;
		case 4:
			return Color.blue;
		}
		return null;
	}

	private static double travel(double distance, double x, double y, double angulo, int cont) {
		int wall;
		x = px + distance * Math.sin((angulo * Math.PI) / 180);
		y = py + distance * Math.cos((angulo * Math.PI) / 180);
		if ((int) x >= 0 && (int) x < mapw && (int) y >= 0 && (int) y < maph) {
			wall = map[(int) x][(int) y];
		} else {
			return 8000;
		}

		if (wall != 0) {

			colors[cont] = wall;
			return distance;
		} else {
			return travel(distance + scale, x, y, angulo, cont);
		}

	}

	private static void mapSet() {
		int outdir = 0;

		int tx = 1, ty = 1;

		for (int i = 0; i < mapw; i++) {
			for (int j = 0; j < maph; j++) {
				map[i][j] = 1;
			}
		}

		while (!mapCheck()) {

			map[tx][ty] = 0;

			ArrayList<Integer> dir = new ArrayList<Integer>();

			if (tx + 2 < mapw) {
				if (map[tx + 2][ty] == 1) {
					dir.add(0);
				}
			}
			if (ty + 2 < maph) {
				if (map[tx][ty + 2] == 1) {
					dir.add(1);
				}
			}
			if (tx - 2 >= 0) {
				if (map[tx - 2][ty] == 1) {
					dir.add(2);
				}
			}
			if (ty - 2 >= 0) {
				if (map[tx][ty - 2] == 1) {
					dir.add(3);
				}
			}

			if (dir.size() == 0) {

				if (tx + 2 < mapw && outdir != 0) {
					if (map[tx + 2][ty] == 0 && map[tx + 1][ty] == 0) {
						dir.add(0);
					}
				}
				if (ty + 2 < maph && outdir != 1) {
					if (map[tx][ty + 2] == 0 && map[tx][ty + 1] == 0) {
						dir.add(1);
					}
				}
				if (tx - 2 >= 0 && outdir != 2) {
					if (map[tx - 2][ty] == 0 && map[tx - 1][ty] == 0) {
						dir.add(2);
					}
				}
				if (ty - 2 >= 0 && outdir != 3) {
					if (map[tx][ty - 2] == 0 && map[tx][ty - 1] == 0) {
						dir.add(3);
					}
				}

			}
			if (dir.size() > 0) {
				int move = dir.get(rand.nextInt(dir.size()));

				outdir = (move + 2) % 4;

				if (move == 0) {
					tx++;
					map[tx][ty] = 0;
					tx++;
				}
				if (move == 1) {
					ty++;
					map[tx][ty] = 0;
					ty++;
				}
				if (move == 2) {
					tx--;
					map[tx][ty] = 0;
					tx--;
				}
				if (move == 3) {
					ty--;
					map[tx][ty] = 0;
					ty--;
				}

				map[tx][ty] = 0;

			} else {
				int rx = (rand.nextInt(((mapw - 1) / 2)) * 2) + 1, ry = (rand.nextInt(((maph - 1) / 2)) * 2) + 1;
				while (map[rx][ry] == 1) {
					rx = (rand.nextInt(((mapw - 1) / 2)) * 2) + 1;
					ry = (rand.nextInt(((maph - 1) / 2)) * 2) + 1;
				}
				tx = rx;
				ty = ry;
			}

		}

		Integer[] endmap = findend();
		map[endmap[0]][endmap[1]] = -1;

		for (int i = 0; i < shortcuts; i++) {
			int rx = (rand.nextInt((int) (mapw / 2)) * 2) + 1, ry = (rand.nextInt((int) (maph / 2)) * 2) + 1;
			while (map[rx][ry] == -1 || (rx == 1 && ry == 1)) {
				rx = (rand.nextInt((int) (mapw / 2)) * 2) + 1;
				ry = (rand.nextInt((int) (maph / 2)) * 2) + 1;
			}
			map[rx][ry] = rand.nextInt(QTDminigame) + 2;
		}
	}

	private static Integer[] findend() {
		ArrayList<Integer[]> rightend = new ArrayList<Integer[]>();
		for (int i = 1; i < mapw - 1; i++) {
			for (int j = 1; j < maph - 1; j++) {
				int counter = 0;
				for (int kl = -1; kl < 2; kl++) {
					for (int k = -1; k < 2; k++) {
						if (map[i + kl][j + k] == 0) {
							counter++;
						}
					}
				}
				if (counter == 2) {
					Integer[] position = { i, j };
					rightend.add(position);
				}
			}
		}
		Integer[] fartestEnd = { 1, 1 };
		for (int i = 0; i < rightend.size(); i++) {
			if (fartestEnd[0] + fartestEnd[1] < rightend.get(i)[0] + rightend.get(i)[1]) {
				fartestEnd[0] = rightend.get(i)[0];
				fartestEnd[1] = rightend.get(i)[1];
			}
		}
		return fartestEnd;
	}

	public static boolean mapCheck() {

		for (int i = 0; i < mapw - 1; i++) {
			for (int j = 0; j < maph - 1; j++) {
				if (map[i][j] == 1 && map[i + 1][j] == 1 && map[i + 1][j + 1] == 1 && map[i][j + 1] == 1) {
					return false;
				}
			}
		}
		return true;
	} 
	
}