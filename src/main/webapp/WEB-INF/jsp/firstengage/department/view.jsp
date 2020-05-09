<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看科室</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/first/view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <div class="detail-title">查看科室：${departmentsHospital.departmentName}</div>
        <div class="detail-option-wrap">
            <div class="option-btns">
                <a href="./adddepartment.do?departmentId=${departmentsHospital.departmentId }" class="btn btn-blue btn-small">编辑</a>
                <a class="btn btn-small J-delete" data-id="${departmentsHospital.departmentId }">删除</a>
            </div>
        </div>
        <div class="detail-block">
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">科室名称：</div>
                    <div class="table-td">
                        ${departmentsHospital.departmentName}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">收费项目：</div>
                    <c:if test="${not empty departmentsHospital.departmentFeeItemsMappings}">
                        <div class="table-td">
                            <c:forEach var="fee" items="${departmentsHospital.departmentFeeItemsMappings}">
                                ${fee.feePro} </br>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
                <div class="table-item item-col">
                    <div class="table-th">科室说明：</div>
                    <div class="table-td">
                        ${departmentsHospital.description}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/department/view.js?rnd=<%=Math.random()%>"></script>
</body>