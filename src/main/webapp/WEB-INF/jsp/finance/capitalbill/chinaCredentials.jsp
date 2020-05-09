<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="电子回执单" scope="application" />
<%@ include file="../../common/common.jsp"%>
   <div class="boc-returning-ticket">
        <div class="boc-rt-box">
            <div class="boc-rt-container">
                <div class="boc-rt-title"><img src="<%= basePath %>static/images/zgyh.jpg"><span>国内支付业务付款回单</span></div>
                <div class="boc-rt-content">
                    <div class="boc-rt-bor">
                        <!-- <div class="boc-bank-title">南京银行网上银行电子回单(借记)</div> -->
                        <div class="boc-bank-detail">
                            <ul>
                                <li class="boc-customer-num"><b style="letter-spacing: 1px;margin-right:10px;">客户号:</b><b>473963367</b></li>
                                <li class="boc-date"><b style="letter-spacing: 1px;margin-right:10px;">日期:</b><b>${bankBill.trandate}</b></li>
                                <li><b>付款人账号：</b><span>479370036226</span></li>
                                <li><b>收款人账号：</b><span>${bankBill.accno2}</span></li>
                                <li class="boc-payer"><b>付款人名称：</b><span>南京贝登医疗股份有限公司</span></li>
                                <li class="boc-payee"><b>收款人名称：</b><span>${bankBill.accName1}</span></li>
                                <li class="boc-payer"><b>付款人开户行：</b><span>中国银行南京新城科技园支行</span></li>
                                <li class="boc-payee"><b>收款人开户行：</b><span>${bankBill.cadbankNm}</span></li>
                                <li class="boc-money"><b>金额：</b><b class="wid20" style="display: inline-block;">CNY${bankBill.amt}<br/>人民币${bankBill.amtChinese}</b></li>
                            </ul>
                        </div>
                        <div class="boc-bank-detail-1">
                             <ul>
                                 <li class="wid17"><span>业务种类：</span><span>${businessTypes}</span></li>
                                 <li><span>业务编号：</span><span>000000000000</span></li>
                                 <li><span>凭证号码：</span><span></span></li>
                             </ul>
                             <div class="boc-usage"><span>用途：</span><span>${bankBill.det}</span></div>
                             
                             <div class="boc-usage"><span>备注：</span><span>${remark}</span></div>
                             <div class="boc-usage boc-usage-fuyan"><span>附言：</span><span>${ps}</span></div>
                             <div class="boc-attention" style="font-size: 0.7rem;">如您已通过银行网点取得相应纸质回单，请注意核对，勿重复记账！</div>
                             <div class="boc-deal-detail">
                                 <ul style="margin-bottom: -3px;">
                                     <li><span>交易机构：</span><span>26237</span></li>
                                     <li class="boc-deal-channel"><span>交易渠道：</span><span>${bankBill.creTyp}</span></li>
                                     <li class="boc-deal-flowing"><span>交易流水号：</span><span>26237</span></li>
                                     <li class="boc-dealer"> <span>经办：</span><img src="<%= basePath %>static/images/yhz.jpg"/></li>
                                     <div class="clear"></div>
                                 </ul>
                             </div>
                             <div class="boc-rtlist-detail" style="margin-top:4px;">
                                 <ul>
                                     <li><span>回单编号：</span><span>2018050357012792</span></li>
                                     <li class="boc-rt-valide"><span>回单验证码：</span><span>242L3LD72ESS</span></li>
                                     <li><span>打印时间：</span><span></span></li>
                                     <li><span>打印次数：</span><span class="mr5"></span><span>次</span></li>
                                 </ul>
                             </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
   </div>
<%@ include file="../../common/footer.jsp"%>