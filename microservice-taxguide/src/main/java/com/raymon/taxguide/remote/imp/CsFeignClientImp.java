package com.raymon.taxguide.remote.imp;

import com.raymon.frame.vo.CSTicketVo;
import com.raymon.frame.vo.EformRecordVo;
import com.raymon.frame.vo.GsonObjResult;
import com.raymon.frame.vo.PageResultVO;
import com.raymon.frame.web.exception.ApplicationException;
import com.raymon.taxguide.remote.CsFeignClient;
import com.raymon.taxguide.pojo.cs.CsServerParams;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CsFeignClientImp implements CsFeignClient {

    @Override
    public GsonObjResult<CSTicketVo> putTicketToPool(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<CSTicketVo> getTicketByAccId(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<CSTicketVo> comfirmDequeueTicket(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<Map<String, Integer>> getWaitingToDealInfo(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<CSTicketVo> getCSTicketByAccId(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<Map<String, Integer>> getWaitCountByCSTicketId(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<PageResultVO<String>> getCanDealCstIdsByAccId(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<PageResultVO<String>> searchCanDealTicketsByAccIdAndCstIds(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    @Override
    public GsonObjResult<CSTicketVo> getCSTicketByAccIdAndCstId(CsServerParams csServerParams) {
        return defalutFallBack();
    }

    private GsonObjResult defalutFallBack(){
        GsonObjResult<EformRecordVo> result =
                new GsonObjResult<EformRecordVo>(GsonObjResult.error,"调用CS服务失败！");
        return result;
    }
}
