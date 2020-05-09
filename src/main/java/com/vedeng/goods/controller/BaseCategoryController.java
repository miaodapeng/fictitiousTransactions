package com.vedeng.goods.controller;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.BaseCategory;
import com.vedeng.goods.model.CategoryAttrValueMapping;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import com.vedeng.goods.model.vo.CategoryAttrValueMappingVo;
import com.vedeng.goods.service.BaseAttributeService;
import com.vedeng.goods.service.BaseAttributeValueService;
import com.vedeng.goods.service.BaseCategoryService;
import com.vedeng.goods.service.VgoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @param
 * @author bill
 * @description 分类基础信息
 * @date $
 */

@Controller
@RequestMapping("/category/base")
public class BaseCategoryController extends BaseController {

    @Autowired
    private BaseCategoryService baseCategoryService;

    @Autowired
    private BaseAttributeService baseAttributeService;

    @Autowired
    private BaseAttributeValueService baseAttributeValueService;

    @Autowired
    private VgoodsService goodsService;

    /**
     * @description 打开分类新增或编辑界面
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    @FormToken(save = true)
    @RequestMapping(value = "/openCategoryPage")
    public ModelAndView openCategoryPage(HttpServletRequest request, BaseCategoryVo baseCategoryVo){
        try{
            ModelAndView categoryPageMv = new ModelAndView();
            if (baseCategoryVo.getBaseCategoryId() != null){//如果不为空，则为编辑
                ResultInfo result = this.checkDelete(baseCategoryVo);
                if (CommonConstants.FAIL_CODE.equals(result.getCode())){
                    return this.pageNotFound();
                }
                String firstLevelCategoryName = baseCategoryVo.getFirstLevelCategoryName();
                String secondLevelCategoryName = baseCategoryVo.getSecondLevelCategoryName();
                        //查询分类详细信息
                baseCategoryVo = baseCategoryService.getBaseCategoryByParam(baseCategoryVo.getBaseCategoryId());
                baseCategoryVo.setFirstLevelCategoryName(firstLevelCategoryName);
                baseCategoryVo.setSecondLevelCategoryName(secondLevelCategoryName);
                if (CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
                    //如果是三级分类，需要查询引用的属性及属性值列表信息
                    List<BaseCategoryVo> baseCategoryVoList = new ArrayList<>();
                    baseCategoryVoList.add(baseCategoryVo);
                    //查询分类关联的属性列表信息
                    List<BaseAttributeVo> attributeVoList = baseAttributeService.getAttributeListByCategoryId(baseCategoryVoList);
                    //查询分类关联的属性值列表信息
                    List<BaseAttributeValueVo> attributeValueVoList = baseAttributeValueService.getAttrValueListByCategoryId(baseCategoryVoList);
                    attributeVoList = baseCategoryService.doAttrAndValueToString(attributeVoList,attributeValueVoList);
                    baseCategoryVo.setAttributeVoList(attributeVoList);
                }
            }
            //查询所有属性以及属性下面的属性值列表
            BaseAttributeVo baseAttributeVo = new BaseAttributeVo();
            baseAttributeVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
            // 获取属性列表
            List<BaseAttributeVo> list = baseAttributeService.getBaseAttributeInfoListPage(baseAttributeVo, null);
            // 获取属性值列表
            BaseAttributeValueVo baseAttributeValueVo = new BaseAttributeValueVo();
            baseAttributeValueVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
            List<BaseAttributeValueVo> baseAttributeValueVoList = baseAttributeValueService.getBaseAttributeValueVoListASC(baseAttributeValueVo,list);
            String attrAndValueJson = baseCategoryService.doAttrAndValueToJson(list,baseAttributeValueVoList);
            //categoryPageMv.addObject("formToken",request.getAttribute("formtoken"));
            categoryPageMv.addObject("attrAndValueJson",attrAndValueJson);
            categoryPageMv.addObject("baseCategoryVo",baseCategoryVo);
            categoryPageMv.setViewName("goods/basecategory/add");
            return categoryPageMv;
        }catch (Exception e){
            logger.error("打开分类新增或编辑界面异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 保存分类信息
     * @author bill
     * @param
     * @date 2019/5/8
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveCategory")
    public ModelAndView saveBaseCategory(HttpServletRequest request, BaseCategoryVo baseCategoryVo){
        try{
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null){
                if (baseCategoryVo.getBaseCategoryId()!= null && !"".equals(baseCategoryVo.getBaseCategoryId())){
                    BaseCategory baseCategory = baseCategoryService.getBaseCategoryByParam(baseCategoryVo.getBaseCategoryId());
                    if (CommonConstants.IS_DELETE_1.equals(baseCategory.getIsDeleted())){
                        return this.pageNotFound();
                    }
                }
                //校验
                ResultInfo checkResult = baseCategoryService.checkCategoryField(baseCategoryVo);
                if (CommonConstants.SUCCESS_CODE.equals(checkResult.getCode())){
                    if (baseCategoryVo.getBaseCategoryId() != null){
                        ResultInfo result = this.checkDelete(baseCategoryVo);
                        if (CommonConstants.FAIL_CODE.equals(result.getCode())){
                            return this.pageNotFound();
                        }
                        baseCategoryVo.setUpdater(user.getUserId());
                    }else{
                        baseCategoryVo.setUpdater(user.getUserId());
                        baseCategoryVo.setCreator(user.getUserId());
                    }
                    // 保存分类信息
                    ResultInfo resultInfo = baseCategoryService.saveBaseCategory(baseCategoryVo);
                    if (resultInfo != null){
                        if (CommonConstants.SUCCESS_CODE.equals(resultInfo.getCode())){// 成功，重定向到详情页
                            if (baseCategoryVo.getIsEditGoods() != null && baseCategoryVo.getIsEditGoods().equals(CommonConstants.STATUS_1)){
                                //如果该分类下有商品，且修改了下面的属性属性值，则更新商品状态
                                goodsService.changeSpuStatusByCategoryChange(baseCategoryVo.getBaseCategoryId(),user);
                            }
                            String url = "redirect:./getCategoryInfo.do?baseCategoryId=" + resultInfo.getData();
                            if(CommonConstants.CATEGORY_LEVEL_2.equals(baseCategoryVo.getBaseCategoryLevel())){
                                url = url+"&firstLevelCategoryName="+ URLEncoder.encode(baseCategoryVo.getFirstLevelCategoryName(),"UTF-8");
                            }
                            if(CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
                                url = url+"&firstLevelCategoryName="+URLEncoder.encode(baseCategoryVo.getFirstLevelCategoryName(),"UTF-8")
                                        +"&secondLevelCategoryName="+ URLEncoder.encode(baseCategoryVo.getSecondLevelCategoryName(),"UTF-8");
                            }
                            return new ModelAndView(url);
                        }else{//失败返回当前页面，并返回报错信息
                            request.setAttribute("error",resultInfo.getMessage());
                        }
                    }else {
                        request.setAttribute("error","商品分类保存失败");
                    }
                }else{
                    request.setAttribute("error",checkResult.getMessage());
                }
            }else{
                request.setAttribute("error","登陆信息已失效，请重新登陆");
            }
            return new ModelAndView("forward:./openNewCategoryPage.do");
        }catch (Exception e){
            logger.error("商品分类保存异常：",e);
            return this.page500();
        }


    }

    /**
     * @description 查看分类详情
     * @author bill
     * @param
     * @date 2019/5/16
     */
    @RequestMapping(value = "/getCategoryInfo")
    public ModelAndView getCategoryInfo(HttpServletRequest request, BaseCategoryVo baseCategoryVo){
        try{
            ResultInfo result = this.checkDelete(baseCategoryVo);
            if (CommonConstants.FAIL_CODE.equals(result.getCode())){
                return this.pageNotFound();
            }
            ModelAndView categoryInfoMv = new ModelAndView();
            String firstLevelCategoryName = baseCategoryVo.getFirstLevelCategoryName();
            String secondLevelCategoryName = baseCategoryVo.getSecondLevelCategoryName();
            // 基本信息 -- 根据id查询属性属性值信息
            baseCategoryVo = baseCategoryService.getBaseCategoryByParam(baseCategoryVo.getBaseCategoryId());
            baseCategoryVo.setFirstLevelCategoryName(firstLevelCategoryName);
            baseCategoryVo.setSecondLevelCategoryName(secondLevelCategoryName);
            if (CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
                //如果是三级分类，需要查询引用的属性及属性值列表信息
                List<BaseCategoryVo> baseCategoryVoList = new ArrayList<>();
                baseCategoryVoList.add(baseCategoryVo);
                //查询分类关联的属性列表信息
                List<BaseAttributeVo> attributeVoList = baseAttributeService.getAttributeListByCategoryId(baseCategoryVoList);
                //查询分类关联的属性值列表信息
                List<BaseAttributeValueVo> attributeValueVoList = baseAttributeValueService.getAttrValueListByCategoryId(baseCategoryVoList);
                attributeVoList = baseAttributeService.doAttrAndValue(attributeVoList,attributeValueVoList);
                baseCategoryVo.setAttributeVoList(attributeVoList);
            }
            categoryInfoMv.addObject("baseCategoryVo",baseCategoryVo);
            categoryInfoMv.setViewName("goods/basecategory/view");
            return categoryInfoMv;
        }catch (Exception e){
            logger.error("查看分类详情异常：",e);
            return this.page500();
        }
    }


