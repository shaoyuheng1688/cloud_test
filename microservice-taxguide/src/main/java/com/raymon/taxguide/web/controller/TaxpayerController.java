package com.raymon.taxguide.web.controller;


import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.service.TaxpayerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/taxpayer")
public class TaxpayerController extends AbstractController {

    @Autowired
    private TaxpayerService taxpayerService;

    @ApiOperation(value = "发起人工导税")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bussId", value = "业务ID：这里指审核不通过的填单ID", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideRecord.class) })
    @RequestMapping(value="getTask",method = {RequestMethod.GET,RequestMethod.POST})
    public String getTaxguideTask(Model model, HttpServletRequest req,String bussId){
        model.addAttribute("Data",taxpayerService.getTaxguideTask(bussId));
        return this.resolverViewName(req, "getTaxguideTask");
    }

    @ApiOperation(value = "纳税人收到呼叫提示，告诉后台纳税人已经就绪")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bussId", value = "业务ID：这里指审核不通过的填单ID", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideRecord.class) })
    @RequestMapping(value="ready",method = {RequestMethod.GET,RequestMethod.POST})
    public String ready(Model model, HttpServletRequest req,String bussId){
        model.addAttribute("Data",taxpayerService.ready(bussId));
        return this.resolverViewName(req, "ready");
    }

    @ApiOperation(value = "获取最新导税状态信息（纳税人轮询接口）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tgId", value = "导税记录主键", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideRecord.class) })
    @RequestMapping(value="getState",method = {RequestMethod.GET,RequestMethod.POST})
    public String getState(Model model, HttpServletRequest req,String tgId){
        model.addAttribute("Data",taxpayerService.getState(tgId));
        return this.resolverViewName(req, "getState");
    }

    @ApiOperation(value = "纳税人提交反馈意见接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tgInfoId", value = "导税记录附录主键", required = true, dataType = "long", paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideRecord.class) })
    @RequestMapping(value="feedback",method = {RequestMethod.GET,RequestMethod.POST})
    public String feedback(Model model, HttpServletRequest req,long tgInfoId,String remark){
        taxpayerService.feedback(tgInfoId,remark);
        return this.resolverViewName(req, "Feedback");
    }
}
