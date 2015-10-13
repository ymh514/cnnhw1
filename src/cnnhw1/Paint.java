package cnnhw1;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.geom.*;

public class Paint extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float[] initial;
	private float threshold;
	private float liney1;
	private float liney2;
	private int framesizex;
	private int framesizey;
	private ArrayList<float[]> array_train = new ArrayList<float[]>();

	public Paint(ArrayList<float[]> array_train, float[] initial, float threshold, float liney1, float liney2,
			int framesizex, int framesizey) {
		this.array_train = array_train;
		this.initial = initial;
		this.threshold = threshold;
		this.liney1 = liney1;
		this.liney2 = liney2;
		this.framesizex = framesizex;
		this.framesizey = framesizey;
	}// need trans to constructor

	Color red = new Color(255, 0, 0);
	Color green = new Color(0, 255, 0);
	Color blue = new Color(0, 0, 255);
	Color black = new Color(0, 0, 0);

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		 * call過來的兩個y軸值,可能要改一下算法
		 */
		// System.out.println("------------"+liney1);
		// System.out.println("------------"+liney2);

		Graphics2D g2d = (Graphics2D) g;
		g.setColor(green);
		g2d.setStroke(new BasicStroke(3));// set line width
		g2d.draw(new Line2D.Float(800, 400 + (-liney1 * 16), 0, 400 + (-liney2 * 16)));
		g.setColor(black);
		g2d.setStroke(new BasicStroke(1));
		g.drawLine(framesizex / 2, 0, framesizex / 2, framesizey);
		g.drawLine(0, framesizey / 2, framesizex, framesizey / 2);
		// set ratio at 16x
		for (int i = 0; i < array_train.size(); i++) {
			if (array_train.get(i)[2] == -1) {
				g.setColor(red);
				g.fillOval((Math.round(398 + (array_train.get(i)[0]) * 16)),
						Math.round((398 + (-array_train.get(i)[1]) * 16)), 4, 4);
			} // use math round get round
			else if (array_train.get(i)[2] == 1) {
				g.setColor(blue);
				g.fillOval((Math.round(398 + (array_train.get(i)[0]) * 16)),
						Math.round((398 + (-array_train.get(i)[1]) * 16)), 4, 4);
			} // use math round get round
		}

		// g.fillOval(400-5, 400-5, 10, 10);
	}

}