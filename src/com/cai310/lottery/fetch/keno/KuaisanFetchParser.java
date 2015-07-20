package com.cai310.lottery.fetch.keno;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cai310.lottery.fetch.SimpleAbstractFetchParser;
import com.cai310.lottery.fetch.jczq.JczqFetchResult;
@Repository("kuaisanFetchParser")
public class KuaisanFetchParser extends  kenoAbstractFetchParser{

	@Override
	public String getCharset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSourceUrl() {
		// TODO Auto-generated method stub
//		Date date=new Date();
//		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		String time=format.format(date);
//		String url="http://www.310win.com/Info/Result/High.aspx?load=ajax&typeID=123&date="+time;
		return "http://www.310win.com/kuaisan/tubiao_210_300.html";
	}


}
