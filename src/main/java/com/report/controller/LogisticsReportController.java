package com.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.report.model.export.ExpressExport;
import com.report.model.export.WareHouseLogExport;
import com.report.service.CommonReportService;
import com.report.service.LogisticsReportService;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.Barcode;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehouseGoodsSet;
import com.vedeng.logistics.service.WarehouseGoodsOperateLogService;
import com.vedeng.logistics.service.WarehouseInService;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.BuyorderService;

/**
 * <b>Description:</b><br> 物流数据导出
 *
 * @author Jerry
 * @Note <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.com.controller
 * <br><b>ClassName:</b> LogisticsReportController
 * <br><b>Date:</b> 2017年11月30日 上午10:35:53
 */
@Controller
@RequestMapping("/report/logistics")
public class LogisticsReportController extends BaseController {
    public static Logger logger = LoggerFactory.getLogger(LogisticsReportController.class);

    @Autowired
    @Qualifier("logisticsReportService")
    private LogisticsReportService logisticsReportService;

    @Autowired
    @Qualifier("commonReportService")
    private CommonReportService commonReportService;

    @Autowired
    @Qualifier("buyorderService")
    private BuyorderService buyorderService;

    @Autowired
    @Qualifier("warehouseInService")
    private WarehouseInService warehouseInService;

    @Autowired
    @Qualifier("warehouseGoodsOperateLogService")
    private WarehouseGoodsOperateLogService warehouseGoodsOperateLogService;

    @Autowired
    @Qualifier("afterSalesOrderService")
    private AfterSalesService afterSalesOrderService;

