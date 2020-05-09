package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderContactExperienceBussinessBrand implements Serializable {
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderContactExperienceBussinessBrandId;

    private Integer traderContactExperienceId;

    private Integer brandId;

    public Integer getTraderContactExperienceBussinessBrandId() {
        return traderContactExperienceBussinessBrandId;
    }

    public void setTraderContactExperienceBussinessBrandId(Integer traderContactExperienceBussinessBrandId) {
        this.traderContactExperienceBussinessBrandId = traderContactExperienceBussinessBrandId;
    }

    public Integer getTraderContactExperienceId() {
        return traderContactExperienceId;
    }

    public void setTraderContactExperienceId(Integer traderContactExperienceId) {
        this.traderContactExperienceId = traderContactExperienceId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}