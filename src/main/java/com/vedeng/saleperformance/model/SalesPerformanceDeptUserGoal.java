package com.vedeng.saleperformance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>Description:</b><br>
 *
 *  关系表
 * @param :
 * @return :
 * @Note <b>Author:</b> Bert <br>
 * <b>Date:</b> 2019/2/22 11:42
 */
public class SalesPerformanceDeptUserGoal implements Serializable {
    private static final long serialVersionUID = -3210327104113360810L;
    /**   SALES_PERFORMANCE_DEPT_USER_GOAL_ID **/
    private Integer salesPerformanceDeptUserGoalId;

    /**   SALES_PERFORMANCE_DEPT_USER_ID **/
    private Integer salesPerformanceDeptUserId;

    /** 统计节点的时间(年月)，日都是1号  YEAR_MONTH **/
    private Date yearMonth;

    /** 目标额(万元)  GOAL **/
    private BigDecimal goal;

    /**     SALES_PERFORMANCE_DEPT_USER_GOAL_ID   **/
    public Integer getSalesPerformanceDeptUserGoalId() {
        return salesPerformanceDeptUserGoalId;
    }

    /**     SALES_PERFORMANCE_DEPT_USER_GOAL_ID   **/
    public void setSalesPerformanceDeptUserGoalId(Integer salesPerformanceDeptUserGoalId) {
        this.salesPerformanceDeptUserGoalId = salesPerformanceDeptUserGoalId;
    }

    /**     SALES_PERFORMANCE_DEPT_USER_ID   **/
    public Integer getSalesPerformanceDeptUserId() {
        return salesPerformanceDeptUserId;
    }

    /**     SALES_PERFORMANCE_DEPT_USER_ID   **/
    public void setSalesPerformanceDeptUserId(Integer salesPerformanceDeptUserId) {
        this.salesPerformanceDeptUserId = salesPerformanceDeptUserId;
    }

    /**   统计节点的时间(年月)，日都是1号  YEAR_MONTH   **/
    public Date getYearMonth() {
        return yearMonth;
    }

    /**   统计节点的时间(年月)，日都是1号  YEAR_MONTH   **/
    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }

    /**   目标额(万元)  GOAL   **/
    public BigDecimal getGoal() {
        return goal;
    }

    /**   目标额(万元)  GOAL   **/
    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }
}