    /**
     * <b>Description:</b><br> 导出商品库位
     *
     * @param request
     * @param response
     * @param session
     * @param warehouseGoodsSet
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年12月6日 下午1:24:39
     */
    @RequestMapping(value = "/exportwarehousegoodssetlist", method = RequestMethod.GET)
    public void exportWarehouseGoodsSetList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WarehouseGoodsSet warehouseGoodsSet) {
        try {

            User user = (User) session.getAttribute(ErpConst.CURR_USER);
            warehouseGoodsSet.setCompanyId(user.getCompanyId());

            Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);

            List<WarehouseGoodsSet> list = logisticsReportService.exportWarehouseGoodsSetsList(warehouseGoodsSet);
            IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/商品库位.jrxml", list, "商品库位.xls");
        } catch (Exception e) {
            logger.error("exportwarehousegoodssetlist:", e);
        }
    }

    /**
     * <b>Description:</b><br> 导出文件寄送
     *
     * @param request
     * @param response
     * @param session
     * @param fileDelivery
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年12月7日 上午11:31:26
     */
    @RequestMapping(value = "/exportfiledeliverylist", method = RequestMethod.GET)
    public void exportFileDeliveryList(HttpServletRequest request, HttpServletResponse response, HttpSession session, FileDelivery fileDelivery) {
        try {

            User user = (User) session.getAttribute(ErpConst.CURR_USER);
            fileDelivery.setCompanyId(user.getCompanyId());

            Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);

            List<Integer> positionType = new ArrayList<>();
            positionType.add(SysOptionConstant.ID_310);// 销售
            positionType.add(SysOptionConstant.ID_311);// 采购
            positionType.add(SysOptionConstant.ID_312);// 售后
            positionType.add(SysOptionConstant.ID_314);// 财务

            List<User> netAllUserList = commonReportService.getMyUserList(session, positionType, false);

            List<Integer> creatorIds = new ArrayList<Integer>();

            // 如果申请人条件是全部的话
            if (netAllUserList != null && (fileDelivery.getCreator() == null || fileDelivery.getCreator() == -1)) {
                for (User c : netAllUserList) {
                    creatorIds.add(c.getUserId());
                }
                creatorIds.add(user.getUserId());
            }
            List<FileDelivery> list = logisticsReportService.exportFileDeliveryList(fileDelivery, creatorIds, page);
            IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/文件寄送列表.jrxml", list, "文件寄送列表.xls");
        } catch (Exception e) {
            logger.error("exportfiledeliverylist:", e);
        }
    }

    /**
     * <b>Description:</b><br> 导出库位
     *
     * @param request
     * @param response
     * @param session
     * @param warehouseGoodsSet
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年12月7日 下午3:51:47
     */
    @RequestMapping(value = "/exportwarehouselist", method = RequestMethod.GET)
    public void exportWarehouseList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WarehouseGoodsSet warehouseGoodsSet) {
        try {
            User user = (User) session.getAttribute(ErpConst.CURR_USER);
            warehouseGoodsSet.setCompanyId(user.getCompanyId());

            Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);

            List<WarehouseGoodsSet> list = logisticsReportService.exportWarehouseList(warehouseGoodsSet, page);
            IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/库位.jrxml", list, "库位.xls");
        } catch (Exception e) {
            logger.error("exportwarehouselist:", e);
        }
    }

    /**
     * <b>Description:</b><br> 导出快递列表
     *
     * @param request
     * @param response
     * @param session
     * @param warehouseGoodsSet
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年12月8日 下午4:05:18
     */
    @RequestMapping(value = "/exportexpresslist", method = RequestMethod.GET)
    public void exportExpressList(HttpServletRequest request, HttpServletResponse response,
                                  Express express,
                                  @RequestParam(required = false, value = "_startTime") String _startTime,
                                  @RequestParam(required = false, value = "_endTime") String _endTime,
                                  @RequestParam(required = false, value = "searchDateType") String searchDateType) {
        try {

            //获取session中user信息
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            express.setCompanyId(user.getCompanyId());
            Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);

            if (StringUtils.isNotBlank(_startTime)) {
                express.setBeginTime(DateUtil.convertLong(_startTime + " 00:00:00", ""));
            }
            if (StringUtils.isNotBlank(_endTime)) {
                express.setEndTime(DateUtil.convertLong(_endTime + " 23:59:59", ""));
            }
            List<ExpressExport> list = logisticsReportService.exportExpressList(express, page);
            IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/快递记录.jrxml", list, "快递记录.xls");
        } catch (Exception e) {
            logger.error("exportexpresslist:", e);
        }
    }

    /**
     * <b>Description:</b><br> 导出出库记录
     *
     * @param request
     * @param response
     * @param session
     * @param express
     * @Note <b>Author:</b> duke
     * <br><b>Date:</b> 2018年1月18日 下午6:14:25
     */
    @RequestMapping(value = "/exportWareHouseOutList", method = RequestMethod.GET)
    public void exportWareHouseOutList(HttpServletRequest request, HttpServletResponse response, WarehouseGoodsOperateLog warehouseGoodsOperateLog,
                                       @RequestParam(required = false, value = "_startTime") String _startTime,
                                       @RequestParam(required = false, value = "_endTime") String _endTime) {

        // 获取session中user信息
        User session_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        long start=0l;
        long end=System.currentTimeMillis();
        if (StringUtils.isNotBlank(_startTime)) {

            warehouseGoodsOperateLog.setBeginTime(DateUtil.convertLong(_startTime + " 00:00:00", ""));
            start=warehouseGoodsOperateLog.getBeginTime();
        }
        if (StringUtils.isNotBlank(_endTime)) {
            warehouseGoodsOperateLog.setEndTime(DateUtil.convertLong(_endTime + " 23:59:59", ""));
            end=warehouseGoodsOperateLog.getEndTime();
        }
        if((end-start)>1000*60*60*24*30){
            try {
                response.getWriter().write("间隔超过30天，请到processing里面导出，或分时间段导出");
            }catch (Exception e){
            }
            return;
        }

        Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
        // 查询出库记录
        warehouseGoodsOperateLog.setOperateType(2);
        warehouseGoodsOperateLog.setIsEnable(1);
        warehouseGoodsOperateLog.setCompanyId(session_user.getCompanyId());
        List<WarehouseGoodsOperateLog> logs = new ArrayList<>();
        try {
            Map<String, Object> wlog = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);
            page = (Page) wlog.get("page");
            logs.addAll((List<WarehouseGoodsOperateLog>) wlog.get("list"));
            if (page != null && page.getTotalPage() > 1) {
                for (int i = 2; i <= page.getTotalPage(); i++) {
                    page.setPageNo(i);
                    Map<String, Object> log = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);
                    logs.addAll((List<WarehouseGoodsOperateLog>) log.get("list"));
                }
            }
        } catch (Exception e) {
            logger.error("warehouseOutLogList:", e);
        }
        if (CollectionUtils.isNotEmpty(logs)) {
            for (WarehouseGoodsOperateLog l : logs) {
                l.setAddTimeStr(DateUtil.convertString(l.getAddTime(), "yyyy-MM-dd hh:mm:ss"));
                l.setRecheckUserName("shawn.xiong");
                if(l.getExpirationDate()!=null) {
                    l.setExpirationDateStr(DateUtil.convertString(l.getExpirationDate(), "yyyy-MM-dd"));
                }
                if (l.getNum() != null) {
                    l.setNum(0 - l.getNum());
                }
            }
        }
