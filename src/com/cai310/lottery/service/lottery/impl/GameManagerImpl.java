package com.cai310.lottery.service.lottery.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.GameColorDao;
import com.cai310.lottery.entity.lottery.GameColor;
import com.cai310.lottery.service.lottery.GameManager;
import com.cai310.orm.hibernate.ExecuteCallBack;
import com.google.common.collect.Maps;

@Service("gameManager")
@Transactional
public class GameManagerImpl implements GameManager {

	@Autowired
	private GameColorDao gameColorDao;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Map<String, String> getGameColor() {
		@SuppressWarnings("unchecked")
		List<GameColor> list = (List<GameColor>) gameColorDao.execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(GameColor.class);
				criteria.setCacheable(true);
				return criteria.list();
			}
		});
		Map<String, String> map = Maps.newHashMap();
		if (list != null && !list.isEmpty()) {
			for (GameColor item : list) {
				map.put(item.getGameName().trim(), item.getGameColor().trim());
			}
		}
		return map;
	}

	@Override
	public void saveGameColor(GameColor gameColor) {
		gameColorDao.save(gameColor);
	}

}
