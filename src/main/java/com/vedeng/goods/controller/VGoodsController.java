package com.vedeng.goods.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.unbiz.fluentvalidator.Result;
import com.beust.jcommander.internal.Maps;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.common.constant.goods.GoodsConstants;
import com.vedeng.common.constant.goods.SpuLevelEnum;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.CustomBigDecimalEditor;
import com.vedeng.common.model.CustomDateEditor;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultJSON;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.BigDecimalCommonUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.validator.goods.SpuValidate;
import com.vedeng.department.model.DepartmentsHospitalGenerate;
import com.vedeng.department.service.DepartmentsHospitalService;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.firstengage.service.FirstEngageService;
import com.vedeng.goods.command.SkuAddCommand;
import com.vedeng.goods.command.SpuAddCommand;
import com.vedeng.goods.command.SpuSearchCommand;
import com.vedeng.goods.dao.BrandGenerateMapper;
import com.vedeng.goods.dao.CoreSpuGenerateExtendMapper;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.model.*;
import com.vedeng.goods.model.dto.CoreSkuBaseDTO;
import com.vedeng.goods.model.dto.CoreSpuBaseDTO;
import com.vedeng.goods.model.vo.*;
import com.vedeng.goods.service.*;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.RoleService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.TraderSupplierService;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/goods/vgoods"})
public class VGoodsController extends BaseController {
    @Autowired
    VgoodsService goodsService;
    @Autowired
    private DepartmentsHospitalService departmentsHospitalService;

    @Autowired
    BaseCategoryService baseCategoryService;
    @Autowired
    FirstEngageService firstEngageService;
    @Autowired
    UnitService unitService;
    @Autowired
    BaseGoodsService baseGoodsService;
    @Autowired
    UserService userService;
    @Autowired
    BrandGenerateMapper brandGenerateMapper;
@Autowired
    WarehouseStockService warehouseStockService;

    @Autowired
    private RoleService roleService;

    @Value("${api_http}")
    protected String api_http;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomBigDecimalEditor bigDecimalEditor = new CustomBigDecimalEditor();
        CustomDateEditor dateEditor = new CustomDateEditor();
        binder.registerCustomEditor(BigDecimal.class, bigDecimalEditor);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @RequestMapping(value = "/list")
    public String index(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        Page page = getPageTag(request, spuCommand.getPageNo(), 5);
        List<CoreSpuBaseVO> list = goodsService.selectSpuListPage(spuCommand, page);
        try {
            model.addAttribute("spuTypeList", getSysOptionDefinitionInNewGoodsFlow());
            //所有的分配人
            List<User> assUser = userService.selectAllAssignUser();
            model.addAttribute("assUser", assUser);

        } catch (Exception e) {
            logger.error("商品列表页，搜索条件初始化失败。", e);
        }
        model.addAttribute("page", page);
        model.addAttribute("list", list);
        model.addAttribute("command",spuCommand);
        return "goods/vgoods/list";
    }

    @Autowired
    CoreSpuGenerateExtendMapper mapper;
    @Autowired
    com.vedeng.goods.dao.RCategoryJUserMapper rCategoryJUserMapper;

    // TODO
    @RequestMapping(value = "/export")
    public void export(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand, HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        List<String> list = Files.readAllLines(Paths.get(Resources.getResourceURL("process.txt").toURI()));
        List<String> aaa = Lists.newArrayList();
        for (String line : list) {
            String cell[] = StringUtils.split(line, "\t");
            System.out.println(cell.length);
            String name1 = cell[0];
            String name3 = cell[2];
            String name2 = cell[1];
            Integer user1 = NumberUtils.toInt(cell[3]);
            Integer catId = 0;
            try {
                catId = mapper.selectCateId(name3, name2, name1);
            } catch (Exception e) {
                aaa.add(name1 + "\t" + name2 + "\t" + name3 + "\n");
                continue;
            }

            System.out.println(catId);
            RCategoryJUser jj = new RCategoryJUser();
            jj.setCategoryId(catId);
            jj.setUserId(user1);
            rCategoryJUserMapper.insert(jj);
            if (cell.length == 5) {
                Integer user2 = NumberUtils.toInt(cell[3]);
                RCategoryJUser jj2 = new RCategoryJUser();
                jj2.setCategoryId(catId);
                jj2.setUserId(user2);
                rCategoryJUserMapper.insert(jj2);
            }
        }
        System.out.println(StringUtils.join(aaa));
        // TODO rainy
    }

