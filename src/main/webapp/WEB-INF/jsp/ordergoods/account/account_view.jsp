<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }查看经销商"
	scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="table-smallest">注册手机号</td>
					<td class="text-left">${traderCustomer.phone }</td>
					<td class="table-smallest">账号状态</td>
					<td class="text-left">
						<c:if test="${ordergoodsStoreAccount.isEnable eq 1}"><span class="onstate">有效</span></c:if>
                     	<c:if test="${ordergoodsStoreAccount.isEnable eq 0}"><span class="offstate">无效</span></c:if>
					</td>
				</tr>
				<tr>
					<td class="table-smallest">注册时间</td>
					<td class="text-left"><date:date value="${ordergoodsStoreAccount.addTime} " /></td>
					<td class="table-smallest">归属销售</td>
					<td class="text-left">${ordergoodsStoreAccount.owner }</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">发票信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="table-smallest">发票抬头</td>
					<td class="text-left">${traderCustomer.traderName }</td>
					<td class="table-smallest">纳税人识别号</td>
					<td class="text-left">${finance.taxNum }</td>
				</tr>
				<tr>
					<td class="table-smallest">注册电话</td>
					<td class="text-left">${finance.regTel}</td>
					<td class="table-smallest">注册地址</td>
					<td class="text-left">${finance.regAddress }</td>
				</tr>
				<tr>
					<td class="table-smallest">开户行</td>
					<td class="text-left">${finance.bank }</td>
					<td class="table-smallest">账号</td>
					<td class="text-left">${finance.bankAccount }</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">企业信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="table-smallest">企业名称</td>
					<td class="text-left" colspan="3">${traderCustomer.traderName }</td>
				</tr>
				<tr>
					<td class="table-smallest">所在地</td>
					<td class="text-left">${traderCustomer.area }</td>
					<td class="table-smallest">详细地址</td>
					<td class="text-left">${traderCustomer.address }</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">地址信息</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>收货人</th>
					<th>联系电话</th>
					<th>地区</th>
					<th>详细地址</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${ordergoodsStoreAccount.contact}</td>
					<td>${ordergoodsStoreAccount.mobile}</td>
					<td>${address.area }</td>
					<td>${address.traderAddress.address }</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>