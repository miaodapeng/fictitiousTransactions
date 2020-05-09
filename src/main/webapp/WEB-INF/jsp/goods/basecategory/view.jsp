<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>分类详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/basecategory/view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <div class="detail-title">分类详情：${baseCategoryVo.baseCategoryName}</div>
        <div class="detail-option-wrap">
            <div class="option-btns">
                <c:if test="${baseCategoryVo.baseCategoryLevel eq 1}">
                    <a href="./openCategoryPage.do?baseCategoryId=${baseCategoryVo.baseCategoryId}" class="btn btn-blue btn-small">编辑
                    </a>
                </c:if>
                <c:if test="${baseCategoryVo.baseCategoryLevel eq 2}">
                    <a href="./openCategoryPage.do?baseCategoryId=${baseCategoryVo.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.firstLevelCategoryName}" class="btn btn-blue btn-small">编辑</a>
                </c:if>
                <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
                    <a href="./openCategoryPage.do?baseCategoryId=${baseCategoryVo.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.firstLevelCategoryName}&secondLevelCategoryName=${baseCategoryVo.secondLevelCategoryName}" class="btn btn-blue btn-small">编辑
                    </a>
                </c:if>
                <a class="btn btn-small J-del" data-lv="${baseCategoryVo.baseCategoryLevel }" data-id="${baseCategoryVo.baseCategoryId}">删除</a>
            </div>
        </div>
        <div class="detail-block">
            <div class="block-title">基本信息</div>
            <div class="detail-table">
                <c:if test="${baseCategoryVo.baseCategoryLevel eq 2 || baseCategoryVo.baseCategoryLevel eq 3}">
                    <div class="table-item">
                        <div class="table-th">上级分类：</div>
                        <div class="table-td">
                            <c:if test="${baseCategoryVo.baseCategoryLevel eq 2}">
                                ${baseCategoryVo.firstLevelCategoryName}
                            </c:if>
                            <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
                                ${baseCategoryVo.firstLevelCategoryName} > ${baseCategoryVo.secondLevelCategoryName}
                            </c:if>
                        </div>
                    </div>
                </c:if>
                <div class="table-item">
                    <div class="table-th">分类名称：</div>
                    <div class="table-td">${baseCategoryVo.baseCategoryName}</div>
                </div>
                <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
                    <div class="table-item">
                        <div class="table-th">分类类型：</div>
                        <div class="table-td">
                            <c:if test="${baseCategoryVo.baseCategoryType eq 1}">
                                医疗器械
                            </c:if>
                            <c:if test="${baseCategoryVo.baseCategoryType eq 2}">
                                非医疗器械
                            </c:if>
                        </div>
                    </div>
                </c:if>
                <div class="table-item">
                    <div class="table-th">别称：</div>
                    <div class="table-td">${baseCategoryVo.baseCategoryNickname}</div>
                </div>
                <div class="table-item">
                    <div class="table-th">品名举例：</div>
                    <div class="table-td">${baseCategoryVo.baseCategoryExampleProduct}</div>
                </div>
                <div class="table-item">
                    <div class="table-th">描述：</div>
                    <div class="table-td">${baseCategoryVo.baseCategoryDescribe}</div>
                </div>
                <div class='table-item <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">item-col</c:if>'>
                    <div class="table-th">预期用途：</div>
                    <div class="table-td">${baseCategoryVo.baseCategoryIntendedUse}</div>
                </div>
            </div>
        </div>
        <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
            <div class="detail-block">
                <div class="block-title">属性信息</div>
                <div class="cate-list">
                    <div class="cate-th">
                        <div class="cate-item">属性名称</div>
                        <div class="cate-item">属性值</div>
                    </div>
                    <div class="cate-cnt">
                        <c:forEach items="${baseCategoryVo.attributeVoList}" var="attributeVo">
                            <div class="cate-tr">
                                <div class="cate-item">${attributeVo.baseAttributeName}</div>
                                <div class="cate-item">
                                    <c:forEach items="${attributeVo.attrValue}" var="attrValueVo">
                                        ${attrValueVo.attrValue} ${attrValueVo.unitName};
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/basecategory/view.js?rnd=<%=Math.random()%>"></script>
</body>

</html>