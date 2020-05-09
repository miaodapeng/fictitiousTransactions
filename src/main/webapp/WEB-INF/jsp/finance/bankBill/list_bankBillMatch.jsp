<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单结款列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>finance/bankbill/bankBillMatchList.do">
			<ul>
				<li>
                    <label class="infor_name">交易时间</label>
                    <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" id="beginTime" name="beginTime" value="${beginTime}" onchange="noEmpty(this,'${beginTime}')">
					<div class="gang">-</div> 
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" id="endTime" name="endTime" value="${endTime}" onchange="noEmpty(this,'${beginTime}')">
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
			<c:if test="${bankBilllist.bankTag == 1}">			
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
                        <th class="wid12">剩余结款金额</th>
                        <th class="wid30">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${num.count}</td>
                        <td>${fn:substring(bankBilllist.realTrandatetime,0,19)}</td>
                        <td>${bankBilllist.accName1}</td>
                        <td><c:if test="${bankBilllist.flag1 != 0}">${bankBilllist.amt}</c:if></td>
                        <td>${bankBilllist.accno2}</td>
                        <td>${bankBilllist.message}</td>
                        <td>${bankBilllist.det}</td>
                        <td><c:if test="${bankBilllist.flag1 == 0}">${bankBilllist.amt}</c:if></td>
                        <td>${bankBilllist.amt-bankBilllist.matchedAmount}</td>
                        <td>
                            <c:if test="${bankBilllist.accName1 == '支付宝（中国）网络技术有限公司' || bankBilllist.accName1 == '财付通支付科技有限公司'}"><span class="bt-smaller bt-border-style border-blue pop-new-data"  layerParams='{"width":"500px","height":"180px","title":"上传批量结款信息","link":"./bankBillBatchInit.do?bankBillId=${bankBilllist.bankBillId}&bankAccName=${bankBilllist.accName1}"}'>批量结款</span></c:if>
                        	<span class="bt-smaller bt-border-style border-blue" onclick="manualMatchInfo(this,${bankBilllist.bankBillId});">手动匹配</span>
                            <span class="bt-smaller bt-border-style border-red pop-new-data"  layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./addIgnore.do?bankBillId=${bankBilllist.bankBillId}&type=1"}'>忽略</span>
                        </td>
                    </tr>
                    <c:if test="${bankBilllist.capitalBillDetailList != null}">
                    <tr>
                        <td class="table-container" colspan="10">
                            <table class="table">
                                <thead>
                                    <tr>
                                    	<th class="wid4">序号</th>
                                        <th class="wid9">订单号</th>
                                        <th class="wid15">合同名称</th>
                                        <th class="wid14">生效时间</th>
                                        <th class="wid10">订单实际总额</th>
                                        <th class="wid10">已付款金额</th>
                                        <th class="wid10">剩余帐期金额</th>
                                        <th>本次拟结款金额</th>
                                        <th>拟结款名称</th>
                                        <th class="wid6">交易主体</th>
                                        <th>备注</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
								<c:set var="remain_cbc_log" value="${bankBilllist.amt-bankBilllist.matchedAmount}"></c:set>
                                <c:set var="page_order_num" value="${page_order_num+bankBilllist.capitalBillDetailList.size()}"></c:set>
                                <c:forEach var="capitalBillDetail" items="${bankBilllist.capitalBillDetailList}" varStatus="cnum">
                                	<c:set var="page_order_amount" value="${page_order_amount+capitalBillDetail.saleorder.totalAmount}"></c:set>
									<c:set var="page_match_amount" value="${page_match_amount+capitalBillDetail.amount}"></c:set>
									<c:set var="remain_saleorder" value="${capitalBillDetail.saleorder.totalAmount-capitalBillDetail.saleorder.receivedAmount}"></c:set>
                                    <tr>
                                    	<td>${cnum.count}</td>
                                        <td>
                                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${capitalBillDetail.saleorder.saleorderId}","link":"./order/saleorder/view.do?saleorderId= ${capitalBillDetail.saleorder.saleorderId}","title":"订单信息"}'> ${capitalBillDetail.saleorder.saleorderNo}</a>
                                        </td>
                                        <td>${capitalBillDetail.saleorder.traderName}</td>
                                        <td><date:date value ="${capitalBillDetail.saleorder.validTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>${capitalBillDetail.saleorder.totalAmount}</td>
                                        <td>${capitalBillDetail.saleorder.receivedAmount}</td>
                                        <td>${capitalBillDetail.saleorder.residueAmount}</td>
                                        <!-- 20181115 Tomcat.hui VDERP-1447 在页面进行判断 -->
                                        <c:choose>
                                            <c:when test="${bankBilllist.accName1 eq '支付宝（中国）网络技术有限公司' or bankBilllist.accName1 eq '财付通支付科技有限公司'}">
                                                <td><input type="text"  name="amount" value="${bankBilllist.amt}" style="text-align:center;" onchange="check_pay(this,${bankBilllist.amt-bankBilllist.matchedAmount},${capitalBillDetail.saleorder.totalAmount-capitalBillDetail.saleorder.receivedAmount},${remain_cbc_log>remain_saleorder?remain_saleorder:remain_cbc_log})"></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td><input type="text"  name="amount" value="${remain_cbc_log>remain_saleorder?remain_saleorder:remain_cbc_log}" style="text-align:center;" onchange="check_pay(this,${bankBilllist.amt-bankBilllist.matchedAmount},${capitalBillDetail.saleorder.totalAmount-capitalBillDetail.saleorder.receivedAmount},${remain_cbc_log>remain_saleorder?remain_saleorder:remain_cbc_log})"></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <!-- 20181115 Tomcat.hui VDERP-1447 在页面进行判断 -->
                                        <td><input type="text" name="payer" value="${bankBilllist.accName1}"></td>
                                        <td>
                                        <select class="input-middle" name="traderSubject" id="">
											<option value="1" <c:if test="${capitalBillDetail.saleorder.searchType ==1}">selected</c:if>>对公</option>
											<option value="2" <c:if test="${capitalBillDetail.saleorder.searchType ==2}">selected</c:if>>对私</option>
										</select>
										</td>
                                        <td><input type="text" name="comments"></td>
                                        <td><span class="bt-smaller bt-border-style border-blue" onclick="salemoneyadd(${bankBilllist.bankBillId},${capitalBillDetail.saleorder.saleorderId},${capitalBillDetail.saleorder.receivedAmount},this)">确认</span></td>
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
            </c:if>
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