    @RequestMapping(value = "/listSku")
    @ResponseBody
    public ResultJSON listsku(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (spuCommand.getSpuId() == null) {
            return ResultJSON.failed().message("spuId不能为空");
        }
        try {
             Page pp = new  Page(spuCommand.getPageNo(),spuCommand.getPageSize() == 10 ? GoodsConstants.SKU_PAGE_SIZE : spuCommand.getPageSize());
            Page page = getPageTag(request, spuCommand.getPageNo(), GoodsConstants.SKU_PAGE_SIZE);

            List<CoreSkuBaseVO> list = goodsService.selectSkuListPage(spuCommand, pp);
            page.setTotalRecord(NumberUtils.toInt(pp.getTotalRecord() + ""));
            return ResultJSON.success().dataWithPage(list, page);
        } catch (Exception e) {
            logger.error("count error" + spuCommand.getSpuId(), e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/productCompany")
    @ResponseBody
    public ResultJSON productCompany(Model model, String productCompanyName,
                                     HttpServletRequest request, HttpServletResponse response) {
        Map<String, Long> map = Maps.newHashMap();
        try {
            List<Map<String, Object>> companies = firstEngageService.getallcompany(productCompanyName);
            return ResultJSON.success().data(companies);
        } catch (Exception e) {
            logger.error("productCompany error" + productCompanyName, e);
            return ResultJSON.failed(e).data(map);
        }
    }

    @RequestMapping(value = "/searchSkuWithDepartment")
    @ResponseBody
    public ResultJSON searchSkuWithDepartment(Model model, String skuName, @ModelAttribute("command") SpuSearchCommand spuCommand,
                                              HttpServletRequest request, HttpServletResponse response) {
        Map<String, Long> map = Maps.newHashMap();
        PageHelper.startPage(spuCommand.getPageNo(), spuCommand.getPageSize());
        try {
            List<Map<String, Object>> companies = goodsService.searchSkuWithDepartment(skuName);
            return ResultJSON.success().data(companies);
        } catch (Exception e) {
            logger.error("productCompany error" + skuName, e);
            return ResultJSON.failed(e).data(map);
        }
    }


    @RequestMapping(value = "/departmentsHospital")
    @ResponseBody
    public ResultJSON departmentsHospital(Model model, String departmentName,
                                          HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Map<String, Object>> companies = Lists.newArrayList();
            List<DepartmentsHospitalGenerate> departmentsHospitalList = departmentsHospitalService.getAllDepartmentsHospital(departmentName, 5);
            if (CollectionUtils.isNotEmpty(departmentsHospitalList)) {
                for (DepartmentsHospitalGenerate generate : departmentsHospitalList) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("value", generate.getDepartmentId());
                    map.put("label", generate.getDepartmentName());
                    companies.add(map);
                }
            }
            return ResultJSON.success().data(companies);
        } catch (Exception e) {
            logger.error("departmentsHospital error" + departmentName, e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/count")
    @ResponseBody
    public ResultJSON countAll(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand,
                               HttpServletRequest request, HttpServletResponse response) {
        Map<String, Long> map = Maps.newHashMap();
        try {
            long newCount = goodsService.countSpuByCheckStatus(GoodsCheckStatusEnum.NEW.getStatus());
            long preCount = goodsService.countSpuByCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
            long rejectCount = goodsService.countSpuByCheckStatus(GoodsCheckStatusEnum.REJECT.getStatus());
            long approveCount = goodsService.countSpuByCheckStatus(GoodsCheckStatusEnum.APPROVE.getStatus());
            long allCount = newCount + preCount + rejectCount + approveCount;
            map.put("newCount", newCount);
            map.put("preCount", preCount);
            map.put("rejectCount", rejectCount);
            map.put("approveCount", approveCount);
            map.put("allCount", allCount);
            return ResultJSON.success().data(map);
        } catch (Exception e) {
            logger.error("count error" + spuCommand.getSpuId(), e);
            return ResultJSON.failed(e).data(map);
        }
    }

    /**
     * 新商品流列表，获取商品类型信息，去除"高值耗材"和"其他"
     * @return 商品类型
     */
    private List<SysOptionDefinition> getSysOptionDefinitionInNewGoodsFlow(){
        return getSysOptionDefinitionList(SysOptionConstant.ID_315)
                .stream()
                //过滤掉"高值耗材"和"其他"这两个商品类型筛选项
                .filter(item -> !item.getSysOptionDefinitionId().equals(653) && !item.getSysOptionDefinitionId().equals(319))
                .collect(Collectors.toList());
    }

    private void initEditSpuPage(SpuAddCommand spuCommand) {
        spuCommand.setSpuTypeList(getSysOptionDefinitionInNewGoodsFlow());
        BrandGenerate brandGenerate = brandGenerateMapper.selectByPrimaryKey(spuCommand.getBrandId());
        if (brandGenerate != null) {
            //TODO
            if (StringUtils.isNotBlank(brandGenerate.getBrandName())) {
                spuCommand.setBrandName(brandGenerate.getBrandName());
            } else {
                spuCommand.setBrandName(brandGenerate.getBrandNameEn());
            }
        }
        // 所有科室
        List<DepartmentsHospitalGenerate> departmentsHospitalList = departmentsHospitalService
                .getAllDepartmentsHospital("", 1000);
        List<DepartmentsHospitalGenerateVO> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(departmentsHospitalList)) {
            for (DepartmentsHospitalGenerate g : departmentsHospitalList) {
                DepartmentsHospitalGenerateVO vo = new DepartmentsHospitalGenerateVO();
                BeanUtils.copyProperties(g, vo);
                if (ArrayUtils.contains(spuCommand.getDepartmentIds(), g.getDepartmentId())) {
                    vo.setSelected(true);
                }
                list.add(vo);
            }
        }
        spuCommand.setDepartmentsHospitalList(list);
        // 所有属性

        spuCommand.setBaseAttributes(goodsService.getAttributeInfoByCategoryId(spuCommand.getCategoryId()));
        if (CollectionUtils.isNotEmpty(spuCommand.getBaseAttributes())) {
            for (BaseAttributeVo vo : spuCommand.getBaseAttributes()) {
                if (ArrayUtils.contains(spuCommand.getBaseAttributeIds(), vo.getBaseAttributeId())) {
                    vo.setSelected(true);
                }
            }
        }
        if (getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_HAOCAI).equals(spuCommand.getSpuType())
                || getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_3).equals(spuCommand.getSpuType())
                || getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_4).equals(spuCommand.getSpuType())
        ) {
            spuCommand.setSkuType(GoodsConstants.SKU_TYPE_CONSUMABLES);
        } else {
            spuCommand.setSkuType(GoodsConstants.SKU_TYPE_INSTRUMENT);
        }
    }

