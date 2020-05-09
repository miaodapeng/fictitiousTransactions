package com.web.mjx.controller;

import com.vedeng.common.model.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Auther: Duke.li
 * @Date: 2019/8/13 16:04
 */
@Controller
@RequestMapping("/mjx/trader")
public class MjxTraderController {

    private static final Logger logger = LoggerFactory.getLogger(MjxTraderController.class);

    @ResponseBody
    @RequestMapping(value = "/saveAddress")
    public ResultInfo saveAddress(){
        logger.info("mjx测试接口");
        return new ResultInfo();
    }
}
