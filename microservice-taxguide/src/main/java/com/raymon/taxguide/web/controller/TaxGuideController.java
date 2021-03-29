package com.raymon.taxguide.web.controller;


import com.raymon.frame.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TaxGuideController extends AbstractController {


    @RequestMapping(value="test",method = {RequestMethod.GET,RequestMethod.POST})
    public String test(Model model, HttpServletRequest req){
        model.addAttribute("test");
        return this.resolverViewName(req, "test");
    }

}
