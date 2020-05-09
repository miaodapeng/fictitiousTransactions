<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
 <div class="formpublic">
            <form method="post" action="" id="complement">
                <ul>
                  <c:if test="${pass==false}">
                   <li>
                   	 <div class="infor_name">
						<span>*</span>
						<lable for='name'>驳回原因</lable>
					 </div>
                   	 <div class="f_left">
                        <select name="reason">
                            <option value="">请选择驳回原因</option>
                            <option value="1">再次补充商机消息</option>
                            <option value="2">备注原因不属实</option>
                        </select>
                        <div class="font-red " style="display: none">关闭原因不允许为空</div>
                    </div>
                   </li>
                   </c:if>
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
                	<input type="hidden" name="formToken" value="${formToken}"/>
                    <button type="button" class="bg-light-green" onclick="complementTask()">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
           </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/bussinesschance/complete.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>