    /**
     * @description 获取商品分类列表
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    @RequestMapping(value = "/index")
    public ModelAndView getCategoryListPage(HttpServletRequest request, BaseCategoryVo baseCategoryVo,
           @RequestParam(required = false, defaultValue = "1") Integer pageNo,
           @RequestParam(required = false) Integer pageSize){
        try{
            ModelAndView categoryListmv = new ModelAndView();
            Page page = getPageTag(request, pageNo, pageSize);
            // 未删除状态
            baseCategoryVo.setIsDeleted(CommonConstants.IS_DELETE_0);
            //paramMap.put("isDeleted", CommonConstants.IS_DELETE_0);
            // 获取一级分类列表
            baseCategoryVo.setBaseCategoryLevel(CommonConstants.CATEGORY_LEVEL_1);
            List<BaseCategoryVo> firstCategoryList = baseCategoryService.getCategoryListPage(baseCategoryVo,page);
            List<BaseCategoryVo> secondCategoryList = null;
            List<BaseCategoryVo> thirdCategoryList = null;
            List<CategoryAttrValueMappingVo> categoryAttrValueMappingVoList = null;
            if (CollectionUtils.isNotEmpty(firstCategoryList)){
                // 获取二级分类列表
                baseCategoryVo.setBaseCategoryLevel(CommonConstants.CATEGORY_LEVEL_2);
                secondCategoryList = baseCategoryService.getCategoryListPage(baseCategoryVo,null);
                if (CollectionUtils.isNotEmpty(secondCategoryList)){
                    // 获取三级分类列表
                    baseCategoryVo.setBaseCategoryLevel(CommonConstants.CATEGORY_LEVEL_3);
                    thirdCategoryList = baseCategoryService.getCategoryListPage(baseCategoryVo,null);
                    if (CollectionUtils.isEmpty(thirdCategoryList) || thirdCategoryList.get(0) == null ||
                            (thirdCategoryList != null && thirdCategoryList.size() == 1 && thirdCategoryList.get(0) != null
                            && thirdCategoryList.get(0).getBaseCategoryId() == null)) {
                        thirdCategoryList = null;
                    }else{
                        // 获取三级分类下引用的属性关联列表
                        categoryAttrValueMappingVoList =  baseCategoryService.getCategoryAttrValueMappingVoList(thirdCategoryList);
                    }
                }
            }
            // 处理分类级别关联
            categoryListmv.addObject("list",this.doBaseCategoryLevel(firstCategoryList,
                    secondCategoryList,thirdCategoryList,categoryAttrValueMappingVoList));
            categoryListmv.addObject("page",page);
            categoryListmv.addObject("baseCategoryVo",baseCategoryVo);
            categoryListmv.setViewName("goods/basecategory/index");
            return categoryListmv;
        }catch (Exception e){
            logger.error("获取商品分类列表异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 转发打开新增或编辑分类页
     * @author bill
     * @param
     * @date 2019/5/8
     */
    @FormToken(save = true)
    @RequestMapping(value = "/openNewCategoryPage")
    public ModelAndView openNewCategoryPage(HttpServletRequest request, BaseCategoryVo baseCategoryVo) {
        try {
            if (baseCategoryVo.getBaseCategoryId()!= null && !"".equals(baseCategoryVo.getBaseCategoryId())){
                ResultInfo result = this.checkDelete(baseCategoryVo);
                if (CommonConstants.FAIL_CODE.equals(result.getCode())){
                    return this.pageNotFound();
                }
            }
            ModelAndView newCategoryMv = new ModelAndView();
            if (CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
                //处理传过来的属性与属性值
                baseCategoryVo.setAttributeVoList(baseCategoryService.doAttrAndValueToList(baseCategoryVo));
            }
            //查询所有属性以及属性下面的属性值列表
            BaseAttributeVo baseAttributeVo = new BaseAttributeVo();
            baseAttributeVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
            // 获取属性列表
            List<BaseAttributeVo> list = baseAttributeService.getBaseAttributeInfoListPage(baseAttributeVo, null);
            // 获取属性值列表
            BaseAttributeValueVo baseAttributeValueVo = new BaseAttributeValueVo();
            baseAttributeValueVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
            List<BaseAttributeValueVo> baseAttributeValueVoList = baseAttributeValueService.getBaseAttributeValueVoListASC(baseAttributeValueVo,list);
            String attrAndValueJson = baseCategoryService.doAttrAndValueToJson(list,baseAttributeValueVoList);
            //newCategoryMv.addObject("formToken",request.getAttribute("formtoken"));
            newCategoryMv.addObject("attrAndValueJson", attrAndValueJson);
            newCategoryMv.addObject("baseCategoryVo", baseCategoryVo);
            newCategoryMv.addObject("error", request.getAttribute("error"));
            newCategoryMv.setViewName("goods/basecategory/add");
            return newCategoryMv;
        }catch (Exception e){
            logger.error("转发打开新增或编辑分类页异常：",e);
            return this.page500();
        }
    }

