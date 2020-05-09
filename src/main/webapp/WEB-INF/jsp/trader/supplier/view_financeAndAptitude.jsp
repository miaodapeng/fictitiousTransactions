<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="供应商财务与资质信息" scope="application" />
<script type="text/javascript">
function delBank(id){
	checkLogin();
	layer.confirm("请确认继续当前操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/supplier/delBank.do",
				data: {'traderFinanceId':id},
				dataType:'json',
				success: function(data){
					self.location.reload();
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
		});

}

</script>	
<%@ include file="../../common/common.jsp"%>
	<%@ include file="supplier_tag.jsp"%>
	<div class="content">
        <%--<div class="parts">--%>
            <%--<div class="title-container">--%>
                <%--<div class="table-title">资质信息</div>--%>
                <%--<c:if test="${supplierInfoByTraderSupplier.isEnable == 1  && ((supplierInfoByTraderSupplier.verifyStatus != null && supplierInfoByTraderSupplier.verifyStatus != 0 )|| supplierInfoByTraderSupplier.verifyStatus == null)}">--%>
                <%--<!-- <div class="title-click  pop-new-data" layerParams='{"width":"1200px","height":"500px","title":"编辑","link":"./editAptitude.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"}'>编辑</div> -->--%>
                <%--<div class="title-click addtitle"--%>
					<%--tabTitle='{"num":"customer","link":"./trader/supplier/editAptitude.do?traderId=${traderSupplier.traderId}&traderCustomerId=${traderSupplier.traderSupplierId}","title":"编辑资质"}'>编辑</div>--%>
                <%--</c:if>--%>
            <%--</div>--%>
            <%--<% Date date=new Date();long now = date.getTime(); request.setAttribute("now", now); %>--%>
            <%--<table class="table table-bordered table-striped table-condensed table-centered">--%>
                <%--<tbody>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">营业执照</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${business ne null && business.uri ne null}">--%>
							   		 <%--<a href="http://${business.domain}${business.uri}" target="_blank">营业执照</a>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--营业执照--%>
							   <%--</c:otherwise>--%>
							<%--</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;--%>
                        	<%--有效期： <date:date value ="${business.begintime}" format="yyyy-MM-dd"/>--%>
		                        	<%--<c:if test="${business ne null && business.endtime eq null}">-无限期</c:if>--%>
			                        <%--<c:if test="${business.endtime ne null}">-<date:date value ="${business.endtime}" format="yyyy-MM-dd"/></c:if>&nbsp;&nbsp;&nbsp;&nbsp;--%>
			                        <%--<c:if test="${not empty business.endtime && business.endtime ne 0 && business.endtime lt now }"><span style="color: red">（已过期）</span></c:if> --%>
		                        	<%--<c:if test="${business.isMedical eq 1}">含有医疗器械</c:if>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">税务登记证</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${tax ne null && tax.uri ne null}">--%>
							   		 <%--<a href="http://${tax.domain}${tax.uri}" target="_blank">税务登记证</a>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--税务登记证--%>
							   <%--</c:otherwise>  --%>
							<%--</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;--%>
                        	<%--有效期：  <date:date value ="${tax.begintime}" format="yyyy-MM-dd"/>--%>
		                        	<%--<c:if test="${tax ne null && tax.endtime eq null}">-无限期</c:if>--%>
			                        <%--<c:if test="${tax.endtime ne null}">-<date:date value ="${tax.endtime}" format="yyyy-MM-dd"/></c:if>--%>
			                        <%--<c:if test="${tax.endtime ne null && tax.endtime ne 0 && tax.endtime lt now }"><span style="color: red">（已过期）</span></c:if>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">组织机构代码证</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${orga ne null && orga.uri ne null}">--%>
							   		 <%--<a href="http://${orga.domain}${orga.uri}" target="_blank">组织机构代码证</a>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--组织机构代码证--%>
							   <%--</c:otherwise>  --%>
							<%--</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;--%>
                       		<%--有效期：<date:date value ="${orga.begintime}" format="yyyy-MM-dd"/>--%>
	                        		<%--<c:if test="${orga ne null && orga.endtime eq null}">-无限期</c:if>--%>
		                        	<%--<c:if test="${orga.endtime ne null}">-<date:date value ="${orga.endtime}" format="yyyy-MM-dd"/></c:if>--%>
		                        	<%--<c:if test="${orga.endtime ne null && orga.endtime ne 0 && orga.endtime lt now }"><span style="color: red">（已过期）</span></c:if> --%>
		                        <%----%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">医疗器械二类备案凭证</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${twoMedicalList ne null }">--%>
								   <%--<c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">--%>
									   <%--<c:if test="${st.index == 0}">--%>
									   	<%--<c:set var="beginTime" value="${twoMedical.begintime}"></c:set>--%>
									   	<%--<c:set var="endTime" value="${twoMedical.endtime}"></c:set>--%>
									   	<%--<c:set var="sn" value="${twoMedical.sn}"></c:set>--%>
									   <%--</c:if>--%>
									   	<%--<c:if test="${twoMedical.uri ne null && not empty twoMedical.uri}">--%>
									   		 <%--<a href="http://${twoMedical.domain}${twoMedical.uri}" target="_blank">医疗器械二类备案凭证 - ${st.index + 1}</a>&nbsp;&nbsp;--%>
									   	<%--</c:if>--%>
								   <%--</c:forEach>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--医疗器械二类备案凭证&nbsp;--%>
							   <%--</c:otherwise>  --%>
							<%--</c:choose>&nbsp;&nbsp;&nbsp;--%>
                        	<%--有效期：<date:date value ="${beginTime} " format="yyyy-MM-dd"/>--%>
                        			<%--<c:if test="${twoMedicalList ne null and endTime eq null && not empty twoMedicalList}">-无限期</c:if>--%>
                        			<%--<c:if test="${endTime ne null}">--%>
                        				<%---<date:date value ="${endTime} " format="yyyy-MM-dd"/>--%>
                        			<%--</c:if>&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn}--%>
                        			 <%--<c:if test="${endTime ne null && endTime ne 0 && endTime lt now }"><span style="color: red">（已过期）</span></c:if>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">医疗器械二类备案凭证详情</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:if test="${not empty two }">--%>
				               <%--<c:forEach items="${two }" var="mc">--%>
				               	<%--${mc.title}&nbsp;&nbsp;--%>
				               <%--</c:forEach>--%>
				            <%--</c:if>--%>
                        <%--</td>--%>
                        	<%----%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">三类医疗资质</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${threeMedical ne null && threeMedical.uri ne null}">--%>
							   		 <%--<a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank">三类医疗资质</a>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--三类医疗资质--%>
							   <%--</c:otherwise>  --%>
							<%--</c:choose>&nbsp;&nbsp;&nbsp;&nbsp;--%>
                        	<%--有效期：<date:date value ="${threeMedical.begintime} " format="yyyy-MM-dd"/>--%>
                        			<%--<c:if test="${threeMedical ne null && threeMedical.endtime eq null}">-无限期</c:if>--%>
                        			<%--<c:if test="${threeMedical.endtime ne null}">--%>
                        				<%---<date:date value ="${threeMedical.endtime} " format="yyyy-MM-dd"/>--%>
                        			<%--</c:if>&nbsp;&nbsp;&nbsp;&nbsp;许可证编号：${threeMedical.sn}--%>
                        			 <%--<c:if test="${threeMedical.endtime ne null && threeMedical.endtime ne 0 && threeMedical.endtime lt now }"><span style="color: red">（已过期）</span></c:if>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">三类医疗资质详情</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:if test="${not empty three }">--%>
				               <%--<c:forEach items="${three }" var="mc">--%>
				               	<%--${mc.title}&nbsp;&nbsp;--%>
				               <%--</c:forEach>--%>
				            <%--</c:if>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">医疗器械生产许可证</td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${product ne null && product.uri ne null}">--%>
							   		 <%--<a href="http://${product.domain}${product.uri}" target="_blank">医疗器械生产许可证</a>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--医疗器械生产许可证--%>
							   <%--</c:otherwise>  --%>
							<%--</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;--%>
                       		<%--有效期：<date:date value ="${product.begintime}" format="yyyy-MM-dd"/>--%>
	                        		<%--<c:if test="${product ne null && product.endtime eq null}">-无限期</c:if>--%>
		                        	<%--<c:if test="${product.endtime ne null}">-<date:date value ="${product.endtime}" format="yyyy-MM-dd"/></c:if>--%>
		                        	<%--<c:if test="${product.endtime ne null && product.endtime ne 0 && product.endtime lt now }"><span style="color: red">（已过期）</span></c:if> --%>
		                        <%----%>
                        <%--</td>--%>
                         <%--<tr>--%>
	                        <%--<td class="table-smallest">医疗器械经营许可证</td>--%>
	                        <%--<td style="text-align: left;">--%>
	                        	<%--<c:choose> --%>
								   <%--<c:when test="${operate ne null && operate.uri ne null}">--%>
								   		 <%--<a href="http://${operate.domain}${operate.uri}" target="_blank">医疗器械经营许可证</a>--%>
								   <%--</c:when>--%>
								   <%--<c:otherwise>--%>
								   		<%--医疗器械经营许可证--%>
								   <%--</c:otherwise>  --%>
								<%--</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;--%>
	                       		<%--有效期：<date:date value ="${operate.begintime}" format="yyyy-MM-dd"/>--%>
		                        		<%--<c:if test="${operate ne null && operate.endtime eq null}">-无限期</c:if>--%>
			                        	<%--<c:if test="${operate.endtime ne null}">-<date:date value ="${operate.endtime}" format="yyyy-MM-dd"/></c:if>--%>
			                        	<%--<c:if test="${operate.endtime ne null && operate.endtime ne 0 && operate.endtime lt now }"><span style="color: red">（已过期）</span></c:if> --%>
			                        <%----%>
	                        <%--</td>--%>
	                    <%--</tr>--%>
	                    <%--<!-- begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 -->--%>
	                    <%--<tr>--%>
	                    	<%--<td class="table-smallest">销售人员授权书</td>--%>
	                    	<%--<td style="text-align: left;">--%>
	                    		<%--<c:choose> --%>
								   <%--<c:when test="${saleAuth ne null && saleAuth.uri ne null}">--%>
								   		 <%--<a href="http://${saleAuth.domain}${saleAuth.uri}" target="_blank">销售人员授权书</a>--%>
								   <%--</c:when>--%>
							   	<%--<c:otherwise>--%>
							   		<%--销售人员授权书--%>
							   	<%--</c:otherwise>--%>
							<%--</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;有效期： <date:date value ="${saleAuth.begintime}" format="yyyy-MM-dd"/>-<date:date value ="${saleAuth.endtime}" format="yyyy-MM-dd"/>--%>
	                    	<%--</td>--%>
	                    <%--</tr>--%>
	                    <%--<tr>--%>
	                    	<%--<td class="table-smallest">授权销售人信息</td>--%>
	                    	<%--<td style="text-align: left;">--%>
	                    		<%--职位:&nbsp;<span>${saleAuth.authPost}</span>&nbsp;&nbsp;, 姓名:&nbsp;<span>${saleAuth.authUserName}</span>&nbsp;&nbsp;, 联系方式:&nbsp;<span>${saleAuth.authContactInfo}</span>--%>
	                    	<%--</td>--%>
	                    <%--</tr>--%>
	                    <%--<!-- end by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 -->--%>
	                    <%----%>
	                    <%--<tr>--%>
                        <%--<td class="table-smallest">品牌授权书 </td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${brandBookList ne null }">--%>
							   <%--<c:forEach items="${brandBookList }" var="brandBook" varStatus="st">--%>
							   <%--<c:if test="${st.index == 0}">--%>
							   	<%--<c:set var="brandBeginTime" value="${brandBook.begintime}"></c:set>--%>
							   	<%--<c:set var="brandEndTime" value="${brandBook.endtime}"></c:set>--%>
							   <%--</c:if>--%>
							   	<%--<c:if test="${brandBook.uri ne null && not empty brandBook.uri}">--%>
							   		 <%--<a href="http://${brandBook.domain}${brandBook.uri}" target="_blank">品牌授权书 - ${st.index + 1}</a>&nbsp;&nbsp;--%>
							   	<%--</c:if>--%>
							   <%--</c:forEach>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--品牌授权书&nbsp;--%>
							   <%--</c:otherwise>  --%>
							<%--</c:choose>&nbsp;&nbsp;&nbsp;--%>
                        	<%--有效期：<date:date value ="${brandBeginTime} " format="yyyy-MM-dd"/>--%>
                        			<%--<c:if test="${brandBookList ne null and brandEndTime eq null and not empty brandBookList}">-无限期</c:if>--%>
                        			<%--<c:if test="${brandEndTime ne null}">--%>
                        				<%---<date:date value ="${brandEndTime} " format="yyyy-MM-dd"/>--%>
                        			<%--</c:if>&nbsp;--%>
                        			 <%--<c:if test="${brandEndTime ne null && brandEndTime ne 0 && brandEndTime lt now }"><span style="color: red">（已过期）</span></c:if>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%----%>
                    <%--<tr>--%>
                        <%--<td class="table-smallest">其他 </td>--%>
                        <%--<td style="text-align: left;">--%>
                        	<%--<c:choose> --%>
							   <%--<c:when test="${otherList ne null }">--%>
							   <%--<c:forEach items="${otherList }" var="other" varStatus="st">--%>
							   	<%--<c:if test="${other.uri ne null && not empty other.uri}">--%>
							   		 <%--<a href="http://${other.domain}${other.uri}" target="_blank">其他资格证书 - ${st.index + 1}</a>&nbsp;&nbsp;--%>
							   	<%--</c:if>--%>
							   <%--</c:forEach>--%>
							   <%--</c:when>--%>
							   <%--<c:otherwise>--%>
							   		<%--其他资格证书&nbsp;--%>
							   <%--</c:otherwise>  --%>
							<%--</c:choose>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%----%>
                <%--</tbody>--%>
            <%--</table>--%>
        <%--</div>--%>
        <div class="parts">
            <div class="title-container">
                <div class="table-title">财务信息</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td>账期</td>
                        <td>当前额度${finance.periodAmount}元，帐期天数${finance.periodDay}天；当前剩余额度${finance.balanceAccount}元 </td>
                        <td>账户余额</td>
                        <td>${finance.amount}</td>	
                    </tr>
                    <tr>
                        <td>信用评估</td>
                        <td>${finance.creditRating}</td>
                        <td></td>
                        <td></td>		
                    </tr>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title">银行帐号</div>
                <c:if test="${supplierInfoByTraderSupplier.isEnable == 1 &&((supplierInfoByTraderSupplier.verifyStatus != null && supplierInfoByTraderSupplier.verifyStatus != 0 )|| supplierInfoByTraderSupplier.verifyStatus == null)}">
               <div class="title-click  pop-new-data" layerParams='{"width":"600px","height":"250px","title":"新增","link":"./toAddFinancePage.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"}'>新增</div>
               </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
               
                	<thead>
		                <tr>
		                	<th class="table-smalle">开户银行</th>
		                    <th class="table-smalle">银行帐号</th>
		                    <th class="table-smalle">开户行支付联行号</th>
		                    <th class="table-smallest">备注</th>
		                    <th class="table-smalle">操作内容</th>
		                </tr>
		            </thead>
		            <tbody>
		            	<c:if test="${not empty bankList }">
		            		<c:forEach items="${bankList }" var="ba">
			                  <tr>
			                  	<td>${ba.bank}</td>
			                      <td>${ba.bankAccount}</td>
			                      <td>${ba.bankCode}</td>
			                      <td>${ba.comments}</td> 
			                      <td class="caozuo">
			                      	<span style="cursor: pointer;" class="caozuo-blue editclick pop-new-data" layerParams='{"width":"600px","height":"250px","title":"编辑",
			                        			"link":"./toEditFinancePage.do?traderFinanceId=${ba.traderFinanceId}&traderId=${traderSupplier.traderId}&traderType=2"}'>编辑</span>
			                        <span class="caozuo-red " onclick="delBank(${ba.traderFinanceId});">删除</span>			
			                      </td>
			                      
			                  </tr>
		            		</c:forEach>
		            	</c:if>
		            	<c:if test="${empty bankList }">
		            		<tr>
		            			<td colspan="5">查询无结果！</td>
		            		</tr>
		            	</c:if>
		            </tbody>
            </table>
        </div>
        
         <div class="parts">
	        <div class="title-container">
	            <div class="table-title nobor">帐期记录</div>
	            <c:if test="${supplierInfoByTraderSupplier.isEnable == 1 && ((supplierInfoByTraderSupplier.verifyStatus != null && supplierInfoByTraderSupplier.verifyStatus != 0 )|| supplierInfoByTraderSupplier.verifyStatus == null)}">
               		<div class="title-click  pop-new-data" layerParams='{"width":"500px","height":"480px","title":"申请帐期",
               			"link":"./accountperiodapply.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"}'>申请帐期</div>
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
									<c:if test="${bill.traderType eq 2}">
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
       <iframe src="./getAmountBillPage.do?traderId=${supplierInfoByTraderSupplier.traderId}&traderType=2&isEnable=${supplierInfoByTraderSupplier.isEnable}&verifyStatus=${supplierInfoByTraderSupplier.verifyStatus}" frameborder="0" style="width: 100%;border: 0; " onload="setIframeHeight(this)" scrolling="no"></iframe>
        <span style="display:none;">
			
			<div class="title-click nobor  pop-new-data" id="popBill"></div>
		</span> -->
         <iframe src="./getCapitalBillPage.do?traderId=${supplierInfoByTraderSupplier.traderId}&traderType=2" frameborder="0" style="width: 100%;border: 0; " onload="setIframeHeight(this)" scrolling="no">
         </iframe>
    </div>
   
</body>

</html>
