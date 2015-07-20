package com.cai310.lottery.entity.lottery.jclq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.PolymorphismType;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqSingleContent;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.SchemeType;
import com.cai310.lottery.support.jclq.TicketItem;
import com.cai310.lottery.support.jclq.TicketItemSingle;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.utils.JsonUtil;

@Entity
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCHEME_TEMP")
public class JclqSchemeTemp extends SchemeTemp {
	
	private static final long serialVersionUID = -7336241912933748922L;

	/** 过关方式 */
	private long passType;

	/** 玩法类型*/
	private PlayType playType;
	
	/** 过关模式*/
	private PassMode passMode;

	/**
	 * 方案类型
	 * 
	 * @see com.cai310.lottery.support.jczq.SchemeType
	 */
	private SchemeType schemeType;


	/** 拆票后的内容 */
	private String ticketContent;

	/**
	 * @return {@link #playType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.jclq.PlayType"),
			@Parameter(name = EnumType.TYPE, value = PlayType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType
	 *            the {@link #playType} to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	
	/**
	 * @return {@link #passType}
	 */
	@Column(nullable = false, updatable = false)
	public long getPassType() {
		return passType;
	}

	/**
	 * @param passType
	 *            the {@link #passType} to set
	 */
	public void setPassType(long passType) {
		this.passType = passType;
	}

