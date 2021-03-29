package com.raymon.taxguide.remote;

import com.raymon.frame.vo.EformRecordVo;
import com.raymon.frame.vo.GsonObjResult;
import com.raymon.frame.vo.ManageUnitVo;
import com.raymon.taxguide.remote.imp.EformFeignClientImp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "eform-server", fallback = EformFeignClientImp.class)
public interface EFormFeignClient {

    @GetMapping("/eform/query/findEformRecordByRecordId.json")
    GsonObjResult<EformRecordVo> findEformRecordByRecordId(@RequestParam("recordId")String recordId);

    @GetMapping("/eform/mu/getCitys.json")
    GsonObjResult<ManageUnitVo> getCitys(@RequestParam("muId")String muId);

}
