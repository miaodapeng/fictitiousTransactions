<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单及基本信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/view.js?rnd=<%=Math.random()%>'></script>
    <div class="content">
    	<div class="customer">
	        <ul>
	            <li>
	                <a class="customer-color" href="${pageContext.request.contextPath}/order/saleorder/viewNew.do?saleorderId=${saleorder.saleorderId }&companyId=${saleorder.companyId }">订单及基本信息</a>
	            </li>
	            <li>
	                <a href="${pageContext.request.contextPath}/order/saleorder/getSaleAndExpress.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">交易及物流信息</a>
	            </li>
	            <li>
	                <a href="${pageContext.request.contextPath}/order/saleorder/getContactAndCheckInfo.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">沟通及审核信息</a>
	            </li>
	            <li>
	                <a href="${pageContext.request.contextPath}/order/saleorder/getAttachmentAndInstance.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">合同及售后</a>
	            </li>
	        </ul>
    	</div>
     	<div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">基本信息</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">订单号</td>
                        <td>${saleorder.saleorderNo}</td>
                        <td class="table-smaller">订单状态</td> 
                        <td>
                            <c:choose>
								<c:when test="${saleorder.status eq 0}">待确认</c:when>
								<c:when test="${saleorder.status eq 1}">进行中</c:when>
								<c:when test="${saleorder.status eq 2}">已完结</c:when>
								<c:when test="${saleorder.status eq 3}">已关闭</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>创建者</td>
                        <td>${saleorder.creatorName}</td>
                        <td>创建时间</td>
                        <td><date:date value ="${saleorder.addTime}"/></td>
                    </tr>
                    <tr>
                        <td>销售部门</td>
                        <td>${saleorder.salesDeptName}</td>
                        <td>归属销售</td>
                        <td>${saleorder.optUserName}</td>
                    </tr>
                    <tr>
                        <td>生效状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.validStatus eq 0}">未生效</c:when>
								<c:when test="${saleorder.validStatus eq 1}">已生效</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>生效时间</td>
                        <td>
                        	<c:if test="${saleorder.validTime > 0}">
	                        <date:date value ="${saleorder.validTime}"/>
	                        </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>审核状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${saleorder.verifyStatus == null}">待审核</c:when>
								<c:when test="${saleorder.verifyStatus eq 0}">审核中</c:when>
								<c:when test="${saleorder.verifyStatus eq 1}">审核通过</c:when>
								<c:when test="${saleorder.verifyStatus eq 2}">审核不通过</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>锁定状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.lockedStatus eq 0}">未锁定</c:when>
								<c:when test="${saleorder.lockedStatus eq 1}">已锁定(<span class="font-red">${saleorder.lockedReason}</span>)</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>商机编号</td>
                        <td>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${saleorder.bussinessChanceId}","link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${saleorder.bussinessChanceId}","title":"商机信息"}'>${saleorder.bussinessChanceNo}</a>
                        </td>
                        <td>报价单号</td>
                        <td>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewquoteorder${saleorder.quoteorderId}","link":"./order/quote/getQuoteDetail.do?quoteorderId=${saleorder.quoteorderId}&viewType=3","title":"报价信息"}'>${saleorder.quoteorderNo}</a>
                        </td>
                    </tr>
                    <tr>
                        <td>收款状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${saleorder.paymentStatus eq 0}">未收款</c:when>
								<c:when test="${saleorder.paymentStatus eq 1}">部分收款</c:when>
								<c:when test="${saleorder.paymentStatus eq 2}">全部收款</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>开票状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.invoiceStatus eq 0}">未开票</c:when>
								<c:when test="${saleorder.invoiceStatus eq 1}">部分开票</c:when>
								<c:when test="${saleorder.invoiceStatus eq 2}">全部开票</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>发货状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${saleorder.deliveryStatus eq 0}">未发货</c:when>
								<c:when test="${saleorder.deliveryStatus eq 1}">部分发货</c:when>
								<c:when test="${saleorder.deliveryStatus eq 2}">全部发货</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>到货状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.arrivalStatus eq 0}">未到货</c:when>
								<c:when test="${saleorder.arrivalStatus eq 1}">部分到货</c:when>
								<c:when test="${saleorder.arrivalStatus eq 2}">全部到货</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>计入业绩</td>
                        <td id="perf_flag">
	                        <c:choose>
								<c:when test="${saleorder.isSalesPerformance eq 1}">已计入</c:when>
								<c:otherwise>未计入</c:otherwise>
							</c:choose>
                        </td>
                        <td>计入业绩时间</td>
                        <td id="perf_time">
	                        <c:choose>
								<c:when test="${saleorder.isSalesPerformance eq 1}"><date:date value ="${saleorder.salesPerformanceTime}"/></c:when>
								<c:otherwise>--</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    客户信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">客户名称</td>
                        <td>
                            <div class="customername pos_rel">
                                <span class="font-blue mr4">
                                <a class="addtitle" href="javascript:void(0);" 
										tabTitle='{"num":"viewcustomer${customer.traderCustomerId}",
										"link":"./trader/customer/baseinfo.do?traderCustomerId=${customer.traderCustomerId}&traderId=${saleorder.traderId}",
										"title":"客户信息"}'>
                            ${saleorder.traderName}</a>
                                </span>
                                <i class="iconbluemouth"></i>
                                <div class="pos_abs customernameshow mouthControlPos">
                                    报价次数：${customer.quoteCount} <br>
                                    交易次数：${customer.buyCount} <br>
                                    交易金额：${customer.buyMoney} <br>
                                    上次交易日期：<date:date value="${customer.lastBussinessTime}" format="yyyy-MM-dd" /> <br>
                                    归属销售：${customer.ownerSale}
                                </div>
                            </div>
                        </td>
                        <td class="table-smaller">地区</td>
                        <td>${saleorder.traderArea}</td>
                    </tr>
                    <tr>
                        <td>客户类型</td>
                        <td>${saleorder.customerTypeStr}</td>
                        <td>客户性质</td>
                        <td>${saleorder.customerNatureStr}</td>
                    </tr>
                    <tr>
                        <td>联系人</td>
                        <td>${saleorder.traderContactName}</td>
                        <td>电话</td>
                        <td>
                        <c:if test="${not empty saleorder.traderContactTelephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.traderContactTelephone}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.traderContactId});"></i>
                       </c:if>
                        ${saleorder.traderContactTelephone}
                        </td>
                    </tr>
                    <tr>
                        <td>手机</td>
                        <td>
                        <c:if test="${not empty saleorder.traderContactMobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.traderContactMobile}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.traderContactId});"></i>
                       </c:if>
                        ${saleorder.traderContactMobile}
                        </td>
                        <td>注册帐号</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>联系地址</td>
                        <td colspan="3">${saleorder.traderAddress}</td>
                    </tr>
                    <tr>
                        <td>客户备注</td>
                        <td colspan="3">${saleorder.traderComments}</td>
                    </tr>
                    <tr>
						<td class="wid30">营业执照</td>
						<td class="text-left" colspan="3">
							<c:choose>
								<c:when test="${business ne null && business.uri ne null}">
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
						<td class="text-left" colspan="3">
							<c:choose> 
							   <c:when test="${tax ne null && tax.uri ne null}">
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
						<td class="text-left" colspan="3">
							<c:choose> 
							   <c:when test="${orga ne null && orga.uri ne null}">
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
					<c:if test="${customerProperty eq 465}"><!-- 分销 -->
						<tr>
							<td class="wid30">医疗器械二类备案凭证</td>
							<td class="text-left" colspan="3">
								<c:choose>
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
							<td class="wid30">二类医疗资质详情</td>
							<td class="text-left" colspan="3">
								<c:if test="${not empty medicalCertificates }">
									<c:forEach items="${medicalCertificates }" var="mc">
										<c:if test="${mc.medicalCategoryId eq 194}">${mc.title}&nbsp;&nbsp;</c:if>
										<c:if test="${mc.medicalCategoryLevel eq 239 ||mc.medicalCategoryLevel eq 241}">${mc.title}（二类）&nbsp;&nbsp;</c:if>
									</c:forEach>
								</c:if></td>
						</tr>
						<tr>
							<td class="wid30">三类医疗资质</td>
							<td class="text-left" colspan="3">
								<c:choose>
									<c:when test="${threeMedical ne null && threeMedical.uri ne null}">
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
							<td class="text-left" colspan="3">
								<c:if test="${not empty medicalCertificates }">
									<c:forEach items="${medicalCertificates }" var="mc">
										<c:if test="${mc.medicalCategoryId eq 194}">${mc.title}&nbsp;&nbsp;</c:if>
										<c:if
											test="${mc.medicalCategoryLevel eq 240 || mc.medicalCategoryLevel eq 241}">${mc.title}（三类）&nbsp;&nbsp;</c:if>
									</c:forEach>
								</c:if>
							</td>
						</tr>
					</c:if>
					<c:if test="${customerProperty eq 466}"><!-- 终端 -->
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
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    收货信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收货客户</td>
                        <td>${saleorder.takeTraderName}</td>
                        <td class="table-smaller">收货联系人</td>
                        <td>${saleorder.takeTraderContactName}</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        <c:if test="${not empty saleorder.takeTraderContactTelephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.takeTraderContactTelephone}',${saleorder.takeTraderId},1,2,${saleorder.saleorderId},${saleorder.takeTraderContactId});"></i>
                        </c:if>
                        ${saleorder.takeTraderContactTelephone}
                        </td>
                        <td>手机</td>
                        <td>
                        <c:if test="${not empty saleorder.takeTraderContactMobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.takeTraderContactMobile}',${saleorder.takeTraderId},1,2,${saleorder.saleorderId},${saleorder.takeTraderContactId});"></i>
                        </c:if>
                        ${saleorder.takeTraderContactMobile}
                        </td>
                    </tr>
                    <tr>
                        <td>收货地区</td>
                        <td>${saleorder.takeTraderArea}</td>
                        <td>发货方式</td>
                        <td>
                        	<c:forEach var="list" items="${deliveryTypes}">
		                    	<c:if test="${saleorder.deliveryType == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>收货地址</td>
                        <td colspan="3">${saleorder.takeTraderAddress}</td>
                    </tr>
                    <tr>
                        <td>指定物流公司</td>
                        <td>
                        	<c:forEach var="list" items="${logisticsList}">
		                    	<c:if test="${saleorder.logisticsId == list.logisticsId}">${list.name}</c:if>
		                    </c:forEach>
                        </td>
                        <td>运费说明</td>
                        <td>
                        	<c:forEach var="list" items="${freightDescriptions}">
		                    	<c:if test="${saleorder.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>物流备注</td>
                        <td colspan="3">${saleorder.logisticsComments}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    收票信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收票客户</td>
                        <td>${saleorder.invoiceTraderName}</td>
                        <td class="table-smaller">收票联系人</td>
                        <td>${saleorder.invoiceTraderContactName}</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        <c:if test="${not empty saleorder.invoiceTraderContactTelephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.invoiceTraderContactTelephone}',${saleorder.invoiceTraderId},1,2,${saleorder.saleorderId},${saleorder.invoiceTraderContactId});"></i>
                        </c:if>
                        ${saleorder.invoiceTraderContactTelephone}
                        </td>
                        <td>手机</td>
                        <td>
                        <c:if test="${not empty saleorder.invoiceTraderContactMobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.invoiceTraderContactMobile}',${saleorder.invoiceTraderId},1,2,${saleorder.saleorderId},${saleorder.invoiceTraderContactId});"></i>
                        </c:if>
                        ${saleorder.invoiceTraderContactMobile}
                        </td>
                    </tr>
                    <tr>
                        <td>收票地区</td>
                        <td>${saleorder.invoiceTraderArea}</td>
                        <td>发票类型</td>
                        <td>
                        	<c:forEach var="list" items="${invoiceTypes}">
		                    	<c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
		                    <c:choose>
		                    	<c:when test="${saleorder.isSendInvoice eq 0}">（不寄送）</c:when>
		                    	<c:otherwise>（寄送）</c:otherwise>
		                    </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>开票方式</td>
                        <td>
                        	<c:choose>
		                    	<c:when test="${saleorder.invoiceMethod eq 1}">手动纸质开票</c:when>
		                    	<c:when test="${saleorder.invoiceMethod eq 2}">自动纸质开票</c:when>
		                    	<c:when test="${saleorder.invoiceMethod eq 3}">自动电子发票</c:when>
		                    </c:choose>
                        </td>
                        <td>延缓开票</td>
                        <td>
		                    <c:choose>
		                    	<c:when test="${saleorder.isDelayInvoice eq 1}">是</c:when>
		                    	<c:otherwise>否</c:otherwise>
		                    </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>收票地址</td>
                        <td colspan="3">${saleorder.invoiceTraderAddress}</td>
                    </tr>
                    <tr>
                        <td>开票备注</td>
                        <td colspan="3">${saleorder.invoiceComments}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts ">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    	终端信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td>终端名称</td>
                        <td>${saleorder.terminalTraderName}</td>
                        <td>终端类型</td>
                        <td>${saleorder.terminalTraderTypeStr}</td>
                    </tr>
                    <tr>
                        <td class="table-smaller">销售区域</td>
                        <td>${saleorder.salesArea}</td>
                        <td class="table-smaller"></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">产品信息</div>
            </div>
            <c:set var="referenceCostPrice" value="0"></c:set>
           	<shiro:hasPermission name="/order/saleorder/referenceCostPrice.do">
           		<c:set var="referenceCostPrice" value="1"></c:set>
        	</shiro:hasPermission>
            <table class="table  table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th class="wid15">产品名称</th>
                        <th class="wid5">品牌</th>
                   
                        <th class="wid8">型号</th>
                        <th class="wid6">单价</th>
                        <c:if test="${referenceCostPrice eq 1 }">
							<th class="wid6">参考成本</th>
						</c:if>
                        <th class="wid5">数量</th>
                        <th class="wid4">单位</th>
                        <th class="wid6">总额</th>
                        <th class="wid4">货期</th>
                        <th class="wid4">直发</th>
                        <th  class="wid10">核价参考</th>
                        <th class="wid7">占用/库存</th>
                        <th >含安调</th>
                        <th>类别管制</th>
                        <th>产品备注</th>
                        <th>内部备注</th>
                        <th>采购状态</th>
                        <th>到库状态</th>
                        <th>发货状态</th>
                        <th>收货状态</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="num" value="0"></c:set>
					<c:set var="totleMoney" value="0.00"></c:set>
					<c:set var="isNotDelPriceZero" value="0"></c:set>
					<c:set var="isUrgent" value="0"></c:set>
					<c:set var="isCold" value="0"></c:set>
					
                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
                		<c:if test="${list.isDelete eq 0}">
							<c:set var="num" value="${num + list.num}"></c:set>
							<c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set>
							<c:if test="${list.price == '0.00'}">
								<c:set var="isNotDelPriceZero" value="1"></c:set>
							</c:if>
						</c:if>
						<c:if test="${list.goodsId == '251526'}">
								<c:set var="isUrgent" value="1"></c:set>
						</c:if>
						<c:if test="${list.goodsId == '256675'}">
								<c:set var="isCold" value="1"></c:set>
						</c:if>
						<!-- 判断该商品是不是归属于当前登陆人 -->
						<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
							<c:set var="shenhe" value="0"></c:set>
							<c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
								<c:if test="${verifyUsernameInfo == curr_user.username}">
									<c:set var="shenhe" value="1"></c:set>
								</c:if>
							</c:forEach>
								<c:if test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name == '产品线归属人审核'}">
									<c:choose>
									<c:when test="${fn:contains(list.goodsUserIdStr, loginUserId)}">
										<c:set var="goodsCategoryUser" value="y"></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="goodsCategoryUser" value="n"></c:set>
									</c:otherwise>
									</c:choose>
								</c:if>
						</c:if>
	                    <tr <c:if test="${list.isDelete eq 1 or goodsCategoryUser eq 'n'}">class="caozuo-grey"</c:if>>
	                        <td>${staut.count}</td>
	                        <td class="text-left">
	                            <div class="customername pos_rel">
	                                <c:choose>
										<c:when test="${list.isDelete eq 1}">
											<span>${list.goodsName}<br/></span>
			                                <span>${list.sku} <br>${list.goods.materialCode}</span>
										</c:when>
										<c:otherwise>
											<span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
			                                <span>${list.sku} <br>${list.goods.materialCode}</span>
			                                <div class="pos_abs customernameshow">
											物料编码：${list.goods.materialCode} <br> 
											注册证号：${list.goods.registrationNumber} <br>
											管理类别：${list.manageCategoryName} <br>
											产品负责人：<c:if test="${not empty list.goods.userList }">
														<c:forEach items="${list.goods.userList }" var="user" varStatus="st">
															${user.username } 
														<c:if test="${st.count != list.goods.userList.size() }">、</c:if>
														</c:forEach>
													</c:if> <br> 
											采购提醒：${list.goods.purchaseRemind} <br>
											包装清单：${list.goods.packingList} <br> 
											服务条款：${list.goods.tos} <br>
											库存：<span id="kc_${list.saleorderGoodsId}">${list.goods.stockNum}</span> <br>
											可用库存：<span id="kykc_${list.saleorderGoodsId}">${(list.goods.stockNum-list.goods.orderOccupy) < 0 ? 0 : (list.goods.stockNum-list.goods.orderOccupy)}</span><br>
											订单占用：<span id="dzzy_${list.saleorderGoodsId}">${list.goods.orderOccupy}</span><br> 
											可调剂：<span id="ktj_${list.saleorderGoodsId}">${list.goods.adjustableNum}</span> <br> 
											审核状态：<c:choose>
											 	<c:when test="${list.goods.verifyStatus eq 0}">审核中</c:when>
											 	<c:when test="${list.goods.verifyStatus eq 1}">审核通过</c:when>
											 	<c:when test="${list.goods.verifyStatus eq 2}">审核不通过</c:when>
											 	<c:otherwise>待审核</c:otherwise>
											 </c:choose>
											</div>
										</c:otherwise>
									</c:choose>
	                            </div>
	                        </td>
	                        <td>${list.brandName}</td>
	                        <td>${list.model}</td>
	                        <td>${list.price}</td>
	                         <c:if test="${referenceCostPrice eq 1 }">
	                        	<c:choose>
	                        		<c:when test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
	                        				<c:set var="shenhe" value="0"></c:set>
											<c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
											<c:if test="${verifyUsernameInfo == curr_user.username}">
												<c:set var="shenhe" value="1"></c:set>
											</c:if>
											</c:forEach>
											<c:choose>
												<c:when test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name == '产品线归属人审核'}">
												<td>
												<input type="text" name="referenceCostPrice_${staut.count}" value="${list.referenceCostPrice == null?'0.00':list.referenceCostPrice}" alt="${list.referenceCostPrice == null?'0.00':list.referenceCostPrice}" goodsCategoryUser="${goodsCategoryUser}">
				                       		 	<input type="hidden" name="saleorderGoodsId" value="${list.saleorderGoodsId}" />
				                       		 	</td>
												</c:when>
												<c:otherwise>
												<td>${list.referenceCostPrice}</td>
	                        					</c:otherwise>
											</c:choose>
	                        		</c:when>
	                        		<c:otherwise>
			                        	<td>${list.referenceCostPrice}</td>
	                        		</c:otherwise>
	                        	</c:choose>
                       		 </c:if>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${list.afterReturnNum=='' ? 0 : list.afterReturnNum eq 0}">
				                        ${list.num}
	                        		</c:when>
	                        		<c:otherwise>
			                        	<div class="customername pos_rel">
				                        	<span>
				                        		${list.num - list.afterReturnNum}
				                        		<i class="iconredsigh ml4 contorlIcon"></i>
				                        	</span>
				                        	<div class="pos_abs customernameshow">原值：${list.num}</div>
			                        	</div>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </td>
	                        <td>${list.unitName}</td>
	                        <td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>${list.deliveryCycle}</td>
	                        <td>
	                            <div class="customername pos_rel">
	                                <span>
	                                <c:choose>
										<c:when test="${list.deliveryDirect eq 0}">否</c:when>
										<c:otherwise>
										是
										<i class="iconbluesigh ml4 contorlIcon"></i></span>
		                                <div class="pos_abs customernameshow">直发原因：${list.deliveryDirectComments}</div>
										</c:otherwise>
									</c:choose>
	                            </div>
	                        </td>
	                        <td>
	                        	<div class="customername pos_rel">
	                        		核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
									参考价格：${list.referencePrice} <br/>
									参考货期：${list.referenceDeliveryCycle} <br/>
									 <c:if test="${referenceCostPrice eq 1 }">
										结算价：${list.settlePrice} <br/>
									</c:if>
									<c:choose>
										<c:when test="${list.reportStatus eq 2}">
											报备成功<i class="iconbluesigh ml4"></i>
										</c:when>
										<c:when test="${list.reportStatus eq 3}">
											报备失败<i class="iconredsigh ml4"></i>
										</c:when>
										<c:otherwise>
											<i class="iconbluesigh ml4"></i>
										</c:otherwise>
									</c:choose>
									<div class="pos_abs customernameshow">
										<c:set var="goodsUserNm" value=""/>
										<c:forEach var="user" items="${userList}">
											<c:if test="${user.userId eq list.lastReferenceUser}"><c:set var="goodsUserNm" value="${user.username}"/></c:if>
										</c:forEach>
										核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
										客户上次购买价格：<fmt:formatNumber type="number" value="${list.lastOrderPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
										参考价格（${goodsUserNm}）：${list.referencePrice} <br/>
										期货交货期：${list.channelDeliveryCycle} <br> 
										现货交货期：${list.delivery} <br>
										参考货期（${goodsUserNm}）：${list.referenceDeliveryCycle} <br/>
										报备结果：${list.reportStatus}
											<c:if test="${list.reportStatus eq 2}">
												成功 <br/>
												报备失败原因：${list.reportComments}
											</c:if>
											<c:if test="${list.reportStatus eq 3}">
												失败 <br/>
												报备失败原因：${list.reportComments}
											</c:if>
									</div>
								</div>
	                        </td>
	                        <td><span id="orderOccupy_stockNum_${list.saleorderGoodsId}">${list.goods.orderOccupy}/${list.goods.stockNum}</span></td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.haveInstallation eq 0}">否</c:when>
									<c:otherwise>是</c:otherwise>
								</c:choose>
	                        </td>
	                        <td>---</td>
	                        <td>${list.goodsComments}</td>
	                        <td>${list.insideComments}</td>
	                        <td><span id="cgztStr_${list.saleorderGoodsId}"></span></td>
	                        <td><span id="dkztStr_${list.saleorderGoodsId}"></span></td>
	                        <td>
	                        		<c:choose>
		                        		<c:when test="${list.deliveryStatus eq 0}">未发货</c:when>
		                        		<c:when test="${list.deliveryStatus eq 1}"><span style="color:green;">部分发货</span></c:when>
		                        		<c:when test="${list.deliveryStatus eq 2}"><span style="color:green;">已发货</span></c:when>
		                        	</c:choose>
	                        </td>
	                         <td>
		                        	<c:choose>
		                        		<c:when test="${list.arrivalStatus eq 0}">未收货</c:when>
		                        		<c:when test="${list.arrivalStatus eq 1}"><span style="color:green;">部分收货</span></c:when>
		                        		<c:when test="${list.arrivalStatus eq 2}"><span style="color:green;">已收货</span></c:when>
		                        	</c:choose>
	                        </td>
	                    </tr>
                    </c:forEach>
                    <tr style="background: #eaf2fd;">
                    	<c:choose>
	                    	<c:when test="${referenceCostPrice ne 1}">
	                    		 	<td colspan="20" class="text-left">
	                        </c:when>
	                        <c:otherwise>
	                        		<td colspan="21" class="text-left">
							</c:otherwise>
                        </c:choose>
                        	<input type="hidden" value="${isNotDelPriceZero}" id="isNotDelPriceZero">
                        	总件数 <span class="font-red">${num}</span>， 总金额
                            <span class="font-red"><fmt:formatNumber type="number" value="${totleMoney}" pattern="0.00" maxFractionDigits="2" /></span>
                            <shiro:hasPermission name="/order/saleorder/showCostAmountAndRate.do">
								，（五行）手填总成本 <span class="font-red"><fmt:formatNumber type="number" value="${totalReferenceCostPrice==null||totalReferenceCostPrice=='' ? 0:totalReferenceCostPrice }" pattern="0.00" maxFractionDigits="2" /></span>
                        	，（五行）毛利率 <span class="font-red">
                        	<c:choose>
                        		<c:when test="${totleMoney <= 0.00}">-100%</c:when>
                        		<c:otherwise>
	                        	<fmt:formatNumber type="number" value="${(totleMoney - totalReferenceCostPrice ) / totleMoney * 100}" pattern="0.00" maxFractionDigits="2" />%
                        		</c:otherwise>
                        	</c:choose>
                        	</span>
							</shiro:hasPermission>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    付款计划
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th style="width:150px">计划</th>
                        <th style="width:150px">计划内容</th>
                        <th style="width:150px">支付金额</th>
                        <th>备注</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${saleorder.paymentType eq 419}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${saleorder.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq saleorder.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if
						test="${saleorder.paymentType ne 419 and saleorder.paymentType ne 424}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${saleorder.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq saleorder.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>账期付款</td>
							<td>${saleorder.accountPeriodAmount}</td>
							<td>到货后${customer.periodDay}天内支付
								 <c:if test="${saleorder.logisticsCollection eq 1}">
		                        	 / 物流代收
	                        	</c:if>
							</td>
						</tr>
					</c:if>
					<c:if test="${saleorder.paymentType eq 424}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${saleorder.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq saleorder.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>账期付款</td>
							<td>${saleorder.accountPeriodAmount}</td>
							<td>到货后${customer.periodDay}天内支付 
								<c:if test="${saleorder.logisticsCollection eq 1}">
		                        	 / 物流代收
	                        	</c:if>
							</td>
						</tr>
						<tr>
							<td>第三期</td>
							<td>尾款</td>
							<td>${saleorder.retainageAmount}</td>
							<td>到货后${saleorder.retainageAmountMonth}个月内支付</td>
						</tr>
					</c:if>
					<tr>
						<td>收款备注</td>
						<td colspan="3">${saleorder.paymentComments}</td>
					</tr>
                    <!-- tr style="background: #eaf2fd;">
                        <td colspan="4" class="text-left">账期付款：账期付款是我司向客户提供的信用付款方式，您需要在约定时间内支付账期额度的金额。本合同中账期以合同开始发货为结算开始时间。</td>
                    </tr-->
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
					其他信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">附加条款</td>
                        <td colspan="3" class="text-left">${saleorder.additionalClause}</td>
                    </tr>
                    <tr>
                        <td class="table-smaller">内部备注</td>
                        <td colspan="3" class="text-left">${saleorder.comments}</td>
                    </tr>
                    <c:if test="${saleorder.orderType ==3}">
                    <tr>
                        <td class="table-smaller">订货备注</td>
                        <td colspan="3" class="text-left">
                        <c:if test="${isUrgent == 1}">
                        	加急&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${isCold == 1}">
                        	使用冷链&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${saleorder.freightDescription == 474}">
                        	快递到付&nbsp;&nbsp;
                        </c:if>
                        </td>
                    </tr>
                    </c:if>
                    <!-- tr>
                        <td class="table-smaller">审核项</td>
                        <td colspan="3" class="text-left">
                            <span class="font-red">---类别管制、价格超限---</span>
                        </td>
                    </tr-->
                </tbody>
            </table>
        </div>
        <div class="tcenter mt-5 mb15">
        	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
        	<c:if test="${saleorder.status != 3 &&  saleorder.status != 2}">
        		<c:if test="${saleorder.validStatus eq 0}">
        			<c:if test="${(null==taskInfo and null == taskInfo.getProcessInstanceId() and endStatus != '审核完成')or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}">
        				<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" 
						tabTitle='{"num":"order_saleorder_edit<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"order/saleorder/edit.do?saleorderId=${saleorder.saleorderId}","title":"编辑订单"}'>编辑订单</button>
        			</c:if>
        		</c:if>
        	</c:if>
        	<c:if test="${((saleorder.status == 0 or saleorder.status == 1) and saleorder.invoiceStatus == 0 and saleorder.deliveryStatus == 0 and saleorder.paymentStatus == 0 and saleorder.lockedStatus eq 0) || (saleorder.isCloseSale eq 1)}">
            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="closeSaleorder(${saleorder.saleorderId})">关闭订单</button>
            </c:if>
       </div>
    </div>
<script type="text/javascript">
$(function()
	{
		var saleorderId = ${saleorder.saleorderId };
		
		var	url = page_url + '/order/saleorder/viewNew.do?saleorderId='+saleorderId;
		if($(window.frameElement).attr('src').indexOf("saleorder/viewNew")<0){
			$(window.frameElement).attr('data-url',url);
		}

		//补订单产品详情相关数据
		$.ajax({
			async:true,
			url:page_url+'/order/saleorder/getsaleordergoodsextrainfo.do',
			data:{"saleorderId":saleorderId, "extraType":"order_saleorder"},//销售订单详情（占用，库存，采购状态，到库状态，发货状态，收货状态）
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					/*layer.alert(data.message, 
							{ icon: 1 },
							function () {
								location.reload();
							}
					);*/
					for (var i = 0; i < data.data.length; i++) {
						//alert(data.data[i].saleorderGoodsId);
						$("#orderOccupy_stockNum_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy+"/"+data.data[i].goods.stockNum);
						$("#kc_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.stockNum);
						$("#kykc_"+data.data[i].saleorderGoodsId).html((data.data[i].goods.stockNum-data.data[i].goods.orderOccupy) < 0 ? 0 : (data.data[i].goods.stockNum-data.data[i].goods.orderOccupy));
						$("#dzzy_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy);
						$("#ktj_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.adjustableNum);
						//采购状态
						var cgztStr = '';
						if (data.data[i].buyNum == undefined || data.data[i].buyNum == 0) {
							cgztStr = "未采购";
						} else if (data.data[i].buyNum < data.data[i].num) {
							cgztStr = "部分采购";
						} else if (data.data[i].buyNum == data.data[i].num) {
							cgztStr = "已采购";
						}
						$("#cgztStr_"+data.data[i].saleorderGoodsId).html(cgztStr);
						//到库状态
						var dkztStr = '';
						if (data.data[i].warehouseNum == undefined || data.data[i].warehouseNum == 0) {
							dkztStr = "未到库";
						} else if (data.data[i].warehouseNum < data.data[i].num) {
							dkztStr = "部分到库";
						} else if (data.data[i].warehouseNum == data.data[i].num) {
							dkztStr = "已到库";
						}
						$("#dkztStr_"+data.data[i].saleorderGoodsId).html(dkztStr);
					}
				}else{
					layer.alert(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	});
</script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>