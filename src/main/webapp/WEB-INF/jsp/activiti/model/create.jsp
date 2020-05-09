<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增流程图" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/common/general.js?rnd=<%=Math.random()%>'></script>
<div class="addElement" style="height: 100%">
	<form action="" method="get" id="createProcessModelForm">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='tenantId'>分公司</lable>
				</div>
				<div class="f_left">
					<select id="tenantId" name="tenantId" class="font-black">
						<option value="0">选择分公司</option>
						<c:if test="${not empty companyList}">
							<c:forEach items="${companyList}" var="company"
								varStatus="status">
								<option value="erp:company:${company.companyId}">${company.companyName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='name'>名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='name' id='name' class="input-middle"
						placeholder="流程定义名称" />
				</div>
			</li>
			<li>
				<div class="infor_name">
					<span></span>
					<lable for='description'>备注</lable>
				</div>

				<div class="f_left">
					<textarea name='description' id='description' class="input-middle"
						placeholder="流程定义备注"></textarea>
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			$("input,textarea,select").bind("keydown change", function() {
				clearErrorMsg();//清除错误信息
			});
			$("#createProcessModelForm")
					.submit(
							function(event) {

								var nameVal = $('#name').val().trim();
								var tenantIdVal = $('#tenantId').val().trim();
								if (tenantIdVal == 0) {
									warnTips("tenantId", "请选择分公司");
									return false
								}
								if (nameVal.length < 1) {
									warnTips("name", "流程设计模型图名称必填");
									return false
								}

								var descriptionVal = $('#description').val();

								$
										.ajax({
											type : "POST",
											url : "",
											async : false,//同步模式
											data : {
												"tenantId" : tenantIdVal,
												"name" : nameVal,
												"description" : descriptionVal
											},
											dataType : 'json',
											complete : function() {

											},
											success : function(data) {
												if (data.code == 0) {
													var nowParent = self.parent;//把当前弹窗的父级对象赋给变量，以防当前弹窗被关掉后无法调用父级js
													nowParent.layer
															.closeAll('iframe');//关闭当前弹窗
													nowParent.layer
															.confirm(
																	"添加模型数据成功，现在就去设计流程图？",
																	{
																		btn : [
																				'确定',
																				'取消' ]
																	// 按钮
																	},
																	function() {//第一按钮
																		nowParent
																				.openNewPage(
																						"activiti_processModel_editor_"
																								+ data.modelId,
																						"编辑流程定义",
																						"./modeler.html?modelId="
																								+ data.modelId);
																		nowParent
																				.refreshNowPageList(data);
																	},
																	function() {//第二按钮
																		nowParent
																				.refreshNowPageList(data);
																	});
												}
											},
											error : function(XMLHttpRequest,
													textStatus) {
												checkLogin();
											}
										});

								return false;
							});

		});
	</script>
</div>
