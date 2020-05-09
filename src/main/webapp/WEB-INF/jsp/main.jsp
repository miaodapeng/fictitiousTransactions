<!DOCTYPE>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>
    <meta charset="UTF-8">
    <title>可关闭的tab，子页面用iframe实现</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/content.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/frame.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/closable-tab.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src='${pageContext.request.contextPath}/static/js/mainHeight.js?rnd=<%=Math.random()%>'></script>
</head>

<body>
    <div class="closable-tab container closable-tab-page">
        <div id="hidden-side-bar" class="hidden-side-bar active">&#9668</div>
        <div id="hidden-top-bar" class="hidden-top-bar active">&#9660</div>
        <div class="same t_left"><i class="iconleft"></i></div>
        <div class='same t_right'><i class="iconright"></i></div>
        <div class="row">
            <!-- 此处是相关代码 -->
            <div class="title">
                <ul class="nav nav-tabs" role="tablist">
                </ul>
            </div>
            <div class="tab-content" style="width:100%;">
            </div>
            <!-- 相关代码结束 -->
        </div>
    </div>
</body>

</html>