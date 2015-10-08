package cnnhw1;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class result extends JPanel{
	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   g.setColor(Color.BLACK);
	   g.drawLine(400,0,400,800);
	   g.drawLine(0, 400, 800, 400);
	 }
}
