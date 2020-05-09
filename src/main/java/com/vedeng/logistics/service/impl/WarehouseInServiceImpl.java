package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.meinian.model.DeliveryOrderDetails;
import com.meinian.model.LogisticsVo;
import com.meinian.model.vo.SendData;
import com.meinian.service.MeinianOderService;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.logistics.dao.BarcodeMapper;
import com.vedeng.logistics.dao.WarehouseGoodsStatusMapper;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.LendOut;

import com.vedeng.system.dao.AttachmentMapper;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.QRCode;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.logistics.dao.LendOutMapper;
import com.vedeng.logistics.model.Barcode;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.service.WarehouseInService;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.service.FtpUtilService;

@Service("warehouseInService")
public class WarehouseInServiceImpl extends BaseServiceimpl implements WarehouseInService {
    public static Logger logger = LoggerFactory.getLogger(WarehouseInServiceImpl.class);

    private static Logger LOGGER = LoggerFactory.getLogger(WarehouseInServiceImpl.class);

    @Resource
    private OrganizationMapper organizationMapper;
    @Autowired
    @Qualifier("ftpUtilService")
    private FtpUtilService ftpUtilService;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Resource
    private MeinianOderService meinianOderService;
    
    @Autowired
    @Qualifier("lendOutMapper")
    private LendOutMapper lendOutMapper;
    
    @Autowired
    @Qualifier("goodsService")
    private GoodsService goodsService;

    @Autowired
    private AttachmentMapper attachmentMapper;

    /**
     * 采购入库列表
     */
    @Override
    public Map<String, Object> getWarehouseinList(BuyorderVo buyorderVo, Page page, List<User> userlist)
            throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 调用采购入库列表
        String url = httpUrl + "logistics/warehousein/getwarehouseinlistpage.htm";
        // 定义反序列化 数据格式
        List<BuyorderVo> list = null;
        final TypeReference<ResultInfo<List<BuyorderVo>>> TypeRef =
                new TypeReference<ResultInfo<List<BuyorderVo>>>() {};
        try {
            ResultInfo<?> result =
                    (ResultInfo<?>) HttpClientUtils.post(url, buyorderVo, clientId, clientKey, TypeRef, page);
            list = (List<BuyorderVo>) result.getData();
            if (list != null && list.size() > 0) {
                for (BuyorderVo bv : list) {
                    // 归属采购：供应商的归属
                    if (ObjectUtils.notEmpty(bv.getTraderId())) {
                        User user = userMapper.getUserByTraderId(bv.getTraderId(), ErpConst.TWO);
                        if (user != null) {
                            bv.setHomePurchasing(getUserNameByUserId(user.getUserId()));
                        }
                    }
                    // 采购部门
                    if (ObjectUtils.notEmpty(bv.getOrgId())) {
                        bv.setBuyDepartmentName(organizationMapper.selectByPrimaryKey(bv.getOrgId()).getOrgName());
                    }
                    // 采购人员
                    if (ObjectUtils.notEmpty(bv.getUserId())) {
                        bv.setBuyPerson(getUserNameByUserId(bv.getUserId()));
                        for (User us : userlist) {
                            if (us.getUserId().equals(bv.getUserId())) {
                                bv.setIsView(ErpConst.ONE);
                                break;
                            }
                        }
                    }

                }
            }
            map.put("list", list);

            map.put("page", result.getPage());

        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return map;
    }

