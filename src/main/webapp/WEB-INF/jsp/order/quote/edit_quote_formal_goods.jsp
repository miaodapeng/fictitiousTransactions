<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑报价产品</title>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/edit_quote_formal_goods.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
	<div class="editElement formpublic">
		<form id="quoteFormalGoodsForm">
			<!-- 终端客户属性和区域 -->
			<input type='hidden' name='terminalTraderName' id='terminalTraderName' value=''>
			<input type='hidden' name='terminalTraderId' id='terminalTraderId' value=''>
			<input type='hidden' name='terminalTraderType' id='terminalTraderType' value=''>
			<input type='hidden' name='salesArea' id='salesArea' value=''>
			<input type='hidden' name='salesAreaId' id='salesAreaId' value=''>
			
			<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
			<input type="hidden" name="quoteorderId" id="quoteorderId" value="${quoteGoods.quoteorderId}"/>
			<input type="hidden" name="quoteorderGoodsId" id="quoteorderGoodsId" value="${quoteGoods.quoteorderGoodsId}"/>
			<ul class="lastResult">
				<li>
					<div class="infor_name mt0">
						<span>*</span> <label>产品名称</label>
					</div>
					<div class="f_left table-largest content1">
						<div class="">
							<span class="font-blue mr10 productname2" id="confirmGoodsName">
								<a class="addtitle2" tabTitle='{"num":"viewgoods${quoteGoods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${quoteGoods.goodsId}","title":"产品信息"}'>${newSkuInfo.SHOW_NAME}</a>
							</span>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>产品信息</label>
					</div>
					<div class="f_left" id="confirmGoodsContent">
						${newSkuInfo.SKU_NO}&nbsp;/&nbsp;
						${newSkuInfo.GOODS_LEVEL_NAME}&nbsp;/&nbsp;
						${newSkuInfo.CHECK_STATUS}&nbsp;/&nbsp;
						产品负责人${newSkuInfo.PRODUCTMANAGER}
					</div>
					<span class="font-grey9">&nbsp;&nbsp;产品未审核通过时，不允许转化到订单中</span>
				</li>
				<li>
					<div class="infor_name ">
						<label>报价</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-smaller mr5" name="price" id="price" onkeyup="countTotalMoney('price');" value="${quoteGoods.price}">
						<span class="font-grey9" id="priceError">
						<c:if test="${quoteGoods.channelPrice==null}">
							<c:if test="${quoteGoods.avgPrice==null}">
								报价平均价（近12个月）：无平均价信息，请向产品部咨询
							</c:if>
							<c:if test="${quoteGoods.avgPrice!=null}">
								报价平均价（近12个月）：
								<fmt:formatNumber type="number" value="${quoteGoods.avgPrice==null?0:quoteGoods.avgPrice}" pattern="0.00" maxFractionDigits="2"/>
							</c:if>
						</c:if>
						<c:if test="${quoteGoods.channelPrice!=null}">核价参考价格：<fmt:formatNumber type="number" value="${quoteGoods.channelPrice==null?0:quoteGoods.channelPrice}" pattern="0.00" maxFractionDigits="2"/></c:if>
						</span>
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<span>*</span> <label>数量</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-smaller" name="num" id="num" onkeyup="countTotalMoney('num');" value="${quoteGoods.num}">
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>总额</label>
					</div>
					<div class="f_left" id="totalMoney"><fmt:formatNumber type="number" value="${(quoteGoods.price==null?0:quoteGoods.price) * quoteGoods.num}" pattern="0.00" maxFractionDigits="2"/></div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>报价含安调</label>
					</div>
					<div class="f_left inputfloat inputfloatmb0">
						<ul>
							<li><input type="radio" name="installation" value="1" <c:if test="${quoteGoods.haveInstallation eq 1}">checked</c:if>> <label>是</label></li>
							<li><input type="radio" name="installation" value="0" <c:if test="${quoteGoods.haveInstallation eq 0}">checked</c:if>> <label>否</label></li>
						</ul>
						<input type="hidden" name="haveInstallation" id="haveInstallation" />
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>货期</label>
					</div>
					<div class="f_left" id="cycleError">
						<input type="text" class="input-smaller mr5" name="deliveryCycle" id="deliveryCycle" value="${quoteGoods.deliveryCycle}">
						<!-- <span class="mt4 mr5">天</span> --> <span class="font-grey9 mt4" id="deliveryCycleErrMsg">核价参考货期：3-5天</span>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>是否直发</label>
					</div>
					<div class="f_left inputfloat inputfloatmb0">
						<ul>
							<li class="mt4"><input type="radio" name="deliveryDirectRad" value="0" <c:if test="${quoteGoods.deliveryDirect eq 0}">checked</c:if>> <label>否</label></li>
							<li class="mt4"><input type="radio" name="deliveryDirectRad" value="1" <c:if test="${quoteGoods.deliveryDirect eq 1}">checked</c:if>> <label>是</label></li>
							<li><input type="text" placeholder="请填写直发原因，含有直发商品的订单不允许提前采购" class="input-larger" name="deliveryDirectComments" id="deliveryDirectComments" value="${quoteGoods.deliveryDirectComments}"></li>
						</ul>
						<input type="hidden" name="deliveryDirect" id="deliveryDirect" value="${quoteGoods.deliveryDirect}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>内部备注</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest mr5" placeholder="内部备注不对外显示" name="insideComments" id="insideComments" value="${quoteGoods.insideComments}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>产品备注</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest mr5" placeholder="产品备注对外显示" name="goodsComments" id="goodsComments" value="${quoteGoods.goodsComments}">
						<div class="font-grey9 mt10">友情提示<br/>1、如果您的操作导致单据总额发生变化，需要重新编辑付款计划</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao  tcenter">
				<button class="bt-bg-style bg-deep-green" type="button" onclick="subBtn();">提交</button>
				<button id="close-layer" type="button" class="dele">取消</button>
			</div>
		</form>
	</div>
</body>
</html>