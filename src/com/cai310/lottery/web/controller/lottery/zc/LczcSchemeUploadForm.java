package com.cai310.lottery.web.controller.lottery.zc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;

public class LczcSchemeUploadForm extends SchemeUploadForm {
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		// TODO:未完成
		throw new RuntimeException("未完成");
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		final String converStr ="-|,";//单式方案兼容"-"或","的投注项分隔符
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

				line = StringUtils.deleteWhitespace(line);
				line = line.replaceAll("\\"+converStr, "");
				
				if (line.length() != ZcUtils.getMatchCount(Lottery.LCZC) * 2)
					throw new DataException("投注内容格式不正确.");
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if (c == '0' || c == '1' || c == '3')
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

}
