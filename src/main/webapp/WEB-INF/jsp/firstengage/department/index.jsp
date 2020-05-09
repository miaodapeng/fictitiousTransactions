<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>科室管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/brand/index.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="erp-wrap">
        <div class="erp-title">
            <div class="erp-title-txt">科室管理</div>
        </div>
        <div class="erp-top-option">
            <div class="option-btn-wrap">
                <a class="btn btn-blue btn-small" tabTitle='{"num":"addDepartment","link":"./firstengage/baseinfo/adddepartment.do","title":"新增科室", "random": "1"}'>新增科室</a>
            </div>
        </div>
        <div class="erp-block base-form search-wrap J-search-wrap">
            <div class="search-list">
                <div class="search-item item-search-select">
                    <div class="item-label">
                        <select name="searchStatus" class="J-select J-search-select">
                            <option value="1" <c:if test="${departmentsHospital.searchStatus eq 1}">selected</c:if> data-place="请输入科室名称/科室说明">关键词</option>
                            <option value="2" <c:if test="${departmentsHospital.searchStatus eq 2}">selected</c:if> data-place="请输入科室名称">科室名称</option>
                            <option value="3" <c:if test="${departmentsHospital.searchStatus eq 3}">selected</c:if> data-place="请输入科室说明">科室说明</option>
                        </select>
                    </div>
                    <div class="item-fields">
                        <div class="search-input-wrap item-input">
                            <input type="text" name="keyWords" autocomplete="off" maxlength="32" class="input-text J-search-word" value="${departmentsHospital.keyWords}">
                            <ul class="search-history-wrap J-search-history" style="display:none;"></ul>
                        </div>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">更新时间：</div>
                    <div class="item-fields J-date-range">
                        <div class="input-date item-input">
                            <input type="text" name="updateStartDate" class="input-text" placeholder="请选择日期" readonly value="${departmentsHospital.updateStartDate}">
                        </div>
                        <div class="search-item-gap">-</div>
                        <div class="input-date item-input">
                            <input type="text" name="updateEndDate" class="input-text" placeholder="请选择日期" readonly value="${departmentsHospital.updateEndDate}">
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
                                <select name="timeSort" class="J-select">
                                    <optgroup>
                                        <option <c:if test="${timeSort eq 1}">selected</c:if> value="1">更新时间：由近到远</option>
                                        <option <c:if test="${timeSort eq 2}">selected</c:if> value="2">更新时间：由远到近</option>
                                    </optgroup>
                                    <optgroup>
                                        <option value="3">对应商品数：由多到少</option>
                                        <option value="4">对应商品数：由少到多</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="option-gap"></div>

                        <c:if test="${not empty hospitals}">
                            <c:if test="${page.totalPage gt 1}">
                                <div class="option-gap"></div>
                                <div class="option-pager">
                                    <div class="option-pager-wrap"></div>
                                    <tags:pageNewSimple page="${page}" />
                                </div>
                            </c:if>
                        </c:if>

                    </div>
                </div>
            </div>
            <table class="table table-base table-hover base-form J-table-wrap">
                <colgroup>
                    <col width="14%">
                    <col width="12%">
                    <col width="22%">
                    <col width="">
                    <col width="12%">
                    <col width="10%">
                </colgroup>
                <tbody>
                    <tr>
                        <th>科室名称</th>
                        <th>对应商品数</th>
                        <th>收费项目</th>
                        <th>科室说明</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                    <c:if test="${empty hospitals}">
                        <tr>
                            <td class="no-data" colspan="10">
                                <div><i class="vd-icon icon-caution1"></i></div>
                                没有匹配的数据
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty hospitals}">
                        <c:forEach items="${hospitals }" var="list" varStatus="status">
                            <tr>
                                <td>
                                    <a href="javascript:void(0)" tabTitle='{"num":"viewDepartment${list.departmentId}","link":"./firstengage/baseinfo/getDepartmentsHospitalInfo.do?departmentsHospitalId=${list.departmentId}","title":"查看科室"}'>${list.departmentName}</a>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${list.goodsNum gt 0}">
                                            <a href="javascript:void(0)" tabTitle='{"num":"viewProdList${list.departmentId}","link":"./goods/vgoods/list.do?departmentName=${list.departmentName}","title":"商品列表"}'>${list.goodsNum}</a>
                                        </c:when>
                                        <c:otherwise>
                                            ${list.goodsNum}
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:if test="${empty list.departmentFeeItemsMappings}">--</c:if>
                                    <c:if test="${not empty list.departmentFeeItemsMappings}">
                                        <div class="line-clamp
                                        2">
                                            <span>
                                                <c:forEach items="${list.departmentFeeItemsMappings }" var="feeList" varStatus="status">
                                                    ${feeList.feePro}<br />
                                                </c:forEach>
                                            </span>
                                        </div>
                                    </c:if>
                                </td>

                                <td>
                                    <c:if test="${null eq list.description}">--</c:if>
                                    <c:if test="${null ne list.description}">
                                        <div class="line-clamp2">
                                            <span title="${list.description}">${list.description}</span>
                                        </div>
                                    </c:if>
                                </td>
                                <td>
                                    ${list.modTimeStr}
                                </td>
                                <td>
                                    <div class="option-select-wrap J-option-select-wrap">
                                        <div class="option-select-btn" tabTitle='{"num":"customer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./firstengage/baseinfo/adddepartment.do?departmentId=${list.departmentId}","title":"编辑科室"}'>编辑</div>
                                        <div class="option-select-icon J-option-select-icon">
                                            <i class="vd-icon icon-down"></i>
                                        </div>
                                        <div class="option-select-list">
                                            <div class="option-select-item J-delete" data-id="${list.departmentId }">删除</div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <c:if test="${not empty hospitals}">
                <c:if test="${page.totalPage gt 1}">
                    <tags:pageNew page="${page}" />
                </c:if>
            </c:if>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/department/index.js?rnd=<%=Math.random()%>"></script>
</body>