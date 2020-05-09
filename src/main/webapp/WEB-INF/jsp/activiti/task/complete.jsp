<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增流程图" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/common/general.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/activiti/task/complete.js?rnd=<%=Math.random()%>"></script>
<div class="addElement" style="height: 100%">
	<form action="" method="get" id="completeTask">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span></span>
					<lable for='comment'>备注</lable>
				</div>
				<div class="f_left">
					<textarea name="comment" id='comment' class="input-large"
						placeholder="审核备注" rows="3"></textarea>
				</div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name='tid' id='tid' value="${params.tid}" /> <input
				type="hidden" name='piid' id='piid' value="${params.piid}" /> <input
				type="hidden" name='pass' id='pass' value="${params.pass}" />
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			$("input,textarea").bind("keydown select", function() {
				clearErrorMsg();//清除错误信息
			});

			$("#completeTask").submit(function(event) {

				var passVal = $('#pass').val().trim();
				var commentVal = $('#comment').val().trim();

				if (passVal == "false" && commentVal.length < 1) {
					warnTips("comment", "审核备注必填！");
					return false
				}
				var params = {
					"tid" : $('#tid').val().trim(),
					"piid" : $('#piid').val().trim(),
					"pass" : passVal,
					"comment" : commentVal
				}
				completeTask("${pageContext.request.contextPath}", params);
				return false;
			});
		});
	</script>
</div>
