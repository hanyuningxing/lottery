package com.cai310.lottery.web.controller.admin.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jdom.JDOMException;

import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.fetch.DataModel;
import com.cai310.lottery.task.BaseFetchTask;
import com.cai310.lottery.task.dlt.DltFetchTask;
import com.cai310.lottery.task.fc3d.Fc3dFetchTask;
import com.cai310.lottery.task.ssq.SsqFetchTask;
import com.cai310.lottery.task.szpl.SzplFetchTask;
import com.cai310.lottery.utils.HttpUtil;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.utils.Struts2Utils;


// 定义返回 success 时重定向到 fetch Action
public class FetchController extends AdminBaseController {
	private static final long serialVersionUID = 6455114611563106136L;
	
	private Pattern p1 = Pattern.compile(FetchConstant.SSQ_MATCH_CONDTION);
	
	private String period;
	private String fxtype;
	private String zxtype;
	private String sort;
	private String ssq_default;
	private String dlt_default;
	private String fc3d_num_default;
	private String fc3d_group_default;	
	private String szpl_num_default;
	private String szpl_group_default;

	// 进入首页
	public String index() {
		List ssq = init(FetchConstant.SSQ_CUR_URL,p1);
		List dlt = init(FetchConstant.DLT_CUR_URL,p1);
		List fc3d_num = init(FetchConstant.FC3D_NUM_URL,p1);
		List fc3d_pos = init(FetchConstant.FC3D_POS_URL,p1);
		List fc3d_dz = init(FetchConstant.FC3D_GROUP_DZ_URL,p1);
		List fc3d_dg = init(FetchConstant.FC3D_GROUP_DG_URL,p1);
		List szpl_num = init(FetchConstant.SZPL_NUM_URL,p1);
		List szpl_pos = init(FetchConstant.SZPL_POS_URL,p1);
		List szpl_dz = init(FetchConstant.SZPL_GROUP_DZ_URL,p1);
		List szpl_dg = init(FetchConstant.SZPL_GROUP_DG_URL,p1);
		
		Struts2Utils.setAttribute("ssq", ssq);
		if(ssq!=null&&ssq.size()>0){
			Struts2Utils.setAttribute("ssq_default", ssq.get(0));
		}
		Struts2Utils.setAttribute("dlt", dlt);
		if(dlt!=null&&dlt.size()>0){
			Struts2Utils.setAttribute("dlt_default", dlt.get(0));
		}
		Struts2Utils.setAttribute("fc3d_num", fc3d_num);
		if(fc3d_num!=null&&fc3d_num.size()>0){
			Struts2Utils.setAttribute("fc3d_num_default", fc3d_num.get(0));
		}
		Struts2Utils.setAttribute("fc3d_pos", fc3d_pos);
		Struts2Utils.setAttribute("fc3d_dz", fc3d_dz);
		if(fc3d_dz!=null&&fc3d_dz.size()>0){
			Struts2Utils.setAttribute("fc3d_group_default", fc3d_num.get(0));
		}
		Struts2Utils.setAttribute("fc3d_dg", fc3d_dg);
		
		Struts2Utils.setAttribute("szpl_num", szpl_num);
		if(szpl_num!=null&&szpl_num.size()>0){
			Struts2Utils.setAttribute("szpl_num_default", szpl_num.get(0));
		}
		Struts2Utils.setAttribute("szpl_pos", szpl_pos);
		Struts2Utils.setAttribute("szpl_dz", szpl_dz);
		if(fc3d_dz!=null&&fc3d_dz.size()>0){
			Struts2Utils.setAttribute("szpl_group_default", szpl_num.get(0));
		}
		Struts2Utils.setAttribute("szpl_dg", szpl_dg);
		return "index";
	}
	//初始化各下拉框选项
	//根据对应的URL获取相应的期数
	private ArrayList init(String url,Pattern p){
		ArrayList list = new ArrayList();
		String content=getContent(url);
		Matcher m = p.matcher(content);
		while(m.find()){
			list.add(m.group(1));
		}
		return list;
	}
	//获取内容
	private String getContent(String url){
		String content="";
		try {
			content = HttpUtil.getContentByURL(url, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	/**
	 * 双色球
	 * @return
	 */
	public String ssq(){
		HashMap result = new HashMap();
		if(ssq_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.SSQ_CUR_URL+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort;
		SsqFetchTask fetch = new SsqFetchTask();	
		buildXml(fetch,url,result);
		return null;
	}
	
	/**
	 * 大乐透
	 * @return
	 */
	public String dlt(){
		HashMap result = new HashMap();
		if(dlt_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.DLT_CUR_URL+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort;
		DltFetchTask fetch = new DltFetchTask();	
		buildXml(fetch,url,result);
		return null;
	}	
	
	/**
	 * 福彩3D 按号码统计
	 * @return
	 */
	public String fc3d_num(){
		HashMap result = new HashMap();
		if(fc3d_num_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.FC3D_NUM_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort;
		Fc3dFetchTask fetch = new Fc3dFetchTask();	
		buildXml(fetch,url,result);
		return null;
	}	
	/**
	 * 福彩3D 按位置统计
	 * @return
	 */
	public String fc3d_pos(){
		HashMap result = new HashMap();
		if(fc3d_num_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.FC3D_POS_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort;
		Fc3dFetchTask fetch = new Fc3dFetchTask();	
		try {
			DataModel data = fetch.fiterContentByPos(getContent(url),fxtype, sort);
			fetch.BuildXMLDocByPos(data);
			result.put("success", true);
			result.put("msg", "XML已生成");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		} catch (JDOMException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		}
		Struts2Utils.renderJson(result);
		return null;
	}	
	
	/**
	 * 福彩3D 组选复式分析 按单注统计
	 * @return
	 */
	public String fc3d_dz(){
		HashMap result = new HashMap();
		if(fc3d_group_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.FC3D_GROUP_DZ_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort+"&zxtype="+zxtype;
		Fc3dFetchTask fetch = new Fc3dFetchTask();	
		try {
			DataModel data = fetch.fiterContentByDZ(getContent(url),fxtype, sort,zxtype);
			fetch.BuildXMLDocByDZ(data);
			result.put("success", true);
			result.put("msg", "XML已生成");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		} catch (JDOMException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		}
		Struts2Utils.renderJson(result);
		return null;
	}		
	
	/**
	 * 福彩3D 组选复式分析 按单个统计
	 * @return
	 */
	public String fc3d_dg(){
		HashMap result = new HashMap();
		if(fc3d_group_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.FC3D_GROUP_DZ_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort+"&zxtype="+zxtype;
		Fc3dFetchTask fetch = new Fc3dFetchTask();	
		try {
			DataModel data = fetch.fiterContentByDG(getContent(url),fxtype, sort,zxtype);
			fetch.BuildXMLDocByDG(data);
			result.put("success", true);
			result.put("msg", "XML已生成");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		} catch (JDOMException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		}
		Struts2Utils.renderJson(result);
		return null;
	}		
	
	
	/**
	 * 数字排列  按号码统计
	 * @return
	 */
	public String szpl_num(){
		HashMap result = new HashMap();
		if(szpl_num_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.SZPL_NUM_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort;
		SzplFetchTask fetch = new SzplFetchTask();	
		buildXml(fetch,url,result);
		return null;
	}	
	/**
	 * 数字排列  按位置统计
	 * @return
	 */
	public String szpl_pos(){
		HashMap result = new HashMap();
		if(fc3d_num_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.SZPL_POS_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort;
		SzplFetchTask fetch = new SzplFetchTask();	
		try {
			DataModel data = fetch.fiterContentByPos(getContent(url),fxtype, sort);
			fetch.BuildXMLDocByPos(data);
			result.put("success", true);
			result.put("msg", "XML已生成");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		} catch (JDOMException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		}
		Struts2Utils.renderJson(result);
		return null;
	}		

	/**
	 * 数字排列 组选复式分析 按单注统计
	 * @return
	 */
	public String szpl_dz(){
		HashMap result = new HashMap();
		if(szpl_group_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.SZPL_GROUP_DZ_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort+"&zxtype="+zxtype;
		SzplFetchTask fetch = new SzplFetchTask();	
		try {
			DataModel data = fetch.fiterContentByDZ(getContent(url),fxtype, sort,zxtype);
			fetch.BuildXMLDocByDZ(data);
			result.put("success", true);
			result.put("msg", "XML已生成");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		} catch (JDOMException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		}
		Struts2Utils.renderJson(result);
		return null;
	}		
	
	/**
	 * 数字排列 组选复式分析 按单个统计
	 * @return
	 */
	public String szpl_dg(){
		HashMap result = new HashMap();
		if(szpl_group_default.equals(period)){
			result.put("success", false);
			result.put("msg", "当前期暂无数据");
			Struts2Utils.renderJson(result);
			return null;
		}
		String url = FetchConstant.SZPL_GROUP_DZ_URL_1+"?expect="+period+"&fxtype="+fxtype+"&sort="+sort+"&zxtype="+zxtype;
		SzplFetchTask fetch = new SzplFetchTask();	
		try {
			DataModel data = fetch.fiterContentByDG(getContent(url),fxtype, sort,zxtype);
			fetch.BuildXMLDocByDG(data);
			result.put("success", true);
			result.put("msg", "XML已生成");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		} catch (JDOMException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		}
		Struts2Utils.renderJson(result);
		return null;
	}			
	/**
	 * 双色球 大乐透 生成XML文件
	 * @param fetch
	 * @param url
	 * @param result
	 */
	private void buildXml(BaseFetchTask fetch,String url,HashMap result){
		try {
			DataModel data = fetch.fiterContent(getContent(url),fxtype, sort);
			fetch.BuildXMLDoc(data);
			result.put("success", true);
			result.put("msg", "XML已生成");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		} catch (JDOMException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "XML生成失败");
		}
		Struts2Utils.renderJson(result);
	}
	
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getFxtype() {
		return fxtype;
	}
	public void setFxtype(String fxtype) {
		this.fxtype = fxtype;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSsq_default() {
		return ssq_default;
	}
	public void setSsq_default(String ssq_default) {
		this.ssq_default = ssq_default;
	}
	public String getDlt_default() {
		return dlt_default;
	}
	public void setDlt_default(String dlt_default) {
		this.dlt_default = dlt_default;
	}
	public String getFc3d_num_default() {
		return fc3d_num_default;
	}
	public void setFc3d_num_default(String fc3d_num_default) {
		this.fc3d_num_default = fc3d_num_default;
	}
	public String getFc3d_group_default() {
		return fc3d_group_default;
	}
	public void setFc3d_group_default(String fc3d_group_default) {
		this.fc3d_group_default = fc3d_group_default;
	}
	public String getZxtype() {
		return zxtype;
	}
	public void setZxtype(String zxtype) {
		this.zxtype = zxtype;
	}
	public String getSzpl_num_default() {
		return szpl_num_default;
	}
	public void setSzpl_num_default(String szpl_num_default) {
		this.szpl_num_default = szpl_num_default;
	}
	public String getSzpl_group_default() {
		return szpl_group_default;
	}
	public void setSzpl_group_default(String szpl_group_default) {
		this.szpl_group_default = szpl_group_default;
	}
	
}
