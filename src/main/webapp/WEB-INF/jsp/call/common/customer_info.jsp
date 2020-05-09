<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  	
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>	
<div class="content-item">
	<table class="table table-td-border1">
		<tbody>
			<tr>
				<td>姓名</td>
				<td class="value">${traderCustomer.contactName }</td>
				<td>所属公司</td>
				<td class="value"><a target="_blank" href="${pageContext.request.contextPath}/trader/customer/baseinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}&traderId=${traderCustomer.traderId}">${traderCustomer.name }</a></td>
				<td>公司类型</td>
				<td class="value">${traderCustomer.customerTypeStr }&nbsp;&nbsp;${traderCustomer.customerPropertyStr }</td>
			</tr>
			<tr>
				<td>客户等级</td>
				<td class="value">${traderCustomer.customerLevelStr}</td>
				<td>合作次数</td>
				<td class="value">${traderCustomer.buyCount }</td>
				<td>最近沟通时间</td>
				<td class="value"><date:date value ="${traderCustomer.lastCommuncateTime} "/></td>
			</tr>
		</tbody>
	</table>
</div>