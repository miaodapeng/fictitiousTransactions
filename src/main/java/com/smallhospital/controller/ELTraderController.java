package com.smallhospital.controller;

import com.alibaba.fastjson.JSON;
import com.smallhospital.dao.ElAfterSaleIntentionMapper;
import com.smallhospital.dto.*;
import com.smallhospital.model.ElAfterSalesIntention;
import com.smallhospital.model.vo.ElTraderVo;
import com.smallhospital.service.ELTraderService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.order.model.Saleorder;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.model.vo.TraderCertificateVo;
import com.vedeng.trader.service.TraderCustomerService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  小医院客户列表
 */
@Controller
@RequestMapping("/el/trader")
public class ELTraderController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ELTraderController.class);

    @Autowired
    private ELTraderService traderService;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    private TraderCustomerService traderCustomerService;


    @ResponseBody
    @RequestMapping(value="/index")
    public ModelAndView index(HttpServletRequest request, ElTraderVo trader,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                              @RequestParam(required = false) Integer pageSize,
                              HttpSession session){
        ModelAndView mv = new ModelAndView();
        User user =(User)session.getAttribute(ErpConst.CURR_USER);
        trader.setOwner(user.getUserId());
        //查询集合
        Page page = getPageTag(request,pageNo,pageSize);
        List<ElTraderVo> traderList = traderService.querylistPage(trader, page);

        if(CollectionUtils.isNotEmpty(traderList)){
            traderList.forEach((ElTraderVo traderVo)->{
                traderVo.setOwnerName(userMapper.getUserNameByTraderId(traderVo.getTraderId()));
            });
        }

        mv.addObject("traderList",traderList);
        mv.addObject("trader", trader);
        mv.addObject("page", page);
        mv.setViewName("el/trader/index");
        return mv;
    }




    @ResponseBody
    @RequestMapping(value = "/addTrader")
    public ModelAndView addTrader(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("el/trader/addTrade");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/validTrader")
    public ResultInfo validTrader(HttpServletRequest request, @Param(value="traderId") Integer traderId) {
        try{
            // 归属销售
            User belongUser = userMapper.getUserInfoByTraderId(traderId, 1);// 1客户，2供应商

            User loginUser = (User) request.getSession().getAttribute(Consts.SESSION_USER);

            if(!loginUser.getUserId().equals(belongUser.getUserId())){
                return new ResultInfo(-1,"请操作属于自己的客户");
            }

            TraderContactGenerate traderContact = this.traderService.getTraderDefaultContact(traderId);

            if(traderContact == null){
                return new ResultInfo(-1,"客户没有默认联系人信息，请完善信息后重试");
            }

            if(StringUtils.isEmpty(traderContact.getEmail())){
                return new ResultInfo(-1,"该客户的联系人邮箱为空，请完善信息后重试!");
            }

            TraderAddress traderAddress = traderService.getTraderDefaultAddress(traderId);
            if(traderAddress == null){
                return new ResultInfo(-1,"客户没有默认地址，请完善信息后重试");
            }

            return new ResultInfo(0,"成功","3");

        }catch (Exception e){
            logger.error("校验新增客户失败",e);
            return new ResultInfo(-1,"失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/saveElTraderinfo")
    public ModelAndView saveElTraderinfo(HttpServletRequest request, HttpSession session, Saleorder saleorder) {
        ModelAndView mv = new ModelAndView();
        try {
            traderService.saveElTraderinfo(saleorder, session);
            return success(mv);
        } catch (Exception e) {
            logger.error("saveElTraderinfo:", e);
            return fail(mv);
        }
    }

    /**
     * 小医院系统获取对账单同步
     * @return 产品详情
     */
    @RequestMapping(method = RequestMethod.POST, value = "/synReconBill")
    @ResponseBody
    public ElResultDTO synReconBill(HttpServletRequest request,@RequestBody ELTraderDto traderDto){

        LOGGER.info("小医院系统获取对账单同步请求参数:" + JSON.toJSONString(traderDto));

        if(traderDto.getHospitalId() == null){
            return ElResultDTO.errorInternal("请求参数hospitalId不能为空");
        }

        ElTraderVo trader = this.traderService.getElTraderByTraderId(traderDto.getHospitalId());
        if(trader == null){
            return ElResultDTO.errorInternal("请求参数hospitalId="+traderDto.getHospitalId()+"非小医院的客户，请重试");
        }

        Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_5);
        traderDto.setTraderId(traderDto.getHospitalId());
        List<ELReconBillDTO> reconBillDataLists= this.traderService.getReconBill(traderDto,page);

        ElResultDTO result = ElResultDTO.ok();
        result.setData(reconBillDataLists);

        LOGGER.info("小医院系统获取对账单同步响应参数:" + JSON.toJSONString(result));

        return result;
    }

    /**
     * 小医院系统获取客户账期额度信息
     * @return 产品详情
     */
    @RequestMapping(method = RequestMethod.POST, value = "/accountPeriod")
    @ResponseBody
    public ElResultDTO accountPeriod(HttpServletRequest request,@RequestBody ELTraderDto traderDto){

        LOGGER.info("小医院系统获取客户账期额度信息请求参数:" + JSON.toJSONString(traderDto));

        if(traderDto.getHospitalId() == null){
            return ElResultDTO.errorInternal("请求参数hospitalId不能为空");
        }

        ElTraderVo trader = this.traderService.getElTraderByTraderId(traderDto.getHospitalId());
        if(trader == null){
            return ElResultDTO.errorInternal("请求参数hospitalId="+traderDto.getHospitalId()+"非小医院的客户，请重试");
        }

        try{
            TraderCertificateVo traderCertificateVo  = new TraderCertificateVo();
            traderCertificateVo.setTraderId(traderDto.getHospitalId());
            traderCertificateVo.setTraderType(ErpConst.ONE);
            Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(traderCertificateVo,"cw");

            ELAccountPeriodDto accountPeriodDto = new ELAccountPeriodDto();
            if(map != null){
                JSONObject financeJsonObject =JSONObject.fromObject(map.get("finance"));
                accountPeriodDto.setPeriodAmount(BigDecimal.valueOf(financeJsonObject.getDouble("periodAmount")));
                accountPeriodDto.setLeftAmount(BigDecimal.valueOf(financeJsonObject.getDouble("balanceAccount")));
                accountPeriodDto.setUsedAmount(accountPeriodDto.getPeriodAmount().subtract(accountPeriodDto.getLeftAmount()));
            }

            ElResultDTO result = ElResultDTO.ok();
            result.setData(accountPeriodDto);

            LOGGER.info("小医院系统获取客户账期额度信息响应参数:" + JSON.toJSONString(result));

            return result;

        }catch (Exception e){
            LOGGER.error("小医院系统获取客户账期额度信息失败",e);
        }

        return ElResultDTO.error("小医院系统获取客户账期额度信息失败");
    }

}
