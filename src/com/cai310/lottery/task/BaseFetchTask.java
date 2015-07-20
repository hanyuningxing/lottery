package com.cai310.lottery.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;


import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.fetch.DataModel;
import com.cai310.lottery.utils.HttpUtil;



public abstract class BaseFetchTask {
	
	public static Pattern p1 = Pattern.compile(FetchConstant.MATCH_PER);
	public static  Pattern p2 = Pattern.compile(FetchConstant.MATCH_TOTAL);
	public static  Pattern p3 = Pattern.compile(FetchConstant.MATCH_CONDTION);	
	
	//直选复式分析  按号码统计
	public static  Pattern p4 = Pattern.compile(FetchConstant.FC3D_MATCH_NUM);
	public static Pattern p5 = Pattern.compile(FetchConstant.FC3D_MATCH_CONDTION);
	public static Pattern p11 = Pattern.compile(FetchConstant.FC3D_MATCH_P_CONDTION_HEAD);
	//直选复式分析  按位置统计
	public static Pattern p6 = Pattern.compile(FetchConstant.FC3D_MATCH_P_CONDTION_HEIGHT);
	public static Pattern p7 = Pattern.compile(FetchConstant.FC3D_MATCH_P_CONDTION_NUM);
	//组选复式分析  按单注号码
	public static  Pattern p8 = Pattern.compile(FetchConstant.FC3D_MATCH_C_NUM);
	public static Pattern p9 = Pattern.compile(FetchConstant.FC3D_MATCH_C_CONDTION);
	//组选复式分析  按单个号码
	public static  Pattern p10 = Pattern.compile(FetchConstant.FC3D_MATCH_C_SM_CONDTION);
	public static  Pattern p12 = Pattern.compile(FetchConstant.FC3D_MATCH_C_SM_HEAD);
	
	public static Pattern p13 = Pattern.compile(FetchConstant.SZPL_MATCH_C_SM_CONDTION);
	public static Pattern p14 = Pattern.compile(FetchConstant.SZPL_MATCH_C_DG_HEAD);
	public static Pattern p15 = Pattern.compile(FetchConstant.SZPL_MATCH_C_DZ_HEAD);
	
	/**
	 * 过滤内容并封装
	 * @param content
	 * @return
	 */
	abstract public DataModel fiterContent(String content,String fxtype,String sort);
	/**
	 * 生成XML文件
	 * @throws IOException
	 * @throws JDOMException
	 */
	abstract public void BuildXMLDoc(DataModel data) throws IOException, JDOMException;
	     
}
