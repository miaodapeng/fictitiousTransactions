<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增修改记录" scope="application" />
<%@ include file="../common/common.jsp"%>
<div class="formpublic">
    <form method="post" action="" id="myform">

        <ul>
            <li>
                <div class="infor_name">
                    <span>*</span>
                    <lable>修正前</lable>
                </div>
                <div class="f_left">
                    <input type="text" id="controversialContent" name="controversialContent" maxlength="20">
                </div>
                <div id="controversialContentMsg" style="clear:both; margin-left: 111px;"></div>
            </li>
            <li>
                <div class="infor_name">
                    <span>*</span>
                    <lable>修正后</lable>
                </div>
                <div class="f_left">
                    <input type="text" id="modifyContent" name="modifyContent" maxlength="20">
                </div>
                <div id="modifyContentMsg" style="clear:both; margin-left: 111px;"></div>
            </li>
        </ul>
        <div class="add-tijiao tcenter mt2">
            <button type="button" class="bt-bg-style bg-deep-green" onclick="formSubmit();">提交</button>
            <button type="button" class="dele" id="close-layer">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/phoneticTranscription/addRecord.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>

