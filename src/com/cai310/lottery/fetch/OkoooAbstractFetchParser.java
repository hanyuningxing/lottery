package com.cai310.lottery.fetch;

import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
  
 

public abstract class OkoooAbstractFetchParser  {

	protected static final transient Logger logger = LoggerFactory.getLogger(OkoooAbstractFetchParser.class);
	
 
	public static  Div  getDivBy(String html, String charset,String attributeName,String attributeValue) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(Div.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof Div) {
				Div tag = (Div) nodeList.elementAt(i);
				if(attributeName==null){return tag;}
 				if(attributeValue.equals(tag.getAttribute(attributeName))){					
 					return tag;	 
 				}
 				 
			}
		}
		return null;
	}
	
	
	
	public static  List<Div>  getDivListBy(String html, String charset,String attributeName,String attributeValue) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(Div.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		List<Div> list = Lists.newArrayList();
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof Div) {
				Div tag = (Div) nodeList.elementAt(i);
 				if(attributeValue.equals(tag.getAttribute(attributeName))){	
 					list.add(tag);			  
 				}
			}
		}
		return list;
	}
	public static  Bullet  getBulletBy(String html, String charset,String attributeName,String attributeValue) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(Bullet.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof Bullet) {
				Bullet tag = (Bullet) nodeList.elementAt(i);
 				if(attributeValue.equals(tag.getAttribute(attributeName))){					
 					return tag;	 
 				}
 				 
			}
		}
		return null;
	}
	
	public static  List<BulletList>  getUlList(String html, String charset) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(BulletList.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		List<BulletList> ulList = Lists.newArrayList();
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof BulletList) {
				BulletList tag = (BulletList) nodeList.elementAt(i);
				ulList.add(tag);
			}
		}
		return ulList;
	}
	
	public TableColumn getTableColumn(String html, String charset,String attributeName,String attributeValue){
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(TableColumn.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
 
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableColumn) {
				TableColumn tag = (TableColumn) nodeList.elementAt(i);
				if(attributeValue.equals(tag.getAttribute(attributeName))){
					return tag;
				}
			}
		}
		return null;
	}
	
	public ParagraphTag getParagraph(String html, String charset,String attributeName,String attributeValue){
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(ParagraphTag.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
 
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof ParagraphTag) {
				ParagraphTag tag = (ParagraphTag) nodeList.elementAt(i);
				if(attributeValue.equals(tag.getAttribute(attributeName))){
					return tag;
				}
			}
		}
		return null;
	}
	public ImageTag getImageTag(String html, String charset,String attributeName,String attributeValue){
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(ImageTag.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
 
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof ImageTag) {
				ImageTag tag = (ImageTag) nodeList.elementAt(i);
				if(attributeName==null){
					return tag;
				}
				if(attributeValue.equals(tag.getAttribute(attributeName))){
					return tag;
				}
			}
		}
		return null;
	}
	
	
	protected  Span  getSpan(String html, String charset,String attributeName,String attributeValue) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(Span.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
 
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof Span) {
				Span tag = (Span) nodeList.elementAt(i);
				if(attributeName==null)return tag;
				if(attributeValue.equals(tag.getAttribute(attributeName))){
					return tag;
				}
			}
		}
		return null;
	}
	public static List<LinkTag>  getLinkTagList(String html, String charset) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);		
		NodeFilter[] filters = new NodeFilter[] { linkFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		List<LinkTag> list = Lists.newArrayList();
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof LinkTag) {
				LinkTag tag = (LinkTag) nodeList.elementAt(i);
				list.add(tag);
			}
		}
		return list;
	}
	public static LinkTag getLinkTag(String html, String charset) {
		Parser parser = Parser.createParser(html, charset);
		NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
		NodeFilter[] filters = new NodeFilter[] { linkFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			logger.warn("解析HTML出错.", e);
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof LinkTag) {
				LinkTag tag = (LinkTag) nodeList.elementAt(i);
				return tag;
			}
		}
		return null;
	}
	public static TableTag getTable(String html, String charset,String attributeName,String attributeValue) {
		Parser parser = Parser.createParser(html, charset);
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			logger.warn("解析HTML出错.", e);
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				if(attributeValue==null){
					return tag;
				}
				if(attributeValue.equals(tag.getAttribute(attributeName))){
					return tag;
				}
			}
		}
		return null;
	}
	
	public static  SelectTag  getSelectBy(String html, String charset,String attributeName,String attributeValue) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(SelectTag.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof SelectTag) {
				SelectTag tag = (SelectTag) nodeList.elementAt(i);
				if(attributeName==null){return tag;}
 				if(attributeValue.equals(tag.getAttribute(attributeName))){					
 					return tag;	 
 				}
 				 
			}
		}
		return null;
	}
	
	protected  List<Span>  getSpanList(String html, String charset,String attributeName,String attributeValue) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(Span.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		List<Span> list= Lists.newArrayList();
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof Span) {
				Span tag = (Span) nodeList.elementAt(i);
				if(attributeName==null){
					list.add(tag);
				}else{
					if(attributeValue.equals(tag.getAttribute(attributeName))){
						list.add(tag);
					}
				}
			}
		}
		return list;
	}
}
