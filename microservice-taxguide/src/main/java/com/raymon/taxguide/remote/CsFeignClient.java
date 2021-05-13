package com.raymon.taxguide.remote;

import com.raymon.taxguide.remote.imp.CsFeignClientImp;
import com.raymon.taxguide.pojo.cs.CsServerParams;
import com.raymon.taxguide.vo.CSTicketVo;
import com.raymon.taxguide.vo.GsonObjResult;
import com.raymon.taxguide.vo.PageResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(value = "cs-server", fallback = CsFeignClientImp.class)
public interface CsFeignClient {

    @GetMapping("/cs/putTicketToPool.json")
    GsonObjResult<CSTicketVo> putTicketToPool(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/getNextTicketByAccId.json")
    GsonObjResult<CSTicketVo> getTicketByAccId(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/comfirmDequeueTicket.json")
    GsonObjResult<CSTicketVo> comfirmDequeueTicket(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/getWaitingToDealInfo.json")
    GsonObjResult<Map<String, Integer>> getWaitingToDealInfo(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/getCSTicketByAccId.json")
    GsonObjResult<CSTicketVo> getCSTicketByAccId(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/getWaitCountByCSTicketId.json")
    GsonObjResult<Map<String, Integer>> getWaitCountByCSTicketId(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/getCanDealCstIdsByAccId.json")
    GsonObjResult<PageResultVO<String>> getCanDealCstIdsByAccId(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/searchCanDealTicketsByAccIdAndCstIds.json")
    GsonObjResult<PageResultVO<String>> searchCanDealTicketsByAccIdAndCstIds(@SpringQueryMap CsServerParams csServerParams);

    @GetMapping("/cs/getCSTicketByAccIdAndCstId.json")
    GsonObjResult<CSTicketVo> getCSTicketByAccIdAndCstId(@SpringQueryMap CsServerParams csServerParams);
}
