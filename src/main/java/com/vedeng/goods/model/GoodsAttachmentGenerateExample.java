package com.vedeng.goods.model;

import java.util.ArrayList;
import java.util.List;

public class GoodsAttachmentGenerateExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public GoodsAttachmentGenerateExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
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
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
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

        public Criteria andGoodsAttachmentIdIsNull() {
            addCriterion("GOODS_ATTACHMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdIsNotNull() {
            addCriterion("GOODS_ATTACHMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdEqualTo(Integer value) {
            addCriterion("GOODS_ATTACHMENT_ID =", value, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdNotEqualTo(Integer value) {
            addCriterion("GOODS_ATTACHMENT_ID <>", value, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdGreaterThan(Integer value) {
            addCriterion("GOODS_ATTACHMENT_ID >", value, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("GOODS_ATTACHMENT_ID >=", value, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdLessThan(Integer value) {
            addCriterion("GOODS_ATTACHMENT_ID <", value, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("GOODS_ATTACHMENT_ID <=", value, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdIn(List<Integer> values) {
            addCriterion("GOODS_ATTACHMENT_ID in", values, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdNotIn(List<Integer> values) {
            addCriterion("GOODS_ATTACHMENT_ID not in", values, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdBetween(Integer value1, Integer value2) {
            addCriterion("GOODS_ATTACHMENT_ID between", value1, value2, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsAttachmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("GOODS_ATTACHMENT_ID not between", value1, value2, "goodsAttachmentId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNull() {
            addCriterion("GOODS_ID is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("GOODS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Integer value) {
            addCriterion("GOODS_ID =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Integer value) {
            addCriterion("GOODS_ID <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Integer value) {
            addCriterion("GOODS_ID >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("GOODS_ID >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Integer value) {
            addCriterion("GOODS_ID <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("GOODS_ID <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Integer> values) {
            addCriterion("GOODS_ID in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Integer> values) {
            addCriterion("GOODS_ID not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("GOODS_ID between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("GOODS_ID not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeIsNull() {
            addCriterion("ATTACHMENT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeIsNotNull() {
            addCriterion("ATTACHMENT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeEqualTo(Integer value) {
            addCriterion("ATTACHMENT_TYPE =", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeNotEqualTo(Integer value) {
            addCriterion("ATTACHMENT_TYPE <>", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeGreaterThan(Integer value) {
            addCriterion("ATTACHMENT_TYPE >", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ATTACHMENT_TYPE >=", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeLessThan(Integer value) {
            addCriterion("ATTACHMENT_TYPE <", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ATTACHMENT_TYPE <=", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeIn(List<Integer> values) {
            addCriterion("ATTACHMENT_TYPE in", values, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeNotIn(List<Integer> values) {
            addCriterion("ATTACHMENT_TYPE not in", values, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeBetween(Integer value1, Integer value2) {
            addCriterion("ATTACHMENT_TYPE between", value1, value2, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ATTACHMENT_TYPE not between", value1, value2, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andDomainIsNull() {
            addCriterion("`DOMAIN` is null");
            return (Criteria) this;
        }

        public Criteria andDomainIsNotNull() {
            addCriterion("`DOMAIN` is not null");
            return (Criteria) this;
        }

        public Criteria andDomainEqualTo(String value) {
            addCriterion("`DOMAIN` =", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotEqualTo(String value) {
            addCriterion("`DOMAIN` <>", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThan(String value) {
            addCriterion("`DOMAIN` >", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThanOrEqualTo(String value) {
            addCriterion("`DOMAIN` >=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThan(String value) {
            addCriterion("`DOMAIN` <", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThanOrEqualTo(String value) {
            addCriterion("`DOMAIN` <=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLike(String value) {
            addCriterion("`DOMAIN` like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotLike(String value) {
            addCriterion("`DOMAIN` not like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainIn(List<String> values) {
            addCriterion("`DOMAIN` in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotIn(List<String> values) {
            addCriterion("`DOMAIN` not in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainBetween(String value1, String value2) {
            addCriterion("`DOMAIN` between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotBetween(String value1, String value2) {
            addCriterion("`DOMAIN` not between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andUriIsNull() {
            addCriterion("URI is null");
            return (Criteria) this;
        }

        public Criteria andUriIsNotNull() {
            addCriterion("URI is not null");
            return (Criteria) this;
        }

        public Criteria andUriEqualTo(String value) {
            addCriterion("URI =", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotEqualTo(String value) {
            addCriterion("URI <>", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThan(String value) {
            addCriterion("URI >", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThanOrEqualTo(String value) {
            addCriterion("URI >=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThan(String value) {
            addCriterion("URI <", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThanOrEqualTo(String value) {
            addCriterion("URI <=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLike(String value) {
            addCriterion("URI like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotLike(String value) {
            addCriterion("URI not like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriIn(List<String> values) {
            addCriterion("URI in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotIn(List<String> values) {
            addCriterion("URI not in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriBetween(String value1, String value2) {
            addCriterion("URI between", value1, value2, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotBetween(String value1, String value2) {
            addCriterion("URI not between", value1, value2, "uri");
            return (Criteria) this;
        }

        public Criteria andAltIsNull() {
            addCriterion("ALT is null");
            return (Criteria) this;
        }

        public Criteria andAltIsNotNull() {
            addCriterion("ALT is not null");
            return (Criteria) this;
        }

        public Criteria andAltEqualTo(String value) {
            addCriterion("ALT =", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltNotEqualTo(String value) {
            addCriterion("ALT <>", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltGreaterThan(String value) {
            addCriterion("ALT >", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltGreaterThanOrEqualTo(String value) {
            addCriterion("ALT >=", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltLessThan(String value) {
            addCriterion("ALT <", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltLessThanOrEqualTo(String value) {
            addCriterion("ALT <=", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltLike(String value) {
            addCriterion("ALT like", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltNotLike(String value) {
            addCriterion("ALT not like", value, "alt");
            return (Criteria) this;
        }

        public Criteria andAltIn(List<String> values) {
            addCriterion("ALT in", values, "alt");
            return (Criteria) this;
        }

        public Criteria andAltNotIn(List<String> values) {
            addCriterion("ALT not in", values, "alt");
            return (Criteria) this;
        }

        public Criteria andAltBetween(String value1, String value2) {
            addCriterion("ALT between", value1, value2, "alt");
            return (Criteria) this;
        }

        public Criteria andAltNotBetween(String value1, String value2) {
            addCriterion("ALT not between", value1, value2, "alt");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("SORT =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("SORT <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("SORT >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("SORT >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("SORT <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("SORT <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("SORT in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("SORT not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("SORT not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andIsDefaultIsNull() {
            addCriterion("IS_DEFAULT is null");
            return (Criteria) this;
        }

        public Criteria andIsDefaultIsNotNull() {
            addCriterion("IS_DEFAULT is not null");
            return (Criteria) this;
        }

        public Criteria andIsDefaultEqualTo(Integer value) {
            addCriterion("IS_DEFAULT =", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultNotEqualTo(Integer value) {
            addCriterion("IS_DEFAULT <>", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultGreaterThan(Integer value) {
            addCriterion("IS_DEFAULT >", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_DEFAULT >=", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultLessThan(Integer value) {
            addCriterion("IS_DEFAULT <", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultLessThanOrEqualTo(Integer value) {
            addCriterion("IS_DEFAULT <=", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultIn(List<Integer> values) {
            addCriterion("IS_DEFAULT in", values, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultNotIn(List<Integer> values) {
            addCriterion("IS_DEFAULT not in", values, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultBetween(Integer value1, Integer value2) {
            addCriterion("IS_DEFAULT between", value1, value2, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_DEFAULT not between", value1, value2, "isDefault");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`STATUS` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`STATUS` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`STATUS` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`STATUS` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`STATUS` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`STATUS` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`STATUS` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`STATUS` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`STATUS` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`STATUS` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`STATUS` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`STATUS` not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated do_not_delete_during_merge Thu Jun 06 14:44:30 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table T_GOODS_ATTACHMENT
     *
     * @mbggenerated Thu Jun 06 14:44:30 CST 2019
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