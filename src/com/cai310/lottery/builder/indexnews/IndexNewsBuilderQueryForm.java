package com.cai310.lottery.builder.indexnews;

import com.cai310.lottery.builder.DefaultInfoBuilderQueryForm;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;

/**
 * Description: 新闻信息——查询器表单参数<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class IndexNewsBuilderQueryForm extends DefaultInfoBuilderQueryForm{
	private Lottery lottery;
	private InfoType infoType;
	private InfoSubType infoSubType;
	public Lottery getLottery() {
		return lottery;
	}
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	public InfoType getInfoType() {
		return infoType;
	}
	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}
	public InfoSubType getInfoSubType() {
		return infoSubType;
	}
	public void setInfoSubType(InfoSubType infoSubType) {
		this.infoSubType = infoSubType;
	}
	
	
}
