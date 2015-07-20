package com.cai310.lottery.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.common.miss.MapSortUtil;
import com.cai310.lottery.web.controller.WebDataException;

public class FootBallFilterBean{
	
	private List<String> target = new ArrayList<String>();
	private List<String[]> source = new ArrayList<String[]>();
	private List<String> afterFilterSource = new ArrayList<String>();
	private List faultsTarget = new ArrayList();
	
	
	public FootBallFilterBean(){
		
	}
	
	public FootBallFilterBean(List<String> datas){
		for(String data:datas){
			String[] temp=data.split(",");
			source.add(temp);
		}
	}
	/**
	 * 全排
	 * @param line 
	 * @param row
	 */
	public void collectionAllRow(String[] line, String row){   
        for (int i = 0; i < source.size(); i++){   
            //取得当前的数组   
            if (i == source.indexOf(line))   
            {   
                //迭代数组   
                for (String st : line)   
                {   
                    st = row + st;   
                    if (i < source.size() - 1){   
                    	collectionAllRow(source.get(i + 1), st);   
                    }else if (i == source.size() - 1){ 
                		if(target.size()>1000000){
                			break;
                		}else{
                        	target.add(st);
                		}
                    }   
                }   
            }   
        }   
    } 
	/**
	 * 过滤
	 */
	public void filter(List<String> soucrce,String[] condition,HashMap extCondMap){
		afterFilterSource.clear();
		String key = condition[4];
		if(key.equals("oddsDesc")||key.equals("oddsAsc")||key.equals("jjzg")||key.equals("glzg")){
			HashMap result = filterOdds(soucrce,extCondMap);
			Map.Entry[] newResult = null;

			int start = new Integer(condition[0]);
			int end = new Integer(condition[1]);
			if(key.equals("oddsDesc")){
				newResult = MapSortUtil.sortByValueDesc(result);		
			}else if(key.equals("oddsAsc")){
				newResult = (Entry[]) MapSortUtil.sortByValueAsc(result);		
			}else if(key.equals("jjzg")){
				newResult = MapSortUtil.sortByValueDesc(result);
				start = 1;
				end = new Integer(condition[0]);
			}else if(key.equals("glzg")){
				newResult = (Entry[]) MapSortUtil.sortByValueAsc(result);		
				start = 1;
				end = new Integer(condition[0]);
			}
			
			if(null!=newResult&&newResult.length>0&&start>0&&start<=end){
				if(newResult.length<=end){
					end = newResult.length;
				}
				for(int i=start-1;i<end;i++){
					String matchLine = (String) newResult[i].getKey();
					afterFilterSource.add(matchLine);
				}
			}		
		}else if(key.indexOf("ccmz")!=-1||key.indexOf("lmgl")!=-1||key.indexOf("djgl")!=-1){//集合过滤
			String jhCondtion = (String) extCondMap.get("jhCondition");
			String[] jhConds = jhCondtion.split("\\|");
			for(int i=1;i<jhConds.length;i++){
				String[] temps = jhConds[i].split(":");
				String[] items = temps[0].split(",");
				String[] conds = temps[1].split("-");
				for(String temp:soucrce){
					if(filterCcMz(temp,conds,items)){
						afterFilterSource.add(temp);
					}else{
						if(afterFilterSource.contains(temp)){
							afterFilterSource.remove(temp);
						}
					}
				}
			}
			List newAfterFilterSource = removeDuplicateWithOrder(afterFilterSource);
			afterFilterSource.clear();
			afterFilterSource.addAll(newAfterFilterSource);
		}else if(key.equals("sjjq")){//截取范围
			Random rand = new Random();
			int size = Integer.parseInt(condition[0]);
			int index = 0;
			for(int i=0;i<size;i++){
				index = rand.nextInt(soucrce.size());
				if(afterFilterSource.contains((String)soucrce.get(index))){
					index = rand.nextInt(soucrce.size());
					i--;
				}else{
					afterFilterSource.add((String)soucrce.get(index));
				}
			}
		}else{
			for(String temp:soucrce){
				if((key.equals("3")||key.equals("1")||key.equals("0"))&&filterGs(temp,condition)){
					afterFilterSource.add(temp);
				}else if(key.equals("hz")&&filterHz(temp,condition)){			
					afterFilterSource.add(temp);
				}else if(key.equals("dd")&&filterDd(temp,condition)){			
					afterFilterSource.add(temp);
				}else if(key.equals("lh")&&filterLh(temp,condition)){			
					afterFilterSource.add(temp);
				}else if(key.equals("ls3")&&filterLs(temp,condition,"3")){
					afterFilterSource.add(temp);
				}else if(key.equals("ls1")&&filterLs(temp,condition,"1")){
					afterFilterSource.add(temp);
				}else if(key.equals("ls0")&&filterLs(temp,condition,"0")){
					afterFilterSource.add(temp);
				}else if(key.equals("bls3")&&filterBls(temp,condition,"3")){
					afterFilterSource.add(temp);
				}else if(key.equals("bls1")&&filterBls(temp,condition,"1")){
					afterFilterSource.add(temp);
				}else if(key.equals("bls0")&&filterBls(temp,condition,"0")){
					afterFilterSource.add(temp);
				}else if(key.equals("sxmz")||key.equals("cxmz")||key.equals("mxmz")){
					List spfDatas = (List) extCondMap.get("spfDatas");
					String[] items = new String[spfDatas.size()];
					for(int i=0;i<items.length;i++){
						String data = (String) spfDatas.get(i);
						String[] spells = data.trim().split(",");
						if(key.equals("sxmz")){
							items[i] = spells[0];
						}else if(key.equals("cxmz")){
							if(spells.length>=2){
								items[i] = spells[1];
							}else{
								items[i] = "-1";
							}
						}else if(key.equals("mxmz")){
							if(spells.length>=3){
								items[i] = spells[2];
							}else{
								items[i] = "-1";
							}
						}
					}
					if(filterMz(temp,condition,items)){
						afterFilterSource.add(temp);
					}
				}else if(key.equals("zsh")&&filterZsh(temp,condition,extCondMap)){
					afterFilterSource.add(temp);
				}else if(key.equals("zsj")&&filterZsj(temp,condition,extCondMap)){
					afterFilterSource.add(temp);
				}else if(key.equals("jjfw")&&filterJjfw(temp,condition,extCondMap)){
					afterFilterSource.add(temp);
				}else if((key.equals("dyzs")&&filterSpmz(temp,condition,extCondMap,"dyzs"))||(key.equals("dezs")&&filterSpmz(temp,condition,extCondMap,"dezs"))||
						(key.equals("dszs")&&filterSpmz(temp,condition,extCondMap,"dszs"))){
					afterFilterSource.add(temp);
				}else if(key.equals("xzgg")){//选择过关
					try{
						List<String> chooseDatas = new ArrayList<String>();
						int size = Integer.parseInt(condition[0])+1;
						xzgg(temp, new ArrayList(),chooseDatas, size);
						for(String data:chooseDatas){
							if(afterFilterSource.size()>=FetchConstant.MAX_FILTER)break;
							else{
								if(!afterFilterSource.contains(data)){
									int count = 0;
									for(int t=0;t<data.length();t++){
										if(data.charAt(t)=='*'){
											count++;
										}
									}
									if(count==(data.length()-size)){
										afterFilterSource.add(data);
									}
								}
							}
						}
					}catch(RuntimeException e){
						
					}
					
				}else if(key.indexOf("fzhz")!=-1||key.indexOf("fzdd")!=-1||key.indexOf("fzzshz")!=-1||key.indexOf("fzzsj")!=-1){
					String jhCondtion = (String) extCondMap.get("jhCondition");
					String[] jhConds = jhCondtion.split("\\|");
					for(int i=1;i<jhConds.length;i++){
						String[] temps = jhConds[i].split(":");
						int index = temps[0].indexOf("n");
						String newTemp = "";
						String newTemp2 = "";
						if(index!=-1){
							for(int t=0;t<temp.length();t++){
								if(t!=index){
									newTemp+=Character.toString(temp.charAt(t));
									newTemp2+=Character.toString(temp.charAt(t));
								}else{
									newTemp2+="#";
								}
							}
						}else{
							newTemp=temp;
							newTemp2=temp;
						}
						String[] items = temps[0].split(",");
						String[] conds = temps[1].split("-");
						if((key.indexOf("fzdd")!=-1&&filterDd(newTemp,conds))||(key.indexOf("fzhz")!=-1&&filterHz(newTemp,conds))||
								(key.indexOf("fzzshz")!=-1&&filterZsh(newTemp2,conds,extCondMap))||(key.indexOf("fzzsj")!=-1&&filterZsj(newTemp2,condition,extCondMap))){			
							afterFilterSource.add(temp);
						}
					}
				
				}
			}
		}
		
		target.clear();
		target.addAll(afterFilterSource);
	}
	/**
	 * 过滤个数3 1 0
	 */
	public boolean filterGs(String source,String[] condition){
		String key = condition[4];
		int count = 0;
		for(int i=0;i<source.length();i++){
			if(Character.toString(source.charAt(i)).equals(key))
				count++;
		}
		if(count>=Integer.parseInt(condition[0])&&count<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	/**
	 * 过滤和值
	 * @param soucrce
	 * @param condition
	 */
	public boolean filterHz(String source,String[] condition){
		int sum = 0;
		for(int i=0;i<source.length();i++){
			int value = Integer.parseInt(Character.toString(source.charAt(i)));
			sum+=value;
		}
		if(sum>=Integer.parseInt(condition[0])&&sum<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	
	/**
	 * 过滤断点
	 * @param soucrce
	 * @param condition
	 */
	public boolean filterDd(String source,String[] condition){
		int count = 0;
		for(int i=0;i<source.length();i++){
			if(i<source.length()-1){
				if(source.charAt(i)!=source.charAt(i+1)){
					count++;
				}
			}
		}
		if(count>=Integer.parseInt(condition[0])&&count<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	/**
	 * 过滤连号
	 * @param soucrce
	 * @param condition
	 */
	public boolean filterLh(String source,String[] condition){
		int count = 0;
		for(int i=0;i<source.length();i++){
			if(i<source.length()-1){
				if(source.charAt(i)==source.charAt(i+1)){
					count++;
				}
			}
		}
		if(count>=Integer.parseInt(condition[0])&&count<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	/**
	 * 过滤主场连胜 连平 连负
	 * @param soucrce
	 * @param condition
	 */
	public boolean filterLs(String source,String[] condition,String key){
		int count = 0;
		int maxCount = 0;
		for(int i=0;i<source.length();i++){
			if(Character.toString(source.charAt(i)).equals(key)){
				count++;
			}else{
				count = 0;
			}	
			if(maxCount<count){
				maxCount = count;
			}
		}
		if(maxCount>=Integer.parseInt(condition[0])&&maxCount<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	
	/**
	 * 过滤主场不连胜 不连平 不连负
	 * @param soucrce
	 * @param condition
	 */
	public boolean filterBls(String source,String[] condition,String key){
		int count = 0;
		int maxCount = 0;
		for(int i=0;i<source.length();i++){
			if(!Character.toString(source.charAt(i)).equals(key)){
				count++;
			}else{
				count = 0;
			}	
			if(maxCount<count){
				maxCount = count;
			}
		}
		if(maxCount>=Integer.parseInt(condition[0])&&maxCount<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	/**
	 * 首次末命中
	 * @param source
	 * @param condition
	 * @param key
	 * @param items 首次末过滤的模板，即对应的第一列或者第二列或者第三列数据
	 * @return
	 */
	public boolean filterMz(String source,String[] condition,String[] items){
		int count = 0;
		for(int i=0;i<items.length;i++){			
			if(Character.toString(source.charAt(i)).equals(items[i])){
				count++;
			}
		}
		if(count>=Integer.parseInt(condition[0])&&count<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	/**
	 * 指数和
	 */
	public boolean filterZsh(String source,String[] condition,HashMap spsMap){
		List<String> originaSps = (List<String>) spsMap.get("originaSps");
		double sum = 0;
		for(int i=0;i<source.length();i++){
			String[] originaSp = originaSps.get(i).split(",");
			if(Character.toString(source.charAt(i)).equals("3")){
				sum+=Double.parseDouble(originaSp[0]);
			}else if(Character.toString(source.charAt(i)).equals("1")){
				sum+=Double.parseDouble(originaSp[1]);
			}else if(Character.toString(source.charAt(i)).equals("0")){
				sum+=Double.parseDouble(originaSp[2]);
			}
		}
		if(sum>=Double.parseDouble(condition[0])&&sum<=Double.parseDouble(condition[1]))return true;
		else return false;
	}
	
	/**
	 * 指数积
	 */
	public boolean filterZsj(String source,String[] condition,HashMap spsMap){
		List<String> originaSps = (List<String>) spsMap.get("originaSps");
		double indigestion = 1.0d;
		for(int i=0;i<source.length();i++){
			String[] originaSp = originaSps.get(i).split(",");
			if(Character.toString(source.charAt(i)).equals("3")){
				indigestion*=Double.parseDouble(originaSp[0]);
			}else if(Character.toString(source.charAt(i)).equals("1")){
				indigestion*=Double.parseDouble(originaSp[1]);
			}else if(Character.toString(source.charAt(i)).equals("0")){
				indigestion*=Double.parseDouble(originaSp[2]);
			}
		}
		if(indigestion>=Double.parseDouble(condition[0]==null?"0":condition[0])&&indigestion<=Double.parseDouble(condition[1]==null?"0":condition[1]))return true;
		else return false;
	}
	
	/**
	 * 奖金范围
	 */
	public boolean filterJjfw(String source,String[] condition,HashMap spsMap){
		List<String> originaSps = (List<String>) spsMap.get("originaSps");
		double bonus = 1.0d;
		for(int i=0;i<source.length();i++){
			String[] originaSp = originaSps.get(i).split(",");
			if(Character.toString(source.charAt(i)).equals("3")){
				bonus*=Double.parseDouble(originaSp[0]);
			}else if(Character.toString(source.charAt(i)).equals("1")){
				bonus*=Double.parseDouble(originaSp[1]);
			}else if(Character.toString(source.charAt(i)).equals("0")){
				bonus*=Double.parseDouble(originaSp[2]);
			}
		}
		bonus*=1.3;
		if(bonus>=Double.parseDouble(condition[0]==null?"0":condition[0])&&bonus<=Double.parseDouble(condition[1]==null?"0":condition[1]))return true;
		else return false;
	}
	
	/**
	 * 第一二三指数命中
	 */
	public boolean filterSpmz(String source,String[] condition,HashMap spsMap,String key){
		double[] minSps = null;
		double[] midSps = null;
		double[] maxSps = null;
		List<String> originaSps = (List<String>) spsMap.get("originaSps");
		int count = 0;
		if("dyzs".equals(key)){
			minSps = (double[]) spsMap.get("minSps");
		}else if("dezs".equals(key)){
			midSps = (double[]) spsMap.get("midSps");
		}else if("dszs".equals(key)){
			maxSps = (double[]) spsMap.get("maxSps");
		}
		for(int i=0;i<source.length();i++){
			String[] sps = originaSps.get(i).split(",");		
			if(Character.toString(source.charAt(i)).equals("3")){
				if("dyzs".equals(key)){
					if(Double.parseDouble(sps[0])==minSps[i]){
						count++;
					}
				}else if("dezs".equals(key)){
					if(Double.parseDouble(sps[0])==midSps[i]){
						count++;
					}
				}else if("dszs".equals(key)){
					if(Double.parseDouble(sps[0])==maxSps[i]){
						count++;
					}
				}				
			}else if(Character.toString(source.charAt(i)).equals("1")){
				if("dyzs".equals(key)){
					if(Double.parseDouble(sps[1])==minSps[i]){
						count++;
					}
				}else if("dezs".equals(key)){
					if(Double.parseDouble(sps[1])==midSps[i]){
						count++;
					}
				}else if("dszs".equals(key)){
					if(Double.parseDouble(sps[1])==maxSps[i]){
						count++;
					}
				}
				
			}else if(Character.toString(source.charAt(i)).equals("0")){		
				if("dyzs".equals(key)){
					if(Double.parseDouble(sps[2])==minSps[i]){
						count++;
					}
				}else if("dezs".equals(key)){
					if(Double.parseDouble(sps[2])==midSps[i]){
						count++;
					}
				}else if("dszs".equals(key)){
					if(Double.parseDouble(sps[2])==maxSps[i]){
						count++;
					}
				}
			}
		}
		if(count>=Integer.parseInt(condition[0])&&count<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	
	/**
	 * 赔率截取
	 */
	public HashMap filterOdds(List<String> source,HashMap spsMap){
		HashMap result = new HashMap();
		List<String> originaSps = (List<String>) spsMap.get("originaSps");
		
		for(int i=0;i<source.size();i++){
			double indigestion = 1.0d;
			String subs = source.get(i);
			for(int j=0;j<subs.length();j++){
				String[] originaSp = originaSps.get(j).split(",");
				if(Character.toString(subs.charAt(j)).equals("3")){
					indigestion*=Double.parseDouble(originaSp[0]);
				}else if(Character.toString(subs.charAt(j)).equals("1")){
					indigestion*=Double.parseDouble(originaSp[1]);
				}else if(Character.toString(subs.charAt(j)).equals("0")){
					indigestion*=Double.parseDouble(originaSp[2]);
				}
			}	
			BigDecimal bd=new BigDecimal(indigestion);  
			result.put(subs,new Double(bd.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()));
		}
		return result;
	}
	/**
	 * 场次命中
	 * @param source
	 * @param condition
	 * @param key
	 * @param items 首次末过滤的模板，即对应的第一列或者第二列或者第三列数据
	 * @return
	 */
	public boolean filterCcMz(String source,String[] condition,String[] items){
		int count = 0;
		for(int i=0;i<items.length;i++){			
			if(items[i].indexOf(Character.toString(source.charAt(i)))!=-1){
				count++;
			}
		}
		if(count>=Integer.parseInt(condition[0])&&count<=Integer.parseInt(condition[1]))return true;
		else return false;
	}
	
	
	/**
	 * 奖金最高 概率最高
	 */
	public List filterPrizeAndProbablity(List<String> source,String[] condition,HashMap spsMap){
		List list = new ArrayList();
		List<String> originaSps = (List<String>) spsMap.get("originaSps");
		for(int i=0;i<source.size();i++){
			double indigestion = 1.0d;
			String temp = source.get(i);
			for(int j=0;j<temp.length();j++){
				String[] originaSp = originaSps.get(j).split(",");
				if(Character.toString(temp.charAt(j)).equals("3")){
					indigestion*=Double.parseDouble(originaSp[0]);
				}else if(Character.toString(temp.charAt(j)).equals("1")){
					indigestion*=Double.parseDouble(originaSp[1]);
				}else if(Character.toString(temp.charAt(j)).equals("0")){
					indigestion*=Double.parseDouble(originaSp[2]);
				}
			}
			list.add(indigestion);
		}
		return list;
	}
	//选择过关
	public static void xzgg(String data, List<String> target,List sortDatas,int size) {  
        if (target.size() == size) {  
        	String end = "";
        	for(int t=0;t<data.length();t++){
        		end+="*";
        	}
        	String newStr = "";
        	for(String str:target){
        		newStr+=str;
        	}
        	newStr+=end;
        	sortDatas.add(newStr);
            return;  
        }  
        for (int i = 0; i < data.length(); i++) {  
            String newDatas = data.substring(i+1,data.length());  
            List newTarget = new ArrayList(target);  
            String star = "";
        	for(int j=0;j<i;j++){
        		star+="*";
        	}
        	newTarget.add(star+Character.toString(data.charAt(i)));
            xzgg(newDatas, newTarget,sortDatas,size);  
        }  
    } 
	/**
	 * 去除重复值
	 * @param list
	 * @return
	 */
	public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
	}
	
	public List<String> getTarget() {
		return target;
	}
	public void setTarget(List<String> target) {
		this.target = target;
	}
	public List<String[]> getSource() {
		return source;
	}

	public void setSource(List<String[]> source) {
		this.source = source;
	}

	public List<String> getAfterFilterSource() {
		return afterFilterSource;
	}
	public void setAfterFilterSource(List<String> afterFilterSource) {
		this.afterFilterSource = afterFilterSource;
	}
	
	public List getFaultsTarget() {
		return faultsTarget;
	}

	public void setFaultsTarget(List faultsTarget) {
		this.faultsTarget = faultsTarget;
	}

	public static void main(String[] args) {
		/*List list = new ArrayList();
		
		list.add("3,1,0");
		list.add("3,1,0");
		list.add("0");
		list.add("3,1");

		FootBallFilterBean filter = new FootBallFilterBean(list);
		filter.collectionAllRow(filter.getSource().get(0),"");
	System.out.println("=========================");

		String[] condition = new String[]{"0","2"};
		String[] condition1 = new String[]{"0","0"};
		List<String[]> conds = new ArrayList<String[]>();
		conds.add(condition);
		conds.add(condition1);
		for(String[] cond:conds){
			filter.filter(filter.getTarget(),cond);	
			System.out.println("============brfore=============");
			List g3Data = filter.getAfterFilterSource();
			for(int i=0;i<g3Data.size();i++){
				System.out.println(g3Data.get(i));
			}
			System.out.println("============after=============");
		}
		
		List duplicate = new ArrayList();
		duplicate.add("1");
		duplicate.add("2");
		duplicate.add("3");
		duplicate.add("3");
		duplicate.add("1");
		duplicate.add("4");

		List newList = removeDuplicateWithOrder(duplicate);
		for(int i=0;i<newList.size();i++){
			System.out.println(newList.get(i));
		}
		FootBallFilterBean filter = new FootBallFilterBean();
		String[] datas = new String[] { "a", "b", "c", "d" };  
		ArrayList list = new ArrayList();
		filter.check(datas,1,2); */
		
		List list = new ArrayList();	
		list.add("3");
		list.add("1,0");
		list.add("3,1");
		list.add("1,0");
		FootBallFilterBean filter = new FootBallFilterBean(list);
		filter.collectionAllRow(filter.getSource().get(0),"");
		List<String> sources = filter.getTarget();
		for(int i=0;i<sources.size();i++){
			System.out.println(sources.get(i));
		}
System.out.println("=================================");
		String[] items = new String[]{"-1","-1","-1","-1"};
		String[] condition = new String[]{"0","2"};
		for(int i=0;i<sources.size();i++){
			String source = sources.get(i);
			if(filter.filterMz(source, items, condition)){
				System.out.println(source);
			}
		}

	}
}
