<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>商品分类管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/basecategory/index.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="erp-wrap">
        <div class="erp-title">
            <div class="erp-title-txt">商品分类管理</div>
        </div>
        <div class="erp-top-option">
            <div class="option-btn-wrap">
                <a class="btn btn-blue btn-small" tabTitle='{"num":"addCategory","link":"./category/base/openCategoryPage.do","title":"新增分类", "random": "1"}'>新增分类</a>
            </div>
        </div>
        <div class="erp-block base-form search-wrap J-search-wrap">
            <div class="search-list">
                <div class="search-item">
                    <div class="item-label">
                        分类名称：
                    </div>
                    <div class="item-fields">
                        <div class="search-input-wrap item-input">
                            <input type="text" name="baseCategoryName" maxlength="64" autocomplete="off" class="input-text" placeholder="请输入分类名称" value="${baseCategoryVo.baseCategoryName}">
                        </div>
                    </div>
                </div>

                <div class="search-item">
                    <div class="item-label">
                        分类类型：
                    </div>
                    <div class="item-fields">
                        <select name="baseCategoryType" id="" class="J-select">
                            <option value="" <c:if test="${baseCategoryVo.baseCategoryType == null }">selected</c:if>>全部</option>
                            <option value="1" <c:if test="${baseCategoryVo.baseCategoryType == 1}">selected</c:if>>医疗器械</option>
                            <option value="2" <c:if test="${baseCategoryVo.baseCategoryType == 2}">selected</c:if>>非医疗器械</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="search-btns">
                <div class="btn btn-small btn-blue-bd J-search">搜索</div>
                <div class="btn btn-small J-reset">重置</div>
            </div>
        </div>
        <div class="erp-block erp-block-list">
            <div class="list-table">
                <div class="table-th">
                    <div class="th">分类名称</div>
                    <div class="th">核心商品数</div>
                    <div class="th">临时商品数</div>
                    <div class="th">其他商品数</div>
                    <div class="th">分类类型</div>
                    <div class="th">属性配置</div>
                    <div class="th">操作</div>
                </div>
                
                <c:if test="${empty list}">
                    <div class="table-tr no-data">
                        <div><i class="vd-icon icon-caution1"></i></div>
                        没有匹配的数据
                    </div>
                </c:if>

                <c:if test="${not empty list}">
                    <c:forEach var="baseCategoryVo" items="${list}">
                        <div class="table-tr">
                            <div class="tr-lv1 J-item-wrap hidden">
                                <div class="tr-list">
                                    <div class="tr-item">
                                        <div class="tr-name-txt">
                                            <c:if test="${not empty baseCategoryVo.secondCategoryList}"><i class="vd-icon icon-down J-cate-toggle"></i></c:if>
                                            <a href="javascript:void(0);" tabTitle='{"num":"attrView${baseCategoryVo.baseCategoryId}","link":"./category/base/getCategoryInfo.do?baseCategoryId=${baseCategoryVo.baseCategoryId}","title":"分类详情"}' class="">${baseCategoryVo.baseCategoryName}</a>
                                        </div>
                                    </div>
                                    <div class="tr-item">
                                        <c:if test="${baseCategoryVo.coreProductNum == null || baseCategoryVo.coreProductNum == 0}">
                                            0
                                        </c:if>
                                        <c:if test="${baseCategoryVo.coreProductNum != null && baseCategoryVo.coreProductNum != 0}">
                                            <a href="javascript:void(0);" tabTitle='{"num":"viewCoreProduct${baseCategoryVo.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${baseCategoryVo.baseCategoryId}&spuLevel=1","title":"商品列表"}' class="">
                                                    ${baseCategoryVo.coreProductNum}
                                            </a>
                                        </c:if>
                                    </div>
                                    <div class="tr-item">
                                        <c:if test="${baseCategoryVo.temporaryProductNum == null || baseCategoryVo.temporaryProductNum == 0}">
                                            0
                                        </c:if>
                                        <c:if test="${baseCategoryVo.temporaryProductNum != null && baseCategoryVo.temporaryProductNum != 0}">
                                            <a href="javascript:void(0);" tabTitle='{"num":"viewTemporaryProduct${baseCategoryVo.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${baseCategoryVo.baseCategoryId}&spuLevel=2","title":"商品列表"}' class="">
                                                    ${baseCategoryVo.temporaryProductNum}
                                            </a>
                                        </c:if>
                                    </div>
                                    <div class="tr-item">
                                        <c:if test="${baseCategoryVo.otherProductNum == null || baseCategoryVo.otherProductNum == 0}">
                                            0
                                        </c:if>
                                        <c:if test="${baseCategoryVo.otherProductNum != null && baseCategoryVo.otherProductNum != 0}">
                                            <a href="javascript:void(0);" tabTitle='{"num":"viewTemporaryProduct${baseCategoryVo.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${baseCategoryVo.baseCategoryId}&spuLevel=0","title":"商品列表"}' class="">
                                                    ${baseCategoryVo.otherProductNum}
                                            </a>
                                        </c:if>
                                    </div>
                                    <div class="tr-item">- -</div>
                                    <div class="tr-item">- -</div>
                                    <div class="tr-item">
                                        <div class="option-select-wrap J-option-select-wrap">
                                            <div class="option-select-btn" tabTitle='{"num":"addChild${baseCategoryVo.baseCategoryId}","link":"./category/base/openCategoryPage.do?parentId=${baseCategoryVo.baseCategoryId}&baseCategoryLevel=${baseCategoryVo.baseCategoryLevel + 1}&firstLevelCategoryName=${baseCategoryVo.baseCategoryName}&treenodes=${baseCategoryVo.treenodes}","title":"新增子分类"}'>新增子分类</div>
                                            <div class="option-select-icon J-option-select-icon">
                                                <i class="vd-icon icon-down"></i>
                                            </div>
                                            <div class="option-select-list">
                                                <div class="option-select-item" tabTitle='{"num":"editCategory${baseCategoryVo.baseCategoryId}","link":"./category/base/openCategoryPage.do?baseCategoryId=${baseCategoryVo.baseCategoryId}","title":"编辑分类"}'>编辑</div>
                                                <div class="option-select-item J-del" data-lv="${baseCategoryVo.baseCategoryLevel }" data-id="${baseCategoryVo.baseCategoryId}">删除</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <c:if test="${not empty baseCategoryVo.secondCategoryList}">
                                    <c:forEach items="${baseCategoryVo.secondCategoryList}" var="secondCategory">
                                        <div class="tr-lv2 J-item-wrap hidden">
                                            <div class="tr-list">
                                                <div class="tr-item">
                                                    <div class="tr-name-txt">
                                                        <c:if test="${not empty secondCategory.thirdCategoryList}"><i class="vd-icon icon-down J-cate-toggle"></i></c:if>
                                                        <a href="javascript:void(0);" tabTitle='{"num":"attrView${secondCategory.baseCategoryId}","link":"./category/base/getCategoryInfo.do?baseCategoryId=${secondCategory.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.baseCategoryName}","title":"分类详情"}' class="">${secondCategory.baseCategoryName}</a>
                                                    </div>
                                                </div>
                                                <div class="tr-item">
                                                    <c:if test="${secondCategory.coreProductNum == null || secondCategory.coreProductNum == 0}">
                                                        0
                                                    </c:if>
                                                    <c:if test="${secondCategory.coreProductNum != null && secondCategory.coreProductNum != 0}">
                                                        <a href="javascript:void(0);" tabTitle='{"num":"viewCoreProduct${secondCategory.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${secondCategory.baseCategoryId}&spuLevel=1","title":"商品列表"}' class="">
                                                                ${secondCategory.coreProductNum}
                                                        </a>
                                                    </c:if>
                                                </div>
                                                <div class="tr-item">
                                                    <c:if test="${secondCategory.temporaryProductNum == null || secondCategory.temporaryProductNum == 0}">
                                                        0
                                                    </c:if>
                                                    <c:if test="${secondCategory.temporaryProductNum != null && secondCategory.temporaryProductNum != 0}">
                                                        <a href="javascript:void(0);" tabTitle='{"num":"viewTemporaryProduct${secondCategory.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${secondCategory.baseCategoryId}&spuLevel=2","title":"商品列表"}' class="">
                                                                ${secondCategory.temporaryProductNum}
                                                        </a>
                                                    </c:if>
                                                </div>
                                                <div class="tr-item">
                                                    <c:if test="${secondCategory.otherProductNum == null || secondCategory.otherProductNum == 0}">
                                                        0
                                                    </c:if>
                                                    <c:if test="${secondCategory.otherProductNum != null && secondCategory.otherProductNum != 0}">
                                                        <a href="javascript:void(0);" tabTitle='{"num":"viewTemporaryProduct${secondCategory.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${secondCategory.baseCategoryId}&spuLevel=0","title":"商品列表"}' class="">
                                                                ${secondCategory.otherProductNum}
                                                        </a>
                                                    </c:if>
                                                </div>
                                                <div class="tr-item">- -</div>
                                                <div class="tr-item">- -</div>
                                                <div class="tr-item">
                                                    <div class="option-select-wrap J-option-select-wrap">
                                                        <div class="option-select-btn" tabTitle='{"num":"addChild${secondCategory.baseCategoryId}","link":"./category/base/openCategoryPage.do?parentId=${secondCategory.baseCategoryId }&baseCategoryLevel=${secondCategory.baseCategoryLevel + 1}&firstLevelCategoryName=${baseCategoryVo.baseCategoryName}&secondLevelCategoryName=${secondCategory.baseCategoryName}&treenodes=${secondCategory.treenodes}","title":"新增子分类"}'>新增子分类</div>
                                                        <div class="option-select-icon J-option-select-icon">
                                                            <i class="vd-icon icon-down"></i>
                                                        </div>
                                                        <div class="option-select-list">
                                                            <div class="option-select-item" tabTitle='{"num":"editCategory${secondCategory.baseCategoryId}","link":"./category/base/openCategoryPage.do?baseCategoryId=${secondCategory.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.baseCategoryName}","title":"编辑分类"}'>编辑</div>
                                                            <div class="option-select-item J-del" data-lv="${secondCategory.baseCategoryLevel }" data-id="${secondCategory.baseCategoryId}">删除</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <c:if test="${not empty secondCategory.thirdCategoryList}">
                                                <c:forEach items="${secondCategory.thirdCategoryList}" var="thirdCategory">
                                                    <div class="tr-lv3 J-item-wrap">
                                                        <div class="tr-list">
                                                            <div class="tr-item">
                                                                <div class="tr-name-txt">
                                                                    <a href="javascript:void(0);" tabTitle='{"num":"attrView${thirdCategory.baseCategoryId}","link":"./category/base/getCategoryInfo.do?baseCategoryId=${thirdCategory.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.baseCategoryName}&secondLevelCategoryName=${secondCategory.baseCategoryName}","title":"分类详情"}' class="">${thirdCategory.baseCategoryName}</a>
                                                                </div>
                                                            </div>
                                                            <div class="tr-item">
                                                                <c:if test="${thirdCategory.coreProductNum == null || thirdCategory.coreProductNum == 0}">
                                                                    0
                                                                </c:if>
                                                                <c:if test="${thirdCategory.coreProductNum != null && thirdCategory.coreProductNum != 0}">
                                                                    <a href="javascript:void(0);" tabTitle='{"num":"viewCoreProduct${thirdCategory.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${thirdCategory.baseCategoryId}&spuLevel=1","title":"商品列表"}' class="">
                                                                            ${thirdCategory.coreProductNum}
                                                                    </a>
                                                                </c:if>
                                                            </div>
                                                            <div class="tr-item">
                                                                <c:if test="${thirdCategory.temporaryProductNum == null || thirdCategory.temporaryProductNum == 0}">
                                                                    0
                                                                </c:if>
                                                                <c:if test="${thirdCategory.temporaryProductNum != null && thirdCategory.temporaryProductNum != 0}">
                                                                    <a href="javascript:void(0);" tabTitle='{"num":"viewTemporaryProduct${thirdCategory.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${thirdCategory.baseCategoryId}&spuLevel=2","title":"商品列表"}' class="">
                                                                            ${thirdCategory.temporaryProductNum}
                                                                    </a>
                                                                </c:if>
                                                            </div>
                                                            <div class="tr-item">
                                                                <c:if test="${thirdCategory.otherProductNum == null || thirdCategory.otherProductNum == 0}">
                                                                    0
                                                                </c:if>
                                                                <c:if test="${thirdCategory.otherProductNum != null && thirdCategory.otherProductNum != 0}">
                                                                    <a href="javascript:void(0);" tabTitle='{"num":"viewOtherProductNum${thirdCategory.baseCategoryId}","link":"${pageContext.request.contextPath}/goods/vgoods/list.do?categoryId=${thirdCategory.baseCategoryId}&spuLevel=3","title":"商品列表"}' class="">
                                                                            ${thirdCategory.otherProductNum}
                                                                    </a>
                                                                </c:if>
                                                            </div>
                                                            <div class="tr-item">
                                                                <c:choose>
                                                                    <c:when test="${thirdCategory.baseCategoryType eq 1}">
                                                                        医疗器械
                                                                    </c:when>
                                                                    <c:when test="${thirdCategory.baseCategoryType eq 2}">
                                                                        非医疗器械
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        - -
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <div class="tr-item">
                                                                <c:choose>
                                                                    <c:when test="${not empty thirdCategory.categoryAttrValueMappingVoList}">
                                                                        <div class="line-clamp2">
                                                                            <span>
                                                                                <c:forEach var="categoryAttrValueMappingVo" items="${thirdCategory.categoryAttrValueMappingVoList}" varStatus="status">
                                                                                    ${categoryAttrValueMappingVo.baseAttributeName} ;
                                                                                </c:forEach>
                                                                            </span>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        - -
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <div class="tr-item">
                                                                <div class="option-select-wrap J-option-select-wrap">
                                                                    <div class="option-select-btn" tabTitle='{"num":"editCategory${thirdCategory.baseCategoryId}","link":"./category/base/openCategoryPage.do?baseCategoryId=${thirdCategory.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.baseCategoryName}&secondLevelCategoryName=${secondCategory.baseCategoryName}","title":"编辑分类"}'>编辑</div>
                                                                    <div class="option-select-icon J-option-select-icon">
                                                                        <i class="vd-icon icon-down"></i>
                                                                    </div>
                                                                    <div class="option-select-list">
                                                                        <div class="option-select-item J-del" data-lv="${thirdCategory.baseCategoryLevel }" data-id="${thirdCategory.baseCategoryId}">删除</div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </c:if>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${page.totalPage > 1}">
                        <tags:pageNew page="${page}" />
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/modules/list.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/goods/basecategory/index.js?rnd=<%=Math.random()%>"></script>
</body>