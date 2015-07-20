package com.cai310.lottery.utils.analyse.ssc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.cai310.lottery.utils.analyse.LottoGameUtils;





public class BaseDataBuilder {

	static String[] caption = {"0","1","2","3","4","5","6","7","8","9"};
	static String[] exHezhi = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
	static String[] sxHezhi = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"};
	static double YXZX = 10;
	static double EXZX = 100;
	static double SXZX = 1000;

	
	public HashMap  execute(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        HashMap map = new HashMap();
        //单个位置遗漏 顺选
        map.put("w1",lgu.execute(caption, resultList, 5,YXZX));
               
		return map;
	}
	//二星直选
	public HashMap  exzx(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        HashMap map = new HashMap();
        String[] source = groupDirectLast2Balls(caption);
        ArrayList result = (ArrayList) filterLast2Num(resultList);
        //单个位置遗漏 顺选
        map.put("w2",lgu.execute(source, result, 0,EXZX));
               
		return map;
	}
	//二星组选
	public HashMap  exgx(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        HashMap map = new HashMap();
        String[] source = groupLast2Balls(caption);
        ArrayList result = (ArrayList) filterLast2Num(resultList);
        //单个位置遗漏 顺选
        map.put("z2",lgu.execute(source, result, 0,EXZX));
               
		return map;
	}
	
	//三星直选
	public HashMap  sxzx(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        HashMap map = new HashMap();
        String[] source = groupDirectLast3Balls(caption);
        ArrayList result = (ArrayList) filterLast3Num(resultList);
        //单个位置遗漏 顺选
        map.put("w3",lgu.execute(source, result, 0,SXZX));
               
		return map;
	}
	
	/**
	 * 二星和值
	 * @param resultList
	 * @return
	 */
	public Vector<ArrayList> exHezhi(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();    
        HashMap map = new HashMap();
        ArrayList tempList = new ArrayList();
        for(String data:resultList){
			String[] temp = data.split(",");
			ArrayList list = new ArrayList();
			String sum = "";
        	Integer num1 = new Integer(temp[3]);
        	Integer num2 = new Integer(temp[4]);
        	sum+=(num1+num2);
        	tempList.add(sum);
        	
        }
		return lgu.execute(exHezhi, tempList, 0,EXZX);
	}
	
	
	/**
	 * 三星和值
	 * @param resultList
	 * @return
	 */
	public Vector<ArrayList> sxHezhi(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();    
        HashMap map = new HashMap();
        ArrayList tempList = new ArrayList();
        for(String data:resultList){
			String[] temp = data.split(",");
			ArrayList list = new ArrayList();
			String sum = "";
        	Integer num0 = new Integer(temp[2]);
        	Integer num1 = new Integer(temp[3]);
        	Integer num2 = new Integer(temp[4]);
        	sum+=(num0+num1+num2);
        	tempList.add(sum);
        	
        }
		return lgu.execute(sxHezhi, tempList, 0,SXZX);
	}
	
	/**
	 * 个位十位两个数组选的集合
	 * @param caption
	 * @return
	 */
	public String[] groupLast2Balls(String[] caption){
		int size = 0;
        for(int i=0;i<caption.length;i++){
        	for(int j=i+1;j<caption.length;j++){
        		size+=1;
        	}
        }
           
        String[] source = new String[size];

        int index=0;
    	for(int j=0;j<caption.length;j++){    		
    		for(int k=j+1;k<caption.length;k++){
    			source[index]=caption[j]+" "+caption[k];
    			if(++index>size)break;	
    		}
    	}
    	return source;
	}
	/**
	 * 个位十位两个数直选的集合
	 * @param caption
	 * @return
	 */
	public String[] groupDirectLast2Balls(String[] caption){
		int size = caption.length*caption.length;   
           
        String[] source = new String[size];

        int index=0;
    	for(int j=0;j<caption.length;j++){    		
    		for(int k=0;k<caption.length;k++){
    			source[index]=caption[j]+" "+caption[k];
    			if(++index>size)break;	
    		}
    	}
    	return source;
	}
	public List filterLast2Num(List<String> resultList){
		List<String> tmpList = new ArrayList();

		//处理后两个号(即十位个位)
		for(String row:resultList){
			String tempRow = row.substring(6,row.length());
			String[] temp = tempRow.split(",");
			String result = "";
			ArrayList w2List = new ArrayList();
			for(String ele:temp){
				w2List.add(ele);
			}
			Collections.sort(w2List);
			for(int n=0;n<w2List.size();n++){
				result+=w2List.get(n);
				if(n<w2List.size()-1){
					result+=" ";
				}
			}
			tmpList.add(result);
		}
		return tmpList;
	}
	
	/**
	 * 百位,十位,个位三个数直选的集合
	 * @param caption
	 * @return
	 */
	public String[] groupDirectLast3Balls(String[] caption){
		int size = caption.length*caption.length*caption.length;
		    
        String[] source = new String[size];

        int index=0;
        for(int t=0;t<caption.length;t++){
    		for(int j=0;j<caption.length;j++){    		
        		for(int k=0;k<caption.length;k++){
        			source[index]=caption[t]+" "+caption[j]+" "+caption[k];
        			if(++index>size)break;	
        		}
        	}
    	}	
    	return source;
	}

	public List filterLast3Num(List<String> resultList){
		List<String> tmpList = new ArrayList();
   	 	//处理后三个号(百位十位个位)
		tmpList = new ArrayList();
		for(String row:resultList){
			String tempRow = row.substring(4,row.length());
			String[] temp = tempRow.split(",");
			ArrayList w3List = new ArrayList();
			for(String ele:temp){
				w3List.add(ele);
			}
			Collections.sort(w3List);
			String result = "";
			for(int n=0;n<w3List.size();n++){
				result+=w3List.get(n);
				if(n<w3List.size()-1){
					result+=" ";
				}
			}
			tmpList.add(result);
		}	
		return tmpList;
	}
	public static void main(String [] args){
		List list = new ArrayList();
		list.add("08,02,03,04,01");
		list.add("04,05,06,07,08");
		list.add("09,10,11,04,05");
		list.add("01,03,05,04,07");
		list.add("01,03,02,04,05");
		list.add("01,02,03,04,05");
		list.add("01,03,07,04,05");
		list.add("01,03,05,04,05");
		list.add("01,02,03,04,05");
		list.add("13,15,16,17,19");
		//"01","03","05","07","09","11
		LottoGameUtils lgu = new LottoGameUtils();
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
