package com.vedeng.trader.model.vo;

import com.vedeng.trader.model.TraderContact;

/**
 * <b>Description:</b><br>
 * 客户联系人vo
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.trader.model.vo <br>
 *       <b>ClassName:</b> TraderContactVo <br>
 *       <b>Date:</b> 2017年5月23日 上午9:35:57
 */
public class TraderContactVo extends TraderContact {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String account;// 注册账号

	private String educationName;// 学历

	private String characterName;// 性格

	/**
	 * 是否注册过贝登商城，-1：没有注册，0：注册过，但不是贝登商城会员，1：注册过，是贝登商城会员(已废弃)
	 */
	private Integer isVedengJx;

	private Integer isVedengMember;//是否是贝登商城会员(新)

	public Integer getIsVedengMember() {
		return isVedengMember;
	}

	public void setIsVedengMember(Integer isVedengMember) {
		this.isVedengMember = isVedengMember;
	}

	public Integer getIsVedengJx() {
		return isVedengJx;
	}

	public void setIsVedengJx(Integer isVedengJx) {
		this.isVedengJx = isVedengJx;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

}
