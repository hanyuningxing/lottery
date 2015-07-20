package com.cai310.lottery.entity.lottery.dczc;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.PolymorphismType;

import com.cai310.lottery.entity.lottery.SchemeTemp;

@Entity
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCHEME_TEMP")
public class DczcSchemeTemp extends SchemeTemp {

}
