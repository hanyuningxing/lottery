package com.cai310.lottery.web.controller.lottery.dczc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.dczc.DczcCompoundContent;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.DczcSingleContent;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.web.controller.lottery.SchemeCreateForm;

public class DczcSchemeCreateForm extends SchemeCreateForm {

	// ------------------------ 复式 -----------------------

	/** 选择的场次内容 */
	private List<DczcMatchItem> items;

	/** 胆码最小命中数 */
	private Integer danMinHit;

	/** 胆码最大命中数 */
	private Integer danMaxHit;

	// ------------------------ 单式-----------------------

	/** 选择的场次 */
	private List<Integer> lineIds;

	/** 格式转换字符 */
	private List<String> codes;

	/** 单式内容 */
	private String content;

	// --------------------- 单复式共用 --------------------

	/** 过关方式 */
	private List<PassType> passTypes;

	/** 过关模式 */
	private PassMode passMode;

	// ----------------------------------------------------
	private List<String> comOkoooCodeList;
	private List<String> com500wanCodeList;

	// ----------------------------------------------------

	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		// 在Controller那边做一些数据验证

		Collections.sort(this.items);
		PassType minPassType = passTypes.get(0);// 最小的过关方式

		final List<DczcMatchItem> danList = new ArrayList<DczcMatchItem>();
		final List<DczcMatchItem> unDanList = new ArrayList<DczcMatchItem>();
		for (DczcMatchItem item : items) {
			if (item.isDan())
				danList.add(item);
			else
				unDanList.add(item);
		}

		// 检查设胆数目
		if (danList.size() > minPassType.getMatchCount()) {
			throw new DataException("设置的胆码不能大于最小的过关场次.");
		}

		if (danMinHit == null || danMinHit <= 0)
			danMinHit = danList.size();
		else if (danMinHit > danList.size())
			throw new DataException("模糊设胆不正确.");

		if (danMinHit + unDanList.size() < minPassType.getMatchCount())
			throw new DataException("模糊设胆不正确.");

		if (danMaxHit == null || danMaxHit <= 0)
			danMaxHit = danList.size();

		int units = 0;
		for (PassType passType : this.passTypes) {
			for (final int needs : passType.getPassMatchs()) {
				units += UnitsCountUtils.countUnits(needs, danList, danMinHit, danMaxHit, unDanList);
				if (units > Constant.MAX_UNITS)
					throw new DataException("复式方案单倍注数不能大于" + Constant.MAX_UNITS + "注.");
			}
		}

		DczcCompoundContent content = new DczcCompoundContent();
		content.setItems(items);
		content.setDanMinHit(danMinHit);
		content.setDanMaxHit(danMaxHit);

