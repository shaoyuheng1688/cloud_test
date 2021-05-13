package com.raymon.taxguide.web.controller;


import com.raymon.taxguide.action.imp.*;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;
import com.raymon.taxguide.pojo.TaxguideClientInfo;
import com.raymon.taxguide.service.TaxmanService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/taxman")
public class TaxmanController extends AbstractController {
//
//    @Autowired
//    private TaxmanService taxmanService;
//
//    @ApiOperation(value = "导税员呼叫纳税人接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accId", value = "导税员Id", required = true, dataType = "String", paramType = "query")
//    })
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideClientInfo.class) })
//    @RequestMapping(value="call",method = {RequestMethod.GET,RequestMethod.POST})
//    public String call(Model model, HttpServletRequest req, String accId){
//        LogTaxmanInfo lti = taxmanService.getUserLogTaxmanInfo(accId);
//        taxmanService.action(lti,new CallActionEvent(lti));
//        model.addAttribute("Data",taxmanService.taxguideClientInfoExtract(lti));
//        return this.resolverViewName(req, "call");
//    }
//
//    @ApiOperation(value = "导税员开始办理接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accId", value = "导税员Id", required = true, dataType = "String", paramType = "query")
//    })
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideClientInfo.class) })
//    @RequestMapping(value="deal",method = {RequestMethod.GET,RequestMethod.POST})
//    public String deal(Model model, HttpServletRequest req, String accId){
//        LogTaxmanInfo lti = taxmanService.getUserLogTaxmanInfo(accId);
//        taxmanService.action(lti,new DealActionEvent(lti));
//        model.addAttribute("Data",taxmanService.taxguideClientInfoExtract(lti));
//        return this.resolverViewName(req, "deal");
//    }
//
//    @ApiOperation(value = "导税员办理完成接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accId", value = "导税员Id", required = true, dataType = "String", paramType = "query")
//    })
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideClientInfo.class) })
//    @RequestMapping(value="finish",method = {RequestMethod.GET,RequestMethod.POST})
//    public String finish(Model model, HttpServletRequest req, String accId){
//        LogTaxmanInfo lti = taxmanService.getUserLogTaxmanInfo(accId);
//        taxmanService.action(lti,new FinishActionEvent(lti));
//        model.addAttribute("Data",taxmanService.taxguideClientInfoExtract(lti));
//        return this.resolverViewName(req, "finish");
//    }
//
//    @ApiOperation(value = "导税员弃号接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accId", value = "导税员Id", required = true, dataType = "String", paramType = "query")
//    })
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideClientInfo.class) })
//    @RequestMapping(value="giveup",method = {RequestMethod.GET,RequestMethod.POST})
//    public String giveup(Model model, HttpServletRequest req, String accId){
//        LogTaxmanInfo lti = taxmanService.getUserLogTaxmanInfo(accId);
//        taxmanService.action(lti,new GiveupActionEvent(lti));
//        model.addAttribute("Data",taxmanService.taxguideClientInfoExtract(lti));
//        return this.resolverViewName(req, "giveup");
//    }
//
//    @ApiOperation(value = "导税员获取客户端信息接口（轮询接口）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accId", value = "导税员Id", required = true, dataType = "String", paramType = "query")
//    })
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideClientInfo.class) })
//    @RequestMapping(value="getState",method = {RequestMethod.GET,RequestMethod.POST})
//    public String getState(Model model, HttpServletRequest req, String accId){
//        LogTaxmanInfo lti = taxmanService.getUserLogTaxmanInfo(accId);
//        model.addAttribute("Data",taxmanService.taxguideClientInfoExtract(lti));
//        return this.resolverViewName(req, "getState");
//    }
//
//    @ApiOperation(value = "手工选号，可办理票号列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accId", value = "导税员Id", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "pageNo", value = "分页参数", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "pageSize", value = "分页参数", required = true, dataType = "String", paramType = "query")
//    })
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = NsrAndTaskInfo.class) })
//    @RequestMapping(value="getCanDealTickets",method = {RequestMethod.GET,RequestMethod.POST})
//    public String getCanDealTickets(Model model, HttpServletRequest req, String accId,
//                                    @RequestParam(defaultValue = "1") int pageNo,@RequestParam(defaultValue = "1") int pageSize){
//        model.addAttribute("Data",taxmanService.getCanDealTickets(accId,pageNo,pageSize));
//        return this.resolverViewName(req, "getCanDealTickets");
//    }
//
//    @ApiOperation(value = "手工选号呼叫")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accId", value = "导税员Id", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "cstId", value = "CS票号ID", required = true, dataType = "String", paramType = "query")
//    })
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TaxguideClientInfo.class) })
//    @RequestMapping(value="manualCall",method = {RequestMethod.GET,RequestMethod.POST})
//    public String ManualCall(Model model, HttpServletRequest req, String accId,String cstId){
//        LogTaxmanInfo lti = taxmanService.getUserLogTaxmanInfo(accId);
//        taxmanService.action(lti,new ManualCallActionEvent(lti,cstId));
//        model.addAttribute("Data",taxmanService.taxguideClientInfoExtract(lti));
//        return this.resolverViewName(req, "ManualCall");
//    }
}
