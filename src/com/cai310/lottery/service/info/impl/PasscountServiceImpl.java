package com.cai310.lottery.service.info.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.dao.lottery.dczc.DczcSchemeWonInfoDao;
import com.cai310.lottery.dao.lottery.dlt.DltPasscountDao;
import com.cai310.lottery.dao.lottery.pl.PlPasscountDao;
import com.cai310.lottery.dao.lottery.ssq.SsqPasscountDao;
import com.cai310.lottery.dao.lottery.welfare3d.Welfare3dPasscountDao;
import com.cai310.lottery.dao.lottery.zc.LczcSchemeWonInfoDao;
import com.cai310.lottery.dao.lottery.zc.SczcSchemeWonInfoDao;
import com.cai310.lottery.dao.lottery.zc.SfzcSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.dczc.BdzcSchemeWonInfo;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dlt.DltPasscount;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.entity.lottery.pl.PlPasscount;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqPasscount;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPasscount;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.LczcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SczcMatch;
import com.cai310.lottery.entity.lottery.zc.SczcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SfzcMatch;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.entity.lottery.zc.ZcPasscount;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.PasscountService;
import com.cai310.lottery.service.lottery.dczc.impl.DczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.dlt.impl.DltPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcPeriodDataEntityManagerImpl;
import com.cai310.lottery.support.pl.LotteryPlayType;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.utils.DczcDataToXMLUtil;
import com.cai310.lottery.utils.DczcIssueListToXMLUtil;
import com.cai310.lottery.utils.DczcPasscountToXMLUtil;
import com.cai310.lottery.utils.DltPasscountToXMLUtil;
import com.cai310.lottery.utils.LczcDataToXMLUtil;
import com.cai310.lottery.utils.PasscountUtil;
import com.cai310.lottery.utils.PlPasscountToXMLUtil;
import com.cai310.lottery.utils.SczcDataToXMLUtil;
import com.cai310.lottery.utils.SfzcDataToXMLUtil;
import com.cai310.lottery.utils.SsqPasscountToXMLUtil;
import com.cai310.lottery.utils.Welfare3dPasscountToXMLUtil;
import com.cai310.lottery.utils.ZcIssueListToXMLUtil;
import com.cai310.lottery.utils.ZcPasscountToXMLUtil;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

@Service("passcountServiceImpl")
@Transactional
public class PasscountServiceImpl implements PasscountService {

	@Autowired
	// 六场
	protected LczcSchemeWonInfoDao lczcSchemeWonInfoDao;
	@Autowired
	// 六场期信息
	protected LczcPeriodDataEntityManagerImpl lczcPeriodDataEntityManager;
	@Autowired
	// 六场对阵
	protected LczcMatchEntityManagerImpl lczcMatchEntityManager;

	@Autowired
	// 四场
	protected SczcSchemeWonInfoDao sczcSchemeWonInfoDao;
	@Autowired
	// 四场期信息
	protected SczcPeriodDataEntityManagerImpl sczcPeriodDataEntityManager;
	@Autowired
	// 四场对阵
	protected SczcMatchEntityManagerImpl sczcMatchEntityManager;

	@Autowired
	// 14/9过关
	protected SfzcSchemeWonInfoDao sfzcSchemeWonInfoDao;
	@Autowired
	// 14/9期信息
	protected SfzcPeriodDataEntityManagerImpl sfzcPeriodDataEntityManager;
	@Autowired
	// 14/9对阵
	protected SfzcMatchEntityManagerImpl sfzcMatchEntityManager;

	@Autowired
	// 北单的 （让球胜平负/总进球数/上下单双/比分/半全场）
	protected DczcSchemeWonInfoDao dczcSchemeWonInfoDao;
	@Autowired
	// 北单的 对阵 （让球胜平负/总进球数/上下单双/比分/半全场）
	protected DczcMatchEntityManagerImpl dczcMatchEntityManager;

	@Autowired
	// 大乐透
	protected DltPasscountDao dltPasscountDao;
	@Autowired
	protected DltPeriodDataEntityManagerImpl dltPeriodDataEntityManager;

	@Autowired
	// 双色球
	protected SsqPasscountDao ssqPasscountDao;
	@Autowired
	protected SsqPeriodDataEntityManagerImpl ssqPeriodDataEntityManager;

	@Autowired
	// 福彩3D
	protected Welfare3dPasscountDao welfare3dPasscountDao;
	@Autowired
	protected Welfare3dPeriodDataEntityManagerImpl welfare3dPeriodDataEntityManager;

