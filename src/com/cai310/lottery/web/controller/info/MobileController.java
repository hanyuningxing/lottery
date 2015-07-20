package com.cai310.lottery.web.controller.info;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.entity.info.Mobile;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.info.MobileEntityManager;
import com.cai310.lottery.support.NumberSaleBean;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.Struts2Utils;
import com.cai310.utils.WriteHTMLUtil;

@Results({
	@Result(name = "list", location = "/WEB-INF/content/zj_mobile/zj_mobile.ftl"),
	@Result(name = "add", location = "/WEB-INF/content/zj_mobile/zj_mobile_add.ftl"),
	@Result(name = "upload_result", location = "/WEB-INF/content/zj_mobile/zj_mobile_result.ftl")})
public class MobileController extends BaseController{
	private static final long serialVersionUID = 5851729092711635404L;
	
	@Autowired
	private MobileEntityManager mobileEntityManager;
	
	private File file;
	
	List<Mobile> mobileList = new ArrayList<Mobile>();
	/**
	 * 
	 * 增加手机号码
	 */
	public String addMobile() {
		String mobileStr = Struts2Utils.getParameter("mobile");
		if(StringUtils.isNotBlank(mobileStr)){
			if(mobileEntityManager.getMobileBy(mobileStr)==null){
				Mobile mobile = new Mobile();
				mobile.setNumber(mobileStr);
				mobileEntityManager.save(mobile);
				this.jsonMap.put("msg", "操作成功.");
				return success();
			}else {
				this.jsonMap.put("msg", "此号码已存在.");
			}
		} else {
			this.jsonMap.put("msg", "请输入您的手机号码.");
		}														
		return faile();
	}	
	
	public String addMobiles(){
		try {
			String content = this.getUploadContent();
			if(StringUtils.isBlank(content)){
				Struts2Utils.setAttribute("result", "上传失败.");
				return "upload_result";
			}			
				
			String [] strArr = content.split("\r\n");
			int size = strArr.length;
			for(int i=0; i<size; i++) {
				if(StringUtils.isNotBlank(strArr[i]) && mobileEntityManager.getMobileBy(strArr[i])==null){
					Mobile mobile = new Mobile();
					mobile.setNumber(strArr[i]);
					mobileEntityManager.save(mobile);
				}				
			}
			Struts2Utils.setAttribute("result", "上传成功.");
		} catch (DataException e) {
			Struts2Utils.setAttribute("result", "上传失败.");
		}
		return "upload_result";
	}
	
	public String list() {
		mobileList = mobileEntityManager.getAll();
		return "list";
	}
	//转向增加号码页面
	public String add() {
		
		return "add";
	}
	
	/**
	 * 获取上传文件的内容
	 * 
	 * @return 上传文件的内容
	 * @throws DataException
	 */
	private String getUploadContent() throws DataException {
		if (file == null)
			throw new DataException("上传文件不存在.");

		try {
			return StringUtils.trim(IOUtils.toString(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			logger.warn("读取上传文件发生异常.", e);
			throw new DataException("读取上传文件发生异常.");
		} catch (IOException e) {
			logger.warn("读取上传文件发生异常.", e);
			throw new DataException("读取上传文件发生异常.");
		}
	}

	public List<Mobile> getMobileList() {
		return mobileList;
	}

	public void setMobileList(List<Mobile> mobileList) {
		this.mobileList = mobileList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
}
