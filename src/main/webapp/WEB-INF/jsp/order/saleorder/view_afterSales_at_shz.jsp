<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后详情-安调-审核中" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/view_afterSales.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var	url = page_url + '/aftersales/order/viewAfterSalesDetail.do?afterSalesId='+$("#afterSalesId").val();
		if($(window.frameElement).attr('src').indexOf("viewAfterSalesDetail")<0){
			$(window.frameElement).attr('data-url',url);
		}
	});
</script>
	<div class="main-container">
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   基本信息
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">订单号</td>
                        <td>${afterSalesVo.afterSalesNo}</td>
                        <td class="wid20">订单状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.atferSalesStatus eq 0}">待确认</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 1}">进行中</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 2}">已完结</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 3}">已关闭</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>创建者</td>
                        <td>${afterSalesVo.creatorName}</td>
                        <td>创建时间</td>
                        <td><date:date value ="${afterSalesVo.addTime}"/></td>
                    </tr>
                    <tr>
                        <td>生效状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if>
                        	<c:if test="${afterSalesVo.validStatus eq 1}">已生效</c:if>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${afterSalesVo.validTime}"/></td>
                    </tr>
                    <tr>
                        <td>审核状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.status eq 0}">待审核</c:if>
							<c:if test="${afterSalesVo.status eq 1}">审核中</c:if>
							<c:if test="${afterSalesVo.status eq 2}">审核通过</c:if>
							<c:if test="${afterSalesVo.status eq 3}">审核不通过</c:if>
                        </td>
                        <td>售后类型</td>
                        <td class="warning-color1">${afterSalesVo.typeName}</td>
                    </tr>
                </tbody>
            </table>
        </div>
		
		<input type="hidden" name="afterSalesId" id="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
		 <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   售后申请
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">联系人</td>
                        <td>${afterSalesVo.traderContactName}</td>
                        <td>手机</td>
                        <td>
	                        ${afterSalesVo.traderContactMobile}
	                        <c:if test="${not empty afterSalesVo.traderContactMobile}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactMobile}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                    </tr>
                     <tr>
                     	<td>电话</td>
                        <td>
                        	${afterSalesVo.traderContactTelephone}
                        	<c:if test="${not empty afterSalesVo.traderContactTelephone}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactTelephone}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                        <td>售后地区</td>
                        <td>${afterSalesVo.area} </td>
                    </tr>
                    <tr>
                        <td>售后地址</td>
                        <td colspan="3">${afterSalesVo.address}</td>
                      
                    </tr>
                     <tr>
                        <td>详情说明</td>
                        <td colspan="3" class="text-left">${afterSalesVo.comments}</td>
                      
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3" class="text-left">
                        	<c:if test="${not empty afterSalesVo.attachmentList }">
                        		<c:forEach items="${afterSalesVo.attachmentList }" var="att">
                        			<a href="http://${att.domain}${att.uri}" target="_blank">${att.name}</a>&nbsp;&nbsp;
                        		</c:forEach>
                        	</c:if>
                        </td>
                      
                    </tr>
                </tbody>
            </table>
        </div>
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   所属订单
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">订单号</td>
                        <td >
                        	<div class="customername pos_rel">
                               <span class="brand-color1 addtitle" style="float:none;" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"订单信息",
                               		"link":"./order/saleorder/view.do?saleorderId=${afterSalesVo.orderId}"}'>${afterSalesVo.orderNo}</span><i class="iconbluemouth"></i>
                               <div class="pos_abs customernameshow" style="display: none;">
                                        付款状态：<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if><br> 
                                        发货状态：<c:if test="${afterSalesVo.deliveryStatus eq 0}">未发货</c:if>
						<c:if test="${afterSalesVo.deliveryStatus eq 1}">部分发货</c:if>
						<c:if test="${afterSalesVo.deliveryStatus eq 2}">全部发货</c:if><br>
                                        开票状态：<c:if test="${afterSalesVo.invoiceStatus eq 0}">未开票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 1}">部分开票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 2}">全部开票</c:if><br>
                                        收货状态：<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 1}">部分收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 2}">全部收货</c:if>
                               </div>
                            </div>
                        </td>
                        <td class="wid20">订单金额</td>
                        <td><fmt:formatNumber type="number" value="${afterSalesVo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                    </tr>
                    <tr>
                        <td>部门</td>
                        <td>${afterSalesVo.orgName}</td>
                        <td>归属销售</td>
                        <td>${afterSalesVo.userName}</td>
                    </tr>
                     <tr>
                        <td>订单状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 0}">待确认</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 1}">进行中</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 2}">已完结</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 3}">已关闭</c:if>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${afterSalesVo.saleorderValidTime}"/></td>
                    </tr>
                     <tr>
                        <td>客户名称</td>
                        <td>
                        	<div class="customername pos_rel">
                                  <span class="brand-color1 addtitle" style="float:none;" tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"客户信息",
										"link":"./trader/customer/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>${afterSalesVo.traderName}</span><i class="iconbluemouth"></i>
                                   <div class="pos_abs customernameshow" style="display: none;">
                                            客户性质：<c:if test="${afterSalesVo.customerNature eq 465}">分销</c:if>
                          <c:if test="${afterSalesVo.customerNature eq 466}">终端</c:if><br> 
                                            交易次数：${afterSalesVo.orderCount}<br>
                                            交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
                                            上次交易日期：<date:date value ="${afterSalesVo.lastOrderTime}"/>   
                                        </div>
                            </div>
                        </td>
                        <td>客户等级</td>
                        <td>
                        	<c:if test="${afterSalesVo.customerLevel eq 146}">A</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 147}">B</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 148}">C</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 149}">D</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 150}">E</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 931}">S</c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">产品与工程师</div>
            </div>
            <c:if test="${not empty afterSalesVo.afterSalesInstallstionVoList}">
                <c:forEach items="${afterSalesVo.afterSalesInstallstionVoList}" var="asg" varStatus="sttaus">
		            <table class="table  table-style10">
		                <thead>
		                    <tr>
		                        <th class="wid5">序号</th>
		                        <th class="wid15">工程师</th>
		                        <th class="wid15">手机</th>
		                        <th class="wid15">服务时间</th>
		                        <th class="wid10">工程师酬金</th>
		                        <th class="wid8">操作</th>
		                    </tr>
		                </thead>
		                <tbody>
		                    <tr>
		                        <td>${sttaus.count }</td>
		                        <td><div class="customername pos_rel">
		                        		${asg.name }<i class="iconbluemouth"></i>
						                <div class="pos_abs customernameshow" style="display: none;">
							                                            工程师开户行：${asg.bank}<br> 
							                                            工程师账号：${asg.bankAccount}<br>
							                                            所属公司：${asg.company}<br>
							                                            服务次数：${asg.serviceTimes}<br>
							                                            工程师服务评分：${asg.serviceScoreAverage}<br>  
							                                            工程师技能评分：${asg.skillScoreAverage}<br> 
	                                    </div>
                                    </div></td>
		                        <td>${asg.mobile }</td>
		                        <td><date:date value ="${asg.serviceTime}" format="yyyy-MM-dd"/></td>
		                        <td>${asg.engineerAmount }</td>
		                        <td class="caozuo">
		                        <c:if test="${afterSalesVo.verifyStatus ne 0}"></c:if>
			                     	<span class="border-blue addtitle" tabTitle='{"num":"viewafterSalesId<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
				                "link":"./aftersales/order/editEngineerPage.do?afterSalesInstallstionId=${asg.afterSalesInstallstionId}&&afterSalesId=${afterSalesVo.afterSalesId}&&areaId=${afterSalesVo.areaId}&&subjectType=${afterSalesVo.subjectType}","title":"编辑产品与工程师"}'>编辑 </span>
			                     
			                     </td>
		                    </tr>
		                     <tr>
	                        	<td colspan="6" class="table-container">
                                    <c:set var="afterSalesGoodsVoListPage" value="${asg.afterSalesGoodsVoList}"></c:set>
                                    <%@ include file="view_afterSales_at_goods.jsp"%>
