<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="cn-zh">

<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>内呼弹窗</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/admin/skin/system.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/callcenterlayer.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/libs/jquery/plugins/layer/layer.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/form.js?rnd=<%=Math.random()%>'></script>
</head>
<script type="text/javascript">
	$(document)
			.ready(
					function(e) {
						//倒计时
						timeEnd();

						//选中样式
						$("tr").click(function() {
							$("tr").removeClass("selected");
							$(this).addClass("selected");
						});

						//搜索事件
						$("#keyword")
								.keyup(
										function() {
											var keyword = $("#keyword").val();
											if (keyword) {
												keyword = keyword
														.toLocaleLowerCase();
												$
														.each(
																$("table"),
																function() {
																	if ($(this)
																			.css(
																					'display') != 'none') {
																		$
																				.each(
																						$(
																								this)
																								.find(
																										"tbody")
																								.find(
																										"tr"),
																						function(
																								i,
																								n) {
																							var trcontent = $(
																									this)
																									.text();
																							trcontent = trcontent
																									.toLocaleLowerCase();
																							if (trcontent
																									.indexOf(keyword) < 0) {
																								$(
																										this)
																										.hide();
																							} else {
																								$(
																										this)
																										.show();
																							}
																						});
																	}
																});
											} else {
												$
														.each(
																$("table"),
																function() {
																	if ($(this)
																			.css(
																					'display') != 'none') {
																		$
																				.each(
																						$(
																								this)
																								.find(
																										"tbody")
																								.find(
																										"tr"),
																						function(
																								i,
																								n) {
																							$(
																									this)
																									.show();
																						});
																	}
																});
											}
										});
					});

	var e = 60;
	var et;
	function timeEnd() {
		e--;
		$(".value2").html(e);
		if (e == 0) {
			cancel();
		} else {
			et = setTimeout("timeEnd()", 1000);
		}
	}

	function clearTimeEnd() {
		e = 60;
		clearTimeout(et);
	}

	var obj = window.parent.document.getElementById("callcenter").contentWindow;
	/**
	 * 内呼
	 */
	function dial() {
		var ani = $("tr[class='selected']").attr('alt');

		if (ani) {
			obj.document.getElementById("csSoftPhone").callInner(ani);
			clearTimeEnd();
			window.parent.closeAgent();
		}
	}
	/**
	 * 监听
	 */
	function monitor() {
		var ani = $("tr[class='selected']").attr('alt');

		if (ani) {
			var agent = $("tr[class='selected']").attr('alt2');
			obj.document.getElementById("csSoftPhone").monitorAgent(ani);
			clearTimeEnd();
			window.parent.document.getElementById('monitorAgent').value = agent;
			window.parent.closeAgent();
		}
	}

	/**
	 * 转接
	 */
	function oneStepTransfer() {
		var ani = $("tr[class='selected']").attr('alt');

		if (ani) {
			var agent = $("tr[class='selected']").attr('alt2');
			obj.document.getElementById("csSoftPhone").oneStepTransfer(ani,
					'AGT');
			clearTimeEnd();
			window.parent.closeAgent();
		}
	}
	/**
	 * 内部咨询
	 */
	function consultex() {
		var ani = $("tr[class='selected']").attr('alt');

		if (ani) {
			var agent = $("tr[class='selected']").attr('alt2');
			if (!agent) {
				agent = ani;
			}
			if ($("#dept_phone").css('display') != 'none') {
				obj.document.getElementById("csSoftPhone").consultex(ani,
						"OUT", '');
			} else {
				obj.document.getElementById("csSoftPhone").consultex(ani,
						'AGT', '');
			}
			clearTimeEnd();
			window.parent.document.getElementById('monitorAgent').value = agent;
			window.parent.closeAgent();
		}
	}

	function cancel() {
		clearTimeEnd();
		var agentstate = obj.document.getElementById("csSoftPhone")
				.getAgentInfo('AGENTSTATE');//模式
		if (agentstate == 16) {
			obj.document.getElementById("csSoftPhone").cancelPrepareMonitor();
		}
		if (agentstate == 24) {
			obj.document.getElementById("csSoftPhone").cancelPrepareCallInner();
		}
		window.parent.closeAgent();
	}

	function agentShow(index, obj) {
		if (index == '1') {
			$("#agent_leave").hide();
			$("#dept_phone").hide();
			$("#agent_on").show();
		}
		if (index == '2') {
			$("#agent_on").hide();
			$("#dept_phone").hide();
			$("#agent_leave").show();
		}
		if (index == '3') {
			$("#agent_on").hide();
			$("#agent_leave").hide();
			$("#dept_phone").show();
		}
		$(obj).siblings("li").removeClass("selected");
		$(obj).addClass("selected");
	}
