package cnnhw1;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Paint extends JPanel{

	private float[] refcoordinate;
	private int length;
	private ArrayList<float[]> array = new ArrayList<float[]>();
	
	public Paint(ArrayList<float[]> array){
		this.array=array;
	}//need trans to constructor
	
	  @Override
	  protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   for(int i =0;i<array.size();i++){
		   
		   g.fillOval((int)(395+(array.get(i)[0])*10),(int)(395+(-array.get(i)[1])*10), 10, 10);
	   }
	   g.drawLine(400,0,400,800);
	   g.drawLine(0, 400, 800, 400);
	   g.fillOval(400-5, 400-5, 10, 10);
	  }
	  
}