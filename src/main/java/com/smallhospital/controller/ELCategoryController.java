package com.smallhospital.controller;

import com.alibaba.fastjson.JSON;
import com.smallhospital.dto.ElResultDTO;
import com.smallhospital.service.ELCategoryService;
import com.vedeng.authorization.model.Position;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/el/category")
public class ELCategoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ELCategoryController.class);

    @Autowired
    private ELCategoryService categoryService;

    @RequestMapping(value="/synCategoryInfo")
    @ResponseBody
    public ResultInfo synCategoryInfo(){

        ResultInfo<Position> resultInfo = new ResultInfo<Position>();
        try{
            categoryService.synCategoryInfo();
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    @RequestMapping(value="/intentionCategoryIds")
    @ResponseBody
    public ElResultDTO intentionCategoryIds(){
        try{
            return ElResultDTO.ok(categoryService.intentionCategoryIds());
        }catch (Exception e){
            LOGGER.error("获取意向产品的分类id列表失败:",e);
            return ElResultDTO.error("获取意向产品的分类id列表失败");
        }
    }

    /**
     * 小医院系统获取产品分类信息
     * @return 产品分类
     */
    @RequestMapping(method = RequestMethod.GET, value = "/detail")
    public ElResultDTO getErpProductCategory(){
        return null;
    }
}
