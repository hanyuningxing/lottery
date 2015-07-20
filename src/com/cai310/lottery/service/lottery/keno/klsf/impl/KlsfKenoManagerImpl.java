package com.cai310.lottery.service.lottery.keno.klsf.impl;

import javax.annotation.Resource;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.klsf.KlsfSchemeDao;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfIssueData;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("klsfKenoManagerImpl")
public class KlsfKenoManagerImpl extends KenoManagerImpl<KlsfIssueData, KlsfScheme> {

	@Resource(name = "klsfKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<KlsfIssueData, KlsfScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.KLSF;
	}
	@Resource(name = "klsfKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlsfIssueData, KlsfScheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Autowired
	private KlsfSchemeDao schemeDao;
	@Override
	protected String autoGetResultData(String issueNumber) {
//		issueNumber = issueNumber.replaceAll("(\\d{8})(\\d{2})", "$1-$2");
//		String text = null;
//		try {
//			text = PageGrabber.readRemoteHtmlData("http://video.shishicai.cn/Lottery/Speed/FCGDKL10/Trend/mt_zs_jrzh.aspx","UTF-8");
//		} catch (Exception e) {
//			System.out.println("抓取开奖出错!");
//			//e.printStackTrace();
//		}
//		if(StringUtils.isNotBlank(text)){
//			Pattern pattern = Pattern.compile("\"" + issueNumber + "\\|([^\"]+?)\"",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
//			Matcher matcher = pattern.matcher(text);
//			String results = null;
//			if(matcher.find()){
//				results = matcher.group(1);
//				if(results != null){
//					return results.trim();
//				}
//			}
//		}
    	return null;
	}
	@Override
	public void updateMissData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected SchemeDao<KlsfScheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}

}
