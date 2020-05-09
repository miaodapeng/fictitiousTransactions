<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="中国银行流水列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content">
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>finance/bankbill/chinaIndex.do">
			<ul>
				<li>
					<label class="infor_name">对方名称</label>
					<input type="text" class="input-middle" placeholder="" name="accName1"  value="${bankBill.accName1}"/>
				</li>
				<li>
					<label class="infor_name">对方开户机构</label>
					<input type="text" class="input-middle" placeholder="" name="cadbankNm"  value="${bankBill.cadbankNm}"/>
				</li>
				<li>
					<label class="infor_name">对方账号</label>
					<input type="text" class="input-middle" placeholder="" name="accno2"  value="${bankBill.accno2}"/>
				</li>
				<li>
					<label class="infor_name">摘要</label>
					<input type="text" class="input-middle" placeholder="" name="message"  value="${bankBill.message}"/>
				</li>
				<li>
					<label class="infor_name">流水号</label>
					<input type="text" class="input-middle" placeholder="" name="tranFlow"  value="${bankBill.tranFlow}"/>
				</li>
				<li>
					<label class="infor_name">匹配项目</label>
					<select class="input-middle f_left" name="matchedObject" id="matchedObject">
							<option value="-1">全部</option>
							<c:forEach var="list" items="${macthObjectList}">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${bankBill.matchedObject == list.sysOptionDefinitionId}">selected="selected"</c:if>>${list.title}</option>
							</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">银行名称</label>
					<input type="text" class="input-middle" placeholder="" name=""  value="${buyorderVo.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">金蝶凭证号</label>
					<input type="text" class="input-middle" placeholder="" name="financeVoucherNo"  value="${bankBill.financeVoucherNo}"/>
				</li>
				
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" placeholder="" name="buyorderNo"  value="${bankBill.buyorderNo}"/>
				</li> 
				
				<li>
					<label class="infor_name">发送结果</label> 
					<select class="input-middle f_left" name="sendResult">
						<option value="-1">全部</option>
						<option value="1" <c:if test="${bankBill.sendResult == 1}">selected="selected"</c:if>>是</option>
						<option value="2" <c:if test="${bankBill.sendResult == 2}">selected="selected"</c:if>>否</option> 
					</select>
				</li>

				<li>
                    <label class="infor_name">交易时间</label>
                    <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="beginTime" value="${beginTime}">
					<div class="gang">-</div> 
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="endTime" value="${endTime}">
                </li>
			</ul>
			<div class="tcenter">
				<input type="hidden" name="nowDate" value="${nowDate}">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="searchReset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportBankBillList(3)">导出列表</span>
				<span class="bg-light-blue bt-bg-style bt-small" onclick="sendChPayBillToKindlee(this)">发送至金蝶</span>
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
			<c:set var="page_get_amount" value="${page_get_amount+(bankBilllist.flag1!=0?bankBilllist.amt:0)}"></c:set>
			<c:set var="page_pay_amount" value="${page_pay_amount+(bankBilllist.flag1==0?bankBilllist.amt:0)}"></c:set>
						
            <table class="table table-style7">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th>流水号</th>
                        <th>对方名称</th>
                        <th>对方开户机构</th>
                        <th class="wid10">对方账号</th>
                        <th>交易时间</th>
                        <th class="wid4">摘要</th>
                        <th class="wid8">收款金额</th>
                        <th class="wid8">付款金额</th>
                        <th class="wid8">银行名称</th>
                        <th class="wid12">银行余额</th>
                        <th class="wid4">备注</th>
                        <th class="wid12">剩余结款金额</th>
                        <th>匹配项目</th>
                        <th class="wid10">金蝶凭证号</th>
                        <th class="wid8">发送结果</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${num.count}</td>
                        <td>${bankBilllist.tranFlow}</td>
                        <td>${bankBilllist.accName1}</td>
                        <td>${bankBilllist.cadbankNm}</td>
                        <td>${bankBilllist.accno2}</td>
                        <td>${bankBilllist.realTrandatetime}</td>
                        <td>${fn:substring(bankBilllist.message, fn:indexOf(bankBilllist.message, "//A:")+4, fn:indexOf(bankBilllist.message, "//U:"))}</td>
                        <td><c:if test="${bankBilllist.flag1 != 0}">${bankBilllist.amt}</c:if></td>
                        <td><c:if test="${bankBilllist.flag1 == 0}">${bankBilllist.amt}</c:if></td>
                        <td></td>
                        <td>${bankBilllist.amt1}</td>
                        <td>${bankBilllist.det}</td>
                        <td>${bankBilllist.amt-bankBilllist.matchedAmount}</td>
                        <td>${bankBilllist.matchedObjectName}</td>
                        <td>${bankBilllist.financeVoucherNo}</td>
                        <c:choose>
                        	<c:when test="${bankBilllist.financeVoucherNoId != null and bankBilllist.financeVoucherNoId != ''}">
                        		<td>是</td>
                        	</c:when>
                        	<c:otherwise>
                        		<td>否</td>
                        	</c:otherwise>
                        </c:choose>
                    </tr>
                    <c:if test="${bankBilllist.capitalBillDetailList != null && bankBilllist.capitalBillDetailList.size()>0}">
                    <tr>
                        <td class="table-container" colspan="16">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="wid10">订单号</th>
                                        <th class="wid20">供应商名称</th>
                                        <th class="wid10">申请金额</th>
                                        <th class="wid10">创建时间</th>
                                        <th class="wid10">生效时间</th>
                                        <th>收款名称</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:set var="page_order_num" value="${page_order_num+bankBilllist.capitalBillDetailList.size()}"></c:set>
                                <c:forEach var="capitalBillDetail" items="${bankBilllist.capitalBillDetailList}">
                                	<c:if test="${capitalBillDetail.orderType == 1}">
	                                	<c:set var="page_order_amount" value="${page_order_amount+capitalBillDetail.saleorder.totalAmount}"></c:set>
										<c:set var="page_match_amount" value="${page_match_amount+capitalBillDetail.amount}"></c:set>
	                                    <tr>
	                                        <td>
	                                        	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${capitalBillDetail.saleorder.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${capitalBillDetail.saleorder.saleorderId}","title":"订单信息"}'> ${capitalBillDetail.saleorder.saleorderNo}</a>
	                                        </td>
	                                        
	                                        <td>${capitalBillDetail.saleorder.traderName}</td>
	                                        <td>${capitalBillDetail.amount}</td>
	                                        <td><date:date value ="${capitalBillDetail.saleorder.addTime}" format="yyyy-MM-dd"/></td>
	                                        <td><date:date value ="${capitalBillDetail.saleorder.validTime}" format="yyyy-MM-dd"/></td>
	                                        <td>${capitalBillDetail.payee}</td>
	                                    </tr>
                                	</c:if>
                                	<c:if test="${capitalBillDetail.orderType == 2}">
                                		<c:set var="page_order_amount" value="${page_order_amount+capitalBillDetail.buyorder.totalAmount}"></c:set>
										<c:set var="page_match_amount" value="${page_match_amount+capitalBillDetail.amount}"></c:set>
	                                    <tr>
	                                        <td>
	                                        	<a class="addtitle" href="javascript:void(0);"  tabTitle='{"num":"viewfinancebuyorder${capitalBillDetail.buyorder.buyorderId}", "link":"./finance/buyorder/viewBuyorder.do?buyorderId=${capitalBillDetail.buyorder.buyorderId}","title":"订单信息"}'>${capitalBillDetail.buyorder.buyorderNo}</a>
	                                        </td>
	                                        <td>${capitalBillDetail.buyorder.traderName}</td>
	                                        <td>${capitalBillDetail.amount}</td>
	                                        <td><date:date value ="${capitalBillDetail.buyorder.addTime}" format="yyyy-MM-dd"/></td>
	                                        <td><date:date value ="${capitalBillDetail.buyorder.validTime}" format="yyyy-MM-dd"/></td>
	                                        <td>${capitalBillDetail.payee}</td>
	                                    </tr>
                                	</c:if>
                                	<c:if test="${capitalBillDetail.orderType == 3}">
										<c:set var="page_match_amount" value="${page_match_amount+capitalBillDetail.amount}"></c:set>
	                                    <tr>
	                                        <td>
	                                        	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewaftersales${capitalBillDetail.afterSales.afterSalesId}","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${capitalBillDetail.afterSales.afterSalesId}&traderType=1","title":"订单信息"}'> ${capitalBillDetail.afterSales.afterSalesNo}</a>
	                                        </td>
	                                        <td></td>
	                                        <td>${capitalBillDetail.amount}</td>
	                                        <td><date:date value ="${capitalBillDetail.afterSales.addTime}" format="yyyy-MM-dd"/></td>
	                                        <td><date:date value ="${capitalBillDetail.afterSales.validTime}" format="yyyy-MM-dd"/></td>
	                                        <td>${capitalBillDetail.payee}</td>
	                                    </tr>
                                	</c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    </c:if>
                </tbody>
            </table>
            </c:forEach>
            <div class="tablelastline">
            	【全部结果 条目：${page.totalRecord} 收款总额：${getAmount} 付款总额：${payAmount} 订单数:${orderNum} 订单总额:${orderAmount}本次到款总额:${matchAmount}】【本页统计 条目：${list.size()} 收款总额：${page_get_amount} 付款总额：${page_pay_amount} 订单数:${page_order_num} 订单总额:${page_order_amount} 本次到款总额:${page_match_amount}】
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
           <tags:page page="${page}" />
        </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/finance/bankBill/index.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
