package com.cai310.lottery.web.controller.info.passcount;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.utils.CommonUtil;
import com.cai310.lottery.utils.PasscountUtil;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.info.passcount.form.DltSchemeWonInfo;
import com.cai310.lottery.web.controller.info.passcount.form.LczcData;
import com.cai310.lottery.web.controller.info.passcount.form.NumberData;
import com.cai310.lottery.web.controller.info.passcount.form.PassCountIssue;
import com.cai310.lottery.web.controller.info.passcount.form.SczcData;
import com.cai310.lottery.web.controller.info.passcount.form.SfzcData;
import com.cai310.lottery.web.controller.info.passcount.form.SsqSchemeWonInfo;
import com.cai310.lottery.web.controller.info.passcount.form.ZcSchemeWonInfo;
import com.cai310.lottery.web.controller.info.passcount.visitor.DltPassCountSchemeVisitor;
import com.cai310.lottery.web.controller.info.passcount.visitor.LczcDataVisitor;
import com.cai310.lottery.web.controller.info.passcount.visitor.NumberDataVisitor;
import com.cai310.lottery.web.controller.info.passcount.visitor.PassCountIssueVisitor;
import com.cai310.lottery.web.controller.info.passcount.visitor.SczcDataVisitor;
import com.cai310.lottery.web.controller.info.passcount.visitor.SfzcDataVisitor;
import com.cai310.lottery.web.controller.info.passcount.visitor.SsqPassCountSchemeVisitor;
import com.cai310.lottery.web.controller.info.passcount.visitor.ZcPassCountSchemeVisitor;
import com.cai310.orm.Pagination;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.Struts2Utils;
@Namespace("/passcount")
@Action("index")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl")
	})
public class IndexController extends BaseController {
	private static final long serialVersionUID = -3004288085663395253L;
	private Pagination pagination = new Pagination(20);
	private Lottery lottery;//彩种
	private Integer pt;//玩法     胜负彩0,任九1;
	
	/**方案状态*/
	private SchemeState state;
	/**
	 * 方案投注的方式
	 */
	private SalesMode mode;
	
	private String searchStr;

	private String periodNumber;
	//大乐透追加 0=全部 1=普通方案 2=追加方案
	private Integer dltAdd=0;
	
	private Boolean mine;
	
	private Boolean won;
	
	private Integer menuType;
	
	private List<PassCountIssue> issueList;
	
	//14场9场的数据
	private SfzcData sfzcData;
	
	//4场的数据
	private SczcData sczcData;
	
	//6场的数据
	private LczcData lczcData;
	
	//数字彩的数据
	private NumberData numberData;
	
	private User user;
	
