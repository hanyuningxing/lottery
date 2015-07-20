package com.cai310.lottery.task.ssq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.fetch.DataModel;
import com.cai310.lottery.fetch.Item;
import com.cai310.lottery.task.BaseFetchTask;
import com.cai310.lottery.utils.HttpUtil;

public class SsqFetchTask extends BaseFetchTask{
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void fetchData() {
		String content;
		try {
			content = HttpUtil.getContentByURL(FetchConstant.SSQ_CUR_URL, false);
			DataModel data = fiterContent(content,"1","1");
			BuildXMLDoc(data);
Log.info("============fetchData==============");			
			content = HttpUtil.getContentByURL(FetchConstant.SSQ_CUR_SORT_XTYPE_1, false);
			DataModel data2 = fiterContent(content,"2","1");
			BuildXMLDoc(data2);
			
			content = HttpUtil.getContentByURL(FetchConstant.SSQ_CUR_SORT_XTYPE_2, false);
			DataModel data3 = fiterContent(content,"3","1");
			BuildXMLDoc(data3);
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DataModel fiterContent(String content,String fxtype,String sort){
		ArrayList redNew = new ArrayList();
		ArrayList blueNew = new ArrayList();
		
		DataModel data = new DataModel();
		data.setPlayType(fxtype);
		data.setOrderNo(new Integer(sort));
		
		Matcher m = p3.matcher(content);
		if(m.find()){		
			data.setPeriod(m.group(2));		
		}
		m = p1.matcher(content);
		while(m.find()){
			if(m.group(1).equals("red_per")){
				Item red = new Item();
				red.setPer(m.group(2));
				data.getRedAreas().add(red);
			}else{
				Item blue = new Item();
				blue.setPer(m.group(2));
				data.getBlueAreas().add(blue);
			}
		}
		m = p2.matcher(content);
		int red = 0;
		int blue = 0;
		while(m.find()){
			if(m.group(2).equals("red_num")){
				Item redArea = data.getRedAreas().get(red);
				redArea.setCount(m.group(1));
				redArea.setNum(m.group(3));
				redNew.add(redArea);
				red++;	
			}else{
				Item blueArea = data.getBlueAreas().get(blue);
				blueArea.setCount(m.group(1));
				blueArea.setNum(m.group(3));
				blueNew.add(blueArea);
				blue++;
			}
		}
		data.setRedAreas(redNew);
		data.setBlueAreas(blueNew);
		
		return data;		
	}



	@Override
	public void BuildXMLDoc(DataModel data) throws IOException, JDOMException {
		//创建根节点 root;
       Element root = new Element("root");
       
       //根节点添加到文档中；
       Document Doc = new Document(root);
        
       //创建节点 items;
        Element elements = new Element("items");
        
        //给 items 节点添加属性 period orderNo playType;
        elements.setAttribute("period",data.getPeriod());
        elements.setAttribute("orderNo",data.getOrderNo().toString());
        elements.setAttribute("playType",data.getPlayType().toString());
        
        //给 items 节点添加子节点并赋值；
        Element redAreas = new Element("redAreas");
        for(int j=0;j<data.getRedAreas().size();j++){
     	   Element red = new Element("red");
     	   Item redArea= data.getRedAreas().get(j);
     	   red.setAttribute("per",redArea.getPer());
     	   red.setAttribute("num",redArea.getNum());
     	   red.setAttribute("count",redArea.getCount());
     	   redAreas.addContent(red);
        }
        
        Element blueAreas = new Element("blueAreas");
        for(int k=0;k<data.getBlueAreas().size();k++){
     	   Element blue = new Element("blue");
     	   Item blueArea= data.getBlueAreas().get(k);
     	   blue.setAttribute("per",blueArea.getPer());
     	   blue.setAttribute("num",blueArea.getNum());
     	   blue.setAttribute("count",blueArea.getCount());
     	   blueAreas.addContent(blue);
        }
        // 给父节点root添加items子节点;    
        elements.addContent(redAreas);
        elements.addContent(blueAreas); 
	    root.addContent(elements);

	    XMLOutputter XMLOut = new XMLOutputter();
	       // 输出xml文件(文件名：当前期_统计方式_排序方式)玩法；
	    if("1".equals(data.getPlayType())){
	    	XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.SSQ_CUR_PATH_1+data.getPeriod()+".xml")));
	    }else if("2".equals(data.getPlayType())){
	    	XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.SSQ_CUR_PATH_2+data.getPeriod()+".xml")));
	    }else{
	    	XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.SSQ_CUR_PATH_3+data.getPeriod()+".xml")));
	    }
	    
	}
	
	
}
