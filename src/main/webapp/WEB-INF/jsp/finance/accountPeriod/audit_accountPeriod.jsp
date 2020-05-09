<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="账期申请审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/accountPeriod/audit_accountPeriod.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<!-- ----------------------------客户信息--------------------------------------- -->
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">客户信息</div>
			</div>
			<table class="table ">
				<tbody>
					<c:choose>
						<c:when test="${tapa.traderType eq 1}">
							<tr>
								<td class="wid20">客户名称</td>
								<td>${customer.traderName}</td>
								<td class="wid20">客户身份</td>
								<td>客户</td>
							</tr>
							<tr>
								<td class="wid20">客户性质</td>
								<td>${customer.customerNatureStr}</td>
								<td class="wid20">地区</td>
								<td>${customer.address}</td>
							</tr>
							<tr>
								<td class="wid20">客户等级</td>
								<td>${customer.customerLevelStr}</td>
								<td class="wid20">归属销售</td>
								<td>${customer.ownerSale}</td>
							</tr>
							<tr>
								<td class="wid20">交易次数</td>
								<td>${customer.buyCount}</td>
								<td class="wid20">交易金额</td>
								<td>${customer.buyMoney}</td>
							</tr>
							<tr>
								<td class="wid20">注册时间</td>
								<td>${customer.registeredDateStr}</td>
								<td class="wid20">注册资金</td>
								<td>${customer.registeredCapital}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="wid20">客户名称</td>
								<td>${traderSupplier.traderSupplierName}</td>
								<td class="wid20">客户身份</td>
								<td>供应商</td>
							</tr>
							<tr>
								<td class="wid20">地区</td>
								<td>${traderSupplier.traderSupplierAddress}</td>
								<td class="wid20">供应商等级</td>
								<td>${traderSupplier.gradeStr}</td>
							</tr>
							<tr>
								<td class="wid20">产品归属</td>
								<td>${traderSupplier.ownerSale}</td>
								<td class="wid20">采购次数</td>
								<td>${traderSupplier.buyCount}</td>
							</tr>
							<tr>
								<td class="wid20">采购金额</td>
								<td>${traderSupplier.buyMoney}</td>
								<td class="wid20"></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		
		<!-- ----------------------------资质信息--------------------------------------- -->
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">资质信息</div>
			</div>
			<table class="table ">
				<tbody>
					<tr>
						<td class="wid30">营业执照</td>
						<td class="text-left">
							<c:choose>
								<c:when test="${not empty business and not empty business.uri }">
									<a href="http://${business.domain}${business.uri}" target="_blank">营业执照</a>
								</c:when>
								<c:otherwise>
							   		营业执照
							   </c:otherwise>
							</c:choose>
							&nbsp;&nbsp;&nbsp;&nbsp; 有效期： 
							<date:date value="${business.begintime}" format="yyyy-MM-dd" /> 
							<c:if test="${business ne null && business.endtime eq null}">-无限期</c:if>
							<c:if test="${business.endtime ne null}">-<date:date value="${business.endtime}" format="yyyy-MM-dd" /></c:if>
							&nbsp;&nbsp;&nbsp;&nbsp; 
							<c:if test="${business.endtime ne null && business.endtime lt now }"><span style="color: red">（已过期）</span></c:if> 
							<c:if test="${business.isMedical eq 1}">含有医疗器械</c:if>
						</td>
					</tr>
					<tr>
						<td class="wid30">税务登记证</td>
						<td class="text-left">
							<c:choose> 
							   <c:when test="${not empty tax and not empty tax.uri }">
							   		 <a href="http://${tax.domain}${tax.uri}" target="_blank">税务登记证</a>
							   </c:when>
							   <c:otherwise>
							   		税务登记证
							   </c:otherwise>  
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
                        	有效期：  <date:date value ="${tax.begintime}" format="yyyy-MM-dd"/>
		                        	<c:if test="${tax ne null && tax.endtime eq null}">-无限期</c:if>
			                        <c:if test="${tax.endtime ne null}">-<date:date value ="${tax.endtime}" format="yyyy-MM-dd"/></c:if>
			                        <c:if test="${tax.endtime ne null && tax.endtime lt now }"><span style="color: red">（已过期）</span></c:if>
						</td>
					</tr>
					<tr>
						<td class="wid30">组织机构代码证</td>
						<td class="text-left">
							<c:choose> 
							   <c:when test="${not empty orga and not empty orga.uri}">
							   		 <a href="http://${orga.domain}${orga.uri}" target="_blank">组织机构代码证</a>
							   </c:when>
							   <c:otherwise>
							   		组织机构代码证
							   </c:otherwise>  
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
                       		有效期：<date:date value ="${orga.begintime}" format="yyyy-MM-dd"/>
	                        		<c:if test="${orga ne null && orga.endtime eq null}">-无限期</c:if>
		                        	<c:if test="${orga.endtime ne null}">-<date:date value ="${orga.endtime}" format="yyyy-MM-dd"/></c:if>
		                        	<c:if test="${orga.endtime ne null && orga.endtime lt now }"><span style="color: red">（已过期）</span></c:if> 
						</td>
					</tr>
					<c:if test="${customer.customerNature eq 465}"><!-- 客户分销 -->
						<tr>
							<td class="wid30">二类医疗资质</td>
							<td class="text-left"><c:choose>
									<c:when test="${twoMedicalList ne null}">
										<c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">
											   <c:if test="${st.index == 0}">
											   	<c:set var="twoBeginTime" value="${twoMedical.begintime}"></c:set>
											   	<c:set var="twoEndTime" value="${twoMedical.endtime}"></c:set>
											   	<c:set var="sn" value="${twoMedical.sn}"></c:set>
											   </c:if>
										   	<c:if test="${twoMedical.uri ne null && not empty twoMedical.uri}">
										   		 <a href="http://${twoMedical.domain}${twoMedical.uri}" target="_blank">二类医疗资质 - ${st.index + 1}</a>&nbsp;&nbsp;
										   	</c:if>
								   		</c:forEach>
									</c:when>
									<c:otherwise>
								   		二类医疗资质
								   </c:otherwise>
								</c:choose>&nbsp;&nbsp;&nbsp;&nbsp; 有效期：
									<date:date value="${twoBeginTime} " format="yyyy-MM-dd" /> 
								<c:if test="${twoMedicalList ne null and twoEndTime eq null && not empty twoMedicalList}">-无限期</c:if>
								<c:if test="${twoEndTime ne null}">-<date:date value="${twoEndTime}" format="yyyy-MM-dd" /></c:if>
								&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn} 
								<c:if test="${twoEndTime ne null && twoEndTime lt now }"><span style="color: red">（已过期）</span></c:if>
							</td>
						</tr>
						<tr>
							<td class="wid30">二类医疗资质详情</td>
							<td class="text-left">
