<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑库房" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="addElement">
	<div class="add-main">
		<form method="post" id="addStorageroomForm"
			action="${pageContext.request.contextPath}/warehouse/storageRoomMag/editStorageRoom.do?storageroomId=${storageroom.storageroomId}">
			<ul class="add-detail floatnone">
				<li>
					<div class="infor_name">
						<span>*</span> <label>库房名称</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" id="storageRoomName"
							name="storageRoomName" value="${storageroom.storageRoomName }" />
					</div>
				</li>
				<li><label class="infor_name"><span>*</span> 所属仓库</label> <select
					class="input-middle" name="warehouseId" id="warehouseId">
						<option value="0">全部</option>
						<c:forEach items="${warehouseList }" var="type" varStatus="status">
							<option value="${type.warehouseId }"
								<c:if test="${not empty storageroom &&  storageroom.warehouseId == type.warehouseId}">selected="selected"</c:if>>${type.warehouseName }</option>
						</c:forEach>
				</select></li>
				<li>
					<div class="infor_name mt0">
						<label>库房备注</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" id="comments"
							name="comments" value="${storageroom.comments }" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>库房温度要求</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-smaller" id="temperatureMin"
							name="temperatureMin" value="${storageroom.temperatureMin }" /> ℃
						<span style="color: black;">&nbsp;-&nbsp;</span> <input
							type="text" class="input-smaller" id="temperatureMax"
							name="temperatureMax" value="${storageroom.temperatureMax }" /> ℃
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<label>库房湿度要求</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-smaller" id="humidityMin"
							name="humidityMin" value="${storageroom.humidityMin }" /> % <span
							style="color: black;">&nbsp;-&nbsp;</span> <input type="text"
							class="input-smaller" id="humidityMax" name="humidityMax"
							value="${storageroom.humidityMax }" /> %
					</div>
				</li>
				<div class="add-tijiao tcenter">
				    <input type="hidden" name="beforeParams" value='${beforeParams}'/>
					<input type="hidden" name="storageroom.storageroomId"
						value="${storageroom.storageroomId }">
					<button type="submit">提交</button>
				</div>
			</ul>
		</form>
	</div>
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/storageRoomMag/add_storageroom.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>