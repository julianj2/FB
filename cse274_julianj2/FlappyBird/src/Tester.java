import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Tester extends JPanel{
	JFrame window = new JFrame("FLAPPY BIRD");

	public Color color;
	ArrayList<pipe> pipes = new ArrayList<pipe>();
	ArrayList<pipeLow> pipesL = new ArrayList<pipeLow>();
	Bird bird = new Bird();
	Random rnd = new Random();
	Timer tmr = null;
	public long count = 0;
	JLabel lblCount = new JLabel("Score");
	long startTime;
	
	public Tester(){
		window.setBounds(100, 100, 1000, 1000);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setAlwaysOnTop(true);
		window.getContentPane().add(this);
		window.setVisible(true);
		//set window size, make it visible
		lblCount.setBounds(116, 105, 55, 25);
		add(lblCount);
		//adds label to show active score in top of the window

		for (int i = 0; i < 10000; i++){

			pipeLow l = new pipeLow();
			l.x = (i * 300)+400;
			l.y = (1000-rnd.nextInt(150)-225);
			l.width = 35;
			l.height = (1000);
			l.xSpeed = -4;
			l.color = Color.GREEN;
			pipesL.add(l);
			//paints lower pipe

			pipe p = new pipe();
			p.x = (i * 300)+400;
			p.y = 0;
			p.width = 35;
			p.height = (rnd.nextInt(150)+390);
			p.xSpeed = -4;
			p.color = Color.GREEN;
			pipes.add(p);
			//paints upper pipe
		}
		repaint();

		bird.x = 200;
		bird.y = 600;
		bird.color = Color.BLUE;
		bird.width = 30;
		bird.height = 30;
		bird.ySpeed=5;
		//set bird start location, color, size

		this.addMouseListener(new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e) {
				bird.y -= 92;
				repaint();
			}
			//makes bird move when clicked (should be ySpeed)

			@Override public void mouseReleased(MouseEvent e) { }
			@Override public void mouseExited(MouseEvent e) { }			
			@Override public void mouseEntered(MouseEvent e) { }
			@Override public void mouseClicked(MouseEvent e) { }
		});

		tmr = new Timer(30,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				bird.jump();
				setScore();

				if (bird.y>=900){
					tmr.stop();

					JOptionPane.showMessageDialog(null,"Game Over: Your score was " + count);
					Bird.isAlive=false;
					saveScore();
				}
				//checks if bird hits the ground

				if (bird.y<=0){
					tmr.stop();

					System.out.println(tmr);
					JOptionPane.showMessageDialog(null,"Game Over: Your score was " + count);
					Bird.isAlive=false;
					saveScore();
				}
				//checks if bird hits the top

				for (pipe p : pipes){
					if (bird.intersects(p)){
						tmr.stop();

						JOptionPane.showMessageDialog(null,"Game Over: Your score was " + count);
						Bird.isAlive=false;
						saveScore();
					}
				}
				//checks if bird hits upper pipes

				for (pipeLow l : pipesL){
					if (bird.intersects(l)){
						tmr.stop();

						JOptionPane.showMessageDialog(null,"Game Over: Your score was " + count);
						Bird.isAlive=false;
						saveScore();
					}
				}
				//checks if bird hits lower pipes

				for (pipe p : pipes) p.move();
				for (pipeLow l : pipesL) l.move();
//					int x = 205;
//					if (l.x>200 && l.x<205){ // needs to be at least 4, since xSpeed = -4
//						count +=1;
//						lblCount.setText(""+count);
//					}
				

				//makes upper and lower pipes move, counts the number of pipes bird has made it through
				//label shows number of pipes you've currently made it through
				repaint();
			}




		});
		startTime = System.currentTimeMillis();
		tmr.start();
	}
	
	private void saveScore() {
		String name = JOptionPane.showInputDialog("enter your initials");
		
		if (name== null) return;
		try {
			RandomAccessFile raf = new RandomAccessFile("scores.bin","rw");

			raf.seek(raf.length());
			raf.writeUTF(name);
			raf.writeLong(count);
			
			raf.seek(0);
			while(raf.getFilePointer()<raf.length()){
				System.out.println(raf.readUTF() + " - " + raf.readLong());
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setScore() {

		count = System.currentTimeMillis()-startTime;
		lblCount.setText(""+count);
	}
	


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(pipe p : pipes) p.draw(g);
		for(pipeLow l : pipesL) l.draw(g);
		bird.draw(g);
		repaint();
	}
	public static void main(String[] args) {
		new Tester();
		//		readBinaryData();
		//		writeBinaryData();

	}
}
