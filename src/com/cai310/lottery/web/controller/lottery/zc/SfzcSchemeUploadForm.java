package com.cai310.lottery.web.controller.lottery.zc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;

public class SfzcSchemeUploadForm extends SchemeUploadForm {
	
	/** 玩法 */
	private PlayType playType;
	
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		// TODO:未完成
		throw new RuntimeException("未完成");
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		String singleContent = getUploadContent().trim();
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

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	
	
	
}
