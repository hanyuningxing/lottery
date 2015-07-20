package com.cai310.lottery.web.controller.user.alipayUtils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.utils.DateUtil;

public class TradeVisitor extends VisitorSupport{
	private String out_trade_no;
	private String trade_status;
	private String total_fee;
	public void visit(Element node){
		if("out_trade_no".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				out_trade_no=node.getText().trim();
			}
		}
		if("trade_status".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				trade_status=node.getText().trim();
			}
		}
		if("total_fee".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				total_fee=node.getText().trim();
			}
		}
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	
}
