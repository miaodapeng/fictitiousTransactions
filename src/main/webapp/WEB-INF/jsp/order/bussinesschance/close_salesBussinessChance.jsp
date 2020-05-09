
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="关闭销售商机" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/clsoe_salesBussinessChance.js?rnd=<%=Math.random()%>"></script>
	 <div class="formpublic popups">
        <form method="post" id="myform">
        <input type="hidden" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId}">
        <input type="hidden" name="taskId" value="${taskId}">
            <ul>
                <li>
                    <div class="infor_name mt4">
                        <span>*</span>
                        <lable>关闭原因</lable>
                    </div>
                    <div class="f_left">
                        <select name="statusComments" id="statusComments">
                            <option value="">请选择关闭原因</option>
                            <c:if test="${not empty closeList }">
                            	<c:forEach items="${closeList}" var ="gyl">
                            		<option value="${gyl.sysOptionDefinitionId}">${gyl.title}</option>
                               	</c:forEach>
                            </c:if>
                        </select>
                        <div class="font-red " style="display: none">关闭原因不允许为空</div>
                    </div>
                </li>
                <li style="display: none;" id="zfyy">
                    <div class="infor_name mt4">
                        <span>*</span>
                        <lable>商机作废原因</lable>
                    </div>
                    <div class="f_left">
                        <select name="cancelReason" id="cancelReason">
                            <option value="">请选择商机作废原因</option>
                            <c:if test="${not empty zfList }">
                            	<c:forEach items="${zfList}" var ="zf">
                            		<option value="${zf.sysOptionDefinitionId}">${zf.title}</option>
                               	</c:forEach>
                            </c:if>
                        </select>
                        <div class="font-red " style="display: none">作废原因不允许为空</div>
                    </div>
                </li>
                <li style="display: none;" id="qtyy">
                    <div class="infor_name mt4">
                        <span>*</span>
                        <lable>其它原因</lable>
                    </div>
                    <div class="f_left inputfloat">
                       <input type="text" class="input-largest" name="otherReason" id="otherReason">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>申请关闭备注</lable>
                    </div>
                    <div class="f_left inputfloat">
                    	
                       <input type="text" class="input-largest" name="closedComments" id="closedComments">
                    </div>
                </li>
              
            </ul>
            <div class="font-grey9 ml110 mb10">
                友情提示<br/>
1、关闭商机时，必须填写备注；

            </div>
            <div class="add-tijiao tcenter">
             <input type="hidden" name="formToken" value="${formToken}"/>
                <button type="submit" id="sub">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
        </form>
    </div>
</body>

</html>
