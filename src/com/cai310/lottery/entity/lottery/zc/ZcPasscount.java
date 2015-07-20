package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.cai310.lottery.entity.lottery.SchemePasscount;

/**
 * 足彩方案过关信息抽象类.
 * 
 */
@MappedSuperclass
public abstract class ZcPasscount extends SchemePasscount {

	private static final long serialVersionUID = 4272390436858509156L;

	private int lost0;
	private int lost1;
	private int lost2;
	private int lost3;
	private Integer passcount;

	@Column(name = "lost0")
	public int getLost0() {
		return lost0;
	}

	public void setLost0(int lost0) {
		this.lost0 = lost0;
	}

	@Column(name = "lost1")
	public int getLost1() {
		return lost1;
	}

	public void setLost1(int lost1) {
		this.lost1 = lost1;
	}

	@Column(name = "lost2")
	public int getLost2() {
		return lost2;
	}

	public void setLost2(int lost2) {
		this.lost2 = lost2;
	}

	@Column(name = "lost3")
	public int getLost3() {
		return lost3;
	}

	public void setLost3(int lost3) {
		this.lost3 = lost3;
	}

	/**
	 * @return the passcount
	 */
	@Column(name = "passcount")
	public Integer getPasscount() {
		return passcount;
	}

	/**
	 * @param passcount the passcount to set
	 */
	public void setPasscount(Integer passcount) {
		this.passcount = passcount;
	}

	 
	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		return this.getLost0() > 0 ? true : false;
	}

	 

}
