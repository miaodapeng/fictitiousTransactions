<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/editConfigSet.js?rnd=<%=Math.random()%>"></script>
<div class="main-container">
    <div class="parts">
        <div class="form-list  form-tips8">
            <form method="post" id="submits">
                <ul>
                    <li>
                        <div class="form-tips">
                            <label>人员：</label>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                                <input class="errobor" type="text" id="deptName" name="deptName" readonly="readonly" value="${salesPerformanceDeptUser.username}">
                                <input type="hidden" id="salesPerformanceDeptUserId" name="salesPerformanceDeptUserId" value="${salesPerformanceDeptUser.salesPerformanceDeptUserId}">
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="form-tips">
                            <label>目标：</label>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                                <input class="errobor" type="text" id="goal" name="goal" value="${salesPerformanceDeptUser.goal}">万
                            </div>
                        </div>
                    </li>
                    <div id="warn" class="font-red" style="text-align:center;"></div>
                </ul>

                <div class="add-tijiao tcenter">
                    <button type="button" class="bt-large bt-bg-style bg-light-green" id="sub" onclick="submitDatass();">提交</button>
                    <button class="dele" id="close-layer">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
