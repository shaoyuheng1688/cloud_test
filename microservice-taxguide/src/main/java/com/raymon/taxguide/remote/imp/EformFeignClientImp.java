package com.raymon.taxguide.remote.imp;

import com.raymon.frame.vo.EformRecordVo;
import com.raymon.frame.vo.GsonObjResult;
import com.raymon.frame.vo.ManageUnitVo;
import com.raymon.frame.web.exception.ApplicationException;
import com.raymon.taxguide.remote.EFormFeignClient;
import org.springframework.stereotype.Component;

@Component
public class EformFeignClientImp implements EFormFeignClient {

    @Override
    public GsonObjResult<EformRecordVo> findEformRecordByRecordId(String recordId) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<ManageUnitVo> getCitys(String muId) {
        return defalutFallBack();
    }

    private GsonObjResult defalutFallBack(){
        GsonObjResult<EformRecordVo> result =
                new GsonObjResult<EformRecordVo>(GsonObjResult.error,"调用填单服务失败！");
        return result;
    }
}
