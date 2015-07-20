package com.cai310.lottery.utils.analyse.qyh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.cai310.lottery.utils.analyse.LottoGameUtils;





public class BaseDataBuilder {

	static String[] caption = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	static String[] minOdd = {"01","03","05","07","09","11"};
	static String[] minEven = {"02","04","06","08","10","12"};
	static String[] maxOdd = {"13","15","17","19","21","23"};
	static String[] maxEven = {"14","16","18","20","22"};
	static String[] maxRandomOdd = {"01","03","05","07","09","11","13","15","17","19","21","23"};
	static String[] hezhi = {"03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23",
			"24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45"};
	static String[] lianhao={"01 02","02 03","03 04","04 05","05 06","06 07","07 08","08 09","09 10","10 11","11 12","12 13","13 14","14 15",
							"15 16","16 17","17 18","18 19","19 20","20 21","21 22","22 23"};
	static String[] commomTailNum = {"01 11","01 21","02 12","02 22","03 13","03 23","04 14","05 15","06 16","07 17","08 18","09 19","10 20","11 21","12 22","13 23"};
	static double W2CYCLE = 253;
	static double W3CYCLE = 1771;
	static double R1CYCLE = 4.6;
	static double R2CYCLE = 25.3;
	static double R3CYCLE = 177.1;
	static double R4CYCLE = 2213.75;
	static double R5CYCLE = 33649;

	
	public HashMap  execute(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        HashMap map = new HashMap();
        //基本遗漏
        map.put("rx",lgu.execute(caption, resultList, 0,W2CYCLE));
        //单个位置遗漏 顺选
        map.put("w1",lgu.execute(caption, resultList, 1,W2CYCLE));
        map.put("w2",lgu.execute(caption, resultList, 2,W2CYCLE));
        map.put("w3",lgu.execute(caption, resultList, 3,W2CYCLE));
       
        List<String> tmpList = new ArrayList();
        //处理前四个号(前四组选)
		for(String row:resultList){
			tmpList.add(row.substring(0,11));
		}	
		map.put("z4",lgu.execute(caption, tmpList, 0,W2CYCLE));
		tmpList = null;
        
        //处理前三个号(前三组选)
		tmpList = new ArrayList();
		for(String row:resultList){
			tmpList.add(row.substring(0,8));
		}	
		map.put("z3",lgu.execute(caption, tmpList, 0,W3CYCLE));
		tmpList = null;
		
        //处理前两个号(前二组选)
		tmpList = new ArrayList();
		for(String row:resultList){
			tmpList.add(row.substring(0,5));
		}	
		resultList = null;
		
		map.put("z2",lgu.execute(caption, tmpList, 0,W3CYCLE));
		tmpList = null;
       
		return map;
	}
	/**
	 * 围选2
	 * @param resultList
	 * @return
	 */
	public HashMap  weixuan2(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();    
        String[] weixuan2 = groupFirst2Balls(caption);
        HashMap map = new HashMap();
        List<String> tmpList = filterPrev2Num(resultList);
		map.put("w2",lgu.execute(weixuan2, tmpList, 0,W2CYCLE));  	
		return map;
	}
	/**
	 * 围选3
	 * @param resultList
	 * @return
	 */
	public HashMap  weixuan3(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        String[] weixuan3 = groupFirst3Balls(caption);	
    	HashMap map = new HashMap();
        List tmpList = filterPrev3Num(resultList);
		map.put("w3",lgu.execute(weixuan3, tmpList, 0,W3CYCLE));
		    	
		return map;
	}
	/**
	 * 任选三 大小奇偶
	 * @param resultList
	 * @return
	 */
	public List  random3Num(String[] sourceList,List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        String[] randomBalls = groupFirst3Balls(sourceList);	
        
        List<String> tmpList = new ArrayList();
    	//任三
		tmpList = new ArrayList();
		for(String row:resultList){
			String[] temp = row.split(",");
			ArrayList r3List = new ArrayList();
			for(String ele:temp){
				for(String ball:sourceList){
					if(ele.equals(ball)){
						r3List.add(ele);
					}
				}
			}
			if(r3List.size()>=3){
				Collections.sort(r3List);
				for(int t=0;t<r3List.size();t++){
					for(int j=t+1;j<r3List.size();j++){
		        		for(int k=j+1;k<r3List.size();k++){
							String result = "";
							result+=r3List.get(t)+" "+r3List.get(j)+" "+r3List.get(k);
							tmpList.add(result);
						}	
					}
				}	
			}else{
					String result = "";
					result+=temp[0]+" "+temp[1]+" "+temp[2];
					tmpList.add(result);
			}
		}
		
		return lgu.oddEvenSummary(randomBalls, tmpList, 0,R3CYCLE);
	}
	
