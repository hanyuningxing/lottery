package com.cai310.lottery.task.dczc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.dczc.DczcSpInfo;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.fetch.dczc.DczcAbstractFetchParser;
import com.cai310.lottery.fetch.dczc.DczcBFOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcBQQSPFOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcContextHolder;
import com.cai310.lottery.fetch.dczc.DczcFetchResult;
import com.cai310.lottery.fetch.dczc.DczcSPFOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcSXDSOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcZJQSOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.win310sp.DczcBfSpWin310FetchParser;
import com.cai310.lottery.fetch.dczc.win310sp.DczcBqcSpWin310FetchParser;
import com.cai310.lottery.fetch.dczc.win310sp.DczcSpfSpWin310FetchParser;
import com.cai310.lottery.fetch.dczc.win310sp.DczcSxdsSpWin310FetchParser;
import com.cai310.lottery.fetch.dczc.win310sp.DczcZjqsSpWin310FetchParser;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.lottery.utils.ZunaoDczcSpVisitor;
import com.cai310.lottery.utils.ZunaoUtil;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DczcFetchTask {
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	protected List<DczcAbstractFetchParser> fetchList;

	@Autowired
	private PeriodEntityManager periodEntityManager;

	@Autowired
	private DczcMatchEntityManager matchEntityManager;

	@PostConstruct
	protected void init() {
		this.fetchList = Lists.newArrayList();
		fetchList.add(new DczcSPFOkoooFetchParser());
		fetchList.add(new DczcZJQSOkoooFetchParser());
		fetchList.add(new DczcSXDSOkoooFetchParser());
		fetchList.add(new DczcBFOkoooFetchParser());
		fetchList.add(new DczcBQQSPFOkoooFetchParser());
	}
	
	private static final String LINE_KEY_PREFIX = "lid";
	protected String getLineKey(Integer lid) {
		return String.format("%s%s", LINE_KEY_PREFIX, lid);
	}
	
	public void fetchSp() {
		TaskType taskType = TaskType.FETCH_SP;
		this.logger.info("[{}]线程开始执行...", taskType.getTypeName());

		Period period = null;
		List<Period> periodList = periodEntityManager.findCurrentPeriods(Lottery.DCZC);
		for (Period p : periodList) {
			if (p.isOnSale()) {
				period = p;
				break;
			}
		}
		if (period == null) {
			this.logger.warn("无当前期.");
			return;
		}
		if (this.fetchList == null || this.fetchList.isEmpty()) {
			this.logger.error("抓取任务为空.");
			return;
		}
		String periodNumber = period.getPeriodNumber();
		PlayType[] playTypeArr = PlayType.values();
		for (PlayType playType : playTypeArr) {
			try {
				Date fetchTime = new Date();
				List<ZunaoDczcSp> sp = null;
				if(playType.equals(PlayType.BF)){
					DczcBfSpWin310FetchParser dczcBfSpWin310FetchParser=new DczcBfSpWin310FetchParser();
					sp=dczcBfSpWin310FetchParser.fetch(periodNumber);
				}else if(playType.equals(PlayType.BQQSPF)){
					DczcBqcSpWin310FetchParser dczcBqcSpWin310FetchParser=new DczcBqcSpWin310FetchParser();
					sp=dczcBqcSpWin310FetchParser.fetch(periodNumber);
				}else if(playType.equals(PlayType.SPF)){
					DczcSpfSpWin310FetchParser dczcSpfSpWin310FetchParser=new DczcSpfSpWin310FetchParser();
					sp=dczcSpfSpWin310FetchParser.fetch(periodNumber);
				}else if(playType.equals(PlayType.SXDS)){
					DczcSxdsSpWin310FetchParser dczcSxdsSpWin310FetchParser=new DczcSxdsSpWin310FetchParser();
					sp=dczcSxdsSpWin310FetchParser.fetch(periodNumber);
				}else if(playType.equals(PlayType.ZJQS)){
					DczcZjqsSpWin310FetchParser dczcZjqsSpWin310FetchParser=new DczcZjqsSpWin310FetchParser();
					sp=dczcZjqsSpWin310FetchParser.fetch(periodNumber);
				}

				if (sp == null) {
					this.logger.warn("[{}]玩法抓取结果为空.", playType.getText());
					continue;
				}
				Map<String, Map<String, Double>> rateData = Maps.newLinkedHashMap();
				for (ZunaoDczcSp zunaoDczcSp : sp) {
						try {
							String lineKey;
							int lid = Integer.valueOf(zunaoDczcSp.getMatchId()) - 1;
							lineKey = getLineKey(lid);
							Map<String, Double> itemMap = getItemMap(playType,zunaoDczcSp.getSp());
							if (itemMap != null && !itemMap.isEmpty())
								rateData.put(lineKey, itemMap);
						} catch (Exception e) {
							this.logger.warn("[{}]玩法抓取结果为空.", playType.getText());
							continue;
						}
				}
				DczcContextHolder.updateRateData(periodNumber, playType, rateData,fetchTime);
			} catch (Exception e) {
				this.logger.error("[" + playType.getText() + "]玩法抓取发生异常.");//, e);
			}
		}
		this.logger.info("抓取完成.");
	}
	protected Map<String, Double> getItemMap(PlayType playType,String sp) {
		Map<String, Double> itemMap = Maps.newHashMap();
		if(playType.equals(PlayType.SPF)){
			
		}
		String[] spArr = sp.split(",");
		for (Item item : playType.getAllItems()) {
			String rateStr = spArr[item.ordinal()];
			Double rate = StringUtils.isNotBlank(rateStr) ? Double.valueOf(rateStr) : 0;
			itemMap.put(item.toString(), rate);
		}
		return itemMap;
	}
	public void persistenceSp() {
		TaskType taskType = TaskType.SAVE_SP;
		this.logger.info("[{}]线程开始执行...", taskType.getTypeName());

		Period period = null;
		List<Period> periodList = periodEntityManager.findCurrentPeriods(Lottery.DCZC);
		for (Period p : periodList) {
			if (p.isOnSale()) {
				period = p;
				break;
			}
		}
		if (period == null) {
			this.logger.warn("无当前期.");
			return;
		}

		for (PlayType playType : PlayType.values()) {
			try {
				FetchDataBean fdb = DczcContextHolder.getRateData(period.getPeriodNumber(), playType);
				if (fdb != null && fdb.getDataBlock() != null && fdb.getDataBlock().getData() != null) {
					SortedMap<String, Map<String, ValueItem>> data = fdb.getDataBlock().getData();
					for (Entry<String, Map<String, ValueItem>> entry : data.entrySet()) {
						String lineKey = entry.getKey();
						Integer lineId = DczcAbstractFetchParser.getLineId(lineKey);
						Map<String, ValueItem> itemMap = entry.getValue();
						Map<String, Double> spMap = Maps.newHashMap();
						for (Entry<String, ValueItem> itemEntry : itemMap.entrySet()) {
							spMap.put(itemEntry.getKey(), itemEntry.getValue().getValue());
						}

						DczcSpInfo dczcSpInfo = new DczcSpInfo();
						dczcSpInfo.setPeriodId(period.getId());
						dczcSpInfo.setPeriodNumber(period.getPeriodNumber());
						dczcSpInfo.setPlayType(playType);
						dczcSpInfo.setLineId(lineId);
						dczcSpInfo.setContent(spMap);

						matchEntityManager.updateSpInfo(dczcSpInfo);
					}
				}
			} catch (Exception e) {
				this.logger.error("[" + playType.getText() + "]玩法持久化SP发生异常.");//, e);
			}
		}
	}
}
