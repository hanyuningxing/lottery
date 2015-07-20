package com.cai310.lottery.web.controller.demo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.MessageType;
import com.cai310.lottery.common.PopuType;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.forum.ForumController;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.MD5;
import com.cai310.utils.PropertiesUtil;
import com.cai310.utils.Struts2Utils;
import com.esms.PostMsg;



public class JqueryController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String index(){
		return "index";
	}
}
