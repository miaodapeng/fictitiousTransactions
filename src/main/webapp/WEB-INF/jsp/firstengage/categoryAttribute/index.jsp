<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>商品属性管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/brand/index.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="erp-wrap">
        <div class="erp-title">
            <div class="erp-title-txt">商品属性管理</div>
        </div>
        <div class="erp-top-option">
            <div class="option-btn-wrap">
                <a class="btn btn-blue btn-small" tabTitle='{"num":"","link":"./firstengage/baseinfo/adddepartment.do","title":"新增科室"}'>新增属性</a>
            </div>
        </div>
        <div class="erp-block base-form search-wrap J-search-wrap">
            <div class="search-list">
                <div class="search-item item-search-select">
                    <div class="item-label">
                        属性名称：
                    </div>
                    <div class="item-fields">
                        <div class="search-input-wrap item-input">
                            <input type="text" name="keyWords" value="" autocomplete="off" class="input-text" placeholder="请输入属性名称" value="">
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
                                        <option value="1">引用分类：由多到少</option>
                                        <option value="2">引用分类：由少到多</option>
                                    </optgroup>
                                    <optgroup>
                                        <option value="3">更新时间：由近到远</option>
                                        <option value="4">更新时间：由远到近</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="option-gap"></div>
                        <%--<tags:pageNewSimple page="" />--%>
                    </div>
                </div>
            </div>
            <table class="table table-base table-hover base-form J-table-wrap">
                <colgroup>
                    <col width="16%">
                    <col width="">
                    <col width="10%">
                    <col width="16%">
                    <col width="7.5%">
                </colgroup>
                <tbody>
                    <tr>
                        <th>属性名称</th>
                        <th>属性值列表</th>
                        <th>已引用商品分类</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                    <c:if test="${empty null}">
                        <tr>
                            <td class="no-data" colspan="10">
                                没有匹配数据
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty brandList}">
                        <c:forEach items="${brandList }" var="list" varStatus="status">
                            <tr>
                                <td>检验科</td>
                                <td>红色；白色；蓝色；黑色；绿色；灰色；银色；</td>
                                <td>
                                    <a href="">300</a>
                                </td>
                                <td>
                                    2019-03-03 10:00:00
                                </td>
                                <td>
                                    <div class="option-select-wrap J-option-select-wrap">
                                        <div class="option-select-btn" tabTitle='{"num":"customer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./goods/brand/getBrandByKey.do?brandId=${list.brandId}","title":"编辑品牌"}'>编辑</div>
                                        <div class="option-select-icon J-option-select-icon">
                                            <i class="vd-icon icon-down"></i>
                                        </div>
                                        <div class="option-select-list">
                                            <div class="option-select-item J-one-del" data-id="${list.brandId }">删除</div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <%--<tags:pageNew page="" />--%>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/pikaday.2.1.0.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/modules/list.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/brand/index.js?rnd=<%=Math.random()%>"></script>
</body>