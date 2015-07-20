package com.cai310.lottery.utils.analyse.welfare36to7;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.utils.analyse.LottoGameUtils;





public class BaseDataBuilder {

	static String[] caption = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"};
	static String[] sxCaption = {"01 13 25","02 14 26","03 15 27","04 16 28","05 17 29","06 18 30","07 19 31","08 20 32","09 21 33","10 22 34","11 23 35","12 24 36"};
	
	static String[] wsCaption = {"0","1","2","3","4","5","6","7","8","9"};
	static String[] hsCaption = {"1","2","3","4","5","6","7","8","9","10","11"};

	static String[] wxCaption = {"09 10 17 18 25 26","07 08 21 22 29 30","05 06 13 14 27 28 35 36","01 02 15 16 23 24 31 32","03 04 11 12 19 20 33 34"};
	static String[] xiaoCaption = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18"};
	static String[] daCaption = {"19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"};
	static String[] danCaption = {"01","03","05","07","09","11","13","15","17","19","21","23","25","27","29","31","33","35"};
	static String[] shuangCaption = {"02","04","06","08","10","12","14","16","18","20","22","24","26","28","30","32","34","36"};
	static String[] xiaodanCaption = {"01","03","05","07","09","11","13","15","17"};
	static String[] xiaoshuangCaption = {"02","04","06","08","10","12","14","16","18"};
	static String[] dadanCaption = {"19","21","23","25","27","29","31","33","35"};
	static String[] dashuangCaption = {"20","22","24","26","28","30","32","34","36"};

	static String[] sxHezhi = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"};
	static double HAOMA = 36;
	static double SHENGXIAO = 12;
	static double[] WEISHU = new double[]{12,5,7,9,12,4,3,1,10,7}; 
	static double[] HESHU = new double[]{12,5,7,9,12,4,3,1,10,7,10}; 
	static double[] WX = new double[]{6,6,6,4.5,4}; 
	static double XT = 2; 


