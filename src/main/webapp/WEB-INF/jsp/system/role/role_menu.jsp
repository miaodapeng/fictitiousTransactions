<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="设置权限" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/admin/role/base.css?rnd=<%=Math.random()%>" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/admin/role/index.css?rnd=<%=Math.random()%>" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/admin/role/list.css?rnd=<%=Math.random()%>" />
<div class="content bx">
	<!-- 角色，数据权限开始 -->
	<section>
		<div class="roleBox">
			<div class="role">
				<p>所选角色  :</p>&nbsp;&nbsp;&nbsp;${exist.roleName}
			</div>
			<div class="Jurisdiction">
				<p>数据权限  :</p>
				<div class="xuanze">
					<input type="checkbox" name="dataAuthority" value="1"
							<c:forEach items="${data}" var="each">
								<c:if test="${each.platformId == 1}">
									checked="checked"
								</c:if>
							</c:forEach>
					/><label>贝登</label>
					<input type="checkbox" name="dataAuthority" value="3"
							<c:forEach items="${data}" var="each">
								<c:if test="${each.platformId == 3}">
									checked="checked"
								</c:if>
							</c:forEach>/>
					<label>医械购</label>
					<button class="btn1">提交</button>
				</div>
			</div>
		</div>
        <input type="hidden" id="roleId" name="roleId" value="${exist.roleId}">
	</section>
	<!-- 角色，数据权限结束-->

	<!-- 应用平台开始 -->
	<section>
		<c:forEach items="${platformList}" var="platform" varStatus="pIndex">
			<div class="applicationBox">
				<div class="application_hd">
					<h3>应用平台-${platform.platformName}</h3>
					<span class="edit-user addtitle" tabTitle='{"num":"addmenu${role.roleId}","link":"./system/role/addmenu.do?roleId=${role.roleId}&platformId=${platform.platformId}","title":""}'>
									修改权限
								</span>
				</div>
				<c:if test="${not empty allList}">
					<div class="applicationMain">
						<c:forEach items="${allList}" var="eachList" varStatus="eIndex">
							<c:if test="${pIndex.count == eIndex.count}">
								<c:if test="${empty eachList}">
									暂未设置权限
								</c:if>
							</c:if>
							<c:if test="${not empty eachList}">
								<c:forEach items="${eachList}" var="group">
									<c:if test="${group.platformId == platform.platformId}">
										<div class="applicationCloum">
											<h3>${group.groupName}</h3>
											<div class="mainbox">
												<c:if test="${not empty group.childNode}">
													<c:forEach items="${group.childNode}" var="childNode">
														<div class="cloumMain">
															<p>${childNode.groupName} ：</p>
															<ul>
																<c:if test="${not empty childNode.actionList}">
																	<c:forEach items="${childNode.actionList}"
																			   var="eachAction" varStatus="actionIndex">
																		<li title="${eachAction.actionId}-${eachAction.actionName}">${eachAction.actionId}-${eachAction.actionName}；</li>
																	</c:forEach>
																</c:if>
															</ul>
														</div>
													</c:forEach>
												</c:if>
												<c:if test="${empty group.childNode}">
													<div class="cloumMain">
														<ul>
															<c:if test="${not empty group.actionList}">
																<c:forEach items="${group.actionList}" var="eachAction">
																	<li title="${eachAction.actionId}-${eachAction.actionName}">${eachAction.actionId}-${eachAction.actionName}；</li>
																</c:forEach>
															</c:if>
														</ul>
													</div>
												</c:if>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
			</div>
		</c:forEach>
	</section>
	<!-- 应用平台结束 -->
	</div>
<script>
	$(function(){
		$('.btn1').on('click',function(){
			var chk_value ='';//定义空字符串
			for(var i = 0;i<$('.xuanze input').length;i++){//循环遍历选中的value值拼接到字符串中
				if($(".xuanze input")[i].checked==true&&chk_value.indexOf($(".xuanze input")[i].value)==-1){
					chk_value = chk_value+ $(".xuanze input")[i].value+','
				}
			}
			// 将字符串最后逗号去除
			var newval = chk_value.replace(/[&,]$/,"")
			var roleId =  $('#roleId').val()
			$.ajax({
				async: false,
				type: "POST",
				url: "/system/role/saveAuthority.do",
				data: {'data':newval, 'roleId':roleId},
				dataType:'json',
				success: function(data){
					if(data.code == 0){
						layer.confirm("保存成功", {
							btn: ['确定'] //按钮
						}, function(){
							layer.close(layer.index);
						});
					}else{
						layer.confirm("保存失败", {
							btn: ['确定'] //按钮
						}, function(){
							layer.close(layer.index);
						});
					}
				},
				error:function(data){
					layer.confirm("保存失败", {
						btn: ['确定'] //按钮
					}, function(){
						layer.close(layer.index);
					});
				}
			});

		})
	})
</script>
	<%@ include file="../../common/footer.jsp"%>