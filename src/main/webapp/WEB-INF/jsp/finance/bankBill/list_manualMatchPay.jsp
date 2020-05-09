<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="终端列表" scope="application" />
<%@ include file="../../common/common.jsp"%>

	<div class="main-container">
		<div id="selectTerminalInfo">
            <table class="table" style="margin-bottom:0px;">
                <thead>
                    <tr>
                    	<th>交易银行名称</th>
                        <th>对方名称</th>
                        <th class="wid16">对方账号称</th>
                        <th>摘要</th>
                        <th>备注</th>
                        <th>收款金额</th>
                    	<th>付款金额</th>
                    	<th>剩余结款金额</th>
                    </tr>
                </thead>
                <tbody class="goodsOpt">
           			 <tr>
           			  <td>
           			  	<c:if test="${bankBill.bankTag==2}">
           			  		南京银行
           			  	</c:if>
           			  	<c:if test="${bankBill.bankTag==3}">
           			  		中国银行
           			  	</c:if>
           			  </td>
                      <td>${bankBill.accName1}</td>
                      <td>${bankBill.accno2}</td>
                      <td>${bankBill.message}</td>
                      <td>${bankBill.det}</td>
                      <td><c:if test="${bankBill.flag1 != 0}">${bankBill.amt}</c:if></td>
                      <td><c:if test="${bankBill.flag1 == 0}">${bankBill.amt}</c:if></td>
                      <td>${bankBill.amt-bankBill.matchedAmount}</td>
                  </tr>
                </tbody>
            </table>
    </div>
    
    	<div class="searchfunc"    style="margin-bottom: -8px;">	
			<form method="post" id="search" action="./getManualMatchPayInfo.do">
				<ul>
            		<li>
            			<div class="infor_name">
                          <span>*</span>
                          <label>请输入关键词</label>
                      	</div>
						<div class='f_left inputfloat'>
						<div>
						<input type="hidden" name="bankBillId"  value="${bankBill.bankBillId}">
						<input type="text" class="input-larger" name="search"  value="${search}" style="margin-right:10px;">
						<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchError">搜索</span>
						</div>
						<div id="traderNameWarn"></div>
						</div>
            		</li>
           		</ul>
			</form>
		</div>
        <div id="selectTerminal">
        	<c:if test="${not empty payApplyList}">
            <table class="table table-bordered table-striped table-condensed table-centered" id="tabeleInfo" >
                <thead>
                    <tr>
                        <th width="5%">选择</th>
                        <th width="5%">序号</th>
                        <th>订单号</th>
                        <th>供应商名称</th>
                        <th>收款名称</th>
                        <th>交易主体</th>
                        <th>交易方式</th>
                    	<th>申请时间</th>
                    	<th>申请金额</th>
                    </tr>
                </thead>
                <tbody class="goodsOpt">
		       		<c:forEach items="${payApplyList}" var="list" varStatus="status">
	                    <tr>
	                        <td><input type="radio" name="checked" value="${list.payApplyId}"/></td>
	                        <td>${status.count}</td>
	                        <td>
	                        				<c:if test="${list.payType == 517}">
	                        				<a class="addtitle1" href="javascript:void(0);" tabTitle='{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./finance/buyorder/viewBuyorder.do?buyorderId=${list.relatedId}","title":"订单信息"}'>${list.buyorderNo}</a>
					                        </c:if>
					                        <c:if test="${list.payType == 518}">
					                        <a class="addtitle1" href="javascript:void(0);" tabTitle='{"num":"view_invoice_after<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",	"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedId}","title":"售后详情"}'>${list.afterSalesNo}</a>
					                        </c:if>
	                        
	                        </td>
	                        <td>
	                        	<span class="font-blue addtitle1" 
								tabTitle='{"num":"viewsupplier<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}","title":"供应商信息"}'>${list.buyorderTraderName}</span>
	                        </td>
	                        <td>${list.traderName}</td>
	                        <td>
	                        <c:if test="${list.traderSubject eq 1}">对公</c:if>
							<c:if test="${list.traderSubject eq 2}">对私</c:if>
							</td>
	                        <td>
	                        	<c:if test="${list.traderMode eq 520}">支付宝</c:if>
								<c:if test="${list.traderMode eq 521}">银行</c:if>
								<c:if test="${list.traderMode eq 522}">微信</c:if>
								<c:if test="${list.traderMode eq 523}">现金</c:if>
								<c:if test="${list.traderMode eq 527}">信用支付</c:if>
								<c:if test="${list.traderMode eq 528}">余额支付</c:if>
								<c:if test="${list.traderMode eq 529}">退还信用</c:if>
								<c:if test="${list.traderMode eq 530}">退还余额</c:if>
							</td>
	                        <td><date:date value ="${list.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
	                        <td>${list.amount}
	                        	<input type="hidden" name="amount" value="${list.amount}">
	                        	<input type="hidden" name="payApplyId" value="${list.payApplyId}">
	                            <input type="hidden" name="taskId" value="${list.taskInfoPayId}">
	                            <input type="hidden" name="payType" value="${list.payType}">	
	                        </td>
	                    </tr>
                	</c:forEach>
                </tbody>
            </table>
            </c:if>
			<c:if test="${empty payApplyList and not empty search}">
									<table class="table table-bordered table-striped table-condensed table-centered" id="tabeleInfo" >
									<!-- 查询无结果弹出 -->
									<tr>
										<td colspan='10'>
											查询无结果！请尝试使用其他搜索条件。
										</td>
									</tr>
			 						</table>
			</c:if>
        	<tags:page page="${page}" optpage="n"/>
        </div>
        <div class='clear'></div>
         <div id="selectTerminalInfo" class="mb15">
         	<div class="add-tijiao tcenter">
                <button type="submit" onclick="paymoneyaddorhand(${bankBill.bankBillId},'${bankBill.tranFlow}',${bankBill.bankTag},${bankBill.amt-bankBill.matchedAmount})">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
         </div>
    </div>
    <input type="hidden" name="formToken" value="${formToken}"/>
<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/finance/bankBill/bankBillMatch.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
