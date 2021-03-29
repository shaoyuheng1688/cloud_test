package com.raymon.taxguide.web.controller;

import com.raymon.frame.web.controller.AbstractController;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.service.QueryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/query")
public class QueryController extends AbstractController {

    @Autowired
    private QueryService queryService;

    @ApiOperation(value = "获取人工导税流转信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordId", value = "填单Id", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideRecord.class) })
    @RequestMapping(value="getManualTaxGuideWorkFlowInfos",method = {RequestMethod.GET,RequestMethod.POST})
    public String getManualTaxGuideWorkFlowInfos(Model model, HttpServletRequest req, String recordId){
        model.addAttribute(queryService.getManualTaxGuideWorkFlowInfos(recordId));
        return this.resolverViewName(req, "getManualTaxGuideWorkFlowInfos");
    }
}
