package com.rongji.egov.messageclient.business.mapper;

import com.rongji.egov.messageclient.business.model.DingMessageZjgy;
import com.rongji.egov.utils.api.paging.Page;
import com.rongji.egov.utils.api.paging.PagingRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DingMessageZjgyMapper {
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
