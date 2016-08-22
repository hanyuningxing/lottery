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

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.utils.DateUtil;
import com.cai310.utils.HttpClientUtil;

public class FetchParserGH extends OkoooAbstractFetchParser {
	private static final String URL="http://www.ghcai.com/ssq/scheme!subList.action?queryForm.sponsorName=%E5%8F%91%E8%B5%B7%E4%BA%BA%E6%98%B5%E7%A7%B0&queryForm.costType=0&queryForm.chooseType=0&menu=all&queryForm.orderType=PROCESS_RATE_DESC&queryForm.lotteryType=all&queryForm.schemeState=&pagination.pageNo=";
	
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
		String phonetxt = "gh_phone.txt";
		String qqtxt = "gh_qq.txt";
		File file = new File(dir, phonetxt);
		File qqfile = new File(dir, qqtxt);
		
		String div=getDivBy(html,CHARSET,"class","hmdt_table").getChildrenHTML();
		TableTag tag=getTable(div, CHARSET,"class","hmdt_table_content");
		TableRow[] rows = tag.getRows();
		int i = 0;
		for (TableRow row : rows) {
			i++;
			if(i<2||i>21){
				continue;
			}
			try {
				TableColumn[] columns = row.getColumns();
				String uid =columns[1].getChild(0).getText();
				String gid=columns[3].getChild(0).getText();
				String money=columns[4].getChild(0).getText();
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
				continue;
			}
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
		FetchParserGH fetch=new FetchParserGH();
		for (int i = 1; i <= 99; i++) {
			fetch.fetch(i+"");
			System.out.println(i);
		}
		
	}
}
