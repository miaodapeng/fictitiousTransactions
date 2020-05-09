<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="提示" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="addElement">
	<div class="add-main">
		<form action="" method="post" id="disableForm">
			<ul class="add-detail">
				<li>
					<div class="f_left">
							当前库房还有在库商品，不允许禁用！<br/>
							请将商品全部清理后再操作。
					</div>

				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<button class="dele" type="button" id="close-layer">好的</button>
			</div>
		</form>
	</div>
</div>
