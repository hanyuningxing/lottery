package com.cai310.lottery.common.miss;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;

import com.cai310.lottery.entity.lottery.MissDataInfo;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqMissDataInfo;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.exception.FieldException;
import com.cai310.lottery.support.ssq.SsqMissDataContent;

public class MissDataUpdate {

	private List<PeriodData> results;

	private MissDataInfo preInfo;

	private List<MissDataFilter> filters;

	/**
	 * 
	 * @param results
	 *            新开奖结果列表
	 * @param preInfo
	 *            上一期的遗漏数据
	 * @param filters
	 *            各个遗漏项的过滤器
	 * @throws DataException
	 */
	public MissDataUpdate(List<PeriodData> results, MissDataInfo preInfo, List<MissDataFilter> filters)
			throws DataException {
		if (results == null || results.isEmpty()) {
			throw new DataException("开奖结果列表不能为空");
		}
		if (preInfo == null) {
			throw new DataException("最近一期的遗漏数据不能为空");
		}
		if (filters == null || filters.isEmpty()) {
			throw new DataException("遗漏项过滤器不能为空!");
		}
		this.results = results;
		this.preInfo = preInfo;
		this.filters = filters;
	}

	public List<MissDataInfo> update() {
		List<MissDataInfo> list = new ArrayList<MissDataInfo>();
		for (PeriodData periodData : results) {
			try {
				MissDataInfo newInfo = preInfo.getClass().newInstance();
				// MissDataContent preMissContent=preInfo.getMissDataContent();
				// MissDataContent
				// newMissContent=preMissContent.getClass().newInstance();
				Map<String, Integer> preMissContent = preInfo.getMissDataMap();
				if (preMissContent == null) {
					preMissContent = new HashMap<String, Integer>();
				}
				Map<String, Integer> newMissContent = new HashMap<String, Integer>();
				newInfo.setPeriodId(periodData.getPeriodId());
				newInfo.setResult(periodData.getResult());
				for (MissDataFilter filter : filters) {
					if (filter.accect(periodData.getResult())) {// 命中
						setValue(newMissContent, filter.getFieldName(), 0);
					} else {// 不命中,在上一期遗漏的基础上加
						Integer val = getValue(preMissContent, filter.getFieldName());
						if (val != null && val > 0) {
							// setIntField(newMissContent,filter.getFieldName(),val+1);
							setValue(newMissContent, filter.getFieldName(), val + 1);
						} else {
							// setIntField(newMissContent,filter.getFieldName(),1);
							setValue(newMissContent, filter.getFieldName(), 1);
						}
					}
				}
				newInfo.setContent(JSONObject.fromObject(newMissContent).toString());
				list.add(newInfo);
				preInfo = newInfo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private void setValue(Map<String, Integer> map, String fieldName, Integer value) {
		map.put(fieldName, value);
	}

	private Integer getValue(Map<String, Integer> map, String fieldName) {
		return (Integer) map.get(fieldName);
	}

	public static void setIntField(Object data, String fieldName, Integer value) throws FieldException {
		try {
			Field field = data.getClass().getDeclaredField(fieldName);
			if (field != null) {
				field.setAccessible(true);// 设置允许访问
				field.set(data, value);
				return;
			}
			throw new FieldException("找不到对应的属性");
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new FieldException("设置属性出错");
	}

	public static Integer getIntField(Object data, String fieldName) throws FieldException {
		try {
			Field field = data.getClass().getDeclaredField(fieldName);
			if (field != null) {
				field.setAccessible(true);// 设置允许访问
				return (Integer) field.get(data);
			}
			throw new FieldException("找不到对应的属性");
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new FieldException("查找属性出错");
	}

	// public static void main(String[] args) {
	// SsqMissDataInfo preInfo=new SsqMissDataInfo();
	// System.out.println(preInfo.getMissDataContent());
	// }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SsqMissDataContent c = new SsqMissDataContent();
		SsqMissDataInfo preInfo = new SsqMissDataInfo();
		try {
			// for(int i=1;i<=33;i++){
			// setIntField(c,"red"+i,0);
			// }
			// for(int i=1;i<=16;i++){
			// setIntField(c,"blue"+i,0);
			// }
			preInfo.setContent(JSONObject.fromObject(c).toString());
			preInfo.setPeriodNumber("10000");
			preInfo.setResult("1,2,3,4,5,6");
			// System.out.println(preInfo.getContent());
			List<MissDataFilter> filters = new ArrayList<MissDataFilter>();
			for (int i = 1; i <= 33; i++) {
				final int num = i;
				MissDataFilter filter = new MissDataFilter() {
					@Override
					public boolean accect(String results) {
						String[] resArrs = results.split(",");
						for (String str : resArrs) {
							if (str.equals(num + "")) {
								System.out.println("命中!");
								return true;
							}
						}
						return false;
					}

					@Override
					public String getFieldName() {
						return "red" + num;
					}

				};
				filters.add(filter);
			}
			for (int i = 1; i <= 16; i++) {
				final int num = i;
				MissDataFilter filter = new MissDataFilter() {
					@Override
					public boolean accect(String results) {
						String[] resArrs = results.split(",");
						return ArrayUtils.contains(resArrs, num);
					}

					@Override
					public String getFieldName() {
						return "blue" + num;
					}

				};
				filters.add(filter);
			}
			Map<Long, String> results = new HashMap<Long, String>();
			results.put(10001L, "1,2,3,4,5,6");
			results.put(10002L, "1,2,3,4,5,6");

			// MissDataUpdate update=new
			// MissDataUpdate(results,preInfo,filters);
			// List<MissDataInfo> list=update.update();
			// for(MissDataInfo info:list){
			// System.out.println(((SsqMissDataContent)info.getMissDataContent()).getRed11());
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
