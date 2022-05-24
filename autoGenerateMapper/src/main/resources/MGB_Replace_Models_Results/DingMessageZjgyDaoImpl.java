package com.rongji.egov.messageclient.business.dao.impl;


import com.rongji.egov.messageclient.business.dao.DingMessageZjgyDao;
import com.rongji.egov.messageclient.business.mapper.DingMessageZjgyMapper;

import com.rongji.egov.messageclient.business.model.DingMessageZjgy;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 
 * @create
 */
@Repository
public class DingMessageZjgyDaoImpl implements DingMessageZjgyDao {

    @Resource
    private DingMessageZjgyMapper mapper;
    @Override
    public  int insertDingMessageZjgy(DingMessageZjgy dingMessageZjgy){
        return  this.mapper.insertDingMessageZjgy(dingMessageZjgy);
    }

    @Override
    public  int delDingMessageZjgy(List<String> list){
        return  this.mapper.delDingMessageZjgy(list);
    }

    @Override
    public  int updateDingMessageZjgy(DingMessageZjgy dingMessageZjgy){
        return  this.mapper.updateDingMessageZjgy(dingMessageZjgy);
    }

    @Override
    public  DingMessageZjgy getDingMessageZjgyById(String id){
        return  this.mapper.getDingMessageZjgyById(id);
    }

    @Override
    public  List<DingMessageZjgy> getDingMessageZjgyByModel(DingMessageZjgy dingMessageZjgy){
        return  this.mapper.getDingMessageZjgyByModel(dingMessageZjgy);
    }

}
