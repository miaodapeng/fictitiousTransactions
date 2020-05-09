<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/editConfigSet.js?rnd=<%=Math.random()%>"></script>
<div class="main-container">
    <div class="parts">
        <div class="form-list  form-tips8">
            <form method="post" id="submit">
                <input type="hidden" name="formToken" value="${formToken}"/>
                <ul>
                    <li>
                        <div class="form-tips">
                            <label>小组名称：</label>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                                <input class="errobor" type="text" id="deptName" name="deptName" value="${deptMember.deptName}">
                                <input class="errobor" type="hidden" id="salesPerformanceDeptId" name="salesPerformanceDeptId" value="${deptMember.salesPerformanceDeptId}">
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="form-tips">
                            <label>负责人：</label>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                                <c:if test="${ not empty deptMember.userList }">
                                    <c:forEach items="${deptMember.userList }" var="orgPosit"
                                               varStatus="status">
                                        <div>
                                                <select class="career_right" name="ids" id="ids">
                                                    <option selected="selected" value="0">请选择</option>
                                                    <c:forEach items="${allUser }" var="posit">
                                                        <option value="${posit.userId }"
                                                                <c:if test="${orgPosit.userId  == posit.userId}">selected="selected"</c:if>>${posit.username }</option>
                                                    </c:forEach>
                                                </select>
                                            <c:choose>
                                                <c:when test="${status.count eq 1}">
                                                    <div class="f_left bt-bg-style bt-middle bg-light-blue"
                                                         onclick="addOrgUser(this);">添加</div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="f_left bt-bg-style bt-middle bg-light-red"
                                                         onclick="delOrgUsers(this);">删除</div>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${ empty deptMember.userList }">
                                    <select class="career_right" name="ids" id="ids">
                                        <option selected="selected" value="0">请选择</option>
                                        <c:forEach items="${allUser}" var="posit">
                                            <option value="${posit.userId }"
                                                    <c:if test="${ userId == posit.userId}">selected="selected"</c:if>>${posit.username }</option>
                                        </c:forEach>
                                    </select>
                                    <div class="f_left bt-bg-style bt-middle bg-light-blue"
                                         onclick="addOrgUser(this);">添加</div>

                                </c:if>

                            </div>
                            <label id="idsName" class="idsName"></label>
                        </div>
                    </li>
                    <div id="warn" class="font-red" style="text-align:center;"></div>
                </ul>

                <div class="add-tijiao tcenter">
                    <button type="button" class="bt-large bt-bg-style bg-light-green" id="sub" onclick="submitData();">提交</button>
                    <button class="dele" id="close-layer">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
