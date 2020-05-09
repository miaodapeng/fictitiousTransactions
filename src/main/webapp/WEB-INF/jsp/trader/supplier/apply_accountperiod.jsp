<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="账期申请" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8 trader-customer-accountperiodapply">
	<form method="post" action="" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<lable>当前帐期额度</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span>${traderSupplier.periodAmount } 元</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>当前帐期天数</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span>${traderSupplier.periodDay } 天</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>当前剩余额度</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span>${traderSupplier.accountPeriodLeft } 元</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>帐期使用次数</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span>${traderSupplier.usedTimes }</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>逾期次数</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span class="warning-color1">${traderSupplier.overdueTimes }</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>本次申请金额</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks" id="accountPeriodApplyLabel">
						<input type="text" name="accountPeriodApply"
							id="accountPeriodApply" class="input-middle"
							onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> <span>元</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>本次申请天数</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks" id="accountPeriodDaysApplyLabel">
						<input type="text" name="accountPeriodDaysApply"
							id="accountPeriodDaysApply" class="input-middle"
							onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> <span>天</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>申请原因</lable>
				</div>
				<div class="f_left">
					<div class="form-blanks">
						<textarea class="input-middle heit50 .textarea-smallest" name="comments" id="comments"></textarea>
					</div>
				</div>
			</li>
		</ul>
		<div class="pop-friend-tips">
			友情提醒<br /> 1、帐期可以重新使用，如果不需要修改帐期额度和时间，您不用重复申请；<br />
		</div>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="traderId"
				value="${traderSupplier.traderId}" /> <input type="hidden"
				name="accountPeriodNow" value="${traderSupplier.periodAmount}" /> <input
				type="hidden" name="accountPeriodDaysNow" value="${traderSupplier.periodDay}" />
				<input
				type="hidden" name="overdueTimes" value="${traderSupplier.overdueTimes}" />
				<input
				type="hidden" name="overdueAmount" value="${traderSupplier.overPeriodAmount}" />
				<input
				type="hidden" name="usedTimes" value="${traderSupplier.usedTimes}" />
				<input
				type="hidden" name="accountPeriodLeft" value="${traderSupplier.accountPeriodLeft}" />
			<input type="hidden" name="formToken" value="${formToken}"/>
			<input type="hidden" id="message" value="${message }">
			<button type="submit" id="submit">提交</button>
			<button class="dele" id="close-layer" type="button">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/supplier/apply_accountperiod.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>