<%-- 								<c:if test="${not empty medicalCertificates }"> --%>
<%-- 									<c:forEach items="${medicalCertificates }" var="mc"> --%>
<%-- 										<c:if test="${mc.medicalCategoryId eq 194}">${mc.title}&nbsp;&nbsp;</c:if> --%>
<%-- 										<c:if test="${mc.medicalCategoryLevel eq 239 ||mc.medicalCategoryLevel eq 241}">${mc.title}（二类）&nbsp;&nbsp;</c:if> --%>
<%-- 									</c:forEach> --%>
<%-- 								</c:if> --%>
									<c:if test="${not empty two }">
					               <c:forEach items="${two }" var="mc">
					               	${mc.title}&nbsp;&nbsp;
					               </c:forEach>
					            </c:if>
								</td>
						</tr>
						<tr>
							<td class="wid30">三类医疗资质</td>
							<td class="text-left">
								<c:choose>
									<c:when test="${not empty threeMedical and not empty threeMedical.uri }">
										<a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank">三类医疗资质</a>
									</c:when>
									<c:otherwise>
								   		三类医疗资质
								   </c:otherwise>
								</c:choose>
								&nbsp;&nbsp;&nbsp;&nbsp; 有效期：<date:date value="${threeMedical.begintime} " format="yyyy-MM-dd" /> 
								<c:if test="${threeMedical ne null && threeMedical.endtime eq null}">-无限期</c:if>
								<c:if test="${threeMedical.endtime ne null}">-<date:date value="${threeMedical.endtime} " format="yyyy-MM-dd" /></c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;许可证编号：${threeMedical.sn} <c:if test="${threeMedical.endtime ne null && threeMedical.endtime lt now }">
								<span style="color: red">（已过期）</span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="wid30">三类医疗资质详情</td>
							<td class="text-left">
							<!-- 
								<c:if test="${not empty medicalCertificates }">
									<c:forEach items="${medicalCertificates }" var="mc">
										<c:if test="${mc.medicalCategoryId eq 194}">${mc.title}&nbsp;&nbsp;</c:if>
										<c:if
											test="${mc.medicalCategoryLevel eq 240 || mc.medicalCategoryLevel eq 241}">${mc.title}（三类）&nbsp;&nbsp;</c:if>
									</c:forEach>
								</c:if>
								 -->
								<c:if test="${not empty three }">
					               <c:forEach items="${three }" var="mc">
					               	${mc.title}&nbsp;&nbsp;
					               </c:forEach>
					            </c:if>
							</td>
						</tr>
					</c:if>
					<c:if test="${customer.customerNature eq 466}"><!-- 客户终端 -->
						<tr>
	                        <td class="table-smallest">医疗机构执业许可证</td>
	                        <td style="text-align: left;">
		                        <c:choose> 
								   <c:when test="${practiceList ne null }">
								   <c:forEach items="${practiceList }" var="practice" varStatus="st">
								   <c:if test="${st.index == 0}">
								   	<c:set var="beginTime" value="${practice.begintime}"></c:set>
								   	<c:set var="endTime" value="${practice.endtime}"></c:set>
								   	<c:set var="sn" value="${practice.sn}"></c:set>
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
	                        	有效期：<date:date value ="${beginTime} " format="yyyy-MM-dd"/>
	                        			<c:if test="${practiceList ne null  && endTime eq null && not empty practiceList}">-无限期</c:if>
	                        			<c:if test="${endTime ne null}">
	                        				-<date:date value ="${endTime} " format="yyyy-MM-dd"/>
	                        			</c:if>&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn}
	                        			 <c:if test="${endTime ne null && endTime ne 0 && endTime lt now }"><span style="color: red">（已过期）</span></c:if>
	                        </td>
	                    </tr>
					</c:if>
					
					<c:if test="${traderType eq 2 }"> <!-- 当前人员是供应商的时候显示 -->
						 <tr>
                        <td class="table-smallest">医疗器械二类备案凭证</td>
                        <td class="text-left"><c:choose>
									<c:when test="${twoMedicalList ne null}">
										<c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">
											   <c:if test="${st.index == 0}">
											   	<c:set var="twoBeginTime" value="${twoMedical.begintime}"></c:set>
											   	<c:set var="twoEndTime" value="${twoMedical.endtime}"></c:set>
											   	<c:set var="sn" value="${twoMedical.sn}"></c:set>
											   </c:if>
										   	<c:if test="${twoMedical.uri ne null && not empty twoMedical.uri}">
										   		 <a href="http://${twoMedical.domain}${twoMedical.uri}" target="_blank">医疗器械二类备案凭证 - ${st.index + 1}</a>&nbsp;&nbsp;
										   	</c:if>
								   		</c:forEach>
									</c:when>
									<c:otherwise>
								   		医疗器械二类备案凭证
								   </c:otherwise>
								</c:choose>&nbsp;&nbsp;&nbsp;&nbsp; 有效期：
									<date:date value="${twoBeginTime} " format="yyyy-MM-dd" /> 
								<c:if test="${twoMedicalList ne null and twoEndTime eq null && not empty twoMedicalList}">-无限期</c:if>
								<c:if test="${twoEndTime ne null}">-<date:date value="${twoEndTime}" format="yyyy-MM-dd" /></c:if>
								&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn} 
								<c:if test="${twoEndTime ne null && twoEndTime lt now }"><span style="color: red">（已过期）</span></c:if>
							</td>
                    </tr>
                    <tr>
                        <td class="table-smallest">医疗器械二类备案凭证详情</td>
                        <td style="text-align: left;">
                        	<c:if test="${not empty two }">
				               <c:forEach items="${two }" var="mc">
				               	${mc.title}&nbsp;&nbsp;
				               </c:forEach>
				            </c:if>
                        </td>
                        	
                    </tr>
                    <tr>
                        <td class="table-smallest">三类医疗资质</td>
                        <td style="text-align: left;">
                        	<c:choose> 
							   <c:when test="${threeMedical ne null && threeMedical.uri ne null}">
							   		 <a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank">三类医疗资质</a>
							   </c:when>
							   <c:otherwise>
							   		三类医疗资质
							   </c:otherwise>  
							</c:choose>&nbsp;&nbsp;&nbsp;&nbsp;
                        	有效期：<date:date value ="${threeMedical.begintime} " format="yyyy-MM-dd"/>
                        			<c:if test="${threeMedical ne null && threeMedical.endtime eq null}">-无限期</c:if>
                        			<c:if test="${threeMedical.endtime ne null}">
                        				-<date:date value ="${threeMedical.endtime} " format="yyyy-MM-dd"/>
                        			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;许可证编号：${threeMedical.sn}
                        			 <c:if test="${threeMedical.endtime ne null && threeMedical.endtime ne 0 && threeMedical.endtime lt now }"><span style="color: red">（已过期）</span></c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smallest">三类医疗资质详情</td>
                        <td style="text-align: left;">
                        	<c:if test="${not empty three }">
				               <c:forEach items="${three }" var="mc">
				               	${mc.title}&nbsp;&nbsp;
				               </c:forEach>
				            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smallest">医疗器械生产许可证</td>
                        <td style="text-align: left;">
                        	<c:choose> 
							   <c:when test="${product ne null && product.uri ne null}">
							   		 <a href="http://${product.domain}${product.uri}" target="_blank">医疗器械生产许可证</a>
							   </c:when>
							   <c:otherwise>
							   		医疗器械生产许可证
							   </c:otherwise>  
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
                       		有效期：<date:date value ="${product.begintime}" format="yyyy-MM-dd"/>
	                        		<c:if test="${product ne null && product.endtime eq null}">-无限期</c:if>
		                        	<c:if test="${product.endtime ne null}">-<date:date value ="${product.endtime}" format="yyyy-MM-dd"/></c:if>
		                        	<c:if test="${product.endtime ne null && product.endtime ne 0 && product.endtime lt now }"><span style="color: red">（已过期）</span></c:if> 
		                        
                        </td>
                         <tr>
	                        <td class="table-smallest">医疗器械经营许可证</td>
	                        <td style="text-align: left;">
	                        	<c:choose> 
								   <c:when test="${operate ne null && operate.uri ne null}">
								   		 <a href="http://${operate.domain}${operate.uri}" target="_blank">医疗器械经营许可证</a>
								   </c:when>
								   <c:otherwise>
								   		医疗器械生产许可证
								   </c:otherwise>  
								</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
	                       		有效期：<date:date value ="${operate.begintime}" format="yyyy-MM-dd"/>
		                        		<c:if test="${operate ne null && operate.endtime eq null}">-无限期</c:if>
			                        	<c:if test="${operate.endtime ne null}">-<date:date value ="${operate.endtime}" format="yyyy-MM-dd"/></c:if>
			                        	<c:if test="${operate.endtime ne null && operate.endtime ne 0 && operate.endtime lt now }"><span style="color: red">（已过期）</span></c:if> 
			                        
	                        </td>
	                    </tr>
	                    <!-- begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 -->
	                    <tr>
	                    	<td class="table-smallest">销售人员授权书</td>
	                    	<td style="text-align: left;">
	                    		<c:choose> 
								   <c:when test="${saleAuth ne null && saleAuth.uri ne null}">
								   		 <a href="http://${saleAuth.domain}${saleAuth.uri}" target="_blank">销售人员授权书</a>
								   </c:when>
							   	<c:otherwise>
							   		销售人员授权书
							   	</c:otherwise>
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;有效期： <date:date value ="${saleAuth.begintime}" format="yyyy-MM-dd"/>-<date:date value ="${saleAuth.endtime}" format="yyyy-MM-dd"/>
	                    	</td>
	                    </tr>
	                    <tr>
	                    	<td class="table-smallest">授权销售人信息</td>
	                    	<td style="text-align: left;">
	                    		职位:&nbsp;<span>${saleAuth.authPost}</span>&nbsp;&nbsp;, 姓名:&nbsp;<span>${saleAuth.authUserName}</span>&nbsp;&nbsp;, 联系方式:&nbsp;<span>${saleAuth.authContactInfo}</span>
	                    	</td>
	                    </tr>
	                    <tr>
                        <td class="table-smallest">品牌授权书 </td>
                        <td style="text-align: left;">
                        	<c:choose> 
							   <c:when test="${brandBookList ne null }">
							   <c:forEach items="${brandBookList }" var="brandBook" varStatus="st">
							   <c:if test="${st.index == 0}">
							   	<c:set var="brandBeginTime" value="${brandBook.begintime}"></c:set>
							   	<c:set var="brandEndTime" value="${brandBook.endtime}"></c:set>
							   </c:if>
							   	<c:if test="${brandBook.uri ne null && not empty brandBook.uri}">
							   		 <a href="http://${brandBook.domain}${brandBook.uri}" target="_blank">品牌授权书 - ${st.index + 1}</a>&nbsp;&nbsp;
							   	</c:if>
							   </c:forEach>
							   </c:when>
							   <c:otherwise>
							   		品牌授权书&nbsp;
							   </c:otherwise>  
							</c:choose>&nbsp;&nbsp;&nbsp;
                        	有效期：<date:date value ="${brandBeginTime} " format="yyyy-MM-dd"/>
                        			<c:if test="${brandBookList ne null and brandEndTime eq null and not empty brandBookList}">-无限期</c:if>
                        			<c:if test="${brandEndTime ne null}">
                        				-<date:date value ="${brandEndTime} " format="yyyy-MM-dd"/>
                        			</c:if>&nbsp;
                        			 <c:if test="${brandEndTime ne null && brandEndTime ne 0 && brandEndTime lt now }"><span style="color: red">（已过期）</span></c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smallest">其他 </td>
                        <td style="text-align: left;">
                        	<c:choose> 
							   <c:when test="${otherList ne null }">
							   <c:forEach items="${otherList }" var="other" varStatus="st">
							   	<c:if test="${other.uri ne null && not empty other.uri}">
							   		 <a href="http://${other.domain}${other.uri}" target="_blank">其他资格证书 - ${st.index + 1}</a>&nbsp;&nbsp;
							   	</c:if>
							   </c:forEach>
							   </c:when>
							   <c:otherwise>
							   		其他资格证书&nbsp;
							   </c:otherwise>  
							</c:choose>
                        </td>
                    </tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<!-- ----------------------------申请信息--------------------------------------- -->
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">申请信息</div>
			</div>
			<table class="table ">
				<tbody>
					<tr>
						<td class="wid20">当前帐期额度</td>
						<td>${tapa.accountPeriodNow}</td>
						<td class="wid20">当前帐期天数</td>
						<td>${tapa.accountPeriodDaysNow}</td>
					</tr>
					<tr>
						<td class="wid20">当前剩余额度</td>
						<td>${tapa.accountPeriodLeft}</td>
						<td class="wid20">帐期使用次数</td>
						<td>${tapa.usedTimes}</td>
					</tr>
					<tr>
						<td class="wid20">逾期次数</td>
						<td class="warning-color1">${tapa.overdueTimes}</td>
						<td class="wid20">逾期总额</td>
						<td class="warning-color1">${tapa.overdueAmount}</td>
					</tr>
					<tr>
						<td class="wid20 warning-color1">本次申请金额</td>
						<td class="warning-color1">${tapa.accountPeriodApply}</td>
						<td class="wid20 warning-color1">本次申请天数</td>
						<td class="warning-color1">${tapa.accountPeriodDaysApply}</td>
					</tr>
					<c:if test="${tapa.traderType eq 1}">
						<tr>
							<td class="wid20">本次交易订单</td>
							<td>
								<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewinvoicesaleorder${tapa.saleorderId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${tapa.saleorderId}","title":"订单信息"}'>${tapa.saleorderNo}</a>
							</td>
							<td class="wid20">预期利润</td>
							<td>${tapa.predictProfit}</td>
						</tr>
					</c:if>
					<tr>
						<td class="wid20">申请原因</td>
						<td colspan="3" class="text-left">${tapa.comments}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- ----------------------------------审核操作-------------------------------------------- -->
		<div class="parts">
			<%-- <form action="./auditAccountPeriod.do" id="auditAccountPeriodForm">
				<input type="hidden" name="traderAccountPeriodApplyId" id="traderAccountPeriodApplyId" value="${tapa.traderAccountPeriodApplyId}"/>
			</form> --%>
			<c:if test="${tapa.status != 2}"><!-- 待审核 -->
				<div class="table-buttons">
					
					<c:choose>
						<c:when test="${tapa.status != 2}">
						<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
							<c:choose>
								<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
								<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&traderAccountPeriodApplyId=${tapa.traderAccountPeriodApplyId}"}'>审核通过</button>
								<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&traderAccountPeriodApplyId=${tapa.traderAccountPeriodApplyId}"}'>审核不通过</button>
								</c:when>
								<c:otherwise>
		        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
								</c:otherwise>
							</c:choose>
						</c:if>
						
						</c:when>
						<c:otherwise>
						
						</c:otherwise>
					</c:choose>					
				</div>
			</c:if>
		</div>
		<!-- ----------------------------------------------------------------------------------- -->
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">审核记录</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>操作人</th>
						<th>操作时间</th>
						<th class="wid80">操作事项</th>
						<th>备注</th>
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
				</tbody>
			</table>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>