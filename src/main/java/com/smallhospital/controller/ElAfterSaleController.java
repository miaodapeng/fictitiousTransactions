package com.smallhospital.controller;

import com.smallhospital.dao.ElAfterSaleIntentionMapper;
import com.smallhospital.dao.ElAfterSalesIntentionDetailMapper;
import com.smallhospital.dto.ElAfterSaleIntentionDto;
import com.smallhospital.model.ElAfterSalesIntention;
import com.smallhospital.model.ElAfterSalesIntentionDetail;
import com.smallhospital.service.impl.ElAfterSaleService;
import com.vedeng.authorization.model.Position;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Daniel
 * @date created in 2020/1/19 9:52
 */
@Controller
@RequestMapping(value = "/el/trader/aftersale")
public class ElAfterSaleController extends BaseController {

    @Autowired
    private ElAfterSaleIntentionMapper elAfterSaleIntentionMapper;

    @Autowired
    private ElAfterSalesIntentionDetailMapper elAfterSalesIntentionDetailMapper;

    @Autowired
    private ElAfterSaleService elAfterSaleService;

    @RequestMapping(value = "/index")
    public ModelAndView afterSaleIntention(HttpServletRequest request,
                                           @RequestParam(required = false) Integer status,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        ModelAndView mv = new ModelAndView();
        Page page = getPageTag(request,pageNo,pageSize);
        Map<String,Object> map = new HashMap<>(2);
        map.put("status",status);
        map.put("page",page);
        List<ElAfterSalesIntention> afterSalesIntentions = elAfterSaleIntentionMapper.getIntentionsUnHandledlistPage(map);
        mv.addObject("page", page);
        mv.addObject("status",status);
        List<ElAfterSaleIntentionDto> intentionDtos = afterSalesIntentions.stream()
                .map(intention -> {
                    ElAfterSaleIntentionDto intentionDto = new ElAfterSaleIntentionDto();
                    BeanUtils.copyProperties(intention, intentionDto);
                    intentionDto.setDetails(elAfterSalesIntentionDetailMapper.getDetailsByIntentionId(intention.getElAfterSaleIntentionId()));
                    return intentionDto;
                })
                .collect(Collectors.toList());
        mv.addObject("intentions",intentionDtos);
        mv.setViewName("el/aftersale/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value="/updateIntention")
    public ResultInfo<Position> updateIntention(ElAfterSalesIntention intention){
        ResultInfo<Position> resultInfo = new ResultInfo<Position>();
        if (intention.getElAfterSaleIntentionId() == null || intention.getStatus() == null){
            resultInfo.setMessage("参数错误");
        } else {
            if (elAfterSaleService.syncAfterSaleApproval(intention)){
                resultInfo.setCode(0);
            } else {
                resultInfo.setMessage("同步医流网系统失败");
            }
        }
        return resultInfo;
    }

    @RequestMapping(value = "/intention/detail",method = RequestMethod.GET)
    public ModelAndView intentionDetail(@RequestParam Integer detailId){
        List<ElAfterSalesIntentionDetail> details = elAfterSalesIntentionDetailMapper.getDetailsByIntentionId(detailId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("details",details);
        mv.setViewName("el/aftersale/intention_detail");
        return mv;
    }
}
