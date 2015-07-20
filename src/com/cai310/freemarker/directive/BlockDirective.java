package com.cai310.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import com.cai310.freemarker.directive.OverrideDirective.TemplateDirectiveBodyOverrideWraper;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@SuppressWarnings("rawtypes")
public class BlockDirective implements TemplateDirectiveModel {
	public final static String DIRECTIVE_NAME = "block";

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String name = DirectiveUtils.getRequiredParam(params, "name");
		TemplateDirectiveBodyOverrideWraper overrideBody = DirectiveUtils.getOverrideBody(env, name);
		if (overrideBody == null) {
			if (body != null) {
				body.render(env.getOut());
			}
		} else {
			DirectiveUtils.setTopBodyForParentBody(env, new TemplateDirectiveBodyOverrideWraper(body, env),
					overrideBody);
			overrideBody.render(env.getOut());
		}
	}

}
