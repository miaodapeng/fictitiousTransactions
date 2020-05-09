<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="formpublic">
    <form method="post" action="" id="closeComplement">
        <ul>
            <li>
                <div class="left">
                        <span  style="color:#F00">*</span>
                    <lable for='name' style="font-weight:bold;">关闭原因</lable>
                </div>
                <div style="text-align: center">
                    <textarea name="closedComments" class="input-larger"  id="closedComments" rows="5" placeholder="请输入关闭原因"></textarea>
                </div>
            </li>
        </ul>
        <div class="add-tijiao tcenter">
            <input type="hidden" value="${fileDeliveryId}" name="fileDeliveryId">
            <button type="button" class="bg-light-green" onclick="closes()">提交</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/fileDelivery/complete.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>