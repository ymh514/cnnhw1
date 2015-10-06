package cnnhw1;

import java.io.*;
import java.math.*;
import java.util.*;

public class cnnhw1{ //目前這個程式碼主要先測試感知機1
//	static ArrayList<ArrayList<String>> array_list = new ArrayList<ArrayList<String>>();
	static ArrayList<float[]> array = new ArrayList<float[]>();
	static Scanner input = new Scanner(System.in);
	
/*
	public static float sub(float s1,float s2){
		BigDecimal b1 = new BigDecimal(Float.toString(s1));
	    BigDecimal b2 = new BigDecimal(Float.toString(s2));
	    return b1.subtract(b2).floatValue();
	} //做運算的時後把值傳進來先用string儲存 bigdecimal 再用float傳出去
*/
	
	/*	1.load file
	 * 	2.use split and trans to float wiz token2,then store into array
	 * 	3.do cal 
	 * 
	 */
	public static void main(String[] args) throws IOException{
		String Filename = "E:\\1041\\cnn\\HW1\\1.txt";
		FileReader fr = new FileReader(Filename); 
		BufferedReader br = new BufferedReader(fr);//在br.ready反查輸入串流的狀況是否有資料

		String txt;
		while((txt=br.readLine())!=null){
			String[] token = txt.split("\t");
			float[] token2 = new float[token.length];//宣告float[]

			try{
				for(int i=0;i<token.length;i++){
					token2[i] = Float.parseFloat(token[i]);	
				}//把token(string)轉乘token2(float)
				array.add(token2);//把txt裡面內容先切割過在都讀進array內 
			}catch(NumberFormatException ex){
				System.out.println("Sorry Error...");
			}
		}
		fr.close();//關閉檔案
		
		System.out.println("Please enter loop times: ");//輸入迭代次數限制
		
		int looptimes = input.nextInt();
		System.out.println("Your loop times is "+ looptimes);
		for(int i=0;i<array.size();i++){
			for(int j=0;j<array.get(i).length;j++){
			System.out.print(array.get(i)[j] + "\t");
			}
			System.out.println();
		}//把arraylist 內的內容都印出來
		
		/*
		 * 以下開始做運算 先設定學習率跟初始設定跟課本一樣  
		 */
		/*
		 * 先用一個基準值 如果>=1 那就直接用他設為1 其他不同設為-1
		 * 同理 如果一開始拿到0or-1 
		 */
		int reference=Float.floatToIntBits(array.get(0)[array.get(0).length-1]);
		for(int i=0;i<array.size();i++){
			if(array.get(i)[array.get(i).length-1]==reference){
				array.get(i)[array.get(i).length-1]=-1;
			}
			else{
				array.get(i)[array.get(i).length-1]=1;
			}		
		}//假裝是對的

		System.out.println("start to do cal");
		float studyrate = 0.8f;
		float[] initial={0f,1.0f};
		float threshold= -1.0f;
		int x0=-1;
		float sum=0f;
		float caltemp=0f;
		int judge=0;
		int xn=0;
		int dataamount = array.size();
		int correctcount=0;
		int correctflag = 0;
		whileloop:
		while(looptimes!=0){
			
			for(int w=0;w<(array.get(xn).length-1);w++){//要用的只有前兩個 最後一個是desire
				sum += (initial[w]) * (array.get(xn)[w]);//做運算時把array內的職先轉乘float
			}
			caltemp=x0*threshold;
			sum += caltemp;	//閥值跟x0相乘最後再做
			if(sum>=0){
				judge=1;//sum result > 0
			}
			else{
				judge=-1;//sum result <0
			}
	
			if (judge!=array.get(xn)[array.get(xn).length-1]&&judge>=0){
				for(int w=0;w<(array.get(xn).length-1);w++){//要用的只有前兩個 最後一個是desire
					initial[w] -= studyrate*array.get(xn)[w];
				}
				threshold -= studyrate*x0;//call sub function 預防遺失精度			
				correctcount=0;
			}
			else if(judge!=array.get(xn)[array.get(xn).length-1]&&judge<0){
				for(int w=0;w<(array.get(0).length-1);w++){//要用的只有前兩個 最後一個是desire
					initial[w] += studyrate*array.get(xn)[w];	
				}
				threshold += studyrate*x0;
				correctcount=0;
			}
			else{
				correctcount++;
			}
			System.out.println("output threshold & weight");
			System.out.printf("%f\n",threshold);
			for(int w=0;w<initial.length;w++){
				System.out.printf("%f\n",initial[w]);
			}
			
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
			looptimes--;//looptime countdown
		}
		if(correctflag==1){
			System.out.println("Stop the loop cuz find a good solution");
		}
		else{
			System.out.println("Sorry, out of looptimes");
		}
	}
}
