<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑报价产品</title>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/edit_quote_temp_goods.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
	<div class="editElement formpublic">
		<form id="quoteTempGoodsForm">
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
							<input type="text" class="input-middle mr5" name="goodsName" id="goodsName" value="${quoteGoods.goodsName}">
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<span>*</span> <label>品牌</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-middle mr5" name="brandName" id="brandName" value="${quoteGoods.brandName}">
						<span class="font-grey9">核价参考价格：12000.00 或 无核价信息，请向产品部咨询</span>
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<span>*</span> <label>型号</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-middle" name="model" id="model" value="${quoteGoods.model}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>报价</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-middle mr5" name="price" id="price" onkeyup="countTotalMoney('price');" value="${quoteGoods.price}">
						<span class="font-grey9">核价参考价格：12000.00 或 无核价信息，请向产品部咨询</span>
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<span>*</span> <label>数量</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-middle" name="num" id="num"	onkeyup="countTotalMoney('num');" value="${quoteGoods.num}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
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
					<div class="f_left">
						<input type="text" class="input-middle mr5" name="deliveryCycle" id="deliveryCycle" value="${quoteGoods.deliveryCycle}">
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
						<input type="hidden" name="deliveryDirect" id="deliveryDirect">
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
						<input type="text" class="input-largest mr5" placeholder="产品备注对外显示" name="goodsComments" id="goodsComments" value="${quoteGoods.insideComments}">
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label>产品图片</label>
					</div>
					<div class="f_left">
							<div class="mb10">
								<input type="file" class="upload_file" style="display: none;" name="lwfile" id="lwfile" onchange="uploadImgFtp(this)">
								<input type="text" class="input-middle" id="fileUrl" placeholder="请上传图片" readonly="readonly" value="${ach.name}">
								<label class="bt-bg-style bt-middle bg-light-blue ml4" onclick="lwfile.click();">浏览</label> 
								
								<c:if test="${!empty ach}">
									<input type="hidden" name="attachmentId" value="${ach.attachmentId}"/>
									<i class="iconsuccesss mt5 none" id="img_icon_wait"></i>
			                        <a href="http://${ach.domain}/${ach.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_opt_look">查看</a>
			                    	<span class="font-red cursor-pointer mt4" onclick="delProductImg()" id="img_opt_del">删除</span>
								</c:if>
								<c:if test="${empty ach}">
									<i class="iconsuccesss mt5 none" id="img_icon_wait"></i>
			                        <a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_opt_look">查看</a>
			                    	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg()" id="img_opt_del">删除</span>
								</c:if>
							<span class="font-grey9">使用jpg格式，2MB以内</span>
							</div>
							<!-- 附件信息表字段隐藏信息 -->
							<input type="hidden" name="name" id="imgName" value="${ach.name}"/> 
							<input type="hidden" name="domain" id="imgDomain" value="${ach.domain}"/> 
							<input type="hidden" name="uri" id="imgUri" value="${ach.uri}"/>
							
						<div class="font-grey9 mt10">友情提示<br/>1、如果您的操作导致报价单金额发生变化，可能需要重新编辑付款计划；</div>
					</div>
				</li>
				
			</ul>
			<div class="add-tijiao  tcenter">
				<button class="bt-bg-style bg-deep-green" type="submit">提交</button>
				<button id="close-layer" type="button" class="dele">取消</button>
			</div>
		</form>
	</div>
</body>
</html>