	public String index() {
		try {
			issueList = getPassCountIssueList();
			if(null==state){
				state = SchemeState.SUCCESS;
			}
			if(null==menuType){
				menuType = 1;
			}
			if(null!=issueList&&!issueList.isEmpty()){
				if(StringUtils.isBlank(periodNumber)){
					periodNumber=((PassCountIssue)issueList.get(0)).getPeriodNumber();
				}
				if(StringUtils.isBlank(periodNumber)){
					//取不到期报错
					throw new WebDataException("取不到期报错.");
				}
				Boolean containPeriodNumber=Boolean.FALSE;
				for (PassCountIssue passCountIssue : issueList) {
					if(periodNumber.trim().equals(passCountIssue.getPeriodNumber().trim())){
						containPeriodNumber=Boolean.TRUE;
					}
				}
				if(!containPeriodNumber){
					//列表不包含期号报错
					throw new WebDataException("列表不包含期号报错.");
				}
				user=this.getLoginUser();
				if(null!=mine&&mine){
					if(null==user){
						//先登录
						CookieUtil.addReUrlCookie();
						return GlobalResults.FWD_LOGIN;
					}else{
						this.setSearchStr(user.getUserName());
					}
				}
				if(LotteryCategory.NUMBER.equals(lottery.getCategory())){
					////数字彩
					if(Lottery.DLT.equals(lottery)){
						List<DltSchemeWonInfo> resultList=getDltSchemeWonInfoList();
						if(null!=resultList&&!resultList.isEmpty()){
							int start = pagination.getFirst();
							int end = start +pagination.getPageSize();
							pagination.setTotalCount(resultList.size());
							if(end > resultList.size())
							    end =resultList.size();
							List l = resultList.subList(start,end);
							pagination.setResult(l);
						}
						putNumberData();
					}
					if(Lottery.SSQ.equals(lottery)){
						List<SsqSchemeWonInfo> resultList=getSsqSchemeWonInfoList();
						if(null!=resultList&&!resultList.isEmpty()){
							int start = pagination.getFirst();
							int end = start +pagination.getPageSize();
							pagination.setTotalCount(resultList.size());
							if(end > resultList.size())
							    end =resultList.size();
							List l = resultList.subList(start,end);
							pagination.setResult(l);
						}
						putNumberData();
					}
				}else if(LotteryCategory.ZC.equals(lottery.getCategory())){
					List<ZcSchemeWonInfo> resultList=getZcSchemeWonInfoList();
					if(null!=resultList&&!resultList.isEmpty()){
						int start = pagination.getFirst();
						int end = start +pagination.getPageSize();
						pagination.setTotalCount(resultList.size());
						if(end > resultList.size())
						    end =resultList.size();
						List l = resultList.subList(start,end);
						pagination.setResult(l);
					}
					if(Lottery.SFZC.equals(lottery)){
						//14场9场
						putSfzcData();
					}if(Lottery.SCZC.equals(lottery)){
						//4场
						putSczcData();
					}if(Lottery.LCZC.equals(lottery)){
						//6场
						putLczcData();
					}
				}else{
					throw new ServiceException("错误-过关统计期彩票类型出错");
				}
				return "index";
			}else{
				//取不到列表报错
				throw new WebDataException("取不到列表报错.");
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}
	private void putNumberData(){
		try {
			String fileName = periodNumber+"_data.xml";
			String dir = PasscountUtil.getPeriodDir(lottery);
			String context = Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			NumberDataVisitor numberDataVisitor=new NumberDataVisitor();
			doc.accept(numberDataVisitor);
			if(null!=numberDataVisitor.getNumberData()){
				this.setNumberData(numberDataVisitor.getNumberData());
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
		}
	}
	
	private void putSfzcData(){
		try {
			String fileName=periodNumber+"_data.xml";
			String dir = null;
			if(Integer.valueOf(0).equals(pt)){
					dir=PasscountUtil.getPeriodDir(lottery)+PlayType.SFZC14.name()+"/";
			}else if(Integer.valueOf(1).equals(pt)){
					dir=PasscountUtil.getPeriodDir(lottery)+PlayType.SFZC9.name()+"/";
			}
			String context=Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			SfzcDataVisitor sfzcDataVisitor=new SfzcDataVisitor();
			doc.accept(sfzcDataVisitor);
			if(null!=sfzcDataVisitor.getSfzcData()){
				this.setSfzcData(sfzcDataVisitor.getSfzcData());
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
		}
	}
	private void putSczcData(){
		try {
			String fileName = periodNumber+"_data.xml";
			String dir = PasscountUtil.getPeriodDir(lottery);
			String context = Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			SczcDataVisitor sczcDataVisitor=new SczcDataVisitor();
			doc.accept(sczcDataVisitor);
			if(null!=sczcDataVisitor.getSczcData()){
				this.setSczcData(sczcDataVisitor.getSczcData());
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
		}
	}
	private void putLczcData(){
		try {
			String fileName = periodNumber+"_data.xml";
			String dir = PasscountUtil.getPeriodDir(lottery);
			String context = Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			LczcDataVisitor lczcDataVisitor=new LczcDataVisitor();
			doc.accept(lczcDataVisitor);
			if(null!=lczcDataVisitor.getLczcData()){
				this.setLczcData(lczcDataVisitor.getLczcData());
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
		}
	}
	
	private List<ZcSchemeWonInfo>  getZcSchemeWonInfoList() throws WebDataException{
		try {
			String fileName=periodNumber+".xml";
			String dir = null;
			if(Lottery.SFZC.equals(lottery)){
				if(Integer.valueOf(0).equals(pt)){
					dir=PasscountUtil.getPeriodDir(lottery)+PlayType.SFZC14.name()+"/";
				}else if(Integer.valueOf(1).equals(pt)){
					dir=PasscountUtil.getPeriodDir(lottery)+PlayType.SFZC9.name()+"/";
				}
				
			}else{
				dir=PasscountUtil.getPeriodDir(lottery);
			}
			String context=Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			if (!file.exists()) {
				 throw new WebDataException("过关文件不存在.");
			}
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			ZcPassCountSchemeVisitor zcPassCountSchemeVisitor=new ZcPassCountSchemeVisitor();
			zcPassCountSchemeVisitor.setSearchStr(searchStr);
			zcPassCountSchemeVisitor.setMode(mode);
			zcPassCountSchemeVisitor.setState(state);
			zcPassCountSchemeVisitor.setLottery(lottery);
			zcPassCountSchemeVisitor.setPlayType(pt);
			zcPassCountSchemeVisitor.setWon(won);
			doc.accept(zcPassCountSchemeVisitor);
			if(null!=zcPassCountSchemeVisitor.getZcSchemeWonInfoList()&&!zcPassCountSchemeVisitor.getZcSchemeWonInfoList().isEmpty()){
				return zcPassCountSchemeVisitor.getZcSchemeWonInfoList();
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
			throw new WebDataException("取过关文件出错.");
		}
		return null;
	}
	
	private List<DltSchemeWonInfo> getDltSchemeWonInfoList() throws WebDataException{
		try {
			String fileName=periodNumber+".xml";
			String dir=PasscountUtil.getPeriodDir(lottery);
			String context=Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			if (!file.exists()) {
				throw new WebDataException("过关文件不存在.");
			}
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			DltPassCountSchemeVisitor dltPassCountSchemeVisitor=new DltPassCountSchemeVisitor();
			dltPassCountSchemeVisitor.setDltAdd(dltAdd);
			dltPassCountSchemeVisitor.setSearchStr(searchStr);
			dltPassCountSchemeVisitor.setMode(mode);
			dltPassCountSchemeVisitor.setState(state);
			dltPassCountSchemeVisitor.setLottery(lottery);
			dltPassCountSchemeVisitor.setWon(won);
			doc.accept(dltPassCountSchemeVisitor);
			if(null!=dltPassCountSchemeVisitor.getDltSchemeWonInfoList()&&!dltPassCountSchemeVisitor.getDltSchemeWonInfoList().isEmpty()){
				return dltPassCountSchemeVisitor.getDltSchemeWonInfoList();
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
			throw new WebDataException("取过关文件出错.");
		}
		return null;
	}
	private List<SsqSchemeWonInfo> getSsqSchemeWonInfoList() throws WebDataException{
		try {
			String fileName=periodNumber+".xml";
			String dir=PasscountUtil.getPeriodDir(lottery);
			String context=Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			if (!file.exists()) {
				throw new WebDataException("过关文件不存在.");
			}
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			SsqPassCountSchemeVisitor ssqPassCountSchemeVisitor=new SsqPassCountSchemeVisitor();
			ssqPassCountSchemeVisitor.setSearchStr(searchStr);
			ssqPassCountSchemeVisitor.setMode(mode);
			ssqPassCountSchemeVisitor.setState(state);
			ssqPassCountSchemeVisitor.setLottery(lottery);
			ssqPassCountSchemeVisitor.setPlayType(pt);
			ssqPassCountSchemeVisitor.setWon(won);
			doc.accept(ssqPassCountSchemeVisitor);
			if(null!=ssqPassCountSchemeVisitor.getSsqSchemeWonInfoList()&&!ssqPassCountSchemeVisitor.getSsqSchemeWonInfoList().isEmpty()){
				return ssqPassCountSchemeVisitor.getSsqSchemeWonInfoList();
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
			throw new WebDataException("取过关文件出错.");
		}
		return null;
	}
	private List<PassCountIssue> getPassCountIssueList() throws WebDataException{
		try {
			String fileName="issue.xml";
			String dir = null;
			if(null==lottery){
				lottery = Lottery.SFZC;
				pt=0;
			}
			if(Lottery.SFZC.equals(lottery)){
				if(Integer.valueOf(0).equals(pt)){
					dir=PasscountUtil.getPeriodDir(lottery)+PlayType.SFZC14.name()+"/";
				}else if(Integer.valueOf(1).equals(pt)){
					dir=PasscountUtil.getPeriodDir(lottery)+PlayType.SFZC9.name()+"/";
				}
				
			}else{
				dir=PasscountUtil.getPeriodDir(lottery);
			}
			String context=Constant.ROOTPATH;
			if(null==context){
				context=Struts2Utils.getSession().getServletContext().getRealPath("/");
			}
			File file = new File(CommonUtil.combinePath(context,dir+fileName));
			if (!file.exists()) {
				throw new WebDataException("过关期号不存在.");
			}
			SAXReader saxReader=new SAXReader();
			Document doc=saxReader.read(file);
			PassCountIssueVisitor passCountIssueVisitor=new PassCountIssueVisitor();
			doc.accept(passCountIssueVisitor);
			if(null!=passCountIssueVisitor.getIssueList()&&!passCountIssueVisitor.getIssueList().isEmpty()){
				return passCountIssueVisitor.getIssueList();
			}
		} catch (DocumentException e) {
			this.logger.warn(e.getMessage());
			throw new WebDataException("取过关期号出错.");
		}
		return null;
	}

	public Pagination getPagination() {
		return pagination;
	}


	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}


	/**
	 * @return the periodNumber
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}



	/**
	 * @param periodNumber the periodNumber to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return the pt
	 */
	public Integer getPt() {
		return pt;
	}

	/**
	 * @param pt the pt to set
	 */
	public void setPt(Integer pt) {
		this.pt = pt;
	}
	/**
	 * @return the lottery
	 */
	public Lottery getLottery() {
		return lottery;
	}
	/**
	 * @param lottery the lottery to set
	 */
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	/**
	 * @return the issueList
	 */
	public List<PassCountIssue> getIssueList() {
		return issueList;
	}
	/**
	 * @param issueList the issueList to set
	 */
	public void setIssueList(List<PassCountIssue> issueList) {
		this.issueList = issueList;
	}
	/**
	 * @return the state
	 */
	public SchemeState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(SchemeState state) {
		this.state = state;
	}
	/**
	 * @return the mode
	 */
	public SalesMode getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}
	/**
	 * @return the searchStr
	 */
	public String getSearchStr() {
		return searchStr;
	}
	/**
	 * @param searchStr the searchStr to set
	 */
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}


	/**
	 * @return the sfzcData
	 */
	public SfzcData getSfzcData() {
		return sfzcData;
	}


	/**
	 * @param sfzcData the sfzcData to set
	 */
	public void setSfzcData(SfzcData sfzcData) {
		this.sfzcData = sfzcData;
	}


	/**
	 * @return the mine
	 */
	public Boolean getMine() {
		return mine;
	}


	/**
	 * @param mine the mine to set
	 */
	public void setMine(Boolean mine) {
		this.mine = mine;
	}


	/**
	 * @return the sczcData
	 */
	public SczcData getSczcData() {
		return sczcData;
	}


	/**
	 * @param sczcData the sczcData to set
	 */
	public void setSczcData(SczcData sczcData) {
		this.sczcData = sczcData;
	}


	/**
	 * @return the lczcData
	 */
	public LczcData getLczcData() {
		return lczcData;
	}


	/**
	 * @param lczcData the lczcData to set
	 */
	public void setLczcData(LczcData lczcData) {
		this.lczcData = lczcData;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the numberData
	 */
	public NumberData getNumberData() {
		return numberData;
	}
	/**
	 * @param numberData the numberData to set
	 */
	public void setNumberData(NumberData numberData) {
		this.numberData = numberData;
	}
	/**
	 * @return the dltAdd
	 */
	public Integer getDltAdd() {
		return dltAdd;
	}
	/**
	 * @param dltAdd the dltAdd to set
	 */
	public void setDltAdd(Integer dltAdd) {
		this.dltAdd = dltAdd;
	}
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	public Boolean getWon() {
		return won;
	}
	public void setWon(Boolean won) {
		this.won = won;
	}
	
}
