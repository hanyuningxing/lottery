package com.cai310.lottery.web.controller.lottery;

import com.cai310.lottery.web.controller.BaseController;

/**
 * 彩种相关控制器基类
 * 
 */
public abstract class LotteryBaseController extends BaseController {
	private Integer menuType=0;
	private static final long serialVersionUID = -1395569304054782567L;
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
}
