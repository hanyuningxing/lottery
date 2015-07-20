package com.cai310.lottery.web.controller.admin.lottery.jczq;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PrintEntityManagerImpl;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeService;
import com.cai310.lottery.service.lottery.ticket.impl.SynchronizedTicketStateManager;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;
import com.cai310.utils.Struts2Utils;

@Action(value = "scheme")
public class JczqSchemeController extends SchemeController<JczqScheme> {
	private static final long serialVersionUID = 5985842237187757494L;
 
	@Autowired
	private JczqSchemeService schemeService;

	@Autowired
	private JczqSchemeEntityManager schemeEntityManager;
	@Autowired
	private SynchronizedTicketStateManager synchronizedTicketStateManager;
	@Autowired
	private PrintEntityManagerImpl printEntityManagerImpl;

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCZQ;
	}

	@Override
	protected SchemeEntityManager<JczqScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<JczqScheme, ?> getSchemeService() {
		return schemeService;
	}

	@Override
	protected String getSchemeContentText() {
		if (scheme == null)
			return super.getSchemeContentText();

		return scheme.getContentText();
	}
	public String odds() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
		   AdminUser adminUser = getAdminUser();
		   if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
		   }
		   if (schemeId == null)throw new WebDataException("方案ID找不到！");
		   JczqScheme jczqScheme= schemeEntityManager.getScheme(schemeId);
		   if (jczqScheme == null)throw new WebDataException("方案找不到！");
		   PrintInterface printInterface = printEntityManagerImpl.findPrintInterfaceBy(jczqScheme.getSchemeNumber(), jczqScheme.getLotteryType());
		   if (printInterface == null)throw new WebDataException("方案出票接口找不到！");
		   synchronizedTicketStateManager.synchronization_Sp_Jc(printInterface);
		   map.put("success", true);
		   map.put("msg", "更新成功");
		} catch (WebDataException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", e.getMessage());
		 }catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", "更新sp发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}
	
}