	public HashMap  execute(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        HashMap map = new HashMap();
        //单个位置遗漏 顺选
        map.put("w1",lgu.execute(caption, resultList, 0,HAOMA));
               
		return map;
	}
	//生肖遗漏
	public HashMap  sxyl(List<String> resultList){
		//鼠-01、13、25 　　牛-02、14、26
		//虎-03、15、27 　　兔-04、16、28
		//龙-05、17、29　　 蛇-06、18、30
		//马-07、19、31　　 羊-08、20、32
		//猴-09、21、33　　 鸡-10、22、34
		//狗-11、23、35 　　猪-12、24、36 
		LottoGameUtils lgu = new LottoGameUtils();
        HashMap map = new HashMap();

		ArrayList result = new ArrayList();
		for(String ball:resultList){
			StringBuffer buffer = new StringBuffer();
			Integer num = new Integer(ball);
			if(num>0&&num<=12){
				buffer.append(ball).append(" ").append(num+12).append(" ").append(num+24);
			}else if(num>12&&num<22){
				buffer.append("0").append(num-12).append(" ").append(ball).append(" ").append(num+12);
			}else if(num>=22&&num<=24){
				buffer.append(num-12).append(" ").append(ball).append(" ").append(num+12);
			}else if(num>24&&num<34){
				buffer.append("0").append(num-24).append(" ").append(num-12).append(" ").append(ball);
			}else{
				buffer.append(num-24).append(" ").append(num-12).append(" ").append(ball);
			}
			result.add(buffer.toString());
		}
        map.put("sx",lgu.execute(sxCaption, result, 0,SHENGXIAO));       
		return map;
	}
	//尾数遗漏
	public Vector<ArrayList>  wsyl(List<String> resultList){  
		ArrayList result = new ArrayList();
		for(String weishu:resultList){
			Integer ws = Integer.parseInt(weishu)%10;
			result.add(ws.toString());
		}
		Vector<ArrayList> list = wsyl(wsCaption,result,0,WEISHU);            
		return list;
	}
	//合数遗漏
	public Vector<ArrayList>  hsyl(List<String> resultList){  
		ArrayList result = new ArrayList();
		for(String weishu:resultList){
			Integer num = Integer.parseInt(weishu);
			if(num>1&&num<10){
				Integer ws = Integer.parseInt(weishu);
				result.add(ws.toString());
			}else{
				Integer shi = num%10;
				Integer ge = num/10;
				Integer hs = shi+ge;
				result.add(hs.toString());
			}
		}
		Vector<ArrayList> list = wsyl(hsCaption,result,0,HESHU);            
		return list;
	}
	//五行遗漏
	public Vector<ArrayList>  wxyl(List<String> resultList){  
		ArrayList result = new ArrayList();
		for(String weishu:resultList){
			for(String wx:wxCaption){
				if(wx.indexOf(weishu)!=-1){
					result.add(wx);
					break;
				}
			}
		}
		Vector<ArrayList> list = wsyl(wxCaption,result,0,WX);            
		return list;
	}
	private Vector<ArrayList>  wsyl(String[] allBall , List resultList, int pos,double[] cycle){
		int REDBALLCOUNT=11,UBBASIC=0;
	    String redBall = "";
	    DecimalFormat df = new DecimalFormat("0.00");
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
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_currLost[q]/UBBASIC), new java.math.BigDecimal(cycle[q])));
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(pano_lastLost[q]-pano_currLost[q]), new java.math.BigDecimal(cycle[q])));
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(resultList.size()), new java.math.BigDecimal(cycle[q])));
	            noramarVector.add(lines);
	        }
	        return noramarVector;
	}
	//形态遗漏
	public HashMap xtyl(List<String> resultList){	
		LottoGameUtils lgu = new LottoGameUtils();
		HashMap map = new HashMap();
		
		map.put("xs", lgu.execute(xiaoCaption, resultList, 0,XT));
		map.put("ds", lgu.execute(daCaption, resultList, 0,XT));
		map.put("dans", lgu.execute(danCaption, resultList, 0,XT));
		map.put("ss", lgu.execute(shuangCaption, resultList, 0,XT));
		map.put("xd", lgu.execute(xiaodanCaption, resultList, 0,XT));
		map.put("xs", lgu.execute(xiaoshuangCaption, resultList, 0,XT));
		map.put("dd", lgu.execute(dadanCaption, resultList, 0,XT));
		map.put("dss", lgu.execute(dashuangCaption, resultList, 0,XT));

		return map;
	}
	
	
	public Vector<ArrayList> dxyl(List<String> resultList){
		//小数: 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18
		//大数: 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36
		//单数: 01 03 05 07 09 11 13 15 17 19 21 23 25 27 29 31 33 35
		//双数: 02 04 06 08 10 12 14 16 18 20 22 24 26 28 30 32 34 36
		//小单: 01 03 05 07 09 11 13 15 17
		//小双: 02 04 06 08 10 12 14 16 18
		//大单: 19 21 23 25 27 29 31 33 35
		//大双: 20 22 24 26 28 30 32 34 36
		String[] types = new String[]{"xs","ds","dans","shuangs","xiaod","xiaos","dad","das"};
		Integer[] cycles = new Integer[]{2,2,2,2,4,4,4,4};
		HashMap curLostMap = new HashMap();
		HashMap curPanoMap = new HashMap();
		HashMap maxLostMap = new HashMap();
		HashMap lastLostMap = new HashMap();
		
		for(String type:types){
			curLostMap.put(type, 0);
			curPanoMap.put(type, 0);
			maxLostMap.put(type, 0);
			lastLostMap.put(type, 0);
		}

		for(String ball:resultList){
			int num = Integer.parseInt(ball);
			if(num>=1&&num<=18){
				curLostMap.put("ds", (Integer)curLostMap.get("ds")+1);

				curPanoMap.put("xs", (Integer)curPanoMap.get("xs")+1);
				lastLostMap.put("xs", curLostMap.get("xs"));
				if((Integer)maxLostMap.get("xs")<(Integer)curLostMap.get("xs")){
					maxLostMap.put("xs", (Integer)curLostMap.get("xs"));
				}
				curLostMap.put("xs", 0);
			}else{
				curLostMap.put("xs", (Integer)curLostMap.get("xs")+1);

				curPanoMap.put("ds", (Integer)curPanoMap.get("ds")+1);
				lastLostMap.put("ds", curLostMap.get("ds"));
				if((Integer)maxLostMap.get("ds")<(Integer)curLostMap.get("ds")){
					maxLostMap.put("ds", (Integer)curLostMap.get("ds"));
				}
				curLostMap.put("ds", 0);

			}	
			
			if(num%2==0){
				curLostMap.put("dans", (Integer)curLostMap.get("dans")+1);

				curPanoMap.put("shuangs", (Integer)curPanoMap.get("shuangs")+1);
				lastLostMap.put("shuangs", curLostMap.get("shuangs"));
				if((Integer)maxLostMap.get("shuangs")<(Integer)curLostMap.get("shuangs")){
					maxLostMap.put("shuangs", (Integer)curLostMap.get("shuangs"));
				}
				curLostMap.put("shuangs", 0);
			}else{
				curLostMap.put("shuangs", (Integer)curLostMap.get("shuangs")+1);

				curPanoMap.put("dans", (Integer)curPanoMap.get("dans")+1);
				lastLostMap.put("dans", curLostMap.get("dans"));
				if((Integer)maxLostMap.get("dans")<(Integer)curLostMap.get("dans")){
					maxLostMap.put("dans", (Integer)curLostMap.get("dans"));
				}
				curLostMap.put("dans", 0);

			}
			//小单
			if(num>=1&&num<=17&&num%2==1){//
				curLostMap.put("xiaos", (Integer)curLostMap.get("xiaos")+1);
				curLostMap.put("dad", (Integer)curLostMap.get("dad")+1);
				curLostMap.put("das", (Integer)curLostMap.get("das")+1);

				curPanoMap.put("xiaod", (Integer)curPanoMap.get("xiaod")+1);
				lastLostMap.put("xiaod", curLostMap.get("xiaod"));
				if((Integer)maxLostMap.get("xiaod")<(Integer)curLostMap.get("xiaod")){
					maxLostMap.put("xiaod", (Integer)curLostMap.get("xiaod"));
				}
				curLostMap.put("xiaod", 0);
			}else if(num>=2&&num<=18&&num%2==0){//小双
				curLostMap.put("xiaod", (Integer)curLostMap.get("xiaod")+1);
				curLostMap.put("dad", (Integer)curLostMap.get("dad")+1);
				curLostMap.put("das", (Integer)curLostMap.get("das")+1);
				
				curPanoMap.put("xiaos", (Integer)curPanoMap.get("xiaos")+1);
				lastLostMap.put("xiaos", curLostMap.get("xiaos"));
				if((Integer)maxLostMap.get("xiaos")<(Integer)curLostMap.get("xiaos")){
					maxLostMap.put("xiaos", (Integer)curLostMap.get("xiaos"));
				}
				curLostMap.put("xiaos", 0);
			}else if(num>=19&&num<=35&&num%2==1){//大单 
				curLostMap.put("xiaod", (Integer)curLostMap.get("xiaod")+1);
				curLostMap.put("xiaos", (Integer)curLostMap.get("xiaos")+1);
				curLostMap.put("das", (Integer)curLostMap.get("das")+1);
				
				curPanoMap.put("dad", (Integer)curPanoMap.get("dad")+1);
				lastLostMap.put("dad", curLostMap.get("dad"));
				if((Integer)maxLostMap.get("dad")<(Integer)curLostMap.get("dad")){
					maxLostMap.put("dad", (Integer)curLostMap.get("dad"));
				}
				curLostMap.put("dad", 0);
			}else if(num>=20&&num<=36&&num%2==0){//"xiaos","dad","das" "xiaos","dad","das" 20 22 24 26 28 30 32 34 36
				curLostMap.put("xiaod", (Integer)curLostMap.get("xiaod")+1);
				curLostMap.put("xiaos", (Integer)curLostMap.get("xiaos")+1);
				curLostMap.put("dad", (Integer)curLostMap.get("dad")+1);
				
				curPanoMap.put("das", (Integer)curPanoMap.get("das")+1);
				lastLostMap.put("das", curLostMap.get("das"));
				if((Integer)maxLostMap.get("das")<(Integer)curLostMap.get("das")){
					maxLostMap.put("das", (Integer)curLostMap.get("das"));
				}
				curLostMap.put("das", 0);
			}
			
		}
		/*System.out.println("大数当前遗漏:"+curLostMap.get("ds")+"  出现次数:"+curPanoMap.get("ds")+"  最大遗漏:"+maxLostMap.get("ds")+"  上次遗漏:"+lastLostMap.get("ds"));
		System.out.println("小数当前遗漏:"+curLostMap.get("xs")+"  出现次数:"+curPanoMap.get("xs")+"  最大遗漏:"+maxLostMap.get("xs")+"  上次遗漏:"+lastLostMap.get("xs"));
		System.out.println("单数当前遗漏:"+curLostMap.get("dans")+"  出现次数:"+curPanoMap.get("dans")+"  最大遗漏:"+maxLostMap.get("dans")+"  上次遗漏:"+lastLostMap.get("dans"));
		System.out.println("双数当前遗漏:"+curLostMap.get("shuangs")+"  出现次数:"+curPanoMap.get("shuangs")+"  最大遗漏:"+maxLostMap.get("shuangs")+"  上次遗漏:"+lastLostMap.get("shuangs"));
		System.out.println("小单当前遗漏:"+curLostMap.get("xiaod")+"  出现次数:"+curPanoMap.get("xiaod")+"  最大遗漏:"+maxLostMap.get("xiaod")+"  上次遗漏:"+lastLostMap.get("xiaod"));
		System.out.println("小双当前遗漏:"+curLostMap.get("xiaos")+"  出现次数:"+curPanoMap.get("xiaos")+"  最大遗漏:"+maxLostMap.get("xiaos")+"  上次遗漏:"+lastLostMap.get("xiaos"));
		System.out.println("大单当前遗漏:"+curLostMap.get("dad")+"  出现次数:"+curPanoMap.get("dad")+"  最大遗漏:"+maxLostMap.get("dad")+"  上次遗漏:"+lastLostMap.get("dad"));
		System.out.println("大双当前遗漏:"+curLostMap.get("das")+"  出现次数:"+curPanoMap.get("das")+"  最大遗漏:"+maxLostMap.get("das")+"  上次遗漏:"+lastLostMap.get("das"));
*/		
	    Vector<ArrayList> noramarVector = new Vector<ArrayList>();
		ArrayList lines = null;
        /**
号码，出现次数，出现机率，最大遗漏，最新遗漏，平均遗漏,欲出几率,投资价值,回补几率,理论次数。
		{"xs","ds","dans","shuangs","xiaod","xiaos","dad","das"};

         */
		int index=0;
		for(String type:types){
			lines = new ArrayList();
			lines.add(type);
	        lines.add(curPanoMap.get(type));
	        if(resultList.size()==0){
	            lines.add(0);
	        }else{
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal((Integer)curPanoMap.get(type)), new java.math.BigDecimal(resultList.size())));
	        }  
	    	
	    	lines.add(maxLostMap.get(type));
	        //红球最新遗漏
	        lines.add(curLostMap.get(type));
	        
	        //红球平均遗漏 Double.valueOf(df.format( (pano_counts[q]==0)? 0.0d : 1.0*(resultList.size()-pano_counts[q])/pano_counts[q]))
	        double pano_average = ((Integer)curPanoMap.get(type)==0)? 0.00d :BigDecimalUtil.divide(new java.math.BigDecimal(resultList.size()-(Integer)curPanoMap.get(type)), new java.math.BigDecimal((Integer)curPanoMap.get(type)+1)).doubleValue();

	        lines.add(pano_average);

	        if(pano_average < 0.01){
	        	lines.add(Double.valueOf(0d));
	        }else{
	            lines.add(BigDecimalUtil.divide(new java.math.BigDecimal((Integer)curLostMap.get(type)), new java.math.BigDecimal(pano_average)));
	        }
	        
	        if (Math.abs(pano_average-(Integer)curLostMap.get(type))<=5 || Math.abs((Integer)curLostMap.get(type) - (Integer)maxLostMap.get(type))<=5)
	            lines.add("yes");
	        else
	            lines.add("no");
	     	lines.add(BigDecimalUtil.divide(new java.math.BigDecimal((Integer)lastLostMap.get(type)),new java.math.BigDecimal(1)));
	        lines.add(BigDecimalUtil.divide(new java.math.BigDecimal((Integer)curLostMap.get(type)), new java.math.BigDecimal(cycles[index])));
	        lines.add(BigDecimalUtil.divide(new java.math.BigDecimal((Integer)lastLostMap.get(type)-(Integer)curLostMap.get(type)), new java.math.BigDecimal(cycles[index])));
	        lines.add(BigDecimalUtil.divide(new java.math.BigDecimal(resultList.size()), new java.math.BigDecimal(cycles[index])));
	        noramarVector.add(lines);
	        index++;
		}
        

		
		return noramarVector;
	}
	public static void main(String [] args){
		List list = new ArrayList();
		/*list.add("08,02,03,04,01");
		list.add("04,05,06,07,08");
		list.add("09,10,11,04,05");
		list.add("01,03,05,04,07");
		list.add("01,03,02,04,05");
		list.add("01,02,03,04,05");
		list.add("01,03,07,04,05");
		list.add("01,03,05,04,05");
		list.add("01,02,03,04,05");
		list.add("13,15,16,17,19");*/
		
		list.add("01");
		list.add("02");
		list.add("19");
		list.add("20");
		list.add("03");
		list.add("04");
		list.add("05");
		list.add("06");
		list.add("07");
		list.add("36");
		list.add("25");

		BaseDataBuilder builder = new BaseDataBuilder();
		builder.dxyl(list);
		//"01","03","05","07","09","11
		//LottoGameUtils lgu = new LottoGameUtils();
		//List result = new BaseDataBuilder().filterPrev3Num(list);
		//List result1 = lgu.oddEvenSummary(new BaseDataBuilder().groupFirst3Balls(minOdd), result, 0);
		//List result1 = new BaseDataBuilder().random3Num(maxEven,list);
		//HashMap result = new BaseDataBuilder().weixuan3(list);
		//HashMap result = new BaseDataBuilder().oddEvenSummary(minOdd, list, 3);
		//System.out.println(result1);
		//Iterator it = ((List)map.get("w2")).iterator();
		//Iterator it = map.keySet().iterator();
		//while(it.hasNext()){
		//	String key = it.next().toString();
		//	Vector v = (Vector)map.get(key);
		//	System.out.println(key+"==========="+v);
		//}
		
//		号码，出现次数，出现机率，最大遗漏，最新遗漏，平均遗漏,欲出几率。
	}
}
