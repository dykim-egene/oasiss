package com.oasis.custom.controller;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class ErrorController {
 
    @ResponseBody
    @RequestMapping(value = "/ajaxTest", method = RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
    public String page(HttpServletRequest request, HttpServletResponse response){
        return "ajaxTest";
    }
}
