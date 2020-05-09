<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<title id="test">贝登ERP资源管理系统</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/content.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/front-page.css?rnd=<%=Math.random()%>">	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/closable-tab.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/main_page.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/libs/jquery/plugins/layer/layer.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/libs/jquery/plugins/layer/mylayer.js?rnd=<%=Math.random()%>"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-min-draggable.js?rnd=<%=Math.random()%>"></script>

<script src="${pageContext.request.contextPath}/static/js/superSlide.2.1.1.js?rnd=<%=Math.random()%>" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/sales_juhe.js?rnd=<%=Math.random()%>" type="text/javascript"></script>
<script type='text/javascript'>
	if (top.location != self.location)
	{
		top.location=self.location;
	}
</script>
</head>

<body>
	<div id="hidden-side-bar" class="hidden-side-bar side-actived">
		<i class='sideleft'></i>
	</div>
	<div class="window_change">
		<div class="side-bar-frameset-box left-box" style="position: relative">
			<iframe class="side-bar-frame "
				src="${pageContext.request.contextPath}/menu.do" id="side-bar-frame"
				name="side-bar-frame " noresize scrolling="no ">
				您的浏览器不支持框架，请升级浏览器以便正常访问 </iframe>
		</div>
		<!-- 注意：如果Callcenter存在， 那么把 " main-frameset-box " 里面的 ' right-box0 ' 删掉，如果不存在就要把 right-box0 加上去-->
		<div
			class="main-frameset-box right-box <c:if test="${empty user.userDetail.ccNumber }">right-box0</c:if>"
			style="overflow-y: hidden;">
			<div class="main-frame ">
				<div class="closable-tab container closable-tab-page pos_rel "
					id="container ">
					<div class="same t_left ">
						<i class="iconleft "></i>
					</div>
					<div class='same t_right'>
						<i class="iconright "></i>
					</div>
					<div class="row ">
						<!-- 此处是相关代码 -->
						<div class="title " id="title ">
							<ul class="nav nav-tabs " role="tablist ">
							</ul>
						</div>
						<div class="tab-content " style="width: 100%; height: 620px"
							id="tab-content"></div>
						<!-- 相关代码结束 -->
					</div>
				</div>
			</div>
		</div>
		<c:if test="${not empty user.userDetail.ccNumber }">
			<div class="top-bar-frameset-box bottom-box"
				style="position: relative;">
				<iframe class="top-bar-frame " id="callcenter"
					src="${pageContext.request.contextPath}/system/call/index.do"
					name="top-bar-frame " scrolling="no " noresize>
					您的浏览器不支持框架，请升级浏览器以便正常访问 </iframe>
				<input type="hidden" id="call_traderId" value="0" /> <input
					type="hidden" id="call_traderType" value="0" /> <input
					type="hidden" id="call_callType" value="0" /> <input type="hidden"
					id="call_orderId" value="0" /> <input type="hidden"
					id="call_traderContactId" value="0" /> <input type="hidden"
					id='monitorAgent' value="" /> <input type="hidden" id='callFailedId'
					value="" /> <input type="hidden" id='call_screen'
					value="1" />
			</div>
			<div id="hidden-top-bar" class="hidden-top-bar1 ">
				<i class="bottomdown"></i>
			</div>
		</c:if>
	</div>
	<input type="hidden" id="userId" value="${ sessionScope.curr_user.userId}"/>
	<input type="hidden" id="positType" value="${ sessionScope.curr_user.positType}"/>
	<input type="hidden" id="websocketUrl" value="${ wsUrl}"/>
	<div id="messageContainer" class="messageContainer">
		<!-- <div class="messageTip">
			<i class="iconmessages"></i> <span>【财务管理】</span> <i
				class="iconmessagecha"></i>
		</div>
		<div class="messagecontent pos_rel">
			<span>您的提前开票申请被拒绝，请查看。</span> <i class="iconarrow"></i>
		</div> -->
	</div>
	<div class="msg-always-wrap" style="display: none">
		【商机管理】未读<span class="msg-always-num" id="msgNum"></span>条
	</div>
	<c:if test="${count==0}">
		<input type="hidden" id="noticeId" value="${notice.noticeId}">
		<div class="version-attention">
            <div class="version-ct">
              <div class="version-box">
                 <div class="version-tit1">${notice.title}</div>
				 <div style="padding:10px 100px;">
				     ${notice.content}
				 </div>
			  </div>
              <div class="have-read"><span>我已阅读(5S)</span></div>
            </div>
        </div>
    </c:if>
	<c:if test="${sessionScope.curr_user.positType eq 310 && not empty list}">
		<div class="front-page-slide" <c:if test='${count==0}'>style="display: none;"</c:if>>
             <div class="index-page">
                <div class="index-slide-banner">
                    <div class="front-page-close"><i class="icon icon-page-close"></i></div>
                    <div class="bd">
                        <ul>
	                        <c:forEach items="${list}" var="ad">
	                            <li class="img-box" style=" float: left;">
	                                <div class="container">
	                                    <span>
	                                    	<img src="${ad.url}"/> 
	                                    </span>
	                                </div>
	                            </li>
                            </c:forEach>
                        </ul>
                    </div>
                     <div class="container hd-container">
                        <ul class="hd">
                        </ul>
                    </div>
                </div>
                <div class="clear">
                </div>
            </div>
        </div>
	</c:if>
</body>
<script type="text/javascript">
	var callIndex;//呼叫弹层面板
	var callAgent;//坐席列表弹层
	var callComm;//新增联系面板
	var callBussincessChance;//商机列表
	//关闭坐席列表
	function closeAgent() {
		layer.close(callAgent);
	}
	function closeComm() {
		layer.close(callComm);
	}
	function closeBussincessChance() {
		layer.close(callBussincessChance);
	}

	function closeScreenAll() {
		layer.confirm("确定关闭弹屏？", {
			btn : [ '确定', '取消' ]
		//按钮
		}, function() {
			layer.closeAll();
		}, function() {
		});
	}

    function hideMsg() {
        $('.side-bar-frameset-box').css('z-index', 10000);
    }

    function showMsg() {
        $('.side-bar-frameset-box').css('z-index', 1);
    }
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/changecareerlayer.js?rnd=<%=Math.random()%>"></script>
</html>