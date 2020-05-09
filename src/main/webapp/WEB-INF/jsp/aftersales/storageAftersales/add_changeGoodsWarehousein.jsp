<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="换货入库" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container logistics-warehousein-addWarehouseIn">
	<form method="post" id="addWarehouseIn"
		action="${pageContext.request.contextPath}/logistics/warehousein/saveWarehouseIn.do">
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">产品信息</div>
			</div>
			<table class="table">
				<tbody>
					<tr>
						<td class="wid20">产品名称</td>
						<td><span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${afterSalesGoodsInfo.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${afterSalesGoodsInfo.goodsId}","title":"产品信息"}'>${afterSalesGoodsInfo.goodsName}</span></td>
						<td class="wid20">订货号</td>
						<td>${afterSalesGoodsInfo.sku}</td>
					</tr>
					<tr>
						<td class="wid20">品牌</td>
						<td>${afterSalesGoodsInfo.brandName}</td>
						<td class="wid20">型号</td>
						<td>${afterSalesGoodsInfo.model}</td>
					</tr>
					<tr>
						<td class="wid20">数量</td>
						<td><font color="red">${afterSalesGoodsInfo.num}</font></td>
						<td class="wid20">单位</td>
						<td>${afterSalesGoodsInfo.unitName}</td>
					</tr>
					<tr>
						<td class="wid20">未入库数量</td>
						<td>${afterSalesGoodsInfo.num-wNum}</td>
						<td class="wid20"></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="parts bar-code-infor">
			<div class="title-container">
				<div class="table-title nobor">选择库位</div>
			</div>
			<table class="table">
				<tbody>
					<tr>
						<td>
							
							<div class="form-list  form-tips4">
					            <ul  style="margin-bottom:0px;">
					                <li>
					                    <div class="form-tips">
					                        <lable>存储位置</lable>
					                    </div>
					                    <div class="f_left ">
					                        <div class="form-blanks">
					                            <select class="wid25" name="warehouses" onchange="changeComment(this)" id="warehouses">
					                               <c:if test="${not empty warehouseGoodsSetList }">
														<c:forEach items="${warehouseGoodsSetList }" var="warehouse" varStatus="num">
															<option value="${warehouse.wareHouseId}_${warehouse.storageroomId}_${warehouse.storageAreaId}_${warehouse.storageRackId}_${warehouse.storageLocationId}" alt="${warehouse.comments}"
															<c:if test="${num.count==1}">selected</c:if>
															>
																${warehouse.wareHouseName} ${warehouse.storageroomName} ${warehouse.storageAreaName} ${warehouse.storageRackName} ${warehouse.storageLocationName}
															</option>
														</c:forEach>
													</c:if>
					                            </select>
					                        </div>
					                    </div>
					                </li>
					                 <li style="margin-bottom:0px;">
					                    <div class="form-tips">
					                        <lable>仓存备注</lable>
					                    </div>
					                    <div class="f_left ">
					                        <div class="form-blanks">
					                        <c:if test="${not empty warehouseGoodsSetList }">
						                        <c:forEach items="${warehouseGoodsSetList }" var="warehouse"  varStatus="num">
							                        <c:if test="${num.count==1}">
							                          <input class="input-largest"  name="comments" id="comments" type="text" value="${warehouse.comments}"/>
							                        </c:if>
						                        </c:forEach>
					                        </c:if>
					                        <c:if test="${empty warehouseGoodsSetList }">
					                            <input class="input-largest" name="comments" id="comments" type="text" />
					                        </c:if>
					                        </div>
					                    </div>
					                </li>
					               <!--  <input type="hidden" name="comments" value=""> -->
					            </ul>
    						</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">请输入条码</div>
			</div>
			<table class="table" id="barcode_table">
				<thead>
					<tr>
						<th class="wid5">序号</th>
						<th>贝登条码</th>
						<th>厂家条码</th>
						<th>入库数量</th>
						<th>批次号</th>
						<th>生产日期</th>
						<th>效期截止日期</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td><input type="text" name="barcode" id= "barcode"
							onchange="checkBarcode(this)" onblur="checkCF(this)"/> <input type="hidden"
							name="barcodeId" /></td>
						<td><input type="text" name="barcodeFactory" onchange="checkbarcodeFactory(this)"></td>
						<td><input type="text" name="num2" onchange="checkNum(this)" disabled="true">
						    <input type="hidden" name="num" >
						</td>
						<td><input type="text" name="batchNumber" onchange="checkbatchNumber(this)"></td>
						<td><input class="Wdate  input-smaller96 "
								   name="productDate" type="text" placeholder="请选择日期"
								   onClick="WdatePicker({minDate:'1970-01-01'})"></td>
						<td><input class="Wdate  input-smaller96 "
							name="expirationDate" type="text" placeholder="请选择日期"
							onClick="WdatePicker({minDate:'%y-%M-%d'})"></td>
					</tr>
				</tbody>
			</table>
			<div class="table-buttons">
				<input type="hidden" name="rknum" id="rknum" value="${rknum }"/>
			     <input type="hidden" name="ywtype" id="ywtype" value="3"/>
			      <input type="hidden" name="businessType" id="businessType" value="${businessType }"/>
				<input type="hidden" id="bNum" value="${afterSalesGoodsInfo.num}"/>
				<input type="hidden" id="wNum" value="${wNum}"/>
				<input type="hidden" name="buyorderId" value="${afterSalesGoodsInfo.afterSalesId}"/>
				<input type="hidden" name="warehouseInNum" value="" /> <input
					type="hidden" name="buyorderGoodsId" id="buyorderGoodsId"
					value="${afterSalesGoodsInfo.afterSalesGoodsId}" /> <input
					type="hidden" name="goodsId" id="goodsId"
					value="${afterSalesGoodsInfo.goodsId}" />
					<input type="hidden" name="formToken" value="${formToken}"/>
				<button type="button" class="bt-bg-style bg-light-blue bt-small"
					onclick="addWarehouseIn();">继续添加</button>
				<button type="button" class="bt-bg-style bg-light-blue bt-small"
					onclick="submitWarehoseIn();">确认入库</button>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/addWarehouseInNew.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
