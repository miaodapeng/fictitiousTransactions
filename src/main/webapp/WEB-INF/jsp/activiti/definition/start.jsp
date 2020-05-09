<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增流程图" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/common/general.js?rnd=<%=Math.random()%>'></script>
<div class="addElement" style="height: 100%">
	<form action="" method="get" id="startProcess">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='userName'>办理人</lable>
				</div>
				<div class="f_left">
					<select id="userName" name="userName" class="font-black">
						<option value="">默认</option>
						<optgroup label="-重新分配-">
							<c:forEach items="${userList}" var="user" varStatus="status">
								<option value="${user.username}">${user.username}</option>
							</c:forEach>
						</optgroup>
					</select>
				</div>
			</li>

			<li>
				<div class="infor_name">
					<span></span>
					<lable for='businessKey'>业务分组</lable>
				</div>
				<div class="f_left">
					<select id="actionGroup" name="actionGroup" class="font-black">
						<option value="0">-选择分组-</option>
						<c:forEach items="${actiongroupList}" var="actiongroup"
							varStatus="status">
							<option value="${actiongroup.actiongroupId}">${actiongroup.name}</option>
						</c:forEach>
					</select>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable for='businessKey'>业务功能</lable>
				</div>
				<div class="f_left">
					<select id="businessKey" name="businessKey" class="font-black">
						<option value="0">-选择功能-</option>
					</select>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<span></span>
					<lable for='name'>流程名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='name' id='name' class="input-middle"
						placeholder="流程实例名称" value="${fullName}" />
				</div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name='pdid' id='pdid' value="${params.pdid}" />
			<input type="hidden" name='pdname' id='pdname'
				value="${params.pdname}" /> <input type="hidden" name='dname'
				id='dname' value="${params.dname}" />
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			$("#startProcess").submit(function(event) {
				var nameVal = $('#name').val().trim();
				var params = {
					"pdid" : $('#pdid').val().trim(),
					"name" : nameVal,
					"businessKey" : $('#businessKey').val().trim(),
					"pdname" : $('#pdname').val().trim(),
					"dname" : $('#dname').val().trim(),
					"userName" : $('#userName').val().trim()
				}
				startProcess(params);
				return false;
			});

			$("#actionGroup").change(function() {
				getActionByActiongroupId($(this).val());
			});
		});

		var startProcess = function(params) {
			if ($.trim(params.pdid) != "") {
				var api = '${pageContext.request.contextPath}/activiti/definition/start.do';
				$.ajax({
					type : "POST",
					async : false,
					contentType : " application/json;charset=utf-8",
					url : api,
					data : JSON.stringify(params),
					dataType : 'json',
					success : function(data) {
						var nowParent = self.parent;//把当前弹窗的父级对象赋给变量，以防当前弹窗被关掉后无法调用父级js
						nowParent.layer.closeAll('iframe');//关闭当前弹窗
						if (data.code == 0) {
							nowParent.layer.confirm(data.message, {
								btn : [ '关闭' ]
							}, function() {
								layer.closeAll('iframe');
								nowParent.layer.closeAll('dialog');
							});
						} else {
							nowParent.layer.confirm(data.message, {
								btn : [ '关闭' ]
							}, function() {
								nowParent.layer.closeAll('dialog');
							});
						}

					},
					error : function(XMLHttpRequest, textStatus) {
						checkLogin();
					}
				});
			}
		}

		var getActionByActiongroupId = function(groupId) {
			if ($.trim(groupId) > 0) {
				var api = '${pageContext.request.contextPath}/activiti/definition/action_by_actiongroup_id.do';
				$
						.ajax({
							type : "GET",
							async : false,
							contentType : " application/json;charset=utf-8",
							url : api,
							data : {
								"groupId" : groupId
							},
							dataType : 'json',
							success : function(data) {
								if (data.code === 0) {
									var businessKey = $("#businessKey");
									var option = "<option value=\"0\">-选择功能-</option>";
									$(businessKey).empty();//清空当前option
									for (var i = 0; i < data.actionList.length; i++) {
										var item = data.actionList[i];
										option += "<option  value=\"erp."
												+ item.moduleName + "." 
												+ item.controllerName + "." 
												+ item.actionName + "."
												+ "id."+ item.actionId 
												+ ":1\">"
												+ item.actionDesc + "</option>";
									}
									$(businessKey).append(option);
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								checkLogin();
							}
						});
			}
		}
	</script>
</div>
