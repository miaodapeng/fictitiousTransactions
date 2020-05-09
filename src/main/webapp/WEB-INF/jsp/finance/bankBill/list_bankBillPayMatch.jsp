<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单付款列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>finance/bankbill/bankBillPayMatchList.do">
			<ul>
				<li>
                    <label class="infor_name">交易时间</label>
                    <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" id="beginTime" name="beginTime" value="${beginTime}" onchange="noEmpty(this,'${beginTime}')">
					<div class="gang">-</div> 
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" id="endTime" name="endTime" value="${endTime}" onchange="noEmpty(this,'${beginTime}')">
                </li>
                
                <li>
					<label class="infor_name">银行</label> 
					<select class="input-middle f_left" name="bankTag">
						<option value="2" <c:if test="${bankBill.bankTag == 2}">selected="selected"</c:if>>南京银行</option>
						<option value="3" <c:if test="${bankBill.bankTag == 3}">selected="selected"</c:if>>中国银行</option> 
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<input type="hidden" name="nowDate" value="${nowDate}">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="searchReset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" >导出列表</span>
			</div>
		</form>
	</div>
<div class="parts">
			<c:set var="page_get_amount" value="0"></c:set>
			<c:set var="page_pay_amount" value="0"></c:set>
			<c:set var="page_order_num" value="0"></c:set>
			<c:set var="page_order_amount" value="0"></c:set>
			<c:set var="page_match_amount" value="0"></c:set>
			
		<c:forEach var="bankBilllist" items="${list}" varStatus="num">
			<c:set var="page_get_amount" value="${page_get_amount+bankBilllist.flag1!=0?bankBilllist.amt:0}"></c:set>
			<c:set var="page_pay_amount" value="${page_pay_amount+bankBilllist.flag1==0?bankBilllist.amt:0}"></c:set>
            <table class="table table-style7">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th class="wid15">交易时间</th>
                        <th >对方名称</th>
                        <th >收款金额</th>
                        <th >对方账号</th>
                        <th class="wid8">摘要</th>
                        <th class="wid8">备注</th>
                        
                        <th >付款金额</th>
                        <th class="wid12">剩余付款金额</th>
                        <th class="wid15">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${num.count}</td>
                        <td>${fn:substring(bankBilllist.realTrandatetime,0,19)}</td>
                        <td>${bankBilllist.accName1}</td>
                        <td><c:if test="${bankBilllist.flag1 != 0}">${bankBilllist.amt}</c:if></td>
                        <td>${bankBilllist.accno2}</td>
                        <td>${bankTag == 2 ? bankBilllist.message : fn:substring(bankBilllist.message, fn:indexOf(bankBilllist.message, "//A:")+4, fn:indexOf(bankBilllist.message, "//U:"))}</td>
                        <td>${bankBilllist.det}</td>
                        <td><c:if test="${bankBilllist.flag1 == 0}">${bankBilllist.amt}</c:if></td>
                        <td>${bankBilllist.amt-bankBilllist.matchedAmount}</td>
                        <td>
                        	<span class="bt-smaller bt-border-style border-blue" onclick="manualMatchPayInfo(this,${bankBilllist.bankBillId});">手动匹配</span>
                            <span class="bt-smaller bt-border-style border-red pop-new-data"  layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./addIgnore.do?bankBillId=${bankBilllist.bankBillId}&type=2"}'>忽略</span>
                        </td>
                    </tr>
                    <c:if test="${bankBilllist.capitalBillDetailList != null}">
                    <tr>
                        <td class="table-container" colspan="10">
                            <table class="table">
                                <thead>
                                    <tr>
                                    	<th class="wid4">选择</th>
                                    	<th class="wid4">序号</th>
                                        <th class="wid9">订单号</th>
                                        <th>供应商名称</th>
                                        <th>收款名称</th>
                                        <th class="wid15">交易主体</th>
                                        <th class="wid15">交易方式</th>
                                        <th class="wid20">申请金额</th>
                                        <th class="wid14">申请时间</th>
                                        <th class="wid6">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
								<c:set var="remain_cbc_log" value="${bankBilllist.amt-bankBilllist.matchedAmount}"></c:set>
                                <c:set var="page_order_num" value="${page_order_num+bankBilllist.capitalBillDetailList.size()}"></c:set>
                                <c:forEach var="capitalBillDetail" items="${bankBilllist.capitalBillDetailList}" varStatus="cnum">
                                	<c:set var="page_order_amount" value="${page_order_amount+capitalBillDetail.saleorder.totalAmount}"></c:set>
									<c:set var="page_match_amount" value="${page_match_amount+capitalBillDetail.amount}"></c:set>
									<c:set var="remain_saleorder" value="${capitalBillDetail.saleorder.totalAmount-capitalBillDetail.saleorder.receivedAmount}"></c:set>
                                    <tr class="check_box_parent">
                                    	<td>
	                                    	<input class="cid_input check_box_checked" type="checkbox" name="checkOne" value="${bankBilllist.bankBillId}" payapplyid="${capitalBillDetail.payApply.payApplyId}" 
	                                    		tranflow="${bankBilllist.tranFlow}" taskinfopayid="${capitalBillDetail.payApply.taskInfoPayId}" banktag="${bankBilllist.bankTag}" 
	                                    		paytype="${capitalBillDetail.payApply.payType}" autocomplete="off">
                                    	</td>
                                    	<td>${cnum.count}</td>
                                        <td>
                                        	<c:if test="${capitalBillDetail.payApply.payType == 517}">
	                        				<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./finance/buyorder/viewBuyorder.do?buyorderId=${capitalBillDetail.payApply.relatedId}","title":"订单信息"}'>${capitalBillDetail.payApply.orderNo}</a>
					                        </c:if>
					                        <c:if test="${capitalBillDetail.payApply.payType == 518}">
					                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"view_invoice_after<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",	"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${capitalBillDetail.payApply.relatedId}","title":"售后详情"}'>${capitalBillDetail.payApply.orderNo}</a>
					                        </c:if>
                                        </td>
                                        <td>
                                        <span class="font-blue addtitle" 
								tabTitle='{"num":"viewsupplier<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/supplier/baseinfo.do?traderId=${capitalBillDetail.payApply.traderId}","title":"供应商信息"}'>${capitalBillDetail.payApply.buyorderTraderName}</span>
                                        
                                        </td>
                                        <td>${capitalBillDetail.payApply.traderName}</td>
                                        <td>
				                        	<c:if test="${capitalBillDetail.payApply.traderSubject eq 1}">对公</c:if>
											<c:if test="${capitalBillDetail.payApply.traderSubject eq 2}">对私</c:if>
											<input type="hidden" value="${capitalBillDetail.payApply.traderSubject}" name="traderSubject" />
				                        </td>
				                        <td>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 520}">支付宝</c:if>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 521}">银行</c:if>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 522}">微信</c:if>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 523}">现金</c:if>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 527}">信用支付</c:if>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 528}">余额支付</c:if>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 529}">退还信用</c:if>
											<c:if test="${capitalBillDetail.payApply.traderMode eq 530}">退还余额</c:if>
										</td>
										<td>${capitalBillDetail.payApply.amount}</td>
                                        <td><date:date value ="${capitalBillDetail.payApply.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td><span class="bt-smaller bt-border-style border-blue confirm_button" onclick="matchBankBill(${bankBilllist.bankBillId},${capitalBillDetail.payApply.payApplyId},'${bankBilllist.tranFlow}',${capitalBillDetail.payApply.taskInfoPayId},${bankBilllist.bankTag},${capitalBillDetail.payApply.payType})">确认</span></td>
                                        <c:if test="${(remain_cbc_log-remain_saleorder)>0}">
                                        	<c:set var="remain_cbc_log" value="${remain_cbc_log-remain_saleorder}"></c:set>
                                        </c:if>
                                        <c:if test="${(remain_cbc_log-remain_saleorder)<=0}">
                                        	<c:set var="remain_cbc_log" value="0"></c:set>
                                        </c:if>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    </c:if>
                </tbody>
            </table>
            </c:forEach>
            <div class="tablelastline" style="margin-bottom:10px;">
            	【全部结果 条目：${page.totalRecord} 收款总额：<span class="font-green">${getAmount}</span> 付款总额：<span class="font-red">${payAmount}</span> 订单数:${orderNum} 订单总额:${orderAmount}本次到款总额:${matchAmount}】【本页统计 条目：${list.size()} 收款总额：<span class="font-green">${page_get_amount}</span> 付款总额：<span class="font-red">${page_pay_amount}</span> 订单数:${page_order_num} 订单总额:${page_order_amount} 本次到款总额:${page_match_amount}
            </div>
              <c:if test="${empty list}">
				<!-- 查询无结果弹出 -->
				 <table class="table">
				 	<tbody>
						<tr>
                        	<td colspan="10">查询无结果！请尝试使用其他搜索条件。</td>
                    	</tr>
                     </tbody>
           		</table>
			  </c:if>
			  
			<div class="inputfloat f_left" style='margin:0px 0 15px 0;'>
				<input type="checkbox" class="mt6 mr4 check_box_all" name="checkAll" autocomplete="off"> <label class="mr10 mt4">全选</label>
				<span class="bt-bg-style bg-light-blue bt-small mr10" onclick="numbersConfirm();">批量确认</span> 
			</div>
			  
           <tags:page page="${page}" />
        </div>
        <div style="display:none;">
				<!-- 弹框 -->
			<div class="title-click nobor  pop-new-data" id="terminalDiv"></div>
		</div>
		<input type="hidden" name="formToken" value="${formToken}"/>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/finance/bankBill/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/finance/bankBill/bankBillMatch.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
