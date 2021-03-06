<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="禁用库房" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="addElement">
	<div class="add-main">
		<form action="" method="post" id="disableForm">
			<ul class="add-detail">
				<li>
					<div class="infor_name">
						<span>*</span> <label>禁用原因</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" id="enableComment" name="enableComment" />
					    <input type="hidden" id="storageroomId" name="storageroomId" value="${storageroom.storageroomId}" /> 
					    <input type="hidden" id="isEnable" name="isEnable" value="${storageroom.isEnable}" />
						<div class="font-grey9 mt10 mb20">
							友情提示<br>1、该操作将一并关闭所含的货区、货架和库位；
						</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
			     <input type="hidden" name="beforeParams" value='${beforeParams}'/>
				<button type="submit">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/storageRoomMag/storageroomForbid.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>