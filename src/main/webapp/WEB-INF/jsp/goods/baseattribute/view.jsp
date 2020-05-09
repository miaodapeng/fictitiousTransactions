<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>查看属性</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/baseattribute/view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <div class="detail-title">属性：${attribute.baseAttributeName}</div>
        <div class="detail-option-wrap">
            <div class="option-btns">
                <a href="./openAttributePage.do?baseAttributeId=${attribute.baseAttributeId }"  class="btn btn-blue btn-small">编辑</a>
                <c:if test="${attribute.categoryNum == 0}">
                    <a href="javascript:void(0);" data-id="${attribute.baseAttributeId }" class="btn btn-small J-one-del">删除</a>
                </c:if>
            </div>
        </div>
        <div class="detail-block">
            <div class="block-title">基本信息</div>
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">属性名称：</div>
                    <div class="table-td">${attribute.baseAttributeName}</div>
                </div>
                <div class="table-item">
                    <div class="table-th">单位：</div>
                    <div class="table-td">
                        <c:if test="${attribute.isUnit == 1}">
                            有
                        </c:if>
                        <c:if test="${attribute.isUnit == 0}">
                            - -
                        </c:if>
                    </div>
                </div>
                <div class="table-item item-col">
                    <div class="table-th">排序 属性值：</div>
                    <div class="table-td">
                        <c:if test="${empty attribute.attrValue}">
                            - -
                        </c:if>
                        <c:if test="${not empty attribute.attrValue}">
                            <c:forEach items="${attribute.attrValue}" var="attrValue">
                                <div>${attrValue.attrValue}${attrValue.unitName}</div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="detail-block" id="cateList">
            <div class="block-title">已引用商品分类</div>
            <input type="hidden" class="J-attr-id" value="${attribute.baseAttributeId }">
            <div class="cate-list">
                <div class="cate-th">
                    <div class="cate-item">序号</div>
                    <div class="cate-item">已引用商品分类</div>
                </div>
                <div class="cate-cnt J-attr-list">

                </div>
            </div>
            <div class="pager J-pager"></div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/pager.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/baseattribute/view.js?rnd=<%=Math.random()%>"></script>
</body>

</html>