//		List<WareHouseLogExport> list = logisticsReportService.exportWareHouseOutList(warehouseGoodsOperateLog, page);
        IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/出库记录.jrxml", logs, "出库记录.xls");
    }

    /**
     * <b>Description:</b><br> 导出入库记录
     *
     * @param request
     * @param response
     * @param warehouseGoodsOperateLog
     * @param searchBeginTime
     * @param searchEndTime
     * @Note <b>Author:</b> duke
     * <br><b>Date:</b> 2018年1月19日 下午1:18:18
     */
    @RequestMapping(value = "/exportWareHouseInList", method = RequestMethod.GET)
    public void exportWareHouseInList(HttpServletRequest request, HttpServletResponse response, WarehouseGoodsOperateLog warehouseGoodsOperateLog,
                                      @RequestParam(required = false, value = "searchbeginTime") String searchBeginTime,
                                      @RequestParam(required = false, value = "searchendTime") String searchEndTime) {

        // 获取session中user信息
        User session_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        if (StringUtils.isNotBlank(searchBeginTime)) {
            warehouseGoodsOperateLog.setBeginTime(DateUtil.convertLong(searchBeginTime + " 00:00:00", DateUtil.TIME_FORMAT));
        }
        if (StringUtils.isNotBlank(searchEndTime)) {
            warehouseGoodsOperateLog.setEndTime(DateUtil.convertLong(searchEndTime + " 23:59:59", DateUtil.TIME_FORMAT));
        }
        // 查询入库记录
        warehouseGoodsOperateLog.setOperateType(1);
        warehouseGoodsOperateLog.setIsEnable(1);
        warehouseGoodsOperateLog.setCompanyId(session_user.getCompanyId());

        Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
        List<WarehouseGoodsOperateLog> logs = new ArrayList<>();
        try {
            Map<String, Object> wlog = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);
            page = (Page) wlog.get("page");
            logs.addAll((List<WarehouseGoodsOperateLog>) wlog.get("list"));
            if (page != null && page.getTotalPage() > 1) {
                for (int i = 2; i <= page.getTotalPage(); i++) {
                    page.setPageNo(i);
                    Map<String, Object> log = warehouseGoodsOperateLogService.getWGOlogList(warehouseGoodsOperateLog, page);
                    logs.addAll((List<WarehouseGoodsOperateLog>) log.get("list"));
                }
            }
        } catch (Exception e) {
            logger.error("warehouseOutLogList:", e);
        }
        if (CollectionUtils.isNotEmpty(logs)) {
            for (WarehouseGoodsOperateLog l : logs) {
                if(l.getAddTime()!=null) {
                    l.setAddTimeStr(DateUtil.convertString(l.getAddTime(), "yyyy-MM-dd hh:mm:ss"));
                }
//                l.setRecheckUserName("shawn.xiong");
                if(l.getExpirationDate()!=null) {
                    l.setExpirationDateStr(DateUtil.convertString(l.getExpirationDate(), "yyyy-MM-dd"));
                }
                if (l.getNum() != null) {
                    l.setNum(l.getNum());
                }
            }
        }
