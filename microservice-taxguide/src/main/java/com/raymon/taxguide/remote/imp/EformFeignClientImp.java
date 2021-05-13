package com.raymon.taxguide.remote.imp;

import com.raymon.taxguide.remote.EFormFeignClient;
import com.raymon.taxguide.vo.*;
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
