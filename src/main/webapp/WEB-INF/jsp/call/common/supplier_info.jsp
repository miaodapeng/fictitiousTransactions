<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  	
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>	
<div class="content-item">
	<table class="table table-td-border1">
		<tbody>
			<tr>
				<td>姓名</td>
				<td class="value">${traderSupplier.contactName }</td>
				<td>所属公司</td>
				<td class="value"><a target="_blank" href="${pageContext.request.contextPath}/trader/supplier/baseinfo.do?traderSupplierId=${traderSupplier.traderSupplierId}&traderId=${traderSupplier.traderId}">${traderSupplier.traderSupplierName }</a></td>
				<td>供应商等级</td>
				<td class="value">${traderSupplier.gradeStr}</td>
			</tr>
			<tr>
				<td>合作次数</td>
				<td class="value">${traderSupplier.buyCount }</td>
				<td>最近沟通时间</td>
				<td class="value"><date:date value ="${traderSupplier.lastCommuncateTime} "/></td>
				<td></td>
				<td class="value"></td>
			</tr>
		</tbody>
	</table>
</div>