</script>

<body>
	<div class="layer-content agent-list">
		<div class="callcenter-title">
			<div class="left-title">座机列表</div>
		</div>
		<div class="menu-bar">
			<ul class="menu-item">
				<li class="selected" onclick="agentShow(1,this);">座席</li>
				<li onclick="agentShow(2,this);">分机</li>
				<c:if test="${type == 2 }">
					<li onclick="agentShow(3,this);">支持部门</li>
				</c:if>
			</ul>
		</div>
		<div class="agent-content-box">
			<div class="content-list">
				<table class="table table-td-border1" id="agent_on">
					<thead class="table-header">
						<tr>
							<td>分机</td>
							<td>名称</td>
							<td>状态</td>
							<td>工作模式</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${agentOn }" var="agent">
							<tr alt="${agent.INS }" alt2="${agent.AGENTNAME }">
								<td>${agent.INS }</td>
								<td>${agent.AGENTNAME }</td>
								<td><c:choose>
										<c:when test="${agent.ST !=3 }">
									对方忙
									</c:when>
										<c:otherwise>
									空闲
									</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${agent.WKMD == 1}">
									下班模式
									</c:when>
										<c:when test="${agent.WKMD == 3}">
									外拨模式
									</c:when>
										<c:when test="${agent.WKMD == 4}">
									班长模式
									</c:when>
										<c:otherwise>
									普通模式
									</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<table class="table table-td-border1" id="agent_leave"
					style="display: none;">
					<thead class="table-header">
						<tr>
							<td>分机</td>
							<td>名称</td>
							<td>状态</td>
							<td>工作模式</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${agentLeave }" var="agent">
							<tr alt="${agent.INS }" alt2="">
								<td>${agent.INS }</td>
								<td></td>
								<td>空闲</td>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<table class="table table-td-border1" id="dept_phone"
					style="display: none;">
					<thead>
						<tr>
							<td>号码</td>
							<td>部门</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${deptPhone }" var="phone">
							<tr alt="${phone.call }" alt2="${phone.call }">
								<td>${phone.phone}</td>
								<td>${phone.name }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="seatllist">
				<div class="seatllist-search">
					<input type="text" name="keyword" id="keyword"> <span
						class="">查找</span>
				</div>
				<div class="countdown">
					<span class="value2 time">60</span> <span>秒后自动关闭</span>
				</div>
				<div>
					<button class="bt-bg-style bt-small bg-light-green" type="button"
						<c:choose>
						<c:when test="${type == 0 }">
							<c:if test="${agentState == 24}">
								onclick="dial();"
							</c:if>
							<c:if test="${agentState == 16}">
								onclick="monitor();"
							</c:if>
						</c:when>
						<c:when test="${type == 1 }">
							onclick="oneStepTransfer();"
						</c:when>
						<c:when test="${type == 2 }">
							onclick="consultex();"
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
						>确定</button>
					<button class="bt-bg-style bt-small bg-light-red" type="button"
						id="close-layer" onclick="cancel();">取消</button>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>

</html>


