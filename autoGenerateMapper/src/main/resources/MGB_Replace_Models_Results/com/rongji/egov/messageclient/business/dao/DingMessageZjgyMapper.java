package com.rongji.egov.messageclient.business.dao;

import com.rongji.egov.messageclient.business.model.DingMessageZjgy;

public interface DingMessageZjgyMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(DingMessageZjgy record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(DingMessageZjgy record);

    /**
     *
     * @mbggenerated
     */
    DingMessageZjgy selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DingMessageZjgy record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DingMessageZjgy record);
}