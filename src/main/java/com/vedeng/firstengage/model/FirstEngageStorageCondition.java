package com.vedeng.firstengage.model;

public class FirstEngageStorageCondition {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_STORAGE_CONDITION_ID
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    private Integer firstEngageStorageConditionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_ID
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    private Integer firstEngageId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_FIRST_ENGAGE_STORAGE_CONDITION.NAME
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_STORAGE_CONDITION_ID
     *
     * @return the value of T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_STORAGE_CONDITION_ID
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    public Integer getFirstEngageStorageConditionId() {
        return firstEngageStorageConditionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_STORAGE_CONDITION_ID
     *
     * @param firstEngageStorageConditionId the value for T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_STORAGE_CONDITION_ID
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    public void setFirstEngageStorageConditionId(Integer firstEngageStorageConditionId) {
        this.firstEngageStorageConditionId = firstEngageStorageConditionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_ID
     *
     * @return the value of T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_ID
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    public Integer getFirstEngageId() {
        return firstEngageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_ID
     *
     * @param firstEngageId the value for T_FIRST_ENGAGE_STORAGE_CONDITION.FIRST_ENGAGE_ID
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    public void setFirstEngageId(Integer firstEngageId) {
        this.firstEngageId = firstEngageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_FIRST_ENGAGE_STORAGE_CONDITION.NAME
     *
     * @return the value of T_FIRST_ENGAGE_STORAGE_CONDITION.NAME
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_FIRST_ENGAGE_STORAGE_CONDITION.NAME
     *
     * @param name the value for T_FIRST_ENGAGE_STORAGE_CONDITION.NAME
     *
     * @mbg.generated Wed Mar 20 18:33:10 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}