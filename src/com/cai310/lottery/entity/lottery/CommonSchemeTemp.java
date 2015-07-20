package com.cai310.lottery.entity.lottery;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 
 * @author Administrator
 *此类主要用于泛型查询,无特殊作用
 */

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCHEME_TEMP")
public class CommonSchemeTemp extends SchemeTemp{
	
}