//        List<WareHouseLogExport> list = logisticsReportService.exportWareHouseInList(warehouseGoodsOperateLog, page);
        IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/入库记录.jrxml", logs, "入库记录.xls");
    }

    /**
     * <b>Description:</b><br> 采购入库批量生成条码
     *
     * @param request
     * @param response
     * @param buyorderId
     * @Note <b>Author:</b>
     * <br><b>Date:</b> 2018年7月6日 下午1:51:32
     */
    @FormToken(remove = true)
    @RequestMapping(value = "/exportBatchBarcode", method = RequestMethod.GET)
    public void exportBatchBarcode(HttpServletRequest request, HttpServletResponse response, Integer buyorderId, String buyorderGoodsIds) {
        // 获取session中user信息
        User session_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 查询采购记录
        BuyorderVo buyorderVo = new BuyorderVo();
        buyorderVo.setBuyorderId(buyorderId);
        buyorderVo.setCompanyId(session_user.getCompanyId());
        if (buyorderGoodsIds != null && buyorderGoodsIds != "") {
            String[] params = buyorderGoodsIds.split("_");
            List<Integer> buyorderGoodsIdList = new ArrayList<>();
            for (int i = 0; i < params.length; i++) {
                Integer buyorderGoodsId = Integer.parseInt(params[i]);
                buyorderGoodsIdList.add(buyorderGoodsId);
            }
            buyorderVo.setBuyorderIdList(buyorderGoodsIdList);
        }
        //未整理接口
        BuyorderVo buyorderInfo = buyorderService.getBuyorderBarcodeVoDetail(buyorderVo, session_user);
        //获取采购订单产品信息
        List<BuyorderGoodsVo> buyorderGoodsList = buyorderInfo.getBuyorderGoodsVoList();
        //采购订单产品信息不等于Null && 采购订单产品信息数量大于0
        List<Integer> buyorderIdList = new ArrayList<>();
        //获取采购订单所有采购详情ID
        for (BuyorderGoodsVo bg : buyorderGoodsList) {
            buyorderIdList.add(bg.getBuyorderGoodsId());
        }
        // 所有这个采购产品的二维码信息
        Barcode barcodes = new Barcode();
        barcodes.setBuyorderIds(buyorderIdList);
        barcodes.setType(1);
        List<Barcode> barcodeLists = null;
        try {
            if (buyorderIdList.size() > 0) {
                barcodeLists = warehouseInService.getBarcode(barcodes);
            }
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        // 有效的条码
        List<Barcode> barcodeIsEnableList = new ArrayList<>();
        for (Barcode be : barcodeLists) {
            if (be.getIsEnable().equals(1)) {
                barcodeIsEnableList.add(be);
            }
        }
        Map<Integer, Integer> barcodeIsEnableSizeMap = new HashMap<>();
        for (Barcode b : barcodeIsEnableList) {
            // 根据DETAIL_GOODS_ID分类可用二维码个数
            if (barcodeIsEnableSizeMap.get(b.getDetailGoodsId()) == null) {
                barcodeIsEnableSizeMap.put(b.getDetailGoodsId(), 1);
            } else {
                barcodeIsEnableSizeMap.put(b.getDetailGoodsId(), barcodeIsEnableSizeMap.get(b.getDetailGoodsId()) + 1);
            }
        }

        Map<Integer, Integer> barcodeListsSizeMap = new HashMap<>();
        for (Barcode bl : barcodeLists) {
            // 根据DETAIL_GOODS_ID分类可用二维码个数
            if (barcodeListsSizeMap.get(bl.getDetailGoodsId()) == null) {
                barcodeListsSizeMap.put(bl.getDetailGoodsId(), 1);
            } else {
                barcodeListsSizeMap.put(bl.getDetailGoodsId(), barcodeListsSizeMap.get(bl.getDetailGoodsId()) + 1);
            }
        }

        //拼接这个商品未入库的条码对象
        List<Barcode> barcodeList = new ArrayList<>();
        if (buyorderGoodsList != null && buyorderGoodsList.size() > 0) {
            for (BuyorderGoodsVo bg : buyorderGoodsList) {
                //原来取得总数是num为了不影响其他功能，将num改为减去售后的值，num-afterSaleUpLimitNum
                bg.setNum(bg.getNum() - bg.getAfterSaleUpLimitNum());

                Integer num = null;
                if (barcodeIsEnableSizeMap != null && barcodeIsEnableSizeMap.get(bg.getBuyorderGoodsId()) != null) {
                    num = bg.getNum() - barcodeIsEnableSizeMap.get(bg.getBuyorderGoodsId());
                } else {
                    num = bg.getNum();
                }
                if (num > 0) {

                    for (Integer i = 0; i < num; i++) {
                        Barcode barcode = new Barcode();

                        Integer blsize = 0;
                        if (barcodeListsSizeMap.get(bg.getBuyorderGoodsId()) != null) {
                            blsize = barcodeListsSizeMap.get(bg.getBuyorderGoodsId());
                        }
                        barcode.setDetailGoodsId(bg.getBuyorderGoodsId());// 设置采购产品ID
                        barcode.setGoodsId(bg.getGoodsId());// 设置商品ID
                        barcode.setType(1);// 设置条码类型 1采购 2售后
                        barcode.setCreator(session_user.getUserId());// 设置添加人
                        barcode.setAddTime(DateUtil.sysTimeMillis());// 添加时间
                        barcode.setUpdater(session_user.getUserId());// 设置更新人
                        barcode.setModTime(DateUtil.sysTimeMillis());// 修改时间
                        barcode.setSequence(blsize + 1 + i);// 设置序号的初始值
                        barcode.setIsEnable(1);
                        barcodeList.add(barcode);
                    }

                }
            }
            ResultInfo<?> result = new ResultInfo<>();
            if (barcodeList.size() > 1500) {
                double count = barcodeList.size();
                double pageNum = 1500;
                Integer page = (int) Math.ceil(count / pageNum);
                for (Integer i = 0; i < page; i++) {
                    List<Barcode> barcodeInfoList = new ArrayList<>();
                    for (Integer j = 1500 * i; 1500 * (i + 1) > j && j < barcodeList.size(); j++) {
                        barcodeInfoList.add(barcodeList.get(j));
                    }
                    try {
                        //批量生成条码
                        //未整理接口
                        if (barcodeInfoList != null && barcodeInfoList.size() > 0) {
                            result = warehouseInService.addBarcodeNoImg(barcodeInfoList);
                        }
                    } catch (Exception e) {
                        logger.error(Contant.ERROR_MSG, e);
                    }
                }
            } else {
                try {
                    //批量生成条码
                    //未整理接口
                    if (barcodeList != null && barcodeList.size() > 0) {
                        result = warehouseInService.addBarcodeNoImg(barcodeList);
                    }
                } catch (Exception e) {
                    logger.error(Contant.ERROR_MSG, e);
                }
            }


        }
        //获取采购订单未入库的产品并生成条码
        List<WarehouseGoodsOperateLog> list = warehouseInService.getNoWarehousein(buyorderVo);
        IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/条码批量生成与入库.jrxml", list, "生成条码记录.xls");
    }

    /**
     * <b>Description:</b><br> 售后入库批量生成条码
     *
     * @param request
     * @param response
     * @param orderId
     * @Note <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月6日 下午2:05:26
     */
    //@FormToken(remove = true)
    @RequestMapping(value = "/exportShBatchBarcode", method = RequestMethod.GET)
    public void exportShBatchBarcode(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer orderId, Integer bussnissType) {
        // 获取session中user信息
        User session_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 查询售后单
        AfterSalesVo afterSales = new AfterSalesVo();
        afterSales.setAfterSalesId(orderId);
        AfterSalesVo asv = afterSalesOrderService.getAfterSalesVoListById(afterSales);
        //获取售后订单产品信息
        asv.setCompanyId(session_user.getCompanyId());
        if (bussnissType == 540 || bussnissType == 539) {
            asv.setBusinessType(1);
        } else {
            asv.setBusinessType(2);
        }
        asv.setIsIn(1);
        asv.setIsNormal(1);
        List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv, session);
        //采购订单产品信息不等于Null && 采购订单产品信息数量大于0
        List<Integer> afterSalesGoodsList = new ArrayList<>();
        //获取采购订单所有采购详情ID
        for (AfterSalesGoodsVo av : asvList) {
            afterSalesGoodsList.add(av.getAfterSalesGoodsId());
        }
        //有效的条码
        Barcode barcodeIsEnable = new Barcode();
        barcodeIsEnable.setBuyorderIds(afterSalesGoodsList);
        barcodeIsEnable.setType(2);
        barcodeIsEnable.setIsEnable(4);
        List<Barcode> barcodeIsEnableList = new ArrayList<>();
        try {
            barcodeIsEnableList = warehouseInService.getBarcode(barcodeIsEnable);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Map<Integer, Integer> barcodeIsEnableSizeMap = new HashMap<>();
        for (Barcode b : barcodeIsEnableList) {
            //根据DETAIL_GOODS_ID分类可用二维码个数
            if (barcodeIsEnableSizeMap.get(b.getDetailGoodsId()) == null) {
                barcodeIsEnableSizeMap.put(b.getDetailGoodsId(), 1);
            } else {
                barcodeIsEnableSizeMap.put(b.getDetailGoodsId(), barcodeIsEnableSizeMap.get(b.getDetailGoodsId()) + 1);
            }
        }

        // 所有这个产品的二维码信息
        Barcode barcodes = new Barcode();
        barcodes.setBuyorderIds(afterSalesGoodsList);
        barcodes.setType(2);
        List<Barcode> barcodeLists = null;
        try {
            barcodeLists = warehouseInService.getBarcode(barcodes);
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        Map<Integer, Integer> barcodeListsSizeMap = new HashMap<>();
        for (Barcode bl : barcodeLists) {
            //根据DETAIL_GOODS_ID分类可用二维码个数
            if (barcodeListsSizeMap.get(bl.getDetailGoodsId()) == null) {
                barcodeListsSizeMap.put(bl.getDetailGoodsId(), 1);
            } else {
                barcodeListsSizeMap.put(bl.getDetailGoodsId(), barcodeListsSizeMap.get(bl.getDetailGoodsId()) + 1);
            }
        }

        //拼接这个商品未入库的条码对象
        List<Barcode> barcodeList = new ArrayList<>();
        if (afterSalesGoodsList != null && afterSalesGoodsList.size() > 0) {
            for (AfterSalesGoodsVo avo : asvList) {

                Integer num = null;
                if (barcodeIsEnableSizeMap != null && barcodeIsEnableSizeMap.get(avo.getAfterSalesGoodsId()) != null) {
                    num = avo.getNum() - barcodeIsEnableSizeMap.get(avo.getAfterSalesGoodsId());
                } else {
                    num = avo.getNum();
                }
                if (num > 0) {

                    for (Integer i = 0; i < num; i++) {
                        Barcode barcode = new Barcode();

                        Integer blsize = 0;
                        if (barcodeListsSizeMap.get(avo.getAfterSalesGoodsId()) != null) {
                            blsize = barcodeListsSizeMap.get(avo.getAfterSalesGoodsId());
                        }
                        barcode.setDetailGoodsId(avo.getAfterSalesGoodsId());// 设置采购产品ID
                        barcode.setGoodsId(avo.getGoodsId());// 设置商品ID
                        barcode.setType(2);// 设置条码类型 1采购 2售后
                        barcode.setCreator(session_user.getUserId());// 设置添加人
                        barcode.setAddTime(DateUtil.sysTimeMillis());// 添加时间
                        barcode.setUpdater(session_user.getUserId());// 设置更新人
                        barcode.setModTime(DateUtil.sysTimeMillis());// 修改时间
                        barcode.setSequence(blsize + 1 + i);// 设置序号的初始值
                        barcode.setIsEnable(1);
                        barcodeList.add(barcode);
                    }

                }
            }
            ResultInfo<?> result = new ResultInfo<>();
            if (barcodeList.size() > 1500) {
                double count = barcodeList.size();
                double pageNum = 1500;
                Integer page = (int) Math.ceil(count / pageNum);
                for (Integer i = 0; i < page; i++) {
                    List<Barcode> barcodeInfoList = new ArrayList<>();
                    for (Integer j = 1500 * i; 1500 * (i + 1) > j && j < barcodeList.size(); j++) {
                        barcodeInfoList.add(barcodeList.get(j));
                    }
                    try {
                        //批量生成条码
                        result = warehouseInService.addBarcodes(barcodeInfoList);
                    } catch (Exception e) {
                        logger.error("barcode error:", e);
                    }
                }
            } else {
                try {
                    //批量生成条码
                    result = warehouseInService.addBarcodes(barcodeList);
                } catch (Exception e) {
                    logger.error(Contant.ERROR_MSG, e);
                }
            }


        }
        //获取售后订单未入库的产品并生成条码
        BuyorderVo buyorderVo = new BuyorderVo();
        buyorderVo.setBussinessId(asv.getAfterSalesId());
        buyorderVo.setBussinessType(2);
        buyorderVo.setCompanyId(session_user.getCompanyId());
        List<WarehouseGoodsOperateLog> list = warehouseInService.getNoWarehousein(buyorderVo);
        IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/条码批量生成与入库.jrxml", list, "生成条码记录.xls");
    }

    /**
     * <b>Description:</b><br> 导出未出库条码
     *
     * @param request
     * @param response
     * @param warehouseGoodsOperateLog
     * @Note <b>Author:</b> scott
     * <br><b>Date:</b> 2018年4月25日 上午11:01:40
     */
    @RequestMapping(value = "/exportOutBarcodeList", method = RequestMethod.GET)
    public void exportOutBarcodeList(HttpServletRequest request, HttpServletResponse response, Goods goods) {

        // 获取session中user信息
        User session_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        //未出库条码信息
        goods.setCompanyId(session_user.getCompanyId());

        Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);

        List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWglList(goods);

        IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/未出库条码列表.jrxml", list, "未出库条码列表.xls");
    }
}
