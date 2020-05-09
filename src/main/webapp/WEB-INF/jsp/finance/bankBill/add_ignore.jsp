<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="操作确认" scope="application" />
<%@ include file="../../common/common.jsp"%>
 <div class="formpublic">
            <form method="post" action="">
                <ul>
                   <li>
					<div class="infor_name">
						<span>*</span>
						<lable for='name'>忽略原因</lable>
					</div>
					<div class="f_left">
						<select name="matchedObject" id="matchedObject">
							<c:forEach var="list" items="${macthObjectList}">
								<c:if test="${list.sysOptionDefinitionId != 841}">
								<option value="${list.sysOptionDefinitionId}">${list.title}</option>
								</c:if>
							</c:forEach>
						</select>
						<!-- <input type="text" name="comments" id="comments" class="input-larger" value="" /> -->
					</div>
				</li>
                </ul>
                <div class="add-tijiao tcenter">
                    <button class="bg-light-blue" type="button" onclick="ignoreadd(this,${bankBillId},0);">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
           </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/finance/bankBill/bankBillMatch.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>