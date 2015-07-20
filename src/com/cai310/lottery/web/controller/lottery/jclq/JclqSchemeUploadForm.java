package com.cai310.lottery.web.controller.lottery.jclq;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;

public class JclqSchemeUploadForm extends SchemeUploadForm {
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		throw new RuntimeException("竞彩足球不支持上传.");
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		throw new RuntimeException("竞彩足球不支持上传.");
	}

}
