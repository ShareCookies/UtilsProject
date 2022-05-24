package com.rongji.egov.urger.model.client.business.dao;

import com.rongji.egov.urger.model.client.business.model.UrgerUrgeRecord;

public interface UrgerUrgeRecordMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbggenerated
     */
    int insert(UrgerUrgeRecord record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UrgerUrgeRecord record);

    /**
     *
     * @mbggenerated
     */
    UrgerUrgeRecord selectByPrimaryKey(String id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UrgerUrgeRecord record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UrgerUrgeRecord record);
}