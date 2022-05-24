package com.rongji.egov.messageclient.business.service;

import com.rongji.egov.messageclient.business.model.DingMessageZjgy;


import java.util.List;

public interface DingMessageZjgyDao {
    int insertDingMessageZjgy(DingMessageZjgy dingMessageZjgy);

    /**
     * 通过多个id批量删除
     * @param list
     * @return
     */
    int delDingMessageZjgy(List<String> list) ;

    int updateDingMessageZjgy(DingMessageZjgy dingMessageZjgy);

    DingMessageZjgy getDingMessageZjgyById(String id);
    /**
     * 根据域对象查询
     */
    List<DingMessageZjgy> getDingMessageZjgyByModel(DingMessageZjgy dingMessageZjgy);



}
