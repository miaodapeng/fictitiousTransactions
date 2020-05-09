<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class="logistics-warehousein-addWarehouseIn">
	  <div class="form-list  form-tips8">
        <form action="" method="post" id="sendmsgs">
            <ul>
                <li>
                    <div class="form-tips">
                    <span id="lno">*</span>
                    <lable>备注信息：</lable>
                    </div>
                    <div class="f_left ">
                        <textarea rows="3" cols="60" id="comments">${saleorderWarehouseComments.comments }</textarea>
                    </div>
                </li>
                </ul>
           <div class="add-tijiao tcenter">
            <input id="saleorderId" type="hidden" value="${saleorderId}" name="saleorderId"/>
            <input id="saleorderWarehouseCommentsId" type="hidden" value="${saleorderWarehouseComments.saleorderWarehouseCommentsId }" name="saleorderWarehouseCommentsId"/>
			<button type="button" class="bt-bg-style bg-deep-green" onclick="updateComments();">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		   </div>
		 </form> 
	</div>
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
