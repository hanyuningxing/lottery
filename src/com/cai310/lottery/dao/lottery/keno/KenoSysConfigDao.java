package com.cai310.lottery.dao.lottery.keno;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.keno.KenoSysConfig;
import com.cai310.orm.hibernate.HibernateDao;

@Repository("kenoSysConfigDao")
public class KenoSysConfigDao extends HibernateDao<KenoSysConfig, String> {

}