	/**
	 * @return {@link #passMode}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.jclq.PassMode"),
			@Parameter(name = EnumType.TYPE, value = PassMode.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PassMode getPassMode() {
		return passMode;
	}

	/**
	 * @param passMode
	 *            the {@link #passMode} to set
	 */
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	/**
	 * @return the schemeType
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.jclq.SchemeType"),
			@Parameter(name = EnumType.TYPE, value = SchemeType.SQL_TYPE) })
	@Column(updatable = false)
	public SchemeType getSchemeType() {
		return schemeType;
	}

	/**
	 * @param schemeType
	 *            the schemeType to set
	 */
	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}
	
	@Lob
	@Column
	public String getTicketContent() {
		return ticketContent;
	}

	public void setTicketContent(String ticketContent) {
		this.ticketContent = ticketContent;
	}

		
	@Transient
	public boolean isCompoundMode() {
		return getMode() == SalesMode.COMPOUND;
	}
	
	/**
	 * @return 获取选择的场次序号集合
	 */
	@Transient
	public List<String> getSelectedMatchKeys() {
		List<String> matchKeys = new ArrayList<String>();
		switch (this.getMode()) {
		case COMPOUND:
			JclqCompoundContent compoundContent = getCompoundContent();
			for (JclqMatchItem item : compoundContent.getItems()) {
				if(!matchKeys.contains(item.getMatchKey())){
					matchKeys.add(item.getMatchKey());
				}				
			}
			break;
		case SINGLE:
			JclqSingleContent singleContent = this.getSingleContent();
			matchKeys = singleContent.getMatchkeys();
			break;
		default:
			throw new RuntimeException("投注方式不合法");
		}
		return matchKeys;
	}
	
	/**
	 * @return 获取选择的场次序号字符串
	 * @return
	 */
	@Transient
	public String getSelectedMatchKeysStr(){
		List<String> matchKeys = this.getSelectedMatchKeys();
		return StringUtil.listToString(matchKeys);
	}
	
	
	@Transient
	public String getContentText() {
		Item[] allItem = getPlayType().getAllItems();

		final String seq = "\r\n";
		final char seq2 = ',';

		StringBuilder sb = new StringBuilder();

		sb.append("玩法类型：").append(getPlayType().getText()).append(seq);
		sb.append("过关模式：").append(getPassMode().getText()).append(seq);

		List<PassType> passTypes = getPassTypeList();
		sb.append("过关方式：");
		for (PassType passType : passTypes) {
			sb.append(passType.getText()).append(seq2);
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(seq);

		if (isCompoundMode()) {//复式方案内容
			JclqCompoundContent content = getCompoundContent();
			if ((content.getDanMinHit() != null && content.getDanMinHit() > 0)
					|| (content.getDanMaxHit() != null && content.getDanMaxHit() > 0)) {
				sb.append("模糊设胆：");
				if (content.getDanMinHit() != null && content.getDanMinHit() > 0)
					sb.append("胆码至少中").append(content.getDanMinHit()).append("场").append(seq2);
				if (content.getDanMaxHit() != null && content.getDanMaxHit() > 0)
					sb.append("胆码至多中").append(content.getDanMaxHit()).append("场").append(seq2);
				sb.deleteCharAt(sb.length() - 1);
				sb.append(seq);
			}
			for (JclqMatchItem matchItem : content.getItems()) {
				if (matchItem.isDan())
					sb.append("[胆]-");
				sb.append("[").append(matchItem.getMatchKey()).append("]：");
				if(PlayType.MIX.equals(getPlayType()))allItem = matchItem.getPlayType().getAllItems();
				for (Item item : allItem) {
					if ((matchItem.getValue() & (1 << item.ordinal())) > 0) {
						sb.append(item.getText()).append(seq2);
					}
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(seq);
			}
		} else {
			JclqSingleContent singleContent = getSingleContent();
			String[] contentArr = singleContent.getContent().split("\r\n");
			List<String> playTypes = singleContent.getPlayTypes();
			
			for (String content : contentArr) {
				String[] ordinalArr = content.split(",");
				for (int i = 0; i < ordinalArr.length; i++) {
					String ordinalStr = ordinalArr[i];
					if (!"#".equals(ordinalStr)) {
						int ordinal = Integer.parseInt(ordinalStr);
						if(this.playType==PlayType.MIX && playTypes!=null){
							allItem = PlayType.valueOfName(playTypes.get(i)).getAllItems();
						}						
						Item item = allItem[ordinal];
						String matchkey_text = JczqUtil.getMatchKeyText(singleContent.getMatchkeys().get(i));
						sb.append("[").append(matchkey_text).append("]").append(item.getText())
								.append(seq2);
					}
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(seq);
			}		
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Transient
	public JclqCompoundContent getCompoundContent() {
		if (StringUtils.isBlank(this.getContent()))
			return null;

		@SuppressWarnings("rawtypes")
		Map classMap = new HashMap();
		classMap.put("items", JclqMatchItem.class);
		return JsonUtil.getObject4JsonString(this.getContent(), JclqCompoundContent.class, classMap);
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public JclqSingleContent getSingleContent() {
		if (StringUtils.isBlank(this.getContent()))
			return null;
		
		@SuppressWarnings("rawtypes")
		Map classMap = new HashMap();
		classMap.put("items", JclqMatchItem.class);
		classMap.put("sps", List.class);
		return JsonUtil.getObject4JsonString(this.getContent(), JclqSingleContent.class, classMap);
	}

	@Transient
	public List<PassType> getPassTypeList() {
		return PassType.getPassTypes(this.getPassType());
	}

	@Transient
	public String getPassTypeText() {
		return PassType.getPassTypeText(this.getPassType());
	}

	@Transient
	public List<TicketItem> getTicketList() throws DataException {
		List<TicketItem> list = new ArrayList<TicketItem>();
		String[] arr = this.getTicketContent().trim().split(TicketItem.ITEM_AND);
		for (String content : arr) {
			list.add(TicketItem.valueOf(content));
		}
		return list;
	}
	
	@Transient
	public List<TicketItemSingle> getSingleTicketList() throws DataException {
		List<TicketItemSingle> list = new ArrayList<TicketItemSingle>();
		String[] arr = this.getTicketContent().trim().split(TicketItem.ITEM_AND);
		for (String content : arr) {
			list.add(TicketItemSingle.valueOf(content));
		}
		return list;
	}

	@Transient
	public int getTicketNum() {
		if (StringUtils.isBlank(this.getTicketContent()))
			return 1;

		return this.getTicketContent().trim().split(TicketItem.ITEM_AND).length;
	}

	@Transient
	public Byte getPlayTypeOrdinal() {
		return (byte) getPlayType().ordinal();
	}
}
