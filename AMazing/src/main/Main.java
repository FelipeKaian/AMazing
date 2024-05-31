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
import java.util.List;
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

/*class Voxel {
	public Voxel(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	int x = 0;
	int y = 0;
	int z = 0;
}*/


public class Main {
	// ELEMENTOS DO JOGO
	private static boolean running = true;
	private static int sense = 2, campx = 80, campy = 60,fps = 0,frames = 0;
	private static double pSpeed = 0.04, precision = 0.5, maxDist = 0, distProcess = 0.5;
	// MAPA TRIDIMENSIONAL
	private static List<Voxel> voxels = new ArrayList<Voxel>();
	// JOGADOR ELEMENTOS
	private static double px = 0, py = 0, pz = 0;
	private static int xangle = 90, yangle = 90;
	// TAMANHO DA TELA
	private static int ww = 1980, wh = 1080;
	private static Random rand = new Random();
	private static boolean w, s, d, a, up, down, space;
	

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
					up = true;
				}
				if (tecla.getKeyCode() == 40) {
					down = true;
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
					up = false;
				}
				if (tecla.getKeyCode() == 40) {
					down = false;
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
		
		canvas.

		frame.add(canvas);

		canvas.createBufferStrategy(3);

		BufferStrategy bufferStrategy = null;
		Graphics ctx = null;

		voxels.add(new Voxel(1, 2, 3));
		voxels.add(new Voxel(3, 5, 7));
		voxels.add(new Voxel(-2, 2, 5));
		voxels.add(new Voxel(8, -3, 1));
		
		long start = System.currentTimeMillis();

		while (running) {
			long now = System.currentTimeMillis();
			if(start+1000<now) {
				fps=frames;
				frames = 0;
				start=now;
			}
			render(canvas, ctx, bufferStrategy);
			frames++;

		}

	}

	private static void render(Canvas canvas, Graphics ctx, BufferStrategy bufferStrategy) throws Exception {

		bufferStrategy = canvas.getBufferStrategy();
		ctx = bufferStrategy.getDrawGraphics();

		ctx.setFont(new Font("Serif", Font.PLAIN, 18));

		ctx.setColor(Color.black);
		ctx.fillRect(0, 0, ww, wh);
		ctx.setColor(Color.white);
		
		ctx.drawString(fps+"", 50, 50);

		if (w) {
			px += pSpeed * Math.sin((((xangle - 45) % 360) * Math.PI) / 180);
			py += pSpeed * Math.cos((((xangle - 45) % 360) * Math.PI) / 180);
		}
		if (s) {
			px += pSpeed * Math.sin((((xangle + 135) % 360) * Math.PI) / 180);
			py += pSpeed * Math.cos((((xangle + 135) % 360) * Math.PI) / 180);
		}

		if (a) {
			xangle -= sense;
		}
		if (d) {
			xangle += sense;
		}
		

		if (up) {
			yangle -= sense;
		}
		if (down) {
			yangle += sense;
		}

		double ax0 = xangle - (campx / 2), ay0 = yangle - (campy / 2);

		for (double a = 0, i = ax0; i <= ax0 + campx; i += precision, a += precision) {
			for (double b = 0, j = ay0; j <= ay0 + campy; j += precision, b += precision) {

				double DistView = 0, lx = Math.floor(px), ly = Math.floor(py), lz = Math.floor(pz);
				double dist = 0;

				double dx = lx, dy = ly, dz = lz;

				while (DistView < maxDist) {

					while (true) {
						if (dx != lx) {
							break;
						} else {
							if (dy != ly) {
								break;
							} else {
								if (dz != lz) {
									break;
								} else {
									dist += distProcess;
									dx = Math.floor(px + dist * Math.sin((i * Math.PI) / 180));
									dy = Math.floor(py + dist * Math.cos((i * Math.PI) / 180));
									dz = Math.floor(pz + dist * Math.cos((j * Math.PI) / 180));
								}
							}
						}
					}

					boolean block = find(dx, dy, dz);

					if (block) {
						double w = Math.floor((ww / (campx)) * precision), h = Math.floor((wh / (campy)) * precision),
								light = Math.floor((-256 / (maxDist * maxDist) * (dist + maxDist) * (dist + maxDist)))
										+ 256;
						ctx.fillRect((int) (w * a / precision), (int) (h * b / precision), (int) (w + 2),
								(int) (h + 2));
						break;
					} else {
						lx = dx;
						ly = dy;
						lz = dz;
						DistView++;
					}
				}
			}
		}

		bufferStrategy.show();

		
		ctx.dispose();
	}

	private static boolean find(double dx, double dy, double dz) {
		for (int i = 0; i < voxels.size(); i++) {

			if (voxels.get(i).x == (int) Math.floor(dx) && voxels.get(i).y == (int) Math.floor(dy)
					&& voxels.get(i).z == (int) Math.floor(dz)) {

				return true;
			}
		}
		return false;
	}
}