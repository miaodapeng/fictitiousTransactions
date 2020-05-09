<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="分配供应商" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content pt10">
	<div class="">
		<form action="" method="post" id="form">
			<ul>
				<li>
					<div class="infor_name mt0">请选择分配方式</div>
					<div class="f_left inputfloat">
						<ul>
							<li><input type="radio" name="type" value="1"
								<c:if test="${type eq 1 or empty type}">checked="checked"</c:if>>
								<label class="mr4">单个</label></li>
							<li><input type="radio" name="type" value="2"
								<c:if test="${type eq 2 }">checked="checked"</c:if>> <label>批量</label></li>
						</ul>
					</div>
				</li>
				<li class="single_assign mt7 mb7 <c:if test="${type eq 2 }">none</c:if>">
					<div class="infor_name">请输入供应商名称</div>
					<div class="f_left inputfloat">
						<ul>
							<li><input type="text" class="input-middle"
								name="traderName" value="${traderName }"> <span
								class=" bt-bg-style bg-light-blue bt-small"
								onclick="search(this);">搜索</span></li>
						</ul>
					</div>
				</li>
				<li
					class="batch_assign mt7 mb7 <c:if test="${type eq 1 or empty type}">none</c:if> ">
					<div class="infor_name">请选择归属采购</div>
					<div class="f_left inputfloat">
						<ul>
							<li><select name="from_user">
									<option value="0">请选择采购人员</option>
									<c:if test="${not empty userList}">
										<c:forEach items="${userList }" var="user">
											<option value="${user.userId }"
												<c:if test="${from_user eq user.userId }">selected="selected"</c:if>>${user.username}</option>
										</c:forEach>
									</c:if>
							</select> <span class=" bt-bg-style bg-light-blue bt-small"
								onclick="search(this);">搜索</span></li>
						</ul>
					</div>
				</li>
			</ul>
		</form>
		<c:if test="${type eq 1}">
			<div class="normal-list-page list-page single_assign">
				<table
					class="table ">
					<thead>
						<tr>
							<th class="wid4">选择</th>
							<th>供应商名称</th>
							<th>地区</th>
							<th>归属采购</th>
							<th>合作次数</th>
							<th>供应商等级</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty traderList }">
							<c:forEach items="${traderList }" var="trader">
								<tr>
									<td><input type="radio" name="tarderId"
										value="${trader.traderId }"></td>
									<td class="text-ellipsis" title="${trader.traderName }">${trader.traderName }</td>
									<td>${trader.areaStr }</td>
									<td>${trader.ownerUser }</td>
									<td>${trader.changeTimes }</td>
									<td>${trader.level }</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<c:if test="${empty traderList}">
					<!-- 查询无结果弹出 -->
					<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
				</c:if>
				<c:if test="${not empty traderList }">
					<div class="single_to_user" >
						<div class="f_left">
							<div class="infor_name infor_name2">请选择新的归属采购</div>
							<div class="f_left">
								<select name="single_to_user">
									<option selected="selected" value="0">请选择采购人员</option>
									<c:if test="${not empty userList }">
										<c:forEach items="${userList }" var="user">
											<option value="${user.userId }">${user.username}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>

						<div class="pages_container" style="margin-top:0px;">
							<tags:page page="${page}" />
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="add-tijiao tcenter">
						<button type="submit" onclick="return formSub();">提交</button>
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${type eq 2}">
			<div class="batch_assign normal-list-page list-page">
				<table
					class="table ">
					<thead>
						<tr>
							<th>采购人员</th>
							<th>供应商数量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${user.username }</td>
							<td>${userSupplierNum }</td>
						</tr>
					</tbody>
				</table>
				<c:if test="${userSupplierNum > 0 }">
					<div class="overflow-hidden">
						<div class="infor_name infor_name2">请选择新的归属采购</div>
						<div class="f_left">
							<select name="batch_to_user">
								<option selected="selected" value="0">请选择采购人员</option>
								<c:if test="${not empty userList }">
									<c:forEach items="${userList }" var="user">
										<option value="${user.userId }">${user.username}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="add-tijiao tcenter mt10" >
						<button type="submit" onclick="return formSub();">提交</button>
					</div>
				</c:if>
			</div>
		</c:if>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/supplier/assign.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>