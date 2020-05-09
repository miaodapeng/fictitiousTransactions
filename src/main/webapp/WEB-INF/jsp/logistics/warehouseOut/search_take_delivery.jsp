
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑收货地址" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript"
	src='<%=basePath%>static/js/aftersales/order/search_afterorder_list.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
function saveLendOutEdit() {
	var takeTraderContactId = $('#contact_1').val();
	var takeTraderAddressId = $('#address_1').val();
	var logisticsId = $('.f_left .input-middle').eq(0).val();
	var freightDescription = $('.f_left .input-middle').eq(1).val();
	var logisticsComments = $('#logisticsComments').val();
	var lendOutId = $("input[name='lendOutId']").val();
	var takeTraderAreaId = $("input[name='areaId']").val();
	if(takeTraderContactId == '0' ){
		layer.alert("请选择收货联系人");
		return false;
	}
	if(takeTraderAddressId == '0' || takeTraderAreaId == '0'){
		layer.alert("请选择收货地址");
		return false;
	}
	console.log(takeTraderAreaId);
	 $.ajax({
		async:true,
		url:page_url+'/warehouse/warehousesout/saveLendOutEdit.do',
		data:{
			"lendOutId":lendOutId,
			"takeTraderContactId":takeTraderContactId,
			"takeTraderAddressId":takeTraderAddressId,
			"logisticsId":logisticsId,
			"freightDescription":freightDescription,
			"logisticsComments":logisticsComments,
			"takeTraderAreaId":takeTraderAreaId
		},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				console.log($('body'))
				//$('body').load(window.location.href);
				window.parent && window.parent.handlerEdit();
				
			}else{
				layer.alert(data.message);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	}) 
	
}

$(function(){
	$('#address_1').change(function(){
		$("[name=areaId]").val($(this).find('option:selected').data('id'))
	})
})

</script>

<div class="formpublic formpublic1">
		<div class="parts content1 ">
			<ul class="payplan">
				<li>
					<div class="infor_name infor_name72">
						<span>*</span> <label>收货客户</label>
					</div> 
						<div class="f_left inputfloat">
							<span class=" mr10 mt3" id="trader_name_span_1">${lendout.traderName}</span> 
							<input type="hidden" name="takeTraderId" id="trader_id_1" value="${lendout.traderId}"> 
							<input type="hidden" name="takeTraderName" id="trader_name_1" value="${lendout.traderName}"/> 
							<%-- <span class="bt-bg-style bt-small bg-light-blue pop-new-data"
							layerParams='{"width":"800px","height":"300px","title":"编辑收货信息","link":"${pageContext.request.contextPath}/trader/customer/searchCustomerList.do?indexId=1&searchTraderName="}'>搜索</span>
							 --%>
					</div>
				</li>
				<li>
					<div class="infor_name infor_name72">
						<span>*</span> <label>收货联系人</label>
					</div>
					<div class="f_left inputfloat">
						<select class="input-xx" id="contact_1" name="takeTraderContactId">
							<option value="0">请选择</option>
							<c:if test="${not empty takeTraderContactList}">
								<c:forEach items="${takeTraderContactList}" var="list"
									varStatus="status">
									<option value="${list.traderContactId}"
										<c:if test="${list.traderContactId eq lendout.takeTraderContactId}">selected="selected"</c:if>>
										${list.name}/${list.telephone}/${list.mobile}</option>
								</c:forEach>
							</c:if>
						</select> 
						<input type="hidden" name="takeTraderContactName"> 
						<input type="hidden" name="takeTraderContactTelephone"> 
						<input type="hidden" name="takeTraderContactMobile">
						<div id="takeTraderContactIdMsg" style="clear: both"></div>
					</div>
				</li>
				<li>
					<div class="infor_name infor_name72">
						<span>*</span> <label>收货地址</label>
					</div>
					<div class="f_left inputfloat">
						<select class="input-xx" id="address_1" name="takeTraderAddressId">
							<option value="0">请选择</option>
							<c:if test="${not empty takeTraderAddressList}">
								<c:forEach items="${takeTraderAddressList}" var="list"
									varStatus="status">
									<option value="${list.traderAddress.traderAddressId}" data-id="${list.traderAddress.areaId}"
										<c:if test="${list.traderAddress.traderAddressId eq lendout.takeTraderAddressId}">selected="selected"
										</c:if>>${list.area}/${list.traderAddress.address}</option>
								</c:forEach>
							</c:if>
						</select>
						<input type="hidden" name="areaId" value="${list.traderAddress.areaId}">
						 <input type="hidden" name="takeTraderArea"> 
						<input type="hidden" name="takeTraderAddress">
						<div id="takeTraderAddressIdMsg" style="clear: both"></div>
					</div>
				</li>
				<li>
					<div class="infor_name infor_name72">
						<label>指定物流公司</label>
					</div>
					<div class="f_left inputfloat">
						<select class="input-middle" name="logisticsId">
							<option value="0">请选择</option>
							<c:forEach var="list" items="${logisticsList}">
								<c:if test="${list.isEnable == 1}">
									<option value="${list.logisticsId}"
										<c:if test="${lendout.logisticsId == list.logisticsId}">selected="selected"</c:if>>${list.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name infor_name72">
						<label>运费说明</label>
					</div>
					<div class="f_left inputfloat">
						<select class="input-middle" name="freightDescription">
							<option value="0">请选择</option>
							<c:forEach var="list" items="${freightDescriptions}">
								<option value="${list.sysOptionDefinitionId}"
									<c:if test="${lendout.freightDescription == list.sysOptionDefinitionId}">selected="selected"</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name infor_name72">
						<label>物流备注</label>
					</div>
					<div class="f_left inputfloat">
						<input type="text" name="logisticsComments" id="logisticsComments"
							value="${lendout.logisticsComments}"
							placeholder="对内使用，适用于向物流部同事告知发货要求，默认同步客户信息中物流备注" class="input-xx" />
					</div>
				</li>
				<li>
					<div class="infor_name ">
						<label></label>
					</div>
					<div class="f_left inputfloat">
						<button type="button" class="bt-bg-style bg-deep-green" onclick="saveLendOutEdit();">提交</button>
					</div>
				</li>
			</ul>
			<input type="hidden" name="lendOutId" id="lendOutId" value="${lendout.lendOutId}"/> 
		</div>
			
</div>
</body>
</html>
