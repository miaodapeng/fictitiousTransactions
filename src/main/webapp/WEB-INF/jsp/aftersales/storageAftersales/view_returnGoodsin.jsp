<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="退货入库详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container logistics-warehousein-addWarehouseIn">
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
                        	<c:choose>
                        		<c:when test="${afterSalesVo.atferSalesStatus==0}">
                        			待确认
                        		</c:when>
                        		<c:when test="${afterSalesVo.atferSalesStatus==1}">
                        		            进行中
                        		</c:when>
                        		<c:when test="${afterSalesVo.atferSalesStatus==2}">
                        			已完结
                        		</c:when>
                        		<c:when test="${afterSalesVo.atferSalesStatus==3}">
                        			已关闭
                        		</c:when>
                        	</c:choose>
                        </td>
                    </tr>
                    <tr>
                    	<td class="wid20">创建者</td>
                        <td>${afterSalesVo.creatorName}</td>
                        <td class="wid20">生效时间</td>
                        <td><date:date value="${afterSalesVo.validTime}" /></td> 
                    </tr>
                     <tr>
                    	<td class="wid20">售后类型</td>
                        <td  class="warning-color1">
                           <c:choose>
                        		<c:when test="${afterSalesVo.type==539}">
                        			退货
                        		</c:when>
                        		<c:when test="${afterSalesVo.type==540}">
                        		            换货
                        		</c:when>
                        	</c:choose>
                        </td>
                        <td class="wid20"></td>
                        <td class=""></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	退货产品信息
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">序号</th>
                        <th>产品名称</th>
                        <th>订货号</th>
						<th>品牌</th>
						<th>型号</th>
                        <th>物料编码</th>

                        <th>单价</th>
                        <th class="wid4 "  >单位</th>
                        <th class="wid8">退货数量</th>
                        <c:if test="${afterSalesVo.type==539 or afterSalesVo.type==540}">
                         <th class="wid8">需入库数量</th>
                        </c:if>
                        <th class="wid8">已退货数量</th>
                        <th>退货方式</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
				<c:set var="skurknum" value=""></c:set>
                <c:forEach var="asv" items="${afterSalesVo.afterSalesGoodsList}" varStatus="num">
                    <tr class="J-skuInfo-tr" skuId="${asv.goodsId}">
                        <%-- <td>
					        <c:if test="${asv.deliveryStatus != 2}">
	                       		<input type="checkbox" name="g_checknox"  value="${asv.goodsId}" onclick="canelAll(this,'g_checknox')">
	                       	</c:if>
                       	</td> --%>
                        <td>${num.count}</td>
                        <c:choose>
							<c:when test="${asv.isActionGoods > 0}">
								<td class="text-left">
									<div class="customername pos_rel">
										<div class="brand-color1"><span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${asv.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${asv.goodsId}","title":"产品信息"}'><font color="red">[活动]</font>${asv.goodsName}
                              <i class="iconbluemouth"></i> <br>
                            </span></div>

										<div class="pos_abs customernameshow mouthControlPos JskuInfo">
<%--											注册证号：${asv.registrationNumber } <br>--%>
<%--											管理类别：${asv.manageCategoryName} <br>--%>
<%--											产品负责人：${asv.goodsHeader } <br>--%>
<%--											采购提醒：${asv.purchaseRemind} <br>--%>
<%--											包装清单：${asv.packingList} <br>--%>
<%--											服务条款：${asv.tos} <br>--%>
<%--											订单占用：${asv.orderOccupy}<br>--%>
<%--											可调剂：${asv.adjustableNum} <br>--%>
<%--											库存：${asv.goodsStock}--%>
										</div>
									</div>
								</td>
							</c:when>
							<c:otherwise>
								<td class="text-left">
									<div class="customername pos_rel">
										<div class="brand-color1"><span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${asv.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${asv.goodsId}","title":"产品信息"}'>${asv.goodsName}
                              <i class="iconbluemouth"></i> <br>
                            </span></div>

										<div class="pos_abs customernameshow mouthControlPos JskuInfo">
<%--											注册证号：${asv.registrationNumber } <br>--%>
<%--											管理类别：${asv.manageCategoryName} <br>--%>
<%--											产品负责人：${asv.goodsHeader } <br>--%>
<%--											采购提醒：${asv.purchaseRemind} <br>--%>
<%--											包装清单：${asv.packingList} <br>--%>
<%--											服务条款：${asv.tos} <br>--%>
<%--											订单占用：${asv.orderOccupy}<br>--%>
<%--											可调剂：${asv.adjustableNum} <br>--%>
<%--											库存：${asv.goodsStock}--%>
										</div>
									</div>
								</td>
							</c:otherwise>
						</c:choose>
                        <td class="JskuCode"> </td>
							<td class="JbrandName"> </td>
							<td  class="JskuModel"> </td>
							<td class="JmaterialCode"> </td>
                        <td>${asv.buyorderPrice}</td>
                        <td class="JskuUnit">${asv.unitName}</td>
                        <td class="warning-color1">${asv.num}</td>
                        <c:if test="${afterSalesVo.type==539 or afterSalesVo.type==540}">
							<c:set var="skurknum" value="${asv.sku}-${asv.rknum},${skurknum}"></c:set>
                        <td class="warning-color1">${asv.rknum}</td>
                        </c:if>
                        <td class="warning-color1">${asv.arrivalNum}</td>
                        <td class="warning-color1">
                           <c:choose>
                        		<c:when test="${asv.deliveryDirect==0}">
                        			普发
                        		</c:when>
                        		<c:when test="${asv.deliveryDirect==1}">
                        		            直发
                        		</c:when>
                        	</c:choose>
                        </td>
							<td>
								<c:if test="${afterSalesVo.type==539 or afterSalesVo.type==540}">
								<a class="addtitle" tabTitle='{"num":"addBarcode${afterSalesVo.afterSalesId}_${asv.goodsId}","link":"./logistics/warehousein/addBarcode.do?goodsId=${asv.goodsId}&afterSalesId=${afterSalesVo.afterSalesId}&afterSalesGoodsId=${asv.afterSalesGoodsId}&type=2&businessType=${afterSales.businessType}&rknum=${asv.rknum}","title":"生成条形码"}'>生成条形码</a>
								</c:if>
								<c:if test="${afterSalesVo.type!=539 and afterSalesVo.type!=540}">
									<a class="addtitle" tabTitle='{"num":"addBarcode${afterSalesVo.afterSalesId}_${asv.goodsId}","link":"./logistics/warehousein/addBarcode.do?goodsId=${asv.goodsId}&afterSalesId=${afterSalesVo.afterSalesId}&afterSalesGoodsId=${asv.afterSalesGoodsId}&type=2&businessType=${afterSales.businessType}","title":"生成条形码"}'>生成条形码</a>
								</c:if>
                            &nbsp; &nbsp; &nbsp;
								<c:if test="${afterSalesVo.type==539 or afterSalesVo.type==540}">
                            <a class="addtitle" tabTitle='{"num":"addWarehouseIn${afterSalesVo.afterSalesId}_${asv.goodsId}","link":"./logistics/warehousein/addWarehouseIn.do?afterSalesId=${afterSalesVo.afterSalesId}&goodsId=${asv.goodsId}&afterSalesGoodsId=${asv.afterSalesGoodsId}&type=2&businessType=${afterSales.businessType}&rknum=${asv.rknum}","title":"入库"}'>入库</a>
								</c:if>
								<c:if test="${afterSalesVo.type!=539 and afterSalesVo.type!=540}">
									<a class="addtitle" tabTitle='{"num":"addWarehouseIn${afterSalesVo.afterSalesId}_${asv.goodsId}","link":"./logistics/warehousein/addWarehouseIn.do?afterSalesId=${afterSalesVo.afterSalesId}&goodsId=${asv.goodsId}&afterSalesGoodsId=${asv.afterSalesGoodsId}&type=2&businessType=${afterSales.businessType}","title":"入库"}'>入库</a>
								</c:if>
                        </td>
                    </tr>
                </c:forEach>
                	<c:if test="${empty afterSalesVo.afterSalesGoodsList}">
					  <tr>
                        <td colspan="12">暂无产品记录</td>
                      </tr>
                    </c:if>
                </tbody>
            </table>
            <!-- <div class="table-style4 " style="margin-left: 17px;">
				<div class="allchose">
				    <input type="checkbox" name="all_g_checknox" onclick="selectall(this,'');" value="g_checknox"> <span>全选</span>
				</div>
		   </div> -->
            <div class="table-buttons">
				<button type="button" class="bt-bg-style bg-light-blue bt-small" id="exportShBatchBarcode"
					onclick="exportShBatchBarcode('${afterSalesVo.afterSalesId}','${afterSalesVo.type}');">批量生成条码并导出</button>
                <c:if test="${afterSalesVo.type==539 or afterSalesVo.type==540}">
                <button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
					layerparams='{"width":"500px","height":"200px","title":"批量入库","link":"${pageContext.request.contextPath}/logistics/warehousein/batchAddWarehouseIn.do?orderId=${afterSalesVo.afterSalesId}&bussnissType=${afterSalesVo.type}&skurknum=${skurknum}"}'>批量入库</button>
				</c:if>
<c:if test="${afterSalesVo.type!=539 and afterSalesVo.type!=540}">
    <button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
	layerparams='{"width":"500px","height":"200px","title":"批量入库","link":"${pageContext.request.contextPath}/logistics/warehousein/batchAddWarehouseIn.do?orderId=${afterSalesVo.afterSalesId}&bussnissType=${afterSalesVo.type}"}'>批量入库</button>
</c:if>
	<button type="button" class="bt-bg-style bg-light-blue bt-small addtitle" tabTitle='{"num":"addBarcode${afterSalesVo.afterSalesId}_","link":"./logistics/warehousein/getWarehouseInBarcode.do?buyorderId=${afterSalesVo.afterSalesId}&type=2","title":"查看全部入库条码"}'>查看全部入库条码</button>
				<button class="bt-bg-style bg-light-blue bt-small addtitle"  id="searchSpan" tabTitle='{"num":"NoWarehouseIn<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./logistics/warehousein/printNoArrive.do?buyorderId=${afterSalesVo.afterSalesId}&type=${afterSalesVo.type}","title":"未到货商品"}'>打印未到货商品</button>
			</div>
        </div>
        
        <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">入库记录</div>
			</div>
			<form method="post" id="pringlog" action="${pageContext.request.contextPath}/logistics/filedelivery/saveFileDelivery.do">
			<table class="table">
				<thead>
					<tr>
						<th class="wid5">选择</th>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th>订货号</th>
						<th>品牌/型号</th>
						<th>物料编码</th>
						<th>单位</th>
						<th>贝登条码</th>
						<th>厂家条码</th>
						<th>入库数量</th>
						<th>批次号</th>
						<th>效期截止日期</th>
						<th class="wid12">操作人</th>
						<th class="wid15">入库时间</th>
						<th class="wid8">操作</th>
					</tr>
				</thead>
				<tbody>
				   <c:set var="flag" value="-1"></c:set>
					<c:forEach var="wlist" items="${wlog}" varStatus="num">
						<tr>
							<td>
							   <c:if test="${wlist.nums == 0}">
							    <c:set var="flag" value="1"></c:set>
								<c:if test="${wlist.isEnable == 1}">
	                            <input type="checkbox" name="warehouseGoodsOperateLogId" alt="<date:date value ="${wlist.addTime}" format="yyyy-MM-dd"/>" value="${wlist.warehouseGoodsOperateLogId}" onclick="canelAllsh(this,'warehouseGoodsOperateLogId')">
	                        	</c:if>
		                        <c:if test="${wlist.isEnable == 0}">
		                        </c:if>
		                        </c:if>
	                        </td>
							<td>${num.count}</td>
							<td class="text-left">
		                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${wlist.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${wlist.goodsId}","title":"产品信息"}'>${newSkuInfosMap[wlist.sku].SHOW_NAME}</a>
							</td>
							<td>${newSkuInfosMap[wlist.sku].SKU_NO}</td>
							<td>${newSkuInfosMap[wlist.sku].BRAND_NAME}/${newSkuInfosMap[wlist.sku].MODEL}</td>
							<td>${newSkuInfosMap[wlist.sku].MATERIAL_CODE}</td>
							<td>${newSkuInfosMap[wlist.sku].UNIT_NAME}</td>
							<td>${wlist.barcode}</td>
							<td>${wlist.barcodeFactory}</td>
							<td>${wlist.num}</td>
							<td>${wlist.batchNumber}</td>
							<td><date:date value ="${wlist.expirationDate}" format="yyyy-MM-dd"/></td>
							<td>${wlist.operator}</td>
							<td><date:date value ="${wlist.addTime}" format="yyyy-MM-dd"/></td>
							<td>
								 <c:if test="${wlist.nums == 0}">
								 <span class="bt-smaller bt-border-style border-red" onclick="cancelWlog('${wlist.warehouseGoodsOperateLogId}','2')">撤销入库</span>
								 </c:if>
							      <c:if test="${wlist.nums != 0}">
									 不可操作
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${!empty wlog}">
                    <tr>
                        <td colspan="15" class="allchosetr text-left">
                        		<!-- 总数量 -->
                        		<c:set var="allnum" value="0"></c:set>
                        		<c:set var="allwarehouseinnum" value="0"></c:set>
	                        	<c:forEach var="buyorderGood" items="${afterSalesVo.afterSalesGoodsList}">
	                        	 <c:if test="${afterSalesVo.type==539 or afterSalesVo.type==540}">
	                        	     <c:set var="allnum" value="${allnum + buyorderGood.rknum}"></c:set>
	                        	 </c:if>
	                        	 <c:if test="${afterSalesVo.type!=539 && afterSalesVo.type!=540}">
	                        	     <c:set var="allnum" value="${allnum + buyorderGood.num}"></c:set>
	                        	 </c:if>
		                        	<c:set var="allwarehouseinnum" value="${allwarehouseinnum + buyorderGood.arrivalNum}"></c:set>
	                        	</c:forEach>
	                        	
                            	已入库/商品总数:<span class="warning-color1">${allwarehouseinnum}/${allnum}</span>
                        </td>
                    </tr>
                   </c:if>
					<c:if test="${empty wlog}">
					  <tr>
                        <td colspan="15">暂无入库记录</td>
                      </tr>
                    </c:if>

				   <c:if test="${not empty wlog}">
					   <tags:page page="${page}" />
				   </c:if>

				</tbody>
			</table>
			<c:if test="${flag eq 1}">
			<c:if test="${!empty wlog}">
			<div class="table-style4 ">
				<div class="allchose">
                   <input type="checkbox" name="all_warehouseGoodsOperateLogId" onclick="selectallsh(this,'pick_checkbox_in');" value="warehouseGoodsOperateLogId"> <span>全选</span>
                </div>
               <div class="times">
					<span class="mr10">请选择批次</span>
					<c:forEach var="tlist" items="${timeArrayrk}" varStatus="num">
                     <input type="checkbox" name="${tlist}" id="pick_checkbox_in" onclick="checkbarcodesh('${tlist}', this.checked,'warehouseGoodsOperateLogId');">
                     <span class="mr20">${tlist}</span>
                    </c:forEach>
				</div>
				<div class="print-record">
					<span class="bt-bg-style bg-light-blue" onclick="printInOrderById('warehouseGoodsOperateLogId','0', 'in_searchc');" id="searchSpan">打印入库单</span>
					<span class="bt-bg-style bg-light-red" onclick="cancelHhAll('2')">撤销入库</span>
				</div>
			</div>
			</c:if>
			</c:if>
			</form>
			<form method="post" id="in_searchc" action="<%= basePath %>logistics/warehousein/printInOrder.do">
				<input type="hidden"  name="orderId" id="orderId" value="${afterSalesVo.orderId }"/>
		    	<input type="hidden"  name="bussinessNo" id="bussinessNo" value="${afterSalesVo.afterSalesNo }"/>
		    	<input type="hidden"  name="bussinessType" id="bussinessType" value="${afterSalesVo.type }"/>
		    	<input type="hidden"  name="wdlIds" id="in_wdlIds" value=""/>
				<input type="hidden"  name="type_f" id="in_type_f" value=""/>
			</form>
		</div>
		
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 物流信息
                </div>
                <div class="title-click nobor addtitle" tabTitle='{"num":"addExpress","link":"./aftersales/order/addExpress.do?afterSalesId=${afterSalesVo.afterSalesId}&flag=1&businessType=${afterSales.businessType}","title":" 新增快递"}'>
                   	 新增快递
                </div>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid10">快递单号</th>
                        <th class="wid10">快递公司</th>
                        <th class="wid10">发货时间</th>
                        <th class="wid8">运费</th>
                        <th>商品</th>
                        <th>备注</th>
                        <!-- <th class="wid10">操作人</th>
                        <th class="wid10">快递状态</th> -->
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="express" items="${expressList}">
                     <tr>
                        <td>${express.logisticsNo}</td>
                        <td>${express.logisticsName}</td>
                        <td><date:date value ="${express.deliveryTime}" format="yyyy-MM-dd"/></td>
                        <td>
                        	<c:set var="amount" value="0.00"></c:set>
                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
                        		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
                        	</c:forEach>
                        	${amount}
                        </td>
                        <td class="text-left">
                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
                            	<div>${expressDetails.goodName}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num} ${expressDetails.unitName}</div><br/>
                            </c:forEach>
                        </td>
                        <td>${express.logisticsComments}</td>
                        <!--<td>${express.updaterUsername}</td>
                        <td>
                        	<c:if test="${express.arrivalStatus == 0}">
                        		未收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 1}">
                        		部分收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 2}">
                        		全部收货
                        	</c:if>
                        </td>-->
                         <td>
                               <%-- <c:if test="${afterSales.type==546 or afterSales.type == 547}">
                                <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',2)">打印</span>
                                <c:if test="${express.arrivalStatus != 2}">
                                <span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./warehouse/warehousesout/editExpress.do?expressId=${express.expressId}&bussinessId=${afterSales.afterSalesId }&bussinessType=2&flag=1&shType=${afterSales.type }","title":" 编辑快递"}'>编辑</span>
                                </c:if>
                               </c:if> --%>
                                <c:if test="${afterSales.type==539 or afterSales.type==540}">
                                 <c:if test="${afterSales.type==540}">
                                <%--  <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',4,'${express.expressId}','1','${afterSales.afterSalesId }')">打印</span>
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','2','${afterSales.orderId }',4,'${express.expressId}','1','${afterSales.afterSalesId }'">打印直发</span> --%>
                                 </c:if>
                                 <c:if test="${express.arrivalStatus != 2}">
                                 <span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./aftersales/order/editExpress.do?expressId=${express.expressId}&afterSalesId=${afterSalesVo.afterSalesId }&flag=1&shType=${afterSales.type }&businessType=1","title":" 编辑快递"}'>编辑</span>
                                 </c:if>
                               </c:if>
                               <span class="bt-smaller bt-border-style border-red" onclick="delwl('${express.expressId}')">删除</span>
                          </td>
                    </tr>
                     </c:forEach>
                     <c:if test="${!empty expressList}">
                    <tr>
                        <td colspan="7" class="allchosetr text-left">
                        		<!-- 总运费 -->
                        		<c:set var="allamount" value="0.00"></c:set>
                        		<!-- 总数量 -->
                        		<c:set var="allarrivalnum" value="0"></c:set>
	                        	<c:forEach var="express" items="${expressList}">
	                        		<c:set var="amount" value="0.00"></c:set>
	                        		<c:set var="arrivalnum" value="0"></c:set>
		                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
		                        		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
		                        		<c:set var="arrivalnum" value="${arrivalnum + expressDetails.num}"></c:set>
		                        	</c:forEach>
		                        	<c:set var="allamount" value="${allamount + amount}"></c:set>
		                        	<c:set var="allarrivalnum" value="${allarrivalnum + arrivalnum}"></c:set>
	                        	</c:forEach>
	                        	<c:set var="allnum" value="0"></c:set>
	                        	<c:forEach var="bgv" items="${afterSalesVo.afterSalesGoodsList}" varStatus="num">
		                        		<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
		                        </c:forEach>
                            	 运费总额：<span class="warning-color1 mr10">${allamount}</span>
                        </td>
                    </tr>
                   </c:if>
                    <c:if test="${empty expressList}">
                     <tr>
                        <td colspan="7">暂无物流信息记录</td>
                    </tr>
                   </c:if>
                   
                </tbody>
            </table>
        </div>
        
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/viewWarehouseIn.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/storageAftersales/viewWarehouseIn.js?rnd=<%=Math.random()%>'></script>

<script type="text/javascript" src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
