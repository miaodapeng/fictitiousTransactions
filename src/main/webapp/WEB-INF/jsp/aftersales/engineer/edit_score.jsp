<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑评分" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>服务评分</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="serviceScore" id="serviceScore" value="${afterSalesInstallstion.serviceScore }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>技能评分</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="skillScore" id="skillScore" value="${afterSalesInstallstion.skillScore }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>评分备注</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="scoreComments" id="scoreComments" value="${afterSalesInstallstion.scoreComments }"/>
					</div>
					<div id="scoreCommentsError"></div>
					<div class="pop-friend-tips mt5">
                        	友情提示：
                     	<br/> 1、请基于事实填写评分；
                     	<br/> 2、请填写1-10之间的数字；
                     </div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="afterSalesInstallstionId" value="${afterSalesInstallstion.afterSalesInstallstionId }">
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<button type="submit">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/aftersales/engineer/edit_score.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>