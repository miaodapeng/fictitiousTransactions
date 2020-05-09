<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }编辑经销商账号"
	scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post"  action="${pageContext.request.contextPath}/ordergoods/manage/saveeditordergoodsaccount.do" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<lable>归属店铺</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">${ordergoodsStore.name }</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>客户名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">${traderCustomer.traderName }</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>客户所在地</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">${traderCustomer.area }</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>联系人</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<select name="traderContactId" id="traderContactId">
							<option value="0">请选择</option>
							<c:if test="${not empty  contactList}">
								<c:forEach items="${contactList }" var="contact">
									<option value="${contact.traderContactId }" <c:if test="${contact.traderContactId eq ordergoodsStoreAccount.traderContactId}">selected="selected"</c:if> title="${contact.mobile }">${contact.name }|${contact.mobile }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>联系地址</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<select name="traderAddressId" id="traderAddressId">
							<option value="0">请选择</option>
							<c:if test="${not empty  addressList}">
								<c:forEach items="${addressList}" var="address">
									<option value="${address.traderAddress.traderAddressId }" <c:if test="${address.traderAddress.traderAddressId eq ordergoodsStoreAccount.traderAddressId}">selected="selected"</c:if>>${address.area }&nbsp;${address.traderAddress.address }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<input type="hidden" name="ordergoodsStoreAccountId" id="ordergoodsStoreAccountId" value="${ordergoodsStoreAccount.ordergoodsStoreAccountId }"/>
			<input type="hidden" name="ordergoodsStoreId" id="ordergoodsStoreId" value="${ordergoodsStoreAccount.ordergoodsStoreId }"/>
			<button type="submit">提交</button>
		</div>
	</form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/account/account_edit.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>