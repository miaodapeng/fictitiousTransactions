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
                            <label>团队名称：</label>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                                <input class="errobor" type="text" id="groupName" name="groupName" value="${groupVo.groupName }">
                                <input type="hidden" id="salesPerformanceGroupId" name="salesPerformanceGroupId" value="${groupVo.salesPerformanceGroupId }">
                            </div>
                        </div>
                    </li>
                    <c:forEach var="configVo" items="${configVoList }" varStatus="status">
                        <li>
                            <div class="form-tips">
                                <input type="hidden" id="configVoList[${status.index}].rSalesPerformanceGroupJConfigId" name="configVoList[${status.index}].rSalesPerformanceGroupJConfigId" value="${configVo.rSalesPerformanceGroupJConfigId }">
                                <input type="hidden" id="configVoList[${status.index}].salesPerformanceConfigId" name="configVoList[${status.index}].salesPerformanceConfigId" value="${configVo.salesPerformanceConfigId }">
                                <input type="hidden" id="configVoList[${status.index}].salesPerformanceGroupId" name="configVoList[${status.index}].salesPerformanceGroupId" value="${groupVo.salesPerformanceGroupId }">
                                <label>${configVo.item }：</label>
                            </div>
                            <div class="f_left ">
                                <div class="form-blanks" >
                                    <input class="weight errobor" id="configVoList-${status.index}" name="configVoList[${status.index}].weight" type="text" value="${configVo.weight }"> %
                                    <div class="warning configVoList[${status.index}].weight"></div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>

                    <li>
                        <div class="form-tips">
                            <label>负责人：</label>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                                <select class="career_right" name="ids" id="ids">
                                    <option selected="selected" value="0">请选择</option>
                                    <c:forEach items="${allUser}" var="posit">
                                        <option value="${posit.userId }"
                                                <c:if test="${ userId == posit.userId}">selected="selected"</c:if>>${posit.username }</option>
                                    </c:forEach>
                                </select>
                                <div class="f_left bt-bg-style bt-middle bg-light-blue"
                                     onclick="addOrgUser(this);">添加</div>
                            </div>
                            <label id="idsName" class="idsName"></label>
                        </div>
                    </li>
                    <div id="warn" class="font-red" style="text-align:center;"></div>
                </ul>

                <div class="add-tijiao tcenter">
                    <button type="button" class="bt-large bt-bg-style bg-light-green" id="sub" onclick="submitDatas();">提交</button>
                    <button class="dele" id="close-layer">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
