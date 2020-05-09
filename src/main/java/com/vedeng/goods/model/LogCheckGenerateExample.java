package com.vedeng.goods.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogCheckGenerateExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public LogCheckGenerateExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andLogIdIsNull() {
            addCriterion("LOG_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogIdIsNotNull() {
            addCriterion("LOG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogIdEqualTo(Integer value) {
            addCriterion("LOG_ID =", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotEqualTo(Integer value) {
            addCriterion("LOG_ID <>", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThan(Integer value) {
            addCriterion("LOG_ID >", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOG_ID >=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThan(Integer value) {
            addCriterion("LOG_ID <", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThanOrEqualTo(Integer value) {
            addCriterion("LOG_ID <=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdIn(List<Integer> values) {
            addCriterion("LOG_ID in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotIn(List<Integer> values) {
            addCriterion("LOG_ID not in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdBetween(Integer value1, Integer value2) {
            addCriterion("LOG_ID between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotBetween(Integer value1, Integer value2) {
            addCriterion("LOG_ID not between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andLogTypeIsNull() {
            addCriterion("LOG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLogTypeIsNotNull() {
            addCriterion("LOG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLogTypeEqualTo(Integer value) {
            addCriterion("LOG_TYPE =", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotEqualTo(Integer value) {
            addCriterion("LOG_TYPE <>", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThan(Integer value) {
            addCriterion("LOG_TYPE >", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOG_TYPE >=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThan(Integer value) {
            addCriterion("LOG_TYPE <", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThanOrEqualTo(Integer value) {
            addCriterion("LOG_TYPE <=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeIn(List<Integer> values) {
            addCriterion("LOG_TYPE in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotIn(List<Integer> values) {
            addCriterion("LOG_TYPE not in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeBetween(Integer value1, Integer value2) {
            addCriterion("LOG_TYPE between", value1, value2, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("LOG_TYPE not between", value1, value2, "logType");
            return (Criteria) this;
        }

        public Criteria andLogBizIdIsNull() {
            addCriterion("LOG_BIZ_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogBizIdIsNotNull() {
            addCriterion("LOG_BIZ_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogBizIdEqualTo(Integer value) {
            addCriterion("LOG_BIZ_ID =", value, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdNotEqualTo(Integer value) {
            addCriterion("LOG_BIZ_ID <>", value, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdGreaterThan(Integer value) {
            addCriterion("LOG_BIZ_ID >", value, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOG_BIZ_ID >=", value, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdLessThan(Integer value) {
            addCriterion("LOG_BIZ_ID <", value, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdLessThanOrEqualTo(Integer value) {
            addCriterion("LOG_BIZ_ID <=", value, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdIn(List<Integer> values) {
            addCriterion("LOG_BIZ_ID in", values, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdNotIn(List<Integer> values) {
            addCriterion("LOG_BIZ_ID not in", values, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdBetween(Integer value1, Integer value2) {
            addCriterion("LOG_BIZ_ID between", value1, value2, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogBizIdNotBetween(Integer value1, Integer value2) {
            addCriterion("LOG_BIZ_ID not between", value1, value2, "logBizId");
            return (Criteria) this;
        }

        public Criteria andLogStatusIsNull() {
            addCriterion("LOG_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andLogStatusIsNotNull() {
            addCriterion("LOG_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andLogStatusEqualTo(Integer value) {
            addCriterion("LOG_STATUS =", value, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusNotEqualTo(Integer value) {
            addCriterion("LOG_STATUS <>", value, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusGreaterThan(Integer value) {
            addCriterion("LOG_STATUS >", value, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOG_STATUS >=", value, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusLessThan(Integer value) {
            addCriterion("LOG_STATUS <", value, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusLessThanOrEqualTo(Integer value) {
            addCriterion("LOG_STATUS <=", value, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusIn(List<Integer> values) {
            addCriterion("LOG_STATUS in", values, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusNotIn(List<Integer> values) {
            addCriterion("LOG_STATUS not in", values, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusBetween(Integer value1, Integer value2) {
            addCriterion("LOG_STATUS between", value1, value2, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("LOG_STATUS not between", value1, value2, "logStatus");
            return (Criteria) this;
        }

        public Criteria andLogMessageIsNull() {
            addCriterion("LOG_MESSAGE is null");
            return (Criteria) this;
        }

        public Criteria andLogMessageIsNotNull() {
            addCriterion("LOG_MESSAGE is not null");
            return (Criteria) this;
        }

        public Criteria andLogMessageEqualTo(String value) {
            addCriterion("LOG_MESSAGE =", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageNotEqualTo(String value) {
            addCriterion("LOG_MESSAGE <>", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageGreaterThan(String value) {
            addCriterion("LOG_MESSAGE >", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_MESSAGE >=", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageLessThan(String value) {
            addCriterion("LOG_MESSAGE <", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageLessThanOrEqualTo(String value) {
            addCriterion("LOG_MESSAGE <=", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageLike(String value) {
            addCriterion("LOG_MESSAGE like", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageNotLike(String value) {
            addCriterion("LOG_MESSAGE not like", value, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageIn(List<String> values) {
            addCriterion("LOG_MESSAGE in", values, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageNotIn(List<String> values) {
            addCriterion("LOG_MESSAGE not in", values, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageBetween(String value1, String value2) {
            addCriterion("LOG_MESSAGE between", value1, value2, "logMessage");
            return (Criteria) this;
        }

        public Criteria andLogMessageNotBetween(String value1, String value2) {
            addCriterion("LOG_MESSAGE not between", value1, value2, "logMessage");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("ADD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("ADD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("ADD_TIME =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("ADD_TIME <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("ADD_TIME >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ADD_TIME >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("ADD_TIME <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("ADD_TIME <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("ADD_TIME in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("ADD_TIME not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("ADD_TIME between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("ADD_TIME not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("CREATOR is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("CREATOR is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(Integer value) {
            addCriterion("CREATOR =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(Integer value) {
            addCriterion("CREATOR <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(Integer value) {
            addCriterion("CREATOR >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("CREATOR >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(Integer value) {
            addCriterion("CREATOR <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(Integer value) {
            addCriterion("CREATOR <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<Integer> values) {
            addCriterion("CREATOR in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<Integer> values) {
            addCriterion("CREATOR not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(Integer value1, Integer value2) {
            addCriterion("CREATOR between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(Integer value1, Integer value2) {
            addCriterion("CREATOR not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNameIsNull() {
            addCriterion("CREATOR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCreatorNameIsNotNull() {
            addCriterion("CREATOR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorNameEqualTo(String value) {
            addCriterion("CREATOR_NAME =", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameNotEqualTo(String value) {
            addCriterion("CREATOR_NAME <>", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameGreaterThan(String value) {
            addCriterion("CREATOR_NAME >", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATOR_NAME >=", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameLessThan(String value) {
            addCriterion("CREATOR_NAME <", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameLessThanOrEqualTo(String value) {
            addCriterion("CREATOR_NAME <=", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameLike(String value) {
            addCriterion("CREATOR_NAME like", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameNotLike(String value) {
            addCriterion("CREATOR_NAME not like", value, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameIn(List<String> values) {
            addCriterion("CREATOR_NAME in", values, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameNotIn(List<String> values) {
            addCriterion("CREATOR_NAME not in", values, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameBetween(String value1, String value2) {
            addCriterion("CREATOR_NAME between", value1, value2, "creatorName");
            return (Criteria) this;
        }

        public Criteria andCreatorNameNotBetween(String value1, String value2) {
            addCriterion("CREATOR_NAME not between", value1, value2, "creatorName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameIsNull() {
            addCriterion("LOG_STATUS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameIsNotNull() {
            addCriterion("LOG_STATUS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameEqualTo(String value) {
            addCriterion("LOG_STATUS_NAME =", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameNotEqualTo(String value) {
            addCriterion("LOG_STATUS_NAME <>", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameGreaterThan(String value) {
            addCriterion("LOG_STATUS_NAME >", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_STATUS_NAME >=", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameLessThan(String value) {
            addCriterion("LOG_STATUS_NAME <", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameLessThanOrEqualTo(String value) {
            addCriterion("LOG_STATUS_NAME <=", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameLike(String value) {
            addCriterion("LOG_STATUS_NAME like", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameNotLike(String value) {
            addCriterion("LOG_STATUS_NAME not like", value, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameIn(List<String> values) {
            addCriterion("LOG_STATUS_NAME in", values, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameNotIn(List<String> values) {
            addCriterion("LOG_STATUS_NAME not in", values, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameBetween(String value1, String value2) {
            addCriterion("LOG_STATUS_NAME between", value1, value2, "logStatusName");
            return (Criteria) this;
        }

        public Criteria andLogStatusNameNotBetween(String value1, String value2) {
            addCriterion("LOG_STATUS_NAME not between", value1, value2, "logStatusName");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated do_not_delete_during_merge Fri May 31 09:38:39 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table V_LOG_CHECK
     *
     * @mbggenerated Fri May 31 09:38:39 CST 2019
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}