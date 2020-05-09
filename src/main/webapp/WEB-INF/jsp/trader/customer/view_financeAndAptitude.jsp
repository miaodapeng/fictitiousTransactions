<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="客户财务与资质信息" scope="application" />
<%@ include file="../../common/common.jsp"%>

	<%@ include file="customer_tag.jsp"%>
	<div class="content">
        <div class="parts">
            <div class="title-container">
                <div class="table-title">资质信息</div>
                <c:if test="${isbelong &&(traderCustomer.aptitudeStatus==null||traderCustomer.aptitudeStatus==3||traderCustomer.aptitudeStatus==2)}">
                <!-- 	<div class="title-click  pop-new-data" layerParams='{"width":"1200px","height":"500px","title":"编辑","link":"./editAptitude.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}"}'>编辑</div> -->
                	<div class="title-click addtitle"
					tabTitle='{"num":"customer${traderCustomer.traderId}","link":"./trader/customer/editAptitude.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}","title":"编辑资质"}'>编辑</div>
                </c:if>
				<c:if test="${traderCustomer.aptitudeStatus!=null and traderCustomer.aptitudeStatus==0 and (taskInfo.assignee == curr_user.username or candidateUserMap['belong'])}">

					<div class="title-click" onclick="checkVerifyStatus(${traderCustomer.traderCustomerId})">审核资质</div>
					<div id="checkAptitudePage" style="display: none" class="addtitle"
						 tabTitle='{"num":"customer${traderCustomer.traderId}","link":"./trader/customer/editAptitude.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}","title":"编辑资质"}'></div>
				</c:if>
				<%--<c:if test="${isbelong and traderCustomer.aptitudeStatus==1}">--%>
					<%--<div class="title-click addtitle"--%>
						 <%--tabTitle='{"num":"customer${traderCustomer.traderId}","link":"./trader/customer/editAptitude.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}","title":"编辑资质"}'>修改资质</div>--%>
				<%--</c:if>--%>
            </div>
           <% Date date=new Date();long now = date.getTime(); request.setAttribute("now", now); %>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                <c:if test="${traderCustomer.customerNature eq 466}">
                    <tr>
                        <td class="table-smallest">是否为营利机构</td>
                        <td style="text-align: left;">
                            <c:if test="${traderCustomer.isProfit ne 1}">是</c:if>
                            <c:if test="${traderCustomer.isProfit eq 1}">否</c:if>
                        </td>
                    </tr>
                </c:if>
				<tr>
                    <c:forEach items="${bussinessList }" var="bus" varStatus="st">
                        <c:if test="${st.index == 0}">
                            <c:set var="threeInOne" value="${bus.threeInOne}"></c:set>
                        </c:if>
                    </c:forEach>
					<td class="table-smallest">三证合一</td>
					<td style="text-align: left;">
                        <c:if test="${threeInOne eq 1}">是</c:if>
                        <c:if test="${threeInOne ne 1}">否</c:if>
                    </td>
				</tr>
                    <tr>
                        <td class="table-smallest">营业执照</td>
                        <td style="text-align: left;">
                        	<%--<c:choose> --%>
							   <%--<c:when test="${not empty business && not empty business.uri }">--%>
							   		 <%--<a href="http://${business.domain}${business.uri}" target="_blank">营业执照</a>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--营业执照--%>
							   <%--</c:otherwise>--%>
							<%--</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;--%>
                        	<%--有效期： <date:date value ="${business.begintime}" format="yyyy-MM-dd"/>--%>
		                        	<%--<c:if test="${business ne null && business.endtime eq null}">-无限期</c:if>--%>
			                        <%--<c:if test="${business.endtime ne null}">-<date:date value ="${business.endtime}" format="yyyy-MM-dd"/></c:if>&nbsp;&nbsp;&nbsp;&nbsp;--%>
			                        <%--<c:if test="${business.endtime ne null && business.endtime ne 0 && business.endtime lt now }"><span style="color: red">（已过期）</span></c:if> --%>
		                        	<%--<c:if test="${business.isMedical eq 1}">含有医疗器械</c:if>--%>
								<c:choose>
									<c:when test="${bussinessList ne null }">
										<c:forEach items="${bussinessList }" var="bus" varStatus="st">
											<c:if test="${st.index == 0}">
												<c:set var="begintime" value="${bus.begintime}"></c:set>
												<c:set var="endtime" value="${bus.endtime}"></c:set>
											</c:if>
											<c:if test="${bus.uri ne null && not empty bus.uri}">
												<a href="http://${bus.domain}${bus.uri}" target="_blank">营业执照- ${st.index + 1}</a>&nbsp;&nbsp;
											</c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										营业执照&nbsp;
									</c:otherwise>
								</c:choose>&nbsp;&nbsp;&nbsp;
								有效期：<date:date value ="${begintime} " format="yyyy-MM-dd"/>
								<c:if test="${bussinessList ne null && endtime eq null && not empty bussinessList}">-无限期</c:if>
								<c:if test="${endtime ne null}">
									-<date:date value ="${endtime} " format="yyyy-MM-dd"/>
								</c:if>
								<%--&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn}--%>
								<c:if test="${endtime ne null && endtime ne 0 && endtime lt now }"><span style="color: red">（已过期）</span></c:if>

						</td>
                    </tr>
                    <tr>
                        <td class="table-smallest">税务登记证</td>
                        <td style="text-align: left;">
                        	<c:choose>
							   <c:when test="${not empty tax && not empty tax.uri}">
							   		 <a href="http://${tax.domain}${tax.uri}" target="_blank">税务登记证</a>
							   </c:when>
							   <c:otherwise>
							   		税务登记证
							   </c:otherwise>
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
                        	有效期：  <date:date value ="${tax.begintime}" format="yyyy-MM-dd"/>
		                        	<c:if test="${tax ne null && tax.endtime eq null}">-无限期</c:if>
			                        <c:if test="${tax.endtime ne null}">-<date:date value ="${tax.endtime}" format="yyyy-MM-dd"/></c:if>
			                        <c:if test="${tax.endtime ne null && tax.endtime ne 0 && tax.endtime lt now }"><span style="color: red">（已过期）</span></c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smallest">组织机构代码证</td>
                        <td style="text-align: left;">
                        	<c:choose>
							   <c:when test="${not empty orga && not empty orga.uri}">
							   		 <a href="http://${orga.domain}${orga.uri}" target="_blank">组织机构代码证</a>
							   </c:when>
							   <c:otherwise>
							   		组织机构代码证
							   </c:otherwise>
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
                       		有效期：<date:date value ="${orga.begintime}" format="yyyy-MM-dd"/>
	                        		<c:if test="${orga ne null && orga.endtime eq null}">-无限期</c:if>
		                        	<c:if test="${orga.endtime ne null}">-<date:date value ="${orga.endtime}" format="yyyy-MM-dd"/></c:if>
		                        	<c:if test="${orga.endtime ne null && orga.endtime ne 0 && orga.endtime lt now }"><span style="color: red">（已过期）</span></c:if>

                        </td>
                    </tr>
                    <c:if test="${traderCustomer.customerNature eq 465}">
	                    <tr>
	                        <td class="table-smallest">二类医疗资质</td>
	                        <td style="text-align: left;">
	                        	<c:choose>
							   <c:when test="${twoMedicalList ne null }">
							   <c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">
								   <c:if test="${st.index == 0}">
								   	<c:set var="beginTime2" value="${twoMedical.begintime}"></c:set>
								   	<c:set var="endTime2" value="${twoMedical.endtime}"></c:set>
								   	<c:set var="sn2" value="${twoMedical.sn}"></c:set>
								   </c:if>
									   	<c:if test="${twoMedical.uri ne null && not empty twoMedical.uri}">
									   		 <a href="http://${twoMedical.domain}${twoMedical.uri}" target="_blank">二类医疗资质- ${st.index + 1}</a>&nbsp;&nbsp;
									   	</c:if>
							   </c:forEach>
							   </c:when>
							   <c:otherwise>
							   		二类医疗资质&nbsp;
							   </c:otherwise>
							</c:choose>&nbsp;&nbsp;&nbsp;
                        	有效期：<date:date value ="${beginTime2} " format="yyyy-MM-dd"/>
                        			<c:if test="${twoMedicalList ne null && endTime2 eq null && not empty twoMedicalList}">-无限期</c:if>
                        			<c:if test="${endTime2 ne null}">
                        				-<date:date value ="${endTime2} " format="yyyy-MM-dd"/>
                        			</c:if>&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn2}
                        			 <c:if test="${endTime2 ne null && endTime2 ne 0 && endTime2 lt now }"><span style="color: red">（已过期）</span></c:if>

	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="table-smallest">二类医疗资质(旧国标)</td>
	                        <td style="text-align: left;">
	                        	<c:if test="${not empty two }">
					               <c:forEach items="${two }" var="mc">
					               	${mc.title}&nbsp;&nbsp;
					               </c:forEach>
					            </c:if>
	                        </td>

	                    </tr>
						<tr>
	                        <td class="table-smallest">二类医疗资质(新国标)</td>
	                        <td style="text-align: left;">
	                        	<c:if test="${not empty newTwo }">
					               <c:forEach items="${newTwo}" var="mc">
					               	${mc.title}&nbsp;&nbsp;
					               </c:forEach>
					            </c:if>
	                        </td>

	                    </tr>
	                    <tr>
	                        <td class="table-smallest">三类医疗资质</td>
	                        <td style="text-align: left;">
	                        	<c:choose>

									<c:when test="${threeMedical ne null }">
										<c:forEach items="${threeMedical}" var="three" varStatus="st">
											<c:if test="${st.index == 0}">
												<c:set var="beginTime3" value="${three.begintime}"></c:set>
												<c:set var="endTime3" value="${three.endtime}"></c:set>
												<c:set var="sn3" value="${three.sn}"></c:set>
											</c:if>
											<c:if test="${three.uri ne null && not empty three.uri}">
												<a href="http://${three.domain}${three.uri}" target="_blank">三类医疗资质- ${st.index + 1}</a>&nbsp;&nbsp;
											</c:if>
										</c:forEach>
									</c:when>
								   <%--<c:when test="${not empty threeMedical && not empty threeMedical.uri}">--%>
								   		 <%--<a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank">三类医疗资质</a>--%>
								   <%--</c:when>--%>
								   <c:otherwise>
								   		三类医疗资质
								   </c:otherwise>
								</c:choose>&nbsp;&nbsp;&nbsp;&nbsp;
	                        	有效期:
								<date:date value ="${beginTime3} " format="yyyy-MM-dd"/>
	                        			<c:if test="${beginTime3 ne null && endTime3 eq null}">-无限期</c:if>
	                        			<c:if test="${endTime3 ne null}">
	                        				-<date:date value ="${endTime3} " format="yyyy-MM-dd"/>
	                        			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;许可证编号：${sn3}
	                        			 <c:if test="${endTime3 ne null && endTime3 ne 0 && endTime3 lt now }"><span style="color: red">（已过期）</span></c:if>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="table-smallest">三类医疗资质(旧国标)</td>
	                        <td style="text-align: left;">
	                        	<c:if test="${not empty three }">
					               <c:forEach items="${three }" var="mc">
					               	${mc.title}&nbsp;&nbsp;
					               </c:forEach>
					            </c:if>
	                        </td>
	                    </tr>
						<tr>
	                        <td class="table-smallest">三类医疗资质(新国标)</td>
	                        <td style="text-align: left;">
	                        	<c:if test="${not empty newThree }">
					               <c:forEach items="${newThree}" var="mc">
					               	${mc.title}&nbsp;&nbsp;
					               </c:forEach>
					            </c:if>
	                        </td>
	                    </tr>
						<tr>
							<td class="table-smallest">备注</td>
							<td style="text-align: left;">
                                ${comment}
							</td>
						</tr>
                    </c:if>

                    <c:if test="${traderCustomer.customerNature eq 466}">
                    	<tr>
	                        <td class="table-smallest">医疗机构执业许可证</td>
	                        <td style="text-align: left;">
		                        <c:choose>
								   <c:when test="${practiceList ne null }">
								   <c:forEach items="${practiceList }" var="practice" varStatus="st">
								   <c:if test="${st.index == 0}">
								   	<c:set var="beginTime2" value="${practice.begintime}"></c:set>
								   	<c:set var="endTime2" value="${practice.endtime}"></c:set>
								   	<c:set var="sn2" value="${practice.sn}"></c:set>
								   </c:if>
								   	<c:if test="${practice.uri ne null && not empty practice.uri}">
								   		 <a href="http://${practice.domain}${practice.uri}" target="_blank">医疗机构执业许可证 - ${st.index + 1}</a>&nbsp;&nbsp;
								   	</c:if>
								   </c:forEach>
								   </c:when>
								   <c:otherwise>
								   		医疗机构执业许可证&nbsp;
								   </c:otherwise>
								</c:choose>&nbsp;&nbsp;&nbsp;
	                        	有效期：<date:date value ="${beginTime2} " format="yyyy-MM-dd"/>
	                        			<c:if test="${practiceList ne null  && endTime2 eq null && not empty practiceList}">-无限期</c:if>
	                        			<c:if test="${endTime2 ne null}">
	                        				-<date:date value ="${endTime2} " format="yyyy-MM-dd"/>
	                        			</c:if>&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn2}
	                        			 <c:if test="${endTime2 ne null && endTime2 ne 0 && endTime2 lt now }"><span style="color: red">（已过期）</span></c:if>
	                        </td>
	                    </tr>
                    </c:if>
                    <!-- add by fralin.wu for[耗材商城的客户管理-代付款证明] at 2018/11/22 begin -->
                    <tr>
                        <td class="table-smallest">代付款证明</td>
                        <td style="text-align: left;">
						   <c:forEach items="${pofPayList}" var="porPay" varStatus="st">
							   	<c:if test="${not empty porPay.uri && not empty porPay.domain}">
							   		 <a href="http://${porPay.domain}${porPay.uri}" target="_blank">代付款证明- ${st.index + 1}</a>&nbsp;&nbsp;
							   	</c:if>
						   </c:forEach>
                        </td>
                    </tr>
                    <!-- add by fralin.wu for[耗材商城的客户管理-代付款证明] at 2018/11/22 end -->

                </tbody>
            </table>
        </div>
		<div class="tcenter mb15 mt-5">

			<c:choose>
				<c:when test="${isbelong and (traderCustomer.aptitudeStatus==1 or traderCustomer.aptitudeStatus==0)}">
					<button class="bt-bg-style bg-light-orange bt-small mr10" onclick="changeAptitude(${traderCustomer.traderCustomerId},${traderCustomer.aptitudeStatus},${taskInfo.id})">修改资质</button>
					<button id="changeAptitudeTitle" style="display: none;" type="button" class="addtitle" tabTitle='{"num":"customer${traderCustomer.traderId}","link":"./trader/customer/editAptitude.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}","title":"编辑资质"}'>修改资质</button>
				</c:when>
				<c:when test="${traderCustomer.aptitudeStatus!=null and traderCustomer.aptitudeStatus==0}">
					<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
				</c:when>
			</c:choose>
		</div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title">财务信息</div>
                <c:if test="${(not empty trader && trader.source eq 0)&&(finance ==null || finance.checkStatus==null||finance.checkStatus==3||finance.checkStatus==2) && customerInfoByTraderCustomer.isEnable == 1 && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null) }">
				<div class="title-click  pop-new-data" layerParams='{"width":"600px","height":"380px","title":"编辑","link":"./toEditFinancePage.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&source=${trader.source}"}'>编辑</div>
			    </c:if>
				<c:if test="${(trader.source eq 1) &&isFinanceBelong &&(finance ==null || finance.checkStatus==null||finance.checkStatus==3||finance.checkStatus==2)}">

					<div class="title-click  pop-new-data" layerParams='{"width":"600px","height":"380px","title":"编辑","link":"./editYxgFinancePage.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&source=${trader.source}"}'>编辑</div>
				</c:if>
				<c:if test="${finance.checkStatus!=null and finance.checkStatus==0 and (financeTaskInfo.assignee == curr_user.username or financeCandidateUserMap['belong'])}">
					<div class="title-click  pop-new-data" layerParams='{"width":"600px","height":"380px","title":"审核","link":"./editYxgFinancePage.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&source=${trader.source}"}'>财务审核</div>
				</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td >注册地址</td>
                        <td >${finance.regAddress}</td>
                        <td >注册电话</td>
                        <td >${finance.regTel}</td>
                    </tr>
                    <tr>
                        <td>税务登记号</td>
                        <td>${finance.taxNum}</td>
                        <td>一般纳税人资质</td>
                        <td>
                        	<c:choose>
							   <c:when test="${finance ne null && not empty finance.averageTaxpayerUri }">
							   		 <a href="http://${finance.averageTaxpayerDomain}${finance.averageTaxpayerUri}" target="_blank">查看资质</a>
							   </c:when>
							   <c:otherwise>

							   </c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>开户银行</td>
                        <td>${finance.bank}</td>
                        <td>开户行支付联行号</td>
                        <td>${finance.bankCode}</td>
                    </tr>
                    <tr>
                        <td>银行帐号</td>
                        <td>${finance.bankAccount}</td>
                        <td>帐期</td>
                        <td>当前额度<fmt:formatNumber type="number" value="${finance.periodAmount == null ? 0:finance.periodAmount}" pattern="0.00" maxFractionDigits="2" />元，帐期天数${finance.periodDay}天；当前剩余额度<fmt:formatNumber type="number" value="${finance.balanceAccount == null ? 0:finance.balanceAccount}" pattern="0.00" maxFractionDigits="2" />元</td>
                    </tr>
                    <tr>
                        <td>账户余额</td>
                        <td><fmt:formatNumber type="number" value="${finance.amount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td>信用评估</td>
                        <td>${finance.creditRating}</td>
                    </tr>
                </tbody>
            </table>
        </div>

		<div class="tcenter mb15 mt-5">

			<c:choose>
				<c:when test="${isFinanceBelong && trader.source eq 1  and finance.checkStatus==1}">
					<span class=" pop-new-data bt-bg-style bg-light-orange bt-small"  layerParams='{"width":"600px","height":"380px","title":"编辑","link":"./editYxgFinancePage.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&source=${trader.source}"}'>修改财务信息</span>
				</c:when>

				<c:when test="${ trader.source eq 0  and finance.checkStatus==1}">
					<span class=" pop-new-data bt-bg-style bg-light-orange bt-small"  layerParams='{"width":"600px","height":"380px","title":"编辑","link":"./toEditFinancePage.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&source=${trader.source}"}'>修改财务信息</span>
				</c:when>
				<c:when test="${finance.checkStatus!=null and finance.checkStatus==0}">
					<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
				</c:when>
			</c:choose>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">
					资质审核记录
				</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
				<tr>
					<td>操作人</td>
					<td>操作时间</td>
					<td>操作事项</td>
					<td>备注</td>
				</tr>
				</thead>
				<tbody>
				<c:if test="${null!=historicActivityInstance}">
					<c:forEach var="hi" items="${historicActivityInstance}" varStatus="status">
						<c:if test="${not empty  hi.activityName}">
							<tr>
								<td>
									<c:choose>
										<c:when test="${hi.activityType == 'startEvent'}">
											${startUser}
										</c:when>
										<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
										</c:when>
										<c:otherwise>
											<c:if test="${historicActivityInstance.size() == status.count}">
												${verifyUsers}
											</c:if>
											<c:if test="${historicActivityInstance.size() != status.count}">
												${hi.assignee}
											</c:if>
										</c:otherwise>
									</c:choose>


								</td>
								<td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<c:choose>
										<c:when test="${hi.activityType == 'startEvent'}">
											开始
										</c:when>
										<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
											结束
										</c:when>
										<c:otherwise>
											${hi.activityName}
										</c:otherwise>
									</c:choose>
								</td>
								<td class="font-red">${commentMap[hi.taskId]}</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty historicActivityInstance}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan="4">暂无审核记录。</td>
					</tr>
				</c:if>
				</tbody>
			</table>

			<div class="clear"></div>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">
					财务审核记录
				</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
				<tr>
					<td>操作人</td>
					<td>操作时间</td>
					<td>操作事项</td>
					<td>备注</td>
				</tr>
				</thead>
				<tbody>
				<c:if test="${null!=financeHistoricActivityInstance}">
					<c:forEach var="hi" items="${financeHistoricActivityInstance}" varStatus="status">
						<c:if test="${not empty  hi.activityName}">
							<tr>
								<td>
									<c:choose>
										<c:when test="${hi.activityType == 'startEvent'}">
											${financeStartUser}
										</c:when>
										<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
										</c:when>
										<c:otherwise>
											<c:if test="${financeHistoricActivityInstance.size() == status.count}">
												${verifyUsersFinance}
											</c:if>
											<c:if test="${financeHistoricActivityInstance.size() != status.count}">
												${hi.assignee}
											</c:if>
										</c:otherwise>
									</c:choose>


								</td>
								<td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<c:choose>
										<c:when test="${hi.activityType == 'startEvent'}">
											开始
										</c:when>
										<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
											结束
										</c:when>
										<c:otherwise>
											${hi.activityName}
										</c:otherwise>
									</c:choose>
								</td>
								<td class="font-red">${financeCommentMap[hi.taskId]}</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty financeHistoricActivityInstance}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan="4">暂无审核记录。</td>
					</tr>
				</c:if>
				</tbody>
			</table>

			<div class="clear"></div>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">帐期记录</div>
				<c:if test="${ customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
					<div class="title-click  pop-new-data" layerParams='{"width":"500px","height":"480px","title":"申请帐期",
	               		"link":"./accountperiodapply.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"}'>申请帐期</div>
				</c:if>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="table-smaller">操作人</th>
						<th class="table-smallest">申请日期</th>
						<th class="table-smallest">申请金额</th>
						<th class="table-smallest">申请天数</th>
						<th class="table-smallest">审核状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty billList }">
						<c:forEach items="${billList }" var="bill">
							<tr>
								<td>${bill.creatorNm }</td>
								<td><date:date value="${bill.addTime}" /></td>
								<td><fmt:formatNumber type="number" value="${bill.accountPeriodApply}" pattern="0.00" maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number" value="${bill.accountPeriodDaysApply}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>
									<c:choose>
										<c:when test="${bill.status eq 0}">待审核</c:when>
										<c:when test="${bill.status eq 1}">审核通过</c:when>
										<c:when test="${bill.status eq 2}">审核不通过</c:when>
									</c:choose>
								</td>
								<td>
									<c:if test="${bill.traderType eq 1}">
										<a href="javascript:void(0);" class="addtitle" tabtitle='{"num":"finance_accountperiod_applyAudit_${bill.traderAccountPeriodApplyId}","link":"./finance/accountperiod/getAccountPeriodApply.do?traderAccountPeriodApplyId=${bill.traderAccountPeriodApplyId}","title":"账期申请审核"}'>查看</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty billList }">
						<tr>
							<td colspan="6">查询无结果！</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	<!--
        <iframe src="./getAmountBillPage.do?traderId=${customerInfoByTraderCustomer.traderId}&traderType=1&isEnable=${customerInfoByTraderCustomer.isEnable}" frameborder="0" style="width: 100%;border: 0; " onload="setIframeHeight(this)" scrolling="no"></iframe>

        <span style="display:none;">

			<div class="title-click nobor  pop-new-data" id="popBill"></div>
		</span> -->
        <iframe src="./getCapitalBillPage.do?traderId=${customerInfoByTraderCustomer.traderId}&traderType=1" frameborder="0" style="width: 100%;border: 0; " onload="setIframeHeight(this)" scrolling="no">
         </iframe>
    </div>
</body>
<script type="text/javascript"
		src='<%= basePath %>static/js/customer/finance_and_aptitude.js?rnd=<%=Math.random()%>'></script>
</html>
