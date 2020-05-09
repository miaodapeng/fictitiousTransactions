<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增订单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/saleorder/add.js?rnd=<%=Math.random()%>'></script>
<div class="content">
	<div class="formtitle">请确认客户信息</div>
	
		<ul>
			<li id="updateTerminalInfo">
				<div class="infor_name ">
					<label>客户名称</label>
				</div>
				<div class="f_left">
					<div class="inputfloat" id="errorTxtMsg">
						<input type="text" placeholder="请输入客户名称" class="input-largest" name="searchTraderName" id="searchTraderName"> 
						<label class="bt-bg-style bg-light-blue bt-small" onclick="searchTerminal();" id="errorMes">搜索</label> 
						<span style="display: none;"> <!-- 弹框 -->
							<div class="title-click nobor  pop-new-data" id="terminalDiv"></div>
						</span>
					</div>
				</div>
			</li>
		</ul>

		<div  id="desc_div" class="none">
		<form method="post" id="addForm" action="${pageContext.request.contextPath}/order/saleorder/saveaddsaleorderinfo.do">
		<ul class="payplan">
	        <li>
	            <div class="infor_name infor_name72">
	                <span>*</span>
	                <label>客户名称</label>
	            </div>
	            <div class="f_left inputfloat">
	                <span class=" mr10 mt3" id="trader_name_span_1"></span>
	                <input type="hidden" name="traderId" id="trader_id_1" value="">
	                <input type="hidden" name="traderName" id="trader_name_1" value="">
	                <input type="hidden" name="customerType" id="customer_type_1" value="">
	                <input type="hidden" name="customerNature" id="customer_nature_1" value="">
	                <span class="bt-bg-style bt-small bg-light-blue pop-new-data" layerParams='{"width":"800px","height":"300px","title":"编辑收货信息","link":"${pageContext.request.contextPath}/trader/customer/searchCustomerList.do?indexId=1&searchTraderName="}'>重新搜索</span>
	            </div>
	        </li>
	        <li>
	            <div class="infor_name infor_name72 mt0">
	                <span>*</span>
	                <label>客户类型</label>
	            </div>
	            <div class="f_left inputfloat" id="customer_type_nature_div">
	            </div>
	        </li>
	        <li>
	            <div class="infor_name infor_name72">
	                <span>*</span>
	                <label>联系人</label>
	            </div>
	            <div class="f_left inputfloat">
	                <select class="input-xx" id="contact_1" name="traderContactId">
	                    <option value="0">请选择</option>
	                    
	                </select>
	                <input type="hidden" name="traderContactName">
	                <input type="hidden" name="traderContactTelephone">
	                <input type="hidden" name="traderContactMobile">
	                <div id="traderContactIdMsg" style="clear:both"></div>
	            </div>
	        </li>
	        <li>
	            <div class="infor_name infor_name72">
	                <span>*</span>
	                <label>联系地址</label>
	            </div>
	            <div class="f_left inputfloat">
	                <select class="input-xx" id="address_1" name="traderAddressId">
	                    <option value="0">请选择</option>
	                </select>
	                <input type="hidden" name="traderArea">
	               	<input type="hidden" name="traderAddress">
	               	<div id="traderAddressIdMsg" style="clear:both"></div>
	            </div>
	        </li>
	    </ul>
		<div class="add-tijiao tcenter mt10">
			<!-- <input type="hidden" id="extraType" name="extraType" value="add"> -->
			<button type="button" class="bt-bg-style bg-deep-green" onclick="addSubmit();">下一步</button>
		</div>
		</form>
		</div>
</div>
<%@ include file="../../common/footer.jsp"%>