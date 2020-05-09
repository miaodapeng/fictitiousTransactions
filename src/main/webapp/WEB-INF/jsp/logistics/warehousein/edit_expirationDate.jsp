<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购入库" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post" action="" id="">
		<ul>
			<li>
				<div class="form-tips">
					<lable>产品名称</lable>
				</div>
				<div class="f_left ">
					<span>${warehouseGoodsOperateLog.goodsName}</span>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>订货号</lable>
				</div>
				<div class="f_left ">
					<span>${warehouseGoodsOperateLog.sku}</span>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>采购订单</lable>
				</div>
				<div class="f_left ">
					<span>${warehouseGoodsOperateLog.buyorderNo}</span>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>入库数量</lable>
				</div>
				<div class="f_left ">
					<span>${warehouseGoodsOperateLog.num}</span>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>库存数量</lable>
				</div>
				<div class="f_left ">
					<span>${warehouseGoodsOperateLog.storeNum}</span>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>批次号</lable>
				</div>
				<div class="f_left ">
					<div>
						<input type="text" class="input-small" name="batchNumber"
							id="batchNumber" value="${warehouseGoodsOperateLog.batchNumber}" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>效期截止时间</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input class="Wdate f_left input-smaller96 mr5" type="text"
							name="expirationDate" placeholder="请选择日期"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="<date:date value ="${warehouseGoodsOperateLog.expirationDate}" format="yyyy-MM-dd"/>">
					</div>
				</div>
			</li>
			<div class="add-tijiao tcenter">
			<input type="hidden" name="beforeParams" value='${beforeParams}'>
				<input type="hidden" class="input-small"
					name="warehouseGoodsOperateLogId"
					value="${warehouseGoodsOperateLog.warehouseGoodsOperateLogId}" />
				<button type="button" class="bg-light-green" onclick="submitEdit()">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
	</form>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/editExpirationDate.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
