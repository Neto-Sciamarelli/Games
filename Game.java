import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{

	public static int WIDTH = 640, HEIGHT = 480;
	public static int SCALE = 2;
	public static Player player;
	public World world;
	
	public List<Inimigo> inimigos = new ArrayList<Inimigo>();
	
	public Game() {
		//Adicionando eventos do teclado
		this.addKeyListener(this);
		//Definindo a largura e a altura da tela
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		new Spritesheet();
		
		player = new Player(32,32);
		inimigos.add(new Inimigo(32, 32));
		inimigos.add(new Inimigo(32, 64));
		world = new World();
	}
	
	//Onde fica todo a lógica do nosso game
	public void tick() {
		player.tick();
		
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).tick();
		}
	}
	
	
	//Onde nosso gráficos são renderizados
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(0, 135, 13));
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		player.render(g);
		
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).render(g);
		}
		
		world.render(g);
		
		bs.show();
	}
	
	public static void main(String[] args) {
		//Instanciando as classes game e JFrame(classe referente a tela do jogo)
		Game game = new Game();
		JFrame frame = new JFrame();
		
		//Adicionando nossa instancia de Game no JFrame
		frame.add(game);
		//Definindo o título do jogo que irá aparecer na janela
		frame.setTitle("Mini Zelda");
		//Serve para "empacotar" tudo que foi feito antes
		frame.pack();
		
		//Deixando null a tela ficará centralizada
		frame.setLocationRelativeTo(null);
		//Fecha o programa também ao fechar a janela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Para realmente vermos a janela
		frame.setVisible(true);
		
		//Cria uma thread para o game que automaticamente chama o metodo run, onde fica o nosso game looping
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		
		while(true) {
			tick();
			render();
			try {
				//Frames por segundo do jogo
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Comandos de movimenntação
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.shoot = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Para de executar a movimentação ao soltar a tecla
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
	}

}
