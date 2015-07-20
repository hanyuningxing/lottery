package com.cai310.lottery.builder.newsinfo;

import java.util.List;

import com.cai310.lottery.builder.DefaultInfoBuilderQueryForm;
import com.cai310.lottery.common.Lottery;

/**
 * Description: 首页新手成长之路信息——查询器表单参数<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class IndexSkillsBuilderQueryForm extends DefaultInfoBuilderQueryForm{
	private List<Lottery> lottery;
	public List<Lottery> getLottery() {
		return lottery;
	}

	public void setLottery(List<Lottery> lottery) {
		this.lottery = lottery;
	}
}
