<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html;">
    <title>商品属性管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/baseattribute/index.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="erp-wrap">
        <div class="erp-title">
            <div class="erp-title-txt">商品属性管理</div>
        </div>
        <div class="erp-top-option">
            <div class="option-btn-wrap">
                <a class="btn btn-blue btn-small" tabTitle='{"num":"addAttribute","link":"./goods/baseattribute/openAttributePage.do","title":"新增属性", "random": "1"}'>新增属性</a>
            </div>
        </div>
        <div class="erp-block base-form search-wrap J-search-wrap">
            <div class="search-list">
                <div class="search-item">
                    <div class="item-label">
                        属性名称：
                    </div>
                    <div class="item-fields">
                        <div class="search-input-wrap item-input">
                            <input type="text" name="baseAttributeName" maxlength="64" autocomplete="off" class="input-text" placeholder="请输入属性名称" value="${baseAttributeVo.baseAttributeName}">
                        </div>
                    </div>
                </div>
            </div>
            <div class="search-btns">
                <div class="btn btn-small btn-blue-bd J-search">搜索</div>
                <div class="btn btn-small J-reset">重置</div>
            </div>
        </div>
        <div class="erp-block erp-block-list">
            <div class="option-wrap J-fix-wrap">
                <div class="option-fix-wrap cf J-fix-cnt">
                    <div class="option-r">
                        <div class="sort-wrap J-sort-wrap">
                            <div class="sort-item">
                                <span class="item-label">排序：</span>
                                <select name="timeSort" id="" class="J-select">
                                    <optgroup>
                                        <option <c:if test="${baseAttributeVo.timeSort eq 1}">selected</c:if> value="1">引用分类：由多到少</option>
                                        <option <c:if test="${baseAttributeVo.timeSort eq 2}">selected</c:if> value="2">引用分类：由少到多</option>
                                    </optgroup>
                                    <optgroup>
                                        <option <c:if test="${baseAttributeVo.timeSort eq 3}">selected</c:if> value="3">更新时间：由近到远</option>
                                        <option <c:if test="${baseAttributeVo.timeSort eq 4}">selected</c:if> value="4">更新时间：由远到近</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="option-gap"></div>
                        <c:if test="${page.totalPage > 1}">
                            <div class="option-pager">
                                <div class="option-pager-wrap"></div>
                                <tags:pageNewSimple page="${page}" />
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <table class="table table-base table-hover base-form J-table-wrap">
                <colgroup>
                    <col width="16.7%">
                    <col width="">
                    <col width="16%">
                    <col width="16%">
                    <col width="10%">
                </colgroup>
                <tbody>
                    <tr>
                        <th>属性名称</th>
                        <th>属性值列表</th>
                        <th>已引用商品分类</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                    <c:if test="${empty list}">
                        <tr>
                            <td class="no-data" colspan="5">
                                <div><i class="vd-icon icon-caution1"></i></div>
                                没有匹配的数据
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty list}">
                        <c:forEach var="list" items="${list}">
                            <tr>
                                <td>
                                    <a href="javascript:void(0);" tabTitle='{"num":"attrView${list.baseAttributeId}","link":"./goods/baseattribute/getAttributeInfo.do?baseAttributeId=${list.baseAttributeId}","title":"查看属性"}' class="">${list.baseAttributeName}</a>
                                </td>
                                <td>
                                    <c:if test="${empty list.attrValue}">
                                        - -
                                    </c:if>
                                    <div class="line-clamp2">
                                        <span>
                                            <c:if test="${not empty list.attrValue}">
                                                <c:forEach var="attrValue" items="${list.attrValue}" varStatus="status">
                                                    ${attrValue.attrValue}<c:if test="${null ne attrValue.unitName}">${attrValue.unitName}</c:if>；
                                                </c:forEach>
                                            </c:if>
                                        </span>
                                    </div>
                                </td>
                                <td>
                                    <c:if test="${list.categoryNum == 0}">
                                        ${list.categoryNum}
                                    </c:if>
                                    <c:if test="${list.categoryNum > 0}">
                                        <a href="javascript:void(0);" tabTitle='{"num":"categoryView${list.baseAttributeId}","link":"./goods/baseattribute/getAttributeInfo.do?baseAttributeId=${list.baseAttributeId}#cateList","title":"查看分类明细"}' class="">${list.categoryNum}</a>
                                    </c:if>
                                </td>
                                <td>
                                    <fmt:formatDate value="${list.modTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <div class="option-select-wrap J-option-select-wrap">
                                        <div class="option-select-btn" tabTitle='{"num":"editAttr${list.baseAttributeId }","link":"./goods/baseattribute/openAttributePage.do?baseAttributeId=${list.baseAttributeId }","title":"编辑属性"}'>编辑</div>
                                        <div class="option-select-icon J-option-select-icon">
                                            <i class="vd-icon icon-down"></i>
                                        </div>
                                        <c:if test="${list.categoryNum == 0}">
                                            <div class="option-select-list">
                                                <div class="option-select-item J-one-del" data-id="${list.baseAttributeId }">删除</div>
                                            </div>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <c:if test="${page.totalPage > 1}">
                <tags:pageNew page="${page}" />
            </c:if>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/modules/list.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/goods/baseattribute/index.js?rnd=<%=Math.random()%>"></script>
</body>

</html>