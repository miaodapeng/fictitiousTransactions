
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑商机备注" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/edit_comments.js?rnd=<%=Math.random()%>"></script>
	 <div class="formpublic popups">
        <form method="post" action="" id="myform">
        <input type="hidden" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId}">
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>备注</lable>
                    </div>
                    <div class="f_left inputfloat">
                       <input type="text" class="input-largest" name="comments" id="comments" value="${bussinessChance.comments}">
                    </div>
                </li>
              
            </ul>
            <div class="font-grey9 ml110 mb10">
                友情提示<br/>
1、关闭商机时，必须填写备注；

            </div>
            <div class="add-tijiao tcenter">
            <input type="hidden" name="beforeParams" value='${beforeParams}'>
                <button type="submit">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
        </form>
    </div>
</body>

</html>
