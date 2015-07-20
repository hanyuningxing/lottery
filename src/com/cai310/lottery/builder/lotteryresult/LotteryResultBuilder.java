package com.cai310.lottery.builder.lotteryresult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.builder.AbstractInfoBuilder;
import com.cai310.lottery.builder.DefaultInfoBuilderCreateConfig;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.dlt.impl.DltPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcPeriodDataEntityManagerImpl;

/**
 * Description: 开奖信息静态化工具类<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * 
 * @author zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class LotteryResultBuilder extends AbstractInfoBuilder {
	// /dlt
	@Resource
	private DltPeriodDataEntityManagerImpl dltPeriodDataEntityManagerImpl;

	// /双色球
	@Resource
	private SsqPeriodDataEntityManagerImpl ssqPeriodDataEntityManagerImpl;
	// /排列
	@Resource
	private PlPeriodDataEntityManagerImpl plPeriodDataEntityManagerImpl;
	// /3D
	@Resource
	private Welfare3dPeriodDataEntityManagerImpl welfare3dPeriodDataEntityManagerImpl;

	// /四场
	@Resource
	private SczcPeriodDataEntityManagerImpl sczcPeriodDataEntityManagerImpl;

	// /六场
	@Resource
	private LczcPeriodDataEntityManagerImpl lczcPeriodDataEntityManagerImpl;

	// /14,9场
	@Resource
	private SfzcPeriodDataEntityManagerImpl sfzcPeriodDataEntityManagerImpl;
	
	// /22选5
	@Resource
	private Tc22to5PeriodDataEntityManagerImpl tc22to5PeriodDataEntityManagerImpl;
	
	// 7乐彩
	@Resource
	private SevenPeriodDataEntityManagerImpl sevenPeriodDataEntityManagerImpl;
	
	// 好彩一
	@Resource
	private Welfare36to7PeriodDataEntityManagerImpl welfare36to7PeriodDataEntityManagerImpl;

	@Autowired
	protected PeriodEntityManager periodManager;
	@Autowired
	protected QueryService queryService;
	// 查询器
	private LotteryResultBuilderQueryForm queryForm;

	// 生成器
	private DefaultInfoBuilderCreateConfig createConfig;

	public DefaultInfoBuilderCreateConfig getCreateConfig() {
		return createConfig;
	}

	public void setCreateConfig(DefaultInfoBuilderCreateConfig createConfig) {
		this.createConfig = createConfig;
	}

	public void createNewIndexFile() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// /双色球
		SsqPeriodData ssqPeriodData = ssqPeriodDataEntityManagerImpl
				.getNewestResultPeriodData();
		Period period;
		if (null != ssqPeriodData
				&& StringUtils.isNotBlank(ssqPeriodData.getResult())) {
			period = periodManager.getPeriod(ssqPeriodData.getPeriodId());
			if (null != period) {
				contents.put("ssqPeriod", period);
				contents.put("ssqPeriodData", ssqPeriodData);
			}
		}
		// ///大乐透
		DltPeriodData dltPeriodData = dltPeriodDataEntityManagerImpl
				.getNewestResultPeriodData();
		if (null != dltPeriodData
				&& StringUtils.isNotBlank(dltPeriodData.getResult())) {
			period = periodManager.getPeriod(dltPeriodData.getPeriodId());
			if (null != period) {
				contents.put("dltPeriod", period);
				contents.put("dltPeriodData", dltPeriodData);

			}
		}
		// /排列
		PlPeriodData plPeriodData = plPeriodDataEntityManagerImpl
				.getNewestResultPeriodData();
		if (null != plPeriodData
				&& StringUtils.isNotBlank(plPeriodData.getResult())) {
			period = periodManager.getPeriod(plPeriodData.getPeriodId());
			if (null != period) {
				contents.put("plPeriod", period);
				contents.put("plPeriodData", plPeriodData);
			}
		}
		// 3d
		Welfare3dPeriodData welfare3dPeriodData = welfare3dPeriodDataEntityManagerImpl
				.getNewestResultPeriodData();
		if (null != welfare3dPeriodData
				&& StringUtils.isNotBlank(welfare3dPeriodData.getResult())) {
			period = periodManager.getPeriod(welfare3dPeriodData.getPeriodId());
			if (null != period) {
				contents.put("welfare3dPeriod", period);
				contents.put("welfare3dPeriodData", welfare3dPeriodData);
			}
		}
		// 22选5
		Tc22to5PeriodData tc22to5PeriodData = tc22to5PeriodDataEntityManagerImpl
				.getNewestResultPeriodData();
		if (null != tc22to5PeriodData
				&& StringUtils.isNotBlank(tc22to5PeriodData.getResult())) {
			period = periodManager.getPeriod(tc22to5PeriodData.getPeriodId());
			if (null != period) {
				contents.put("tc22to5Period", period);
				contents.put("tc22to5PeriodData", tc22to5PeriodData);
			}
		}
		
		// 7乐彩
		SevenPeriodData sevenPeriodData = sevenPeriodDataEntityManagerImpl
				.getNewestResultPeriodData();
		if (null != sevenPeriodData
				&& StringUtils.isNotBlank(sevenPeriodData.getResult())) {
			period = periodManager.getPeriod(sevenPeriodData.getPeriodId());
			if (null != period) {
				contents.put("sevenPeriod", period);
				contents.put("sevenPeriodData", sevenPeriodData);
			}
		}
		
		
		// 好彩一
		Welfare36to7PeriodData welfare36to7PeriodData = welfare36to7PeriodDataEntityManagerImpl
				.getNewestResultPeriodData();
		if (null != welfare36to7PeriodData
				&& StringUtils.isNotBlank(welfare36to7PeriodData.getResult())) {
			period = periodManager.getPeriod(welfare36to7PeriodData.getPeriodId());
			if (null != period) {
				contents.put("welfare36to7Period", period);
				contents.put("welfare36to7PeriodData", welfare36to7PeriodData);
			}
		}
		
		
		

		String templatePath = createConfig.getTemplatePath();
		String indexTemplateName = createConfig.getTemplateName();
		String indexTargetFilePath = createConfig.getTargetFilePath();
		String indexTargetFileName = createConfig.getTargetFileName();
		contents.put("base", Constant.BASEPATH);

		// 生成最新开奖公告XML文件
		this.buildResultXml(contents);
		super.createNewsFile(contents, templatePath, indexTemplateName,
				indexTargetFilePath, indexTargetFileName);

	}

	public LotteryResultBuilderQueryForm getQueryForm() {
		return queryForm;
	}

	public void setQueryForm(LotteryResultBuilderQueryForm queryForm) {
		this.queryForm = queryForm;
	}

	/**
	 * 生成XML文件 为车业务提供最新开奖公告
	 * 
	 * @param data
	 * @throws IOException
	 * @throws JDOMException
	 */
	private void buildResultXml(Map content) {
		// 创建根节点 root;
		Element root = new Element("root");

		// 根节点添加到文档中；
		Document Doc = new Document(root);
		Period ssqPeriod = (Period) content.get("ssqPeriod");
		PeriodData ssqPeriodData = (PeriodData) content.get("ssqPeriodData");
		Period dltPeriod = (Period) content.get("dltPeriod");
		PeriodData dltPeriodData = (PeriodData) content.get("dltPeriodData");
		Period plPeriod = (Period) content.get("plPeriod");
		PeriodData plPeriodData = (PeriodData) content.get("plPeriodData");
		Period welfare3dPeriod = (Period) content.get("welfare3dPeriod");
		PeriodData welfare3dPeriodData = (PeriodData) content.get("welfare3dPeriodData");
		Period tc22to5Period = (Period) content.get("tc22to5Period");
		PeriodData tc22to5PeriodData = (PeriodData) content.get("tc22to5PeriodData");

		pushContent(root, ssqPeriod, ssqPeriodData);
		pushContent(root, dltPeriod, dltPeriodData);
		// 排列五
		pushContent(root, plPeriod, plPeriodData);
		// 排列三
		pushContent(root, plPeriod, plPeriodData);
		pushContent(root, welfare3dPeriod, welfare3dPeriodData);
		pushContent(root, tc22to5Period, tc22to5PeriodData);

		XMLOutputter XMLOut = new XMLOutputter();

		try {
			XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH
					+ FetchConstant.LAST_RESULT + "result.xml")));
			Log.info("【开奖公告生成XML文件成功】");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.info("【开奖公告生成XML文件失败】");
		} catch (IOException e) {
			e.printStackTrace();
			Log.info("【开奖公告生成XML文件失败】");
		}
	}

	private void pushContent(Element root, Period period, PeriodData periodData) {
		if(period==null)return;
		Element item = new Element("item");
		if (null != period && null != period.getLotteryType())
			item.setAttribute("lotteryType", period.getLotteryType()
					.getLotteryName());
		if (StringUtils.isNotBlank(period.getPeriodNumber()))
			item.setAttribute("periodNumber", period.getPeriodNumber());
		if (StringUtils.isNotBlank(periodData.getResult()))
			item.setAttribute("result", periodData.getResult());
		// item.setAttribute("tzfbURL",base+"/tzfb/tzfb!ssq_link?playType=1&&period="+period.getPeriodNumber()+"&&result="+periodData.getResult()+"&&preriodId="+period.getId());
		// item.setAttribute("mlfxURL",base+"/info/news!index.action?lottery="+period.getLotteryType()+"&&infoType=FORECAST");
		// item.setAttribute("mcpURL",base+"/"+period.getLotteryType().getKey()+"/scheme!editNew.action");
		// item.setAttribute("hmURL",base+"/"+period.getLotteryType().getKey()+"/scheme!subList.action");
		root.addContent(item);
	}
}
