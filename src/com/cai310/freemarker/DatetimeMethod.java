package com.cai310.freemarker;

import java.util.List;

import org.joda.time.DateTime;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class DatetimeMethod implements TemplateMethodModel {

	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if (!args.isEmpty()) {
			return new DateTime(args.get(0));
		} else {
			return new DateTime();
		} 
	}
}