<%--		                            <table class="table">--%>
<%--		                                <thead>--%>
<%--		                                   <tr>--%>
<%--		                                       <th class="wid20">产品名称</th>--%>
<%--						                       <th class="wid10">品牌</th>--%>
<%--						                       <th class="wid8">型号</th>--%>
<%--						                       <th class="wid8">物料编码</th>--%>
<%--						                       <th class="wid8">采购订单</th>--%>
<%--						                       <th class="wid5">销售价</th>--%>
<%--						                       <th class="wid8">数量</th>--%>
<%--						                       <th class="wid5">单位</th>--%>
<%--						                       <th class="wid5">售后数量</th>--%>
<%--		                                   </tr>--%>
<%--		                                </thead>--%>
<%--		                                <tbody>--%>
<%--		                                    <c:if test="${not empty asg.afterSalesGoodsVoList}">--%>
<%--						                		<c:forEach items="${asg.afterSalesGoodsVoList}" var="asgv" varStatus="sttaus">--%>
<%--							                		<tr>--%>
<%--								                        <td class="text-left">--%>
<%--								                            <div class="customername pos_rel">--%>
<%--																<c:if test="${asgv.isActionGoods==1}"><span style="color:red;">【活动】</span></c:if>--%>
<%--							                                       <span class="brand-color1 addtitle" style="float:none;"--%>
<%--							                                       		tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",--%>
<%--							                                       					"link":"./goods/goods/viewbaseinfo.do?goodsId=${asgv.goodsId}",--%>
<%--							                                       					"title":"产品信息"}'>${asgv.goodsName}</span>--%>
<%--							                                       <br>${asgv.sku}--%>
<%--							                            	</div>--%>
<%--								                        </td>--%>
<%--								                        <td>${asgv.brandName}</td>--%>
<%--								                        <td>${asgv.materialCode}</td>--%>
<%--								                        <td>${asgv.model}</td>--%>
<%--								                        <td>--%>
<%--								                        	<c:if test="${not empty asgv.buyorderNos}">--%>
<%--									                        	<c:forEach items="${asgv.buyorderNos}" var="buyorder">--%>
<%--									                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",--%>
<%--																			"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a><br>--%>
<%--									                        	</c:forEach>--%>
<%--									                        </c:if>--%>
<%--								                        </td>--%>
<%--														<td>${asgv.saleorderPrice}</td>--%>
<%--								                        <td>${asgv.saleorderNum}</td>--%>
<%--								                        <td>${asgv.unitName}</td>--%>
<%--								                        <td class="warning-color1">${asgv.num}</td>--%>
<%--								                    </tr>--%>
<%--							                	</c:forEach>--%>
<%--						                    </c:if>--%>
<%--						                    <c:if test="${empty asg.afterSalesGoodsVoList}">--%>
<%--							                    <tr>--%>
<%--							                        <td colspan="8">暂无记录</td>--%>
<%--							                    </tr>--%>
<%--						                    </c:if>--%>
<%--		                                </tbody>--%>
<%--		                            </table>--%>
	                        	</td>
	                    	</tr>
	                    	</tbody>
            			</table>
	             </c:forEach>
            </c:if>
            
            <table class="table  table-style10">
              <thead>
                  <tr>
                      <th class="wid5">序号</th>
                      <th class="wid15">工程师</th>
                      <th class="wid15">手机</th>
                      <th class="wid15">服务时间</th>
                      <th class="wid10">工程师酬金</th>
                      <th class="wid8">操作</th>
                  </tr>
              </thead>
              <tbody>
                	<tr>
                     <td></td>
                     <td></td>
                     <td></td>
                     <td></td>
                     <td></td>
                     <td class="caozuo">
                     <c:if test="${afterSalesVo.verifyStatus ne 0}"></c:if>
                     	<span class="border-blue addtitle" tabTitle='{"num":"viewafterSalesId<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
	                			"link":"./aftersales/order/addEngineerPage.do?afterSalesId=${afterSalesVo.afterSalesId}&&areaId=${afterSalesVo.areaId}&&subjectType=${afterSalesVo.subjectType}","title":"编辑产品与工程师"}'>编辑 </span>
                     
                     </td>
                 </tr>
                  <tr>
                    	<td colspan="6" class="table-container">
