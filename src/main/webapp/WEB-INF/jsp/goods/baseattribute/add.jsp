<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <c:if test="${null ne attribute.baseAttributeId }">
            编辑属性
        </c:if>
        <c:if test="${null eq attribute.baseAttributeId }">
            新增属性
        </c:if>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/baseattribute/add.css?rnd=<%=Math.random()%>">
</head>

<body>
    <form action="./saveAttribute.do" id="form_submit" class="J-form" method="POST">
        <input type="hidden" name="formToken" value="${formToken}" />
        <input type="hidden" name="baseAttributeId" value="${attribute.baseAttributeId}" />
        <input type="hidden" name="categoryNum" value="${attribute.categoryNum}" />
        <div class="form-wrap">
            <div class="form-container base-form form-span-8">
                <c:if test="${null ne attribute.baseAttributeId }">
                    <div class="form-title">编辑属性</div>
                </c:if>
                <c:if test="${null eq attribute.baseAttributeId }">
                    <div class="form-title">新增属性</div>
                </c:if>

                <!-- 后台报错的区域 -->
                <div class="vd-tip tip-red" <c:if test="${empty error}">style="display: none;"</c:if> >
                    <i class="vd-tip-icon vd-icon icon-error2"></i>
                    <div class="vd-tip-cnt">${error}</div>
                </div>
                <!-- end -->
                <div class="form-block">
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>属性名称：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" name="baseAttributeName" placeholder="请输入属性名称" valid-max="16" class="input-text <c:if test="${attribute.categoryNum > 0}">disabled</c:if>" value="${attribute.baseAttributeName}" <c:if test="${attribute.categoryNum > 0}">readonly</c:if>>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">单位：</div>
                        <div class="form-fields">
                            <div class="input-checkbox">
                                <label class="input-wrap">
                                    <c:if test="${attribute.categoryNum < 1 || attribute.categoryNum == null}">
                                        <input type="checkbox" class="J-set-unit" <c:if test="${attribute.isUnit eq 1 || attribute.isUnit==null}">checked</c:if> name="isUnit" value="1">
                                    </c:if>
                                    <c:if test="${attribute.categoryNum > 0}">
                                        <input type="hidden" name="isUnit" value="${attribute.isUnit }">
                                        <input type="checkbox" class="J-set-unit" <c:if test="${attribute.isUnit eq 1 || attribute.isUnit==null}">checked</c:if> value="1" disabled>
                                    </c:if>
                                    <span class="input-ctnr"></span>设置单位
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>排序 属性值：</div>
                        <div class="form-fields">
                            <div class="attr-wrap J-attr-wrap">
                                <div class="attr-item-wrap th-wrap cf">
                                    <div class="attr-item col-1">排序值</div>
                                    <div class="attr-item col-4">属性值</div>
                                    <div class="attr-item col-2 J-attr-unit">单位</div>
                                </div>
                                <c:if test="${empty attribute.attrValue}">
                                    <div class="attr-item-wrap J-item-wrap cf">
                                        <div class="attr-item col-1 item-center">
                                            <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
                                        </div>
                                        <div class="attr-item col-4">
                                            <input type="text" name="attrValue" autocomplete="off" valid-max="30" class="input-text J-attr-value">
                                            <div class="feedback-block"></div>
                                        </div>
                                        <div class="attr-item J-attr-unit col-2">
                                            <div class="J-attr-unit-wrap"></div>
                                            <input type="hidden" name="unitId" class="J-unit-value">
                                        </div>
                                        <div class="col-1">
                                            <i class="vd-icon icon-recycle J-attr-del"></i>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty attribute.attrValue}">
                                    <c:forEach var="attrValue" items="${attribute.attrValue}">
                                        <div class="attr-item-wrap J-item-wrap cf">
                                            <div class="attr-item col-1 item-center">
                                                <input type="hidden" name="baseAttributeValueId" value="${attrValue.baseAttributeValueId}">
                                                <input type="text" name="sort" class="input-text J-sort-num" maxlength="2" value="${attrValue.sort}">
                                            </div>
                                            <div class="attr-item col-4">
                                                <input type="hidden" name="valueCategoryNum" value="${attrValue.valueCategoryNum}">
                                                <input type="text" name="attrValue" autocomplete="off" class="input-text J-attr-value <c:if test="${attrValue.valueCategoryNum > 0}">disabled</c:if>" valid-max="30" value="${attrValue.attrValue}" <c:if test="${attrValue.valueCategoryNum > 0}">readonly</c:if>>
                                                <div class="feedback-block"></div>
                                            </div>
                                            <div class="attr-item J-attr-unit col-2">
                                                <c:if test="${attrValue.valueCategoryNum > 0}">
                                                    <select disabled>
                                                        <c:forEach var="unit" items="${unitInfo }">
                                                            <option value="${unit.value}" <c:if test="${attrValue.unitId == unit.value}"> selected
                                                                </c:if>>${unit.label}</option>
                                                        </c:forEach>
                                                    </select>
                                                </c:if>
                                                <c:if test="${attrValue.valueCategoryNum < 1 || attrValue.valueCategoryNum == null}">
                                                    <div class="J-attr-unit-wrap"></div>
                                                </c:if>
                                                <input type="hidden" class="J-unit-value" name="unitId" value="${attrValue.unitId == 0 ? '' : attrValue.unitId}">
                                            </div>
                                            <c:if test="${attrValue.valueCategoryNum < 1 || attrValue.valueCategoryNum == null}">
                                                <div class="col-1">
                                                    <i class="vd-icon icon-recycle J-attr-del"></i>
                                                </div>
                                            </c:if>
                                        </div>
                                    </c:forEach>
                                </c:if>
                    </div>
                    <div class="attr-add">
                        <a href="javascript:void(0);" class="attr-add-btn J-attr-add">
                            <i class="vd-icon icon-add"></i>新增属性值
                        </a>
                        <span class="attr-add-tip">（已添加<span class="J-attr-num"></span>条，最多可添加50条）</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-btn">
            <div class="form-fields">
                <button type="submit" class="btn btn-blue btn-large">保存</button>
                <c:if test="${null ne attribute.baseAttributeId }">
                    <a class="btn btn-large" href="./getAttributeInfo.do?baseAttributeId=${attribute.baseAttributeId}">取消</a>
                </c:if>
            </div>
        </div>
        </div>
        </div>
    </form>
    <script type="text/tmpl" class="J-attr-tmpl">
         <div class="attr-item-wrap J-item-wrap cf">
            <div class="attr-item col-1 item-center">
                <input type="text" class="input-text J-sort-num" name="sort" maxlength="2">
            </div>
            <div class="attr-item col-4">
                <input type="text" class="input-text J-attr-value" autocomplete="off" name="attrValue" valid-max="30">
                <div class="feedback-block"></div>
            </div>
            <div class="attr-item J-attr-unit col-2">
                <div class="J-attr-unit-wrap"></div>
                <input type="hidden" name="unitId" class="J-unit-value">
            </div>
            <div class="col-1">
                <i class="vd-icon icon-recycle J-attr-del"></i>
            </div>
        </div>
    </script>
    <select class="J-unit-tmpl" style="display: none;">
        <c:forEach var="unit" items="${unitInfo }">
            <option cz-value="${unit.value}" cz-label="${unit.label}"></option>
        </c:forEach>
    </select>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/baseattribute/add.js?rnd=<%=Math.random()%>"></script>
</body>

</html>