<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>商品品牌管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/brand/index.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="erp-wrap">
        <div class="erp-title">
            <div class="erp-title-txt">商品品牌管理</div>
        </div>
        <div class="erp-top-option">
            <div class="option-btn-wrap">
                <a class="btn btn-blue btn-small" tabTitle='{"num":"customer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./firstengage/brand/addBrand.do","title":"新增品牌", "random": "1"}'>新增品牌</a>
            </div>
        </div>
        <div class="erp-block base-form search-wrap J-search-wrap">
            <div class="search-list">
                <div class="search-item item-search-select">
                    <div class="item-label">
                        <select name="searchStatus" class="J-select J-search-select">
                            <option <c:if test="${brand.searchStatus eq 1}">selected="selected"</c:if> value="1" data-place="请输入品牌名称/品牌ID">关键词</option>
                            <option <c:if test="${brand.searchStatus eq 2}">selected="selected"</c:if> value="2" data-place="请输入品牌名称">品牌名称</option>
                            <option <c:if test="${brand.searchStatus eq 3}">selected="selected"</c:if> value="3" data-place="请输入品牌ID">品牌ID</option>
                        </select>
                    </div>
                    <div class="item-fields">
                        <div class="search-input-wrap item-input">
                            <input type="text" name="keyWords" value="${brand.keyWords}" maxlength="64" autocomplete="off" class="input-text J-search-word">
                            <ul class="search-history-wrap J-search-history" style="display:none;"></ul>
                        </div>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">商品品牌：</div>
                    <div class="item-fields">
                        <select name="brandNature" class="J-select">
                            <option value="0" <c:if test="${brand.brandNature eq 0}">selected="selected"</c:if>>全部品牌</option>
                            <option value="1" <c:if test="${brand.brandNature eq 1}">selected="selected"</c:if>>国产品牌</option>
                            <option value="2" <c:if test="${brand.brandNature eq 2}">selected="selected"</c:if>>进口品牌</option>
                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">更新时间：</div>
                    <div class="item-fields J-date-range">
                        <div class="input-date item-input">
                            <input type="text" name="effectStartDate" value="${brand.effectStartDate}" class="input-text" placeholder="请选择日期" readonly value="">
                        </div>
                        <div class="search-item-gap">-</div>
                        <div class="input-date item-input">
                            <input type="text" name="effectEndDate" value="${brand.effectEndDate}" class="input-text" placeholder="请选择日期" readonly value="">
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
                    <div class="option-select-wrap J-option-select-wrap">
                        <div class="option-select-btn J-export-all">全部导出</div>
                        <div class="option-select-icon J-option-select-icon">
                            <i class="vd-icon icon-down"></i>
                        </div>
                        <div class="option-select-list">
                            <div class="option-select-item J-export-select">导出选中项</div>
                        </div>
                    </div>
                    <div class="option-r">
                        <div class="sort-wrap J-sort-wrap">
                            <div class="sort-item">
                                <span class="item-label">排序：</span>
                                <select name="timeSort" id="" class="J-select">
                                    <option value="1" <c:if test="${brand.timeSort eq 1 }">selected</c:if>
                                        >更新时间：由近到远</option>
                                    <option value="2" <c:if test="${brand.timeSort eq 2 }">selected</c:if>
                                        >更新时间：由远到近</option>
                                </select>
                            </div>
                        </div>
                        <c:if test="${not empty brandList}">
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
                    <col width="5%">
                    <col width="14.5%">
                    <col width="14.5%">
                    <col width="">
                    <col width="10%">
                    <col width="7.5%">
                    <col width="8%">
                    <col width="7.5%">
                    <col width="10%">
                </colgroup>
                <tbody>
                    <tr>
                        <th>
                            <c:if test="${not empty brandList }">
                                <div class="input-checkbox">
                                    <label class="input-wrap">
                                        <input type="checkbox" class="J-select-all">
                                        <span class="input-ctnr"></span>
                                    </label>
                                </div>
                            </c:if>
                        </th>
                        <th>品牌ID</th>
                        <th>品牌名称</th>
                        <th>品牌Logo</th>
                        <th>商品品牌</th>
                        <th>品牌网址</th>
                        <th>商品数量</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                    <c:if test="${empty brandList}">
                        <tr>
                            <td class="no-data" colspan="10">
                                <div><i class="vd-icon icon-caution1"></i></div>
                                没有匹配的数据
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty brandList}">
                        <c:forEach items="${brandList }" var="list" varStatus="status">
                            <tr>
                                <td>
                                    <div class="input-checkbox">
                                        <label class="input-wrap">
                                            <input type="checkbox" class="J-select-item" value="${list.brandId }">
                                            <span class="input-ctnr"></span>
                                        </label>
                                    </div>
                                </td>
                                <td>${list.brandId}</td>
                                <td>
                                    <div class="line-clamp2">
                                        <a href="javascript:void(0);" tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
                                    												"link":"./firstengage/brand/viewBrandDetail.do?brandId=${list.brandId}","title":"查看品牌"}'>
                                            <c:if test="${list.brandNature eq 1}">${list.brandName}</c:if>
                                            <c:if test="${list.brandNature eq 2}">${list.brandNameEn}</c:if>
                                        </a>
                                    </div>
                                </td>
                                <td>
                                    <img class="logo-img" src="http://${list.logoDomain}${list.logoUri}" alt="">
                                </td>
                                <td>
                                    <c:if test="${list.brandNature eq 1}">国产品牌</c:if>
                                    <c:if test="${list.brandNature eq 2}">进口品牌</c:if>
                                </td>
                                <td>
                                    <div class="line-clamp2">
                                        <span title="${list.brandWebsite}"><a target="_blank" href="${list.brandWebsite}">${list.brandWebsite}</a></span>
                                    </div>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${list.goodsNum gt 0}">
                                            <a href="javascript:void(0)" tabTitle='{"num":"viewBrand${list.brandId}","link":"./goods/vgoods/list.do?brandName=${list.brandName}","title":"商品列表"}'>${list.goodsNum}</a>
                                        </c:when>
                                        <c:otherwise>
                                            ${list.goodsNum}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${null eq list.modTimeStr}">- -</c:if>
                                    <c:if test="${null ne list.modTimeStr}">${list.modTimeStr}</c:if>
                                </td>
                                <td>
                                    <div class="option-select-wrap J-option-select-wrap">
                                        <div class="option-select-btn" tabTitle='{"num":"customer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./firstengage/brand/addBrand.do?brandId=${list.brandId}","title":"编辑品牌"}'>编辑</div>
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
            <c:if test="${not empty brandList}">
                <c:if test="${page.totalPage gt 1}">
                    <tags:pageNew page="${page}" />
                </c:if>
            </c:if>
        </div>
    </div>
    <script type="text/tmpl" class="J-del-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i>确认删除该品牌？
            </div>
            <form class="J-del-form">
                <div class="del-cnt base-form">
                    <textarea name="content" id="" cols="30" rows="10" class="input-textarea" placeholder="必填：请填写删除理由，最少10个字，最多300个字"></textarea>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/modules/list.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/brand/index.js?rnd=<%=Math.random()%>"></script>
</body>

</html>