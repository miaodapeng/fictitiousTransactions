<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑产品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/saleorder/edit_saleorder_goods.js?rnd=<%=Math.random()%>'></script>
	<div class=" formpublic" id="confirmGoodsDiv">
		<form id="saleorderFormalGoodsForm">
			<ul class="lastResult">
				<li>
					<div class="infor_name mt0">
						<span>*</span> <label>产品名称</label>
					</div>
					<div class="f_left table-largest content1">
						<div class="">
							<span class="font-blue mr10 productname2" id="confirmGoodsName">${newSkuInfo.SHOW_NAME}</span>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>品牌/型号</label>
					</div>
					<div class="f_left" id="confirmGoodsBrandNameModel">${newSkuInfo.BRAND_NAME}/${newSkuInfo.MODEL}</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>产品信息</label>
					</div>
					<div class="f_left" id="confirmGoodsContent">${saleorderGoods.sku}/${newSkuInfo.GOODS_LEVEL_NAME}/${newSkuInfo.PRODUCTMANAGER}</div>
					<span class="font-grey9 ml5">产品未审核通过时，不允许转化到订单中</span>
				</li>
				<li>
					<div class="infor_name ">
						<label>单价</label>
					</div>
					<div class="f_left">
						<input type="text" onchange="changePriceStatus()" class="input-middle mr5" name="price" id="price" onkeyup="confirmTotalMoney('price');" value="${saleorderGoods.price}"/>
						<font style="color: red" id="priceMsg"></font>
					</div>
					<input type="hidden" id="priceUpdateStatus" value="0">
					<input type="hidden" id="orderType" value="${orderType}">
					<input type="hidden" id="isCoupons" value="${saleorder.isCoupons}">
					<input type="hidden" id="actionId" value="${saleorder.actionId}">
					<input type="hidden" id="paymentStatus" value="${saleorder.paymentStatus}">
				</li>
				<li>
					<div class="infor_name ">
						<span>*</span> <label>数量</label>
					</div>
					<div class="f_left">
						<input <c:if test="${5 eq orderType }">disabled="disabled"</c:if> type="text" class="input-middle" name="num" id="num"	onkeyup="confirmTotalMoney('num');" value="${saleorderGoods.num}" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<span>*</span> <label>单位</label>
					</div>
					<div class="f_left" id="confirmUnitName">${newSkuInfo.UNIT_NAME}</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>总额</label>
					</div>
					<div class="f_left" id="confirmTotalMoney" >
					    <fmt:formatNumber type="number" value="${(saleorderGoods.price==null?0:saleorderGoods.price) * saleorderGoods.num}" pattern="0.00" maxFractionDigits="2"/>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>报价含安调</label>
					</div>
					<div class="f_left inputfloat inputfloatmb0">
						<ul>
							<li><input type="radio" name="installation" value="1" <c:if test="${saleorderGoods.haveInstallation eq 1}">checked</c:if>> <label>是</label></li>
							<li><input type="radio" name="installation" value="0" <c:if test="${saleorderGoods.haveInstallation eq 0}">checked</c:if>> <label>否</label></li>
						</ul>
						<input type="hidden" name="haveInstallation" id="haveInstallation" />
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>货期</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-middle mr5" name="deliveryCycle" id="deliveryCycle" value="${saleorderGoods.deliveryCycle}">
						<span class="font-grey9 mt4">核价参考货期：3-5天</span>
						<div id="deliveryCycleDiv"></div>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>是否直发</label>
					</div>
					<div class="f_left inputfloat inputfloatmb0">
						<ul>
							<li class="mt4"><input type="radio" name="deliveryDirectRad" value="0" <c:if test="${saleorderGoods.deliveryDirect eq 0}">checked</c:if>> <label>否</label></li>
							<li class="mt4"><input type="radio" name="deliveryDirectRad" value="1" <c:if test="${saleorderGoods.deliveryDirect eq 1}">checked</c:if>> <label>是</label></li>
							<li><input type="text" placeholder="请填写直发原因，含有直发商品的订单不允许提前采购" class="input-larger" name="deliveryDirectComments" id="deliveryDirectComments" value="${saleorderGoods.deliveryDirectComments}"></li>
						</ul>
						<div id="deliveryDirectCommentsDiv"></div>
						<input type="hidden" name="deliveryDirect" id="deliveryDirect">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>内部备注</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest mr5" placeholder="内部备注不对外显示" name="insideComments" id="insideComments" value="${saleorderGoods.insideComments}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>产品备注</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest mr5" placeholder="产品备注对外显示" name="goodsComments" id="goodsComments" value="${saleorderGoods.goodsComments}" maxlength="60">
						<div><span class="font-grey9 mt5">友情提示<br>1、如果您的操作导致报价单金额发生变化，可能需要重新编辑付款计划；</span></div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao  tcenter">
				<input type="hidden" name="saleorderId" id="saleorderId" value="${saleorderGoods.saleorderId}"/>
				<input type="hidden" name="saleorderGoodsId" id="saleorderGoodsId" value="${saleorderGoods.saleorderGoodsId}"/>
				<input type="hidden" name="sku" id="sku" value="${saleorderGoods.sku}"/>
				<button class="bt-bg-style bg-deep-green" type="button" onclick="confirmSubmit();">提交</button>
				<button id="close-layer" type="button" class="dele">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>