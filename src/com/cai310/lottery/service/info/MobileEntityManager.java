package com.cai310.lottery.service.info;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.entity.info.Mobile;
import com.cai310.lottery.entity.user.User;

public interface MobileEntityManager {
	Mobile save(Mobile mobile);
	List<Mobile> getAll();
	Mobile getMobileBy(String number);
}
