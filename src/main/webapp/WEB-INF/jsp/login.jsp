<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>贝登ERP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css?rnd=<%=Math.random()%>">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src='${pageContext.request.contextPath}/static/js/form.js?rnd=<%=Math.random()%>'></script>
    <script type="text/javascript" src='${pageContext.request.contextPath}/static/js/login.js?rnd=<%=Math.random()%>'></script>
    <script type='text/javascript'>
		if (top.location != self.location)
		{
			top.location=self.location;
		}
	</script>
</head>

<body>
	 <div class="login-con">
        <div class="login-main">
            <div class="login-logo"></div>
            <div class="login-form">
                <form method="post" action="${pageContext.request.contextPath}/dologin.do">
                    <ul>
                        <li>
                            <div class="bor user">
                                <input type="text" placeholder="用户名" name="username" value="${lastLoginName}"/>
                            </div>
                        </li>
                        <li>
                            <div class="bor password">
                                <input type="password" placeholder="密码" name="password" />
                            </div>
                        </li>
                    </ul>
                    <div class="chose-company">
                        <div class="chose-com-cont selectedcolor" onclick="selectCompany(0);">



                            <span>南京贝登医疗股份有限公司</span>


                            <i class="iconxiala"></i>
                        </div>

                    </div>
                    <c:if test="${not empty login_faild_times}">
                    <div class="confirm">
                        <div>
                            <input type="text" placeholder="验证码" name="code"/>
                            <img class="confirmimg" id="img" src="${pageContext.request.contextPath}/code.do"/>
                            <i class="fresh" onclick="changeCode()"></i>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${not empty msg}"><div class="warning">${msg}</div></c:if>
                    <div class="submit">

                        <button type="submit">登录</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="login_copyright">版权所有 南京贝登医疗股份有限公司 &copy;2020 All Right Reserved</div>
    </div>

</body>
<script type="text/javascript">
function selectCompany(companyId){
	$("input[name='companyId']").val(companyId);
}

function genTimestamp() {    
    var time = new Date();    
    return time.getTime();    
}    
function changeCode() {    
    $("#img").attr("src", "./code.do?t=" + genTimestamp());    
}    
</script>
</html>
