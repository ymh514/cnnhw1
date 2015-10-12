package cnnhw1;

import java.io.*;
import java.math.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class cnnhw1 extends JFrame{ //目前這個程式碼主要先測試感知機1

	static ArrayList<float[]> array_input = new ArrayList<float[]>();
	static ArrayList<float[]> array_temp = new ArrayList<float[]>();	
	static ArrayList<float[]> array_train = new ArrayList<float[]>();
	static ArrayList<float[]> array_test = new ArrayList<float[]>();
		
//	static Scanner input = new Scanner(System.in);
	
	private static float eastxbound=400.0f;
	private static float westxbound=-400.0f;
	private static int framesizex=800;
	private static int framesizey=800;
	
	private static ArrayList<float[]> classify(ArrayList<float[]> array_input){
		
		int reference=(int) array_input.get(0)[array_input.get(0).length-1];
		for(int i=0;i<array_input.size();i++){
			if(array_input.get(i)[array_input.get(i).length-1]==reference){
				array_input.get(i)[array_input.get(i).length-1]=1;			}
			else{
				array_input.get(i)[array_input.get(i).length-1]=-1;		
			}		
		}//假裝是對的有時候要改 -1 1 記註記住 	
		return array_input;
	}	

	private static void flagdecide(int correctflag){
		if(correctflag==1){
			System.out.println("-----------------------");
			System.out.println("Find a good solution");
			System.out.println("-----------------------");
		}
		else{
			System.out.println("-----------------------");
			System.out.println("Sorry, out of looptimes");
			System.out.println("-----------------------");
		}		
	}
	
	private static void genarateframe(ArrayList<float[]> array_input,float[] initial,float threshold,float liney1,float liney2,int framesizex,int framesizey){
		JFrame frame = new JFrame();

		frame.setVisible(true);//just set visible
		frame.setSize(framesizex, framesizey);//set the frame size
		frame.setLocation(100,100);//set the frame show location
		frame.setResizable(false);

		Paint trypaint = new Paint(array_input,initial,threshold,liney1,liney2,framesizex,framesizey);
		frame.add(trypaint);//add paint(class) things in to the frame		
	}

	private static void printarraylist(ArrayList<float[]> array_input,ArrayList<float[]> array_temp,ArrayList<float[]> array_train,ArrayList<float[]> array_test){
		for(int i=0;i<array_input.size();i++){
			for(int j=0;j<array_input.get(i).length;j++){
			System.out.print(array_input.get(i)[j] + "\t");
			}
			System.out.println();
		}//把arraylist 內的內容都印出來
	}
	
	private static void printthresholdandinitial(float threshold,float[] initial){
		System.out.println("output threshold & weight");
		System.out.printf("%f\n",threshold);//use c language to print prevant loss ....??
		for(int w=0;w<initial.length;w++){
			System.out.printf("%f\n",initial[w]);//use c language to print prevant loss ....??
		}		
	}

	private static void putInputToTemp(ArrayList<float[]> array_input){
		int arrayinputamount=array_input.size();
		Random rand=new Random();
		while(arrayinputamount!=0){
			int n=rand.nextInt(arrayinputamount)+0;
			array_temp.add(array_input.get(n));
			arrayinputamount--;
		}
	}
	
	private static void separateTemp(ArrayList<float[]> array_temp){
		int totalamount=array_temp.size();
		int tocalamount=(totalamount*2)/3;
		int totestamount=totalamount-tocalamount;

		while(tocalamount!=0){
			array_train.add(array_temp.get(0));
			array_temp.remove(0);
			tocalamount--;
		}
		while(totestamount!=0){
			array_test.add(array_temp.get(0));
			array_temp.remove(0);
			totestamount--;
		}	
	}
	
	private static void printCorrectRatio(float[] initial,float threshold,ArrayList<float[]> array_test){
		int testcorrect=testCheck(initial,threshold,array_test);//call function(testCheck) to get correct
		int testamount=array_test.size();
		System.out.println("test amount is :"+testamount);
		System.out.println("right palce amount is :"+testcorrect);
		System.out.println("Test correct ratio is : "+((float)testcorrect/(float)testamount)*100+"%");	
	}
	
	private static float sumall(ArrayList<float[]> array,float[] initial,float caltemp,float threshold,float sum,int x0,int xn){
		for(int w=0;w<(array.get(xn).length-1);w++){//要用的只有前兩個 最後一個是desire
			sum += (initial[w]) * (array.get(xn)[w]);//做運算時把array內的職先轉乘float
		}
		caltemp=x0*threshold;
		sum += caltemp;	//閥值跟x0相乘最後再做		
		return sum;
	}	
	
	private static	float getY(float w0,float w1,float threshold,float x){
		float y;
		y=(threshold/w1)-(w0/w1)*(x/16);
		return y;
	}
	
	private static int testCheck(float[] initial,float threshold,ArrayList<float[]> array_test){
		//run calculate again but this time just count correcttimes
		float caltemp=0f;
		float judge=0f;
		int x0=-1;
		int testcorrect=0;
		int testamount=array_test.size();
		int wrongflag=0;
		for(int i=0;i<testamount;i++){
			float sum=0f;
				for(int w=0;w<(array_test.get(i).length-1);w++){//要用的只有前兩個 最後一個是desire
					sum += (initial[w]) * (array_test.get(i)[w]);//做運算時把array內的職先轉乘float
				}
				caltemp=x0*threshold;
				sum += caltemp;	//閥值跟x0相乘最後再做		

				judge = Math.signum(sum);//use math's sign function return value
				
				if (judge!=array_test.get(i)[array_test.get(i).length-1]&&judge>=0){
					wrongflag++;//just count,do nothing
				}
				else if(judge!=array_test.get(i)[array_test.get(i).length-1]&&judge<0){
					wrongflag++;//just count,do nothing
				}
				else{
					testcorrect++;
				}				
		}
		return testcorrect;
	}


	/*	 
	 *  1.load file & use split then trans to float wiz token2,then store into array
	 *  2.class defination
	 *  3.copy array_input to array_temp with random, then separate 2/3 as train dataset 1/3 as test set
	 *  4.get initial information
	 * 	5.do calculate & set some flag 
	 *  6.genarate line equation
	 *  7.check correct ratio(call function in printCorrctRatio) then print correctratio
	 *  8.generate gui 
	 */
	public static void main(String[] args) throws IOException{
	
	/*
	 * 1.load file & use split then trans to float wiz token2,then store into array
	 */
		String Filename = "E:\\1041\\cnn\\HW1\\dataset\\2Circle2.txt";
		FileReader fr = new FileReader(Filename); 
		BufferedReader br = new BufferedReader(fr);//在br.ready反查輸入串流的狀況是否有資料

		String txt;
		while((txt=br.readLine())!=null){
			
			/* If there is space before split(), it will cause the error
			 * So, we could to use trim() to remove the space at the beginning and the end.
			 * Then split the result, which doesn't include the space at the beginning and the end.
			 * "\\s+" would match any of space, as you don't have to consider the number of space in the string
			 */
			String[] token = txt.trim().split("\\s+");//<-----背起來
			//String[] token = txt.split(" ");//<-----original split
			
			float[] token2 = new float[token.length];//宣告float[]

			try{
				for(int i=0;i<token.length;i++){
					token2[i] = Float.parseFloat(token[i]);	
				}//把token(string)轉乘token2(float)
				array_input.add(token2);//把txt裡面內容先切割過在都讀進array內 
			}catch(NumberFormatException ex){
				System.out.println("Sorry Error...");
			}
		}
		fr.close();//關閉檔案
		
//		printarraylist(array_input);//print out all in arraylist

	/* 2.class defination 
	 * 先用一個基準值 如果>=1 那就直接用他設為1 其他不同設為-1
	 * 同理 如果一開始拿到0or-1 
	 */
		classify(array_input);//call function to classify
		
	/*
	 * 3.copy array_input to array_temp with random, then separate 2/3 as train dataset 1/3 as test set
	 */
		putInputToTemp(array_input);// copy to temp with random
		
		separateTemp(array_temp);//separate to train and test set,set 2/3 as train set 1/3 as test set


	/*
	 * 4.get initial information. maybe will change to top in the future. 
	 * 
	 */		
		
		// Erstellung Array vom Datentyp Object, Hinzufügen der Komponenten		
		JTextField usrlooptimes = new JTextField();
		JTextField usrstudyrate = new JTextField();
		JTextField usrthreshold = new JTextField();
        Object[] message = {"Looptimes (eg.50)", usrlooptimes,"Studyrate (eg.0.8)", usrstudyrate,"Threshold (eg.-1.0)",usrthreshold};

        JOptionPane pane = new JOptionPane( message,JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Initial Parameter").setVisible(true);		
	/*
	 * calculate initial value setting
	 */
		int looptimes = Integer.parseInt(usrlooptimes.getText());
		System.out.println("Your loop times is "+ looptimes);
		
		float studyrate= Float.parseFloat(usrstudyrate.getText());
		System.out.println("Your studyrate is "+ studyrate);
		
		float threshold= Float.parseFloat(usrthreshold.getText());
		System.out.println("Your threshold is "+ threshold);
		System.out.println("-----start to do calculate-----");

	/*
	 * 	5.do calculate & set some flag
	 */
		float[] initial={1.0f,-1.0f};
		int x0=-1;
		float caltemp=0f;
		float judge=0f;
		int xn=0;
		int dataamount = array_train.size();
		int correctcount=0;
		int correctflag = 0;
		
		whileloop:
			while(looptimes!=0){
				float sum=0f;//db2 sum is a register and you have to reset to zero.
				sum  =sumall(array_train,initial,caltemp,threshold,sum,x0,xn);//call function to sum all things
				
				judge = Math.signum(sum);//use math's sign function return value
		
				if (judge!=array_train.get(xn)[array_train.get(xn).length-1]&&judge>=0){
					for(int w=0;w<(array_train.get(xn).length-1);w++){//要用的只有前兩個 最後一個是desire
						initial[w] -= studyrate*array_train.get(xn)[w];
					}
					threshold -= studyrate*x0;			
					correctcount=0;
				}
				else if(judge!=array_train.get(xn)[array_train.get(xn).length-1]&&judge<0){
					for(int w=0;w<(array_train.get(0).length-1);w++){//要用的只有前兩個 最後一個是desire
						initial[w] += studyrate*array_train.get(xn)[w];
					}
					threshold += studyrate*x0;
					correctcount=0;
				}
				else{
					correctcount++;
				}
				
//				printthresholdandinitial(threshold,initial);
				
				if(correctcount==dataamount-1){
					correctflag=1;
					break whileloop;
				}//當趨近於收斂時給一個correctflag = 1  讓後面break之後的印可以印正確的資訊
							
				if (xn==dataamount-1){
					xn = 0;
				}
				else{
					xn++;
				}//xn 歸零重頭開始算
				looptimes--;//looptimes countdown
			}

		
		flagdecide(correctflag);//call function to decide
		

	/*
	 * 6.genarate line equation
	 */
		float liney1=getY(initial[0],initial[1],threshold,eastxbound);
		float liney2=getY(initial[0],initial[1],threshold,westxbound);
		
	/*
	 * 7.check correct ratio(call function in printCorrctRatio) then print correctratio
	 */
		printCorrectRatio(initial,threshold,array_test);


	/*
	 * 8.generate gui
	 */		
		
		genarateframe(array_train,initial,threshold,liney1,liney2,framesizex,framesizey);//call function to genarate frame

	}	

}