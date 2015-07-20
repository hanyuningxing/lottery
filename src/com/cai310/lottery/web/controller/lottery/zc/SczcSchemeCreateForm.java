package com.cai310.lottery.web.controller.lottery.zc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.zc.SczcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.web.controller.lottery.SchemeCreateForm;

public class SczcSchemeCreateForm extends SchemeCreateForm {

	/** 选择的场次内容 */
	private SczcCompoundItem[] items = new SczcCompoundItem[8];

	/** 单式内容 */
	private String content;

	public SczcSchemeCreateForm() {
		super();
		for (int i = 0; i < items.length; i++) {
			items[i] = new SczcCompoundItem();
		}
	}

	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		StringBuffer content = new StringBuffer();
		int selectedMatchCount = 0;//选中的场次数
		for (SczcCompoundItem item : items) {
			if (item.selectedCount() > 0) {
				selectedMatchCount++;
				content.append(item.toByte()).append(ZcUtils.getContentSpiltCode());
			}
		}
		if(selectedMatchCount<ZcUtils.SCZC_MATCH_COUNT * 2){
			throw new DataException("选择场次不合法，请选择该玩法需要的场次数！");
		}
		int units = ZcUtils.calcBetUnits(items);		
		return new ContentBean(units, content.substring(0, content.toString().length() - 1));
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		final String converStr ="-|,";//单式方案兼容"-"或","的投注项分隔符
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

				line = StringUtils.deleteWhitespace(line);
				line = line.replaceAll("\\"+converStr, "");
				
				if (line.length() != ZcUtils.getMatchCount(Lottery.SCZC) * 2)
					throw new DataException("投注内容格式不正确.");
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if (c == '0' || c == '1' || c == '2' || c == '3')
						continue;
					throw new DataException("投注内容格式不正确.");
				}
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SczcCompoundItem[] getItems() {
		return items;
	}

	public void setItems(SczcCompoundItem[] items) {
		this.items = items;
	}

}
