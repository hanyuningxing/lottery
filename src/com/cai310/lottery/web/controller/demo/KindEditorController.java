package com.cai310.lottery.web.controller.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.utils.FileUtils;
import com.cai310.utils.Struts2Utils;

@Namespace("/kindEditor")
@Action(value = "kindEditor")
@Results({ 
		@Result(name = "index", location = "/WEB-INF/content/demo/uploadImg/index.ftl") })
public class KindEditorController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3545448995862735395L;
	
	/** 图片文件 **/
	private File imgFile; 
	
	/** 文件名称 **/
	private String imgFileFileName;
	
	/** 文件宽度 **/
	private String imgWidth;
	
	/** 文件高度 **/
	private String imgHeight;
	
	/** 文件对齐方式 **/
	private String align;
	
	/** 图片标题 **/
	private String imgTitle;
	
	public String index() {
		return "index";
	}
	
	public String uploadImg() {
		
		Struts2Utils.getResponse().setContentType("text/html; charset=UTF-8");
		
		// 文件保存目录路径
		String savePath = ServletActionContext.getServletContext().getRealPath("/") + "uploadImg/";
		
		// 文件保存目录URL
		String saveUrl = Struts2Utils.getRequest().getContextPath() + "/uploadImg/";
		
		// 定义允许上传的文件扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };
		
		// 最大文件大小
		long maxSize = 2*1024*1024;
		
		PrintWriter out = null;
		
		try {
			out = Struts2Utils.getResponse().getWriter();
		} catch (IOException e1) {
			this.logger.warn(e1.getMessage(),e1);
		}
		
		if (imgFile == null) {
			out.println(getError("请选择文件。"));
			return null;
		}
		//检查目录
		 File uploadDir = new File(savePath);
		 if (!uploadDir.isDirectory()) {
			 out.println(getError("上传目录不存在。"));
			 return null;
		 }
		//检查目录写权限
		if(!uploadDir.canWrite()){
			out.println(getError("上传目录没有写权限。"));
			return null;
		}				
		
		// 创建文件夹
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		String fileExt = imgFileFileName.substring(imgFileFileName.lastIndexOf(".") + 1).toLowerCase();
		if (!Arrays.<String> asList(fileTypes).contains(fileExt)) {
			out.println(getError("上传文件扩展名[" + fileExt + "]是不允许的扩展名。"));
			return null;
		}
		if (imgFile.length() > maxSize) {
			out.println(getError("[ " + imgFileFileName + " ]超过单个文件大小限制，文件大小[ " + imgFile.length() + " ]，限制为[ " + maxSize + " ] "));
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		File uploadedFile = new File(savePath, newFileName);
		try {
			FileUtils.copy(imgFile, uploadedFile);
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", saveUrl + newFileName);
			this.logger.debug(obj.toString());
			out.println(obj.toString());
			this.logger.debug("上传图片:[" + uploadedFile.getName() + "]" + ">>>[" + newFileName + "]成功");
		} catch (IOException e) {
			this.logger.warn("图片上传失败:" + e);
		}
		return null;
	}
		
	
	 private String getError(String message) {
		 JSONObject obj = new JSONObject();
		 obj.put("error", 1);
		 obj.put("message", message);
		 this.logger.debug(obj.toString());
		 return obj.toString();
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}

	public String getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	

}