<%--                            <c:set var="afterSalesGoodsVoListPage" value="${afterSalesVo.afterSalesGoodsList}"></c:set>--%>
<%--                            <%@ include file="view_afterSales_at_goods.jsp"%>--%>
<%--                         <table class="table">--%>
<%--                             <thead>--%>
<%--                                <tr>--%>
<%--                                   <th class="wid20">产品名称</th>--%>
<%--			                       <th class="wid10">品牌</th>--%>
<%--			                       <th class="wid8">型号</th>--%>
<%--			                       <th class="wid8">物料编码</th>--%>
<%--			                       <th class="wid8">采购订单</th>--%>
<%--			                       <th class="wid5">销售价</th>--%>
<%--			                       <th class="wid8">数量</th>--%>
<%--			                       <th class="wid5">单位</th>--%>
<%--			                       <th class="wid5">售后数量</th>--%>
<%--                                </tr>--%>
<%--                             </thead>--%>
<%--                             <tbody>--%>
<%--		                     	<c:if test="${not empty afterSalesVo.afterSalesGoodsList}">--%>
<%--			                		<c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg" varStatus="sttaus">--%>
<%--				                		<tr>--%>
<%--					                    	<td class="text-left">--%>
<%--					                        	<div class="customername pos_rel">--%>
<%--				                                	<span class="brand-color1 addtitle" tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",--%>
<%--				                                       			"link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}", "title":"产品信息"}'>${asg.goodsName}</span>--%>
<%--				                                    <br>${asg.sku}--%>
<%--				                            	</div>--%>
<%--					                        </td>--%>
<%--					                        <td>${asg.brandName}</td>--%>
<%--					                        <td>${asg.materialCode}</td>--%>
<%--					                        <td>${asg.model}</td>--%>
<%--					                        <td>--%>
<%--					                        	<c:if test="${not empty asg.buyorderNos}">--%>
<%--						                        	<c:forEach items="${asg.buyorderNos}" var="buyorder">--%>
<%--						                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",--%>
<%--																"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a><br>--%>
<%--						                        	</c:forEach>--%>
<%--						                        </c:if>--%>
<%--					                        </td>--%>
<%--											<td>${asg.saleorderPrice}</td>--%>
<%--					                        <td>${asg.saleorderNum}</td>--%>
<%--					                        <td>${asg.unitName}</td>--%>
<%--					                        <td class="warning-color1">${asg.num}</td>--%>
<%--					                    </tr>--%>
<%--				                	</c:forEach>--%>
<%--			                    </c:if>--%>
<%--			                    <c:if test="${empty afterSalesVo.afterSalesGoodsList}">--%>
<%--				                    <tr>--%>
<%--				                        <td colspan="8">暂无记录</td>--%>
<%--				                    </tr>--%>
<%--			                    </c:if>--%>
<%--                             </tbody>--%>
<%--                         </table>--%>
                    	</td>
                	</tr>
                </tbody>
          </table>
            
        </div>
        
         <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后服务费
                </div>
                <c:if test="${afterSalesVo.verifyStatus ne 0}"></c:if>
                 <div class="title-click nobor  pop-new-data" layerParams='{"width":"600px","height":"400px","title":"编辑售后服务费",
		                  "link":"./editInstallstionPage.do?afterSalesDetailId=${afterSalesVo.afterSalesDetailId}&&afterSalesId=${afterSalesVo.afterSalesId}&&serviceAmount=${afterSalesVo.serviceAmount}&&isSendInvoice=${afterSalesVo.isSendInvoice}&&invoiceType=${afterSalesVo.invoiceType}&flag=at"}'>编辑</div>
            	
            </div>
            <table class="table">
                <tbody>
                	<tr>
                        <td>安调费</td>
                        <td>${afterSalesVo.serviceAmount }</td>
                        <td>票种</td>
                        <td>
                        	<c:if test="${afterSalesVo.invoiceType eq 429}">17%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 430}">17%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 682}">16%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 681}">16%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 972}">13%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 971}">13%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 683}">6%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 684}">6%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 685}">3%增值税普用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 686}">3%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 687}">0%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 688}">0%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>收票客户</td>
                        <td>${afterSalesVo.invoiceTraderName }</td>
                        <td>收票联系人</td>
                        <td>${afterSalesVo.invoiceTraderContactName }</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>${afterSalesVo.invoiceTraderContactTelephone }</td>
                        <td>手机</td>
                        <td>${afterSalesVo.invoiceTraderContactMobile }</td>
                    </tr>
                    <tr>
                        <td>收票地区</td>
                        <td>${afterSalesVo.invoiceTraderArea }</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>收票地址</td>
                        <td colspan="3">${afterSalesVo.invoiceTraderAddress }</td>
                    </tr>
                    <tr>
                        <td>开票备注</td>
                        <td colspan="3">${afterSalesVo.invoiceComments }</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    沟通记录
                </div>
                 <c:if test="${afterSalesVo.status ne 3}">
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录",
	                		"link":"<%= basePath %>/aftersales/order/addCommunicatePage.do?afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=1"}'>
	                   	 新增
	                </div>
                </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid10">沟通时间</th>
                        <th class="">录音</th>
                        <th class="">联系人</th>
                        <th class="">联系方式</th>
                        <th class="">沟通方式</th>
                        <th class="wid30">沟通内容</th>
                        <th class="">操作人</th>
                        <th class="wid8">下次联系日期</th>
                        <th class="wid15">下次沟通内容</th>
                        <th class="">备注</th>
                        <th class="wid10">创建时间</th>
                        <th class="wid6">操作</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${not empty communicateList}">
                		<c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
                			<tr>
		                        <td><date:date value ="${communicateRecord.begintime} "/>~<date:date value ="${communicateRecord.endtime}" format="HH:mm:ss"/></td>
		                        <td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>
		                        <td>${communicateRecord.contactName}</td>
		                        <td>${communicateRecord.phone}</td>
		                        <td>${communicateRecord.communicateModeName}</td>
		                        <td>
		                        	<ul class="communicatecontent ml0">
										<c:choose>
		                        			<c:when test="${not empty communicateRecord.tag }">
												<c:forEach items="${communicateRecord.tag }" var="tag">
													<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
												</c:forEach>
											</c:when>
											<c:otherwise>
													<li>${communicateRecord.contactContent }</li>
											</c:otherwise>
		                        		</c:choose>
									</ul>
								</td>
		                        <td>${communicateRecord.user.username}</td>
		                        <c:choose>
			                    	<c:when test="${communicateRecord.isDone == 0 }">
				                   		<td class="font-red">${communicateRecord.nextContactDate }</td>
			                    	</c:when>
			                    	<c:otherwise>
				                    	<td>${communicateRecord.nextContactDate }</td>
			                    	</c:otherwise>
			                    </c:choose>
			                    <td>${communicateRecord.nextContactContent}</td>
		                        <td>${communicateRecord.comments}</td>
		                        <td><date:date value ="${communicateRecord.addTime} "/></td>
		                        
		                        <td class="caozuo">
		                        	<c:if test="${afterSalesVo.verifyStatus ne 0}"></c:if>
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"60%","height":"63%","title":"编辑沟通记录",
		                        		"link":"<%= basePath %>/aftersales/order/editcommunicate.do?orderFlag=${afterSalesVo.atferSalesStatus }&flag=${afterSalesVo.status }&communicateRecordId=${communicateRecord.communicateRecordId}&afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=1"}'>编辑</span>
		                        	
		                        </td>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty communicateList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='12'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后过程
                </div>
                <c:if test="${afterSalesVo.status ne 3 }">
	                <div class="title-click nobor  pop-new-data" 
	                	layerParams='{"width":"700px","height":"250px","title":"新增售后过程","link":"./addAfterSalesRecordPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增
	                </div>
                </c:if>
            </div>
           <table class="table">
                <thead>
                    <tr>
                        <th>沟通时间</th>
                        <th>操作人</th>
                        <th>售后内容</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty afterSalesVo.afterSalesRecordVoList}">
	               		<c:forEach items="${afterSalesVo.afterSalesRecordVoList}" var="asi" >
	               			<tr>
		                        <td><date:date value ="${asi.addTime} "/></td>
		                        <td>${asi.optName}</td>
		                        <td>${asi.content}</td>
		                        <td class="caozuo">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"700px","height":"250px","title":"编辑售后过程",
		                        			"link":"./editAfterSalesRecordPage.do?afterSalesRecordId=${asi.afterSalesRecordId}"}'>编辑</span>
		                        </td>
		                    </tr>
	               		</c:forEach>
	               	</c:if>
	               	<c:if test="${empty afterSalesVo.afterSalesRecordVoList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
             <div class="table-buttons">
             <c:if test="${afterSalesVo.status ne 3 }">
                <form action="" method="post" id="myform">
             		<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
             		<input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
             		<input type="hidden" name="orderId" value="${afterSalesVo.orderId}"/>
             		<input type="hidden" name="type" value="${afterSalesVo.type}"/>
             		<input type="hidden" name="formToken" value="${formToken}"/>
             		<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
						<c:choose>
							<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=2"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=2"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
	        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
							</c:otherwise>
						</c:choose>
					</c:if>
                </form>
             </c:if>
            </div>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
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
                	<c:if test="${null==historicActivityInstance}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
                </tbody>
            </table>
        </div>

     </div>   
</body>

</html>