    @RequestMapping(value = "/getAttributeInfoByCategoryId")
    @ResponseBody
    public ResultJSON getAttributeInfoByCategoryId(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand,
                                                   HttpServletRequest request, HttpServletResponse response) {
        try {
            List<BaseAttributeVo> list = goodsService.getAttributeInfoByCategoryId(spuCommand.getCategoryId());
            return ResultJSON.success().data(list).message(CommonConstants.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error("getAttributeInfoByCategoryId error" + spuCommand.getSpuId(), e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/searchFirstEngages")
    @ResponseBody
    public ResultJSON searchFirstEngages(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand, HttpServletRequest request) {
        try {
            Page page = getPageTag(request, spuCommand.getPageNo(), spuCommand.getPageSize());
            List<Map<String, Object>> result = goodsService.searchFirstEngageListPage(spuCommand.getSearchValue(), page);
            return ResultJSON.success().data(result).message(CommonConstants.SUCCESS_MSG);
        } catch (
                Exception e) {
            logger.error("searchFirstEngage error" + spuCommand.getSearchValue(), e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/getFirstEngageById")
    @ResponseBody
    public ResultJSON getFirstEngageById(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand) {
        try {
            FirstEngage engage = firstEngageService.getFirstSearchBaseInfo(spuCommand.getFirstEngageId());
            if(engage==null){
                return ResultJSON.failed();
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("firstEngageId", engage.getFirstEngageId());
            map.put("effectStartDate", engage.getEffectStartDate());
            map.put("effectEndDate", engage.getEffectEndDate());
            map.put("productCompanyChineseName", engage.getProductCompanyChineseName());
            map.put("oldStandardCategoryName", engage.getOldStandardCategoryName());
            map.put("newStandardCategoryName", engage.getNewStandardCategoryName());
            map.put("registrationNumber", engage.getRegistrationNumber());
            //审核状态
            map.put("checkStatus", GoodsCheckStatusEnum.statusName(engage.getStatus()));
            map.put("checkStatusInt", engage.getStatus());
            // 管理分类

            SysOptionDefinition option = getSysOptionDefinition(NumberUtils.toInt(engage.getManageCategoryLevelShow()));
            if (option != null) {
                map.put("manageCategoryLevel", option.getTitle());
            }

            return ResultJSON.success().data(map).message(CommonConstants.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error("searchFirstEngage error" + spuCommand.getFirstEngageId(), e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/getFeeItemByDeptIds")
    @ResponseBody
    public ResultJSON getFeeItemByDeptIds(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand
            , Integer[] departmentIds) {
        if (ArrayUtils.isEmpty(departmentIds)) {
            return ResultJSON.failed().message("科室不能为空");
        }
        try {
            Map<String, Object> param = Maps.newHashMap();
            List list = Lists.newArrayList();
            for (Integer deptId : departmentIds) {
                list.add(Maps.newHashMap("departmentId", deptId));
            }
            param.put("hospitalList", list);
            param.put("isDelete", "0");
            return ResultJSON.success().data(departmentsHospitalService.getDepartmentsHospitalByParam(param)).message(CommonConstants.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error("searchFirstEngage error" + spuCommand.getFirstEngageId(), e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/addTempSpu")
    public ModelAndView addTempSpu(@ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        spuCommand.setSpuLevel(SpuLevelEnum.TEMP.spuLevel());
        return addSpu(spuCommand, request, response);
    }

    //转为普通商品
    @RequestMapping(value = "/changeTempToCoreSpu")
    public ModelAndView changeTempToCoreSpu(@ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        ModelAndView mv = addSpu(spuCommand, request, response);
        spuCommand.setSpuLevel(SpuLevelEnum.CORE.spuLevel());

        return mv;
    }

    @RequestMapping(value = "/addSpu")
    public ModelAndView addSpu(@ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
                         HttpServletResponse response) throws Exception {


        try {
            goodsService.initSpu(spuCommand);
            initEditSpuPage(spuCommand);
        } catch (ShowErrorMsgException e) {
            logger.error("addSpu addSpu", e);
            spuCommand.setErrors(Lists.newArrayList(e.getMessage()));
        }
        ModelAndView mv=new ModelAndView();
        mv.setViewName("goods/vgoods/spu/spu_edit");
        List<User> productOwnerUsers=userService.selectAllAssignUser();
        mv.addObject("productOwnerUsers",productOwnerUsers);
        return mv;
    }

    @RequestMapping(value = "/viewSpu")
    public String viewSpu(Model model, @ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        try {
            goodsService.initSpu(spuCommand);

            model.addAttribute("showType", spuCommand.getShowType());

            model.addAttribute("brand", brandGenerateMapper.selectByPrimaryKey(spuCommand.getBrandId()));

            model.addAttribute("cateName", baseCategoryService.getOrganizedCategoryNameById(spuCommand.getCategoryId()));

            List<LogCheckGenerate> logCheckGenerateList = goodsService.listSpuCheckLog(spuCommand.getSpuId());


            model.addAttribute("logCheckGenerateList", logCheckGenerateList);
            FirstEngage firstEngage = firstEngageService.getFirstSearchBaseInfo(spuCommand.getFirstEngageId());
            if (firstEngage != null) {
                model.addAttribute("firstEngage", firstEngage);
                SysOptionDefinition option = getSysOptionDefinition(NumberUtils.toInt(firstEngage.getManageCategoryLevelShow()));
                if (option != null) {
                    firstEngage.setManageCategoryLevelShow(option.getTitle());
                }
            }
            initEditSpuPage(spuCommand);
        } catch (ShowErrorMsgException e) {
            spuCommand.setErrors(Lists.newArrayList(e.getMessage()));
        }
        return "goods/vgoods/spu/spu_view";
    }


    //    @RequestMapping(value = "/saveTempSpu")
//    @ResponseBody
//    public ResultJSON saveTempSpu(Model model, @ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
//                                  HttpServletResponse response) throws Exception {
//        spuCommand.setSpuLevel(SpuLevelEnum.TEMP.spuLevel());
//        saveSpu(model, spuCommand, request, response);
//        if(CollectionUtils.isNotEmpty(spuCommand.getErrors())){
//            return ResultJSON.failed().message(StringUtils.join(spuCommand.getErrors(),","));
//        }else{
//            return ResultJSON.success();
//        }
//    }
    @RequestMapping(value = "/saveTempSpu")
    public String saveTempSpu(Model model, @ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        spuCommand.setSpuLevel(SpuLevelEnum.TEMP.spuLevel());
        return saveSpu(model, spuCommand, request, response);
    }


    @RequestMapping(value = "/saveSpu")
    public String saveSpu(Model model, @ModelAttribute("command") SpuAddCommand spuCommand,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result ret = SpuValidate.check(spuCommand);
        if (!ret.isSuccess()) {
            spuCommand.setErrors(ret.getErrors());
            initEditSpuPage(spuCommand);
            transCommandFileToViewFile(spuCommand);

            List<User> productOwnerUsers=userService.selectAllAssignUser();
            model.addAttribute("productOwnerUsers",productOwnerUsers);
            return "goods/vgoods/spu/spu_edit";
        }
        try {
            goodsService.saveSpu(spuCommand);
            //重新加载页面数据
            initEditSpuPage(spuCommand);
            transCommandFileToViewFile(spuCommand);
            spuCommand.setTips("操作成功");
            return "redirect:/goods/vgoods/viewSpu.do?spuId=" + spuCommand.getSpuId();
        } catch (ShowErrorMsgException e) {
            goodsService.initSpu(spuCommand);
            initEditSpuPage(spuCommand);
            transCommandFileToViewFile(spuCommand);
            spuCommand.setErrors(Lists.newArrayList(e.getMessage()));
            List<User> productOwnerUsers=userService.selectAllAssignUser();
            model.addAttribute("productOwnerUsers",productOwnerUsers);
        }
        return "goods/vgoods/spu/spu_edit";
    }

    private void transCommandFileToViewFile(SpuAddCommand spuCommand) {
        // 产品检测报告
        spuCommand.setSpuCheckFileJson(transFileInfo(spuCommand.getSpuCheckFiles()));
        // 产品专利文件
        spuCommand.setSpuPatentFilesJson(transFileInfo(spuCommand.getSpuPatentFiles()));
    }

//    // 提交审核
//
//    /**
//     * 需要传输spuName
//     *
//     * @param spuCommand
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/submitToCheck")
//    public String submitToCheck(Model model, @ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
//                                HttpServletResponse response) throws Exception {
//        Result ret = SpuValidate.checkSubmitCheck(spuCommand);
//        if (!ret.isSuccess()) {
//            spuCommand.setTips(StringUtils.join(ret.getErrors(), ","));
//            return viewSpu(model, spuCommand, request, response);
//        }
//        try {
//            goodsService.submitToCheck(spuCommand);
//            spuCommand.setTips("操作成功");
//            String url = "./goods/vgoods/viewSpu.do?spuId=" + spuCommand.getSpuId();
//            Map<String, String> map = new HashMap<>();
//            map.put("spuName", spuCommand.getShowName());
//            // TODO
//            // MessageUtil.sendMessage(75, varifyUserList, map, url,
//            // spuCommand.getUser().getUsername());//
//        } catch (ShowErrorMsgException e) {
//            spuCommand.setErrors(Lists.newArrayList(e.getMessage()));
//        }
//        return viewSpu(model, spuCommand, request, response);
//    }

    /**
     * 审核操作
     */
    @RequestMapping(value = "/checkSpu")
    @ResponseBody
    public ResultJSON checkSpu(Model model, @ModelAttribute("command") SpuAddCommand spuCommand, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        Result ret = SpuValidate.checkCheckSpu(spuCommand);
        if (!ret.isSuccess()) {
            spuCommand.setTips(StringUtils.join(ret.getErrors(), ","));
            return ResultJSON.failed().message(StringUtils.join(ret.getErrors(), ","));
        }
        try {
            goodsService.checkSpu(spuCommand);
        } catch (Exception e) {
            logger.error("checkSpu", e);
            return ResultJSON.failed().message(e.getMessage());
        }
        return ResultJSON.success().message("操作成功");
    }

    /**
     * 审核操作
     */
    @RequestMapping(value = "/checkSku")
    @ResponseBody
    public ResultJSON checkSku(Model model, @ModelAttribute("command") SkuAddCommand command, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        Result ret = SpuValidate.checkCheckSku(command);
        if (!ret.isSuccess()) {
            command.setTips(StringUtils.join(ret.getErrors(), ","));
            return ResultJSON.failed().message(StringUtils.join(ret.getErrors(), ","));
        }
        try {
            goodsService.checkSku(command);
        } catch (Exception e) {
            logger.error("checkSku", e);
            return ResultJSON.failed().message(e.getMessage());
        }
        return ResultJSON.success().message("操作成功");
    }

    @RequestMapping(value = "/addSku")
    public String addSku(Model model, @ModelAttribute("command") SkuAddCommand command,
                         @ModelAttribute("skuGenerate") CoreSkuGenerate skuGenerate, HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        try {
            //初始化页面SPU信息
            initBaseSpuPage(model, command);
            //初始化页面SKU信息
            //编辑
            if (command.getSkuId() != null && command.getSkuId() > 0) {
                CoreSkuGenerate generate = goodsService.initSku(command);
                model.addAttribute("skuGenerate", generate);
                if (generate != null) {
                    model.addAttribute("minOrder", generate.getMinOrder() != null ? generate.getMinOrder().intValue() : "");
                }
            }
            //初始化页面其他信息
            initSkuPage(model, command);
        } catch (ShowErrorMsgException e) {
            command.setErrors(Lists.newArrayList(e.getErrorMsg()));
        }
        return "goods/vgoods/sku/sku_edit";
    }

    @RequestMapping(value = "/saveTempSku")
    @ResponseBody
    public ResultJSON saveTempSku(Model model, @ModelAttribute("command") SkuAddCommand spuCommand,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result ret = SpuValidate.checkTempSku(spuCommand);
        if (!ret.isSuccess()) {
            return ResultJSON.failed();
        }
        try {
            goodsService.saveTempSku(spuCommand);
        } catch (Exception e) {
            logger.error("saveTempSku", e);
            return ResultJSON.failed(e);
        }
        return ResultJSON.success();
    }

    @RequestMapping(value = "/saveSku")
    public String saveSku(Model model, @ModelAttribute("command") SkuAddCommand command,
                          @ModelAttribute("skuGenerate") CoreSkuGenerate skuGenerate, HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        //初始化页面SPU信息
        initBaseSpuPage(model, command);
        Result ret = SpuValidate.checkSku(command, skuGenerate);
        if (ret.isSuccess()) {
            try {
                goodsService.saveSku(command, skuGenerate);
                command.setBaseAttributeValueId(null);//加载数据库里面的value
                command.setTips("操作成功");
                /**
                 * 在新增或者编辑SKU时需要向供应管理组助理推送审核站内信
                 */
                List<Integer> userIds = roleService.getUserIdByRoleName("供应管理组助理", sessUser.getCompanyId());
                HashMap<String, String> params = new HashMap<>();
                params.put("sku",skuGenerate.getSkuNo());
                MessageUtil.sendMessage(12,userIds,params,"/goods/vgoods/viewSku.do?skuId=" + command.getSkuId());
                return "redirect:/goods/vgoods/viewSku.do?skuId=" + command.getSkuId();
            } catch (Exception e) {
                logger.error("addSkuPost", e);
                command.setErrors(Lists.newArrayList(e.getMessage()));
            }
            //command.setBaseAttributeValueId(null);//加载数据库里面的value
        }
        initSkuPage(model, command);
        initCommandFile(command, skuGenerate);

        return "goods/vgoods/sku/sku_edit";
    }

    private void initCommandFile(SkuAddCommand command, CoreSkuGenerate skuGenerate) {
        // 产品检测报告
        command.setSkuCheckFilesJson(transFileInfo(command.getSkuCheckFiles()));
        // 产品专利文件
        command.setSkuPatentFilesJson(transFileInfo(command.getSkuPatentFiles()));
        // 产品核心零部件
        command.setCorePartPriceFileJson(transFileInfo(command.getCorePartPriceFile()));
    }

    @RequestMapping(value = "/viewSku")
    public String viewSku(Model model, @ModelAttribute("command") SkuAddCommand command, HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        try {
            User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            List<Role> roles = roleService.getUserRoles(sessUser);
            Boolean isSupplyAssistant = false;
            if (roles != null && CollectionUtils.isNotEmpty(roles)){
                for (Role role : roles) {
                    if ("供应管理组助理".equals(role.getRoleName())){
                        isSupplyAssistant = true;
                        break;
                    }
                }
            }
            model.addAttribute("isSupplyAssistant", isSupplyAssistant);

            //初始化页面SPU信息
            CoreSpuBaseVO spuBaseVO= initBaseSpuPage(model, command);

            model.addAttribute("showType", command.getShowType());
            //初始化页面SKU信息
            CoreSkuGenerate skuGenerate = goodsService.initSku(command);
            model.addAttribute("skuGenerate", skuGenerate);
            //初始化页面其他信息
            initSkuPage(model, command);

            // model.addAttribute("brand", brandGenerateMapper.selectByPrimaryKey(skuGenerate.getBrandId()));

            //model.addAttribute("cateName", baseCategoryService.getOrganizedCategoryNameById(spuCommand.getCategoryId()));

            List<LogCheckGenerate> logCheckGenerateList = goodsService.listSkuCheckLog(command.getSkuId());
            model.addAttribute("logCheckGenerateList", logCheckGenerateList);
            //VDERP-1793
            BaseCategoryVo categoryVo = baseCategoryService.getBaseCategoryByParam(spuBaseVO.getCategoryId());
            if (categoryVo != null) {
                model.addAttribute("categoryType",categoryVo.getBaseCategoryType());//
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", sessUser.getUserId());
            paramMap.put("firstEngageId", spuBaseVO.getFirstEngageId());
            FirstEngage firstEngage = firstEngageService.getFirstSearchDetailOnlyZczAttachment(paramMap,spuBaseVO.getFirstEngageId());
            if (firstEngage != null) {
                model.addAttribute("firstEngage", firstEngage);
                SysOptionDefinition option = getSysOptionDefinition(NumberUtils.toInt(firstEngage.getManageCategoryLevelShow()));
                if (option != null) {
                    firstEngage.setManageCategoryLevelShow(option.getTitle());
                }
            }
model.addAttribute("api_http",api_http);


            //单位
            //TODO 以下为挪过来的老代码 暂时只给老产品开启查询
            // if (command.getSkuId() < 30000) {


            Integer type = 0;// 销售
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("goodsId", command.getSkuId());
            map.put("type", 0);// 销售
            List<GoodsChannelPrice> goodsChannelPriceList = goodsChannelPriceService.getGoodsChannelPriceByGoodsId(map);
            model.addAttribute("goodsChannelPriceList", goodsChannelPriceList);


            Integer type2 = 1;// 采购
            Map<String, Integer> map2 = new HashMap<String, Integer>();
            map2.put("goodsId", command.getSkuId());
            map2.put("type", 1);// 采购
            List<GoodsChannelPrice> goodsChannelPriceList2 = goodsChannelPriceService.getGoodsChannelPriceByGoodsId(map2);
            model.addAttribute("goodsChannelPriceList2", goodsChannelPriceList2);


            Goods goods = new Goods();
            goods.setGoodsId(command.getSkuId());
           Map<String, WarehouseStock> stockInfoMap= warehouseStockService.getStockInfo(Lists.newArrayList("V"+command.getSkuId()));
            //goods = oldGoodsService.getGoodsById(goods);
          //  if (goods != null) {
                WarehouseStock stockInfo=stockInfoMap.get("V"+command.getSkuId());
                goods.setStockNum(stockInfo.getStockNum());
                goods.setOrderOccupy(stockInfo.getOccupyNum());

                model.addAttribute("goods", goods);
                // 获取主供应商列表信息
                List<TraderSupplierVo> mainSupplyList = traderSupplierService
                        .getMainSupplyListByGoodsId(command.getSkuId());
                model.addAttribute("mainSupplyList", mainSupplyList);
                // 获取产品结算价
                GoodsSettlementPrice goodsSettlementPrice = new GoodsSettlementPrice();
                goodsSettlementPrice.setCompanyId(1);
                goodsSettlementPrice.setGoodsId(command.getSkuId());
                GoodsSettlementPrice goodsSettlementPriceInfo = goodsSettlementPriceService
                        .getGoodsSettlePriceByGoods(goodsSettlementPrice);

                model.addAttribute("goodsSettlementPriceInfo", goodsSettlementPriceInfo);
           // }


            // }
        } catch (Exception e) {
            logger.error("viewSku", e);
            command.setErrors(Lists.newArrayList(e.getMessage()));
        }
        return "goods/vgoods/sku/sku_view";
    }

    @RequestMapping(value = "/viewTempSkuAjax")
    @ResponseBody
    public ResultJSON viewTempSkuAjax(Model model, @ModelAttribute("command") SkuAddCommand command, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        try {
            //初始化页面SKU信息
            CoreSkuGenerate skuGenerate = goodsService.initSku(command);
            Map<String, String> map = Maps.newHashMap();
            if (StringUtils.isNotBlank(skuGenerate.getModel())) {
                map.put("skuInfo", skuGenerate.getModel());
            } else {
                map.put("skuInfo", skuGenerate.getSpec());
            }
            return ResultJSON.success().data(map);
        } catch (Exception e) {
            logger.error("viewSku", e);
            return ResultJSON.failed(e);
        }
    }

    private CoreSpuBaseVO initBaseSpuPage(Model model, SkuAddCommand command) {
        //所有SPU下的属性
        CoreSpuBaseDTO coreSpuDto = baseGoodsService.selectSpuBaseById(command.getSpuId());
        if (coreSpuDto == null) {
            if (command.getSkuId() != null) {
                CoreSkuBaseDTO skuBaseDTO = baseGoodsService.selectSkuBaseById(command.getSkuId());
                if (skuBaseDTO != null) {
                    command.setSpuId(skuBaseDTO.getSpuId());
                    coreSpuDto = baseGoodsService.selectSpuBaseById(skuBaseDTO.getSpuId());
                }
            }
        }
        if (coreSpuDto == null) {
            throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "找不到对应的SPU！");
        }
        CoreSpuBaseVO spuBaseVO = new CoreSpuBaseVO();
        BeanUtils.copyProperties(coreSpuDto, spuBaseVO);
        model.addAttribute("coreSpuDto", spuBaseVO);


        if (getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_HAOCAI).equals(NumberUtils.toInt(coreSpuDto.getSpuType()))
                || getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_3).equals(NumberUtils.toInt(coreSpuDto.getSpuType()))
                || getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_4).equals(NumberUtils.toInt(coreSpuDto.getSpuType()))
        ) {
            command.setSkuType(GoodsConstants.SKU_TYPE_CONSUMABLES);
        } else {
            command.setSkuType(GoodsConstants.SKU_TYPE_INSTRUMENT);
        }
        model.addAttribute("spuTypeList", getSysOptionDefinitionList(SysOptionConstant.ID_315));
        return spuBaseVO;
    }

    private void initSkuPage(Model model, SkuAddCommand command) {
        //所有SKU下的属性
        List<BaseAttributeVo> list = goodsService.selectAllAttributeBySkuId(command.getSpuId(), command.getSkuId());
        if (CollectionUtils.isNotEmpty(list) && !ArrayUtils.isEmpty(command.getBaseAttributeValueId())) {
            for (BaseAttributeVo vo : list) {
                if (CollectionUtils.isNotEmpty(vo.getAttrValue())) {
                    for (BaseAttributeValueVo valueVo : vo.getAttrValue()) {
                        if (ArrayUtils.contains(command.getBaseAttributeValueId(), valueVo.getBaseAttributeValueId())) {
                            valueVo.setSelected(true);
                        } else {
                            valueVo.setSelected(false);
                        }
                    }
                }
            }
        }
        model.addAttribute("baseAttributeVoList", list);
        //所有单位
        Unit unit = new Unit();
        unit.setCompanyId(command.getUser().getCompanyId());
        List<Unit> unitList = unitService.getAllUnitList(unit);
        model.addAttribute("unitList", unitList);
    }

    @RequestMapping(value = "/deleteSpu")
    @ResponseBody
    public ResultJSON deleteSpu(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand,
                                HttpServletRequest request, HttpServletResponse response) {
        try {
            Result ret = SpuValidate.checkDeleteSpu(spuCommand);
            if (!ret.isSuccess()) {
                return ResultJSON.failed().message(StringUtils.join(ret.getErrors(), ","));
            }
            goodsService.deleteSpu(spuCommand);
            return ResultJSON.success().message(CommonConstants.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error("error", e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/deleteSku")
    @ResponseBody
    public ResultJSON deleteSku(Model model, @ModelAttribute("command") SpuSearchCommand spuCommand,
                                HttpServletRequest request, HttpServletResponse response) {
        try {
            Result ret = SpuValidate.checkDeleteSku(spuCommand);
            if (!ret.isSuccess()) {
                return ResultJSON.failed().message(StringUtils.join(ret.getErrors(), ","));
            }
            goodsService.deleteSku(spuCommand);
            return ResultJSON.success().message(CommonConstants.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error("deleteSku", e);
            return ResultJSON.failed().message(e.getMessage());
        }
    }

    @RequestMapping(value = "/copySku")
    public String copySku(Model model, @ModelAttribute("command") SkuAddCommand command, @ModelAttribute("generate") CoreSkuGenerate generate,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result ret = SpuValidate.checkCopySku(command);
        if (!ret.isSuccess()) {
            String result = addSku(model, command, generate, request, response);
            command.setSkuId(null);
            command.setErrors(ret.getErrors());
            return result;
        }
        String result = addSku(model, command, generate, request, response);
        command.setSkuId(null);
        return result;
    }

    @RequestMapping(value = "/backupSku")
    @ResponseBody
    public ResultJSON backupSku(Model model, @ModelAttribute("command") SkuAddCommand command,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result ret = SpuValidate.checkBackupSku(command);
        if (!ret.isSuccess()) {
            return ResultJSON.failed().message(StringUtils.join(ret.getErrors()));
        }
        try {
            goodsService.backupSku(command);
            return ResultJSON.success();
        } catch (Exception e) {
            logger.error("error", e);
            return ResultJSON.failed(e);
        }
    }
    @RequestMapping(value = "/static/skuTip")
    @ResponseBody
    public ResultJSON skuTip(Model model,Integer skuId,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (skuId==null) {
            return ResultJSON.failed().message("skuId is null");
        }
        try {
            return ResultJSON.success().data(goodsService.skuTip(skuId));
        } catch (Exception e) {
            logger.error("error", e);
            return ResultJSON.failed(e);
        }
    }

    @RequestMapping(value = "/static/getCostPrice")
    @ResponseBody
    public ResultJSON getCostPrice(Model model, @ModelAttribute("command") SkuAddCommand command,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //command.getSkuId()  ==orderdetailid
            Map<String,Object> objectMap=goodsService.getCostPrice(command.getSkuId());
            return ResultJSON.success().data(objectMap);
        } catch (Exception e) {
            logger.error("error", e);
            return ResultJSON.failed(e);
        }
    }

    private String transFileInfo(String paths) {
        List<FileInfo> fileInfos = Lists.newArrayList();
        if (StringUtils.isNotBlank(paths)) {
            String[] urls = StringUtils.split(paths, ",");
            for (String url : urls) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setHttpUrl(api_http + domain);
                int lastSplit = org.apache.commons.lang3.StringUtils.lastIndexOf(url, "/");
                if (lastSplit > 1) {
                    fileInfo.setFilePath(org.apache.commons.lang3.StringUtils.substring(url, 0, lastSplit + 1));
                    fileInfo.setFileName(org.apache.commons.lang3.StringUtils.substring(url, lastSplit + 1));
                }
                fileInfo.setPrefix("jpg");
                fileInfo.setCode(0);
                fileInfo.setMessage(null);
                fileInfos.add(fileInfo);
            }
        }
        return JsonUtils.convertConllectionToJsonStr(fileInfos);
    }

    @Autowired
    @Qualifier("goodsChannelPriceService")
    private GoodsChannelPriceService goodsChannelPriceService;
    @Autowired
    @Qualifier("goodsService")
    private GoodsService oldGoodsService;
    @Autowired
    @Qualifier("traderSupplierService")
    private TraderSupplierService traderSupplierService;
    @Autowired
    @Qualifier("goodsSettlementPriceService")
    private GoodsSettlementPriceService goodsSettlementPriceService;
}