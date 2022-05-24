package com.rongji.egov.messageclient.web.controller;


import com.rongji.egov.messageclient.business.model.DingMessageZjgy;
import com.rongji.egov.messageclient.business.service.DingMessageZjgyMng;
import com.rongji.egov.utils.exception.BusinessException;
import com.rongji.egov.utils.spring.validation.InsertValidate;
import com.rongji.egov.utils.spring.validation.UpdateValidate;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author  hcg
 * @create  
 * @desc
 **/
@RestController
public class DingMessageZjgyController {

    @Resource
    private DingMessageZjgyMng dingMessageZjgyMng;



    /**
     * 添加
     *
     * @param dingMessageZjgy
     * @param bindingResult
     * @return
     */
    @PostMapping("/dingMessageZjgy/insertDingMessageZjgy")
    public DingMessageZjgy insertDingMessageZjgy(@RequestBody @Validated({InsertValidate.class}) DingMessageZjgy dingMessageZjgy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(bindingResult.getFieldError().getDefaultMessage());
        }

        int insertFlag = dingMessageZjgyMng.insertDingMessageZjgy(dingMessageZjgy);

        if (insertFlag!= 1) {
            throw new BusinessException("添加异常");
        }
        return dingMessageZjgyMng.getDingMessageZjgyById(dingMessageZjgy.getId());
    }

    /**
     * 删除多个
     *
     * @param ids
     */
    @PostMapping("/dingMessageZjgy/deleteDingMessageZjgy")
    public void deleteDingMessageZjgy(@RequestBody List<String> ids) {
        int deleteFlag = dingMessageZjgyMng.delDingMessageZjgy(ids);
        if (deleteFlag < 1) {
            throw new BusinessException("删除出错");
        }
    }


    /**
     * 更新
     *
     * @param dingMessageZjgy
     * @param bindingResult
     * @return
     */
    @PostMapping("/dingMessageZjgy/updateDingMessageZjgy")
    public DingMessageZjgy updateDingMessageZjgy(@RequestBody @Validated({UpdateValidate.class}) DingMessageZjgy dingMessageZjgy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(bindingResult.getFieldError().getDefaultMessage());
        }

        if (StringUtils.isBlank(dingMessageZjgy.getId())) {
            throw new BusinessException("更新异常，缺少id");
        }
        int updateFlag = dingMessageZjgyMng.updateDingMessageZjgy(dingMessageZjgy);
        if (updateFlag != 1) {
            throw new BusinessException("更新错误");
        } else {
            return dingMessageZjgyMng.getDingMessageZjgyById(dingMessageZjgy.getId());
        }
    }

    /**
     * 根据model获取list
     * @return
     */
    @GetMapping("/dingMessageZjgy/listDingMessageZjgyByModel")
    public List<DingMessageZjgy> listDingMessageZjgy(DingMessageZjgy dingMessageZjgy){
        return dingMessageZjgyMng.getDingMessageZjgyByModel(dingMessageZjgy);
    }

    @GetMapping("/dingMessageZjgy/getDingMessageZjgyById")
    public DingMessageZjgy getDingMessageZjgyById(String id) {
        return dingMessageZjgyMng.getDingMessageZjgyById(id);
    }


}
