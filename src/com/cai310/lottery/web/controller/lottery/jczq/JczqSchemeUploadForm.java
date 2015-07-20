package com.cai310.lottery.web.controller.lottery.jczq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.dto.lottery.jczq.JczqSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqCompoundContent;
import com.cai310.lottery.support.jczq.JczqContentBean;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.SchemeType;
import com.cai310.lottery.support.jczq.TicketItem;
import com.cai310.lottery.support.jczq.TicketSplitCallback;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class JczqSchemeUploadForm extends SchemeUploadForm {
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		throw new RuntimeException("竞彩足球不支持上传.");
	}
	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		///竞彩添加胜平负单式不同倍数
		throw new RuntimeException("竞彩足球不支持上传.");
	}
	

}
