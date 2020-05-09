<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑备货订单产品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/saleorder/bh_edit_saleorder_goods.js?rnd=<%=Math.random()%>'></script>
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
					<div class="f_left" id="confirmGoodsContent">${newSkuInfo.SKU_NO}/${newSkuInfo.GOODS_LEVEL_NAME}/产品负责人${newSkuInfo.PRODUCTMANAGER}</div>
				</li>
				<li>
					<div class="infor_name ">
						<span>*</span> <label>备货价</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-middle mr5" name="price" id="price" onkeyup="confirmTotalMoney('price');" value="${saleorderGoods.price}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<span>*</span> <label>数量</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-middle" name="num" id="num"	onkeyup="confirmTotalMoney('num');" value="${saleorderGoods.num}">
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
					<div class="f_left" id="confirmTotalMoney"><fmt:formatNumber type="number" value="${(saleorderGoods.price==null?0:saleorderGoods.price) * saleorderGoods.num}" pattern="0.00" maxFractionDigits="2"/></div>
				</li>
				<li>
					<div class="infor_name ">
						<label>供应商</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest mr5" placeholder="" name="supplierName" id="supplierName" value="${saleorderGoods.supplierName}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>内部备注</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest mr5" placeholder="内部备注，适用于向采购部转达特殊要求" name="insideComments" id="insideComments" value="${saleorderGoods.insideComments}">
					</div>
				</li>
			</ul>
			<div class="add-tijiao  tcenter">
				<input type="hidden" name="saleorderId" id="saleorderId" value="${saleorderGoods.saleorderId}"/>
				<input type="hidden" name="saleorderGoodsId" id="saleorderGoodsId" value="${saleorderGoods.saleorderGoodsId}"/>
				<button class="bt-bg-style bg-deep-green" type="button" onclick="confirmSubmit();">提交</button>
				<button id="close-layer" type="button" class="dele">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>