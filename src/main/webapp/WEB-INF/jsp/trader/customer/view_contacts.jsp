
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="联系人详情" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/view_contact.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
function edit(url){
	parent.document.getElementById('tab_frame_1').src=url;
}
</script>
<%@ include file="customer_tag.jsp"%>
<div class="content">
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
               	 联系人基本信息
            </div>
            <input type="hidden" id="traderType" name="traderType" value="1"/>
            <input type="hidden" name="traderCustomerId" id="traderCustomerId" value="${traderCustomer.traderCustomerId}"/>
            <input type="hidden" name="traderId" id="traderId" value="${traderCustomer.traderId}"/>
            <div>
               
            	<c:if test="${traderContact.isEnable eq 1}">
            		<span class="title-click nobor font-red" onclick="setDisabled(${traderContact.traderContactId},0,0);">禁用</span>
            	</c:if>
            	<c:if test="${traderContact.isEnable eq 0}">
            		<span class="title-click nobor" onclick="setDisabled(${traderContact.traderContactId},1,0);">启用</span>
            	</c:if>
            	 <span class="title-click nobor" 
		              onclick="goUrl('${pageContext.request.contextPath}/trader/customer/toEditContactPage.do?traderContactId=${traderContact.traderContactId}&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}');">编辑</span>
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <tbody>
            	<tr>
                    <td >姓名</td>
                    <td >${traderContact.name}</td>
                    <td >性别</td>
                    <td >
                    	<c:if test="${traderContact.sex eq 0 }">女</c:if>
		                <c:if test="${traderContact.sex eq 1 }">男</c:if>
		                <c:if test="${traderContact.sex eq 2 }">未知</c:if>
                    </td>
                </tr>
                <tr>
                    <td >部门</td>
                    <td >${traderContact.department}</td>
                    <td >头衔</td>
                    <td >${traderContact.position}</td>
                </tr>
                <tr>
                    <td >电话</td>
                    <td >${traderContact.telephone}</td>
                    <td >传真</td>
                    <td >${traderContact.fax}</td>
                </tr>
                <tr>
                    <td >手机</td>
                    <td >${traderContact.mobile}</td>
                    <td >手机2</td>
                    <td >${traderContact.mobile2}</td>
				</tr>
                <tr>
                    <td >邮件</td>
                    <td >${traderContact.email}</td>
                    <td >QQ</td>
                    <td >${traderContact.qq}</td>
                </tr>
                <tr>
                    <td >在职状态</td>
                    <td >
                    	<c:if test="${traderContact.isOnJob eq 1 }">在职</c:if>
		                <c:if test="${traderContact.isOnJob eq 0 }">离职</c:if>
		            </td>
                    <td >注册帐号</td>
                    <td >${traderContact.account}</td>
                </tr>
                <tr>
                    <td >出生日期</td>
                    <td ><fmt:formatDate value="${traderContact.birthday}" pattern="yyyy-MM-dd"/></td>
                    <td >婚否</td>
                    <td >
                    	<c:if test="${traderContact.isMarried eq 1 }">已婚</c:if>
		                <c:if test="${traderContact.isMarried eq 0 }">未知</c:if>
		                <c:if test="${traderContact.isMarried eq 2 }">未婚</c:if>
                    </td>
                </tr>
                <tr>
                    <td >子女</td>
                    <td >
                    	<c:if test="${traderContact.haveChildren eq 1 }">有</c:if>
		                <c:if test="${traderContact.haveChildren eq 0 }">未知</c:if>
		                <c:if test="${traderContact.haveChildren eq 2 }">没有</c:if>
                    </td>
                    <td >学历</td>
                    <td >${traderContact.educationName}</td>
                </tr>
                <tr>
                    <td >备注</td>
                    <td >${traderContact.comments}</td>
                    <td >性格</td>
                    <td >${traderContact.characterName}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="parts table-style12">
        <div class="title-container">
            <div class="table-title nobor">
               	 联系人行业背景
            </div>
            <div class="title-click nobor  pop-new-data" 
            	layerParams='{"width":"800px","height":"350px","title":"新增联系人行业背景","link":"./addContactExperience.do?traderContactId=${traderContact.traderContactId}&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"}'>
                新增</div>
        </div>
        <c:if test="${not empty experienceList}">
	            	<c:forEach items="${experienceList}" var="ex">
        <table class="table">
            <tbody>
	            
	            		<tr>
		                    <td style="width: 150px">工作年份</td>
		                    <td ><date:date value ="${ex.begintime}" format="yyyy.MM"/>-<date:date value ="${ex.endtime}" format="yyyy.MM"/></td>
		                    <td style="width:150px;" rowspan="5">
		                    	 <span class="bt-smaller bt-border-style border-blue pop-new-data" 
            	layerParams='{"width":"800px","height":"350px","title":"编辑联系人行业背景","link":"./editContactExperience.do?traderContactExperienceId=${ex.traderContactExperienceId}&traderContactId=${traderContact.traderContactId}&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"}'>
                 				编辑</span>
		                    	 <span class="bt-smaller bt-border-style border-red" onclick="del(${ex.traderContactExperienceId},${ex.traderContactId})">删除</span>
		                    </td>
		                </tr>
		                <tr>
		                    <td >企业名称</td>
		                    <td >${ex.company}</td>
		                </tr>
		                <tr>
		                    <td >职位</td>
		                    <td >${ex.position}</td>
		                </tr>
		                <tr>
		                    <td >经营品牌</td>
		                    <td >${ex.brands}</td>
		                </tr>
		                <tr>
		                    <td >经营区域</td>
		                    <td >${ex.address}</td>
		                </tr>
	            	
            </tbody>
        </table>
        </c:forEach>
	            </c:if>
    </div>
    <!-- 
    <div class="ml10 pb50">
        友情提醒：
        <br/> 1、如果该客户同时也是供应商，则显示供应商相关数据，否则供应信息为空；采购次数和采购金额指我司向其采购订单的次数和金额。
    </div> -->
</div>
</body>


</html>
