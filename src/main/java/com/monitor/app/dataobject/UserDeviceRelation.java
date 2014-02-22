package com.monitor.app.dataobject;

import java.util.Date;

public class UserDeviceRelation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cr_user_device_relation.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cr_user_device_relation.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cr_user_device_relation.site_id
     *
     * @mbggenerated
     */
    private Long siteId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cr_user_device_relation.device_id
     *
     * @mbggenerated
     */
    private Long deviceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cr_user_device_relation.device_name
     *
     * @mbggenerated
     */
    private String deviceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cr_user_device_relation.gmt_create
     *
     * @mbggenerated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cr_user_device_relation.gmt_modify
     *
     * @mbggenerated
     */
    private Date gmtModify;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cr_user_device_relation.id
     *
     * @return the value of cr_user_device_relation.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cr_user_device_relation.id
     *
     * @param id the value for cr_user_device_relation.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cr_user_device_relation.user_id
     *
     * @return the value of cr_user_device_relation.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cr_user_device_relation.user_id
     *
     * @param userId the value for cr_user_device_relation.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cr_user_device_relation.site_id
     *
     * @return the value of cr_user_device_relation.site_id
     *
     * @mbggenerated
     */
    public Long getSiteId() {
        return siteId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cr_user_device_relation.site_id
     *
     * @param siteId the value for cr_user_device_relation.site_id
     *
     * @mbggenerated
     */
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cr_user_device_relation.device_id
     *
     * @return the value of cr_user_device_relation.device_id
     *
     * @mbggenerated
     */
    public Long getDeviceId() {
        return deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cr_user_device_relation.device_id
     *
     * @param deviceId the value for cr_user_device_relation.device_id
     *
     * @mbggenerated
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cr_user_device_relation.device_name
     *
     * @return the value of cr_user_device_relation.device_name
     *
     * @mbggenerated
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cr_user_device_relation.device_name
     *
     * @param deviceName the value for cr_user_device_relation.device_name
     *
     * @mbggenerated
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cr_user_device_relation.gmt_create
     *
     * @return the value of cr_user_device_relation.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cr_user_device_relation.gmt_create
     *
     * @param gmtCreate the value for cr_user_device_relation.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cr_user_device_relation.gmt_modify
     *
     * @return the value of cr_user_device_relation.gmt_modify
     *
     * @mbggenerated
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cr_user_device_relation.gmt_modify
     *
     * @param gmtModify the value for cr_user_device_relation.gmt_modify
     *
     * @mbggenerated
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}