<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <c:if test="${null ne baseCategoryVo.baseCategoryId }">
            编辑分类
        </c:if>
        <c:if test="${null eq baseCategoryVo.baseCategoryId }">
            新增分类
        </c:if>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/basecategory/add.css?rnd=<%=Math.random()%>">
</head>

<body>
    <form action="./saveCategory.do" id="form_submit" class="J-form" method="POST">
        <input type="hidden" name="formToken" value="${formToken}" />
        <input type="hidden" name="parentId" value="${baseCategoryVo.parentId}" />
        <input type="hidden" name="treenodes" value="${baseCategoryVo.treenodes}" />
        <input type="hidden" name="firstLevelCategoryName" value="${baseCategoryVo.firstLevelCategoryName}" />
        <input type="hidden" name="secondLevelCategoryName" value="${baseCategoryVo.secondLevelCategoryName}" />
        <input type="hidden" name="baseCategoryId" value="${baseCategoryVo.baseCategoryId}">
        <input type="hidden" class="J-continue-add" name="isEditGoods" value="" />
        <c:if test="${baseCategoryVo.baseCategoryLevel eq 1 || baseCategoryVo.baseCategoryLevel eq null}">
            <input type="hidden" name="baseCategoryLevel" value="1" />
        </c:if>
        <c:if test="${baseCategoryVo.baseCategoryLevel eq 2}">
            <input type="hidden" name="baseCategoryLevel" value="2" />
        </c:if>
        <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
            <input type="hidden" name="baseCategoryLevel" value="3" />
        </c:if>

        <div class="form-wrap">
            <div class="form-container base-form form-span-8">
                <c:if test="${null ne baseCategoryVo.baseCategoryId }">
                    <div class="form-title">编辑分类</div>
                </c:if>
                <c:if test="${null eq baseCategoryVo.baseCategoryId }">
                    <div class="form-title">新增分类</div>
                </c:if>
                <!-- 后台报错的区域 -->
                <c:if test="${not empty error}">
                    <div class="vd-tip tip-red">
                        <i class="vd-tip-icon vd-icon icon-error2"></i>
                        <div class="vd-tip-cnt">${error}</div>
                    </div>
                </c:if>
                <!-- end -->
                <div class="form-block">
                    <div class="form-block-title">基本信息</div>
                    <%--<c:if test="${null ne parentName}">
                        <div class="form-item">
                            <div class="form-label">父级分类：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <span>${parentName}</span>
                                </div>
                            </div>
                        </div>
                    </c:if>--%>
                    <c:if test="${baseCategoryVo.baseCategoryLevel eq 2 || baseCategoryVo.baseCategoryLevel eq 3}">
                        <div class="form-item">
                            <div class="form-label">上级分类：</div>
                            <div class="form-fields">
                                <div class="fields-label">
                                    <c:if test="${baseCategoryVo.baseCategoryLevel eq 2}">
                                            ${baseCategoryVo.firstLevelCategoryName}
                                    </c:if>
                                    <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
                                        ${baseCategoryVo.firstLevelCategoryName} - ${baseCategoryVo.secondLevelCategoryName}
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>分类名称：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" name="baseCategoryName" valid-max="64" class="input-text" value="${baseCategoryVo.baseCategoryName}">
                            </div>
                        </div>
                    </div>
                    <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>分类类型：</div>
                            <div class="form-fields">
                                <div class="input-radio">
                                    <label class="input-wrap">
                                        <input type="radio" name="baseCategoryType" value="1" <c:if test="${baseCategoryVo.baseCategoryType eq 1}">checked</c:if>>
                                        <span class="input-ctnr"></span>医疗器械
                                    </label>
                                    <label class="input-wrap">
                                        <input type="radio" name="baseCategoryType" value="2" <c:if test="${baseCategoryVo.baseCategoryType eq 2}">checked</c:if>>
                                        <span class="input-ctnr"></span>非医疗器械
                                    </label>
                                </div>
                                <div class="feedback-block" wrapfor="baseCategoryType"></div>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-item">
                        <div class="form-label">别称：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" name="baseCategoryNickname" valid-max="200" class="input-text" value="${baseCategoryVo.baseCategoryNickname}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">品名举例：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <textarea class="input-textarea" name="baseCategoryExampleProduct" valid-max="200" cols="30" rows="10">${baseCategoryVo.baseCategoryExampleProduct}</textarea>
                            </div>
                            <div class="form-fields-tip">- 例：血型分析仪、全自动血型分析仪、全自动配血及血型分析仪、血型分析用凝胶卡判读仪、血库系统。</div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">描述：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <textarea class="input-textarea" name="baseCategoryDescribe" valid-max="500" cols="30" rows="10">${baseCategoryVo.baseCategoryDescribe}</textarea>
                            </div>
                            <div class="form-fields-tip">- 例：血型分析仪  通常由工作平台、标本试管架装置、试剂混匀装置、加样系统、孵育器、离心机、判读装置等组成。原理一般为微孔板红细胞凝集法、凝胶卡式检测法等。</div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">预期用途：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <textarea class="input-textarea" name="baseCategoryIntendedUse" valid-max="500" cols="30" rows="10">${baseCategoryVo.baseCategoryIntendedUse}</textarea>
                            </div>
                            <div class="form-fields-tip">- 例：血型分析仪  与适配试剂配合使用，用于ABO/Rh血型检测、交叉配血检测及不规则抗体检测等。</div>
                        </div>
                    </div>
                </div>
                <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
                    <div class="form-block">
                        <div class="form-block-title">属性信息</div>
                        <div class="form-item">
                            <div class="form-label">属性名 属性值：</div>
                            <div class="form-fields">
                                <div class="attr-list">
                                    <div class="attr-title cf">
                                        <div class="title-item col-4">属性名</div>
                                        <div class="title-item col-4">属性值</div>
                                    </div>
                                    <div class="attr-tr-wrap J-attr-wrap">
                                        <c:if test="${empty baseCategoryVo.attributeVoList}">
                                            <div class="attr-tr cf J-attr-item">
                                                <div class="attr-item col-4">
                                                    <div class="J-attr-name-wrap"></div>
                                                    <input type="hidden" value="" class="J-attr-name" name="baseAttributeId">
                                                </div>
                                                <div class="attr-item col-4">
                                                    <div class="J-attr-value-wrap"></div>
                                                    <input type="hidden" value="" class="J-attr-value" name="baseAttributeValueIds">
                                                </div>
                                                <div class="col-1 attr-del">
                                                    <i class="vd-icon icon-recycle J-attr-del"></i>
                                                </div>
                                            </div>
                                        </c:if>


                                        <c:if test="${not empty baseCategoryVo.attributeVoList}">
                                            <c:forEach items="${baseCategoryVo.attributeVoList}" var="attribute">
                                                <div class="attr-tr cf J-attr-item">
                                                    <div class="attr-item col-4">
                                                        <div class="J-attr-name-wrap"></div>
                                                        <input type="hidden" class="J-attr-name" name="baseAttributeId" value="${attribute.baseAttributeId}">
                                                    </div>
                                                    <div class="attr-item col-4">
                                                        <div class="J-attr-value-wrap"></div>
                                                        <input type="hidden" value="${attribute.baseAttributeValueIds}" class="J-attr-value" name="baseAttributeValueIds">
                                                    </div>
                                                    <div class="col-1 attr-del">
                                                        <i class="vd-icon icon-recycle J-attr-del"></i>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:if>


                                    </div>
                                    <div class="attr-add">
                                        <a href="javascript:void(0);" class="attr-add-btn J-attr-add">
                                            <i class="vd-icon icon-add"></i>添加属性值
                                        </a>
                                        <span class="attr-add-tip">（已添加<span class="J-attr-num"></span>条，最多可添加50条）</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="form-btn">
                    <div class="form-fields">
                        <button type="submit" class="btn btn-blue btn-large">保存</button>
                        <c:if test="${null ne baseCategoryVo.baseCategoryId }">
                            <c:if test="${baseCategoryVo.baseCategoryLevel eq null || baseCategoryVo.baseCategoryLevel eq 1}">
                                <a href="./getCategoryInfo.do?baseCategoryId=${baseCategoryVo.baseCategoryId}" class="btn btn-large">取消</a>
                            </c:if>
                            <c:if test="${baseCategoryVo.baseCategoryLevel eq 2}">
                                <a href="./getCategoryInfo.do?baseCategoryId=${baseCategoryVo.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.firstLevelCategoryName}" class="btn btn-large">取消</a>
                            </c:if>
                            <c:if test="${baseCategoryVo.baseCategoryLevel eq 3}">
                                <a href="./getCategoryInfo.do?baseCategoryId=${baseCategoryVo.baseCategoryId}&firstLevelCategoryName=${baseCategoryVo.firstLevelCategoryName}&secondLevelCategoryName=${baseCategoryVo.secondLevelCategoryName}" class="btn btn-large">取消</a>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <script type="text/json" class="J-attr-json">
        ${attrAndValueJson}

    </script>
    <script type="text/tmpl" class="J-attr-tmpl">
        <div class="attr-tr cf J-attr-item">
            <div class="attr-item col-4">
                <div class="J-attr-name-wrap"></div>
                <input type="hidden" value="" class="J-attr-name" name="baseAttributeId">
            </div>
            <div class="attr-item col-4">
                <div class="J-attr-value-wrap"></div>
                <input type="hidden" value="" class="J-attr-value" name="baseAttributeValueIds">
            </div>
            <div class="col-1 attr-del">
                <i class="vd-icon icon-recycle J-attr-del"></i>
            </div>
        </div>
    </script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/basecategory/add.js?rnd=<%=Math.random()%>"></script>
</body>

</html>