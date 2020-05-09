package com.newtask;

import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.vo.CapitalBillDetailVo;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.reportSaleorder.model.ReportCapital;
import com.vedeng.reportSaleorder.model.vo.ReportSaleorderVo;
import com.vedeng.reportSaleorder.service.ReportSaleorderService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <b>Description:</b>订单报表数据初始化<br>
 * @Note
 * <b>Author:calvin</b> 
 * <br><b>Date:</b> 2019/12/30
 */
@JobHandler(value = "reportSaleorderHandler")
@Component
public class ReportSaleorderHandler extends BaseHandler{
    private  final Logger _LOG = LoggerFactory.getLogger(ReportSaleorderHandler.class);

    @Autowired
    private ReportSaleorderService reportSaleorderService;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        return getReportList();
    }

    /**
     * 获取订单数据
     * @return
     * @Author Cooper.xu
     * @Date 2019/04/29 11:15
     */
    public ReturnT<String> getReportList(){
        _LOG.info("《《《《《《《《《《《《==报表数据开始处理==》》》》》》》》》》》》》");
        try{
            //查询当前表中是否有数据，以确定是否需要初始化所有数据
            Integer orderNum = reportSaleorderService.getOrderNum();
            //大于0，处理当月数据
            if (orderNum > 0){
                _LOG.info("《《《《《《《《《《《《处理当月数据");
                /**查询当月满足条件的订单数据，查询条件：
                 * 1、非售后订单
                 * 2、订单金额>0
                 * 3、四大业务部门的销售订单
                 * 4、非售后创建的订单
                 * 5、订单状态为进行中和已完结
                 * 6、订单可发货时间为当月起始到计算时间结束
                 * */
                Map<String,Object> map = new HashMap<>();
                map.put("orderType",2);//2为售后订单
                map.put("totalAmount",0);//订单金额
                map.put("companyId",1);//公司ID
                /*List<Integer> orgIdList = new ArrayList<>();
                orgIdList.add(36);
                orgIdList.add(38);
                orgIdList.add(39);
                orgIdList.add(40);
                map.put("orgIdList",orgIdList);
                List<Integer> orderStatusList = new ArrayList<>();
                orderStatusList.add(1);
                orderStatusList.add(2);
                map.put("orderStatusList",orderStatusList);*/
                //String dateNow = DateUtil.convertString(System.currentTimeMillis(),"dd");
                Calendar calendar1 = Calendar.getInstance();
                if ("1".equals(String.valueOf(calendar1.get(Calendar.DAY_OF_MONTH)))){//如果是第一个月的第一天，就把时间的月份设为上个月
                    calendar1.add(Calendar.MONTH, -1);
                }
                //设为月份的第一天零时零分零秒
                calendar1.set(Calendar.DAY_OF_MONTH,1);
                //将小时至0
                calendar1.set(Calendar.HOUR_OF_DAY, 0);
                //将分钟至0
                calendar1.set(Calendar.MINUTE, 0);
                //将秒至0
                calendar1.set(Calendar.SECOND,0);
                //将毫秒至0
                calendar1.set(Calendar.MILLISECOND, 0);
                Long beginTime = calendar1.getTimeInMillis();
                String thisMon = "";
                if (calendar1.get(Calendar.MONTH)<10){
                    thisMon = calendar1.get(Calendar.YEAR)+"/0"+(calendar1.get(Calendar.MONTH)+1);
                }else {
                    thisMon = calendar1.get(Calendar.YEAR)+"/"+(calendar1.get(Calendar.MONTH)+1);
                }
                map.put("beginTime", beginTime);//当前月份的第一天凌晨
                List<ReportSaleorderVo> reportSaleorderVoList = reportSaleorderService.getSaleorderList(map);
                if (!CollectionUtils.isEmpty(reportSaleorderVoList)) {
                    //根据一定的逻辑处理数据，计算部门统计数据，如退款金额、订货金额等
                    List<ReportSaleorderVo> reportSaleorderList = this.doSaleorderList(reportSaleorderVoList);
                    if (!CollectionUtils.isEmpty(reportSaleorderList)) {
                        //删除库中当月已有的订单数据
                        Integer deleResult = reportSaleorderService.deleteReportSaleorderListThisMon(thisMon);
                        //插入当月的新的订单数据
                        Integer insertResult = reportSaleorderService.insertBatch(reportSaleorderList);
                    }
                }
                //计算当月的到款额记录
                List<ReportCapital> reportCapitalList = reportSaleorderService.getReportCapitalList(map);
                if (!CollectionUtils.isEmpty(reportCapitalList)) {
                    //删除库中当月的到款额记录
                    Integer capitalDeleResult = reportSaleorderService.deleteReportCapitalListThisMon(thisMon);
                    //插入当月的新的到款额记录
                    Integer capitalInsertResult = reportSaleorderService.insertReportCapitalBatch(reportCapitalList);
                }
            } else {//小于等于0，初始化所有数据
                _LOG.info("《《《《《《《《《《《《初始化所有数据");
                //分阶段查询数据，2018年2月份以后的数据为一阶段，2018年2月份以前的数据为一阶段，老ERP数据
                /**查询2018年2月份以后的订单数据第一阶段，查询条件：
                 * 1、非售后订单
                 * 2、订单金额 > 0
                 * 3、四大业务部门的销售订单
                 * 4、非售后创建的订单
                 * 5、订单状态为进行中和已完结
                 * 6、生效时间 >= 2018年2月
                 * 7、已付款
                 * */
                Map<String,Object> map = new HashMap<>();
                map.put("orderType",2);//2为售后订单
                map.put("totalAmount",0);//订单金额
                map.put("companyId",1);//公司ID
                Long beginTime = DateUtil.convertLong("2018-02-01 00:00:00","yyyy-MM-dd HH:mm:ss");
                map.put("beginTime", beginTime);
                List<ReportSaleorderVo> reportSaleorderVoFirstList = reportSaleorderService.getSaleorderList(map);
                /**查询2018年2月份以前的订单数据，查询条件：
                 * 1、非售后订单
                 * 2、订单金额 > 0
                 * 3、四大业务部门的销售订单
                 * 4、非售后创建的订单
                 * 5、订单状态为进行中和已完结
                 * 6、订单可发货时间 < 2018年2月
                 * */
                map.remove("beginTime");
                map.put("validBeginTime", DateUtil.convertLong("2017-01-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
                map.put("validEndTime", DateUtil.convertLong("2018-02-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
                map.put("isAccountPeriod",0);//是否账期
                map.put("paymentStatus",0);//支付状态
                List<String> userNameList = new ArrayList<>();//需要去除的订单的创建者姓名
                userNameList.add("Ada.hong");
                userNameList.add("emily.zhang");
                userNameList.add("sophia.lan");
                map.put("userNameList",userNameList);
                List<ReportSaleorderVo> reportSaleorderVoSecondList = reportSaleorderService.getSaleorderList(map);
                //合并两阶段订单数据，并根据订单号去掉重复订单
                List<ReportSaleorderVo> reportSaleorderVoList = new ArrayList<>();
                reportSaleorderVoList.addAll(reportSaleorderVoFirstList);
                reportSaleorderVoList.addAll(reportSaleorderVoSecondList);
                if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                    //根据一定的逻辑处理数据，计算部门统计数据，如退款金额、订货金额、订单时间等
                    List<ReportSaleorderVo> reportSaleorderList = this.doSaleorderList(reportSaleorderVoList);
                    // 根据订单号去除重复数据
                    List<ReportSaleorderVo> tempList = (List<ReportSaleorderVo>) reportSaleorderList.stream().collect(
                            collectingAndThen(toCollection(() -> new TreeSet<>(comparing(ReportSaleorderVo::getSaleorderNo))), ArrayList::new)
                    );
                    //插入组合成的订单数据
                    if (!CollectionUtils.isEmpty(tempList)) {
                        Integer insertResult = reportSaleorderService.insertBatch(tempList);
                    }
                }
                //初始化所有的到款额记录
                map.put("beginTime", DateUtil.convertLong("2017-01-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
                List<ReportCapital> reportCapitalList = reportSaleorderService.getReportCapitalList(map);
                if (!CollectionUtils.isEmpty(reportCapitalList)) {
                    //插入到款额记录
                    Integer capitalInsertResult = reportSaleorderService.insertReportCapitalBatch(reportCapitalList);
                }
            }
        }catch (Exception e){
            _LOG.error("获取订单数据异常:",e);
             return ReturnT.FAIL;
        }
        _LOG.info("《《《《《《《《《《《《==报表数据处理结束==》》》》》》》》》》》》》");
        return ReturnT.SUCCESS;
    }


    /**
     * 处理订单数据
     * @param reportSaleorderVoList
     * @return
     * @Author Cooper.xu
     * @Date 2019/05/05 11:15
     */
    public List<ReportSaleorderVo> doSaleorderList(List<ReportSaleorderVo> reportSaleorderVoList){
        try{
            if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                Long beginTime = DateUtil.convertLong("2017-01-01 00:00:00","yyyy-MM-dd HH:mm:ss");//订单开始时间
                Long demarcatedTime = DateUtil.convertLong("2018-02-01 00:00:00","yyyy-MM-dd HH:mm:ss");//老ERP和新ERP数据分界时间
                //按照订单所属部门进行第一次筛选，必须是四大业务部门的订单，且创建人所属部门不可为售后
                reportSaleorderVoList = this.doSaleorderListByOrg(reportSaleorderVoList);
                //获取订单客户的一些信息
                reportSaleorderVoList = this.getSaleorderCustomerInfo(reportSaleorderVoList);
                //查询订单的采购金额
                reportSaleorderVoList = this.getSaleorderBuyAmount(reportSaleorderVoList);
                //查询订单的退款金额
                reportSaleorderVoList = this.getSaleorderRefundAmount(reportSaleorderVoList);
                //按照时间对数据做第二次筛选并按时间做分类
                List<ReportSaleorderVo> saleorderVoNearTimeList = new ArrayList<>();//2018年02月01日 00：00：00 到今天的订单列表
                List<ReportSaleorderVo> saleorderVoAgoTimeList = new ArrayList<>();//2017年01月01日 00：00：00 至 2018年02月01日 00：00：00 的订单列表
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList){
                    //可发货时间 >= 2018-02-01 00:00:00 且 生效时间 >= 2018-02-01 00:00:00
                    if (reportSaleorderVo.getSatisfyDeliveryTime() >= demarcatedTime &&
                            reportSaleorderVo.getValidTime() >= demarcatedTime){
                        saleorderVoNearTimeList.add(reportSaleorderVo);
                    }
                    //生效时间 >= 2017-01-01 00:00:00 且 生效时间 < 2018-02-01 00:00:00
                    if (reportSaleorderVo.getValidTime() < demarcatedTime &&
                            reportSaleorderVo.getValidTime() >= beginTime){
                        saleorderVoAgoTimeList.add(reportSaleorderVo);
                    }
                }
                //对订单的时间做处理
                List<ReportSaleorderVo> reportSaleorderList = new ArrayList<>();
                List<ReportSaleorderVo> saleorderVoNearTimeLastList = this.doSaleorderNearTimeList(saleorderVoNearTimeList);
                if ( saleorderVoNearTimeLastList != null){
                    reportSaleorderList.addAll(saleorderVoNearTimeLastList);
                }
                List<ReportSaleorderVo> saleorderVoAgoTimeLastList = this.doSaleorderAgoTimeList(saleorderVoAgoTimeList);
                if ( saleorderVoAgoTimeLastList != null) {
                    reportSaleorderList.addAll(saleorderVoAgoTimeLastList);
                }

                return reportSaleorderList;
            }
            return null;
        }catch (Exception e){
            _LOG.error("处理订单数据异常:",e);
            return null;
        }
    }
    /**
     * 按照部门进行订单筛选
     * @param reportSaleorderVoList
     * @return
     * @Author Cooper.xu
     * @Date 2019/05/05 11:15
     */
    public List<ReportSaleorderVo> doSaleorderListByOrg(List<ReportSaleorderVo> reportSaleorderVoList){
        try{
            //查询创建人部门等信息列表
            if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                List<ReportSaleorderVo> reportSaleorderVoCreatorOrgList = reportSaleorderService.getSaleorderCreatorOrgList(reportSaleorderVoList);
                List<ReportSaleorderVo> reportSaleorderVoCreatorOrgLastList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(reportSaleorderVoCreatorOrgList)){
                    for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList){
                        for (ReportSaleorderVo reportSaleorderVoCreatorOrg : reportSaleorderVoCreatorOrgList){
                            //初始化订单创建人部门信息
                            if (reportSaleorderVo.getSaleorderId().equals(reportSaleorderVoCreatorOrg.getSaleorderId())){
                                reportSaleorderVo.setCreatorDeptId(reportSaleorderVoCreatorOrg.getCreatorDeptId());
                                reportSaleorderVo.setCreatorDeptName(reportSaleorderVoCreatorOrg.getCreatorDeptName());
                                reportSaleorderVo.setCreatorParentDeptId(reportSaleorderVoCreatorOrg.getCreatorParentDeptId());
                                reportSaleorderVo.setCreatorParentDeptName(reportSaleorderVoCreatorOrg.getCreatorParentDeptName());
                                reportSaleorderVoCreatorOrgLastList.add(reportSaleorderVo);
                            }
                        }
                    }
                }
                List<ReportSaleorderVo> reportSaleorderVoLastList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(reportSaleorderVoCreatorOrgLastList)){
                    //查询订单所属人部门等信息
                    List<ReportSaleorderVo> reportSaleorderVoValidOrgList = reportSaleorderService.getSaleorderValidOrgList(reportSaleorderVoCreatorOrgLastList);
                    for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoCreatorOrgLastList) {
                        for (ReportSaleorderVo reportSaleorderVoValidOrg : reportSaleorderVoValidOrgList) {
                            //初始化订单所属人部门信息
                            if (reportSaleorderVo.getSaleorderId().equals(reportSaleorderVoValidOrg.getSaleorderId())){
                                reportSaleorderVo.setValidDeptId(reportSaleorderVoValidOrg.getValidDeptId());
                                reportSaleorderVo.setValidDeptName(reportSaleorderVoValidOrg.getValidDeptName());
                                reportSaleorderVo.setValidParentDeptId(reportSaleorderVoValidOrg.getValidParentDeptId());
                                reportSaleorderVo.setValidParentDeptName(reportSaleorderVoValidOrg.getValidParentDeptName());
                                reportSaleorderVoLastList.add(reportSaleorderVo);
                            }
                        }
                    }
                }
                return reportSaleorderVoLastList;
            }
            return null;
        }catch (Exception e) {
            _LOG.error("按照部门进行订单筛选异常:",e);
            return null;
        }
    }

    /**
     * 获取订单客户信息
     * @param reportSaleorderVoList
     * @return
     * @Author Cooper.xu
     * @Date 2019/05/05 11:15
     */
    public List<ReportSaleorderVo> getSaleorderCustomerInfo(List<ReportSaleorderVo> reportSaleorderVoList){
        try {
            if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                List<Integer> zxsIds = new ArrayList<>();//直辖市Id列表
                zxsIds.add(2);
                zxsIds.add(32);
                zxsIds.add(27);
                zxsIds.add(25);
                //查询客户名称以及客户注册地信息
                List<ReportSaleorderVo> reportSaleorderVoCustomerList = reportSaleorderService.getSaleorderCustomerInfo(reportSaleorderVoList);
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList){
                    for (ReportSaleorderVo reportSaleorderVoCustomer : reportSaleorderVoCustomerList){
                        if (reportSaleorderVo.getSaleorderId().equals(reportSaleorderVoCustomer.getSaleorderId())){
                            reportSaleorderVo.setCustomerName(reportSaleorderVoCustomer.getCustomerName());
                            /** 注意：这个地方的数据处理，如果组织架构变了或者所属人职位发生变化，需要实时修改，不要忘了啊。。。 **/
                            if ("max.wan".equals(reportSaleorderVo.getValidUserName())){//max.wan的订单要单独处理
                                if (zxsIds.contains(reportSaleorderVoCustomer.getCustomerValidProvinceId())){
                                    //如果客户的注册地信息是直辖市，max.wan的订单归属部门就放在直辖市组
                                    reportSaleorderVo.setValidDeptId(108);
                                    reportSaleorderVo.setValidDeptName("医疗B2B业务部直辖市组");
                                }else {//否则放在华东大区
                                    reportSaleorderVo.setValidDeptId(53);
                                    reportSaleorderVo.setValidDeptName("医疗B2B业务部华东大区");
                                }
                            }
                            //reportSaleorderVo.setCustomerValidProvinceId(reportSaleorderVoCustomer.getCustomerValidProvinceId());
                        }
                    }
                }
                //查询其他大区订单销售区域等信息
                List<ReportSaleorderVo> reportSaleorderVoSaleAreaList = reportSaleorderService.getSaleorderSaleAreaInfo(reportSaleorderVoList);
                //Integer customerCityId = 0;
                //String customerCity = "";
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList){
                    for (ReportSaleorderVo reportSaleorderVoSaleArea : reportSaleorderVoSaleAreaList){
                        if (reportSaleorderVo.getSaleorderId().equals(reportSaleorderVoSaleArea.getSaleorderId())){
                            reportSaleorderVo.setCustomerProvinceId(reportSaleorderVoSaleArea.getCustomerProvinceId());
                            reportSaleorderVo.setCustomerProvince(reportSaleorderVoSaleArea.getCustomerProvince());
                            reportSaleorderVo.setCustomerCityId(reportSaleorderVoSaleArea.getCustomerCityId());
                            reportSaleorderVo.setCustomerCity(reportSaleorderVoSaleArea.getCustomerCity());
                        }
                    }
                }
                //查询订单客户性质等信息
                List<ReportSaleorderVo> reportSaleorderVoCustomerNatureList = reportSaleorderService.getSaleorderCustomerNatureInfo(reportSaleorderVoList);
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList){
                    for (ReportSaleorderVo reportSaleorderVoCustomerNature : reportSaleorderVoCustomerNatureList){
                        if (reportSaleorderVo.getSaleorderId().equals(reportSaleorderVoCustomerNature.getSaleorderId())){
                            reportSaleorderVo.setCustomerLevelId(reportSaleorderVoCustomerNature.getCustomerLevelId());
                            reportSaleorderVo.setCustomerLevel(reportSaleorderVoCustomerNature.getCustomerLevel());
                            reportSaleorderVo.setCustomerNatureId(reportSaleorderVoCustomerNature.getCustomerNatureId());
                            reportSaleorderVo.setCustomerNature(reportSaleorderVoCustomerNature.getCustomerNature());
                        }
                    }
                }
                return reportSaleorderVoList;
            }
            return null;
        } catch (Exception e) {
            _LOG.error("获取订单客户信息异常:",e);
            return null;
        }
    }

    /**
     * 获取订单采购金额
     * @param reportSaleorderVoList
     * @return
     * @Author Cooper.xu
     * @Date 2019/05/05 11:15
     */
    public List<ReportSaleorderVo> getSaleorderBuyAmount(List<ReportSaleorderVo> reportSaleorderVoList){
        try {
            if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                //获取订单的有效采购商品金额
                List<SaleorderGoods> saleorderGoodsList = reportSaleorderService.getSaleorderBuyAmount(reportSaleorderVoList);
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList){
                    BigDecimal purchaseAmount = new BigDecimal("0.0000");
                    for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
                        if (reportSaleorderVo.getSaleorderId().equals(saleorderGoods.getSaleorderId())){
                            purchaseAmount = purchaseAmount.add(saleorderGoods.getReferenceCostPrice().multiply(new BigDecimal(String.valueOf(saleorderGoods.getNum()))).divide(new BigDecimal("10000"),4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    reportSaleorderVo.setPurchaseAmount(purchaseAmount);
                }
                return reportSaleorderVoList;
            }
            return null;
        }catch (Exception e) {
            _LOG.error("获取订单采购金额异常:",e);
            return null;
        }
    }

    /**
     * 获取订单退款金额
     * @param reportSaleorderVoList
     * @return
     * @Author Cooper.xu
     * @Date 2019/05/05 11:15
     */
    public List<ReportSaleorderVo> getSaleorderRefundAmount(List<ReportSaleorderVo> reportSaleorderVoList){
        try {
            if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                //获取订单的有效退款金额
                List<CapitalBillDetailVo> capitalBillDetailVoList = reportSaleorderService.getSaleorderRefundAmount(reportSaleorderVoList);
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList) {
                    BigDecimal refundAmount = new BigDecimal("0.00");
                    for (CapitalBillDetailVo capitalBillDetailVo : capitalBillDetailVoList) {
                        if (reportSaleorderVo.getSaleorderId().equals(capitalBillDetailVo.getSaleorderId())) {
                            refundAmount = refundAmount.add(capitalBillDetailVo.getAmount().divide(new BigDecimal("10000"),2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    reportSaleorderVo.setRefundAmount(refundAmount);
                }
                return reportSaleorderVoList;
            }
            return null;
        } catch (Exception e) {
            _LOG.error("获取订单退款金额异常:",e);
            return null;
        }
    }

    /**
     * 处理2018年02月01日及以后的订单时间
     * @param reportSaleorderVoList
     * @return
     * @Author Cooper.xu
     * @Date 2019/05/06 13:15
     */
    public List<ReportSaleorderVo> doSaleorderNearTimeList(List<ReportSaleorderVo> reportSaleorderVoList){
        try {
            if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList) {
                    reportSaleorderVo.setDataDateYear(DateUtil.convertString(reportSaleorderVo.getSatisfyDeliveryTime(),"yyyy"));
                    reportSaleorderVo.setDataDateMonth(DateUtil.convertString(reportSaleorderVo.getSatisfyDeliveryTime(),"MM"));
                    reportSaleorderVo.setDataDateYm(DateUtil.convertString(reportSaleorderVo.getSatisfyDeliveryTime(),"yyyy/MM"));
                    reportSaleorderVo.setDataDate(DateUtil.convertString(reportSaleorderVo.getSatisfyDeliveryTime(),"yyyy/MM/dd"));
                }
                return reportSaleorderVoList;
            }
            return null;
        } catch (Exception e) {
            _LOG.error("处理2018年02月01日及以后的订单时间:",e);
            return null;
        }
    }

    /**
     * 处理2018年02月01日以前的订单时间
     * @param reportSaleorderVoList
     * @return
     * @Author Cooper.xu
     * @Date 2019/05/06 13:15
     */
    public List<ReportSaleorderVo> doSaleorderAgoTimeList(List<ReportSaleorderVo> reportSaleorderVoList){
        try {
            if (!CollectionUtils.isEmpty(reportSaleorderVoList)){
                //查询订单的付款时间
                List<ReportSaleorderVo> reportSaleorderVoFirstPayTimeList = reportSaleorderService.getSaleorderFirstPayTimeList(reportSaleorderVoList);
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList) {
                    List<Long> traderTimeList = new ArrayList<>();
                    for (ReportSaleorderVo reportSaleorderVoFirstPayTime : reportSaleorderVoFirstPayTimeList) {
                        //traderTime = reportSaleorderVoFirstPayTime.getTraderTime();
                        if (reportSaleorderVo.getSaleorderId().equals(reportSaleorderVoFirstPayTime.getSaleorderId())) {
                            traderTimeList.add(reportSaleorderVoFirstPayTime.getTraderTime());
                        }
                    }
                    Long minTraderTime = 0L;
                    //找到订单的首次付款时间
                    if (traderTimeList.size() > 0){
                        minTraderTime = traderTimeList.get(0);
                        for (Long traderTime : traderTimeList) {
                            if (traderTime < minTraderTime) {
                                minTraderTime = traderTime;
                            }
                        }
                    }
                    reportSaleorderVo.setTraderTime(minTraderTime);
                }
                //根据订单是否属于账期订单，来处理订单的时间,账期订单用生效时间，非账期订单取首次付款时间
                Long orderTime = 0L;
                for (ReportSaleorderVo reportSaleorderVo : reportSaleorderVoList) {
                    if ("0".equals(reportSaleorderVo.getIsAccountPeriod())) {
                        orderTime = reportSaleorderVo.getTraderTime();
                    }else {
                        orderTime = reportSaleorderVo.getValidTime();
                    }
                    reportSaleorderVo.setDataDateYear(DateUtil.convertString(orderTime,"yyyy"));
                    reportSaleorderVo.setDataDateMonth(DateUtil.convertString(orderTime,"MM"));
                    reportSaleorderVo.setDataDateYm(DateUtil.convertString(orderTime,"yyyy/MM"));
                    reportSaleorderVo.setDataDate(DateUtil.convertString(orderTime,"yyyy/MM/dd"));
                }
                return reportSaleorderVoList;
            }
            return null;
        } catch (Exception e) {
            _LOG.error("处理2018年02月01日以前的订单时间异常:",e);
            return null;
        }
    }
}
