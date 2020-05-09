<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="付款申请" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/payapply/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
	<div class="content">
		<div class="searchfunc">		
			<form action="${pageContext.request.contextPath}/finance/payapply/getPayApplyListPage.do" method="post" id="search">
				<ul>
	                <li>
	                    <label class="infor_name">订单号</label>
	                    <input type="text" class="input-middle" name="buyorderNo" id="" value="${payApply.buyorderNo}">
	                </li>
	                <li>
	                    <label class="infor_name">供应商名称</label>
	                    <input type="text" class="input-middle" name="buyorderTraderName" id="" value="${payApply.buyorderTraderName}">
	                </li>
	                <li>
	                    <label class="infor_name">收款名称</label>
	                    <input type="text" class="input-middle" name="traderName" id="" value="${payApply.traderName}">
	                </li>
                    <li>
	                    <label class="infor_name">交易方式</label>
	                    <select class="input-middle f_left" name="traderMode">
	                    	<option value="-1">全部</option>
		                    <c:forEach var="list" items="${traderModeList}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${payApply.traderMode == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">交易主体</label>
	                    <select class="input-middle f_left" name="traderSubject">
	                    	<option value="-1">全部</option>
	                    	<option value="1" <c:if test="${payApply.traderSubject == 1}">selected="selected"</c:if> >对公</option>
	                    	<option value="2" <c:if test="${payApply.traderSubject == 2}">selected="selected"</c:if> >对私</option>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">审核状态</label>
	                    <select class="input-middle f_left" name="validStatus">
	                    	<option value="-1">全部</option>
	                    	<option value="0" <c:if test="${payApply.validStatus == 0}">selected="selected"</c:if> >审核中</option>
	                    	<option value="1" <c:if test="${payApply.validStatus == 1}">selected="selected"</c:if> >通过</option>
	                    	<option value="2" <c:if test="${payApply.validStatus == 2}">selected="selected"</c:if> >不通过</option>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">制单状态</label>
	                    <select class="input-middle f_left" name="isBill">
	                    	<option value="-1">全部</option>
	                    	<option value="0" <c:if test="${payApply.isBill == 0}">selected="selected"</c:if> >未制单</option>
	                    	<option value="1" <c:if test="${payApply.isBill == 1}">selected="selected"</c:if> >已制单 </option>
	                    </select>
                    </li>
                    
                    
                    <!-- 是否余额支付 -->
                    <li>
	                    <label class="infor_name">是否余额支付</label>
	                    <select class="input-middle f_left" name="is_528">
	                    	<option value="-1">全部</option>
	                    	<option value="1" <c:if test="${payApply.is_528 eq 1}">selected="selected"</c:if> >是</option>
	                    	<option value="0" <c:if test="${payApply.is_528 eq 0}">selected="selected"</c:if> >否</option>
	                    </select>
                    </li>
                    
                    
                    
                    <li>
						<label class="infor_name">订单金额</label>
						<input class="f_left input-smaller96 mr5" type="text" name="searchBeginAmount" id="searchBeginAmount" value='${payApply.searchBeginAmount}'>
	                    <div class="gang">-</div>
	                    <input class="f_left input-smaller96" type="text" name="searchEndAmount" id="searchEndAmount" value='${payApply.searchEndAmount}'>
					</li>
                    <li>
                    	<label class="infor_name">申请时间</label>
						<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<c:choose>
							<c:when test="${payApply.searchBegintime > 0 && searchDateType != 'second'}">
								<date:date value ="${payApply.searchBegintime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${ searchDateType == 'second'}">
								<date:date value ="${payApply.searchBegintime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:otherwise>
								${pre1MonthDate}
							</c:otherwise>
						</c:choose>'>
	                    <div class="gang">-</div>
	                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<c:choose>
							<c:when test="${payApply.searchEndtime > 0}">
								<date:date value ="${payApply.searchEndtime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${ searchDateType == 'second'}">
								<date:date value ="${payApply.searchEndtime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:otherwise>
								${nowDate}
							</c:otherwise>
						</c:choose>'>
					</li>
					<li>
						<label class="infor_name">流水交易时间</label>
						<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchPayEndtimeStr\')}'})" name="searchPayBegintimeStr" id="searchPayBegintimeStr" value='<c:choose>
							<c:when test="${payApply.searchPayBegintime > 0}">
								<date:date value ="${payApply.searchPayBegintime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${ searchDateType == 'second'}">
								<date:date value ="${payApply.searchPayBegintime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:otherwise>
								${nowDate}
							</c:otherwise>
						</c:choose>'>
	                    <div class="gang">-</div>
	                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchPayBegintimeStr\')}'})" name="searchPayEndtimeStr" id="searchPayEndtimeStr" value='<c:choose>
							<c:when test="${payApply.searchPayEndtime > 0}">
								<date:date value ="${payApply.searchPayEndtime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${searchDateType == 'second'}">
								<date:date value ="${payApply.searchPayEndtime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:otherwise>
								${nowDate}
							</c:otherwise>
						</c:choose>'>
					</li>

					<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. start -->
					<li>
						<label class="infor_name">付款备注</label>
						<select class="input-middle f_left" name="comments">
							<option value="-1">全部</option>
							<option value="1" <c:if test="${payApply.comments eq 1}">selected="selected"</c:if> >有</option>
							<option value="0" <c:if test="${payApply.comments eq 0}">selected="selected"</c:if> >无</option>
						</select>
					</li>
					<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: . end -->

            	</ul>
            	<div class="tcenter">
            		<input type="hidden" name="searchDateType" value="second"/><!-- 标记是否新打开查询页 -->
	                <span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span>
	                <span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
            		<span class="bg-light-blue bt-bg-style bt-small" onclick="exportPayApplyList()">导出列表</span>
            	
            	</div>
			</form>
		</div>
	
		<div class="parts">
            <c:if test="${not empty payApplyList}">
			<c:set var="pageNum" value="0"></c:set>
			<c:set var="pageApplyAmount" value="0.00"></c:set>
			<c:set var="pagePayAmount" value="0.00"></c:set>
            <c:forEach items="${payApplyList}" var="list" varStatus="status">
            <table class="table table-style7">
                <thead>
                    <tr>
						<th class="wid5">选择</th>
                        <th style="width:120px">订单号</th>
                        <th>供应商名称</th>
                        <th style="width:150px">申请金额</th>
                        <th style="width:150px">申请时间</th>
                        <th style="width:120px">申请人</th>
                        <th>收款名称</th>
                        <th style="width:60px">交易主体</th>
                        <th style="width:60px">审核状态</th>
                        <th style="width:60px">制单状态</th>
                        <th style="width:85px">是否余额支付</th>
                        <th style="width:85px">付款备注</th>
                        <th style="width:140px">操作</th>
                    </tr>
                </thead>
                <tbody>
                		<c:set var="pageNum" value="${status.count}"></c:set>
						<c:set var="pageApplyAmount" value="${pageApplyAmount + list.amount}"></c:set>
                		<c:if test="${list.validStatus eq 1}">
							<c:set var="pagePayAmount" value="${pagePayAmount + list.amount}"></c:set>
						</c:if>
	                    <tr>
							<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. start -->
							<td>
								<c:if test="${list.isBill == 0}">
									<input type="checkbox" name="checkbox_one" value="${list.payApplyId}" >
								</c:if>
							</td>
							<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. end -->
	                        <td>
	                        <c:if test="${list.payType == 517}">
	                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./finance/buyorder/viewBuyorder.do?buyorderId=${list.relatedId}","title":"订单信息"}'>${list.buyorderNo}</a>
	                        </c:if>
	                        <c:if test="${list.payType == 518}">
	                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"view_invoice_after<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",	"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedId}","title":"售后详情"}'>${list.afterSalesNo}</a>
	                        </c:if>
	                        
	                        <c:set var="shenhe" value="0"></c:set>
							<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
								<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
									<c:if test="${verifyUsernameInfo == loginUser.username}">
										<c:set var="shenhe" value="1"></c:set>
									</c:if>
								</c:forEach>
							</c:if>
							${list.verifyStatus == 0 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
							</td>
	                        <td>${list.buyorderTraderName}</td>
	                        <td>${list.amount}</td>
	                        <td><date:date value ="${list.addTime}"/></td>
	                        <td>${list.creatorName}</td>
	                        <td>${list.traderName}</td>
	                        <td>
	                        	<c:if test="${list.traderSubject eq 1}">对公</c:if>
								<c:if test="${list.traderSubject eq 2}">对私</c:if>
	                        </td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.verifyStatus eq 0}">
										审核中
									</c:when>
									<c:when test="${list.verifyStatus eq 1}">
										审核通过
									</c:when>
									<c:when test="${list.verifyStatus eq 2}">
										审核未通过
									</c:when>
									<c:otherwise>
										待审核
									</c:otherwise>
								</c:choose>
	                        </td>
	                        <td>
	                        	<c:if test="${list.isBill eq 0}">未制单</c:if>
								<c:if test="${list.isBill eq 1}">已制单</c:if>
	                        </td>
	                        <td><c:if test="${list.traderMode eq 528}">是</c:if><c:if test="${list.traderMode ne 528}">否</c:if></td>
							<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. start -->
							<td>${list.comments}</td>
							<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. end -->
	                        <td>
	                        	<c:if test="${list.isBill eq 1 && list.verifyStatus eq 0}">
	                        		<c:if test="${list.payType == 517}">
	                        		<span class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"600px","height":"350px","title":"新增交易记录","link":"${pageContext.request.contextPath}/finance/buyorder/initPayApplyPass.do?id=${list.payApplyId}&taskId=${list.taskInfoPayId}"}'>通过</span>
	                        		<span class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/order/buyorder/complement.do?taskId=${list.taskInfoPayId}&pass=false&type=1"}'>不通过</span>
	                        		</c:if>
	                        		<c:if test="${list.payType == 518}">
	                        		<span class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"600px","height":"350px","title":"新增交易记录","link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${list.relatedId}&billType=2&payApplyId=${list.payApplyId}&taskId=${list.taskInfoPayId}&pageType=1"}'>通过</span>
	                        		<span class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/finance/after/complement.do?taskId=${list.taskInfoPayId}&pass=false&type=1"}'>不通过</span>
	                        		</c:if>
	                        	</c:if>
								<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. start -->
								<c:if test="${list.isBill eq 0 and list.payType == 517 }">
									<button type="button" class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/order/buyorder/complement.do?taskId=${list.taskInfoPayId}&pass=true&type=1"}'>同意</button>
									<button type="button" class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/order/buyorder/complement.do?taskId=${list.taskInfoPayId}&pass=false&type=1"}'>不同意</button>
								</c:if>
								<c:if test="${list.isBill eq 0 and list.payType == 518 }">
									<button type="button" class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/finance/after/complement.do?taskId=${list.taskInfoPayId}&pass=true&type=1"}'>同意</button>
									<button type="button" class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/finance/after/complement.do?taskId=${list.taskInfoPayId}&pass=false&type=1"}'>不同意</button>
								</c:if>
								<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. end -->
	                        </td>
	                    </tr>
                	
                    <c:if test="${list.bankBillList != null and !empty list.bankBillList}">
                    <tr>
                        <td class="table-container" colspan="11">
                            <table class="table">
                                <thead>
                                    <tr>
                                    	<th class="wid4">序号</th>
                        				<th class="wid15">交易时间</th>
				                        <th >对方名称</th>
				                        <th >对方账号</th>
				                        <th class="wid8">摘要</th>
				                        <th class="wid8">备注</th>
				                        <th >收款金额</th>
				                        <th >付款金额</th>
				                        <th class="wid12">剩余结款金额</th>
				                        <th class="wid15">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="bankBilllist" items="${list.bankBillList}" varStatus="num">
                                    <tr>
                                    	 <td>${num.count}</td>
				                        <td>${bankBilllist.realTrandatetime}</td>
				                        <td>${bankBilllist.accName1}</td>
				                        <td>${bankBilllist.accno2}</td>
				                        <td>${bankBilllist.message}</td>
				                        <td>${bankBilllist.det}</td>
				                        <td><c:if test="${bankBilllist.flag1 != 0}">${bankBilllist.amt}</c:if></td>
				                        <td><c:if test="${bankBilllist.flag1 == 0}">${bankBilllist.amt}</c:if></td>
				                        <td>${bankBilllist.amt-bankBilllist.matchedAmount}</td>
				                        <td>
				                        	<c:if test="${list.verifyStatus eq 0 && list.isBill eq 1}">
				                        		<span class="bt-smaller bt-border-style border-blue" onclick="matchBankBill('${list.payApplyId}','${bankBilllist.bankBillId}','${bankBilllist.tranFlow}','${list.taskInfoPayId}','${bankBilllist.bankTag}','${list.payType}');">匹配</span>
				                        	</c:if>
				                        </td>
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
        	</c:if>
        	<c:if test="${not empty payApplyList}">
				<div class="tablelastline" style="margin:0px;" >
					【全部结果 订单数：${page.totalRecord} 申请总额：<fmt:formatNumber type="number" value="${payApply.payApplyTotalAmount}" pattern="0.00" maxFractionDigits="2" /> 付款总额：<fmt:formatNumber type="number" value="${payApply.payApplyPayTotalAmount}" pattern="0.00" maxFractionDigits="2" />】
					【本页统计 订单数：${pageNum} 申请总额：<fmt:formatNumber type="number" value="${pageApplyAmount}" pattern="0.00" maxFractionDigits="2" /> 付款总额：<fmt:formatNumber type="number" value="${pagePayAmount}" pattern="0.00" maxFractionDigits="2" />】
				</div>
			</c:if>
              <c:if test="${empty payApplyList}">
				
				<!-- 查询无结果弹出 -->
           		<div style="border:1px solid #ddd;padding:4px 0;text-align:center;">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
           <tags:page page="${page}"/>
        </div>
		<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. start -->
		<div class="inputfloat f_left" style='margin:0px 0 15px 0;'>
			<input type="checkbox" name="check_All" onclick="checkall(this,'');" value="checkbox_one"> <span>全选</span>
			<span class="bt-bg-style bg-light-blue bt-small mr10" onclick="batchConfirm();">批量确认</span>
		</div>
		<!-- add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. end -->
        <input type="hidden" name="formToken" value="${formToken}"/>
	</div>
<%@ include file="../../common/footer.jsp"%>