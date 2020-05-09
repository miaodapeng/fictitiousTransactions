package com.vedeng.goods.controller;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.SetMeal;
import com.vedeng.goods.model.vo.SetMealSkuMappingVo;
import com.vedeng.goods.model.vo.SetMealVo;
import com.vedeng.goods.service.GoodsSetMealService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/vgoods/setMeal")
public class VGoodsSetMealController extends BaseController {

    @Autowired
    private GoodsSetMealService goodsSetMealService;

    /**
     * @description 打开套餐列表页面
     * @author cooper
     * @param
     * @date 2019/6/12
     */
    @RequestMapping("/index")
    public ModelAndView index(SetMealVo setMealVo, HttpServletRequest request,
              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
              @RequestParam(required = false) Integer pageSize){
        try{
            //查询套餐管理列表
            Page page = getPageTag(request, pageNo, pageSize);
            // 未删除状态
            setMealVo.setIsDeleted(CommonConstants.IS_DELETE_0);
            List<SetMeal> setMealList = goodsSetMealService.getSetMealListPage(setMealVo,page);
            ModelAndView indexMv = new ModelAndView();
            indexMv.addObject("setMealList",setMealList);
            indexMv.addObject("setMealVo",setMealVo);
            indexMv.addObject("page",page);
            indexMv.setViewName("goods/setMeal/index");
            return indexMv;
        }catch (Exception e){
            logger.error("打开套餐列表页面异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 打开套餐新增或编辑页面
     * @author cooper
     * @param
     * @date 2019/6/12
     */
    @FormToken(save = true)
    @RequestMapping("/openSetMeal")
    public ModelAndView openSetMeal(Integer setMealId, HttpServletRequest request){
        try{
            ModelAndView openMv = new ModelAndView();
            if (setMealId != null){
                //编辑页面,获取套餐信息
                SetMeal setMeal = goodsSetMealService.getSetMealById(setMealId);
                //获取套餐与商品关联信息列表
                SetMealSkuMappingVo setMealSkuMappingVo = new SetMealSkuMappingVo();
                setMealSkuMappingVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
                setMealSkuMappingVo.setSetMealId(setMealId);
                List<SetMealSkuMappingVo> setMealSkuMappingVoList = goodsSetMealService.getSetMealSkuMappingVoList(setMealSkuMappingVo);
                openMv.addObject("setMealSkuMappingVoList",setMealSkuMappingVoList);
                openMv.addObject("setMeal",setMeal);
            }
            openMv.setViewName("goods/setMeal/addSetMeal");
            return openMv;
        }catch (Exception e){
            logger.error("打开套餐新增或编辑页面异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 查看套餐信息
     * @author cooper
     * @param
     * @date 2019/6/12
     */
    @RequestMapping("/viewSetMeal")
    public ModelAndView viewSetMeal(Integer setMealId, HttpServletRequest request){
        try{
            ModelAndView viewMv = new ModelAndView();
            //获取套餐信息
            SetMeal setMeal = goodsSetMealService.getSetMealById(setMealId);
            //获取套餐与商品关联信息列表
            SetMealSkuMappingVo setMealSkuMappingVo = new SetMealSkuMappingVo();
            setMealSkuMappingVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
            setMealSkuMappingVo.setSetMealId(setMealId);
            List<SetMealSkuMappingVo> setMealSkuMappingVoList = goodsSetMealService.getSetMealSkuMappingVoList(setMealSkuMappingVo);
            viewMv.addObject("setMealSkuMappingVoList",setMealSkuMappingVoList);
            viewMv.addObject("setMeal",setMeal);
            viewMv.setViewName("goods/setMeal/viewSetMeal");
            return viewMv;
        }catch (Exception e){
            logger.error("保存套餐信息异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 保存套餐信息
     * @author cooper
     * @param
     * @date 2019/6/12
     */
    @FormToken(remove = true)
    @RequestMapping("/saveSetMeal")
    public ModelAndView saveSetMeal(SetMealVo setMealVo, HttpServletRequest request){
        try{
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null){
                //校验
                ResultInfo resultInfo = this.checkSetMeal(setMealVo);
                if (CommonConstants.SUCCESS_CODE.equals(resultInfo.getCode())){//检验成功
                    //保存
                    Integer setMealId = goodsSetMealService.saveSetMeal(setMealVo,user);
                    return new ModelAndView("redirect:./viewSetMeal.do?setMealId="+setMealId);
                }else {//检验失败
                    request.setAttribute("error",resultInfo.getMessage());
                }
            }else {
                request.setAttribute("error","登陆信息已失效，请重新登陆！");
            }
            return new ModelAndView("forward:./openNewSetMeal.do");
        }catch (Exception e){
            logger.error("保存套餐信息异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 转发打开新的套餐新增或编辑页面
     * @author cooper
     * @param
     * @date 2019/6/12
     */
    @FormToken(save = true)
    @RequestMapping("/openNewSetMeal")
    public ModelAndView openNewSetMeal(SetMealVo setMealVo, HttpServletRequest request){
        try{
            ModelAndView openNewMv = new ModelAndView();
            openNewMv.addObject("setMealSkuMappingVoList", setMealVo.getSetMealSkuMappingVoList());
            openNewMv.addObject("error", request.getAttribute("error"));
            openNewMv.addObject("setMeal",setMealVo);
            openNewMv.setViewName("goods/setMeal/addSetMeal");
            return openNewMv;
        }catch (Exception e){
            logger.error("转发打开新的套餐新增或编辑页面异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 删除套餐信息
     * @author cooper
     * @param
     * @date 2019/6/12
     */
    @RequestMapping("/deleteSetMeal")
    @ResponseBody
    public ResultInfo deleteSetMeal(String setMealIds, String deletedReason, HttpServletRequest request){
        try{
            if (setMealIds.length() > 0){
                User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
                if (user != null) {
                    //检验是否已经删除
                    List<SetMeal> setMealList = goodsSetMealService.getSetMealByIds(setMealIds);
                    if (setMealIds.split(",").length != setMealList.size()){
                        return new ResultInfo(CommonConstants.FAIL_CODE,"存在已被删除的套餐！");
                    }
                    if (deletedReason == null || "".equals(deletedReason)){
                        return new ResultInfo(CommonConstants.FAIL_CODE,"删除原因不可为空！");
                    }else if (deletedReason.length() < 10){
                        return new ResultInfo(CommonConstants.FAIL_CODE,"删除原因不可少于 10 个字！");
                    }
                    Integer result = goodsSetMealService.deleteSetMeal(setMealIds, deletedReason,user);
                    if (result > 0){
                        return new ResultInfo(CommonConstants.SUCCESS_CODE,"删除成功！");
                    }else {
                        return new ResultInfo(CommonConstants.FAIL_CODE,CommonConstants.FAIL_MSG);
                    }
                }else {
                    return new ResultInfo(CommonConstants.FAIL_CODE,"登陆信息已失效，请重新登陆！");
                }
            }else {
                return new ResultInfo(CommonConstants.FAIL_CODE,"请选中要删除的套餐！");
            }
        }catch (Exception e){
            logger.error("删除套餐信息异常：",e);
            return new ResultInfo(CommonConstants.FAIL_CODE,CommonConstants.FAIL_MSG);
        }
    }

    /**
     * @description 校验字段
     * @author cooper
     * @param
     * @date 2019/6/12
     */
    public ResultInfo checkSetMeal(SetMealVo setMealVo){
        try{
            if (setMealVo == null){
                return new ResultInfo(CommonConstants.FAIL_CODE,"请填写完整！");
            }else{
                if (setMealVo.getSetMealName() == null || "".equals(setMealVo.getSetMealName())){
                    return new ResultInfo(CommonConstants.FAIL_CODE,"套餐名称未填写，无法提交！");
                }else if (setMealVo.getSetMealName().length() > 50){
                    return new ResultInfo(CommonConstants.FAIL_CODE,"套餐名称最多只可以填写 50 个字，无法提交！");
                }
                SetMeal setMeal = goodsSetMealService.getSetMealByName(setMealVo.getSetMealName(),setMealVo.getSetMealId());
                if (setMeal != null && !"".equals(setMeal.getSetMealName())){
                    return new ResultInfo(CommonConstants.FAIL_CODE,"已存在相同名称的套餐，无法提交！");
                }
                if (setMealVo.getSetMealType() == null){
                    return new ResultInfo(CommonConstants.FAIL_CODE,"未选择套餐类型，无法提交！");
                }

                if (setMealVo.getSetMealDescript() != null && ! "".equals(setMealVo.getSetMealDescript())
                        && setMealVo.getSetMealDescript().length() > 500){
                    return new ResultInfo(CommonConstants.FAIL_CODE,"套餐说明最多只可以填写 500 个字，无法提交！");
                }

                if (CollectionUtils.isNotEmpty(setMealVo.getSetMealSkuMappingVoList())){
                    List<SetMealSkuMappingVo> setMealSkuMappingVoList = setMealVo.getSetMealSkuMappingVoList();
                    if (setMealSkuMappingVoList.size()>99){
                        return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中最多添加99条商品，无法提交！");
                    }
                    for (int i=0;i<setMealSkuMappingVoList.size();i++){
                        for (int j = i+1;j<setMealSkuMappingVoList.size();j++){
                            if (setMealSkuMappingVoList.get(i).getSkuId().equals(setMealSkuMappingVoList.get(j).getSkuId())){
                                return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在重复商品，无法提交！");
                            }
                        }
                        SetMealSkuMappingVo setMealSkuMappingVo = setMealSkuMappingVoList.get(i);
                        if (setMealSkuMappingVo.getSkuSign() == null){
                            return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在商品未选择商品标记，无法提交！");
                        }
                        if (setMealSkuMappingVo.getSkuId() == null){
                            return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在商品未选择具体商品，无法提交！");
                        }
                        if (setMealVo.getSetMealType().equals(CommonConstants.SET_MEAL_TYPE_2)){
                            if (setMealSkuMappingVo.getDepartmentIds() == null || "".equals(setMealSkuMappingVo.getDepartmentIds())){
                                return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在商品未选择科室，无法提交！");
                            }
                        }
                        if (setMealSkuMappingVo.getQuantity() == null || setMealSkuMappingVo.getQuantity() == 0 ){
                            return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在商品未填写数量，无法提交！");
                        }
                        if (setMealSkuMappingVo.getPurpose() == null || "".equals(setMealSkuMappingVo.getPurpose())){
                            return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在商品未填写用途，无法提交！");
                        }else if (setMealSkuMappingVo.getPurpose().length() > 200){
                            return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在商品的用途超过 200 个字，无法提交！");
                        }
                        if (setMealSkuMappingVo.getRemark() != null && !"".equals(setMealSkuMappingVo.getRemark())
                            && setMealSkuMappingVo.getRemark().length() > 500){
                            return new ResultInfo(CommonConstants.FAIL_CODE,"套餐明细中存在商品的备注超过 500 个字，无法提交！");
                        }
                    }
                }
            }
            return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);
        }catch (Exception e){
            logger.error("校验套餐信息字段异常：",e);
            return new ResultInfo(CommonConstants.FAIL_CODE,CommonConstants.FAIL_MSG);
        }
    }
}
