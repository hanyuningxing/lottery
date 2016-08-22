package com.cai310.lottery.fetch.dczc.win310sp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.utils.DateUtil;
import com.cai310.utils.HttpClientUtil;

public class FetchParserCtiy extends OkoooAbstractFetchParser {
	private static final String URL="http://www.citycai.com/blog/";
	
	private static final String CHARSET = "GBK";
	
	public void fetch(String periodNumber){
		String html = HttpClientUtil.getRemoteSource(URL+periodNumber, CHARSET);
		parser(html);
	}
	public void parser(String html) {
		File dir = new File("D://Infos");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String phonetxt = "city_phone.txt";
		String qqtxt = "city_qq.txt";
		File file = new File(dir, phonetxt);
		File qqfile = new File(dir, qqtxt);
		
		String div=getDivBy(html,CHARSET,"class","main").getChildrenHTML();
		TableTag tag=getTable(div, CHARSET,"class","dtable");
		TableRow[] rows = tag.getRows();
		try {
			TableColumn[] columns = rows[1].getColumns();
			String uidSpan=columns[0].getChildrenHTML();
			String uid=getSpan(uidSpan, CHARSET, null, null).getChild(0).getText();
			
			String moneySpan=columns[1].getChildrenHTML();
			String money=getSpan(moneySpan, CHARSET, "class", "orgbold").getChild(0).getText();
			
			String gidSpan=columns[3].getChildrenHTML();
			String[] gids = new String[]{"北京单场","胜负彩","进球彩","六场半全场","竞彩篮球","竞彩足球"};
			String gid="";
			for (String g : gids) {
				if(gidSpan.contains(g)){
					gid = g;
				}
			}
			if(StringUtils.isEmpty(gid)){
				gid="0";
			}
			if(isMobilephone(uid)&&!hasSameValue(uid,file)){
				String info = uid+"#"+gid+"#"+money+"#"+DateUtil.dateToStr(new Date())+"\r\n";
				FileWriter fileWriter=new FileWriter(file,true);
				BufferedWriter writer = new BufferedWriter(fileWriter);
				writer.write(info);
				writer.close();
			}
			
			if(isQQ(uid)&&!hasSameValue(uid,qqfile)){
				String info = uid+"#"+gid+"#"+money+"#"+DateUtil.dateToStr(new Date())+"\r\n";
				FileWriter fileWriter=new FileWriter(qqfile,true);
				BufferedWriter writer = new BufferedWriter(fileWriter);
				writer.write(info);
				writer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private final static boolean hasSameValue(String phone,File file) throws Exception {
		boolean flag = false;
		if (file.exists() && file.isFile()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				temp = temp.trim();
				if (temp.length() > 0) {
					if(temp.split("\\#")[0].equals(phone)){
						flag = true;
					}
				}
			}
			br.close();
			br = null ;
			return flag;
		}else{
			return false;
		} 
	}
	
	public final static boolean isMobilephone(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	public final static boolean isQQ(String qq) {
		Pattern p = Pattern.compile("^[qQ_-]*[1-9](\\d){4,9}$");
		Matcher m = p.matcher(qq);
		return m.matches();
	}
	public static void main(String[] args) {
		FetchParserCtiy fetch=new FetchParserCtiy();
		for (int i = 1468; i <= 28670; i++) {
			try {
			fetch.fetch(i+"/");
			}catch(Exception e){
				continue;
			}
			System.out.println(i);
		}
		
		
	}
}
