package com.kevi.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 页面控制类
 */
@Controller
public class PageController {

//    @RequestMapping(value = "", method = RequestMethod.GET)
    @GetMapping
    public String lookIndexPage(Model model){
        model.addAttribute("time", new Date());
        return "index";
    }

}
