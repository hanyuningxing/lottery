package com.cai310.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NumUtils {
	/**
	 * 字符串转浮点型，如果为空則返回NULL
	 * 
	 * @param num
	 * @return
	 */
	public static Float createFloat(String num) {
		if (num == null || num.equals(""))
			return null;
		try {
			return new Float(num);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转整型，如果为空則返回NULL
	 * 
	 * @param num
	 * @return
	 */
	public static Integer createInteger(String num) {
		if (num == null || num.equals(""))
			return null;
		try {
			return new Integer(num);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转Double，如果为空則返回NULL
	 * 
	 * @param num
	 * @return
	 */
	public static Double createDouble(String num) {
		if (num == null || num.equals(""))
			return null;
		try {
			return new Double(num);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转长整型，如果为空則返回NULL
	 * 
	 * @param num
	 * @return
	 */
	public static Long createLong(String num) {
		if (num == null || num.equals(""))
			return null;
		try {
			return new Long(num);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer[] toIntegerArr(String strArr[]) {
		Integer[] ia = new Integer[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			ia[i] = Integer.valueOf(strArr[i]);
		}
		return ia;
	}

	public static List<Integer> toIntegerList(List<String> strList) {
		List<Integer> l = new ArrayList<Integer>();
		Iterator<String> it = strList.iterator();
		while (it.hasNext()) {
			l.add(Integer.valueOf(it.next()));
		}
		return l;
	}

	public static List<Integer> toIntegerList(String[] strArr) {
		List<Integer> l = new ArrayList<Integer>();
		for (String str : strArr) {
			l.add(Integer.valueOf(str));
		}
		return l;
	}

	public static List<Integer> toIntegerList(String bet, String splitStr) {
		List<Integer> l = new ArrayList<Integer>();
		if (bet.indexOf(splitStr) != -1) {
			String[] bets = bet.split(splitStr);
			for (String str : bets) {
				l.add(Integer.valueOf(str));
			}
		} else {
			l.add(Integer.valueOf(bet));
		}
		return l;
	}

	public static Integer[] listToIntegerArr(List<String> intList) {
		Integer[] arr = new Integer[intList.size()];
		for (int i = 0; i < intList.size(); i++) {
			arr[i] = Integer.valueOf(intList.get(i));
		}
		return arr;
	}

	public static Integer compare(Integer[] bet, Integer[] result, Boolean sort) {
		Integer hit = Integer.valueOf("0");
		if (sort) {
			Arrays.sort(bet);
			Arrays.sort(result);
		}
		for (int i = 0; i < bet.length; i++) {
			if (bet[i].equals(result[i])) {
				hit++;
			}
		}
		return hit;
	}
	/**
	 * 判断20以内的数是否为质数
	 * @param i
	 * @return
	 */
	public static boolean isPrimNum(int r){
		if (r==1||r==3||r==5||r==7||r==11||r==13||r==17||r==19) {
			return true;
		}
		return false;
	}

	//数字转换字符串 不足长度补零  目标stringLenth字符串长度:2 转为002
	public static String getNumToString(int num,int stringLenth){
	  String str_m = String.valueOf(num);
	  StringBuffer str = new StringBuffer();
	  for(int i=0;i<stringLenth;i++) {
		  str.append("0");
	  }
	  str_m=str.substring(0, stringLenth-str_m.length())+str_m;
	  return str_m;
	}
	 /** 
     * 格式化输出 浮点数 
     *  
     * @param d 
     *            双精度浮点数 
     * @param max  
     *               小数点后-最大保留位数 
     * @param min 
     *            小数点后-最小保留位数(默认为 2 ,不足补0) 
     * @return 
     */  
    public static String format(Double d, Integer max ,Integer min) {  
        if(null == d){  
            return "";  
        }  
        Integer _min = (null == min || min < 0) ? 2 : min;  
        String pattern = "0";  
        DecimalFormat formatter = new DecimalFormat(pattern);  
        if (null != _min) {  
            formatter.setMinimumFractionDigits(_min);  
        }  
        if (null != max) {  
            formatter.setMaximumFractionDigits(max);  
        }  
        return formatter.format(d);  
    }  
    /** 
     * 格式化输出 浮点数 
     *  
     * @param d 
     *            双精度浮点数 
     * @param max  
     *               小数点后-最大保留位数 
     * @param min 
     *            小数点后-最小保留位数(默认为 2 ,不足补0) 
     * @return 
     */  
    public static String format(BigDecimal d, Integer max ,Integer min) {  
        if(null == d){  
            return "";  
        }  
        Integer _min = (null == min || min < 0) ? 2 : min;  
        String pattern = "0";  
        DecimalFormat formatter = new DecimalFormat(pattern);  
        if (null != _min) {  
            formatter.setMinimumFractionDigits(_min);  
        }  
        if (null != max) {  
            formatter.setMaximumFractionDigits(max);  
        }  
        return formatter.format(d);  
    }

    /**
     * 四舍五入
     * @param d 格式化数字
     * @param num 小数点保留数
     * @return
     */
    public static String format(Double d,Integer num){
    	BigDecimal formatNum = new BigDecimal(d).setScale(num, BigDecimal.ROUND_HALF_UP); 
    	return formatNum.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(format(1.81*2.6*2,3));
	}
}
