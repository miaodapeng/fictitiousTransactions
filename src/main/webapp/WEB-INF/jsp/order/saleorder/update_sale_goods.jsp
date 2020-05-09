<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="修改订单商品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/update_sale_goods.js?rnd=<%=Math.random()%>'></script>
	<div class="form-list form-tips4">
		<form method="post" id="updateSaleGoodsForm">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span>
				        <label>发货方式</label>
				    </div>
				    <div class="f_left">
				        <input type="radio" name="deliveryDirect" id="deliveryDirect_n" checked value="0"/>普发
				        &nbsp;&nbsp;&nbsp;
				        <input type="radio" name="deliveryDirect" id="deliveryDirect_y" value="1"/>直发
				    </div>
				</li>
				<li>
					<div class="infor_name">
				        <label>直发原因</label>
				    </div>
				    <div class="f_left">
				        <input type="text" class="input-middle" name="deliveryDirectComments" id="deliveryDirectComments" />
				    </div>
				</li>
				<li>
					<div class="infor_name"></div>
				    <div class="f_left font-grey9">
						注意：含有直发商品的订单不允许提前采购
				    </div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="updateSaleGoodsSave(${saleorderId},'${saleorderGoodsIdArr}');">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>