	@Autowired
	// 排列
	protected PlPasscountDao plPasscountDao;
	@Autowired
	protected PlPeriodDataEntityManagerImpl plPeriodDataEntityManager;

	@Resource(name = "queryService")
	protected QueryService queryService;

	public void createPasscountXml(Period period) throws Exception {// 足彩的
		if (null == period)
			throw new ServiceException("错误-过关统计期为空");
		if (null != period.getLotteryType()) {
			if (null != period.getLotteryType().getCategory()) {
				if (LotteryCategory.NUMBER.equals(period.getLotteryType().getCategory())) {
					// 数字彩
					createNumberPasscountXml(period);// 过关统计的方案内容xml
					createIssueListXml(period);// 列表xml
					creatPeriodDataXml(period);// 期数据xml
				} else if (LotteryCategory.ZC.equals(period.getLotteryType().getCategory())) {
					// 足彩
					createZcPasscountXml(period);// 过关统计的方案内容xml
					createIssueListXml(period);// 列表xml
					creatPeriodDataXml(period);// 期数据xml
				} else if (LotteryCategory.DCZC.equals(period.getLotteryType().getCategory())) {
					// 北单
					createDczcPasscountXml(period);// 过关统计的方案内容xml
					createDczcIssueListXml(period);// 列表xml
					creatPeriodDataXml(period);// 期数据xml
				} else {
					throw new ServiceException("错误-过关统计期彩票类型出错");
				}
			} else {
				throw new ServiceException("错误-过关统计期彩票类型出错");
			}
		} else {
			throw new ServiceException("错误-过关统计期彩票类型出错");
		}
	}

	public void createZcPasscountXml(Period period) throws Exception {
		List<ZcPasscount> list;
		Integer playType = null;
		String dir = null;
		String fileName = null;
		Lottery lottery = period.getLotteryType();
		if (Lottery.SFZC.equals(lottery)) {
			// 如果是14、9场的话。要生成两个xml
			list = getZcPasscountByPeriod(period, PlayType.SFZC14);
			playType = PlayType.SFZC14.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + PlayType.SFZC14.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createZcPasscountXml(list, playType, dir, fileName);

			list = getZcPasscountByPeriod(period, PlayType.SFZC9);
			playType = PlayType.SFZC9.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + PlayType.SFZC9.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createZcPasscountXml(list, playType, dir, fileName);

		} else {
			list = getZcPasscountByPeriod(period, null);
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = period.getPeriodNumber() + ".xml";
			createZcPasscountXml(list, null, dir, fileName);
		}
	}

	public void createNumberPasscountXml(Period period) throws Exception {

		Integer playType = null;
		String dir = null;
		String fileName = null;
		Lottery lottery = period.getLotteryType();
		if (Lottery.PL.equals(lottery)) {
			// 如果是排列的话。要生成两个xml
			List<PlPasscount> list;
			list = getPlSchemeWonInfoByPeriod(period, LotteryPlayType.PL3);
			playType = LotteryPlayType.PL3.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + LotteryPlayType.PL3.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createPlPasscountXml(list, playType, dir, fileName);

			list = getPlSchemeWonInfoByPeriod(period, LotteryPlayType.PL5);
			playType = LotteryPlayType.PL5.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + LotteryPlayType.PL5.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createPlPasscountXml(list, playType, dir, fileName);

		} else if (Lottery.DLT.equals(lottery)) {
			List<DltPasscount> list = getDltSchemeWonInfo(period);
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = period.getPeriodNumber() + ".xml";
			DltPasscountToXMLUtil.createDltPasscountXml(list, dir, fileName);

		} else if (Lottery.SSQ.equals(lottery)) {
			List<SsqPasscount> list = getSsqSchemeWonInfo(period);
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = period.getPeriodNumber() + ".xml";
			SsqPasscountToXMLUtil.createSsqPasscountXml(list, dir, fileName);

		} else if (Lottery.WELFARE3D.equals(lottery)) {
			List<Welfare3dPasscount> list = getWelfare3dSchemeWonInfo(period);
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = period.getPeriodNumber() + ".xml";
			Welfare3dPasscountToXMLUtil.createWelfare3dPasscountXml(list, dir, fileName);
		}
	}

