<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/quote/lose_order_reason.js?rnd=<%=Math.random()%>'></script>
<div class="editElement" style="height:100%">
	<form action="" method="post" id="followOrderForm">
		<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
		<input type="hidden" name="quoteorderId" value="${quote.quoteorderId}"/>
		<ul class="edit-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='followOrderStatusComments'>请填写失单原因</lable>
				</div>
				<div class="f_left">
					<input type="text" name='followOrderStatusComments' id='followOrderStatusComments' class="input-middle"/>
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
