package com.smallhospital.controller;

import com.smallhospital.dto.ELSkuDto;
import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.ElContractSku;
import com.smallhospital.model.ElSku;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.model.vo.ElContractSkuVO;
import com.smallhospital.model.vo.ElTraderVo;
import com.smallhospital.service.ELContractService;
import com.smallhospital.service.ELContractSkuService;
import com.smallhospital.service.ELTraderService;
import com.smallhospital.service.ElSkuService;
import com.smallhospital.service.impl.remote.SynProductInfoService;
import com.smallhospital.service.impl.remote.SysContractInfoService;
import com.smallhospital.service.impl.validator.*;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.Unit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  小医院客户列表
 */
@Controller
@RequestMapping("/el/contract")
public class ELContractController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ELContractController.class);

    @Autowired
    private ELContractService contractService;

    @Autowired
    private ELContractSkuService contractSkuService;

    @Autowired
    private ELTraderService traderService;

    @Autowired
    private ElSkuService skuService;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    private SysContractInfoService sysContractInfoService;

    @Autowired
    private SynProductInfoService synProductInfoService;

    @Autowired
    private ContractEffectiveValidator contractEffectiveValidator;

    @Autowired
    private ContractProductSynValidator contractProductSynValidator;

    @Autowired
    private ContractHasProductValidator contractHasProductValidator;

    @Autowired
    private ContractProductNumOverFlowValidator contractProductNumOverFlowValidator;

    @Autowired
    private ContractSkuOverlapValidator contractSkuOverlapValidator;

    /**
     * 销售总监角色id
     */
    public static final Integer SALE_DIRECTOR_ROLE_ID = 4;

    /**
     * 销售主管角色id
     */
    public static final Integer SALE_CHARGE_ROLE_ID = 5;

    /**
     * 所有的sku
     */
    private List<Integer> elSkuIds;

    /**
     * 合同内的sku
     */
    private List<Integer> htSkuIds;

    /**
     * 其他有效合同内的sku
     */
    private List<Integer> otherContractSkuIds;

    @ResponseBody
    @RequestMapping(value="/index")
    public ModelAndView index(HttpServletRequest request, ELContractVO contract,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                              @RequestParam(required = false) Integer pageSize,
                              HttpSession session){
        ModelAndView mv = new ModelAndView();
        User user =(User)session.getAttribute(ErpConst.CURR_USER);

        //查询当前操作员角色是否是 产品专员,产品主管,产品总监中的一个
        List<Integer> auditRoles = Arrays.asList(new Integer[]{SALE_DIRECTOR_ROLE_ID,SALE_CHARGE_ROLE_ID});
        List<Integer> userRoleIdLists = user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toList());

        userRoleIdLists.retainAll(auditRoles);

        if(CollectionUtils.isNotEmpty(userRoleIdLists)){
            contract.setAuditStatus(1);
            mv.addObject("auditFlag","true");
        }else{
            contract.setOwner(user.getUserId());
            mv.addObject("auditFlag","false");
        }

        //查询集合
        Page page = getPageTag(request,pageNo,pageSize);
        List<ELContractVO> contractList = contractService.querylistPage(contract, page);

        if(CollectionUtils.isNotEmpty(contractList)){
            contractList.stream().filter(contractVO->{

                return contractVO.getOwner() != 0;
            }).forEach((ELContractVO contractVO)->{
                contractVO.setOwnerName(userMapper.selectByPrimaryKey(contractVO.getOwner()).getUsername());
            });
        }

        mv.addObject("contractList",contractList);
        mv.addObject("contract", contract);
        mv.addObject("page", page);
        mv.setViewName("el/contract/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/toAddContract")
    public ModelAndView toAddContract(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();

        //查出登录人所属的客户列表
        List<ElTraderVo> tradeList = this.traderService.findCustomerByLoginId(user.getUserId());
        mv.addObject("tradeList",tradeList);

        mv.setViewName("el/contract/addContract");
        return mv;
    }

    /**
     * 新增合同基本信息
     * @param request
     * @param contract
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addContract")
    public ModelAndView addContract(HttpServletRequest request,ELContractVO contract) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();
        try{

            contract.setOwner(user.getUserId());
            contract.setCreator(user.getUserId());
            contract.setUpdator(user.getUserId());
            contract.setSignDate(DateUtil.convertLong(contract.getSignDateStr(), DateUtil.DATE_FORMAT));
            contract.setContractValidityDateStart(DateUtil.convertLong(contract.getContractValidityDateStartStr(), DateUtil.DATE_FORMAT));
            contract.setContractValidityDateEnd(DateUtil.convertLong(contract.getContractValidityDateEndStr(), DateUtil.DATE_FORMAT));
            contract.setStatus(1);

            int contractId = contractService.saveContractInfo(contract);
            mv.addObject("url", "./toEditContract.do?contractId=" + contractId);
            return success(mv);
        }catch (Exception e){
            e.printStackTrace();
            return fail(mv);
        }
    }

    /**
     * 编辑合同页面
     * @param request
     * @param contractId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/toEditContract")
    public ModelAndView toEditContract(HttpServletRequest request,@Param(value = "contractId") Integer contractId) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();

        //查出登录人所属的客户列表
        List<ElTraderVo> tradeList = this.traderService.findCustomerByLoginId(user.getUserId());
        mv.addObject("tradeList",tradeList);

        ELContractVO contractInfo = this.contractService.findById(contractId);
        mv.addObject("contractInfo",contractInfo);

        List<ElContractSkuVO> skuList = this.contractSkuService.findByContractId(contractId);
        mv.addObject("skuList",skuList);

        mv.setViewName("el/contract/editContract");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/toBatchAddContractSku")
    public ModelAndView toBatchAddContractSku(HttpServletRequest request,@Param(value = "contractId") Integer contractId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("contractId",contractId);
        mv.setViewName("el/contract/batchAddContractSkus");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/batchAddContractSku")
    public ResultInfo<?> batchAddContractSku(HttpServletRequest request, @Param(value = "contractId") Integer contractId,
                                             @RequestParam("lwfile") MultipartFile lwfile) {
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try {
            // 临时文件存放地址
            String path = request.getSession().getServletContext().getRealPath("/upload/saleorder");
            FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

            if(fileInfo.getCode() != 0){
                return resultInfo;
            }

            // 获取excel路径
            Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
            // 获取第一面sheet
            Sheet sheet = workbook.getSheetAt(0);
            // 起始行
            int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
            int endRowNum = sheet.getLastRowNum();// 结束行

            List<ElContractSku> skuLists = new ArrayList<ElContractSku>();// 保存Excel中读取的数据

            //查询库中所有的skuID
            elSkuIds = this.skuService.findAllSkuIds();
            //查询当前合同的sku
            htSkuIds = this.contractSkuService.findByContractId(contractId).stream().map(ElContractSkuVO::getSkuId).collect(Collectors.toList());

            //查询当前客户其他有效合同中的所有sku,保证sku不重叠
            ELContractVO contractVO = this.contractService.findById(contractId);
            otherContractSkuIds = this.contractService.findOtherValidSkus(contractVO);

            for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
                ElContractSku sku = parseRowContent(sheet,rowNum,resultInfo,contractId,user);
                skuLists.add(sku);
            }

            contractSkuService.batchAddContractSkus(skuLists);

            resultInfo.setCode(0);
            resultInfo.setMessage("批量导入成功");
        } catch (Exception e) {
            logger.error("saveBatchElSkus:", e);
        }
        return resultInfo;
    }

    private ElContractSku parseRowContent(Sheet sheet,int rowNum,ResultInfo<?> resultInfo,
                                          Integer contractId,User user) throws Exception{

        // 获取excel的值
        ElContractSku sku = new ElContractSku();

        Row row = sheet.getRow(rowNum);

        for(int cellNum=0;cellNum <=1;cellNum++){

            Cell cell = row.getCell(cellNum);

            if(cellNum == 0){
                validatorSkuId(sku,cell,resultInfo,rowNum,cellNum);
            }

            if(cellNum == 1){
                validatorSkuPrice(sku,cell,resultInfo,rowNum,cellNum);
            }
        }

        sku.setAddTime(DateUtil.gainNowDate());
        sku.setUpdateTime(DateUtil.gainNowDate());
        sku.setCreator(user.getUserId());
        sku.setUpdator(user.getUserId());
        sku.setContractId(contractId);
        return sku;
    }

    /**
     * 校验sku价格
     * @param sku
     * @param cell
     * @param resultInfo
     * @param rowNum
     * @param cellNum
     * @throws Exception
     */
    private void validatorSkuPrice(ElContractSku sku, Cell cell, ResultInfo<?> resultInfo, int rowNum, int cellNum) throws Exception{

        //非数字类型
        if (cell == null || cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {// cell==null单元格空白（无内容，默认空）
            resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
            throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
        }

        Double pice = cell.getNumericCellValue();
        if (!(pice.toString()).matches("[0-9]{1,14}\\.{0,1}[0-9]{0,2}")) {
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
        }

        BigDecimal bd = new BigDecimal(pice);
        sku.setContractPrice(bd.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 校验sku
     * @param sku
     * @param cell
     * @param resultInfo
     * @param rowNum
     * @param cellNum
     * @throws Exception
     */
    private void validatorSkuId(ElContractSku sku,Cell cell,ResultInfo resultInfo,int rowNum,int cellNum) throws Exception{
        //非数字类型
        if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) {// cell==null单元格空白（无内容，默认空）
            resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
            throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
        }

        String skuOrderNum = cell.getStringCellValue();
        int skuId = Integer.valueOf(skuOrderNum.substring(1));

        if (!(skuId + "").matches("^[1-9]+[0-9]*$")) {
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不是数字，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不是数字，请验证！");
        }

        //校验是否是小医院的sku
        if(!elSkuIds.contains(skuId)){
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：sku值非医疗的SKU，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：sku值非医疗的SKU，请验证！");
        }

        //合同已经存在该sku
        if(htSkuIds.contains(skuId)){
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：sku已经存在合同中，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：sku已经存在合同中，请验证！");
        }

        //sku有重叠
        if(otherContractSkuIds.contains(skuId)){
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：sku与其他合同的SKU有重叠，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：sku与其他合同的SKU有重叠，请验证！");
        }

        sku.setSkuId(skuId);
    }

    @ResponseBody
    @RequestMapping(value="/delContractSku")
    public ResultInfo<Position> delContractSku(HttpServletRequest request, @Param(value="contactSkuId") Integer contactSkuId){
        ResultInfo<Position> resultInfo = new ResultInfo<Position>();
        try{
            contractSkuService.deleteById(contactSkuId);
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 编辑sku页面
     * @return 产品列表信息
     */
    @RequestMapping(method = RequestMethod.GET, value = "/toContractSkuEdit")
    @ResponseBody
    public ModelAndView toContractSkuEdit(@Param(value="contactSkuId") Integer contactSkuId){
        ModelAndView mv = new ModelAndView();
        ElContractSku sku = this.contractSkuService.findById(contactSkuId);
        mv.addObject("sku",sku);
        mv.setViewName("el/sku/editSku");
        return mv;
    }

    /**
     * 合同审核页面
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value="/toConractAudit")
    public ModelAndView toConractAudit(@Param(value="contractId") Integer contractId) {
        ModelAndView mv = new ModelAndView();
        ELContractVO contractInfo = this.contractService.findById(contractId);
        mv.addObject("contractInfo",contractInfo);
        mv.setViewName("el/contract/contractAudit");
        return mv;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value="/contractSkuEdit")
    public ResultInfo<?> contractSkuEdit(HttpServletRequest request,ElContractSku sku) {

        ResultInfo<Position> resultInfo = new ResultInfo<Position>();
        try{
            contractSkuService.modify(sku);
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/editContract")
    public ModelAndView editContract(HttpServletRequest request, ELContractVO contract) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        try {
            contract.setUpdator(user.getUserId());
            contract.setSignDate(DateUtil.convertLong(contract.getSignDateStr(), DateUtil.DATE_FORMAT));
            contract.setContractValidityDateStart(DateUtil.convertLong(contract.getContractValidityDateStartStr(), DateUtil.DATE_FORMAT));
            contract.setContractValidityDateEnd(DateUtil.convertLong(contract.getContractValidityDateEndStr(), DateUtil.DATE_FORMAT));

            contractService.updateContract(contract);

            return success(mv);

        } catch (Exception e) {
            logger.error("editContract:", e);
            return fail(mv);
        }
    }

    /**
     * 合同审核
     * @param request
     * @param contract
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/contractAudit")
    public ResultInfo contractAudit(HttpServletRequest request, ELContractVO contract) {
        ResultInfo<?> resultInfo = new ResultInfo<>();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        try {
            //审核通过 合同状态改为生效
            if(contract.getAuditStatus() == 2){
                contract.setEffctiveStatus(1);
            }

            contract.setAuditStatus(contract.getAuditStatus());
            contract.setAuditDesc(contract.getAuditDesc());
            contract.setAuditer(user.getUserId());
            contract.setAuditTime(System.currentTimeMillis());

            contractService.updateContract(contract);

            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            return resultInfo;
        } catch (Exception e) {
            logger.error("contractAudit:", e);
        }
        return resultInfo;
    }


    /**
     * 提交审核
     * @param request
     * @param contractId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/submitContractValidator")
    public ResultInfo<?> submitContractValidator(HttpServletRequest request, @Param(value="contractId") Integer contractId){
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try{

            //查询当前客户其他有效合同中的所有sku,保证sku不重叠
            ELContractVO contractVO = this.contractService.findById(contractId);

            ContractValidatorChain chain = ContractValidatorChainBuild.newBuild()
                    .setContractHasProductValidator(this.contractHasProductValidator)
                    .setContractProductNumOverFlowValidator(this.contractProductNumOverFlowValidator)
                    .setContractSkuOverlapValidator(this.contractSkuOverlapValidator)
                    .create();

            ValidatorResult validatorResult = chain.validator(contractVO);

            //检验不通过返回结果
            if(!validatorResult.getResult()){
                resultInfo.setMessage(validatorResult.getMessage());
                return resultInfo;
            }

            //更新审核状态为审核中
            ELContractVO contract = new ELContractVO();
            contract.setElContractId(contractId);
            contract.setAuditStatus(1);
            contract.setEffctiveStatus(0);
            contract.setProductSynStatus(0);
            contract.setContractSynStatus(0);
            contract.setConfirmStatus(0);
            contractService.updateContract(contract);

            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            LOGGER.error("提交审核失败",e);
        }
        return resultInfo;
    }


    /**
     * 生效合同
     * @param request
     * @param contractId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/effctiveContract")
    public ResultInfo<?> effctiveContract(HttpServletRequest request, @Param(value="contractId") Integer contractId){
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try{

            //查询当前客户其他有效合同中的所有sku,保证sku不重叠
            ELContractVO contractVO = this.contractService.findById(contractId);

            ContractValidatorChain chain = ContractValidatorChainBuild.newBuild()
                    .setContractHasProductValidator(this.contractHasProductValidator)
                    .setContractProductNumOverFlowValidator(this.contractProductNumOverFlowValidator)
                    .create();

            ValidatorResult validatorResult = chain.validator(contractVO);

            //检验不通过返回结果
            if(!validatorResult.getResult()){
                resultInfo.setMessage(validatorResult.getMessage());
                return resultInfo;
            }

            //生效合同
            ELContractVO contract = new ELContractVO();
            contract.setElContractId(contractId);
            contract.setEffctiveStatus(1);
            contractService.updateContract(contract);

            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            LOGGER.error("合同生效失败",e);
        }
        return resultInfo;
    }


    /**
     * 同步合同产品给小医院
     * @param request
     * @param contractId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/synContractSkuToEl")
    public ResultInfo<?> synContractSkuToEl(HttpServletRequest request, @Param(value="contractId") Integer contractId){
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try{

            //查询当前客户其他有效合同中的所有sku,保证sku不重叠
            ELContractVO contractVO = this.contractService.findById(contractId);

            ContractValidatorChain chain = ContractValidatorChainBuild.newBuild()
                    .setContractEffectiveValidator(this.contractEffectiveValidator)
                    .setContractHasProductValidator(this.contractHasProductValidator)
                    .setContractProductNumOverFlowValidator(this.contractProductNumOverFlowValidator)
                    .setContractSkuOverlapValidator(this.contractSkuOverlapValidator)
                    .create();

            ValidatorResult validatorResult = chain.validator(contractVO);

            //检验不通过返回结果
            if(!validatorResult.getResult()){
                resultInfo.setMessage(validatorResult.getMessage());
                return resultInfo;
            }

            //同步给小医院合同的产品详情
            synProductInfoService.syncData(contractId);

            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            LOGGER.error("同步产品失败",e);
        }
        return resultInfo;
    }

    /**
     * 同步合同给小医院
     * @param request
     * @param contractId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/synContractToEl")
    public ResultInfo<?> synContractToEl(HttpServletRequest request, @Param(value="contractId") Integer contractId){
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try{

            //这边也可以用校验器链模式加构造器模式抽取下，代码不复杂，有空可以抽下
            //查询当前客户其他有效合同中的所有sku,保证sku不重叠
            ELContractVO contractVO = this.contractService.findById(contractId);

            ContractValidatorChain chain = ContractValidatorChainBuild.newBuild()
                    .setContractEffectiveValidator(this.contractEffectiveValidator)
                    .setContractProductSynValidator(this.contractProductSynValidator)
                    .setContractHasProductValidator(this.contractHasProductValidator)
                    .setContractProductNumOverFlowValidator(this.contractProductNumOverFlowValidator)
                    .setContractSkuOverlapValidator(this.contractSkuOverlapValidator)
                    .create();

            ValidatorResult validatorResult = chain.validator(contractVO);

            //检验不通过返回结果
            if(!validatorResult.getResult()){
                resultInfo.setMessage(validatorResult.getMessage());
                return resultInfo;
            }

            //同步给小医院合同信息
            sysContractInfoService.syncData(contractId);

            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            LOGGER.error("同步小医院失败",e);
        }
        return resultInfo;
    }


    /**
     * 失效合同
     * @param request
     * @param contractId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/invalidContract")
    public ResultInfo<?> invalidContract(HttpServletRequest request, @Param(value="contractId") Integer contractId){
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try{
            ELContractVO contract = new ELContractVO();
            contract.setElContractId(contractId);
            contract.setEffctiveStatus(0);
            contractService.updateContract(contract);
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }
}
