<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }添加经销商账号"
	scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post"  action="${pageContext.request.contextPath}/ordergoods/manage/saveaddordergoodsaccount.do" id="myform">
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
					<div class="form-blanks">
						<span id="traderName"></span>
						<span class="bt-small bt-bg-style bg-light-blue pop-new-data" layerParams='{"width":"900px","height":"500px","title":"搜索客户","link":"./searchtradercustomernew.do?ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId }"}'>搜索</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>客户所在地</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks"><span id="area"></span></div>
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
							<option value="0" title="请选择">请选择</option>
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
						</select>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="traderCustomerId" id="traderCustomerId" value=""/>
			<input type="hidden" name="ordergoodsStoreId" id="ordergoodsStoreId" value="${ordergoodsStore.ordergoodsStoreId }"/>
			<button type="submit">提交</button>
		</div>
	</form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/account/account_add.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>