<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增流程图" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/common/general.js?rnd=<%=Math.random()%>'></script>
<div class="addElement" style="height: 100%">
	<form action="" method="get" id="bindAction">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span>
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
					<span>*</span>
					<lable for='actionStr'>业务功能</lable>
				</div>
				<div class="f_left">
					<select id="actionStr" name="actionStr" class="font-black">
						<option value="0">-选择功能-</option>
					</select>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='preBusinessKey'>业务标识前缀</lable>
				</div>
				<div class="f_left">
					<textarea name='preBusinessKey' id='preBusinessKey' class="input-middle"
						placeholder="业务标识前缀" rows="3" readonly="readonly"></textarea>
				</div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name='procdefId' id='procdefId' value="${params.pdid}" />
			<input type="hidden" name='actionId' id='actionId' value="${params.pdid}" />
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			$("#bindAction").submit(function(event) {
				var params = {
					"procdefId" : $('#procdefId').val().trim(),
					"preBusinessKey" : $('#preBusinessKey').val().trim(),
					"actionId" : $('#actionId').val().trim(),
				}
				bindAction(params);
				return false;
			});

			$("#actionGroup").change(function() {
				getActionByActiongroupId($(this).val());
			});
			
			$("#actionStr").change(function() {
				changeItemValue($(this).val());
			});
		});

		var bindAction = function(params) {
			if ($.trim(params.procdefId) != "") {
				var api = '${pageContext.request.contextPath}/activiti/definition/bind_action.do';
				$.ajax({
					type : "POST",
					async : false,
					url : api,
					data : params,
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
									var actionStr = $("#actionStr");
									var option = "<option value=\"0\">-选择功能-</option>";
									$(actionStr).empty();//清空当前option
									for (var i = 0; i < data.actionList.length; i++) {
										var item = data.actionList[i];
										option += "<option  value=\"erp."
												+ item.moduleName + "." 
												+ item.controllerName + "." 
												+ item.actionName + "."
												+ "id."+ item.actionId 
												+ "\">"
												+ item.actionDesc + "</option>";
									}
									$(actionStr).append(option);
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								checkLogin();
							}
						});
			}
		}
		
		var changeItemValue = function(str){
			var strs = str.split(".id.");
			var preBusinessKey = strs[0];
			var actionId = strs[1];
			$('#preBusinessKey').val(preBusinessKey);
			$('#actionId').val(actionId);
		}
	</script>
</div>
