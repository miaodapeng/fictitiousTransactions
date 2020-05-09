package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderContactExperienceBussinessArea implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderContactExperienceBussinessAreaId;

    private Integer traderContactExperienceId;

    private Integer areaId;

    private String areaIds;

	public Integer getTraderContactExperienceBussinessAreaId() {
        return traderContactExperienceBussinessAreaId;
    }

    public void setTraderContactExperienceBussinessAreaId(Integer traderContactExperienceBussinessAreaId) {
        this.traderContactExperienceBussinessAreaId = traderContactExperienceBussinessAreaId;
    }

    public Integer getTraderContactExperienceId() {
        return traderContactExperienceId;
    }

    public void setTraderContactExperienceId(Integer traderContactExperienceId) {
        this.traderContactExperienceId = traderContactExperienceId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds == null ? null : areaIds.trim();
    }
}