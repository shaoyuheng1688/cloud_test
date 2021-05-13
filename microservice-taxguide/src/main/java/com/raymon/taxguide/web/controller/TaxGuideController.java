package com.raymon.taxguide.web.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.raymon.taxguide.provider.RabbitSender;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TaxGuideController extends AbstractController {

    @Autowired
    private TestService testService;
    @Autowired
    private TaxguideRepository taxguideRepository;
    @Autowired
    RabbitSender rabbitSender;

    @RequestMapping(value="test",method = {RequestMethod.GET,RequestMethod.POST})
    public String test(Model model, HttpServletRequest req){
        model.addAttribute("test");
        return this.resolverViewName(req, "test");
    }

    @RequestMapping(value="test1",method = {RequestMethod.GET,RequestMethod.POST})
    public String test1(Model model, HttpServletRequest req){
        testService.test();
//        LogTaxguidePollTime logTaxguidePollTime =
//                new LogTaxguidePollTime("999",1,"123");
//        LogTaxguidePollTime a = taxguideRepository.saveLogTaxguidePollTime(logTaxguidePollTime);
//        throw new ApplicationException("????");
        model.addAttribute("test1");
        return this.resolverViewName(req, "test");
    }

    @RequestMapping(value="test2",method = {RequestMethod.GET,RequestMethod.POST})
    public String test2(Model model, HttpServletRequest req){
        rabbitSender.sendConfirm();
        model.addAttribute("test2");
        return this.resolverViewName(req, "test2");
    }
}
