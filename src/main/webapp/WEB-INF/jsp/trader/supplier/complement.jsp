<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
 <div class="formpublic">
            <form method="post" action="" id="complement">
                <ul>
                   <li>
					<div class="infor_name">
						<c:if test="${pass==false}">
						<span>*</span>
						</c:if>
						<lable for='name'>备注</lable>
					</div>
					<div class="f_left">
						<input type="text" name="comment" id="comment" class="input-larger" value="" />
					</div>
				</li>
                </ul>
                <div class="add-tijiao tcenter">
                	<input type="hidden" value="${taskId}" name="taskId">
                	<input type="hidden" value="${pass}" name="pass">
                	<input type="hidden" value="${traderSupplierId}" name="traderSupplierId">
                	<input type="hidden" name="formToken" value="${formToken}"/>
                    <button type="button" class="bg-light-green" onclick="complementTask()">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
           </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/supplier/complete.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>