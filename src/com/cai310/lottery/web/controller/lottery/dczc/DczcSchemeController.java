package com.cai310.lottery.web.controller.lottery.dczc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.dczc.DczcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.dczc.DczcSpInfo;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.fetch.dczc.DczcAbstractFetchParser;
import com.cai310.lottery.fetch.dczc.DczcContextHolder;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeService;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.DczcCompoundContent;
import com.cai310.lottery.support.dczc.DczcExtraItem;
import com.cai310.lottery.support.dczc.DczcExtraMatchItem;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.DczcUtils;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.ItemBQQSPF;
import com.cai310.lottery.support.dczc.ItemSPF;
import com.cai310.lottery.support.dczc.ItemSXDS;
import com.cai310.lottery.support.dczc.ItemZJQS;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.support.dczc.PrizeForecast;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.SchemeBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Namespace("/" + DczcConstant.KEY)
@Action(value = "scheme")
@Results({
		@Result(name = "wonListTable", location = "/WEB-INF/content/scheme/wonList-table.ftl"),
		})
public class DczcSchemeController extends
		SchemeBaseController<DczcScheme, DczcSchemeDTO, DczcSchemeCreateForm, DczcSchemeUploadForm, SchemeTemp> {
	private static final long serialVersionUID = 5783479221989581469L;
	
	
	private PlayType playType;

	@Autowired
	private DczcSchemeService schemeService;

	@Autowired
	private DczcSchemeEntityManager schemeEntityManager;

	@Autowired
	private DczcMatchEntityManager matchEntityManager;

	@Autowired
	@Qualifier("dczcMatchCache")
	private Cache matchCache;

	// private PrizeForecastForm forecastForm;
	private List<String> spss = new ArrayList<String>();

	@Override
	protected SchemeService<DczcScheme, DczcSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected DczcSchemeEntityManager getSchemeEntityManager() {
		return schemeEntityManager;
	}

	// -------------------------------------------------------
	@Override
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria c = super.buildListDetachedCriteria();
		if (playType == null)
			playType = PlayType.SPF;

		c.add(Restrictions.eq("m.playType", playType));

		return c;
	}

	@Override
	protected XDetachedCriteria buildFilterListDetachedCriteria() {
		XDetachedCriteria c = super.buildFilterListDetachedCriteria();
		if (playType == null)
			playType = PlayType.SPF;

		c.add(Restrictions.eq("m.playType", playType));

		return c;
	}
	
	public String wonListTable() {				
		XDetachedCriteria criteria = super.buildWonListQueryCriteria();

		if(this.getPlayType()!=null)
		criteria.add(Restrictions.eq("m.playType", this.getPlayType()));
			
		this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);
		
		return "wonListTable";
	}

	@Override
	protected String doEditNew() throws Exception {
		if (playType == null)
			playType = PlayType.SPF;

		Item[] itemArr = playType.getAllItems();
		Map<String, PlayType> playMap = Maps.newLinkedHashMap();
		
		if (playType == PlayType.BF) {
			Map<String, Item[]> itemMap = new LinkedHashMap<String, Item[]>();
			itemMap.put("胜", ItemBF.WINS);
			itemMap.put("平", ItemBF.DRAWS);
			itemMap.put("负", ItemBF.LOSES);
			Struts2Utils.setAttribute("itemMap", itemMap);
			Struts2Utils.setAttribute("itemColspan", ItemBF.WINS.length);
		} else {
			Map<String, Item[]> itemMap = Maps.newLinkedHashMap();
			itemMap.put(playType.name(), playType.getAllItems());
			Struts2Utils.setAttribute("itemMap", itemMap);
		}
		playMap.put(this.playType.name(), this.playType);
		
		Struts2Utils.setAttribute("playMap",playMap);
		Struts2Utils.setAttribute("itemArr", itemArr);

		Integer lastAheadMinuteEnd = null;// 最迟的提前截止时间
		switch (salesMode) {
		case COMPOUND:
			for (int m : DczcConstant.AHEAD_END_ARR) {
				if (lastAheadMinuteEnd == null || lastAheadMinuteEnd > m)
					lastAheadMinuteEnd = m;
			}
			break;
		case SINGLE:
			lastAheadMinuteEnd = DczcConstant.SINGLE_AHEAD_END;
			Struts2Utils.setAttribute("hasHandicap", playType == PlayType.SPF);
			break;
		default:
			throw new WebDataException("投注方式不合法.");
		}
		Struts2Utils.setAttribute("aheadMinuteEnd", lastAheadMinuteEnd);

		List<DczcMatch> matchs = findMatchsOfCacheable(this.period.getId());
		if (matchs == null || matchs.isEmpty())
			throw new WebDataException("对阵不存在.");

		List<String> games = new ArrayList<String>();
		int handicapCount = 0;
		int unHandicapCount = 0;
		Map<String, List<DczcMatch>> map = new TreeMap<String, List<DczcMatch>>();
		for (DczcMatch m : matchs) {
			if (StringUtils.isNotBlank(m.getGameName())) {
				if (!games.contains(m.getGameName()))
					games.add(m.getGameName());
			}

			if (m.getHandicap() != null && m.getHandicap() != 0)
				handicapCount++;
			else
				unHandicapCount++;

			String key = getKey(m.getMatchTime());
			List<DczcMatch> list = map.get(key);
			if (list == null) {
				list = new ArrayList<DczcMatch>();
				map.put(key, list);
			}
			list.add(m);
		}

		Map<Integer, DczcSpInfo> infoMap = new HashMap<Integer, DczcSpInfo>();
		Integer spVersion;
		{
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

					infoMap.put(dczcSpInfo.getLineId(), dczcSpInfo);
				}
				spVersion = fdb.getDataBlock().getVersion();
			} else {
				List<DczcSpInfo> dczcSpInfoList = matchEntityManager.getDczcSpInfo(playType, period.getPeriodNumber());
				for (DczcSpInfo sp : dczcSpInfoList) {
					infoMap.put(sp.getLineId(), sp);
				}
				spVersion = 0;
			}
		}

		Struts2Utils.setAttribute("spVersion", spVersion);
		Struts2Utils.setAttribute("infoMap", infoMap);
		Struts2Utils.setAttribute("games", games);
		Struts2Utils.setAttribute("handicapCount", handicapCount);
		Struts2Utils.setAttribute("unHandicapCount", unHandicapCount);
		Struts2Utils.setAttribute("matchMap", map);
		return super.doEditNew();
	}
	
	public String review() throws Exception {
		this.preparePeriodsOfList(10);
		if (playType == null)
			playType = PlayType.SPF;

		Struts2Utils.setAttribute("itemArr", playType.getAllItems());
		if (playType == PlayType.BF) {
			Map<String, Item[]> itemMap = Maps.newLinkedHashMap();
			itemMap.put("胜", ItemBF.WINS);
			itemMap.put("平", ItemBF.DRAWS);
			itemMap.put("负", ItemBF.LOSES);
			Struts2Utils.setAttribute("itemMap", itemMap);
			Struts2Utils.setAttribute("itemColspan", ItemBF.WINS.length);
		}
		String periodIdStr = Struts2Utils.getParameter("periodId");
		long periodId = 0;
		if(StringUtils.isBlank(periodIdStr)) {
			periodId = this.periods.get(1).getId();
		} else {
			periodId = Long.valueOf(periodIdStr);
		}
		Struts2Utils.setAttribute("periodId", periodId);
		List<DczcMatch> matchs = findMatchsOfCacheable(periodId);
		if (matchs == null || matchs.isEmpty())
			throw new WebDataException("对阵不存在.");

		List<String> games = new ArrayList<String>();
		int handicapCount = 0;
		int unHandicapCount = 0;
		Map<String, List<DczcMatch>> map = new TreeMap<String, List<DczcMatch>>();
		for (DczcMatch m : matchs) {
			if (!m.isEnded())
				continue;
			if (StringUtils.isNotBlank(m.getGameName())) {
				if (!games.contains(m.getGameName()))
					games.add(m.getGameName());
			}

			if (m.getHandicap() != null && m.getHandicap() != 0)
				handicapCount++;
			else
				unHandicapCount++;

			String key = getKey(m.getMatchTime());
			List<DczcMatch> list = map.get(key);
			if (list == null) {
				list = new ArrayList<DczcMatch>();
				map.put(key, list);
			}
			list.add(m);			
		}
		Struts2Utils.setAttribute("games", games);
		Struts2Utils.setAttribute("handicapCount", handicapCount);
		Struts2Utils.setAttribute("unHandicapCount", unHandicapCount);
		Struts2Utils.setAttribute("matchMap", map);
		return "review";
	}

	@SuppressWarnings("unchecked")
	protected List<DczcMatch> findMatchsOfCacheable(Long periodId) {
		Element el = matchCache.get(periodId);
		if (el == null) {
			List<DczcMatch> matchs = matchEntityManager.findMatchs(periodId);
			if (matchs != null)
				matchCache.put(new Element(periodId, matchs));

			return matchs;
		} else {
			return (List<DczcMatch>) el.getValue();
		}
	}

	protected String getKey(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.HOUR_OF_DAY, -10);
		String dateStr = DateUtil.dateToStr(c.getTime(), "yyyy-MM-dd");
		String weedStr = com.cai310.utils.DateUtil.getWeekStr(c);
		StringBuilder sb = new StringBuilder();
		sb.append(dateStr).append(" ").append(weedStr);
		return sb.toString();
	}

	@Override
	protected DczcSchemeDTO buildSchemeDTO() throws WebDataException {
		checkCreateForm();

		switch (createForm.getMode()) {
		case COMPOUND:
			checkCreateFormOfCOMPOUND();
			break;
		case SINGLE:
			supportOkoooAnd500wan();
			checkCreateFormOfSINGLE();
			break;
		default:
			throw new WebDataException("投注方式不合法.");
		}

		DczcSchemeDTO dto = super.buildSchemeDTO();
		dto.setPlayType(playType);
		dto.setPassMode(createForm.getPassMode());
		dto.setPassTypes(createForm.getPassTypes());
		return dto;
	}

	@Override
	public String myList() {
		if (playType == null)
			playType = PlayType.SPF;
		return super.myList();
	}

	@Override
	protected SchemeQueryDTO buildMySchemeQueryDTO() {
		SchemeQueryDTO dto = super.buildMySchemeQueryDTO();
		if (playType != null) {
			dto.setPlayType(playType.name());
		}
		return dto;
	}

	protected void supportOkoooAnd500wan() throws WebDataException {
		String singleContent;
		try {
			singleContent = createForm.getSingleSchemeContent();
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		if (StringUtils.isNotBlank(singleContent)) {
			if (singleContent.indexOf('→') > 0) {
				List<String> comOkoooCodeList = null;
				switch (playType) {
				case SPF:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemSPF item : ItemSPF.values()) {
						comOkoooCodeList.add(item.getValue());
					}
					break;
				case ZJQS:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemZJQS item : ItemZJQS.values()) {
						comOkoooCodeList.add(item.getText());
					}
					break;
				case SXDS:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemSXDS item : ItemSXDS.values()) {
						comOkoooCodeList.add(item.getText());
					}
					break;
				case BF:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemBF item : ItemBF.values()) {
						comOkoooCodeList.add(item.getText());
					}
					break;
				case BQQSPF:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemBQQSPF item : ItemBQQSPF.values()) {
						comOkoooCodeList.add(item.getValue().replaceAll("(\\d)(\\d)", "$1-$2"));
					}
					break;
				}

				createForm.setComOkoooCodeList(comOkoooCodeList);
			} else if (singleContent.indexOf(":[") > 0) {
				List<String> com500wanCodeList = null;
				switch (playType) {
				case SPF:
					com500wanCodeList = new ArrayList<String>();
					for (ItemSPF item : ItemSPF.values()) {
						com500wanCodeList.add(item.getValue());
					}
					break;
				case BF:
					com500wanCodeList = new ArrayList<String>();
					for (ItemBF item : ItemBF.values()) {
						com500wanCodeList.add(item.getText());
					}
					break;
				}
				createForm.setCom500wanCodeList(com500wanCodeList);
			}
		}
	}

	protected void checkCreateForm() throws WebDataException {
		if (createForm == null)
			throw new WebDataException("表单数据为空.");

		if (playType == null)
			throw new WebDataException("玩法类型不能为空.");

		//if (createForm.getPassMode() == null)
		//	throw new WebDataException("过关模式不能为空.");
		if (createForm.getPassTypes() == null || createForm.getPassTypes().isEmpty())
			throw new WebDataException("过关方式不能为空.");
	}

	protected void checkCreateFormOfCOMPOUND() throws WebDataException {
		if (createForm.getItems() == null || createForm.getItems().isEmpty())
			throw new WebDataException("投注内容不能为空.");

		switch (createForm.getPassMode()) {
		case NORMAL:
			if (createForm.getItems().size() > playType.getMaxMatchSize())
				throw new WebDataException("[" + playType.getText() + "]普通过关选择不能超过" + playType.getMaxMatchSize() + "场.");

			if (createForm.getPassTypes().size() > 1)
				throw new WebDataException("普通过关模式只能选择一个过关方式.");

			PassType passType = createForm.getPassTypes().get(0);
			if (passType != PassType.P1 && passType.getMatchCount() != createForm.getItems().size())
				throw new WebDataException("选择的场次数目与过关方式不匹配.");
			break;
		case MULTIPLE:
			for (PassType type : createForm.getPassTypes()) {
				if (!type.isForMultipleMode())
					throw new WebDataException("多选过关模式不支持[" + type.getText() + "]过关.");
				if (type.getMatchCount() > playType.getMaxMatchSize())
					throw new WebDataException("过关方式不正确.");
			}
			break;
		default:
			throw new WebDataException("过关模式不合法.");
		}
	}

	protected void checkCreateFormOfSINGLE() throws WebDataException {
		if (createForm.getPassMode() != PassMode.NORMAL)
			throw new WebDataException("单式只支持普通过关模式.");

		if (createForm.getLineIds() == null || createForm.getLineIds().isEmpty())
			throw new WebDataException("未选择投注场次.");

		if (createForm.getPassTypes().size() > 1)
			throw new WebDataException("普通过关模式只能选择一个过关方式.");
		PassType passType = createForm.getPassTypes().get(0);
		if (passType.getUnits() != 1)
			throw new WebDataException("单式不支持此过关方式[" + passType.getText() + "].");
		else if (passType.getMatchCount() > playType.getMaxMatchSize())
			throw new WebDataException("不支持此过关方式[" + passType.getText() + "].");

		if (createForm.getCodes() == null || createForm.getCodes().isEmpty())
			throw new WebDataException("格式转换字符不能为空.");

		int codeSize = playType.getAllItems().length;
		if (createForm.getCodes().size() != codeSize)
			throw new WebDataException("格式转换字符的数目不对.");

		int codeStrLen = 0;
		Set<String> codeSet = new HashSet<String>();
		for (String code : createForm.getCodes()) {
			if (code == null)
				throw new WebDataException("格式转换字符不能为空.");
			if (!code.matches(RegexUtils.UpperLetterAndNumber))
				throw new WebDataException("格式转换字符只能由大写英文字母和数字组成.");

			if (codeStrLen == 0) {
				codeStrLen = code.length();
				if (codeStrLen > 2)
					throw new WebDataException("格式转换字符的长度只允许最多两个字符.");
			} else if (code.length() != codeStrLen)
				throw new WebDataException("格式转换字符的长度必须相同.");

			if (codeSet.contains(code))
				throw new WebDataException("格式转换字符不能重复.");

			codeSet.add(code);
		}
	}

	/**
	 * 计算单式单倍注数
	 */
	public String countSingleUnits() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			checkCreateForm();
			supportOkoooAnd500wan();
			checkCreateFormOfSINGLE();
			int units = createForm.countSingleUnits();
			resultMap.put("success", true);
			resultMap.put("units", units);
		} catch (WebDataException e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		} catch (DataException e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		} catch (Exception e) {
			logger.warn("计算单式单倍注数出错.", e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}
		Struts2Utils.renderJson(resultMap);
		return null;
	}

	@Override
	protected String doShow() throws WebDataException {
		if (scheme != null) {
			if (scheme.isCompoundMode()) {
				List<DczcMatch> matchs = findMatchsOfCacheable(scheme.getPeriodId());
				Map<Integer, DczcMatch> matchMap = new HashMap<Integer, DczcMatch>();
				for (DczcMatch m : matchs) {
					matchMap.put(m.getLineId(), m);
				}
				Struts2Utils.setAttribute("matchMap", matchMap);
			}
		}
		return super.doShow();
	}

	@Override
	protected void handleCanSubscribe(Date endSubscribeTime) throws WebDataException {
		if (endSubscribeTime.getTime() > System.currentTimeMillis()) {
			List<DczcMatch> matchs = findMatchsOfCacheable(scheme.getPeriodId());

			int aheadEndMinute = DczcUtils.getAheadEndMinute(scheme.getMode(), scheme.getPassMode());

			Calendar calendar = Calendar.getInstance();
			List<Integer> lineIds = scheme.getSelectedLineIds();
			for (DczcMatch match : matchs) {
				if (lineIds.contains(match.getLineId())) {
					calendar.setTime(match.getMatchTime());
					calendar.add(Calendar.MINUTE, -aheadEndMinute);
					if (calendar.getTime().before(endSubscribeTime))
						endSubscribeTime = calendar.getTime();
				}
			}
		}
		super.handleCanSubscribe(endSubscribeTime);
	}

	public String combinations() {
		try {
			if (id == null)
				throw new WebDataException("方案ID不能为空.");

			scheme = getSchemeEntityManager().getScheme(id);
			if (scheme == null)
				throw new WebDataException("方案不存在.");

			User user = getLoginUser();
			String canViewDetailStr = canViewDetail(scheme, period, user);
			if (!"true".equalsIgnoreCase(canViewDetailStr))
				throw new WebDataException(canViewDetailStr);

			pagination.setPageSize(20);
			pagination = scheme.getCombinations(pagination);

			return "combinations";
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

	@Override
	public String canViewDetail(DczcScheme scheme, Period period, User user) {
		if (user != null && user.getId().equals(scheme.getSponsorId()))
			return "true";
		switch (scheme.getSecretType()) {
		case FULL_PUBLIC:
			return "true";
		case DRAWN_PUBLIC:
			if (period.isDrawed() && scheme.isUpdateWon())
				return "true";
			else
				return SecretType.DRAWN_PUBLIC.getSecretName();
		}
		return "方案保密";
	}

	public String prizeForecastOfInit() {
		try {
			if (playType == null)
				throw new WebDataException("玩法类型为空.");
			if (createForm == null)
				throw new WebDataException("表单为空.");
			if (createForm.getPeriodId() == null)
				throw new WebDataException("期ID为空.");
			if (createForm.getMultiple() == null || createForm.getMultiple() < 1)
				throw new WebDataException("倍数不正确.");
			if (createForm.getItems() == null || createForm.getItems().isEmpty())
				throw new WebDataException("场欠内容为空.");
			if (createForm.getPassTypes() == null || createForm.getPassTypes().isEmpty())
				throw new WebDataException("过关方式为空.");

			for (PassType passType : createForm.getPassTypes()) {
				if (passType.getUnits() > 1 && createForm.getPassTypes().size() > 1)
					throw new WebDataException("过关方式不正确.");
			}

			return prizeForecast(createForm.getPeriodId(), playType, createForm.getItems(), createForm.getMultiple(),
					createForm.getPassTypes(), createForm.getDanMinHit(), createForm.getDanMaxHit());
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	public String prizeForecastOfScheme() {
		try {
			if (id != null)
				scheme = getSchemeEntityManager().getScheme(id);
			else {
				String schemeNumber = Struts2Utils.getRequest().getParameter("schemeNumber");
				if (StringUtils.isBlank(schemeNumber))
					throw new WebDataException("方案号为空.");
				scheme = getSchemeEntityManager().getSchemeBy(schemeNumber);
			}
			if (scheme == null)
				throw new WebDataException("方案不存在.");
			if (!scheme.isCompoundMode())
				throw new WebDataException("只有复式方案才支持奖金预测.");

			DczcCompoundContent compoundContent = scheme.getCompoundContent();

			return prizeForecast(scheme.getPeriodId(), scheme.getPlayType(), compoundContent.getItems(),
					scheme.getMultiple(), scheme.getPassTypes(), compoundContent.getDanMinHit(),
					compoundContent.getDanMaxHit());
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	protected String prizeForecast(Long periodId, PlayType playType, List<DczcMatchItem> items, Integer multiple,
			List<PassType> passTypes, Integer danMinHit, Integer danMaxHit) throws WebDataException {
		if (spss == null || spss.isEmpty())
			throw new WebDataException("SP数据为空.");
		else if (spss.size() != items.size())
			throw new WebDataException("SP数据数目与场次数目不对应.");

		List<Integer> lineIdList = new ArrayList<Integer>();
		for (DczcMatchItem matchItem : items) {
			lineIdList.add(matchItem.getLineId());
		}

		Map<Integer, DczcMatch> matchMap = matchEntityManager.findMatchMap(periodId, lineIdList);
		if (matchMap == null || matchMap.size() != lineIdList.size())
			throw new WebDataException("找不到对应场次的赛事.");
		Struts2Utils.setAttribute("matchMap", matchMap);

		Item[] allItems = playType.getAllItems();
		List<DczcExtraMatchItem> extraMatchItemList = new ArrayList<DczcExtraMatchItem>();
		for (int i = 0; i < items.size(); i++) {
			DczcMatchItem matchItem = items.get(i);

			DczcMatch match = matchMap.get(matchItem.getLineId());
			Item resultItem = null;
			Double resultSp = null;
			if (match.isEnded()) {
				resultItem = match.getResult(playType);
				resultSp = match.getResultSp(playType);
			}

			List<DczcExtraItem> values = new ArrayList<DczcExtraItem>();
			String spsStr = this.spss.get(i);
			if (StringUtils.isBlank(spsStr))
				throw new WebDataException("SP值数据不正确.");
			String[] spArr = spsStr.split(",");
			int k = 0;
			for (Item item : allItems) {
				if (matchItem.hasSelect(item)) {
					Double sp;
					if (match.isCancel()) {
						sp = DczcConstant.CANCEL_MATCH_RESULT_SP;
					} else if (match.isEnded() && resultItem != null && resultSp != null) {
						sp = resultSp;
					} else {
						if (k >= spArr.length)
							throw new WebDataException("SP值数据不正确.");
						String spStr = spArr[k];
						sp = (StringUtils.isNotBlank(spStr)) ? Double.valueOf(spStr) : 0.0d;
					}

					DczcExtraItem extraItem = new DczcExtraItem();
					extraItem.setItem(item);
					extraItem.setSp(sp);
					values.add(extraItem);

					k++;
				}
			}

			DczcExtraMatchItem extraMatchItem = new DczcExtraMatchItem();
			extraMatchItem.setLineId(matchItem.getLineId());
			extraMatchItem.setDan(matchItem.isDan());
			extraMatchItem.setValues(values);
			extraMatchItem.setCancel(match.isCancel());
			if (match.isEnded() && resultItem != null && resultSp != null) {
				extraMatchItem.setEnd(true);
				extraMatchItem.setResultItem(resultItem);
				extraMatchItem.setResultSp(resultSp);
			}
			extraMatchItemList.add(extraMatchItem);
		}

		Struts2Utils.setAttribute("playType", playType);

		PrizeForecast prizeForecast = new PrizeForecast(multiple, extraMatchItemList, passTypes, danMinHit, danMaxHit);
		Struts2Utils.setAttribute("prizeForecast", prizeForecast);

		return "prize-forecast";
	}

	/**
	 * 玩法介绍
	 */
	@Override
	public String introduction() throws WebDataException {
		if (PlayType.BF.equals(playType)) {
			return "introduction-BF";
		} else if (PlayType.BQQSPF.equals(playType)) {
			return "introduction-BQQSPF";
		} else if (PlayType.SPF.equals(playType)) {
			return "introduction-SPF";
		} else if (PlayType.SXDS.equals(playType)) {
			return "introduction-SXDS";
		} else if (PlayType.ZJQS.equals(playType)) {
			return "introduction-ZJQS";
		}
		return null;

	}

	@Override
	public String protocol() throws WebDataException {
		if (PlayType.BF.equals(playType)) {
			return "protocol-BF";
		} else if (PlayType.BQQSPF.equals(playType)) {
			return "protocol-BQQSPF";
		} else if (PlayType.SPF.equals(playType)) {
			return "protocol-SPF";
		} else if (PlayType.SXDS.equals(playType)) {
			return "protocol-SXDS";
		} else if (PlayType.ZJQS.equals(playType)) {
			return "protocol-ZJQS";
		}
		return null;

	}

	// -------------------------------------------------------

	/**
	 * @return {@link #createForm}
	 */
	public DczcSchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm
	 *            the {@link #createForm} to set
	 */
	public void setCreateForm(DczcSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public DczcSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm
	 *            the {@link #uploadForm} to set
	 */
	public void setUploadForm(DczcSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	/**
	 * @return {@link #playType}
	 */
	public PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType
	 *            the {@link #playType} to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	/**
	 * @return {@link #spss}
	 */
	public List<String> getSpss() {
		return spss;
	}

	/**
	 * @param spss
	 *            the {@link #spss} to set
	 */
	public void setSpss(List<String> spss) {
		this.spss = spss;
	}
}
