package com.cai310.lottery.task.fc3d;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.fetch.DataModel;
import com.cai310.lottery.fetch.Item;
import com.cai310.lottery.task.BaseFetchTask;
import com.cai310.lottery.utils.HttpUtil;

public class Fc3dFetchTask extends BaseFetchTask{
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void fetchDataByNum() {
		String content;
		try {
			content = HttpUtil.getContentByURL(FetchConstant.FC3D_NUM_URL, false);
			DataModel data = fiterContent(content,"1","1");
			BuildXMLDoc(data);			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fetchDataByPos() {
		String content;
		try {			
			content = HttpUtil.getContentByURL(FetchConstant.FC3D_POS_URL, false);
			DataModel data = fiterContentByPos(content,"2","1");
			BuildXMLDocByPos(data);				
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void fetchDataByDZ() {
		String content;
		try {			
			//组选三
			content = HttpUtil.getContentByURL(FetchConstant.FC3D_GROUP_DZ_URL_2, false);
			DataModel data = fiterContentByDZ(content,"2","1","1");
			BuildXMLDocByDZ(data);		
			//组选六
			content = HttpUtil.getContentByURL(FetchConstant.FC3D_GROUP_DZ_URL_3, false);
			DataModel data1 = fiterContentByDZ(content,"2","1","2");
			BuildXMLDocByDZ(data1);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void fetchDataByDG() {
		String content;
		try {		
			//组选三
			content = HttpUtil.getContentByURL(FetchConstant.FC3D_GROUP_DG_URL_2, false);
			DataModel data = fiterContentByDG(content,"1","1","1");
			BuildXMLDocByDG(data);	
			//组选六
			content = HttpUtil.getContentByURL(FetchConstant.FC3D_GROUP_DG_URL_3, false);
			DataModel data1 = fiterContentByDG(content,"1","1","2");
			BuildXMLDocByDG(data1);	
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	@Override
	public DataModel fiterContent(String content,String fxtype,String sort) {
		DataModel data = new DataModel();
		Matcher m = p5.matcher(content);
		data.setPlayType(fxtype);
		data.setOrderNo(new Integer(sort));
		if(m.find()){			
			data.setPeriod(m.group(2));			
		}
		
		m = p4.matcher(content);
		ArrayList<Item> items = new ArrayList<Item>();
		
		while(m.find()){
			Item item = new Item();
			item.setNo(m.group(1));
			item.setBw(m.group(2));
			item.setSw(m.group(3));
			item.setGw(m.group(4));
			item.setUnits(m.group(5));
			item.setPer(m.group(6));
			
			items.add(item);
		}
		data.setItems(items);
		
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
        
        //创建节点 items; 给 items 节点添加子节点并赋值；
        for(int j=0;j<data.getItems().size();j++){
     	   Element itemEl = new Element("item");
     	   Item item= data.getItems().get(j);
     	   itemEl.setAttribute("no",item.getNo());
     	   itemEl.setAttribute("bw",item.getBw());
     	   itemEl.setAttribute("sw",item.getSw());
     	   itemEl.setAttribute("gw",item.getGw());
     	   itemEl.setAttribute("units",item.getUnits());
     	   itemEl.setAttribute("per",item.getPer());
     	   elements.addContent(itemEl);
        }
        
        
        // 给父节点root添加items子节点;    
	       root.addContent(elements);

	       XMLOutputter XMLOut = new XMLOutputter();
	       // 输出xml文件(文件名：当前期)玩法；	       
		   XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.FC3D_NUM_PATH_1+data.getPeriod()+".xml")));
		   	    
	}
	
	/**
	 * 直选复式分析  按位置统计
	 * 过滤内容并封装
	 * @param content
	 * @return
	 */
	public static DataModel fiterContentByPos(String content,String fxtype,String sort){
		
		DataModel data = new DataModel();
		
		ArrayList<Item> baiItems = new ArrayList<Item>();
		ArrayList<Item> shiItems = new ArrayList<Item>();
		ArrayList<Item> geItems = new ArrayList<Item>();
		
		ArrayList<Item> newBaiItems = new ArrayList<Item>();
		ArrayList<Item> newShiItems = new ArrayList<Item>();
		ArrayList<Item> newGeItems = new ArrayList<Item>();

		Matcher m = p11.matcher(content);
		data.setPlayType(fxtype);
		data.setOrderNo(new Integer(new Integer(sort)));
		
		if(m.find()){			
			data.setPeriod(m.group(2));			
		}
		
		m = p6.matcher(content);
		AtomicInteger ai = new AtomicInteger();
		while(m.find()){
			Item item = new Item();
			item.setPer(m.group(1));
			ai.incrementAndGet();
			if(ai.get()<=10){			
				baiItems.add(item);
			}else if(ai.get()>=11&&ai.get()<=20){
				shiItems.add(item);
			}else{
				geItems.add(item);
			}
		}
		ai.set(0);
		int bai = 0;
		int shi = 0;
		int ge = 0;
		m = p7.matcher(content);
		while(m.find()){
			ai.incrementAndGet();
			if(ai.get()<=10&&ai.get()>=0){
				Item item = baiItems.get(bai);
				item.setUnits(m.group(1));
				item.setNo(m.group(2));
				newBaiItems.add(item);
				bai++;
			}else if(ai.get()<=20&&ai.get()>=11){
				Item item = shiItems.get(shi);
				item.setUnits(m.group(1));
				item.setNo(m.group(2));
				newShiItems.add(item);
				shi++;
			}else{
				Item item = geItems.get(ge);
				item.setUnits(m.group(1));
				item.setNo(m.group(2));
				newGeItems.add(item);
				ge++;
			}
		}
		data.setBaiItems(newBaiItems);
		data.setShiItems(newShiItems);
		data.setGeItems(newGeItems);
		return data;
	}
	/**
	 * 直选复式分析  按位置统计
	 * 生成XML文件
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static void BuildXMLDocByPos(DataModel data) throws IOException, JDOMException {
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
           
           //创建节点 items; 给 items 节点添加子节点并赋值；
           Element baiEles = new Element("baiAreas");
           Element shiEles = new Element("shiAreas");
           Element geEles = new Element("geAreas");
           
           for(int j=0;j<data.getBaiItems().size();j++){
        	   Element itemEl = new Element("item");
        	   Item item= data.getBaiItems().get(j);
        	   itemEl.setAttribute("units",item.getUnits());
        	   itemEl.setAttribute("per",item.getPer());
        	   itemEl.setAttribute("num",item.getNo());
        	   baiEles.addContent(itemEl);
           }
           for(int j=0;j<data.getShiItems().size();j++){
        	   Element itemEl = new Element("item");
        	   Item item= data.getShiItems().get(j);
        	   itemEl.setAttribute("units",item.getUnits());
        	   itemEl.setAttribute("per",item.getPer());
        	   itemEl.setAttribute("num",item.getNo());
        	   shiEles.addContent(itemEl);
           }
           for(int j=0;j<data.getGeItems().size();j++){
        	   Element itemEl = new Element("item");
        	   Item item= data.getGeItems().get(j);
        	   itemEl.setAttribute("units",item.getUnits());
        	   itemEl.setAttribute("per",item.getPer());
        	   itemEl.setAttribute("num",item.getNo());
        	   geEles.addContent(itemEl);
           }
           elements.addContent(baiEles);
           elements.addContent(shiEles);
           elements.addContent(geEles);
           // 给父节点root添加items子节点;    
	       root.addContent(elements);

	       XMLOutputter XMLOut = new XMLOutputter();
	       // 输出xml文件(文件名：当前期_统计方式)玩法；	       
		   XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.FC3D_NUM_PATH_2+data.getPeriod()+".xml")));
	  }	

	/**
	 * 过滤内容并封装
	 * 组选复式分析
	 * 按单注号码
	 * @param content
	 * @return
	 */
	public static DataModel fiterContentByDZ(String content,String fxtype,String sort,String groupType){
		
		DataModel data = new DataModel();
		Matcher m = p9.matcher(content);
		
		data.setPlayType(fxtype);
		data.setOrderNo(new Integer(sort));
		data.setGroupType(groupType);
		if(m.find()){
			//data.setGroupType(m.group(1));			
			data.setPeriod(m.group(3));			
		}
		
		m = p8.matcher(content);
		ArrayList<Item> items = new ArrayList<Item>();
		
		while(m.find()){
			Item item = new Item();;
			item.setBw(m.group(1));
			item.setSw(m.group(2));
			item.setGw(m.group(3));
			item.setUnits(m.group(4));
			item.setPer(m.group(5));
			items.add(item);
		}
		data.setGroupItems(items);
		
		return data;
	}	

	
	/**
	 * 组选复式分析
	 * 按单注号码
	 * 生成XML文件
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static void BuildXMLDocByDZ(DataModel data) throws IOException, JDOMException {
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
           elements.setAttribute("groupType",data.getGroupType().toString());

           
           //创建节点 items; 给 items 节点添加子节点并赋值；
           for(int j=0;j<data.getGroupItems().size();j++){
        	   Element itemEl = new Element("item");
        	   Item item= data.getGroupItems().get(j);
        	   itemEl.setAttribute("gw",item.getGw());
        	   itemEl.setAttribute("sw",item.getSw());
        	   itemEl.setAttribute("bw",item.getBw());
        	   itemEl.setAttribute("units",item.getUnits());
        	   itemEl.setAttribute("per",item.getPer());
        	   elements.addContent(itemEl);
           }
          
           // 给父节点root添加items子节点;    
	       root.addContent(elements);

	       XMLOutputter XMLOut = new XMLOutputter();
	       // 输出xml文件(文件名：当前期_组选方式)玩法；
	       
	       //注意 按统计方式 fxtype=1是按单个号码 fxtype=2是按单注号码 	       
	       XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.FC3D_GROUP_PATH_1+data.getPeriod()+"_"+data.getGroupType()+".xml")));
	    }	

	
	/**
	 * 过滤内容并封装
	 * 组选复式分析
	 * 按单个号码
	 * @param content
	 * @return
	 */
	public static DataModel fiterContentByDG(String content,String fxtype,String sort,String groupType){
		
		DataModel data = new DataModel();
		Matcher m = p12.matcher(content);
		data.setPlayType(fxtype);
		data.setOrderNo(new Integer(sort));
		data.setGroupType(groupType);
		
		if(m.find()){
			//data.setGroupType(m.group(1));		
			data.setPeriod(m.group(3));
		}
		m = p10.matcher(content);

		ArrayList<Item> items = new ArrayList<Item>();
		
		while(m.find()){
			Item item = new Item();;
			item.setGw(m.group(1));
			item.setUnits(m.group(2));
			item.setPer(m.group(3));
			items.add(item);
		}
		data.setGroupItems(items);
		
		return data;
	}	
	
	/**
	 * 组选复式分析
	 * 按单个号码
	 * 生成XML文件
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static void BuildXMLDocByDG(DataModel data) throws IOException, JDOMException {
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
           elements.setAttribute("groupType",data.getGroupType().toString());

           
           //创建节点 items; 给 items 节点添加子节点并赋值；
           for(int j=0;j<data.getGroupItems().size();j++){
        	   Element itemEl = new Element("item");
        	   Item item= data.getGroupItems().get(j);
        	   itemEl.setAttribute("gw",item.getGw());
        	   itemEl.setAttribute("units",item.getUnits());
        	   itemEl.setAttribute("per",item.getPer());
        	   elements.addContent(itemEl);
           }
          
           // 给父节点root添加items子节点;    
	       root.addContent(elements);

	       XMLOutputter XMLOut = new XMLOutputter();
	       // 输出xml文件(文件名：当前期_统计方式)玩法；
	       
	       //注意 按统计方式 fxtype=1是按单个号码 fxtype=2是按单注号码 
	       XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.FC3D_GROUP_PATH_2+data.getPeriod()+"_"+data.getGroupType()+".xml")));
}	
	
}
