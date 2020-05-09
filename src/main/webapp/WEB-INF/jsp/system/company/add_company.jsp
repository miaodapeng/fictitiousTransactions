<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增分公司" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="addElement">
	<div class="add-main">
		<c:if test="${not empty allErrors }">
			<div class="service-return-error">
				<c:forEach items="${allErrors }" var="error" varStatus="status">
					${status.count}、${error.defaultMessage }<br/>
				</c:forEach>
	        </div>
		</c:if>
		<sf:form modelAttribute="company" method="post" id="companyform">
	        <ul class="add-detail">
	            <li>
	            	<div class="infor_name">
	                    <span>*</span>
	                    <lable>公司名称</lable>
	                </div>
	                <div class="f_left">
	                	<sf:input path="companyName" name="companyName" id="companyName" class="input-larger"/>
	                </div>
	                <div class="clear"></div>
	            </li>
	            <li>
	            	<div class="infor_name">
	                    <span>*</span>
	                    <lable>访问地址</lable>
	                </div>
	                <div class="f_left">
	                	<sf:input path="domain" name="domain" id="domain" class="input-larger"/>
	                </div>
	                <div class="clear"></div>
	            </li>
	        </ul>
	        <div class="add-tijiao tcenter">
	        	<input type="hidden" name="formToken" value="${formToken}"/>
	            <button type="submit" id="submit">提交</button>
	            <button type="button" class="dele " id="close-layer">取消</button>
	        </div>
	    </sf:form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/company/add.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>