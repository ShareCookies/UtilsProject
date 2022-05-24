package com.rongji.egov.messageclient.business.business.dao;

import com.rongji.egov.messageclient.business.business.model.DingUser;

public interface DingUserMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(DingUser record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(DingUser record);

    /**
     *
     * @mbggenerated
     */
    DingUser selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DingUser record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DingUser record);
}