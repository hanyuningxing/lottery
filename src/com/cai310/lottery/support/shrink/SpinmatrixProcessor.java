package com.cai310.lottery.support.shrink;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.freemarker.FreemarkerTemplateProcessor;
import com.google.common.collect.Maps;

public class SpinmatrixProcessor {
	private static final NumberFormat DEFAULT_NF = new DecimalFormat("00");

	@Autowired
	private FreemarkerTemplateProcessor freemarkerTemplateProcessor;

	public String spinmatrix(SpinmatrixType spinmatrixType, int[] nums) {
		return spinmatrix(spinmatrixType, nums, DEFAULT_NF);
	}

	public String spinmatrix(SpinmatrixType spinmatrixType, int[] nums, NumberFormat nf) {
		String templatePath = getTemplatePath(spinmatrixType, nums.length);
		if (templatePath == null)
			return null;
		Arrays.sort(nums);
		Map<String, String> map = Maps.newHashMap();
		for (int i = 0; i < nums.length; i++) {
			map.put(getNumKey(i + 1), nf.format(nums[i]));
		}
		return freemarkerTemplateProcessor.processTemplate(templatePath, map);
	}

	private String getNumKey(int num) {
		return String.format("num_%1$02d", num);
	}

	public String getTemplatePath(SpinmatrixType spinmatrixType, int numSize) {
		if (numSize < spinmatrixType.getMin() || numSize > spinmatrixType.getMax())
			return null;
		return String.format("/WEB-INF/template/spinmatrix/%s/%s_%s.ftl", spinmatrixType.getKey(),
				spinmatrixType.getKey(), numSize);
	}

}
