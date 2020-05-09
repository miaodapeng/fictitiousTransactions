<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品外借出库" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="customer">
	<ul>
		<li><a href="/warehouse/warehousesout/index.do">销售出库</a></li>
		<li><a href="/warehouse/businessWarehouseOut/changeIndex.do">采购售后出库</a>
		</li>
		<li><a href="/warehouse/businessWarehouseOut/index.do">销售售后出库</a>
		</li>
		<li><a href="/warehouse/warehousesout/lendOutIndex.do"
			class="customer-color">商品外借出库</a></li>
	</ul>
</div>
<div class="form-list  form-tips8">
	<form method="post" action="/warehouse/warehousesout/saveAddLendOutInfo.do" class="J-form" id="">
		<ul>
			<li>
				<div class="form-tips">
					<span id="lno">*</span>
					<lable>借用原因</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<label> <input type="radio" class="input-middle J-type"
							checked value="1" name="lendOutType"/><span>样品外借 </span>
						</label> 
						<label> <input type="radio" class="input-middle J-type"
							value="2" name="lendOutType"/><span>售后垫货 </span>
						</label>
					</div>
				</div>
			</li>
			<div class="J-block1" style="display: none;">
				<li>
					<div class="form-tips">
						<span id="lno">*</span>
						<lable>借用企业</lable>
					</div> 
					<span class=" mr10 mt3" id="trader_name_span_1">${traderName}</span>
					<span class="bt-bg-style bt-small bg-light-blue pop-new-data"
					layerParams='{"width":"800px","height":"300px","title":"选择客户","link":"${pageContext.request.contextPath}/trader/customer/searchCustomerList.do?indexId=1&lendOut=1&searchTraderName="}'>选择客户</span>
					<div id="traderIdMsg" style="clear:both"></div>
				</li>
				<li>
					<div class="form-tips">
						<span id="lno">*</span>
						<lable>产品名称</lable>
					</div> 
					<span class=" mr10 mt3" id="goods_name_span_1" name="goodsName">${goodsName}</span>
					<span class="bt-bg-style bt-small bg-light-blue  pop-new-data"
					layerParams='{"width":"800px","height":"300px","title":"添加产品","link":"/order/saleorder/addSaleorderGoods.do?lendOut=1&saleorderId=${saleorder.saleorderId}"}'>选择产品</span>
					<div id="goodsIdMsg" style="clear:both"></div>
				</li>
				<div class="form-tips-show">
					<li>
						<div class="form-tips">
							<lable>库存量</lable>
						</div> <span class=" mr10 mt3" id="goods_num_span_1">${goodsStock}</span>
					</li>
					<li>
						<div class="form-tips">
							<span id="lno">*</span>
							<lable>借用数量</lable>
						</div> <input type="text" name="lendOutNum" id="lendoutnum1" value=""
						height="100px">
						<div id="lendOutNumMsg" style="clear:both"></div>
					</li>
					<li>
						<div class="form-tips">
							<span id="lno">*</span>
							<lable>预计归还时间</lable>
						</div> <input class="Wdate f_left input-smaller96" type="text"
						placeholder="请选择日期"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate: '%y-%M-%d'})"
						name="returnTimeStr" id="searchEndtimeStr" value=''>
						<div id="searchEndtimeStrMsg" style="clear:both"></div>
					</li>
				</div>
				<input type="hidden" id="goods_id_span_1" value="${goodsId}" name="goodsId"/>
			</div>
			<div class="J-block2" style="display: none;">
				<li>
					<div class="form-tips">
						<span id="lno">*</span>
						<lable>产品名称</lable>
					</div>
					<span class=" mr10 mt3" id="goods_name_span_2" name="goodsName">${goodsName}</span>
					<span class="bt-bg-style bt-small bg-light-blue  pop-new-data"
					layerParams='{"width":"800px","height":"300px","title":"关联售后单","link":"/aftersales/order/searchAfterOrder.do?lendOut=1"}'>选择产品</span>
					<div id="afterNoMsg" style="clear:both"></div>
				</li>
				<div class="form-tips-show_2">
					<li>
						<div class="form-tips">
							<lable>关联售后单</lable>
						</div> <span class=" mr10 mt3" id="goods_aftersalesno_span_2" name="afterSalesNo">${afterSalesNo}</span>
					</li>
					<li>
						<div class="form-tips">
							<lable>待重发数量</lable>
						</div> <span class=" mr10 mt3" id="goods_num_span_2">${num}</span>
					</li>
					<li>
						<div class="form-tips">
							<lable>库存量</lable>
						</div> <span class=" mr10 mt3" id="goods_stock_span_2">${goodsStockNum}</span>
					</li>
					<li>
						<div class="form-tips">
							<span id="lno">*</span>
							<lable>借用数量</lable>
						</div> <input type="text" name="lendOutNum" id="lendoutnum2" value=""
						height="100px">
						<div id="lendOutNumMsg2" style="clear:both"></div>
					</li>
					<li>
						<div class="form-tips">
							<span id="lno">*</span>
							<lable>预计归还时间</lable>
						</div> <input class="Wdate f_left input-smaller96" type="text"
						placeholder="请选择日期"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate: '%y-%M-%d'})"
						name="returnTimeStr" id="searchEndtimeStr2" value=''>
						<div id="searchEndtimeStrMsg2" style="clear:both"></div>
					</li>
				</div>
				<input type="hidden" id="goods_id_span_2" value="${goodsId}"  name="goodsId"/>
			</div>
		</ul>
		<div class="add-tijiao">
			<input type="hidden" id="trader_id_span_1" value="${traderId}" name="traderId"/> 
			<input type="hidden" id="goods_sku_span_1" value="${sku}" name="sku"/> 
			<input type="hidden" id="goods_aftersalesno_span_val2" value="${afterSalesNo}" name="afterSalesNo"/> 
			<input type="hidden" id="trader_type_span_1" value="${traderType}" name="traderType"/> 
			<input type="hidden" name="formToken" value="${formToken}"/>
			<button type="button" class="bt-bg-style bg-deep-green"
				onclick="savelendout();">开始出库</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseOut/lendOutIndex.js?rnd=<%=Math.random()%>'></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>