		return new ContentBean(units, JSONObject.fromObject(content).toString());
	}

	/**
	 * 获取方案内容
	 * 
	 * @return 方案内容
	 * @throws DataException
	 */
	public String getSingleSchemeContent() throws DataException {
		if (isFileUpload())
			return getUploadContent();
		else
			return getContent();
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		// 在Controller那边做一些数据验证

		String singleContent = getSingleSchemeContent();
		if (StringUtils.isBlank(singleContent))
			throw new DataException("方案内容不能为空.");

		final PassType passType = passTypes.get(0);
		final int codeStrLen = codes.get(0).length();
		String temp = "";
		for (int i = 0; i < codeStrLen; i++) {
			temp += "#";
		}
		final String placeholder = temp;

		singleContent = singleContent.trim();
		String[] arr = singleContent.replaceAll("(\\*)", "#").split("(\r\n|\n)+");
		int units = 0;

		LineResolve resolve;
		if (comOkoooCodeList != null && !comOkoooCodeList.isEmpty()) {
			resolve = new ComOkoooLineResolveImpl(passType, codeStrLen, placeholder, comOkoooCodeList);
		} else if (com500wanCodeList != null && !com500wanCodeList.isEmpty()) {
			resolve = new Com500wanLineResolveImpl(passType, codeStrLen, placeholder, com500wanCodeList);
		} else {
			resolve = new SimpleLineResolveImpl(passType, codeStrLen, placeholder);
		}

		final String seq = "\r\n";
		StringBuilder sb = new StringBuilder();
		for (String line : arr) {
			String formatLine = resolve.resolve(line);
			units++;
			if (units > Constant.MAX_SINGLE_UNITS)
				throw new DataException("单式方案单倍注数不能大于" + Constant.MAX_SINGLE_UNITS + "注.");

			sb.append(formatLine).append(seq);
		}
		sb.delete(sb.length() - seq.length(), sb.length());

		DczcSingleContent contentObj = new DczcSingleContent();
		contentObj.setLineIds(lineIds);
		contentObj.setContent(sb.toString());
		return new ContentBean(units, JSONObject.fromObject(contentObj).toString());
	}

	// ================================================================

	interface LineResolve {
		String resolve(String line) throws DataException;
	}

	abstract class LineResolveImpl implements LineResolve {
		static final char place = '#';
		static final char itemSeq = ',';

		final PassType passType;
		final int codeStrLen;
		final String placeholder;
		final int len;

		private LineResolveImpl(PassType passType, int codeStrLen, String placeholder) {
			super();
			this.passType = passType;
			this.codeStrLen = codeStrLen;
			this.placeholder = placeholder;
			len = lineIds.size() * this.codeStrLen;
		}
	}

	class SimpleLineResolveImpl extends LineResolveImpl {
		private StringBuilder lineSb = new StringBuilder();

		private SimpleLineResolveImpl(PassType passType, int codeStrLen, String placeholder) {
			super(passType, codeStrLen, placeholder);
		}

		public String resolve(String line) throws DataException {
			line = line.trim();
			if (line.length() < len) {
				throw new DataException("投注内容格式不正确！");
			} else if (line.length() > len) {
				if (!line.substring(len, line.length()).matches("^(\\*|#)+$"))
					throw new DataException("投注内容格式不正确！");
			}
			line = line.substring(0, len);
			int c = 0;
			lineSb.setLength(0);
			for (int i = 0; i < len; i += codeStrLen) {
				String temp = line.substring(i, i + codeStrLen);
				if (placeholder.equals(temp)) {
					lineSb.append(place).append(itemSeq);
					continue;
				} else {
					int index = codes.indexOf(temp);
					if (index < 0)
						throw new DataException("投注内容格式不正确！");

					lineSb.append(index).append(itemSeq);
					c++;
				}
			}
			if (c != passType.getMatchCount())
				throw new DataException("投注内容格式不正确！");

			lineSb.deleteCharAt(lineSb.length() - 1);
			return lineSb.toString();
		}
	}

	class Com500wanLineResolveImpl extends LineResolveImpl {
		final Pattern pattern = Pattern.compile("\\s*(\\d{1,2})\\s*:\\s*\\[([^\\]]+)\\]\\s*");
		final List<String> codeList;
		private StringBuilder lineSb = new StringBuilder();
		private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		private Com500wanLineResolveImpl(PassType passType, int codeStrLen, String placeholder, List<String> codeList) {
			super(passType, codeStrLen, placeholder);
			this.codeList = codeList;
		}

		public String resolve(String line) throws DataException {
			lineSb.setLength(0);
			map.clear();

			line = line.trim();
			String[] arr = line.split("/");
			for (String str : arr) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					Integer id = Integer.valueOf(matcher.group(1));
					Integer lineId = id - 1;
					String value = matcher.group(2).trim();

					if (map.keySet().contains(lineId))
						throw new DataException("场次重复.");
					if (map.values().contains(value))
						throw new DataException("投注内容格式不正确！");
					
					if (!lineIds.contains(lineId))
						throw new DataException("场次错误.");

					int index = codeList.indexOf(value);
					if (index < 0)
						throw new DataException("投注内容格式不正确！");

					map.put(lineId, index);
				}
			}
			if (map.size() != passType.getMatchCount())
				throw new DataException("投注内容格式不正确！");

			for (Integer lineId : lineIds) {
				if (map.containsKey(lineId))
					lineSb.append(map.get(lineId));
				else
					lineSb.append(place);

				lineSb.append(itemSeq);
			}
			lineSb.deleteCharAt(lineSb.length() - 1);
			return lineSb.toString();
		}
	}

	class ComOkoooLineResolveImpl extends LineResolveImpl {
		final Pattern pattern = Pattern.compile("\\s*(\\d{1,2})\\s*→\\s*([^\\s]+).*");
		final List<String> codeList;
		private StringBuilder lineSb = new StringBuilder();
		private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		private ComOkoooLineResolveImpl(PassType passType, int codeStrLen, String placeholder, List<String> codeList) {
			super(passType, codeStrLen, placeholder);
			this.codeList = codeList;
		}

		public String resolve(String line) throws DataException {
			lineSb.setLength(0);
			map.clear();

			line = line.trim();
			String[] arr = line.split(",");
			for (String str : arr) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					Integer id = Integer.valueOf(matcher.group(1));
					Integer lineId = id - 1;
					String value = matcher.group(2).trim();

					if (map.keySet().contains(lineId))
						throw new DataException("场次重复.");
					if (map.values().contains(value))
						throw new DataException("投注内容格式不正确！");

					if (!lineIds.contains(lineId))
						throw new DataException("场次错误.");

					int index = codeList.indexOf(value);
					if (index < 0)
						throw new DataException("投注内容格式不正确！");

					map.put(lineId, index);
				}
			}
			if (map.size() != passType.getMatchCount())
				throw new DataException("投注内容格式不正确！");

			for (Integer lineId : lineIds) {
				if (map.containsKey(lineId))
					lineSb.append(map.get(lineId));
				else
					lineSb.append(place);

				lineSb.append(itemSeq);
			}
			lineSb.deleteCharAt(lineSb.length() - 1);
			return lineSb.toString();
		}
	}

	// ================================================================

	/**
	 * 计算单式单倍注数
	 * 
	 * @return 单式单倍注数
	 * @throws DataException
	 */
	public int countSingleUnits() throws DataException {
		return buildSingleContentBean().getUnits();
	}

	/**
	 * @return {@link #items}
	 */
	public List<DczcMatchItem> getItems() {
		return items;
	}

	/**
	 * @param items the {@link #items} to set
	 */
	public void setItems(List<DczcMatchItem> items) {
		this.items = items;
	}

	/**
	 * @return {@link #passTypes}
	 */
	public List<PassType> getPassTypes() {
		return passTypes;
	}

	/**
	 * @param passTypes the {@link #passTypes} to set
	 */
	public void setPassTypes(List<PassType> passTypes) {
		this.passTypes = passTypes;
	}

	/**
	 * @return {@link #passMode}
	 */
	public PassMode getPassMode() {
		return passMode;
	}

	/**
	 * @param passMode the {@link #passMode} to set
	 */
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	/**
	 * @return {@link #danMinHit}
	 */
	public Integer getDanMinHit() {
		return danMinHit;
	}

	/**
	 * @param danMinHit the {@link #danMinHit} to set
	 */
	public void setDanMinHit(Integer danMinHit) {
		this.danMinHit = danMinHit;
	}

	/**
	 * @return {@link #danMaxHit}
	 */
	public Integer getDanMaxHit() {
		return danMaxHit;
	}

	/**
	 * @param danMaxHit the {@link #danMaxHit} to set
	 */
	public void setDanMaxHit(Integer danMaxHit) {
		this.danMaxHit = danMaxHit;
	}

	/**
	 * @return {@link #lineIds}
	 */
	public List<Integer> getLineIds() {
		return lineIds;
	}

	/**
	 * @param lineIds the {@link #lineIds} to set
	 */
	public void setLineIds(List<Integer> lineIds) {
		this.lineIds = lineIds;
	}

	/**
	 * @return {@link #codes}
	 */
	public List<String> getCodes() {
		return codes;
	}

	/**
	 * @param codes the {@link #codes} to set
	 */
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	/**
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param comOkoooCodeList the {@link #comOkoooCodeList} to set
	 */
	public void setComOkoooCodeList(List<String> comOkoooCodeList) {
		this.comOkoooCodeList = comOkoooCodeList;
	}

	/**
	 * @param com500wanCodeList the {@link #com500wanCodeList} to set
	 */
	public void setCom500wanCodeList(List<String> com500wanCodeList) {
		this.com500wanCodeList = com500wanCodeList;
	}

	public static void main(String[] args) {
		String str = "06→3";
		System.out.println(str.matches("\\s*(\\d{1,2})\\s*→\\s*([^\\s]+).*"));
	}

}
