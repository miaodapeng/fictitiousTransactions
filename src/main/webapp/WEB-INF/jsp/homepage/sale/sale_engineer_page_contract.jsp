<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="今日任务" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/home/page/home_page.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/home/page/sale_engineer_page_contract_list.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
        <%@ include file="sale_engineer_tag.jsp"%>
        <div class="showing-card">
        	 <input type="hidden" name="formToken" value="${formToken}"/>
        	  <input type="hidden" name="searchType" value="${searchType}"/>
            <ul>           
                <li>
                    <div class="card-container personal-data"  style="height: 100px">
                        <div class="card-title blue-title">
                            	销售合同上传数据<span style="font-size: 12px">（从2018年08月01日开始统计）</span>
                        </div>
                        <div class="card-content blue-content" style="height: 80px!important;">
                            <ul>

	                            			<li>
			                                    <span>未提交审核合同数</span>
			                                   <c:choose>
			                                    	<c:when test="${count1!=0}">
				                                    	<a  href="./index.do?accessType=6&searchType=1" style="color: white;font-size: 20px;" 
				                                    	onmouseover="this.style.color='red'" onmouseout="this.style.color='white'"
				                                    	>${count1}</a>
				                                    </c:when>
				                                    <c:otherwise>
				                                    	<span class="num">${count1}</span>
				                                    </c:otherwise>
			                                    </c:choose>
			                                </li>

	                            			<li>
			                                    <span>不合格合同数</span>
			                                    <c:choose>
			                                    	<c:when test="${count2!=0}">
				                                    	<a href="./index.do?accessType=6&searchType=2" style="color: white;font-size: 20px;" 
				                                    	onmouseover="this.style.color='red'" onmouseout="this.style.color='white'"
				                                    	>${count2}</a>
				                                    </c:when>
				                                    <c:otherwise>
				                                    	<span class="num">${count2}</span>
				                                    </c:otherwise>
			                                    </c:choose>
			                                </li>

                            </ul>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
         <div class="table-friend-tip mb15" style="margin-top:0;color:#FF6600">
			温馨提示：<br/>
			1、请在上传合同附件完成后，点击申请审核，运营部门才能进行审核处理。<br/>
			2、回传类型为电子+纸质的订单，除了需要在线上上传附件提交审核外，还需要将客户回寄的合同线下交给运营部门人员审核。
         </div>
	<div class="searchfunc" >
		<form method="post" id="search">
			<ul>
				<li>
					<label class="infor_name">客户名称</label>
					<input type="text" class="input-middle" name="customerName" id="customerName" value="${saleorder.customerName}"/>
				</li>
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorder.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">订单归属</label>
					<select class="input-middle" name="optUserName" id="optUserName">
						<option value="-1">全部</option>
						<option value="${currUser}" <c:if test="${saleorder.optUserName != '-1'  && saleorder.optUserName != null}">selected="selected"</c:if> >${currUser}</option>
					</select>
				</li>
				<li>
					<label class="infor_name">初次打款时间</label>
					<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${saleorder.searchBegintime}" format="yyyy-MM-dd"/>'>
                    <div class="gang">-</div>
                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${saleorder.searchEndtime}" format="yyyy-MM-dd"/>'>
				</li>
				<li>
					<label class="infor_name">回传类型</label>
					<select class="input-middle" name=contractType id="contractType">
						<option value="">全部类型</option>
						<option <c:if test="${saleorder.contractType eq '电子'}">selected</c:if> value="电子">电子</option>
						<option <c:if test="${saleorder.contractType eq '电子+纸质'}">selected</c:if> value="电子+纸质">电子+纸质</option>
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<c:if test="${not empty traderId}">
					<input type="hidden" name="traderId" value="${traderId }" >
				</c:if>
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">查询</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>

			</div>
		</form>
	</div>

	<div class="content">
		<div class="fixdiv">
			<div class='superdiv' style='width:1450px;'>
				<table
					class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid4">序号</th>
							<th class="wid10">客户名称</th>
							<th class="wid8">订单号</th>
							<th class="wid6">已收款金额</th>
							<th class="wid8">初次打款时间</th>
							<th class="wid4">距离今天数</th>
							<th class="wid6">回传类型</th>
							<th class="wid6">订单归属</th>	
							<th class="wid15">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${contractSaleorderList}" varStatus="num">
							<tr>
								<td>${num.count}</td>
								<td>
									<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.status eq 0}">
											<span class="orangecircle"></span>
										</c:when>
										<c:when test="${list.status eq 1}">
											<span class="greencircle"></span>
										</c:when>
										<c:when test="${list.status eq 2}">
											<span class="bluecircle"></span>
										</c:when>
										<c:when test="${list.status eq 3}">
											<span class="greycircle"></span>
										</c:when>
									</c:choose>
									
									<c:if test="${list.validStatus eq 0}">
										<c:set var="shenhe" value="0"></c:set>
										<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
											<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
												<c:if test="${verifyUsernameInfo == loginUser.username}">
													<c:set var="shenhe" value="1"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
									</c:if>
									<c:set var="costPriceEmpty" value="0"></c:set>
										<c:if test="${list.costUserIdsList!=null && null!=list.costUserIdsList}">
											<c:forEach items="${list.costUserIdsList}" var="costUserIds">
												<c:if test="${costUserIds == loginUser.userId}">
													<c:set var="costPriceEmpty" value="1"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderNo}</a>${list.validStatus == 0 && list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}${costPriceEmpty == 1 ?"<font color='red'>[填]</font>":""}
									<%-- <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderNo}</a>${list.validStatus == 0 && list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""} --%>
								</td>
								<td>${list.paymentAmount}</td>
								<td>${list.firstPaymentTime}</td>
								<td <c:if test="${list.nowDayBetween gt 30}"> style='color:red;'</c:if> >${list.nowDayBetween} </td>
								<td>${list.contractType}</td>
								<td>${list.optUserName}</td>
								<td>
									<a class="pop-new-data" layerParams='{"width":"620px","height":"300px","title":"合同回传","link":"${pageContext.request.contextPath}/order/saleorder/contractReturnInit.do?saleorderId=${list.saleorderId}"}'>上传</a>&nbsp;&nbsp;&nbsp;
	                        		<a class="pop-new-data" layerParams='{"width":"720px","height":"300px","title":"合同上传列表","link":"${pageContext.request.contextPath}/order/saleorder/contractReturnListNoFormtoken.do?saleorderId=${list.saleorderId}"}'>查看附件</a>&nbsp;&nbsp;&nbsp;
	                        		<a onclick="requestCheck(${list.saleorderId},${list.taskId == null ?0: list.taskId},${list.contractFileCount});">申请审核</a>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty contractSaleorderList}">
			      			<!-- 查询无结果弹出 -->
			          		<tr>
			          			<td colspan="9">数据为空</td>
			          		</tr>
				       	</c:if>
					</tbody>
				</table>
			</div>
		</div>
       	<tags:page page="${page1}"/>
	</div>


<%@ include file="../../common/footer.jsp"%>