package com.cai310.lottery.entity.info;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 赔率信息类
 * 
 */
@MappedSuperclass
public class OddsData implements Serializable {

	private static final long serialVersionUID = -1949293792466420941L;

	private float assia;

	private float europWin;

	private float europDraw;
	/**/
	private float europLost;

	@Column
	public float getAssia() {
		return assia;
	}

	public void setAssia(float assia) {
		this.assia = assia;
	}

	@Column
	public float getEuropWin() {
		return europWin;
	}

	public void setEuropWin(float europWin) {
		this.europWin = europWin;
	}

	@Column
	public float getEuropDraw() {
		return europDraw;
	}

	public void setEuropDraw(float europDraw) {
		this.europDraw = europDraw;
	}

	@Column
	public float getEuropLost() {
		return europLost;
	}

	public void setEuropLost(float europLost) {
		this.europLost = europLost;
	}

}
