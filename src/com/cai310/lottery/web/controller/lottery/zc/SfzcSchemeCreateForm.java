package com.cai310.lottery.web.controller.lottery.zc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.web.controller.lottery.SchemeCreateForm;

public class SfzcSchemeCreateForm extends SchemeCreateForm {

	/** 选择的场次内容 */
	private SfzcCompoundItem[] items = new SfzcCompoundItem[14];

	/** 玩法 */
	private PlayType playType;

	/** 单式内容 */
	private String content;

	/** 最小命中数 */
	private int danMinHit = -1;

	public SfzcSchemeCreateForm() {
		super();
		for (int i = 0; i < items.length; i++) {
			items[i] = new SfzcCompoundItem();
		}
	}

	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		StringBuffer content = new StringBuffer();
		int units = 0;
		if(playType==null)throw new DataException("玩法类型为空！");
		int selectedMatchCount = 0;//选中的场次数
		switch(playType){
		case SFZC9:			
			content.append(danMinHit).append(ZcUtils.getDanMinHitContentSpiltCode());
			List<SfzcCompoundItem> danList = new ArrayList<SfzcCompoundItem>();
			List<SfzcCompoundItem> unDanList = new ArrayList<SfzcCompoundItem>();
			
			for (SfzcCompoundItem item : items) {
				if (item.selectedCount() > 0) {
					if (item.isShedan()) {
						danList.add(item);
					} else {
						unDanList.add(item);
					}
					selectedMatchCount++;
				}
			}
			if(selectedMatchCount<ZcUtils.SFZC9_MATCH_MINSELECT_COUNT){
				throw new DataException("选择场次不合法，请选择该玩法需要的场次数！");
			}

			SchemeConverWork<SfzcCompoundItem> work = new SchemeConverWork<SfzcCompoundItem>(
					ZcUtils.SFZC9_MATCH_MINSELECT_COUNT, danList, unDanList, danMinHit, -1);
			for (List<SfzcCompoundItem> itemList : work.getResultList()) {
				units += ZcUtils.calcBetUnits(itemList.toArray(new SfzcCompoundItem[9]));
			}
			break;
		case SFZC14:
			for (SfzcCompoundItem item : items) {
				if (item.selectedCount() > 0) {					
					selectedMatchCount++;
				}
			}
			if(selectedMatchCount<ZcUtils.SFZC14_MATCH_COUNT){
				throw new DataException("选择场次不合法，请选择该玩法需要的场次数！");
			}
			units = ZcUtils.calcBetUnits(items);
			break;
		default:
			throw new DataException("玩法类型不合法！");
		}
		for (SfzcCompoundItem item : items) {
			content.append(item.toByte()).append(ZcUtils.getContentSpiltCode());
		}
		return new ContentBean(units, content.substring(0, content.toString().length() - 1));
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		String singleContent = getSingleSchemeContent().trim();
		if (StringUtils.isBlank(singleContent))
			throw new DataException("方案内容不能为空.");
		BufferedReader reader = new BufferedReader(new StringReader(singleContent));
		StringBuffer betContnet = new StringBuffer();
		int units = 0;
		try {
			String line = reader.readLine().trim();
			while (line != null) {
				if (line.length() > 1024)
					throw new DataException("方案内容太大.");

				line = this.formatSingleResults(line);//格式化内容,去除分隔符
				
				this.validateSingleResults(line);//验证格式合法性

				if (StringUtils.isNotBlank(line)) {
					units++;
					betContnet.append(line);
					betContnet.append("\r\n");
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ContentBean(units, betContnet.toString());
	}
	
	/**
	 * 格式化单式投注项内容
	 * @param line
	 * @return
	 */
	private String formatSingleResults(String line){
		final String converStr ="-|,";//单式方案兼容"-"或","的投注项分隔符
		line = line.trim();
		line = StringUtils.deleteWhitespace(line);
		line = line.replaceAll("\\"+converStr, "");
		line = line.replaceAll("\\D", String.valueOf(ZcUtils.getSfzc9NoSelectedCode()));
		return line;
	}
	
	/**
	 * 验证单式内容的合法性
	 * @param line
	 * @throws DataException
	 */
	private void validateSingleResults(String line) throws DataException{
		if (line.length() != ZcUtils.getMatchCount(Lottery.SFZC))
			throw new DataException("投注内容格式不正确.");
		if (playType == PlayType.SFZC14) {
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (c == '0' || c == '1' || c == '3')
					continue;
				throw new DataException("投注内容格式不正确.");
			}
		} else if (playType == PlayType.SFZC9) {
			int codeCount = 0;
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (c == '0' || c == '1' || c == '3' || c == ZcUtils.getSfzc9NoSelectedCode()) {
					if (c != ZcUtils.getSfzc9NoSelectedCode())
						codeCount++;
					continue;
				}
				throw new DataException("投注内容格式不正确.");
			}
			if (codeCount != 9)
				throw new DataException("投注内容格式不正确.");
		} else {
			throw new DataException("玩法匹配异常.");
		}
	}

	/**
	 * 获取方案内容
	 * 
	 * @return 方案内容
	 * @throws DataException
	 */
	private String getSingleSchemeContent() throws DataException {
		if (isFileUpload())
			return getUploadContent();
		else
			return getContent();
	}

	/**
	 * @return {@link #items}
	 */
	public SfzcCompoundItem[] getItems() {
		return items;
	}

	/**
	 * @param items the {@link #items} to set
	 */
	public void setItems(SfzcCompoundItem[] items) {
		this.items = items;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDanMinHit() {
		return danMinHit;
	}

	public void setDanMinHit(int danMinHit) {
		this.danMinHit = danMinHit;
	}

}
