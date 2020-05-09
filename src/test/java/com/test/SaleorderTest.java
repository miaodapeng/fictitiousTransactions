package com.test;

import com.vedeng.authorization.model.User;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.CapitalBillDetail;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class SaleorderTest {
    @Autowired
    private SaleorderService saleorderService;
    @Autowired
    private BuyorderService buyorderService;
    @Autowired
    private CapitalBillService capitalBillService;
    @Autowired
    private UserService userService;
    @Test
    public void testSaleorderPrepaid(){
        Saleorder saleorder=saleorderService.getsaleorderbySaleorderId(103286);
        if(saleorder.getPrepaidAmount().compareTo(BigDecimal.ZERO) == 0){
            System.out.println(123);
        }
    }

    @Test
    public void  readSaleorder(){
        Saleorder saleorder=null;
        InputStream is = null;
        ObjectInputStream ois = null;
        File f = new File("D:/123.out");
        try {
            is = new FileInputStream(f);
            ois = new ObjectInputStream(is);
            saleorder = (Saleorder)ois.readObject();
            System.out.println(saleorder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testBuyorder(){
        BuyorderVo order =null;
        InputStream is = null;
        ObjectInputStream ois = null;
        File f = new File("D:/246.out");
        try {
            is = new FileInputStream(f);
            ois = new ObjectInputStream(is);
            order = (BuyorderVo) ois.readObject();
            solveBuyOrder(order);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void solveBuyOrder(BuyorderVo buyorderInfo){
        User user=new User();
        user.setCompanyId(1);
        user.setUserId(1);
        user.setUsername("njadmin");
        if(buyorderInfo.getPrepaidAmount().compareTo(BigDecimal.ZERO) == 0){
            BuyorderVo buyorder = new BuyorderVo();
            buyorder.setBuyorderId(buyorderInfo.getBuyorderId());
            if(buyorderInfo.getRetainageAmount().compareTo(BigDecimal.ZERO) == 0){
                //如果尾款等于0.付款状态为全部付款
                buyorder.setPaymentStatus(2);
            }else{
                //如果尾款不等于0.付款状态为部分付款
                buyorder.setPaymentStatus(1);
            }
            buyorder.setPaymentTime(DateUtil.sysTimeMillis());
            buyorder.setSatisfyDeliveryTime(DateUtil.sysTimeMillis());
            //添加啊流水
            // 归属销售
            User belongUser = new User();
            if(buyorderInfo.getTraderId() != null ){
                belongUser = userService.getUserByTraderId(buyorderInfo.getTraderId(), 2);// 1客户，2供应商
                if(belongUser != null && belongUser.getUserId() != null){
                    belongUser = userService.getUserById(belongUser.getUserId());
                }
            }
            CapitalBill capitalBill = new CapitalBill();
            capitalBill.setCompanyId(user.getCompanyId());
            //信用支付
            capitalBill.setTraderMode(527);
            capitalBill.setCurrencyUnitId(1);
            capitalBill.setTraderTime(DateUtil.sysTimeMillis());
            //交易类型 转移
            capitalBill.setTraderType(3);
            capitalBill.setPayee(buyorderInfo.getTraderName());
            capitalBill.setPayer(user.getCompanyName());

            List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
            CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
            //订单类型   采购订单
            capitalBillDetail.setOrderType(2);
            capitalBillDetail.setOrderNo(buyorderInfo.getBuyorderNo());
            capitalBillDetail.setRelatedId(buyorderInfo.getBuyorderId());
            //所属类型  经销商（包含终端）
            capitalBillDetail.setTraderType(2);
            capitalBillDetail.setTraderId(buyorderInfo.getTraderId());
            capitalBillDetail.setUserId(buyorderInfo.getUserId());
            //业务类型  订单收款
            capitalBillDetail.setBussinessType(525);
            capitalBillDetail.setAmount(buyorderInfo.getAccountPeriodAmount());
            if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
                capitalBillDetail.setOrgName(belongUser.getOrgName());
                capitalBillDetail.setOrgId(belongUser.getOrgId());
            }
            capitalBillDetails.add(capitalBillDetail);

            capitalBill.setCapitalBillDetails(capitalBillDetails);
            CapitalBillDetail capitalBillDetailInfo = new CapitalBillDetail();
            capitalBillDetailInfo.setOrderType(2);
            capitalBillDetailInfo.setOrderNo(buyorderInfo.getBuyorderNo());
            capitalBillDetailInfo.setRelatedId(buyorderInfo.getBuyorderId());
            capitalBillDetailInfo.setTraderType(2);
            capitalBillDetailInfo.setTraderId(buyorderInfo.getTraderId());
            capitalBillDetailInfo.setUserId(buyorderInfo.getUserId());
            //业务类型  订单收款
            capitalBillDetailInfo.setBussinessType(525);
            capitalBillDetailInfo.setAmount(buyorderInfo.getAccountPeriodAmount());
            if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
                capitalBillDetailInfo.setOrgName(belongUser.getOrgName());
                capitalBillDetailInfo.setOrgId(belongUser.getOrgId());
            }
            capitalBill.setCapitalBillDetail(capitalBillDetailInfo);
            //添加当前登陆人
            capitalBill.setCreator(user.getUserId());
            capitalBillService.saveCapitalBill(capitalBill);
            buyorder.setStatus(1);
            buyorderService.saveBuyorder(buyorder);
        }else{
            BuyorderVo buyorder = new BuyorderVo();
            buyorder.setBuyorderId(buyorderInfo.getBuyorderId());
            buyorder.setStatus(1);
            buyorderService.saveBuyorder(buyorder);
        }
    }
}
