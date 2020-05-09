<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="电子回执单" scope="application" />
<%@ include file="../../common/common.jsp"%>
 <div class="electric-returning-ticket">
        <div class="electric-rt-box">
            <div class="electric-rt-container">
                <div class="electric-rt-title"><i class="iconzhuyi"></i>电子回单详细</div>
                <div class="electric-rt-content">
                    <div class="electric-rt-bor">
                        <div class="nj-bank-title">南京银行网上银行电子回单(借记)</div>
                        <div class="nj-bank-detail">
                            <ul>
                                <li>
                                    <div class="f_left">
                                        <label style="margin: 0;">电子回单号：</label>
                                        <span style="height: 25px;"><b></b></span>
                                    </div>
                                    <div class="f_right">
                                        <label style="text-align: right;width:80px;margin: 0;">交易流水号：</label>
                                        <span style="width: 85px;text-align:right;height: 25px;"><b>${fn:split(bankBill.tranFlow,"-")[0]}</b></span>
                                    </div>
                                </li>
                                <li>
                                    <div class="f_left">
                                        <div class="customer-account f_left">
                                          	  客户账号信息
                                        </div>
                                        <div class="customer-account-infor f_left">
                                                <div  class="">
                                                    <label >户名：</label>
                                                    <span >南京贝登医疗股份有限公司</span>
                                                 </div>
                                                 <div  class="">
                                                    <label>账号：</label>
                                                    <span>01280120210024916</span>
                                                </div>
                                        </div>
                                    </div>
                                    <div class="f_right">
                                        <div class="customer-account f_left">
                                           	 对方账号信息
                                        </div>
                                        <div class="customer-account-infor f_left">
                                                <div class="">
                                                    <label>户名：</label>
                                                    <span style="max-width: 150px; display: inline-block;">${bankBill.accName1}</span>
                                                 </div>
                                                 <div class="">
                                                    <label>账号：</label>
                                                    <span>${bankBill.accno2}</span>
                                                </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                     <div class="customer-account-infor2 f_left">
                                                <div  class="">
                                                    <label>交易金额：</label>
                                                    <span class="font-red">${bankBill.amt}</span>
                                                 </div>
                                                 <div  class="">
                                                    <label>交易日期：</label>
                                                    <span>${bankBill.trandate}</span>
                                                </div>
                                                <div  class="">
                                                    <label>交易代码：</label>
                                                    <span>1244</span>
                                                </div>
                                                <div  class="">
                                                    <label>摘要内容：</label>
                                                    <span>${bankBill.message}</span>
                                                </div>
                                    </div>
                                    <div class="customer-account-infor2 f_right">
                                                <div  class="">
                                                    <label>金额(大写)：</label>
                                                    <span>${bankBill.amtChinese}</span>
                                                 </div>
                                                 <div  class="">
                                                    <label>时间戳：</label>
                                                    <span>${bankBill.trantime}000</span>
                                                </div>
                                                <div  class="">
                                                    <label>备注信息：</label>
                                                    <span>${bankBill.det}</span>
                                                </div>
                                                <div  class="">
                                                    <label>电子回单验证码：</label>
                                                    <span></span>
                                                </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="nj-bank-tip">
                            注意：此回单不作为入账依据及发票凭证。多张回单，验证码一致系重复打印。
                        </div>
                    </div>
                </div>
            </div>
        </div>
   </div>
<%@ include file="../../common/footer.jsp"%>