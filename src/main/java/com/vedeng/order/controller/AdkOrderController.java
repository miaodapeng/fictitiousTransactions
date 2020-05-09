package com.vedeng.order.controller;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.common.constants.Contant;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.ResultCodeConstants;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.order.dao.AdkLogMapper;
import com.vedeng.order.model.adk.*;
import com.vedeng.order.service.AdkOrderService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

//

/**
 * <b>Description:</b><br>
 * 艾迪康K3接口联调
 *
 * @author hank
 * @Note <b>ProjectName:</b> erp <br>
 * <b>Date:</b> 2019年4月8日
 */
@Controller
@RequestMapping("/order/adkorder")
public class AdkOrderController extends BaseController {
    public static Logger logger = LoggerFactory.getLogger(AdkOrderController.class);

    @Autowired
    AdkOrderService adkOrderService;
    @Autowired
    AdkLogMapper adkLogMapper;
    // 业务基本不变，参数直接放到这里
    private String whiteIp = "";
    private static String sn = "sk1234";

    @ResponseBody
    @RequestMapping("/importProduct")
    public ResultInfo<?> importProduct(HttpServletRequest request, @RequestParam("file") MultipartFile file,
                                       HttpSession session) throws Exception {
        ResultInfo<?> result = new ResultInfo<>();
        try {
            User user = (User) session.getAttribute(ErpConst.CURR_USER);

            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            // 获取第一面sheet
            Sheet sheet = workbook.getSheetAt(0);
            checksheet(sheet);
            List<TAdkGoods> goods = Lists.newArrayList();
            // 循环行数
            List<String> skuList = Lists.newArrayList();
            for (int rowNum = sheet.getFirstRowNum() + 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    log.warn("当前行无数据importProduct，系统忽略，第" + rowNum + "行");
                    continue;
                }
                TAdkGoods good = new TAdkGoods();
                good.setAddTime(new Date());
                good.setAddName(user.getUsername());
                good.setAdkGoodsCode(getCellString(row.getCell(0)));
                good.setAdkGoodsName(getCellString(row.getCell(1)));
                good.setAdkGoodsModel(getCellString(row.getCell(2)));
                good.setAdkGoodsUnit(getCellString(row.getCell(3)));
                good.setErpGoodsSku(getCellString(row.getCell(4)));
                good.setChangeNum(NumberUtils.toInt(getCellString(row.getCell(5)),1));

                if (StringUtils.isBlank(getCellString(row.getCell(6)))) {
                    throw new Exception("上传文件数据第" + rowNum + "行第6列有异常数据");
                }
                good.setErpSkuPrice(new BigDecimal(getCellString(row.getCell(6))));
                skuList.add(good.getErpGoodsSku());
                if (StringUtils.isBlank(good.getAdkGoodsCode())) {
                    throw new Exception("上传文件数据第" + rowNum + "行第1列有异常数据");
                }
                if (StringUtils.isBlank(good.getAdkGoodsName())) {
                    throw new Exception("上传文件数据第" + rowNum + "行第2列有异常数据");
                }
                if (StringUtils.isBlank(good.getAdkGoodsModel())) {
                    throw new Exception("上传文件数据第" + rowNum + "行第3列有异常数据");
                }
                if (StringUtils.isBlank(good.getAdkGoodsUnit())) {
                    throw new Exception("上传文件数据第" + rowNum + "行第4列有异常数据");
                }
                if (StringUtils.isBlank(good.getErpGoodsSku())) {
                    throw new Exception("上传文件数据第" + rowNum + "行第5列有异常数据");
                }
                goods.add(good);
            }
            adkOrderService.importProducts(goods, skuList, user);
            result.setCode(0);

        } catch (Exception e) {
            log.error("导入失败", e);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/importSuppliers")
    public ResultInfo<?> importSuppliers(HttpServletRequest request, @RequestParam("file") MultipartFile file,
                                         HttpSession session) {
        ResultInfo<?> result = new ResultInfo<>();
        try {
            User user = (User) session.getAttribute(ErpConst.CURR_USER);

            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            // 获取第一面sheet
            Sheet sheet = workbook.getSheetAt(0);
            checksheet(sheet);
            List<TAdkSupplier> goods = Lists.newArrayList();
            // 循环行数
            for (int rowNum = sheet.getFirstRowNum() + 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    log.warn("当前行无数据importSuppliers，系统忽略，第" + rowNum + "行");
                    continue;
                }
                TAdkSupplier good = new TAdkSupplier();
                good.setAddTime(new Date());
                good.setAddName(user.getUsername());

                good.setAdkSupplierCode(getCellString(row.getCell(0)));

                good.setAdkSupplierName(getCellString(row.getCell(1)));
                if (StringUtils.isBlank(good.getAdkSupplierCode())) {
                    throw new Exception("上传文件数据第" + rowNum + "行第1列有异常数据");
                }
                if (StringUtils.isBlank(good.getAdkSupplierName())) {
                    throw new Exception("上传文件数据第" + rowNum + "行第2列有异常数据");
                }
                goods.add(good);
            }
            adkOrderService.importSuppliers(goods, user);
            result.setCode(0);

        } catch (Exception e) {
            log.error("导入失败", e);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    private String getCellString(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell != null && Cell.CELL_TYPE_STRING == cell.getCellType()) {
            return cell.getStringCellValue();
        }
        if (cell != null && Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    private void checksheet(Sheet sheet) throws Exception {
        // 起始行
        int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
        int endRowNum = sheet.getLastRowNum();// 结束行
        if (startRowNum > endRowNum) {
            throw new Exception("上传文件为空模板, 数据读取从第2行开始。 请下载标准模板并正确填写后重试!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/test")
    public String testxx(HttpServletRequest request, HttpSession session) {
        int pageNo = 3, pageSize = 11;
        Page page = getPageTag(request, pageNo, pageSize);
        return "OK";
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Map<String, Object> add(String params, Long timestamp, String ak, String sig, HttpServletRequest request,
                                   HttpSession session) {
        try {
            if (log.isInfoEnabled()) {
                log.info("adkadd  远程调用开始：：：params=" + params + "\ttimestamp=" + timestamp + "\tak=" + ak + "\tsig="
                        + sig + "\t ip=" + getIpAddress(request));
            }
            ResultInfo<?> result = new ResultInfo<>();
            AdkLog adkLog = initRemoteLog(params, timestamp, ak, sig, getIpAddress(request));
            if (StringUtils.isBlank(params)) {
                return fail(adkLog, ResultCodeConstants.PARAMETER_ERROR_6003, "参数 params  不能为空");
            }
            if (timestamp == null) {
                return fail(adkLog, ResultCodeConstants.PARAMETER_ERROR_6003, "参数 timestamp  不能为空");
            }
            if (StringUtils.isBlank(ak)) {
                return fail(adkLog, ResultCodeConstants.PARAMETER_ERROR_6003, "参数 ak  不能为空");
            }
            if (StringUtils.isBlank(sig)) {
                return fail(adkLog, ResultCodeConstants.PARAMETER_ERROR_6003, "参数 sig  不能为空");
            }
            //

            AdkOrder order = null;
            try {
                order = JSON.parseObject(params, AdkOrder.class);
            } catch (Exception e) {
                log.error("adkadd  远程JSON解析失败：：：\tsig=" + sig, e);
            }
            if (order == null) {
                return fail(adkLog, ResultCodeConstants.PARAMETER_ERROR_6003, "JSON解析失败");
            }
            adkLog.setAdkOrderNo(order.getOrderNo() + "," + order.getTraderName());

//			if (StringUtils.contains(whiteIp, getIpAddress(request))) {
//				if (log.isInfoEnabled()) {
//					log.info("adkadd ip在白名单内：：：\tsig=" + sig + "\t" + getIpAddress(request));
//				}
//				try {
//					adkOrderService.remoteAdd(order, result);
//					adkLog.setOrderNo(order.getOrderNo());
//				} catch (Exception e) {
//					log.error("adkadd  远程添加订单失败1：：：\tsig=" + sig, e);
//                    return fail(adkLog,ResultCodeConstants.SERVICE_ERROR_500 ,StringUtils.substring(e.getMessage(), 0, 1900));
//				}
//				return change(adkLog, result);
//			}
            try {
                boolean pass = checkAuth(params, timestamp, ak, sig);
                if (!pass) {
                    log.error("adkadd ADK签名认证不通过 sig不匹配:" + getIpAddress(request));
                    return fail(adkLog, ResultCodeConstants.SERVICE_ERROR_500, "adkadd ADK签名认证不通过 sig不匹配:" + getIpAddress(request));
                }
            } catch (Exception e) {
                log.error("adkadd ADK签名认证不通过 :" + getIpAddress(request) + "\t params=" + params, e);
                return fail(adkLog, ResultCodeConstants.SERVICE_ERROR_500, "adkadd ADK签名认证不通过  :" + getIpAddress(request));
            }
            try {
                adkOrderService.remoteAdd(order, result);
                if (order != null) {
//                    if("exist:".equals(order.getOrderNo())){
//                        try {adkLogMapper.deleteByPrimaryKey(adkLog.getAdkLogId());} catch (Exception e) { //ignor
//                            }
//                    }
                    adkLog.setOrderNo(order.getOrderNo());
                }
            } catch (Exception e) {
               // log.error("adkadd  远程添加订单失败2：：：\tsig=" + sig, e);
                return fail(adkLog, ResultCodeConstants.SERVICE_ERROR_500, "系统异常" + StringUtils.substring(e.getMessage(), 0, 1900));
            }
            return success(adkLog, result);
        } catch (Exception e) {
            log.error("adkadd  远程添加订单失败：：：\tsig=" + sig, e);
            return fail(null, ResultCodeConstants.SERVICE_ERROR_500, "系统异常" + StringUtils.substring(e.getMessage(), 0, 1900));
        }
    }

    private AdkLog initRemoteLog(String params, Long timestamp, String ak, String sig, String ip) {
        AdkLog adkLog = new AdkLog();
        adkLog.setAdkLogParam(params);
        adkLog.setAdkLogTimestamp(timestamp);
        adkLog.setAdkLogAk(ak);
        adkLog.setAdkLogSig(sig);
        adkLog.setAddTime(new Date());
        adkLog.setRequestId(ip);
        adkLog.setUpdateTime(new Date());
        adkLog.setAdkLogStatus(CommonConstants.DISABLE + "");
        adkLog.setAdkLogMsg("接收消息");
        adkLog.setAdkLogType(CommonConstants.ADK_LOG_RECEIVE);
        adkLogMapper.insert(adkLog);
        return adkLog;
    }

    private Map<String, Object> fail(AdkLog adkLog, int code, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", ResultCodeConstants.FAIL);
        map.put("code", code);
        map.put("message", msg);
        if (adkLog != null && adkLog.getAdkLogId() != null) {
            try {
                adkLog.setAdkLogMsg(msg);
                adkLog.setAdkLogStatus(CommonConstants.DISABLE + "");
                adkLogMapper.updateByPrimaryKey(adkLog);
            } catch (Exception e) {
                log.warn("adkadd  更新日志失败：：：\tsig=", e);
            }
        }
        return map;
    }

    private Map<String, Object> success(AdkLog adkLog, ResultInfo<?> result) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", ResultCodeConstants.SUCCESS);
        map.put("code", 0);
        map.put("message", "执行成功");
        try {
            adkLog.setAdkLogMsg("执行成功" + adkLog.getOrderNo());
            adkLog.setAdkLogStatus(CommonConstants.ENABLE + "");
            adkLogMapper.updateByPrimaryKey(adkLog);
        } catch (Exception e) {
            log.warn("adkadd  更新日志失败：：：\tsig=", e);
        }
        return map;
    }

    private boolean checkAuth(String params, Long timestamp, String ak, String sig)
            throws UnsupportedEncodingException {
        String appendOrigin = "ak=" + ak + "&params="
                + StringUtils.replace(StringUtils.upperCase(URLEncoder.encode(params, "utf-8")), "%28", "(")
                + "&timestamp=" + timestamp + sn;
        String appendOrigin2 = "ak=" + ak + "&params=&timestamp=" + timestamp + sn;
        String serverSig = DigestUtils.md5Hex(appendOrigin);
        String serverSig2 = DigestUtils.md5Hex(appendOrigin2);
        return StringUtils.equals(serverSig, sig) || StringUtils.equals(serverSig2, sig);
    }

    public static String buildJson() {
        AdkOrder order = new AdkOrder();
        order.setComments("杭州艾迪康医学检验中心有限公司");

        order.setInvoiceTraderContactName("仇珍怡");
        order.setInvoiceTraderContactTelephone("0571-87779321");
        order.setInvoiceTraderContactMobile("13675870157");
        order.setOrderNo("ORD-2019001");

        order.setTakeTraderContactName("仇珍怡");
        order.setTakeTraderContactTelephone("0571-87779321");
        order.setTakeTraderContactMobile("13675870157");

        order.setTotalAmount(new BigDecimal("101233.4"));
        order.setTraderName("杭州艾迪康医学检验中心有限公司");
        List<AdkOrderGoods> goods = Lists.newArrayList();
        for (int i = 2; i < 4; i++) {
            AdkOrderGoods good = new AdkOrderGoods();
            good.setGoodsCode("code-" + i);
            good.setGoodsComments("第" + i + "个goods");
            good.setGoodsName("第" + i + "个goods");
            good.setModel("model");
            good.setNum("3");
            good.setPrice(new BigDecimal("44332.2"));
            good.setUnitName("件");
            goods.add(good);
        }
        order.setOrderGoods(goods);
        System.out.println(JSON.toJSON(order));
        return JSON.toJSONString(order);

    }

    public static void main(String[] args) {
        // DigestUtils.md5Hex("DigestUtils")
        String ak = "1";
        String sid = "";
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> paramMap = new TreeMap<String, String>();

        String json = buildJson();
        String p = null;
        try {
            p = "ak=" + ak + "&params=" + URLEncoder.encode(json, "UTF-8") + "&timestamp=1554726774955";
        } catch (UnsupportedEncodingException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        System.out.println(p);
        System.out.println(DigestUtils.md5Hex(p + sn));
        System.out.println(DigestUtils.md5Hex("ak=1&params=&timestamp=1556440457466sk1234"));
        // 12a037e6b6cd0b90e2e0a90c57a234fe
        System.out.println(p + "&sig=" + DigestUtils.md5Hex(p + sn));

        String json1 = "{\"orderno\":\"poord006204\",\"tradername\":\"福州艾迪康医学检验所有限公司\",\"totalamount\":11775.00,\"comments\":\"\",\"ordergoods\":[{\"goodscode\":\"1.1.08.2.1108200013\",\"goodsname\":\"稀释液（迈瑞三分类）\",\"model\":\"20l\",\"unitname\":\"ml\",\"num\":200000.0000,\"price\":0.004000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100169\",\"goodsname\":\"血细胞分析用溶血剂58leoi（bc5600）-迈瑞\",\"model\":\"1l*4瓶/箱\",\"unitname\":\"瓶\",\"num\":8.0000,\"price\":347.750000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100170\",\"goodsname\":\"血细胞分析用溶血剂58leoii（bc5600）-迈瑞\",\"model\":\"500ml*4瓶/箱\",\"unitname\":\"瓶\",\"num\":4.0000,\"price\":463.750000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100171\",\"goodsname\":\"血细胞分析用溶血剂58lba（bc5600）-迈瑞\",\"model\":\"1l*4瓶/箱\",\"unitname\":\"瓶\",\"num\":8.0000,\"price\":360.250000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100172\",\"goodsname\":\"血细胞分析用溶血剂58lh（bc5600）-迈瑞\",\"model\":\"500ml*4瓶/箱\",\"unitname\":\"瓶\",\"num\":12.0000,\"price\":231.750000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100039\",\"goodsname\":\"53探头清洗液（bc-5600）迈瑞\",\"model\":\"50ml/瓶\",\"unitname\":\"ml\",\"num\":150.0000,\"price\":0.900000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100038\",\"goodsname\":\"血细胞分析稀释液58d（bc-5600）迈瑞\",\"model\":\"20l/箱\",\"unitname\":\"l\",\"num\":80.0000,\"price\":6.750000,\"goodscomments\":\"\"}]},{\"orderno\":\"poord006204\",\"tradername\":\"ç¦å·è¾è¿ªåº·å»å­¦æ£éªææéå¬å¸\",\"totalamount\":11775.00,\"comments\":\"\",\"ordergoods\":[{\"goodscode\":\"1.1.08.2.1108200013\",\"goodsname\":\"ç¨éæ¶²ï¼è¿çä¸åç±»ï¼\",\"model\":\"20l\",\"unitname\":\"ml\",\"num\":200000.0000,\"price\":0.004000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100169\",\"goodsname\":\"è¡ç»èåæç¨æº¶è¡å58leoiï¼bc5600ï¼-è¿ç\",\"model\":\"1l*4ç¶/ç®±\",\"unitname\":\"ç¶\",\"num\":8.0000,\"price\":347.750000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100170\",\"goodsname\":\"è¡ç»èåæç¨æº¶è¡å58leoiiï¼bc5600ï¼-è¿ç\",\"model\":\"500ml*4ç¶/ç®±\",\"unitname\":\"ç¶\",\"num\":4.0000,\"price\":463.750000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100171\",\"goodsname\":\"è¡ç»èåæç¨æº¶è¡å58lbaï¼bc5600ï¼-è¿ç\",\"model\":\"1l*4ç¶/ç®±\",\"unitname\":\"ç¶\",\"num\":8.0000,\"price\":360.250000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100172\",\"goodsname\":\"è¡ç»èåæç¨æº¶è¡å58lhï¼bc5600ï¼-è¿ç\",\"model\":\"500ml\",\"unitname\":\"\",\"num\":12.0000,\"price\":231.750000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100039\",\"goodsname\":\"53\",\"model\":\"50ml\",\"unitname\":\"ml\",\"num\":150.0000,\"price\":0.900000,\"goodscomments\":\"\"},{\"goodscode\":\"1.1.08.1.1108100038\",\"goodsname\":\"0\",\"model\":\"20l\",\"unitname\":\"l\",\"num\":80.0000,\"price\":6.750000,\"goodscomments\":\"\"}]}";
        AdkOrder order = null;
        try {
            order = JSON.parseObject(json1, AdkOrder.class);
        } catch (Exception e) {
            logger.error(Contant.ERROR_MSG, e);
        }
    }

}
