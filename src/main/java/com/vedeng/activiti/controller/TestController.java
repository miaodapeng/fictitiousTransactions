package com.vedeng.activiti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Controller  
//@Scope("prototype")   
@RequestMapping("/activiti")
public class TestController 
{  
    @RequestMapping(value="/test",method=RequestMethod.GET)  
    public ModelAndView  goTest(String name,String password)  
    {  
        ModelAndView mov=new ModelAndView();  
        mov.setViewName("/test");  
        return mov;  
    }
}  