	public void createDczcPasscountXml(Period period) throws Exception {// 创建北单过关统计的方案内容xml方法
		List<BdzcSchemeWonInfo> list;
		Integer playType = null;
		String dir = null;
		String fileName = null;
		Lottery lottery = period.getLotteryType();
		if (Lottery.DCZC.equals(lottery)) {// 北单的 包括5个 要生成5个xml哦
			list = getDczcSchemeWonInfoByPeriod(period, com.cai310.lottery.support.dczc.PlayType.SPF);
			playType = com.cai310.lottery.support.dczc.PlayType.SPF.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.SPF.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createDczcPasscountXml(list, playType, dir, fileName);

			list = getDczcSchemeWonInfoByPeriod(period, com.cai310.lottery.support.dczc.PlayType.ZJQS);
			playType = com.cai310.lottery.support.dczc.PlayType.ZJQS.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.ZJQS.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createDczcPasscountXml(list, playType, dir, fileName);

			list = getDczcSchemeWonInfoByPeriod(period, com.cai310.lottery.support.dczc.PlayType.SXDS);
			playType = com.cai310.lottery.support.dczc.PlayType.SXDS.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.SXDS.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createDczcPasscountXml(list, playType, dir, fileName);

			list = getDczcSchemeWonInfoByPeriod(period, com.cai310.lottery.support.dczc.PlayType.BF);
			playType = com.cai310.lottery.support.dczc.PlayType.BF.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.BF.name() + "/";
			fileName = period.getPeriodNumber() + ".xml";
			createDczcPasscountXml(list, playType, dir, fileName);

			list = getDczcSchemeWonInfoByPeriod(period, com.cai310.lottery.support.dczc.PlayType.BQQSPF);
			playType = com.cai310.lottery.support.dczc.PlayType.BQQSPF.ordinal();
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.BQQSPF.name()
					+ "/";
			fileName = period.getPeriodNumber() + ".xml";
			createDczcPasscountXml(list, playType, dir, fileName);
		}
	}

