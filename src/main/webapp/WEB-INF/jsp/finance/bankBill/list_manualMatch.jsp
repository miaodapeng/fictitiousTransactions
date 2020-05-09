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
			<form method="post" id="search" action="./getManualMatchInfo.do">
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
        	<c:if test="${not empty saleorderList}">
            <table class="table table-bordered table-striped table-condensed table-centered" id="tabeleInfo" >
                <thead>
                    <tr>
                        <th width="5%">选择</th>
                        <th>订单号</th>
                        <th>客户名称</th>
                        <th>生效时间</th>
                        <th>订单实际总额</th>
                    	<th>已付款金额</th>
                    	<th>本次拟结款金额</th>
                    	<th>拟结款名称</th>
                    	<th>交易主体</th>
                    	<th>备注</th>
                    </tr>
                </thead>
                <tbody class="goodsOpt">
		       		<c:forEach items="${saleorderList}" var="list" varStatus="status">
	                    <tr>
	                        <td><input type="radio" name="checked" value="${list.saleorderId}"/></td>
	                        <td>${list.saleorderNo}</td>
	                        <td>${list.traderName}</td>
	                        <td><date:date value ="${list.validTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
	                        <td>${list.realAmount}</td>
	                        <td>${list.receivedAmount}</td>
	                        <td><input type="text" name="amount" value="${remain_cbc_log>remain_saleorder?remain_saleorder:remain_cbc_log}" style="text-align:center;" onchange="check_pay(this,${bankBill.amt-bankBill.matchedAmount},${list.realAmount-list.receivedAmount},'')"></td>
                            <td><input type="text" name="payer" value="${list.traderName}"></td>
                            <td>
                            <select class="input-middle" name="traderSubject" id="">
								<option value="1" <c:if test="${capitalBillDetail.saleorder.searchType ==1}">selected</c:if>>对公</option>
								<option value="2" <c:if test="${capitalBillDetail.saleorder.searchType ==2}">selected</c:if>>对私</option>
							</select>
							</td>
                            <td>
                            <input type="text" name="comments">
                            <input type="hidden" name="saleorderId" value="${list.saleorderId}">
                            <input type="hidden" name="receivedAmount" value="${list.receivedAmount}">	
                            </td>
	                    </tr>
                	</c:forEach>
                </tbody>
            </table>
            </c:if>
			<c:if test="${empty saleorderList and not empty search}">
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
                <button type="submit" onclick="salemoneyaddorhand(${bankBill.bankBillId})">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
         </div>
    </div>
    <input type="hidden" name="formToken" value="${formToken}"/>
<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/finance/bankBill/bankBillMatch.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