	/**
	 * 奇偶统计
	 * @param resultList
	 * @return
	 */
	public HashMap  oddEvenSummary(List<String> resultList,int type){
		LottoGameUtils lgu = new LottoGameUtils();
    	HashMap map = new HashMap();
    	
    	if(type==3){
    		List result = filterPrev2Num(resultList);
    		//组二 两小奇 两大奇
    		ArrayList minOddCount2 = lgu.oddEvenSummary(groupFirst2Balls(minOdd), result, 0,W2CYCLE);
    		ArrayList maxOddCount2 = lgu.oddEvenSummary(groupFirst2Balls(maxOdd), result, 0,W2CYCLE);
    		
    		//组二 两小偶 两大偶
    		ArrayList minEvenCount2 = lgu.oddEvenSummary(groupFirst2Balls(minEven), result, 0,W2CYCLE);
    		ArrayList maxEvenCount2 = lgu.oddEvenSummary(groupFirst2Balls(maxEven), result, 0,W2CYCLE);	
    		
    		map.put("minOddCount2", minOddCount2);
            map.put("maxOddCount2", maxOddCount2);
            map.put("minEvenCount2", minEvenCount2);
            map.put("maxEvenCount2", maxEvenCount2);
    	}else if(type==4){
    		List result = filterPrev3Num(resultList);

    		//组三 三小奇 三大奇
    		ArrayList minOddCount3 = lgu.oddEvenSummary(groupFirst3Balls(minOdd), resultList, 0,W3CYCLE);
    		ArrayList maxOddCount3 = lgu.oddEvenSummary(groupFirst3Balls(maxOdd), resultList, 0,W3CYCLE);
    		
    		//组三 三小偶 三大偶
    		ArrayList minEvenCount3 = lgu.oddEvenSummary(groupFirst3Balls(minEven), resultList, 3,W3CYCLE);
    		ArrayList maxEvenCount3 = lgu.oddEvenSummary(groupFirst3Balls(maxEven), resultList, 3,W3CYCLE);
    		
    		map.put("minOddCount3", minOddCount3);
            map.put("maxOddCount3", maxOddCount3);
            map.put("minEvenCount3", minEvenCount3);
            map.put("maxEvenCount3", maxEvenCount3);
    	}else if(type==5){ 
    		
    		map.put("minRandom3Odd", random3Num(minOdd, resultList));
            map.put("maxRandom3Odd", random3Num(maxOdd, resultList));
            map.put("minRandom3Even", random3Num(minEven, resultList));
            map.put("maxRandom3Even", random3Num(maxEven, resultList));
    	}
	
		return map;
	}
	
