package com.cai310.lottery.common;

public enum EventLogType {
	// 注：byte类型值范围[-128至127]
	// [1至49]供销售流程使用的
	// [50至69]保留供以后调整分配
	// [70至127]供非销售流程操作使用

	// [1]分界线
	// ******************************************************************************
	
	PauseSale((byte) 7, "暂停销售"),
	ResumeSale((byte) 8, "恢复销售"),
	HideIssue((byte) 10, "设为非当前销售") ,
	BeginSale((byte) 11, "开始销售") ,
	EndSale((byte) 12, "结束销售") ,
	Baodi((byte) 13, "保底") ,
	CommitPayment((byte) 14, "完成交易") ,
	UpdateResult((byte) 15, "更新中奖") ,
	DelivePrize((byte) 16, "派发奖金") ,
	CloseIssue((byte) 17, "关闭期号") ,
	ChasePlan((byte) 21, "触发追号") ,
	NextIssueChasePlan((byte) 24, "触发下一期追号") ,
	UpdatePrize((byte) 25, "更新奖金") ,
	SetTime((byte) 26, "设置销售时间") ,
	SetResult((byte) 27, "设置开奖号码") ,
	NewPeriod((byte) 28, "创建期数据") ,
	UpdatePeriod((byte) 29, "修改期数据") ,
	UpdateMatch((byte) 30, "更新对阵") ,
	UnPrintedEndSale((byte) 32, "结束未出票销售") ,
	SaleAnalyse((byte) 33, "销售统计") ,
	ShowIssue((byte) 34, "设为当前销售") ,
	DeliveWinRecord((byte) 35, "派发战绩") ,
	// [50]分界线
	// ******************************************************************************
	Security((byte) 50, "后台安全框架") ,
	Info_Admin((byte) 51, "后台文章") ,
	Index_Admin((byte) 52, "后台首页生成") ,
	Fund_Admin((byte) 53, "后台操作资金") ,
	Drawing_Admin((byte) 54, "后台操作退款订单") ,
	User_Admin((byte) 55, "后台操作用户") ,

	// [70]分界线
	// ******************************************************************************
	Login((byte) 70, "登录") ,
	ResetUserWinRecord((byte) 78, "重置战绩") ,
	ForcePrint((byte) 79, "方案强制出票") ,
	CancelScheme((byte) 80, "方案撤单") ,
	CancelJoin((byte) 81, "合买撤单") ,
	CancelBaodi((byte) 82, "撤销保底") ,
	UploadScheme((byte) 83, "上传方案") ,
	ResetUnUpdateWon((byte) 84, "重置未开奖") ,
	stopChase((byte) 85, "停止追号") ,
	editRemark((byte) 86, "编辑备注") ,
	keepDefault((byte) 87, "方案恢复默认排序") ,
	keepTop((byte) 88, "方案置顶") ,
	keepBottom((byte) 89, "方案置底") ,
	reserved((byte) 90, "保留方案") ,
	cancelReserved((byte) 91, "取消保留方案") ,
	refundment((byte) 92, "退款") ,
	//用户操作
	sentMsg((byte) 93, "短信验证") ,
	register((byte) 94, "用户注册"),
	updateUserInfo((byte) 95, "帐号设置"),
	epayCheckLog((byte) 96, "在线充值") ,
	Score_Admin((byte) 97, "后台操作积分");

	private final byte logType;
	private final String text;

	private EventLogType(byte logType, String text) {
		this.logType = logType;
		this.text = text;
	}

	public byte getLogType() {
		return logType;
	}

	public String getText() {
		return text;
	}

	public static EventLogType valueOf(byte logType) {
		for (EventLogType type : EventLogType.values()) {
			if (type.logType == logType)
				return type;
		}
		return null;
	}
}
