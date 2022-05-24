package com.rongji.egov.messageclient.business.service.impl;


import com.rongji.egov.messageclient.business.service.DingMessageZjgyMng;
import com.rongji.egov.messageclient.business.dao.DingMessageZjgyDao;

import com.rongji.egov.messageclient.business.model.DingMessageZjgy;


import javax.annotation.Resource;
import java.util.List;
import com.rongji.egov.utils.common.IdUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;
import com.rongji.egov.user.business.model.SecurityUser;
import com.rongji.egov.user.business.util.SecurityUtils;
import com.rongji.egov.user.business.model.UmsUser;
/**
 * @author 
 * @create
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class DingMessageZjgyMngImpl implements DingMessageZjgyMng {

    @Resource
    private DingMessageZjgyDao dao;
    @Override
    public  int insertDingMessageZjgy(DingMessageZjgy dingMessageZjgy){
		if (StringUtils.isBlank(dingMessageZjgy.getId())) {
            dingMessageZjgy.setId(IdUtil.getUID());
        }
		// 初始化默认数据
        SecurityUser securityUser = SecurityUtils.getPrincipal();
        UmsUser umsUser=securityUser.getUmsUser();
        String userNo=securityUser.getUserNo();
		//dingMessageZjgy.setCreateTime(new Date());
		//dingMessageZjgy.setCreateUesr(umsUser.getUserName());
        //dingMessageZjgy.setCreateUesrNo(userNo);
        //dingMessageZjgy.setSystemNo(securityUser.getSystemNo());
        return  this.dao.insertDingMessageZjgy(dingMessageZjgy);
    }

    @Override
    public  int delDingMessageZjgy(List<String> list){
        return  this.dao.delDingMessageZjgy(list);
    }

    @Override
    public  int updateDingMessageZjgy(DingMessageZjgy dingMessageZjgy){
        return  this.dao.updateDingMessageZjgy(dingMessageZjgy);
    }

    @Override
    public  DingMessageZjgy getDingMessageZjgyById(String id){
        return  this.dao.getDingMessageZjgyById(id);
    }

    @Override
    public  List<DingMessageZjgy> getDingMessageZjgyByModel(DingMessageZjgy dingMessageZjgy){
        return  this.dao.getDingMessageZjgyByModel(dingMessageZjgy);
    }


}
