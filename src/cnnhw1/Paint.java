package cnnhw1;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.geom.*;
public class Paint extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float[] initial;
	private float threshold;
	private float liney1;
	private float liney2;
	private ArrayList<float[]> array_input = new ArrayList<float[]>();
	
	public Paint(ArrayList<float[]> array_input,float[] initial,float threshold,float liney1,float liney2){
		this.array_input=array_input;
		this.initial=initial;
		this.threshold=threshold;
		this.liney1=liney1;
		this.liney2=liney2;
		
	}//need trans to constructor
	

	
	Color red = new Color(255,0,0);
	Color green = new Color(0,255,0);
	Color blue = new Color(0,0,255);
	Color black = new Color(0,0,0);

	  @Override
	  protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   System.out.println("------------"+liney1);
	   System.out.println("------------"+liney2);
	   
       Graphics2D g2d = (Graphics2D) g;
       g.setColor(green);
       g2d.draw(new Line2D.Float(800, 400+(-liney1*16), 0, 400+(-liney2*16)));

       g.setColor(black);
	   g.drawLine(400,0,400,800);
	   g.drawLine(0, 400, 800, 400);
	   //set ratio at 16x
	   for(int i =0;i<array_input.size();i++){
		   if(array_input.get(i)[2]==-1){
			   g.setColor(red);   
			   g.fillOval((int)(398+(array_input.get(i)[0])*16),(int)(398+(-array_input.get(i)[1])*16), 4, 4);
		   }
		   else if(array_input.get(i)[2]==1){
			   g.setColor(blue);   
			   g.fillOval((int)(398+(array_input.get(i)[0])*16),(int)(398+(-array_input.get(i)[1])*16), 4, 4);   
		   }
	   }


//	   g.fillOval(400-5, 400-5, 10, 10);
	  }
	  
}