package com.cai310.lottery.service.info.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.info.MobileDAO;
import com.cai310.lottery.entity.info.Mobile;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.info.MobileEntityManager;

@Service("mobileEntityManager")
@Transactional
public class MobileEntityManagerImpl implements MobileEntityManager {
	
	@Autowired
	private MobileDAO mobileDAO;
	
	@Override
	public Mobile save(Mobile mobile) {
		mobileDAO.save(mobile);
		return mobile;
	}
	
	public List<Mobile> getAll() {
		return mobileDAO.getAll();
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Mobile getMobileBy(String number) {
		if (StringUtils.isNotBlank(number)){			
			List<Mobile> l = mobileDAO.findBy("number", number.trim());
			if (null != l && !l.isEmpty()) {
				return l.get(0);
			}
		}
		return null;
	}

}
