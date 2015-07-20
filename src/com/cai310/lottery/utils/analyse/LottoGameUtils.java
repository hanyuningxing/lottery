package com.cai310.lottery.utils.analyse;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.mortbay.log.Log;

import com.cai310.lottery.utils.BigDecimalUtil;


public class LottoGameUtils {

    private int REDBALLCOUNT=11,UBBASIC=0;
    private String redBall = "";
    private DecimalFormat df = new DecimalFormat("0.00");
    
	/**
	 * 乐透遗漏数据生成
	 * @param resultList
	 * @param pos 位置，0则为所有位置
	 * @return
	 */
	public Vector<ArrayList>  execute(String[] allBall , List resultList, int pos,double cycle){
		
		//处理ball 对应的index 
		HashMap<String,Integer> ballMap = new HashMap<String,Integer>();
		REDBALLCOUNT = allBall.length;
		for(int i =0; i < REDBALLCOUNT; i++)
		{
			ballMap.put(allBall[i], i);
		}
		
	       Vector<ArrayList> noramarVector = new Vector<ArrayList>();
	        ArrayList lines = null;
	        ArrayList<String> balls = new ArrayList<String>();
	        
	        int[] pano_counts = new int[REDBALLCOUNT];
	        int[] pano_currLost = new int[REDBALLCOUNT];
	        int[] pano_maxLost = new int[REDBALLCOUNT];
	        int[] pano_lastLost = new int[REDBALLCOUNT];
	        
	        double[] pano_average = new double[REDBALLCOUNT];
	        int ballIndex = -1;
	        String tempValue ="";

	        
	        //process and render...
	        for(int i=0;i<resultList.size();i++){
	            redBall = resultList.get(i).toString();
	            balls.clear();
	            redBall=redBall.split("#")[0];
	            String [] codes =redBall.split("\\,");
	            if(pos > 0){
	            	String t = codes[pos -1];
	            	codes = new String[1];
	            	codes[0] = t;
	            }
	            UBBASIC = codes.length;
	            
	            for(int k=0;k<UBBASIC;k++) balls.add(codes[k]);
	            
	            for(int v=0;v<UBBASIC;v++){
	                ballIndex = ballMap.get(balls.get(v));
	                	    	        
	                pano_counts[ballIndex]++;
	                pano_lastLost[ballIndex] = pano_currLost[ballIndex];
	                for(int j=0;j<REDBALLCOUNT ; j++){
	                    tempValue = allBall[j];
	                    if (!balls.contains(tempValue)){                	                 	
	                    	pano_currLost[j]++;
	                    }
	                }
	                if (pano_maxLost[ballIndex]<pano_currLost[ballIndex]) 
	                	pano_maxLost[ballIndex]=pano_currLost[ballIndex];

	                pano_currLost[ballIndex] = 0;
	            }
	           
	        }
	        for(int q=0;q<REDBALLCOUNT;q++){
	        	if (pano_maxLost[q]<pano_currLost[q]) 
                	pano_maxLost[q]=pano_currLost[q];
	        }
	        for(int q=0;q<REDBALLCOUNT;q++){
	            lines = new ArrayList();
	            /**
号码，出现次数，出现机率，最大遗漏，最新遗漏，平均遗漏,欲出几率,投资价值,回补几率,理论次数。
	             */
	            lines.add(allBall[q]);
	            lines.add(pano_counts[q]);
	            if(resultList.size()==0){
		            lines.add(0);
	            }else{
		            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_counts[q]), new java.math.BigDecimal(resultList.size())));
	            }  
            	//红球最大遗漏
            	lines.add(UBBASIC==0?0:pano_maxLost[q] / UBBASIC);
	            //红球最新遗漏
	            lines.add(UBBASIC==0?0:pano_currLost[q] / UBBASIC);
	            
	            //红球平均遗漏 Double.valueOf(df.format( (pano_counts[q]==0)? 0.0d : 1.0*(resultList.size()-pano_counts[q])/pano_counts[q]))
	            pano_average[q] = (pano_counts[q]==0)? 0.00d :BigDecimalUtil.divide(new java.math.BigDecimal(resultList.size()-pano_counts[q]), new java.math.BigDecimal(pano_counts[q]+1)).doubleValue();

	            lines.add(pano_average[q]);

	            if(pano_average[q] < 0.01){
	            	lines.add(Double.valueOf(0d));
	            }else{
		            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_currLost[q]/ UBBASIC), new java.math.BigDecimal(pano_average[q])));
	            }
	            
	            if (Math.abs(pano_average[q]-pano_currLost[q])<=5 || Math.abs(pano_currLost[q] - pano_maxLost[q])<=5)
	                lines.add("yes");
	            else
	                lines.add("no");
	         	lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_lastLost[q]), new java.math.BigDecimal(UBBASIC)));
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_currLost[q]/UBBASIC), new java.math.BigDecimal(cycle)));
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_lastLost[q]-pano_currLost[q]), new java.math.BigDecimal(cycle)));
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(resultList.size()), new java.math.BigDecimal(cycle)));
	            noramarVector.add(lines);
	        }
	        return noramarVector;
	}
	
	
	/**
	 * 奇偶统计
	 * @param resultList
	 * @return
	 */
	public ArrayList  oddEvenSummary(String[] allBall,List<String> resultList,int pos,double cycle){
         ArrayList<String> balls = new ArrayList<String>();
         int pano_counts = 0;
	     int pano_currLost = 0;
	     int pano_maxLost = 0;
	     int pano_lastLost = 0;
	     int maxLost = 0;
	     double pano_average = 0d;
	     
		 for(int i=0;i<resultList.size();i++){
	            redBall = resultList.get(i).toString();
	            balls.clear();
	            redBall=redBall.split("#")[0];
	            String [] codes =redBall.split("\\,");
	            	            
	            for(int k=0;k<codes.length;k++) balls.add(codes[k]);
            	boolean flag = false;
            	int count = 0;
	            for(String ball:balls){
	            	for(String code:allBall){
	            		if(ball.equals(code)){
	            			flag = true;          		
	            		}
	            	}
	            }
	            if(flag){	     
	            	pano_lastLost = pano_currLost;
	            	pano_currLost = 0;
	            	pano_counts++;
	            }else {
	            	pano_currLost++;    
	            }
	            if(pano_maxLost<pano_currLost)
	       			pano_maxLost = pano_currLost;
	            	
		 }
   		
   		ArrayList lines = new ArrayList();
       /**
出现次数，出现机率，最大遗漏，最新遗漏，平均遗漏,欲出几率。
        */
       lines.add(resultList.size()==0?0:pano_counts);
       lines.add(resultList.size()==0?0.00d:BigDecimalUtil.divide(new java.math.BigDecimal(pano_counts), new java.math.BigDecimal(resultList.size())));
        	   
       //红球最大遗漏
       lines.add(resultList.size()==0?resultList.size():pano_maxLost);
       //红球最新遗漏
       lines.add(resultList.size()==0?resultList.size():pano_currLost);
       //红球平均遗漏
       pano_average = (pano_counts==0)? 0.00d :BigDecimalUtil.divide(new java.math.BigDecimal(resultList.size()-pano_counts), new java.math.BigDecimal(pano_counts+1)).doubleValue();

       lines.add(pano_average);

       if(pano_average < 0.01){
       		lines.add(Double.valueOf(0.00d));
       }else{
           lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_currLost), new java.math.BigDecimal(pano_average)).doubleValue());
       }       
       
       lines.add(pano_lastLost);
       lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_currLost), new java.math.BigDecimal(resultList.size())).doubleValue());
       lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_lastLost-pano_currLost), new java.math.BigDecimal(cycle)).doubleValue());

	   return lines;
	}
    
	
	public static void main(String [] args)throws Exception{
		List list = new ArrayList();
		//*10-4
		list.add("01,02,03,04,05");
		list.add("02,03,04,06,07");
		list.add("03,05,07,09,10");
		list.add("02,04,08,09,03");
		list.add("04,06,07,08,09");
		list.add("01,05,03,04,08");
		list.add("04,08,06,07,09");
		list.add("03,05,06,09,10");
		list.add("03,05,06,07,08");
		list.add("06,07,08,09,11");
		//*   出现次数    当前遗漏    最大遗漏  
		//01    1         1         1
		//02    1         1         1
		//03    1         1         1
		//04    2         0         0
		//05    2         0         0
		//06    1         0         1
		//07    1         0         1
		//08    1         0         1
		/*
		list.add("1,2,3,4,5");
		list.add("1,2,3,4,5");
		list.add("1,2,3,4,5");
		list.add("1,2,3,4,5");
		list.add("6,7,8,9,0");
		//*/
		/*Vector<ArrayList> noramarVector = new LottoGameUtils().commonTailNum(new String[]{"01","02","03","04","05","06","07","08","09","10","11"},list,0);
		for(int i=0;i<noramarVector.size();i++){		
			System.out.print(noramarVector.get(i));
			System.out.println();
		}*/
		//号码，出现次数，出现机率，最大遗漏，最新遗漏，平均遗漏,欲出几率。
		//System.out.println(new LottoGameUtils().oddEvenSummary(new String[]{"02","04","06","08","10","12"}, list, 2));
	}

}
