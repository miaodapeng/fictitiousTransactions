<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增联系人" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/add_contact.js?rnd=<%=Math.random()%>"></script>
    <div class="formpublic">
        <form method="post" action="" id="myform">
        	
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>姓名</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-middle" name="name" id="name" />
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>联系方式</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-middle" name="telephone" id="telephone"/>
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter">
                <button type="submit" class="dele">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
            <input type="hidden" name="formToken" value="${formToken}"/>
        </form>
    </div>
</body>

</html>
