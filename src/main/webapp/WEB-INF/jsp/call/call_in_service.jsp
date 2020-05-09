<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="来电|售后" scope="application" />
<%@ include file="./common/header.jsp"%>

<div class="layer-content call-layer-content">
	<!-- 标题 -->
	<div class="callcenter-title">
		<%@ include file="./common/call_in.jsp"%>
		<div class="right-title">
			<c:if test="${not empty traderCustomer.traderId}">
			<span onclick="addbussinesschance('${phone}',${traderCustomer.traderId});">新增商机</span>
			</c:if>
			<c:if test="${ empty traderCustomer.traderId}">
			<span onclick="addbussinesschance('${phone}',0);">新增商机</span>
			</c:if>
			<i class="iconclosetitle" onclick="window.parent.closeScreenAll();"></i>
		</div>
	</div>
	<!-- 表格信息 -->
	<div class="content-box">
		<div class="content-colum content-colum2">
			<div class="content-item">
				<table class="table table-td-border1">
	                <tbody>
	                    <tr>
	                        <td class="wid10">确认客户身份</td>
	                        <td>
	                        <div>
                                    <ul class="inputfloat">
                                        <li>
                                        <input type="radio" name="traderType" value="1" checked="checked">
                                        <span>客户</span>
                                        </li>
                                          <li>
                                        <input type="radio" name="traderType" value="2">
                                        <span>供应商</span>
                                        </li>
                                    </ul>                                        
                                    </div>
	                        
	        					</td>
	                    </tr>
					</tbody>
				</table>
			</div>
		
		<div id="customerDiv">
            <%@ include file="./common/customer_info.jsp"%>
            <%@ include file="./common/communicate.jsp"%>
            <%@ include file="./common/bussiness_chance.jsp"%>
            <%@ include file="./common/quoteorder.jsp"%>
            <%@ include file="./common/saleorder.jsp"%> 
        </div>
        <div id="supplierDiv" style="display: none">
            <%@ include file="./common/supplier_info.jsp"%>
            <%@ include file="./common/communicate_supplier.jsp"%>
            <%@ include file="./common/buyorder.jsp"%> 
        </div>
		<div class="clear"></div>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$("input[name='traderType']").change(function(){
		if($(this).val() == 1){
			$("#customerDiv").show();
			$("#supplierDiv").hide();
		}
		if($(this).val() == 2){
			$("#customerDiv").hide();
			$("#supplierDiv").show();
		}
	});
});
</script>
<%@ include file="../common/footer.jsp"%>