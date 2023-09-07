import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	public static BufferedImage spritesheet;
	
	public static BufferedImage[] player_front;
	public static BufferedImage[] inimigo_front;
	
	public static BufferedImage tileWall;
	
	public Spritesheet() {
		try {
			spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Armazenando diversas sprites em um único array (utilizado na hora de realizar a animação do player ou inimigo)
		player_front = new BufferedImage[2];
		player_front[0] = Spritesheet.getSprite(0, 11, 16, 16);
		player_front[1] = Spritesheet.getSprite(16, 11, 16, 16);
		
		inimigo_front = new BufferedImage[2];
		inimigo_front[0] = Spritesheet.getSprite(164, 11, 16, 16);
		inimigo_front[1] = Spritesheet.getSprite(180, 11, 16, 16);
		tileWall = Spritesheet.getSprite(280,  221, 16, 16);
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}
