
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="转移联系人" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/supplier/transfer_contact.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
    <table class="table table-bordered table-striped table-condensed table-centered">
		<thead>
			<tr>
				<th>姓名</th>
				<th>性别</th>
				<th>部门</th>
				<th>头衔</th>
				<th>手机</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody class="employeestate">
			<tr>
				<td>${traderContact.name}</td>
				<td>
					<c:if test="${traderContact.sex eq 0 }">女</c:if>
                    <c:if test="${traderContact.sex eq 1 }">男</c:if>
                    <c:if test="${traderContact.sex eq 2 }">未知</c:if>
				</td>
				<td>${traderContact.department}</td>
				<td>${traderContact.position}</td>
				<td>${traderContact.mobile}</td>
				<td>
					<c:if test="${traderContact.isOnJob eq 1 }">在职</c:if>
                    <c:if test="${traderContact.isOnJob eq 0 }">离职</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	<form action="${pageContext.request.contextPath}/trader/supplier/getSupplisersByName.do" id="myform" method="post">
		<input type="hidden" name="traderContactId" value="${traderContact.traderContactId}" id="contactId"/>
		<input type="hidden" name="name" value="${traderContact.name}"/>
		<input type="hidden" name="sex" value="${traderContact.sex}"/>
		<input type="hidden" name="department" value="${traderContact.department}"/>
		<input type="hidden" name="position" value="${traderContact.position}"/>
		<input type="hidden" name="mobile" value="${traderContact.mobile}"/>
		<input type="hidden" name="isOnJob" value="${traderContact.isOnJob}"/>
		<input type="hidden" name="traderType" id="traderType" value="2"/>
		<input type="hidden" id="traderSupplierId" name="traderSupplierId" value="${traderSupplier.traderSupplierId}"/>
		<div>
			<ul>
				<li>
					<div class="infor_name ">
						<label>转移到供应商</label>	 
					</div>
					<div class='f_left'>
						<div class='inputfloat'>
							<input type="text" name="supplierName" id="searchName" value="${supplierName}"  class="input-largest">
							<span class="bg-light-blue bt-bg-style bt-small"  id="search">搜索</span>
							<span class="mt4" id="supplierName"></span>
							<span class="bg-light-blue bt-bg-style bt-small none"  id="research" onclick="research();">重新搜索</span>							
						</div>
						<div id="supplierNameError" class="font-red none">查询条件不允许为空</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
	<div id="cus">
		<table class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid20">供应商名称</th>
					<th>地区</th>
					<th class="wid15">创建时间</th>
					<th>归属销售</th>
					<th class="wid10">选择</th>
				</tr>
			</thead>
			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="tsv" varStatus="status">
						<tr>
							<td class="text-left">${tsv.traderSupplierName }</td>
							<td>${tsv.traderSupplierAddress }</td>
							<td><date:date value ="${tsv.addTime} "/></td>
							<td>${tsv.personal}</td>
							<td>
								<c:if test="${tsv.isView eq 1}">
								<!--  
									<input type='radio' value="${tsv.traderId}" />-->
									<span class="font-blue cursor-pointer" onclick="selectObj(${tsv.traderId},'${tsv.traderSupplierName }');">选择</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<tags:page page="${page}"/>
	</div>

	    <div class="font-grey9 ml15 line20">
        友情提醒：
        <br/> 1、转移联系人功能适用于联系人工作单做变更等情况；
        <br/> 2、转移联系人后，该联系人将从该公司迁移到目标公司；
        <br/> 3、转移联系人后，将清除联系人部门、头衔、电话、传真、状态等信息；
    </div>
    	<div class="add-tijiao tcenter mt15">
		<input type="hidden" name="traderId" />
         <button type="submit" id="submit">确定</button>
         <button type="button" class="dele" id="close-layer">取消</button>
    </div>
    </div>
</body>

</html>
