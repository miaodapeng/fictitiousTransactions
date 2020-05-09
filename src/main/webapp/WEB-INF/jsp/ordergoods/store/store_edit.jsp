<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑店铺" scope="application" />
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
		<form method="post" id="storeform">
	        <ul class="add-detail">
	            <li>
	            	<div class="infor_name">
	                    <span>*</span>
	                    <lable>店铺名称</lable>
	                </div>
	                <div class="f_left">
	                	<input name="name" id="name" class="input-larger" value="${ordergoodsStore.name }"/>
	                </div>
	                <div class="clear"></div>
	            </li>
	        </ul>
	        <div class="add-tijiao tcenter">
	        	<input type="hidden" name="ordergoodsStoreId" value="${ordergoodsStore.ordergoodsStoreId }">
	        	<input type="hidden" name="beforeParams" value='${beforeParams}'/>
	            <button type="submit" id="submit">提交</button>
	            <button type="button" class="dele " id="close-layer">取消</button>
	        </div>
	    </form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/ordergoods/store/store_edit.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>