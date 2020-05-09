package com.smallhospital.controller;

import com.alibaba.fastjson.JSON;
import com.smallhospital.dto.*;
import com.smallhospital.model.ElSku;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.model.vo.ELSkuVO;
import com.smallhospital.service.ELContractService;
import com.smallhospital.service.ElSkuService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.goods.dao.CoreSkuGenerateMapper;
import com.vedeng.goods.dao.CoreSkuSearchGenerateMapper;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.CoreSkuSearchGenerate;
import com.vedeng.goods.model.CoreSkuSearchGenerateExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *  小医院客户列表
 */
@Controller
@RequestMapping("/el/sku")
public class ELSkuController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ELSkuController.class);

    @Autowired
    private ElSkuService skuService;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    private ELContractService contractService;

    @Autowired
    @Qualifier("coreSkuGenerateMapper")
    private CoreSkuGenerateMapper coreSkuGenerateMapper;

    @Autowired
    @Qualifier("coreSkuSearchGenerateMapper")
    private CoreSkuSearchGenerateMapper coreSkuSearchGenerateMapper;

    @ResponseBody
    @RequestMapping(value="/index")
    public ModelAndView index(HttpServletRequest request, ELSkuVO skuVO,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                              @RequestParam(required = false) Integer pageSize,
                              HttpSession session){
        ModelAndView mv = new ModelAndView();
        User user =(User)session.getAttribute(ErpConst.CURR_USER);
        //查询集合
        Page page = getPageTag(request,pageNo,pageSize);
        List<ELSkuVO> skuList = skuService.querylistPage(skuVO, page);

        mv.addObject("skuList",skuList);
        mv.addObject("sku", skuVO);
        mv.addObject("page", page);
        mv.setViewName("el/sku/index");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/batchAddELSkuInit")
    public ModelAndView addTrader(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("el/sku/batchAddSkus");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/saveBatchElSkus")
    public ResultInfo<?> saveBatchElSkus(HttpServletRequest request,
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

            List<ElSku> skuLists = new ArrayList<ElSku>();// 保存Excel中读取的数据

            //查询库中所有的skuID
            List<Integer> skuIds = this.skuService.findAllSkuIds();

            for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
                ElSku sku = parseRowContent(user,skuIds,sheet,rowNum,resultInfo);
                skuLists.add(sku);
            }

            skuService.batchAddSkus(skuLists);

            resultInfo.setCode(0);
            resultInfo.setMessage("批量导入成功");
        } catch (Exception e) {
            logger.error("saveBatchElSkus:", e);
        }
        return resultInfo;
    }

    /**
     * 校验excel表中某一行的内容，如果校验失败直接抛异常
     * @param rowNum
     * @param resultInfo
     */
    private ElSku parseRowContent(User user,List<Integer> skuIds,Sheet sheet,int rowNum,ResultInfo<?> resultInfo) throws Exception{

        Row row = sheet.getRow(rowNum);

        int cellNum = 0;
        Cell cell = row.getCell(cellNum);

        if (cell == null) {// cell==null单元格空白（无内容，默认空）
            resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
            throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
        }

        String skuOrderNum = cell.getStringCellValue();
        if (skuOrderNum == null) {
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
        }

        int skuId = Integer.valueOf(skuOrderNum.substring(1));

        if (!(skuId + "").matches("^[1-9]+[0-9]*$")) {
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不是数字，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不是数字，请验证！");
        }

        //校验sku是否是有效的sku
        boolean valid = skuService.checkIsValidSku(skuId);

//        CoreSkuSearchGenerateExample example = new CoreSkuSearchGenerateExample();
//        example.createCriteria().andCheckStatusEqualTo(3).andSkuIdEqualTo(num);
//        List<CoreSkuSearchGenerate> listSkus = coreSkuSearchGenerateMapper.selectByExample(example);
        if(!valid){
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值在系统中不存在，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值在系统中不存在，请验证！");
        }

        //校验sku是否已经存在
        if(skuIds.contains(skuId)){
            resultInfo.setMessage(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值已经添加，请验证！");
            throw new Exception(
                    "第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值已经添加，请验证！");
        }

        // 获取excel的值
        ElSku sku = new ElSku();
        sku.setAddTime(DateUtil.gainNowDate());
        sku.setUpdateTime(DateUtil.gainNowDate());
        sku.setCreator(user.getUserId());
        sku.setUpdator(user.getUserId());
        sku.setSkuId(skuId);
        return sku;
    }


    @ResponseBody
    @RequestMapping(value="deleteElSku")
    public ResultInfo<Position> deleteElSku(HttpServletRequest request, ELSkuVO skuVO){
        ResultInfo<Position> resultInfo = new ResultInfo<Position>();
        try{
            skuService.deleteById(skuVO.getElSkuId());
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 小医院系统获取产品列表
     * @return 产品列表信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/list")
    @ResponseBody
    public ElResultDTO getElSkuList(@RequestBody ELSkuDto skuDto){


        LOGGER.info("小医院合同申请列表入参:" + JSON.toJSONString(skuDto));

        if(skuDto.getHospitalId() == null){
            return ElResultDTO.errorInternal("请求参数不能为空");
        }

//        //查询所有小医院的skuId
//        List<Integer> elSkuIds = this.skuService.findAllSkuIds();
//
//        ELContractVO contractVO = new ELContractVO();
//        contractVO.setTraderId(skuDto.getHospitalId());
//
//        //查询当前客户合同中的所有skuId
//        List<Integer> contractSkuIds = this.contractService.findOtherValidSkus(contractVO);
//
//        //做差集,排除掉合同中的sku
//        elSkuIds.removeAll(contractSkuIds);
//        skuDto.setValidSkuIds(elSkuIds);
//
//        ElResultDTO result = ElResultDTO.ok();
//
//        if(!CollectionUtils.isEmpty(elSkuIds)){
//            result.setData(skuService.findElSkulistPage(skuDto));
//        }

        //修改为分页查询
        try{

            Page page = Page.newBuilder(skuDto.getPageNo(),skuDto.getPageSize(),null);

            List<ELSkuBasicInfo> skuLists = skuService.findElSkulistPage(skuDto,page);

            ElResultDTO result = ElResultDTO.ok();
            result.setData(skuLists);
            result.setTotal(page.getTotalRecord());

            LOGGER.info("小医院合同申请列表响应:" + JSON.toJSONString(result));
            return result;

        }catch (Exception e){
            LOGGER.error("小医院分页请求失败",e);
        }
        return ElResultDTO.errorInternal("小医院分页请求失败");
    }

    /**
     * 小医院系统获取产品详情
     * @return 产品详情
     */
    @RequestMapping(method = RequestMethod.POST, value = "/detail")
    @ResponseBody
    public ElResultDTO detail(@RequestBody ELSkuDetaiDto detail){

        if(detail.getProductId() == null){
            return ElResultDTO.errorInternal("请求参数productId不能为空");
        }

        ELSkuDetailInfo detailInfo = skuService.findDetailSkuInfo(detail.getProductId());

        if(StringUtil.isEmpty(detailInfo.getSpec())){
            detailInfo.setSpec("/");
        }

        ElResultDTO result = ElResultDTO.ok();
        result.setData(detailInfo);

        return result;
    }
}
