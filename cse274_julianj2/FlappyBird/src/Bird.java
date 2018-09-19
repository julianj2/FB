import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bird extends Rectangle{
	public int ySpeed;
	public Color color;
	public Rectangle bird;
	public static boolean isAlive;
	
	public boolean jump(){
		y += Math.abs(ySpeed);
		return (y >= 900);
	}
	
	public void draw(Graphics g){
		g.setColor(color); g.fillRect(x, y, width, height);
		g.setColor(Color.RED); g.drawRect(x, y, width, height);
	}
}