	/**
	 * 任三三码
	 * @param resultList
	 * @return
	 */
	public Vector<ArrayList>  executeRand3Balls(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();
        
        String[] randomBalls = groupFirst3Balls(caption);	
        
        List<String> tmpList = new ArrayList();
    	//任三
		tmpList = new ArrayList();
		for(String row:resultList){
			String[] temp = row.split(",");
			ArrayList r3List = new ArrayList();
			for(String ele:temp){
				for(String ball:resultList){
					if(ele.equals(ball)){
						r3List.add(ele);
					}
				}
			}
			if(r3List.size()>=3){
				Collections.sort(r3List);
				for(int t=0;t<r3List.size();t++){
					for(int j=t+1;j<r3List.size();j++){
		        		for(int k=j+1;k<r3List.size();k++){
							String result = "";
							result+=r3List.get(t)+" "+r3List.get(j)+" "+r3List.get(k);
							tmpList.add(result);
						}	
					}
				}	
			}else{
					ArrayList list = new ArrayList();
					for(String ele:temp){
						list.add(ele);
					}
					Collections.sort(list);
					String result = "";
					result+=list.get(0)+" "+list.get(1)+" "+list.get(2);
					tmpList.add(result);
			}
		}
				       
		return lgu.execute(randomBalls, tmpList, 0,R3CYCLE); 
	}
	/**
	 * 围二和值
	 * @param resultList
	 * @return
	 */
	public Vector<ArrayList> hezhi(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();    
        HashMap map = new HashMap();
        ArrayList tempList = new ArrayList();
        for(String data:resultList){
			String[] temp = data.split(",");
			ArrayList list = new ArrayList();
			String sum = "";
        	Integer num1 = new Integer(temp[0]);
        	Integer num2 = new Integer(temp[1]);
        	if(num1+num2<10){
        		sum+="0"+(num1+num2);
        	}else{
        		sum+=(num1+num2);
        	}
        	tempList.add(sum);
        	
        }
		return lgu.execute(hezhi, tempList, 0,W2CYCLE);
	}
	/**
	 * 连号
	 * @param resultList
	 * @return
	 */
	public ArrayList lianhao(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();    
        HashMap map = new HashMap();
        String[] balls = groupFirst2Balls(caption);
        ArrayList tempList = (ArrayList) filterPrev2Num(resultList);
        
        Vector<ArrayList> datas = lgu.execute(balls, tempList, 0,W2CYCLE);
         
        ArrayList list = new ArrayList();
		for(int i=0;i<datas.size();i++){
			ArrayList data = datas.get(i);
			for(int j=0;j<lianhao.length;j++){
				if(lianhao[j].equals(data.get(0))){
					list.add(data.toArray());
				}
			}
		}
		return list;
	}
	
	/**
	 * 同尾号
	 * @param resultList
	 * @return
	 */
	public ArrayList tongweihao(List<String> resultList){
		LottoGameUtils lgu = new LottoGameUtils();    
        HashMap map = new HashMap();
        String[] balls = groupFirst2Balls(caption);
        ArrayList tempList = (ArrayList) filterPrev2Num(resultList);
        
        Vector<ArrayList> datas = lgu.execute(balls, tempList, 0,W2CYCLE);
        
        ArrayList list = new ArrayList();
		for(int i=0;i<datas.size();i++){
			ArrayList data = datas.get(i);
			for(int j=0;j<commomTailNum.length;j++){
				if(commomTailNum[j].equals(data.get(0))){
					list.add(data.toArray());
				}
			}
		}
		return list;
	}
	/**
	 * 前两个数的集合
	 * @param caption
	 * @return
	 */
	public String[] groupFirst2Balls(String[] caption){
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
	
	public List filterPrev2Num(List<String> resultList){
		List<String> tmpList = new ArrayList();
   	 
        //处理前两个号(围二组选)
		for(String row:resultList){
			String tempRow = row.substring(0,5);
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
	 * 前三个数的集合
	 * @param caption
	 * @return
	 */
	public String[] groupFirst3Balls(String[] caption){
		int size = 0;
		for(int t = 0;t<caption.length;t++){
        	for(int i=t+1;i<caption.length;i++){
            	for(int j=i+1;j<caption.length;j++){
            		size+=1;
            	}
            }
        }        
        String[] source = new String[size];

        int index=0;
        for(int t=0;t<caption.length;t++){
    		for(int j=t+1;j<caption.length;j++){    		
        		for(int k=j+1;k<caption.length;k++){
        			source[index]=caption[t]+" "+caption[j]+" "+caption[k];
        			if(++index>size)break;	
        		}
        	}
    	}	
    	return source;
	}
	
	public List filterPrev3Num(List<String> resultList){
		List<String> tmpList = new ArrayList();
   	 	//处理前三个号(围三组选)
		tmpList = new ArrayList();
		for(String row:resultList){
			String tempRow = row.substring(0,8);
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
		List result = new BaseDataBuilder().filterPrev3Num(list);
		//List result1 = lgu.oddEvenSummary(new BaseDataBuilder().groupFirst3Balls(minOdd), result, 0);
		List result1 = new BaseDataBuilder().random3Num(maxEven,list);
		//HashMap result = new BaseDataBuilder().weixuan3(list);
		//HashMap result = new BaseDataBuilder().oddEvenSummary(minOdd, list, 3);
		System.out.println(result1);
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