	public void createIssueListXml(Period period) throws Exception {// 列表xml
		Lottery lottery = period.getLotteryType();
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class, "m");
		criteria.add(Restrictions.eq("m.lotteryType", lottery));
		criteria.add(Restrictions.eq("m.passcount", Boolean.TRUE));
		criteria.addOrder(Order.desc("m.periodNumber"));
		List<Period> list = queryService.findByDetachedCriteria(criteria, 0, 20);
		if (null == list || list.isEmpty()) {
			throw new ServiceException("错误-过关统计列表为空");
		}
		Collections.sort(list, new Comparator<Period>() {
			public int compare(Period o1, Period o2) {
				return o2.getPeriodNumber().compareTo(o1.getPeriodNumber());
			}
		});
		String dir = null;
		String fileName = null;
		if (Lottery.SFZC.equals(lottery)) {
			// 如果是14、9场的话。要生成两个xml
			fileName = "issue.xml";
			dir = PasscountUtil.getPeriodDir(lottery) + PlayType.SFZC14.name() + "/";
			ZcIssueListToXMLUtil.createZcIssueXml(list, dir, fileName);
			dir = PasscountUtil.getPeriodDir(lottery) + PlayType.SFZC9.name() + "/";
			ZcIssueListToXMLUtil.createZcIssueXml(list, dir, fileName);

		} else if (Lottery.PL.equals(lottery)) {
			// 排列也要生成2个xml
			fileName = "issue.xml";
			dir = PasscountUtil.getPeriodDir(lottery) + LotteryPlayType.PL3.name() + "/";
			ZcIssueListToXMLUtil.createZcIssueXml(list, dir, fileName);
			dir = PasscountUtil.getPeriodDir(lottery) + LotteryPlayType.PL5.name() + "/";
			ZcIssueListToXMLUtil.createZcIssueXml(list, dir, fileName);

		} else {
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = "issue.xml";
			ZcIssueListToXMLUtil.createZcIssueXml(list, dir, fileName);
		}
	}

	public void createDczcIssueListXml(Period period) throws Exception {// 生成列表xml方法
		Lottery lottery = period.getLotteryType();
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class, "m");
		criteria.add(Restrictions.eq("m.lotteryType", lottery));
		criteria.add(Restrictions.eq("m.passcount", Boolean.TRUE));
		criteria.addOrder(Order.desc("m.periodNumber"));
		List<Period> list = queryService.findByDetachedCriteria(criteria, 0, 20);
		if (null == list || list.isEmpty()) {
			throw new ServiceException("错误-过关统计列表为空");
		}
		Collections.sort(list, new Comparator<Period>() {
			public int compare(Period o1, Period o2) {
				return o2.getPeriodNumber().compareTo(o1.getPeriodNumber());
			}
		});
		String dir = null;
		String fileName = null;
		if (Lottery.DCZC.equals(lottery)) {
			// 北单。要生成5个xml
			fileName = "issue.xml";
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.SPF.name() + "/";
			DczcIssueListToXMLUtil.createDczcIssueXml(list, dir, fileName);
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.ZJQS.name() + "/";
			DczcIssueListToXMLUtil.createDczcIssueXml(list, dir, fileName);
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.SXDS.name() + "/";
			DczcIssueListToXMLUtil.createDczcIssueXml(list, dir, fileName);
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.BF.name() + "/";
			DczcIssueListToXMLUtil.createDczcIssueXml(list, dir, fileName);
			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.BQQSPF.name()
					+ "/";
			DczcIssueListToXMLUtil.createDczcIssueXml(list, dir, fileName);
		}
	}

	public void creatPeriodDataXml(Period period) throws Exception {// 生成期数据xml方法
		String dir = null;
		String fileName = null;
		Lottery lottery = period.getLotteryType();
		if (Lottery.LCZC.equals(lottery)) {
			// /6
			LczcPeriodData lczcPeriodData = lczcPeriodDataEntityManager.getPeriodData(period.getId());
			if (null == lczcPeriodData)
				throw new ServiceException("错误-过关统计开奖数据为空-请更新开奖数据");
			LczcMatch[] lczcMatchs = lczcMatchEntityManager.findMatchs(period.getId());
			if (null == lczcMatchs)
				throw new ServiceException("错误-过关统计对阵为空-请更新对阵");
			dir = PasscountUtil.getPeriodDir(lottery) + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			LczcDataToXMLUtil.createLczcPeriodDataXml(period, lczcPeriodData, lczcMatchs, dir, fileName);
		} else if (Lottery.SCZC.equals(lottery)) {
			// /4
			SczcPeriodData sczcPeriodData = sczcPeriodDataEntityManager.getPeriodData(period.getId());
			if (null == sczcPeriodData)
				throw new ServiceException("错误-过关统计开奖数据为空-请更新开奖数据");
			SczcMatch[] sczcMatchs = sczcMatchEntityManager.findMatchs(period.getId());
			if (null == sczcMatchs)
				throw new ServiceException("错误-过关统计对阵为空-请更新对阵");
			dir = PasscountUtil.getPeriodDir(lottery) + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			SczcDataToXMLUtil.createSczcPeriodDataXml(period, sczcPeriodData, sczcMatchs, dir, fileName);
		} else if (Lottery.SFZC.equals(lottery)) {
			// 14+9
			SfzcPeriodData sfzcPeriodData = sfzcPeriodDataEntityManager.getPeriodData(period.getId());
			if (null == sfzcPeriodData)
				throw new ServiceException("错误-过关统计开奖数据为空-请更新开奖数据");
			SfzcMatch[] sfzcMatchs = sfzcMatchEntityManager.findMatchs(period.getId());
			if (null == sfzcMatchs)
				throw new ServiceException("错误-过关统计对阵为空-请更新对阵");
			dir = PasscountUtil.getPeriodDir(lottery) + PlayType.SFZC14.name() + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			SfzcDataToXMLUtil.createSfzcPeriodDataXml(period, sfzcPeriodData, sfzcMatchs, dir, fileName);

			dir = PasscountUtil.getPeriodDir(lottery) + PlayType.SFZC9.name() + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			SfzcDataToXMLUtil.createSfzcPeriodDataXml(period, sfzcPeriodData, sfzcMatchs, dir, fileName);
		} else if (Lottery.DCZC.equals(lottery)) {
			// 北单的 有5个
			List<DczcMatch> dczcMatchs = dczcMatchEntityManager.findMatchs(period.getId());
			if (null == dczcMatchs)
				throw new ServiceException("错误-过关统计对阵为空-请更新对阵");

			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.SPF.name() + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			DczcDataToXMLUtil.createDczcPeriodDataXml(period, dczcMatchs, dir, fileName);

			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.ZJQS.name() + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			DczcDataToXMLUtil.createDczcPeriodDataXml(period, dczcMatchs, dir, fileName);

			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.SXDS.name() + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			DczcDataToXMLUtil.createDczcPeriodDataXml(period, dczcMatchs, dir, fileName);

			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.BF.name() + "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			DczcDataToXMLUtil.createDczcPeriodDataXml(period, dczcMatchs, dir, fileName);

			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.dczc.PlayType.BQQSPF.name()
					+ "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			DczcDataToXMLUtil.createDczcPeriodDataXml(period, dczcMatchs, dir, fileName);

		} else if (Lottery.DLT.equals(lottery)) {
			// 大乐透
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = period.getPeriodNumber() + "_data.xml";
			DltPeriodData dltPeriodData = dltPeriodDataEntityManager.getPeriodData(period.getId());
			DltPasscountToXMLUtil.createDltPeriodDataXml(period, dltPeriodData, dir, fileName);

		} else if (Lottery.SSQ.equals(lottery)) {
			// 双色球
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = period.getPeriodNumber() + "_data.xml";
			SsqPeriodData ssqPeriodData = ssqPeriodDataEntityManager.getPeriodData(period.getId());
			SsqPasscountToXMLUtil.createSsqPeriodDataXml(period, ssqPeriodData, dir, fileName);

		} else if (Lottery.WELFARE3D.equals(lottery)) {
			// 福彩3D
			dir = PasscountUtil.getPeriodDir(lottery);
			fileName = period.getPeriodNumber() + "_data.xml";
			Welfare3dPeriodData welfare3dPeriodData = welfare3dPeriodDataEntityManager.getPeriodData(period.getId());
			Welfare3dPasscountToXMLUtil.createWelfare3dPeriodDataXml(period, welfare3dPeriodData, dir, fileName);

		} else if (Lottery.PL.equals(lottery)) {

			// 排列3+5
			PlPeriodData plPeriodData = plPeriodDataEntityManager.getPeriodData(period.getId());

			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.pl.LotteryPlayType.PL3.name()
					+ "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			PlPasscountToXMLUtil.createPlPeriodDataXml(period, plPeriodData, dir, fileName);

			dir = PasscountUtil.getPeriodDir(lottery) + com.cai310.lottery.support.pl.LotteryPlayType.PL5.name()
					+ "/";
			fileName = period.getPeriodNumber() + "_data.xml";
			PlPasscountToXMLUtil.createPlPeriodDataXml(period, plPeriodData, dir, fileName);
		}
	}

	/**
	 * 生成足彩Xml
	 * 
	 * @param list 过关列表
	 * @param result 开奖号码
	 * @param playType 玩法
	 * @param dir 目录
	 * @param fileName 文件名
	 * @throws IOException
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void createZcPasscountXml(List<ZcPasscount> list, Integer playType, String dir, String fileName)
			throws IOException {
		ZcPasscountToXMLUtil.createZcPasscountXml(list, dir, fileName);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void createDczcPasscountXml(List<BdzcSchemeWonInfo> list, Integer playType, String dir, String fileName)
			throws IOException {
		DczcPasscountToXMLUtil.createDczcPasscountXml(list, dir, fileName);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void createPlPasscountXml(List<PlPasscount> list, Integer playType, String dir, String fileName)
			throws IOException {
		PlPasscountToXMLUtil.createPlPasscountXml(list, dir, fileName);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PlPasscount> getPlSchemeWonInfoByPeriod(Period period, LotteryPlayType playType) {
		Lottery lottery = period.getLotteryType();
		if (Lottery.PL.equals(lottery)) {
			// 排列
			return getPlSchemeWonInfo(playType, period);
		}
		return null;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ZcPasscount> getZcPasscountByPeriod(Period period, PlayType playType) {
		Lottery lottery = period.getLotteryType();
		if (Lottery.LCZC.equals(lottery)) {
			// /6
			return getLczcSchemeWonInfo(period);
		} else if (Lottery.SCZC.equals(lottery)) {
			// /4
			return getSczcSchemeWonInfo(period);
		} else if (Lottery.SFZC.equals(lottery)) {
			// 14 9
			return getSfzcSchemeWonInfo(playType, period);
		}
		return null;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<BdzcSchemeWonInfo> getDczcSchemeWonInfoByPeriod(Period period,
			com.cai310.lottery.support.dczc.PlayType playType) {
		Lottery lottery = period.getLotteryType();
		if (Lottery.DCZC.equals(lottery)) {
			// 北单
			return getDczcSchemeWonInfo(playType, period);
		}
		return null;
	}

	// 4
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ZcPasscount> getSczcSchemeWonInfo(final Period period) {
		return (List<ZcPasscount>) sczcSchemeWonInfoDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("lost0"));
				criteria.addOrder(Order.desc("lost1"));
				criteria.addOrder(Order.desc("lost2"));
				criteria.addOrder(Order.desc("lost3"));
				criteria.addOrder(Order.desc("passcount"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

	// 6
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ZcPasscount> getLczcSchemeWonInfo(final Period period) {
		return (List<ZcPasscount>) lczcSchemeWonInfoDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("lost0"));
				criteria.addOrder(Order.desc("lost1"));
				criteria.addOrder(Order.desc("lost2"));
				criteria.addOrder(Order.desc("lost3"));
				criteria.addOrder(Order.desc("lost3"));
				criteria.addOrder(Order.desc("passcount"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

	// 14 9
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ZcPasscount> getSfzcSchemeWonInfo(final PlayType playType, final Period period) {
		return (List<ZcPasscount>) sfzcSchemeWonInfoDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("playType", playType));
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("lost0"));
				criteria.addOrder(Order.desc("lost1"));
				criteria.addOrder(Order.desc("lost2"));
				criteria.addOrder(Order.desc("lost3"));
				criteria.addOrder(Order.desc("passcount"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

	// 北单
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<BdzcSchemeWonInfo> getDczcSchemeWonInfo(final com.cai310.lottery.support.dczc.PlayType playType,
			final Period period) {
		return (List<BdzcSchemeWonInfo>) dczcSchemeWonInfoDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("playType", playType));
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("finsh"));
				criteria.addOrder(Order.desc("schemePrize"));
				criteria.addOrder(Order.desc("wonCount"));
				criteria.addOrder(Order.desc("passcount"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

	// 大乐透
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DltPasscount> getDltSchemeWonInfo(final Period period) {
		return (List<DltPasscount>) dltPasscountDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("winUnit.firstWinUnits"));
				criteria.addOrder(Order.desc("winUnit.secondWinUnits"));
				criteria.addOrder(Order.desc("winUnit.thirdWinUnits"));
				criteria.addOrder(Order.desc("winUnit.fourthWinUnits"));
				criteria.addOrder(Order.desc("winUnit.fifthWinUnits"));
				criteria.addOrder(Order.desc("winUnit.sixthWinUnits"));
				criteria.addOrder(Order.desc("winUnit.seventhWinUnits"));
				criteria.addOrder(Order.desc("winUnit.eighthWinUnits"));
				criteria.addOrder(Order.desc("winUnit.select12to2WinUnits"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

	// 双色球
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SsqPasscount> getSsqSchemeWonInfo(final Period period) {
		return (List<SsqPasscount>) ssqPasscountDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("winUnit.firstWinUnits"));
				criteria.addOrder(Order.desc("winUnit.secondWinUnits"));
				criteria.addOrder(Order.desc("winUnit.thirdWinUnits"));
				criteria.addOrder(Order.desc("winUnit.fourthWinUnits"));
				criteria.addOrder(Order.desc("winUnit.fifthWinUnits"));
				criteria.addOrder(Order.desc("winUnit.sixthWinUnits"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

	// 福彩3d
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Welfare3dPasscount> getWelfare3dSchemeWonInfo(final Period period) {
		return (List<Welfare3dPasscount>) welfare3dPasscountDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("winUnit.winUnits"));
				criteria.addOrder(Order.desc("winUnit.g3WinUnits"));
				criteria.addOrder(Order.desc("winUnit.g6WinUnits"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

	// 排列
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PlPasscount> getPlSchemeWonInfo(final LotteryPlayType playType, final Period period) {
		return (List<PlPasscount>) plPasscountDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				if (LotteryPlayType.PL5.equals(playType)) {
					criteria.add(Restrictions.eq("playType", PlPlayType.P5Direct));
				} else if (LotteryPlayType.PL3.equals(playType)) {
					criteria.add(Restrictions.not(Restrictions.eq("playType", PlPlayType.P5Direct)));
				} else {
					// 报错
					throw new ServiceException("错误-过关统计排列类型为空");
				}
				criteria.add(Restrictions.eq("periodId", period.getId()));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.addOrder(Order.asc("state"));
				criteria.addOrder(Order.desc("winUnit.p5WinUnits"));
				criteria.addOrder(Order.desc("winUnit.p3WinUnits"));
				criteria.addOrder(Order.desc("winUnit.p3G3WinUnits"));
				criteria.addOrder(Order.desc("winUnit.p3G6WinUnits"));
				criteria.addOrder(Order.asc("units"));
				return criteria.list();
			}
		});
	}

}
