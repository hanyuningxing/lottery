package com.cai310.lottery.web.controller.info.passcount.visitor;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.NumberData;

public class NumberDataVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private NumberData numberData;

	/**
	 * <data> <periodData> <firstPrize>111</firstPrize> <prizePool></prizePool>
	 * <result></result> <totalSales>111</totalSales> </periodData>
	 * <time>2010-07-04 23:14</time> <startTime>2010-07-02 16:27</startTime>
	 * <endTime>2010-06-26 20:00</endTime> </data>
	 * 
	 */
	@Override
	public void visit(Element node) {
		if (null == numberData) {
			numberData = new NumberData();
		}
		if (StringUtils.isNotBlank(node.getText())) {
			if ("firstPrize".equals(node.getName())) {
				numberData.setFirstPrize(Long.valueOf(node.getText()));
			}

			if ("firstWinUnit".equals(node.getName())) {
				numberData.setFirstWinUnit(Long.valueOf(node.getText()));
			}

			if ("secondWinUnit".equals(node.getName())) {
				numberData.setSecondWinUnit(Long.valueOf(node.getText()));
			}
			if ("secondPrize".equals(node.getName())) {
				numberData.setSecondPrize(Long.valueOf(node.getText()));
			}

			if ("prizePool".equals(node.getName())) {
				numberData.setPrizePool(Long.valueOf(node.getText()));
			}
			if ("totalSales".equals(node.getName())) {
				numberData.setTotalSales(Long.valueOf(node.getText()));
			}
			if ("result".equals(node.getName())) {
				numberData.setResult(node.getText());
			}
			if ("time".equals(node.getName())) {
				numberData.setUpdateTime(node.getText().trim());
			}
			if ("startTime".equals(node.getName())) {
				numberData.setStartTime(node.getText().trim());
			}
			if ("endTime".equals(node.getName())) {
				numberData.setEndTime(node.getText().trim());
			}

		}
	}

	/**
	 * @return the numberData
	 */
	public NumberData getNumberData() {
		return numberData;
	}

	/**
	 * @param numberData
	 *            the numberData to set
	 */
	public void setNumberData(NumberData numberData) {
		this.numberData = numberData;
	}

}