    /**
     * 根据数量生成二维码
     */
    @Override
    public ResultInfo<?> addBarcode(Integer num, Barcode barcode) throws Exception {
        String url = httpUrl + "logistics/warehousein/addbarcode.htm";
        List<Barcode> barcodeList = null;
        List<QRCode> qrcodeList = new ArrayList<>();
        final TypeReference<ResultInfo<List<Barcode>>> TypeRef = new TypeReference<ResultInfo<List<Barcode>>>() {};
        Map<String, Object> barcodeMap = new HashMap<>();
        barcodeMap.put("barcode", barcode);
        barcodeMap.put("num", num);
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, barcodeMap, clientId, clientKey, TypeRef);
            // 接口返回条码生成的记录
            if (result.getCode() == 0) {
                barcodeList = (List<Barcode>) result.getData();
                // 二維碼內容，保存地址，文件名稱（無logo）
                for (Barcode b : barcodeList) {
                    QRCode qrcode = new QRCode();
                    qrcode.setContent(b.getBarcode());
                    qrcode.setRelatedId(b.getBarcodeId());
                    qrcodeList.add(qrcode);
                }
                // 根据生成的条码记录生成二维码
                ResultInfo<?> qrresult = ftpUtilService.makeQRCode(qrcodeList, "warehouseIn");
                if (qrresult.getCode() == 0) {
                    List<Attachment> attachmentList = new ArrayList<>();
                    for (QRCode q : (List<QRCode>) qrresult.getListData()) {
                        Attachment attachment = new Attachment();
                        attachment.setUri(q.getFtpPath());
                        attachment.setName(q.getFtpName());
                        attachment.setRelatedId(q.getRelatedId());
                        attachment.setAddTime(barcode.getAddTime());
                        attachment.setDomain(picUrl);
                        attachment.setCreator(barcode.getCreator());
                        attachment.setSort(100);
                        attachment.setIsDefault(0);
                        attachment.setAttachmentFunction(SysOptionConstant.ID_499);
                        attachment.setAttachmentType(SysOptionConstant.ID_500);
                        attachmentList.add(attachment);
                    }
                    String url_a = httpUrl + "attachment/saveattachments.htm";
                    ResultInfo<?> atresult =
                            (ResultInfo<?>) HttpClientUtils.post(url_a, attachmentList, clientId, clientKey, TypeRef);
                    if (atresult.getCode() == 0) {
                        return atresult;
                    } else {
                        return new ResultInfo();
                    }
                } else {
                    return new ResultInfo();
                }
            } else {
                return new ResultInfo();
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo();

        }
    }

    /**
     * 批量生产生成二维码
     */
    @Override
    public ResultInfo<?> addBarcodes(List<Barcode> barcode) throws Exception {
        String url = httpUrl + "logistics/warehousein/addbarcodes.htm";
        List<Barcode> barcodeList = null;
        List<QRCode> qrcodeList = new ArrayList<>();
        final TypeReference<ResultInfo<List<Barcode>>> TypeRef = new TypeReference<ResultInfo<List<Barcode>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, barcode, clientId, clientKey, TypeRef);
            // 接口返回条码生成的记录
            if (result.getCode() == 0) {
                barcodeList = (List<Barcode>) result.getData();
                // 二維碼內容，保存地址，文件名稱（無logo）
                for (Barcode b : barcodeList) {
                    QRCode qrcode = new QRCode();
                    qrcode.setContent(b.getBarcode());
                    qrcode.setRelatedId(b.getBarcodeId());
                    qrcodeList.add(qrcode);
                }
                // 根据生成的条码记录生成二维码
                ResultInfo<?> qrresult = ftpUtilService.makeQRCode(qrcodeList, "warehouseIn");
                if (qrresult.getCode() == 0) {
                    List<Attachment> attachmentList = new ArrayList<>();
                    for (QRCode q : (List<QRCode>) qrresult.getListData()) {
                        Attachment attachment = new Attachment();
                        attachment.setUri(q.getFtpPath());
                        attachment.setName(q.getFtpName());
                        attachment.setRelatedId(q.getRelatedId());
                        attachment.setAddTime(barcode.get(0).getAddTime());
                        attachment.setDomain(picUrl);
                        attachment.setCreator(barcode.get(0).getCreator());
                        attachment.setSort(100);
                        attachment.setIsDefault(0);
                        attachment.setAttachmentFunction(SysOptionConstant.ID_499);
                        attachment.setAttachmentType(SysOptionConstant.ID_500);
                        attachmentList.add(attachment);
                    }
                    String url_a = httpUrl + "attachment/saveattachments.htm";
                    ResultInfo<?> atresult =
                            (ResultInfo<?>) HttpClientUtils.post(url_a, attachmentList, clientId, clientKey, TypeRef);
                    if (atresult.getCode() == 0) {
                        return atresult;
                    } else {
                        return new ResultInfo();
                    }
                } else {
                    return new ResultInfo();
                }
            } else {
                return new ResultInfo();
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo();

        }
    }

    /**
     * 获取二维码信息
     */
    @Override
    public List<Barcode> getBarcode(Barcode barcode) throws Exception {
        String url = httpUrl + "logistics/warehousein/getbarcode.htm";
        List<Barcode> barcodeList = null;
        final TypeReference<ResultInfo<List<Barcode>>> TypeRef = new TypeReference<ResultInfo<List<Barcode>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, barcode, clientId, clientKey, TypeRef);
            // 接口返回条码生成的记录
            if (result.getCode() == 0) {
                barcodeList = (List<Barcode>) result.getData();
                // 二维码人员查询
                List<Integer> userIds = new ArrayList<>();
                if (null != barcodeList) {
                    for (Barcode b : barcodeList) {
                        userIds.add(b.getCreator());
                    }
                }
                if (userIds.size() > 0) {
                    //去重
                    userIds = new ArrayList(new HashSet(userIds));
                    List<User> userList = userMapper.getUserByUserIds(userIds);
                    // 信息补充
                    if (null != barcodeList) {
                        for (Barcode b : barcodeList) {
                            for (User u : userList) {
                                if (b.getCreator().equals(u.getUserId())) {
                                    b.setCreatorName(u.getUsername());
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return barcodeList;
    }

    /**
     * 修改二维码信息
     */
    @Override
    public ResultInfo<?> editBarcode(List<Barcode> barcodeList) throws Exception {
        String url = httpUrl + "logistics/warehousein/editbarcode.htm";
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, barcodeList, clientId, clientKey, TypeRef);
            // 接口返回条码生成的记录
            if (result.getCode() == 0) {
                return new ResultInfo(0, "修改成功");
            } else {
                return new ResultInfo();
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo();
        }
    }

    /**
     * 补充老数据中的二维码
     */
    @Override
    public ResultInfo<?> supplementBarcode(List<Barcode> barcodeList) throws Exception {
        final TypeReference<ResultInfo<Attachment>> TypeRef = new TypeReference<ResultInfo<Attachment>>() {};
        List<QRCode> qrcodeList = new ArrayList<>();
        for (Barcode b : barcodeList) {
            QRCode qrcode = new QRCode();
            qrcode.setContent(b.getBarcode());
            qrcode.setRelatedId(b.getBarcodeId());
            qrcodeList.add(qrcode);
        }
        // 根据生成的条码记录生成二维码
        ResultInfo<?> qrresult = ftpUtilService.makeQRCode(qrcodeList, "warehouseIn");
        if (qrresult.getCode() == 0) {
            List<Attachment> attachmentList = new ArrayList<>();
            for (QRCode q : (List<QRCode>) qrresult.getListData()) {
                Attachment attachment = new Attachment();
                attachment.setRelatedId(q.getRelatedId());
//                attachment.setAttachmentFunction(SysOptionConstant.ID_499);
//                attachment.setAttachmentType(SysOptionConstant.ID_500);
                String url_a = httpUrl + "attachment/getattachment.htm";
                ResultInfo<?> aresult =
                        (ResultInfo<?>) HttpClientUtils.post(url_a, attachment, clientId, clientKey, TypeRef);
                if (aresult.getCode() == 0) {
                    // 查询出附件的主键id
                    Attachment a = (Attachment) aresult.getData();
                    if (null != a) {
                        attachment.setAttachmentId(a.getAttachmentId());
                    }

                }
                attachment.setUri(q.getFtpPath());
                attachment.setName(q.getFtpName());

                attachment.setAddTime(barcodeList.get(0).getAddTime());
                attachment.setDomain(picUrl);
                attachment.setCreator(barcodeList.get(0).getCreator());
                attachment.setSort(100);
                attachment.setIsDefault(0);
                attachment.setAttachmentFunction(SysOptionConstant.ID_499);
                attachment.setAttachmentType(SysOptionConstant.ID_500);
                attachmentList.add(attachment);
            }
            String url_a = httpUrl + "attachment/saveattachments.htm";
            ResultInfo<?> atresult =
                    (ResultInfo<?>) HttpClientUtils.post(url_a, attachmentList, clientId, clientKey, TypeRef);
            if (atresult.getCode() == 0) {
                return atresult;
            } else {
                return new ResultInfo();
            }
        } else {
            return new ResultInfo();
        }
    }

    /**
     * 批量生成多个二维码并存到数据库
     */
    @Override
    public List<WarehouseGoodsOperateLog> getNoWarehousein(BuyorderVo buyorderVo) {
        String url = httpUrl + "warehousegoodsoperatelog/getnowarehousein.htm";
        List<WarehouseGoodsOperateLog> wgolList = null;
        final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef =
                new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderVo, clientId, clientKey, TypeRef);
            // 接口返回条码生成的记录
            if (result.getCode() == 0) {
                wgolList = (List<WarehouseGoodsOperateLog>) result.getData();
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return wgolList;
    }

    /**
     * 根据采购单号获取所有已入库条码信息
     */
    @Override
    public List<Barcode> getWarehouseInBarcode(Barcode barcode) {
        String url = httpUrl + "logistics/warehousein/getwarehouseinbarcode.htm";
        List<Barcode> barcodeList = null;
        final TypeReference<ResultInfo<List<Barcode>>> TypeRef = new TypeReference<ResultInfo<List<Barcode>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, barcode, clientId, clientKey, TypeRef);
            // 接口返回条码生成的记录
            if (result.getCode() == 0) {
                barcodeList = (List<Barcode>) result.getData();
                // 二维码人员查询
                List<Integer> userIds = new ArrayList<>();
                if (null != barcodeList) {
                    for (Barcode b : barcodeList) {
                        userIds.add(b.getCreator());
                    }
                }
                if (userIds.size() > 0) {
                    //去重
                    userIds = new ArrayList(new HashSet(userIds));
                    List<User> userList = userMapper.getUserByUserIds(userIds);
                    // 信息补充
                    if (null != barcodeList) {
                        for (Barcode b : barcodeList) {
                            for (User u : userList) {
                                if (b.getCreator().equals(u.getUserId())) {
                                    b.setCreatorName(u.getUsername());
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
        return barcodeList;
    }

    /**
     * 根据二维码id判断该二维码是否已经入库
     */
    @Override
    public ResultInfo<?> checkBarcode(Barcode barcode) throws Exception {
        // TODO Auto-generated method stub
        String url = httpUrl + "logistics/warehousein/checkbarcode.htm";
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, barcode, clientId, clientKey, TypeRef);
            // 接口返回二维码入库数量
            if (result.getCode() == 0) {
                return new ResultInfo(0, "该条形码已经入库，不可作废");
            } else {
                return new ResultInfo(-1, "该条形码未入库");
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo();
        }
    }

    @Override
    public ResultInfo<?> saveWarehouseinAttachment(Attachment attachment) {
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<Attachment>> TypeRef = new TypeReference<ResultInfo<Attachment>>() {};
        String url = httpUrl + "logistics/warehousein/savewarehouseinattachment.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, attachment, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    @Override
    public List<Attachment> getAttachmentList(Attachment attachment) {
        List<Attachment> list = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<Attachment>>> TypeRef =
                new TypeReference<ResultInfo<List<Attachment>>>() {};
        String url = httpUrl + "logistics/warehousein/getattachmentbyatt.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, attachment, clientId, clientKey, TypeRef);
            list = (List<Attachment>) result.getData();

            // 操作人信息补充
            if (list.size() > 0) {
                List<Integer> userIds = new ArrayList<>();
                for (Attachment b : list) {
                    if (b.getCreator() > 0) {
                        userIds.add(b.getCreator());
                    }
                }

                if (userIds.size() > 0) {
                    //去重
                    userIds = new ArrayList(new HashSet(userIds));
                    List<User> userList = userMapper.getUserByUserIds(userIds);

                    for (Attachment b : list) {
                        for (User u : userList) {
                            if (u.getUserId().equals(b.getCreator())) {
                                b.setUsername(u.getUsername());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return list;
    }

    @Override
    public ResultInfo<?> delWarehouseinAttachment(Attachment attachment) {
        // TODO Auto-generated method stub
        ResultInfo<?> result = null;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        String url = httpUrl + "logistics/warehousein/delwarehouseinattachment.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, attachment, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        }
        return result;
    }

    /**
     * 批量生产生成二维码(不生成图片)
     */
    @Override
    public ResultInfo<?> addBarcodeNoImg(List<Barcode> barcode) throws Exception {
        String url = httpUrl + "logistics/warehousein/addbarcodes.htm";
        List<Barcode> barcodeList = null;
        List<QRCode> qrcodeList = new ArrayList<>();
        final TypeReference<ResultInfo<List<Barcode>>> TypeRef = new TypeReference<ResultInfo<List<Barcode>>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, barcode, clientId, clientKey, TypeRef);
            // 接口返回条码生成的记录
            if (result.getCode() == 0) {
                barcodeList = (List<Barcode>) result.getData();
                // 二維碼內容，保存地址，文件名稱（無logo）
                for (Barcode b : barcodeList) {
                    QRCode qrcode = new QRCode();
                    qrcode.setContent(b.getBarcode());
                    qrcode.setRelatedId(b.getBarcodeId());
                    qrcodeList.add(qrcode);
                }
                // 根据生成的条码记录生成二维码
                // ResultInfo<?> qrresult = ftpUtilService.makeQRCode(qrcodeList,"warehouseIn");
                // if (qrresult.getCode() == 0) {
                List<Attachment> attachmentList = new ArrayList<>();
                for (QRCode q : qrcodeList) {
                    Attachment attachment = new Attachment();
                    attachment.setUri(q.getFtpPath());
                    attachment.setName(q.getFtpName());
                    attachment.setRelatedId(q.getRelatedId());
                    attachment.setAddTime(barcode.get(0).getAddTime());
                    attachment.setDomain(picUrl);
                    attachment.setCreator(barcode.get(0).getCreator());
                    attachment.setSort(100);
                    attachment.setIsDefault(0);
                    attachment.setAttachmentFunction(SysOptionConstant.ID_499);
                    attachment.setAttachmentType(SysOptionConstant.ID_500);
                    attachmentList.add(attachment);
                }
                String url_a = httpUrl + "attachment/saveattachments.htm";
                ResultInfo<?> atresult =
                        (ResultInfo<?>) HttpClientUtils.post(url_a, attachmentList, clientId, clientKey, TypeRef);
                if (atresult.getCode() == 0) {
                    return atresult;
                } else {
                    return new ResultInfo();
                }
                // } else {
                // return new ResultInfo();
                // }
            } else {
                return new ResultInfo();
            }
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return new ResultInfo();

        }
    }

    /**
     * 上传供货单
     * @param express
     * @return
     * @auther Bert
     * @data 2018 11 12  17 13
     */
    @Override
    public String sendMeinianOrders(Express express) {
        ResultInfo<?> queryResult = null;
        //请求的结果
            String url = httpUrl + "tradercustomer/getSendToMeinianData.htm";
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        try {
            //返回结果
            queryResult = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
        } catch (Exception e) {
            LOGGER.error("dbCenter getSendToMeinianData error:", e);
        }
        //对返回值做空指正处理
        if (ObjectUtils.allNotNull(queryResult)){

            //获取数据
            Object data = queryResult.getData();
            //空指针异常处理
            if ( null == data){
                return ErpConst.SEND_DATA_FAIL;
            }

            try {
                //将获取到的数据转成json数组
                net.sf.json.JSONArray jsonarray = net.sf.json.JSONArray.fromObject(data);
                //将获取到的数据类型的转换
                List<SendData> sendDataList = (List<SendData>)net.sf.json.JSONArray.toList(jsonarray, new SendData(), new JsonConfig());
                List<SendData> sendData = sendDataList.stream().filter( x->StringUtil.isNotEmpty(x.getOrderNumber())).collect(Collectors.toList());
                //对返回值做空指正处理
                if (CollectionUtils.isNotEmpty(sendData)){
                    LogisticsVo logistics = new LogisticsVo();
                    logistics.setExpressNumber(express.getLogisticsNo());
                    //供货单详情 details
                    List<DeliveryOrderDetails> list = new ArrayList<>();
                    sendData.stream().forEach( x -> {
                        //进行参数的判断
                        if (!StringUtil.isEmpty(x.getOrderNumber())){
                            DeliveryOrderDetails deliveryOrderDetails =  new DeliveryOrderDetails();
                            //设置所需属性值（价格,批次，批次失效，备注，供货数量 , 订单号 , 产品编码, 供方货号/供货平台产品ID , 产品型号）
                            deliveryOrderDetails.setPrice(x.getPrice().doubleValue());
                            deliveryOrderDetails.setBatchNumber(x.getBatchNumber());
                            if (x.getBatchExpiryTime() != 0){
                                deliveryOrderDetails.setBatchExpiryTime(DateUtil.convertString(x.getBatchExpiryTime(), "yyyy-MM-dd"));
                            }else{
                                deliveryOrderDetails.setBatchExpiryTime("2050-12-31");
                            }
                            deliveryOrderDetails.setRemark(x.getRemark());
                            deliveryOrderDetails.setSupplyNumber(x.getSupplyNumber().longValue());
                            deliveryOrderDetails.setOrderNumber(x.getOrderNumber());
                            deliveryOrderDetails.setProductCode(x.getProductCode());
                            deliveryOrderDetails.setSupplierNumber(x.getSupplierNumber());
                            if(null != x.getSerialNumber() && !"".equals(x.getSerialNumber())){
                            	deliveryOrderDetails.setSerialNumber(x.getSerialNumber());
                            }
                            list.add(deliveryOrderDetails);
                        }
                    });
                    logistics.setDetails(list);

                    //查询到的订单信息推送至美年的服务器
                    String result = meinianOderService.sendToMeinianSupply(logistics);
                    //成功返回S 失败返回E
                    if (ErpConst.SEND_DATA_FAIL.equalsIgnoreCase(result)) {
                        LOGGER.error("send meinian order error :" + JSON.toJSONString(logistics));
                    }

                    return result;
                    /*if (ErpConst.SEND_DATA_SUCCESS.equals(result)){
                        return ErpConst.SEND_DATA_SUCCESS;
                    }*/
                }
            } catch (Exception e) {
                LOGGER.error("meinian json error:", e);
            }
        }
        return ErpConst.SEND_DATA_FAIL;
    }

	@Override
	public Map<String, Object> getlendoutListPage(HttpServletRequest request, LendOut lendout, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("lendout", lendout);
		List<LendOut> list = lendOutMapper.getlendoutListPage(map);
		map.put("list", list);
		return map;
	}

	@Override
	public Goods getLendoutGoodsInfo(Integer lendOutId) {
		LendOut lendout = lendOutMapper.selectByPrimaryKey(lendOutId);
//		Integer num = lendOutMapper.getdeliveryNum(lendout);//以出库数量
		//外借订单下的产品信息
		Goods goods = new Goods();
		goods.setGoodsId(lendout.getGoodsId());
		goods = goodsService.getGoodsById(goods);
		//已发数量
		goods.setDeliveryNum(lendout.getDeliverNum());
		goods.setNum(lendout.getLendOutNum());
		return goods;
	}

    @Override
    public ResultInfo saveSkuBarcode(List<Goods> goodsList, User user) {
        List<QRCode> qrcodeList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(goodsList)) {
            final TypeReference<ResultInfo<List<Barcode>>> TypeRef = new TypeReference<ResultInfo<List<Barcode>>>() {
            };
            try {
                // 接口返回条码生成的记录
                // 二維碼內容，保存地址，文件名稱（無logo）
                for (Goods goods : goodsList) {
                    QRCode qrcode = new QRCode();
                    qrcode.setContent(goods.getSku());
                    qrcode.setRelatedId(goods.getGoodsId());
                    qrcodeList.add(qrcode);
                }
                // 根据生成的条码记录生成二维码
                ResultInfo<?> qrresult = ftpUtilService.makeQRCode(qrcodeList, "warehouseIn");
                if (qrresult.getCode() == 0) {
                    List<Attachment> attachmentList = new ArrayList<>();
                    for (QRCode q : (List<QRCode>) qrresult.getListData()) {
                        Attachment attachment = new Attachment();
                        attachment.setUri(q.getFtpPath());
                        attachment.setName(q.getFtpName());
                        attachment.setRelatedId(q.getRelatedId());
                        attachment.setAddTime(DateUtil.sysTimeMillis());
                        attachment.setDomain(picUrl);
                        attachment.setCreator(user.getUserId());
                        attachment.setSort(100);
                        attachment.setIsDefault(0);
                        //sku二维码
                        attachment.setAttachmentFunction(SysOptionConstant.ID_1400);
                        attachment.setAttachmentType(SysOptionConstant.ID_500);
                        attachmentList.add(attachment);
                    }
                    String url_a = httpUrl + "attachment/saveattachments.htm";
                    ResultInfo<?> atresult =
                            (ResultInfo<?>) HttpClientUtils.post(url_a, attachmentList, clientId, clientKey, TypeRef);
                    if (atresult.getCode() == 0) {
                        return atresult;
                    }
                }
            } catch (Exception e) {
                logger.error("saveSkuBarcode error:{}", e);
            }
        }
        return new ResultInfo();
    }

    @Override
    public List<Goods> checkSkuBarcode(List<Integer> goodsIdList) {
        List<Goods> goodsList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(goodsIdList)) {
            for (Integer goodsId : goodsIdList) {
                //查询当前sku二维码
                Attachment attachment = getSkuBarcode(goodsId);
                if (attachment == null) {
                    Goods goods = new Goods();
                    goods.setGoodsId(goodsId);
                    goods.setSku("V" + goodsId);
                    goodsList.add(goods);
                }
            }
        }
        return goodsList;
    }

    @Override
    public Attachment getSkuBarcode(Integer goodsId) {
        return attachmentMapper.getSkuBarcodeByGoodsId(goodsId);
    }

    @Override
    public List<Attachment> getSkuBarcodeList(List<Integer> goodsIds) {
        List<Attachment> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(goodsIds)){
            for (Integer goodsId : goodsIds) {
                if(goodsId != null) {
                    Attachment skuBarcode = getSkuBarcode(goodsId);
                    if(skuBarcode != null) {
                        list.add(skuBarcode);
                    }
                }
            }
        }
        return list;
    }
}