    /**
     * @description 删除分类
     * @author bill
     * @param
     * @date 2019/5/17
     */
    @RequestMapping(value = "/deleteCategory")
    @ResponseBody
    public ResultInfo deleteCategory(HttpServletRequest request, BaseCategoryVo baseCategoryVo){
        try{
            // 当前用户
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null) {//登陆已失效
                BaseCategory baseCategory = baseCategoryService.getBaseCategoryByParam(baseCategoryVo.getBaseCategoryId());
                if (CommonConstants.IS_DELETE_1.equals(baseCategory.getIsDeleted())){
                    return new ResultInfo(CommonConstants.FAIL_CODE,"该分类已经被删除，无法操作");
                }
                baseCategoryVo.setUpdater(user.getUserId());
                baseCategoryVo.setModTime(new Date());
                baseCategoryVo.setIsDeleted(CommonConstants.IS_DELETE_1);
                List<BaseCategoryVo> firstCategoryList = null;
                List<BaseCategoryVo> secondCategoryList = null;
                List<BaseCategoryVo> thirdCategoryList = null;
                List<CategoryAttrValueMappingVo> categoryAttrValueMappingVoList = null;
                //如果删除的是一级分类，则删除该一级分类以及该一级分类下的所有二级、三级分类，以及三级分类下的所有属性引用关系
                if (CommonConstants.CATEGORY_LEVEL_1.equals(baseCategoryVo.getBaseCategoryLevel())){
                    firstCategoryList = new ArrayList<>();
                    firstCategoryList.add(baseCategoryVo);
                    //查询出该一级分类下的所有未删除的二级分类
                    secondCategoryList = baseCategoryService.getCategoryListByIds(firstCategoryList,CommonConstants.CATEGORY_LEVEL_2);
                    //查询出所有二级分类下的三级分类
                    if (CollectionUtils.isNotEmpty(secondCategoryList)){
                        thirdCategoryList = baseCategoryService.getCategoryListByIds(secondCategoryList,CommonConstants.CATEGORY_LEVEL_3);
                    }
                }
                //如果删除的是二级分类，则删除该二级分类以及该二级分类下的所有三级分类，以及三级分类下的所有属性引用关系
                if (CommonConstants.CATEGORY_LEVEL_2.equals(baseCategoryVo.getBaseCategoryLevel())){
                    //查询出该二级分类下的所有三级分类
                    secondCategoryList = new ArrayList<>();
                    secondCategoryList.add(baseCategoryVo);
                    thirdCategoryList = baseCategoryService.getCategoryListByIds(secondCategoryList,CommonConstants.CATEGORY_LEVEL_3);
                }
                //如果是三级分类，则删除该分类本身，以及三级分类下的所有属性引用关系
                if (CommonConstants.CATEGORY_LEVEL_3.equals(baseCategoryVo.getBaseCategoryLevel())){
                    //查询出三级分类下的商品数目信息
                    thirdCategoryList = baseCategoryService.getthirdCategoryListById(baseCategoryVo);
                }
                //验证是否可删除
                if (CollectionUtils.isEmpty(thirdCategoryList) || thirdCategoryList.get(0) == null ||
                        (thirdCategoryList != null && thirdCategoryList.size() == 1 && thirdCategoryList.get(0) != null
                                && thirdCategoryList.get(0).getBaseCategoryId() == null)){
                    thirdCategoryList = null;
                }else{
                    for (BaseCategoryVo baseCategoryVoTemp : thirdCategoryList){
                        Integer coreProductNum = baseCategoryVoTemp.getCoreProductNum() == null ? 0 : baseCategoryVoTemp.getCoreProductNum();
                        Integer temporaryProductNum = baseCategoryVoTemp.getTemporaryProductNum() == null ? 0 : baseCategoryVoTemp.getTemporaryProductNum();
                        Integer otherProductNum = baseCategoryVoTemp.getOtherProductNum() == null ? 0 : baseCategoryVoTemp.getOtherProductNum();
                        if (coreProductNum > 0 || temporaryProductNum > 0
                                || otherProductNum > 0){
                            return new ResultInfo(CommonConstants.FAIL_CODE,"分类下存在商品，暂不可删除！");
                        }
                    }
                }
                //删除操作
                Integer result = baseCategoryService.deleteCategory(firstCategoryList,secondCategoryList,thirdCategoryList,user);
                if (result > 0){
                    return new ResultInfo(CommonConstants.SUCCESS_CODE,"删除成功！");
                }
                return new ResultInfo(CommonConstants.FAIL_CODE,"删除失败！");
            }else{
                return new ResultInfo(CommonConstants.FAIL_CODE,"登陆信息已失效，请重新登陆！");
            }
        }catch (Exception e){
            logger.error("删除分类信息异常：",e);
            return new ResultInfo(CommonConstants.FAIL_CODE,"删除分类异常！");
        }
    }

    /**
     * @description 根据属性id查询所有引用的分类
     * @author bill
     * @param
     * @date 2019/5/17
     */
    @RequestMapping(value = "/getCateforyListByAttrId")
    @ResponseBody
    public ResultInfo getCateforyListByAttrId(HttpServletRequest request, Integer baseAttributeId,
        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
        @RequestParam(required = false, defaultValue = "20") Integer pageSize){
        ResultInfo resultInfo = new ResultInfo();
        Page page = getPageTag(request, pageNo, pageSize);
        // 已引用商品分类
        List<BaseCategoryVo> categoryList = baseCategoryService.getBaseCategoryListPageByAttr(baseAttributeId, page);
        resultInfo.setCode(CommonConstants.SUCCESS_CODE);
        resultInfo.setListData(categoryList);
        resultInfo.setPage(page);
        resultInfo.setMessage("查询成功！");
        return resultInfo;
    }

    /**
     * @description 处理分类级别关联
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    public List<BaseCategoryVo> doBaseCategoryLevel(List<BaseCategoryVo> firstCategoryList,
                    List<BaseCategoryVo> secondCategoryList,List<BaseCategoryVo> thirdCategoryList,
                    List<CategoryAttrValueMappingVo> categoryAttrValueMappingVoList){
        try{
            //先将三级分类关联的属性列表关联到三级分类中
            if (CollectionUtils.isNotEmpty(thirdCategoryList)){
                for (BaseCategoryVo thirdCategory : thirdCategoryList) {
                    if (thirdCategory != null){
                        List<CategoryAttrValueMappingVo> attributeVoList = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(categoryAttrValueMappingVoList)){
                           for (CategoryAttrValueMappingVo categoryAttrValueMappingVo : categoryAttrValueMappingVoList) {
                                if (categoryAttrValueMappingVoList != null){
                                    if (thirdCategory.getBaseCategoryId().equals(categoryAttrValueMappingVo.getBaseCategoryId())){
                                        attributeVoList.add(categoryAttrValueMappingVo);
                                    }
                                }
                            }
                        }
                        thirdCategory.setCategoryAttrValueMappingVoList(attributeVoList);
                    }
                }
            }

            //先将三级分类关联到二级分类中
            if (CollectionUtils.isNotEmpty(secondCategoryList)) {
                for (BaseCategoryVo secondCategory : secondCategoryList) {
                    if (secondCategory != null){
                        List<BaseCategoryVo> categoryVoList = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(thirdCategoryList)) {
                            for (BaseCategoryVo thirdCategory : thirdCategoryList) {
                                if (thirdCategory !=  null){
                                    if (secondCategory.getBaseCategoryId().equals(thirdCategory.getParentId())) {
                                        categoryVoList.add(thirdCategory);
                                        Integer coreProductNum = thirdCategory.getCoreProductNum() == null ? 0 : thirdCategory.getCoreProductNum();
                                        Integer temporaryProductNum = thirdCategory.getTemporaryProductNum() == null ? 0 : thirdCategory.getTemporaryProductNum();
                                        Integer otherProductNum = thirdCategory.getOtherProductNum() == null ? 0 : thirdCategory.getOtherProductNum();
                                        secondCategory.setCoreProductNum(secondCategory.getCoreProductNum() == null ? coreProductNum : secondCategory.getCoreProductNum() + coreProductNum);
                                        secondCategory.setTemporaryProductNum(secondCategory.getTemporaryProductNum() == null ? temporaryProductNum : secondCategory.getTemporaryProductNum() + temporaryProductNum);
                                        secondCategory.setOtherProductNum(secondCategory.getOtherProductNum() == null ? otherProductNum : secondCategory.getOtherProductNum() + otherProductNum);
                                    }
                                }
                            }
                        }
                        secondCategory.setThirdCategoryList(categoryVoList);
                    }
                }
            }
            //再将二级分类关联到一级分类中
            if (CollectionUtils.isNotEmpty(firstCategoryList)) {
                for (BaseCategoryVo firstCategory : firstCategoryList) {
                    if (firstCategory !=  null){
                        List<BaseCategoryVo> categoryVoList = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(secondCategoryList)) {
                            for (BaseCategoryVo secondCategory : secondCategoryList) {
                                if (secondCategory != null){
                                    if (firstCategory.getBaseCategoryId().equals(secondCategory.getParentId())){
                                        categoryVoList.add(secondCategory);
                                        Integer coreProductNum = firstCategory.getCoreProductNum() == null ? 0 : firstCategory.getCoreProductNum();
                                        Integer temporaryProductNum = firstCategory.getTemporaryProductNum() == null ? 0 : firstCategory.getTemporaryProductNum();
                                        Integer otherProductNum = firstCategory.getOtherProductNum() == null ? 0 : firstCategory.getOtherProductNum();
                                        firstCategory.setCoreProductNum(secondCategory.getCoreProductNum() == null ? coreProductNum : secondCategory.getCoreProductNum() + coreProductNum);
                                        firstCategory.setTemporaryProductNum(secondCategory.getTemporaryProductNum() == null ? temporaryProductNum : secondCategory.getTemporaryProductNum() + temporaryProductNum);
                                        firstCategory.setOtherProductNum(secondCategory.getOtherProductNum() == null ? otherProductNum : secondCategory.getOtherProductNum() + otherProductNum);
                                    }
                                }
                            }
                        }
                        firstCategory.setSecondCategoryList(categoryVoList);
                    }
                }
            }
            //剔除不满足搜索条件的所有分类

            return firstCategoryList;
        }catch (Exception e){
            logger.error("处理分类级别关联",e);
            return null;
        }
    }

    /**
     * 列表页检查是否已经删除
     * @param baseCategoryVo
     * @return
     */
    public ResultInfo checkDelete(BaseCategoryVo baseCategoryVo){
        baseCategoryVo = baseCategoryService.getBaseCategoryByParam(baseCategoryVo.getBaseCategoryId());
        if (CommonConstants.IS_DELETE_1.equals(baseCategoryVo.getIsDeleted())){
            return new ResultInfo(CommonConstants.FAIL_CODE,CommonConstants.FAIL_MSG);
        }
        return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);
    }

    /**
     * @description 获取分类列表
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    @RequestMapping(value = "/getCategoryList")
    @ResponseBody
    public ResultInfo getCategoryList(HttpServletRequest request, HttpServletResponse response){
        try{
            BaseCategoryVo baseCategoryVo = new BaseCategoryVo();
            // 未删除状态
            baseCategoryVo.setIsDeleted(CommonConstants.IS_DELETE_0);
            //paramMap.put("isDeleted", CommonConstants.IS_DELETE_0);
            // 获取一级分类列表
            baseCategoryVo.setBaseCategoryLevel(CommonConstants.CATEGORY_LEVEL_1);
            List<BaseCategoryVo> firstCategoryList = baseCategoryService.getCategoryListPage(baseCategoryVo,null);
            List<BaseCategoryVo> secondCategoryList = null;
            List<BaseCategoryVo> thirdCategoryList = null;
            if (CollectionUtils.isNotEmpty(firstCategoryList)){
                // 获取二级分类列表
                baseCategoryVo.setBaseCategoryLevel(CommonConstants.CATEGORY_LEVEL_2);
                secondCategoryList = baseCategoryService.getCategoryListPage(baseCategoryVo,null);
                if (CollectionUtils.isNotEmpty(secondCategoryList)){
                    // 获取三级分类列表
                    baseCategoryVo.setBaseCategoryLevel(CommonConstants.CATEGORY_LEVEL_3);
                    thirdCategoryList = baseCategoryService.getCategoryListPage(baseCategoryVo,null);
                    if (CollectionUtils.isEmpty(thirdCategoryList) || thirdCategoryList.get(0) == null ||
                            (thirdCategoryList != null && thirdCategoryList.size() == 1 && thirdCategoryList.get(0) != null
                                    && thirdCategoryList.get(0).getBaseCategoryId() == null)) {
                        thirdCategoryList = null;
                    }
                }
            }
            // 处理分类级别关联
            firstCategoryList = this.doBaseCategoryLevel(firstCategoryList,secondCategoryList,thirdCategoryList,null);
            return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG,firstCategoryList);
        }catch (Exception e){
            logger.error("处理分类级别关联",e);
            return null;
        }
    }

    /**
     * @description 检查三级分类下是否有商品，有商品的话保存三级分类是否修改了属性以及属性值
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    @RequestMapping(value = "/checkCategoryAttr")
    @ResponseBody
    public ResultInfo checkCategoryAttr(Integer baseCategoryId,String attrName,String attrValue,
           HttpServletRequest request, HttpServletResponse response){
        try{
            //如果是三级分类，且是编辑后保存，查询分类下是否有商品
            BaseCategoryVo baseCategoryVo = new BaseCategoryVo();
            baseCategoryVo.setIsDeleted(CommonConstants.IS_DELETE_0);
            baseCategoryVo.setBaseCategoryId(baseCategoryId);
            baseCategoryVo.setBaseCategoryLevel(CommonConstants.CATEGORY_LEVEL_3);
            List<BaseCategoryVo> thirdCategoryList = baseCategoryService.getthirdCategoryListById(baseCategoryVo);
            if (CollectionUtils.isEmpty(thirdCategoryList) || thirdCategoryList.get(0) == null ||
                    (thirdCategoryList != null && thirdCategoryList.size() == 1 && thirdCategoryList.get(0) != null
                            && thirdCategoryList.get(0).getBaseCategoryId() == null)){
                thirdCategoryList = null;
            }else{
                BaseCategoryVo baseCategoryVoTemp = thirdCategoryList.get(0);
                Integer coreProductNum = baseCategoryVoTemp.getCoreProductNum() == null ? 0 : baseCategoryVoTemp.getCoreProductNum();
                Integer temporaryProductNum = baseCategoryVoTemp.getTemporaryProductNum() == null ? 0 : baseCategoryVoTemp.getTemporaryProductNum();
                Integer otherProductNum = baseCategoryVoTemp.getOtherProductNum() == null ? 0 : baseCategoryVoTemp.getOtherProductNum();
                if (coreProductNum > 0 || temporaryProductNum > 0
                        || otherProductNum > 0){
                    //处理属性与属性值
                    String[] attrIdArray = attrName.split(",");
                    String[] attrValueIdsArray = attrValue.split(",");
                    baseCategoryVo.setBaseAttributeId(attrIdArray);
                    baseCategoryVo.setBaseAttributeValueIds(attrValueIdsArray);
                    List<BaseAttributeVo> baseAttributeVoList = baseCategoryService.doAttrAndValueToList(baseCategoryVo);
                    if (CollectionUtils.isNotEmpty(baseAttributeVoList)){
                        List<CategoryAttrValueMappingVo> categoryAttrValueMappingVos = new ArrayList<>();
                        for (BaseAttributeVo attributeVo : baseAttributeVoList){
                            String[] attrValueIdArray = attributeVo.getBaseAttributeValueIds().split("@");
                            for (String attrValueId : attrValueIdArray){
                                CategoryAttrValueMappingVo categoryAttrValueMappingVo = new CategoryAttrValueMappingVo();
                                categoryAttrValueMappingVo.setBaseAttributeId(attributeVo.getBaseAttributeId());
                                if (attrValueId!=null&& !"".equals(attrValueId)){
                                    categoryAttrValueMappingVo.setBaseAttributeValueId(Integer.valueOf(attrValueId));
                                }
                                categoryAttrValueMappingVos.add(categoryAttrValueMappingVo);
                            }
                        }
                        //查询该分类下的属性与属性值关联列表
                        List<CategoryAttrValueMapping> categoryAttrValueMappingVoList = baseCategoryService.getCategoryAttrValueMappingVoList(baseCategoryId);
                        if (CollectionUtils.isNotEmpty(categoryAttrValueMappingVos) && CollectionUtils.isNotEmpty(categoryAttrValueMappingVoList)){
                            Integer categoryAttrValueMappingNum = 0;
                            for (CategoryAttrValueMapping categoryAttrValueMapping : categoryAttrValueMappingVoList){
                                for (CategoryAttrValueMappingVo categoryAttrValueMappingVo : categoryAttrValueMappingVos){
                                    if (categoryAttrValueMapping.getBaseAttributeId().equals(categoryAttrValueMappingVo.getBaseAttributeId())
                                         && categoryAttrValueMapping.getBaseAttributeValueId().equals(categoryAttrValueMappingVo.getBaseAttributeValueId())){
                                        categoryAttrValueMappingNum = categoryAttrValueMappingNum + 1;
                                    }
                                }
                            }
                            if (categoryAttrValueMappingNum != categoryAttrValueMappingVoList.size()){
                                return new ResultInfo(CommonConstants.FAIL_CODE,"修改类目，会导致所有子商品参数信息丢失且不可恢复，并且会下架所有子商品，请确认是否继续修改？");
                            }
                        }
                    }
                }
            }
            return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);
        }catch (Exception e){
            logger.error("检查三级分类下上商品异常：",e);
            return null;
        }
    }

    /**
     *  根据关键词（商品名/分类名/国标码）获取分类列表
     * @param keyWords
     * @param request
     * @param response
     * @return
     * @author cooper.xu
     */
    @RequestMapping(value = "/getCategoryListByKeyWords")
    @ResponseBody
    public ResultInfo getCategoryListByKeyWords(String keyWords, HttpServletRequest request, HttpServletResponse response){
        try{
            if (keyWords == null || "".equals(keyWords)){
                return new ResultInfo(CommonConstants.FAIL_CODE,"关键词不可为空");
            }else {
                List<BaseCategoryVo> baseCategoryVoList = baseCategoryService.getCategoryListByKeyWords(keyWords);
                return new ResultInfo(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MSG, baseCategoryVoList);
            }
        }catch (Exception e){
            logger.error("根据关键词（商品名/分类名/国标码）获取分类列表异常：",e);
            return null;
        }
    }
}
