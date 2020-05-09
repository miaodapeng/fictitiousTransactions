<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购入库" scope="application" />
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
                        <td class="wid20">采购单号</td>
                        <td><a class="addtitle" href="javascript:void(0);" tabtitle="{&quot;num&quot;:&quot;viewbuyorder${buyorderInfo.buyorderId}&quot;,
							&quot;link&quot;:&quot;./order/buyorder/viewBuyordersh.do?buyorderId=${buyorderInfo.buyorderId}&quot;,&quot;title&quot;:&quot;采购订单信息&quot;}">
								${buyorderInfo.buyorderNo}
							</a>
                        </td>
                        <td class="wid20">生效时间</td>
                        <td><date:date value="${buyorderInfo.validTime}" /></td>
                    </tr>
                    <tr>
                    	<td class="wid20">产品部门</td>
                        <td>${buyorderInfo.buyDepartmentName}</td>
                        <td class="wid20">归属</td>
                        <td>${buyorderInfo.buyPerson}</td>
                    </tr>
                     <tr>
                    	<td class="wid20">订单总额</td>
                        <td><fmt:formatNumber type="number" value="${buyorderInfo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td class="wid20">到货状态</td>
                        <td>
                        	<c:choose>
                        		<c:when test="${buyorderInfo.arrivalStatus==0}">
                        		<font color="red">
                        			未到货
                        		</font>
                        		</c:when>
                        		<c:when test="${buyorderInfo.arrivalStatus==1}">
                        		<font color="red">
                        			部分到货
                        		</font>
                        		</c:when>
                        		<c:when test="${buyorderInfo.arrivalStatus==2}">
                        			全部到货
                        		</c:when>
                        	</c:choose>
                        </td>
                    </tr>
                    <tr>
                    	<td class="wid20">指定物流公司</td>
                        <td>${buyorderInfo.logisticsName}</td>
                        <td class="wid20">运费说明</td>
                        <td>${buyorderInfo.freightDes}</td>
                    </tr>
                     <tr>
                    	<td class="wid20">物流备注</td>
                        <td colspan="3"><font color="red">${buyorderInfo.logisticsComments}</font></td>
                    </tr>
                    <tr>
                    	<td class="wid20">补充条款</td>
                        <td colspan="3"><font color="red">${buyorderInfo.additionalClause}</font></td>
                    </tr>
                </tbody>
            </table>
        </div>
        
         <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
					供应商信息
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">供应商</td>
                        <td>${buyorderInfo.traderName}</td>
                        <td class="wid20"></td>
                        <td></td>
                    </tr>
                    <tr>
                    	<td class="wid20">供应商地址</td>
                        <td colspan="3">${buyorderInfo.traderAddress}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	产品信息
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                    	<th class="wid5">选择</th>
                        <th class="wid5">序号</th>
                        <th>产品名称</th>
                        <th>订货号</th>
                        <th>物料编码</th>
                        <th>品牌/型号</th>
                        <th class="wid4">单位</th>
                        <th class="wid8">采购总数</th>
                        <th class="wid8">已入库</th>
                        <th>操作</th>
                        <th class="wid14">关联销售单号</th>
                        <th>采购备注</th>
                        <th class="wid8">注册证号</th>
                        <th>生产企业</th>
                        <th>生产许可证</th>
                    </tr>
                </thead>
                <tbody>
                <c:set var="flag" value="0"></c:set>
                <c:forEach var="bgv" items="${buyorderInfo.buyorderGoodsVoList}" varStatus="num">
                    <tr>
                    	<td>
                    			<c:if test="${bgv.num != bgv.arrivalNum}">
                    				<c:set var="flag" value="1"></c:set>
		                            <input type="checkbox" name="buyorderGoodsId"  value="${bgv.buyorderGoodsId}" onclick="canelAll(this,'buyorderGoodsId')">
	                        	</c:if>
		                        <c:if test="${bgv.num == bgv.arrivalNum}">
		                        </c:if>
	                        	
	                    </td>
                        <td>${num.count}</td>
                        <td class="text-left">
                            <div class="brand-color1"><span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${bgv.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[bgv.sku].SHOW_NAME}</span></div>
                        </td>
                        <td>${newSkuInfosMap[bgv.sku].SKU_NO}</td>
                        <td>${newSkuInfosMap[bgv.sku].MATERIAL_CODE}</td>
                        <td>
                        	<div>${newSkuInfosMap[bgv.sku].BRAND_NAME}</div>
                            <div>${newSkuInfosMap[bgv.sku].MODEL}</div>
                        </td>
                        <td>${newSkuInfosMap[bgv.sku].UNIT_NAME}</td>
                        <td>${bgv.num}</td>
                        <td>${bgv.arrivalNum}</td>
                        <td>
                        	<div><a class="addtitle" tabTitle='{"num":"addBarcode${buyorderInfo.buyorderId}_${bgv.buyorderGoodsId}","link":"./logistics/warehousein/addBarcode.do?goodsId=${bgv.goodsId}&buyorderId=${buyorderInfo.buyorderId}&buyorderGoodsId=${bgv.buyorderGoodsId}&type=1","title":"生成条形码"}'>生成条形码</a></div>
                        	<br/>
                            <div><a class="addtitle" tabTitle='{"num":"addWarehouseIn${buyorderInfo.buyorderId}_${bgv.buyorderGoodsId}","link":"./logistics/warehousein/addWarehouseIn.do?buyorderId=${buyorderInfo.buyorderId}&goodsId=${bgv.goodsId}&buyorderGoodsId=${bgv.buyorderGoodsId}&type=1","title":"入库"}'>入库</a></div>
                        </td>
                        <td class="J-order-wrap">
							<c:forEach var="saleorderGoods" items="${bgv.saleorderGoodsVoList}">
								<div>
									<c:set var="type" value="${fn:substring(saleorderGoods.saleorderNo, 0, 2)}"></c:set>
									<c:if test="${type=='VS' or type=='DH'or type=='JX' or type=='HC' or type=='BD'}">
										<a class="addtitle J-order-item" href="javascript:void(0);" tabTitle='{"num":"warehousesout<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./warehouse/warehousesout/detailJump.do?saleorderId=${saleorderGoods.saleorderId}","title":"出库详情"}'>${saleorderGoods.saleorderNo}</a>
									</c:if>
									<c:if test="${type=='BH'}">
										<a class="addtitle J-order-item" href="javascript:void(0);" tabtitle="{&quot;num&quot;:&quot;order_saleorder_viewbhsaleorder${saleorderGoods.saleorderId}&quot;,&quot;link&quot;:&quot;./order/saleorder/viewBhSaleorder.do?saleorderId=${saleorderGoods.saleorderId}&quot;,&quot;title&quot;:&quot;备货信息&quot;}">
											${saleorderGoods.saleorderNo}
										</a>
									</c:if>
								</div>
							</c:forEach>
						</td>
						<td>${bgv.insideComments}</td>
                        <td>${bgv.registrationNumber}</td>
                        <td>${bgv.manufacturer}</td>
                        <td>${bgv.productionLicense}</td>
                    </tr>
                </c:forEach>
                	<c:if test="${empty buyorderInfo.buyorderGoodsVoList}">
					  <tr>
                        <td colspan="16">暂无产品记录</td>
                      </tr>
                    </c:if>
                </tbody>
            </table>
            <c:if test="${!empty buyorderInfo.buyorderGoodsVoList && flag==1}">
			<div class="table-style4 ">
				<div class="allchose">
					<input type="checkbox" name="allcheck_buyorderGoodsId" onclick="selectall(this,'buyorderGoodsId');"> <span>全选</span>
				</div>
				<div class="print-record">
				</div>
			</div>
			</c:if>
            <div class="table-buttons">
            	<c:if test="${!empty buyorderInfo.buyorderGoodsVoList && buyorderInfo.buyorderGoodsVoList.size()>0}">
				<button type="button" class="bt-bg-style bg-light-blue bt-small" id="exportBatchBarcode"
					onclick="exportBatchBarcode();">批量生成条码并导出</button>
				<button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
					layerparams='{"width":"500px","height":"200px","title":"批量入库","link":"./batchAddWarehouseIn.do?orderId=${buyorderInfo.buyorderId}&bussnissType=0"}'>批量入库</button>
				<button type="button" class="bt-bg-style bg-light-blue bt-small addtitle" tabTitle='{"num":"addBarcode${buyorderInfo.buyorderId}_${bgv.buyorderGoodsId}","link":"./logistics/warehousein/getWarehouseInBarcode.do?buyorderId=${buyorderInfo.buyorderId}&type=1","title":"查看全部入库条码"}'>查看全部入库条码</button>
				<button class="bt-bg-style bg-light-blue bt-small addtitle"  id="searchSpan" tabTitle='{"num":"NoWarehouseIn<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./logistics/warehousein/printNoArrive.do?buyorderId=${buyorderInfo.buyorderId}&type=0","title":"未到货商品"}'>打印未到货商品</button>
				</c:if>
			</div>
        </div>
        
        <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">入库记录</div>
			</div>
			<form method="post" id="inSearchc" action="<%= basePath %>logistics/warehousein/printInOrder.do">
			<table class="table">
				<thead>
					<tr>
						<th class="wid5">选择</th>
						<th class="wid5">序号</th>
						<th>产品名称</th>
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
	                            <input type="checkbox" name="warehouseGoodsOperateLogId" alt="<date:date value ="${wlist.addTime}" format="yyyy.MM.dd"/>" value="${wlist.warehouseGoodsOperateLogId}" onclick="canelAll(this,'warehouseGoodsOperateLogId')">
	                        	</c:if>
		                        <c:if test="${wlist.isEnable == 0}">
		                        </c:if>
							    </c:if>
	                        </td>
							<td>${num.count}</td>
							<td>${warehouseGoodsOperateLogMap[wlist.sku].SHOW_NAME}</td>
							<td>${warehouseGoodsOperateLogMap[wlist.sku].BRAND_NAME}/${warehouseGoodsOperateLogMap[wlist.sku].MODEL}</td>
							<td>${warehouseGoodsOperateLogMap[wlist.sku].MATERIAL_CODE}</td>
							<td>${warehouseGoodsOperateLogMap[wlist.sku].UNIT_NAME}</td>
							<td>${wlist.barcode}</td>
							<td>${wlist.barcodeFactory}</td>
							<td>${wlist.num}</td>
							<td>${wlist.batchNumber}</td>
							<td><date:date value ="${wlist.expirationDate}" format="yyyy.MM.dd"/></td>
							<td>${wlist.operator}</td>
							<td><date:date value ="${wlist.addTime}" format="yyyy.MM.dd"/></td>
							<td>
							 <c:if test="${wlist.nums == 0}">
							 <span class="bt-smaller bt-border-style border-red" onclick="cancelWlog('${wlist.warehouseGoodsOperateLogId}','1')">撤销入库</span>
							 </c:if>
							 <c:if test="${wlist.nums != 0}">
							 不可操作
							 </c:if>
							</td>
						</tr>
					</c:forEach>
					 <c:if test="${!empty wlog}">
                    <tr>
                        <td colspan="14" class="allchosetr text-left">
                        		<!-- 总数量 -->
                        		<c:set var="allnum" value="0"></c:set>
                        		<c:set var="allwarehouseinnum" value="0"></c:set>
	                        	<c:forEach var="buyorderGood" items="${buyorderInfo.buyorderGoodsVoList}">
	                        		<c:set var="allnum" value="${allnum + buyorderGood.num}"></c:set>
		                        	<c:set var="allwarehouseinnum" value="${allwarehouseinnum + buyorderGood.arrivalNum}"></c:set>
	                        	</c:forEach>
	                        	
                            	已入库/商品总数:<span class="warning-color1">${allwarehouseinnum}/${allnum}</span>
                        </td>
                    </tr>
                   </c:if>
					<c:if test="${empty wlog}">
					  <tr>
                        <td colspan="14">暂无入库记录</td>
                      </tr>
                    </c:if>
				</tbody>
			</table>
			<c:if test="${flag eq 1}">
			<c:if test="${!empty wlog}">
			<div class="table-style4 ">
				<div class="allchose">
					<input type="checkbox" name="allcheck_warehouseGoodsOperateLogId"onclick="selectall(this,'warehouseGoodsOperateLogId');"> <span>全选</span>
				</div>
				<div class="times">
					<span class="mr10">请选择批次</span>
					<c:forEach var="tlist" items="${timeArray}" varStatus="num">
                     <input type="checkbox" name="${tlist}" class="warehouseGoodsOperateLogId" onclick="checkbarcode('${tlist}', this.checked);">
                     <span class="mr20">${tlist}</span>
                    </c:forEach>
				</div>
				<div class="print-record">
					<span class="bt-bg-style bg-light-blue" onclick="printInOrderById('warehouseGoodsOperateLogId','0', 'inSearchc');" id="searchSpan">打印入库单</span>
					<span class="bt-bg-style bg-light-red" onclick="cancelWlogAll('1')">撤销入库</span>
				</div>
			</div>
			</c:if>
			</c:if>
			
				<input type="hidden" name="formToken" id="formToken" value="${formToken}"/>
				<input type="hidden"  name="buyorderId" id="buyorderId" value="${buyorderInfo.buyorderId}"/>
				<input type="hidden"  name="buyorderNo" id="buyorderNo" value="${buyorderInfo.buyorderNo}"/>
				<input type="hidden"  name="wdlIds" id="in_wdlIds" value=""/>
				<input type="hidden"  name="type_f" id="in_type_f" value=""/>
			</form>
		</div>
		
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 物流信息
                </div>
                <div class="title-click nobor addtitle" tabTitle='{"num":"addExpress","link":"./logistics/warehousein/addExpress.do?buyorderId=${buyorderInfo.buyorderId}","title":" 新增快递"}'>
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
                            <span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./logistics/warehousein/editExpress.do?expressId=${express.expressId}&buyorderId=${buyorderInfo.buyorderId}","title":" 编辑快递"}'>编辑</span>
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
	                        	<c:forEach var="bgv" items="${buyorderInfo.buyorderGoodsVoList}" varStatus="num">
		                        		<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
		                        </c:forEach>
                            	 运费总额：<span class="warning-color1 mr10">${allamount}</span>已入库/商品总数:<span class="warning-color1">${allarrivalnum}/${allnum}</span>
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
       	<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 入库附件
                </div>
                <div class="title-click nobor  pop-new-data" layerparams='{"width":"520px","height":"200px","title":"入库附件","link":"./contractReturnInit.do?buyorderId=${buyorderInfo.buyorderId}"}' >
                   	 新增附件
                </div>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid6">序号</th>
                        <th>附件名称</th>
                        <th>操作人</th>
                        <th>上传时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${not empty AttachmentList }">
                		<c:forEach var="att" items="${AttachmentList}" varStatus="status">
                			<tr>
	                			<td>${status.count}</td>
	                			<td><a href="http://${att.domain}${att.uri}" target="_blank">${att.name}</a></td>
	                			<td>${att.username}</td>
	                			<td><date:date value ="${att.addTime}"/></td>
	                			<td>
		                			<span class="forbid clcforbid" onclick="contractReturnDel(${att.attachmentId})">删除</span>
		                		</td>
	                		</tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty AttachmentList }">
                		<td colspan="5">暂无入库附件记录</td>
                	</c:if>
                </tbody>
           </table>
       </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/viewWarehouseIn.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
