import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class pipeLow extends Rectangle{
	public Color color;
	public int sw, sh;
	public int xSpeed;
	
	public boolean move() {
		x += -Math.abs(xSpeed);
		
		
		return (x>=100);
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
	}
}
