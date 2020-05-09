<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="合同及售后" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/view.js?rnd=<%=Math.random()%>'></script>
<div class="content">
 	<div class="customer">
       <ul>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/viewNew.do?saleorderId=${saleorder.saleorderId }&companyId=${saleorder.companyId }">订单及基本信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/getSaleAndExpress.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">交易及物流信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/getContactAndCheckInfo.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">沟通及审核信息</a>
            </li>
            <li>
                <a class="customer-color"  href="${pageContext.request.contextPath}/order/saleorder/getAttachmentAndInstance.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">合同及售后</a>
            </li>
        </ul>
  	</div>
   	<div class="parts">
   		<div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                   	 合同回传
                </div>
                <c:if test="${saleorder.status != 3}">
                	<div class="title-click nobor pop-new-data" 
                	layerParams='{"width":"520px","height":"200px","title":"合同回传",
                	"link":"./contractReturnInit.do?saleorderId=${saleorder.saleorderId}"}'>上传</div>
            	</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>合同回传</th>
                        <th class="table-small">操作人</th>
                        <th class="table-small">时间</th>
                        <th class="table-smallest5"> 操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="contractReturnNum" value="0"></c:set>
                	<c:forEach items="${saleorderAttachmentList}" var="list" varStatus="status">
                    	<c:if test="${list.attachmentFunction == 492}">
                    		<c:set var="contractReturnNum" value="1"></c:set>
							<tr>
		                        <td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
		                        <td>${list.username}</td>
		                        <td><date:date value ="${list.addTime}"/></td>
		                        <td>
		                            <div class="caozuo">
		                            	<c:if test="${saleorder.status != 3}">
		                                <span class="caozuo-red" onclick="contractReturnDel(${list.attachmentId})">删除</span>
		                            	</c:if>
		                            </div>
		                        </td>
		                    </tr>
                   		</c:if>
                   	</c:forEach>
                   	<c:if test="${contractReturnNum == 0}">
                   	<tr>
	           			<td colspan="4">暂无合同回传。</td>
	           		</tr>
	           		</c:if>
                </tbody>
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    	送货单回传
                </div>
                <c:if test="${saleorder.status != 3}">
                	<div class="title-click nobor pop-new-data" 
                		layerParams='{"width":"520px","height":"200px","title":"送货单回传",
                		"link":"./deliveryOrderReturnInit.do?saleorderId=${saleorder.saleorderId}"}'>上传</div>
            	</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>送货单</th>
                        <th class="table-small">操作人</th>
                        <th class="table-small">时间</th>
                        <th class="table-smallest5">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="deliveryOrderReturnNum" value="0"></c:set>
                	<c:forEach items="${saleorderAttachmentList}" var="list" varStatus="status">
                    	<c:if test="${list.attachmentFunction == 493}">
                    		<c:set var="deliveryOrderReturnNum" value="1"></c:set>
							<tr>
		                        <td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
		                        <td>${list.username}</td>
		                        <td><date:date value ="${list.addTime}"/></td>
		                        <td>
		                            <div class="caozuo">
		                            	<c:if test="${saleorder.status != 3}">
		                                <span class="caozuo-red" onclick="deliveryOrderReturnDel(${list.attachmentId})">删除</span>
		                            	</c:if>
		                            </div>
		                        </td>
		                    </tr>
                   		</c:if>
                   	</c:forEach>
                   	<c:if test="${deliveryOrderReturnNum == 0}">
                   	<tr>
	           			<td colspan="4">暂无送货单回传。</td>
	           		</tr>
	           		</c:if>
                </tbody>
                
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    	录保卡
                </div>
                <c:if test="${saleorder.status != 3}">
                	 <div class="title-click nobor addtitle" 
                	 	tabTitle='{"num":"allgoodswarranty<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"./order/saleorder/allgoodswarranty.do?saleorderId=${saleorder.saleorderId}","title":"全部"}'>全部</div>					
                </c:if>
              
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="table-small">产品名称</th>
                        <th class="table-small">品牌</th>
                        <th class="table-small">型号</th>
                        <th class="table-small">贝登条码</th>
                        <th class="table-small">厂商条码</th>
                        <th class="table-small">录保卡时间</th>
                        <th class="table-smallest10">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:choose>
                		<c:when test="${not empty goodsWarrantys }">
                			<c:forEach items="${goodsWarrantys }" var="goodsWarranty">
                			<tr>
                				<td class="text-left">
                					<div class="brand-color1">
                						<a class="addtitle" href="javascript:void(0);" 
                							tabTitle='{"num":"viewgoods${goodsWarranty.goodsId}",
                							"link":"./goods/goods/viewbaseinfo.do?goodsId=${goodsWarranty.goodsId}",
                							"title":"产品信息"}'>${goodsWarranty.goodsName }</a></div>
									<div>${goodsWarranty.sku }</div>
									<div>${goodsWarranty.materialCode }</div>
                				</td>
                				<td>${goodsWarranty.brandName }</td>
                				<td>${goodsWarranty.model }</td>
                				<td>${goodsWarranty.barcode }</td>
                				<td>${goodsWarranty.barcodeFactory }</td>
                				<td><date:date value ="${goodsWarranty.addTime}"/></td>
                				<td>
                					<span class="edit-user addtitle"
										tabTitle='{"num":"viewwarranty${goodsWarranty.saleorderGoodsWarrantyId }",
										"link":"./order/saleorder/viewgoodswarranty.do?saleorderGoodsWarrantyId=${goodsWarranty.saleorderGoodsWarrantyId}",
										"title":"查看保卡"}'>查看</span>
                				</td>
                			</tr>
                			</c:forEach>
                		</c:when>
                		<c:otherwise>
                			<tr>
		                        <td colspan="7">查询无结果！</td>
		                    </tr>
                		</c:otherwise>
                	</c:choose>
                </tbody>
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                	售后列表
                </div>
                <c:if test="${saleorder.status != 3 and saleorder.status != 0}">
					<c:if test="${saleorder.deliveryDirect eq 1}">
						<div class="title-click nobor" onclick="confirmMsg(${saleorder.saleorderId});">新增售后</div>
						<span style="display:none;">
							<div class="title-click nobor addtitle2" id="addAfter"></div>
						</span>
					</c:if>
					<c:if test="${saleorder.deliveryDirect eq 0}">
						<div class="title-click nobor addtitle" 
							tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
							"link":"./order/saleorder/addAfterSalesPage.do?flag=th&&saleorderId=${saleorder.saleorderId}",
							"title":"售后详情"}'>新增售后</div> 
					</c:if>
										
                </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid15">售后单号</th>
						<th class="wid15">售后类型</th>
                        <th class="wid20">创建时间</th>
                        <th class="wid10">创建人</th>
                        <th class="wid10">订单状态</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${asList}" var="aftersales" varStatus="status">
						<tr>
	                        <td>
	                        	<span class="font-blue addtitle" 
									tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
									"link":"./order/saleorder/viewAfterSalesDetail.do?afterSalesId=${aftersales.afterSalesId}",
									"title":"售后详情"}'>
									${aftersales.afterSalesNo}
								</span>
	                        </td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${aftersales.type eq 539}">销售退货</c:when>
	                        		<c:when test="${aftersales.type eq 540}">销售换货</c:when>
	                        		<c:when test="${aftersales.type eq 541}">销售安调</c:when>
	                        		<c:when test="${aftersales.type eq 584}">销售维修</c:when>
	                        		<c:when test="${aftersales.type eq 542}">销售退票</c:when>
	                        		<c:when test="${aftersales.type eq 543}">销售退款</c:when>
	                        		<c:when test="${aftersales.type eq 544}">销售订单技术咨询</c:when>
	                        		<c:when test="${aftersales.type eq 545}">销售订单其他</c:when>
	                        	</c:choose>
	                        </td>
	                        <td><date:date value ="${aftersales.addTime}"/></td>
	                        <td>${aftersales.creatorName}</td>
	                        <td>
	                            <c:if test="${aftersales.atferSalesStatus eq 0}">待确认</c:if>
								<c:if test="${aftersales.atferSalesStatus eq 1}">进行中</c:if>
								<c:if test="${aftersales.atferSalesStatus eq 2}">已完结</c:if>
								<c:if test="${aftersales.atferSalesStatus eq 3}">已关闭</c:if>
	                        </td>
	                    </tr>
                   	</c:forEach>
                   	<c:if test="${empty asList}">
	                     <tr>
	                        <td colspan="5">查询无结果！</td>
	                    </tr>
                    </c:if>
                </tbody>
            </table>
       	</div>
       	<div class="text-center mb15">
       		<c:if test="${saleorder.validStatus == 0 && saleorder.status!=3}">
		        <span class="bg-light-green bt-bg-style bt-small addtitle"
									tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }",
									"link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}","title":"打印预览"}'>打印预览</span>
		        </c:if>
       	</div>
       	<c:if test="${saleorder.status == 2}">
	        <div class="tcenter mt-5 mb15">
	        	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
	        	<span class="bg-light-green bt-bg-style bt-small addtitle"
							tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }",
							"link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}&
							comment=${saleorder.invoiceComments}","title":"打印"}'>打印</span>
	        </div>
	    </c:if>
	     <c:if test="${saleorder.status != 3 &&  saleorder.status != 2}"><!-- 订单状态为未关闭 -->
	     	<div class="tcenter mt-5 mb15">
        		<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
        		<c:if test="${saleorder.validStatus ne 0}">
        			<!-- button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="noValidSaleorder(${saleorder.saleorderId})">撤销生效</button-->
					<span class="bg-light-green bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }","link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}","title":"打印"}'>打印</span>
        		</c:if>
        	</div>
	     </c:if>
      	<input type="hidden" name="formToken" value="${formToken}"/>
	</div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>