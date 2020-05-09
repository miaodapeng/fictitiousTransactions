<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>


<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>商品首营列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/first/index.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="erp-wrap">
        <div class="erp-title">
            <div class="erp-title-txt">商品首营管理</div>
        </div>
        <c:if test="${null ne overDateCount and overDateCount gt 0}">
            <div class="vd-tip">
                <i class="vd-tip-icon vd-icon icon-info2"></i>
                <div class="vd-tip-cnt">当前有${overDateCount}个注册证即将过期，
                    <c:if test='${tag eq 1}'><a href="./getFirstEngageInfo.do?tag=1&dealStatus=1">立即处理</a></c:if>
                    <c:if test='${tag eq 2}'><a href="./getFirstEngageInfo.do?tag=2&status=1&dealStatus=1">立即处理</a></c:if>
                    <c:if test='${tag eq 3}'><a href="./getFirstEngageInfo.do?tag=3&status=3&dealStatus=1">立即处理</a></c:if>
                    <c:if test='${tag eq 4}'><a href="./getFirstEngageInfo.do?tag=4&status=2&dealStatus=1">立即处理</a></c:if>
                </div>
            </div>
        </c:if>

        <div class="tab-nav J-list-tab" data-name="tag">
            <a class="tab-item <c:if test='${tag eq 1}'>current</c:if>" href="./getFirstEngageInfo.do?tag=1" data-value="1">全部(${total})</a>
            <a class="tab-item <c:if test='${tag eq 2}'>current</c:if>" href="./getFirstEngageInfo.do?tag=2&status=1" data-value="2&status=1">待审核(${one})</a>
            <a class="tab-item <c:if test='${tag eq 3}'>current</c:if>" href="./getFirstEngageInfo.do?tag=3&status=3" data-value="3&status=3">审核通过(${two})</a>
            <a class="tab-item <c:if test='${tag eq 4}'>current</c:if>" href="./getFirstEngageInfo.do?tag=4&status=2" data-value="4&status=2">审核不通过(${three})</a>
        </div>
        <div class="erp-top-option">
            <div class="option-btn-wrap">
                <a class="btn btn-blue btn-small" tabTitle='{"num":"addProduct","link":"./firstengage/baseinfo/add.do","title":"新增商品", "random": "1"}'>新增首营信息</a>
            </div>
        </div>
        <div class="erp-block base-form search-wrap J-search-wrap">
            <div class="search-list">
                <div class="search-item item-search-select">
                    <div class="item-label">
                        <select name="searchStatus" class="J-select J-search-select">
                            <option value="1" <c:if test="${searchStatus eq 1}">selected</c:if> data-place="请输入注册证号/备案号/生产企业">关键词</option>
                            <option value="2" <c:if test="${searchStatus eq 2}">selected</c:if> data-place="请输入注册证号/备案号">注册证号</option>
                            <option value="3" <c:if test="${searchStatus eq 3}">selected</c:if> data-place="请输入生产企业">生产企业</option>
                        </select>
                    </div>
                    <div class="item-fields">
                        <div class="search-input-wrap item-input">
                            <input type="text" name="keyWords" autocomplete="off" maxlength="64" class="input-text J-search-word" value="${firstEngage.keyWords }">
                            <ul class="search-history-wrap J-search-history" style="display:none;"></ul>
                        </div>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">注册证有效期：</div>
                    <div class="item-fields J-date-range">
                        <div class="input-date item-input">
                            <input type="text" name="effectStartDate" class="input-text" placeholder="请选择日期" readonly value="${firstEngage.effectStartDate }">
                        </div>
                        <div class="search-item-gap">-</div>
                        <div class="input-date item-input">
                            <input type="text" name="effectEndDate" class="input-text" placeholder="请选择日期" readonly value="${firstEngage.effectEndDate }">
                        </div>
                    </div>
                </div>

                <c:if test="${tag ne 2 and tag ne 4}">
                    <div class="search-item">
                        <div class="item-label">临效期状态：</div>
                        <div class="item-fields">
                            <select name="dealStatus" class="J-select">
                                <option value="-1">全部</option>
                                <option value="1" <c:if test="${firstEngage.dealStatus eq 1 }">selected</c:if> >待处理</option>
                                <option value="2" <c:if test="${firstEngage.dealStatus eq 2 }">selected</c:if> >已处理</option>
                            </select>
                        </div>
                    </div>
                    <div class="search-item">
                        <div class="item-label">注册证过期状态：</div>
                        <div class="item-fields">
                            <select name="isOverDate" class="J-select">
                                <option <c:if test="${firstEngage.isOverDate eq -1 }">selected</c:if> value="-1">全部</option>
                                <option <c:if test="${firstEngage.isOverDate eq 1 }">selected</c:if> value="1">半年即将过期（3~6个月）</option>
                                <option <c:if test="${firstEngage.isOverDate eq 2 }">selected</c:if> value="2">3个月即将过期（1~3个月）</option>
                                <option <c:if test="${firstEngage.isOverDate eq 3 }">selected</c:if> value="3">1个月即将过期（0~1个月）</option>
                                <option <c:if test="${firstEngage.isOverDate eq 4 }">selected</c:if> value="4">已经过期</option>
                            </select>
                        </div>
                    </div>
                </c:if>

            </div>
            <div class="search-btns">
                <div class="btn btn-small btn-blue-bd J-search">搜索</div>
                <div class="btn btn-small J-reset">重置</div>
            </div>
        </div>
        <div class="erp-block erp-block-list">
            <div class="option-wrap J-fix-wrap">
                <div class="option-fix-wrap cf J-fix-cnt">
                    <c:if test="${(not empty firstEngageList) && (tag ne 2) }">
                        <button class="btn btn-small J-multi-del">批量删除</button>
                    </c:if>
                    <div class="option-r">
                        <div class="sort-wrap J-sort-wrap">
                            <div class="sort-item">
                                <span class="item-label">排序：</span>
                                <select name="timeSort" id="" class="J-select">
                                    <optgroup>
                                        <option value="1" <c:if test="${firstEngage.timeSort eq 1 }">selected</c:if>
                                            >更新时间：由近到远</option>
                                        <option value="2" <c:if test="${firstEngage.timeSort eq 2 }">selected</c:if>
                                            >更新时间：由远到近</option>
                                    </optgroup>
                                    <optgroup>
                                        <option value="3" <c:if test="${firstEngage.timeSort eq 3 }">selected</c:if>
                                            >注册证有效期：由近到远（后天-明天-今天-昨天）</option>
                                        <option value="4" <c:if test="${firstEngage.timeSort eq 4 }">selected</c:if>
                                            >注册证有效期：由远到近（昨天-今天-明天-后天）</option>
                                    </optgroup>
                                    <optgroup>
                                        <option value="5" <c:if test="${firstEngage.timeSort eq 5 }">selected</c:if>
                                            >产品有效期：由近到远</option>
                                        <option value="6" <c:if test="${firstEngage.timeSort eq 6 }">selected</c:if>
                                            >产品有效期：由远到近</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>

                        <c:if test="${not empty firstEngageList}">
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
                    <c:if test="${tag ne 2 }">
                        <col width="5%">
                    </c:if>
                    <col width="14.5%">
                    <col width="14.5%">
                    <col width="">
                    <col width="10%">
                    <col width="7.5%">
                    <col width="8%">
                    <c:if test='${tag eq 1}'>
                        <col width="7.5%">
                    </c:if>
                    <c:if test="${tag ne 2}">
                        <col width="10%">
                    </c:if>
                </colgroup>
                <tbody>
                    <tr>
                        <c:if test="${tag ne 2 }">
                            <th>
                                <c:if test="${not empty firstEngageList}">
                                    <div class="input-checkbox">
                                        <label class="input-wrap">
                                            <input type="checkbox" class="J-select-all">
                                            <span class="input-ctnr"></span>
                                        </label>
                                    </div>
                                </c:if>
                            </th>
                        </c:if>
                        <th>注册证号/备案凭证号</th>
                        <th>生产企业</th>
                        <th>国标分类</th>
                        <th>注册证至</th>
                        <th>产品有效期</th>
                        <th>更新时间</th>
                        <c:if test='${tag eq 1}'>
                            <th>状态</th>
                        </c:if>
                        <c:if test="${tag ne 2}">
                            <th>操作</th>
                        </c:if>
                    </tr>
                    <c:if test="${empty firstEngageList}">
                        <tr>
                            <td class="no-data" colspan='
                            <c:if test="${tag eq 1 }">
                                9
                            </c:if>
                            <c:if test="${tag eq 2 }">
                                6
                            </c:if>
                            <c:if test="${tag eq 3 || tag eq 4 }">
                                8
                            </c:if>
                            '>
                                <div><i class="vd-icon icon-caution1"></i></div>
                                没有匹配的数据
                            </td>
                        </tr>
                    </c:if>

                    <c:if test="${not empty firstEngageList}">
                        <c:forEach var="firstEngage" items="${firstEngageList }">
                            <tr>
                                <c:if test="${tag ne 2}">
                                    <td>
                                        <div class="input-checkbox">
                                            <label class="input-wrap">
                                                <input type="checkbox" class="J-select-item" value="${firstEngage.firstEngageId }" <c:if test="${firstEngage.status eq 1}">disabled</c:if> >
                                                <span class="input-ctnr"></span>
                                            </label>
                                        </div>
                                    </td>
                                </c:if>
                                <td>
                                    <a href="javascript:void(0);" tabTitle='{"num":"viewProduct${firstEngage.firstEngageId }","link":"./firstengage/baseinfo/getFirstSearchDetail.do?firstEngageId=${firstEngage.firstEngageId }","title":"查看商品"}' class="">${firstEngage.registrationNumber }</a>
                                </td>
                                <td>${firstEngage.productCompanyChineseName }</td>
                                <td>
                                    <div class="stand-wrap">
                                        <c:choose>
                                            <c:when test="${null ne firstEngage.newStandardCategoryName and '' ne firstEngage.newStandardCategoryName}">
                                                    <div class="line-clamp2">
                                                        <span title="${firstEngage.newStandardCategoryName}">${firstEngage.newStandardCategoryName}</span>
                                                    </div>
                                            </c:when>
                                            <c:when test="${null ne firstEngage.oldStandardCategoryName and '' ne firstEngage.oldStandardCategoryName}">
                                                    <div class="line-clamp2">
                                                        <span title="${firstEngage.oldStandardCategoryName}">${firstEngage.oldStandardCategoryName}${firstEngage.dealStatus}</span>
                                                    </div>
                                            </c:when>
                                            <c:otherwise>
                                                - -
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${firstEngage.dealStatus ne 0 }">
                                            <div class="tip-wrap">
                                                <i class="vd-icon icon-info2 <c:if test="${firstEngage.dealStatus eq 2 }">icon-grey</c:if>">
                                                    <div class="tip arrow-left">
                                                        <div class="tip-con">
                                                            <c:if test="${firstEngage.dealStatus eq 1 }">临效期待处理</c:if>
                                                            <c:if test="${firstEngage.dealStatus eq 2 }">临效期已处理</c:if>
                                                        </div>
                                                        <span class="arrow arrow-out">
                                                            <span class="arrow arrow-in"></span>
                                                        </span>
                                                    </div>
                                                </i>
                                            </div>
                                        </c:if>
                                    </div>
                                </td>
                                <td>${firstEngage.registrationEffectiveDateStr }</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${firstEngage.effectiveDays gt 0}">
                                            ${firstEngage.effectiveDays}
                                            <c:if test="${firstEngage.effectiveDayUnit eq 1}">天</c:if>
                                            <c:if test="${firstEngage.effectiveDayUnit eq 2}">月</c:if>
                                            <c:if test="${firstEngage.effectiveDayUnit eq 3}">年</c:if>
                                        </c:when>
                                        <c:otherwise>
                                            - -
                                        </c:otherwise>
                                    </c:choose>

                                </td>
                                <td>${firstEngage.modTimeStr }</td>
                                <c:if test='${tag eq 1}'>
                                    <td>
                                        <c:if test="${firstEngage.status eq 1 }">待审核</c:if>
                                        <c:if test="${firstEngage.status eq 3 }">审核通过</c:if>
                                        <c:if test="${firstEngage.status eq 2 }">审核不通过</c:if>
                                    </td>
                                </c:if>
                                <c:if test="${tag ne 2}">
                                    <td>
                                        <c:if test="${firstEngage.status ne 1}">
                                            <div class="option-select-wrap J-option-select-wrap" data-id="${firstEngage.firstEngageId }">
                                                <div class="option-select-btn" tabTitle='{"num":"editProduct${firstEngage.firstEngageId }","link":"./firstengage/baseinfo/add.do?firstEngageId=${firstEngage.firstEngageId }","title":"编辑商品"}'>编辑</div>
                                                <div class="option-select-icon J-option-select-icon">
                                                    <i class="vd-icon icon-down"></i>
                                                </div>
                                                <div class="option-select-list">
                                                    <div class="option-select-item J-one-del">删除</div>
                                                    <c:if test="${firstEngage.dealStatus eq 1 }">
                                                        <div class="option-select-item J-overday" data-id="${firstEngage.registrationNumberId }">过期处理</div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:if>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <c:if test="${not empty firstEngageList}">
                <c:if test="${page.totalPage gt 1}">
                    <tags:pageNew page="${page}" />
                </c:if>
            </c:if>
        </div>
    </div>
    <script type="text/tmpl" class="J-del-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i>确认删除该首营品种资质？
            </div>
            <form class="J-del-form">
                <div class="del-cnt base-form">
                    <textarea name="content" id="" cols="30" rows="10" class="input-textarea" placeholder="必填：请填写删除原因，最少10个字，最多300个字"></textarea>
                </div>
            </form>
        </div>
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/pikaday.2.1.0.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/modules/list.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/first/index.js?rnd=<%=Math.random()%>"></script>
</body>

</html>