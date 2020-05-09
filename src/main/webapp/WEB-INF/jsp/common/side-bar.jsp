<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>贝登CRM</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/frame.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/nanoscroller.css?rnd=<%=Math.random()%>">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery.nanoscroller.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/libs/jquery/plugins/layer/layer.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/frame.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/form.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/side-bar.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/common.js?rnd=<%=Math.random()%>"></script>
	<script type="text/javascript">
		$(function(){
			
			
			
		})
		function addNewFlag(obj){
			$(obj).removeClass("iconnew");
			$.ajax({
    			url:page_url + '/order/saleorder/saveReadNewFlag.do',
    			type:"POST",
    			dataType : "json",
    			async: false,
    			success:function(data)
    			{
    				if(data.code==0){
    					
    				}else{
    					
    				}
    			},
    			error:function(data){
    				if(data.status ==1001){
    					layer.alert("当前操作无权限")
    				}
    			}
    		}); 
		}
	</script>
</head>

<body class="sidebarbody">
	<div class="nano has-scrollbar">
		<div class="frame-body container content">
			<div class="side-bar">
				<div class="side-nav-content">
					<div style="height: 42px">
						<img src="${pageContext.request.contextPath}/static/images/sidebarlogo.jpg" style="position: fixed; top: 0px; left: -4px; z-index: 22;">
					</div>
					<ul class="side-nav" id="side-nav">
						<c:forEach items="${menu1List}" var="list1" varStatus="status1">
							<c:set var="actionCount" value="0"></c:set>
							<li id="actiongroup${status1.index}">
								<h4>
									<i class="${list1.iconStyle}"></i>${list1.name}
								</h4>
								<c:if test="${not empty list1.childNode}">
									<c:forEach items="${list1.childNode}" var="child">
										<h3>
											<i class="${child.iconStyle}"></i>${child.name}
										</h3>
									<ul>
										<c:forEach items="${menu2List}" var="list2" varStatus="status2">
											<c:if test="${child.actiongroupId eq list2.actiongroupId}">
												<li>
													<c:if test="${list2.actionDesc eq '产品检索'}">
														<a href="javascript:void(0);" link="${pageContext.request.contextPath}/${list2.moduleName}/${list2.controllerName}/${list2.actionName}.do" id="${status1.count}${status2.count}"
														   <c:if test="${isClick eq 0 && haveAd eq 1}">class="iconnew" onclick="addNewFlag(this);"</c:if>  >${list2.actionDesc}</a>
													</c:if>
													<c:if test="${list2.actionDesc ne '产品检索'}">
														<a href="javascript:void(0);" link="${pageContext.request.contextPath}/${list2.moduleName}/${list2.controllerName}/${list2.actionName}.do" id="${status1.count}${status2.count}">${list2.actionDesc}</a>
													</c:if>
												</li>
												<c:set var="actionCount" value="1"></c:set>
											</c:if>
										</c:forEach>
									</ul>
									</c:forEach>
								</c:if>
								<c:if test="${empty list1.childNode}">
									<ul>
										<c:forEach items="${menu2List}" var="list2" varStatus="status2">
											<c:if test="${list1.actiongroupId eq list2.actiongroupId}">
												<li>
													<c:if test="${list2.actionDesc eq '产品检索'}">
														<a href="javascript:void(0);" link="${pageContext.request.contextPath}/${list2.moduleName}/${list2.controllerName}/${list2.actionName}.do" id="${status1.count}${status2.count}"
														   <c:if test="${isClick eq 0 && haveAd eq 1}">class="iconnew" onclick="addNewFlag(this);"</c:if>  >${list2.actionDesc}</a>
													</c:if>
													<c:if test="${list2.actionDesc ne '产品检索'}">

														<a href="javascript:void(0);" link="${list2.finalUrl}" id="${status1.count}${status2.count}">${list2.actionDesc}</a>
													</c:if>
												</li>
												<c:set var="actionCount" value="1"></c:set>
											</c:if>
										</c:forEach>
									</ul>
								</c:if>
							</li>
							<c:if test="${actionCount eq 0}">
								<script>
									$(document).ready(function(){
										var index = ${status1.index};
										$("#actiongroup"+index).remove();
									});
								</script>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<div class="sidebar-bottom">
			<div class="users">
				<div class="fixedInformation">
					<div class="userinfor">
						<p class="pl7">
							<i class="iconusername"></i>
							${user.username}
						</p>
						<c:forEach items="${position }" var="posit">
							<c:if test="${posit.orgId eq user.orgId}">
								<p class="pl33" title="${posit.orgName }">${posit.orgName }</p>
								<p class="pl33" title="${posit.positionName }">${posit.positionName }</p>
							</c:if>
						</c:forEach>
					</div>
					<ul>
						<c:if test="${position.size() > 1 }">
						<li class="pop-new-data-left"
							layerParams='{"width":"800","height":"280","title":"切换职位","link":"${pageContext.request.contextPath}/changeorg.do"}'>
							<i class="iconuserdepartment"></i> 切换职位
						</li>
						</c:if>
						<li onclick="logout();"><i class="iconuserposition"></i>退出系统</li>
					</ul>
				</div>
				<div class="iconuserarrow">
					<i></i>
				</div>
			</div>
			<ul>
				<li class="pos_rel">
					<a href="javascript:void(0);" link="${pageContext.request.contextPath}/system/message/index.do" onclick="_click();"
					title="消息列表" name="消息列表"><i class="iconunews" ></i><i class="newstips" id="msg" style="display: none;"></i></a></li>
				<li><a href="javascript:void(0);" link="${pageContext.request.contextPath}/system/notice/helpNoticeListPage.do" title="系统帮助"
					name="系统帮助"><i class="iconhelp"></i></a></li>
				<li><a href="javascript:void(0);"
					link="${pageContext.request.contextPath}/system/user/myinfo.do?userId=${user.userId}"
					title="个人设置" name="个人设置"><i class="iconsetting"></i></a></li>
				<li><i class="iconuser"></i></li>
			</ul>
			<input type="hidden" id="userId" value="${ sessionScope.curr_user.userId}"/>
		</div>
	</div>
	<script type="text/javascript">
var userId = $("#userId").val();
var url = "http://"+curWwwPath.substring(7, pos) + projectName+"/system/message/getMessageCount.do";

//查询当前未处理的信息
$.ajax({
	async : false,
	url : url,
	data : {
		"userId" : userId
	},
	type : "POST",
	dataType : "json",
	success : function(data) {
		if(data.code == 0){
		   if(data.data>0){
		    	 $("#msg").show();
		     }else{
		    	 $("#msg").hide();
		     } 
		}else{
			layer.alert(data.message, { icon : 2});
		}
	}
});
function _click(){
	//$(".messageContainer").empty();
	$(window.parent.document).contents().find('.messageContainer').empty();
}
</script>
</body>

</html>
